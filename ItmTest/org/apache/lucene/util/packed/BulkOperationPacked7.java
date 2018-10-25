// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BulkOperationPacked7.java

package org.apache.lucene.util.packed;


// Referenced classes of package org.apache.lucene.util.packed:
//			BulkOperationPacked

final class BulkOperationPacked7 extends BulkOperationPacked
{

	static final boolean $assertionsDisabled = !org/apache/lucene/util/packed/BulkOperationPacked7.desiredAssertionStatus();

	public BulkOperationPacked7()
	{
		super(7);
		if (!$assertionsDisabled && blockCount() != 7)
			throw new AssertionError();
		if (!$assertionsDisabled && valueCount() != 64)
			throw new AssertionError();
		else
			return;
	}

	public void decode(long blocks[], int blocksOffset, int values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < iterations; i++)
		{
			long block0 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)(block0 >>> 57);
			values[valuesOffset++] = (int)(block0 >>> 50 & 127L);
			values[valuesOffset++] = (int)(block0 >>> 43 & 127L);
			values[valuesOffset++] = (int)(block0 >>> 36 & 127L);
			values[valuesOffset++] = (int)(block0 >>> 29 & 127L);
			values[valuesOffset++] = (int)(block0 >>> 22 & 127L);
			values[valuesOffset++] = (int)(block0 >>> 15 & 127L);
			values[valuesOffset++] = (int)(block0 >>> 8 & 127L);
			values[valuesOffset++] = (int)(block0 >>> 1 & 127L);
			long block1 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block0 & 1L) << 6 | block1 >>> 58);
			values[valuesOffset++] = (int)(block1 >>> 51 & 127L);
			values[valuesOffset++] = (int)(block1 >>> 44 & 127L);
			values[valuesOffset++] = (int)(block1 >>> 37 & 127L);
			values[valuesOffset++] = (int)(block1 >>> 30 & 127L);
			values[valuesOffset++] = (int)(block1 >>> 23 & 127L);
			values[valuesOffset++] = (int)(block1 >>> 16 & 127L);
			values[valuesOffset++] = (int)(block1 >>> 9 & 127L);
			values[valuesOffset++] = (int)(block1 >>> 2 & 127L);
			long block2 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block1 & 3L) << 5 | block2 >>> 59);
			values[valuesOffset++] = (int)(block2 >>> 52 & 127L);
			values[valuesOffset++] = (int)(block2 >>> 45 & 127L);
			values[valuesOffset++] = (int)(block2 >>> 38 & 127L);
			values[valuesOffset++] = (int)(block2 >>> 31 & 127L);
			values[valuesOffset++] = (int)(block2 >>> 24 & 127L);
			values[valuesOffset++] = (int)(block2 >>> 17 & 127L);
			values[valuesOffset++] = (int)(block2 >>> 10 & 127L);
			values[valuesOffset++] = (int)(block2 >>> 3 & 127L);
			long block3 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block2 & 7L) << 4 | block3 >>> 60);
			values[valuesOffset++] = (int)(block3 >>> 53 & 127L);
			values[valuesOffset++] = (int)(block3 >>> 46 & 127L);
			values[valuesOffset++] = (int)(block3 >>> 39 & 127L);
			values[valuesOffset++] = (int)(block3 >>> 32 & 127L);
			values[valuesOffset++] = (int)(block3 >>> 25 & 127L);
			values[valuesOffset++] = (int)(block3 >>> 18 & 127L);
			values[valuesOffset++] = (int)(block3 >>> 11 & 127L);
			values[valuesOffset++] = (int)(block3 >>> 4 & 127L);
			long block4 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block3 & 15L) << 3 | block4 >>> 61);
			values[valuesOffset++] = (int)(block4 >>> 54 & 127L);
			values[valuesOffset++] = (int)(block4 >>> 47 & 127L);
			values[valuesOffset++] = (int)(block4 >>> 40 & 127L);
			values[valuesOffset++] = (int)(block4 >>> 33 & 127L);
			values[valuesOffset++] = (int)(block4 >>> 26 & 127L);
			values[valuesOffset++] = (int)(block4 >>> 19 & 127L);
			values[valuesOffset++] = (int)(block4 >>> 12 & 127L);
			values[valuesOffset++] = (int)(block4 >>> 5 & 127L);
			long block5 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block4 & 31L) << 2 | block5 >>> 62);
			values[valuesOffset++] = (int)(block5 >>> 55 & 127L);
			values[valuesOffset++] = (int)(block5 >>> 48 & 127L);
			values[valuesOffset++] = (int)(block5 >>> 41 & 127L);
			values[valuesOffset++] = (int)(block5 >>> 34 & 127L);
			values[valuesOffset++] = (int)(block5 >>> 27 & 127L);
			values[valuesOffset++] = (int)(block5 >>> 20 & 127L);
			values[valuesOffset++] = (int)(block5 >>> 13 & 127L);
			values[valuesOffset++] = (int)(block5 >>> 6 & 127L);
			long block6 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block5 & 63L) << 1 | block6 >>> 63);
			values[valuesOffset++] = (int)(block6 >>> 56 & 127L);
			values[valuesOffset++] = (int)(block6 >>> 49 & 127L);
			values[valuesOffset++] = (int)(block6 >>> 42 & 127L);
			values[valuesOffset++] = (int)(block6 >>> 35 & 127L);
			values[valuesOffset++] = (int)(block6 >>> 28 & 127L);
			values[valuesOffset++] = (int)(block6 >>> 21 & 127L);
			values[valuesOffset++] = (int)(block6 >>> 14 & 127L);
			values[valuesOffset++] = (int)(block6 >>> 7 & 127L);
			values[valuesOffset++] = (int)(block6 & 127L);
		}

	}

	public void decode(byte blocks[], int blocksOffset, int values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < 8 * iterations; i++)
		{
			int byte0 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = byte0 >>> 1;
			int byte1 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte0 & 1) << 6 | byte1 >>> 2;
			int byte2 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte1 & 3) << 5 | byte2 >>> 3;
			int byte3 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte2 & 7) << 4 | byte3 >>> 4;
			int byte4 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte3 & 0xf) << 3 | byte4 >>> 5;
			int byte5 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte4 & 0x1f) << 2 | byte5 >>> 6;
			int byte6 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte5 & 0x3f) << 1 | byte6 >>> 7;
			values[valuesOffset++] = byte6 & 0x7f;
		}

	}

	public void decode(long blocks[], int blocksOffset, long values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < iterations; i++)
		{
			long block0 = blocks[blocksOffset++];
			values[valuesOffset++] = block0 >>> 57;
			values[valuesOffset++] = block0 >>> 50 & 127L;
			values[valuesOffset++] = block0 >>> 43 & 127L;
			values[valuesOffset++] = block0 >>> 36 & 127L;
			values[valuesOffset++] = block0 >>> 29 & 127L;
			values[valuesOffset++] = block0 >>> 22 & 127L;
			values[valuesOffset++] = block0 >>> 15 & 127L;
			values[valuesOffset++] = block0 >>> 8 & 127L;
			values[valuesOffset++] = block0 >>> 1 & 127L;
			long block1 = blocks[blocksOffset++];
			values[valuesOffset++] = (block0 & 1L) << 6 | block1 >>> 58;
			values[valuesOffset++] = block1 >>> 51 & 127L;
			values[valuesOffset++] = block1 >>> 44 & 127L;
			values[valuesOffset++] = block1 >>> 37 & 127L;
			values[valuesOffset++] = block1 >>> 30 & 127L;
			values[valuesOffset++] = block1 >>> 23 & 127L;
			values[valuesOffset++] = block1 >>> 16 & 127L;
			values[valuesOffset++] = block1 >>> 9 & 127L;
			values[valuesOffset++] = block1 >>> 2 & 127L;
			long block2 = blocks[blocksOffset++];
			values[valuesOffset++] = (block1 & 3L) << 5 | block2 >>> 59;
			values[valuesOffset++] = block2 >>> 52 & 127L;
			values[valuesOffset++] = block2 >>> 45 & 127L;
			values[valuesOffset++] = block2 >>> 38 & 127L;
			values[valuesOffset++] = block2 >>> 31 & 127L;
			values[valuesOffset++] = block2 >>> 24 & 127L;
			values[valuesOffset++] = block2 >>> 17 & 127L;
			values[valuesOffset++] = block2 >>> 10 & 127L;
			values[valuesOffset++] = block2 >>> 3 & 127L;
			long block3 = blocks[blocksOffset++];
			values[valuesOffset++] = (block2 & 7L) << 4 | block3 >>> 60;
			values[valuesOffset++] = block3 >>> 53 & 127L;
			values[valuesOffset++] = block3 >>> 46 & 127L;
			values[valuesOffset++] = block3 >>> 39 & 127L;
			values[valuesOffset++] = block3 >>> 32 & 127L;
			values[valuesOffset++] = block3 >>> 25 & 127L;
			values[valuesOffset++] = block3 >>> 18 & 127L;
			values[valuesOffset++] = block3 >>> 11 & 127L;
			values[valuesOffset++] = block3 >>> 4 & 127L;
			long block4 = blocks[blocksOffset++];
			values[valuesOffset++] = (block3 & 15L) << 3 | block4 >>> 61;
			values[valuesOffset++] = block4 >>> 54 & 127L;
			values[valuesOffset++] = block4 >>> 47 & 127L;
			values[valuesOffset++] = block4 >>> 40 & 127L;
			values[valuesOffset++] = block4 >>> 33 & 127L;
			values[valuesOffset++] = block4 >>> 26 & 127L;
			values[valuesOffset++] = block4 >>> 19 & 127L;
			values[valuesOffset++] = block4 >>> 12 & 127L;
			values[valuesOffset++] = block4 >>> 5 & 127L;
			long block5 = blocks[blocksOffset++];
			values[valuesOffset++] = (block4 & 31L) << 2 | block5 >>> 62;
			values[valuesOffset++] = block5 >>> 55 & 127L;
			values[valuesOffset++] = block5 >>> 48 & 127L;
			values[valuesOffset++] = block5 >>> 41 & 127L;
			values[valuesOffset++] = block5 >>> 34 & 127L;
			values[valuesOffset++] = block5 >>> 27 & 127L;
			values[valuesOffset++] = block5 >>> 20 & 127L;
			values[valuesOffset++] = block5 >>> 13 & 127L;
			values[valuesOffset++] = block5 >>> 6 & 127L;
			long block6 = blocks[blocksOffset++];
			values[valuesOffset++] = (block5 & 63L) << 1 | block6 >>> 63;
			values[valuesOffset++] = block6 >>> 56 & 127L;
			values[valuesOffset++] = block6 >>> 49 & 127L;
			values[valuesOffset++] = block6 >>> 42 & 127L;
			values[valuesOffset++] = block6 >>> 35 & 127L;
			values[valuesOffset++] = block6 >>> 28 & 127L;
			values[valuesOffset++] = block6 >>> 21 & 127L;
			values[valuesOffset++] = block6 >>> 14 & 127L;
			values[valuesOffset++] = block6 >>> 7 & 127L;
			values[valuesOffset++] = block6 & 127L;
		}

	}

	public void decode(byte blocks[], int blocksOffset, long values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < 8 * iterations; i++)
		{
			long byte0 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = byte0 >>> 1;
			long byte1 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte0 & 1L) << 6 | byte1 >>> 2;
			long byte2 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte1 & 3L) << 5 | byte2 >>> 3;
			long byte3 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte2 & 7L) << 4 | byte3 >>> 4;
			long byte4 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte3 & 15L) << 3 | byte4 >>> 5;
			long byte5 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte4 & 31L) << 2 | byte5 >>> 6;
			long byte6 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte5 & 63L) << 1 | byte6 >>> 7;
			values[valuesOffset++] = byte6 & 127L;
		}

	}

}
