// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItemBufferedOutput.java

package com.iflytek.itm.search.sil;

import java.io.IOException;
import java.io.PrintStream;
import org.apache.lucene.store.DataOutput;

public class ItemBufferedOutput extends DataOutput
{

	private byte buffer[];
	private int bufferPosition;

	public ItemBufferedOutput()
	{
		buffer = new byte[1];
		bufferPosition = 0;
	}

	public void writeByte(byte b)
		throws IOException
	{
		if (bufferPosition >= buffer.length)
			grow();
		buffer[bufferPosition++] = b;
	}

	public void writeBytes(byte b[], int offset, int length)
		throws IOException
	{
		int bytesLeft = buffer.length - bufferPosition;
		if (bytesLeft < length)
			if (length > buffer.length)
				grow(length);
			else
				grow();
		System.arraycopy(b, offset, buffer, bufferPosition, length);
		bufferPosition += length;
	}

	public void grow()
		throws IOException
	{
		grow(buffer.length);
	}

	public void grow(int len)
		throws IOException
	{
		byte newBytes[] = new byte[buffer.length + len];
		System.arraycopy(buffer, 0, newBytes, 0, buffer.length);
		buffer = newBytes;
	}

	public byte[] data()
	{
		if (bufferPosition == buffer.length)
		{
			return buffer;
		} else
		{
			byte result[] = new byte[bufferPosition];
			System.arraycopy(buffer, 0, result, 0, bufferPosition);
			return result;
		}
	}

	public int length()
	{
		return bufferPosition;
	}

	public static void main(String args[])
		throws Exception
	{
		int a = 10;
		String b = "wgggfiy";
		ItemBufferedOutput buffer = new ItemBufferedOutput();
		buffer.writeVInt(a);
		buffer.writeString(b);
		System.out.println((new StringBuilder()).append("bytes=").append(buffer.data()).toString());
	}
}
