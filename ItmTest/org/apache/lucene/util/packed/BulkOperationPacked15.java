// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BulkOperationPacked15.java

package org.apache.lucene.util.packed;


// Referenced classes of package org.apache.lucene.util.packed:
//			BulkOperationPacked

final class BulkOperationPacked15 extends BulkOperationPacked
{

	static final boolean $assertionsDisabled = !org/apache/lucene/util/packed/BulkOperationPacked15.desiredAssertionStatus();

	public BulkOperationPacked15()
	{
		super(15);
		if (!$assertionsDisabled && blockCount() != 15)
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
			values[valuesOffset++] = (int)(block0 >>> 49);
			values[valuesOffset++] = (int)(block0 >>> 34 & 32767L);
			values[valuesOffset++] = (int)(block0 >>> 19 & 32767L);
			values[valuesOffset++] = (int)(block0 >>> 4 & 32767L);
			long block1 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block0 & 15L) << 11 | block1 >>> 53);
			values[valuesOffset++] = (int)(block1 >>> 38 & 32767L);
			values[valuesOffset++] = (int)(block1 >>> 23 & 32767L);
			values[valuesOffset++] = (int)(block1 >>> 8 & 32767L);
			long block2 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block1 & 255L) << 7 | block2 >>> 57);
			values[valuesOffset++] = (int)(block2 >>> 42 & 32767L);
			values[valuesOffset++] = (int)(block2 >>> 27 & 32767L);
			values[valuesOffset++] = (int)(block2 >>> 12 & 32767L);
			long block3 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block2 & 4095L) << 3 | block3 >>> 61);
			values[valuesOffset++] = (int)(block3 >>> 46 & 32767L);
			values[valuesOffset++] = (int)(block3 >>> 31 & 32767L);
			values[valuesOffset++] = (int)(block3 >>> 16 & 32767L);
			values[valuesOffset++] = (int)(block3 >>> 1 & 32767L);
			long block4 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block3 & 1L) << 14 | block4 >>> 50);
			values[valuesOffset++] = (int)(block4 >>> 35 & 32767L);
			values[valuesOffset++] = (int)(block4 >>> 20 & 32767L);
			values[valuesOffset++] = (int)(block4 >>> 5 & 32767L);
			long block5 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block4 & 31L) << 10 | block5 >>> 54);
			values[valuesOffset++] = (int)(block5 >>> 39 & 32767L);
			values[valuesOffset++] = (int)(block5 >>> 24 & 32767L);
			values[valuesOffset++] = (int)(block5 >>> 9 & 32767L);
			long block6 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block5 & 511L) << 6 | block6 >>> 58);
			values[valuesOffset++] = (int)(block6 >>> 43 & 32767L);
			values[valuesOffset++] = (int)(block6 >>> 28 & 32767L);
			values[valuesOffset++] = (int)(block6 >>> 13 & 32767L);
			long block7 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block6 & 8191L) << 2 | block7 >>> 62);
			values[valuesOffset++] = (int)(block7 >>> 47 & 32767L);
			values[valuesOffset++] = (int)(block7 >>> 32 & 32767L);
			values[valuesOffset++] = (int)(block7 >>> 17 & 32767L);
			values[valuesOffset++] = (int)(block7 >>> 2 & 32767L);
			long block8 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block7 & 3L) << 13 | block8 >>> 51);
			values[valuesOffset++] = (int)(block8 >>> 36 & 32767L);
			values[valuesOffset++] = (int)(block8 >>> 21 & 32767L);
			values[valuesOffset++] = (int)(block8 >>> 6 & 32767L);
			long block9 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block8 & 63L) << 9 | block9 >>> 55);
			values[valuesOffset++] = (int)(block9 >>> 40 & 32767L);
			values[valuesOffset++] = (int)(block9 >>> 25 & 32767L);
			values[valuesOffset++] = (int)(block9 >>> 10 & 32767L);
			long block10 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block9 & 1023L) << 5 | block10 >>> 59);
			values[valuesOffset++] = (int)(block10 >>> 44 & 32767L);
			values[valuesOffset++] = (int)(block10 >>> 29 & 32767L);
			values[valuesOffset++] = (int)(block10 >>> 14 & 32767L);
			long block11 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block10 & 16383L) << 1 | block11 >>> 63);
			values[valuesOffset++] = (int)(block11 >>> 48 & 32767L);
			values[valuesOffset++] = (int)(block11 >>> 33 & 32767L);
			values[valuesOffset++] = (int)(block11 >>> 18 & 32767L);
			values[valuesOffset++] = (int)(block11 >>> 3 & 32767L);
			long block12 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block11 & 7L) << 12 | block12 >>> 52);
			values[valuesOffset++] = (int)(block12 >>> 37 & 32767L);
			values[valuesOffset++] = (int)(block12 >>> 22 & 32767L);
			values[valuesOffset++] = (int)(block12 >>> 7 & 32767L);
			long block13 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block12 & 127L) << 8 | block13 >>> 56);
			values[valuesOffset++] = (int)(block13 >>> 41 & 32767L);
			values[valuesOffset++] = (int)(block13 >>> 26 & 32767L);
			values[valuesOffset++] = (int)(block13 >>> 11 & 32767L);
			long block14 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block13 & 2047L) << 4 | block14 >>> 60);
			values[valuesOffset++] = (int)(block14 >>> 45 & 32767L);
			values[valuesOffset++] = (int)(block14 >>> 30 & 32767L);
			values[valuesOffset++] = (int)(block14 >>> 15 & 32767L);
			values[valuesOffset++] = (int)(block14 & 32767L);
		}

	}

	public void decode(byte blocks[], int blocksOffset, int values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < 8 * iterations; i++)
		{
			int byte0 = blocks[blocksOffset++] & 0xff;
			int byte1 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = byte0 << 7 | byte1 >>> 1;
			int byte2 = blocks[blocksOffset++] & 0xff;
			int byte3 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte1 & 1) << 14 | byte2 << 6 | byte3 >>> 2;
			int byte4 = blocks[blocksOffset++] & 0xff;
			int byte5 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte3 & 3) << 13 | byte4 << 5 | byte5 >>> 3;
			int byte6 = blocks[blocksOffset++] & 0xff;
			int byte7 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte5 & 7) << 12 | byte6 << 4 | byte7 >>> 4;
			int byte8 = blocks[blocksOffset++] & 0xff;
			int byte9 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte7 & 0xf) << 11 | byte8 << 3 | byte9 >>> 5;
			int byte10 = blocks[blocksOffset++] & 0xff;
			int byte11 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte9 & 0x1f) << 10 | byte10 << 2 | byte11 >>> 6;
			int byte12 = blocks[blocksOffset++] & 0xff;
			int byte13 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte11 & 0x3f) << 9 | byte12 << 1 | byte13 >>> 7;
			int byte14 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte13 & 0x7f) << 8 | byte14;
		}

	}

	public void decode(long blocks[], int blocksOffset, long values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < iterations; i++)
		{
			long block0 = blocks[blocksOffset++];
			values[valuesOffset++] = block0 >>> 49;
			values[valuesOffset++] = block0 >>> 34 & 32767L;
			values[valuesOffset++] = block0 >>> 19 & 32767L;
			values[valuesOffset++] = block0 >>> 4 & 32767L;
			long block1 = blocks[blocksOffset++];
			values[valuesOffset++] = (block0 & 15L) << 11 | block1 >>> 53;
			values[valuesOffset++] = block1 >>> 38 & 32767L;
			values[valuesOffset++] = block1 >>> 23 & 32767L;
			values[valuesOffset++] = block1 >>> 8 & 32767L;
			long block2 = blocks[blocksOffset++];
			values[valuesOffset++] = (block1 & 255L) << 7 | block2 >>> 57;
			values[valuesOffset++] = block2 >>> 42 & 32767L;
			values[valuesOffset++] = block2 >>> 27 & 32767L;
			values[valuesOffset++] = block2 >>> 12 & 32767L;
			long block3 = blocks[blocksOffset++];
			values[valuesOffset++] = (block2 & 4095L) << 3 | block3 >>> 61;
			values[valuesOffset++] = block3 >>> 46 & 32767L;
			values[valuesOffset++] = block3 >>> 31 & 32767L;
			values[valuesOffset++] = block3 >>> 16 & 32767L;
			values[valuesOffset++] = block3 >>> 1 & 32767L;
			long block4 = blocks[blocksOffset++];
			values[valuesOffset++] = (block3 & 1L) << 14 | block4 >>> 50;
			values[valuesOffset++] = block4 >>> 35 & 32767L;
			values[valuesOffset++] = block4 >>> 20 & 32767L;
			values[valuesOffset++] = block4 >>> 5 & 32767L;
			long block5 = blocks[blocksOffset++];
			values[valuesOffset++] = (block4 & 31L) << 10 | block5 >>> 54;
			values[valuesOffset++] = block5 >>> 39 & 32767L;
			values[valuesOffset++] = block5 >>> 24 & 32767L;
			values[valuesOffset++] = block5 >>> 9 & 32767L;
			long block6 = blocks[blocksOffset++];
			values[valuesOffset++] = (block5 & 511L) << 6 | block6 >>> 58;
			values[valuesOffset++] = block6 >>> 43 & 32767L;
			values[valuesOffset++] = block6 >>> 28 & 32767L;
			values[valuesOffset++] = block6 >>> 13 & 32767L;
			long block7 = blocks[blocksOffset++];
			values[valuesOffset++] = (block6 & 8191L) << 2 | block7 >>> 62;
			values[valuesOffset++] = block7 >>> 47 & 32767L;
			values[valuesOffset++] = block7 >>> 32 & 32767L;
			values[valuesOffset++] = block7 >>> 17 & 32767L;
			values[valuesOffset++] = block7 >>> 2 & 32767L;
			long block8 = blocks[blocksOffset++];
			values[valuesOffset++] = (block7 & 3L) << 13 | block8 >>> 51;
			values[valuesOffset++] = block8 >>> 36 & 32767L;
			values[valuesOffset++] = block8 >>> 21 & 32767L;
			values[valuesOffset++] = block8 >>> 6 & 32767L;
			long block9 = blocks[blocksOffset++];
			values[valuesOffset++] = (block8 & 63L) << 9 | block9 >>> 55;
			values[valuesOffset++] = block9 >>> 40 & 32767L;
			values[valuesOffset++] = block9 >>> 25 & 32767L;
			values[valuesOffset++] = block9 >>> 10 & 32767L;
			long block10 = blocks[blocksOffset++];
			values[valuesOffset++] = (block9 & 1023L) << 5 | block10 >>> 59;
			values[valuesOffset++] = block10 >>> 44 & 32767L;
			values[valuesOffset++] = block10 >>> 29 & 32767L;
			values[valuesOffset++] = block10 >>> 14 & 32767L;
			long block11 = blocks[blocksOffset++];
			values[valuesOffset++] = (block10 & 16383L) << 1 | block11 >>> 63;
			values[valuesOffset++] = block11 >>> 48 & 32767L;
			values[valuesOffset++] = block11 >>> 33 & 32767L;
			values[valuesOffset++] = block11 >>> 18 & 32767L;
			values[valuesOffset++] = block11 >>> 3 & 32767L;
			long block12 = blocks[blocksOffset++];
			values[valuesOffset++] = (block11 & 7L) << 12 | block12 >>> 52;
			values[valuesOffset++] = block12 >>> 37 & 32767L;
			values[valuesOffset++] = block12 >>> 22 & 32767L;
			values[valuesOffset++] = block12 >>> 7 & 32767L;
			long block13 = blocks[blocksOffset++];
			values[valuesOffset++] = (block12 & 127L) << 8 | block13 >>> 56;
			values[valuesOffset++] = block13 >>> 41 & 32767L;
			values[valuesOffset++] = block13 >>> 26 & 32767L;
			values[valuesOffset++] = block13 >>> 11 & 32767L;
			long block14 = blocks[blocksOffset++];
			values[valuesOffset++] = (block13 & 2047L) << 4 | block14 >>> 60;
			values[valuesOffset++] = block14 >>> 45 & 32767L;
			values[valuesOffset++] = block14 >>> 30 & 32767L;
			values[valuesOffset++] = block14 >>> 15 & 32767L;
			values[valuesOffset++] = block14 & 32767L;
		}

	}

	public void decode(byte blocks[], int blocksOffset, long values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < 8 * iterations; i++)
		{
			long byte0 = blocks[blocksOffset++] & 0xff;
			long byte1 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = byte0 << 7 | byte1 >>> 1;
			long byte2 = blocks[blocksOffset++] & 0xff;
			long byte3 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte1 & 1L) << 14 | byte2 << 6 | byte3 >>> 2;
			long byte4 = blocks[blocksOffset++] & 0xff;
			long byte5 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte3 & 3L) << 13 | byte4 << 5 | byte5 >>> 3;
			long byte6 = blocks[blocksOffset++] & 0xff;
			long byte7 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte5 & 7L) << 12 | byte6 << 4 | byte7 >>> 4;
			long byte8 = blocks[blocksOffset++] & 0xff;
			long byte9 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte7 & 15L) << 11 | byte8 << 3 | byte9 >>> 5;
			long byte10 = blocks[blocksOffset++] & 0xff;
			long byte11 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte9 & 31L) << 10 | byte10 << 2 | byte11 >>> 6;
			long byte12 = blocks[blocksOffset++] & 0xff;
			long byte13 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte11 & 63L) << 9 | byte12 << 1 | byte13 >>> 7;
			long byte14 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte13 & 127L) << 8 | byte14;
		}

	}

}
