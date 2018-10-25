// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BulkOperationPacked24.java

package org.apache.lucene.util.packed;


// Referenced classes of package org.apache.lucene.util.packed:
//			BulkOperationPacked

final class BulkOperationPacked24 extends BulkOperationPacked
{

	static final boolean $assertionsDisabled = !org/apache/lucene/util/packed/BulkOperationPacked24.desiredAssertionStatus();

	public BulkOperationPacked24()
	{
		super(24);
		if (!$assertionsDisabled && blockCount() != 3)
			throw new AssertionError();
		if (!$assertionsDisabled && valueCount() != 8)
			throw new AssertionError();
		else
			return;
	}

	public void decode(long blocks[], int blocksOffset, int values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < iterations; i++)
		{
			long block0 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)(block0 >>> 40);
			values[valuesOffset++] = (int)(block0 >>> 16 & 0xffffffL);
			long block1 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block0 & 65535L) << 8 | block1 >>> 56);
			values[valuesOffset++] = (int)(block1 >>> 32 & 0xffffffL);
			values[valuesOffset++] = (int)(block1 >>> 8 & 0xffffffL);
			long block2 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block1 & 255L) << 16 | block2 >>> 48);
			values[valuesOffset++] = (int)(block2 >>> 24 & 0xffffffL);
			values[valuesOffset++] = (int)(block2 & 0xffffffL);
		}

	}

	public void decode(byte blocks[], int blocksOffset, int values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < 8 * iterations; i++)
		{
			int byte0 = blocks[blocksOffset++] & 0xff;
			int byte1 = blocks[blocksOffset++] & 0xff;
			int byte2 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = byte0 << 16 | byte1 << 8 | byte2;
		}

	}

	public void decode(long blocks[], int blocksOffset, long values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < iterations; i++)
		{
			long block0 = blocks[blocksOffset++];
			values[valuesOffset++] = block0 >>> 40;
			values[valuesOffset++] = block0 >>> 16 & 0xffffffL;
			long block1 = blocks[blocksOffset++];
			values[valuesOffset++] = (block0 & 65535L) << 8 | block1 >>> 56;
			values[valuesOffset++] = block1 >>> 32 & 0xffffffL;
			values[valuesOffset++] = block1 >>> 8 & 0xffffffL;
			long block2 = blocks[blocksOffset++];
			values[valuesOffset++] = (block1 & 255L) << 16 | block2 >>> 48;
			values[valuesOffset++] = block2 >>> 24 & 0xffffffL;
			values[valuesOffset++] = block2 & 0xffffffL;
		}

	}

	public void decode(byte blocks[], int blocksOffset, long values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < 8 * iterations; i++)
		{
			long byte0 = blocks[blocksOffset++] & 0xff;
			long byte1 = blocks[blocksOffset++] & 0xff;
			long byte2 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = byte0 << 16 | byte1 << 8 | byte2;
		}

	}

}
