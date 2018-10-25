// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AbstractDynamicMBean.java

package org.apache.log4j.jmx;

import java.util.*;
import javax.management.*;
import org.apache.log4j.Appender;
import org.apache.log4j.Logger;

public abstract class AbstractDynamicMBean
	implements DynamicMBean, MBeanRegistration
{

	String dClassName;
	MBeanServer server;
	private final Vector mbeanList = new Vector();

	public AbstractDynamicMBean()
	{
	}

	protected static String getAppenderName(Appender appender)
	{
		String name = appender.getName();
		if (name == null || name.trim().length() == 0)
			name = appender.toString();
		return name;
	}

	public AttributeList getAttributes(String attributeNames[])
	{
		if (attributeNames == null)
			throw new RuntimeOperationsException(new IllegalArgumentException("attributeNames[] cannot be null"), "Cannot invoke a getter of " + dClassName);
		AttributeList resultList = new AttributeList();
		if (attributeNames.length == 0)
			return resultList;
		for (int i = 0; i < attributeNames.length; i++)
			try
			{
				Object value = getAttribute(attributeNames[i]);
				resultList.add(new Attribute(attributeNames[i], value));
			}
			catch (JMException e)
			{
				e.printStackTrace();
			}
			catch (RuntimeException e)
			{
				e.printStackTrace();
			}

		return resultList;
	}

	public AttributeList setAttributes(AttributeList attributes)
	{
		if (attributes == null)
			throw new RuntimeOperationsException(new IllegalArgumentException("AttributeList attributes cannot be null"), "Cannot invoke a setter of " + dClassName);
		AttributeList resultList = new AttributeList();
		if (attributes.isEmpty())
			return resultList;
		for (Iterator i = attributes.iterator(); i.hasNext();)
		{
			Attribute attr = (Attribute)i.next();
			try
			{
				setAttribute(attr);
				String name = attr.getName();
				Object value = getAttribute(name);
				resultList.add(new Attribute(name, value));
			}
			catch (JMException e)
			{
				e.printStackTrace();
			}
			catch (RuntimeException e)
			{
				e.printStackTrace();
			}
		}

		return resultList;
	}

	protected abstract Logger getLogger();

	public void postDeregister()
	{
		getLogger().debug("postDeregister is called.");
	}

	public void postRegister(Boolean boolean1)
	{
	}

	public ObjectName preRegister(MBeanServer server, ObjectName name)
	{
		getLogger().debug("preRegister called. Server=" + server + ", name=" + name);
		this.server = server;
		return name;
	}

	protected void registerMBean(Object mbean, ObjectName objectName)
		throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException
	{
		server.registerMBean(mbean, objectName);
		mbeanList.add(objectName);
	}

	public void preDeregister()
	{
		getLogger().debug("preDeregister called.");
		for (Enumeration iterator = mbeanList.elements(); iterator.hasMoreElements();)
		{
			ObjectName name = (ObjectName)iterator.nextElement();
			try
			{
				server.unregisterMBean(name);
			}
			catch (InstanceNotFoundException e)
			{
				getLogger().warn("Missing MBean " + name.getCanonicalName());
			}
			catch (MBeanRegistrationException e)
			{
				getLogger().warn("Failed unregistering " + name.getCanonicalName());
			}
		}

	}
}
