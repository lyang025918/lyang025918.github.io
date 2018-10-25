// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AdapterDescriptor.java

package IceGrid;

import IceInternal.BasicStream;
import java.io.Serializable;
import java.util.List;

// Referenced classes of package IceGrid:
//			ObjectDescriptorSeqHelper

public class AdapterDescriptor
	implements Cloneable, Serializable
{

	public String name;
	public String description;
	public String id;
	public String replicaGroupId;
	public String priority;
	public boolean registerProcess;
	public boolean serverLifetime;
	public List objects;
	public List allocatables;
	static final boolean $assertionsDisabled = !IceGrid/AdapterDescriptor.desiredAssertionStatus();

	public AdapterDescriptor()
	{
	}

	public AdapterDescriptor(String name, String description, String id, String replicaGroupId, String priority, boolean registerProcess, boolean serverLifetime, 
			List objects, List allocatables)
	{
		this.name = name;
		this.description = description;
		this.id = id;
		this.replicaGroupId = replicaGroupId;
		this.priority = priority;
		this.registerProcess = registerProcess;
		this.serverLifetime = serverLifetime;
		this.objects = objects;
		this.allocatables = allocatables;
	}

	public boolean equals(Object rhs)
	{
		if (this == rhs)
			return true;
		AdapterDescriptor _r = null;
		try
		{
			_r = (AdapterDescriptor)rhs;
		}
		catch (ClassCastException ex) { }
		if (_r != null)
		{
			if (name != _r.name && (name == null || _r.name == null || !name.equals(_r.name)))
				return false;
			if (description != _r.description && (description == null || _r.description == null || !description.equals(_r.description)))
				return false;
			if (id != _r.id && (id == null || _r.id == null || !id.equals(_r.id)))
				return false;
			if (replicaGroupId != _r.replicaGroupId && (replicaGroupId == null || _r.replicaGroupId == null || !replicaGroupId.equals(_r.replicaGroupId)))
				return false;
			if (priority != _r.priority && (priority == null || _r.priority == null || !priority.equals(_r.priority)))
				return false;
			if (registerProcess != _r.registerProcess)
				return false;
			if (serverLifetime != _r.serverLifetime)
				return false;
			if (objects != _r.objects && (objects == null || _r.objects == null || !objects.equals(_r.objects)))
				return false;
			return allocatables == _r.allocatables || allocatables != null && _r.allocatables != null && allocatables.equals(_r.allocatables);
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
		if (description != null)
			__h = 5 * __h + description.hashCode();
		if (id != null)
			__h = 5 * __h + id.hashCode();
		if (replicaGroupId != null)
			__h = 5 * __h + replicaGroupId.hashCode();
		if (priority != null)
			__h = 5 * __h + priority.hashCode();
		__h = 5 * __h + (registerProcess ? 1 : 0);
		__h = 5 * __h + (serverLifetime ? 1 : 0);
		if (objects != null)
			__h = 5 * __h + objects.hashCode();
		if (allocatables != null)
			__h = 5 * __h + allocatables.hashCode();
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
		__os.writeString(description);
		__os.writeString(id);
		__os.writeString(replicaGroupId);
		__os.writeString(priority);
		__os.writeBool(registerProcess);
		__os.writeBool(serverLifetime);
		ObjectDescriptorSeqHelper.write(__os, objects);
		ObjectDescriptorSeqHelper.write(__os, allocatables);
	}

	public void __read(BasicStream __is)
	{
		name = __is.readString();
		description = __is.readString();
		id = __is.readString();
		replicaGroupId = __is.readString();
		priority = __is.readString();
		registerProcess = __is.readBool();
		serverLifetime = __is.readBool();
		objects = ObjectDescriptorSeqHelper.read(__is);
		allocatables = ObjectDescriptorSeqHelper.read(__is);
	}

}
