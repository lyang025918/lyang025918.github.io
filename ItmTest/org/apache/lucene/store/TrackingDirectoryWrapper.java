// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TrackingDirectoryWrapper.java

package org.apache.lucene.store;

import java.io.Closeable;
import java.io.IOException;
import java.util.*;

// Referenced classes of package org.apache.lucene.store:
//			Directory, IOContext, IndexOutput, IndexInput, 
//			Lock, LockFactory

public final class TrackingDirectoryWrapper extends Directory
	implements Closeable
{

	private final Directory other;
	private final Set createdFileNames = Collections.synchronizedSet(new HashSet());

	public TrackingDirectoryWrapper(Directory other)
	{
		this.other = other;
	}

	public String[] listAll()
		throws IOException
	{
		return other.listAll();
	}

	public boolean fileExists(String name)
		throws IOException
	{
		return other.fileExists(name);
	}

	public void deleteFile(String name)
		throws IOException
	{
		createdFileNames.remove(name);
		other.deleteFile(name);
	}

	public long fileLength(String name)
		throws IOException
	{
		return other.fileLength(name);
	}

	public IndexOutput createOutput(String name, IOContext context)
		throws IOException
	{
		createdFileNames.add(name);
		return other.createOutput(name, context);
	}

	public void sync(Collection names)
		throws IOException
	{
		other.sync(names);
	}

	public IndexInput openInput(String name, IOContext context)
		throws IOException
	{
		return other.openInput(name, context);
	}

	public Lock makeLock(String name)
	{
		return other.makeLock(name);
	}

	public void clearLock(String name)
		throws IOException
	{
		other.clearLock(name);
	}

	public void close()
		throws IOException
	{
		other.close();
	}

	public void setLockFactory(LockFactory lockFactory)
		throws IOException
	{
		other.setLockFactory(lockFactory);
	}

	public LockFactory getLockFactory()
	{
		return other.getLockFactory();
	}

	public String getLockID()
	{
		return other.getLockID();
	}

	public String toString()
	{
		return (new StringBuilder()).append("TrackingDirectoryWrapper(").append(other.toString()).append(")").toString();
	}

	public void copy(Directory to, String src, String dest, IOContext context)
		throws IOException
	{
		createdFileNames.add(dest);
		other.copy(to, src, dest, context);
	}

	public Directory.IndexInputSlicer createSlicer(String name, IOContext context)
		throws IOException
	{
		return other.createSlicer(name, context);
	}

	public Set getCreatedFiles()
	{
		return createdFileNames;
	}
}
