// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FSDirectory.java

package org.apache.lucene.store;

import java.io.*;
import java.util.*;
import org.apache.lucene.util.Constants;
import org.apache.lucene.util.ThreadInterruptedException;

// Referenced classes of package org.apache.lucene.store:
//			Directory, NativeFSLockFactory, NoSuchDirectoryException, MMapDirectory, 
//			SimpleFSDirectory, NIOFSDirectory, FSLockFactory, RateLimiter, 
//			LockFactory, IOContext, IndexOutput, BufferedIndexOutput, 
//			BufferedIndexInput, IndexInput, DataInput

public abstract class FSDirectory extends Directory
{
	protected static class FSIndexOutput extends BufferedIndexOutput
	{

		private final FSDirectory parent;
		private final String name;
		private final RandomAccessFile file;
		private volatile boolean isOpen;
		private final RateLimiter rateLimiter;
		static final boolean $assertionsDisabled = !org/apache/lucene/store/FSDirectory.desiredAssertionStatus();

		public void flushBuffer(byte b[], int offset, int size)
			throws IOException
		{
			if (!$assertionsDisabled && !isOpen)
				throw new AssertionError();
			if (rateLimiter != null)
				rateLimiter.pause(size);
			file.write(b, offset, size);
		}

		public void close()
			throws IOException
		{
			boolean success;
			parent.onIndexOutputClosed(this);
			if (!isOpen)
				break MISSING_BLOCK_LABEL_90;
			success = false;
			super.close();
			success = true;
			isOpen = false;
			if (!success)
				try
				{
					file.close();
				}
				catch (Throwable t) { }
			else
				file.close();
			break MISSING_BLOCK_LABEL_90;
			Exception exception;
			exception;
			isOpen = false;
			if (!success)
				try
				{
					file.close();
				}
				catch (Throwable t) { }
			else
				file.close();
			throw exception;
		}

		public void seek(long pos)
			throws IOException
		{
			super.seek(pos);
			file.seek(pos);
		}

		public long length()
			throws IOException
		{
			return file.length();
		}

		public void setLength(long length)
			throws IOException
		{
			file.setLength(length);
		}



		public FSIndexOutput(FSDirectory parent, String name, RateLimiter rateLimiter)
			throws IOException
		{
			this.parent = parent;
			this.name = name;
			file = new RandomAccessFile(new File(parent.directory, name), "rw");
			isOpen = true;
			this.rateLimiter = rateLimiter;
		}
	}

	protected static abstract class FSIndexInput extends BufferedIndexInput
	{

		protected final RandomAccessFile file;
		boolean isClone;
		protected final int chunkSize;
		protected final long off;
		protected final long end;

		public void close()
			throws IOException
		{
			if (!isClone)
				file.close();
		}

		public FSIndexInput clone()
		{
			FSIndexInput clone = (FSIndexInput)super.clone();
			clone.isClone = true;
			return clone;
		}

		public final long length()
		{
			return end - off;
		}

		boolean isFDValid()
			throws IOException
		{
			return file.getFD().valid();
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

		protected FSIndexInput(String resourceDesc, File path, IOContext context, int chunkSize)
			throws IOException
		{
			super(resourceDesc, context);
			isClone = false;
			file = new RandomAccessFile(path, "r");
			this.chunkSize = chunkSize;
			off = 0L;
			end = file.length();
		}

		protected FSIndexInput(String resourceDesc, RandomAccessFile file, long off, long length, int bufferSize, 
				int chunkSize)
		{
			super(resourceDesc, bufferSize);
			isClone = false;
			this.file = file;
			this.chunkSize = chunkSize;
			this.off = off;
			end = off + length;
			isClone = true;
		}
	}


	public static final int DEFAULT_READ_CHUNK_SIZE;
	protected final File directory;
	protected final Set staleFiles = Collections.synchronizedSet(new HashSet());
	private int chunkSize;
	private volatile RateLimiter mergeWriteRateLimiter;

	private static File getCanonicalPath(File file)
		throws IOException
	{
		return new File(file.getCanonicalPath());
	}

	protected FSDirectory(File path, LockFactory lockFactory)
		throws IOException
	{
		chunkSize = DEFAULT_READ_CHUNK_SIZE;
		if (lockFactory == null)
			lockFactory = new NativeFSLockFactory();
		directory = getCanonicalPath(path);
		if (directory.exists() && !directory.isDirectory())
		{
			throw new NoSuchDirectoryException((new StringBuilder()).append("file '").append(directory).append("' exists but is not a directory").toString());
		} else
		{
			setLockFactory(lockFactory);
			return;
		}
	}

	public static FSDirectory open(File path)
		throws IOException
	{
		return open(path, null);
	}

	public static FSDirectory open(File path, LockFactory lockFactory)
		throws IOException
	{
		if ((Constants.WINDOWS || Constants.SUN_OS || Constants.LINUX) && Constants.JRE_IS_64BIT && MMapDirectory.UNMAP_SUPPORTED)
			return new MMapDirectory(path, lockFactory);
		if (Constants.WINDOWS)
			return new SimpleFSDirectory(path, lockFactory);
		else
			return new NIOFSDirectory(path, lockFactory);
	}

	public void setLockFactory(LockFactory lockFactory)
		throws IOException
	{
		super.setLockFactory(lockFactory);
		if (lockFactory instanceof FSLockFactory)
		{
			FSLockFactory lf = (FSLockFactory)lockFactory;
			File dir = lf.getLockDir();
			if (dir == null)
			{
				lf.setLockDir(directory);
				lf.setLockPrefix(null);
			} else
			if (dir.getCanonicalPath().equals(directory.getCanonicalPath()))
				lf.setLockPrefix(null);
		}
	}

	public static String[] listAll(File dir)
		throws IOException
	{
		if (!dir.exists())
			throw new NoSuchDirectoryException((new StringBuilder()).append("directory '").append(dir).append("' does not exist").toString());
		if (!dir.isDirectory())
			throw new NoSuchDirectoryException((new StringBuilder()).append("file '").append(dir).append("' exists but is not a directory").toString());
		String result[] = dir.list(new FilenameFilter() {

			public boolean accept(File dir, String file)
			{
				return !(new File(dir, file)).isDirectory();
			}

		});
		if (result == null)
			throw new IOException((new StringBuilder()).append("directory '").append(dir).append("' exists and is a directory, but cannot be listed: list() returned null").toString());
		else
			return result;
	}

	public String[] listAll()
		throws IOException
	{
		ensureOpen();
		return listAll(directory);
	}

	public boolean fileExists(String name)
	{
		ensureOpen();
		File file = new File(directory, name);
		return file.exists();
	}

	public static long fileModified(File directory, String name)
	{
		File file = new File(directory, name);
		return file.lastModified();
	}

	public long fileLength(String name)
		throws IOException
	{
		ensureOpen();
		File file = new File(directory, name);
		long len = file.length();
		if (len == 0L && !file.exists())
			throw new FileNotFoundException(name);
		else
			return len;
	}

	public void deleteFile(String name)
		throws IOException
	{
		ensureOpen();
		File file = new File(directory, name);
		if (!file.delete())
		{
			throw new IOException((new StringBuilder()).append("Cannot delete ").append(file).toString());
		} else
		{
			staleFiles.remove(name);
			return;
		}
	}

	public IndexOutput createOutput(String name, IOContext context)
		throws IOException
	{
		ensureOpen();
		ensureCanWrite(name);
		return new FSIndexOutput(this, name, context.context != IOContext.Context.MERGE ? null : mergeWriteRateLimiter);
	}

	public void setMaxMergeWriteMBPerSec(Double mbPerSec)
	{
		RateLimiter limiter = mergeWriteRateLimiter;
		if (mbPerSec == null)
		{
			if (limiter != null)
			{
				limiter.setMbPerSec(1.7976931348623157E+308D);
				mergeWriteRateLimiter = null;
			}
		} else
		if (limiter != null)
			limiter.setMbPerSec(mbPerSec.doubleValue());
		else
			mergeWriteRateLimiter = new RateLimiter(mbPerSec.doubleValue());
	}

	public void setMaxMergeWriteLimiter(RateLimiter mergeWriteRateLimiter)
	{
		this.mergeWriteRateLimiter = mergeWriteRateLimiter;
	}

	public Double getMaxMergeWriteMBPerSec()
	{
		RateLimiter limiter = mergeWriteRateLimiter;
		return limiter != null ? Double.valueOf(limiter.getMbPerSec()) : null;
	}

	protected void ensureCanWrite(String name)
		throws IOException
	{
		if (!directory.exists() && !directory.mkdirs())
			throw new IOException((new StringBuilder()).append("Cannot create directory: ").append(directory).toString());
		File file = new File(directory, name);
		if (file.exists() && !file.delete())
			throw new IOException((new StringBuilder()).append("Cannot overwrite: ").append(file).toString());
		else
			return;
	}

	protected void onIndexOutputClosed(FSIndexOutput io)
	{
		staleFiles.add(io.name);
	}

	public void sync(Collection names)
		throws IOException
	{
		ensureOpen();
		Set toSync = new HashSet(names);
		toSync.retainAll(staleFiles);
		String name;
		for (Iterator i$ = toSync.iterator(); i$.hasNext(); fsync(name))
			name = (String)i$.next();

		staleFiles.removeAll(toSync);
	}

	public String getLockID()
	{
		ensureOpen();
		String dirName;
		try
		{
			dirName = directory.getCanonicalPath();
		}
		catch (IOException e)
		{
			throw new RuntimeException(e.toString(), e);
		}
		int digest = 0;
		for (int charIDX = 0; charIDX < dirName.length(); charIDX++)
		{
			char ch = dirName.charAt(charIDX);
			digest = 31 * digest + ch;
		}

		return (new StringBuilder()).append("lucene-").append(Integer.toHexString(digest)).toString();
	}

	public synchronized void close()
	{
		isOpen = false;
	}

	public File getDirectory()
	{
		ensureOpen();
		return directory;
	}

	public String toString()
	{
		return (new StringBuilder()).append(getClass().getName()).append("@").append(directory).append(" lockFactory=").append(getLockFactory()).toString();
	}

	public final void setReadChunkSize(int chunkSize)
	{
		if (chunkSize <= 0)
			throw new IllegalArgumentException("chunkSize must be positive");
		if (!Constants.JRE_IS_64BIT)
			this.chunkSize = chunkSize;
	}

	public final int getReadChunkSize()
	{
		return chunkSize;
	}

	protected void fsync(String name)
		throws IOException
	{
		File fullFile;
		boolean success;
		int retryCount;
		IOException exc;
		fullFile = new File(directory, name);
		success = false;
		retryCount = 0;
		exc = null;
_L2:
		RandomAccessFile file;
		if (success || retryCount >= 5)
			break; /* Loop/switch isn't completed */
		retryCount++;
		file = null;
		file = new RandomAccessFile(fullFile, "rw");
		file.getFD().sync();
		success = true;
		if (file != null)
			file.close();
		continue; /* Loop/switch isn't completed */
		IOException ioe;
		ioe;
		if (file != null)
			file.close();
		throw ioe;
		ioe;
		if (exc == null)
			exc = ioe;
		try
		{
			Thread.sleep(5L);
		}
		catch (InterruptedException ie)
		{
			throw new ThreadInterruptedException(ie);
		}
		if (true) goto _L2; else goto _L1
_L1:
		if (!success)
			throw exc;
		else
			return;
	}

	static 
	{
		DEFAULT_READ_CHUNK_SIZE = Constants.JRE_IS_64BIT ? 0x7fffffff : 0x6400000;
	}
}
