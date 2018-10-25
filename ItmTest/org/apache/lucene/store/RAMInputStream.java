// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RAMInputStream.java

package org.apache.lucene.store;

import java.io.EOFException;
import java.io.IOException;

// Referenced classes of package org.apache.lucene.store:
//			IndexInput, RAMFile

public class RAMInputStream extends IndexInput
	implements Cloneable
{

	static final int BUFFER_SIZE = 1024;
	private RAMFile file;
	private long length;
	private byte currentBuffer[];
	private int currentBufferIndex;
	private int bufferPosition;
	private long bufferStart;
	private int bufferLength;

	public RAMInputStream(String name, RAMFile f)
		throws IOException
	{
		super((new StringBuilder()).append("RAMInputStream(name=").append(name).append(")").toString());
		file = f;
		length = file.length;
		if (length / 1024L >= 0x7fffffffL)
		{
			throw new IOException((new StringBuilder()).append("RAMInputStream too large length=").append(length).append(": ").append(name).toString());
		} else
		{
			currentBufferIndex = -1;
			currentBuffer = null;
			return;
		}
	}

	public void close()
	{
	}

	public long length()
	{
		return length;
	}

	public byte readByte()
		throws IOException
	{
		if (bufferPosition >= bufferLength)
		{
			currentBufferIndex++;
			switchCurrentBuffer(true);
		}
		return currentBuffer[bufferPosition++];
	}

	public void readBytes(byte b[], int offset, int len)
		throws IOException
	{
		while (len > 0) 
		{
			if (bufferPosition >= bufferLength)
			{
				currentBufferIndex++;
				switchCurrentBuffer(true);
			}
			int remainInBuffer = bufferLength - bufferPosition;
			int bytesToCopy = len >= remainInBuffer ? remainInBuffer : len;
			System.arraycopy(currentBuffer, bufferPosition, b, offset, bytesToCopy);
			offset += bytesToCopy;
			len -= bytesToCopy;
			bufferPosition += bytesToCopy;
		}
	}

	private final void switchCurrentBuffer(boolean enforceEOF)
		throws IOException
	{
		bufferStart = 1024L * (long)currentBufferIndex;
		if (currentBufferIndex >= file.numBuffers())
		{
			if (enforceEOF)
				throw new EOFException((new StringBuilder()).append("read past EOF: ").append(this).toString());
			currentBufferIndex--;
			bufferPosition = 1024;
		} else
		{
			currentBuffer = file.getBuffer(currentBufferIndex);
			bufferPosition = 0;
			long buflen = length - bufferStart;
			bufferLength = buflen <= 1024L ? (int)buflen : 1024;
		}
	}

	public long getFilePointer()
	{
		return currentBufferIndex >= 0 ? bufferStart + (long)bufferPosition : 0L;
	}

	public void seek(long pos)
		throws IOException
	{
		if (currentBuffer == null || pos < bufferStart || pos >= bufferStart + 1024L)
		{
			currentBufferIndex = (int)(pos / 1024L);
			switchCurrentBuffer(false);
		}
		bufferPosition = (int)(pos % 1024L);
	}
}
