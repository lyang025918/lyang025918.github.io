// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PropertyDescriptor.java

package IceGrid;

import IceInternal.BasicStream;
import java.io.Serializable;

public class PropertyDescriptor
	implements Cloneable, Serializable
{

	public String name;
	public String value;
	static final boolean $assertionsDisabled = !IceGrid/PropertyDescriptor.desiredAssertionStatus();

	public PropertyDescriptor()
	{
	}

	public PropertyDescriptor(String name, String value)
	{
		this.name = name;
		this.value = value;
	}

	public boolean equals(Object rhs)
	{
		if (this == rhs)
			return true;
		PropertyDescriptor _r = null;
		try
		{
			_r = (PropertyDescriptor)rhs;
		}
		catch (ClassCastException ex) { }
		if (_r != null)
		{
			if (name != _r.name && (name == null || _r.name == null || !name.equals(_r.name)))
				return false;
			return value == _r.value || value != null && _r.value != null && value.equals(_r.value);
		} else
		{
			return false;
		}
	}

	public int hashCode()
	{
		int __h = 0;
		if (name != null)
			__h = 5 * __h + name.hashCode();
		if (value != null)
			__h = 5 * __h + value.hashCode();
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
		__os.writeString(name);
		__os.writeString(value);
	}

	public void __read(BasicStream __is)
	{
		name = __is.readString();
		value = __is.readString();
	}

}
