// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NumericRangeFilter.java

package org.apache.lucene.search;


// Referenced classes of package org.apache.lucene.search:
//			MultiTermQueryWrapperFilter, NumericRangeQuery

public final class NumericRangeFilter extends MultiTermQueryWrapperFilter
{

	private NumericRangeFilter(NumericRangeQuery query)
	{
		super(query);
	}

	public static NumericRangeFilter newLongRange(String field, int precisionStep, Long min, Long max, boolean minInclusive, boolean maxInclusive)
	{
		return new NumericRangeFilter(NumericRangeQuery.newLongRange(field, precisionStep, min, max, minInclusive, maxInclusive));
	}

	public static NumericRangeFilter newLongRange(String field, Long min, Long max, boolean minInclusive, boolean maxInclusive)
	{
		return new NumericRangeFilter(NumericRangeQuery.newLongRange(field, min, max, minInclusive, maxInclusive));
	}

	public static NumericRangeFilter newIntRange(String field, int precisionStep, Integer min, Integer max, boolean minInclusive, boolean maxInclusive)
	{
		return new NumericRangeFilter(NumericRangeQuery.newIntRange(field, precisionStep, min, max, minInclusive, maxInclusive));
	}

	public static NumericRangeFilter newIntRange(String field, Integer min, Integer max, boolean minInclusive, boolean maxInclusive)
	{
		return new NumericRangeFilter(NumericRangeQuery.newIntRange(field, min, max, minInclusive, maxInclusive));
	}

	public static NumericRangeFilter newDoubleRange(String field, int precisionStep, Double min, Double max, boolean minInclusive, boolean maxInclusive)
	{
		return new NumericRangeFilter(NumericRangeQuery.newDoubleRange(field, precisionStep, min, max, minInclusive, maxInclusive));
	}

	public static NumericRangeFilter newDoubleRange(String field, Double min, Double max, boolean minInclusive, boolean maxInclusive)
	{
		return new NumericRangeFilter(NumericRangeQuery.newDoubleRange(field, min, max, minInclusive, maxInclusive));
	}

	public static NumericRangeFilter newFloatRange(String field, int precisionStep, Float min, Float max, boolean minInclusive, boolean maxInclusive)
	{
		return new NumericRangeFilter(NumericRangeQuery.newFloatRange(field, precisionStep, min, max, minInclusive, maxInclusive));
	}

	public static NumericRangeFilter newFloatRange(String field, Float min, Float max, boolean minInclusive, boolean maxInclusive)
	{
		return new NumericRangeFilter(NumericRangeQuery.newFloatRange(field, min, max, minInclusive, maxInclusive));
	}

	public boolean includesMin()
	{
		return ((NumericRangeQuery)query).includesMin();
	}

	public boolean includesMax()
	{
		return ((NumericRangeQuery)query).includesMax();
	}

	public Number getMin()
	{
		return ((NumericRangeQuery)query).getMin();
	}

	public Number getMax()
	{
		return ((NumericRangeQuery)query).getMax();
	}

	public int getPrecisionStep()
	{
		return ((NumericRangeQuery)query).getPrecisionStep();
	}
}
