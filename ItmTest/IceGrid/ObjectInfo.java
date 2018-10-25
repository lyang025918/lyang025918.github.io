// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ObjectInfo.java

package IceGrid;

import Ice.ObjectPrx;
import IceInternal.BasicStream;
import java.io.Serializable;

public class ObjectInfo
	implements Cloneable, Serializable
{

	public ObjectPrx proxy;
	public String type;
	static final boolean $assertionsDisabled = !IceGrid/ObjectInfo.desiredAssertionStatus();

	public ObjectInfo()
	{
	}

	public ObjectInfo(ObjectPrx proxy, String type)
	{
		this.proxy = proxy;
		this.type = type;
	}

	public boolean equals(Object rhs)
	{
		if (this == rhs)
			return true;
		ObjectInfo _r = null;
		try
		{
			_r = (ObjectInfo)rhs;
		}
		catch (ClassCastException ex) { }
		if (_r != null)
		{
			if (proxy != _r.proxy && (proxy == null || _r.proxy == null || !proxy.equals(_r.proxy)))
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
		if (proxy != null)
			__h = 5 * __h + proxy.hashCode();
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
		__os.writeProxy(proxy);
		__os.writeString(type);
	}

	public void __read(BasicStream __is)
	{
		proxy = __is.readProxy();
		type = __is.readString();
	}

}
