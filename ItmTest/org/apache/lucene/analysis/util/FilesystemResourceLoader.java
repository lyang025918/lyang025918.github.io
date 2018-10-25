// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FilesystemResourceLoader.java

package org.apache.lucene.analysis.util;

import java.io.*;

// Referenced classes of package org.apache.lucene.analysis.util:
//			ClasspathResourceLoader, ResourceLoader

public final class FilesystemResourceLoader
	implements ResourceLoader
{

	private final File baseDirectory;
	private final ResourceLoader delegate;

	public FilesystemResourceLoader()
	{
		this((File)null);
	}

	public FilesystemResourceLoader(File baseDirectory)
	{
		this(baseDirectory, ((ResourceLoader) (new ClasspathResourceLoader())));
	}

	public FilesystemResourceLoader(File baseDirectory, ResourceLoader delegate)
	{
		if (baseDirectory != null && !baseDirectory.isDirectory())
			throw new IllegalArgumentException("baseDirectory is not a directory or null");
		if (delegate == null)
		{
			throw new IllegalArgumentException("delegate ResourceLoader may not be null");
		} else
		{
			this.baseDirectory = baseDirectory;
			this.delegate = delegate;
			return;
		}
	}

	public InputStream openResource(String resource)
		throws IOException
	{
		File file;
		file = new File(resource);
		if (baseDirectory != null && !file.isAbsolute())
			file = new File(baseDirectory, resource);
		return new FileInputStream(file);
		FileNotFoundException fnfe;
		fnfe;
		return delegate.openResource(resource);
	}

	public Object newInstance(String cname, Class expectedType)
	{
		return delegate.newInstance(cname, expectedType);
	}
}
