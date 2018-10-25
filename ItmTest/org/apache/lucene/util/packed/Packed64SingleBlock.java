// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Packed64SingleBlock.java

package org.apache.lucene.util.packed;

import java.io.IOException;
import java.util.Arrays;
import org.apache.lucene.store.DataInput;
import org.apache.lucene.util.RamUsageEstimator;

// Referenced classes of package org.apache.lucene.util.packed:
//			BulkOperation, PackedInts

abstract class Packed64SingleBlock extends PackedInts.MutableImpl
{
	static class Packed64SingleBlock32 extends Packed64SingleBlock
	{

		public long get(int index)
		{
			int o = index >>> 1;
			int b = index & 1;
			int shift = b << 5;
			return blocks[o] >>> shift & 0xffffffffL;
		}

		public void set(int index, long value)
		{
			int o = index >>> 1;
			int b = index & 1;
			int shift = b << 5;
			blocks[o] = blocks[o] & ~(0xffffffffL << shift) | value << shift;
		}

		Packed64SingleBlock32(int valueCount)
		{
			super(valueCount, 32);
		}
	}

	static class Packed64SingleBlock21 extends Packed64SingleBlock
	{

		public long get(int index)
		{
			int o = index / 3;
			int b = index % 3;
			int shift = b * 21;
			return blocks[o] >>> shift & 0x1fffffL;
		}

		public void set(int index, long value)
		{
			int o = index / 3;
			int b = index % 3;
			int shift = b * 21;
			blocks[o] = blocks[o] & ~(0x1fffffL << shift) | value << shift;
		}

		Packed64SingleBlock21(int valueCount)
		{
			super(valueCount, 21);
		}
	}

	static class Packed64SingleBlock16 extends Packed64SingleBlock
	{

		public long get(int index)
		{
			int o = index >>> 2;
			int b = index & 3;
			int shift = b << 4;
			return blocks[o] >>> shift & 65535L;
		}

		public void set(int index, long value)
		{
			int o = index >>> 2;
			int b = index & 3;
			int shift = b << 4;
			blocks[o] = blocks[o] & ~(65535L << shift) | value << shift;
		}

		Packed64SingleBlock16(int valueCount)
		{
			super(valueCount, 16);
		}
	}

	static class Packed64SingleBlock12 extends Packed64SingleBlock
	{

		public long get(int index)
		{
			int o = index / 5;
			int b = index % 5;
			int shift = b * 12;
			return blocks[o] >>> shift & 4095L;
		}

		public void set(int index, long value)
		{
			int o = index / 5;
			int b = index % 5;
			int shift = b * 12;
			blocks[o] = blocks[o] & ~(4095L << shift) | value << shift;
		}

		Packed64SingleBlock12(int valueCount)
		{
			super(valueCount, 12);
		}
	}

	static class Packed64SingleBlock10 extends Packed64SingleBlock
	{

		public long get(int index)
		{
			int o = index / 6;
			int b = index % 6;
			int shift = b * 10;
			return blocks[o] >>> shift & 1023L;
		}

		public void set(int index, long value)
		{
			int o = index / 6;
			int b = index % 6;
			int shift = b * 10;
			blocks[o] = blocks[o] & ~(1023L << shift) | value << shift;
		}

		Packed64SingleBlock10(int valueCount)
		{
			super(valueCount, 10);
		}
	}

	static class Packed64SingleBlock9 extends Packed64SingleBlock
	{

		public long get(int index)
		{
			int o = index / 7;
			int b = index % 7;
			int shift = b * 9;
			return blocks[o] >>> shift & 511L;
		}

		public void set(int index, long value)
		{
			int o = index / 7;
			int b = index % 7;
			int shift = b * 9;
			blocks[o] = blocks[o] & ~(511L << shift) | value << shift;
		}

		Packed64SingleBlock9(int valueCount)
		{
			super(valueCount, 9);
		}
	}

	static class Packed64SingleBlock8 extends Packed64SingleBlock
	{

		public long get(int index)
		{
			int o = index >>> 3;
			int b = index & 7;
			int shift = b << 3;
			return blocks[o] >>> shift & 255L;
		}

		public void set(int index, long value)
		{
			int o = index >>> 3;
			int b = index & 7;
			int shift = b << 3;
			blocks[o] = blocks[o] & ~(255L << shift) | value << shift;
		}

		Packed64SingleBlock8(int valueCount)
		{
			super(valueCount, 8);
		}
	}

	static class Packed64SingleBlock7 extends Packed64SingleBlock
	{

		public long get(int index)
		{
			int o = index / 9;
			int b = index % 9;
			int shift = b * 7;
			return blocks[o] >>> shift & 127L;
		}

		public void set(int index, long value)
		{
			int o = index / 9;
			int b = index % 9;
			int shift = b * 7;
			blocks[o] = blocks[o] & ~(127L << shift) | value << shift;
		}

		Packed64SingleBlock7(int valueCount)
		{
			super(valueCount, 7);
		}
	}

	static class Packed64SingleBlock6 extends Packed64SingleBlock
	{

		public long get(int index)
		{
			int o = index / 10;
			int b = index % 10;
			int shift = b * 6;
			return blocks[o] >>> shift & 63L;
		}

		public void set(int index, long value)
		{
			int o = index / 10;
			int b = index % 10;
			int shift = b * 6;
			blocks[o] = blocks[o] & ~(63L << shift) | value << shift;
		}

		Packed64SingleBlock6(int valueCount)
		{
			super(valueCount, 6);
		}
	}

	static class Packed64SingleBlock5 extends Packed64SingleBlock
	{

		public long get(int index)
		{
			int o = index / 12;
			int b = index % 12;
			int shift = b * 5;
			return blocks[o] >>> shift & 31L;
		}

		public void set(int index, long value)
		{
			int o = index / 12;
			int b = index % 12;
			int shift = b * 5;
			blocks[o] = blocks[o] & ~(31L << shift) | value << shift;
		}

		Packed64SingleBlock5(int valueCount)
		{
			super(valueCount, 5);
		}
	}

	static class Packed64SingleBlock4 extends Packed64SingleBlock
	{

		public long get(int index)
		{
			int o = index >>> 4;
			int b = index & 0xf;
			int shift = b << 2;
			return blocks[o] >>> shift & 15L;
		}

		public void set(int index, long value)
		{
			int o = index >>> 4;
			int b = index & 0xf;
			int shift = b << 2;
			blocks[o] = blocks[o] & ~(15L << shift) | value << shift;
		}

		Packed64SingleBlock4(int valueCount)
		{
			super(valueCount, 4);
		}
	}

	static class Packed64SingleBlock3 extends Packed64SingleBlock
	{

		public long get(int index)
		{
			int o = index / 21;
			int b = index % 21;
			int shift = b * 3;
			return blocks[o] >>> shift & 7L;
		}

		public void set(int index, long value)
		{
			int o = index / 21;
			int b = index % 21;
			int shift = b * 3;
			blocks[o] = blocks[o] & ~(7L << shift) | value << shift;
		}

		Packed64SingleBlock3(int valueCount)
		{
			super(valueCount, 3);
		}
	}

	static class Packed64SingleBlock2 extends Packed64SingleBlock
	{

		public long get(int index)
		{
			int o = index >>> 5;
			int b = index & 0x1f;
			int shift = b << 1;
			return blocks[o] >>> shift & 3L;
		}

		public void set(int index, long value)
		{
			int o = index >>> 5;
			int b = index & 0x1f;
			int shift = b << 1;
			blocks[o] = blocks[o] & ~(3L << shift) | value << shift;
		}

		Packed64SingleBlock2(int valueCount)
		{
			super(valueCount, 2);
		}
	}

	static class Packed64SingleBlock1 extends Packed64SingleBlock
	{

		public long get(int index)
		{
			int o = index >>> 6;
			int b = index & 0x3f;
			int shift = b << 0;
			return blocks[o] >>> shift & 1L;
		}

		public void set(int index, long value)
		{
			int o = index >>> 6;
			int b = index & 0x3f;
			int shift = b << 0;
			blocks[o] = blocks[o] & ~(1L << shift) | value << shift;
		}

		Packed64SingleBlock1(int valueCount)
		{
			super(valueCount, 1);
		}
	}


	public static final int MAX_SUPPORTED_BITS_PER_VALUE = 32;
	private static final int SUPPORTED_BITS_PER_VALUE[] = {
		1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 
		12, 16, 21, 32
	};
	final long blocks[];
	static final boolean $assertionsDisabled = !org/apache/lucene/util/packed/Packed64SingleBlock.desiredAssertionStatus();

	public static boolean isSupported(int bitsPerValue)
	{
		return Arrays.binarySearch(SUPPORTED_BITS_PER_VALUE, bitsPerValue) >= 0;
	}

	private static int requiredCapacity(int valueCount, int valuesPerBlock)
	{
		return valueCount / valuesPerBlock + (valueCount % valuesPerBlock != 0 ? 1 : 0);
	}

	Packed64SingleBlock(int valueCount, int bitsPerValue)
	{
		super(valueCount, bitsPerValue);
		if (!$assertionsDisabled && !isSupported(bitsPerValue))
		{
			throw new AssertionError();
		} else
		{
			int valuesPerBlock = 64 / bitsPerValue;
			blocks = new long[requiredCapacity(valueCount, valuesPerBlock)];
			return;
		}
	}

	public void clear()
	{
		Arrays.fill(blocks, 0L);
	}

	public long ramBytesUsed()
	{
		return RamUsageEstimator.sizeOf(blocks);
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
		int valuesPerBlock = 64 / bitsPerValue;
		int offsetInBlock = index % valuesPerBlock;
		if (offsetInBlock != 0)
		{
			for (int i = offsetInBlock; i < valuesPerBlock && len > 0; i++)
			{
				arr[off++] = get(index++);
				len--;
			}

			if (len == 0)
				return index - originalIndex;
		}
		if (!$assertionsDisabled && index % valuesPerBlock != 0)
			throw new AssertionError();
		PackedInts.Decoder decoder = BulkOperation.of(PackedInts.Format.PACKED_SINGLE_BLOCK, bitsPerValue);
		if (!$assertionsDisabled && decoder.blockCount() != 1)
			throw new AssertionError();
		if (!$assertionsDisabled && decoder.valueCount() != valuesPerBlock)
			throw new AssertionError();
		int blockIndex = index / valuesPerBlock;
		int nblocks = (index + len) / valuesPerBlock - blockIndex;
		decoder.decode(blocks, blockIndex, arr, off, nblocks);
		int diff = nblocks * valuesPerBlock;
		index += diff;
		len -= diff;
		if (index > originalIndex)
			return index - originalIndex;
		if (!$assertionsDisabled && index != originalIndex)
			throw new AssertionError();
		else
			return super.get(index, arr, off, len);
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
		int valuesPerBlock = 64 / bitsPerValue;
		int offsetInBlock = index % valuesPerBlock;
		if (offsetInBlock != 0)
		{
			for (int i = offsetInBlock; i < valuesPerBlock && len > 0; i++)
			{
				set(index++, arr[off++]);
				len--;
			}

			if (len == 0)
				return index - originalIndex;
		}
		if (!$assertionsDisabled && index % valuesPerBlock != 0)
			throw new AssertionError();
		BulkOperation op = BulkOperation.of(PackedInts.Format.PACKED_SINGLE_BLOCK, bitsPerValue);
		if (!$assertionsDisabled && op.blockCount() != 1)
			throw new AssertionError();
		if (!$assertionsDisabled && op.valueCount() != valuesPerBlock)
			throw new AssertionError();
		int blockIndex = index / valuesPerBlock;
		int nblocks = (index + len) / valuesPerBlock - blockIndex;
		op.encode(arr, off, blocks, blockIndex, nblocks);
		int diff = nblocks * valuesPerBlock;
		index += diff;
		len -= diff;
		if (index > originalIndex)
			return index - originalIndex;
		if (!$assertionsDisabled && index != originalIndex)
			throw new AssertionError();
		else
			return super.set(index, arr, off, len);
	}

	public void fill(int fromIndex, int toIndex, long val)
	{
		if (!$assertionsDisabled && fromIndex < 0)
			throw new AssertionError();
		if (!$assertionsDisabled && fromIndex > toIndex)
			throw new AssertionError();
		if (!$assertionsDisabled && PackedInts.bitsRequired(val) > bitsPerValue)
			throw new AssertionError();
		int valuesPerBlock = 64 / bitsPerValue;
		if (toIndex - fromIndex <= valuesPerBlock << 1)
		{
			super.fill(fromIndex, toIndex, val);
			return;
		}
		int fromOffsetInBlock = fromIndex % valuesPerBlock;
		if (fromOffsetInBlock != 0)
		{
			for (int i = fromOffsetInBlock; i < valuesPerBlock; i++)
				set(fromIndex++, val);

			if (!$assertionsDisabled && fromIndex % valuesPerBlock != 0)
				throw new AssertionError();
		}
		int fromBlock = fromIndex / valuesPerBlock;
		int toBlock = toIndex / valuesPerBlock;
		if (!$assertionsDisabled && fromBlock * valuesPerBlock != fromIndex)
			throw new AssertionError();
		long blockValue = 0L;
		for (int i = 0; i < valuesPerBlock; i++)
			blockValue |= val << i * bitsPerValue;

		Arrays.fill(blocks, fromBlock, toBlock, blockValue);
		for (int i = valuesPerBlock * toBlock; i < toIndex; i++)
			set(i, val);

	}

	protected PackedInts.Format getFormat()
	{
		return PackedInts.Format.PACKED_SINGLE_BLOCK;
	}

	public String toString()
	{
		return (new StringBuilder()).append(getClass().getSimpleName()).append("(bitsPerValue=").append(bitsPerValue).append(", size=").append(size()).append(", elements.length=").append(blocks.length).append(")").toString();
	}

	public static Packed64SingleBlock create(DataInput in, int valueCount, int bitsPerValue)
		throws IOException
	{
		Packed64SingleBlock reader = create(valueCount, bitsPerValue);
		for (int i = 0; i < reader.blocks.length; i++)
			reader.blocks[i] = in.readLong();

		return reader;
	}

	public static Packed64SingleBlock create(int valueCount, int bitsPerValue)
	{
		switch (bitsPerValue)
		{
		case 1: // '\001'
			return new Packed64SingleBlock1(valueCount);

		case 2: // '\002'
			return new Packed64SingleBlock2(valueCount);

		case 3: // '\003'
			return new Packed64SingleBlock3(valueCount);

		case 4: // '\004'
			return new Packed64SingleBlock4(valueCount);

		case 5: // '\005'
			return new Packed64SingleBlock5(valueCount);

		case 6: // '\006'
			return new Packed64SingleBlock6(valueCount);

		case 7: // '\007'
			return new Packed64SingleBlock7(valueCount);

		case 8: // '\b'
			return new Packed64SingleBlock8(valueCount);

		case 9: // '\t'
			return new Packed64SingleBlock9(valueCount);

		case 10: // '\n'
			return new Packed64SingleBlock10(valueCount);

		case 12: // '\f'
			return new Packed64SingleBlock12(valueCount);

		case 16: // '\020'
			return new Packed64SingleBlock16(valueCount);

		case 21: // '\025'
			return new Packed64SingleBlock21(valueCount);

		case 32: // ' '
			return new Packed64SingleBlock32(valueCount);

		case 11: // '\013'
		case 13: // '\r'
		case 14: // '\016'
		case 15: // '\017'
		case 17: // '\021'
		case 18: // '\022'
		case 19: // '\023'
		case 20: // '\024'
		case 22: // '\026'
		case 23: // '\027'
		case 24: // '\030'
		case 25: // '\031'
		case 26: // '\032'
		case 27: // '\033'
		case 28: // '\034'
		case 29: // '\035'
		case 30: // '\036'
		case 31: // '\037'
		default:
			throw new IllegalArgumentException("Unsupported number of bits per value: 32");
		}
	}

}
