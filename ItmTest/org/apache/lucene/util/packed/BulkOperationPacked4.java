// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BulkOperationPacked4.java

package org.apache.lucene.util.packed;


// Referenced classes of package org.apache.lucene.util.packed:
//			BulkOperationPacked

final class BulkOperationPacked4 extends BulkOperationPacked
{

	static final boolean $assertionsDisabled = !org/apache/lucene/util/packed/BulkOperationPacked4.desiredAssertionStatus();

	public BulkOperationPacked4()
	{
		super(4);
		if (!$assertionsDisabled && blockCount() != 1)
			throw new AssertionError();
		if (!$assertionsDisabled && valueCount() != 16)
			throw new AssertionError();
		else
			return;
	}

	public void decode(long blocks[], int blocksOffset, int values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < iterations; i++)
		{
			long block = blocks[blocksOffset++];
			for (int shift = 60; shift >= 0; shift -= 4)
				values[valuesOffset++] = (int)(block >>> shift & 15L);

		}

	}

	public void decode(byte blocks[], int blocksOffset, int values[], int valuesOffset, int iterations)
	{
		for (int j = 0; j < 8 * iterations; j++)
		{
			byte block = blocks[blocksOffset++];
			values[valuesOffset++] = block >>> 4 & 0xf;
			values[valuesOffset++] = block & 0xf;
		}

	}

	public void decode(long blocks[], int blocksOffset, long values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < iterations; i++)
		{
			long block = blocks[blocksOffset++];
			for (int shift = 60; shift >= 0; shift -= 4)
				values[valuesOffset++] = block >>> shift & 15L;

		}

	}

	public void decode(byte blocks[], int blocksOffset, long values[], int valuesOffset, int iterations)
	{
		for (int j = 0; j < 8 * iterations; j++)
		{
			byte block = blocks[blocksOffset++];
			values[valuesOffset++] = block >>> 4 & 0xf;
			values[valuesOffset++] = block & 0xf;
		}

	}

}
