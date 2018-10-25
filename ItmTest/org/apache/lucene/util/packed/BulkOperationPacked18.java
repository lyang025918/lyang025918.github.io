// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BulkOperationPacked18.java

package org.apache.lucene.util.packed;


// Referenced classes of package org.apache.lucene.util.packed:
//			BulkOperationPacked

final class BulkOperationPacked18 extends BulkOperationPacked
{

	static final boolean $assertionsDisabled = !org/apache/lucene/util/packed/BulkOperationPacked18.desiredAssertionStatus();

	public BulkOperationPacked18()
	{
		super(18);
		if (!$assertionsDisabled && blockCount() != 9)
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
			values[valuesOffset++] = (int)(block0 >>> 46);
			values[valuesOffset++] = (int)(block0 >>> 28 & 0x3ffffL);
			values[valuesOffset++] = (int)(block0 >>> 10 & 0x3ffffL);
			long block1 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block0 & 1023L) << 8 | block1 >>> 56);
			values[valuesOffset++] = (int)(block1 >>> 38 & 0x3ffffL);
			values[valuesOffset++] = (int)(block1 >>> 20 & 0x3ffffL);
			values[valuesOffset++] = (int)(block1 >>> 2 & 0x3ffffL);
			long block2 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block1 & 3L) << 16 | block2 >>> 48);
			values[valuesOffset++] = (int)(block2 >>> 30 & 0x3ffffL);
			values[valuesOffset++] = (int)(block2 >>> 12 & 0x3ffffL);
			long block3 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block2 & 4095L) << 6 | block3 >>> 58);
			values[valuesOffset++] = (int)(block3 >>> 40 & 0x3ffffL);
			values[valuesOffset++] = (int)(block3 >>> 22 & 0x3ffffL);
			values[valuesOffset++] = (int)(block3 >>> 4 & 0x3ffffL);
			long block4 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block3 & 15L) << 14 | block4 >>> 50);
			values[valuesOffset++] = (int)(block4 >>> 32 & 0x3ffffL);
			values[valuesOffset++] = (int)(block4 >>> 14 & 0x3ffffL);
			long block5 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block4 & 16383L) << 4 | block5 >>> 60);
			values[valuesOffset++] = (int)(block5 >>> 42 & 0x3ffffL);
			values[valuesOffset++] = (int)(block5 >>> 24 & 0x3ffffL);
			values[valuesOffset++] = (int)(block5 >>> 6 & 0x3ffffL);
			long block6 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block5 & 63L) << 12 | block6 >>> 52);
			values[valuesOffset++] = (int)(block6 >>> 34 & 0x3ffffL);
			values[valuesOffset++] = (int)(block6 >>> 16 & 0x3ffffL);
			long block7 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block6 & 65535L) << 2 | block7 >>> 62);
			values[valuesOffset++] = (int)(block7 >>> 44 & 0x3ffffL);
			values[valuesOffset++] = (int)(block7 >>> 26 & 0x3ffffL);
			values[valuesOffset++] = (int)(block7 >>> 8 & 0x3ffffL);
			long block8 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block7 & 255L) << 10 | block8 >>> 54);
			values[valuesOffset++] = (int)(block8 >>> 36 & 0x3ffffL);
			values[valuesOffset++] = (int)(block8 >>> 18 & 0x3ffffL);
			values[valuesOffset++] = (int)(block8 & 0x3ffffL);
		}

	}

	public void decode(byte blocks[], int blocksOffset, int values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < 8 * iterations; i++)
		{
			int byte0 = blocks[blocksOffset++] & 0xff;
			int byte1 = blocks[blocksOffset++] & 0xff;
			int byte2 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = byte0 << 10 | byte1 << 2 | byte2 >>> 6;
			int byte3 = blocks[blocksOffset++] & 0xff;
			int byte4 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte2 & 0x3f) << 12 | byte3 << 4 | byte4 >>> 4;
			int byte5 = blocks[blocksOffset++] & 0xff;
			int byte6 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte4 & 0xf) << 14 | byte5 << 6 | byte6 >>> 2;
			int byte7 = blocks[blocksOffset++] & 0xff;
			int byte8 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte6 & 3) << 16 | byte7 << 8 | byte8;
		}

	}

	public void decode(long blocks[], int blocksOffset, long values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < iterations; i++)
		{
			long block0 = blocks[blocksOffset++];
			values[valuesOffset++] = block0 >>> 46;
			values[valuesOffset++] = block0 >>> 28 & 0x3ffffL;
			values[valuesOffset++] = block0 >>> 10 & 0x3ffffL;
			long block1 = blocks[blocksOffset++];
			values[valuesOffset++] = (block0 & 1023L) << 8 | block1 >>> 56;
			values[valuesOffset++] = block1 >>> 38 & 0x3ffffL;
			values[valuesOffset++] = block1 >>> 20 & 0x3ffffL;
			values[valuesOffset++] = block1 >>> 2 & 0x3ffffL;
			long block2 = blocks[blocksOffset++];
			values[valuesOffset++] = (block1 & 3L) << 16 | block2 >>> 48;
			values[valuesOffset++] = block2 >>> 30 & 0x3ffffL;
			values[valuesOffset++] = block2 >>> 12 & 0x3ffffL;
			long block3 = blocks[blocksOffset++];
			values[valuesOffset++] = (block2 & 4095L) << 6 | block3 >>> 58;
			values[valuesOffset++] = block3 >>> 40 & 0x3ffffL;
			values[valuesOffset++] = block3 >>> 22 & 0x3ffffL;
			values[valuesOffset++] = block3 >>> 4 & 0x3ffffL;
			long block4 = blocks[blocksOffset++];
			values[valuesOffset++] = (block3 & 15L) << 14 | block4 >>> 50;
			values[valuesOffset++] = block4 >>> 32 & 0x3ffffL;
			values[valuesOffset++] = block4 >>> 14 & 0x3ffffL;
			long block5 = blocks[blocksOffset++];
			values[valuesOffset++] = (block4 & 16383L) << 4 | block5 >>> 60;
			values[valuesOffset++] = block5 >>> 42 & 0x3ffffL;
			values[valuesOffset++] = block5 >>> 24 & 0x3ffffL;
			values[valuesOffset++] = block5 >>> 6 & 0x3ffffL;
			long block6 = blocks[blocksOffset++];
			values[valuesOffset++] = (block5 & 63L) << 12 | block6 >>> 52;
			values[valuesOffset++] = block6 >>> 34 & 0x3ffffL;
			values[valuesOffset++] = block6 >>> 16 & 0x3ffffL;
			long block7 = blocks[blocksOffset++];
			values[valuesOffset++] = (block6 & 65535L) << 2 | block7 >>> 62;
			values[valuesOffset++] = block7 >>> 44 & 0x3ffffL;
			values[valuesOffset++] = block7 >>> 26 & 0x3ffffL;
			values[valuesOffset++] = block7 >>> 8 & 0x3ffffL;
			long block8 = blocks[blocksOffset++];
			values[valuesOffset++] = (block7 & 255L) << 10 | block8 >>> 54;
			values[valuesOffset++] = block8 >>> 36 & 0x3ffffL;
			values[valuesOffset++] = block8 >>> 18 & 0x3ffffL;
			values[valuesOffset++] = block8 & 0x3ffffL;
		}

	}

	public void decode(byte blocks[], int blocksOffset, long values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < 8 * iterations; i++)
		{
			long byte0 = blocks[blocksOffset++] & 0xff;
			long byte1 = blocks[blocksOffset++] & 0xff;
			long byte2 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = byte0 << 10 | byte1 << 2 | byte2 >>> 6;
			long byte3 = blocks[blocksOffset++] & 0xff;
			long byte4 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte2 & 63L) << 12 | byte3 << 4 | byte4 >>> 4;
			long byte5 = blocks[blocksOffset++] & 0xff;
			long byte6 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte4 & 15L) << 14 | byte5 << 6 | byte6 >>> 2;
			long byte7 = blocks[blocksOffset++] & 0xff;
			long byte8 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte6 & 3L) << 16 | byte7 << 8 | byte8;
		}

	}

}
