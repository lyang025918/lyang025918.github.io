// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TemplateDescriptor.java

package IceGrid;

import Ice.Object;
import IceInternal.BasicStream;
import IceInternal.Ex;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

// Referenced classes of package IceGrid:
//			StringStringDictHelper, CommunicatorDescriptor

public class TemplateDescriptor
	implements Cloneable, Serializable
{
	private class Patcher
		implements IceInternal.Patcher
	{

		final TemplateDescriptor this$0;

		public void patch(Ice.Object v)
		{
			try
			{
				descriptor = (CommunicatorDescriptor)v;
			}
			catch (ClassCastException ex)
			{
				Ex.throwUOE(type(), v.ice_id());
			}
		}

		public String type()
		{
			return "::IceGrid::CommunicatorDescriptor";
		}

		private Patcher()
		{
			this$0 = TemplateDescriptor.this;
			super();
		}

	}


	public CommunicatorDescriptor descriptor;
	public List parameters;
	public Map parameterDefaults;
	static final boolean $assertionsDisabled = !IceGrid/TemplateDescriptor.desiredAssertionStatus();

	public TemplateDescriptor()
	{
	}

	public TemplateDescriptor(CommunicatorDescriptor descriptor, List parameters, Map parameterDefaults)
	{
		this.descriptor = descriptor;
		this.parameters = parameters;
		this.parameterDefaults = parameterDefaults;
	}

	public boolean equals(Object rhs)
	{
		if (this == rhs)
			return true;
		TemplateDescriptor _r = null;
		try
		{
			_r = (TemplateDescriptor)rhs;
		}
		catch (ClassCastException ex) { }
		if (_r != null)
		{
			if (descriptor != _r.descriptor && (descriptor == null || _r.descriptor == null || !descriptor.equals(_r.descriptor)))
				return false;
			if (parameters != _r.parameters && (parameters == null || _r.parameters == null || !parameters.equals(_r.parameters)))
				return false;
			return parameterDefaults == _r.parameterDefaults || parameterDefaults != null && _r.parameterDefaults != null && parameterDefaults.equals(_r.parameterDefaults);
		} else
		{
			return false;
		}
	}

	public int hashCode()
	{
		int __h = 0;
		if (descriptor != null)
			__h = 5 * __h + descriptor.hashCode();
		if (parameters != null)
			__h = 5 * __h + parameters.hashCode();
		if (parameterDefaults != null)
			__h = 5 * __h + parameterDefaults.hashCode();
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
		__os.writeObject(descriptor);
		if (parameters == null)
		{
			__os.writeSize(0);
		} else
		{
			__os.writeSize(parameters.size());
			String __elem;
			for (Iterator i$ = parameters.iterator(); i$.hasNext(); __os.writeString(__elem))
				__elem = (String)i$.next();

		}
		StringStringDictHelper.write(__os, parameterDefaults);
	}

	public void __read(BasicStream __is)
	{
		__is.readObject(new Patcher());
		parameters = new LinkedList();
		int __len0 = __is.readAndCheckSeqSize(1);
		for (int __i0 = 0; __i0 < __len0; __i0++)
		{
			String __elem = __is.readString();
			parameters.add(__elem);
		}

		parameterDefaults = StringStringDictHelper.read(__is);
	}

}
