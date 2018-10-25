// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DirectPackedReader.java

package org.apache.lucene.util.packed;

import java.io.IOException;
import org.apache.lucene.store.IndexInput;

// Referenced classes of package org.apache.lucene.util.packed:
//			PackedInts

final class DirectPackedReader extends PackedInts.ReaderImpl
{

	private final IndexInput in;
	private final long startPointer;
	private static final int BLOCK_BITS = 6;
	private static final int MOD_MASK = 63;
	private final long masks[];

	public DirectPackedReader(int bitsPerValue, int valueCount, IndexInput in)
	{
		super(valueCount, bitsPerValue);
		this.in = in;
		long v = 1L;
		masks = new long[bitsPerValue];
		for (int i = 0; i < bitsPerValue; i++)
		{
			v *= 2L;
			masks[i] = v - 1L;
		}

		startPointer = in.getFilePointer();
	}

	public long get(int index)
	{
		int elementPos;
		int bitPos;
		long majorBitPos = (long)index * (long)bitsPerValue;
		elementPos = (int)(majorBitPos >>> 6);
		bitPos = (int)(majorBitPos & 63L);
		long result;
		in.seek(startPointer + (long)(elementPos << 3));
		long l1 = in.readLong();
		int bits1 = 64 - bitPos;
		if (bits1 >= bitsPerValue)
		{
			result = l1 >> bits1 - bitsPerValue & masks[bitsPerValue - 1];
		} else
		{
			int bits2 = bitsPerValue - bits1;
			long result1 = (l1 & masks[bits1 - 1]) << bits2;
			long l2 = in.readLong();
			long result2 = l2 >> 64 - bits2 & masks[bits2 - 1];
			result = result1 | result2;
		}
		return result;
		IOException ioe;
		ioe;
		throw new IllegalStateException("failed", ioe);
	}

	public long ramBytesUsed()
	{
		return 0L;
	}
}
