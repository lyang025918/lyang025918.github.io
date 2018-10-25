// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BulkOperationPacked10.java

package org.apache.lucene.util.packed;


// Referenced classes of package org.apache.lucene.util.packed:
//			BulkOperationPacked

final class BulkOperationPacked10 extends BulkOperationPacked
{

	static final boolean $assertionsDisabled = !org/apache/lucene/util/packed/BulkOperationPacked10.desiredAssertionStatus();

	public BulkOperationPacked10()
	{
		super(10);
		if (!$assertionsDisabled && blockCount() != 5)
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
			values[valuesOffset++] = (int)(block0 >>> 54);
			values[valuesOffset++] = (int)(block0 >>> 44 & 1023L);
			values[valuesOffset++] = (int)(block0 >>> 34 & 1023L);
			values[valuesOffset++] = (int)(block0 >>> 24 & 1023L);
			values[valuesOffset++] = (int)(block0 >>> 14 & 1023L);
			values[valuesOffset++] = (int)(block0 >>> 4 & 1023L);
			long block1 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block0 & 15L) << 6 | block1 >>> 58);
			values[valuesOffset++] = (int)(block1 >>> 48 & 1023L);
			values[valuesOffset++] = (int)(block1 >>> 38 & 1023L);
			values[valuesOffset++] = (int)(block1 >>> 28 & 1023L);
			values[valuesOffset++] = (int)(block1 >>> 18 & 1023L);
			values[valuesOffset++] = (int)(block1 >>> 8 & 1023L);
			long block2 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block1 & 255L) << 2 | block2 >>> 62);
			values[valuesOffset++] = (int)(block2 >>> 52 & 1023L);
			values[valuesOffset++] = (int)(block2 >>> 42 & 1023L);
			values[valuesOffset++] = (int)(block2 >>> 32 & 1023L);
			values[valuesOffset++] = (int)(block2 >>> 22 & 1023L);
			values[valuesOffset++] = (int)(block2 >>> 12 & 1023L);
			values[valuesOffset++] = (int)(block2 >>> 2 & 1023L);
			long block3 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block2 & 3L) << 8 | block3 >>> 56);
			values[valuesOffset++] = (int)(block3 >>> 46 & 1023L);
			values[valuesOffset++] = (int)(block3 >>> 36 & 1023L);
			values[valuesOffset++] = (int)(block3 >>> 26 & 1023L);
			values[valuesOffset++] = (int)(block3 >>> 16 & 1023L);
			values[valuesOffset++] = (int)(block3 >>> 6 & 1023L);
			long block4 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block3 & 63L) << 4 | block4 >>> 60);
			values[valuesOffset++] = (int)(block4 >>> 50 & 1023L);
			values[valuesOffset++] = (int)(block4 >>> 40 & 1023L);
			values[valuesOffset++] = (int)(block4 >>> 30 & 1023L);
			values[valuesOffset++] = (int)(block4 >>> 20 & 1023L);
			values[valuesOffset++] = (int)(block4 >>> 10 & 1023L);
			values[valuesOffset++] = (int)(block4 & 1023L);
		}

	}

	public void decode(byte blocks[], int blocksOffset, int values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < 8 * iterations; i++)
		{
			int byte0 = blocks[blocksOffset++] & 0xff;
			int byte1 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = byte0 << 2 | byte1 >>> 6;
			int byte2 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte1 & 0x3f) << 4 | byte2 >>> 4;
			int byte3 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte2 & 0xf) << 6 | byte3 >>> 2;
			int byte4 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte3 & 3) << 8 | byte4;
		}

	}

	public void decode(long blocks[], int blocksOffset, long values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < iterations; i++)
		{
			long block0 = blocks[blocksOffset++];
			values[valuesOffset++] = block0 >>> 54;
			values[valuesOffset++] = block0 >>> 44 & 1023L;
			values[valuesOffset++] = block0 >>> 34 & 1023L;
			values[valuesOffset++] = block0 >>> 24 & 1023L;
			values[valuesOffset++] = block0 >>> 14 & 1023L;
			values[valuesOffset++] = block0 >>> 4 & 1023L;
			long block1 = blocks[blocksOffset++];
			values[valuesOffset++] = (block0 & 15L) << 6 | block1 >>> 58;
			values[valuesOffset++] = block1 >>> 48 & 1023L;
			values[valuesOffset++] = block1 >>> 38 & 1023L;
			values[valuesOffset++] = block1 >>> 28 & 1023L;
			values[valuesOffset++] = block1 >>> 18 & 1023L;
			values[valuesOffset++] = block1 >>> 8 & 1023L;
			long block2 = blocks[blocksOffset++];
			values[valuesOffset++] = (block1 & 255L) << 2 | block2 >>> 62;
			values[valuesOffset++] = block2 >>> 52 & 1023L;
			values[valuesOffset++] = block2 >>> 42 & 1023L;
			values[valuesOffset++] = block2 >>> 32 & 1023L;
			values[valuesOffset++] = block2 >>> 22 & 1023L;
			values[valuesOffset++] = block2 >>> 12 & 1023L;
			values[valuesOffset++] = block2 >>> 2 & 1023L;
			long block3 = blocks[blocksOffset++];
			values[valuesOffset++] = (block2 & 3L) << 8 | block3 >>> 56;
			values[valuesOffset++] = block3 >>> 46 & 1023L;
			values[valuesOffset++] = block3 >>> 36 & 1023L;
			values[valuesOffset++] = block3 >>> 26 & 1023L;
			values[valuesOffset++] = block3 >>> 16 & 1023L;
			values[valuesOffset++] = block3 >>> 6 & 1023L;
			long block4 = blocks[blocksOffset++];
			values[valuesOffset++] = (block3 & 63L) << 4 | block4 >>> 60;
			values[valuesOffset++] = block4 >>> 50 & 1023L;
			values[valuesOffset++] = block4 >>> 40 & 1023L;
			values[valuesOffset++] = block4 >>> 30 & 1023L;
			values[valuesOffset++] = block4 >>> 20 & 1023L;
			values[valuesOffset++] = block4 >>> 10 & 1023L;
			values[valuesOffset++] = block4 & 1023L;
		}

	}

	public void decode(byte blocks[], int blocksOffset, long values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < 8 * iterations; i++)
		{
			long byte0 = blocks[blocksOffset++] & 0xff;
			long byte1 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = byte0 << 2 | byte1 >>> 6;
			long byte2 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte1 & 63L) << 4 | byte2 >>> 4;
			long byte3 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte2 & 15L) << 6 | byte3 >>> 2;
			long byte4 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte3 & 3L) << 8 | byte4;
		}

	}

}
