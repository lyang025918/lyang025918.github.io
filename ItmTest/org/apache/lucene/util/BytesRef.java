// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BytesRef.java

package org.apache.lucene.util;

import java.util.Comparator;

// Referenced classes of package org.apache.lucene.util:
//			CharsRef, UnicodeUtil, ArrayUtil

public final class BytesRef
	implements Comparable, Cloneable
{
	/**
	 * @deprecated Class UTF8SortedAsUTF16Comparator is deprecated
	 */

	private static class UTF8SortedAsUTF16Comparator
		implements Comparator
	{

		public int compare(BytesRef a, BytesRef b)
		{
			byte aBytes[] = a.bytes;
			int aUpto = a.offset;
			byte bBytes[] = b.bytes;
			int bUpto = b.offset;
			int aStop;
			if (a.length < b.length)
				aStop = aUpto + a.length;
			else
				aStop = aUpto + b.length;
			while (aUpto < aStop) 
			{
				int aByte = aBytes[aUpto++] & 0xff;
				int bByte = bBytes[bUpto++] & 0xff;
				if (aByte != bByte)
				{
					if (aByte >= 238 && bByte >= 238)
					{
						if ((aByte & 0xfe) == 238)
							aByte += 14;
						if ((bByte & 0xfe) == 238)
							bByte += 14;
					}
					return aByte - bByte;
				}
			}
			return a.length - b.length;
		}

		public volatile int compare(Object x0, Object x1)
		{
			return compare((BytesRef)x0, (BytesRef)x1);
		}

		private UTF8SortedAsUTF16Comparator()
		{
		}

	}

	private static class UTF8SortedAsUnicodeComparator
		implements Comparator
	{

		public int compare(BytesRef a, BytesRef b)
		{
			byte aBytes[] = a.bytes;
			int aUpto = a.offset;
			byte bBytes[] = b.bytes;
			int bUpto = b.offset;
			for (int aStop = aUpto + Math.min(a.length, b.length); aUpto < aStop;)
			{
				int aByte = aBytes[aUpto++] & 0xff;
				int bByte = bBytes[bUpto++] & 0xff;
				int diff = aByte - bByte;
				if (diff != 0)
					return diff;
			}

			return a.length - b.length;
		}

		public volatile int compare(Object x0, Object x1)
		{
			return compare((BytesRef)x0, (BytesRef)x1);
		}

		private UTF8SortedAsUnicodeComparator()
		{
		}

	}


	public static final byte EMPTY_BYTES[] = new byte[0];
	public byte bytes[];
	public int offset;
	public int length;
	private static final Comparator utf8SortedAsUnicodeSortOrder = new UTF8SortedAsUnicodeComparator();
	/**
	 * @deprecated Field utf8SortedAsUTF16SortOrder is deprecated
	 */
	private static final Comparator utf8SortedAsUTF16SortOrder = new UTF8SortedAsUTF16Comparator();
	static final boolean $assertionsDisabled = !org/apache/lucene/util/BytesRef.desiredAssertionStatus();

	public BytesRef()
	{
		this(EMPTY_BYTES);
	}

	public BytesRef(byte bytes[], int offset, int length)
	{
		if (!$assertionsDisabled && bytes == null)
			throw new AssertionError();
		if (!$assertionsDisabled && offset < 0)
			throw new AssertionError();
		if (!$assertionsDisabled && length < 0)
			throw new AssertionError();
		if (!$assertionsDisabled && bytes.length < offset + length)
		{
			throw new AssertionError();
		} else
		{
			this.bytes = bytes;
			this.offset = offset;
			this.length = length;
			return;
		}
	}

	public BytesRef(byte bytes[])
	{
		this(bytes, 0, bytes.length);
	}

	public BytesRef(int capacity)
	{
		bytes = new byte[capacity];
	}

	public BytesRef(CharSequence text)
	{
		this();
		copyChars(text);
	}

	public void copyChars(CharSequence text)
	{
		if (!$assertionsDisabled && offset != 0)
		{
			throw new AssertionError();
		} else
		{
			UnicodeUtil.UTF16toUTF8(text, 0, text.length(), this);
			return;
		}
	}

	public boolean bytesEquals(BytesRef other)
	{
		if (!$assertionsDisabled && other == null)
			throw new AssertionError();
		if (length == other.length)
		{
			int otherUpto = other.offset;
			byte otherBytes[] = other.bytes;
			int end = offset + length;
			for (int upto = offset; upto < end;)
			{
				if (bytes[upto] != otherBytes[otherUpto])
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

	public BytesRef clone()
	{
		return new BytesRef(bytes, offset, length);
	}

	public int hashCode()
	{
		int hash = 0;
		int end = offset + length;
		for (int i = offset; i < end; i++)
			hash = 31 * hash + bytes[i];

		return hash;
	}

	public boolean equals(Object other)
	{
		if (other == null)
			return false;
		if (other instanceof BytesRef)
			return bytesEquals((BytesRef)other);
		else
			return false;
	}

	public String utf8ToString()
	{
		CharsRef ref = new CharsRef(length);
		UnicodeUtil.UTF8toUTF16(bytes, offset, length, ref);
		return ref.toString();
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
			sb.append(Integer.toHexString(bytes[i] & 0xff));
		}

		sb.append(']');
		return sb.toString();
	}

	public void copyBytes(BytesRef other)
	{
		if (bytes.length - offset < other.length)
		{
			bytes = new byte[other.length];
			offset = 0;
		}
		System.arraycopy(other.bytes, other.offset, bytes, offset, other.length);
		length = other.length;
	}

	public void append(BytesRef other)
	{
		int newLen = length + other.length;
		if (bytes.length - offset < newLen)
		{
			byte newBytes[] = new byte[newLen];
			System.arraycopy(bytes, offset, newBytes, 0, length);
			offset = 0;
			bytes = newBytes;
		}
		System.arraycopy(other.bytes, other.offset, bytes, length + offset, other.length);
		length = newLen;
	}

	public void grow(int newLength)
	{
		if (!$assertionsDisabled && offset != 0)
		{
			throw new AssertionError();
		} else
		{
			bytes = ArrayUtil.grow(bytes, newLength);
			return;
		}
	}

	public int compareTo(BytesRef other)
	{
		return utf8SortedAsUnicodeSortOrder.compare(this, other);
	}

	public static Comparator getUTF8SortedAsUnicodeComparator()
	{
		return utf8SortedAsUnicodeSortOrder;
	}

	/**
	 * @deprecated Method getUTF8SortedAsUTF16Comparator is deprecated
	 */

	public static Comparator getUTF8SortedAsUTF16Comparator()
	{
		return utf8SortedAsUTF16SortOrder;
	}

	public static BytesRef deepCopyOf(BytesRef other)
	{
		BytesRef copy = new BytesRef();
		copy.copyBytes(other);
		return copy;
	}

	public volatile Object clone()
		throws CloneNotSupportedException
	{
		return clone();
	}

	public volatile int compareTo(Object x0)
	{
		return compareTo((BytesRef)x0);
	}

}
