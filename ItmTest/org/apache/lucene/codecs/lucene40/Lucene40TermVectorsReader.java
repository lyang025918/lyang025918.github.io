// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Lucene40TermVectorsReader.java

package org.apache.lucene.codecs.lucene40;

import java.io.Closeable;
import java.io.IOException;
import java.util.*;
import org.apache.lucene.codecs.CodecUtil;
import org.apache.lucene.codecs.TermVectorsReader;
import org.apache.lucene.index.*;
import org.apache.lucene.store.*;
import org.apache.lucene.util.*;

public class Lucene40TermVectorsReader extends TermVectorsReader
	implements Closeable
{
	private static class TVDocsAndPositionsEnum extends DocsAndPositionsEnum
	{

		private boolean didNext;
		private int doc;
		private int nextPos;
		private Bits liveDocs;
		private int positions[];
		private int startOffsets[];
		private int endOffsets[];
		private int payloadOffsets[];
		private BytesRef payload;
		private byte payloadBytes[];
		static final boolean $assertionsDisabled = !org/apache/lucene/codecs/lucene40/Lucene40TermVectorsReader.desiredAssertionStatus();

		public int freq()
			throws IOException
		{
			if (positions != null)
				return positions.length;
			if (!$assertionsDisabled && startOffsets == null)
				throw new AssertionError();
			else
				return startOffsets.length;
		}

		public int docID()
		{
			return doc;
		}

		public int nextDoc()
		{
			if (!didNext && (liveDocs == null || liveDocs.get(0)))
			{
				didNext = true;
				return doc = 0;
			} else
			{
				return doc = 0x7fffffff;
			}
		}

		public int advance(int target)
		{
			if (!didNext && target == 0)
				return nextDoc();
			else
				return doc = 0x7fffffff;
		}

		public void reset(Bits liveDocs, int positions[], int startOffsets[], int endOffsets[], int payloadLengths[], byte payloadBytes[])
		{
			this.liveDocs = liveDocs;
			this.positions = positions;
			this.startOffsets = startOffsets;
			this.endOffsets = endOffsets;
			payloadOffsets = payloadLengths;
			this.payloadBytes = payloadBytes;
			doc = -1;
			didNext = false;
			nextPos = 0;
		}

		public BytesRef getPayload()
		{
			if (payloadOffsets == null)
				return null;
			int off = payloadOffsets[nextPos - 1];
			int end = nextPos != payloadOffsets.length ? payloadOffsets[nextPos] : payloadBytes.length;
			if (end - off == 0)
			{
				return null;
			} else
			{
				payload.bytes = payloadBytes;
				payload.offset = off;
				payload.length = end - off;
				return payload;
			}
		}

		public int nextPosition()
		{
			if (!$assertionsDisabled && (positions == null || nextPos >= positions.length) && (startOffsets == null || nextPos >= startOffsets.length))
				throw new AssertionError();
			if (positions != null)
			{
				return positions[nextPos++];
			} else
			{
				nextPos++;
				return -1;
			}
		}

		public int startOffset()
		{
			if (startOffsets == null)
				return -1;
			else
				return startOffsets[nextPos - 1];
		}

		public int endOffset()
		{
			if (endOffsets == null)
				return -1;
			else
				return endOffsets[nextPos - 1];
		}


		private TVDocsAndPositionsEnum()
		{
			doc = -1;
			payload = new BytesRef();
		}

	}

	private static class TVDocsEnum extends DocsEnum
	{

		private boolean didNext;
		private int doc;
		private int freq;
		private Bits liveDocs;

		public int freq()
			throws IOException
		{
			return freq;
		}

		public int docID()
		{
			return doc;
		}

		public int nextDoc()
		{
			if (!didNext && (liveDocs == null || liveDocs.get(0)))
			{
				didNext = true;
				return doc = 0;
			} else
			{
				return doc = 0x7fffffff;
			}
		}

		public int advance(int target)
		{
			if (!didNext && target == 0)
				return nextDoc();
			else
				return doc = 0x7fffffff;
		}

		public void reset(Bits liveDocs, int freq)
		{
			this.liveDocs = liveDocs;
			this.freq = freq;
			doc = -1;
			didNext = false;
		}

		private TVDocsEnum()
		{
			doc = -1;
		}

	}

	private class TVTermsEnum extends TermsEnum
	{

		private final IndexInput origTVF;
		private final IndexInput tvf;
		private int numTerms;
		private int nextTerm;
		private int freq;
		private BytesRef lastTerm;
		private BytesRef term;
		private boolean storePositions;
		private boolean storeOffsets;
		private boolean storePayloads;
		private long tvfFP;
		private int positions[];
		private int startOffsets[];
		private int endOffsets[];
		private int payloadOffsets[];
		private int lastPayloadLength;
		private byte payloadData[];
		static final boolean $assertionsDisabled = !org/apache/lucene/codecs/lucene40/Lucene40TermVectorsReader.desiredAssertionStatus();
		final Lucene40TermVectorsReader this$0;

		public boolean canReuse(IndexInput tvf)
		{
			return tvf == origTVF;
		}

		public void reset(int numTerms, long tvfFPStart, boolean storePositions, boolean storeOffsets, boolean storePayloads)
			throws IOException
		{
			this.numTerms = numTerms;
			this.storePositions = storePositions;
			this.storeOffsets = storeOffsets;
			this.storePayloads = storePayloads;
			nextTerm = 0;
			tvf.seek(tvfFPStart);
			tvfFP = 1L + tvfFPStart;
			positions = null;
			startOffsets = null;
			endOffsets = null;
			payloadOffsets = null;
			payloadData = null;
			lastPayloadLength = -1;
		}

		public org.apache.lucene.index.TermsEnum.SeekStatus seekCeil(BytesRef text, boolean useCache)
			throws IOException
		{
			if (nextTerm != 0)
			{
				int cmp = text.compareTo(term);
				if (cmp < 0)
				{
					nextTerm = 0;
					tvf.seek(tvfFP);
				} else
				if (cmp == 0)
					return org.apache.lucene.index.TermsEnum.SeekStatus.FOUND;
			}
			while (next() != null) 
			{
				int cmp = text.compareTo(term);
				if (cmp < 0)
					return org.apache.lucene.index.TermsEnum.SeekStatus.NOT_FOUND;
				if (cmp == 0)
					return org.apache.lucene.index.TermsEnum.SeekStatus.FOUND;
			}
			return org.apache.lucene.index.TermsEnum.SeekStatus.END;
		}

		public void seekExact(long ord)
		{
			throw new UnsupportedOperationException();
		}

		public BytesRef next()
			throws IOException
		{
			if (nextTerm >= numTerms)
				return null;
			term.copyBytes(lastTerm);
			int start = tvf.readVInt();
			int deltaLen = tvf.readVInt();
			term.length = start + deltaLen;
			term.grow(term.length);
			tvf.readBytes(term.bytes, start, deltaLen);
			freq = tvf.readVInt();
			if (storePayloads)
			{
				positions = new int[freq];
				payloadOffsets = new int[freq];
				int totalPayloadLength = 0;
				int pos = 0;
				for (int posUpto = 0; posUpto < freq; posUpto++)
				{
					int code = tvf.readVInt();
					pos += code >>> 1;
					positions[posUpto] = pos;
					if ((code & 1) != 0)
						lastPayloadLength = tvf.readVInt();
					payloadOffsets[posUpto] = totalPayloadLength;
					totalPayloadLength += lastPayloadLength;
					if (!$assertionsDisabled && totalPayloadLength < 0)
						throw new AssertionError();
				}

				payloadData = new byte[totalPayloadLength];
				tvf.readBytes(payloadData, 0, payloadData.length);
			} else
			if (storePositions)
			{
				positions = new int[freq];
				int pos = 0;
				for (int posUpto = 0; posUpto < freq; posUpto++)
				{
					pos += tvf.readVInt();
					positions[posUpto] = pos;
				}

			}
			if (storeOffsets)
			{
				startOffsets = new int[freq];
				endOffsets = new int[freq];
				int offset = 0;
				for (int posUpto = 0; posUpto < freq; posUpto++)
				{
					startOffsets[posUpto] = offset + tvf.readVInt();
					offset = endOffsets[posUpto] = startOffsets[posUpto] + tvf.readVInt();
				}

			}
			lastTerm.copyBytes(term);
			nextTerm++;
			return term;
		}

		public BytesRef term()
		{
			return term;
		}

		public long ord()
		{
			throw new UnsupportedOperationException();
		}

		public int docFreq()
		{
			return 1;
		}

		public long totalTermFreq()
		{
			return (long)freq;
		}

		public DocsEnum docs(Bits liveDocs, DocsEnum reuse, int flags)
			throws IOException
		{
			TVDocsEnum docsEnum;
			if (reuse != null && (reuse instanceof TVDocsEnum))
				docsEnum = (TVDocsEnum)reuse;
			else
				docsEnum = new TVDocsEnum();
			docsEnum.reset(liveDocs, freq);
			return docsEnum;
		}

		public DocsAndPositionsEnum docsAndPositions(Bits liveDocs, DocsAndPositionsEnum reuse, int flags)
			throws IOException
		{
			if (!storePositions && !storeOffsets)
				return null;
			TVDocsAndPositionsEnum docsAndPositionsEnum;
			if (reuse != null && (reuse instanceof TVDocsAndPositionsEnum))
				docsAndPositionsEnum = (TVDocsAndPositionsEnum)reuse;
			else
				docsAndPositionsEnum = new TVDocsAndPositionsEnum();
			docsAndPositionsEnum.reset(liveDocs, positions, startOffsets, endOffsets, payloadOffsets, payloadData);
			return docsAndPositionsEnum;
		}

		public Comparator getComparator()
		{
			return BytesRef.getUTF8SortedAsUnicodeComparator();
		}


		public TVTermsEnum()
		{
			this$0 = Lucene40TermVectorsReader.this;
			super();
			lastTerm = new BytesRef();
			term = new BytesRef();
			origTVF = Lucene40TermVectorsReader.this.tvf;
			tvf = origTVF.clone();
		}
	}

	private class TVTerms extends Terms
	{

		private final int numTerms;
		private final long tvfFPStart;
		private final boolean storePositions;
		private final boolean storeOffsets;
		private final boolean storePayloads;
		final Lucene40TermVectorsReader this$0;

		public TermsEnum iterator(TermsEnum reuse)
			throws IOException
		{
			TVTermsEnum termsEnum;
			if (reuse instanceof TVTermsEnum)
			{
				termsEnum = (TVTermsEnum)reuse;
				if (!termsEnum.canReuse(tvf))
					termsEnum = new TVTermsEnum();
			} else
			{
				termsEnum = new TVTermsEnum();
			}
			termsEnum.reset(numTerms, tvfFPStart, storePositions, storeOffsets, storePayloads);
			return termsEnum;
		}

		public long size()
		{
			return (long)numTerms;
		}

		public long getSumTotalTermFreq()
		{
			return -1L;
		}

		public long getSumDocFreq()
		{
			return (long)numTerms;
		}

		public int getDocCount()
		{
			return 1;
		}

		public Comparator getComparator()
		{
			return BytesRef.getUTF8SortedAsUnicodeComparator();
		}

		public boolean hasOffsets()
		{
			return storeOffsets;
		}

		public boolean hasPositions()
		{
			return storePositions;
		}

		public boolean hasPayloads()
		{
			return storePayloads;
		}

		public TVTerms(long tvfFP)
			throws IOException
		{
			this$0 = Lucene40TermVectorsReader.this;
			super();
			tvf.seek(tvfFP);
			numTerms = tvf.readVInt();
			byte bits = tvf.readByte();
			storePositions = (bits & 1) != 0;
			storeOffsets = (bits & 2) != 0;
			storePayloads = (bits & 4) != 0;
			tvfFPStart = tvf.getFilePointer();
		}
	}

	private class TVFields extends Fields
	{

		private final int fieldNumbers[];
		private final long fieldFPs[];
		private final Map fieldNumberToIndex = new HashMap();
		static final boolean $assertionsDisabled = !org/apache/lucene/codecs/lucene40/Lucene40TermVectorsReader.desiredAssertionStatus();
		final Lucene40TermVectorsReader this$0;

		public Iterator iterator()
		{
			return new Iterator() {

				private int fieldUpto;
				final TVFields this$1;

				public String next()
				{
					if (fieldNumbers != null && fieldUpto < fieldNumbers.length)
						return fieldInfos.fieldInfo(fieldNumbers[fieldUpto++]).name;
					else
						throw new NoSuchElementException();
				}

				public boolean hasNext()
				{
					return fieldNumbers != null && fieldUpto < fieldNumbers.length;
				}

				public void remove()
				{
					throw new UnsupportedOperationException();
				}

				public volatile Object next()
				{
					return next();
				}

				
				{
					this$1 = TVFields.this;
					super();
				}
			};
		}

		public Terms terms(String field)
			throws IOException
		{
			FieldInfo fieldInfo = fieldInfos.fieldInfo(field);
			if (fieldInfo == null)
				return null;
			Integer fieldIndex = (Integer)fieldNumberToIndex.get(Integer.valueOf(fieldInfo.number));
			if (fieldIndex == null)
				return null;
			else
				return new TVTerms(fieldFPs[fieldIndex.intValue()]);
		}

		public int size()
		{
			if (fieldNumbers == null)
				return 0;
			else
				return fieldNumbers.length;
		}



		public TVFields(int docID)
			throws IOException
		{
			this$0 = Lucene40TermVectorsReader.this;
			super();
			seekTvx(docID);
			tvd.seek(tvx.readLong());
			int fieldCount = tvd.readVInt();
			if (!$assertionsDisabled && fieldCount < 0)
				throw new AssertionError();
			if (fieldCount != 0)
			{
				fieldNumbers = new int[fieldCount];
				fieldFPs = new long[fieldCount];
				for (int fieldUpto = 0; fieldUpto < fieldCount; fieldUpto++)
				{
					int fieldNumber = tvd.readVInt();
					fieldNumbers[fieldUpto] = fieldNumber;
					fieldNumberToIndex.put(Integer.valueOf(fieldNumber), Integer.valueOf(fieldUpto));
				}

				long position = tvx.readLong();
				fieldFPs[0] = position;
				for (int fieldUpto = 1; fieldUpto < fieldCount; fieldUpto++)
				{
					position += tvd.readVLong();
					fieldFPs[fieldUpto] = position;
				}

			} else
			{
				fieldNumbers = null;
				fieldFPs = null;
			}
		}
	}


	static final byte STORE_POSITIONS_WITH_TERMVECTOR = 1;
	static final byte STORE_OFFSET_WITH_TERMVECTOR = 2;
	static final byte STORE_PAYLOAD_WITH_TERMVECTOR = 4;
	static final String VECTORS_FIELDS_EXTENSION = "tvf";
	static final String VECTORS_DOCUMENTS_EXTENSION = "tvd";
	static final String VECTORS_INDEX_EXTENSION = "tvx";
	static final String CODEC_NAME_FIELDS = "Lucene40TermVectorsFields";
	static final String CODEC_NAME_DOCS = "Lucene40TermVectorsDocs";
	static final String CODEC_NAME_INDEX = "Lucene40TermVectorsIndex";
	static final int VERSION_NO_PAYLOADS = 0;
	static final int VERSION_PAYLOADS = 1;
	static final int VERSION_START = 0;
	static final int VERSION_CURRENT = 1;
	static final long HEADER_LENGTH_FIELDS = (long)CodecUtil.headerLength("Lucene40TermVectorsFields");
	static final long HEADER_LENGTH_DOCS = (long)CodecUtil.headerLength("Lucene40TermVectorsDocs");
	static final long HEADER_LENGTH_INDEX = (long)CodecUtil.headerLength("Lucene40TermVectorsIndex");
	private FieldInfos fieldInfos;
	private IndexInput tvx;
	private IndexInput tvd;
	private IndexInput tvf;
	private int size;
	private int numTotalDocs;
	static final boolean $assertionsDisabled = !org/apache/lucene/codecs/lucene40/Lucene40TermVectorsReader.desiredAssertionStatus();

	Lucene40TermVectorsReader(FieldInfos fieldInfos, IndexInput tvx, IndexInput tvd, IndexInput tvf, int size, int numTotalDocs)
	{
		this.fieldInfos = fieldInfos;
		this.tvx = tvx;
		this.tvd = tvd;
		this.tvf = tvf;
		this.size = size;
		this.numTotalDocs = numTotalDocs;
	}

	public Lucene40TermVectorsReader(Directory d, SegmentInfo si, FieldInfos fieldInfos, IOContext context)
		throws IOException
	{
		String segment;
		int size;
		boolean success;
		segment = si.name;
		size = si.getDocCount();
		success = false;
		String idxName = IndexFileNames.segmentFileName(segment, "", "tvx");
		tvx = d.openInput(idxName, context);
		int tvxVersion = CodecUtil.checkHeader(tvx, "Lucene40TermVectorsIndex", 0, 1);
		String fn = IndexFileNames.segmentFileName(segment, "", "tvd");
		tvd = d.openInput(fn, context);
		int tvdVersion = CodecUtil.checkHeader(tvd, "Lucene40TermVectorsDocs", 0, 1);
		fn = IndexFileNames.segmentFileName(segment, "", "tvf");
		tvf = d.openInput(fn, context);
		int tvfVersion = CodecUtil.checkHeader(tvf, "Lucene40TermVectorsFields", 0, 1);
		if (!$assertionsDisabled && HEADER_LENGTH_INDEX != tvx.getFilePointer())
			throw new AssertionError();
		if (!$assertionsDisabled && HEADER_LENGTH_DOCS != tvd.getFilePointer())
			throw new AssertionError();
		if (!$assertionsDisabled && HEADER_LENGTH_FIELDS != tvf.getFilePointer())
			throw new AssertionError();
		if (!$assertionsDisabled && tvxVersion != tvdVersion)
			throw new AssertionError();
		if (!$assertionsDisabled && tvxVersion != tvfVersion)
			throw new AssertionError();
		numTotalDocs = (int)(tvx.length() - HEADER_LENGTH_INDEX >> 4);
		this.size = numTotalDocs;
		if (!$assertionsDisabled && size != 0 && numTotalDocs != size)
			throw new AssertionError();
		this.fieldInfos = fieldInfos;
		success = true;
		if (!success)
			try
			{
				close();
			}
			catch (Throwable t) { }
		break MISSING_BLOCK_LABEL_351;
		Exception exception;
		exception;
		if (!success)
			try
			{
				close();
			}
			catch (Throwable t) { }
		throw exception;
	}

	IndexInput getTvdStream()
	{
		return tvd;
	}

	IndexInput getTvfStream()
	{
		return tvf;
	}

	void seekTvx(int docNum)
		throws IOException
	{
		tvx.seek((long)docNum * 16L + HEADER_LENGTH_INDEX);
	}

	final void rawDocs(int tvdLengths[], int tvfLengths[], int startDocID, int numDocs)
		throws IOException
	{
		if (tvx == null)
		{
			Arrays.fill(tvdLengths, 0);
			Arrays.fill(tvfLengths, 0);
			return;
		}
		seekTvx(startDocID);
		long tvdPosition = tvx.readLong();
		tvd.seek(tvdPosition);
		long tvfPosition = tvx.readLong();
		tvf.seek(tvfPosition);
		long lastTvdPosition = tvdPosition;
		long lastTvfPosition = tvfPosition;
		for (int count = 0; count < numDocs;)
		{
			int docID = startDocID + count + 1;
			if (!$assertionsDisabled && docID > numTotalDocs)
				throw new AssertionError();
			if (docID < numTotalDocs)
			{
				tvdPosition = tvx.readLong();
				tvfPosition = tvx.readLong();
			} else
			{
				tvdPosition = tvd.length();
				tvfPosition = tvf.length();
				if (!$assertionsDisabled && count != numDocs - 1)
					throw new AssertionError();
			}
			tvdLengths[count] = (int)(tvdPosition - lastTvdPosition);
			tvfLengths[count] = (int)(tvfPosition - lastTvfPosition);
			count++;
			lastTvdPosition = tvdPosition;
			lastTvfPosition = tvfPosition;
		}

	}

	public void close()
		throws IOException
	{
		IOUtils.close(new Closeable[] {
			tvx, tvd, tvf
		});
	}

	int size()
	{
		return size;
	}

	public Fields get(int docID)
		throws IOException
	{
		if (docID < 0 || docID >= numTotalDocs)
			throw new IllegalArgumentException((new StringBuilder()).append("doID=").append(docID).append(" is out of bounds [0..").append(numTotalDocs - 1).append("]").toString());
		if (tvx != null)
		{
			Fields fields = new TVFields(docID);
			if (fields.size() == 0)
				return null;
			else
				return fields;
		} else
		{
			return null;
		}
	}

	public TermVectorsReader clone()
	{
		IndexInput cloneTvx = null;
		IndexInput cloneTvd = null;
		IndexInput cloneTvf = null;
		if (tvx != null && tvd != null && tvf != null)
		{
			cloneTvx = tvx.clone();
			cloneTvd = tvd.clone();
			cloneTvf = tvf.clone();
		}
		return new Lucene40TermVectorsReader(fieldInfos, cloneTvx, cloneTvd, cloneTvf, size, numTotalDocs);
	}

	public volatile Object clone()
		throws CloneNotSupportedException
	{
		return clone();
	}





}
