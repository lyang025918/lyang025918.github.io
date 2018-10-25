// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BulkOperationPacked21.java

package org.apache.lucene.util.packed;


// Referenced classes of package org.apache.lucene.util.packed:
//			BulkOperationPacked

final class BulkOperationPacked21 extends BulkOperationPacked
{

	static final boolean $assertionsDisabled = !org/apache/lucene/util/packed/BulkOperationPacked21.desiredAssertionStatus();

	public BulkOperationPacked21()
	{
		super(21);
		if (!$assertionsDisabled && blockCount() != 21)
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
			values[valuesOffset++] = (int)(block0 >>> 43);
			values[valuesOffset++] = (int)(block0 >>> 22 & 0x1fffffL);
			values[valuesOffset++] = (int)(block0 >>> 1 & 0x1fffffL);
			long block1 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block0 & 1L) << 20 | block1 >>> 44);
			values[valuesOffset++] = (int)(block1 >>> 23 & 0x1fffffL);
			values[valuesOffset++] = (int)(block1 >>> 2 & 0x1fffffL);
			long block2 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block1 & 3L) << 19 | block2 >>> 45);
			values[valuesOffset++] = (int)(block2 >>> 24 & 0x1fffffL);
			values[valuesOffset++] = (int)(block2 >>> 3 & 0x1fffffL);
			long block3 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block2 & 7L) << 18 | block3 >>> 46);
			values[valuesOffset++] = (int)(block3 >>> 25 & 0x1fffffL);
			values[valuesOffset++] = (int)(block3 >>> 4 & 0x1fffffL);
			long block4 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block3 & 15L) << 17 | block4 >>> 47);
			values[valuesOffset++] = (int)(block4 >>> 26 & 0x1fffffL);
			values[valuesOffset++] = (int)(block4 >>> 5 & 0x1fffffL);
			long block5 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block4 & 31L) << 16 | block5 >>> 48);
			values[valuesOffset++] = (int)(block5 >>> 27 & 0x1fffffL);
			values[valuesOffset++] = (int)(block5 >>> 6 & 0x1fffffL);
			long block6 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block5 & 63L) << 15 | block6 >>> 49);
			values[valuesOffset++] = (int)(block6 >>> 28 & 0x1fffffL);
			values[valuesOffset++] = (int)(block6 >>> 7 & 0x1fffffL);
			long block7 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block6 & 127L) << 14 | block7 >>> 50);
			values[valuesOffset++] = (int)(block7 >>> 29 & 0x1fffffL);
			values[valuesOffset++] = (int)(block7 >>> 8 & 0x1fffffL);
			long block8 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block7 & 255L) << 13 | block8 >>> 51);
			values[valuesOffset++] = (int)(block8 >>> 30 & 0x1fffffL);
			values[valuesOffset++] = (int)(block8 >>> 9 & 0x1fffffL);
			long block9 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block8 & 511L) << 12 | block9 >>> 52);
			values[valuesOffset++] = (int)(block9 >>> 31 & 0x1fffffL);
			values[valuesOffset++] = (int)(block9 >>> 10 & 0x1fffffL);
			long block10 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block9 & 1023L) << 11 | block10 >>> 53);
			values[valuesOffset++] = (int)(block10 >>> 32 & 0x1fffffL);
			values[valuesOffset++] = (int)(block10 >>> 11 & 0x1fffffL);
			long block11 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block10 & 2047L) << 10 | block11 >>> 54);
			values[valuesOffset++] = (int)(block11 >>> 33 & 0x1fffffL);
			values[valuesOffset++] = (int)(block11 >>> 12 & 0x1fffffL);
			long block12 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block11 & 4095L) << 9 | block12 >>> 55);
			values[valuesOffset++] = (int)(block12 >>> 34 & 0x1fffffL);
			values[valuesOffset++] = (int)(block12 >>> 13 & 0x1fffffL);
			long block13 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block12 & 8191L) << 8 | block13 >>> 56);
			values[valuesOffset++] = (int)(block13 >>> 35 & 0x1fffffL);
			values[valuesOffset++] = (int)(block13 >>> 14 & 0x1fffffL);
			long block14 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block13 & 16383L) << 7 | block14 >>> 57);
			values[valuesOffset++] = (int)(block14 >>> 36 & 0x1fffffL);
			values[valuesOffset++] = (int)(block14 >>> 15 & 0x1fffffL);
			long block15 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block14 & 32767L) << 6 | block15 >>> 58);
			values[valuesOffset++] = (int)(block15 >>> 37 & 0x1fffffL);
			values[valuesOffset++] = (int)(block15 >>> 16 & 0x1fffffL);
			long block16 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block15 & 65535L) << 5 | block16 >>> 59);
			values[valuesOffset++] = (int)(block16 >>> 38 & 0x1fffffL);
			values[valuesOffset++] = (int)(block16 >>> 17 & 0x1fffffL);
			long block17 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block16 & 0x1ffffL) << 4 | block17 >>> 60);
			values[valuesOffset++] = (int)(block17 >>> 39 & 0x1fffffL);
			values[valuesOffset++] = (int)(block17 >>> 18 & 0x1fffffL);
			long block18 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block17 & 0x3ffffL) << 3 | block18 >>> 61);
			values[valuesOffset++] = (int)(block18 >>> 40 & 0x1fffffL);
			values[valuesOffset++] = (int)(block18 >>> 19 & 0x1fffffL);
			long block19 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block18 & 0x7ffffL) << 2 | block19 >>> 62);
			values[valuesOffset++] = (int)(block19 >>> 41 & 0x1fffffL);
			values[valuesOffset++] = (int)(block19 >>> 20 & 0x1fffffL);
			long block20 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block19 & 0xfffffL) << 1 | block20 >>> 63);
			values[valuesOffset++] = (int)(block20 >>> 42 & 0x1fffffL);
			values[valuesOffset++] = (int)(block20 >>> 21 & 0x1fffffL);
			values[valuesOffset++] = (int)(block20 & 0x1fffffL);
		}

	}

	public void decode(byte blocks[], int blocksOffset, int values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < 8 * iterations; i++)
		{
			int byte0 = blocks[blocksOffset++] & 0xff;
			int byte1 = blocks[blocksOffset++] & 0xff;
			int byte2 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = byte0 << 13 | byte1 << 5 | byte2 >>> 3;
			int byte3 = blocks[blocksOffset++] & 0xff;
			int byte4 = blocks[blocksOffset++] & 0xff;
			int byte5 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte2 & 7) << 18 | byte3 << 10 | byte4 << 2 | byte5 >>> 6;
			int byte6 = blocks[blocksOffset++] & 0xff;
			int byte7 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte5 & 0x3f) << 15 | byte6 << 7 | byte7 >>> 1;
			int byte8 = blocks[blocksOffset++] & 0xff;
			int byte9 = blocks[blocksOffset++] & 0xff;
			int byte10 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte7 & 1) << 20 | byte8 << 12 | byte9 << 4 | byte10 >>> 4;
			int byte11 = blocks[blocksOffset++] & 0xff;
			int byte12 = blocks[blocksOffset++] & 0xff;
			int byte13 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte10 & 0xf) << 17 | byte11 << 9 | byte12 << 1 | byte13 >>> 7;
			int byte14 = blocks[blocksOffset++] & 0xff;
			int byte15 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte13 & 0x7f) << 14 | byte14 << 6 | byte15 >>> 2;
			int byte16 = blocks[blocksOffset++] & 0xff;
			int byte17 = blocks[blocksOffset++] & 0xff;
			int byte18 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte15 & 3) << 19 | byte16 << 11 | byte17 << 3 | byte18 >>> 5;
			int byte19 = blocks[blocksOffset++] & 0xff;
			int byte20 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte18 & 0x1f) << 16 | byte19 << 8 | byte20;
		}

	}

	public void decode(long blocks[], int blocksOffset, long values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < iterations; i++)
		{
			long block0 = blocks[blocksOffset++];
			values[valuesOffset++] = block0 >>> 43;
			values[valuesOffset++] = block0 >>> 22 & 0x1fffffL;
			values[valuesOffset++] = block0 >>> 1 & 0x1fffffL;
			long block1 = blocks[blocksOffset++];
			values[valuesOffset++] = (block0 & 1L) << 20 | block1 >>> 44;
			values[valuesOffset++] = block1 >>> 23 & 0x1fffffL;
			values[valuesOffset++] = block1 >>> 2 & 0x1fffffL;
			long block2 = blocks[blocksOffset++];
			values[valuesOffset++] = (block1 & 3L) << 19 | block2 >>> 45;
			values[valuesOffset++] = block2 >>> 24 & 0x1fffffL;
			values[valuesOffset++] = block2 >>> 3 & 0x1fffffL;
			long block3 = blocks[blocksOffset++];
			values[valuesOffset++] = (block2 & 7L) << 18 | block3 >>> 46;
			values[valuesOffset++] = block3 >>> 25 & 0x1fffffL;
			values[valuesOffset++] = block3 >>> 4 & 0x1fffffL;
			long block4 = blocks[blocksOffset++];
			values[valuesOffset++] = (block3 & 15L) << 17 | block4 >>> 47;
			values[valuesOffset++] = block4 >>> 26 & 0x1fffffL;
			values[valuesOffset++] = block4 >>> 5 & 0x1fffffL;
			long block5 = blocks[blocksOffset++];
			values[valuesOffset++] = (block4 & 31L) << 16 | block5 >>> 48;
			values[valuesOffset++] = block5 >>> 27 & 0x1fffffL;
			values[valuesOffset++] = block5 >>> 6 & 0x1fffffL;
			long block6 = blocks[blocksOffset++];
			values[valuesOffset++] = (block5 & 63L) << 15 | block6 >>> 49;
			values[valuesOffset++] = block6 >>> 28 & 0x1fffffL;
			values[valuesOffset++] = block6 >>> 7 & 0x1fffffL;
			long block7 = blocks[blocksOffset++];
			values[valuesOffset++] = (block6 & 127L) << 14 | block7 >>> 50;
			values[valuesOffset++] = block7 >>> 29 & 0x1fffffL;
			values[valuesOffset++] = block7 >>> 8 & 0x1fffffL;
			long block8 = blocks[blocksOffset++];
			values[valuesOffset++] = (block7 & 255L) << 13 | block8 >>> 51;
			values[valuesOffset++] = block8 >>> 30 & 0x1fffffL;
			values[valuesOffset++] = block8 >>> 9 & 0x1fffffL;
			long block9 = blocks[blocksOffset++];
			values[valuesOffset++] = (block8 & 511L) << 12 | block9 >>> 52;
			values[valuesOffset++] = block9 >>> 31 & 0x1fffffL;
			values[valuesOffset++] = block9 >>> 10 & 0x1fffffL;
			long block10 = blocks[blocksOffset++];
			values[valuesOffset++] = (block9 & 1023L) << 11 | block10 >>> 53;
			values[valuesOffset++] = block10 >>> 32 & 0x1fffffL;
			values[valuesOffset++] = block10 >>> 11 & 0x1fffffL;
			long block11 = blocks[blocksOffset++];
			values[valuesOffset++] = (block10 & 2047L) << 10 | block11 >>> 54;
			values[valuesOffset++] = block11 >>> 33 & 0x1fffffL;
			values[valuesOffset++] = block11 >>> 12 & 0x1fffffL;
			long block12 = blocks[blocksOffset++];
			values[valuesOffset++] = (block11 & 4095L) << 9 | block12 >>> 55;
			values[valuesOffset++] = block12 >>> 34 & 0x1fffffL;
			values[valuesOffset++] = block12 >>> 13 & 0x1fffffL;
			long block13 = blocks[blocksOffset++];
			values[valuesOffset++] = (block12 & 8191L) << 8 | block13 >>> 56;
			values[valuesOffset++] = block13 >>> 35 & 0x1fffffL;
			values[valuesOffset++] = block13 >>> 14 & 0x1fffffL;
			long block14 = blocks[blocksOffset++];
			values[valuesOffset++] = (block13 & 16383L) << 7 | block14 >>> 57;
			values[valuesOffset++] = block14 >>> 36 & 0x1fffffL;
			values[valuesOffset++] = block14 >>> 15 & 0x1fffffL;
			long block15 = blocks[blocksOffset++];
			values[valuesOffset++] = (block14 & 32767L) << 6 | block15 >>> 58;
			values[valuesOffset++] = block15 >>> 37 & 0x1fffffL;
			values[valuesOffset++] = block15 >>> 16 & 0x1fffffL;
			long block16 = blocks[blocksOffset++];
			values[valuesOffset++] = (block15 & 65535L) << 5 | block16 >>> 59;
			values[valuesOffset++] = block16 >>> 38 & 0x1fffffL;
			values[valuesOffset++] = block16 >>> 17 & 0x1fffffL;
			long block17 = blocks[blocksOffset++];
			values[valuesOffset++] = (block16 & 0x1ffffL) << 4 | block17 >>> 60;
			values[valuesOffset++] = block17 >>> 39 & 0x1fffffL;
			values[valuesOffset++] = block17 >>> 18 & 0x1fffffL;
			long block18 = blocks[blocksOffset++];
			values[valuesOffset++] = (block17 & 0x3ffffL) << 3 | block18 >>> 61;
			values[valuesOffset++] = block18 >>> 40 & 0x1fffffL;
			values[valuesOffset++] = block18 >>> 19 & 0x1fffffL;
			long block19 = blocks[blocksOffset++];
			values[valuesOffset++] = (block18 & 0x7ffffL) << 2 | block19 >>> 62;
			values[valuesOffset++] = block19 >>> 41 & 0x1fffffL;
			values[valuesOffset++] = block19 >>> 20 & 0x1fffffL;
			long block20 = blocks[blocksOffset++];
			values[valuesOffset++] = (block19 & 0xfffffL) << 1 | block20 >>> 63;
			values[valuesOffset++] = block20 >>> 42 & 0x1fffffL;
			values[valuesOffset++] = block20 >>> 21 & 0x1fffffL;
			values[valuesOffset++] = block20 & 0x1fffffL;
		}

	}

	public void decode(byte blocks[], int blocksOffset, long values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < 8 * iterations; i++)
		{
			long byte0 = blocks[blocksOffset++] & 0xff;
			long byte1 = blocks[blocksOffset++] & 0xff;
			long byte2 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = byte0 << 13 | byte1 << 5 | byte2 >>> 3;
			long byte3 = blocks[blocksOffset++] & 0xff;
			long byte4 = blocks[blocksOffset++] & 0xff;
			long byte5 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte2 & 7L) << 18 | byte3 << 10 | byte4 << 2 | byte5 >>> 6;
			long byte6 = blocks[blocksOffset++] & 0xff;
			long byte7 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte5 & 63L) << 15 | byte6 << 7 | byte7 >>> 1;
			long byte8 = blocks[blocksOffset++] & 0xff;
			long byte9 = blocks[blocksOffset++] & 0xff;
			long byte10 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte7 & 1L) << 20 | byte8 << 12 | byte9 << 4 | byte10 >>> 4;
			long byte11 = blocks[blocksOffset++] & 0xff;
			long byte12 = blocks[blocksOffset++] & 0xff;
			long byte13 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte10 & 15L) << 17 | byte11 << 9 | byte12 << 1 | byte13 >>> 7;
			long byte14 = blocks[blocksOffset++] & 0xff;
			long byte15 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte13 & 127L) << 14 | byte14 << 6 | byte15 >>> 2;
			long byte16 = blocks[blocksOffset++] & 0xff;
			long byte17 = blocks[blocksOffset++] & 0xff;
			long byte18 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte15 & 3L) << 19 | byte16 << 11 | byte17 << 3 | byte18 >>> 5;
			long byte19 = blocks[blocksOffset++] & 0xff;
			long byte20 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte18 & 31L) << 16 | byte19 << 8 | byte20;
		}

	}

}
