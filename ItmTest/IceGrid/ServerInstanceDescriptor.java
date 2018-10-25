// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ServerInstanceDescriptor.java

package IceGrid;

import IceInternal.BasicStream;
import java.io.Serializable;
import java.util.Map;

// Referenced classes of package IceGrid:
//			PropertySetDescriptor, StringStringDictHelper, PropertySetDescriptorDictHelper

public class ServerInstanceDescriptor
	implements Cloneable, Serializable
{

	public String template;
	public Map parameterValues;
	public PropertySetDescriptor propertySet;
	public Map servicePropertySets;
	static final boolean $assertionsDisabled = !IceGrid/ServerInstanceDescriptor.desiredAssertionStatus();

	public ServerInstanceDescriptor()
	{
	}

	public ServerInstanceDescriptor(String template, Map parameterValues, PropertySetDescriptor propertySet, Map servicePropertySets)
	{
		this.template = template;
		this.parameterValues = parameterValues;
		this.propertySet = propertySet;
		this.servicePropertySets = servicePropertySets;
	}

	public boolean equals(Object rhs)
	{
		if (this == rhs)
			return true;
		ServerInstanceDescriptor _r = null;
		try
		{
			_r = (ServerInstanceDescriptor)rhs;
		}
		catch (ClassCastException ex) { }
		if (_r != null)
		{
			if (template != _r.template && (template == null || _r.template == null || !template.equals(_r.template)))
				return false;
			if (parameterValues != _r.parameterValues && (parameterValues == null || _r.parameterValues == null || !parameterValues.equals(_r.parameterValues)))
				return false;
			if (propertySet != _r.propertySet && (propertySet == null || _r.propertySet == null || !propertySet.equals(_r.propertySet)))
				return false;
			return servicePropertySets == _r.servicePropertySets || servicePropertySets != null && _r.servicePropertySets != null && servicePropertySets.equals(_r.servicePropertySets);
		} else
		{
			return false;
		}
	}

	public int hashCode()
	{
		int __h = 0;
		if (template != null)
			__h = 5 * __h + template.hashCode();
		if (parameterValues != null)
			__h = 5 * __h + parameterValues.hashCode();
		if (propertySet != null)
			__h = 5 * __h + propertySet.hashCode();
		if (servicePropertySets != null)
			__h = 5 * __h + servicePropertySets.hashCode();
		return __h;
	}

	public Object clone()
	{
		Object o = null;
		try
		{
			o = super.clone();
		}
		catch (CloneNotSupportedException ex)
		{
			if (!$assertionsDisabled)
				throw new AssertionError();
		}
		return o;
	}

	public void __write(BasicStream __os)
	{
		__os.writeString(template);
		StringStringDictHelper.write(__os, parameterValues);
		propertySet.__write(__os);
		PropertySetDescriptorDictHelper.write(__os, servicePropertySets);
	}

	public void __read(BasicStream __is)
	{
		template = __is.readString();
		parameterValues = StringStringDictHelper.read(__is);
		propertySet = new PropertySetDescriptor();
		propertySet.__read(__is);
		servicePropertySets = PropertySetDescriptorDictHelper.read(__is);
	}

}
