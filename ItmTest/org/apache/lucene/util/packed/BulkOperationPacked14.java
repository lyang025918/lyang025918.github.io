// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BulkOperationPacked14.java

package org.apache.lucene.util.packed;


// Referenced classes of package org.apache.lucene.util.packed:
//			BulkOperationPacked

final class BulkOperationPacked14 extends BulkOperationPacked
{

	static final boolean $assertionsDisabled = !org/apache/lucene/util/packed/BulkOperationPacked14.desiredAssertionStatus();

	public BulkOperationPacked14()
	{
		super(14);
		if (!$assertionsDisabled && blockCount() != 7)
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
			values[valuesOffset++] = (int)(block0 >>> 50);
			values[valuesOffset++] = (int)(block0 >>> 36 & 16383L);
			values[valuesOffset++] = (int)(block0 >>> 22 & 16383L);
			values[valuesOffset++] = (int)(block0 >>> 8 & 16383L);
			long block1 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block0 & 255L) << 6 | block1 >>> 58);
			values[valuesOffset++] = (int)(block1 >>> 44 & 16383L);
			values[valuesOffset++] = (int)(block1 >>> 30 & 16383L);
			values[valuesOffset++] = (int)(block1 >>> 16 & 16383L);
			values[valuesOffset++] = (int)(block1 >>> 2 & 16383L);
			long block2 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block1 & 3L) << 12 | block2 >>> 52);
			values[valuesOffset++] = (int)(block2 >>> 38 & 16383L);
			values[valuesOffset++] = (int)(block2 >>> 24 & 16383L);
			values[valuesOffset++] = (int)(block2 >>> 10 & 16383L);
			long block3 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block2 & 1023L) << 4 | block3 >>> 60);
			values[valuesOffset++] = (int)(block3 >>> 46 & 16383L);
			values[valuesOffset++] = (int)(block3 >>> 32 & 16383L);
			values[valuesOffset++] = (int)(block3 >>> 18 & 16383L);
			values[valuesOffset++] = (int)(block3 >>> 4 & 16383L);
			long block4 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block3 & 15L) << 10 | block4 >>> 54);
			values[valuesOffset++] = (int)(block4 >>> 40 & 16383L);
			values[valuesOffset++] = (int)(block4 >>> 26 & 16383L);
			values[valuesOffset++] = (int)(block4 >>> 12 & 16383L);
			long block5 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block4 & 4095L) << 2 | block5 >>> 62);
			values[valuesOffset++] = (int)(block5 >>> 48 & 16383L);
			values[valuesOffset++] = (int)(block5 >>> 34 & 16383L);
			values[valuesOffset++] = (int)(block5 >>> 20 & 16383L);
			values[valuesOffset++] = (int)(block5 >>> 6 & 16383L);
			long block6 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block5 & 63L) << 8 | block6 >>> 56);
			values[valuesOffset++] = (int)(block6 >>> 42 & 16383L);
			values[valuesOffset++] = (int)(block6 >>> 28 & 16383L);
			values[valuesOffset++] = (int)(block6 >>> 14 & 16383L);
			values[valuesOffset++] = (int)(block6 & 16383L);
		}

	}

	public void decode(byte blocks[], int blocksOffset, int values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < 8 * iterations; i++)
		{
			int byte0 = blocks[blocksOffset++] & 0xff;
			int byte1 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = byte0 << 6 | byte1 >>> 2;
			int byte2 = blocks[blocksOffset++] & 0xff;
			int byte3 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte1 & 3) << 12 | byte2 << 4 | byte3 >>> 4;
			int byte4 = blocks[blocksOffset++] & 0xff;
			int byte5 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte3 & 0xf) << 10 | byte4 << 2 | byte5 >>> 6;
			int byte6 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte5 & 0x3f) << 8 | byte6;
		}

	}

	public void decode(long blocks[], int blocksOffset, long values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < iterations; i++)
		{
			long block0 = blocks[blocksOffset++];
			values[valuesOffset++] = block0 >>> 50;
			values[valuesOffset++] = block0 >>> 36 & 16383L;
			values[valuesOffset++] = block0 >>> 22 & 16383L;
			values[valuesOffset++] = block0 >>> 8 & 16383L;
			long block1 = blocks[blocksOffset++];
			values[valuesOffset++] = (block0 & 255L) << 6 | block1 >>> 58;
			values[valuesOffset++] = block1 >>> 44 & 16383L;
			values[valuesOffset++] = block1 >>> 30 & 16383L;
			values[valuesOffset++] = block1 >>> 16 & 16383L;
			values[valuesOffset++] = block1 >>> 2 & 16383L;
			long block2 = blocks[blocksOffset++];
			values[valuesOffset++] = (block1 & 3L) << 12 | block2 >>> 52;
			values[valuesOffset++] = block2 >>> 38 & 16383L;
			values[valuesOffset++] = block2 >>> 24 & 16383L;
			values[valuesOffset++] = block2 >>> 10 & 16383L;
			long block3 = blocks[blocksOffset++];
			values[valuesOffset++] = (block2 & 1023L) << 4 | block3 >>> 60;
			values[valuesOffset++] = block3 >>> 46 & 16383L;
			values[valuesOffset++] = block3 >>> 32 & 16383L;
			values[valuesOffset++] = block3 >>> 18 & 16383L;
			values[valuesOffset++] = block3 >>> 4 & 16383L;
			long block4 = blocks[blocksOffset++];
			values[valuesOffset++] = (block3 & 15L) << 10 | block4 >>> 54;
			values[valuesOffset++] = block4 >>> 40 & 16383L;
			values[valuesOffset++] = block4 >>> 26 & 16383L;
			values[valuesOffset++] = block4 >>> 12 & 16383L;
			long block5 = blocks[blocksOffset++];
			values[valuesOffset++] = (block4 & 4095L) << 2 | block5 >>> 62;
			values[valuesOffset++] = block5 >>> 48 & 16383L;
			values[valuesOffset++] = block5 >>> 34 & 16383L;
			values[valuesOffset++] = block5 >>> 20 & 16383L;
			values[valuesOffset++] = block5 >>> 6 & 16383L;
			long block6 = blocks[blocksOffset++];
			values[valuesOffset++] = (block5 & 63L) << 8 | block6 >>> 56;
			values[valuesOffset++] = block6 >>> 42 & 16383L;
			values[valuesOffset++] = block6 >>> 28 & 16383L;
			values[valuesOffset++] = block6 >>> 14 & 16383L;
			values[valuesOffset++] = block6 & 16383L;
		}

	}

	public void decode(byte blocks[], int blocksOffset, long values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < 8 * iterations; i++)
		{
			long byte0 = blocks[blocksOffset++] & 0xff;
			long byte1 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = byte0 << 6 | byte1 >>> 2;
			long byte2 = blocks[blocksOffset++] & 0xff;
			long byte3 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte1 & 3L) << 12 | byte2 << 4 | byte3 >>> 4;
			long byte4 = blocks[blocksOffset++] & 0xff;
			long byte5 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte3 & 15L) << 10 | byte4 << 2 | byte5 >>> 6;
			long byte6 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte5 & 63L) << 8 | byte6;
		}

	}

}
