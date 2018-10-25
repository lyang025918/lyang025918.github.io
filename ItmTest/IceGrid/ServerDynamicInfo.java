// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ServerDynamicInfo.java

package IceGrid;

import IceInternal.BasicStream;
import java.io.Serializable;

// Referenced classes of package IceGrid:
//			ServerState

public class ServerDynamicInfo
	implements Cloneable, Serializable
{

	public String id;
	public ServerState state;
	public int pid;
	public boolean enabled;
	static final boolean $assertionsDisabled = !IceGrid/ServerDynamicInfo.desiredAssertionStatus();

	public ServerDynamicInfo()
	{
	}

	public ServerDynamicInfo(String id, ServerState state, int pid, boolean enabled)
	{
		this.id = id;
		this.state = state;
		this.pid = pid;
		this.enabled = enabled;
	}

	public boolean equals(Object rhs)
	{
		if (this == rhs)
			return true;
		ServerDynamicInfo _r = null;
		try
		{
			_r = (ServerDynamicInfo)rhs;
		}
		catch (ClassCastException ex) { }
		if (_r != null)
		{
			if (id != _r.id && (id == null || _r.id == null || !id.equals(_r.id)))
				return false;
			if (state != _r.state && (state == null || _r.state == null || !state.equals(_r.state)))
				return false;
			if (pid != _r.pid)
				return false;
			return enabled == _r.enabled;
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
		if (state != null)
			__h = 5 * __h + state.hashCode();
		__h = 5 * __h + pid;
		__h = 5 * __h + (enabled ? 1 : 0);
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
		state.__write(__os);
		__os.writeInt(pid);
		__os.writeBool(enabled);
	}

	public void __read(BasicStream __is)
	{
		id = __is.readString();
		state = ServerState.__read(__is);
		pid = __is.readInt();
		enabled = __is.readBool();
	}

}
