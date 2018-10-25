// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FileSwitchDirectory.java

package org.apache.lucene.store;

import java.io.IOException;
import java.util.*;

// Referenced classes of package org.apache.lucene.store:
//			Directory, NoSuchDirectoryException, IOContext, IndexOutput, 
//			IndexInput

public class FileSwitchDirectory extends Directory
{

	private final Directory secondaryDir;
	private final Directory primaryDir;
	private final Set primaryExtensions;
	private boolean doClose;

	public FileSwitchDirectory(Set primaryExtensions, Directory primaryDir, Directory secondaryDir, boolean doClose)
	{
		this.primaryExtensions = primaryExtensions;
		this.primaryDir = primaryDir;
		this.secondaryDir = secondaryDir;
		this.doClose = doClose;
		lockFactory = primaryDir.getLockFactory();
	}

	public Directory getPrimaryDir()
	{
		return primaryDir;
	}

	public Directory getSecondaryDir()
	{
		return secondaryDir;
	}

	public void close()
		throws IOException
	{
		if (!doClose)
			break MISSING_BLOCK_LABEL_39;
		secondaryDir.close();
		primaryDir.close();
		break MISSING_BLOCK_LABEL_34;
		Exception exception;
		exception;
		primaryDir.close();
		throw exception;
		doClose = false;
	}

	public String[] listAll()
		throws IOException
	{
		Set files = new HashSet();
		NoSuchDirectoryException exc = null;
		try
		{
			String arr$[] = primaryDir.listAll();
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				String f = arr$[i$];
				files.add(f);
			}

		}
		catch (NoSuchDirectoryException e)
		{
			exc = e;
		}
		try
		{
			String arr$[] = secondaryDir.listAll();
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				String f = arr$[i$];
				files.add(f);
			}

		}
		catch (NoSuchDirectoryException e)
		{
			if (exc != null)
				throw exc;
			if (files.isEmpty())
				throw e;
		}
		if (exc != null && files.isEmpty())
			throw exc;
		else
			return (String[])files.toArray(new String[files.size()]);
	}

	public static String getExtension(String name)
	{
		int i = name.lastIndexOf('.');
		if (i == -1)
			return "";
		else
			return name.substring(i + 1, name.length());
	}

	private Directory getDirectory(String name)
	{
		String ext = getExtension(name);
		if (primaryExtensions.contains(ext))
			return primaryDir;
		else
			return secondaryDir;
	}

	public boolean fileExists(String name)
		throws IOException
	{
		return getDirectory(name).fileExists(name);
	}

	public void deleteFile(String name)
		throws IOException
	{
		getDirectory(name).deleteFile(name);
	}

	public long fileLength(String name)
		throws IOException
	{
		return getDirectory(name).fileLength(name);
	}

	public IndexOutput createOutput(String name, IOContext context)
		throws IOException
	{
		return getDirectory(name).createOutput(name, context);
	}

	public void sync(Collection names)
		throws IOException
	{
		List primaryNames = new ArrayList();
		List secondaryNames = new ArrayList();
		for (Iterator i$ = names.iterator(); i$.hasNext();)
		{
			String name = (String)i$.next();
			if (primaryExtensions.contains(getExtension(name)))
				primaryNames.add(name);
			else
				secondaryNames.add(name);
		}

		primaryDir.sync(primaryNames);
		secondaryDir.sync(secondaryNames);
	}

	public IndexInput openInput(String name, IOContext context)
		throws IOException
	{
		return getDirectory(name).openInput(name, context);
	}

	public Directory.IndexInputSlicer createSlicer(String name, IOContext context)
		throws IOException
	{
		return getDirectory(name).createSlicer(name, context);
	}
}
