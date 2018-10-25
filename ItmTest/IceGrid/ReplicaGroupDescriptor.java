// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ReplicaGroupDescriptor.java

package IceGrid;

import Ice.Object;
import IceInternal.BasicStream;
import IceInternal.Ex;
import java.io.Serializable;
import java.util.List;

// Referenced classes of package IceGrid:
//			ObjectDescriptorSeqHelper, LoadBalancingPolicy

public class ReplicaGroupDescriptor
	implements Cloneable, Serializable
{
	private class Patcher
		implements IceInternal.Patcher
	{

		final ReplicaGroupDescriptor this$0;

		public void patch(Ice.Object v)
		{
			try
			{
				loadBalancing = (LoadBalancingPolicy)v;
			}
			catch (ClassCastException ex)
			{
				Ex.throwUOE(type(), v.ice_id());
			}
		}

		public String type()
		{
			return "::IceGrid::LoadBalancingPolicy";
		}

		private Patcher()
		{
			this$0 = ReplicaGroupDescriptor.this;
			super();
		}

	}


	public String id;
	public LoadBalancingPolicy loadBalancing;
	public List objects;
	public String description;
	static final boolean $assertionsDisabled = !IceGrid/ReplicaGroupDescriptor.desiredAssertionStatus();

	public ReplicaGroupDescriptor()
	{
	}

	public ReplicaGroupDescriptor(String id, LoadBalancingPolicy loadBalancing, List objects, String description)
	{
		this.id = id;
		this.loadBalancing = loadBalancing;
		this.objects = objects;
		this.description = description;
	}

	public boolean equals(Object rhs)
	{
		if (this == rhs)
			return true;
		ReplicaGroupDescriptor _r = null;
		try
		{
			_r = (ReplicaGroupDescriptor)rhs;
		}
		catch (ClassCastException ex) { }
		if (_r != null)
		{
			if (id != _r.id && (id == null || _r.id == null || !id.equals(_r.id)))
				return false;
			if (loadBalancing != _r.loadBalancing && (loadBalancing == null || _r.loadBalancing == null || !loadBalancing.equals(_r.loadBalancing)))
				return false;
			if (objects != _r.objects && (objects == null || _r.objects == null || !objects.equals(_r.objects)))
				return false;
			return description == _r.description || description != null && _r.description != null && description.equals(_r.description);
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
		if (loadBalancing != null)
			__h = 5 * __h + loadBalancing.hashCode();
		if (objects != null)
			__h = 5 * __h + objects.hashCode();
		if (description != null)
			__h = 5 * __h + description.hashCode();
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
		__os.writeObject(loadBalancing);
		ObjectDescriptorSeqHelper.write(__os, objects);
		__os.writeString(description);
	}

	public void __read(BasicStream __is)
	{
		id = __is.readString();
		__is.readObject(new Patcher());
		objects = ObjectDescriptorSeqHelper.read(__is);
		description = __is.readString();
	}

}
