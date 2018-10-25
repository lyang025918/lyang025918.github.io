// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ServiceInstanceDescriptor.java

package IceGrid;

import Ice.Object;
import IceInternal.BasicStream;
import IceInternal.Ex;
import java.io.Serializable;
import java.util.Map;

// Referenced classes of package IceGrid:
//			PropertySetDescriptor, StringStringDictHelper, ServiceDescriptor

public class ServiceInstanceDescriptor
	implements Cloneable, Serializable
{
	private class Patcher
		implements IceInternal.Patcher
	{

		final ServiceInstanceDescriptor this$0;

		public void patch(Ice.Object v)
		{
			try
			{
				descriptor = (ServiceDescriptor)v;
			}
			catch (ClassCastException ex)
			{
				Ex.throwUOE(type(), v.ice_id());
			}
		}

		public String type()
		{
			return "::IceGrid::ServiceDescriptor";
		}

		private Patcher()
		{
			this$0 = ServiceInstanceDescriptor.this;
			super();
		}

	}


	public String template;
	public Map parameterValues;
	public ServiceDescriptor descriptor;
	public PropertySetDescriptor propertySet;
	static final boolean $assertionsDisabled = !IceGrid/ServiceInstanceDescriptor.desiredAssertionStatus();

	public ServiceInstanceDescriptor()
	{
	}

	public ServiceInstanceDescriptor(String template, Map parameterValues, ServiceDescriptor descriptor, PropertySetDescriptor propertySet)
	{
		this.template = template;
		this.parameterValues = parameterValues;
		this.descriptor = descriptor;
		this.propertySet = propertySet;
	}

	public boolean equals(Object rhs)
	{
		if (this == rhs)
			return true;
		ServiceInstanceDescriptor _r = null;
		try
		{
			_r = (ServiceInstanceDescriptor)rhs;
		}
		catch (ClassCastException ex) { }
		if (_r != null)
		{
			if (template != _r.template && (template == null || _r.template == null || !template.equals(_r.template)))
				return false;
			if (parameterValues != _r.parameterValues && (parameterValues == null || _r.parameterValues == null || !parameterValues.equals(_r.parameterValues)))
				return false;
			if (descriptor != _r.descriptor && (descriptor == null || _r.descriptor == null || !descriptor.equals(_r.descriptor)))
				return false;
			return propertySet == _r.propertySet || propertySet != null && _r.propertySet != null && propertySet.equals(_r.propertySet);
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
		if (descriptor != null)
			__h = 5 * __h + descriptor.hashCode();
		if (propertySet != null)
			__h = 5 * __h + propertySet.hashCode();
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
		__os.writeObject(descriptor);
		propertySet.__write(__os);
	}

	public void __read(BasicStream __is)
	{
		template = __is.readString();
		parameterValues = StringStringDictHelper.read(__is);
		__is.readObject(new Patcher());
		propertySet = new PropertySetDescriptor();
		propertySet.__read(__is);
	}

}
