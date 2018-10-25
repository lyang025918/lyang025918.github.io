// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PropertySetter.java

package org.apache.log4j.config;

import java.beans.*;
import java.io.InterruptedIOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Properties;
import org.apache.log4j.Appender;
import org.apache.log4j.Level;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.helpers.OptionConverter;
import org.apache.log4j.spi.OptionHandler;

// Referenced classes of package org.apache.log4j.config:
//			PropertySetterException

public class PropertySetter
{

	protected Object obj;
	protected PropertyDescriptor props[];

	public PropertySetter(Object obj)
	{
		this.obj = obj;
	}

	protected void introspect()
	{
		try
		{
			BeanInfo bi = Introspector.getBeanInfo(obj.getClass());
			props = bi.getPropertyDescriptors();
		}
		catch (IntrospectionException ex)
		{
			LogLog.error("Failed to introspect " + obj + ": " + ex.getMessage());
			props = new PropertyDescriptor[0];
		}
	}

	public static void setProperties(Object obj, Properties properties, String prefix)
	{
		(new PropertySetter(obj)).setProperties(properties, prefix);
	}

	public void setProperties(Properties properties, String prefix)
	{
		int len = prefix.length();
		Enumeration e = properties.propertyNames();
		do
		{
			if (!e.hasMoreElements())
				break;
			String key = (String)e.nextElement();
			if (key.startsWith(prefix) && key.indexOf('.', len + 1) <= 0)
			{
				String value = OptionConverter.findAndSubst(key, properties);
				key = key.substring(len);
				if (!"layout".equals(key) && !"errorhandler".equals(key) || !(obj instanceof Appender))
				{
					PropertyDescriptor prop = getPropertyDescriptor(Introspector.decapitalize(key));
					if (prop != null && (org.apache.log4j.spi.OptionHandler.class).isAssignableFrom(prop.getPropertyType()) && prop.getWriteMethod() != null)
					{
						OptionHandler opt = (OptionHandler)OptionConverter.instantiateByKey(properties, prefix + key, prop.getPropertyType(), null);
						PropertySetter setter = new PropertySetter(opt);
						setter.setProperties(properties, prefix + key + ".");
						try
						{
							prop.getWriteMethod().invoke(obj, new Object[] {
								opt
							});
						}
						catch (IllegalAccessException ex)
						{
							LogLog.warn("Failed to set property [" + key + "] to value \"" + value + "\". ", ex);
						}
						catch (InvocationTargetException ex)
						{
							if ((ex.getTargetException() instanceof InterruptedException) || (ex.getTargetException() instanceof InterruptedIOException))
								Thread.currentThread().interrupt();
							LogLog.warn("Failed to set property [" + key + "] to value \"" + value + "\". ", ex);
						}
						catch (RuntimeException ex)
						{
							LogLog.warn("Failed to set property [" + key + "] to value \"" + value + "\". ", ex);
						}
					} else
					{
						setProperty(key, value);
					}
				}
			}
		} while (true);
		activate();
	}

	public void setProperty(String name, String value)
	{
		if (value == null)
			return;
		name = Introspector.decapitalize(name);
		PropertyDescriptor prop = getPropertyDescriptor(name);
		if (prop == null)
			LogLog.warn("No such property [" + name + "] in " + obj.getClass().getName() + ".");
		else
			try
			{
				setProperty(prop, name, value);
			}
			catch (PropertySetterException ex)
			{
				LogLog.warn("Failed to set property [" + name + "] to value \"" + value + "\". ", ex.rootCause);
			}
	}

	public void setProperty(PropertyDescriptor prop, String name, String value)
		throws PropertySetterException
	{
		Method setter = prop.getWriteMethod();
		if (setter == null)
			throw new PropertySetterException("No setter for property [" + name + "].");
		Class paramTypes[] = setter.getParameterTypes();
		if (paramTypes.length != 1)
			throw new PropertySetterException("#params for setter != 1");
		Object arg;
		try
		{
			arg = convertArg(value, paramTypes[0]);
		}
		catch (Throwable t)
		{
			throw new PropertySetterException("Conversion to type [" + paramTypes[0] + "] failed. Reason: " + t);
		}
		if (arg == null)
			throw new PropertySetterException("Conversion to type [" + paramTypes[0] + "] failed.");
		LogLog.debug("Setting property [" + name + "] to [" + arg + "].");
		try
		{
			setter.invoke(obj, new Object[] {
				arg
			});
		}
		catch (IllegalAccessException ex)
		{
			throw new PropertySetterException(ex);
		}
		catch (InvocationTargetException ex)
		{
			if ((ex.getTargetException() instanceof InterruptedException) || (ex.getTargetException() instanceof InterruptedIOException))
				Thread.currentThread().interrupt();
			throw new PropertySetterException(ex);
		}
		catch (RuntimeException ex)
		{
			throw new PropertySetterException(ex);
		}
	}

	protected Object convertArg(String val, Class type)
	{
		if (val == null)
			return null;
		String v = val.trim();
		if ((java.lang.String.class).isAssignableFrom(type))
			return val;
		if (Integer.TYPE.isAssignableFrom(type))
			return new Integer(v);
		if (Long.TYPE.isAssignableFrom(type))
			return new Long(v);
		if (Boolean.TYPE.isAssignableFrom(type))
		{
			if ("true".equalsIgnoreCase(v))
				return Boolean.TRUE;
			if ("false".equalsIgnoreCase(v))
				return Boolean.FALSE;
		} else
		{
			if ((org.apache.log4j.Priority.class).isAssignableFrom(type))
				return OptionConverter.toLevel(v, Level.DEBUG);
			if ((org.apache.log4j.spi.ErrorHandler.class).isAssignableFrom(type))
				return OptionConverter.instantiateByClassName(v, org.apache.log4j.spi.ErrorHandler.class, null);
		}
		return null;
	}

	protected PropertyDescriptor getPropertyDescriptor(String name)
	{
		if (props == null)
			introspect();
		for (int i = 0; i < props.length; i++)
			if (name.equals(props[i].getName()))
				return props[i];

		return null;
	}

	public void activate()
	{
		if (obj instanceof OptionHandler)
			((OptionHandler)obj).activateOptions();
	}
}
