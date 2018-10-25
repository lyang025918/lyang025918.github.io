// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Constants.java

package mylib.file.store;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

// Referenced classes of package mylib.file.store:
//			LucenePackage

public final class Constants
{

	public static final String JVM_VENDOR = System.getProperty("java.vm.vendor");
	public static final String JVM_VERSION = System.getProperty("java.vm.version");
	public static final String JVM_NAME = System.getProperty("java.vm.name");
	public static final String JAVA_VERSION = System.getProperty("java.version");
	/**
	 * @deprecated Field JAVA_1_1 is deprecated
	 */
	public static final boolean JAVA_1_1 = JAVA_VERSION.startsWith("1.1.");
	/**
	 * @deprecated Field JAVA_1_2 is deprecated
	 */
	public static final boolean JAVA_1_2 = JAVA_VERSION.startsWith("1.2.");
	/**
	 * @deprecated Field JAVA_1_3 is deprecated
	 */
	public static final boolean JAVA_1_3 = JAVA_VERSION.startsWith("1.3.");
	public static final String OS_NAME = System.getProperty("os.name");
	public static final boolean LINUX = OS_NAME.startsWith("Linux");
	public static final boolean WINDOWS = OS_NAME.startsWith("Windows");
	public static final boolean SUN_OS = OS_NAME.startsWith("SunOS");
	public static final boolean MAC_OS_X = OS_NAME.startsWith("Mac OS X");
	public static final String OS_ARCH = System.getProperty("os.arch");
	public static final String OS_VERSION = System.getProperty("os.version");
	public static final String JAVA_VENDOR = System.getProperty("java.vendor");
	public static final boolean JRE_IS_MINIMUM_JAVA6;
	public static final boolean JRE_IS_MINIMUM_JAVA7;
	public static final boolean JRE_IS_64BIT;
	public static final String LUCENE_MAIN_VERSION = ident("3.6.1");
	public static final String LUCENE_VERSION;

	private Constants()
	{
	}

	private static String ident(String s)
	{
		return s.toString();
	}

	static 
	{
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
		boolean v6 = true;
		try
		{
			java/lang/String.getMethod("isEmpty", new Class[0]);
		}
		catch (NoSuchMethodException nsme)
		{
			v6 = false;
		}
		JRE_IS_MINIMUM_JAVA6 = v6;
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
		Package pkg = LucenePackage.get();
		String v = pkg != null ? pkg.getImplementationVersion() : null;
		if (v == null)
			v = (new StringBuilder()).append(LUCENE_MAIN_VERSION).append("-SNAPSHOT").toString();
		else
		if (!v.startsWith(LUCENE_MAIN_VERSION))
			v = (new StringBuilder()).append(LUCENE_MAIN_VERSION).append("-SNAPSHOT ").append(v).toString();
		LUCENE_VERSION = ident(v);
	}
}
