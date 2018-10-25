// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FixedBitSet.java

package org.apache.lucene.util;

import java.io.IOException;
import java.util.Arrays;
import org.apache.lucene.search.DocIdSet;
import org.apache.lucene.search.DocIdSetIterator;

// Referenced classes of package org.apache.lucene.util:
//			OpenBitSetIterator, Bits, BitUtil

public final class FixedBitSet extends DocIdSet
	implements Bits
{

	private final long bits[];
	private int numBits;
	static final boolean $assertionsDisabled = !org/apache/lucene/util/FixedBitSet.desiredAssertionStatus();

	public static int bits2words(int numBits)
	{
		int numLong = numBits >>> 6;
		if ((numBits & 0x3f) != 0)
			numLong++;
		return numLong;
	}

	public FixedBitSet(int numBits)
	{
		this.numBits = numBits;
		bits = new long[bits2words(numBits)];
	}

	public FixedBitSet(long storedBits[], int numBits)
	{
		this.numBits = numBits;
		bits = storedBits;
	}

	public FixedBitSet(FixedBitSet other)
	{
		bits = new long[other.bits.length];
		System.arraycopy(other.bits, 0, bits, 0, bits.length);
		numBits = other.numBits;
	}

	public DocIdSetIterator iterator()
	{
		return new OpenBitSetIterator(bits, bits.length);
	}

	public Bits bits()
	{
		return this;
	}

	public int length()
	{
		return numBits;
	}

	public boolean isCacheable()
	{
		return true;
	}

	public long[] getBits()
	{
		return bits;
	}

	public int cardinality()
	{
		return (int)BitUtil.pop_array(bits, 0, bits.length);
	}

	public boolean get(int index)
	{
		if (!$assertionsDisabled && (index < 0 || index >= numBits))
		{
			throw new AssertionError((new StringBuilder()).append("index=").append(index).toString());
		} else
		{
			int i = index >> 6;
			int bit = index & 0x3f;
			long bitmask = 1L << bit;
			return (bits[i] & bitmask) != 0L;
		}
	}

	public void set(int index)
	{
		if (!$assertionsDisabled && (index < 0 || index >= numBits))
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

	public boolean getAndSet(int index)
	{
		if (!$assertionsDisabled && (index < 0 || index >= numBits))
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

	public void clear(int index)
	{
		if (!$assertionsDisabled && (index < 0 || index >= numBits))
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

	public boolean getAndClear(int index)
	{
		if (!$assertionsDisabled && (index < 0 || index >= numBits))
		{
			throw new AssertionError();
		} else
		{
			int wordNum = index >> 6;
			int bit = index & 0x3f;
			long bitmask = 1L << bit;
			boolean val = (bits[wordNum] & bitmask) != 0L;
			bits[wordNum] &= ~bitmask;
			return val;
		}
	}

	public int nextSetBit(int index)
	{
		if (!$assertionsDisabled && (index < 0 || index >= numBits))
			throw new AssertionError();
		int i = index >> 6;
		int subIndex = index & 0x3f;
		long word = bits[i] >> subIndex;
		if (word != 0L)
			return (i << 6) + subIndex + BitUtil.ntz(word);
		while (++i < bits.length) 
		{
			word = bits[i];
			if (word != 0L)
				return (i << 6) + BitUtil.ntz(word);
		}
		return -1;
	}

	public int prevSetBit(int index)
	{
		if (!$assertionsDisabled && (index < 0 || index >= numBits))
			throw new AssertionError((new StringBuilder()).append("index=").append(index).append(" numBits=").append(numBits).toString());
		int i = index >> 6;
		int subIndex = index & 0x3f;
		long word = bits[i] << 63 - subIndex;
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

	public void or(DocIdSetIterator iter)
		throws IOException
	{
		int doc;
		if ((iter instanceof OpenBitSetIterator) && iter.docID() == -1)
		{
			OpenBitSetIterator obs = (OpenBitSetIterator)iter;
			or(obs.arr, obs.words);
			obs.advance(numBits);
		} else
		{
			while ((doc = iter.nextDoc()) < numBits) 
				set(doc);
		}
	}

	public void or(FixedBitSet other)
	{
		or(other.bits, other.bits.length);
	}

	private void or(long otherArr[], int otherLen)
	{
		long thisArr[] = bits;
		for (int pos = Math.min(thisArr.length, otherLen); --pos >= 0;)
			thisArr[pos] |= otherArr[pos];

	}

	public void and(DocIdSetIterator iter)
		throws IOException
	{
		if ((iter instanceof OpenBitSetIterator) && iter.docID() == -1)
		{
			OpenBitSetIterator obs = (OpenBitSetIterator)iter;
			and(obs.arr, obs.words);
			obs.advance(numBits);
		} else
		{
			if (numBits == 0)
				return;
			int disiDoc;
			int bitSetDoc;
			for (bitSetDoc = nextSetBit(0); bitSetDoc != -1 && (disiDoc = iter.advance(bitSetDoc)) < numBits; bitSetDoc = ++disiDoc >= numBits ? -1 : nextSetBit(disiDoc))
				clear(bitSetDoc, disiDoc);

			if (bitSetDoc != -1)
				clear(bitSetDoc, numBits);
		}
	}

	public void and(FixedBitSet other)
	{
		and(other.bits, other.bits.length);
	}

	private void and(long otherArr[], int otherLen)
	{
		long thisArr[] = bits;
		for (int pos = Math.min(thisArr.length, otherLen); --pos >= 0;)
			thisArr[pos] &= otherArr[pos];

		if (thisArr.length > otherLen)
			Arrays.fill(thisArr, otherLen, thisArr.length, 0L);
	}

	public void andNot(DocIdSetIterator iter)
		throws IOException
	{
		int doc;
		if ((iter instanceof OpenBitSetIterator) && iter.docID() == -1)
		{
			OpenBitSetIterator obs = (OpenBitSetIterator)iter;
			andNot(obs.arr, obs.words);
			obs.advance(numBits);
		} else
		{
			while ((doc = iter.nextDoc()) < numBits) 
				clear(doc);
		}
	}

	public void andNot(FixedBitSet other)
	{
		andNot(other.bits, other.bits.length);
	}

	private void andNot(long otherArr[], int otherLen)
	{
		long thisArr[] = bits;
		for (int pos = Math.min(thisArr.length, otherLen); --pos >= 0;)
			thisArr[pos] &= ~otherArr[pos];

	}

	public void flip(int startIndex, int endIndex)
	{
		if (!$assertionsDisabled && (startIndex < 0 || startIndex >= numBits))
			throw new AssertionError();
		if (!$assertionsDisabled && (endIndex < 0 || endIndex > numBits))
			throw new AssertionError();
		if (endIndex <= startIndex)
			return;
		int startWord = startIndex >> 6;
		int endWord = endIndex - 1 >> 6;
		long startmask = -1L << startIndex;
		long endmask = -1L >>> -endIndex;
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

	public void set(int startIndex, int endIndex)
	{
		if (!$assertionsDisabled && (startIndex < 0 || startIndex >= numBits))
			throw new AssertionError();
		if (!$assertionsDisabled && (endIndex < 0 || endIndex > numBits))
			throw new AssertionError();
		if (endIndex <= startIndex)
			return;
		int startWord = startIndex >> 6;
		int endWord = endIndex - 1 >> 6;
		long startmask = -1L << startIndex;
		long endmask = -1L >>> -endIndex;
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

	public void clear(int startIndex, int endIndex)
	{
		if (!$assertionsDisabled && (startIndex < 0 || startIndex >= numBits))
			throw new AssertionError();
		if (!$assertionsDisabled && (endIndex < 0 || endIndex > numBits))
			throw new AssertionError();
		if (endIndex <= startIndex)
			return;
		int startWord = startIndex >> 6;
		int endWord = endIndex - 1 >> 6;
		long startmask = -1L << startIndex;
		long endmask = -1L >>> -endIndex;
		startmask = ~startmask;
		endmask = ~endmask;
		if (startWord == endWord)
		{
			bits[startWord] &= startmask | endmask;
			return;
		} else
		{
			bits[startWord] &= startmask;
			Arrays.fill(bits, startWord + 1, endWord, 0L);
			bits[endWord] &= endmask;
			return;
		}
	}

	public FixedBitSet clone()
	{
		return new FixedBitSet(this);
	}

	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (!(o instanceof FixedBitSet))
			return false;
		FixedBitSet other = (FixedBitSet)o;
		if (numBits != other.length())
			return false;
		else
			return Arrays.equals(bits, other.bits);
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
