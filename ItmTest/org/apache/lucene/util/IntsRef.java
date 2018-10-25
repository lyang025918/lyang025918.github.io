// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IntsRef.java

package org.apache.lucene.util;


// Referenced classes of package org.apache.lucene.util:
//			ArrayUtil

public final class IntsRef
	implements Comparable, Cloneable
{

	public static final int EMPTY_INTS[] = new int[0];
	public int ints[];
	public int offset;
	public int length;
	static final boolean $assertionsDisabled = !org/apache/lucene/util/IntsRef.desiredAssertionStatus();

	public IntsRef()
	{
		ints = EMPTY_INTS;
	}

	public IntsRef(int capacity)
	{
		ints = new int[capacity];
	}

	public IntsRef(int ints[], int offset, int length)
	{
		if (!$assertionsDisabled && ints == null)
			throw new AssertionError();
		if (!$assertionsDisabled && offset < 0)
			throw new AssertionError();
		if (!$assertionsDisabled && length < 0)
			throw new AssertionError();
		if (!$assertionsDisabled && ints.length < offset + length)
		{
			throw new AssertionError();
		} else
		{
			this.ints = ints;
			this.offset = offset;
			this.length = length;
			return;
		}
	}

	public IntsRef clone()
	{
		return new IntsRef(ints, offset, length);
	}

	public int hashCode()
	{
		int prime = 31;
		int result = 0;
		int end = offset + length;
		for (int i = offset; i < end; i++)
			result = 31 * result + ints[i];

		return result;
	}

	public boolean equals(Object other)
	{
		if (other == null)
			return false;
		if (other instanceof IntsRef)
			return intsEquals((IntsRef)other);
		else
			return false;
	}

	public boolean intsEquals(IntsRef other)
	{
		if (length == other.length)
		{
			int otherUpto = other.offset;
			int otherInts[] = other.ints;
			int end = offset + length;
			for (int upto = offset; upto < end;)
			{
				if (ints[upto] != otherInts[otherUpto])
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

	public int compareTo(IntsRef other)
	{
		if (this == other)
			return 0;
		int aInts[] = ints;
		int aUpto = offset;
		int bInts[] = other.ints;
		int bUpto = other.offset;
		for (int aStop = aUpto + Math.min(length, other.length); aUpto < aStop;)
		{
			int aInt = aInts[aUpto++];
			int bInt = bInts[bUpto++];
			if (aInt > bInt)
				return 1;
			if (aInt < bInt)
				return -1;
		}

		return length - other.length;
	}

	public void copyInts(IntsRef other)
	{
		if (ints.length - offset < other.length)
		{
			ints = new int[other.length];
			offset = 0;
		}
		System.arraycopy(other.ints, other.offset, ints, offset, other.length);
		length = other.length;
	}

	public void grow(int newLength)
	{
		if (!$assertionsDisabled && offset != 0)
			throw new AssertionError();
		if (ints.length < newLength)
			ints = ArrayUtil.grow(ints, newLength);
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		int end = offset + length;
		for (int i = offset; i < end; i++)
		{
			if (i > offset)
				sb.append(' ');
			sb.append(Integer.toHexString(ints[i]));
		}

		sb.append(']');
		return sb.toString();
	}

	public static IntsRef deepCopyOf(IntsRef other)
	{
		IntsRef clone = new IntsRef();
		clone.copyInts(other);
		return clone;
	}

	public volatile Object clone()
		throws CloneNotSupportedException
	{
		return clone();
	}

	public volatile int compareTo(Object x0)
	{
		return compareTo((IntsRef)x0);
	}

}
