// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ZeroConfSupport.java

package org.apache.log4j.net;

import java.lang.reflect.*;
import java.util.*;
import org.apache.log4j.helpers.LogLog;

public class ZeroConfSupport
{

	private static Object jmDNS = initializeJMDNS();
	Object serviceInfo;
	private static Class jmDNSClass;
	private static Class serviceInfoClass;

	public ZeroConfSupport(String zone, int port, String name, Map properties)
	{
		boolean isVersion3 = false;
		try
		{
			jmDNSClass.getMethod("create", null);
			isVersion3 = true;
		}
		catch (NoSuchMethodException e) { }
		if (isVersion3)
		{
			LogLog.debug("using JmDNS version 3 to construct serviceInfo instance");
			serviceInfo = buildServiceInfoVersion3(zone, port, name, properties);
		} else
		{
			LogLog.debug("using JmDNS version 1.0 to construct serviceInfo instance");
			serviceInfo = buildServiceInfoVersion1(zone, port, name, properties);
		}
	}

	public ZeroConfSupport(String zone, int port, String name)
	{
		this(zone, port, name, ((Map) (new HashMap())));
	}

	private static Object createJmDNSVersion1()
	{
		return jmDNSClass.newInstance();
		InstantiationException e;
		e;
		LogLog.warn("Unable to instantiate JMDNS", e);
		break MISSING_BLOCK_LABEL_24;
		e;
		LogLog.warn("Unable to instantiate JMDNS", e);
		return null;
	}

	private static Object createJmDNSVersion3()
	{
		Method jmDNSCreateMethod = jmDNSClass.getMethod("create", null);
		return jmDNSCreateMethod.invoke(null, null);
		IllegalAccessException e;
		e;
		LogLog.warn("Unable to instantiate jmdns class", e);
		break MISSING_BLOCK_LABEL_44;
		e;
		LogLog.warn("Unable to access constructor", e);
		break MISSING_BLOCK_LABEL_44;
		e;
		LogLog.warn("Unable to call constructor", e);
		return null;
	}

	private Object buildServiceInfoVersion1(String zone, int port, String name, Map properties)
	{
		Hashtable hashtableProperties = new Hashtable(properties);
		Object result;
		Class args[] = new Class[6];
		args[0] = java.lang.String.class;
		args[1] = java.lang.String.class;
		args[2] = Integer.TYPE;
		args[3] = Integer.TYPE;
		args[4] = Integer.TYPE;
		args[5] = java.util.Hashtable.class;
		Constructor constructor = serviceInfoClass.getConstructor(args);
		Object values[] = new Object[6];
		values[0] = zone;
		values[1] = name;
		values[2] = new Integer(port);
		values[3] = new Integer(0);
		values[4] = new Integer(0);
		values[5] = hashtableProperties;
		result = constructor.newInstance(values);
		LogLog.debug("created serviceinfo: " + result);
		return result;
		IllegalAccessException e;
		e;
		LogLog.warn("Unable to construct ServiceInfo instance", e);
		break MISSING_BLOCK_LABEL_263;
		e;
		LogLog.warn("Unable to get ServiceInfo constructor", e);
		break MISSING_BLOCK_LABEL_263;
		e;
		LogLog.warn("Unable to construct ServiceInfo instance", e);
		break MISSING_BLOCK_LABEL_263;
		e;
		LogLog.warn("Unable to construct ServiceInfo instance", e);
		return null;
	}

	private Object buildServiceInfoVersion3(String zone, int port, String name, Map properties)
	{
		Object result;
		Class args[] = new Class[6];
		args[0] = java.lang.String.class;
		args[1] = java.lang.String.class;
		args[2] = Integer.TYPE;
		args[3] = Integer.TYPE;
		args[4] = Integer.TYPE;
		args[5] = java.util.Map.class;
		Method serviceInfoCreateMethod = serviceInfoClass.getMethod("create", args);
		Object values[] = new Object[6];
		values[0] = zone;
		values[1] = name;
		values[2] = new Integer(port);
		values[3] = new Integer(0);
		values[4] = new Integer(0);
		values[5] = properties;
		result = serviceInfoCreateMethod.invoke(null, values);
		LogLog.debug("created serviceinfo: " + result);
		return result;
		IllegalAccessException e;
		e;
		LogLog.warn("Unable to invoke create method", e);
		break MISSING_BLOCK_LABEL_243;
		e;
		LogLog.warn("Unable to find create method", e);
		break MISSING_BLOCK_LABEL_243;
		e;
		LogLog.warn("Unable to invoke create method", e);
		return null;
	}

	public void advertise()
	{
		try
		{
			Method method = jmDNSClass.getMethod("registerService", new Class[] {
				serviceInfoClass
			});
			method.invoke(jmDNS, new Object[] {
				serviceInfo
			});
			LogLog.debug("registered serviceInfo: " + serviceInfo);
		}
		catch (IllegalAccessException e)
		{
			LogLog.warn("Unable to invoke registerService method", e);
		}
		catch (NoSuchMethodException e)
		{
			LogLog.warn("No registerService method", e);
		}
		catch (InvocationTargetException e)
		{
			LogLog.warn("Unable to invoke registerService method", e);
		}
	}

	public void unadvertise()
	{
		try
		{
			Method method = jmDNSClass.getMethod("unregisterService", new Class[] {
				serviceInfoClass
			});
			method.invoke(jmDNS, new Object[] {
				serviceInfo
			});
			LogLog.debug("unregistered serviceInfo: " + serviceInfo);
		}
		catch (IllegalAccessException e)
		{
			LogLog.warn("Unable to invoke unregisterService method", e);
		}
		catch (NoSuchMethodException e)
		{
			LogLog.warn("No unregisterService method", e);
		}
		catch (InvocationTargetException e)
		{
			LogLog.warn("Unable to invoke unregisterService method", e);
		}
	}

	private static Object initializeJMDNS()
	{
		try
		{
			jmDNSClass = Class.forName("javax.jmdns.JmDNS");
			serviceInfoClass = Class.forName("javax.jmdns.ServiceInfo");
		}
		catch (ClassNotFoundException e)
		{
			LogLog.warn("JmDNS or serviceInfo class not found", e);
		}
		boolean isVersion3 = false;
		try
		{
			jmDNSClass.getMethod("create", null);
			isVersion3 = true;
		}
		catch (NoSuchMethodException e) { }
		if (isVersion3)
			return createJmDNSVersion3();
		else
			return createJmDNSVersion1();
	}

	public static Object getJMDNSInstance()
	{
		return jmDNS;
	}

}
