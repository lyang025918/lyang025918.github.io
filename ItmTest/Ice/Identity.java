// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Identity.java

package Ice;

import IceInternal.BasicStream;
import java.io.Serializable;

public class Identity
	implements Cloneable, Serializable
{

	public String name;
	public String category;
	static final boolean $assertionsDisabled = !Ice/Identity.desiredAssertionStatus();

	public Identity()
	{
	}

	public Identity(String name, String category)
	{
		this.name = name;
		this.category = category;
	}

	public boolean equals(Object rhs)
	{
		if (this == rhs)
			return true;
		Identity _r = null;
		try
		{
			_r = (Identity)rhs;
		}
		catch (ClassCastException ex) { }
		if (_r != null)
		{
			if (name != _r.name && (name == null || _r.name == null || !name.equals(_r.name)))
				return false;
			return category == _r.category || category != null && _r.category != null && category.equals(_r.category);
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
		if (category != null)
			__h = 5 * __h + category.hashCode();
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
		__os.writeString(category);
	}

	public void __read(BasicStream __is)
	{
		name = __is.readString();
		category = __is.readString();
	}

}
