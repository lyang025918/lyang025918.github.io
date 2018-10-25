// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NumericUtils.java

package org.apache.lucene.util;


// Referenced classes of package org.apache.lucene.util:
//			BytesRef

public final class NumericUtils
{
	public static abstract class IntRangeBuilder
	{

		public void addRange(BytesRef minPrefixCoded, BytesRef maxPrefixCoded)
		{
			throw new UnsupportedOperationException();
		}

		public void addRange(int min, int max, int shift)
		{
			BytesRef minBytes = new BytesRef(6);
			BytesRef maxBytes = new BytesRef(6);
			NumericUtils.intToPrefixCoded(min, shift, minBytes);
			NumericUtils.intToPrefixCoded(max, shift, maxBytes);
			addRange(minBytes, maxBytes);
		}

		public IntRangeBuilder()
		{
		}
	}

	public static abstract class LongRangeBuilder
	{

		public void addRange(BytesRef minPrefixCoded, BytesRef maxPrefixCoded)
		{
			throw new UnsupportedOperationException();
		}

		public void addRange(long min, long max, int shift)
		{
			BytesRef minBytes = new BytesRef(11);
			BytesRef maxBytes = new BytesRef(11);
			NumericUtils.longToPrefixCoded(min, shift, minBytes);
			NumericUtils.longToPrefixCoded(max, shift, maxBytes);
			addRange(minBytes, maxBytes);
		}

		public LongRangeBuilder()
		{
		}
	}


	public static final int PRECISION_STEP_DEFAULT = 4;
	public static final byte SHIFT_START_LONG = 32;
	public static final int BUF_SIZE_LONG = 11;
	public static final byte SHIFT_START_INT = 96;
	public static final int BUF_SIZE_INT = 6;

	private NumericUtils()
	{
	}

	public static int longToPrefixCoded(long val, int shift, BytesRef bytes)
	{
		if (shift > 63 || shift < 0)
			throw new IllegalArgumentException("Illegal shift value, must be 0..63");
		int nChars = (63 - shift) / 7 + 1;
		bytes.offset = 0;
		bytes.length = nChars + 1;
		if (bytes.bytes.length < bytes.length)
			bytes.grow(11);
		int hash;
		bytes.bytes[0] = (byte)(hash = 32 + shift);
		long sortableBits = val ^ 0x8000000000000000L;
		for (sortableBits >>>= shift; nChars > 0; sortableBits >>>= 7)
			bytes.bytes[nChars--] = (byte)(int)(sortableBits & 127L);

		for (int i = 1; i < bytes.length; i++)
			hash = 31 * hash + bytes.bytes[i];

		return hash;
	}

	public static int intToPrefixCoded(int val, int shift, BytesRef bytes)
	{
		if (shift > 31 || shift < 0)
			throw new IllegalArgumentException("Illegal shift value, must be 0..31");
		int nChars = (31 - shift) / 7 + 1;
		bytes.offset = 0;
		bytes.length = nChars + 1;
		if (bytes.bytes.length < bytes.length)
			bytes.grow(6);
		int hash;
		bytes.bytes[0] = (byte)(hash = 96 + shift);
		int sortableBits = val ^ 0x80000000;
		for (sortableBits >>>= shift; nChars > 0; sortableBits >>>= 7)
			bytes.bytes[nChars--] = (byte)(sortableBits & 0x7f);

		for (int i = 1; i < bytes.length; i++)
			hash = 31 * hash + bytes.bytes[i];

		return hash;
	}

	public static int getPrefixCodedLongShift(BytesRef val)
	{
		int shift = val.bytes[val.offset] - 32;
		if (shift > 63 || shift < 0)
			throw new NumberFormatException((new StringBuilder()).append("Invalid shift value (").append(shift).append(") in prefixCoded bytes (is encoded value really an INT?)").toString());
		else
			return shift;
	}

	public static int getPrefixCodedIntShift(BytesRef val)
	{
		int shift = val.bytes[val.offset] - 96;
		if (shift > 31 || shift < 0)
			throw new NumberFormatException("Invalid shift value in prefixCoded bytes (is encoded value really an INT?)");
		else
			return shift;
	}

	public static long prefixCodedToLong(BytesRef val)
	{
		long sortableBits = 0L;
		int i = val.offset + 1;
		for (int limit = val.offset + val.length; i < limit; i++)
		{
			sortableBits <<= 7;
			byte b = val.bytes[i];
			if (b < 0)
				throw new NumberFormatException((new StringBuilder()).append("Invalid prefixCoded numerical value representation (byte ").append(Integer.toHexString(b & 0xff)).append(" at position ").append(i - val.offset).append(" is invalid)").toString());
			sortableBits |= b;
		}

		return sortableBits << getPrefixCodedLongShift(val) ^ 0x8000000000000000L;
	}

	public static int prefixCodedToInt(BytesRef val)
	{
		int sortableBits = 0;
		int i = val.offset + 1;
		for (int limit = val.offset + val.length; i < limit; i++)
		{
			sortableBits <<= 7;
			byte b = val.bytes[i];
			if (b < 0)
				throw new NumberFormatException((new StringBuilder()).append("Invalid prefixCoded numerical value representation (byte ").append(Integer.toHexString(b & 0xff)).append(" at position ").append(i - val.offset).append(" is invalid)").toString());
			sortableBits |= b;
		}

		return sortableBits << getPrefixCodedIntShift(val) ^ 0x80000000;
	}

	public static long doubleToSortableLong(double val)
	{
		long f = Double.doubleToLongBits(val);
		if (f < 0L)
			f ^= 0x7fffffffffffffffL;
		return f;
	}

	public static double sortableLongToDouble(long val)
	{
		if (val < 0L)
			val ^= 0x7fffffffffffffffL;
		return Double.longBitsToDouble(val);
	}

	public static int floatToSortableInt(float val)
	{
		int f = Float.floatToIntBits(val);
		if (f < 0)
			f ^= 0x7fffffff;
		return f;
	}

	public static float sortableIntToFloat(int val)
	{
		if (val < 0)
			val ^= 0x7fffffff;
		return Float.intBitsToFloat(val);
	}

	public static void splitLongRange(LongRangeBuilder builder, int precisionStep, long minBound, long maxBound)
	{
		splitRange(builder, 64, precisionStep, minBound, maxBound);
	}

	public static void splitIntRange(IntRangeBuilder builder, int precisionStep, int minBound, int maxBound)
	{
		splitRange(builder, 32, precisionStep, minBound, maxBound);
	}

	private static void splitRange(Object builder, int valSize, int precisionStep, long minBound, long maxBound)
	{
		if (precisionStep < 1)
			throw new IllegalArgumentException("precisionStep must be >=1");
		if (minBound > maxBound)
			return;
		int shift = 0;
		do
		{
			long diff = 1L << shift + precisionStep;
			long mask = (1L << precisionStep) - 1L << shift;
			boolean hasLower = (minBound & mask) != 0L;
			boolean hasUpper = (maxBound & mask) != mask;
			long nextMinBound = (hasLower ? minBound + diff : minBound) & ~mask;
			long nextMaxBound = (hasUpper ? maxBound - diff : maxBound) & ~mask;
			boolean lowerWrapped = nextMinBound < minBound;
			boolean upperWrapped = nextMaxBound > maxBound;
			if (shift + precisionStep >= valSize || nextMinBound > nextMaxBound || lowerWrapped || upperWrapped)
			{
				addRange(builder, valSize, minBound, maxBound, shift);
				break;
			}
			if (hasLower)
				addRange(builder, valSize, minBound, minBound | mask, shift);
			if (hasUpper)
				addRange(builder, valSize, maxBound & ~mask, maxBound, shift);
			minBound = nextMinBound;
			maxBound = nextMaxBound;
			shift += precisionStep;
		} while (true);
	}

	private static void addRange(Object builder, int valSize, long minBound, long maxBound, int shift)
	{
		maxBound |= (1L << shift) - 1L;
		switch (valSize)
		{
		case 64: // '@'
			((LongRangeBuilder)builder).addRange(minBound, maxBound, shift);
			break;

		case 32: // ' '
			((IntRangeBuilder)builder).addRange((int)minBound, (int)maxBound, shift);
			break;

		default:
			throw new IllegalArgumentException("valSize must be 32 or 64.");
		}
	}
}
