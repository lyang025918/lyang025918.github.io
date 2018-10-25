// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BulkOperationPacked13.java

package org.apache.lucene.util.packed;


// Referenced classes of package org.apache.lucene.util.packed:
//			BulkOperationPacked

final class BulkOperationPacked13 extends BulkOperationPacked
{

	static final boolean $assertionsDisabled = !org/apache/lucene/util/packed/BulkOperationPacked13.desiredAssertionStatus();

	public BulkOperationPacked13()
	{
		super(13);
		if (!$assertionsDisabled && blockCount() != 13)
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
			values[valuesOffset++] = (int)(block0 >>> 51);
			values[valuesOffset++] = (int)(block0 >>> 38 & 8191L);
			values[valuesOffset++] = (int)(block0 >>> 25 & 8191L);
			values[valuesOffset++] = (int)(block0 >>> 12 & 8191L);
			long block1 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block0 & 4095L) << 1 | block1 >>> 63);
			values[valuesOffset++] = (int)(block1 >>> 50 & 8191L);
			values[valuesOffset++] = (int)(block1 >>> 37 & 8191L);
			values[valuesOffset++] = (int)(block1 >>> 24 & 8191L);
			values[valuesOffset++] = (int)(block1 >>> 11 & 8191L);
			long block2 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block1 & 2047L) << 2 | block2 >>> 62);
			values[valuesOffset++] = (int)(block2 >>> 49 & 8191L);
			values[valuesOffset++] = (int)(block2 >>> 36 & 8191L);
			values[valuesOffset++] = (int)(block2 >>> 23 & 8191L);
			values[valuesOffset++] = (int)(block2 >>> 10 & 8191L);
			long block3 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block2 & 1023L) << 3 | block3 >>> 61);
			values[valuesOffset++] = (int)(block3 >>> 48 & 8191L);
			values[valuesOffset++] = (int)(block3 >>> 35 & 8191L);
			values[valuesOffset++] = (int)(block3 >>> 22 & 8191L);
			values[valuesOffset++] = (int)(block3 >>> 9 & 8191L);
			long block4 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block3 & 511L) << 4 | block4 >>> 60);
			values[valuesOffset++] = (int)(block4 >>> 47 & 8191L);
			values[valuesOffset++] = (int)(block4 >>> 34 & 8191L);
			values[valuesOffset++] = (int)(block4 >>> 21 & 8191L);
			values[valuesOffset++] = (int)(block4 >>> 8 & 8191L);
			long block5 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block4 & 255L) << 5 | block5 >>> 59);
			values[valuesOffset++] = (int)(block5 >>> 46 & 8191L);
			values[valuesOffset++] = (int)(block5 >>> 33 & 8191L);
			values[valuesOffset++] = (int)(block5 >>> 20 & 8191L);
			values[valuesOffset++] = (int)(block5 >>> 7 & 8191L);
			long block6 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block5 & 127L) << 6 | block6 >>> 58);
			values[valuesOffset++] = (int)(block6 >>> 45 & 8191L);
			values[valuesOffset++] = (int)(block6 >>> 32 & 8191L);
			values[valuesOffset++] = (int)(block6 >>> 19 & 8191L);
			values[valuesOffset++] = (int)(block6 >>> 6 & 8191L);
			long block7 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block6 & 63L) << 7 | block7 >>> 57);
			values[valuesOffset++] = (int)(block7 >>> 44 & 8191L);
			values[valuesOffset++] = (int)(block7 >>> 31 & 8191L);
			values[valuesOffset++] = (int)(block7 >>> 18 & 8191L);
			values[valuesOffset++] = (int)(block7 >>> 5 & 8191L);
			long block8 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block7 & 31L) << 8 | block8 >>> 56);
			values[valuesOffset++] = (int)(block8 >>> 43 & 8191L);
			values[valuesOffset++] = (int)(block8 >>> 30 & 8191L);
			values[valuesOffset++] = (int)(block8 >>> 17 & 8191L);
			values[valuesOffset++] = (int)(block8 >>> 4 & 8191L);
			long block9 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block8 & 15L) << 9 | block9 >>> 55);
			values[valuesOffset++] = (int)(block9 >>> 42 & 8191L);
			values[valuesOffset++] = (int)(block9 >>> 29 & 8191L);
			values[valuesOffset++] = (int)(block9 >>> 16 & 8191L);
			values[valuesOffset++] = (int)(block9 >>> 3 & 8191L);
			long block10 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block9 & 7L) << 10 | block10 >>> 54);
			values[valuesOffset++] = (int)(block10 >>> 41 & 8191L);
			values[valuesOffset++] = (int)(block10 >>> 28 & 8191L);
			values[valuesOffset++] = (int)(block10 >>> 15 & 8191L);
			values[valuesOffset++] = (int)(block10 >>> 2 & 8191L);
			long block11 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block10 & 3L) << 11 | block11 >>> 53);
			values[valuesOffset++] = (int)(block11 >>> 40 & 8191L);
			values[valuesOffset++] = (int)(block11 >>> 27 & 8191L);
			values[valuesOffset++] = (int)(block11 >>> 14 & 8191L);
			values[valuesOffset++] = (int)(block11 >>> 1 & 8191L);
			long block12 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block11 & 1L) << 12 | block12 >>> 52);
			values[valuesOffset++] = (int)(block12 >>> 39 & 8191L);
			values[valuesOffset++] = (int)(block12 >>> 26 & 8191L);
			values[valuesOffset++] = (int)(block12 >>> 13 & 8191L);
			values[valuesOffset++] = (int)(block12 & 8191L);
		}

	}

	public void decode(byte blocks[], int blocksOffset, int values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < 8 * iterations; i++)
		{
			int byte0 = blocks[blocksOffset++] & 0xff;
			int byte1 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = byte0 << 5 | byte1 >>> 3;
			int byte2 = blocks[blocksOffset++] & 0xff;
			int byte3 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte1 & 7) << 10 | byte2 << 2 | byte3 >>> 6;
			int byte4 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte3 & 0x3f) << 7 | byte4 >>> 1;
			int byte5 = blocks[blocksOffset++] & 0xff;
			int byte6 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte4 & 1) << 12 | byte5 << 4 | byte6 >>> 4;
			int byte7 = blocks[blocksOffset++] & 0xff;
			int byte8 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte6 & 0xf) << 9 | byte7 << 1 | byte8 >>> 7;
			int byte9 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte8 & 0x7f) << 6 | byte9 >>> 2;
			int byte10 = blocks[blocksOffset++] & 0xff;
			int byte11 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte9 & 3) << 11 | byte10 << 3 | byte11 >>> 5;
			int byte12 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte11 & 0x1f) << 8 | byte12;
		}

	}

	public void decode(long blocks[], int blocksOffset, long values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < iterations; i++)
		{
			long block0 = blocks[blocksOffset++];
			values[valuesOffset++] = block0 >>> 51;
			values[valuesOffset++] = block0 >>> 38 & 8191L;
			values[valuesOffset++] = block0 >>> 25 & 8191L;
			values[valuesOffset++] = block0 >>> 12 & 8191L;
			long block1 = blocks[blocksOffset++];
			values[valuesOffset++] = (block0 & 4095L) << 1 | block1 >>> 63;
			values[valuesOffset++] = block1 >>> 50 & 8191L;
			values[valuesOffset++] = block1 >>> 37 & 8191L;
			values[valuesOffset++] = block1 >>> 24 & 8191L;
			values[valuesOffset++] = block1 >>> 11 & 8191L;
			long block2 = blocks[blocksOffset++];
			values[valuesOffset++] = (block1 & 2047L) << 2 | block2 >>> 62;
			values[valuesOffset++] = block2 >>> 49 & 8191L;
			values[valuesOffset++] = block2 >>> 36 & 8191L;
			values[valuesOffset++] = block2 >>> 23 & 8191L;
			values[valuesOffset++] = block2 >>> 10 & 8191L;
			long block3 = blocks[blocksOffset++];
			values[valuesOffset++] = (block2 & 1023L) << 3 | block3 >>> 61;
			values[valuesOffset++] = block3 >>> 48 & 8191L;
			values[valuesOffset++] = block3 >>> 35 & 8191L;
			values[valuesOffset++] = block3 >>> 22 & 8191L;
			values[valuesOffset++] = block3 >>> 9 & 8191L;
			long block4 = blocks[blocksOffset++];
			values[valuesOffset++] = (block3 & 511L) << 4 | block4 >>> 60;
			values[valuesOffset++] = block4 >>> 47 & 8191L;
			values[valuesOffset++] = block4 >>> 34 & 8191L;
			values[valuesOffset++] = block4 >>> 21 & 8191L;
			values[valuesOffset++] = block4 >>> 8 & 8191L;
			long block5 = blocks[blocksOffset++];
			values[valuesOffset++] = (block4 & 255L) << 5 | block5 >>> 59;
			values[valuesOffset++] = block5 >>> 46 & 8191L;
			values[valuesOffset++] = block5 >>> 33 & 8191L;
			values[valuesOffset++] = block5 >>> 20 & 8191L;
			values[valuesOffset++] = block5 >>> 7 & 8191L;
			long block6 = blocks[blocksOffset++];
			values[valuesOffset++] = (block5 & 127L) << 6 | block6 >>> 58;
			values[valuesOffset++] = block6 >>> 45 & 8191L;
			values[valuesOffset++] = block6 >>> 32 & 8191L;
			values[valuesOffset++] = block6 >>> 19 & 8191L;
			values[valuesOffset++] = block6 >>> 6 & 8191L;
			long block7 = blocks[blocksOffset++];
			values[valuesOffset++] = (block6 & 63L) << 7 | block7 >>> 57;
			values[valuesOffset++] = block7 >>> 44 & 8191L;
			values[valuesOffset++] = block7 >>> 31 & 8191L;
			values[valuesOffset++] = block7 >>> 18 & 8191L;
			values[valuesOffset++] = block7 >>> 5 & 8191L;
			long block8 = blocks[blocksOffset++];
			values[valuesOffset++] = (block7 & 31L) << 8 | block8 >>> 56;
			values[valuesOffset++] = block8 >>> 43 & 8191L;
			values[valuesOffset++] = block8 >>> 30 & 8191L;
			values[valuesOffset++] = block8 >>> 17 & 8191L;
			values[valuesOffset++] = block8 >>> 4 & 8191L;
			long block9 = blocks[blocksOffset++];
			values[valuesOffset++] = (block8 & 15L) << 9 | block9 >>> 55;
			values[valuesOffset++] = block9 >>> 42 & 8191L;
			values[valuesOffset++] = block9 >>> 29 & 8191L;
			values[valuesOffset++] = block9 >>> 16 & 8191L;
			values[valuesOffset++] = block9 >>> 3 & 8191L;
			long block10 = blocks[blocksOffset++];
			values[valuesOffset++] = (block9 & 7L) << 10 | block10 >>> 54;
			values[valuesOffset++] = block10 >>> 41 & 8191L;
			values[valuesOffset++] = block10 >>> 28 & 8191L;
			values[valuesOffset++] = block10 >>> 15 & 8191L;
			values[valuesOffset++] = block10 >>> 2 & 8191L;
			long block11 = blocks[blocksOffset++];
			values[valuesOffset++] = (block10 & 3L) << 11 | block11 >>> 53;
			values[valuesOffset++] = block11 >>> 40 & 8191L;
			values[valuesOffset++] = block11 >>> 27 & 8191L;
			values[valuesOffset++] = block11 >>> 14 & 8191L;
			values[valuesOffset++] = block11 >>> 1 & 8191L;
			long block12 = blocks[blocksOffset++];
			values[valuesOffset++] = (block11 & 1L) << 12 | block12 >>> 52;
			values[valuesOffset++] = block12 >>> 39 & 8191L;
			values[valuesOffset++] = block12 >>> 26 & 8191L;
			values[valuesOffset++] = block12 >>> 13 & 8191L;
			values[valuesOffset++] = block12 & 8191L;
		}

	}

	public void decode(byte blocks[], int blocksOffset, long values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < 8 * iterations; i++)
		{
			long byte0 = blocks[blocksOffset++] & 0xff;
			long byte1 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = byte0 << 5 | byte1 >>> 3;
			long byte2 = blocks[blocksOffset++] & 0xff;
			long byte3 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte1 & 7L) << 10 | byte2 << 2 | byte3 >>> 6;
			long byte4 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte3 & 63L) << 7 | byte4 >>> 1;
			long byte5 = blocks[blocksOffset++] & 0xff;
			long byte6 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte4 & 1L) << 12 | byte5 << 4 | byte6 >>> 4;
			long byte7 = blocks[blocksOffset++] & 0xff;
			long byte8 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte6 & 15L) << 9 | byte7 << 1 | byte8 >>> 7;
			long byte9 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte8 & 127L) << 6 | byte9 >>> 2;
			long byte10 = blocks[blocksOffset++] & 0xff;
			long byte11 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte9 & 3L) << 11 | byte10 << 3 | byte11 >>> 5;
			long byte12 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte11 & 31L) << 8 | byte12;
		}

	}

}
