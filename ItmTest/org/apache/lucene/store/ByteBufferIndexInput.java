// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ByteBufferIndexInput.java

package org.apache.lucene.store;

import java.io.EOFException;
import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.Iterator;
import org.apache.lucene.util.WeakIdentityMap;

// Referenced classes of package org.apache.lucene.store:
//			IndexInput, AlreadyClosedException, DataInput

abstract class ByteBufferIndexInput extends IndexInput
{

	private ByteBuffer buffers[];
	private final long chunkSizeMask;
	private final int chunkSizePower;
	private int offset;
	private long length;
	private String sliceDescription;
	private int curBufIndex;
	private ByteBuffer curBuf;
	private boolean isClone;
	private final WeakIdentityMap clones = WeakIdentityMap.newConcurrentHashMap();
	static final boolean $assertionsDisabled = !org/apache/lucene/store/ByteBufferIndexInput.desiredAssertionStatus();

	ByteBufferIndexInput(String resourceDescription, ByteBuffer buffers[], long length, int chunkSizePower)
		throws IOException
	{
		super(resourceDescription);
		isClone = false;
		this.buffers = buffers;
		this.length = length;
		this.chunkSizePower = chunkSizePower;
		chunkSizeMask = (1L << chunkSizePower) - 1L;
		if (!$assertionsDisabled && (chunkSizePower < 0 || chunkSizePower > 30))
			throw new AssertionError();
		if (!$assertionsDisabled && length >>> chunkSizePower >= 0x7fffffffL)
		{
			throw new AssertionError();
		} else
		{
			seek(0L);
			return;
		}
	}

	public final byte readByte()
		throws IOException
	{
		return curBuf.get();
		BufferUnderflowException e;
		e;
		do
		{
			curBufIndex++;
			if (curBufIndex >= buffers.length)
				throw new EOFException((new StringBuilder()).append("read past EOF: ").append(this).toString());
			curBuf = buffers[curBufIndex];
			curBuf.position(0);
		} while (!curBuf.hasRemaining());
		return curBuf.get();
		NullPointerException npe;
		npe;
		throw new AlreadyClosedException((new StringBuilder()).append("Already closed: ").append(this).toString());
	}

	public final void readBytes(byte b[], int offset, int len)
		throws IOException
	{
		try
		{
			curBuf.get(b, offset, len);
		}
		catch (BufferUnderflowException e)
		{
			for (int curAvail = curBuf.remaining(); len > curAvail; curAvail = curBuf.remaining())
			{
				curBuf.get(b, offset, curAvail);
				len -= curAvail;
				offset += curAvail;
				curBufIndex++;
				if (curBufIndex >= buffers.length)
					throw new EOFException((new StringBuilder()).append("read past EOF: ").append(this).toString());
				curBuf = buffers[curBufIndex];
				curBuf.position(0);
			}

			curBuf.get(b, offset, len);
		}
		catch (NullPointerException npe)
		{
			throw new AlreadyClosedException((new StringBuilder()).append("Already closed: ").append(this).toString());
		}
	}

	public final short readShort()
		throws IOException
	{
		return curBuf.getShort();
		BufferUnderflowException e;
		e;
		return super.readShort();
		NullPointerException npe;
		npe;
		throw new AlreadyClosedException((new StringBuilder()).append("Already closed: ").append(this).toString());
	}

	public final int readInt()
		throws IOException
	{
		return curBuf.getInt();
		BufferUnderflowException e;
		e;
		return super.readInt();
		NullPointerException npe;
		npe;
		throw new AlreadyClosedException((new StringBuilder()).append("Already closed: ").append(this).toString());
	}

	public final long readLong()
		throws IOException
	{
		return curBuf.getLong();
		BufferUnderflowException e;
		e;
		return super.readLong();
		NullPointerException npe;
		npe;
		throw new AlreadyClosedException((new StringBuilder()).append("Already closed: ").append(this).toString());
	}

	public final long getFilePointer()
	{
		return (((long)curBufIndex << chunkSizePower) + (long)curBuf.position()) - (long)offset;
		NullPointerException npe;
		npe;
		throw new AlreadyClosedException((new StringBuilder()).append("Already closed: ").append(this).toString());
	}

	public final void seek(long pos)
		throws IOException
	{
		if (pos < 0L)
			throw new IllegalArgumentException((new StringBuilder()).append("Seeking to negative position: ").append(this).toString());
		pos += offset;
		int bi = (int)(pos >> chunkSizePower);
		try
		{
			ByteBuffer b = buffers[bi];
			b.position((int)(pos & chunkSizeMask));
			curBufIndex = bi;
			curBuf = b;
		}
		catch (ArrayIndexOutOfBoundsException aioobe)
		{
			throw new EOFException((new StringBuilder()).append("seek past EOF: ").append(this).toString());
		}
		catch (IllegalArgumentException iae)
		{
			throw new EOFException((new StringBuilder()).append("seek past EOF: ").append(this).toString());
		}
		catch (NullPointerException npe)
		{
			throw new AlreadyClosedException((new StringBuilder()).append("Already closed: ").append(this).toString());
		}
	}

	public final long length()
	{
		return length;
	}

	public final ByteBufferIndexInput clone()
	{
		ByteBufferIndexInput clone = buildSlice(0L, length);
		try
		{
			clone.seek(getFilePointer());
		}
		catch (IOException ioe)
		{
			throw new RuntimeException((new StringBuilder()).append("Should never happen: ").append(this).toString(), ioe);
		}
		return clone;
	}

	public final ByteBufferIndexInput slice(String sliceDescription, long offset, long length)
	{
		if (isClone)
			throw new IllegalStateException((new StringBuilder()).append("cannot slice() ").append(sliceDescription).append(" from a cloned IndexInput: ").append(this).toString());
		ByteBufferIndexInput clone = buildSlice(offset, length);
		clone.sliceDescription = sliceDescription;
		try
		{
			clone.seek(0L);
		}
		catch (IOException ioe)
		{
			throw new RuntimeException((new StringBuilder()).append("Should never happen: ").append(this).toString(), ioe);
		}
		return clone;
	}

	private ByteBufferIndexInput buildSlice(long offset, long length)
	{
		if (buffers == null)
			throw new AlreadyClosedException((new StringBuilder()).append("Already closed: ").append(this).toString());
		if (offset < 0L || length < 0L || offset + length > this.length)
			throw new IllegalArgumentException((new StringBuilder()).append("slice() ").append(sliceDescription).append(" out of bounds: offset=").append(offset).append(",length=").append(length).append(",fileLength=").append(this.length).append(": ").append(this).toString());
		offset += this.offset;
		ByteBufferIndexInput clone = (ByteBufferIndexInput)super.clone();
		clone.isClone = true;
		if (!$assertionsDisabled && clone.clones != clones)
		{
			throw new AssertionError();
		} else
		{
			clone.buffers = buildSlice(buffers, offset, length);
			clone.offset = (int)(offset & chunkSizeMask);
			clone.length = length;
			clones.put(clone, Boolean.TRUE);
			return clone;
		}
	}

	private ByteBuffer[] buildSlice(ByteBuffer buffers[], long offset, long length)
	{
		long sliceEnd = offset + length;
		int startIndex = (int)(offset >>> chunkSizePower);
		int endIndex = (int)(sliceEnd >>> chunkSizePower);
		ByteBuffer slices[] = new ByteBuffer[(endIndex - startIndex) + 1];
		for (int i = 0; i < slices.length; i++)
			slices[i] = buffers[startIndex + i].duplicate();

		slices[slices.length - 1].limit((int)(sliceEnd & chunkSizeMask));
		return slices;
	}

	private void unsetBuffers()
	{
		buffers = null;
		curBuf = null;
		curBufIndex = 0;
	}

	public final void close()
		throws IOException
	{
		if (buffers == null)
		{
			unsetBuffers();
			return;
		}
		ByteBuffer bufs[];
		bufs = buffers;
		unsetBuffers();
		if (isClone)
		{
			unsetBuffers();
			return;
		}
		ByteBufferIndexInput clone;
		for (Iterator it = clones.keyIterator(); it.hasNext(); clone.unsetBuffers())
		{
			clone = (ByteBufferIndexInput)it.next();
			if (!$assertionsDisabled && !clone.isClone)
				throw new AssertionError();
		}

		clones.clear();
		ByteBuffer arr$[] = bufs;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			ByteBuffer b = arr$[i$];
			freeBuffer(b);
		}

		unsetBuffers();
		break MISSING_BLOCK_LABEL_143;
		Exception exception;
		exception;
		unsetBuffers();
		throw exception;
	}

	protected abstract void freeBuffer(ByteBuffer bytebuffer)
		throws IOException;

	public final String toString()
	{
		if (sliceDescription != null)
			return (new StringBuilder()).append(super.toString()).append(" [slice=").append(sliceDescription).append("]").toString();
		else
			return super.toString();
	}

	public volatile IndexInput clone()
	{
		return clone();
	}

	public volatile DataInput clone()
	{
		return clone();
	}

	public volatile Object clone()
		throws CloneNotSupportedException
	{
		return clone();
	}

}
