// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RegistryInfo.java

package IceGrid;

import IceInternal.BasicStream;
import java.io.Serializable;

public class RegistryInfo
	implements Cloneable, Serializable
{

	public String name;
	public String hostname;
	static final boolean $assertionsDisabled = !IceGrid/RegistryInfo.desiredAssertionStatus();

	public RegistryInfo()
	{
	}

	public RegistryInfo(String name, String hostname)
	{
		this.name = name;
		this.hostname = hostname;
	}

	public boolean equals(Object rhs)
	{
		if (this == rhs)
			return true;
		RegistryInfo _r = null;
		try
		{
			_r = (RegistryInfo)rhs;
		}
		catch (ClassCastException ex) { }
		if (_r != null)
		{
			if (name != _r.name && (name == null || _r.name == null || !name.equals(_r.name)))
				return false;
			return hostname == _r.hostname || hostname != null && _r.hostname != null && hostname.equals(_r.hostname);
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
		if (hostname != null)
			__h = 5 * __h + hostname.hashCode();
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
		__os.writeString(hostname);
	}

	public void __read(BasicStream __is)
	{
		name = __is.readString();
		hostname = __is.readString();
	}

}
