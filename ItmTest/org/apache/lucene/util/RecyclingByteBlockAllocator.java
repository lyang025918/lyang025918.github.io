// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RecyclingByteBlockAllocator.java

package org.apache.lucene.util;

import java.util.concurrent.atomic.AtomicLong;

// Referenced classes of package org.apache.lucene.util:
//			RamUsageEstimator, ArrayUtil, ByteBlockPool

public final class RecyclingByteBlockAllocator extends ByteBlockPool.Allocator
{

	private byte freeByteBlocks[][];
	private final int maxBufferedBlocks;
	private int freeBlocks;
	private final AtomicLong bytesUsed;
	public static final int DEFAULT_BUFFERED_BLOCKS = 64;
	static final boolean $assertionsDisabled = !org/apache/lucene/util/RecyclingByteBlockAllocator.desiredAssertionStatus();

	public RecyclingByteBlockAllocator(int blockSize, int maxBufferedBlocks, AtomicLong bytesUsed)
	{
		super(blockSize);
		freeBlocks = 0;
		freeByteBlocks = new byte[Math.min(10, maxBufferedBlocks)][];
		this.maxBufferedBlocks = maxBufferedBlocks;
		this.bytesUsed = bytesUsed;
	}

	public RecyclingByteBlockAllocator(int blockSize, int maxBufferedBlocks)
	{
		this(blockSize, maxBufferedBlocks, new AtomicLong());
	}

	public RecyclingByteBlockAllocator()
	{
		this(32768, 64, new AtomicLong());
	}

	public synchronized byte[] getByteBlock()
	{
		if (freeBlocks == 0)
		{
			bytesUsed.addAndGet(blockSize);
			return new byte[blockSize];
		} else
		{
			byte b[] = freeByteBlocks[--freeBlocks];
			freeByteBlocks[freeBlocks] = null;
			return b;
		}
	}

	public synchronized void recycleByteBlocks(byte blocks[][], int start, int end)
	{
		int numBlocks = Math.min(maxBufferedBlocks - freeBlocks, end - start);
		int size = freeBlocks + numBlocks;
		if (size >= freeByteBlocks.length)
		{
			byte newBlocks[][] = new byte[ArrayUtil.oversize(size, RamUsageEstimator.NUM_BYTES_OBJECT_REF)][];
			System.arraycopy(freeByteBlocks, 0, newBlocks, 0, freeBlocks);
			freeByteBlocks = newBlocks;
		}
		int stop = start + numBlocks;
		for (int i = start; i < stop; i++)
		{
			freeByteBlocks[freeBlocks++] = blocks[i];
			blocks[i] = null;
		}

		for (int i = stop; i < end; i++)
			blocks[i] = null;

		bytesUsed.addAndGet(-(end - stop) * blockSize);
		if (!$assertionsDisabled && bytesUsed.get() < 0L)
			throw new AssertionError();
		else
			return;
	}

	public synchronized int numBufferedBlocks()
	{
		return freeBlocks;
	}

	public synchronized long bytesUsed()
	{
		return bytesUsed.get();
	}

	public int maxBufferedBlocks()
	{
		return maxBufferedBlocks;
	}

	public synchronized int freeBlocks(int num)
	{
		if (!$assertionsDisabled && num < 0)
			throw new AssertionError();
		int stop;
		int count;
		if (num > freeBlocks)
		{
			stop = 0;
			count = freeBlocks;
		} else
		{
			stop = freeBlocks - num;
			count = num;
		}
		while (freeBlocks > stop) 
			freeByteBlocks[--freeBlocks] = null;
		bytesUsed.addAndGet(-count * blockSize);
		if (!$assertionsDisabled && bytesUsed.get() < 0L)
			throw new AssertionError();
		else
			return count;
	}

}
