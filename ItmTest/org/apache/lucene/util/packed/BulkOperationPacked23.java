// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BulkOperationPacked23.java

package org.apache.lucene.util.packed;


// Referenced classes of package org.apache.lucene.util.packed:
//			BulkOperationPacked

final class BulkOperationPacked23 extends BulkOperationPacked
{

	static final boolean $assertionsDisabled = !org/apache/lucene/util/packed/BulkOperationPacked23.desiredAssertionStatus();

	public BulkOperationPacked23()
	{
		super(23);
		if (!$assertionsDisabled && blockCount() != 23)
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
			values[valuesOffset++] = (int)(block0 >>> 41);
			values[valuesOffset++] = (int)(block0 >>> 18 & 0x7fffffL);
			long block1 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block0 & 0x3ffffL) << 5 | block1 >>> 59);
			values[valuesOffset++] = (int)(block1 >>> 36 & 0x7fffffL);
			values[valuesOffset++] = (int)(block1 >>> 13 & 0x7fffffL);
			long block2 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block1 & 8191L) << 10 | block2 >>> 54);
			values[valuesOffset++] = (int)(block2 >>> 31 & 0x7fffffL);
			values[valuesOffset++] = (int)(block2 >>> 8 & 0x7fffffL);
			long block3 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block2 & 255L) << 15 | block3 >>> 49);
			values[valuesOffset++] = (int)(block3 >>> 26 & 0x7fffffL);
			values[valuesOffset++] = (int)(block3 >>> 3 & 0x7fffffL);
			long block4 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block3 & 7L) << 20 | block4 >>> 44);
			values[valuesOffset++] = (int)(block4 >>> 21 & 0x7fffffL);
			long block5 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block4 & 0x1fffffL) << 2 | block5 >>> 62);
			values[valuesOffset++] = (int)(block5 >>> 39 & 0x7fffffL);
			values[valuesOffset++] = (int)(block5 >>> 16 & 0x7fffffL);
			long block6 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block5 & 65535L) << 7 | block6 >>> 57);
			values[valuesOffset++] = (int)(block6 >>> 34 & 0x7fffffL);
			values[valuesOffset++] = (int)(block6 >>> 11 & 0x7fffffL);
			long block7 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block6 & 2047L) << 12 | block7 >>> 52);
			values[valuesOffset++] = (int)(block7 >>> 29 & 0x7fffffL);
			values[valuesOffset++] = (int)(block7 >>> 6 & 0x7fffffL);
			long block8 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block7 & 63L) << 17 | block8 >>> 47);
			values[valuesOffset++] = (int)(block8 >>> 24 & 0x7fffffL);
			values[valuesOffset++] = (int)(block8 >>> 1 & 0x7fffffL);
			long block9 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block8 & 1L) << 22 | block9 >>> 42);
			values[valuesOffset++] = (int)(block9 >>> 19 & 0x7fffffL);
			long block10 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block9 & 0x7ffffL) << 4 | block10 >>> 60);
			values[valuesOffset++] = (int)(block10 >>> 37 & 0x7fffffL);
			values[valuesOffset++] = (int)(block10 >>> 14 & 0x7fffffL);
			long block11 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block10 & 16383L) << 9 | block11 >>> 55);
			values[valuesOffset++] = (int)(block11 >>> 32 & 0x7fffffL);
			values[valuesOffset++] = (int)(block11 >>> 9 & 0x7fffffL);
			long block12 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block11 & 511L) << 14 | block12 >>> 50);
			values[valuesOffset++] = (int)(block12 >>> 27 & 0x7fffffL);
			values[valuesOffset++] = (int)(block12 >>> 4 & 0x7fffffL);
			long block13 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block12 & 15L) << 19 | block13 >>> 45);
			values[valuesOffset++] = (int)(block13 >>> 22 & 0x7fffffL);
			long block14 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block13 & 0x3fffffL) << 1 | block14 >>> 63);
			values[valuesOffset++] = (int)(block14 >>> 40 & 0x7fffffL);
			values[valuesOffset++] = (int)(block14 >>> 17 & 0x7fffffL);
			long block15 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block14 & 0x1ffffL) << 6 | block15 >>> 58);
			values[valuesOffset++] = (int)(block15 >>> 35 & 0x7fffffL);
			values[valuesOffset++] = (int)(block15 >>> 12 & 0x7fffffL);
			long block16 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block15 & 4095L) << 11 | block16 >>> 53);
			values[valuesOffset++] = (int)(block16 >>> 30 & 0x7fffffL);
			values[valuesOffset++] = (int)(block16 >>> 7 & 0x7fffffL);
			long block17 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block16 & 127L) << 16 | block17 >>> 48);
			values[valuesOffset++] = (int)(block17 >>> 25 & 0x7fffffL);
			values[valuesOffset++] = (int)(block17 >>> 2 & 0x7fffffL);
			long block18 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block17 & 3L) << 21 | block18 >>> 43);
			values[valuesOffset++] = (int)(block18 >>> 20 & 0x7fffffL);
			long block19 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block18 & 0xfffffL) << 3 | block19 >>> 61);
			values[valuesOffset++] = (int)(block19 >>> 38 & 0x7fffffL);
			values[valuesOffset++] = (int)(block19 >>> 15 & 0x7fffffL);
			long block20 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block19 & 32767L) << 8 | block20 >>> 56);
			values[valuesOffset++] = (int)(block20 >>> 33 & 0x7fffffL);
			values[valuesOffset++] = (int)(block20 >>> 10 & 0x7fffffL);
			long block21 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block20 & 1023L) << 13 | block21 >>> 51);
			values[valuesOffset++] = (int)(block21 >>> 28 & 0x7fffffL);
			values[valuesOffset++] = (int)(block21 >>> 5 & 0x7fffffL);
			long block22 = blocks[blocksOffset++];
			values[valuesOffset++] = (int)((block21 & 31L) << 18 | block22 >>> 46);
			values[valuesOffset++] = (int)(block22 >>> 23 & 0x7fffffL);
			values[valuesOffset++] = (int)(block22 & 0x7fffffL);
		}

	}

	public void decode(byte blocks[], int blocksOffset, int values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < 8 * iterations; i++)
		{
			int byte0 = blocks[blocksOffset++] & 0xff;
			int byte1 = blocks[blocksOffset++] & 0xff;
			int byte2 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = byte0 << 15 | byte1 << 7 | byte2 >>> 1;
			int byte3 = blocks[blocksOffset++] & 0xff;
			int byte4 = blocks[blocksOffset++] & 0xff;
			int byte5 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte2 & 1) << 22 | byte3 << 14 | byte4 << 6 | byte5 >>> 2;
			int byte6 = blocks[blocksOffset++] & 0xff;
			int byte7 = blocks[blocksOffset++] & 0xff;
			int byte8 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte5 & 3) << 21 | byte6 << 13 | byte7 << 5 | byte8 >>> 3;
			int byte9 = blocks[blocksOffset++] & 0xff;
			int byte10 = blocks[blocksOffset++] & 0xff;
			int byte11 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte8 & 7) << 20 | byte9 << 12 | byte10 << 4 | byte11 >>> 4;
			int byte12 = blocks[blocksOffset++] & 0xff;
			int byte13 = blocks[blocksOffset++] & 0xff;
			int byte14 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte11 & 0xf) << 19 | byte12 << 11 | byte13 << 3 | byte14 >>> 5;
			int byte15 = blocks[blocksOffset++] & 0xff;
			int byte16 = blocks[blocksOffset++] & 0xff;
			int byte17 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte14 & 0x1f) << 18 | byte15 << 10 | byte16 << 2 | byte17 >>> 6;
			int byte18 = blocks[blocksOffset++] & 0xff;
			int byte19 = blocks[blocksOffset++] & 0xff;
			int byte20 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte17 & 0x3f) << 17 | byte18 << 9 | byte19 << 1 | byte20 >>> 7;
			int byte21 = blocks[blocksOffset++] & 0xff;
			int byte22 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte20 & 0x7f) << 16 | byte21 << 8 | byte22;
		}

	}

	public void decode(long blocks[], int blocksOffset, long values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < iterations; i++)
		{
			long block0 = blocks[blocksOffset++];
			values[valuesOffset++] = block0 >>> 41;
			values[valuesOffset++] = block0 >>> 18 & 0x7fffffL;
			long block1 = blocks[blocksOffset++];
			values[valuesOffset++] = (block0 & 0x3ffffL) << 5 | block1 >>> 59;
			values[valuesOffset++] = block1 >>> 36 & 0x7fffffL;
			values[valuesOffset++] = block1 >>> 13 & 0x7fffffL;
			long block2 = blocks[blocksOffset++];
			values[valuesOffset++] = (block1 & 8191L) << 10 | block2 >>> 54;
			values[valuesOffset++] = block2 >>> 31 & 0x7fffffL;
			values[valuesOffset++] = block2 >>> 8 & 0x7fffffL;
			long block3 = blocks[blocksOffset++];
			values[valuesOffset++] = (block2 & 255L) << 15 | block3 >>> 49;
			values[valuesOffset++] = block3 >>> 26 & 0x7fffffL;
			values[valuesOffset++] = block3 >>> 3 & 0x7fffffL;
			long block4 = blocks[blocksOffset++];
			values[valuesOffset++] = (block3 & 7L) << 20 | block4 >>> 44;
			values[valuesOffset++] = block4 >>> 21 & 0x7fffffL;
			long block5 = blocks[blocksOffset++];
			values[valuesOffset++] = (block4 & 0x1fffffL) << 2 | block5 >>> 62;
			values[valuesOffset++] = block5 >>> 39 & 0x7fffffL;
			values[valuesOffset++] = block5 >>> 16 & 0x7fffffL;
			long block6 = blocks[blocksOffset++];
			values[valuesOffset++] = (block5 & 65535L) << 7 | block6 >>> 57;
			values[valuesOffset++] = block6 >>> 34 & 0x7fffffL;
			values[valuesOffset++] = block6 >>> 11 & 0x7fffffL;
			long block7 = blocks[blocksOffset++];
			values[valuesOffset++] = (block6 & 2047L) << 12 | block7 >>> 52;
			values[valuesOffset++] = block7 >>> 29 & 0x7fffffL;
			values[valuesOffset++] = block7 >>> 6 & 0x7fffffL;
			long block8 = blocks[blocksOffset++];
			values[valuesOffset++] = (block7 & 63L) << 17 | block8 >>> 47;
			values[valuesOffset++] = block8 >>> 24 & 0x7fffffL;
			values[valuesOffset++] = block8 >>> 1 & 0x7fffffL;
			long block9 = blocks[blocksOffset++];
			values[valuesOffset++] = (block8 & 1L) << 22 | block9 >>> 42;
			values[valuesOffset++] = block9 >>> 19 & 0x7fffffL;
			long block10 = blocks[blocksOffset++];
			values[valuesOffset++] = (block9 & 0x7ffffL) << 4 | block10 >>> 60;
			values[valuesOffset++] = block10 >>> 37 & 0x7fffffL;
			values[valuesOffset++] = block10 >>> 14 & 0x7fffffL;
			long block11 = blocks[blocksOffset++];
			values[valuesOffset++] = (block10 & 16383L) << 9 | block11 >>> 55;
			values[valuesOffset++] = block11 >>> 32 & 0x7fffffL;
			values[valuesOffset++] = block11 >>> 9 & 0x7fffffL;
			long block12 = blocks[blocksOffset++];
			values[valuesOffset++] = (block11 & 511L) << 14 | block12 >>> 50;
			values[valuesOffset++] = block12 >>> 27 & 0x7fffffL;
			values[valuesOffset++] = block12 >>> 4 & 0x7fffffL;
			long block13 = blocks[blocksOffset++];
			values[valuesOffset++] = (block12 & 15L) << 19 | block13 >>> 45;
			values[valuesOffset++] = block13 >>> 22 & 0x7fffffL;
			long block14 = blocks[blocksOffset++];
			values[valuesOffset++] = (block13 & 0x3fffffL) << 1 | block14 >>> 63;
			values[valuesOffset++] = block14 >>> 40 & 0x7fffffL;
			values[valuesOffset++] = block14 >>> 17 & 0x7fffffL;
			long block15 = blocks[blocksOffset++];
			values[valuesOffset++] = (block14 & 0x1ffffL) << 6 | block15 >>> 58;
			values[valuesOffset++] = block15 >>> 35 & 0x7fffffL;
			values[valuesOffset++] = block15 >>> 12 & 0x7fffffL;
			long block16 = blocks[blocksOffset++];
			values[valuesOffset++] = (block15 & 4095L) << 11 | block16 >>> 53;
			values[valuesOffset++] = block16 >>> 30 & 0x7fffffL;
			values[valuesOffset++] = block16 >>> 7 & 0x7fffffL;
			long block17 = blocks[blocksOffset++];
			values[valuesOffset++] = (block16 & 127L) << 16 | block17 >>> 48;
			values[valuesOffset++] = block17 >>> 25 & 0x7fffffL;
			values[valuesOffset++] = block17 >>> 2 & 0x7fffffL;
			long block18 = blocks[blocksOffset++];
			values[valuesOffset++] = (block17 & 3L) << 21 | block18 >>> 43;
			values[valuesOffset++] = block18 >>> 20 & 0x7fffffL;
			long block19 = blocks[blocksOffset++];
			values[valuesOffset++] = (block18 & 0xfffffL) << 3 | block19 >>> 61;
			values[valuesOffset++] = block19 >>> 38 & 0x7fffffL;
			values[valuesOffset++] = block19 >>> 15 & 0x7fffffL;
			long block20 = blocks[blocksOffset++];
			values[valuesOffset++] = (block19 & 32767L) << 8 | block20 >>> 56;
			values[valuesOffset++] = block20 >>> 33 & 0x7fffffL;
			values[valuesOffset++] = block20 >>> 10 & 0x7fffffL;
			long block21 = blocks[blocksOffset++];
			values[valuesOffset++] = (block20 & 1023L) << 13 | block21 >>> 51;
			values[valuesOffset++] = block21 >>> 28 & 0x7fffffL;
			values[valuesOffset++] = block21 >>> 5 & 0x7fffffL;
			long block22 = blocks[blocksOffset++];
			values[valuesOffset++] = (block21 & 31L) << 18 | block22 >>> 46;
			values[valuesOffset++] = block22 >>> 23 & 0x7fffffL;
			values[valuesOffset++] = block22 & 0x7fffffL;
		}

	}

	public void decode(byte blocks[], int blocksOffset, long values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < 8 * iterations; i++)
		{
			long byte0 = blocks[blocksOffset++] & 0xff;
			long byte1 = blocks[blocksOffset++] & 0xff;
			long byte2 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = byte0 << 15 | byte1 << 7 | byte2 >>> 1;
			long byte3 = blocks[blocksOffset++] & 0xff;
			long byte4 = blocks[blocksOffset++] & 0xff;
			long byte5 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte2 & 1L) << 22 | byte3 << 14 | byte4 << 6 | byte5 >>> 2;
			long byte6 = blocks[blocksOffset++] & 0xff;
			long byte7 = blocks[blocksOffset++] & 0xff;
			long byte8 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte5 & 3L) << 21 | byte6 << 13 | byte7 << 5 | byte8 >>> 3;
			long byte9 = blocks[blocksOffset++] & 0xff;
			long byte10 = blocks[blocksOffset++] & 0xff;
			long byte11 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte8 & 7L) << 20 | byte9 << 12 | byte10 << 4 | byte11 >>> 4;
			long byte12 = blocks[blocksOffset++] & 0xff;
			long byte13 = blocks[blocksOffset++] & 0xff;
			long byte14 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte11 & 15L) << 19 | byte12 << 11 | byte13 << 3 | byte14 >>> 5;
			long byte15 = blocks[blocksOffset++] & 0xff;
			long byte16 = blocks[blocksOffset++] & 0xff;
			long byte17 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte14 & 31L) << 18 | byte15 << 10 | byte16 << 2 | byte17 >>> 6;
			long byte18 = blocks[blocksOffset++] & 0xff;
			long byte19 = blocks[blocksOffset++] & 0xff;
			long byte20 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte17 & 63L) << 17 | byte18 << 9 | byte19 << 1 | byte20 >>> 7;
			long byte21 = blocks[blocksOffset++] & 0xff;
			long byte22 = blocks[blocksOffset++] & 0xff;
			values[valuesOffset++] = (byte20 & 127L) << 16 | byte21 << 8 | byte22;
		}

	}

}
