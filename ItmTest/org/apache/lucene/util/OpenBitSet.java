// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   OpenBitSet.java

package org.apache.lucene.util;

import java.util.Arrays;
import org.apache.lucene.search.DocIdSet;
import org.apache.lucene.search.DocIdSetIterator;

// Referenced classes of package org.apache.lucene.util:
//			OpenBitSetIterator, Bits, BitUtil, ArrayUtil

public class OpenBitSet extends DocIdSet
	implements Bits, Cloneable
{

	protected long bits[];
	protected int wlen;
	private long numBits;
	static final boolean $assertionsDisabled = !org/apache/lucene/util/OpenBitSet.desiredAssertionStatus();

	public OpenBitSet(long numBits)
	{
		this.numBits = numBits;
		bits = new long[bits2words(numBits)];
		wlen = bits.length;
	}

	public OpenBitSet()
	{
		this(64L);
	}

	public OpenBitSet(long bits[], int numWords)
	{
		this.bits = bits;
		wlen = numWords;
		numBits = wlen * 64;
	}

	public DocIdSetIterator iterator()
	{
		return new OpenBitSetIterator(bits, wlen);
	}

	public Bits bits()
	{
		return this;
	}

	public boolean isCacheable()
	{
		return true;
	}

	public long capacity()
	{
		return (long)(bits.length << 6);
	}

	public long size()
	{
		return capacity();
	}

	public int length()
	{
		return bits.length << 6;
	}

	public boolean isEmpty()
	{
		return cardinality() == 0L;
	}

	public long[] getBits()
	{
		return bits;
	}

	public void setBits(long bits[])
	{
		this.bits = bits;
	}

	public int getNumWords()
	{
		return wlen;
	}

	public void setNumWords(int nWords)
	{
		wlen = nWords;
	}

	public boolean get(int index)
	{
		int i = index >> 6;
		if (i >= bits.length)
		{
			return false;
		} else
		{
			int bit = index & 0x3f;
			long bitmask = 1L << bit;
			return (bits[i] & bitmask) != 0L;
		}
	}

	public boolean fastGet(int index)
	{
		if (!$assertionsDisabled && (index < 0 || (long)index >= numBits))
		{
			throw new AssertionError();
		} else
		{
			int i = index >> 6;
			int bit = index & 0x3f;
			long bitmask = 1L << bit;
			return (bits[i] & bitmask) != 0L;
		}
	}

	public boolean get(long index)
	{
		int i = (int)(index >> 6);
		if (i >= bits.length)
		{
			return false;
		} else
		{
			int bit = (int)index & 0x3f;
			long bitmask = 1L << bit;
			return (bits[i] & bitmask) != 0L;
		}
	}

	public boolean fastGet(long index)
	{
		if (!$assertionsDisabled && (index < 0L || index >= numBits))
		{
			throw new AssertionError();
		} else
		{
			int i = (int)(index >> 6);
			int bit = (int)index & 0x3f;
			long bitmask = 1L << bit;
			return (bits[i] & bitmask) != 0L;
		}
	}

	public int getBit(int index)
	{
		if (!$assertionsDisabled && (index < 0 || (long)index >= numBits))
		{
			throw new AssertionError();
		} else
		{
			int i = index >> 6;
			int bit = index & 0x3f;
			return (int)(bits[i] >>> bit) & 1;
		}
	}

	public void set(long index)
	{
		int wordNum = expandingWordNum(index);
		int bit = (int)index & 0x3f;
		long bitmask = 1L << bit;
		bits[wordNum] |= bitmask;
	}

	public void fastSet(int index)
	{
		if (!$assertionsDisabled && (index < 0 || (long)index >= numBits))
		{
			throw new AssertionError();
		} else
		{
			int wordNum = index >> 6;
			int bit = index & 0x3f;
			long bitmask = 1L << bit;
			bits[wordNum] |= bitmask;
			return;
		}
	}

	public void fastSet(long index)
	{
		if (!$assertionsDisabled && (index < 0L || index >= numBits))
		{
			throw new AssertionError();
		} else
		{
			int wordNum = (int)(index >> 6);
			int bit = (int)index & 0x3f;
			long bitmask = 1L << bit;
			bits[wordNum] |= bitmask;
			return;
		}
	}

	public void set(long startIndex, long endIndex)
	{
		if (endIndex <= startIndex)
			return;
		int startWord = (int)(startIndex >> 6);
		int endWord = expandingWordNum(endIndex - 1L);
		long startmask = -1L << (int)startIndex;
		long endmask = -1L >>> (int)(-endIndex);
		if (startWord == endWord)
		{
			bits[startWord] |= startmask & endmask;
			return;
		} else
		{
			bits[startWord] |= startmask;
			Arrays.fill(bits, startWord + 1, endWord, -1L);
			bits[endWord] |= endmask;
			return;
		}
	}

	protected int expandingWordNum(long index)
	{
		int wordNum = (int)(index >> 6);
		if (wordNum >= wlen)
		{
			ensureCapacity(index + 1L);
			wlen = wordNum + 1;
		}
		if (!$assertionsDisabled && (numBits = Math.max(numBits, index + 1L)) < 0L)
			throw new AssertionError();
		else
			return wordNum;
	}

	public void fastClear(int index)
	{
		if (!$assertionsDisabled && (index < 0 || (long)index >= numBits))
		{
			throw new AssertionError();
		} else
		{
			int wordNum = index >> 6;
			int bit = index & 0x3f;
			long bitmask = 1L << bit;
			bits[wordNum] &= ~bitmask;
			return;
		}
	}

	public void fastClear(long index)
	{
		if (!$assertionsDisabled && (index < 0L || index >= numBits))
		{
			throw new AssertionError();
		} else
		{
			int wordNum = (int)(index >> 6);
			int bit = (int)index & 0x3f;
			long bitmask = 1L << bit;
			bits[wordNum] &= ~bitmask;
			return;
		}
	}

	public void clear(long index)
	{
		int wordNum = (int)(index >> 6);
		if (wordNum >= wlen)
		{
			return;
		} else
		{
			int bit = (int)index & 0x3f;
			long bitmask = 1L << bit;
			bits[wordNum] &= ~bitmask;
			return;
		}
	}

	public void clear(int startIndex, int endIndex)
	{
		if (endIndex <= startIndex)
			return;
		int startWord = startIndex >> 6;
		if (startWord >= wlen)
			return;
		int endWord = endIndex - 1 >> 6;
		long startmask = -1L << startIndex;
		long endmask = -1L >>> -endIndex;
		startmask = ~startmask;
		endmask = ~endmask;
		if (startWord == endWord)
		{
			bits[startWord] &= startmask | endmask;
			return;
		}
		bits[startWord] &= startmask;
		int middle = Math.min(wlen, endWord);
		Arrays.fill(bits, startWord + 1, middle, 0L);
		if (endWord < wlen)
			bits[endWord] &= endmask;
	}

	public void clear(long startIndex, long endIndex)
	{
		if (endIndex <= startIndex)
			return;
		int startWord = (int)(startIndex >> 6);
		if (startWord >= wlen)
			return;
		int endWord = (int)(endIndex - 1L >> 6);
		long startmask = -1L << (int)startIndex;
		long endmask = -1L >>> (int)(-endIndex);
		startmask = ~startmask;
		endmask = ~endmask;
		if (startWord == endWord)
		{
			bits[startWord] &= startmask | endmask;
			return;
		}
		bits[startWord] &= startmask;
		int middle = Math.min(wlen, endWord);
		Arrays.fill(bits, startWord + 1, middle, 0L);
		if (endWord < wlen)
			bits[endWord] &= endmask;
	}

	public boolean getAndSet(int index)
	{
		if (!$assertionsDisabled && (index < 0 || (long)index >= numBits))
		{
			throw new AssertionError();
		} else
		{
			int wordNum = index >> 6;
			int bit = index & 0x3f;
			long bitmask = 1L << bit;
			boolean val = (bits[wordNum] & bitmask) != 0L;
			bits[wordNum] |= bitmask;
			return val;
		}
	}

	public boolean getAndSet(long index)
	{
		if (!$assertionsDisabled && (index < 0L || index >= numBits))
		{
			throw new AssertionError();
		} else
		{
			int wordNum = (int)(index >> 6);
			int bit = (int)index & 0x3f;
			long bitmask = 1L << bit;
			boolean val = (bits[wordNum] & bitmask) != 0L;
			bits[wordNum] |= bitmask;
			return val;
		}
	}

	public void fastFlip(int index)
	{
		if (!$assertionsDisabled && (index < 0 || (long)index >= numBits))
		{
			throw new AssertionError();
		} else
		{
			int wordNum = index >> 6;
			int bit = index & 0x3f;
			long bitmask = 1L << bit;
			bits[wordNum] ^= bitmask;
			return;
		}
	}

	public void fastFlip(long index)
	{
		if (!$assertionsDisabled && (index < 0L || index >= numBits))
		{
			throw new AssertionError();
		} else
		{
			int wordNum = (int)(index >> 6);
			int bit = (int)index & 0x3f;
			long bitmask = 1L << bit;
			bits[wordNum] ^= bitmask;
			return;
		}
	}

	public void flip(long index)
	{
		int wordNum = expandingWordNum(index);
		int bit = (int)index & 0x3f;
		long bitmask = 1L << bit;
		bits[wordNum] ^= bitmask;
	}

	public boolean flipAndGet(int index)
	{
		if (!$assertionsDisabled && (index < 0 || (long)index >= numBits))
		{
			throw new AssertionError();
		} else
		{
			int wordNum = index >> 6;
			int bit = index & 0x3f;
			long bitmask = 1L << bit;
			bits[wordNum] ^= bitmask;
			return (bits[wordNum] & bitmask) != 0L;
		}
	}

	public boolean flipAndGet(long index)
	{
		if (!$assertionsDisabled && (index < 0L || index >= numBits))
		{
			throw new AssertionError();
		} else
		{
			int wordNum = (int)(index >> 6);
			int bit = (int)index & 0x3f;
			long bitmask = 1L << bit;
			bits[wordNum] ^= bitmask;
			return (bits[wordNum] & bitmask) != 0L;
		}
	}

	public void flip(long startIndex, long endIndex)
	{
		if (endIndex <= startIndex)
			return;
		int startWord = (int)(startIndex >> 6);
		int endWord = expandingWordNum(endIndex - 1L);
		long startmask = -1L << (int)startIndex;
		long endmask = -1L >>> (int)(-endIndex);
		if (startWord == endWord)
		{
			bits[startWord] ^= startmask & endmask;
			return;
		}
		bits[startWord] ^= startmask;
		for (int i = startWord + 1; i < endWord; i++)
			bits[i] = ~bits[i];

		bits[endWord] ^= endmask;
	}

	public long cardinality()
	{
		return BitUtil.pop_array(bits, 0, wlen);
	}

	public static long intersectionCount(OpenBitSet a, OpenBitSet b)
	{
		return BitUtil.pop_intersect(a.bits, b.bits, 0, Math.min(a.wlen, b.wlen));
	}

	public static long unionCount(OpenBitSet a, OpenBitSet b)
	{
		long tot = BitUtil.pop_union(a.bits, b.bits, 0, Math.min(a.wlen, b.wlen));
		if (a.wlen < b.wlen)
			tot += BitUtil.pop_array(b.bits, a.wlen, b.wlen - a.wlen);
		else
		if (a.wlen > b.wlen)
			tot += BitUtil.pop_array(a.bits, b.wlen, a.wlen - b.wlen);
		return tot;
	}

	public static long andNotCount(OpenBitSet a, OpenBitSet b)
	{
		long tot = BitUtil.pop_andnot(a.bits, b.bits, 0, Math.min(a.wlen, b.wlen));
		if (a.wlen > b.wlen)
			tot += BitUtil.pop_array(a.bits, b.wlen, a.wlen - b.wlen);
		return tot;
	}

	public static long xorCount(OpenBitSet a, OpenBitSet b)
	{
		long tot = BitUtil.pop_xor(a.bits, b.bits, 0, Math.min(a.wlen, b.wlen));
		if (a.wlen < b.wlen)
			tot += BitUtil.pop_array(b.bits, a.wlen, b.wlen - a.wlen);
		else
		if (a.wlen > b.wlen)
			tot += BitUtil.pop_array(a.bits, b.wlen, a.wlen - b.wlen);
		return tot;
	}

	public int nextSetBit(int index)
	{
		int i = index >> 6;
		if (i >= wlen)
			return -1;
		int subIndex = index & 0x3f;
		long word = bits[i] >> subIndex;
		if (word != 0L)
			return (i << 6) + subIndex + BitUtil.ntz(word);
		while (++i < wlen) 
		{
			word = bits[i];
			if (word != 0L)
				return (i << 6) + BitUtil.ntz(word);
		}
		return -1;
	}

	public long nextSetBit(long index)
	{
		int i = (int)(index >>> 6);
		if (i >= wlen)
			return -1L;
		int subIndex = (int)index & 0x3f;
		long word = bits[i] >>> subIndex;
		if (word != 0L)
			return ((long)i << 6) + (long)(subIndex + BitUtil.ntz(word));
		while (++i < wlen) 
		{
			word = bits[i];
			if (word != 0L)
				return ((long)i << 6) + (long)BitUtil.ntz(word);
		}
		return -1L;
	}

	public int prevSetBit(int index)
	{
		int i = index >> 6;
		int subIndex;
		long word;
		if (i >= wlen)
		{
			i = wlen - 1;
			if (i < 0)
				return -1;
			subIndex = 63;
			word = bits[i];
		} else
		{
			if (i < 0)
				return -1;
			subIndex = index & 0x3f;
			word = bits[i] << 63 - subIndex;
		}
		if (word != 0L)
			return ((i << 6) + subIndex) - Long.numberOfLeadingZeros(word);
		while (--i >= 0) 
		{
			word = bits[i];
			if (word != 0L)
				return ((i << 6) + 63) - Long.numberOfLeadingZeros(word);
		}
		return -1;
	}

	public long prevSetBit(long index)
	{
		int i = (int)(index >> 6);
		int subIndex;
		long word;
		if (i >= wlen)
		{
			i = wlen - 1;
			if (i < 0)
				return -1L;
			subIndex = 63;
			word = bits[i];
		} else
		{
			if (i < 0)
				return -1L;
			subIndex = (int)index & 0x3f;
			word = bits[i] << 63 - subIndex;
		}
		if (word != 0L)
			return (((long)i << 6) + (long)subIndex) - (long)Long.numberOfLeadingZeros(word);
		while (--i >= 0) 
		{
			word = bits[i];
			if (word != 0L)
				return (((long)i << 6) + 63L) - (long)Long.numberOfLeadingZeros(word);
		}
		return -1L;
	}

	public OpenBitSet clone()
	{
		OpenBitSet obs;
		obs = (OpenBitSet)super.clone();
		obs.bits = (long[])obs.bits.clone();
		return obs;
		CloneNotSupportedException e;
		e;
		throw new RuntimeException(e);
	}

	public void intersect(OpenBitSet other)
	{
		int newLen = Math.min(wlen, other.wlen);
		long thisArr[] = bits;
		long otherArr[] = other.bits;
		for (int pos = newLen; --pos >= 0;)
			thisArr[pos] &= otherArr[pos];

		if (wlen > newLen)
			Arrays.fill(bits, newLen, wlen, 0L);
		wlen = newLen;
	}

	public void union(OpenBitSet other)
	{
		int newLen = Math.max(wlen, other.wlen);
		ensureCapacityWords(newLen);
		if (!$assertionsDisabled && (numBits = Math.max(other.numBits, numBits)) < 0L)
			throw new AssertionError();
		long thisArr[] = bits;
		long otherArr[] = other.bits;
		for (int pos = Math.min(wlen, other.wlen); --pos >= 0;)
			thisArr[pos] |= otherArr[pos];

		if (wlen < newLen)
			System.arraycopy(otherArr, wlen, thisArr, wlen, newLen - wlen);
		wlen = newLen;
	}

	public void remove(OpenBitSet other)
	{
		int idx = Math.min(wlen, other.wlen);
		long thisArr[] = bits;
		long otherArr[] = other.bits;
		while (--idx >= 0) 
			thisArr[idx] &= ~otherArr[idx];
	}

	public void xor(OpenBitSet other)
	{
		int newLen = Math.max(wlen, other.wlen);
		ensureCapacityWords(newLen);
		if (!$assertionsDisabled && (numBits = Math.max(other.numBits, numBits)) < 0L)
			throw new AssertionError();
		long thisArr[] = bits;
		long otherArr[] = other.bits;
		for (int pos = Math.min(wlen, other.wlen); --pos >= 0;)
			thisArr[pos] ^= otherArr[pos];

		if (wlen < newLen)
			System.arraycopy(otherArr, wlen, thisArr, wlen, newLen - wlen);
		wlen = newLen;
	}

	public void and(OpenBitSet other)
	{
		intersect(other);
	}

	public void or(OpenBitSet other)
	{
		union(other);
	}

	public void andNot(OpenBitSet other)
	{
		remove(other);
	}

	public boolean intersects(OpenBitSet other)
	{
		int pos = Math.min(wlen, other.wlen);
		long thisArr[] = bits;
		long otherArr[] = other.bits;
		while (--pos >= 0) 
			if ((thisArr[pos] & otherArr[pos]) != 0L)
				return true;
		return false;
	}

	public void ensureCapacityWords(int numWords)
	{
		if (bits.length < numWords)
			bits = ArrayUtil.grow(bits, numWords);
	}

	public void ensureCapacity(long numBits)
	{
		ensureCapacityWords(bits2words(numBits));
	}

	public void trimTrailingZeros()
	{
		int idx;
		for (idx = wlen - 1; idx >= 0 && bits[idx] == 0L; idx--);
		wlen = idx + 1;
	}

	public static int bits2words(long numBits)
	{
		return (int)((numBits - 1L >>> 6) + 1L);
	}

	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (!(o instanceof OpenBitSet))
			return false;
		OpenBitSet b = (OpenBitSet)o;
		OpenBitSet a;
		if (b.wlen > wlen)
		{
			a = b;
			b = this;
		} else
		{
			a = this;
		}
		for (int i = a.wlen - 1; i >= b.wlen; i--)
			if (a.bits[i] != 0L)
				return false;

		for (int i = b.wlen - 1; i >= 0; i--)
			if (a.bits[i] != b.bits[i])
				return false;

		return true;
	}

	public int hashCode()
	{
		long h = 0L;
		for (int i = bits.length; --i >= 0;)
		{
			h ^= bits[i];
			h = h << 1 | h >>> 63;
		}

		return (int)(h >> 32 ^ h) + 0x98761234;
	}

	public volatile Object clone()
		throws CloneNotSupportedException
	{
		return clone();
	}

}
