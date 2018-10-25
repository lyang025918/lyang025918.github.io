// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Util.java

package IceInternal;

import Ice.*;
import java.io.*;

// Referenced classes of package IceInternal:
//			ProtocolPluginFacadeI, Instance, ProtocolPluginFacade

public final class Util
{

	public Util()
	{
	}

	public static Instance getInstance(Communicator communicator)
	{
		CommunicatorI p = (CommunicatorI)communicator;
		return p.getInstance();
	}

	public static ProtocolPluginFacade getProtocolPluginFacade(Communicator communicator)
	{
		return new ProtocolPluginFacadeI(communicator);
	}

	public static InputStream openResource(ClassLoader cl, String path)
		throws IOException
	{
		InputStream stream = cl.getResourceAsStream(path);
		if (stream == null)
			try
			{
				File f = new File(path);
				if (f.exists())
					stream = new FileInputStream(f);
			}
			catch (SecurityException ex) { }
		return stream;
	}

	public static Class findClass(String className, ClassLoader cl)
		throws LinkageError
	{
		Class c = null;
		if (cl != null)
			c = loadClass(className, cl);
		if (c == null)
			try
			{
				cl = ClassLoader.getSystemClassLoader();
				if (cl != null)
					c = loadClass(className, cl);
			}
			catch (SecurityException ex) { }
		if (c == null)
			try
			{
				cl = Thread.currentThread().getContextClassLoader();
				if (cl != null)
					c = loadClass(className, cl);
			}
			catch (SecurityException ex) { }
		try
		{
			if (c == null)
				c = Class.forName(className);
		}
		catch (ClassNotFoundException ex) { }
		return c;
	}

	private static Class loadClass(String className, ClassLoader cl)
		throws LinkageError
	{
		if (cl == null)
			break MISSING_BLOCK_LABEL_11;
		return cl.loadClass(className);
		ClassNotFoundException ex;
		ex;
		return null;
	}

	public static int getThreadPriorityProperty(Properties properties, String prefix)
	{
		String pri;
		pri = properties.getProperty((new StringBuilder()).append(prefix).append(".ThreadPriority").toString());
		if (pri.equals("MIN_PRIORITY") || pri.equals("java.lang.Thread.MIN_PRIORITY"))
			return 1;
		if (pri.equals("NORM_PRIORITY") || pri.equals("java.lang.Thread.NORM_PRIORITY"))
			return 5;
		if (pri.equals("MAX_PRIORITY") || pri.equals("java.lang.Thread.MAX_PRIORITY"))
			return 10;
		return Integer.parseInt(pri);
		NumberFormatException ex;
		ex;
		return 5;
	}
}
