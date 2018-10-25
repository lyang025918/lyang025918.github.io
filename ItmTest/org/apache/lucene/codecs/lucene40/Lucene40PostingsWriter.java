// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Lucene40PostingsWriter.java

package org.apache.lucene.codecs.lucene40;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.codecs.*;
import org.apache.lucene.index.*;
import org.apache.lucene.store.*;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.IOUtils;

// Referenced classes of package org.apache.lucene.codecs.lucene40:
//			Lucene40SkipListWriter

public final class Lucene40PostingsWriter extends PostingsWriterBase
{
	private static class PendingTerm
	{

		public final long freqStart;
		public final long proxStart;
		public final int skipOffset;

		public PendingTerm(long freqStart, long proxStart, int skipOffset)
		{
			this.freqStart = freqStart;
			this.proxStart = proxStart;
			this.skipOffset = skipOffset;
		}
	}


	static final String TERMS_CODEC = "Lucene40PostingsWriterTerms";
	static final String FRQ_CODEC = "Lucene40PostingsWriterFrq";
	static final String PRX_CODEC = "Lucene40PostingsWriterPrx";
	static final int VERSION_START = 0;
	static final int VERSION_CURRENT = 0;
	final IndexOutput freqOut;
	final IndexOutput proxOut;
	final Lucene40SkipListWriter skipListWriter;
	static final int DEFAULT_SKIP_INTERVAL = 16;
	final int skipInterval;
	final int skipMinimum;
	final int maxSkipLevels = 10;
	final int totalNumDocs;
	IndexOutput termsOut;
	org.apache.lucene.index.FieldInfo.IndexOptions indexOptions;
	boolean storePayloads;
	boolean storeOffsets;
	long freqStart;
	long proxStart;
	FieldInfo fieldInfo;
	int lastPayloadLength;
	int lastOffsetLength;
	int lastPosition;
	int lastOffset;
	int lastDocID;
	int df;
	private final List pendingTerms;
	private final RAMOutputStream bytesWriter;
	static final boolean $assertionsDisabled = !org/apache/lucene/codecs/lucene40/Lucene40PostingsWriter.desiredAssertionStatus();

	public Lucene40PostingsWriter(SegmentWriteState state)
		throws IOException
	{
		this(state, 16);
	}

	public Lucene40PostingsWriter(SegmentWriteState state, int skipInterval)
		throws IOException
	{
		boolean success;
		IndexOutput proxOut;
		maxSkipLevels = 10;
		pendingTerms = new ArrayList();
		bytesWriter = new RAMOutputStream();
		this.skipInterval = skipInterval;
		skipMinimum = skipInterval;
		String fileName = IndexFileNames.segmentFileName(state.segmentInfo.name, state.segmentSuffix, "frq");
		freqOut = state.directory.createOutput(fileName, state.context);
		success = false;
		proxOut = null;
		CodecUtil.writeHeader(freqOut, "Lucene40PostingsWriterFrq", 0);
		if (state.fieldInfos.hasProx())
		{
			String fileName = IndexFileNames.segmentFileName(state.segmentInfo.name, state.segmentSuffix, "prx");
			proxOut = state.directory.createOutput(fileName, state.context);
			CodecUtil.writeHeader(proxOut, "Lucene40PostingsWriterPrx", 0);
		} else
		{
			proxOut = null;
		}
		this.proxOut = proxOut;
		success = true;
		if (!success)
			IOUtils.closeWhileHandlingException(new Closeable[] {
				freqOut, proxOut
			});
		break MISSING_BLOCK_LABEL_211;
		Exception exception;
		exception;
		if (!success)
			IOUtils.closeWhileHandlingException(new Closeable[] {
				freqOut, proxOut
			});
		throw exception;
		totalNumDocs = state.segmentInfo.getDocCount();
		skipListWriter = new Lucene40SkipListWriter(skipInterval, 10, totalNumDocs, freqOut, proxOut);
		return;
	}

	public void start(IndexOutput termsOut)
		throws IOException
	{
		this.termsOut = termsOut;
		CodecUtil.writeHeader(termsOut, "Lucene40PostingsWriterTerms", 0);
		termsOut.writeInt(skipInterval);
		termsOut.writeInt(10);
		termsOut.writeInt(skipMinimum);
	}

	public void startTerm()
	{
		freqStart = freqOut.getFilePointer();
		if (proxOut != null)
			proxStart = proxOut.getFilePointer();
		lastPayloadLength = -1;
		lastOffsetLength = -1;
		skipListWriter.resetSkip();
	}

	public void setField(FieldInfo fieldInfo)
	{
		this.fieldInfo = fieldInfo;
		indexOptions = fieldInfo.getIndexOptions();
		storeOffsets = indexOptions.compareTo(org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS) >= 0;
		storePayloads = fieldInfo.hasPayloads();
	}

	public void startDoc(int docID, int termDocFreq)
		throws IOException
	{
		int delta = docID - lastDocID;
		if (docID < 0 || df > 0 && delta <= 0)
			throw new CorruptIndexException((new StringBuilder()).append("docs out of order (").append(docID).append(" <= ").append(lastDocID).append(" ) (freqOut: ").append(freqOut).append(")").toString());
		if (++df % skipInterval == 0)
		{
			skipListWriter.setSkipData(lastDocID, storePayloads, lastPayloadLength, storeOffsets, lastOffsetLength);
			skipListWriter.bufferSkip(df);
		}
		if (!$assertionsDisabled && docID >= totalNumDocs)
			throw new AssertionError((new StringBuilder()).append("docID=").append(docID).append(" totalNumDocs=").append(totalNumDocs).toString());
		lastDocID = docID;
		if (indexOptions == org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_ONLY)
			freqOut.writeVInt(delta);
		else
		if (1 == termDocFreq)
		{
			freqOut.writeVInt(delta << 1 | 1);
		} else
		{
			freqOut.writeVInt(delta << 1);
			freqOut.writeVInt(termDocFreq);
		}
		lastPosition = 0;
		lastOffset = 0;
	}

	public void addPosition(int position, BytesRef payload, int startOffset, int endOffset)
		throws IOException
	{
		if (!$assertionsDisabled && indexOptions.compareTo(org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS) < 0)
			throw new AssertionError((new StringBuilder()).append("invalid indexOptions: ").append(indexOptions).toString());
		if (!$assertionsDisabled && proxOut == null)
			throw new AssertionError();
		int delta = position - lastPosition;
		if (!$assertionsDisabled && delta < 0)
			throw new AssertionError((new StringBuilder()).append("position=").append(position).append(" lastPosition=").append(lastPosition).toString());
		lastPosition = position;
		int payloadLength = 0;
		if (storePayloads)
		{
			payloadLength = payload != null ? payload.length : 0;
			if (payloadLength != lastPayloadLength)
			{
				lastPayloadLength = payloadLength;
				proxOut.writeVInt(delta << 1 | 1);
				proxOut.writeVInt(payloadLength);
			} else
			{
				proxOut.writeVInt(delta << 1);
			}
		} else
		{
			proxOut.writeVInt(delta);
		}
		if (storeOffsets)
		{
			int offsetDelta = startOffset - lastOffset;
			int offsetLength = endOffset - startOffset;
			if (!$assertionsDisabled && (offsetDelta < 0 || offsetLength < 0))
				throw new AssertionError((new StringBuilder()).append("startOffset=").append(startOffset).append(",lastOffset=").append(lastOffset).append(",endOffset=").append(endOffset).toString());
			if (offsetLength != lastOffsetLength)
			{
				proxOut.writeVInt(offsetDelta << 1 | 1);
				proxOut.writeVInt(offsetLength);
			} else
			{
				proxOut.writeVInt(offsetDelta << 1);
			}
			lastOffset = startOffset;
			lastOffsetLength = offsetLength;
		}
		if (payloadLength > 0)
			proxOut.writeBytes(payload.bytes, payload.offset, payloadLength);
	}

	public void finishDoc()
	{
	}

	public void finishTerm(TermStats stats)
		throws IOException
	{
		if (!$assertionsDisabled && stats.docFreq <= 0)
			throw new AssertionError();
		if (!$assertionsDisabled && stats.docFreq != df)
			throw new AssertionError();
		int skipOffset;
		if (df >= skipMinimum)
			skipOffset = (int)(skipListWriter.writeSkip(freqOut) - freqStart);
		else
			skipOffset = -1;
		pendingTerms.add(new PendingTerm(freqStart, proxStart, skipOffset));
		lastDocID = 0;
		df = 0;
	}

	public void flushTermsBlock(int start, int count)
		throws IOException
	{
		if (count == 0)
		{
			termsOut.writeByte((byte)0);
			return;
		}
		if (!$assertionsDisabled && start > pendingTerms.size())
			throw new AssertionError();
		if (!$assertionsDisabled && count > start)
			throw new AssertionError();
		int limit = (pendingTerms.size() - start) + count;
		PendingTerm firstTerm = (PendingTerm)pendingTerms.get(limit - count);
		bytesWriter.writeVLong(firstTerm.freqStart);
		if (firstTerm.skipOffset != -1)
		{
			if (!$assertionsDisabled && firstTerm.skipOffset <= 0)
				throw new AssertionError();
			bytesWriter.writeVInt(firstTerm.skipOffset);
		}
		if (indexOptions.compareTo(org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS) >= 0)
			bytesWriter.writeVLong(firstTerm.proxStart);
		long lastFreqStart = firstTerm.freqStart;
		long lastProxStart = firstTerm.proxStart;
		for (int idx = (limit - count) + 1; idx < limit; idx++)
		{
			PendingTerm term = (PendingTerm)pendingTerms.get(idx);
			bytesWriter.writeVLong(term.freqStart - lastFreqStart);
			lastFreqStart = term.freqStart;
			if (term.skipOffset != -1)
			{
				if (!$assertionsDisabled && term.skipOffset <= 0)
					throw new AssertionError();
				bytesWriter.writeVInt(term.skipOffset);
			}
			if (indexOptions.compareTo(org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS) >= 0)
			{
				bytesWriter.writeVLong(term.proxStart - lastProxStart);
				lastProxStart = term.proxStart;
			}
		}

		termsOut.writeVInt((int)bytesWriter.getFilePointer());
		bytesWriter.writeTo(termsOut);
		bytesWriter.reset();
		pendingTerms.subList(limit - count, limit).clear();
	}

	public void close()
		throws IOException
	{
		freqOut.close();
		if (proxOut != null)
			proxOut.close();
		break MISSING_BLOCK_LABEL_41;
		Exception exception;
		exception;
		if (proxOut != null)
			proxOut.close();
		throw exception;
	}

}
