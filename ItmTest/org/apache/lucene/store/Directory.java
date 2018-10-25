// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Directory.java

package org.apache.lucene.store;

import java.io.*;
import java.util.Collection;
import org.apache.lucene.util.IOUtils;

// Referenced classes of package org.apache.lucene.store:
//			AlreadyClosedException, IOContext, IndexOutput, IndexInput, 
//			LockFactory, Lock, BufferedIndexInput, DataInput

public abstract class Directory
	implements Closeable
{
	private static final class SlicedIndexInput extends BufferedIndexInput
	{

		IndexInput base;
		long fileOffset;
		long length;

		public SlicedIndexInput clone()
		{
			SlicedIndexInput clone = (SlicedIndexInput)super.clone();
			clone.base = base.clone();
			clone.fileOffset = fileOffset;
			clone.length = length;
			return clone;
		}

		protected void readInternal(byte b[], int offset, int len)
			throws IOException
		{
			long start = getFilePointer();
			if (start + (long)len > length)
			{
				throw new EOFException((new StringBuilder()).append("read past EOF: ").append(this).toString());
			} else
			{
				base.seek(fileOffset + start);
				base.readBytes(b, offset, len, false);
				return;
			}
		}

		protected void seekInternal(long l)
		{
		}

		public void close()
			throws IOException
		{
			base.close();
		}

		public long length()
		{
			return length;
		}

		public volatile BufferedIndexInput clone()
		{
			return clone();
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

		SlicedIndexInput(String sliceDescription, IndexInput base, long fileOffset, long length)
		{
			this(sliceDescription, base, fileOffset, length, 1024);
		}

		SlicedIndexInput(String sliceDescription, IndexInput base, long fileOffset, long length, int readBufferSize)
		{
			super((new StringBuilder()).append("SlicedIndexInput(").append(sliceDescription).append(" in ").append(base).append(" slice=").append(fileOffset).append(":").append(fileOffset + length).append(")").toString(), readBufferSize);
			this.base = base.clone();
			this.fileOffset = fileOffset;
			this.length = length;
		}
	}

	public abstract class IndexInputSlicer
		implements Closeable
	{

		final Directory this$0;

		public abstract IndexInput openSlice(String s, long l, long l1)
			throws IOException;

		/**
		 * @deprecated Method openFullSlice is deprecated
		 */

		public abstract IndexInput openFullSlice()
			throws IOException;

		public IndexInputSlicer()
		{
			this$0 = Directory.this;
			super();
		}
	}


	protected volatile boolean isOpen;
	protected LockFactory lockFactory;
	static final boolean $assertionsDisabled = !org/apache/lucene/store/Directory.desiredAssertionStatus();

	public Directory()
	{
		isOpen = true;
	}

	public abstract String[] listAll()
		throws IOException;

	public abstract boolean fileExists(String s)
		throws IOException;

	public abstract void deleteFile(String s)
		throws IOException;

	public abstract long fileLength(String s)
		throws IOException;

	public abstract IndexOutput createOutput(String s, IOContext iocontext)
		throws IOException;

	public abstract void sync(Collection collection)
		throws IOException;

	public abstract IndexInput openInput(String s, IOContext iocontext)
		throws IOException;

	public Lock makeLock(String name)
	{
		return lockFactory.makeLock(name);
	}

	public void clearLock(String name)
		throws IOException
	{
		if (lockFactory != null)
			lockFactory.clearLock(name);
	}

	public abstract void close()
		throws IOException;

	public void setLockFactory(LockFactory lockFactory)
		throws IOException
	{
		if (!$assertionsDisabled && lockFactory == null)
		{
			throw new AssertionError();
		} else
		{
			this.lockFactory = lockFactory;
			lockFactory.setLockPrefix(getLockID());
			return;
		}
	}

	public LockFactory getLockFactory()
	{
		return lockFactory;
	}

	public String getLockID()
	{
		return toString();
	}

	public String toString()
	{
		return (new StringBuilder()).append(super.toString()).append(" lockFactory=").append(getLockFactory()).toString();
	}

	public void copy(Directory to, String src, String dest, IOContext context)
		throws IOException
	{
		IndexOutput os;
		IndexInput is;
		IOException priorException;
		os = null;
		is = null;
		priorException = null;
		os = to.createOutput(dest, context);
		is = openInput(src, context);
		os.copyBytes(is, is.length());
		boolean success = false;
		IOUtils.closeWhileHandlingException(priorException, new Closeable[] {
			os, is
		});
		success = true;
		if (!success)
			try
			{
				to.deleteFile(dest);
			}
			catch (Throwable t) { }
		break MISSING_BLOCK_LABEL_245;
		Exception exception;
		exception;
		if (!success)
			try
			{
				to.deleteFile(dest);
			}
			catch (Throwable t) { }
		throw exception;
		IOException ioe;
		ioe;
		priorException = ioe;
		ioe = 0;
		IOUtils.closeWhileHandlingException(priorException, new Closeable[] {
			os, is
		});
		ioe = 1;
		if (!ioe)
			try
			{
				to.deleteFile(dest);
			}
			catch (Throwable t) { }
		break MISSING_BLOCK_LABEL_245;
		Exception exception1;
		exception1;
		if (!ioe)
			try
			{
				to.deleteFile(dest);
			}
			catch (Throwable t) { }
		throw exception1;
		Exception exception2;
		exception2;
		boolean success = false;
		IOUtils.closeWhileHandlingException(priorException, new Closeable[] {
			os, is
		});
		success = true;
		if (!success)
			try
			{
				to.deleteFile(dest);
			}
			catch (Throwable t) { }
		break MISSING_BLOCK_LABEL_242;
		Exception exception3;
		exception3;
		if (!success)
			try
			{
				to.deleteFile(dest);
			}
			catch (Throwable t) { }
		throw exception3;
		throw exception2;
	}

	public IndexInputSlicer createSlicer(final String name, final IOContext context)
		throws IOException
	{
		ensureOpen();
		return new IndexInputSlicer() {

			private final IndexInput base;
			final String val$name;
			final IOContext val$context;
			final Directory this$0;

			public IndexInput openSlice(String sliceDescription, long offset, long length)
			{
				return new SlicedIndexInput((new StringBuilder()).append("SlicedIndexInput(").append(sliceDescription).append(" in ").append(base).append(")").toString(), base, offset, length);
			}

			public void close()
				throws IOException
			{
				base.close();
			}

			public IndexInput openFullSlice()
			{
				return base.clone();
			}

			
				throws IOException
			{
				this$0 = Directory.this;
				name = s;
				context = iocontext;
				super();
				base = openInput(name, context);
			}
		};
	}

	protected final void ensureOpen()
		throws AlreadyClosedException
	{
		if (!isOpen)
			throw new AlreadyClosedException("this Directory is closed");
		else
			return;
	}

}
