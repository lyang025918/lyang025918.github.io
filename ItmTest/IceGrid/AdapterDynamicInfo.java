// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AdapterDynamicInfo.java

package IceGrid;

import Ice.ObjectPrx;
import IceInternal.BasicStream;
import java.io.Serializable;

public class AdapterDynamicInfo
	implements Cloneable, Serializable
{

	public String id;
	public ObjectPrx proxy;
	static final boolean $assertionsDisabled = !IceGrid/AdapterDynamicInfo.desiredAssertionStatus();

	public AdapterDynamicInfo()
	{
	}

	public AdapterDynamicInfo(String id, ObjectPrx proxy)
	{
		this.id = id;
		this.proxy = proxy;
	}

	public boolean equals(Object rhs)
	{
		if (this == rhs)
			return true;
		AdapterDynamicInfo _r = null;
		try
		{
			_r = (AdapterDynamicInfo)rhs;
		}
		catch (ClassCastException ex) { }
		if (_r != null)
		{
			if (id != _r.id && (id == null || _r.id == null || !id.equals(_r.id)))
				return false;
			return proxy == _r.proxy || proxy != null && _r.proxy != null && proxy.equals(_r.proxy);
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
		if (proxy != null)
			__h = 5 * __h + proxy.hashCode();
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
		__os.writeString(id);
		__os.writeProxy(proxy);
	}

	public void __read(BasicStream __is)
	{
		id = __is.readString();
		proxy = __is.readProxy();
	}

}
