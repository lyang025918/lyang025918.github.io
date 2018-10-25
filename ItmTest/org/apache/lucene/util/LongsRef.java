// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LongsRef.java

package org.apache.lucene.util;


// Referenced classes of package org.apache.lucene.util:
//			ArrayUtil

public final class LongsRef
	implements Comparable, Cloneable
{

	public static final long EMPTY_LONGS[] = new long[0];
	public long longs[];
	public int offset;
	public int length;
	static final boolean $assertionsDisabled = !org/apache/lucene/util/LongsRef.desiredAssertionStatus();

	public LongsRef()
	{
		longs = EMPTY_LONGS;
	}

	public LongsRef(int capacity)
	{
		longs = new long[capacity];
	}

	public LongsRef(long longs[], int offset, int length)
	{
		if (!$assertionsDisabled && longs == null)
			throw new AssertionError();
		if (!$assertionsDisabled && offset < 0)
			throw new AssertionError();
		if (!$assertionsDisabled && length < 0)
			throw new AssertionError();
		if (!$assertionsDisabled && longs.length < offset + length)
		{
			throw new AssertionError();
		} else
		{
			this.longs = longs;
			this.offset = offset;
			this.length = length;
			return;
		}
	}

	public LongsRef clone()
	{
		return new LongsRef(longs, offset, length);
	}

	public int hashCode()
	{
		int prime = 31;
		int result = 0;
		long end = offset + length;
		for (int i = offset; (long)i < end; i++)
			result = 31 * result + (int)(longs[i] ^ longs[i] >>> 32);

		return result;
	}

	public boolean equals(Object other)
	{
		if (other == null)
			return false;
		if (other instanceof LongsRef)
			return longsEquals((LongsRef)other);
		else
			return false;
	}

	public boolean longsEquals(LongsRef other)
	{
		if (length == other.length)
		{
			int otherUpto = other.offset;
			long otherInts[] = other.longs;
			long end = offset + length;
			for (int upto = offset; (long)upto < end;)
			{
				if (longs[upto] != otherInts[otherUpto])
					return false;
				upto++;
				otherUpto++;
			}

			return true;
		} else
		{
			return false;
		}
	}

	public int compareTo(LongsRef other)
	{
		if (this == other)
			return 0;
		long aInts[] = longs;
		int aUpto = offset;
		long bInts[] = other.longs;
		int bUpto = other.offset;
		for (long aStop = aUpto + Math.min(length, other.length); (long)aUpto < aStop;)
		{
			long aInt = aInts[aUpto++];
			long bInt = bInts[bUpto++];
			if (aInt > bInt)
				return 1;
			if (aInt < bInt)
				return -1;
		}

		return length - other.length;
	}

	public void copyLongs(LongsRef other)
	{
		if (longs.length - offset < other.length)
		{
			longs = new long[other.length];
			offset = 0;
		}
		System.arraycopy(other.longs, other.offset, longs, offset, other.length);
		length = other.length;
	}

	public void grow(int newLength)
	{
		if (!$assertionsDisabled && offset != 0)
			throw new AssertionError();
		if (longs.length < newLength)
			longs = ArrayUtil.grow(longs, newLength);
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		long end = offset + length;
		for (int i = offset; (long)i < end; i++)
		{
			if (i > offset)
				sb.append(' ');
			sb.append(Long.toHexString(longs[i]));
		}

		sb.append(']');
		return sb.toString();
	}

	public static LongsRef deepCopyOf(LongsRef other)
	{
		LongsRef clone = new LongsRef();
		clone.copyLongs(other);
		return clone;
	}

	public volatile Object clone()
		throws CloneNotSupportedException
	{
		return clone();
	}

	public volatile int compareTo(Object x0)
	{
		return compareTo((LongsRef)x0);
	}

}
