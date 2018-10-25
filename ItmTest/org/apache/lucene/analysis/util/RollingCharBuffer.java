// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RollingCharBuffer.java

package org.apache.lucene.analysis.util;

import java.io.IOException;
import java.io.Reader;
import org.apache.lucene.util.ArrayUtil;

public final class RollingCharBuffer
{

	private Reader reader;
	private char buffer[];
	private int nextWrite;
	private int nextPos;
	private int count;
	private boolean end;
	static final boolean $assertionsDisabled = !org/apache/lucene/analysis/util/RollingCharBuffer.desiredAssertionStatus();

	public RollingCharBuffer()
	{
		buffer = new char[512];
	}

	public void reset(Reader reader)
	{
		this.reader = reader;
		nextPos = 0;
		nextWrite = 0;
		count = 0;
		end = false;
	}

	public int get(int pos)
		throws IOException
	{
		if (pos == nextPos)
		{
			if (end)
				return -1;
			if (count == buffer.length)
			{
				char newBuffer[] = new char[ArrayUtil.oversize(1 + count, 2)];
				System.arraycopy(buffer, nextWrite, newBuffer, 0, buffer.length - nextWrite);
				System.arraycopy(buffer, 0, newBuffer, buffer.length - nextWrite, nextWrite);
				nextWrite = buffer.length;
				buffer = newBuffer;
			}
			if (nextWrite == buffer.length)
				nextWrite = 0;
			int toRead = buffer.length - Math.max(count, nextWrite);
			int readCount = reader.read(buffer, nextWrite, toRead);
			if (readCount == -1)
			{
				end = true;
				return -1;
			} else
			{
				int ch = buffer[nextWrite];
				nextWrite += readCount;
				count += readCount;
				nextPos += readCount;
				return ch;
			}
		}
		if (!$assertionsDisabled && pos >= nextPos)
			throw new AssertionError();
		if (!$assertionsDisabled && nextPos - pos > count)
			throw new AssertionError((new StringBuilder()).append("nextPos=").append(nextPos).append(" pos=").append(pos).append(" count=").append(count).toString());
		else
			return buffer[getIndex(pos)];
	}

	private boolean inBounds(int pos)
	{
		return pos >= 0 && pos < nextPos && pos >= nextPos - count;
	}

	private int getIndex(int pos)
	{
		int index = nextWrite - (nextPos - pos);
		if (index < 0)
		{
			index += buffer.length;
			if (!$assertionsDisabled && index < 0)
				throw new AssertionError();
		}
		return index;
	}

	public char[] get(int posStart, int length)
	{
		if (!$assertionsDisabled && length <= 0)
			throw new AssertionError();
		if (!$assertionsDisabled && !inBounds(posStart))
			throw new AssertionError((new StringBuilder()).append("posStart=").append(posStart).append(" length=").append(length).toString());
		int startIndex = getIndex(posStart);
		int endIndex = getIndex(posStart + length);
		char result[] = new char[length];
		if (endIndex >= startIndex && length < buffer.length)
		{
			System.arraycopy(buffer, startIndex, result, 0, endIndex - startIndex);
		} else
		{
			int part1 = buffer.length - startIndex;
			System.arraycopy(buffer, startIndex, result, 0, part1);
			System.arraycopy(buffer, 0, result, buffer.length - startIndex, length - part1);
		}
		return result;
	}

	public void freeBefore(int pos)
	{
		if (!$assertionsDisabled && pos < 0)
			throw new AssertionError();
		if (!$assertionsDisabled && pos > nextPos)
			throw new AssertionError();
		int newCount = nextPos - pos;
		if (!$assertionsDisabled && newCount > count)
			throw new AssertionError((new StringBuilder()).append("newCount=").append(newCount).append(" count=").append(count).toString());
		if (!$assertionsDisabled && newCount > buffer.length)
		{
			throw new AssertionError((new StringBuilder()).append("newCount=").append(newCount).append(" buf.length=").append(buffer.length).toString());
		} else
		{
			count = newCount;
			return;
		}
	}

}
