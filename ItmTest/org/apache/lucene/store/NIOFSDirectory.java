// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NIOFSDirectory.java

package org.apache.lucene.store;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

// Referenced classes of package org.apache.lucene.store:
//			FSDirectory, Directory, LockFactory, IOContext, 
//			IndexInput, BufferedIndexInput

public class NIOFSDirectory extends FSDirectory
{
	protected static class NIOFSIndexInput extends FSDirectory.FSIndexInput
	{

		private ByteBuffer byteBuf;
		final FileChannel channel;
		static final boolean $assertionsDisabled = !org/apache/lucene/store/NIOFSDirectory.desiredAssertionStatus();

		protected void newBuffer(byte newBuffer[])
		{
			super.newBuffer(newBuffer);
			byteBuf = ByteBuffer.wrap(newBuffer);
		}

		protected void readInternal(byte b[], int offset, int len)
			throws IOException
		{
			ByteBuffer bb;
			if (b == buffer && 0 == offset)
			{
				if (!$assertionsDisabled && byteBuf == null)
					throw new AssertionError();
				byteBuf.clear();
				byteBuf.limit(len);
				bb = byteBuf;
			} else
			{
				bb = ByteBuffer.wrap(b, offset, len);
			}
			int readOffset = bb.position();
			int readLength = bb.limit() - readOffset;
			if (!$assertionsDisabled && readLength != len)
				throw new AssertionError();
			long pos = getFilePointer() + off;
			if (pos + (long)len > end)
				throw new EOFException((new StringBuilder()).append("read past EOF: ").append(this).toString());
			try
			{
				int i;
				for (; readLength > 0; readLength -= i)
				{
					int limit;
					if (readLength > chunkSize)
						limit = readOffset + chunkSize;
					else
						limit = readOffset + readLength;
					bb.limit(limit);
					i = channel.read(bb, pos);
					pos += i;
					readOffset += i;
				}

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

		protected void seekInternal(long l)
			throws IOException
		{
		}


		public NIOFSIndexInput(File path, IOContext context, int chunkSize)
			throws IOException
		{
			super((new StringBuilder()).append("NIOFSIndexInput(path=\"").append(path).append("\")").toString(), path, context, chunkSize);
			channel = file.getChannel();
		}

		public NIOFSIndexInput(String sliceDescription, File path, RandomAccessFile file, FileChannel fc, long off, long length, int bufferSize, int chunkSize)
		{
			super((new StringBuilder()).append("NIOFSIndexInput(").append(sliceDescription).append(" in path=\"").append(path).append("\" slice=").append(off).append(":").append(off + length).append(")").toString(), file, off, length, bufferSize, chunkSize);
			channel = fc;
			isClone = true;
		}
	}


	public NIOFSDirectory(File path, LockFactory lockFactory)
		throws IOException
	{
		super(path, lockFactory);
	}

	public NIOFSDirectory(File path)
		throws IOException
	{
		super(path, null);
	}

	public IndexInput openInput(String name, IOContext context)
		throws IOException
	{
		ensureOpen();
		return new NIOFSIndexInput(new File(getDirectory(), name), context, getReadChunkSize());
	}

	public Directory.IndexInputSlicer createSlicer(String name, final IOContext context)
		throws IOException
	{
		ensureOpen();
		final File path = new File(getDirectory(), name);
		final RandomAccessFile descriptor = new RandomAccessFile(path, "r");
		return new Directory.IndexInputSlicer() {

			final RandomAccessFile val$descriptor;
			final File val$path;
			final IOContext val$context;
			final NIOFSDirectory this$0;

			public void close()
				throws IOException
			{
				descriptor.close();
			}

			public IndexInput openSlice(String sliceDescription, long offset, long length)
			{
				return new NIOFSIndexInput(sliceDescription, path, descriptor, descriptor.getChannel(), offset, length, BufferedIndexInput.bufferSize(context), getReadChunkSize());
			}

			public IndexInput openFullSlice()
			{
				return openSlice("full-slice", 0L, descriptor.length());
				IOException ex;
				ex;
				throw new RuntimeException(ex);
			}

			
			{
				this$0 = NIOFSDirectory.this;
				descriptor = randomaccessfile;
				path = file;
				context = iocontext;
				super(NIOFSDirectory.this);
			}
		};
	}
}
