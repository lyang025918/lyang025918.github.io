// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FSIndexInput.java

package mylib.file;

import java.io.*;
import mylib.file.store.BufferedIndexInput;
import mylib.file.store.IndexOutput;

public class FSIndexInput extends BufferedIndexInput
{
	protected static class Descriptor extends RandomAccessFile
	{

		protected volatile boolean isOpen;
		long position;
		final long length = length();

		public void close()
			throws IOException
		{
			if (isOpen)
			{
				isOpen = false;
				super.close();
			}
		}

		public Descriptor(File file, String mode)
			throws IOException
		{
			super(file, mode);
			isOpen = true;
		}
	}


	protected final Descriptor file;
	boolean isClone;
	protected final int chunkSize;

	public FSIndexInput(String path)
		throws IOException
	{
		this(path, 1024);
	}

	public FSIndexInput(String path, int bufferSize)
		throws IOException
	{
		this(path, bufferSize, 0x6400000);
	}

	public FSIndexInput(String path, int bufferSize, int chunkSize)
		throws IOException
	{
		this("anonymous FSIndexInput", new File(path), bufferSize, chunkSize);
	}

	public FSIndexInput(String resourceDesc, File path, int bufferSize, int chunkSize)
		throws IOException
	{
		super(resourceDesc, bufferSize);
		file = new Descriptor(path, "r");
		this.chunkSize = chunkSize;
	}

	protected void readInternal(byte b[], int offset, int len)
		throws IOException
	{
		synchronized (file)
		{
			long position = getFilePointer();
			if (position != file.position)
			{
				file.seek(position);
				file.position = position;
			}
			int total = 0;
			try
			{
				do
				{
					int readLength;
					if (total + chunkSize > len)
						readLength = len - total;
					else
						readLength = chunkSize;
					int i = file.read(b, offset + total, readLength);
					if (i == -1)
						throw new EOFException((new StringBuilder()).append("read past EOF: ").append(this).toString());
					file.position += i;
					total += i;
				} while (total < len);
			}
			catch (OutOfMemoryError e)
			{
				OutOfMemoryError outOfMemoryError = new OutOfMemoryError((new StringBuilder()).append("OutOfMemoryError likely caused by the Sun VM Bug described in https://issues.apache.org/jira/browse/LUCENE-1566; try calling FSDirectory.setReadChunkSize with a value smaller than the current chunk size (").append(chunkSize).append(")").toString());
				outOfMemoryError.initCause(e);
				throw outOfMemoryError;
			}
			catch (IOException ioe)
			{
				IOException newIOE = new IOException((new StringBuilder()).append(ioe.getMessage()).append(": ").append(this).toString());
				newIOE.initCause(ioe);
				throw newIOE;
			}
		}
	}

	public void close()
		throws IOException
	{
		if (!isClone)
			file.close();
	}

	protected void seekInternal(long l)
	{
	}

	public long length()
	{
		return file.length;
	}

	public Object clone()
	{
		FSIndexInput clone = (FSIndexInput)super.clone();
		clone.isClone = true;
		return clone;
	}

	boolean isFDValid()
		throws IOException
	{
		return file.getFD().valid();
	}

	public void copyBytes(IndexOutput out, long numBytes)
		throws IOException
	{
		numBytes -= flushBuffer(out, numBytes);
		out.copyBytes(this, numBytes);
	}

	public static void main(String args[])
	{
		try
		{
			FSIndexInput fio = new FSIndexInput("test.dat");
			int a = fio.readInt();
			String str = fio.readString();
			fio.close();
		}
		catch (IOException e)
		{
			System.out.printf("Error: %s\n", new Object[] {
				e.getMessage()
			});
			e.printStackTrace();
		}
	}
}
