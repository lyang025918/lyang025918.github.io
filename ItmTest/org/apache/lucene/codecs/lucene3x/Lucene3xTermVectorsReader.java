// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Lucene3xTermVectorsReader.java

package org.apache.lucene.codecs.lucene3x;

import java.io.Closeable;
import java.io.IOException;
import java.util.*;
import org.apache.lucene.codecs.TermVectorsReader;
import org.apache.lucene.index.*;
import org.apache.lucene.store.*;
import org.apache.lucene.util.*;

// Referenced classes of package org.apache.lucene.codecs.lucene3x:
//			Lucene3xSegmentInfoFormat

/**
 * @deprecated Class Lucene3xTermVectorsReader is deprecated
 */

class Lucene3xTermVectorsReader extends TermVectorsReader
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
		static final boolean $assertionsDisabled = !org/apache/lucene/codecs/lucene3x/Lucene3xTermVectorsReader.desiredAssertionStatus();

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

		public void reset(Bits liveDocs, TermAndPostings termAndPostings)
		{
			this.liveDocs = liveDocs;
			positions = termAndPostings.positions;
			startOffsets = termAndPostings.startOffsets;
			endOffsets = termAndPostings.endOffsets;
			doc = -1;
			didNext = false;
			nextPos = 0;
		}

		public BytesRef getPayload()
		{
			return null;
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
			if (startOffsets != null)
				return startOffsets[nextPos - 1];
			else
				return -1;
		}

		public int endOffset()
		{
			if (endOffsets != null)
				return endOffsets[nextPos - 1];
			else
				return -1;
		}


		private TVDocsAndPositionsEnum()
		{
			doc = -1;
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

		public void reset(Bits liveDocs, TermAndPostings termAndPostings)
		{
			this.liveDocs = liveDocs;
			freq = termAndPostings.freq;
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

		private boolean unicodeSortOrder;
		private final IndexInput origTVF;
		private final IndexInput tvf;
		private int numTerms;
		private int currentTerm;
		private boolean storePositions;
		private boolean storeOffsets;
		private TermAndPostings termAndPostings[];
		final Lucene3xTermVectorsReader this$0;

		public boolean canReuse(IndexInput tvf)
		{
			return tvf == origTVF;
		}

		public void reset(int numTerms, long tvfFPStart, boolean storePositions, boolean storeOffsets, boolean unicodeSortOrder)
			throws IOException
		{
			this.numTerms = numTerms;
			this.storePositions = storePositions;
			this.storeOffsets = storeOffsets;
			currentTerm = -1;
			tvf.seek(tvfFPStart);
			this.unicodeSortOrder = unicodeSortOrder;
			readVectors();
			if (unicodeSortOrder)
				Arrays.sort(termAndPostings, new Comparator() {

					final TVTermsEnum this$1;

					public int compare(TermAndPostings left, TermAndPostings right)
					{
						return left.term.compareTo(right.term);
					}

					public volatile int compare(Object x0, Object x1)
					{
						return compare((TermAndPostings)x0, (TermAndPostings)x1);
					}

				
				{
					this$1 = TVTermsEnum.this;
					super();
				}
				});
		}

		private void readVectors()
			throws IOException
		{
			termAndPostings = new TermAndPostings[numTerms];
			BytesRef lastTerm = new BytesRef();
			for (int i = 0; i < numTerms; i++)
			{
				TermAndPostings t = new TermAndPostings();
				BytesRef term = new BytesRef();
				term.copyBytes(lastTerm);
				int start = tvf.readVInt();
				int deltaLen = tvf.readVInt();
				term.length = start + deltaLen;
				term.grow(term.length);
				tvf.readBytes(term.bytes, start, deltaLen);
				t.term = term;
				int freq = tvf.readVInt();
				t.freq = freq;
				if (storePositions)
				{
					int positions[] = new int[freq];
					int pos = 0;
					for (int posUpto = 0; posUpto < freq; posUpto++)
					{
						int delta = tvf.readVInt();
						if (delta == -1)
							delta = 0;
						pos += delta;
						positions[posUpto] = pos;
					}

					t.positions = positions;
				}
				if (storeOffsets)
				{
					int startOffsets[] = new int[freq];
					int endOffsets[] = new int[freq];
					int offset = 0;
					for (int posUpto = 0; posUpto < freq; posUpto++)
					{
						startOffsets[posUpto] = offset + tvf.readVInt();
						offset = endOffsets[posUpto] = startOffsets[posUpto] + tvf.readVInt();
					}

					t.startOffsets = startOffsets;
					t.endOffsets = endOffsets;
				}
				lastTerm.copyBytes(term);
				termAndPostings[i] = t;
			}

		}

		public org.apache.lucene.index.TermsEnum.SeekStatus seekCeil(BytesRef text, boolean useCache)
			throws IOException
		{
			Comparator comparator = getComparator();
			for (int i = 0; i < numTerms; i++)
			{
				int cmp = comparator.compare(text, termAndPostings[i].term);
				if (cmp < 0)
				{
					currentTerm = i;
					return org.apache.lucene.index.TermsEnum.SeekStatus.NOT_FOUND;
				}
				if (cmp == 0)
				{
					currentTerm = i;
					return org.apache.lucene.index.TermsEnum.SeekStatus.FOUND;
				}
			}

			currentTerm = termAndPostings.length;
			return org.apache.lucene.index.TermsEnum.SeekStatus.END;
		}

		public void seekExact(long ord)
		{
			throw new UnsupportedOperationException();
		}

		public BytesRef next()
			throws IOException
		{
			if (++currentTerm >= numTerms)
				return null;
			else
				return term();
		}

		public BytesRef term()
		{
			return termAndPostings[currentTerm].term;
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
			return (long)termAndPostings[currentTerm].freq;
		}

		public DocsEnum docs(Bits liveDocs, DocsEnum reuse, int flags)
			throws IOException
		{
			TVDocsEnum docsEnum;
			if (reuse != null && (reuse instanceof TVDocsEnum))
				docsEnum = (TVDocsEnum)reuse;
			else
				docsEnum = new TVDocsEnum();
			docsEnum.reset(liveDocs, termAndPostings[currentTerm]);
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
			docsAndPositionsEnum.reset(liveDocs, termAndPostings[currentTerm]);
			return docsAndPositionsEnum;
		}

		public Comparator getComparator()
		{
			if (unicodeSortOrder)
				return BytesRef.getUTF8SortedAsUnicodeComparator();
			else
				return BytesRef.getUTF8SortedAsUTF16Comparator();
		}

		public TVTermsEnum()
			throws IOException
		{
			this$0 = Lucene3xTermVectorsReader.this;
			super();
			origTVF = Lucene3xTermVectorsReader.this.tvf;
			tvf = origTVF.clone();
		}
	}

	static class TermAndPostings
	{

		BytesRef term;
		int freq;
		int positions[];
		int startOffsets[];
		int endOffsets[];

		TermAndPostings()
		{
		}
	}

	private class TVTerms extends Terms
	{

		private final int numTerms;
		private final long tvfFPStart;
		private final boolean storePositions;
		private final boolean storeOffsets;
		private final boolean unicodeSortOrder;
		final Lucene3xTermVectorsReader this$0;

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
			termsEnum.reset(numTerms, tvfFPStart, storePositions, storeOffsets, unicodeSortOrder);
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
			if (unicodeSortOrder)
				return BytesRef.getUTF8SortedAsUnicodeComparator();
			else
				return BytesRef.getUTF8SortedAsUTF16Comparator();
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
			return false;
		}

		public TVTerms(long tvfFP)
			throws IOException
		{
			this$0 = Lucene3xTermVectorsReader.this;
			super();
			tvf.seek(tvfFP);
			numTerms = tvf.readVInt();
			byte bits = tvf.readByte();
			storePositions = (bits & 1) != 0;
			storeOffsets = (bits & 2) != 0;
			tvfFPStart = tvf.getFilePointer();
			unicodeSortOrder = sortTermsByUnicode();
		}
	}

	private class TVFields extends Fields
	{

		private final int fieldNumbers[];
		private final long fieldFPs[];
		private final Map fieldNumberToIndex = new HashMap();
		static final boolean $assertionsDisabled = !org/apache/lucene/codecs/lucene3x/Lucene3xTermVectorsReader.desiredAssertionStatus();
		final Lucene3xTermVectorsReader this$0;

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
			this$0 = Lucene3xTermVectorsReader.this;
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


	static final int FORMAT_UTF8_LENGTH_IN_BYTES = 4;
	public static final int FORMAT_CURRENT = 4;
	public static final int FORMAT_MINIMUM = 4;
	static final int FORMAT_SIZE = 4;
	public static final byte STORE_POSITIONS_WITH_TERMVECTOR = 1;
	public static final byte STORE_OFFSET_WITH_TERMVECTOR = 2;
	public static final String VECTORS_FIELDS_EXTENSION = "tvf";
	public static final String VECTORS_DOCUMENTS_EXTENSION = "tvd";
	public static final String VECTORS_INDEX_EXTENSION = "tvx";
	private FieldInfos fieldInfos;
	private IndexInput tvx;
	private IndexInput tvd;
	private IndexInput tvf;
	private int size;
	private int numTotalDocs;
	private int docStoreOffset;
	private final CompoundFileDirectory storeCFSReader;
	private final int format;
	static final boolean $assertionsDisabled = !org/apache/lucene/codecs/lucene3x/Lucene3xTermVectorsReader.desiredAssertionStatus();

	Lucene3xTermVectorsReader(FieldInfos fieldInfos, IndexInput tvx, IndexInput tvd, IndexInput tvf, int size, int numTotalDocs, int docStoreOffset, 
			int format)
	{
		this.fieldInfos = fieldInfos;
		this.tvx = tvx;
		this.tvd = tvd;
		this.tvf = tvf;
		this.size = size;
		this.numTotalDocs = numTotalDocs;
		this.docStoreOffset = docStoreOffset;
		this.format = format;
		storeCFSReader = null;
	}

	public Lucene3xTermVectorsReader(Directory d, SegmentInfo si, FieldInfos fieldInfos, IOContext context)
		throws CorruptIndexException, IOException
	{
		String segment;
		int docStoreOffset;
		int size;
		boolean success;
		segment = Lucene3xSegmentInfoFormat.getDocStoreSegment(si);
		docStoreOffset = Lucene3xSegmentInfoFormat.getDocStoreOffset(si);
		size = si.getDocCount();
		success = false;
		if (docStoreOffset != -1 && Lucene3xSegmentInfoFormat.getDocStoreIsCompoundFile(si))
			d = storeCFSReader = new CompoundFileDirectory(si.dir, IndexFileNames.segmentFileName(segment, "", "cfx"), context, false);
		else
			storeCFSReader = null;
		String idxName = IndexFileNames.segmentFileName(segment, "", "tvx");
		tvx = d.openInput(idxName, context);
		format = checkValidFormat(tvx);
		String fn = IndexFileNames.segmentFileName(segment, "", "tvd");
		tvd = d.openInput(fn, context);
		int tvdFormat = checkValidFormat(tvd);
		fn = IndexFileNames.segmentFileName(segment, "", "tvf");
		tvf = d.openInput(fn, context);
		int tvfFormat = checkValidFormat(tvf);
		if (!$assertionsDisabled && format != tvdFormat)
			throw new AssertionError();
		if (!$assertionsDisabled && format != tvfFormat)
			throw new AssertionError();
		numTotalDocs = (int)(tvx.length() >> 4);
		if (-1 == docStoreOffset)
		{
			this.docStoreOffset = 0;
			this.size = numTotalDocs;
			if (!$assertionsDisabled && size != 0 && numTotalDocs != size)
				throw new AssertionError();
		} else
		{
			this.docStoreOffset = docStoreOffset;
			this.size = size;
			if (!$assertionsDisabled && numTotalDocs < size + docStoreOffset)
				throw new AssertionError((new StringBuilder()).append("numTotalDocs=").append(numTotalDocs).append(" size=").append(size).append(" docStoreOffset=").append(docStoreOffset).toString());
		}
		this.fieldInfos = fieldInfos;
		success = true;
		if (!success)
			try
			{
				close();
			}
			catch (Throwable t) { }
		break MISSING_BLOCK_LABEL_407;
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

	void seekTvx(int docNum)
		throws IOException
	{
		tvx.seek((long)(docNum + docStoreOffset) * 16L + 4L);
	}

	private int checkValidFormat(IndexInput in)
		throws CorruptIndexException, IOException
	{
		int format = in.readInt();
		if (format < 4)
			throw new IndexFormatTooOldException(in, format, 4, 4);
		if (format > 4)
			throw new IndexFormatTooNewException(in, format, 4, 4);
		else
			return format;
	}

	public void close()
		throws IOException
	{
		IOUtils.close(new Closeable[] {
			tvx, tvd, tvf, storeCFSReader
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
		return new Lucene3xTermVectorsReader(fieldInfos, cloneTvx, cloneTvd, cloneTvf, size, numTotalDocs, docStoreOffset, format);
	}

	protected boolean sortTermsByUnicode()
	{
		return true;
	}

	public volatile Object clone()
		throws CloneNotSupportedException
	{
		return clone();
	}





}
