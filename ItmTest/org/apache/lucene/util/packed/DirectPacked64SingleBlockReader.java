// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DirectPacked64SingleBlockReader.java

package org.apache.lucene.util.packed;

import java.io.IOException;
import org.apache.lucene.store.IndexInput;

// Referenced classes of package org.apache.lucene.util.packed:
//			PackedInts

final class DirectPacked64SingleBlockReader extends PackedInts.ReaderImpl
{

	private final IndexInput in;
	private final long startPointer;
	private final int valuesPerBlock;
	private final long mask;

	DirectPacked64SingleBlockReader(int bitsPerValue, int valueCount, IndexInput in)
	{
		super(valueCount, bitsPerValue);
		this.in = in;
		startPointer = in.getFilePointer();
		valuesPerBlock = 64 / bitsPerValue;
		mask = ~(-1L << bitsPerValue);
	}

	public long get(int index)
	{
		long skip;
		int blockOffset = index / valuesPerBlock;
		skip = (long)blockOffset << 3;
		long block;
		int offsetInBlock;
		in.seek(startPointer + skip);
		block = in.readLong();
		offsetInBlock = index % valuesPerBlock;
		return block >>> offsetInBlock * bitsPerValue & mask;
		IOException e;
		e;
		throw new IllegalStateException("failed", e);
	}

	public long ramBytesUsed()
	{
		return 0L;
	}
}
