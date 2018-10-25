// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RAMFile.java

package org.apache.lucene.store;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

// Referenced classes of package org.apache.lucene.store:
//			RAMDirectory

public class RAMFile
{

	protected ArrayList buffers;
	long length;
	RAMDirectory directory;
	protected long sizeInBytes;

	public RAMFile()
	{
		buffers = new ArrayList();
	}

	RAMFile(RAMDirectory directory)
	{
		buffers = new ArrayList();
		this.directory = directory;
	}

	public synchronized long getLength()
	{
		return length;
	}

	protected synchronized void setLength(long length)
	{
		this.length = length;
	}

	protected final byte[] addBuffer(int size)
	{
		byte buffer[] = newBuffer(size);
		synchronized (this)
		{
			buffers.add(buffer);
			sizeInBytes += size;
		}
		if (directory != null)
			directory.sizeInBytes.getAndAdd(size);
		return buffer;
	}

	protected final synchronized byte[] getBuffer(int index)
	{
		return (byte[])buffers.get(index);
	}

	protected final synchronized int numBuffers()
	{
		return buffers.size();
	}

	protected byte[] newBuffer(int size)
	{
		return new byte[size];
	}

	public synchronized long getSizeInBytes()
	{
		return sizeInBytes;
	}
}
