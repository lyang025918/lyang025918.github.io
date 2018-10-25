// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BulkOperationPacked6.java

package org.apache.lucene.util.packed;


// Referenced classes of package org.apache.lucene.util.packed:
//			BulkOperationPacked

final class BulkOperationPacked6 extends BulkOperationPacked
{

	static final boolean $assertionsDisabled = !org/apache/lucene/util/packed/BulkOperationPacked6.desiredAssertionStatus();

	public BulkOperationPacked6()
	{
		super(6);
		if (!$assertionsDisabled && blockCount() != 3)
			throw new AssertionError();
		if (!$assertionsDisabled && valueCount() != 32)
			throw new AssertionError();
		else
			return;
	}

	public void decode(long blocks[], int blocksOffset, int values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < iterations; i++)
		{
			long block0 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)(block0 >>> 58);
			values[valuesOffset++] = (int)(block0 >>> 52 & 63L);
			values[valuesOffset++] = (int)(block0 >>> 46 & 63L);
			values[valuesOffset++] = (int)(block0 >>> 40 & 63L);
			values[valuesOffset++] = (int)(block0 >>> 34 & 63L);
			values[valuesOffset++] = (int)(block0 >>> 28 & 63L);
			values[valuesOffset++] = (int)(block0 >>> 22 & 63L);
			values[valuesOffset++] = (int)(block0 >>> 16 & 63L);
			values[valuesOffset++] = (int)(block0 >>> 10 & 63L);
			values[valuesOffset++] = (int)(block0 >>> 4 & 63L);
			long block1 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block0 & 15L) << 2 | block1 >>> 62);
			values[valuesOffset++] = (int)(block1 >>> 56 & 63L);
			values[valuesOffset++] = (int)(block1 >>> 50 & 63L);
			values[valuesOffset++] = (int)(block1 >>> 44 & 63L);
			values[valuesOffset++] = (int)(block1 >>> 38 & 63L);
			values[valuesOffset++] = (int)(block1 >>> 32 & 63L);
			values[valuesOffset++] = (int)(block1 >>> 26 & 63L);
			values[valuesOffset++] = (int)(block1 >>> 20 & 63L);
			values[valuesOffset++] = (int)(block1 >>> 14 & 63L);
			values[valuesOffset++] = (int)(block1 >>> 8 & 63L);
			values[valuesOffset++] = (int)(block1 >>> 2 & 63L);
			long block2 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block1 & 3L) << 4 | block2 >>> 60);
			values[valuesOffset++] = (int)(block2 >>> 54 & 63L);
			values[valuesOffset++] = (int)(block2 >>> 48 & 63L);
			values[valuesOffset++] = (int)(block2 >>> 42 & 63L);
			values[valuesOffset++] = (int)(block2 >>> 36 & 63L);
			values[valuesOffset++] = (int)(block2 >>> 30 & 63L);
			values[valuesOffset++] = (int)(block2 >>> 24 & 63L);
			values[valuesOffset++] = (int)(block2 >>> 18 & 63L);
			values[valuesOffset++] = (int)(block2 >>> 12 & 63L);
			values[valuesOffset++] = (int)(block2 >>> 6 & 63L);
			values[valuesOffset++] = (int)(block2 & 63L);
		}

	}

	public void decode(byte blocks[], int blocksOffset, int values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < 8 * iterations; i++)
		{
			int byte0 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = byte0 >>> 2;
			int byte1 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte0 & 3) << 4 | byte1 >>> 4;
			int byte2 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte1 & 0xf) << 2 | byte2 >>> 6;
			values[valuesOffset++] = byte2 & 0x3f;
		}

	}

	public void decode(long blocks[], int blocksOffset, long values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < iterations; i++)
		{
			long block0 = blocks[blocksOffset++];
			values[valuesOffset++] = block0 >>> 58;
			values[valuesOffset++] = block0 >>> 52 & 63L;
			values[valuesOffset++] = block0 >>> 46 & 63L;
			values[valuesOffset++] = block0 >>> 40 & 63L;
			values[valuesOffset++] = block0 >>> 34 & 63L;
			values[valuesOffset++] = block0 >>> 28 & 63L;
			values[valuesOffset++] = block0 >>> 22 & 63L;
			values[valuesOffset++] = block0 >>> 16 & 63L;
			values[valuesOffset++] = block0 >>> 10 & 63L;
			values[valuesOffset++] = block0 >>> 4 & 63L;
			long block1 = blocks[blocksOffset++];
			values[valuesOffset++] = (block0 & 15L) << 2 | block1 >>> 62;
			values[valuesOffset++] = block1 >>> 56 & 63L;
			values[valuesOffset++] = block1 >>> 50 & 63L;
			values[valuesOffset++] = block1 >>> 44 & 63L;
			values[valuesOffset++] = block1 >>> 38 & 63L;
			values[valuesOffset++] = block1 >>> 32 & 63L;
			values[valuesOffset++] = block1 >>> 26 & 63L;
			values[valuesOffset++] = block1 >>> 20 & 63L;
			values[valuesOffset++] = block1 >>> 14 & 63L;
			values[valuesOffset++] = block1 >>> 8 & 63L;
			values[valuesOffset++] = block1 >>> 2 & 63L;
			long block2 = blocks[blocksOffset++];
			values[valuesOffset++] = (block1 & 3L) << 4 | block2 >>> 60;
			values[valuesOffset++] = block2 >>> 54 & 63L;
			values[valuesOffset++] = block2 >>> 48 & 63L;
			values[valuesOffset++] = block2 >>> 42 & 63L;
			values[valuesOffset++] = block2 >>> 36 & 63L;
			values[valuesOffset++] = block2 >>> 30 & 63L;
			values[valuesOffset++] = block2 >>> 24 & 63L;
			values[valuesOffset++] = block2 >>> 18 & 63L;
			values[valuesOffset++] = block2 >>> 12 & 63L;
			values[valuesOffset++] = block2 >>> 6 & 63L;
			values[valuesOffset++] = block2 & 63L;
		}

	}

	public void decode(byte blocks[], int blocksOffset, long values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < 8 * iterations; i++)
		{
			long byte0 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = byte0 >>> 2;
			long byte1 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte0 & 3L) << 4 | byte1 >>> 4;
			long byte2 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte1 & 15L) << 2 | byte2 >>> 6;
			values[valuesOffset++] = byte2 & 63L;
		}

	}

}
