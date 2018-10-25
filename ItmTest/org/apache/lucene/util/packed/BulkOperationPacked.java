// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BulkOperationPacked.java

package org.apache.lucene.util.packed;


// Referenced classes of package org.apache.lucene.util.packed:
//			BulkOperation

class BulkOperationPacked extends BulkOperation
{

	private final int bitsPerValue;
	private final int blockCount;
	private final int valueCount;
	private final long mask;
	static final boolean $assertionsDisabled = !org/apache/lucene/util/packed/BulkOperationPacked.desiredAssertionStatus();

	public BulkOperationPacked(int bitsPerValue)
	{
		this.bitsPerValue = bitsPerValue;
		if (!$assertionsDisabled && (bitsPerValue <= 0 || bitsPerValue > 64))
			throw new AssertionError();
		int blocks;
		for (blocks = bitsPerValue; (blocks & 1) == 0; blocks >>>= 1);
		blockCount = blocks;
		valueCount = (64 * blockCount) / bitsPerValue;
		if (bitsPerValue == 64)
			mask = -1L;
		else
			mask = (1L << bitsPerValue) - 1L;
		if (!$assertionsDisabled && valueCount * bitsPerValue != 64 * blockCount)
			throw new AssertionError();
		else
			return;
	}

	public int blockCount()
	{
		return blockCount;
	}

	public int valueCount()
	{
		return valueCount;
	}

	public void decode(long blocks[], int blocksOffset, long values[], int valuesOffset, int iterations)
	{
		int bitsLeft = 64;
		for (int i = 0; i < valueCount * iterations; i++)
		{
			bitsLeft -= bitsPerValue;
			if (bitsLeft < 0)
			{
				values[valuesOffset++] = (blocks[blocksOffset++] & (1L << bitsPerValue + bitsLeft) - 1L) << -bitsLeft | blocks[blocksOffset] >>> 64 + bitsLeft;
				bitsLeft += 64;
			} else
			{
				values[valuesOffset++] = blocks[blocksOffset] >>> bitsLeft & mask;
			}
		}

	}

	public void decode(byte blocks[], int blocksOffset, long values[], int valuesOffset, int iterations)
	{
		int blockBitsLeft = 8;
		int valueBitsLeft = bitsPerValue;
		long nextValue = 0L;
		for (int end = valuesOffset + iterations * valueCount; valuesOffset < end;)
			if (valueBitsLeft > blockBitsLeft)
			{
				nextValue |= ((long)blocks[blocksOffset++] & (1L << blockBitsLeft) - 1L) << valueBitsLeft - blockBitsLeft;
				valueBitsLeft -= blockBitsLeft;
				blockBitsLeft = 8;
			} else
			{
				nextValue |= ((long)blocks[blocksOffset] & 255L) >>> blockBitsLeft - valueBitsLeft & (1L << valueBitsLeft) - 1L;
				values[valuesOffset++] = nextValue;
				nextValue = 0L;
				blockBitsLeft -= valueBitsLeft;
				valueBitsLeft = bitsPerValue;
			}

	}

	public void decode(long blocks[], int blocksOffset, int values[], int valuesOffset, int iterations)
	{
		if (bitsPerValue > 32)
			throw new UnsupportedOperationException((new StringBuilder()).append("Cannot decode ").append(bitsPerValue).append("-bits values into an int[]").toString());
		int bitsLeft = 64;
		for (int i = 0; i < valueCount * iterations; i++)
		{
			bitsLeft -= bitsPerValue;
			if (bitsLeft < 0)
			{
				values[valuesOffset++] = (int)((blocks[blocksOffset++] & (1L << bitsPerValue + bitsLeft) - 1L) << -bitsLeft | blocks[blocksOffset] >>> 64 + bitsLeft);
				bitsLeft += 64;
			} else
			{
				values[valuesOffset++] = (int)(blocks[blocksOffset] >>> bitsLeft & mask);
			}
		}

	}

	public void decode(byte blocks[], int blocksOffset, int values[], int valuesOffset, int iterations)
	{
		if (bitsPerValue > 32)
			throw new UnsupportedOperationException((new StringBuilder()).append("Cannot decode ").append(bitsPerValue).append("-bits values into an int[]").toString());
		int blockBitsLeft = 8;
		int valueBitsLeft = bitsPerValue;
		int nextValue = 0;
		for (int end = valuesOffset + iterations * valueCount; valuesOffset < end;)
			if (valueBitsLeft > blockBitsLeft)
			{
				nextValue = (int)((long)nextValue | ((long)blocks[blocksOffset++] & (1L << blockBitsLeft) - 1L) << valueBitsLeft - blockBitsLeft);
				valueBitsLeft -= blockBitsLeft;
				blockBitsLeft = 8;
			} else
			{
				nextValue = (int)((long)nextValue | ((long)blocks[blocksOffset] & 255L) >>> blockBitsLeft - valueBitsLeft & (1L << valueBitsLeft) - 1L);
				values[valuesOffset++] = nextValue;
				nextValue = 0;
				blockBitsLeft -= valueBitsLeft;
				valueBitsLeft = bitsPerValue;
			}

	}

	public void encode(long values[], int valuesOffset, long blocks[], int blocksOffset, int iterations)
	{
		long nextBlock = 0L;
		int bitsLeft = 64;
		for (int i = 0; i < valueCount * iterations; i++)
		{
			bitsLeft -= bitsPerValue;
			if (bitsLeft > 0)
			{
				nextBlock |= values[valuesOffset++] << bitsLeft;
				continue;
			}
			if (bitsLeft == 0)
			{
				nextBlock |= values[valuesOffset++];
				blocks[blocksOffset++] = nextBlock;
				nextBlock = 0L;
				bitsLeft = 64;
			} else
			{
				nextBlock |= values[valuesOffset] >>> -bitsLeft;
				blocks[blocksOffset++] = nextBlock;
				nextBlock = (values[valuesOffset++] & (1L << -bitsLeft) - 1L) << 64 + bitsLeft;
				bitsLeft += 64;
			}
		}

	}

	public void encode(int values[], int valuesOffset, long blocks[], int blocksOffset, int iterations)
	{
		long nextBlock = 0L;
		int bitsLeft = 64;
		for (int i = 0; i < valueCount * iterations; i++)
		{
			bitsLeft -= bitsPerValue;
			if (bitsLeft > 0)
			{
				nextBlock |= ((long)values[valuesOffset++] & 0xffffffffL) << bitsLeft;
				continue;
			}
			if (bitsLeft == 0)
			{
				nextBlock |= (long)values[valuesOffset++] & 0xffffffffL;
				blocks[blocksOffset++] = nextBlock;
				nextBlock = 0L;
				bitsLeft = 64;
			} else
			{
				nextBlock |= ((long)values[valuesOffset] & 0xffffffffL) >>> -bitsLeft;
				blocks[blocksOffset++] = nextBlock;
				nextBlock = ((long)values[valuesOffset++] & (1L << -bitsLeft) - 1L) << 64 + bitsLeft;
				bitsLeft += 64;
			}
		}

	}

	public void encode(long values[], int valuesOffset, byte blocks[], int blocksOffset, int iterations)
	{
		long nextBlock = 0L;
		int bitsLeft = 64;
		for (int i = 0; i < valueCount * iterations; i++)
		{
			bitsLeft -= bitsPerValue;
			if (bitsLeft > 0)
			{
				nextBlock |= values[valuesOffset++] << bitsLeft;
				continue;
			}
			if (bitsLeft == 0)
			{
				nextBlock |= values[valuesOffset++];
				blocksOffset = writeLong(nextBlock, blocks, blocksOffset);
				nextBlock = 0L;
				bitsLeft = 64;
			} else
			{
				nextBlock |= values[valuesOffset] >>> -bitsLeft;
				blocksOffset = writeLong(nextBlock, blocks, blocksOffset);
				nextBlock = (values[valuesOffset++] & (1L << -bitsLeft) - 1L) << 64 + bitsLeft;
				bitsLeft += 64;
			}
		}

	}

	public void encode(int values[], int valuesOffset, byte blocks[], int blocksOffset, int iterations)
	{
		long nextBlock = 0L;
		int bitsLeft = 64;
		for (int i = 0; i < valueCount * iterations; i++)
		{
			bitsLeft -= bitsPerValue;
			if (bitsLeft > 0)
			{
				nextBlock |= ((long)values[valuesOffset++] & 0xffffffffL) << bitsLeft;
				continue;
			}
			if (bitsLeft == 0)
			{
				nextBlock |= (long)values[valuesOffset++] & 0xffffffffL;
				blocksOffset = writeLong(nextBlock, blocks, blocksOffset);
				nextBlock = 0L;
				bitsLeft = 64;
			} else
			{
				nextBlock |= ((long)values[valuesOffset] & 0xffffffffL) >>> -bitsLeft;
				blocksOffset = writeLong(nextBlock, blocks, blocksOffset);
				nextBlock = ((long)values[valuesOffset++] & (1L << -bitsLeft) - 1L) << 64 + bitsLeft;
				bitsLeft += 64;
			}
		}

	}

}
