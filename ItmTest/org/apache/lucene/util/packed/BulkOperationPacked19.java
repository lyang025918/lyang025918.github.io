// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BulkOperationPacked19.java

package org.apache.lucene.util.packed;


// Referenced classes of package org.apache.lucene.util.packed:
//			BulkOperationPacked

final class BulkOperationPacked19 extends BulkOperationPacked
{

	static final boolean $assertionsDisabled = !org/apache/lucene/util/packed/BulkOperationPacked19.desiredAssertionStatus();

	public BulkOperationPacked19()
	{
		super(19);
		if (!$assertionsDisabled && blockCount() != 19)
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
			values[valuesOffset++] = (int)(block0 >>> 45);
			values[valuesOffset++] = (int)(block0 >>> 26 & 0x7ffffL);
			values[valuesOffset++] = (int)(block0 >>> 7 & 0x7ffffL);
			long block1 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block0 & 127L) << 12 | block1 >>> 52);
			values[valuesOffset++] = (int)(block1 >>> 33 & 0x7ffffL);
			values[valuesOffset++] = (int)(block1 >>> 14 & 0x7ffffL);
			long block2 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block1 & 16383L) << 5 | block2 >>> 59);
			values[valuesOffset++] = (int)(block2 >>> 40 & 0x7ffffL);
			values[valuesOffset++] = (int)(block2 >>> 21 & 0x7ffffL);
			values[valuesOffset++] = (int)(block2 >>> 2 & 0x7ffffL);
			long block3 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block2 & 3L) << 17 | block3 >>> 47);
			values[valuesOffset++] = (int)(block3 >>> 28 & 0x7ffffL);
			values[valuesOffset++] = (int)(block3 >>> 9 & 0x7ffffL);
			long block4 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block3 & 511L) << 10 | block4 >>> 54);
			values[valuesOffset++] = (int)(block4 >>> 35 & 0x7ffffL);
			values[valuesOffset++] = (int)(block4 >>> 16 & 0x7ffffL);
			long block5 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block4 & 65535L) << 3 | block5 >>> 61);
			values[valuesOffset++] = (int)(block5 >>> 42 & 0x7ffffL);
			values[valuesOffset++] = (int)(block5 >>> 23 & 0x7ffffL);
			values[valuesOffset++] = (int)(block5 >>> 4 & 0x7ffffL);
			long block6 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block5 & 15L) << 15 | block6 >>> 49);
			values[valuesOffset++] = (int)(block6 >>> 30 & 0x7ffffL);
			values[valuesOffset++] = (int)(block6 >>> 11 & 0x7ffffL);
			long block7 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block6 & 2047L) << 8 | block7 >>> 56);
			values[valuesOffset++] = (int)(block7 >>> 37 & 0x7ffffL);
			values[valuesOffset++] = (int)(block7 >>> 18 & 0x7ffffL);
			long block8 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block7 & 0x3ffffL) << 1 | block8 >>> 63);
			values[valuesOffset++] = (int)(block8 >>> 44 & 0x7ffffL);
			values[valuesOffset++] = (int)(block8 >>> 25 & 0x7ffffL);
			values[valuesOffset++] = (int)(block8 >>> 6 & 0x7ffffL);
			long block9 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block8 & 63L) << 13 | block9 >>> 51);
			values[valuesOffset++] = (int)(block9 >>> 32 & 0x7ffffL);
			values[valuesOffset++] = (int)(block9 >>> 13 & 0x7ffffL);
			long block10 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block9 & 8191L) << 6 | block10 >>> 58);
			values[valuesOffset++] = (int)(block10 >>> 39 & 0x7ffffL);
			values[valuesOffset++] = (int)(block10 >>> 20 & 0x7ffffL);
			values[valuesOffset++] = (int)(block10 >>> 1 & 0x7ffffL);
			long block11 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block10 & 1L) << 18 | block11 >>> 46);
			values[valuesOffset++] = (int)(block11 >>> 27 & 0x7ffffL);
			values[valuesOffset++] = (int)(block11 >>> 8 & 0x7ffffL);
			long block12 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block11 & 255L) << 11 | block12 >>> 53);
			values[valuesOffset++] = (int)(block12 >>> 34 & 0x7ffffL);
			values[valuesOffset++] = (int)(block12 >>> 15 & 0x7ffffL);
			long block13 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block12 & 32767L) << 4 | block13 >>> 60);
			values[valuesOffset++] = (int)(block13 >>> 41 & 0x7ffffL);
			values[valuesOffset++] = (int)(block13 >>> 22 & 0x7ffffL);
			values[valuesOffset++] = (int)(block13 >>> 3 & 0x7ffffL);
			long block14 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block13 & 7L) << 16 | block14 >>> 48);
			values[valuesOffset++] = (int)(block14 >>> 29 & 0x7ffffL);
			values[valuesOffset++] = (int)(block14 >>> 10 & 0x7ffffL);
			long block15 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block14 & 1023L) << 9 | block15 >>> 55);
			values[valuesOffset++] = (int)(block15 >>> 36 & 0x7ffffL);
			values[valuesOffset++] = (int)(block15 >>> 17 & 0x7ffffL);
			long block16 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block15 & 0x1ffffL) << 2 | block16 >>> 62);
			values[valuesOffset++] = (int)(block16 >>> 43 & 0x7ffffL);
			values[valuesOffset++] = (int)(block16 >>> 24 & 0x7ffffL);
			values[valuesOffset++] = (int)(block16 >>> 5 & 0x7ffffL);
			long block17 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block16 & 31L) << 14 | block17 >>> 50);
			values[valuesOffset++] = (int)(block17 >>> 31 & 0x7ffffL);
			values[valuesOffset++] = (int)(block17 >>> 12 & 0x7ffffL);
			long block18 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block17 & 4095L) << 7 | block18 >>> 57);
			values[valuesOffset++] = (int)(block18 >>> 38 & 0x7ffffL);
			values[valuesOffset++] = (int)(block18 >>> 19 & 0x7ffffL);
			values[valuesOffset++] = (int)(block18 & 0x7ffffL);
		}

	}

	public void decode(byte blocks[], int blocksOffset, int values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < 8 * iterations; i++)
		{
			int byte0 = blocks[blocksOffset++] & 0xff;
			int byte1 = blocks[blocksOffset++] & 0xff;
			int byte2 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = byte0 << 11 | byte1 << 3 | byte2 >>> 5;
			int byte3 = blocks[blocksOffset++] & 0xff;
			int byte4 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte2 & 0x1f) << 14 | byte3 << 6 | byte4 >>> 2;
			int byte5 = blocks[blocksOffset++] & 0xff;
			int byte6 = blocks[blocksOffset++] & 0xff;
			int byte7 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte4 & 3) << 17 | byte5 << 9 | byte6 << 1 | byte7 >>> 7;
			int byte8 = blocks[blocksOffset++] & 0xff;
			int byte9 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte7 & 0x7f) << 12 | byte8 << 4 | byte9 >>> 4;
			int byte10 = blocks[blocksOffset++] & 0xff;
			int byte11 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte9 & 0xf) << 15 | byte10 << 7 | byte11 >>> 1;
			int byte12 = blocks[blocksOffset++] & 0xff;
			int byte13 = blocks[blocksOffset++] & 0xff;
			int byte14 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte11 & 1) << 18 | byte12 << 10 | byte13 << 2 | byte14 >>> 6;
			int byte15 = blocks[blocksOffset++] & 0xff;
			int byte16 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte14 & 0x3f) << 13 | byte15 << 5 | byte16 >>> 3;
			int byte17 = blocks[blocksOffset++] & 0xff;
			int byte18 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte16 & 7) << 16 | byte17 << 8 | byte18;
		}

	}

	public void decode(long blocks[], int blocksOffset, long values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < iterations; i++)
		{
			long block0 = blocks[blocksOffset++];
			values[valuesOffset++] = block0 >>> 45;
			values[valuesOffset++] = block0 >>> 26 & 0x7ffffL;
			values[valuesOffset++] = block0 >>> 7 & 0x7ffffL;
			long block1 = blocks[blocksOffset++];
			values[valuesOffset++] = (block0 & 127L) << 12 | block1 >>> 52;
			values[valuesOffset++] = block1 >>> 33 & 0x7ffffL;
			values[valuesOffset++] = block1 >>> 14 & 0x7ffffL;
			long block2 = blocks[blocksOffset++];
			values[valuesOffset++] = (block1 & 16383L) << 5 | block2 >>> 59;
			values[valuesOffset++] = block2 >>> 40 & 0x7ffffL;
			values[valuesOffset++] = block2 >>> 21 & 0x7ffffL;
			values[valuesOffset++] = block2 >>> 2 & 0x7ffffL;
			long block3 = blocks[blocksOffset++];
			values[valuesOffset++] = (block2 & 3L) << 17 | block3 >>> 47;
			values[valuesOffset++] = block3 >>> 28 & 0x7ffffL;
			values[valuesOffset++] = block3 >>> 9 & 0x7ffffL;
			long block4 = blocks[blocksOffset++];
			values[valuesOffset++] = (block3 & 511L) << 10 | block4 >>> 54;
			values[valuesOffset++] = block4 >>> 35 & 0x7ffffL;
			values[valuesOffset++] = block4 >>> 16 & 0x7ffffL;
			long block5 = blocks[blocksOffset++];
			values[valuesOffset++] = (block4 & 65535L) << 3 | block5 >>> 61;
			values[valuesOffset++] = block5 >>> 42 & 0x7ffffL;
			values[valuesOffset++] = block5 >>> 23 & 0x7ffffL;
			values[valuesOffset++] = block5 >>> 4 & 0x7ffffL;
			long block6 = blocks[blocksOffset++];
			values[valuesOffset++] = (block5 & 15L) << 15 | block6 >>> 49;
			values[valuesOffset++] = block6 >>> 30 & 0x7ffffL;
			values[valuesOffset++] = block6 >>> 11 & 0x7ffffL;
			long block7 = blocks[blocksOffset++];
			values[valuesOffset++] = (block6 & 2047L) << 8 | block7 >>> 56;
			values[valuesOffset++] = block7 >>> 37 & 0x7ffffL;
			values[valuesOffset++] = block7 >>> 18 & 0x7ffffL;
			long block8 = blocks[blocksOffset++];
			values[valuesOffset++] = (block7 & 0x3ffffL) << 1 | block8 >>> 63;
			values[valuesOffset++] = block8 >>> 44 & 0x7ffffL;
			values[valuesOffset++] = block8 >>> 25 & 0x7ffffL;
			values[valuesOffset++] = block8 >>> 6 & 0x7ffffL;
			long block9 = blocks[blocksOffset++];
			values[valuesOffset++] = (block8 & 63L) << 13 | block9 >>> 51;
			values[valuesOffset++] = block9 >>> 32 & 0x7ffffL;
			values[valuesOffset++] = block9 >>> 13 & 0x7ffffL;
			long block10 = blocks[blocksOffset++];
			values[valuesOffset++] = (block9 & 8191L) << 6 | block10 >>> 58;
			values[valuesOffset++] = block10 >>> 39 & 0x7ffffL;
			values[valuesOffset++] = block10 >>> 20 & 0x7ffffL;
			values[valuesOffset++] = block10 >>> 1 & 0x7ffffL;
			long block11 = blocks[blocksOffset++];
			values[valuesOffset++] = (block10 & 1L) << 18 | block11 >>> 46;
			values[valuesOffset++] = block11 >>> 27 & 0x7ffffL;
			values[valuesOffset++] = block11 >>> 8 & 0x7ffffL;
			long block12 = blocks[blocksOffset++];
			values[valuesOffset++] = (block11 & 255L) << 11 | block12 >>> 53;
			values[valuesOffset++] = block12 >>> 34 & 0x7ffffL;
			values[valuesOffset++] = block12 >>> 15 & 0x7ffffL;
			long block13 = blocks[blocksOffset++];
			values[valuesOffset++] = (block12 & 32767L) << 4 | block13 >>> 60;
			values[valuesOffset++] = block13 >>> 41 & 0x7ffffL;
			values[valuesOffset++] = block13 >>> 22 & 0x7ffffL;
			values[valuesOffset++] = block13 >>> 3 & 0x7ffffL;
			long block14 = blocks[blocksOffset++];
			values[valuesOffset++] = (block13 & 7L) << 16 | block14 >>> 48;
			values[valuesOffset++] = block14 >>> 29 & 0x7ffffL;
			values[valuesOffset++] = block14 >>> 10 & 0x7ffffL;
			long block15 = blocks[blocksOffset++];
			values[valuesOffset++] = (block14 & 1023L) << 9 | block15 >>> 55;
			values[valuesOffset++] = block15 >>> 36 & 0x7ffffL;
			values[valuesOffset++] = block15 >>> 17 & 0x7ffffL;
			long block16 = blocks[blocksOffset++];
			values[valuesOffset++] = (block15 & 0x1ffffL) << 2 | block16 >>> 62;
			values[valuesOffset++] = block16 >>> 43 & 0x7ffffL;
			values[valuesOffset++] = block16 >>> 24 & 0x7ffffL;
			values[valuesOffset++] = block16 >>> 5 & 0x7ffffL;
			long block17 = blocks[blocksOffset++];
			values[valuesOffset++] = (block16 & 31L) << 14 | block17 >>> 50;
			values[valuesOffset++] = block17 >>> 31 & 0x7ffffL;
			values[valuesOffset++] = block17 >>> 12 & 0x7ffffL;
			long block18 = blocks[blocksOffset++];
			values[valuesOffset++] = (block17 & 4095L) << 7 | block18 >>> 57;
			values[valuesOffset++] = block18 >>> 38 & 0x7ffffL;
			values[valuesOffset++] = block18 >>> 19 & 0x7ffffL;
			values[valuesOffset++] = block18 & 0x7ffffL;
		}

	}

	public void decode(byte blocks[], int blocksOffset, long values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < 8 * iterations; i++)
		{
			long byte0 = blocks[blocksOffset++] & 0xff;
			long byte1 = blocks[blocksOffset++] & 0xff;
			long byte2 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = byte0 << 11 | byte1 << 3 | byte2 >>> 5;
			long byte3 = blocks[blocksOffset++] & 0xff;
			long byte4 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte2 & 31L) << 14 | byte3 << 6 | byte4 >>> 2;
			long byte5 = blocks[blocksOffset++] & 0xff;
			long byte6 = blocks[blocksOffset++] & 0xff;
			long byte7 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte4 & 3L) << 17 | byte5 << 9 | byte6 << 1 | byte7 >>> 7;
			long byte8 = blocks[blocksOffset++] & 0xff;
			long byte9 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte7 & 127L) << 12 | byte8 << 4 | byte9 >>> 4;
			long byte10 = blocks[blocksOffset++] & 0xff;
			long byte11 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte9 & 15L) << 15 | byte10 << 7 | byte11 >>> 1;
			long byte12 = blocks[blocksOffset++] & 0xff;
			long byte13 = blocks[blocksOffset++] & 0xff;
			long byte14 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte11 & 1L) << 18 | byte12 << 10 | byte13 << 2 | byte14 >>> 6;
			long byte15 = blocks[blocksOffset++] & 0xff;
			long byte16 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte14 & 63L) << 13 | byte15 << 5 | byte16 >>> 3;
			long byte17 = blocks[blocksOffset++] & 0xff;
			long byte18 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte16 & 7L) << 16 | byte17 << 8 | byte18;
		}

	}

}
