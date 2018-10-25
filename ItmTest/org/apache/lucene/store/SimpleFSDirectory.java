// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SimpleFSDirectory.java

package org.apache.lucene.store;

import java.io.*;

// Referenced classes of package org.apache.lucene.store:
//			FSDirectory, Directory, LockFactory, IOContext, 
//			IndexInput, BufferedIndexInput

public class SimpleFSDirectory extends FSDirectory
{
	protected static class SimpleFSIndexInput extends FSDirectory.FSIndexInput
	{

		protected void readInternal(byte b[], int offset, int len)
			throws IOException
		{
			synchronized (file)
			{
				long position = off + getFilePointer();
				file.seek(position);
				int total = 0;
				if (position + (long)len > end)
					throw new EOFException((new StringBuilder()).append("read past EOF: ").append(this).toString());
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
					throw new IOException((new StringBuilder()).append(ioe.getMessage()).append(": ").append(this).toString(), ioe);
				}
			}
		}

		protected void seekInternal(long l)
		{
		}

		public SimpleFSIndexInput(String resourceDesc, File path, IOContext context, int chunkSize)
			throws IOException
		{
			super(resourceDesc, path, context, chunkSize);
		}

		public SimpleFSIndexInput(String resourceDesc, RandomAccessFile file, long off, long length, int bufferSize, 
				int chunkSize)
		{
			super(resourceDesc, file, off, length, bufferSize, chunkSize);
		}
	}


	public SimpleFSDirectory(File path, LockFactory lockFactory)
		throws IOException
	{
		super(path, lockFactory);
	}

	public SimpleFSDirectory(File path)
		throws IOException
	{
		super(path, null);
	}

	public IndexInput openInput(String name, IOContext context)
		throws IOException
	{
		ensureOpen();
		File path = new File(directory, name);
		return new SimpleFSIndexInput((new StringBuilder()).append("SimpleFSIndexInput(path=\"").append(path.getPath()).append("\")").toString(), path, context, getReadChunkSize());
	}

	public Directory.IndexInputSlicer createSlicer(String name, final IOContext context)
		throws IOException
	{
		ensureOpen();
		final File file = new File(getDirectory(), name);
		final RandomAccessFile descriptor = new RandomAccessFile(file, "r");
		return new Directory.IndexInputSlicer() {

			final RandomAccessFile val$descriptor;
			final File val$file;
			final IOContext val$context;
			final SimpleFSDirectory this$0;

			public void close()
				throws IOException
			{
				descriptor.close();
			}

			public IndexInput openSlice(String sliceDescription, long offset, long length)
			{
				return new SimpleFSIndexInput((new StringBuilder()).append("SimpleFSIndexInput(").append(sliceDescription).append(" in path=\"").append(file.getPath()).append("\" slice=").append(offset).append(":").append(offset + length).append(")").toString(), descriptor, offset, length, BufferedIndexInput.bufferSize(context), getReadChunkSize());
			}

			public IndexInput openFullSlice()
			{
				return openSlice("full-slice", 0L, descriptor.length());
				IOException ex;
				ex;
				throw new RuntimeException(ex);
			}

			
			{
				this$0 = SimpleFSDirectory.this;
				descriptor = randomaccessfile;
				file = file1;
				context = iocontext;
				super(SimpleFSDirectory.this);
			}
		};
	}
}
