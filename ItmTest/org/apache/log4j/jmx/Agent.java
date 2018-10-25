// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Agent.java

package org.apache.log4j.jmx;

import java.io.InterruptedIOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.management.*;
import org.apache.log4j.Logger;

// Referenced classes of package org.apache.log4j.jmx:
//			HierarchyDynamicMBean

/**
 * @deprecated Class Agent is deprecated
 */

public class Agent
{

	/**
	 * @deprecated Field log is deprecated
	 */
	static Logger log;

	/**
	 * @deprecated Method Agent is deprecated
	 */

	public Agent()
	{
	}

	private static Object createServer()
	{
		Object newInstance = null;
		try
		{
			newInstance = Class.forName("com.sun.jdmk.comm.HtmlAdapterServer").newInstance();
		}
		catch (ClassNotFoundException ex)
		{
			throw new RuntimeException(ex.toString());
		}
		catch (InstantiationException ex)
		{
			throw new RuntimeException(ex.toString());
		}
		catch (IllegalAccessException ex)
		{
			throw new RuntimeException(ex.toString());
		}
		return newInstance;
	}

	private static void startServer(Object server)
	{
		try
		{
			server.getClass().getMethod("start", new Class[0]).invoke(server, new Object[0]);
		}
		catch (InvocationTargetException ex)
		{
			Throwable cause = ex.getTargetException();
			if (cause instanceof RuntimeException)
				throw (RuntimeException)cause;
			if (cause != null)
			{
				if ((cause instanceof InterruptedException) || (cause instanceof InterruptedIOException))
					Thread.currentThread().interrupt();
				throw new RuntimeException(cause.toString());
			} else
			{
				throw new RuntimeException();
			}
		}
		catch (NoSuchMethodException ex)
		{
			throw new RuntimeException(ex.toString());
		}
		catch (IllegalAccessException ex)
		{
			throw new RuntimeException(ex.toString());
		}
	}

	/**
	 * @deprecated Method start is deprecated
	 */

	public void start()
	{
		MBeanServer server = MBeanServerFactory.createMBeanServer();
		Object html = createServer();
		try
		{
			log.info("Registering HtmlAdaptorServer instance.");
			server.registerMBean(html, new ObjectName("Adaptor:name=html,port=8082"));
			log.info("Registering HierarchyDynamicMBean instance.");
			HierarchyDynamicMBean hdm = new HierarchyDynamicMBean();
			server.registerMBean(hdm, new ObjectName("log4j:hiearchy=default"));
		}
		catch (JMException e)
		{
			log.error("Problem while registering MBeans instances.", e);
			return;
		}
		catch (RuntimeException e)
		{
			log.error("Problem while registering MBeans instances.", e);
			return;
		}
		startServer(html);
	}

	static Class class$(String x0)
	{
		return Class.forName(x0);
		ClassNotFoundException x1;
		x1;
		throw (new NoClassDefFoundError()).initCause(x1);
	}

	static 
	{
		log = Logger.getLogger(org.apache.log4j.jmx.Agent.class);
	}
}
