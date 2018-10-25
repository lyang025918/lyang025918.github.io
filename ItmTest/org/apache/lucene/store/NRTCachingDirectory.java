// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NRTCachingDirectory.java

package org.apache.lucene.store;

import java.io.Closeable;
import java.io.IOException;
import java.util.*;
import org.apache.lucene.util.IOUtils;

// Referenced classes of package org.apache.lucene.store:
//			Directory, RAMDirectory, NoSuchDirectoryException, IOContext, 
//			IndexOutput, IndexInput, MergeInfo, FlushInfo, 
//			LockFactory, Lock

public class NRTCachingDirectory extends Directory
{

	private final RAMDirectory cache = new RAMDirectory();
	private final Directory delegate;
	private final long maxMergeSizeBytes;
	private final long maxCachedBytes;
	private static final boolean VERBOSE = false;
	private final Object uncacheLock = new Object();
	static final boolean $assertionsDisabled = !org/apache/lucene/store/NRTCachingDirectory.desiredAssertionStatus();

	public NRTCachingDirectory(Directory delegate, double maxMergeSizeMB, double maxCachedMB)
	{
		this.delegate = delegate;
		maxMergeSizeBytes = (long)(maxMergeSizeMB * 1024D * 1024D);
		maxCachedBytes = (long)(maxCachedMB * 1024D * 1024D);
	}

	public Directory getDelegate()
	{
		return delegate;
	}

	public LockFactory getLockFactory()
	{
		return delegate.getLockFactory();
	}

	public void setLockFactory(LockFactory lf)
		throws IOException
	{
		delegate.setLockFactory(lf);
	}

	public String getLockID()
	{
		return delegate.getLockID();
	}

	public Lock makeLock(String name)
	{
		return delegate.makeLock(name);
	}

	public void clearLock(String name)
		throws IOException
	{
		delegate.clearLock(name);
	}

	public String toString()
	{
		return (new StringBuilder()).append("NRTCachingDirectory(").append(delegate).append("; maxCacheMB=").append((double)(maxCachedBytes / 1024L) / 1024D).append(" maxMergeSizeMB=").append((double)(maxMergeSizeBytes / 1024L) / 1024D).append(")").toString();
	}

	public synchronized String[] listAll()
		throws IOException
	{
		Set files = new HashSet();
		String arr$[] = cache.listAll();
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			String f = arr$[i$];
			files.add(f);
		}

		try
		{
			arr$ = delegate.listAll();
			len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				String f = arr$[i$];
				files.add(f);
			}

		}
		catch (NoSuchDirectoryException ex)
		{
			if (files.isEmpty())
				throw ex;
		}
		return (String[])files.toArray(new String[files.size()]);
	}

	public long sizeInBytes()
	{
		return cache.sizeInBytes();
	}

	public synchronized boolean fileExists(String name)
		throws IOException
	{
		return cache.fileExists(name) || delegate.fileExists(name);
	}

	public synchronized void deleteFile(String name)
		throws IOException
	{
		if (cache.fileExists(name))
		{
			if (!$assertionsDisabled && delegate.fileExists(name))
				throw new AssertionError((new StringBuilder()).append("name=").append(name).toString());
			cache.deleteFile(name);
		} else
		{
			delegate.deleteFile(name);
		}
	}

	public synchronized long fileLength(String name)
		throws IOException
	{
		if (cache.fileExists(name))
			return cache.fileLength(name);
		else
			return delegate.fileLength(name);
	}

	public String[] listCachedFiles()
	{
		return cache.listAll();
	}

	public IndexOutput createOutput(String name, IOContext context)
		throws IOException
	{
		if (doCacheWrite(name, context))
		{
			try
			{
				delegate.deleteFile(name);
			}
			catch (IOException ioe) { }
			return cache.createOutput(name, context);
		}
		try
		{
			cache.deleteFile(name);
		}
		catch (IOException ioe) { }
		return delegate.createOutput(name, context);
	}

	public void sync(Collection fileNames)
		throws IOException
	{
		String fileName;
		for (Iterator i$ = fileNames.iterator(); i$.hasNext(); unCache(fileName))
			fileName = (String)i$.next();

		delegate.sync(fileNames);
	}

	public synchronized IndexInput openInput(String name, IOContext context)
		throws IOException
	{
		if (cache.fileExists(name))
			return cache.openInput(name, context);
		else
			return delegate.openInput(name, context);
	}

	public synchronized Directory.IndexInputSlicer createSlicer(String name, IOContext context)
		throws IOException
	{
		ensureOpen();
		if (cache.fileExists(name))
			return cache.createSlicer(name, context);
		else
			return delegate.createSlicer(name, context);
	}

	public void close()
		throws IOException
	{
		String arr$[] = cache.listAll();
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			String fileName = arr$[i$];
			unCache(fileName);
		}

		cache.close();
		delegate.close();
	}

	protected boolean doCacheWrite(String name, IOContext context)
	{
		long bytes = 0L;
		if (context.mergeInfo != null)
			bytes = context.mergeInfo.estimatedMergeBytes;
		else
		if (context.flushInfo != null)
			bytes = context.flushInfo.estimatedSegmentSize;
		return !name.equals("segments.gen") && bytes <= maxMergeSizeBytes && bytes + cache.sizeInBytes() <= maxCachedBytes;
	}

	private void unCache(String fileName)
		throws IOException
	{
label0:
		{
			synchronized (uncacheLock)
			{
				if (cache.fileExists(fileName))
					break label0;
			}
			return;
		}
		IOContext context;
		IndexOutput out;
		IndexInput in;
		if (delegate.fileExists(fileName))
			throw new IOException((new StringBuilder()).append("cannot uncache file=\"").append(fileName).append("\": it was separately also created in the delegate directory").toString());
		context = IOContext.DEFAULT;
		out = delegate.createOutput(fileName, context);
		in = null;
		in = cache.openInput(fileName, context);
		out.copyBytes(in, in.length());
		IOUtils.close(new Closeable[] {
			in, out
		});
		break MISSING_BLOCK_LABEL_147;
		Exception exception;
		exception;
		IOUtils.close(new Closeable[] {
			in, out
		});
		throw exception;
		synchronized (this)
		{
			cache.deleteFile(fileName);
		}
		obj;
		JVM INSTR monitorexit ;
		  goto _L1
		exception2;
		throw exception2;
_L1:
	}

}
