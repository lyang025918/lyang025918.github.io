// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CharsRef.java

package org.apache.lucene.util;

import java.util.Comparator;

// Referenced classes of package org.apache.lucene.util:
//			ArrayUtil

public final class CharsRef
	implements Comparable, CharSequence, Cloneable
{
	/**
	 * @deprecated Class UTF16SortedAsUTF8Comparator is deprecated
	 */

	private static class UTF16SortedAsUTF8Comparator
		implements Comparator
	{

		public int compare(CharsRef a, CharsRef b)
		{
			if (a == b)
				return 0;
			char aChars[] = a.chars;
			int aUpto = a.offset;
			char bChars[] = b.chars;
			int bUpto = b.offset;
			for (int aStop = aUpto + Math.min(a.length, b.length); aUpto < aStop;)
			{
				char aChar = aChars[aUpto++];
				char bChar = bChars[bUpto++];
				if (aChar != bChar)
				{
					if (aChar >= '\uD800' && bChar >= '\uD800')
					{
						if (aChar >= '\uE000')
							aChar -= '\u0800';
						else
							aChar += '\u2000';
						if (bChar >= '\uE000')
							bChar -= '\u0800';
						else
							bChar += '\u2000';
					}
					return aChar - bChar;
				}
			}

			return a.length - b.length;
		}

		public volatile int compare(Object x0, Object x1)
		{
			return compare((CharsRef)x0, (CharsRef)x1);
		}

		private UTF16SortedAsUTF8Comparator()
		{
		}

	}


	public static final char EMPTY_CHARS[] = new char[0];
	public char chars[];
	public int offset;
	public int length;
	/**
	 * @deprecated Field utf16SortedAsUTF8SortOrder is deprecated
	 */
	private static final Comparator utf16SortedAsUTF8SortOrder = new UTF16SortedAsUTF8Comparator();
	static final boolean $assertionsDisabled = !org/apache/lucene/util/CharsRef.desiredAssertionStatus();

	public CharsRef()
	{
		this(EMPTY_CHARS, 0, 0);
	}

	public CharsRef(int capacity)
	{
		chars = new char[capacity];
	}

	public CharsRef(char chars[], int offset, int length)
	{
		if (!$assertionsDisabled && chars == null)
			throw new AssertionError();
		if (!$assertionsDisabled && offset < 0)
			throw new AssertionError();
		if (!$assertionsDisabled && length < 0)
			throw new AssertionError();
		if (!$assertionsDisabled && chars.length < offset + length)
		{
			throw new AssertionError();
		} else
		{
			this.chars = chars;
			this.offset = offset;
			this.length = length;
			return;
		}
	}

	public CharsRef(String string)
	{
		chars = string.toCharArray();
		offset = 0;
		length = chars.length;
	}

	public CharsRef clone()
	{
		return new CharsRef(chars, offset, length);
	}

	public int hashCode()
	{
		int prime = 31;
		int result = 0;
		int end = offset + length;
		for (int i = offset; i < end; i++)
			result = 31 * result + chars[i];

		return result;
	}

	public boolean equals(Object other)
	{
		if (other == null)
			return false;
		if (other instanceof CharsRef)
			return charsEquals((CharsRef)other);
		else
			return false;
	}

	public boolean charsEquals(CharsRef other)
	{
		if (length == other.length)
		{
			int otherUpto = other.offset;
			char otherChars[] = other.chars;
			int end = offset + length;
			for (int upto = offset; upto < end;)
			{
				if (chars[upto] != otherChars[otherUpto])
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

	public int compareTo(CharsRef other)
	{
		if (this == other)
			return 0;
		char aChars[] = chars;
		int aUpto = offset;
		char bChars[] = other.chars;
		int bUpto = other.offset;
		for (int aStop = aUpto + Math.min(length, other.length); aUpto < aStop;)
		{
			int aInt = aChars[aUpto++];
			int bInt = bChars[bUpto++];
			if (aInt > bInt)
				return 1;
			if (aInt < bInt)
				return -1;
		}

		return length - other.length;
	}

	public void copyChars(CharsRef other)
	{
		copyChars(other.chars, other.offset, other.length);
	}

	public void grow(int newLength)
	{
		if (!$assertionsDisabled && offset != 0)
			throw new AssertionError();
		if (chars.length < newLength)
			chars = ArrayUtil.grow(chars, newLength);
	}

	public void copyChars(char otherChars[], int otherOffset, int otherLength)
	{
		if (chars.length - offset < otherLength)
		{
			chars = new char[otherLength];
			offset = 0;
		}
		System.arraycopy(otherChars, otherOffset, chars, offset, otherLength);
		length = otherLength;
	}

	public void append(char otherChars[], int otherOffset, int otherLength)
	{
		int newLen = length + otherLength;
		if (chars.length - offset < newLen)
		{
			char newChars[] = new char[newLen];
			System.arraycopy(chars, offset, newChars, 0, length);
			offset = 0;
			chars = newChars;
		}
		System.arraycopy(otherChars, otherOffset, chars, length + offset, otherLength);
		length = newLen;
	}

	public String toString()
	{
		return new String(chars, offset, length);
	}

	public int length()
	{
		return length;
	}

	public char charAt(int index)
	{
		if (index < 0 || index >= length)
			throw new IndexOutOfBoundsException();
		else
			return chars[offset + index];
	}

	public CharSequence subSequence(int start, int end)
	{
		if (start < 0 || end > length || start > end)
			throw new IndexOutOfBoundsException();
		else
			return new CharsRef(chars, offset + start, offset + end);
	}

	/**
	 * @deprecated Method getUTF16SortedAsUTF8Comparator is deprecated
	 */

	public static Comparator getUTF16SortedAsUTF8Comparator()
	{
		return utf16SortedAsUTF8SortOrder;
	}

	public static CharsRef deepCopyOf(CharsRef other)
	{
		CharsRef clone = new CharsRef();
		clone.copyChars(other);
		return clone;
	}

	public volatile Object clone()
		throws CloneNotSupportedException
	{
		return clone();
	}

	public volatile int compareTo(Object x0)
	{
		return compareTo((CharsRef)x0);
	}

}
