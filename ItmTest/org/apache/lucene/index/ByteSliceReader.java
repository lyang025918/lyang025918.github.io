// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ByteSliceReader.java

package org.apache.lucene.index;

import java.io.IOException;
import org.apache.lucene.store.DataInput;
import org.apache.lucene.store.DataOutput;
import org.apache.lucene.util.ByteBlockPool;

final class ByteSliceReader extends DataInput
{

	ByteBlockPool pool;
	int bufferUpto;
	byte buffer[];
	public int upto;
	int limit;
	int level;
	public int bufferOffset;
	public int endIndex;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/ByteSliceReader.desiredAssertionStatus();

	ByteSliceReader()
	{
	}

	public void init(ByteBlockPool pool, int startIndex, int endIndex)
	{
		if (!$assertionsDisabled && endIndex - startIndex < 0)
			throw new AssertionError();
		if (!$assertionsDisabled && startIndex < 0)
			throw new AssertionError();
		if (!$assertionsDisabled && endIndex < 0)
			throw new AssertionError();
		this.pool = pool;
		this.endIndex = endIndex;
		level = 0;
		bufferUpto = startIndex / 32768;
		bufferOffset = bufferUpto * 32768;
		buffer = pool.buffers[bufferUpto];
		upto = startIndex & 0x7fff;
		int firstSize = ByteBlockPool.levelSizeArray[0];
		if (startIndex + firstSize >= endIndex)
			limit = endIndex & 0x7fff;
		else
			limit = (upto + firstSize) - 4;
	}

	public boolean eof()
	{
		if (!$assertionsDisabled && upto + bufferOffset > endIndex)
			throw new AssertionError();
		else
			return upto + bufferOffset == endIndex;
	}

	public byte readByte()
	{
		if (!$assertionsDisabled && eof())
			throw new AssertionError();
		if (!$assertionsDisabled && upto > limit)
			throw new AssertionError();
		if (upto == limit)
			nextSlice();
		return buffer[upto++];
	}

	public long writeTo(DataOutput out)
		throws IOException
	{
		long size = 0L;
		do
		{
			if (limit + bufferOffset == endIndex)
			{
				if (!$assertionsDisabled && endIndex - bufferOffset < upto)
					throw new AssertionError();
				out.writeBytes(buffer, upto, limit - upto);
				size += limit - upto;
				break;
			}
			out.writeBytes(buffer, upto, limit - upto);
			size += limit - upto;
			nextSlice();
		} while (true);
		return size;
	}

	public void nextSlice()
	{
		int nextIndex = ((buffer[limit] & 0xff) << 24) + ((buffer[1 + limit] & 0xff) << 16) + ((buffer[2 + limit] & 0xff) << 8) + (buffer[3 + limit] & 0xff);
		level = ByteBlockPool.nextLevelArray[level];
		int newSize = ByteBlockPool.levelSizeArray[level];
		bufferUpto = nextIndex / 32768;
		bufferOffset = bufferUpto * 32768;
		buffer = pool.buffers[bufferUpto];
		upto = nextIndex & 0x7fff;
		if (nextIndex + newSize >= endIndex)
		{
			if (!$assertionsDisabled && endIndex - nextIndex <= 0)
				throw new AssertionError();
			limit = endIndex - bufferOffset;
		} else
		{
			limit = (upto + newSize) - 4;
		}
	}

	public void readBytes(byte b[], int offset, int len)
	{
label0:
		{
			do
			{
				if (len <= 0)
					break label0;
				int numLeft = limit - upto;
				if (numLeft >= len)
					break;
				System.arraycopy(buffer, upto, b, offset, numLeft);
				offset += numLeft;
				len -= numLeft;
				nextSlice();
			} while (true);
			System.arraycopy(buffer, upto, b, offset, len);
			upto += len;
		}
	}

}
