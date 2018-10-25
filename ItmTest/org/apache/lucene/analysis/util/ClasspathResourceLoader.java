// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ClasspathResourceLoader.java

package org.apache.lucene.analysis.util;

import java.io.IOException;
import java.io.InputStream;

// Referenced classes of package org.apache.lucene.analysis.util:
//			ResourceLoader

public final class ClasspathResourceLoader
	implements ResourceLoader
{

	private final Class clazz;
	private final ClassLoader loader;

	public ClasspathResourceLoader()
	{
		this(Thread.currentThread().getContextClassLoader());
	}

	public ClasspathResourceLoader(ClassLoader loader)
	{
		this(null, loader);
	}

	public ClasspathResourceLoader(Class clazz)
	{
		this(clazz, clazz.getClassLoader());
	}

	private ClasspathResourceLoader(Class clazz, ClassLoader loader)
	{
		this.clazz = clazz;
		this.loader = loader;
	}

	public InputStream openResource(String resource)
		throws IOException
	{
		InputStream stream = clazz == null ? loader.getResourceAsStream(resource) : clazz.getResourceAsStream(resource);
		if (stream == null)
			throw new IOException((new StringBuilder()).append("Resource not found: ").append(resource).toString());
		else
			return stream;
	}

	public Object newInstance(String cname, Class expectedType)
	{
		Class clazz = Class.forName(cname, true, loader).asSubclass(expectedType);
		return clazz.newInstance();
		Exception e;
		e;
		throw new RuntimeException((new StringBuilder()).append("Cannot instantiate class: ").append(cname).toString(), e);
	}
}
