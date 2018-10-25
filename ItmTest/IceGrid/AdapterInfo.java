// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AdapterInfo.java

package IceGrid;

import Ice.ObjectPrx;
import IceInternal.BasicStream;
import java.io.Serializable;

public class AdapterInfo
	implements Cloneable, Serializable
{

	public String id;
	public ObjectPrx proxy;
	public String replicaGroupId;
	static final boolean $assertionsDisabled = !IceGrid/AdapterInfo.desiredAssertionStatus();

	public AdapterInfo()
	{
	}

	public AdapterInfo(String id, ObjectPrx proxy, String replicaGroupId)
	{
		this.id = id;
		this.proxy = proxy;
		this.replicaGroupId = replicaGroupId;
	}

	public boolean equals(Object rhs)
	{
		if (this == rhs)
			return true;
		AdapterInfo _r = null;
		try
		{
			_r = (AdapterInfo)rhs;
		}
		catch (ClassCastException ex) { }
		if (_r != null)
		{
			if (id != _r.id && (id == null || _r.id == null || !id.equals(_r.id)))
				return false;
			if (proxy != _r.proxy && (proxy == null || _r.proxy == null || !proxy.equals(_r.proxy)))
				return false;
			return replicaGroupId == _r.replicaGroupId || replicaGroupId != null && _r.replicaGroupId != null && replicaGroupId.equals(_r.replicaGroupId);
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
		if (replicaGroupId != null)
			__h = 5 * __h + replicaGroupId.hashCode();
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
		__os.writeString(replicaGroupId);
	}

	public void __read(BasicStream __is)
	{
		id = __is.readString();
		proxy = __is.readProxy();
		replicaGroupId = __is.readString();
	}

}
