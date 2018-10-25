// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ServerInfo.java

package IceGrid;

import Ice.Object;
import IceInternal.BasicStream;
import IceInternal.Ex;
import java.io.Serializable;

// Referenced classes of package IceGrid:
//			ServerDescriptor

public class ServerInfo
	implements Cloneable, Serializable
{
	private class Patcher
		implements IceInternal.Patcher
	{

		final ServerInfo this$0;

		public void patch(Ice.Object v)
		{
			try
			{
				descriptor = (ServerDescriptor)v;
			}
			catch (ClassCastException ex)
			{
				Ex.throwUOE(type(), v.ice_id());
			}
		}

		public String type()
		{
			return "::IceGrid::ServerDescriptor";
		}

		private Patcher()
		{
			this$0 = ServerInfo.this;
			super();
		}

	}


	public String application;
	public String uuid;
	public int revision;
	public String node;
	public ServerDescriptor descriptor;
	public String sessionId;
	static final boolean $assertionsDisabled = !IceGrid/ServerInfo.desiredAssertionStatus();

	public ServerInfo()
	{
	}

	public ServerInfo(String application, String uuid, int revision, String node, ServerDescriptor descriptor, String sessionId)
	{
		this.application = application;
		this.uuid = uuid;
		this.revision = revision;
		this.node = node;
		this.descriptor = descriptor;
		this.sessionId = sessionId;
	}

	public boolean equals(Object rhs)
	{
		if (this == rhs)
			return true;
		ServerInfo _r = null;
		try
		{
			_r = (ServerInfo)rhs;
		}
		catch (ClassCastException ex) { }
		if (_r != null)
		{
			if (application != _r.application && (application == null || _r.application == null || !application.equals(_r.application)))
				return false;
			if (uuid != _r.uuid && (uuid == null || _r.uuid == null || !uuid.equals(_r.uuid)))
				return false;
			if (revision != _r.revision)
				return false;
			if (node != _r.node && (node == null || _r.node == null || !node.equals(_r.node)))
				return false;
			if (descriptor != _r.descriptor && (descriptor == null || _r.descriptor == null || !descriptor.equals(_r.descriptor)))
				return false;
			return sessionId == _r.sessionId || sessionId != null && _r.sessionId != null && sessionId.equals(_r.sessionId);
		} else
		{
			return false;
		}
	}

	public int hashCode()
	{
		int __h = 0;
		if (application != null)
			__h = 5 * __h + application.hashCode();
		if (uuid != null)
			__h = 5 * __h + uuid.hashCode();
		__h = 5 * __h + revision;
		if (node != null)
			__h = 5 * __h + node.hashCode();
		if (descriptor != null)
			__h = 5 * __h + descriptor.hashCode();
		if (sessionId != null)
			__h = 5 * __h + sessionId.hashCode();
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
		__os.writeString(application);
		__os.writeString(uuid);
		__os.writeInt(revision);
		__os.writeString(node);
		__os.writeObject(descriptor);
		__os.writeString(sessionId);
	}

	public void __read(BasicStream __is)
	{
		application = __is.readString();
		uuid = __is.readString();
		revision = __is.readInt();
		node = __is.readString();
		__is.readObject(new Patcher());
		sessionId = __is.readString();
	}

}
