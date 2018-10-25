// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Current.java

package Ice;

import java.util.Map;

// Referenced classes of package Ice:
//			Identity, OperationMode, ObjectAdapter, Connection

public class Current
	implements Cloneable
{

	public ObjectAdapter adapter;
	public Connection con;
	public Identity id;
	public String facet;
	public String operation;
	public OperationMode mode;
	public Map ctx;
	public int requestId;
	static final boolean $assertionsDisabled = !Ice/Current.desiredAssertionStatus();

	public Current()
	{
	}

	public Current(ObjectAdapter adapter, Connection con, Identity id, String facet, String operation, OperationMode mode, Map ctx, 
			int requestId)
	{
		this.adapter = adapter;
		this.con = con;
		this.id = id;
		this.facet = facet;
		this.operation = operation;
		this.mode = mode;
		this.ctx = ctx;
		this.requestId = requestId;
	}

	public boolean equals(Object rhs)
	{
		if (this == rhs)
			return true;
		Current _r = null;
		try
		{
			_r = (Current)rhs;
		}
		catch (ClassCastException ex) { }
		if (_r != null)
		{
			if (adapter != _r.adapter && (adapter == null || _r.adapter == null || !adapter.equals(_r.adapter)))
				return false;
			if (con != _r.con && (con == null || _r.con == null || !con.equals(_r.con)))
				return false;
			if (id != _r.id && (id == null || _r.id == null || !id.equals(_r.id)))
				return false;
			if (facet != _r.facet && (facet == null || _r.facet == null || !facet.equals(_r.facet)))
				return false;
			if (operation != _r.operation && (operation == null || _r.operation == null || !operation.equals(_r.operation)))
				return false;
			if (mode != _r.mode && (mode == null || _r.mode == null || !mode.equals(_r.mode)))
				return false;
			if (ctx != _r.ctx && (ctx == null || _r.ctx == null || !ctx.equals(_r.ctx)))
				return false;
			return requestId == _r.requestId;
		} else
		{
			return false;
		}
	}

	public int hashCode()
	{
		int __h = 0;
		if (adapter != null)
			__h = 5 * __h + adapter.hashCode();
		if (con != null)
			__h = 5 * __h + con.hashCode();
		if (id != null)
			__h = 5 * __h + id.hashCode();
		if (facet != null)
			__h = 5 * __h + facet.hashCode();
		if (operation != null)
			__h = 5 * __h + operation.hashCode();
		if (mode != null)
			__h = 5 * __h + mode.hashCode();
		if (ctx != null)
			__h = 5 * __h + ctx.hashCode();
		__h = 5 * __h + requestId;
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

}
