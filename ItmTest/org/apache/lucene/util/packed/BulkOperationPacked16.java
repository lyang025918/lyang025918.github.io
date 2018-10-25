// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BulkOperationPacked16.java

package org.apache.lucene.util.packed;


// Referenced classes of package org.apache.lucene.util.packed:
//			BulkOperationPacked

final class BulkOperationPacked16 extends BulkOperationPacked
{

	static final boolean $assertionsDisabled = !org/apache/lucene/util/packed/BulkOperationPacked16.desiredAssertionStatus();

	public BulkOperationPacked16()
	{
		super(16);
		if (!$assertionsDisabled && blockCount() != 1)
			throw new AssertionError();
		if (!$assertionsDisabled && valueCount() != 4)
			throw new AssertionError();
		else
			return;
	}

	public void decode(long blocks[], int blocksOffset, int values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < iterations; i++)
		{
			long block = blocks[blocksOffset++];
			for (int shift = 48; shift >= 0; shift -= 16)
				values[valuesOffset++] = (int)(block >>> shift & 65535L);

		}

	}

	public void decode(byte blocks[], int blocksOffset, int values[], int valuesOffset, int iterations)
	{
		for (int j = 0; j < 4 * iterations; j++)
			values[valuesOffset++] = (blocks[blocksOffset++] & 0xff) << 8 | blocks[blocksOffset++] & 0xff;

	}

	public void decode(long blocks[], int blocksOffset, long values[], int valuesOffset, int iterations)
	{
		for (int i = 0; i < iterations; i++)
		{
			long block = blocks[blocksOffset++];
			for (int shift = 48; shift >= 0; shift -= 16)
				values[valuesOffset++] = block >>> shift & 65535L;

		}

	}

	public void decode(byte blocks[], int blocksOffset, long values[], int valuesOffset, int iterations)
	{
		for (int j = 0; j < 4 * iterations; j++)
			values[valuesOffset++] = ((long)blocks[blocksOffset++] & 255L) << 8 | (long)blocks[blocksOffset++] & 255L;

	}

}
