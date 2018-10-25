// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RAMDirectory.java

package org.apache.lucene.store;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// Referenced classes of package org.apache.lucene.store:
//			Directory, SingleInstanceLockFactory, RAMFile, RAMOutputStream, 
//			RAMInputStream, IOContext, IndexOutput, IndexInput

public class RAMDirectory extends Directory
{

	protected final Map fileMap;
	protected final AtomicLong sizeInBytes;

	public RAMDirectory()
	{
		fileMap = new ConcurrentHashMap();
		sizeInBytes = new AtomicLong();
		try
		{
			setLockFactory(new SingleInstanceLockFactory());
		}
		catch (IOException e) { }
	}

	public RAMDirectory(Directory dir, IOContext context)
		throws IOException
	{
		this(dir, false, context);
	}

	private RAMDirectory(Directory dir, boolean closeDir, IOContext context)
		throws IOException
	{
		this();
		String arr$[] = dir.listAll();
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			String file = arr$[i$];
			dir.copy(this, file, file, context);
		}

		if (closeDir)
			dir.close();
	}

	public final String[] listAll()
	{
		ensureOpen();
		Set fileNames = fileMap.keySet();
		List names = new ArrayList(fileNames.size());
		String name;
		for (Iterator i$ = fileNames.iterator(); i$.hasNext(); names.add(name))
			name = (String)i$.next();

		return (String[])names.toArray(new String[names.size()]);
	}

	public final boolean fileExists(String name)
	{
		ensureOpen();
		return fileMap.containsKey(name);
	}

	public final long fileLength(String name)
		throws IOException
	{
		ensureOpen();
		RAMFile file = (RAMFile)fileMap.get(name);
		if (file == null)
			throw new FileNotFoundException(name);
		else
			return file.getLength();
	}

	public final long sizeInBytes()
	{
		ensureOpen();
		return sizeInBytes.get();
	}

	public void deleteFile(String name)
		throws IOException
	{
		ensureOpen();
		RAMFile file = (RAMFile)fileMap.remove(name);
		if (file != null)
		{
			file.directory = null;
			sizeInBytes.addAndGet(-file.sizeInBytes);
		} else
		{
			throw new FileNotFoundException(name);
		}
	}

	public IndexOutput createOutput(String name, IOContext context)
		throws IOException
	{
		ensureOpen();
		RAMFile file = newRAMFile();
		RAMFile existing = (RAMFile)fileMap.remove(name);
		if (existing != null)
		{
			sizeInBytes.addAndGet(-existing.sizeInBytes);
			existing.directory = null;
		}
		fileMap.put(name, file);
		return new RAMOutputStream(file);
	}

	protected RAMFile newRAMFile()
	{
		return new RAMFile(this);
	}

	public void sync(Collection collection)
		throws IOException
	{
	}

	public IndexInput openInput(String name, IOContext context)
		throws IOException
	{
		ensureOpen();
		RAMFile file = (RAMFile)fileMap.get(name);
		if (file == null)
			throw new FileNotFoundException(name);
		else
			return new RAMInputStream(name, file);
	}

	public void close()
	{
		isOpen = false;
		fileMap.clear();
	}
}
