// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BulkOperationPacked2.java

package org.apache.lucene.util.packed;


// Referenced classes of package org.apache.lucene.util.packed:
//			BulkOperationPacked

final class BulkOperationPacked2 extends BulkOperationPacked
{

	static final boolean $assertionsDisabled = !org/apache/lucene/util/packed/BulkOperationPacked2.desiredAssertionStatus();

	public BulkOperationPacked2()
	{
		super(2);
		if (!$assertionsDisabled && blockCount() != 1)
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
			long block = blocks[blocksOffset++];
			for (int shift = 62; shift >= 0; shift -= 2)
				values[valuesOffset++] = (int)(block >>> shift & 3L);

		}

	}

	public void decode(byte blocks[], int blocksOffset, int values[], int valuesOffset, int iterations)
	{
		for (int j = 0; j < 8 * iterations; j++)
		{
			byte block = blocks[blocksOffset++];
			values[valuesOffset++] = block >>> 6 & 3;
			values[valuesOffset++] = block >>> 4 & 3;
			values[valuesOffset++] = block >>> 2 & 3;
			values[valuesOffset++] = block & 3;
		}

	}

	public void decode(long blocks[], int blocksOffset, long values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < iterations; i++)
		{
			long block = blocks[blocksOffset++];
			for (int shift = 62; shift >= 0; shift -= 2)
				values[valuesOffset++] = block >>> shift & 3L;

		}

	}

	public void decode(byte blocks[], int blocksOffset, long values[], int valuesOffset, int iterations)
	{
		for (int j = 0; j < 8 * iterations; j++)
		{
			byte block = blocks[blocksOffset++];
			values[valuesOffset++] = block >>> 6 & 3;
			values[valuesOffset++] = block >>> 4 & 3;
			values[valuesOffset++] = block >>> 2 & 3;
			values[valuesOffset++] = block & 3;
		}

	}

}
