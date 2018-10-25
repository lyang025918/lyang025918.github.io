// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NumericRangeQuery.java

package org.apache.lucene.search;

import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.*;
import org.apache.lucene.util.*;

// Referenced classes of package org.apache.lucene.search:
//			MultiTermQuery

public final class NumericRangeQuery extends MultiTermQuery
{
	private final class NumericRangeTermsEnum extends FilteredTermsEnum
	{

		private BytesRef currentLowerBound;
		private BytesRef currentUpperBound;
		private final LinkedList rangeBounds;
		private final Comparator termComp = getComparator();
		static final boolean $assertionsDisabled = !org/apache/lucene/search/NumericRangeQuery.desiredAssertionStatus();
		final NumericRangeQuery this$0;

		private void nextRange()
		{
			if (!$assertionsDisabled && rangeBounds.size() % 2 != 0)
				throw new AssertionError();
			currentLowerBound = (BytesRef)rangeBounds.removeFirst();
			if (!$assertionsDisabled && currentUpperBound != null && termComp.compare(currentUpperBound, currentLowerBound) > 0)
			{
				throw new AssertionError("The current upper bound must be <= the new lower bound");
			} else
			{
				currentUpperBound = (BytesRef)rangeBounds.removeFirst();
				return;
			}
		}

		protected final BytesRef nextSeekTerm(BytesRef term)
		{
			while (rangeBounds.size() >= 2) 
			{
				nextRange();
				if (term == null || termComp.compare(term, currentUpperBound) <= 0)
					return term == null || termComp.compare(term, currentLowerBound) <= 0 ? currentLowerBound : term;
			}
			if (!$assertionsDisabled && !rangeBounds.isEmpty())
			{
				throw new AssertionError();
			} else
			{
				currentLowerBound = currentUpperBound = null;
				return null;
			}
		}

		protected final org.apache.lucene.index.FilteredTermsEnum.AcceptStatus accept(BytesRef term)
		{
			for (; currentUpperBound == null || termComp.compare(term, currentUpperBound) > 0; nextRange())
			{
				if (rangeBounds.isEmpty())
					return org.apache.lucene.index.FilteredTermsEnum.AcceptStatus.END;
				if (termComp.compare(term, rangeBounds.getFirst()) < 0)
					return org.apache.lucene.index.FilteredTermsEnum.AcceptStatus.NO_AND_SEEK;
			}

			return org.apache.lucene.index.FilteredTermsEnum.AcceptStatus.YES;
		}



		NumericRangeTermsEnum(TermsEnum tenum)
		{
label0:
			{
				this$0 = NumericRangeQuery.this;
				super(tenum);
				rangeBounds = new LinkedList();
				switch (1..SwitchMap.org.apache.lucene.document.FieldType.NumericType[dataType.ordinal()])
				{
				case 1: // '\001'
				case 2: // '\002'
				{
					long minBound;
					if (dataType == org.apache.lucene.document.FieldType.NumericType.LONG)
					{
						minBound = min != null ? min.longValue() : 0x8000000000000000L;
					} else
					{
						if (!$assertionsDisabled && dataType != org.apache.lucene.document.FieldType.NumericType.DOUBLE)
							throw new AssertionError();
						minBound = min != null ? NumericUtils.doubleToSortableLong(min.doubleValue()) : NumericRangeQuery.LONG_NEGATIVE_INFINITY;
					}
					if (!minInclusive && min != null)
					{
						if (minBound == 0x7fffffffffffffffL)
							break label0;
						minBound++;
					}
					long maxBound;
					if (dataType == org.apache.lucene.document.FieldType.NumericType.LONG)
					{
						maxBound = max != null ? max.longValue() : 0x7fffffffffffffffL;
					} else
					{
						if (!$assertionsDisabled && dataType != org.apache.lucene.document.FieldType.NumericType.DOUBLE)
							throw new AssertionError();
						maxBound = max != null ? NumericUtils.doubleToSortableLong(max.doubleValue()) : NumericRangeQuery.LONG_POSITIVE_INFINITY;
					}
					if (!maxInclusive && max != null)
					{
						if (maxBound == 0x8000000000000000L)
							break label0;
						maxBound--;
					}
					NumericUtils.splitLongRange(new org.apache.lucene.util.NumericUtils.LongRangeBuilder() {

						final NumericRangeQuery val$this$0;
						final NumericRangeTermsEnum this$1;

						public final void addRange(BytesRef minPrefixCoded, BytesRef maxPrefixCoded)
						{
							rangeBounds.add(minPrefixCoded);
							rangeBounds.add(maxPrefixCoded);
						}


// JavaClassFileOutputException: Invalid index accessing method local variables table of <init>
					}, precisionStep, minBound, maxBound);
					break label0;
				}

				case 3: // '\003'
				case 4: // '\004'
				{
					int minBound;
					if (dataType == org.apache.lucene.document.FieldType.NumericType.INT)
					{
						minBound = min != null ? min.intValue() : 0x80000000;
					} else
					{
						if (!$assertionsDisabled && dataType != org.apache.lucene.document.FieldType.NumericType.FLOAT)
							throw new AssertionError();
						minBound = min != null ? NumericUtils.floatToSortableInt(min.floatValue()) : NumericRangeQuery.INT_NEGATIVE_INFINITY;
					}
					if (!minInclusive && min != null)
					{
						if (minBound == 0x7fffffff)
							break label0;
						minBound++;
					}
					int maxBound;
					if (dataType == org.apache.lucene.document.FieldType.NumericType.INT)
					{
						maxBound = max != null ? max.intValue() : 0x7fffffff;
					} else
					{
						if (!$assertionsDisabled && dataType != org.apache.lucene.document.FieldType.NumericType.FLOAT)
							throw new AssertionError();
						maxBound = max != null ? NumericUtils.floatToSortableInt(max.floatValue()) : NumericRangeQuery.INT_POSITIVE_INFINITY;
					}
					if (!maxInclusive && max != null)
					{
						if (maxBound == 0x80000000)
							break label0;
						maxBound--;
					}
					NumericUtils.splitIntRange(new org.apache.lucene.util.NumericUtils.IntRangeBuilder() {

						final NumericRangeQuery val$this$0;
						final NumericRangeTermsEnum this$1;

						public final void addRange(BytesRef minPrefixCoded, BytesRef maxPrefixCoded)
						{
							rangeBounds.add(minPrefixCoded);
							rangeBounds.add(maxPrefixCoded);
						}


// JavaClassFileOutputException: Invalid index accessing method local variables table of <init>
					}, precisionStep, minBound, maxBound);
					break;
				}

				default:
				{
					throw new IllegalArgumentException("Invalid NumericType");
				}
				}
			}
		}
	}


	final int precisionStep;
	final org.apache.lucene.document.FieldType.NumericType dataType;
	final Number min;
	final Number max;
	final boolean minInclusive;
	final boolean maxInclusive;
	static final long LONG_NEGATIVE_INFINITY = NumericUtils.doubleToSortableLong((-1.0D / 0.0D));
	static final long LONG_POSITIVE_INFINITY = NumericUtils.doubleToSortableLong((1.0D / 0.0D));
	static final int INT_NEGATIVE_INFINITY = NumericUtils.floatToSortableInt((-1.0F / 0.0F));
	static final int INT_POSITIVE_INFINITY = NumericUtils.floatToSortableInt((1.0F / 0.0F));

	private NumericRangeQuery(String field, int precisionStep, org.apache.lucene.document.FieldType.NumericType dataType, Number min, Number max, boolean minInclusive, boolean maxInclusive)
	{
		super(field);
		if (precisionStep < 1)
		{
			throw new IllegalArgumentException("precisionStep must be >=1");
		} else
		{
			this.precisionStep = precisionStep;
			this.dataType = dataType;
			this.min = min;
			this.max = max;
			this.minInclusive = minInclusive;
			this.maxInclusive = maxInclusive;
			return;
		}
	}

	public static NumericRangeQuery newLongRange(String field, int precisionStep, Long min, Long max, boolean minInclusive, boolean maxInclusive)
	{
		return new NumericRangeQuery(field, precisionStep, org.apache.lucene.document.FieldType.NumericType.LONG, min, max, minInclusive, maxInclusive);
	}

	public static NumericRangeQuery newLongRange(String field, Long min, Long max, boolean minInclusive, boolean maxInclusive)
	{
		return new NumericRangeQuery(field, 4, org.apache.lucene.document.FieldType.NumericType.LONG, min, max, minInclusive, maxInclusive);
	}

	public static NumericRangeQuery newIntRange(String field, int precisionStep, Integer min, Integer max, boolean minInclusive, boolean maxInclusive)
	{
		return new NumericRangeQuery(field, precisionStep, org.apache.lucene.document.FieldType.NumericType.INT, min, max, minInclusive, maxInclusive);
	}

	public static NumericRangeQuery newIntRange(String field, Integer min, Integer max, boolean minInclusive, boolean maxInclusive)
	{
		return new NumericRangeQuery(field, 4, org.apache.lucene.document.FieldType.NumericType.INT, min, max, minInclusive, maxInclusive);
	}

	public static NumericRangeQuery newDoubleRange(String field, int precisionStep, Double min, Double max, boolean minInclusive, boolean maxInclusive)
	{
		return new NumericRangeQuery(field, precisionStep, org.apache.lucene.document.FieldType.NumericType.DOUBLE, min, max, minInclusive, maxInclusive);
	}

	public static NumericRangeQuery newDoubleRange(String field, Double min, Double max, boolean minInclusive, boolean maxInclusive)
	{
		return new NumericRangeQuery(field, 4, org.apache.lucene.document.FieldType.NumericType.DOUBLE, min, max, minInclusive, maxInclusive);
	}

	public static NumericRangeQuery newFloatRange(String field, int precisionStep, Float min, Float max, boolean minInclusive, boolean maxInclusive)
	{
		return new NumericRangeQuery(field, precisionStep, org.apache.lucene.document.FieldType.NumericType.FLOAT, min, max, minInclusive, maxInclusive);
	}

	public static NumericRangeQuery newFloatRange(String field, Float min, Float max, boolean minInclusive, boolean maxInclusive)
	{
		return new NumericRangeQuery(field, 4, org.apache.lucene.document.FieldType.NumericType.FLOAT, min, max, minInclusive, maxInclusive);
	}

	protected TermsEnum getTermsEnum(Terms terms, AttributeSource atts)
		throws IOException
	{
		if (min != null && max != null && ((Comparable)min).compareTo(max) > 0)
			return TermsEnum.EMPTY;
		else
			return new NumericRangeTermsEnum(terms.iterator(null));
	}

	public boolean includesMin()
	{
		return minInclusive;
	}

	public boolean includesMax()
	{
		return maxInclusive;
	}

	public Number getMin()
	{
		return min;
	}

	public Number getMax()
	{
		return max;
	}

	public int getPrecisionStep()
	{
		return precisionStep;
	}

	public String toString(String field)
	{
		StringBuilder sb = new StringBuilder();
		if (!getField().equals(field))
			sb.append(getField()).append(':');
		return sb.append(minInclusive ? '[' : '{').append(min != null ? min.toString() : "*").append(" TO ").append(max != null ? max.toString() : "*").append(maxInclusive ? ']' : '}').append(ToStringUtils.boost(getBoost())).toString();
	}

	public final boolean equals(Object o)
	{
		if (o == this)
			return true;
		if (!super.equals(o))
			return false;
		if (o instanceof NumericRangeQuery)
		{
			NumericRangeQuery q = (NumericRangeQuery)o;
			return (q.min != null ? q.min.equals(min) : min == null) && (q.max != null ? q.max.equals(max) : max == null) && (minInclusive == q.minInclusive && maxInclusive == q.maxInclusive && precisionStep == q.precisionStep);
		} else
		{
			return false;
		}
	}

	public final int hashCode()
	{
		int hash = super.hashCode();
		hash += precisionStep ^ 0x64365465;
		if (min != null)
			hash += min.hashCode() ^ 0x14fa55fb;
		if (max != null)
			hash += max.hashCode() ^ 0x733fa5fe;
		return hash + (Boolean.valueOf(minInclusive).hashCode() ^ 0x14fa55fb) + (Boolean.valueOf(maxInclusive).hashCode() ^ 0x733fa5fe);
	}


	// Unreferenced inner class org/apache/lucene/search/NumericRangeQuery$1

/* anonymous class */
	static class 1
	{

		static final int $SwitchMap$org$apache$lucene$document$FieldType$NumericType[];

		static 
		{
			$SwitchMap$org$apache$lucene$document$FieldType$NumericType = new int[org.apache.lucene.document.FieldType.NumericType.values().length];
			try
			{
				$SwitchMap$org$apache$lucene$document$FieldType$NumericType[org.apache.lucene.document.FieldType.NumericType.LONG.ordinal()] = 1;
			}
			catch (NoSuchFieldError ex) { }
			try
			{
				$SwitchMap$org$apache$lucene$document$FieldType$NumericType[org.apache.lucene.document.FieldType.NumericType.DOUBLE.ordinal()] = 2;
			}
			catch (NoSuchFieldError ex) { }
			try
			{
				$SwitchMap$org$apache$lucene$document$FieldType$NumericType[org.apache.lucene.document.FieldType.NumericType.INT.ordinal()] = 3;
			}
			catch (NoSuchFieldError ex) { }
			try
			{
				$SwitchMap$org$apache$lucene$document$FieldType$NumericType[org.apache.lucene.document.FieldType.NumericType.FLOAT.ordinal()] = 4;
			}
			catch (NoSuchFieldError ex) { }
		}
	}

}
