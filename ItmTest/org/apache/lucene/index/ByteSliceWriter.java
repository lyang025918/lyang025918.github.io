// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ByteSliceWriter.java

package org.apache.lucene.index;

import org.apache.lucene.store.DataOutput;
import org.apache.lucene.util.ByteBlockPool;

final class ByteSliceWriter extends DataOutput
{

	private byte slice[];
	private int upto;
	private final ByteBlockPool pool;
	int offset0;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/ByteSliceWriter.desiredAssertionStatus();

	public ByteSliceWriter(ByteBlockPool pool)
	{
		this.pool = pool;
	}

	public void init(int address)
	{
		slice = pool.buffers[address >> 15];
		if (!$assertionsDisabled && slice == null)
			throw new AssertionError();
		upto = address & 0x7fff;
		offset0 = address;
		if (!$assertionsDisabled && upto >= slice.length)
			throw new AssertionError();
		else
			return;
	}

	public void writeByte(byte b)
	{
		if (!$assertionsDisabled && slice == null)
			throw new AssertionError();
		if (slice[upto] != 0)
		{
			upto = pool.allocSlice(slice, upto);
			slice = pool.buffer;
			offset0 = pool.byteOffset;
			if (!$assertionsDisabled && slice == null)
				throw new AssertionError();
		}
		slice[upto++] = b;
		if (!$assertionsDisabled && upto == slice.length)
			throw new AssertionError();
		else
			return;
	}

	public void writeBytes(byte b[], int offset, int len)
	{
		for (int offsetEnd = offset + len; offset < offsetEnd;)
		{
			if (slice[upto] != 0)
			{
				upto = pool.allocSlice(slice, upto);
				slice = pool.buffer;
				offset0 = pool.byteOffset;
			}
			slice[upto++] = b[offset++];
			if (!$assertionsDisabled && upto == slice.length)
				throw new AssertionError();
		}

	}

	public int getAddress()
	{
		return upto + (offset0 & 0xffff8000);
	}

}
