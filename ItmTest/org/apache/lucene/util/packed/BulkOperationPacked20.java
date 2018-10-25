// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BulkOperationPacked20.java

package org.apache.lucene.util.packed;


// Referenced classes of package org.apache.lucene.util.packed:
//			BulkOperationPacked

final class BulkOperationPacked20 extends BulkOperationPacked
{

	static final boolean $assertionsDisabled = !org/apache/lucene/util/packed/BulkOperationPacked20.desiredAssertionStatus();

	public BulkOperationPacked20()
	{
		super(20);
		if (!$assertionsDisabled && blockCount() != 5)
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
			values[valuesOffset++] = (int)(block0 >>> 44);
			values[valuesOffset++] = (int)(block0 >>> 24 & 0xfffffL);
			values[valuesOffset++] = (int)(block0 >>> 4 & 0xfffffL);
			long block1 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block0 & 15L) << 16 | block1 >>> 48);
			values[valuesOffset++] = (int)(block1 >>> 28 & 0xfffffL);
			values[valuesOffset++] = (int)(block1 >>> 8 & 0xfffffL);
			long block2 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block1 & 255L) << 12 | block2 >>> 52);
			values[valuesOffset++] = (int)(block2 >>> 32 & 0xfffffL);
			values[valuesOffset++] = (int)(block2 >>> 12 & 0xfffffL);
			long block3 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block2 & 4095L) << 8 | block3 >>> 56);
			values[valuesOffset++] = (int)(block3 >>> 36 & 0xfffffL);
			values[valuesOffset++] = (int)(block3 >>> 16 & 0xfffffL);
			long block4 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block3 & 65535L) << 4 | block4 >>> 60);
			values[valuesOffset++] = (int)(block4 >>> 40 & 0xfffffL);
			values[valuesOffset++] = (int)(block4 >>> 20 & 0xfffffL);
			values[valuesOffset++] = (int)(block4 & 0xfffffL);
		}

	}

	public void decode(byte blocks[], int blocksOffset, int values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < 8 * iterations; i++)
		{
			int byte0 = blocks[blocksOffset++] & 0xff;
			int byte1 = blocks[blocksOffset++] & 0xff;
			int byte2 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = byte0 << 12 | byte1 << 4 | byte2 >>> 4;
			int byte3 = blocks[blocksOffset++] & 0xff;
			int byte4 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte2 & 0xf) << 16 | byte3 << 8 | byte4;
		}

	}

	public void decode(long blocks[], int blocksOffset, long values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < iterations; i++)
		{
			long block0 = blocks[blocksOffset++];
			values[valuesOffset++] = block0 >>> 44;
			values[valuesOffset++] = block0 >>> 24 & 0xfffffL;
			values[valuesOffset++] = block0 >>> 4 & 0xfffffL;
			long block1 = blocks[blocksOffset++];
			values[valuesOffset++] = (block0 & 15L) << 16 | block1 >>> 48;
			values[valuesOffset++] = block1 >>> 28 & 0xfffffL;
			values[valuesOffset++] = block1 >>> 8 & 0xfffffL;
			long block2 = blocks[blocksOffset++];
			values[valuesOffset++] = (block1 & 255L) << 12 | block2 >>> 52;
			values[valuesOffset++] = block2 >>> 32 & 0xfffffL;
			values[valuesOffset++] = block2 >>> 12 & 0xfffffL;
			long block3 = blocks[blocksOffset++];
			values[valuesOffset++] = (block2 & 4095L) << 8 | block3 >>> 56;
			values[valuesOffset++] = block3 >>> 36 & 0xfffffL;
			values[valuesOffset++] = block3 >>> 16 & 0xfffffL;
			long block4 = blocks[blocksOffset++];
			values[valuesOffset++] = (block3 & 65535L) << 4 | block4 >>> 60;
			values[valuesOffset++] = block4 >>> 40 & 0xfffffL;
			values[valuesOffset++] = block4 >>> 20 & 0xfffffL;
			values[valuesOffset++] = block4 & 0xfffffL;
		}

	}

	public void decode(byte blocks[], int blocksOffset, long values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < 8 * iterations; i++)
		{
			long byte0 = blocks[blocksOffset++] & 0xff;
			long byte1 = blocks[blocksOffset++] & 0xff;
			long byte2 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = byte0 << 12 | byte1 << 4 | byte2 >>> 4;
			long byte3 = blocks[blocksOffset++] & 0xff;
			long byte4 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte2 & 15L) << 16 | byte3 << 8 | byte4;
		}

	}

}
