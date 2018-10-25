// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Lucene3xFields.java

package org.apache.lucene.codecs.lucene3x;

import java.io.Closeable;
import java.io.IOException;
import java.util.*;
import org.apache.lucene.codecs.FieldsProducer;
import org.apache.lucene.index.*;
import org.apache.lucene.store.*;
import org.apache.lucene.util.*;

// Referenced classes of package org.apache.lucene.codecs.lucene3x:
//			TermInfosReader, SegmentTermPositions, SegmentTermEnum, SegmentTermDocs

/**
 * @deprecated Class Lucene3xFields is deprecated
 */

class Lucene3xFields extends FieldsProducer
{
	private final class PreDocsAndPositionsEnum extends DocsAndPositionsEnum
	{

		private final SegmentTermPositions pos;
		private int docID;
		static final boolean $assertionsDisabled = !org/apache/lucene/codecs/lucene3x/Lucene3xFields.desiredAssertionStatus();
		final Lucene3xFields this$0;

		IndexInput getFreqStream()
		{
			return freqStream;
		}

		public DocsAndPositionsEnum reset(SegmentTermEnum termEnum, Bits liveDocs)
			throws IOException
		{
			pos.setLiveDocs(liveDocs);
			pos.seek(termEnum);
			docID = -1;
			return this;
		}

		public int nextDoc()
			throws IOException
		{
			if (pos.next())
				return docID = pos.doc();
			else
				return docID = 0x7fffffff;
		}

		public int advance(int target)
			throws IOException
		{
			if (pos.skipTo(target))
				return docID = pos.doc();
			else
				return docID = 0x7fffffff;
		}

		public int freq()
			throws IOException
		{
			return pos.freq();
		}

		public int docID()
		{
			return docID;
		}

		public int nextPosition()
			throws IOException
		{
			if (!$assertionsDisabled && docID == 0x7fffffff)
				throw new AssertionError();
			else
				return pos.nextPosition();
		}

		public int startOffset()
			throws IOException
		{
			return -1;
		}

		public int endOffset()
			throws IOException
		{
			return -1;
		}

		public BytesRef getPayload()
			throws IOException
		{
			return pos.getPayload();
		}


		PreDocsAndPositionsEnum()
			throws IOException
		{
			this$0 = Lucene3xFields.this;
			super();
			docID = -1;
			pos = new SegmentTermPositions(freqStream, proxStream, getTermsDict(), fieldInfos);
		}
	}

	private final class PreDocsEnum extends DocsEnum
	{

		private final SegmentTermDocs docs;
		private int docID;
		final Lucene3xFields this$0;

		IndexInput getFreqStream()
		{
			return freqStream;
		}

		public PreDocsEnum reset(SegmentTermEnum termEnum, Bits liveDocs)
			throws IOException
		{
			docs.setLiveDocs(liveDocs);
			docs.seek(termEnum);
			docs.freq = 1;
			docID = -1;
			return this;
		}

		public int nextDoc()
			throws IOException
		{
			if (docs.next())
				return docID = docs.doc();
			else
				return docID = 0x7fffffff;
		}

		public int advance(int target)
			throws IOException
		{
			if (docs.skipTo(target))
				return docID = docs.doc();
			else
				return docID = 0x7fffffff;
		}

		public int freq()
			throws IOException
		{
			return docs.freq();
		}

		public int docID()
		{
			return docID;
		}

		PreDocsEnum()
			throws IOException
		{
			this$0 = Lucene3xFields.this;
			super();
			docID = -1;
			docs = new SegmentTermDocs(freqStream, getTermsDict(), fieldInfos);
		}
	}

	private class PreTermsEnum extends TermsEnum
	{

		private SegmentTermEnum termEnum;
		private FieldInfo fieldInfo;
		private String internedFieldName;
		private boolean skipNext;
		private BytesRef current;
		private SegmentTermEnum seekTermEnum;
		private static final byte UTF8_NON_BMP_LEAD = -16;
		private static final byte UTF8_HIGH_BMP_LEAD = -18;
		private final byte scratch[];
		private final BytesRef prevTerm;
		private final BytesRef scratchTerm;
		private int newSuffixStart;
		private boolean unicodeSortOrder;
		static final boolean $assertionsDisabled = !org/apache/lucene/codecs/lucene3x/Lucene3xFields.desiredAssertionStatus();
		final Lucene3xFields this$0;

		private final boolean isHighBMPChar(byte b[], int idx)
		{
			return (b[idx] & 0xffffffee) == -18;
		}

		private final boolean isNonBMPChar(byte b[], int idx)
		{
			return (b[idx] & 0xfffffff0) == -16;
		}

		private boolean seekToNonBMP(SegmentTermEnum te, BytesRef term, int pos)
			throws IOException
		{
			int savLength = term.length;
			if (!$assertionsDisabled && term.offset != 0)
				throw new AssertionError();
			if (!$assertionsDisabled && !isHighBMPChar(term.bytes, pos))
				throw new AssertionError();
			if (term.bytes.length < 4 + pos)
				term.grow(4 + pos);
			scratch[0] = term.bytes[pos];
			scratch[1] = term.bytes[pos + 1];
			scratch[2] = term.bytes[pos + 2];
			term.bytes[pos] = -16;
			term.bytes[pos + 1] = -112;
			term.bytes[pos + 2] = -128;
			term.bytes[pos + 3] = -128;
			term.length = 4 + pos;
			getTermsDict().seekEnum(te, new Term(fieldInfo.name, term), true);
			Term t2 = te.term();
			if (t2 == null || t2.field() != internedFieldName)
				return false;
			BytesRef b2 = t2.bytes();
			if (!$assertionsDisabled && b2.offset != 0)
				throw new AssertionError();
			boolean matches;
			if (b2.length >= term.length && isNonBMPChar(b2.bytes, pos))
			{
				matches = true;
				int i = 0;
				do
				{
					if (i >= pos)
						break;
					if (term.bytes[i] != b2.bytes[i])
					{
						matches = false;
						break;
					}
					i++;
				} while (true);
			} else
			{
				matches = false;
			}
			term.length = savLength;
			term.bytes[pos] = scratch[0];
			term.bytes[pos + 1] = scratch[1];
			term.bytes[pos + 2] = scratch[2];
			return matches;
		}

		private boolean doContinue()
			throws IOException
		{
			int downTo = prevTerm.length - 1;
			boolean didSeek = false;
			for (int limit = Math.min(newSuffixStart, scratchTerm.length - 1); downTo > limit; downTo--)
			{
				if (isHighBMPChar(prevTerm.bytes, downTo) && seekToNonBMP(seekTermEnum, prevTerm, downTo))
				{
					getTermsDict().seekEnum(termEnum, seekTermEnum.term(), true);
					newSuffixStart = downTo;
					scratchTerm.copyBytes(termEnum.term().bytes());
					didSeek = true;
					break;
				}
				if ((prevTerm.bytes[downTo] & 0xc0) == 192 || (prevTerm.bytes[downTo] & 0x80) == 0)
					prevTerm.length = downTo;
			}

			return didSeek;
		}

		private boolean doPop()
			throws IOException
		{
			if (!$assertionsDisabled && newSuffixStart > prevTerm.length)
				throw new AssertionError();
			if (!$assertionsDisabled && newSuffixStart >= scratchTerm.length && newSuffixStart != 0)
				throw new AssertionError();
			if (prevTerm.length > newSuffixStart && isNonBMPChar(prevTerm.bytes, newSuffixStart) && isHighBMPChar(scratchTerm.bytes, newSuffixStart))
			{
				scratchTerm.bytes[newSuffixStart] = -1;
				scratchTerm.length = newSuffixStart + 1;
				getTermsDict().seekEnum(termEnum, new Term(fieldInfo.name, scratchTerm), true);
				Term t2 = termEnum.term();
				if (t2 != null && t2.field() == internedFieldName)
				{
					BytesRef b2 = t2.bytes();
					if (!$assertionsDisabled && b2.offset != 0)
					{
						throw new AssertionError();
					} else
					{
						scratchTerm.copyBytes(b2);
						setNewSuffixStart(prevTerm, scratchTerm);
						return true;
					}
				}
				if (newSuffixStart != 0 || scratchTerm.length != 0)
				{
					newSuffixStart = 0;
					scratchTerm.length = 0;
					return true;
				}
			}
			return false;
		}

		private void surrogateDance()
			throws IOException
		{
			if (!unicodeSortOrder)
				return;
			if (termEnum.term() == null || termEnum.term().field() != internedFieldName)
				scratchTerm.length = 0;
			else
				scratchTerm.copyBytes(termEnum.term().bytes());
			if (!$assertionsDisabled && prevTerm.offset != 0)
				throw new AssertionError();
			if (!$assertionsDisabled && scratchTerm.offset != 0)
				throw new AssertionError();
			while (!doContinue() && doPop()) ;
			doPushes();
		}

		private void doPushes()
			throws IOException
		{
			for (int upTo = newSuffixStart; upTo < scratchTerm.length;)
				if (isNonBMPChar(scratchTerm.bytes, upTo) && (upTo > newSuffixStart || upTo >= prevTerm.length || !isNonBMPChar(prevTerm.bytes, upTo) && !isHighBMPChar(prevTerm.bytes, upTo)))
				{
					if (!$assertionsDisabled && scratchTerm.length < upTo + 4)
						throw new AssertionError();
					int savLength = scratchTerm.length;
					scratch[0] = scratchTerm.bytes[upTo];
					scratch[1] = scratchTerm.bytes[upTo + 1];
					scratch[2] = scratchTerm.bytes[upTo + 2];
					scratchTerm.bytes[upTo] = -18;
					scratchTerm.bytes[upTo + 1] = -128;
					scratchTerm.bytes[upTo + 2] = -128;
					scratchTerm.length = upTo + 3;
					getTermsDict().seekEnum(seekTermEnum, new Term(fieldInfo.name, scratchTerm), true);
					scratchTerm.bytes[upTo] = scratch[0];
					scratchTerm.bytes[upTo + 1] = scratch[1];
					scratchTerm.bytes[upTo + 2] = scratch[2];
					scratchTerm.length = savLength;
					Term t2 = seekTermEnum.term();
					boolean matches;
					if (t2 != null && t2.field() == internedFieldName)
					{
						BytesRef b2 = t2.bytes();
						if (!$assertionsDisabled && b2.offset != 0)
							throw new AssertionError();
						if (b2.length >= upTo + 3 && isHighBMPChar(b2.bytes, upTo))
						{
							matches = true;
							int i = 0;
							do
							{
								if (i >= upTo)
									break;
								if (scratchTerm.bytes[i] != b2.bytes[i])
								{
									matches = false;
									break;
								}
								i++;
							} while (true);
						} else
						{
							matches = false;
						}
					} else
					{
						matches = false;
					}
					if (matches)
					{
						getTermsDict().seekEnum(termEnum, seekTermEnum.term(), true);
						scratchTerm.copyBytes(seekTermEnum.term().bytes());
						upTo += 3;
					} else
					{
						upTo++;
					}
				} else
				{
					upTo++;
				}

		}

		void reset(FieldInfo fieldInfo)
			throws IOException
		{
			this.fieldInfo = fieldInfo;
			internedFieldName = fieldInfo.name.intern();
			Term term = new Term(internedFieldName);
			if (termEnum == null)
			{
				termEnum = getTermsDict().terms(term);
				seekTermEnum = getTermsDict().terms(term);
			} else
			{
				getTermsDict().seekEnum(termEnum, term, true);
			}
			skipNext = true;
			unicodeSortOrder = sortTermsByUnicode();
			Term t = termEnum.term();
			if (t != null && t.field() == internedFieldName)
			{
				newSuffixStart = 0;
				prevTerm.length = 0;
				surrogateDance();
			}
		}

		public Comparator getComparator()
		{
			if (unicodeSortOrder)
				return BytesRef.getUTF8SortedAsUnicodeComparator();
			else
				return BytesRef.getUTF8SortedAsUTF16Comparator();
		}

		public void seekExact(long ord)
			throws IOException
		{
			throw new UnsupportedOperationException();
		}

		public long ord()
			throws IOException
		{
			throw new UnsupportedOperationException();
		}

		public org.apache.lucene.index.TermsEnum.SeekStatus seekCeil(BytesRef term, boolean useCache)
			throws IOException
		{
			skipNext = false;
			TermInfosReader tis = getTermsDict();
			Term t0 = new Term(fieldInfo.name, term);
			if (!$assertionsDisabled && termEnum == null)
				throw new AssertionError();
			tis.seekEnum(termEnum, t0, useCache);
			Term t = termEnum.term();
			if (t != null && t.field() == internedFieldName && term.bytesEquals(t.bytes()))
			{
				current = t.bytes();
				return org.apache.lucene.index.TermsEnum.SeekStatus.FOUND;
			}
			if (t == null || t.field() != internedFieldName)
			{
				scratchTerm.copyBytes(term);
				if (!$assertionsDisabled && scratchTerm.offset != 0)
					throw new AssertionError();
				for (int i = scratchTerm.length - 1; i >= 0; i--)
					if (isHighBMPChar(scratchTerm.bytes, i) && seekToNonBMP(seekTermEnum, scratchTerm, i))
					{
						scratchTerm.copyBytes(seekTermEnum.term().bytes());
						getTermsDict().seekEnum(termEnum, seekTermEnum.term(), useCache);
						newSuffixStart = 1 + i;
						doPushes();
						current = termEnum.term().bytes();
						return org.apache.lucene.index.TermsEnum.SeekStatus.NOT_FOUND;
					}

				current = null;
				return org.apache.lucene.index.TermsEnum.SeekStatus.END;
			}
			prevTerm.copyBytes(term);
			BytesRef br = t.bytes();
			if (!$assertionsDisabled && br.offset != 0)
				throw new AssertionError();
			setNewSuffixStart(term, br);
			surrogateDance();
			Term t2 = termEnum.term();
			if (t2 == null || t2.field() != internedFieldName)
				if (!$assertionsDisabled && t2 != null && t2.field().equals(internedFieldName))
				{
					throw new AssertionError();
				} else
				{
					current = null;
					return org.apache.lucene.index.TermsEnum.SeekStatus.END;
				}
			current = t2.bytes();
			if (!$assertionsDisabled && unicodeSortOrder && term.compareTo(current) >= 0)
				throw new AssertionError((new StringBuilder()).append("term=").append(UnicodeUtil.toHexString(term.utf8ToString())).append(" vs current=").append(UnicodeUtil.toHexString(current.utf8ToString())).toString());
			else
				return org.apache.lucene.index.TermsEnum.SeekStatus.NOT_FOUND;
		}

		private void setNewSuffixStart(BytesRef br1, BytesRef br2)
		{
			int limit = Math.min(br1.length, br2.length);
			int lastStart = 0;
			for (int i = 0; i < limit; i++)
			{
				if ((br1.bytes[br1.offset + i] & 0xc0) == 192 || (br1.bytes[br1.offset + i] & 0x80) == 0)
					lastStart = i;
				if (br1.bytes[br1.offset + i] != br2.bytes[br2.offset + i])
				{
					newSuffixStart = lastStart;
					return;
				}
			}

			newSuffixStart = limit;
		}

		public BytesRef next()
			throws IOException
		{
			if (skipNext)
			{
				skipNext = false;
				if (termEnum.term() == null)
					return null;
				if (termEnum.term().field() != internedFieldName)
					return null;
				else
					return current = termEnum.term().bytes();
			}
			prevTerm.copyBytes(termEnum.term().bytes());
			Term t;
			if (termEnum.next() && termEnum.term().field() == internedFieldName)
			{
				newSuffixStart = termEnum.newSuffixStart;
				surrogateDance();
				t = termEnum.term();
				if (t == null || t.field() != internedFieldName)
				{
					if (!$assertionsDisabled && t != null && t.field().equals(internedFieldName))
						throw new AssertionError();
					current = null;
				} else
				{
					current = t.bytes();
				}
				return current;
			}
			newSuffixStart = 0;
			surrogateDance();
			t = termEnum.term();
			if (t == null || t.field() != internedFieldName)
			{
				if (!$assertionsDisabled && t != null && t.field().equals(internedFieldName))
					throw new AssertionError();
				else
					return null;
			} else
			{
				current = t.bytes();
				return current;
			}
		}

		public BytesRef term()
		{
			return current;
		}

		public int docFreq()
		{
			return termEnum.docFreq();
		}

		public long totalTermFreq()
		{
			return -1L;
		}

		public DocsEnum docs(Bits liveDocs, DocsEnum reuse, int flags)
			throws IOException
		{
			PreDocsEnum docsEnum;
			if (reuse == null || !(reuse instanceof PreDocsEnum))
			{
				docsEnum = new PreDocsEnum();
			} else
			{
				docsEnum = (PreDocsEnum)reuse;
				if (docsEnum.getFreqStream() != freqStream)
					docsEnum = new PreDocsEnum();
			}
			return docsEnum.reset(termEnum, liveDocs);
		}

		public DocsAndPositionsEnum docsAndPositions(Bits liveDocs, DocsAndPositionsEnum reuse, int flags)
			throws IOException
		{
			if (fieldInfo.getIndexOptions() != org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS)
				return null;
			PreDocsAndPositionsEnum docsPosEnum;
			if (reuse == null || !(reuse instanceof PreDocsAndPositionsEnum))
			{
				docsPosEnum = new PreDocsAndPositionsEnum();
			} else
			{
				docsPosEnum = (PreDocsAndPositionsEnum)reuse;
				if (docsPosEnum.getFreqStream() != freqStream)
					docsPosEnum = new PreDocsAndPositionsEnum();
			}
			return docsPosEnum.reset(termEnum, liveDocs);
		}


		private PreTermsEnum()
		{
			this$0 = Lucene3xFields.this;
			super();
			scratch = new byte[4];
			prevTerm = new BytesRef();
			scratchTerm = new BytesRef();
		}

	}

	private class PreTerms extends Terms
	{

		final FieldInfo fieldInfo;
		static final boolean $assertionsDisabled = !org/apache/lucene/codecs/lucene3x/Lucene3xFields.desiredAssertionStatus();
		final Lucene3xFields this$0;

		public TermsEnum iterator(TermsEnum reuse)
			throws IOException
		{
			PreTermsEnum termsEnum = new PreTermsEnum();
			termsEnum.reset(fieldInfo);
			return termsEnum;
		}

		public Comparator getComparator()
		{
			if (sortTermsByUnicode())
				return BytesRef.getUTF8SortedAsUnicodeComparator();
			else
				return BytesRef.getUTF8SortedAsUTF16Comparator();
		}

		public long size()
			throws IOException
		{
			return -1L;
		}

		public long getSumTotalTermFreq()
		{
			return -1L;
		}

		public long getSumDocFreq()
			throws IOException
		{
			return -1L;
		}

		public int getDocCount()
			throws IOException
		{
			return -1;
		}

		public boolean hasOffsets()
		{
			if (!$assertionsDisabled && fieldInfo.getIndexOptions().compareTo(org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS) >= 0)
				throw new AssertionError();
			else
				return false;
		}

		public boolean hasPositions()
		{
			return fieldInfo.getIndexOptions().compareTo(org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS) >= 0;
		}

		public boolean hasPayloads()
		{
			return fieldInfo.hasPayloads();
		}


		PreTerms(FieldInfo fieldInfo)
		{
			this$0 = Lucene3xFields.this;
			super();
			this.fieldInfo = fieldInfo;
		}
	}


	private static final boolean DEBUG_SURROGATES = false;
	public TermInfosReader tis;
	public final TermInfosReader tisNoIndex;
	public final IndexInput freqStream;
	public final IndexInput proxStream;
	private final FieldInfos fieldInfos;
	private final SegmentInfo si;
	final TreeMap fields;
	final Map preTerms;
	private final Directory dir;
	private final IOContext context;
	private Directory cfsReader;
	static final boolean $assertionsDisabled = !org/apache/lucene/codecs/lucene3x/Lucene3xFields.desiredAssertionStatus();

	public Lucene3xFields(Directory dir, FieldInfos fieldInfos, SegmentInfo info, IOContext context, int indexDivisor)
		throws IOException
	{
		boolean success;
		fields = new TreeMap();
		preTerms = new HashMap();
		si = info;
		if (indexDivisor < 0)
			indexDivisor = -indexDivisor;
		success = false;
		TermInfosReader r = new TermInfosReader(dir, info.name, fieldInfos, context, indexDivisor);
		if (indexDivisor == -1)
		{
			tisNoIndex = r;
		} else
		{
			tisNoIndex = null;
			tis = r;
		}
		this.context = context;
		this.fieldInfos = fieldInfos;
		freqStream = dir.openInput(IndexFileNames.segmentFileName(info.name, "", "frq"), context);
		boolean anyProx = false;
		Iterator i$ = fieldInfos.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			FieldInfo fi = (FieldInfo)i$.next();
			if (fi.isIndexed())
			{
				fields.put(fi.name, fi);
				preTerms.put(fi.name, new PreTerms(fi));
				if (fi.getIndexOptions() == org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS)
					anyProx = true;
			}
		} while (true);
		if (anyProx)
			proxStream = dir.openInput(IndexFileNames.segmentFileName(info.name, "", "prx"), context);
		else
			proxStream = null;
		success = true;
		if (!success)
			close();
		break MISSING_BLOCK_LABEL_280;
		Exception exception;
		exception;
		if (!success)
			close();
		throw exception;
		this.dir = dir;
		return;
	}

	protected boolean sortTermsByUnicode()
	{
		return true;
	}

	public Iterator iterator()
	{
		return Collections.unmodifiableSet(fields.keySet()).iterator();
	}

	public Terms terms(String field)
	{
		return (Terms)preTerms.get(field);
	}

	public int size()
	{
		if (!$assertionsDisabled && preTerms.size() != fields.size())
			throw new AssertionError();
		else
			return fields.size();
	}

	public long getUniqueTermCount()
		throws IOException
	{
		return getTermsDict().size();
	}

	private synchronized TermInfosReader getTermsDict()
	{
		if (tis != null)
			return tis;
		else
			return tisNoIndex;
	}

	public void close()
		throws IOException
	{
		IOUtils.close(new Closeable[] {
			tis, tisNoIndex, cfsReader, freqStream, proxStream
		});
	}



}
