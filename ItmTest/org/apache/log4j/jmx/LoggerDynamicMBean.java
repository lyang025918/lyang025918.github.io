// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LoggerDynamicMBean.java

package org.apache.log4j.jmx;

import java.beans.IntrospectionException;
import java.util.Enumeration;
import java.util.Vector;
import javax.management.*;
import org.apache.log4j.*;
import org.apache.log4j.helpers.OptionConverter;

// Referenced classes of package org.apache.log4j.jmx:
//			AbstractDynamicMBean, AppenderDynamicMBean

public class LoggerDynamicMBean extends AbstractDynamicMBean
	implements NotificationListener
{

	private MBeanConstructorInfo dConstructors[];
	private MBeanOperationInfo dOperations[];
	private Vector dAttributes;
	private String dClassName;
	private String dDescription;
	private static Logger cat;
	private Logger logger;

	public LoggerDynamicMBean(Logger logger)
	{
		dConstructors = new MBeanConstructorInfo[1];
		dOperations = new MBeanOperationInfo[1];
		dAttributes = new Vector();
		dClassName = getClass().getName();
		dDescription = "This MBean acts as a management facade for a org.apache.log4j.Logger instance.";
		this.logger = logger;
		buildDynamicMBeanInfo();
	}

	public void handleNotification(Notification notification, Object handback)
	{
		cat.debug("Received notification: " + notification.getType());
		registerAppenderMBean((Appender)notification.getUserData());
	}

	private void buildDynamicMBeanInfo()
	{
		java.lang.reflect.Constructor constructors[] = getClass().getConstructors();
		dConstructors[0] = new MBeanConstructorInfo("HierarchyDynamicMBean(): Constructs a HierarchyDynamicMBean instance", constructors[0]);
		dAttributes.add(new MBeanAttributeInfo("name", "java.lang.String", "The name of this Logger.", true, false, false));
		dAttributes.add(new MBeanAttributeInfo("priority", "java.lang.String", "The priority of this logger.", true, true, false));
		MBeanParameterInfo params[] = new MBeanParameterInfo[2];
		params[0] = new MBeanParameterInfo("class name", "java.lang.String", "add an appender to this logger");
		params[1] = new MBeanParameterInfo("appender name", "java.lang.String", "name of the appender");
		dOperations[0] = new MBeanOperationInfo("addAppender", "addAppender(): add an appender", params, "void", 1);
	}

	protected Logger getLogger()
	{
		return logger;
	}

	public MBeanInfo getMBeanInfo()
	{
		MBeanAttributeInfo attribs[] = new MBeanAttributeInfo[dAttributes.size()];
		dAttributes.toArray(attribs);
		MBeanInfo mb = new MBeanInfo(dClassName, dDescription, attribs, dConstructors, dOperations, new MBeanNotificationInfo[0]);
		return mb;
	}

	public Object invoke(String operationName, Object params[], String signature[])
		throws MBeanException, ReflectionException
	{
		if (operationName.equals("addAppender"))
		{
			addAppender((String)params[0], (String)params[1]);
			return "Hello world.";
		} else
		{
			return null;
		}
	}

	public Object getAttribute(String attributeName)
		throws AttributeNotFoundException, MBeanException, ReflectionException
	{
		if (attributeName == null)
			throw new RuntimeOperationsException(new IllegalArgumentException("Attribute name cannot be null"), "Cannot invoke a getter of " + dClassName + " with null attribute name");
		if (attributeName.equals("name"))
			return logger.getName();
		if (attributeName.equals("priority"))
		{
			Level l = logger.getLevel();
			if (l == null)
				return null;
			else
				return l.toString();
		}
		if (!attributeName.startsWith("appender="))
			break MISSING_BLOCK_LABEL_184;
		return new ObjectName("log4j:" + attributeName);
		MalformedObjectNameException e;
		e;
		cat.error("Could not create ObjectName" + attributeName);
		break MISSING_BLOCK_LABEL_184;
		e;
		cat.error("Could not create ObjectName" + attributeName);
		throw new AttributeNotFoundException("Cannot find " + attributeName + " attribute in " + dClassName);
	}

	void addAppender(String appenderClass, String appenderName)
	{
		cat.debug("addAppender called with " + appenderClass + ", " + appenderName);
		Appender appender = (Appender)OptionConverter.instantiateByClassName(appenderClass, org.apache.log4j.Appender.class, null);
		appender.setName(appenderName);
		logger.addAppender(appender);
	}

	public void setAttribute(Attribute attribute)
		throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException
	{
		if (attribute == null)
			throw new RuntimeOperationsException(new IllegalArgumentException("Attribute cannot be null"), "Cannot invoke a setter of " + dClassName + " with null attribute");
		String name = attribute.getName();
		Object value = attribute.getValue();
		if (name == null)
			throw new RuntimeOperationsException(new IllegalArgumentException("Attribute name cannot be null"), "Cannot invoke the setter of " + dClassName + " with null attribute name");
		if (name.equals("priority"))
		{
			if (value instanceof String)
			{
				String s = (String)value;
				Level p = logger.getLevel();
				if (s.equalsIgnoreCase("NULL"))
					p = null;
				else
					p = OptionConverter.toLevel(s, p);
				logger.setLevel(p);
			}
		} else
		{
			throw new AttributeNotFoundException("Attribute " + name + " not found in " + getClass().getName());
		}
	}

	void appenderMBeanRegistration()
	{
		Appender appender;
		for (Enumeration enumeration = logger.getAllAppenders(); enumeration.hasMoreElements(); registerAppenderMBean(appender))
			appender = (Appender)enumeration.nextElement();

	}

	void registerAppenderMBean(Appender appender)
	{
		String name = getAppenderName(appender);
		cat.debug("Adding AppenderMBean for appender named " + name);
		ObjectName objectName = null;
		try
		{
			AppenderDynamicMBean appenderMBean = new AppenderDynamicMBean(appender);
			objectName = new ObjectName("log4j", "appender", name);
			if (!server.isRegistered(objectName))
			{
				registerMBean(appenderMBean, objectName);
				dAttributes.add(new MBeanAttributeInfo("appender=" + name, "javax.management.ObjectName", "The " + name + " appender.", true, true, false));
			}
		}
		catch (JMException e)
		{
			cat.error("Could not add appenderMBean for [" + name + "].", e);
		}
		catch (IntrospectionException e)
		{
			cat.error("Could not add appenderMBean for [" + name + "].", e);
		}
		catch (RuntimeException e)
		{
			cat.error("Could not add appenderMBean for [" + name + "].", e);
		}
	}

	public void postRegister(Boolean registrationDone)
	{
		appenderMBeanRegistration();
	}

	static 
	{
		cat = Logger.getLogger(org.apache.log4j.jmx.LoggerDynamicMBean.class);
	}
}
