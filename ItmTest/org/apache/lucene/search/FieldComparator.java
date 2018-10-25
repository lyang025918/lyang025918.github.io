// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FieldComparator.java

package org.apache.lucene.search;

import java.io.IOException;
import java.util.Comparator;
import org.apache.lucene.index.*;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.packed.PackedInts;

// Referenced classes of package org.apache.lucene.search:
//			FieldCache, Scorer, ScoreCachingWrappingScorer

public abstract class FieldComparator
{
	public static final class TermValDocValuesComparator extends FieldComparator
	{

		private BytesRef values[];
		private org.apache.lucene.index.DocValues.Source docTerms;
		private final String field;
		private BytesRef bottom;
		private final BytesRef tempBR = new BytesRef();
		static final boolean $assertionsDisabled = !org/apache/lucene/search/FieldComparator.desiredAssertionStatus();

		public int compare(int slot1, int slot2)
		{
			if (!$assertionsDisabled && values[slot1] == null)
				throw new AssertionError();
			if (!$assertionsDisabled && values[slot2] == null)
				throw new AssertionError();
			else
				return values[slot1].compareTo(values[slot2]);
		}

		public int compareBottom(int doc)
		{
			if (!$assertionsDisabled && bottom == null)
				throw new AssertionError();
			else
				return bottom.compareTo(docTerms.getBytes(doc, tempBR));
		}

		public void copy(int slot, int doc)
		{
			if (values[slot] == null)
				values[slot] = new BytesRef();
			docTerms.getBytes(doc, values[slot]);
		}

		public FieldComparator setNextReader(AtomicReaderContext context)
			throws IOException
		{
			DocValues dv = context.reader().docValues(field);
			if (dv != null)
				docTerms = dv.getSource();
			else
				docTerms = DocValues.getDefaultSource(org.apache.lucene.index.DocValues.Type.BYTES_VAR_DEREF);
			return this;
		}

		public void setBottom(int bottom)
		{
			this.bottom = values[bottom];
		}

		public BytesRef value(int slot)
		{
			return values[slot];
		}

		public int compareValues(BytesRef val1, BytesRef val2)
		{
			if (!$assertionsDisabled && val1 == null)
				throw new AssertionError();
			if (!$assertionsDisabled && val2 == null)
				throw new AssertionError();
			else
				return val1.compareTo(val2);
		}

		public int compareDocToValue(int doc, BytesRef value)
		{
			return docTerms.getBytes(doc, tempBR).compareTo(value);
		}

		public volatile int compareDocToValue(int x0, Object x1)
			throws IOException
		{
			return compareDocToValue(x0, (BytesRef)x1);
		}

		public volatile int compareValues(Object x0, Object x1)
		{
			return compareValues((BytesRef)x0, (BytesRef)x1);
		}

		public volatile Object value(int x0)
		{
			return value(x0);
		}


		TermValDocValuesComparator(int numHits, String field)
		{
			values = new BytesRef[numHits];
			this.field = field;
		}
	}

	public static final class TermValComparator extends FieldComparator
	{

		private BytesRef values[];
		private FieldCache.DocTerms docTerms;
		private final String field;
		private BytesRef bottom;
		private final BytesRef tempBR = new BytesRef();

		public int compare(int slot1, int slot2)
		{
			BytesRef val1 = values[slot1];
			BytesRef val2 = values[slot2];
			if (val1 == null)
				return val2 != null ? -1 : 0;
			if (val2 == null)
				return 1;
			else
				return val1.compareTo(val2);
		}

		public int compareBottom(int doc)
		{
			BytesRef val2 = docTerms.getTerm(doc, tempBR);
			if (bottom == null)
				return val2 != null ? -1 : 0;
			if (val2 == null)
				return 1;
			else
				return bottom.compareTo(val2);
		}

		public void copy(int slot, int doc)
		{
			if (values[slot] == null)
				values[slot] = new BytesRef();
			docTerms.getTerm(doc, values[slot]);
		}

		public FieldComparator setNextReader(AtomicReaderContext context)
			throws IOException
		{
			docTerms = FieldCache.DEFAULT.getTerms(context.reader(), field);
			return this;
		}

		public void setBottom(int bottom)
		{
			this.bottom = values[bottom];
		}

		public BytesRef value(int slot)
		{
			return values[slot];
		}

		public int compareValues(BytesRef val1, BytesRef val2)
		{
			if (val1 == null)
				return val2 != null ? -1 : 0;
			if (val2 == null)
				return 1;
			else
				return val1.compareTo(val2);
		}

		public int compareDocToValue(int doc, BytesRef value)
		{
			return docTerms.getTerm(doc, tempBR).compareTo(value);
		}

		public volatile int compareDocToValue(int x0, Object x1)
			throws IOException
		{
			return compareDocToValue(x0, (BytesRef)x1);
		}

		public volatile int compareValues(Object x0, Object x1)
		{
			return compareValues((BytesRef)x0, (BytesRef)x1);
		}

		public volatile Object value(int x0)
		{
			return value(x0);
		}

		TermValComparator(int numHits, String field)
		{
			values = new BytesRef[numHits];
			this.field = field;
		}
	}

	public static final class TermOrdValDocValuesComparator extends FieldComparator
	{
		private final class AnyOrdComparator extends PerSegmentComparator
		{

			private final int docBase;
			final TermOrdValDocValuesComparator this$0;

			public int compareBottom(int doc)
			{
				int docOrd = termsIndex.ord(doc);
				if (bottomSameReader)
					return bottomOrd - docOrd;
				return bottomOrd < docOrd ? -1 : 1;
			}

			public void copy(int slot, int doc)
			{
				int ord = termsIndex.ord(doc);
				ords[slot] = ord;
				if (values[slot] == null)
					values[slot] = new BytesRef();
				termsIndex.getByOrd(ord, values[slot]);
				readerGen[slot] = currentReaderGen;
			}

			public AnyOrdComparator(int docBase)
			{
				this$0 = TermOrdValDocValuesComparator.this;
				super();
				this.docBase = docBase;
			}
		}

		private final class AnyPackedDocToOrdComparator extends PerSegmentComparator
		{

			private final org.apache.lucene.util.packed.PackedInts.Reader readerOrds;
			private final int docBase;
			static final boolean $assertionsDisabled = !org/apache/lucene/search/FieldComparator.desiredAssertionStatus();
			final TermOrdValDocValuesComparator this$0;

			public int compareBottom(int doc)
			{
				if (!$assertionsDisabled && bottomSlot == -1)
					throw new AssertionError();
				int docOrd = (int)readerOrds.get(doc);
				if (bottomSameReader)
					return bottomOrd - docOrd;
				return bottomOrd < docOrd ? -1 : 1;
			}

			public void copy(int slot, int doc)
			{
				int ord = (int)readerOrds.get(doc);
				ords[slot] = ord;
				if (values[slot] == null)
					values[slot] = new BytesRef();
				termsIndex.getByOrd(ord, values[slot]);
				readerGen[slot] = currentReaderGen;
			}


			public AnyPackedDocToOrdComparator(org.apache.lucene.util.packed.PackedInts.Reader readerOrds, int docBase)
			{
				this$0 = TermOrdValDocValuesComparator.this;
				super();
				this.readerOrds = readerOrds;
				this.docBase = docBase;
			}
		}

		private final class IntOrdComparator extends PerSegmentComparator
		{

			private final int readerOrds[];
			private final org.apache.lucene.index.DocValues.SortedSource termsIndex;
			private final int docBase;
			static final boolean $assertionsDisabled = !org/apache/lucene/search/FieldComparator.desiredAssertionStatus();
			final TermOrdValDocValuesComparator this$0;

			public int compareBottom(int doc)
			{
				if (!$assertionsDisabled && bottomSlot == -1)
					throw new AssertionError();
				int docOrd = readerOrds[doc];
				if (bottomSameReader)
					return bottomOrd - docOrd;
				return bottomOrd < docOrd ? -1 : 1;
			}

			public void copy(int slot, int doc)
			{
				int ord = readerOrds[doc];
				ords[slot] = ord;
				if (values[slot] == null)
					values[slot] = new BytesRef();
				termsIndex.getByOrd(ord, values[slot]);
				readerGen[slot] = currentReaderGen;
			}


			public IntOrdComparator(int readerOrds[], org.apache.lucene.index.DocValues.SortedSource termsIndex, int docBase)
			{
				this$0 = TermOrdValDocValuesComparator.this;
				super();
				this.readerOrds = readerOrds;
				this.termsIndex = termsIndex;
				this.docBase = docBase;
			}
		}

		private final class ShortOrdComparator extends PerSegmentComparator
		{

			private final short readerOrds[];
			private final org.apache.lucene.index.DocValues.SortedSource termsIndex;
			private final int docBase;
			static final boolean $assertionsDisabled = !org/apache/lucene/search/FieldComparator.desiredAssertionStatus();
			final TermOrdValDocValuesComparator this$0;

			public int compareBottom(int doc)
			{
				if (!$assertionsDisabled && bottomSlot == -1)
					throw new AssertionError();
				int docOrd = readerOrds[doc] & 0xffff;
				if (bottomSameReader)
					return bottomOrd - docOrd;
				return bottomOrd < docOrd ? -1 : 1;
			}

			public void copy(int slot, int doc)
			{
				int ord = readerOrds[doc] & 0xffff;
				ords[slot] = ord;
				if (values[slot] == null)
					values[slot] = new BytesRef();
				termsIndex.getByOrd(ord, values[slot]);
				readerGen[slot] = currentReaderGen;
			}


			public ShortOrdComparator(short readerOrds[], org.apache.lucene.index.DocValues.SortedSource termsIndex, int docBase)
			{
				this$0 = TermOrdValDocValuesComparator.this;
				super();
				this.readerOrds = readerOrds;
				this.termsIndex = termsIndex;
				this.docBase = docBase;
			}
		}

		private final class ByteOrdComparator extends PerSegmentComparator
		{

			private final byte readerOrds[];
			private final org.apache.lucene.index.DocValues.SortedSource termsIndex;
			private final int docBase;
			static final boolean $assertionsDisabled = !org/apache/lucene/search/FieldComparator.desiredAssertionStatus();
			final TermOrdValDocValuesComparator this$0;

			public int compareBottom(int doc)
			{
				if (!$assertionsDisabled && bottomSlot == -1)
					throw new AssertionError();
				int docOrd = readerOrds[doc] & 0xff;
				if (bottomSameReader)
					return bottomOrd - docOrd;
				return bottomOrd < docOrd ? -1 : 1;
			}

			public void copy(int slot, int doc)
			{
				int ord = readerOrds[doc] & 0xff;
				ords[slot] = ord;
				if (values[slot] == null)
					values[slot] = new BytesRef();
				termsIndex.getByOrd(ord, values[slot]);
				readerGen[slot] = currentReaderGen;
			}


			public ByteOrdComparator(byte readerOrds[], org.apache.lucene.index.DocValues.SortedSource termsIndex, int docBase)
			{
				this$0 = TermOrdValDocValuesComparator.this;
				super();
				this.readerOrds = readerOrds;
				this.termsIndex = termsIndex;
				this.docBase = docBase;
			}
		}

		abstract class PerSegmentComparator extends FieldComparator
		{

			static final boolean $assertionsDisabled = !org/apache/lucene/search/FieldComparator.desiredAssertionStatus();
			final TermOrdValDocValuesComparator this$0;

			public FieldComparator setNextReader(AtomicReaderContext context)
				throws IOException
			{
				return TermOrdValDocValuesComparator.this.setNextReader(context);
			}

			public int compare(int slot1, int slot2)
			{
				return TermOrdValDocValuesComparator.this.compare(slot1, slot2);
			}

			public void setBottom(int bottom)
			{
				TermOrdValDocValuesComparator.this.setBottom(bottom);
			}

			public BytesRef value(int slot)
			{
				return TermOrdValDocValuesComparator.this.value(slot);
			}

			public int compareValues(BytesRef val1, BytesRef val2)
			{
				if (!$assertionsDisabled && val1 == null)
					throw new AssertionError();
				if (!$assertionsDisabled && val2 == null)
					throw new AssertionError();
				else
					return comp.compare(val1, val2);
			}

			public int compareDocToValue(int doc, BytesRef value)
			{
				return TermOrdValDocValuesComparator.this.compareDocToValue(doc, value);
			}

			public volatile int compareDocToValue(int x0, Object x1)
				throws IOException
			{
				return compareDocToValue(x0, (BytesRef)x1);
			}

			public volatile int compareValues(Object x0, Object x1)
			{
				return compareValues((BytesRef)x0, (BytesRef)x1);
			}

			public volatile Object value(int x0)
			{
				return value(x0);
			}


			PerSegmentComparator()
			{
				this$0 = TermOrdValDocValuesComparator.this;
				super();
			}
		}


		final int ords[];
		final BytesRef values[];
		final int readerGen[];
		int currentReaderGen;
		org.apache.lucene.index.DocValues.SortedSource termsIndex;
		Comparator comp;
		private final String field;
		int bottomSlot;
		int bottomOrd;
		boolean bottomSameReader;
		BytesRef bottomValue;
		final BytesRef tempBR = new BytesRef();
		static final boolean $assertionsDisabled = !org/apache/lucene/search/FieldComparator.desiredAssertionStatus();

		public int compare(int slot1, int slot2)
		{
			if (readerGen[slot1] == readerGen[slot2])
				return ords[slot1] - ords[slot2];
			BytesRef val1 = values[slot1];
			BytesRef val2 = values[slot2];
			if (val1 == null)
				return val2 != null ? -1 : 0;
			if (val2 == null)
				return 1;
			else
				return comp.compare(val1, val2);
		}

		public int compareBottom(int doc)
		{
			throw new UnsupportedOperationException();
		}

		public void copy(int slot, int doc)
		{
			throw new UnsupportedOperationException();
		}

		public int compareDocToValue(int doc, BytesRef value)
		{
			return termsIndex.getBytes(doc, tempBR).compareTo(value);
		}

		public FieldComparator setNextReader(AtomicReaderContext context)
			throws IOException
		{
			int docBase = context.docBase;
			DocValues dv = context.reader().docValues(field);
			if (dv == null)
			{
				termsIndex = DocValues.getDefaultSortedSource(org.apache.lucene.index.DocValues.Type.BYTES_VAR_SORTED, context.reader().maxDoc());
			} else
			{
				termsIndex = dv.getSource().asSortedSource();
				if (termsIndex == null)
					throw new IllegalStateException((new StringBuilder()).append("DocValues exist for field \"").append(field).append("\", but not as a sorted source: type=").append(dv.getSource().getType()).append(" reader=").append(context.reader()).toString());
			}
			comp = termsIndex.getComparator();
			FieldComparator perSegComp = null;
			if (termsIndex.hasPackedDocToOrd())
			{
				org.apache.lucene.util.packed.PackedInts.Reader docToOrd = termsIndex.getDocToOrd();
				if (docToOrd.hasArray())
				{
					Object arr = docToOrd.getArray();
					if (!$assertionsDisabled && arr == null)
						throw new AssertionError();
					if (arr instanceof byte[])
						perSegComp = new ByteOrdComparator((byte[])(byte[])arr, termsIndex, docBase);
					else
					if (arr instanceof short[])
						perSegComp = new ShortOrdComparator((short[])(short[])arr, termsIndex, docBase);
					else
					if (arr instanceof int[])
						perSegComp = new IntOrdComparator((int[])(int[])arr, termsIndex, docBase);
				}
				if (perSegComp == null)
					perSegComp = new AnyPackedDocToOrdComparator(docToOrd, docBase);
			} else
			if (perSegComp == null)
				perSegComp = new AnyOrdComparator(docBase);
			currentReaderGen++;
			if (bottomSlot != -1)
				perSegComp.setBottom(bottomSlot);
			return perSegComp;
		}

		public void setBottom(int bottom)
		{
			bottomSlot = bottom;
			bottomValue = values[bottomSlot];
			if (currentReaderGen == readerGen[bottomSlot])
			{
				bottomOrd = ords[bottomSlot];
				bottomSameReader = true;
			} else
			if (bottomValue == null)
			{
				if (!$assertionsDisabled && ords[bottomSlot] != 0)
					throw new AssertionError();
				bottomOrd = 0;
				bottomSameReader = true;
				readerGen[bottomSlot] = currentReaderGen;
			} else
			{
				int index = termsIndex.getOrdByValue(bottomValue, tempBR);
				if (index < 0)
				{
					bottomOrd = -index - 2;
					bottomSameReader = false;
				} else
				{
					bottomOrd = index;
					bottomSameReader = true;
					readerGen[bottomSlot] = currentReaderGen;
					ords[bottomSlot] = bottomOrd;
				}
			}
		}

		public BytesRef value(int slot)
		{
			return values[slot];
		}

		public volatile int compareDocToValue(int x0, Object x1)
			throws IOException
		{
			return compareDocToValue(x0, (BytesRef)x1);
		}

		public volatile Object value(int x0)
		{
			return value(x0);
		}


		public TermOrdValDocValuesComparator(int numHits, String field)
		{
			currentReaderGen = -1;
			bottomSlot = -1;
			ords = new int[numHits];
			values = new BytesRef[numHits];
			readerGen = new int[numHits];
			this.field = field;
		}
	}

	public static final class TermOrdValComparator extends FieldComparator
	{
		private final class AnyOrdComparator extends PerSegmentComparator
		{

			private final org.apache.lucene.util.packed.PackedInts.Reader readerOrds;
			private final FieldCache.DocTermsIndex termsIndex;
			private final int docBase;
			static final boolean $assertionsDisabled = !org/apache/lucene/search/FieldComparator.desiredAssertionStatus();
			final TermOrdValComparator this$0;

			public int compareBottom(int doc)
			{
				if (!$assertionsDisabled && bottomSlot == -1)
					throw new AssertionError();
				int docOrd = (int)readerOrds.get(doc);
				if (bottomSameReader)
					return bottomOrd - docOrd;
				return bottomOrd < docOrd ? -1 : 1;
			}

			public void copy(int slot, int doc)
			{
				int ord = (int)readerOrds.get(doc);
				ords[slot] = ord;
				if (ord == 0)
				{
					values[slot] = null;
				} else
				{
					if (!$assertionsDisabled && ord <= 0)
						throw new AssertionError();
					if (values[slot] == null)
						values[slot] = new BytesRef();
					termsIndex.lookup(ord, values[slot]);
				}
				readerGen[slot] = currentReaderGen;
			}


			public AnyOrdComparator(org.apache.lucene.util.packed.PackedInts.Reader readerOrds, FieldCache.DocTermsIndex termsIndex, int docBase)
			{
				this$0 = TermOrdValComparator.this;
				super();
				this.readerOrds = readerOrds;
				this.termsIndex = termsIndex;
				this.docBase = docBase;
			}
		}

		private final class IntOrdComparator extends PerSegmentComparator
		{

			private final int readerOrds[];
			private final FieldCache.DocTermsIndex termsIndex;
			private final int docBase;
			static final boolean $assertionsDisabled = !org/apache/lucene/search/FieldComparator.desiredAssertionStatus();
			final TermOrdValComparator this$0;

			public int compareBottom(int doc)
			{
				if (!$assertionsDisabled && bottomSlot == -1)
					throw new AssertionError();
				int docOrd = readerOrds[doc];
				if (bottomSameReader)
					return bottomOrd - docOrd;
				return bottomOrd < docOrd ? -1 : 1;
			}

			public void copy(int slot, int doc)
			{
				int ord = readerOrds[doc];
				ords[slot] = ord;
				if (ord == 0)
				{
					values[slot] = null;
				} else
				{
					if (!$assertionsDisabled && ord <= 0)
						throw new AssertionError();
					if (values[slot] == null)
						values[slot] = new BytesRef();
					termsIndex.lookup(ord, values[slot]);
				}
				readerGen[slot] = currentReaderGen;
			}


			public IntOrdComparator(int readerOrds[], FieldCache.DocTermsIndex termsIndex, int docBase)
			{
				this$0 = TermOrdValComparator.this;
				super();
				this.readerOrds = readerOrds;
				this.termsIndex = termsIndex;
				this.docBase = docBase;
			}
		}

		private final class ShortOrdComparator extends PerSegmentComparator
		{

			private final short readerOrds[];
			private final FieldCache.DocTermsIndex termsIndex;
			private final int docBase;
			static final boolean $assertionsDisabled = !org/apache/lucene/search/FieldComparator.desiredAssertionStatus();
			final TermOrdValComparator this$0;

			public int compareBottom(int doc)
			{
				if (!$assertionsDisabled && bottomSlot == -1)
					throw new AssertionError();
				int docOrd = readerOrds[doc] & 0xffff;
				if (bottomSameReader)
					return bottomOrd - docOrd;
				return bottomOrd < docOrd ? -1 : 1;
			}

			public void copy(int slot, int doc)
			{
				int ord = readerOrds[doc] & 0xffff;
				ords[slot] = ord;
				if (ord == 0)
				{
					values[slot] = null;
				} else
				{
					if (!$assertionsDisabled && ord <= 0)
						throw new AssertionError();
					if (values[slot] == null)
						values[slot] = new BytesRef();
					termsIndex.lookup(ord, values[slot]);
				}
				readerGen[slot] = currentReaderGen;
			}


			public ShortOrdComparator(short readerOrds[], FieldCache.DocTermsIndex termsIndex, int docBase)
			{
				this$0 = TermOrdValComparator.this;
				super();
				this.readerOrds = readerOrds;
				this.termsIndex = termsIndex;
				this.docBase = docBase;
			}
		}

		private final class ByteOrdComparator extends PerSegmentComparator
		{

			private final byte readerOrds[];
			private final FieldCache.DocTermsIndex termsIndex;
			private final int docBase;
			static final boolean $assertionsDisabled = !org/apache/lucene/search/FieldComparator.desiredAssertionStatus();
			final TermOrdValComparator this$0;

			public int compareBottom(int doc)
			{
				if (!$assertionsDisabled && bottomSlot == -1)
					throw new AssertionError();
				int docOrd = readerOrds[doc] & 0xff;
				if (bottomSameReader)
					return bottomOrd - docOrd;
				return bottomOrd < docOrd ? -1 : 1;
			}

			public void copy(int slot, int doc)
			{
				int ord = readerOrds[doc] & 0xff;
				ords[slot] = ord;
				if (ord == 0)
				{
					values[slot] = null;
				} else
				{
					if (!$assertionsDisabled && ord <= 0)
						throw new AssertionError();
					if (values[slot] == null)
						values[slot] = new BytesRef();
					termsIndex.lookup(ord, values[slot]);
				}
				readerGen[slot] = currentReaderGen;
			}


			public ByteOrdComparator(byte readerOrds[], FieldCache.DocTermsIndex termsIndex, int docBase)
			{
				this$0 = TermOrdValComparator.this;
				super();
				this.readerOrds = readerOrds;
				this.termsIndex = termsIndex;
				this.docBase = docBase;
			}
		}

		abstract class PerSegmentComparator extends FieldComparator
		{

			final TermOrdValComparator this$0;

			public FieldComparator setNextReader(AtomicReaderContext context)
				throws IOException
			{
				return TermOrdValComparator.this.setNextReader(context);
			}

			public int compare(int slot1, int slot2)
			{
				return TermOrdValComparator.this.compare(slot1, slot2);
			}

			public void setBottom(int bottom)
			{
				TermOrdValComparator.this.setBottom(bottom);
			}

			public BytesRef value(int slot)
			{
				return TermOrdValComparator.this.value(slot);
			}

			public int compareValues(BytesRef val1, BytesRef val2)
			{
				if (val1 == null)
					return val2 != null ? -1 : 0;
				if (val2 == null)
					return 1;
				else
					return val1.compareTo(val2);
			}

			public int compareDocToValue(int doc, BytesRef value)
			{
				return TermOrdValComparator.this.compareDocToValue(doc, value);
			}

			public volatile int compareDocToValue(int x0, Object x1)
				throws IOException
			{
				return compareDocToValue(x0, (BytesRef)x1);
			}

			public volatile int compareValues(Object x0, Object x1)
			{
				return compareValues((BytesRef)x0, (BytesRef)x1);
			}

			public volatile Object value(int x0)
			{
				return value(x0);
			}

			PerSegmentComparator()
			{
				this$0 = TermOrdValComparator.this;
				super();
			}
		}


		final int ords[];
		final BytesRef values[];
		final int readerGen[];
		int currentReaderGen;
		FieldCache.DocTermsIndex termsIndex;
		private final String field;
		int bottomSlot;
		int bottomOrd;
		boolean bottomSameReader;
		BytesRef bottomValue;
		final BytesRef tempBR = new BytesRef();
		static final boolean $assertionsDisabled = !org/apache/lucene/search/FieldComparator.desiredAssertionStatus();

		public int compare(int slot1, int slot2)
		{
			if (readerGen[slot1] == readerGen[slot2])
				return ords[slot1] - ords[slot2];
			BytesRef val1 = values[slot1];
			BytesRef val2 = values[slot2];
			if (val1 == null)
				return val2 != null ? -1 : 0;
			if (val2 == null)
				return 1;
			else
				return val1.compareTo(val2);
		}

		public int compareBottom(int doc)
		{
			throw new UnsupportedOperationException();
		}

		public void copy(int slot, int doc)
		{
			throw new UnsupportedOperationException();
		}

		public int compareDocToValue(int doc, BytesRef value)
		{
			BytesRef docValue = termsIndex.getTerm(doc, tempBR);
			if (docValue == null)
				return value != null ? -1 : 0;
			if (value == null)
				return 1;
			else
				return docValue.compareTo(value);
		}

		public FieldComparator setNextReader(AtomicReaderContext context)
			throws IOException
		{
			int docBase = context.docBase;
			termsIndex = FieldCache.DEFAULT.getTermsIndex(context.reader(), field);
			org.apache.lucene.util.packed.PackedInts.Reader docToOrd = termsIndex.getDocToOrd();
			FieldComparator perSegComp = null;
			if (docToOrd.hasArray())
			{
				Object arr = docToOrd.getArray();
				if (arr instanceof byte[])
					perSegComp = new ByteOrdComparator((byte[])(byte[])arr, termsIndex, docBase);
				else
				if (arr instanceof short[])
					perSegComp = new ShortOrdComparator((short[])(short[])arr, termsIndex, docBase);
				else
				if (arr instanceof int[])
					perSegComp = new IntOrdComparator((int[])(int[])arr, termsIndex, docBase);
			}
			if (perSegComp == null)
				perSegComp = new AnyOrdComparator(docToOrd, termsIndex, docBase);
			currentReaderGen++;
			if (bottomSlot != -1)
				perSegComp.setBottom(bottomSlot);
			return perSegComp;
		}

		public void setBottom(int bottom)
		{
			bottomSlot = bottom;
			bottomValue = values[bottomSlot];
			if (currentReaderGen == readerGen[bottomSlot])
			{
				bottomOrd = ords[bottomSlot];
				bottomSameReader = true;
			} else
			if (bottomValue == null)
			{
				if (!$assertionsDisabled && ords[bottomSlot] != 0)
					throw new AssertionError();
				bottomOrd = 0;
				bottomSameReader = true;
				readerGen[bottomSlot] = currentReaderGen;
			} else
			{
				int index = binarySearch(tempBR, termsIndex, bottomValue);
				if (index < 0)
				{
					bottomOrd = -index - 2;
					bottomSameReader = false;
				} else
				{
					bottomOrd = index;
					bottomSameReader = true;
					readerGen[bottomSlot] = currentReaderGen;
					ords[bottomSlot] = bottomOrd;
				}
			}
		}

		public BytesRef value(int slot)
		{
			return values[slot];
		}

		public volatile int compareDocToValue(int x0, Object x1)
			throws IOException
		{
			return compareDocToValue(x0, (BytesRef)x1);
		}

		public volatile Object value(int x0)
		{
			return value(x0);
		}


		public TermOrdValComparator(int numHits, String field)
		{
			currentReaderGen = -1;
			bottomSlot = -1;
			ords = new int[numHits];
			values = new BytesRef[numHits];
			readerGen = new int[numHits];
			this.field = field;
		}
	}

	public static final class DocComparator extends FieldComparator
	{

		private final int docIDs[];
		private int docBase;
		private int bottom;

		public int compare(int slot1, int slot2)
		{
			return docIDs[slot1] - docIDs[slot2];
		}

		public int compareBottom(int doc)
		{
			return bottom - (docBase + doc);
		}

		public void copy(int slot, int doc)
		{
			docIDs[slot] = docBase + doc;
		}

		public FieldComparator setNextReader(AtomicReaderContext context)
		{
			docBase = context.docBase;
			return this;
		}

		public void setBottom(int bottom)
		{
			this.bottom = docIDs[bottom];
		}

		public Integer value(int slot)
		{
			return Integer.valueOf(docIDs[slot]);
		}

		public int compareDocToValue(int doc, Integer valueObj)
		{
			int value = valueObj.intValue();
			int docValue = docBase + doc;
			if (docValue < value)
				return -1;
			return docValue <= value ? 0 : 1;
		}

		public volatile int compareDocToValue(int x0, Object x1)
			throws IOException
		{
			return compareDocToValue(x0, (Integer)x1);
		}

		public volatile Object value(int x0)
		{
			return value(x0);
		}

		DocComparator(int numHits)
		{
			docIDs = new int[numHits];
		}
	}

	public static final class RelevanceComparator extends FieldComparator
	{

		private final float scores[];
		private float bottom;
		private Scorer scorer;
		static final boolean $assertionsDisabled = !org/apache/lucene/search/FieldComparator.desiredAssertionStatus();

		public int compare(int slot1, int slot2)
		{
			float score1 = scores[slot1];
			float score2 = scores[slot2];
			return score1 <= score2 ? ((int) (score1 >= score2 ? 0 : 1)) : -1;
		}

		public int compareBottom(int doc)
			throws IOException
		{
			float score = scorer.score();
			if (!$assertionsDisabled && Float.isNaN(score))
				throw new AssertionError();
			else
				return bottom <= score ? ((int) (bottom >= score ? 0 : 1)) : -1;
		}

		public void copy(int slot, int doc)
			throws IOException
		{
			scores[slot] = scorer.score();
			if (!$assertionsDisabled && Float.isNaN(scores[slot]))
				throw new AssertionError();
			else
				return;
		}

		public FieldComparator setNextReader(AtomicReaderContext context)
		{
			return this;
		}

		public void setBottom(int bottom)
		{
			this.bottom = scores[bottom];
		}

		public void setScorer(Scorer scorer)
		{
			if (!(scorer instanceof ScoreCachingWrappingScorer))
				this.scorer = new ScoreCachingWrappingScorer(scorer);
			else
				this.scorer = scorer;
		}

		public Float value(int slot)
		{
			return Float.valueOf(scores[slot]);
		}

		public int compareValues(Float first, Float second)
		{
			return second.compareTo(first);
		}

		public int compareDocToValue(int doc, Float valueObj)
			throws IOException
		{
			float value = valueObj.floatValue();
			float docValue = scorer.score();
			if (!$assertionsDisabled && Float.isNaN(docValue))
				throw new AssertionError();
			if (docValue < value)
				return 1;
			return docValue <= value ? 0 : -1;
		}

		public volatile int compareDocToValue(int x0, Object x1)
			throws IOException
		{
			return compareDocToValue(x0, (Float)x1);
		}

		public volatile int compareValues(Object x0, Object x1)
		{
			return compareValues((Float)x0, (Float)x1);
		}

		public volatile Object value(int x0)
		{
			return value(x0);
		}


		RelevanceComparator(int numHits)
		{
			scores = new float[numHits];
		}
	}

	public static final class LongComparator extends NumericComparator
	{

		private final long values[];
		private final FieldCache.LongParser parser;
		private long currentReaderValues[];
		private long bottom;

		public int compare(int slot1, int slot2)
		{
			long v1 = values[slot1];
			long v2 = values[slot2];
			if (v1 > v2)
				return 1;
			return v1 >= v2 ? 0 : -1;
		}

		public int compareBottom(int doc)
		{
			long v2 = currentReaderValues[doc];
			if (docsWithField != null && v2 == 0L && !docsWithField.get(doc))
				v2 = ((Long)missingValue).longValue();
			if (bottom > v2)
				return 1;
			return bottom >= v2 ? 0 : -1;
		}

		public void copy(int slot, int doc)
		{
			long v2 = currentReaderValues[doc];
			if (docsWithField != null && v2 == 0L && !docsWithField.get(doc))
				v2 = ((Long)missingValue).longValue();
			values[slot] = v2;
		}

		public FieldComparator setNextReader(AtomicReaderContext context)
			throws IOException
		{
			currentReaderValues = FieldCache.DEFAULT.getLongs(context.reader(), field, parser, missingValue != null);
			return super.setNextReader(context);
		}

		public void setBottom(int bottom)
		{
			this.bottom = values[bottom];
		}

		public Long value(int slot)
		{
			return Long.valueOf(values[slot]);
		}

		public int compareDocToValue(int doc, Long valueObj)
		{
			long value = valueObj.longValue();
			long docValue = currentReaderValues[doc];
			if (docsWithField != null && docValue == 0L && !docsWithField.get(doc))
				docValue = ((Long)missingValue).longValue();
			if (docValue < value)
				return -1;
			return docValue <= value ? 0 : 1;
		}

		public volatile int compareDocToValue(int x0, Object x1)
			throws IOException
		{
			return compareDocToValue(x0, (Long)x1);
		}

		public volatile Object value(int x0)
		{
			return value(x0);
		}

		LongComparator(int numHits, String field, FieldCache.Parser parser, Long missingValue)
		{
			super(field, missingValue);
			values = new long[numHits];
			this.parser = (FieldCache.LongParser)parser;
		}
	}

	public static final class IntDocValuesComparator extends FieldComparator
	{

		private final long values[];
		private org.apache.lucene.index.DocValues.Source currentReaderValues;
		private final String field;
		private long bottom;

		public int compare(int slot1, int slot2)
		{
			long v1 = values[slot1];
			long v2 = values[slot2];
			if (v1 > v2)
				return 1;
			return v1 >= v2 ? 0 : -1;
		}

		public int compareBottom(int doc)
		{
			long v2 = currentReaderValues.getInt(doc);
			if (bottom > v2)
				return 1;
			return bottom >= v2 ? 0 : -1;
		}

		public void copy(int slot, int doc)
		{
			values[slot] = currentReaderValues.getInt(doc);
		}

		public FieldComparator setNextReader(AtomicReaderContext context)
			throws IOException
		{
			DocValues docValues = context.reader().docValues(field);
			if (docValues != null)
				currentReaderValues = docValues.getSource();
			else
				currentReaderValues = DocValues.getDefaultSource(org.apache.lucene.index.DocValues.Type.FIXED_INTS_64);
			return this;
		}

		public void setBottom(int bottom)
		{
			this.bottom = values[bottom];
		}

		public Long value(int slot)
		{
			return Long.valueOf(values[slot]);
		}

		public int compareDocToValue(int doc, Long valueObj)
		{
			long value = valueObj.longValue();
			long docValue = currentReaderValues.getInt(doc);
			if (docValue < value)
				return -1;
			return docValue <= value ? 0 : 1;
		}

		public volatile int compareDocToValue(int x0, Object x1)
			throws IOException
		{
			return compareDocToValue(x0, (Long)x1);
		}

		public volatile Object value(int x0)
		{
			return value(x0);
		}

		IntDocValuesComparator(int numHits, String field)
		{
			values = new long[numHits];
			this.field = field;
		}
	}

	public static final class IntComparator extends NumericComparator
	{

		private final int values[];
		private final FieldCache.IntParser parser;
		private int currentReaderValues[];
		private int bottom;

		public int compare(int slot1, int slot2)
		{
			int v1 = values[slot1];
			int v2 = values[slot2];
			if (v1 > v2)
				return 1;
			return v1 >= v2 ? 0 : -1;
		}

		public int compareBottom(int doc)
		{
			int v2 = currentReaderValues[doc];
			if (docsWithField != null && v2 == 0 && !docsWithField.get(doc))
				v2 = ((Integer)missingValue).intValue();
			if (bottom > v2)
				return 1;
			return bottom >= v2 ? 0 : -1;
		}

		public void copy(int slot, int doc)
		{
			int v2 = currentReaderValues[doc];
			if (docsWithField != null && v2 == 0 && !docsWithField.get(doc))
				v2 = ((Integer)missingValue).intValue();
			values[slot] = v2;
		}

		public FieldComparator setNextReader(AtomicReaderContext context)
			throws IOException
		{
			currentReaderValues = FieldCache.DEFAULT.getInts(context.reader(), field, parser, missingValue != null);
			return super.setNextReader(context);
		}

		public void setBottom(int bottom)
		{
			this.bottom = values[bottom];
		}

		public Integer value(int slot)
		{
			return Integer.valueOf(values[slot]);
		}

		public int compareDocToValue(int doc, Integer valueObj)
		{
			int value = valueObj.intValue();
			int docValue = currentReaderValues[doc];
			if (docsWithField != null && docValue == 0 && !docsWithField.get(doc))
				docValue = ((Integer)missingValue).intValue();
			if (docValue < value)
				return -1;
			return docValue <= value ? 0 : 1;
		}

		public volatile int compareDocToValue(int x0, Object x1)
			throws IOException
		{
			return compareDocToValue(x0, (Integer)x1);
		}

		public volatile Object value(int x0)
		{
			return value(x0);
		}

		IntComparator(int numHits, String field, FieldCache.Parser parser, Integer missingValue)
		{
			super(field, missingValue);
			values = new int[numHits];
			this.parser = (FieldCache.IntParser)parser;
		}
	}

	public static final class ShortComparator extends NumericComparator
	{

		private final short values[];
		private final FieldCache.ShortParser parser;
		private short currentReaderValues[];
		private short bottom;

		public int compare(int slot1, int slot2)
		{
			return values[slot1] - values[slot2];
		}

		public int compareBottom(int doc)
		{
			short v2 = currentReaderValues[doc];
			if (docsWithField != null && v2 == 0 && !docsWithField.get(doc))
				v2 = ((Short)missingValue).shortValue();
			return bottom - v2;
		}

		public void copy(int slot, int doc)
		{
			short v2 = currentReaderValues[doc];
			if (docsWithField != null && v2 == 0 && !docsWithField.get(doc))
				v2 = ((Short)missingValue).shortValue();
			values[slot] = v2;
		}

		public FieldComparator setNextReader(AtomicReaderContext context)
			throws IOException
		{
			currentReaderValues = FieldCache.DEFAULT.getShorts(context.reader(), field, parser, missingValue != null);
			return super.setNextReader(context);
		}

		public void setBottom(int bottom)
		{
			this.bottom = values[bottom];
		}

		public Short value(int slot)
		{
			return Short.valueOf(values[slot]);
		}

		public int compareDocToValue(int doc, Short valueObj)
		{
			short value = valueObj.shortValue();
			short docValue = currentReaderValues[doc];
			if (docsWithField != null && docValue == 0 && !docsWithField.get(doc))
				docValue = ((Short)missingValue).shortValue();
			return docValue - value;
		}

		public volatile int compareDocToValue(int x0, Object x1)
			throws IOException
		{
			return compareDocToValue(x0, (Short)x1);
		}

		public volatile Object value(int x0)
		{
			return value(x0);
		}

		ShortComparator(int numHits, String field, FieldCache.Parser parser, Short missingValue)
		{
			super(field, missingValue);
			values = new short[numHits];
			this.parser = (FieldCache.ShortParser)parser;
		}
	}

	public static final class FloatComparator extends NumericComparator
	{

		private final float values[];
		private final FieldCache.FloatParser parser;
		private float currentReaderValues[];
		private float bottom;

		public int compare(int slot1, int slot2)
		{
			float v1 = values[slot1];
			float v2 = values[slot2];
			if (v1 > v2)
				return 1;
			return v1 >= v2 ? 0 : -1;
		}

		public int compareBottom(int doc)
		{
			float v2 = currentReaderValues[doc];
			if (docsWithField != null && v2 == 0.0F && !docsWithField.get(doc))
				v2 = ((Float)missingValue).floatValue();
			if (bottom > v2)
				return 1;
			return bottom >= v2 ? 0 : -1;
		}

		public void copy(int slot, int doc)
		{
			float v2 = currentReaderValues[doc];
			if (docsWithField != null && v2 == 0.0F && !docsWithField.get(doc))
				v2 = ((Float)missingValue).floatValue();
			values[slot] = v2;
		}

		public FieldComparator setNextReader(AtomicReaderContext context)
			throws IOException
		{
			currentReaderValues = FieldCache.DEFAULT.getFloats(context.reader(), field, parser, missingValue != null);
			return super.setNextReader(context);
		}

		public void setBottom(int bottom)
		{
			this.bottom = values[bottom];
		}

		public Float value(int slot)
		{
			return Float.valueOf(values[slot]);
		}

		public int compareDocToValue(int doc, Float valueObj)
		{
			float value = valueObj.floatValue();
			float docValue = currentReaderValues[doc];
			if (docsWithField != null && docValue == 0.0F && !docsWithField.get(doc))
				docValue = ((Float)missingValue).floatValue();
			if (docValue < value)
				return -1;
			return docValue <= value ? 0 : 1;
		}

		public volatile int compareDocToValue(int x0, Object x1)
			throws IOException
		{
			return compareDocToValue(x0, (Float)x1);
		}

		public volatile Object value(int x0)
		{
			return value(x0);
		}

		FloatComparator(int numHits, String field, FieldCache.Parser parser, Float missingValue)
		{
			super(field, missingValue);
			values = new float[numHits];
			this.parser = (FieldCache.FloatParser)parser;
		}
	}

	public static final class FloatDocValuesComparator extends FieldComparator
	{

		private final double values[];
		private final String field;
		private org.apache.lucene.index.DocValues.Source currentReaderValues;
		private double bottom;

		public int compare(int slot1, int slot2)
		{
			double v1 = values[slot1];
			double v2 = values[slot2];
			if (v1 > v2)
				return 1;
			return v1 >= v2 ? 0 : -1;
		}

		public int compareBottom(int doc)
		{
			double v2 = currentReaderValues.getFloat(doc);
			if (bottom > v2)
				return 1;
			return bottom >= v2 ? 0 : -1;
		}

		public void copy(int slot, int doc)
		{
			values[slot] = currentReaderValues.getFloat(doc);
		}

		public FieldComparator setNextReader(AtomicReaderContext context)
			throws IOException
		{
			DocValues docValues = context.reader().docValues(field);
			if (docValues != null)
				currentReaderValues = docValues.getSource();
			else
				currentReaderValues = DocValues.getDefaultSource(org.apache.lucene.index.DocValues.Type.FLOAT_64);
			return this;
		}

		public void setBottom(int bottom)
		{
			this.bottom = values[bottom];
		}

		public Double value(int slot)
		{
			return Double.valueOf(values[slot]);
		}

		public int compareDocToValue(int doc, Double valueObj)
		{
			double value = valueObj.doubleValue();
			double docValue = currentReaderValues.getFloat(doc);
			if (docValue < value)
				return -1;
			return docValue <= value ? 0 : 1;
		}

		public volatile int compareDocToValue(int x0, Object x1)
			throws IOException
		{
			return compareDocToValue(x0, (Double)x1);
		}

		public volatile Object value(int x0)
		{
			return value(x0);
		}

		FloatDocValuesComparator(int numHits, String field)
		{
			values = new double[numHits];
			this.field = field;
		}
	}

	public static final class DoubleComparator extends NumericComparator
	{

		private final double values[];
		private final FieldCache.DoubleParser parser;
		private double currentReaderValues[];
		private double bottom;

		public int compare(int slot1, int slot2)
		{
			double v1 = values[slot1];
			double v2 = values[slot2];
			if (v1 > v2)
				return 1;
			return v1 >= v2 ? 0 : -1;
		}

		public int compareBottom(int doc)
		{
			double v2 = currentReaderValues[doc];
			if (docsWithField != null && v2 == 0.0D && !docsWithField.get(doc))
				v2 = ((Double)missingValue).doubleValue();
			if (bottom > v2)
				return 1;
			return bottom >= v2 ? 0 : -1;
		}

		public void copy(int slot, int doc)
		{
			double v2 = currentReaderValues[doc];
			if (docsWithField != null && v2 == 0.0D && !docsWithField.get(doc))
				v2 = ((Double)missingValue).doubleValue();
			values[slot] = v2;
		}

		public FieldComparator setNextReader(AtomicReaderContext context)
			throws IOException
		{
			currentReaderValues = FieldCache.DEFAULT.getDoubles(context.reader(), field, parser, missingValue != null);
			return super.setNextReader(context);
		}

		public void setBottom(int bottom)
		{
			this.bottom = values[bottom];
		}

		public Double value(int slot)
		{
			return Double.valueOf(values[slot]);
		}

		public int compareDocToValue(int doc, Double valueObj)
		{
			double value = valueObj.doubleValue();
			double docValue = currentReaderValues[doc];
			if (docsWithField != null && docValue == 0.0D && !docsWithField.get(doc))
				docValue = ((Double)missingValue).doubleValue();
			if (docValue < value)
				return -1;
			return docValue <= value ? 0 : 1;
		}

		public volatile int compareDocToValue(int x0, Object x1)
			throws IOException
		{
			return compareDocToValue(x0, (Double)x1);
		}

		public volatile Object value(int x0)
		{
			return value(x0);
		}

		DoubleComparator(int numHits, String field, FieldCache.Parser parser, Double missingValue)
		{
			super(field, missingValue);
			values = new double[numHits];
			this.parser = (FieldCache.DoubleParser)parser;
		}
	}

	public static final class ByteComparator extends NumericComparator
	{

		private final byte values[];
		private final FieldCache.ByteParser parser;
		private byte currentReaderValues[];
		private byte bottom;

		public int compare(int slot1, int slot2)
		{
			return values[slot1] - values[slot2];
		}

		public int compareBottom(int doc)
		{
			byte v2 = currentReaderValues[doc];
			if (docsWithField != null && v2 == 0 && !docsWithField.get(doc))
				v2 = ((Byte)missingValue).byteValue();
			return bottom - v2;
		}

		public void copy(int slot, int doc)
		{
			byte v2 = currentReaderValues[doc];
			if (docsWithField != null && v2 == 0 && !docsWithField.get(doc))
				v2 = ((Byte)missingValue).byteValue();
			values[slot] = v2;
		}

		public FieldComparator setNextReader(AtomicReaderContext context)
			throws IOException
		{
			currentReaderValues = FieldCache.DEFAULT.getBytes(context.reader(), field, parser, missingValue != null);
			return super.setNextReader(context);
		}

		public void setBottom(int bottom)
		{
			this.bottom = values[bottom];
		}

		public Byte value(int slot)
		{
			return Byte.valueOf(values[slot]);
		}

		public int compareDocToValue(int doc, Byte value)
		{
			byte docValue = currentReaderValues[doc];
			if (docsWithField != null && docValue == 0 && !docsWithField.get(doc))
				docValue = ((Byte)missingValue).byteValue();
			return docValue - value.byteValue();
		}

		public volatile int compareDocToValue(int x0, Object x1)
			throws IOException
		{
			return compareDocToValue(x0, (Byte)x1);
		}

		public volatile Object value(int x0)
		{
			return value(x0);
		}

		ByteComparator(int numHits, String field, FieldCache.Parser parser, Byte missingValue)
		{
			super(field, missingValue);
			values = new byte[numHits];
			this.parser = (FieldCache.ByteParser)parser;
		}
	}

	public static abstract class NumericComparator extends FieldComparator
	{

		protected final Number missingValue;
		protected final String field;
		protected Bits docsWithField;

		public FieldComparator setNextReader(AtomicReaderContext context)
			throws IOException
		{
			if (missingValue != null)
			{
				docsWithField = FieldCache.DEFAULT.getDocsWithField(context.reader(), field);
				if (docsWithField instanceof org.apache.lucene.util.Bits.MatchAllBits)
					docsWithField = null;
			} else
			{
				docsWithField = null;
			}
			return this;
		}

		public NumericComparator(String field, Number missingValue)
		{
			this.field = field;
			this.missingValue = missingValue;
		}
	}


	public FieldComparator()
	{
	}

	public abstract int compare(int i, int j);

	public abstract void setBottom(int i);

	public abstract int compareBottom(int i)
		throws IOException;

	public abstract void copy(int i, int j)
		throws IOException;

	public abstract FieldComparator setNextReader(AtomicReaderContext atomicreadercontext)
		throws IOException;

	public void setScorer(Scorer scorer1)
	{
	}

	public abstract Object value(int i);

	public int compareValues(Object first, Object second)
	{
		if (first == null)
			return second != null ? -1 : 0;
		if (second == null)
			return 1;
		else
			return ((Comparable)first).compareTo(second);
	}

	public abstract int compareDocToValue(int i, Object obj)
		throws IOException;

	protected static final int binarySearch(BytesRef br, FieldCache.DocTermsIndex a, BytesRef key)
	{
		return binarySearch(br, a, key, 1, a.numOrd() - 1);
	}

	protected static final int binarySearch(BytesRef br, FieldCache.DocTermsIndex a, BytesRef key, int low, int high)
	{
		while (low <= high) 
		{
			int mid = low + high >>> 1;
			BytesRef midVal = a.lookup(mid, br);
			int cmp;
			if (midVal != null)
				cmp = midVal.compareTo(key);
			else
				cmp = -1;
			if (cmp < 0)
				low = mid + 1;
			else
			if (cmp > 0)
				high = mid - 1;
			else
				return mid;
		}
		return -(low + 1);
	}
}
