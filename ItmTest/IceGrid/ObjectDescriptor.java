// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ObjectDescriptor.java

package IceGrid;

import Ice.Identity;
import IceInternal.BasicStream;
import java.io.Serializable;

public class ObjectDescriptor
	implements Cloneable, Serializable
{

	public Identity id;
	public String type;
	static final boolean $assertionsDisabled = !IceGrid/ObjectDescriptor.desiredAssertionStatus();

	public ObjectDescriptor()
	{
	}

	public ObjectDescriptor(Identity id, String type)
	{
		this.id = id;
		this.type = type;
	}

	public boolean equals(Object rhs)
	{
		if (this == rhs)
			return true;
		ObjectDescriptor _r = null;
		try
		{
			_r = (ObjectDescriptor)rhs;
		}
		catch (ClassCastException ex) { }
		if (_r != null)
		{
			if (id != _r.id && (id == null || _r.id == null || !id.equals(_r.id)))
				return false;
			return type == _r.type || type != null && _r.type != null && type.equals(_r.type);
		} else
		{
			return false;
		}
	}

	public int hashCode()
	{
		int __h = 0;
		if (id != null)
			__h = 5 * __h + id.hashCode();
		if (type != null)
			__h = 5 * __h + type.hashCode();
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
		id.__write(__os);
		__os.writeString(type);
	}

	public void __read(BasicStream __is)
	{
		id = new Identity();
		id.__read(__is);
		type = __is.readString();
	}

}
