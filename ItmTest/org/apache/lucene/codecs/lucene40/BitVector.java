// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BitVector.java

package org.apache.lucene.codecs.lucene40;

import java.io.IOException;
import java.util.Arrays;
import org.apache.lucene.codecs.CodecUtil;
import org.apache.lucene.store.*;
import org.apache.lucene.util.MutableBits;

final class BitVector
	implements Cloneable, MutableBits
{

	private byte bits[];
	private int size;
	private int count;
	private int version;
	private static final byte BYTE_COUNTS[] = {
		0, 1, 1, 2, 1, 2, 2, 3, 1, 2, 
		2, 3, 2, 3, 3, 4, 1, 2, 2, 3, 
		2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 
		4, 5, 1, 2, 2, 3, 2, 3, 3, 4, 
		2, 3, 3, 4, 3, 4, 4, 5, 2, 3, 
		3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 
		4, 5, 5, 6, 1, 2, 2, 3, 2, 3, 
		3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 
		2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 
		4, 5, 4, 5, 5, 6, 2, 3, 3, 4, 
		3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 
		5, 6, 3, 4, 4, 5, 4, 5, 5, 6, 
		4, 5, 5, 6, 5, 6, 6, 7, 1, 2, 
		2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 
		3, 4, 4, 5, 2, 3, 3, 4, 3, 4, 
		4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 
		2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 
		4, 5, 4, 5, 5, 6, 3, 4, 4, 5, 
		4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 
		6, 7, 2, 3, 3, 4, 3, 4, 4, 5, 
		3, 4, 4, 5, 4, 5, 5, 6, 3, 4, 
		4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 
		5, 6, 6, 7, 3, 4, 4, 5, 4, 5, 
		5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 
		4, 5, 5, 6, 5, 6, 6, 7, 5, 6, 
		6, 7, 6, 7, 7, 8
	};
	private static String CODEC = "BitVector";
	public static final int VERSION_PRE = -1;
	public static final int VERSION_START = 0;
	public static final int VERSION_DGAPS_CLEARED = 1;
	public static final int VERSION_CURRENT = 1;
	static final boolean $assertionsDisabled = !org/apache/lucene/codecs/lucene40/BitVector.desiredAssertionStatus();

	public BitVector(int n)
	{
		size = n;
		bits = new byte[getNumBytes(size)];
		count = 0;
	}

	BitVector(byte bits[], int size)
	{
		this.bits = bits;
		this.size = size;
		count = -1;
	}

	private int getNumBytes(int size)
	{
		int bytesLength = size >>> 3;
		if ((size & 7) != 0)
			bytesLength++;
		return bytesLength;
	}

	public BitVector clone()
	{
		byte copyBits[] = new byte[bits.length];
		System.arraycopy(bits, 0, copyBits, 0, bits.length);
		BitVector clone = new BitVector(copyBits, size);
		clone.count = count;
		return clone;
	}

	public final void set(int bit)
	{
		if (bit >= size)
		{
			throw new ArrayIndexOutOfBoundsException((new StringBuilder()).append("bit=").append(bit).append(" size=").append(size).toString());
		} else
		{
			bits[bit >> 3] |= 1 << (bit & 7);
			count = -1;
			return;
		}
	}

	public final boolean getAndSet(int bit)
	{
		if (bit >= size)
			throw new ArrayIndexOutOfBoundsException((new StringBuilder()).append("bit=").append(bit).append(" size=").append(size).toString());
		int pos = bit >> 3;
		int v = bits[pos];
		int flag = 1 << (bit & 7);
		if ((flag & v) != 0)
			return true;
		bits[pos] = (byte)(v | flag);
		if (count != -1)
		{
			count++;
			if (!$assertionsDisabled && count > size)
				throw new AssertionError();
		}
		return false;
	}

	public final void clear(int bit)
	{
		if (bit >= size)
		{
			throw new ArrayIndexOutOfBoundsException(bit);
		} else
		{
			bits[bit >> 3] &= ~(1 << (bit & 7));
			count = -1;
			return;
		}
	}

	public final boolean getAndClear(int bit)
	{
		if (bit >= size)
			throw new ArrayIndexOutOfBoundsException(bit);
		int pos = bit >> 3;
		int v = bits[pos];
		int flag = 1 << (bit & 7);
		if ((flag & v) == 0)
			return false;
		bits[pos] &= ~flag;
		if (count != -1)
		{
			count--;
			if (!$assertionsDisabled && count < 0)
				throw new AssertionError();
		}
		return true;
	}

	public final boolean get(int bit)
	{
		if (!$assertionsDisabled && (bit < 0 || bit >= size))
			throw new AssertionError((new StringBuilder()).append("bit ").append(bit).append(" is out of bounds 0..").append(size - 1).toString());
		else
			return (bits[bit >> 3] & 1 << (bit & 7)) != 0;
	}

	public final int size()
	{
		return size;
	}

	public int length()
	{
		return size;
	}

	public final int count()
	{
		if (count == -1)
		{
			int c = 0;
			int end = bits.length;
			for (int i = 0; i < end; i++)
				c += BYTE_COUNTS[bits[i] & 0xff];

			count = c;
		}
		if (!$assertionsDisabled && count > size)
			throw new AssertionError((new StringBuilder()).append("count=").append(count).append(" size=").append(size).toString());
		else
			return count;
	}

	public final int getRecomputedCount()
	{
		int c = 0;
		int end = bits.length;
		for (int i = 0; i < end; i++)
			c += BYTE_COUNTS[bits[i] & 0xff];

		return c;
	}

	public int getVersion()
	{
		return version;
	}

	public final void write(Directory d, String name, IOContext context)
		throws IOException
	{
		IndexOutput output;
		if (!$assertionsDisabled && (d instanceof CompoundFileDirectory))
			throw new AssertionError();
		output = d.createOutput(name, context);
		output.writeInt(-2);
		CodecUtil.writeHeader(output, CODEC, 1);
		if (isSparse())
			writeClearedDgaps(output);
		else
			writeBits(output);
		if (!$assertionsDisabled && !verifyCount())
			throw new AssertionError();
		output.close();
		break MISSING_BLOCK_LABEL_106;
		Exception exception;
		exception;
		output.close();
		throw exception;
	}

	public void invertAll()
	{
		if (count != -1)
			count = size - count;
		if (bits.length > 0)
		{
			for (int idx = 0; idx < bits.length; idx++)
				bits[idx] = (byte)(~bits[idx]);

			clearUnusedBits();
		}
	}

	private void clearUnusedBits()
	{
		if (bits.length > 0)
		{
			int lastNBits = size & 7;
			if (lastNBits != 0)
			{
				int mask = (1 << lastNBits) - 1;
				bits[bits.length - 1] &= mask;
			}
		}
	}

	public void setAll()
	{
		Arrays.fill(bits, (byte)-1);
		clearUnusedBits();
		count = size;
	}

	private void writeBits(IndexOutput output)
		throws IOException
	{
		output.writeInt(size());
		output.writeInt(count());
		output.writeBytes(bits, bits.length);
	}

	private void writeClearedDgaps(IndexOutput output)
		throws IOException
	{
		output.writeInt(-1);
		output.writeInt(size());
		output.writeInt(count());
		int last = 0;
		int numCleared = size() - count();
		for (int i = 0; i < bits.length && numCleared > 0; i++)
		{
			if (bits[i] == -1)
				continue;
			output.writeVInt(i - last);
			output.writeByte(bits[i]);
			last = i;
			numCleared -= 8 - BYTE_COUNTS[bits[i] & 0xff];
			if (!$assertionsDisabled && numCleared < 0 && (i != bits.length - 1 || numCleared != -(8 - (size & 7))))
				throw new AssertionError();
		}

	}

	private boolean isSparse()
	{
		int clearedCount = size() - count();
		if (clearedCount == 0)
			return true;
		int avgGapLength = bits.length / clearedCount;
		int expectedDGapBytes;
		if (avgGapLength <= 128)
			expectedDGapBytes = 1;
		else
		if (avgGapLength <= 16384)
			expectedDGapBytes = 2;
		else
		if (avgGapLength <= 0x200000)
			expectedDGapBytes = 3;
		else
		if (avgGapLength <= 0x10000000)
			expectedDGapBytes = 4;
		else
			expectedDGapBytes = 5;
		int bytesPerSetBit = expectedDGapBytes + 1;
		long expectedBits = 32 + 8 * bytesPerSetBit * clearedCount;
		long factor = 10L;
		return 10L * expectedBits < (long)size();
	}

	public BitVector(Directory d, String name, IOContext context)
		throws IOException
	{
		IndexInput input;
		input = d.openInput(name, context);
		int firstInt = input.readInt();
		if (firstInt == -2)
		{
			version = CodecUtil.checkHeader(input, CODEC, 0, 1);
			size = input.readInt();
		} else
		{
			version = -1;
			size = firstInt;
		}
		if (size == -1)
		{
			if (version >= 1)
				readClearedDgaps(input);
			else
				readSetDgaps(input);
		} else
		{
			readBits(input);
		}
		if (version < 1)
			invertAll();
		if (!$assertionsDisabled && !verifyCount())
			throw new AssertionError();
		input.close();
		break MISSING_BLOCK_LABEL_154;
		Exception exception;
		exception;
		input.close();
		throw exception;
	}

	private boolean verifyCount()
	{
		if (!$assertionsDisabled && count == -1)
			throw new AssertionError();
		int countSav = count;
		count = -1;
		if (!$assertionsDisabled && countSav != count())
			throw new AssertionError((new StringBuilder()).append("saved count was ").append(countSav).append(" but recomputed count is ").append(count).toString());
		else
			return true;
	}

	private void readBits(IndexInput input)
		throws IOException
	{
		count = input.readInt();
		bits = new byte[getNumBytes(size)];
		input.readBytes(bits, 0, bits.length);
	}

	private void readSetDgaps(IndexInput input)
		throws IOException
	{
		size = input.readInt();
		count = input.readInt();
		bits = new byte[getNumBytes(size)];
		int last = 0;
		for (int n = count(); n > 0;)
		{
			last += input.readVInt();
			bits[last] = input.readByte();
			n -= BYTE_COUNTS[bits[last] & 0xff];
			if (!$assertionsDisabled && n < 0)
				throw new AssertionError();
		}

	}

	private void readClearedDgaps(IndexInput input)
		throws IOException
	{
		size = input.readInt();
		count = input.readInt();
		bits = new byte[getNumBytes(size)];
		Arrays.fill(bits, (byte)-1);
		clearUnusedBits();
		int last = 0;
		for (int numCleared = size() - count(); numCleared > 0;)
		{
			last += input.readVInt();
			bits[last] = input.readByte();
			numCleared -= 8 - BYTE_COUNTS[bits[last] & 0xff];
			if (!$assertionsDisabled && numCleared < 0 && (last != bits.length - 1 || numCleared != -(8 - (size & 7))))
				throw new AssertionError();
		}

	}

	public volatile Object clone()
		throws CloneNotSupportedException
	{
		return clone();
	}

}
