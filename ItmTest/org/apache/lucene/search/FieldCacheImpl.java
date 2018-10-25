// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FieldCacheImpl.java

package org.apache.lucene.search;

import java.io.IOException;
import java.io.PrintStream;
import java.util.*;
import org.apache.lucene.index.*;
import org.apache.lucene.util.*;
import org.apache.lucene.util.packed.GrowableWriter;
import org.apache.lucene.util.packed.PackedInts;

// Referenced classes of package org.apache.lucene.search:
//			FieldCache

class FieldCacheImpl
	implements FieldCache
{
	static final class DocTermOrdsCache extends Cache
	{

		protected Object createValue(AtomicReader reader, Entry entryKey, boolean setDocsWithField)
			throws IOException
		{
			return new DocTermOrds(reader, entryKey.field);
		}

		DocTermOrdsCache(FieldCacheImpl wrapper)
		{
			super(wrapper);
		}
	}

	static final class DocTermsCache extends Cache
	{

		protected Object createValue(AtomicReader reader, Entry entryKey, boolean setDocsWithField)
			throws IOException
		{
			PagedBytes bytes;
			GrowableWriter docToOffset;
label0:
			{
				Terms terms = reader.terms(entryKey.field);
				float acceptableOverheadRatio = ((Float)entryKey.custom).floatValue();
				int termCountHardLimit = reader.maxDoc();
				bytes = new PagedBytes(15);
				int startBPV;
				if (terms != null)
				{
					long numUniqueTerms = terms.size();
					if (numUniqueTerms != -1L)
					{
						if (numUniqueTerms > (long)termCountHardLimit)
							numUniqueTerms = termCountHardLimit;
						startBPV = PackedInts.bitsRequired(numUniqueTerms * 4L);
					} else
					{
						startBPV = 1;
					}
				} else
				{
					startBPV = 1;
				}
				docToOffset = new GrowableWriter(startBPV, reader.maxDoc(), acceptableOverheadRatio);
				bytes.copyUsingLengthPrefix(new BytesRef());
				if (terms == null)
					break label0;
				int termCount = 0;
				TermsEnum termsEnum = terms.iterator(null);
				DocsEnum docs = null;
				do
				{
					if (termCount++ == termCountHardLimit)
						break label0;
					BytesRef term = termsEnum.next();
					if (term == null)
						break label0;
					long pointer = bytes.copyUsingLengthPrefix(term);
					docs = termsEnum.docs(null, docs, 0);
					do
					{
						int docID = docs.nextDoc();
						if (docID == 0x7fffffff)
							break;
						docToOffset.set(docID, pointer);
					} while (true);
				} while (true);
			}
			return new DocTermsImpl(bytes.freeze(true), docToOffset.getMutable());
		}

		DocTermsCache(FieldCacheImpl wrapper)
		{
			super(wrapper);
		}
	}

	private static class DocTermsImpl extends FieldCache.DocTerms
	{

		private final org.apache.lucene.util.PagedBytes.Reader bytes;
		private final org.apache.lucene.util.packed.PackedInts.Reader docToOffset;

		public int size()
		{
			return docToOffset.size();
		}

		public boolean exists(int docID)
		{
			return docToOffset.get(docID) == 0L;
		}

		public BytesRef getTerm(int docID, BytesRef ret)
		{
			int pointer = (int)docToOffset.get(docID);
			return bytes.fill(ret, pointer);
		}

		public DocTermsImpl(org.apache.lucene.util.PagedBytes.Reader bytes, org.apache.lucene.util.packed.PackedInts.Reader docToOffset)
		{
			this.bytes = bytes;
			this.docToOffset = docToOffset;
		}
	}

	static class DocTermsIndexCache extends Cache
	{

		protected Object createValue(AtomicReader reader, Entry entryKey, boolean setDocsWithField)
			throws IOException
		{
			Terms terms = reader.terms(entryKey.field);
			float acceptableOverheadRatio = ((Float)entryKey.custom).floatValue();
			PagedBytes bytes = new PagedBytes(15);
			int maxDoc = reader.maxDoc();
			int termCountHardLimit;
			if (maxDoc == 0x7fffffff)
				termCountHardLimit = 0x7fffffff;
			else
				termCountHardLimit = maxDoc + 1;
			int startBytesBPV;
			int startTermsBPV;
			int startNumUniqueTerms;
			if (terms != null)
			{
				long numUniqueTerms = terms.size();
				if (numUniqueTerms != -1L)
				{
					if (numUniqueTerms > (long)termCountHardLimit)
						numUniqueTerms = termCountHardLimit;
					startBytesBPV = PackedInts.bitsRequired(numUniqueTerms * 4L);
					startTermsBPV = PackedInts.bitsRequired(numUniqueTerms);
					startNumUniqueTerms = (int)numUniqueTerms;
				} else
				{
					startBytesBPV = 1;
					startTermsBPV = 1;
					startNumUniqueTerms = 1;
				}
			} else
			{
				startBytesBPV = 1;
				startTermsBPV = 1;
				startNumUniqueTerms = 1;
			}
			GrowableWriter termOrdToBytesOffset = new GrowableWriter(startBytesBPV, 1 + startNumUniqueTerms, acceptableOverheadRatio);
			GrowableWriter docToTermOrd = new GrowableWriter(startTermsBPV, maxDoc, acceptableOverheadRatio);
			bytes.copyUsingLengthPrefix(new BytesRef());
			int termOrd = 1;
			if (terms != null)
			{
				TermsEnum termsEnum = terms.iterator(null);
				DocsEnum docs = null;
				do
				{
					BytesRef term = termsEnum.next();
					if (term == null || termOrd >= termCountHardLimit)
						break;
					if (termOrd == termOrdToBytesOffset.size())
						termOrdToBytesOffset = termOrdToBytesOffset.resize(ArrayUtil.oversize(1 + termOrd, 1));
					termOrdToBytesOffset.set(termOrd, bytes.copyUsingLengthPrefix(term));
					docs = termsEnum.docs(null, docs, 0);
					do
					{
						int docID = docs.nextDoc();
						if (docID == 0x7fffffff)
							break;
						docToTermOrd.set(docID, termOrd);
					} while (true);
					termOrd++;
				} while (true);
				if (termOrdToBytesOffset.size() > termOrd)
					termOrdToBytesOffset = termOrdToBytesOffset.resize(termOrd);
			}
			return new DocTermsIndexImpl(bytes.freeze(true), termOrdToBytesOffset.getMutable(), docToTermOrd.getMutable(), termOrd);
		}

		DocTermsIndexCache(FieldCacheImpl wrapper)
		{
			super(wrapper);
		}
	}

	public static class DocTermsIndexImpl extends FieldCache.DocTermsIndex
	{
		class DocTermsIndexEnum extends TermsEnum
		{

			int currentOrd;
			int currentBlockNumber;
			int end;
			final byte blocks[][];
			final int blockEnds[];
			final BytesRef term = new BytesRef();
			static final boolean $assertionsDisabled = !org/apache/lucene/search/FieldCacheImpl.desiredAssertionStatus();
			final DocTermsIndexImpl this$0;

			public org.apache.lucene.index.TermsEnum.SeekStatus seekCeil(BytesRef text, boolean useCache)
				throws IOException
			{
				int low = 1;
				for (int high = numOrd - 1; low <= high;)
				{
					int mid = low + high >>> 1;
					seekExact(mid);
					int cmp = term.compareTo(text);
					if (cmp < 0)
						low = mid + 1;
					else
					if (cmp > 0)
						high = mid - 1;
					else
						return org.apache.lucene.index.TermsEnum.SeekStatus.FOUND;
				}

				if (low == numOrd)
				{
					return org.apache.lucene.index.TermsEnum.SeekStatus.END;
				} else
				{
					seekExact(low);
					return org.apache.lucene.index.TermsEnum.SeekStatus.NOT_FOUND;
				}
			}

			public void seekExact(long ord)
				throws IOException
			{
				if (!$assertionsDisabled && (ord < 0L || ord > (long)numOrd))
				{
					throw new AssertionError();
				} else
				{
					currentBlockNumber = bytes.fillAndGetIndex(term, termOrdToBytesOffset.get((int)ord));
					end = blockEnds[currentBlockNumber];
					currentOrd = (int)ord;
					return;
				}
			}

			public BytesRef next()
				throws IOException
			{
				int start = term.offset + term.length;
				if (start >= end)
				{
					if (currentBlockNumber + 1 >= blocks.length)
						return null;
					currentBlockNumber++;
					term.bytes = blocks[currentBlockNumber];
					end = blockEnds[currentBlockNumber];
					start = 0;
					if (end <= 0)
						return null;
				}
				currentOrd++;
				byte block[] = term.bytes;
				if ((block[start] & 0x80) == 0)
				{
					term.length = block[start];
					term.offset = start + 1;
				} else
				{
					term.length = (block[start] & 0x7f) << 8 | block[1 + start] & 0xff;
					term.offset = start + 2;
				}
				return term;
			}

			public BytesRef term()
				throws IOException
			{
				return term;
			}

			public long ord()
				throws IOException
			{
				return (long)currentOrd;
			}

			public int docFreq()
			{
				throw new UnsupportedOperationException();
			}

			public long totalTermFreq()
			{
				return -1L;
			}

			public DocsEnum docs(Bits liveDocs, DocsEnum reuse, int flags)
				throws IOException
			{
				throw new UnsupportedOperationException();
			}

			public DocsAndPositionsEnum docsAndPositions(Bits liveDocs, DocsAndPositionsEnum reuse, int flags)
				throws IOException
			{
				throw new UnsupportedOperationException();
			}

			public Comparator getComparator()
			{
				return BytesRef.getUTF8SortedAsUnicodeComparator();
			}

			public void seekExact(BytesRef term, TermState state)
				throws IOException
			{
				if (!$assertionsDisabled && (state == null || !(state instanceof OrdTermState)))
				{
					throw new AssertionError();
				} else
				{
					seekExact(((OrdTermState)state).ord);
					return;
				}
			}

			public TermState termState()
				throws IOException
			{
				OrdTermState state = new OrdTermState();
				state.ord = currentOrd;
				return state;
			}


			public DocTermsIndexEnum()
			{
				this$0 = DocTermsIndexImpl.this;
				super();
				currentOrd = 0;
				currentBlockNumber = 0;
				blocks = bytes.getBlocks();
				blockEnds = bytes.getBlockEnds();
				currentBlockNumber = bytes.fillAndGetIndex(term, termOrdToBytesOffset.get(0));
				end = blockEnds[currentBlockNumber];
			}
		}


		private final org.apache.lucene.util.PagedBytes.Reader bytes;
		private final org.apache.lucene.util.packed.PackedInts.Reader termOrdToBytesOffset;
		private final org.apache.lucene.util.packed.PackedInts.Reader docToTermOrd;
		private final int numOrd;

		public org.apache.lucene.util.packed.PackedInts.Reader getDocToOrd()
		{
			return docToTermOrd;
		}

		public int numOrd()
		{
			return numOrd;
		}

		public int getOrd(int docID)
		{
			return (int)docToTermOrd.get(docID);
		}

		public int size()
		{
			return docToTermOrd.size();
		}

		public BytesRef lookup(int ord, BytesRef ret)
		{
			return bytes.fill(ret, termOrdToBytesOffset.get(ord));
		}

		public TermsEnum getTermsEnum()
		{
			return new DocTermsIndexEnum();
		}




		public DocTermsIndexImpl(org.apache.lucene.util.PagedBytes.Reader bytes, org.apache.lucene.util.packed.PackedInts.Reader termOrdToBytesOffset, org.apache.lucene.util.packed.PackedInts.Reader docToTermOrd, int numOrd)
		{
			this.bytes = bytes;
			this.docToTermOrd = docToTermOrd;
			this.termOrdToBytesOffset = termOrdToBytesOffset;
			this.numOrd = numOrd;
		}
	}

	static final class DoubleCache extends Cache
	{

		static final boolean $assertionsDisabled = !org/apache/lucene/search/FieldCacheImpl.desiredAssertionStatus();

		protected Object createValue(AtomicReader reader, Entry entryKey, boolean setDocsWithField)
			throws IOException
		{
			String field;
			FieldCache.DoubleParser parser;
			field = entryKey.field;
			parser = (FieldCache.DoubleParser)entryKey.custom;
			if (parser != null)
				break MISSING_BLOCK_LABEL_52;
			return wrapper.getDoubles(reader, field, FieldCache.DEFAULT_DOUBLE_PARSER, setDocsWithField);
			NumberFormatException ne;
			ne;
			return wrapper.getDoubles(reader, field, FieldCache.NUMERIC_UTILS_DOUBLE_PARSER, setDocsWithField);
			int maxDoc;
			double retArray[];
			FixedBitSet docsWithField;
label0:
			{
				maxDoc = reader.maxDoc();
				retArray = null;
				Terms terms = reader.terms(field);
				docsWithField = null;
				if (terms == null)
					break label0;
				if (setDocsWithField)
				{
					int termsDocCount = terms.getDocCount();
					if (!$assertionsDisabled && termsDocCount > maxDoc)
						throw new AssertionError();
					if (termsDocCount == maxDoc)
					{
						wrapper.setDocsWithField(reader, field, new org.apache.lucene.util.Bits.MatchAllBits(maxDoc));
						setDocsWithField = false;
					}
				}
				TermsEnum termsEnum = terms.iterator(null);
				DocsEnum docs = null;
				try
				{
					do
					{
						BytesRef term = termsEnum.next();
						if (term == null)
							break label0;
						double termval = parser.parseDouble(term);
						if (retArray == null)
							retArray = new double[maxDoc];
						docs = termsEnum.docs(null, docs, 0);
						do
						{
							int docID = docs.nextDoc();
							if (docID == 0x7fffffff)
								break;
							retArray[docID] = termval;
							if (setDocsWithField)
							{
								if (docsWithField == null)
									docsWithField = new FixedBitSet(maxDoc);
								docsWithField.set(docID);
							}
						} while (true);
					} while (true);
				}
				catch (FieldCache.StopFillCacheException stop) { }
			}
			if (retArray == null)
				retArray = new double[maxDoc];
			if (setDocsWithField)
				wrapper.setDocsWithField(reader, field, docsWithField);
			return retArray;
		}


		DoubleCache(FieldCacheImpl wrapper)
		{
			super(wrapper);
		}
	}

	static final class LongCache extends Cache
	{

		static final boolean $assertionsDisabled = !org/apache/lucene/search/FieldCacheImpl.desiredAssertionStatus();

		protected Object createValue(AtomicReader reader, Entry entryKey, boolean setDocsWithField)
			throws IOException
		{
			String field;
			FieldCache.LongParser parser;
			field = entryKey.field;
			parser = (FieldCache.LongParser)entryKey.custom;
			if (parser != null)
				break MISSING_BLOCK_LABEL_52;
			return wrapper.getLongs(reader, field, FieldCache.DEFAULT_LONG_PARSER, setDocsWithField);
			NumberFormatException ne;
			ne;
			return wrapper.getLongs(reader, field, FieldCache.NUMERIC_UTILS_LONG_PARSER, setDocsWithField);
			int maxDoc;
			long retArray[];
			FixedBitSet docsWithField;
label0:
			{
				maxDoc = reader.maxDoc();
				retArray = null;
				Terms terms = reader.terms(field);
				docsWithField = null;
				if (terms == null)
					break label0;
				if (setDocsWithField)
				{
					int termsDocCount = terms.getDocCount();
					if (!$assertionsDisabled && termsDocCount > maxDoc)
						throw new AssertionError();
					if (termsDocCount == maxDoc)
					{
						wrapper.setDocsWithField(reader, field, new org.apache.lucene.util.Bits.MatchAllBits(maxDoc));
						setDocsWithField = false;
					}
				}
				TermsEnum termsEnum = terms.iterator(null);
				DocsEnum docs = null;
				try
				{
					do
					{
						BytesRef term = termsEnum.next();
						if (term == null)
							break label0;
						long termval = parser.parseLong(term);
						if (retArray == null)
							retArray = new long[maxDoc];
						docs = termsEnum.docs(null, docs, 0);
						do
						{
							int docID = docs.nextDoc();
							if (docID == 0x7fffffff)
								break;
							retArray[docID] = termval;
							if (setDocsWithField)
							{
								if (docsWithField == null)
									docsWithField = new FixedBitSet(maxDoc);
								docsWithField.set(docID);
							}
						} while (true);
					} while (true);
				}
				catch (FieldCache.StopFillCacheException stop) { }
			}
			if (retArray == null)
				retArray = new long[maxDoc];
			if (setDocsWithField)
				wrapper.setDocsWithField(reader, field, docsWithField);
			return retArray;
		}


		LongCache(FieldCacheImpl wrapper)
		{
			super(wrapper);
		}
	}

	static final class FloatCache extends Cache
	{

		static final boolean $assertionsDisabled = !org/apache/lucene/search/FieldCacheImpl.desiredAssertionStatus();

		protected Object createValue(AtomicReader reader, Entry entryKey, boolean setDocsWithField)
			throws IOException
		{
			String field;
			FieldCache.FloatParser parser;
			field = entryKey.field;
			parser = (FieldCache.FloatParser)entryKey.custom;
			if (parser != null)
				break MISSING_BLOCK_LABEL_52;
			return wrapper.getFloats(reader, field, FieldCache.DEFAULT_FLOAT_PARSER, setDocsWithField);
			NumberFormatException ne;
			ne;
			return wrapper.getFloats(reader, field, FieldCache.NUMERIC_UTILS_FLOAT_PARSER, setDocsWithField);
			int maxDoc;
			float retArray[];
			FixedBitSet docsWithField;
label0:
			{
				maxDoc = reader.maxDoc();
				retArray = null;
				Terms terms = reader.terms(field);
				docsWithField = null;
				if (terms == null)
					break label0;
				if (setDocsWithField)
				{
					int termsDocCount = terms.getDocCount();
					if (!$assertionsDisabled && termsDocCount > maxDoc)
						throw new AssertionError();
					if (termsDocCount == maxDoc)
					{
						wrapper.setDocsWithField(reader, field, new org.apache.lucene.util.Bits.MatchAllBits(maxDoc));
						setDocsWithField = false;
					}
				}
				TermsEnum termsEnum = terms.iterator(null);
				DocsEnum docs = null;
				try
				{
					do
					{
						BytesRef term = termsEnum.next();
						if (term == null)
							break label0;
						float termval = parser.parseFloat(term);
						if (retArray == null)
							retArray = new float[maxDoc];
						docs = termsEnum.docs(null, docs, 0);
						do
						{
							int docID = docs.nextDoc();
							if (docID == 0x7fffffff)
								break;
							retArray[docID] = termval;
							if (setDocsWithField)
							{
								if (docsWithField == null)
									docsWithField = new FixedBitSet(maxDoc);
								docsWithField.set(docID);
							}
						} while (true);
					} while (true);
				}
				catch (FieldCache.StopFillCacheException stop) { }
			}
			if (retArray == null)
				retArray = new float[maxDoc];
			if (setDocsWithField)
				wrapper.setDocsWithField(reader, field, docsWithField);
			return retArray;
		}


		FloatCache(FieldCacheImpl wrapper)
		{
			super(wrapper);
		}
	}

	static final class DocsWithFieldCache extends Cache
	{

		static final boolean $assertionsDisabled = !org/apache/lucene/search/FieldCacheImpl.desiredAssertionStatus();

		protected Object createValue(AtomicReader reader, Entry entryKey, boolean setDocsWithField)
			throws IOException
		{
			FixedBitSet res;
			int maxDoc;
label0:
			{
				String field = entryKey.field;
				res = null;
				Terms terms = reader.terms(field);
				maxDoc = reader.maxDoc();
				if (terms == null)
					break label0;
				int termsDocCount = terms.getDocCount();
				if (!$assertionsDisabled && termsDocCount > maxDoc)
					throw new AssertionError();
				if (termsDocCount == maxDoc)
					return new org.apache.lucene.util.Bits.MatchAllBits(maxDoc);
				TermsEnum termsEnum = terms.iterator(null);
				DocsEnum docs = null;
				do
				{
					BytesRef term = termsEnum.next();
					if (term == null)
						break label0;
					if (res == null)
						res = new FixedBitSet(maxDoc);
					docs = termsEnum.docs(null, docs, 0);
					do
					{
						int docID = docs.nextDoc();
						if (docID == 0x7fffffff)
							break;
						res.set(docID);
					} while (true);
				} while (true);
			}
			if (res == null)
				return new org.apache.lucene.util.Bits.MatchNoBits(maxDoc);
			int numSet = res.cardinality();
			if (numSet >= maxDoc)
			{
				if (!$assertionsDisabled && numSet != maxDoc)
					throw new AssertionError();
				else
					return new org.apache.lucene.util.Bits.MatchAllBits(maxDoc);
			} else
			{
				return res;
			}
		}


		DocsWithFieldCache(FieldCacheImpl wrapper)
		{
			super(wrapper);
		}
	}

	static final class IntCache extends Cache
	{

		static final boolean $assertionsDisabled = !org/apache/lucene/search/FieldCacheImpl.desiredAssertionStatus();

		protected Object createValue(AtomicReader reader, Entry entryKey, boolean setDocsWithField)
			throws IOException
		{
			String field;
			FieldCache.IntParser parser;
			field = entryKey.field;
			parser = (FieldCache.IntParser)entryKey.custom;
			if (parser != null)
				break MISSING_BLOCK_LABEL_52;
			return wrapper.getInts(reader, field, FieldCache.DEFAULT_INT_PARSER, setDocsWithField);
			NumberFormatException ne;
			ne;
			return wrapper.getInts(reader, field, FieldCache.NUMERIC_UTILS_INT_PARSER, setDocsWithField);
			int maxDoc;
			int retArray[];
			FixedBitSet docsWithField;
label0:
			{
				maxDoc = reader.maxDoc();
				retArray = null;
				Terms terms = reader.terms(field);
				docsWithField = null;
				if (terms == null)
					break label0;
				if (setDocsWithField)
				{
					int termsDocCount = terms.getDocCount();
					if (!$assertionsDisabled && termsDocCount > maxDoc)
						throw new AssertionError();
					if (termsDocCount == maxDoc)
					{
						wrapper.setDocsWithField(reader, field, new org.apache.lucene.util.Bits.MatchAllBits(maxDoc));
						setDocsWithField = false;
					}
				}
				TermsEnum termsEnum = terms.iterator(null);
				DocsEnum docs = null;
				try
				{
					do
					{
						BytesRef term = termsEnum.next();
						if (term == null)
							break label0;
						int termval = parser.parseInt(term);
						if (retArray == null)
							retArray = new int[maxDoc];
						docs = termsEnum.docs(null, docs, 0);
						do
						{
							int docID = docs.nextDoc();
							if (docID == 0x7fffffff)
								break;
							retArray[docID] = termval;
							if (setDocsWithField)
							{
								if (docsWithField == null)
									docsWithField = new FixedBitSet(maxDoc);
								docsWithField.set(docID);
							}
						} while (true);
					} while (true);
				}
				catch (FieldCache.StopFillCacheException stop) { }
			}
			if (retArray == null)
				retArray = new int[maxDoc];
			if (setDocsWithField)
				wrapper.setDocsWithField(reader, field, docsWithField);
			return retArray;
		}


		IntCache(FieldCacheImpl wrapper)
		{
			super(wrapper);
		}
	}

	static final class ShortCache extends Cache
	{

		static final boolean $assertionsDisabled = !org/apache/lucene/search/FieldCacheImpl.desiredAssertionStatus();

		protected Object createValue(AtomicReader reader, Entry entryKey, boolean setDocsWithField)
			throws IOException
		{
			String field;
			short retArray[];
			FixedBitSet docsWithField;
label0:
			{
				field = entryKey.field;
				FieldCache.ShortParser parser = (FieldCache.ShortParser)entryKey.custom;
				if (parser == null)
					return wrapper.getShorts(reader, field, FieldCache.DEFAULT_SHORT_PARSER, setDocsWithField);
				int maxDoc = reader.maxDoc();
				retArray = new short[maxDoc];
				Terms terms = reader.terms(field);
				docsWithField = null;
				if (terms == null)
					break label0;
				if (setDocsWithField)
				{
					int termsDocCount = terms.getDocCount();
					if (!$assertionsDisabled && termsDocCount > maxDoc)
						throw new AssertionError();
					if (termsDocCount == maxDoc)
					{
						wrapper.setDocsWithField(reader, field, new org.apache.lucene.util.Bits.MatchAllBits(maxDoc));
						setDocsWithField = false;
					}
				}
				TermsEnum termsEnum = terms.iterator(null);
				DocsEnum docs = null;
				try
				{
					do
					{
						BytesRef term = termsEnum.next();
						if (term == null)
							break label0;
						short termval = parser.parseShort(term);
						docs = termsEnum.docs(null, docs, 0);
						do
						{
							int docID = docs.nextDoc();
							if (docID == 0x7fffffff)
								break;
							retArray[docID] = termval;
							if (setDocsWithField)
							{
								if (docsWithField == null)
									docsWithField = new FixedBitSet(maxDoc);
								docsWithField.set(docID);
							}
						} while (true);
					} while (true);
				}
				catch (FieldCache.StopFillCacheException stop) { }
			}
			if (setDocsWithField)
				wrapper.setDocsWithField(reader, field, docsWithField);
			return retArray;
		}


		ShortCache(FieldCacheImpl wrapper)
		{
			super(wrapper);
		}
	}

	static final class ByteCache extends Cache
	{

		static final boolean $assertionsDisabled = !org/apache/lucene/search/FieldCacheImpl.desiredAssertionStatus();

		protected Object createValue(AtomicReader reader, Entry entryKey, boolean setDocsWithField)
			throws IOException
		{
			String field;
			byte retArray[];
			FixedBitSet docsWithField;
label0:
			{
				field = entryKey.field;
				FieldCache.ByteParser parser = (FieldCache.ByteParser)entryKey.custom;
				if (parser == null)
					return wrapper.getBytes(reader, field, FieldCache.DEFAULT_BYTE_PARSER, setDocsWithField);
				int maxDoc = reader.maxDoc();
				retArray = new byte[maxDoc];
				Terms terms = reader.terms(field);
				docsWithField = null;
				if (terms == null)
					break label0;
				if (setDocsWithField)
				{
					int termsDocCount = terms.getDocCount();
					if (!$assertionsDisabled && termsDocCount > maxDoc)
						throw new AssertionError();
					if (termsDocCount == maxDoc)
					{
						wrapper.setDocsWithField(reader, field, new org.apache.lucene.util.Bits.MatchAllBits(maxDoc));
						setDocsWithField = false;
					}
				}
				TermsEnum termsEnum = terms.iterator(null);
				DocsEnum docs = null;
				try
				{
					do
					{
						BytesRef term = termsEnum.next();
						if (term == null)
							break label0;
						byte termval = parser.parseByte(term);
						docs = termsEnum.docs(null, docs, 0);
						do
						{
							int docID = docs.nextDoc();
							if (docID == 0x7fffffff)
								break;
							retArray[docID] = termval;
							if (setDocsWithField)
							{
								if (docsWithField == null)
									docsWithField = new FixedBitSet(maxDoc);
								docsWithField.set(docID);
							}
						} while (true);
					} while (true);
				}
				catch (FieldCache.StopFillCacheException stop) { }
			}
			if (setDocsWithField)
				wrapper.setDocsWithField(reader, field, docsWithField);
			return retArray;
		}


		ByteCache(FieldCacheImpl wrapper)
		{
			super(wrapper);
		}
	}

	static class Entry
	{

		final String field;
		final Object custom;

		public boolean equals(Object o)
		{
			if (o instanceof Entry)
			{
				Entry other = (Entry)o;
				if (other.field.equals(field))
					if (other.custom == null)
					{
						if (custom == null)
							return true;
					} else
					if (other.custom.equals(custom))
						return true;
			}
			return false;
		}

		public int hashCode()
		{
			return field.hashCode() ^ (custom != null ? custom.hashCode() : 0);
		}

		Entry(String field, Object custom)
		{
			this.field = field;
			this.custom = custom;
		}
	}

	static abstract class Cache
	{

		final FieldCacheImpl wrapper;
		final Map readerCache = new WeakHashMap();

		protected abstract Object createValue(AtomicReader atomicreader, Entry entry, boolean flag)
			throws IOException;

		public void purge(AtomicReader r)
		{
			Object readerKey = r.getCoreCacheKey();
			synchronized (readerCache)
			{
				readerCache.remove(readerKey);
			}
		}

		public void put(AtomicReader reader, Entry key, Object value)
		{
			Object readerKey = reader.getCoreCacheKey();
			synchronized (readerCache)
			{
				Map innerCache = (Map)readerCache.get(readerKey);
				if (innerCache == null)
				{
					innerCache = new HashMap();
					readerCache.put(readerKey, innerCache);
					wrapper.initReader(reader);
				}
				if (innerCache.get(key) == null)
					innerCache.put(key, value);
			}
		}

		public Object get(AtomicReader reader, Entry key, boolean setDocsWithField)
			throws IOException
		{
			Map innerCache;
			Object value;
			Object readerKey = reader.getCoreCacheKey();
			synchronized (readerCache)
			{
				innerCache = (Map)readerCache.get(readerKey);
				if (innerCache == null)
				{
					innerCache = new HashMap();
					readerCache.put(readerKey, innerCache);
					wrapper.initReader(reader);
					value = null;
				} else
				{
					value = innerCache.get(key);
				}
				if (value == null)
				{
					value = new FieldCache.CreationPlaceholder();
					innerCache.put(key, value);
				}
			}
			if (!(value instanceof FieldCache.CreationPlaceholder))
				break MISSING_BLOCK_LABEL_254;
			Object obj = value;
			JVM INSTR monitorenter ;
			FieldCache.CreationPlaceholder progress;
			progress = (FieldCache.CreationPlaceholder)value;
			if (progress.value == null)
			{
				progress.value = createValue(reader, key, setDocsWithField);
				synchronized (readerCache)
				{
					innerCache.put(key, progress.value);
				}
				if (key.custom != null && wrapper != null)
				{
					PrintStream infoStream = wrapper.getInfoStream();
					if (infoStream != null)
						printNewInsanity(infoStream, progress.value);
				}
			}
			return progress.value;
			Exception exception2;
			exception2;
			throw exception2;
			return value;
		}

		private void printNewInsanity(PrintStream infoStream, Object value)
		{
			org.apache.lucene.util.FieldCacheSanityChecker.Insanity insanities[] = FieldCacheSanityChecker.checkSanity(wrapper);
label0:
			for (int i = 0; i < insanities.length; i++)
			{
				org.apache.lucene.util.FieldCacheSanityChecker.Insanity insanity = insanities[i];
				FieldCache.CacheEntry entries[] = insanity.getCacheEntries();
				int j = 0;
				do
				{
					if (j >= entries.length)
						continue label0;
					if (entries[j].getValue() == value)
					{
						infoStream.println((new StringBuilder()).append("WARNING: new FieldCache insanity created\nDetails: ").append(insanity.toString()).toString());
						infoStream.println("\nStack:\n");
						(new Throwable()).printStackTrace(infoStream);
						continue label0;
					}
					j++;
				} while (true);
			}

		}

		Cache(FieldCacheImpl wrapper)
		{
			this.wrapper = wrapper;
		}
	}

	static final class StopFillCacheException extends RuntimeException
	{

		StopFillCacheException()
		{
		}
	}

	private static final class CacheEntryImpl extends FieldCache.CacheEntry
	{

		private final Object readerKey;
		private final String fieldName;
		private final Class cacheType;
		private final Object custom;
		private final Object value;

		public Object getReaderKey()
		{
			return readerKey;
		}

		public String getFieldName()
		{
			return fieldName;
		}

		public Class getCacheType()
		{
			return cacheType;
		}

		public Object getCustom()
		{
			return custom;
		}

		public Object getValue()
		{
			return value;
		}

		CacheEntryImpl(Object readerKey, String fieldName, Class cacheType, Object custom, Object value)
		{
			this.readerKey = readerKey;
			this.fieldName = fieldName;
			this.cacheType = cacheType;
			this.custom = custom;
			this.value = value;
		}
	}


	private Map caches;
	final org.apache.lucene.index.SegmentReader.CoreClosedListener purgeCore = new org.apache.lucene.index.SegmentReader.CoreClosedListener() {

		final FieldCacheImpl this$0;

		public void onClose(SegmentReader owner)
		{
			purge(owner);
		}

			
			{
				this$0 = FieldCacheImpl.this;
				super();
			}
	};
	final org.apache.lucene.index.IndexReader.ReaderClosedListener purgeReader = new org.apache.lucene.index.IndexReader.ReaderClosedListener() {

		static final boolean $assertionsDisabled = !org/apache/lucene/search/FieldCacheImpl.desiredAssertionStatus();
		final FieldCacheImpl this$0;

		public void onClose(IndexReader owner)
		{
			if (!$assertionsDisabled && !(owner instanceof AtomicReader))
			{
				throw new AssertionError();
			} else
			{
				purge((AtomicReader)owner);
				return;
			}
		}


			
			{
				this$0 = FieldCacheImpl.this;
				super();
			}
	};
	private volatile PrintStream infoStream;
	static final boolean $assertionsDisabled = !org/apache/lucene/search/FieldCacheImpl.desiredAssertionStatus();

	FieldCacheImpl()
	{
		init();
	}

	private synchronized void init()
	{
		caches = new HashMap(9);
		caches.put(Byte.TYPE, new ByteCache(this));
		caches.put(Short.TYPE, new ShortCache(this));
		caches.put(Integer.TYPE, new IntCache(this));
		caches.put(Float.TYPE, new FloatCache(this));
		caches.put(Long.TYPE, new LongCache(this));
		caches.put(Double.TYPE, new DoubleCache(this));
		caches.put(org/apache/lucene/search/FieldCache$DocTerms, new DocTermsCache(this));
		caches.put(org/apache/lucene/search/FieldCache$DocTermsIndex, new DocTermsIndexCache(this));
		caches.put(org/apache/lucene/index/DocTermOrds, new DocTermOrdsCache(this));
		caches.put(org/apache/lucene/search/FieldCacheImpl$DocsWithFieldCache, new DocsWithFieldCache(this));
	}

	public synchronized void purgeAllCaches()
	{
		init();
	}

	public synchronized void purge(AtomicReader r)
	{
		Cache c;
		for (Iterator i$ = caches.values().iterator(); i$.hasNext(); c.purge(r))
			c = (Cache)i$.next();

	}

	public synchronized FieldCache.CacheEntry[] getCacheEntries()
	{
		List result = new ArrayList(17);
		Iterator i$ = caches.entrySet().iterator();
		do
		{
			if (!i$.hasNext())
				break;
			java.util.Map.Entry cacheEntry = (java.util.Map.Entry)i$.next();
			Cache cache = (Cache)cacheEntry.getValue();
			Class cacheType = (Class)cacheEntry.getKey();
			synchronized (cache.readerCache)
			{
				Iterator i$ = cache.readerCache.entrySet().iterator();
				do
				{
					if (!i$.hasNext())
						break;
					java.util.Map.Entry readerCacheEntry = (java.util.Map.Entry)i$.next();
					Object readerKey = readerCacheEntry.getKey();
					if (readerKey != null)
					{
						Map innerCache = (Map)readerCacheEntry.getValue();
						Iterator i$ = innerCache.entrySet().iterator();
						while (i$.hasNext()) 
						{
							java.util.Map.Entry mapEntry = (java.util.Map.Entry)i$.next();
							Entry entry = (Entry)mapEntry.getKey();
							result.add(new CacheEntryImpl(readerKey, entry.field, cacheType, entry.custom, mapEntry.getValue()));
						}
					}
				} while (true);
			}
		} while (true);
		return (FieldCache.CacheEntry[])result.toArray(new FieldCache.CacheEntry[result.size()]);
	}

	private void initReader(AtomicReader reader)
	{
		if (reader instanceof SegmentReader)
		{
			((SegmentReader)reader).addCoreClosedListener(purgeCore);
		} else
		{
			Object key = reader.getCoreCacheKey();
			if (key instanceof AtomicReader)
				((AtomicReader)key).addReaderClosedListener(purgeReader);
			else
				reader.addReaderClosedListener(purgeReader);
		}
	}

	public byte[] getBytes(AtomicReader reader, String field, boolean setDocsWithField)
		throws IOException
	{
		return getBytes(reader, field, null, setDocsWithField);
	}

	public byte[] getBytes(AtomicReader reader, String field, FieldCache.ByteParser parser, boolean setDocsWithField)
		throws IOException
	{
		return (byte[])(byte[])((Cache)caches.get(Byte.TYPE)).get(reader, new Entry(field, parser), setDocsWithField);
	}

	public short[] getShorts(AtomicReader reader, String field, boolean setDocsWithField)
		throws IOException
	{
		return getShorts(reader, field, null, setDocsWithField);
	}

	public short[] getShorts(AtomicReader reader, String field, FieldCache.ShortParser parser, boolean setDocsWithField)
		throws IOException
	{
		return (short[])(short[])((Cache)caches.get(Short.TYPE)).get(reader, new Entry(field, parser), setDocsWithField);
	}

	void setDocsWithField(AtomicReader reader, String field, Bits docsWithField)
	{
		int maxDoc = reader.maxDoc();
		Bits bits;
		if (docsWithField == null)
			bits = new org.apache.lucene.util.Bits.MatchNoBits(maxDoc);
		else
		if (docsWithField instanceof FixedBitSet)
		{
			int numSet = ((FixedBitSet)docsWithField).cardinality();
			if (numSet >= maxDoc)
			{
				if (!$assertionsDisabled && numSet != maxDoc)
					throw new AssertionError();
				bits = new org.apache.lucene.util.Bits.MatchAllBits(maxDoc);
			} else
			{
				bits = docsWithField;
			}
		} else
		{
			bits = docsWithField;
		}
		((Cache)caches.get(org/apache/lucene/search/FieldCacheImpl$DocsWithFieldCache)).put(reader, new Entry(field, null), bits);
	}

	public int[] getInts(AtomicReader reader, String field, boolean setDocsWithField)
		throws IOException
	{
		return getInts(reader, field, null, setDocsWithField);
	}

	public int[] getInts(AtomicReader reader, String field, FieldCache.IntParser parser, boolean setDocsWithField)
		throws IOException
	{
		return (int[])(int[])((Cache)caches.get(Integer.TYPE)).get(reader, new Entry(field, parser), setDocsWithField);
	}

	public Bits getDocsWithField(AtomicReader reader, String field)
		throws IOException
	{
		return (Bits)((Cache)caches.get(org/apache/lucene/search/FieldCacheImpl$DocsWithFieldCache)).get(reader, new Entry(field, null), false);
	}

	public float[] getFloats(AtomicReader reader, String field, boolean setDocsWithField)
		throws IOException
	{
		return getFloats(reader, field, null, setDocsWithField);
	}

	public float[] getFloats(AtomicReader reader, String field, FieldCache.FloatParser parser, boolean setDocsWithField)
		throws IOException
	{
		return (float[])(float[])((Cache)caches.get(Float.TYPE)).get(reader, new Entry(field, parser), setDocsWithField);
	}

	public long[] getLongs(AtomicReader reader, String field, boolean setDocsWithField)
		throws IOException
	{
		return getLongs(reader, field, null, setDocsWithField);
	}

	public long[] getLongs(AtomicReader reader, String field, FieldCache.LongParser parser, boolean setDocsWithField)
		throws IOException
	{
		return (long[])(long[])((Cache)caches.get(Long.TYPE)).get(reader, new Entry(field, parser), setDocsWithField);
	}

	public double[] getDoubles(AtomicReader reader, String field, boolean setDocsWithField)
		throws IOException
	{
		return getDoubles(reader, field, null, setDocsWithField);
	}

	public double[] getDoubles(AtomicReader reader, String field, FieldCache.DoubleParser parser, boolean setDocsWithField)
		throws IOException
	{
		return (double[])(double[])((Cache)caches.get(Double.TYPE)).get(reader, new Entry(field, parser), setDocsWithField);
	}

	public FieldCache.DocTermsIndex getTermsIndex(AtomicReader reader, String field)
		throws IOException
	{
		return getTermsIndex(reader, field, 0.5F);
	}

	public FieldCache.DocTermsIndex getTermsIndex(AtomicReader reader, String field, float acceptableOverheadRatio)
		throws IOException
	{
		return (FieldCache.DocTermsIndex)((Cache)caches.get(org/apache/lucene/search/FieldCache$DocTermsIndex)).get(reader, new Entry(field, Float.valueOf(acceptableOverheadRatio)), false);
	}

	public FieldCache.DocTerms getTerms(AtomicReader reader, String field)
		throws IOException
	{
		return getTerms(reader, field, 0.5F);
	}

	public FieldCache.DocTerms getTerms(AtomicReader reader, String field, float acceptableOverheadRatio)
		throws IOException
	{
		return (FieldCache.DocTerms)((Cache)caches.get(org/apache/lucene/search/FieldCache$DocTerms)).get(reader, new Entry(field, Float.valueOf(acceptableOverheadRatio)), false);
	}

	public DocTermOrds getDocTermOrds(AtomicReader reader, String field)
		throws IOException
	{
		return (DocTermOrds)((Cache)caches.get(org/apache/lucene/index/DocTermOrds)).get(reader, new Entry(field, null), false);
	}

	public void setInfoStream(PrintStream stream)
	{
		infoStream = stream;
	}

	public PrintStream getInfoStream()
	{
		return infoStream;
	}


}
