// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Packed8ThreeBlocks.java

package org.apache.lucene.util.packed;

import java.io.IOException;
import java.util.Arrays;
import org.apache.lucene.store.DataInput;
import org.apache.lucene.util.RamUsageEstimator;

// Referenced classes of package org.apache.lucene.util.packed:
//			PackedInts

final class Packed8ThreeBlocks extends PackedInts.MutableImpl
{

	final byte blocks[];
	public static final int MAX_SIZE = 0x2aaaaaaa;
	static final boolean $assertionsDisabled = !org/apache/lucene/util/packed/Packed8ThreeBlocks.desiredAssertionStatus();

	Packed8ThreeBlocks(int valueCount)
	{
		super(valueCount, 24);
		if (valueCount > 0x2aaaaaaa)
		{
			throw new ArrayIndexOutOfBoundsException("MAX_SIZE exceeded");
		} else
		{
			blocks = new byte[valueCount * 3];
			return;
		}
	}

	Packed8ThreeBlocks(DataInput in, int valueCount)
		throws IOException
	{
		this(valueCount);
		for (int i = 0; i < 3 * valueCount; i++)
			blocks[i] = in.readByte();

		int mod = blocks.length % 8;
		if (mod != 0)
		{
			for (int i = mod; i < 8; i++)
				in.readByte();

		}
	}

	public long get(int index)
	{
		int o = index * 3;
		return ((long)blocks[o] & 255L) << 16 | ((long)blocks[o + 1] & 255L) << 8 | (long)blocks[o + 2] & 255L;
	}

	public int get(int index, long arr[], int off, int len)
	{
		if (!$assertionsDisabled && len <= 0)
			throw new AssertionError((new StringBuilder()).append("len must be > 0 (got ").append(len).append(")").toString());
		if (!$assertionsDisabled && (index < 0 || index >= valueCount))
			throw new AssertionError();
		if (!$assertionsDisabled && off + len > arr.length)
			throw new AssertionError();
		int gets = Math.min(valueCount - index, len);
		int i = index * 3;
		for (int end = (index + gets) * 3; i < end; i += 3)
			arr[off++] = ((long)blocks[i] & 255L) << 16 | ((long)blocks[i + 1] & 255L) << 8 | (long)blocks[i + 2] & 255L;

		return gets;
	}

	public void set(int index, long value)
	{
		int o = index * 3;
		blocks[o] = (byte)(int)(value >>> 16);
		blocks[o + 1] = (byte)(int)(value >>> 8);
		blocks[o + 2] = (byte)(int)value;
	}

	public int set(int index, long arr[], int off, int len)
	{
		if (!$assertionsDisabled && len <= 0)
			throw new AssertionError((new StringBuilder()).append("len must be > 0 (got ").append(len).append(")").toString());
		if (!$assertionsDisabled && (index < 0 || index >= valueCount))
			throw new AssertionError();
		if (!$assertionsDisabled && off + len > arr.length)
			throw new AssertionError();
		int sets = Math.min(valueCount - index, len);
		int i = off;
		int o = index * 3;
		for (int end = off + sets; i < end; i++)
		{
			long value = arr[i];
			blocks[o++] = (byte)(int)(value >>> 16);
			blocks[o++] = (byte)(int)(value >>> 8);
			blocks[o++] = (byte)(int)value;
		}

		return sets;
	}

	public void fill(int fromIndex, int toIndex, long val)
	{
		byte block1 = (byte)(int)(val >>> 16);
		byte block2 = (byte)(int)(val >>> 8);
		byte block3 = (byte)(int)val;
		int i = fromIndex * 3;
		for (int end = toIndex * 3; i < end; i += 3)
		{
			blocks[i] = block1;
			blocks[i + 1] = block2;
			blocks[i + 2] = block3;
		}

	}

	public void clear()
	{
		Arrays.fill(blocks, (byte)0);
	}

	public long ramBytesUsed()
	{
		return RamUsageEstimator.sizeOf(blocks);
	}

	public String toString()
	{
		return (new StringBuilder()).append(getClass().getSimpleName()).append("(bitsPerValue=").append(bitsPerValue).append(", size=").append(size()).append(", elements.length=").append(blocks.length).append(")").toString();
	}

}
