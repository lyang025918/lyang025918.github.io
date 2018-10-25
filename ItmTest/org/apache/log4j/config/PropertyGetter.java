// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PropertyGetter.java

package org.apache.log4j.config;

import java.beans.*;
import java.io.InterruptedIOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.apache.log4j.helpers.LogLog;

public class PropertyGetter
{
	public static interface PropertyCallback
	{

		public abstract void foundProperty(Object obj1, String s, String s1, Object obj2);
	}


	protected static final Object NULL_ARG[] = new Object[0];
	protected Object obj;
	protected PropertyDescriptor props[];

	public PropertyGetter(Object obj)
		throws IntrospectionException
	{
		BeanInfo bi = Introspector.getBeanInfo(obj.getClass());
		props = bi.getPropertyDescriptors();
		this.obj = obj;
	}

	public static void getProperties(Object obj, PropertyCallback callback, String prefix)
	{
		try
		{
			(new PropertyGetter(obj)).getProperties(callback, prefix);
		}
		catch (IntrospectionException ex)
		{
			LogLog.error("Failed to introspect object " + obj, ex);
		}
	}

	public void getProperties(PropertyCallback callback, String prefix)
	{
		for (int i = 0; i < props.length; i++)
		{
			Method getter = props[i].getReadMethod();
			if (getter == null || !isHandledType(getter.getReturnType()))
				continue;
			String name = props[i].getName();
			try
			{
				Object result = getter.invoke(obj, NULL_ARG);
				if (result != null)
					callback.foundProperty(obj, prefix, name, result);
				continue;
			}
			catch (IllegalAccessException ex)
			{
				LogLog.warn("Failed to get value of property " + name);
				continue;
			}
			catch (InvocationTargetException ex)
			{
				if ((ex.getTargetException() instanceof InterruptedException) || (ex.getTargetException() instanceof InterruptedIOException))
					Thread.currentThread().interrupt();
				LogLog.warn("Failed to get value of property " + name);
			}
			catch (RuntimeException ex)
			{
				LogLog.warn("Failed to get value of property " + name);
			}
		}

	}

	protected boolean isHandledType(Class type)
	{
		return (java.lang.String.class).isAssignableFrom(type) || (Integer.TYPE.isAssignableFrom(type) || Long.TYPE.isAssignableFrom(type) || Boolean.TYPE.isAssignableFrom(type)) || (org.apache.log4j.Priority.class).isAssignableFrom(type);
	}

}
