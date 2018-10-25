// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BulkOperationPackedSingleBlock.java

package org.apache.lucene.util.packed;


// Referenced classes of package org.apache.lucene.util.packed:
//			BulkOperation

final class BulkOperationPackedSingleBlock extends BulkOperation
{

	private static final int BLOCK_COUNT = 1;
	private final int bitsPerValue;
	private final int valueCount;
	private final long mask;

	public BulkOperationPackedSingleBlock(int bitsPerValue)
	{
		this.bitsPerValue = bitsPerValue;
		valueCount = 64 / bitsPerValue;
		mask = (1L << bitsPerValue) - 1L;
	}

	public final int blockCount()
	{
		return 1;
	}

	public int valueCount()
	{
		return valueCount;
	}

	private static long readLong(byte blocks[], int blocksOffset)
	{
		return ((long)blocks[blocksOffset++] & 255L) << 56 | ((long)blocks[blocksOffset++] & 255L) << 48 | ((long)blocks[blocksOffset++] & 255L) << 40 | ((long)blocks[blocksOffset++] & 255L) << 32 | ((long)blocks[blocksOffset++] & 255L) << 24 | ((long)blocks[blocksOffset++] & 255L) << 16 | ((long)blocks[blocksOffset++] & 255L) << 8 | (long)blocks[blocksOffset++] & 255L;
	}

	private int decode(long block, long values[], int valuesOffset)
	{
		values[valuesOffset++] = block & mask;
		for (int j = 1; j < valueCount; j++)
		{
			block >>>= bitsPerValue;
			values[valuesOffset++] = block & mask;
		}

		return valuesOffset;
	}

	private int decode(long block, int values[], int valuesOffset)
	{
		values[valuesOffset++] = (int)(block & mask);
		for (int j = 1; j < valueCount; j++)
		{
			block >>>= bitsPerValue;
			values[valuesOffset++] = (int)(block & mask);
		}

		return valuesOffset;
	}

	private long encode(long values[], int valuesOffset)
	{
		long block = values[valuesOffset++];
		for (int j = 1; j < valueCount; j++)
			block |= values[valuesOffset++] << j * bitsPerValue;

		return block;
	}

	private long encode(int values[], int valuesOffset)
	{
		long block = (long)values[valuesOffset++] & 0xffffffffL;
		for (int j = 1; j < valueCount; j++)
			block |= ((long)values[valuesOffset++] & 0xffffffffL) << j * bitsPerValue;

		return block;
	}

	public void decode(long blocks[], int blocksOffset, long values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < iterations; i++)
		{
			long block = blocks[blocksOffset++];
			valuesOffset = decode(block, values, valuesOffset);
		}

	}

	public void decode(byte blocks[], int blocksOffset, long values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < iterations; i++)
		{
			long block = readLong(blocks, blocksOffset);
			blocksOffset += 8;
			valuesOffset = decode(block, values, valuesOffset);
		}

	}

	public void decode(long blocks[], int blocksOffset, int values[], int valuesOffset, int iterations)
	{
		if (bitsPerValue > 32)
			throw new UnsupportedOperationException((new StringBuilder()).append("Cannot decode ").append(bitsPerValue).append("-bits values into an int[]").toString());
		for (int i = 0; i < iterations; i++)
		{
			long block = blocks[blocksOffset++];
			valuesOffset = decode(block, values, valuesOffset);
		}

	}

	public void decode(byte blocks[], int blocksOffset, int values[], int valuesOffset, int iterations)
	{
		if (bitsPerValue > 32)
			throw new UnsupportedOperationException((new StringBuilder()).append("Cannot decode ").append(bitsPerValue).append("-bits values into an int[]").toString());
		for (int i = 0; i < iterations; i++)
		{
			long block = readLong(blocks, blocksOffset);
			blocksOffset += 8;
			valuesOffset = decode(block, values, valuesOffset);
		}

	}

	public void encode(long values[], int valuesOffset, long blocks[], int blocksOffset, int iterations)
	{
		for (int i = 0; i < iterations; i++)
		{
			blocks[blocksOffset++] = encode(values, valuesOffset);
			valuesOffset += valueCount;
		}

	}

	public void encode(int values[], int valuesOffset, long blocks[], int blocksOffset, int iterations)
	{
		for (int i = 0; i < iterations; i++)
		{
			blocks[blocksOffset++] = encode(values, valuesOffset);
			valuesOffset += valueCount;
		}

	}

	public void encode(long values[], int valuesOffset, byte blocks[], int blocksOffset, int iterations)
	{
		for (int i = 0; i < iterations; i++)
		{
			long block = encode(values, valuesOffset);
			valuesOffset += valueCount;
			blocksOffset = writeLong(block, blocks, blocksOffset);
		}

	}

	public void encode(int values[], int valuesOffset, byte blocks[], int blocksOffset, int iterations)
	{
		for (int i = 0; i < iterations; i++)
		{
			long block = encode(values, valuesOffset);
			valuesOffset += valueCount;
			blocksOffset = writeLong(block, blocks, blocksOffset);
		}

	}
}
