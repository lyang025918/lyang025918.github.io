// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Loader.java

package org.apache.log4j.helpers;

import java.io.InterruptedIOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;

// Referenced classes of package org.apache.log4j.helpers:
//			LogLog, OptionConverter

public class Loader
{

	static final String TSTR = "Caught Exception while in Loader.getResource. This may be innocuous.";
	private static boolean java1 = true;
	private static boolean ignoreTCL = false;

	public Loader()
	{
	}

	/**
	 * @deprecated Method getResource is deprecated
	 */

	public static URL getResource(String resource, Class clazz)
	{
		return getResource(resource);
	}

	public static URL getResource(String resource)
	{
		URL url;
		ClassLoader classLoader = null;
		url = null;
		if (java1 || ignoreTCL)
			break MISSING_BLOCK_LABEL_72;
		ClassLoader classLoader = getTCL();
		if (classLoader == null)
			break MISSING_BLOCK_LABEL_72;
		LogLog.debug("Trying to find [" + resource + "] using context classloader " + classLoader + ".");
		url = classLoader.getResource(resource);
		if (url != null)
			return url;
		ClassLoader classLoader = (org.apache.log4j.helpers.Loader.class).getClassLoader();
		if (classLoader == null)
			break MISSING_BLOCK_LABEL_205;
		LogLog.debug("Trying to find [" + resource + "] using " + classLoader + " class loader.");
		url = classLoader.getResource(resource);
		if (url != null)
			return url;
		break MISSING_BLOCK_LABEL_205;
		IllegalAccessException t;
		t;
		LogLog.warn("Caught Exception while in Loader.getResource. This may be innocuous.", t);
		break MISSING_BLOCK_LABEL_205;
		t;
		if ((t.getTargetException() instanceof InterruptedException) || (t.getTargetException() instanceof InterruptedIOException))
			Thread.currentThread().interrupt();
		LogLog.warn("Caught Exception while in Loader.getResource. This may be innocuous.", t);
		break MISSING_BLOCK_LABEL_205;
		t;
		LogLog.warn("Caught Exception while in Loader.getResource. This may be innocuous.", t);
		LogLog.debug("Trying to find [" + resource + "] using ClassLoader.getSystemResource().");
		return ClassLoader.getSystemResource(resource);
	}

	public static boolean isJava1()
	{
		return java1;
	}

	private static ClassLoader getTCL()
		throws IllegalAccessException, InvocationTargetException
	{
		Method method = null;
		try
		{
			method = (java.lang.Thread.class).getMethod("getContextClassLoader", null);
		}
		catch (NoSuchMethodException e)
		{
			return null;
		}
		return (ClassLoader)method.invoke(Thread.currentThread(), null);
	}

	public static Class loadClass(String clazz)
		throws ClassNotFoundException
	{
		if (java1 || ignoreTCL)
			return Class.forName(clazz);
		return getTCL().loadClass(clazz);
		InvocationTargetException e;
		e;
		if ((e.getTargetException() instanceof InterruptedException) || (e.getTargetException() instanceof InterruptedIOException))
			Thread.currentThread().interrupt();
		break MISSING_BLOCK_LABEL_56;
		Throwable t;
		t;
		return Class.forName(clazz);
	}

	static 
	{
		String prop = OptionConverter.getSystemProperty("java.version", null);
		if (prop != null)
		{
			int i = prop.indexOf('.');
			if (i != -1 && prop.charAt(i + 1) != '1')
				java1 = false;
		}
		String ignoreTCLProp = OptionConverter.getSystemProperty("log4j.ignoreTCL", null);
		if (ignoreTCLProp != null)
			ignoreTCL = OptionConverter.toBoolean(ignoreTCLProp, true);
	}
}
