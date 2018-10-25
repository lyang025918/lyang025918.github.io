// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Lucene40TermVectorsWriter.java

package org.apache.lucene.codecs.lucene40;

import java.io.Closeable;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import org.apache.lucene.codecs.CodecUtil;
import org.apache.lucene.codecs.TermVectorsWriter;
import org.apache.lucene.index.*;
import org.apache.lucene.store.*;
import org.apache.lucene.util.*;

// Referenced classes of package org.apache.lucene.codecs.lucene40:
//			Lucene40TermVectorsReader

public final class Lucene40TermVectorsWriter extends TermVectorsWriter
{

	private final Directory directory;
	private final String segment;
	private IndexOutput tvx;
	private IndexOutput tvd;
	private IndexOutput tvf;
	private long fps[];
	private int fieldCount;
	private int numVectorFields;
	private String lastFieldName;
	private final BytesRef lastTerm;
	private int offsetStartBuffer[];
	private int offsetEndBuffer[];
	private BytesRef payloadData;
	private int bufferedIndex;
	private int bufferedFreq;
	private boolean positions;
	private boolean offsets;
	private boolean payloads;
	int lastPosition;
	int lastOffset;
	int lastPayloadLength;
	BytesRef scratch;
	private static final int MAX_RAW_MERGE_DOCS = 4192;
	static final boolean $assertionsDisabled = !org/apache/lucene/codecs/lucene40/Lucene40TermVectorsWriter.desiredAssertionStatus();

	public Lucene40TermVectorsWriter(Directory directory, String segment, IOContext context)
		throws IOException
	{
		boolean success;
		tvx = null;
		tvd = null;
		tvf = null;
		fps = new long[10];
		fieldCount = 0;
		numVectorFields = 0;
		lastTerm = new BytesRef(10);
		offsetStartBuffer = new int[10];
		offsetEndBuffer = new int[10];
		payloadData = new BytesRef(10);
		bufferedIndex = 0;
		bufferedFreq = 0;
		positions = false;
		offsets = false;
		payloads = false;
		lastPosition = 0;
		lastOffset = 0;
		lastPayloadLength = -1;
		scratch = new BytesRef();
		this.directory = directory;
		this.segment = segment;
		success = false;
		tvx = directory.createOutput(IndexFileNames.segmentFileName(segment, "", "tvx"), context);
		CodecUtil.writeHeader(tvx, "Lucene40TermVectorsIndex", 1);
		tvd = directory.createOutput(IndexFileNames.segmentFileName(segment, "", "tvd"), context);
		CodecUtil.writeHeader(tvd, "Lucene40TermVectorsDocs", 1);
		tvf = directory.createOutput(IndexFileNames.segmentFileName(segment, "", "tvf"), context);
		CodecUtil.writeHeader(tvf, "Lucene40TermVectorsFields", 1);
		if (!$assertionsDisabled && Lucene40TermVectorsReader.HEADER_LENGTH_INDEX != tvx.getFilePointer())
			throw new AssertionError();
		if (!$assertionsDisabled && Lucene40TermVectorsReader.HEADER_LENGTH_DOCS != tvd.getFilePointer())
			throw new AssertionError();
		if (!$assertionsDisabled && Lucene40TermVectorsReader.HEADER_LENGTH_FIELDS != tvf.getFilePointer())
			throw new AssertionError();
		success = true;
		if (!success)
			abort();
		break MISSING_BLOCK_LABEL_337;
		Exception exception;
		exception;
		if (!success)
			abort();
		throw exception;
	}

	public void startDocument(int numVectorFields)
		throws IOException
	{
		lastFieldName = null;
		this.numVectorFields = numVectorFields;
		tvx.writeLong(tvd.getFilePointer());
		tvx.writeLong(tvf.getFilePointer());
		tvd.writeVInt(numVectorFields);
		fieldCount = 0;
		fps = ArrayUtil.grow(fps, numVectorFields);
	}

	public void startField(FieldInfo info, int numTerms, boolean positions, boolean offsets, boolean payloads)
		throws IOException
	{
		if (!$assertionsDisabled && lastFieldName != null && info.name.compareTo(lastFieldName) <= 0)
			throw new AssertionError((new StringBuilder()).append("fieldName=").append(info.name).append(" lastFieldName=").append(lastFieldName).toString());
		lastFieldName = info.name;
		this.positions = positions;
		this.offsets = offsets;
		this.payloads = payloads;
		lastTerm.length = 0;
		lastPayloadLength = -1;
		fps[fieldCount++] = tvf.getFilePointer();
		tvd.writeVInt(info.number);
		tvf.writeVInt(numTerms);
		byte bits = 0;
		if (positions)
			bits |= 1;
		if (offsets)
			bits |= 2;
		if (payloads)
			bits |= 4;
		tvf.writeByte(bits);
		if (!$assertionsDisabled && fieldCount > numVectorFields)
			throw new AssertionError();
		if (fieldCount == numVectorFields)
		{
			for (int i = 1; i < fieldCount; i++)
				tvd.writeVLong(fps[i] - fps[i - 1]);

		}
	}

	public void startTerm(BytesRef term, int freq)
		throws IOException
	{
		int prefix = StringHelper.bytesDifference(lastTerm, term);
		int suffix = term.length - prefix;
		tvf.writeVInt(prefix);
		tvf.writeVInt(suffix);
		tvf.writeBytes(term.bytes, term.offset + prefix, suffix);
		tvf.writeVInt(freq);
		lastTerm.copyBytes(term);
		lastPosition = lastOffset = 0;
		if (offsets && positions)
		{
			offsetStartBuffer = ArrayUtil.grow(offsetStartBuffer, freq);
			offsetEndBuffer = ArrayUtil.grow(offsetEndBuffer, freq);
		}
		bufferedIndex = 0;
		bufferedFreq = freq;
		payloadData.length = 0;
	}

	public void addProx(int numProx, DataInput positions, DataInput offsets)
		throws IOException
	{
		if (payloads)
		{
			for (int i = 0; i < numProx; i++)
			{
				int code = positions.readVInt();
				if ((code & 1) == 1)
				{
					int length = positions.readVInt();
					scratch.grow(length);
					scratch.length = length;
					positions.readBytes(scratch.bytes, scratch.offset, scratch.length);
					writePosition(code >>> 1, scratch);
				} else
				{
					writePosition(code >>> 1, null);
				}
			}

			tvf.writeBytes(payloadData.bytes, payloadData.offset, payloadData.length);
		} else
		if (positions != null)
		{
			for (int i = 0; i < numProx; i++)
				tvf.writeVInt(positions.readVInt() >>> 1);

		}
		if (offsets != null)
		{
			for (int i = 0; i < numProx; i++)
			{
				tvf.writeVInt(offsets.readVInt());
				tvf.writeVInt(offsets.readVInt());
			}

		}
	}

	public void addPosition(int position, int startOffset, int endOffset, BytesRef payload)
		throws IOException
	{
		if (positions && (offsets || payloads))
		{
			writePosition(position - lastPosition, payload);
			lastPosition = position;
			if (offsets)
			{
				offsetStartBuffer[bufferedIndex] = startOffset;
				offsetEndBuffer[bufferedIndex] = endOffset;
			}
			bufferedIndex++;
			if (bufferedIndex == bufferedFreq)
			{
				if (payloads)
					tvf.writeBytes(payloadData.bytes, payloadData.offset, payloadData.length);
				for (int i = 0; i < bufferedIndex; i++)
					if (offsets)
					{
						tvf.writeVInt(offsetStartBuffer[i] - lastOffset);
						tvf.writeVInt(offsetEndBuffer[i] - offsetStartBuffer[i]);
						lastOffset = offsetEndBuffer[i];
					}

			}
		} else
		if (positions)
		{
			writePosition(position - lastPosition, payload);
			lastPosition = position;
		} else
		if (offsets)
		{
			tvf.writeVInt(startOffset - lastOffset);
			tvf.writeVInt(endOffset - startOffset);
			lastOffset = endOffset;
		}
	}

	private void writePosition(int delta, BytesRef payload)
		throws IOException
	{
		if (payloads)
		{
			int payloadLength = payload != null ? payload.length : 0;
			if (payloadLength != lastPayloadLength)
			{
				lastPayloadLength = payloadLength;
				tvf.writeVInt(delta << 1 | 1);
				tvf.writeVInt(payloadLength);
			} else
			{
				tvf.writeVInt(delta << 1);
			}
			if (payloadLength > 0)
			{
				if (payloadLength + payloadData.length < 0)
					throw new UnsupportedOperationException("A term cannot have more than Integer.MAX_VALUE bytes of payload data in a single document");
				payloadData.append(payload);
			}
		} else
		{
			tvf.writeVInt(delta);
		}
	}

	public void abort()
	{
		try
		{
			close();
		}
		catch (Throwable ignored) { }
		IOUtils.deleteFilesIgnoringExceptions(directory, new String[] {
			IndexFileNames.segmentFileName(segment, "", "tvx"), IndexFileNames.segmentFileName(segment, "", "tvd"), IndexFileNames.segmentFileName(segment, "", "tvf")
		});
	}

	private void addRawDocuments(Lucene40TermVectorsReader reader, int tvdLengths[], int tvfLengths[], int numDocs)
		throws IOException
	{
		long tvdPosition = tvd.getFilePointer();
		long tvfPosition = tvf.getFilePointer();
		long tvdStart = tvdPosition;
		long tvfStart = tvfPosition;
		for (int i = 0; i < numDocs; i++)
		{
			tvx.writeLong(tvdPosition);
			tvdPosition += tvdLengths[i];
			tvx.writeLong(tvfPosition);
			tvfPosition += tvfLengths[i];
		}

		tvd.copyBytes(reader.getTvdStream(), tvdPosition - tvdStart);
		tvf.copyBytes(reader.getTvfStream(), tvfPosition - tvfStart);
		if (!$assertionsDisabled && tvd.getFilePointer() != tvdPosition)
			throw new AssertionError();
		if (!$assertionsDisabled && tvf.getFilePointer() != tvfPosition)
			throw new AssertionError();
		else
			return;
	}

	public final int merge(MergeState mergeState)
		throws IOException
	{
		int rawDocLengths[] = new int[4192];
		int rawDocLengths2[] = new int[4192];
		int idx = 0;
		int numDocs = 0;
		for (int i = 0; i < mergeState.readers.size(); i++)
		{
			AtomicReader reader = (AtomicReader)mergeState.readers.get(i);
			SegmentReader matchingSegmentReader = mergeState.matchingSegmentReaders[idx++];
			Lucene40TermVectorsReader matchingVectorsReader = null;
			if (matchingSegmentReader != null)
			{
				org.apache.lucene.codecs.TermVectorsReader vectorsReader = matchingSegmentReader.getTermVectorsReader();
				if (vectorsReader != null && (vectorsReader instanceof Lucene40TermVectorsReader))
					matchingVectorsReader = (Lucene40TermVectorsReader)vectorsReader;
			}
			if (reader.getLiveDocs() != null)
				numDocs += copyVectorsWithDeletions(mergeState, matchingVectorsReader, reader, rawDocLengths, rawDocLengths2);
			else
				numDocs += copyVectorsNoDeletions(mergeState, matchingVectorsReader, reader, rawDocLengths, rawDocLengths2);
		}

		finish(mergeState.fieldInfos, numDocs);
		return numDocs;
	}

	private int copyVectorsWithDeletions(MergeState mergeState, Lucene40TermVectorsReader matchingVectorsReader, AtomicReader reader, int rawDocLengths[], int rawDocLengths2[])
		throws IOException
	{
		int maxDoc = reader.maxDoc();
		Bits liveDocs = reader.getLiveDocs();
		int totalNumDocs = 0;
		if (matchingVectorsReader != null)
		{
			for (int docNum = 0; docNum < maxDoc;)
				if (!liveDocs.get(docNum))
				{
					docNum++;
				} else
				{
					int start = docNum;
					int numDocs = 0;
					do
					{
						docNum++;
						numDocs++;
						if (docNum >= maxDoc)
							break;
						if (liveDocs.get(docNum))
							continue;
						docNum++;
						break;
					} while (numDocs < 4192);
					matchingVectorsReader.rawDocs(rawDocLengths, rawDocLengths2, start, numDocs);
					addRawDocuments(matchingVectorsReader, rawDocLengths, rawDocLengths2, numDocs);
					totalNumDocs += numDocs;
					mergeState.checkAbort.work(300 * numDocs);
				}

		} else
		{
			for (int docNum = 0; docNum < maxDoc; docNum++)
				if (liveDocs.get(docNum))
				{
					org.apache.lucene.index.Fields vectors = reader.getTermVectors(docNum);
					addAllDocVectors(vectors, mergeState);
					totalNumDocs++;
					mergeState.checkAbort.work(300D);
				}

		}
		return totalNumDocs;
	}

	private int copyVectorsNoDeletions(MergeState mergeState, Lucene40TermVectorsReader matchingVectorsReader, AtomicReader reader, int rawDocLengths[], int rawDocLengths2[])
		throws IOException
	{
		int maxDoc = reader.maxDoc();
		if (matchingVectorsReader != null)
		{
			for (int docCount = 0; docCount < maxDoc;)
			{
				int len = Math.min(4192, maxDoc - docCount);
				matchingVectorsReader.rawDocs(rawDocLengths, rawDocLengths2, docCount, len);
				addRawDocuments(matchingVectorsReader, rawDocLengths, rawDocLengths2, len);
				docCount += len;
				mergeState.checkAbort.work(300 * len);
			}

		} else
		{
			for (int docNum = 0; docNum < maxDoc; docNum++)
			{
				org.apache.lucene.index.Fields vectors = reader.getTermVectors(docNum);
				addAllDocVectors(vectors, mergeState);
				mergeState.checkAbort.work(300D);
			}

		}
		return maxDoc;
	}

	public void finish(FieldInfos fis, int numDocs)
	{
		if (Lucene40TermVectorsReader.HEADER_LENGTH_INDEX + (long)numDocs * 16L != tvx.getFilePointer())
			throw new RuntimeException((new StringBuilder()).append("tvx size mismatch: mergedDocs is ").append(numDocs).append(" but tvx size is ").append(tvx.getFilePointer()).append(" file=").append(tvx.toString()).append("; now aborting this merge to prevent index corruption").toString());
		else
			return;
	}

	public void close()
		throws IOException
	{
		IOUtils.close(new Closeable[] {
			tvx, tvd, tvf
		});
		tvx = tvd = tvf = null;
	}

	public Comparator getComparator()
	{
		return BytesRef.getUTF8SortedAsUnicodeComparator();
	}

}
