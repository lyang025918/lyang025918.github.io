// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RAMOutputStream.java

package org.apache.lucene.store;

import java.io.IOException;

// Referenced classes of package org.apache.lucene.store:
//			IndexOutput, RAMFile

public class RAMOutputStream extends IndexOutput
{

	static final int BUFFER_SIZE = 1024;
	private RAMFile file;
	private byte currentBuffer[];
	private int currentBufferIndex;
	private int bufferPosition;
	private long bufferStart;
	private int bufferLength;
	static final boolean $assertionsDisabled = !org/apache/lucene/store/RAMOutputStream.desiredAssertionStatus();

	public RAMOutputStream()
	{
		this(new RAMFile());
	}

	public RAMOutputStream(RAMFile f)
	{
		file = f;
		currentBufferIndex = -1;
		currentBuffer = null;
	}

	public void writeTo(IndexOutput out)
		throws IOException
	{
		flush();
		long end = file.length;
		long pos = 0L;
		int buffer = 0;
		long nextPos;
		for (; pos < end; pos = nextPos)
		{
			int length = 1024;
			nextPos = pos + (long)length;
			if (nextPos > end)
				length = (int)(end - pos);
			out.writeBytes(file.getBuffer(buffer++), length);
		}

	}

	public void writeTo(byte bytes[], int offset)
		throws IOException
	{
		flush();
		long end = file.length;
		long pos = 0L;
		int buffer = 0;
		int bytesUpto = offset;
		long nextPos;
		for (; pos < end; pos = nextPos)
		{
			int length = 1024;
			nextPos = pos + (long)length;
			if (nextPos > end)
				length = (int)(end - pos);
			System.arraycopy(file.getBuffer(buffer++), 0, bytes, bytesUpto, length);
			bytesUpto += length;
		}

	}

	public void reset()
	{
		currentBuffer = null;
		currentBufferIndex = -1;
		bufferPosition = 0;
		bufferStart = 0L;
		bufferLength = 0;
		file.setLength(0L);
	}

	public void close()
		throws IOException
	{
		flush();
	}

	public void seek(long pos)
		throws IOException
	{
		setFileLength();
		if (pos < bufferStart || pos >= bufferStart + (long)bufferLength)
		{
			currentBufferIndex = (int)(pos / 1024L);
			switchCurrentBuffer();
		}
		bufferPosition = (int)(pos % 1024L);
	}

	public long length()
	{
		return file.length;
	}

	public void writeByte(byte b)
		throws IOException
	{
		if (bufferPosition == bufferLength)
		{
			currentBufferIndex++;
			switchCurrentBuffer();
		}
		currentBuffer[bufferPosition++] = b;
	}

	public void writeBytes(byte b[], int offset, int len)
		throws IOException
	{
		if (!$assertionsDisabled && b == null)
			throw new AssertionError();
		while (len > 0) 
		{
			if (bufferPosition == bufferLength)
			{
				currentBufferIndex++;
				switchCurrentBuffer();
			}
			int remainInBuffer = currentBuffer.length - bufferPosition;
			int bytesToCopy = len >= remainInBuffer ? remainInBuffer : len;
			System.arraycopy(b, offset, currentBuffer, bufferPosition, bytesToCopy);
			offset += bytesToCopy;
			len -= bytesToCopy;
			bufferPosition += bytesToCopy;
		}
	}

	private final void switchCurrentBuffer()
	{
		if (currentBufferIndex == file.numBuffers())
			currentBuffer = file.addBuffer(1024);
		else
			currentBuffer = file.getBuffer(currentBufferIndex);
		bufferPosition = 0;
		bufferStart = 1024L * (long)currentBufferIndex;
		bufferLength = currentBuffer.length;
	}

	private void setFileLength()
	{
		long pointer = bufferStart + (long)bufferPosition;
		if (pointer > file.length)
			file.setLength(pointer);
	}

	public void flush()
		throws IOException
	{
		setFileLength();
	}

	public long getFilePointer()
	{
		return currentBufferIndex >= 0 ? bufferStart + (long)bufferPosition : 0L;
	}

	public long sizeInBytes()
	{
		return (long)file.numBuffers() * 1024L;
	}

}
