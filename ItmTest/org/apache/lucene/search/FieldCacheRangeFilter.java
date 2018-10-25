// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FieldCacheRangeFilter.java

package org.apache.lucene.search;

import java.io.IOException;
import org.apache.lucene.index.AtomicReader;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.util.*;

// Referenced classes of package org.apache.lucene.search:
//			Filter, FieldCache, DocIdSet, FieldCacheDocIdSet

public abstract class FieldCacheRangeFilter extends Filter
{

	final String field;
	final FieldCache.Parser parser;
	final Object lowerVal;
	final Object upperVal;
	final boolean includeLower;
	final boolean includeUpper;

	private FieldCacheRangeFilter(String field, FieldCache.Parser parser, Object lowerVal, Object upperVal, boolean includeLower, boolean includeUpper)
	{
		this.field = field;
		this.parser = parser;
		this.lowerVal = lowerVal;
		this.upperVal = upperVal;
		this.includeLower = includeLower;
		this.includeUpper = includeUpper;
	}

	public abstract DocIdSet getDocIdSet(AtomicReaderContext atomicreadercontext, Bits bits)
		throws IOException;

	public static FieldCacheRangeFilter newStringRange(String field, String lowerVal, String upperVal, boolean includeLower, boolean includeUpper)
	{
		return new FieldCacheRangeFilter(field, null, lowerVal, upperVal, includeLower, includeUpper) {

			static final boolean $assertionsDisabled = !org/apache/lucene/search/FieldCacheRangeFilter.desiredAssertionStatus();

			public DocIdSet getDocIdSet(AtomicReaderContext context, Bits acceptDocs)
				throws IOException
			{
				FieldCache.DocTermsIndex fcsi = FieldCache.DEFAULT.getTermsIndex(context.reader(), field);
				BytesRef spare = new BytesRef();
				int lowerPoint = fcsi.binarySearchLookup(lowerVal != null ? new BytesRef((CharSequence)lowerVal) : null, spare);
				int upperPoint = fcsi.binarySearchLookup(upperVal != null ? new BytesRef((CharSequence)upperVal) : null, spare);
				int inclusiveLowerPoint;
				if (lowerPoint == 0)
				{
					if (!$assertionsDisabled && lowerVal != null)
						throw new AssertionError();
					inclusiveLowerPoint = 1;
				} else
				if (includeLower && lowerPoint > 0)
					inclusiveLowerPoint = lowerPoint;
				else
				if (lowerPoint > 0)
					inclusiveLowerPoint = lowerPoint + 1;
				else
					inclusiveLowerPoint = Math.max(1, -lowerPoint - 1);
				int inclusiveUpperPoint;
				if (upperPoint == 0)
				{
					if (!$assertionsDisabled && upperVal != null)
						throw new AssertionError();
					inclusiveUpperPoint = 0x7fffffff;
				} else
				if (includeUpper && upperPoint > 0)
					inclusiveUpperPoint = upperPoint;
				else
				if (upperPoint > 0)
					inclusiveUpperPoint = upperPoint - 1;
				else
					inclusiveUpperPoint = -upperPoint - 2;
				if (inclusiveUpperPoint <= 0 || inclusiveLowerPoint > inclusiveUpperPoint)
					return DocIdSet.EMPTY_DOCIDSET;
				if (!$assertionsDisabled && (inclusiveLowerPoint <= 0 || inclusiveUpperPoint <= 0))
					throw new AssertionError();
				else
					return new FieldCacheDocIdSet(inclusiveLowerPoint, inclusiveUpperPoint) {

						final FieldCache.DocTermsIndex val$fcsi;
						final int val$inclusiveLowerPoint;
						final int val$inclusiveUpperPoint;
						final 1 this$0;

						protected final boolean matchDoc(int doc)
						{
							int docOrd = fcsi.getOrd(doc);
							return docOrd >= inclusiveLowerPoint && docOrd <= inclusiveUpperPoint;
						}

					
					{
						this$0 = 1.this;
						fcsi = doctermsindex;
						inclusiveLowerPoint = i;
						inclusiveUpperPoint = j;
						super(x0, x1);
					}
					};
			}


		};
	}

	public static FieldCacheRangeFilter newByteRange(String field, Byte lowerVal, Byte upperVal, boolean includeLower, boolean includeUpper)
	{
		return newByteRange(field, null, lowerVal, upperVal, includeLower, includeUpper);
	}

	public static FieldCacheRangeFilter newByteRange(String field, FieldCache.ByteParser parser, Byte lowerVal, Byte upperVal, boolean includeLower, boolean includeUpper)
	{
		return new FieldCacheRangeFilter(field, parser, lowerVal, upperVal, includeLower, includeUpper) {

			public DocIdSet getDocIdSet(AtomicReaderContext context, Bits acceptDocs)
				throws IOException
			{
				byte inclusiveLowerPoint;
				if (lowerVal != null)
				{
					byte i = ((Byte)lowerVal).byteValue();
					if (!includeLower && i == 127)
						return DocIdSet.EMPTY_DOCIDSET;
					inclusiveLowerPoint = (byte)(includeLower ? i : i + 1);
				} else
				{
					inclusiveLowerPoint = -128;
				}
				byte inclusiveUpperPoint;
				if (upperVal != null)
				{
					byte i = ((Byte)upperVal).byteValue();
					if (!includeUpper && i == -128)
						return DocIdSet.EMPTY_DOCIDSET;
					inclusiveUpperPoint = (byte)(includeUpper ? i : i - 1);
				} else
				{
					inclusiveUpperPoint = 127;
				}
				if (inclusiveLowerPoint > inclusiveUpperPoint)
				{
					return DocIdSet.EMPTY_DOCIDSET;
				} else
				{
					byte values[] = FieldCache.DEFAULT.getBytes(context.reader(), field, (FieldCache.ByteParser)parser, false);
					return new FieldCacheDocIdSet(inclusiveLowerPoint, inclusiveUpperPoint) {

						final byte val$values[];
						final byte val$inclusiveLowerPoint;
						final byte val$inclusiveUpperPoint;
						final 2 this$0;

						protected boolean matchDoc(int doc)
						{
							return values[doc] >= inclusiveLowerPoint && values[doc] <= inclusiveUpperPoint;
						}

					
					{
						this$0 = 2.this;
						values = abyte0;
						inclusiveLowerPoint = byte0;
						inclusiveUpperPoint = byte1;
						super(x0, x1);
					}
					};
				}
			}

		};
	}

	public static FieldCacheRangeFilter newShortRange(String field, Short lowerVal, Short upperVal, boolean includeLower, boolean includeUpper)
	{
		return newShortRange(field, null, lowerVal, upperVal, includeLower, includeUpper);
	}

	public static FieldCacheRangeFilter newShortRange(String field, FieldCache.ShortParser parser, Short lowerVal, Short upperVal, boolean includeLower, boolean includeUpper)
	{
		return new FieldCacheRangeFilter(field, parser, lowerVal, upperVal, includeLower, includeUpper) {

			public DocIdSet getDocIdSet(AtomicReaderContext context, Bits acceptDocs)
				throws IOException
			{
				short inclusiveLowerPoint;
				if (lowerVal != null)
				{
					short i = ((Short)lowerVal).shortValue();
					if (!includeLower && i == 32767)
						return DocIdSet.EMPTY_DOCIDSET;
					inclusiveLowerPoint = (short)(includeLower ? i : i + 1);
				} else
				{
					inclusiveLowerPoint = -32768;
				}
				short inclusiveUpperPoint;
				if (upperVal != null)
				{
					short i = ((Short)upperVal).shortValue();
					if (!includeUpper && i == -32768)
						return DocIdSet.EMPTY_DOCIDSET;
					inclusiveUpperPoint = (short)(includeUpper ? i : i - 1);
				} else
				{
					inclusiveUpperPoint = 32767;
				}
				if (inclusiveLowerPoint > inclusiveUpperPoint)
				{
					return DocIdSet.EMPTY_DOCIDSET;
				} else
				{
					short values[] = FieldCache.DEFAULT.getShorts(context.reader(), field, (FieldCache.ShortParser)parser, false);
					return new FieldCacheDocIdSet(inclusiveLowerPoint, inclusiveUpperPoint) {

						final short val$values[];
						final short val$inclusiveLowerPoint;
						final short val$inclusiveUpperPoint;
						final 3 this$0;

						protected boolean matchDoc(int doc)
						{
							return values[doc] >= inclusiveLowerPoint && values[doc] <= inclusiveUpperPoint;
						}

					
					{
						this$0 = 3.this;
						values = aword0;
						inclusiveLowerPoint = word0;
						inclusiveUpperPoint = word1;
						super(x0, x1);
					}
					};
				}
			}

		};
	}

	public static FieldCacheRangeFilter newIntRange(String field, Integer lowerVal, Integer upperVal, boolean includeLower, boolean includeUpper)
	{
		return newIntRange(field, null, lowerVal, upperVal, includeLower, includeUpper);
	}

	public static FieldCacheRangeFilter newIntRange(String field, FieldCache.IntParser parser, Integer lowerVal, Integer upperVal, boolean includeLower, boolean includeUpper)
	{
		return new FieldCacheRangeFilter(field, parser, lowerVal, upperVal, includeLower, includeUpper) {

			public DocIdSet getDocIdSet(AtomicReaderContext context, Bits acceptDocs)
				throws IOException
			{
				int inclusiveLowerPoint;
				if (lowerVal != null)
				{
					int i = ((Integer)lowerVal).intValue();
					if (!includeLower && i == 0x7fffffff)
						return DocIdSet.EMPTY_DOCIDSET;
					inclusiveLowerPoint = includeLower ? i : i + 1;
				} else
				{
					inclusiveLowerPoint = 0x80000000;
				}
				int inclusiveUpperPoint;
				if (upperVal != null)
				{
					int i = ((Integer)upperVal).intValue();
					if (!includeUpper && i == 0x80000000)
						return DocIdSet.EMPTY_DOCIDSET;
					inclusiveUpperPoint = includeUpper ? i : i - 1;
				} else
				{
					inclusiveUpperPoint = 0x7fffffff;
				}
				if (inclusiveLowerPoint > inclusiveUpperPoint)
				{
					return DocIdSet.EMPTY_DOCIDSET;
				} else
				{
					int values[] = FieldCache.DEFAULT.getInts(context.reader(), field, (FieldCache.IntParser)parser, false);
					return new FieldCacheDocIdSet(inclusiveLowerPoint, inclusiveUpperPoint) {

						final int val$values[];
						final int val$inclusiveLowerPoint;
						final int val$inclusiveUpperPoint;
						final 4 this$0;

						protected boolean matchDoc(int doc)
						{
							return values[doc] >= inclusiveLowerPoint && values[doc] <= inclusiveUpperPoint;
						}

					
					{
						this$0 = 4.this;
						values = ai;
						inclusiveLowerPoint = i;
						inclusiveUpperPoint = j;
						super(x0, x1);
					}
					};
				}
			}

		};
	}

	public static FieldCacheRangeFilter newLongRange(String field, Long lowerVal, Long upperVal, boolean includeLower, boolean includeUpper)
	{
		return newLongRange(field, null, lowerVal, upperVal, includeLower, includeUpper);
	}

	public static FieldCacheRangeFilter newLongRange(String field, FieldCache.LongParser parser, Long lowerVal, Long upperVal, boolean includeLower, boolean includeUpper)
	{
		return new FieldCacheRangeFilter(field, parser, lowerVal, upperVal, includeLower, includeUpper) {

			public DocIdSet getDocIdSet(AtomicReaderContext context, Bits acceptDocs)
				throws IOException
			{
				long inclusiveLowerPoint;
				if (lowerVal != null)
				{
					long i = ((Long)lowerVal).longValue();
					if (!includeLower && i == 0x7fffffffffffffffL)
						return DocIdSet.EMPTY_DOCIDSET;
					inclusiveLowerPoint = includeLower ? i : i + 1L;
				} else
				{
					inclusiveLowerPoint = 0x8000000000000000L;
				}
				long inclusiveUpperPoint;
				if (upperVal != null)
				{
					long i = ((Long)upperVal).longValue();
					if (!includeUpper && i == 0x8000000000000000L)
						return DocIdSet.EMPTY_DOCIDSET;
					inclusiveUpperPoint = includeUpper ? i : i - 1L;
				} else
				{
					inclusiveUpperPoint = 0x7fffffffffffffffL;
				}
				if (inclusiveLowerPoint > inclusiveUpperPoint)
				{
					return DocIdSet.EMPTY_DOCIDSET;
				} else
				{
					long values[] = FieldCache.DEFAULT.getLongs(context.reader(), field, (FieldCache.LongParser)parser, false);
					return new FieldCacheDocIdSet(inclusiveLowerPoint, inclusiveUpperPoint) {

						final long val$values[];
						final long val$inclusiveLowerPoint;
						final long val$inclusiveUpperPoint;
						final 5 this$0;

						protected boolean matchDoc(int doc)
						{
							return values[doc] >= inclusiveLowerPoint && values[doc] <= inclusiveUpperPoint;
						}

					
					{
						this$0 = 5.this;
						values = al;
						inclusiveLowerPoint = l;
						inclusiveUpperPoint = l1;
						super(x0, x1);
					}
					};
				}
			}

		};
	}

	public static FieldCacheRangeFilter newFloatRange(String field, Float lowerVal, Float upperVal, boolean includeLower, boolean includeUpper)
	{
		return newFloatRange(field, null, lowerVal, upperVal, includeLower, includeUpper);
	}

	public static FieldCacheRangeFilter newFloatRange(String field, FieldCache.FloatParser parser, Float lowerVal, Float upperVal, boolean includeLower, boolean includeUpper)
	{
		return new FieldCacheRangeFilter(field, parser, lowerVal, upperVal, includeLower, includeUpper) {

			public DocIdSet getDocIdSet(AtomicReaderContext context, Bits acceptDocs)
				throws IOException
			{
				float inclusiveLowerPoint;
				if (lowerVal != null)
				{
					float f = ((Float)lowerVal).floatValue();
					if (!includeUpper && f > 0.0F && Float.isInfinite(f))
						return DocIdSet.EMPTY_DOCIDSET;
					int i = NumericUtils.floatToSortableInt(f);
					inclusiveLowerPoint = NumericUtils.sortableIntToFloat(includeLower ? i : i + 1);
				} else
				{
					inclusiveLowerPoint = (-1.0F / 0.0F);
				}
				float inclusiveUpperPoint;
				if (upperVal != null)
				{
					float f = ((Float)upperVal).floatValue();
					if (!includeUpper && f < 0.0F && Float.isInfinite(f))
						return DocIdSet.EMPTY_DOCIDSET;
					int i = NumericUtils.floatToSortableInt(f);
					inclusiveUpperPoint = NumericUtils.sortableIntToFloat(includeUpper ? i : i - 1);
				} else
				{
					inclusiveUpperPoint = (1.0F / 0.0F);
				}
				if (inclusiveLowerPoint > inclusiveUpperPoint)
				{
					return DocIdSet.EMPTY_DOCIDSET;
				} else
				{
					float values[] = FieldCache.DEFAULT.getFloats(context.reader(), field, (FieldCache.FloatParser)parser, false);
					return new FieldCacheDocIdSet(inclusiveLowerPoint, inclusiveUpperPoint) {

						final float val$values[];
						final float val$inclusiveLowerPoint;
						final float val$inclusiveUpperPoint;
						final 6 this$0;

						protected boolean matchDoc(int doc)
						{
							return values[doc] >= inclusiveLowerPoint && values[doc] <= inclusiveUpperPoint;
						}

					
					{
						this$0 = 6.this;
						values = af;
						inclusiveLowerPoint = f;
						inclusiveUpperPoint = f1;
						super(x0, x1);
					}
					};
				}
			}

		};
	}

	public static FieldCacheRangeFilter newDoubleRange(String field, Double lowerVal, Double upperVal, boolean includeLower, boolean includeUpper)
	{
		return newDoubleRange(field, null, lowerVal, upperVal, includeLower, includeUpper);
	}

	public static FieldCacheRangeFilter newDoubleRange(String field, FieldCache.DoubleParser parser, Double lowerVal, Double upperVal, boolean includeLower, boolean includeUpper)
	{
		return new FieldCacheRangeFilter(field, parser, lowerVal, upperVal, includeLower, includeUpper) {

			public DocIdSet getDocIdSet(AtomicReaderContext context, Bits acceptDocs)
				throws IOException
			{
				double inclusiveLowerPoint;
				if (lowerVal != null)
				{
					double f = ((Double)lowerVal).doubleValue();
					if (!includeUpper && f > 0.0D && Double.isInfinite(f))
						return DocIdSet.EMPTY_DOCIDSET;
					long i = NumericUtils.doubleToSortableLong(f);
					inclusiveLowerPoint = NumericUtils.sortableLongToDouble(includeLower ? i : i + 1L);
				} else
				{
					inclusiveLowerPoint = (-1.0D / 0.0D);
				}
				double inclusiveUpperPoint;
				if (upperVal != null)
				{
					double f = ((Double)upperVal).doubleValue();
					if (!includeUpper && f < 0.0D && Double.isInfinite(f))
						return DocIdSet.EMPTY_DOCIDSET;
					long i = NumericUtils.doubleToSortableLong(f);
					inclusiveUpperPoint = NumericUtils.sortableLongToDouble(includeUpper ? i : i - 1L);
				} else
				{
					inclusiveUpperPoint = (1.0D / 0.0D);
				}
				if (inclusiveLowerPoint > inclusiveUpperPoint)
				{
					return DocIdSet.EMPTY_DOCIDSET;
				} else
				{
					double values[] = FieldCache.DEFAULT.getDoubles(context.reader(), field, (FieldCache.DoubleParser)parser, false);
					return new FieldCacheDocIdSet(inclusiveLowerPoint, inclusiveUpperPoint) {

						final double val$values[];
						final double val$inclusiveLowerPoint;
						final double val$inclusiveUpperPoint;
						final 7 this$0;

						protected boolean matchDoc(int doc)
						{
							return values[doc] >= inclusiveLowerPoint && values[doc] <= inclusiveUpperPoint;
						}

					
					{
						this$0 = 7.this;
						values = ad;
						inclusiveLowerPoint = d;
						inclusiveUpperPoint = d1;
						super(x0, x1);
					}
					};
				}
			}

		};
	}

	public final String toString()
	{
		StringBuilder sb = (new StringBuilder(field)).append(":");
		return sb.append(includeLower ? '[' : '{').append(lowerVal != null ? lowerVal.toString() : "*").append(" TO ").append(upperVal != null ? upperVal.toString() : "*").append(includeUpper ? ']' : '}').toString();
	}

	public final boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (!(o instanceof FieldCacheRangeFilter))
			return false;
		FieldCacheRangeFilter other = (FieldCacheRangeFilter)o;
		if (!field.equals(other.field) || includeLower != other.includeLower || includeUpper != other.includeUpper)
			return false;
		if (lowerVal == null ? other.lowerVal != null : !lowerVal.equals(other.lowerVal))
			return false;
		if (upperVal == null ? other.upperVal != null : !upperVal.equals(other.upperVal))
			return false;
		return parser == null ? other.parser == null : parser.equals(other.parser);
	}

	public final int hashCode()
	{
		int h = field.hashCode();
		h ^= lowerVal == null ? 0x20cdc4ec : lowerVal.hashCode();
		h = h << 1 | h >>> 31;
		h ^= upperVal == null ? 0x9c326fdd : upperVal.hashCode();
		h ^= parser == null ? 0xa2463494 : parser.hashCode();
		h ^= (includeLower ? 0x5c586ea0 : 0xea3df636) ^ (includeUpper ? 0x6695b902 : 0x742608b5);
		return h;
	}

	public String getField()
	{
		return field;
	}

	public boolean includesLower()
	{
		return includeLower;
	}

	public boolean includesUpper()
	{
		return includeUpper;
	}

	public Object getLowerVal()
	{
		return lowerVal;
	}

	public Object getUpperVal()
	{
		return upperVal;
	}

	public FieldCache.Parser getParser()
	{
		return parser;
	}

}
