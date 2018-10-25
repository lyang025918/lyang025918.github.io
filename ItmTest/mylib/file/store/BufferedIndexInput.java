// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BufferedIndexInput.java

package mylib.file.store;

import java.io.EOFException;
import java.io.IOException;

// Referenced classes of package mylib.file.store:
//			IndexInput, IndexOutput

public abstract class BufferedIndexInput extends IndexInput
{

	public static final int BUFFER_SIZE = 1024;
	private int bufferSize;
	protected byte buffer[];
	private long bufferStart;
	private int bufferLength;
	private int bufferPosition;
	static final boolean $assertionsDisabled = !mylib/file/store/BufferedIndexInput.desiredAssertionStatus();

	public final byte readByte()
		throws IOException
	{
		if (bufferPosition >= bufferLength)
			refill();
		return buffer[bufferPosition++];
	}

	/**
	 * @deprecated Method BufferedIndexInput is deprecated
	 */

	public BufferedIndexInput()
	{
		this("anonymous BuffereIndexInput");
	}

	public BufferedIndexInput(String resourceDesc)
	{
		this(resourceDesc, 1024);
	}

	/**
	 * @deprecated Method BufferedIndexInput is deprecated
	 */

	public BufferedIndexInput(int bufferSize)
	{
		this("anonymous BuffereIndexInput", bufferSize);
	}

	public BufferedIndexInput(String resourceDesc, int bufferSize)
	{
		super(resourceDesc);
		this.bufferSize = 1024;
		bufferStart = 0L;
		bufferLength = 0;
		bufferPosition = 0;
		checkBufferSize(bufferSize);
		this.bufferSize = bufferSize;
	}

	public final void setBufferSize(int newSize)
	{
		if (!$assertionsDisabled && buffer != null && bufferSize != buffer.length)
			throw new AssertionError((new StringBuilder()).append("buffer=").append(buffer).append(" bufferSize=").append(bufferSize).append(" buffer.length=").append(buffer == null ? 0 : buffer.length).toString());
		if (newSize != bufferSize)
		{
			checkBufferSize(newSize);
			bufferSize = newSize;
			if (buffer != null)
			{
				byte newBuffer[] = new byte[newSize];
				int leftInBuffer = bufferLength - bufferPosition;
				int numToCopy;
				if (leftInBuffer > newSize)
					numToCopy = newSize;
				else
					numToCopy = leftInBuffer;
				System.arraycopy(buffer, bufferPosition, newBuffer, 0, numToCopy);
				bufferStart += bufferPosition;
				bufferPosition = 0;
				bufferLength = numToCopy;
				newBuffer(newBuffer);
			}
		}
	}

	protected void newBuffer(byte newBuffer[])
	{
		buffer = newBuffer;
	}

	public final int getBufferSize()
	{
		return bufferSize;
	}

	private void checkBufferSize(int bufferSize)
	{
		if (bufferSize <= 0)
			throw new IllegalArgumentException((new StringBuilder()).append("bufferSize must be greater than 0 (got ").append(bufferSize).append(")").toString());
		else
			return;
	}

	public final void readBytes(byte b[], int offset, int len)
		throws IOException
	{
		readBytes(b, offset, len, true);
	}

	public final void readBytes(byte b[], int offset, int len, boolean useBuffer)
		throws IOException
	{
		if (len <= bufferLength - bufferPosition)
		{
			if (len > 0)
				System.arraycopy(buffer, bufferPosition, b, offset, len);
			bufferPosition += len;
		} else
		{
			int available = bufferLength - bufferPosition;
			if (available > 0)
			{
				System.arraycopy(buffer, bufferPosition, b, offset, available);
				offset += available;
				len -= available;
				bufferPosition += available;
			}
			if (useBuffer && len < bufferSize)
			{
				refill();
				if (bufferLength < len)
				{
					System.arraycopy(buffer, 0, b, offset, bufferLength);
					throw new EOFException((new StringBuilder()).append("read past EOF: ").append(this).toString());
				}
				System.arraycopy(buffer, 0, b, offset, len);
				bufferPosition = len;
			} else
			{
				long after = bufferStart + (long)bufferPosition + (long)len;
				if (after > length())
					throw new EOFException((new StringBuilder()).append("read past EOF: ").append(this).toString());
				readInternal(b, offset, len);
				bufferStart = after;
				bufferPosition = 0;
				bufferLength = 0;
			}
		}
	}

	public final short readShort()
		throws IOException
	{
		if (2 <= bufferLength - bufferPosition)
			return (short)((buffer[bufferPosition++] & 0xff) << 8 | buffer[bufferPosition++] & 0xff);
		else
			return super.readShort();
	}

	public final int readInt()
		throws IOException
	{
		if (4 <= bufferLength - bufferPosition)
			return (buffer[bufferPosition++] & 0xff) << 24 | (buffer[bufferPosition++] & 0xff) << 16 | (buffer[bufferPosition++] & 0xff) << 8 | buffer[bufferPosition++] & 0xff;
		else
			return super.readInt();
	}

	public final long readLong()
		throws IOException
	{
		if (8 <= bufferLength - bufferPosition)
		{
			int i1 = (buffer[bufferPosition++] & 0xff) << 24 | (buffer[bufferPosition++] & 0xff) << 16 | (buffer[bufferPosition++] & 0xff) << 8 | buffer[bufferPosition++] & 0xff;
			int i2 = (buffer[bufferPosition++] & 0xff) << 24 | (buffer[bufferPosition++] & 0xff) << 16 | (buffer[bufferPosition++] & 0xff) << 8 | buffer[bufferPosition++] & 0xff;
			return (long)i1 << 32 | (long)i2 & 0xffffffffL;
		} else
		{
			return super.readLong();
		}
	}

	public final int readVInt()
		throws IOException
	{
		if (5 <= bufferLength - bufferPosition)
		{
			byte b = buffer[bufferPosition++];
			int i = b & 0x7f;
			if ((b & 0x80) == 0)
				return i;
			b = buffer[bufferPosition++];
			i |= (b & 0x7f) << 7;
			if ((b & 0x80) == 0)
				return i;
			b = buffer[bufferPosition++];
			i |= (b & 0x7f) << 14;
			if ((b & 0x80) == 0)
				return i;
			b = buffer[bufferPosition++];
			i |= (b & 0x7f) << 21;
			if ((b & 0x80) == 0)
				return i;
			b = buffer[bufferPosition++];
			i |= (b & 0xf) << 28;
			if ((b & 0xf0) == 0)
				return i;
			else
				throw new IOException("Invalid vInt detected (too many bits)");
		} else
		{
			return super.readVInt();
		}
	}

	public final long readVLong()
		throws IOException
	{
		if (9 <= bufferLength - bufferPosition)
		{
			byte b = buffer[bufferPosition++];
			long i = (long)b & 127L;
			if ((b & 0x80) == 0)
				return i;
			b = buffer[bufferPosition++];
			i |= ((long)b & 127L) << 7;
			if ((b & 0x80) == 0)
				return i;
			b = buffer[bufferPosition++];
			i |= ((long)b & 127L) << 14;
			if ((b & 0x80) == 0)
				return i;
			b = buffer[bufferPosition++];
			i |= ((long)b & 127L) << 21;
			if ((b & 0x80) == 0)
				return i;
			b = buffer[bufferPosition++];
			i |= ((long)b & 127L) << 28;
			if ((b & 0x80) == 0)
				return i;
			b = buffer[bufferPosition++];
			i |= ((long)b & 127L) << 35;
			if ((b & 0x80) == 0)
				return i;
			b = buffer[bufferPosition++];
			i |= ((long)b & 127L) << 42;
			if ((b & 0x80) == 0)
				return i;
			b = buffer[bufferPosition++];
			i |= ((long)b & 127L) << 49;
			if ((b & 0x80) == 0)
				return i;
			b = buffer[bufferPosition++];
			i |= ((long)b & 127L) << 56;
			if ((b & 0x80) == 0)
				return i;
			else
				throw new IOException("Invalid vLong detected (negative values disallowed)");
		} else
		{
			return super.readVLong();
		}
	}

	private void refill()
		throws IOException
	{
		long start = bufferStart + (long)bufferPosition;
		long end = start + (long)bufferSize;
		if (end > length())
			end = length();
		int newLength = (int)(end - start);
		if (newLength <= 0)
			throw new EOFException((new StringBuilder()).append("read past EOF: ").append(this).toString());
		if (buffer == null)
		{
			newBuffer(new byte[bufferSize]);
			seekInternal(bufferStart);
		}
		readInternal(buffer, 0, newLength);
		bufferLength = newLength;
		bufferStart = start;
		bufferPosition = 0;
	}

	protected abstract void readInternal(byte abyte0[], int i, int j)
		throws IOException;

	public final long getFilePointer()
	{
		return bufferStart + (long)bufferPosition;
	}

	public final void seek(long pos)
		throws IOException
	{
		if (pos >= bufferStart && pos < bufferStart + (long)bufferLength)
		{
			bufferPosition = (int)(pos - bufferStart);
		} else
		{
			bufferStart = pos;
			bufferPosition = 0;
			bufferLength = 0;
			seekInternal(pos);
		}
	}

	protected abstract void seekInternal(long l)
		throws IOException;

	public Object clone()
	{
		BufferedIndexInput clone = (BufferedIndexInput)super.clone();
		clone.buffer = null;
		clone.bufferLength = 0;
		clone.bufferPosition = 0;
		clone.bufferStart = getFilePointer();
		return clone;
	}

	protected final int flushBuffer(IndexOutput out, long numBytes)
		throws IOException
	{
		int toCopy = bufferLength - bufferPosition;
		if ((long)toCopy > numBytes)
			toCopy = (int)numBytes;
		if (toCopy > 0)
		{
			out.writeBytes(buffer, bufferPosition, toCopy);
			bufferPosition += toCopy;
		}
		return toCopy;
	}

	public void copyBytes(IndexOutput out, long numBytes)
		throws IOException
	{
		if (!$assertionsDisabled && numBytes < 0L)
			throw new AssertionError((new StringBuilder()).append("numBytes=").append(numBytes).toString());
		for (; numBytes > 0L; numBytes -= flushBuffer(out, numBytes))
			if (bufferLength == bufferPosition)
				refill();

	}

}
