// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Constants.java

package org.apache.lucene.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import org.apache.lucene.LucenePackage;

public final class Constants
{

	public static final String JVM_VENDOR = System.getProperty("java.vm.vendor");
	public static final String JVM_VERSION = System.getProperty("java.vm.version");
	public static final String JVM_NAME = System.getProperty("java.vm.name");
	public static final String JAVA_VERSION = System.getProperty("java.version");
	public static final String OS_NAME = System.getProperty("os.name");
	public static final boolean LINUX = OS_NAME.startsWith("Linux");
	public static final boolean WINDOWS = OS_NAME.startsWith("Windows");
	public static final boolean SUN_OS = OS_NAME.startsWith("SunOS");
	public static final boolean MAC_OS_X = OS_NAME.startsWith("Mac OS X");
	public static final String OS_ARCH = System.getProperty("os.arch");
	public static final String OS_VERSION = System.getProperty("os.version");
	public static final String JAVA_VENDOR = System.getProperty("java.vendor");
	/**
	 * @deprecated Field JRE_IS_MINIMUM_JAVA6 is deprecated
	 */
	public static final boolean JRE_IS_MINIMUM_JAVA6 = (new Boolean(true)).booleanValue();
	public static final boolean JRE_IS_MINIMUM_JAVA7;
	public static final boolean JRE_IS_MINIMUM_JAVA8;
	public static final boolean JRE_IS_64BIT;
	public static final String LUCENE_MAIN_VERSION = ident("4.0.0.2");
	public static final String LUCENE_VERSION;
	static final boolean $assertionsDisabled;

	private Constants()
	{
	}

	private static String ident(String s)
	{
		return s.toString();
	}

	static 
	{
		$assertionsDisabled = !org/apache/lucene/util/Constants.desiredAssertionStatus();
		boolean is64Bit = false;
		try
		{
			Class unsafeClass = Class.forName("sun.misc.Unsafe");
			Field unsafeField = unsafeClass.getDeclaredField("theUnsafe");
			unsafeField.setAccessible(true);
			Object unsafe = unsafeField.get(null);
			int addressSize = ((Number)unsafeClass.getMethod("addressSize", new Class[0]).invoke(unsafe, new Object[0])).intValue();
			is64Bit = addressSize >= 8;
		}
		catch (Exception e)
		{
			String x = System.getProperty("sun.arch.data.model");
			if (x != null)
				is64Bit = x.indexOf("64") != -1;
			else
			if (OS_ARCH != null && OS_ARCH.indexOf("64") != -1)
				is64Bit = true;
			else
				is64Bit = false;
		}
		JRE_IS_64BIT = is64Bit;
		boolean v7 = true;
		try
		{
			java/lang/Throwable.getMethod("getSuppressed", new Class[0]);
		}
		catch (NoSuchMethodException nsme)
		{
			v7 = false;
		}
		JRE_IS_MINIMUM_JAVA7 = v7;
		if (JRE_IS_MINIMUM_JAVA7)
		{
			boolean v8 = true;
			try
			{
				java/util/Collections.getMethod("emptySortedSet", new Class[0]);
			}
			catch (NoSuchMethodException nsme)
			{
				v8 = false;
			}
			JRE_IS_MINIMUM_JAVA8 = v8;
		} else
		{
			JRE_IS_MINIMUM_JAVA8 = false;
		}
		Package pkg = LucenePackage.get();
		String v = pkg != null ? pkg.getImplementationVersion() : null;
		if (v == null)
		{
			String parts[] = LUCENE_MAIN_VERSION.split("\\.");
			if (parts.length == 4)
			{
				if (!$assertionsDisabled && !parts[2].equals("0"))
					throw new AssertionError();
				v = (new StringBuilder()).append(parts[0]).append(".").append(parts[1]).append("-SNAPSHOT").toString();
			} else
			{
				v = (new StringBuilder()).append(LUCENE_MAIN_VERSION).append("-SNAPSHOT").toString();
			}
		}
		LUCENE_VERSION = ident(v);
	}
}
