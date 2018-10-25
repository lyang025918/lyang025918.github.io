// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NodeUpdateDescriptor.java

package IceGrid;

import Ice.Object;
import Ice.StringSeqHelper;
import IceInternal.BasicStream;
import IceInternal.Ex;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

// Referenced classes of package IceGrid:
//			StringStringDictHelper, PropertySetDescriptorDictHelper, ServerInstanceDescriptorSeqHelper, ServerDescriptorSeqHelper, 
//			BoxedString

public class NodeUpdateDescriptor
	implements Cloneable, Serializable
{
	private class Patcher
		implements IceInternal.Patcher
	{

		private int __member;
		private String __typeId;
		final NodeUpdateDescriptor this$0;

		public void patch(Ice.Object v)
		{
			try
			{
				switch (__member)
				{
				case 0: // '\0'
					__typeId = "::IceGrid::BoxedString";
					description = (BoxedString)v;
					break;

				case 1: // '\001'
					__typeId = "::IceGrid::BoxedString";
					loadFactor = (BoxedString)v;
					break;
				}
			}
			catch (ClassCastException ex)
			{
				Ex.throwUOE(type(), v.ice_id());
			}
		}

		public String type()
		{
			return __typeId;
		}

		Patcher(int member)
		{
			this$0 = NodeUpdateDescriptor.this;
			super();
			__member = member;
		}
	}


	public String name;
	public BoxedString description;
	public Map variables;
	public String removeVariables[];
	public Map propertySets;
	public String removePropertySets[];
	public List serverInstances;
	public List servers;
	public String removeServers[];
	public BoxedString loadFactor;
	static final boolean $assertionsDisabled = !IceGrid/NodeUpdateDescriptor.desiredAssertionStatus();

	public NodeUpdateDescriptor()
	{
	}

	public NodeUpdateDescriptor(String name, BoxedString description, Map variables, String removeVariables[], Map propertySets, String removePropertySets[], List serverInstances, 
			List servers, String removeServers[], BoxedString loadFactor)
	{
		this.name = name;
		this.description = description;
		this.variables = variables;
		this.removeVariables = removeVariables;
		this.propertySets = propertySets;
		this.removePropertySets = removePropertySets;
		this.serverInstances = serverInstances;
		this.servers = servers;
		this.removeServers = removeServers;
		this.loadFactor = loadFactor;
	}

	public boolean equals(Object rhs)
	{
		if (this == rhs)
			return true;
		NodeUpdateDescriptor _r = null;
		try
		{
			_r = (NodeUpdateDescriptor)rhs;
		}
		catch (ClassCastException ex) { }
		if (_r != null)
		{
			if (name != _r.name && (name == null || _r.name == null || !name.equals(_r.name)))
				return false;
			if (description != _r.description && (description == null || _r.description == null || !description.equals(_r.description)))
				return false;
			if (variables != _r.variables && (variables == null || _r.variables == null || !variables.equals(_r.variables)))
				return false;
			if (!Arrays.equals(removeVariables, _r.removeVariables))
				return false;
			if (propertySets != _r.propertySets && (propertySets == null || _r.propertySets == null || !propertySets.equals(_r.propertySets)))
				return false;
			if (!Arrays.equals(removePropertySets, _r.removePropertySets))
				return false;
			if (serverInstances != _r.serverInstances && (serverInstances == null || _r.serverInstances == null || !serverInstances.equals(_r.serverInstances)))
				return false;
			if (servers != _r.servers && (servers == null || _r.servers == null || !servers.equals(_r.servers)))
				return false;
			if (!Arrays.equals(removeServers, _r.removeServers))
				return false;
			return loadFactor == _r.loadFactor || loadFactor != null && _r.loadFactor != null && loadFactor.equals(_r.loadFactor);
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
		if (variables != null)
			__h = 5 * __h + variables.hashCode();
		if (removeVariables != null)
		{
			for (int __i0 = 0; __i0 < removeVariables.length; __i0++)
				if (removeVariables[__i0] != null)
					__h = 5 * __h + removeVariables[__i0].hashCode();

		}
		if (propertySets != null)
			__h = 5 * __h + propertySets.hashCode();
		if (removePropertySets != null)
		{
			for (int __i1 = 0; __i1 < removePropertySets.length; __i1++)
				if (removePropertySets[__i1] != null)
					__h = 5 * __h + removePropertySets[__i1].hashCode();

		}
		if (serverInstances != null)
			__h = 5 * __h + serverInstances.hashCode();
		if (servers != null)
			__h = 5 * __h + servers.hashCode();
		if (removeServers != null)
		{
			for (int __i2 = 0; __i2 < removeServers.length; __i2++)
				if (removeServers[__i2] != null)
					__h = 5 * __h + removeServers[__i2].hashCode();

		}
		if (loadFactor != null)
			__h = 5 * __h + loadFactor.hashCode();
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
		__os.writeObject(description);
		StringStringDictHelper.write(__os, variables);
		StringSeqHelper.write(__os, removeVariables);
		PropertySetDescriptorDictHelper.write(__os, propertySets);
		StringSeqHelper.write(__os, removePropertySets);
		ServerInstanceDescriptorSeqHelper.write(__os, serverInstances);
		ServerDescriptorSeqHelper.write(__os, servers);
		StringSeqHelper.write(__os, removeServers);
		__os.writeObject(loadFactor);
	}

	public void __read(BasicStream __is)
	{
		name = __is.readString();
		__is.readObject(new Patcher(0));
		variables = new TreeMap();
		int __sz0 = __is.readSize();
		for (int __i0 = 0; __i0 < __sz0; __i0++)
		{
			String __key = __is.readString();
			String __value = __is.readString();
			variables.put(__key, __value);
		}

		removeVariables = StringSeqHelper.read(__is);
		propertySets = PropertySetDescriptorDictHelper.read(__is);
		removePropertySets = StringSeqHelper.read(__is);
		serverInstances = ServerInstanceDescriptorSeqHelper.read(__is);
		servers = ServerDescriptorSeqHelper.read(__is);
		removeServers = StringSeqHelper.read(__is);
		__is.readObject(new Patcher(1));
	}

}
