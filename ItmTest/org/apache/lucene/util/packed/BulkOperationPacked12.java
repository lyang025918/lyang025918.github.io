// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BulkOperationPacked12.java

package org.apache.lucene.util.packed;


// Referenced classes of package org.apache.lucene.util.packed:
//			BulkOperationPacked

final class BulkOperationPacked12 extends BulkOperationPacked
{

	static final boolean $assertionsDisabled = !org/apache/lucene/util/packed/BulkOperationPacked12.desiredAssertionStatus();

	public BulkOperationPacked12()
	{
		super(12);
		if (!$assertionsDisabled && blockCount() != 3)
			throw new AssertionError();
		if (!$assertionsDisabled && valueCount() != 16)
			throw new AssertionError();
		else
			return;
	}

	public void decode(long blocks[], int blocksOffset, int values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < iterations; i++)
		{
			long block0 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)(block0 >>> 52);
			values[valuesOffset++] = (int)(block0 >>> 40 & 4095L);
			values[valuesOffset++] = (int)(block0 >>> 28 & 4095L);
			values[valuesOffset++] = (int)(block0 >>> 16 & 4095L);
			values[valuesOffset++] = (int)(block0 >>> 4 & 4095L);
			long block1 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block0 & 15L) << 8 | block1 >>> 56);
			values[valuesOffset++] = (int)(block1 >>> 44 & 4095L);
			values[valuesOffset++] = (int)(block1 >>> 32 & 4095L);
			values[valuesOffset++] = (int)(block1 >>> 20 & 4095L);
			values[valuesOffset++] = (int)(block1 >>> 8 & 4095L);
			long block2 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block1 & 255L) << 4 | block2 >>> 60);
			values[valuesOffset++] = (int)(block2 >>> 48 & 4095L);
			values[valuesOffset++] = (int)(block2 >>> 36 & 4095L);
			values[valuesOffset++] = (int)(block2 >>> 24 & 4095L);
			values[valuesOffset++] = (int)(block2 >>> 12 & 4095L);
			values[valuesOffset++] = (int)(block2 & 4095L);
		}

	}

	public void decode(byte blocks[], int blocksOffset, int values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < 8 * iterations; i++)
		{
			int byte0 = blocks[blocksOffset++] & 0xff;
			int byte1 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = byte0 << 4 | byte1 >>> 4;
			int byte2 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte1 & 0xf) << 8 | byte2;
		}

	}

	public void decode(long blocks[], int blocksOffset, long values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < iterations; i++)
		{
			long block0 = blocks[blocksOffset++];
			values[valuesOffset++] = block0 >>> 52;
			values[valuesOffset++] = block0 >>> 40 & 4095L;
			values[valuesOffset++] = block0 >>> 28 & 4095L;
			values[valuesOffset++] = block0 >>> 16 & 4095L;
			values[valuesOffset++] = block0 >>> 4 & 4095L;
			long block1 = blocks[blocksOffset++];
			values[valuesOffset++] = (block0 & 15L) << 8 | block1 >>> 56;
			values[valuesOffset++] = block1 >>> 44 & 4095L;
			values[valuesOffset++] = block1 >>> 32 & 4095L;
			values[valuesOffset++] = block1 >>> 20 & 4095L;
			values[valuesOffset++] = block1 >>> 8 & 4095L;
			long block2 = blocks[blocksOffset++];
			values[valuesOffset++] = (block1 & 255L) << 4 | block2 >>> 60;
			values[valuesOffset++] = block2 >>> 48 & 4095L;
			values[valuesOffset++] = block2 >>> 36 & 4095L;
			values[valuesOffset++] = block2 >>> 24 & 4095L;
			values[valuesOffset++] = block2 >>> 12 & 4095L;
			values[valuesOffset++] = block2 & 4095L;
		}

	}

	public void decode(byte blocks[], int blocksOffset, long values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < 8 * iterations; i++)
		{
			long byte0 = blocks[blocksOffset++] & 0xff;
			long byte1 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = byte0 << 4 | byte1 >>> 4;
			long byte2 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte1 & 15L) << 8 | byte2;
		}

	}

}
