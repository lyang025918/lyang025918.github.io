// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NodeDynamicInfo.java

package IceGrid;

import IceInternal.BasicStream;
import java.io.Serializable;
import java.util.List;

// Referenced classes of package IceGrid:
//			NodeInfo, ServerDynamicInfoSeqHelper, AdapterDynamicInfoSeqHelper

public class NodeDynamicInfo
	implements Cloneable, Serializable
{

	public NodeInfo info;
	public List servers;
	public List adapters;
	static final boolean $assertionsDisabled = !IceGrid/NodeDynamicInfo.desiredAssertionStatus();

	public NodeDynamicInfo()
	{
	}

	public NodeDynamicInfo(NodeInfo info, List servers, List adapters)
	{
		this.info = info;
		this.servers = servers;
		this.adapters = adapters;
	}

	public boolean equals(Object rhs)
	{
		if (this == rhs)
			return true;
		NodeDynamicInfo _r = null;
		try
		{
			_r = (NodeDynamicInfo)rhs;
		}
		catch (ClassCastException ex) { }
		if (_r != null)
		{
			if (info != _r.info && (info == null || _r.info == null || !info.equals(_r.info)))
				return false;
			if (servers != _r.servers && (servers == null || _r.servers == null || !servers.equals(_r.servers)))
				return false;
			return adapters == _r.adapters || adapters != null && _r.adapters != null && adapters.equals(_r.adapters);
		} else
		{
			return false;
		}
	}

	public int hashCode()
	{
		int __h = 0;
		if (info != null)
			__h = 5 * __h + info.hashCode();
		if (servers != null)
			__h = 5 * __h + servers.hashCode();
		if (adapters != null)
			__h = 5 * __h + adapters.hashCode();
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
		info.__write(__os);
		ServerDynamicInfoSeqHelper.write(__os, servers);
		AdapterDynamicInfoSeqHelper.write(__os, adapters);
	}

	public void __read(BasicStream __is)
	{
		info = new NodeInfo();
		info.__read(__is);
		servers = ServerDynamicInfoSeqHelper.read(__is);
		adapters = AdapterDynamicInfoSeqHelper.read(__is);
	}

}
