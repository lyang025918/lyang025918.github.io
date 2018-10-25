// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LayoutDynamicMBean.java

package org.apache.log4j.jmx;

import java.beans.*;
import java.io.InterruptedIOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Vector;
import javax.management.*;
import org.apache.log4j.*;
import org.apache.log4j.helpers.OptionConverter;
import org.apache.log4j.spi.OptionHandler;

// Referenced classes of package org.apache.log4j.jmx:
//			AbstractDynamicMBean, MethodUnion

public class LayoutDynamicMBean extends AbstractDynamicMBean
{

	private MBeanConstructorInfo dConstructors[];
	private Vector dAttributes;
	private String dClassName;
	private Hashtable dynamicProps;
	private MBeanOperationInfo dOperations[];
	private String dDescription;
	private static Logger cat;
	private Layout layout;

	public LayoutDynamicMBean(Layout layout)
		throws IntrospectionException
	{
		dConstructors = new MBeanConstructorInfo[1];
		dAttributes = new Vector();
		dClassName = getClass().getName();
		dynamicProps = new Hashtable(5);
		dOperations = new MBeanOperationInfo[1];
		dDescription = "This MBean acts as a management facade for log4j layouts.";
		this.layout = layout;
		buildDynamicMBeanInfo();
	}

	private void buildDynamicMBeanInfo()
		throws IntrospectionException
	{
		java.lang.reflect.Constructor constructors[] = getClass().getConstructors();
		dConstructors[0] = new MBeanConstructorInfo("LayoutDynamicMBean(): Constructs a LayoutDynamicMBean instance", constructors[0]);
		BeanInfo bi = Introspector.getBeanInfo(layout.getClass());
		PropertyDescriptor pd[] = bi.getPropertyDescriptors();
		int size = pd.length;
		for (int i = 0; i < size; i++)
		{
			String name = pd[i].getName();
			Method readMethod = pd[i].getReadMethod();
			Method writeMethod = pd[i].getWriteMethod();
			if (readMethod == null)
				continue;
			Class returnClass = readMethod.getReturnType();
			if (!isSupportedType(returnClass))
				continue;
			String returnClassName;
			if (returnClass.isAssignableFrom(org.apache.log4j.Level.class))
				returnClassName = "java.lang.String";
			else
				returnClassName = returnClass.getName();
			dAttributes.add(new MBeanAttributeInfo(name, returnClassName, "Dynamic", true, writeMethod != null, false));
			dynamicProps.put(name, new MethodUnion(readMethod, writeMethod));
		}

		MBeanParameterInfo params[] = new MBeanParameterInfo[0];
		dOperations[0] = new MBeanOperationInfo("activateOptions", "activateOptions(): add an layout", params, "void", 1);
	}

	private boolean isSupportedType(Class clazz)
	{
		if (clazz.isPrimitive())
			return true;
		if (clazz == (java.lang.String.class))
			return true;
		return clazz.isAssignableFrom(org.apache.log4j.Level.class);
	}

	public MBeanInfo getMBeanInfo()
	{
		cat.debug("getMBeanInfo called.");
		MBeanAttributeInfo attribs[] = new MBeanAttributeInfo[dAttributes.size()];
		dAttributes.toArray(attribs);
		return new MBeanInfo(dClassName, dDescription, attribs, dConstructors, dOperations, new MBeanNotificationInfo[0]);
	}

	public Object invoke(String operationName, Object params[], String signature[])
		throws MBeanException, ReflectionException
	{
		if (operationName.equals("activateOptions") && (layout instanceof OptionHandler))
		{
			OptionHandler oh = layout;
			oh.activateOptions();
			return "Options activated.";
		} else
		{
			return null;
		}
	}

	protected Logger getLogger()
	{
		return cat;
	}

	public Object getAttribute(String attributeName)
		throws AttributeNotFoundException, MBeanException, ReflectionException
	{
		MethodUnion mu;
		if (attributeName == null)
			throw new RuntimeOperationsException(new IllegalArgumentException("Attribute name cannot be null"), "Cannot invoke a getter of " + dClassName + " with null attribute name");
		mu = (MethodUnion)dynamicProps.get(attributeName);
		cat.debug("----name=" + attributeName + ", mu=" + mu);
		if (mu == null || mu.readMethod == null)
			break MISSING_BLOCK_LABEL_153;
		return mu.readMethod.invoke(layout, null);
		InvocationTargetException e;
		e;
		if ((e.getTargetException() instanceof InterruptedException) || (e.getTargetException() instanceof InterruptedIOException))
			Thread.currentThread().interrupt();
		return null;
		e;
		return null;
		e;
		return null;
		throw new AttributeNotFoundException("Cannot find " + attributeName + " attribute in " + dClassName);
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
		MethodUnion mu = (MethodUnion)dynamicProps.get(name);
		if (mu != null && mu.writeMethod != null)
		{
			Object o[] = new Object[1];
			Class params[] = mu.writeMethod.getParameterTypes();
			if (params[0] == (org.apache.log4j.Priority.class))
				value = OptionConverter.toLevel((String)value, (Level)getAttribute(name));
			o[0] = value;
			try
			{
				mu.writeMethod.invoke(layout, o);
			}
			catch (InvocationTargetException e)
			{
				if ((e.getTargetException() instanceof InterruptedException) || (e.getTargetException() instanceof InterruptedIOException))
					Thread.currentThread().interrupt();
				cat.error("FIXME", e);
			}
			catch (IllegalAccessException e)
			{
				cat.error("FIXME", e);
			}
			catch (RuntimeException e)
			{
				cat.error("FIXME", e);
			}
		} else
		{
			throw new AttributeNotFoundException("Attribute " + name + " not found in " + getClass().getName());
		}
	}

	static 
	{
		cat = Logger.getLogger(org.apache.log4j.jmx.LayoutDynamicMBean.class);
	}
}
