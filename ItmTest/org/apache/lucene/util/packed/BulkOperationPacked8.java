// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BulkOperationPacked8.java

package org.apache.lucene.util.packed;


// Referenced classes of package org.apache.lucene.util.packed:
//			BulkOperationPacked

final class BulkOperationPacked8 extends BulkOperationPacked
{

	static final boolean $assertionsDisabled = !org/apache/lucene/util/packed/BulkOperationPacked8.desiredAssertionStatus();

	public BulkOperationPacked8()
	{
		super(8);
		if (!$assertionsDisabled && blockCount() != 1)
			throw new AssertionError();
		if (!$assertionsDisabled && valueCount() != 8)
			throw new AssertionError();
		else
			return;
	}

	public void decode(long blocks[], int blocksOffset, int values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < iterations; i++)
		{
			long block = blocks[blocksOffset++];
			for (int shift = 56; shift >= 0; shift -= 8)
				values[valuesOffset++] = (int)(block >>> shift & 255L);

		}

	}

	public void decode(byte blocks[], int blocksOffset, int values[], int valuesOffset, int iterations)
	{
		for (int j = 0; j < 8 * iterations; j++)
			values[valuesOffset++] = blocks[blocksOffset++] & 0xff;

	}

	public void decode(long blocks[], int blocksOffset, long values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < iterations; i++)
		{
			long block = blocks[blocksOffset++];
			for (int shift = 56; shift >= 0; shift -= 8)
				values[valuesOffset++] = block >>> shift & 255L;

		}

	}

	public void decode(byte blocks[], int blocksOffset, long values[], int valuesOffset, int iterations)
	{
		for (int j = 0; j < 8 * iterations; j++)
			values[valuesOffset++] = blocks[blocksOffset++] & 0xff;

	}

}
