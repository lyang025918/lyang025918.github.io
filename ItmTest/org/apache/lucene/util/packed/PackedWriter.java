// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PackedWriter.java

package org.apache.lucene.util.packed;

import java.io.EOFException;
import java.io.IOException;
import java.util.Arrays;
import org.apache.lucene.store.DataOutput;

// Referenced classes of package org.apache.lucene.util.packed:
//			BulkOperation, PackedInts

final class PackedWriter extends PackedInts.Writer
{

	boolean finished;
	final PackedInts.Format format;
	final BulkOperation encoder;
	final long nextBlocks[];
	final long nextValues[];
	final int iterations;
	int off;
	int written;
	static final boolean $assertionsDisabled = !org/apache/lucene/util/packed/PackedWriter.desiredAssertionStatus();

	PackedWriter(PackedInts.Format format, DataOutput out, int valueCount, int bitsPerValue, int mem)
	{
		super(out, valueCount, bitsPerValue);
		this.format = format;
		encoder = BulkOperation.of(format, bitsPerValue);
		iterations = encoder.computeIterations(valueCount, mem);
		nextBlocks = new long[iterations * encoder.blockCount()];
		nextValues = new long[iterations * encoder.valueCount()];
		off = 0;
		written = 0;
		finished = false;
	}

	protected PackedInts.Format getFormat()
	{
		return format;
	}

	public void add(long v)
		throws IOException
	{
		if (!$assertionsDisabled && (v < 0L || v > PackedInts.maxValue(bitsPerValue)))
			throw new AssertionError();
		if (!$assertionsDisabled && finished)
			throw new AssertionError();
		if (valueCount != -1 && written >= valueCount)
			throw new EOFException("Writing past end of stream");
		nextValues[off++] = v;
		if (off == nextValues.length)
			flush();
		written++;
	}

	public void finish()
		throws IOException
	{
		if (!$assertionsDisabled && finished)
			throw new AssertionError();
		if (valueCount != -1)
			while (written < valueCount) 
				add(0L);
		flush();
		finished = true;
	}

	private void flush()
		throws IOException
	{
		encoder.encode(nextValues, 0, nextBlocks, 0, iterations);
		int blocks = format.nblocks(bitsPerValue, off);
		for (int i = 0; i < blocks; i++)
			out.writeLong(nextBlocks[i]);

		Arrays.fill(nextValues, 0L);
		off = 0;
	}

	public int ord()
	{
		return written - 1;
	}

}
