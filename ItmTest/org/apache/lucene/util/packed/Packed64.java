// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Packed64.java

package org.apache.lucene.util.packed;

import java.io.IOException;
import java.util.Arrays;
import org.apache.lucene.store.DataInput;
import org.apache.lucene.util.RamUsageEstimator;

// Referenced classes of package org.apache.lucene.util.packed:
//			BulkOperation, PackedInts

class Packed64 extends PackedInts.MutableImpl
{

	static final int BLOCK_SIZE = 64;
	static final int BLOCK_BITS = 6;
	static final int MOD_MASK = 63;
	private final long blocks[];
	private final long maskRight;
	private final int bpvMinusBlockSize;
	static final boolean $assertionsDisabled = !org/apache/lucene/util/packed/Packed64.desiredAssertionStatus();

	public Packed64(int valueCount, int bitsPerValue)
	{
		this(new long[size(valueCount, bitsPerValue)], valueCount, bitsPerValue);
	}

	public Packed64(long blocks[], int valueCount, int bitsPerValue)
	{
		super(valueCount, bitsPerValue);
		this.blocks = blocks;
		maskRight = (-1L << 64 - bitsPerValue) >>> 64 - bitsPerValue;
		bpvMinusBlockSize = bitsPerValue - 64;
	}

	public Packed64(DataInput in, int valueCount, int bitsPerValue)
		throws IOException
	{
		super(valueCount, bitsPerValue);
		int size = size(valueCount, bitsPerValue);
		blocks = new long[size];
		for (int i = 0; i < size; i++)
			blocks[i] = in.readLong();

		maskRight = (-1L << 64 - bitsPerValue) >>> 64 - bitsPerValue;
		bpvMinusBlockSize = bitsPerValue - 64;
	}

	private static int size(int valueCount, int bitsPerValue)
	{
		long totBitCount = (long)valueCount * (long)bitsPerValue;
		return (int)(totBitCount / 64L + (long)(totBitCount % 64L != 0L ? 1 : 0));
	}

	public long get(int index)
	{
		long majorBitPos = (long)index * (long)bitsPerValue;
		int elementPos = (int)(majorBitPos >>> 6);
		long endBits = (majorBitPos & 63L) + (long)bpvMinusBlockSize;
		if (endBits <= 0L)
			return blocks[elementPos] >>> (int)(-endBits) & maskRight;
		else
			return (blocks[elementPos] << (int)endBits | blocks[elementPos + 1] >>> (int)(64L - endBits)) & maskRight;
	}

	public int get(int index, long arr[], int off, int len)
	{
		if (!$assertionsDisabled && len <= 0)
			throw new AssertionError((new StringBuilder()).append("len must be > 0 (got ").append(len).append(")").toString());
		if (!$assertionsDisabled && (index < 0 || index >= valueCount))
			throw new AssertionError();
		len = Math.min(len, valueCount - index);
		if (!$assertionsDisabled && off + len > arr.length)
			throw new AssertionError();
		int originalIndex = index;
		PackedInts.Decoder decoder = BulkOperation.of(PackedInts.Format.PACKED, bitsPerValue);
		int offsetInBlocks = index % decoder.valueCount();
		if (offsetInBlocks != 0)
		{
			for (int i = offsetInBlocks; i < decoder.valueCount() && len > 0; i++)
			{
				arr[off++] = get(index++);
				len--;
			}

			if (len == 0)
				return index - originalIndex;
		}
		if (!$assertionsDisabled && index % decoder.valueCount() != 0)
			throw new AssertionError();
		int blockIndex = (int)((long)index * (long)bitsPerValue) >>> 6;
		if (!$assertionsDisabled && ((long)index * (long)bitsPerValue & 63L) != 0L)
			throw new AssertionError();
		int iterations = len / decoder.valueCount();
		decoder.decode(blocks, blockIndex, arr, off, iterations);
		int gotValues = iterations * decoder.valueCount();
		index += gotValues;
		len -= gotValues;
		if (!$assertionsDisabled && len < 0)
			throw new AssertionError();
		if (index > originalIndex)
			return index - originalIndex;
		if (!$assertionsDisabled && index != originalIndex)
			throw new AssertionError();
		else
			return super.get(index, arr, off, len);
	}

	public void set(int index, long value)
	{
		long majorBitPos = (long)index * (long)bitsPerValue;
		int elementPos = (int)(majorBitPos >>> 6);
		long endBits = (majorBitPos & 63L) + (long)bpvMinusBlockSize;
		if (endBits <= 0L)
		{
			blocks[elementPos] = blocks[elementPos] & ~(maskRight << (int)(-endBits)) | value << (int)(-endBits);
			return;
		} else
		{
			blocks[elementPos] = blocks[elementPos] & ~(maskRight >>> (int)endBits) | value >>> (int)endBits;
			blocks[elementPos + 1] = blocks[elementPos + 1] & -1L >>> (int)endBits | value << (int)(64L - endBits);
			return;
		}
	}

	public int set(int index, long arr[], int off, int len)
	{
		if (!$assertionsDisabled && len <= 0)
			throw new AssertionError((new StringBuilder()).append("len must be > 0 (got ").append(len).append(")").toString());
		if (!$assertionsDisabled && (index < 0 || index >= valueCount))
			throw new AssertionError();
		len = Math.min(len, valueCount - index);
		if (!$assertionsDisabled && off + len > arr.length)
			throw new AssertionError();
		int originalIndex = index;
		PackedInts.Encoder encoder = BulkOperation.of(PackedInts.Format.PACKED, bitsPerValue);
		int offsetInBlocks = index % encoder.valueCount();
		if (offsetInBlocks != 0)
		{
			for (int i = offsetInBlocks; i < encoder.valueCount() && len > 0; i++)
			{
				set(index++, arr[off++]);
				len--;
			}

			if (len == 0)
				return index - originalIndex;
		}
		if (!$assertionsDisabled && index % encoder.valueCount() != 0)
			throw new AssertionError();
		int blockIndex = (int)((long)index * (long)bitsPerValue) >>> 6;
		if (!$assertionsDisabled && ((long)index * (long)bitsPerValue & 63L) != 0L)
			throw new AssertionError();
		int iterations = len / encoder.valueCount();
		encoder.encode(arr, off, blocks, blockIndex, iterations);
		int setValues = iterations * encoder.valueCount();
		index += setValues;
		len -= setValues;
		if (!$assertionsDisabled && len < 0)
			throw new AssertionError();
		if (index > originalIndex)
			return index - originalIndex;
		if (!$assertionsDisabled && index != originalIndex)
			throw new AssertionError();
		else
			return super.set(index, arr, off, len);
	}

	public String toString()
	{
		return (new StringBuilder()).append("Packed64(bitsPerValue=").append(bitsPerValue).append(", size=").append(size()).append(", elements.length=").append(blocks.length).append(")").toString();
	}

	public long ramBytesUsed()
	{
		return RamUsageEstimator.sizeOf(blocks);
	}

	public void fill(int fromIndex, int toIndex, long val)
	{
		if (!$assertionsDisabled && PackedInts.bitsRequired(val) > getBitsPerValue())
			throw new AssertionError();
		if (!$assertionsDisabled && fromIndex > toIndex)
			throw new AssertionError();
		int nAlignedValues = 64 / gcd(64, bitsPerValue);
		int span = toIndex - fromIndex;
		if (span <= 3 * nAlignedValues)
		{
			super.fill(fromIndex, toIndex, val);
			return;
		}
		int fromIndexModNAlignedValues = fromIndex % nAlignedValues;
		if (fromIndexModNAlignedValues != 0)
		{
			for (int i = fromIndexModNAlignedValues; i < nAlignedValues; i++)
				set(fromIndex++, val);

		}
		if (!$assertionsDisabled && fromIndex % nAlignedValues != 0)
			throw new AssertionError();
		int nAlignedBlocks = nAlignedValues * bitsPerValue >> 6;
		Packed64 values = new Packed64(nAlignedValues, bitsPerValue);
		for (int i = 0; i < nAlignedValues; i++)
			values.set(i, val);

		long nAlignedValuesBlocks[] = values.blocks;
		if (!$assertionsDisabled && nAlignedBlocks > nAlignedValuesBlocks.length)
			throw new AssertionError();
		int startBlock = (int)((long)fromIndex * (long)bitsPerValue >>> 6);
		int endBlock = (int)((long)toIndex * (long)bitsPerValue >>> 6);
		for (int block = startBlock; block < endBlock; block++)
		{
			long blockValue = nAlignedValuesBlocks[block % nAlignedBlocks];
			blocks[block] = blockValue;
		}

		for (int i = (int)(((long)endBlock << 6) / (long)bitsPerValue); i < toIndex; i++)
			set(i, val);

	}

	private static int gcd(int a, int b)
	{
		if (a < b)
			return gcd(b, a);
		if (b == 0)
			return a;
		else
			return gcd(b, a % b);
	}

	public void clear()
	{
		Arrays.fill(blocks, 0L);
	}

}
