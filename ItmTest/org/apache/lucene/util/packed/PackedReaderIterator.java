// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PackedReaderIterator.java

package org.apache.lucene.util.packed;

import java.io.EOFException;
import java.io.IOException;
import org.apache.lucene.store.DataInput;
import org.apache.lucene.util.LongsRef;

// Referenced classes of package org.apache.lucene.util.packed:
//			BulkOperation, PackedInts

final class PackedReaderIterator extends PackedInts.ReaderIteratorImpl
{

	final PackedInts.Format format;
	final BulkOperation bulkOperation;
	final long nextBlocks[];
	final LongsRef nextValues;
	final int iterations;
	int position;
	static final boolean $assertionsDisabled = !org/apache/lucene/util/packed/PackedReaderIterator.desiredAssertionStatus();

	PackedReaderIterator(PackedInts.Format format, int valueCount, int bitsPerValue, DataInput in, int mem)
	{
		super(valueCount, bitsPerValue, in);
		this.format = format;
		bulkOperation = BulkOperation.of(format, bitsPerValue);
		iterations = bulkOperation.computeIterations(valueCount, mem);
		if (!$assertionsDisabled && iterations <= 0)
			throw new AssertionError();
		nextBlocks = new long[iterations * bulkOperation.blockCount()];
		nextValues = new LongsRef(new long[iterations * bulkOperation.valueCount()], 0, 0);
		if (!$assertionsDisabled && iterations * bulkOperation.valueCount() != nextValues.longs.length)
			throw new AssertionError();
		if (!$assertionsDisabled && iterations * bulkOperation.blockCount() != nextBlocks.length)
		{
			throw new AssertionError();
		} else
		{
			nextValues.offset = nextValues.longs.length;
			position = -1;
			return;
		}
	}

	public LongsRef next(int count)
		throws IOException
	{
		if (!$assertionsDisabled && nextValues.length < 0)
			throw new AssertionError();
		if (!$assertionsDisabled && count <= 0)
			throw new AssertionError();
		if (!$assertionsDisabled && nextValues.offset + nextValues.length > nextValues.longs.length)
			throw new AssertionError();
		nextValues.offset += nextValues.length;
		int remaining = valueCount - position - 1;
		if (remaining <= 0)
			throw new EOFException();
		count = Math.min(remaining, count);
		if (nextValues.offset == nextValues.longs.length)
		{
			int remainingBlocks = format.nblocks(bitsPerValue, remaining);
			int blocksToRead = Math.min(remainingBlocks, nextBlocks.length);
			for (int i = 0; i < blocksToRead; i++)
				nextBlocks[i] = in.readLong();

			for (int i = blocksToRead; i < nextBlocks.length; i++)
				nextBlocks[i] = 0L;

			bulkOperation.decode(nextBlocks, 0, nextValues.longs, 0, iterations);
			nextValues.offset = 0;
		}
		nextValues.length = Math.min(nextValues.longs.length - nextValues.offset, count);
		position += nextValues.length;
		return nextValues;
	}

	public int ord()
	{
		return position;
	}

}
