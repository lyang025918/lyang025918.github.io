// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ApplicationUpdateDescriptor.java

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
//			StringStringDictHelper, PropertySetDescriptorDictHelper, ReplicaGroupDescriptorSeqHelper, TemplateDescriptorDictHelper, 
//			NodeUpdateDescriptorSeqHelper, BoxedString, BoxedDistributionDescriptor

public class ApplicationUpdateDescriptor
	implements Cloneable, Serializable
{
	private class Patcher
		implements IceInternal.Patcher
	{

		private int __member;
		private String __typeId;
		final ApplicationUpdateDescriptor this$0;

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
					__typeId = "::IceGrid::BoxedDistributionDescriptor";
					distrib = (BoxedDistributionDescriptor)v;
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
			this$0 = ApplicationUpdateDescriptor.this;
			super();
			__member = member;
		}
	}


	public String name;
	public BoxedString description;
	public BoxedDistributionDescriptor distrib;
	public Map variables;
	public String removeVariables[];
	public Map propertySets;
	public String removePropertySets[];
	public List replicaGroups;
	public String removeReplicaGroups[];
	public Map serverTemplates;
	public String removeServerTemplates[];
	public Map serviceTemplates;
	public String removeServiceTemplates[];
	public List nodes;
	public String removeNodes[];
	static final boolean $assertionsDisabled = !IceGrid/ApplicationUpdateDescriptor.desiredAssertionStatus();

	public ApplicationUpdateDescriptor()
	{
	}

	public ApplicationUpdateDescriptor(String name, BoxedString description, BoxedDistributionDescriptor distrib, Map variables, String removeVariables[], Map propertySets, String removePropertySets[], 
			List replicaGroups, String removeReplicaGroups[], Map serverTemplates, String removeServerTemplates[], Map serviceTemplates, String removeServiceTemplates[], List nodes, 
			String removeNodes[])
	{
		this.name = name;
		this.description = description;
		this.distrib = distrib;
		this.variables = variables;
		this.removeVariables = removeVariables;
		this.propertySets = propertySets;
		this.removePropertySets = removePropertySets;
		this.replicaGroups = replicaGroups;
		this.removeReplicaGroups = removeReplicaGroups;
		this.serverTemplates = serverTemplates;
		this.removeServerTemplates = removeServerTemplates;
		this.serviceTemplates = serviceTemplates;
		this.removeServiceTemplates = removeServiceTemplates;
		this.nodes = nodes;
		this.removeNodes = removeNodes;
	}

	public boolean equals(Object rhs)
	{
		if (this == rhs)
			return true;
		ApplicationUpdateDescriptor _r = null;
		try
		{
			_r = (ApplicationUpdateDescriptor)rhs;
		}
		catch (ClassCastException ex) { }
		if (_r != null)
		{
			if (name != _r.name && (name == null || _r.name == null || !name.equals(_r.name)))
				return false;
			if (description != _r.description && (description == null || _r.description == null || !description.equals(_r.description)))
				return false;
			if (distrib != _r.distrib && (distrib == null || _r.distrib == null || !distrib.equals(_r.distrib)))
				return false;
			if (variables != _r.variables && (variables == null || _r.variables == null || !variables.equals(_r.variables)))
				return false;
			if (!Arrays.equals(removeVariables, _r.removeVariables))
				return false;
			if (propertySets != _r.propertySets && (propertySets == null || _r.propertySets == null || !propertySets.equals(_r.propertySets)))
				return false;
			if (!Arrays.equals(removePropertySets, _r.removePropertySets))
				return false;
			if (replicaGroups != _r.replicaGroups && (replicaGroups == null || _r.replicaGroups == null || !replicaGroups.equals(_r.replicaGroups)))
				return false;
			if (!Arrays.equals(removeReplicaGroups, _r.removeReplicaGroups))
				return false;
			if (serverTemplates != _r.serverTemplates && (serverTemplates == null || _r.serverTemplates == null || !serverTemplates.equals(_r.serverTemplates)))
				return false;
			if (!Arrays.equals(removeServerTemplates, _r.removeServerTemplates))
				return false;
			if (serviceTemplates != _r.serviceTemplates && (serviceTemplates == null || _r.serviceTemplates == null || !serviceTemplates.equals(_r.serviceTemplates)))
				return false;
			if (!Arrays.equals(removeServiceTemplates, _r.removeServiceTemplates))
				return false;
			if (nodes != _r.nodes && (nodes == null || _r.nodes == null || !nodes.equals(_r.nodes)))
				return false;
			return Arrays.equals(removeNodes, _r.removeNodes);
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
		if (distrib != null)
			__h = 5 * __h + distrib.hashCode();
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
		if (replicaGroups != null)
			__h = 5 * __h + replicaGroups.hashCode();
		if (removeReplicaGroups != null)
		{
			for (int __i2 = 0; __i2 < removeReplicaGroups.length; __i2++)
				if (removeReplicaGroups[__i2] != null)
					__h = 5 * __h + removeReplicaGroups[__i2].hashCode();

		}
		if (serverTemplates != null)
			__h = 5 * __h + serverTemplates.hashCode();
		if (removeServerTemplates != null)
		{
			for (int __i3 = 0; __i3 < removeServerTemplates.length; __i3++)
				if (removeServerTemplates[__i3] != null)
					__h = 5 * __h + removeServerTemplates[__i3].hashCode();

		}
		if (serviceTemplates != null)
			__h = 5 * __h + serviceTemplates.hashCode();
		if (removeServiceTemplates != null)
		{
			for (int __i4 = 0; __i4 < removeServiceTemplates.length; __i4++)
				if (removeServiceTemplates[__i4] != null)
					__h = 5 * __h + removeServiceTemplates[__i4].hashCode();

		}
		if (nodes != null)
			__h = 5 * __h + nodes.hashCode();
		if (removeNodes != null)
		{
			for (int __i5 = 0; __i5 < removeNodes.length; __i5++)
				if (removeNodes[__i5] != null)
					__h = 5 * __h + removeNodes[__i5].hashCode();

		}
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
		__os.writeObject(distrib);
		StringStringDictHelper.write(__os, variables);
		StringSeqHelper.write(__os, removeVariables);
		PropertySetDescriptorDictHelper.write(__os, propertySets);
		StringSeqHelper.write(__os, removePropertySets);
		ReplicaGroupDescriptorSeqHelper.write(__os, replicaGroups);
		StringSeqHelper.write(__os, removeReplicaGroups);
		TemplateDescriptorDictHelper.write(__os, serverTemplates);
		StringSeqHelper.write(__os, removeServerTemplates);
		TemplateDescriptorDictHelper.write(__os, serviceTemplates);
		StringSeqHelper.write(__os, removeServiceTemplates);
		NodeUpdateDescriptorSeqHelper.write(__os, nodes);
		StringSeqHelper.write(__os, removeNodes);
	}

	public void __read(BasicStream __is)
	{
		name = __is.readString();
		__is.readObject(new Patcher(0));
		__is.readObject(new Patcher(1));
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
		replicaGroups = ReplicaGroupDescriptorSeqHelper.read(__is);
		removeReplicaGroups = StringSeqHelper.read(__is);
		serverTemplates = TemplateDescriptorDictHelper.read(__is);
		removeServerTemplates = StringSeqHelper.read(__is);
		serviceTemplates = TemplateDescriptorDictHelper.read(__is);
		removeServiceTemplates = StringSeqHelper.read(__is);
		nodes = NodeUpdateDescriptorSeqHelper.read(__is);
		removeNodes = StringSeqHelper.read(__is);
	}

}
