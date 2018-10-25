// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BufferedIndexOutput.java

package org.apache.lucene.store;

import java.io.IOException;

// Referenced classes of package org.apache.lucene.store:
//			IndexOutput

public abstract class BufferedIndexOutput extends IndexOutput
{

	static final int BUFFER_SIZE = 16384;
	private final byte buffer[] = new byte[16384];
	private long bufferStart;
	private int bufferPosition;

	public BufferedIndexOutput()
	{
		bufferStart = 0L;
		bufferPosition = 0;
	}

	public void writeByte(byte b)
		throws IOException
	{
		if (bufferPosition >= 16384)
			flush();
		buffer[bufferPosition++] = b;
	}

	public void writeBytes(byte b[], int offset, int length)
		throws IOException
	{
		int bytesLeft = 16384 - bufferPosition;
		if (bytesLeft >= length)
		{
			System.arraycopy(b, offset, buffer, bufferPosition, length);
			bufferPosition += length;
			if (16384 - bufferPosition == 0)
				flush();
		} else
		if (length > 16384)
		{
			if (bufferPosition > 0)
				flush();
			flushBuffer(b, offset, length);
			bufferStart += length;
		} else
		{
			int pos = 0;
			do
			{
				if (pos >= length)
					break;
				int pieceLength = length - pos >= bytesLeft ? bytesLeft : length - pos;
				System.arraycopy(b, pos + offset, buffer, bufferPosition, pieceLength);
				pos += pieceLength;
				bufferPosition += pieceLength;
				bytesLeft = 16384 - bufferPosition;
				if (bytesLeft == 0)
				{
					flush();
					bytesLeft = 16384;
				}
			} while (true);
		}
	}

	public void flush()
		throws IOException
	{
		flushBuffer(buffer, bufferPosition);
		bufferStart += bufferPosition;
		bufferPosition = 0;
	}

	private void flushBuffer(byte b[], int len)
		throws IOException
	{
		flushBuffer(b, 0, len);
	}

	protected abstract void flushBuffer(byte abyte0[], int i, int j)
		throws IOException;

	public void close()
		throws IOException
	{
		flush();
	}

	public long getFilePointer()
	{
		return bufferStart + (long)bufferPosition;
	}

	public void seek(long pos)
		throws IOException
	{
		flush();
		bufferStart = pos;
	}

	public abstract long length()
		throws IOException;
}
