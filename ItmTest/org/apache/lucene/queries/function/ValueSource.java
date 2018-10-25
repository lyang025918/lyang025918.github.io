// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ValueSource.java

package org.apache.lucene.queries.function;

import java.io.IOException;
import java.util.IdentityHashMap;
import java.util.Map;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.search.*;

// Referenced classes of package org.apache.lucene.queries.function:
//			FunctionValues

public abstract class ValueSource
{
	class ValueSourceComparator extends FieldComparator
	{

		private final double values[];
		private FunctionValues docVals;
		private double bottom;
		private final Map fcontext;
		final ValueSource this$0;

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
			double v2 = docVals.doubleVal(doc);
			if (bottom > v2)
				return 1;
			return bottom >= v2 ? 0 : -1;
		}

		public void copy(int slot, int doc)
		{
			values[slot] = docVals.doubleVal(doc);
		}

		public FieldComparator setNextReader(AtomicReaderContext context)
			throws IOException
		{
			docVals = getValues(fcontext, context);
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
			double docValue = docVals.doubleVal(doc);
			if (docValue < value)
				return -1;
			return docValue <= value ? 0 : -1;
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

		ValueSourceComparator(Map fcontext, int numHits)
		{
			this$0 = ValueSource.this;
			super();
			this.fcontext = fcontext;
			values = new double[numHits];
		}
	}

	class ValueSourceComparatorSource extends FieldComparatorSource
	{

		private final Map context;
		final ValueSource this$0;

		public FieldComparator newComparator(String fieldname, int numHits, int sortPos, boolean reversed)
			throws IOException
		{
			return new ValueSourceComparator(context, numHits);
		}

		public ValueSourceComparatorSource(Map context)
		{
			this$0 = ValueSource.this;
			super();
			this.context = context;
		}
	}

	class ValueSourceSortField extends SortField
	{

		final ValueSource this$0;

		public SortField rewrite(IndexSearcher searcher)
			throws IOException
		{
			Map context = ValueSource.newContext(searcher);
			createWeight(context, searcher);
			return new SortField(getField(), new ValueSourceComparatorSource(context), getReverse());
		}

		public ValueSourceSortField(boolean reverse)
		{
			this$0 = ValueSource.this;
			super(description(), org.apache.lucene.search.SortField.Type.REWRITEABLE, reverse);
		}
	}


	public ValueSource()
	{
	}

	public abstract FunctionValues getValues(Map map, AtomicReaderContext atomicreadercontext)
		throws IOException;

	public abstract boolean equals(Object obj);

	public abstract int hashCode();

	public abstract String description();

	public String toString()
	{
		return description();
	}

	public void createWeight(Map map, IndexSearcher indexsearcher)
		throws IOException
	{
	}

	public static Map newContext(IndexSearcher searcher)
	{
		Map context = new IdentityHashMap();
		context.put("searcher", searcher);
		return context;
	}

	public SortField getSortField(boolean reverse)
		throws IOException
	{
		return new ValueSourceSortField(reverse);
	}
}
