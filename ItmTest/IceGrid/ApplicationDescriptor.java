// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ApplicationDescriptor.java

package IceGrid;

import IceInternal.BasicStream;
import java.io.Serializable;
import java.util.*;

// Referenced classes of package IceGrid:
//			DistributionDescriptor, StringStringDictHelper, ReplicaGroupDescriptorSeqHelper, TemplateDescriptorDictHelper, 
//			NodeDescriptorDictHelper, PropertySetDescriptorDictHelper

public class ApplicationDescriptor
	implements Cloneable, Serializable
{

	public String name;
	public Map variables;
	public List replicaGroups;
	public Map serverTemplates;
	public Map serviceTemplates;
	public Map nodes;
	public DistributionDescriptor distrib;
	public String description;
	public Map propertySets;
	static final boolean $assertionsDisabled = !IceGrid/ApplicationDescriptor.desiredAssertionStatus();

	public ApplicationDescriptor()
	{
	}

	public ApplicationDescriptor(String name, Map variables, List replicaGroups, Map serverTemplates, Map serviceTemplates, Map nodes, DistributionDescriptor distrib, 
			String description, Map propertySets)
	{
		this.name = name;
		this.variables = variables;
		this.replicaGroups = replicaGroups;
		this.serverTemplates = serverTemplates;
		this.serviceTemplates = serviceTemplates;
		this.nodes = nodes;
		this.distrib = distrib;
		this.description = description;
		this.propertySets = propertySets;
	}

	public boolean equals(Object rhs)
	{
		if (this == rhs)
			return true;
		ApplicationDescriptor _r = null;
		try
		{
			_r = (ApplicationDescriptor)rhs;
		}
		catch (ClassCastException ex) { }
		if (_r != null)
		{
			if (name != _r.name && (name == null || _r.name == null || !name.equals(_r.name)))
				return false;
			if (variables != _r.variables && (variables == null || _r.variables == null || !variables.equals(_r.variables)))
				return false;
			if (replicaGroups != _r.replicaGroups && (replicaGroups == null || _r.replicaGroups == null || !replicaGroups.equals(_r.replicaGroups)))
				return false;
			if (serverTemplates != _r.serverTemplates && (serverTemplates == null || _r.serverTemplates == null || !serverTemplates.equals(_r.serverTemplates)))
				return false;
			if (serviceTemplates != _r.serviceTemplates && (serviceTemplates == null || _r.serviceTemplates == null || !serviceTemplates.equals(_r.serviceTemplates)))
				return false;
			if (nodes != _r.nodes && (nodes == null || _r.nodes == null || !nodes.equals(_r.nodes)))
				return false;
			if (distrib != _r.distrib && (distrib == null || _r.distrib == null || !distrib.equals(_r.distrib)))
				return false;
			if (description != _r.description && (description == null || _r.description == null || !description.equals(_r.description)))
				return false;
			return propertySets == _r.propertySets || propertySets != null && _r.propertySets != null && propertySets.equals(_r.propertySets);
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
		if (variables != null)
			__h = 5 * __h + variables.hashCode();
		if (replicaGroups != null)
			__h = 5 * __h + replicaGroups.hashCode();
		if (serverTemplates != null)
			__h = 5 * __h + serverTemplates.hashCode();
		if (serviceTemplates != null)
			__h = 5 * __h + serviceTemplates.hashCode();
		if (nodes != null)
			__h = 5 * __h + nodes.hashCode();
		if (distrib != null)
			__h = 5 * __h + distrib.hashCode();
		if (description != null)
			__h = 5 * __h + description.hashCode();
		if (propertySets != null)
			__h = 5 * __h + propertySets.hashCode();
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
		StringStringDictHelper.write(__os, variables);
		ReplicaGroupDescriptorSeqHelper.write(__os, replicaGroups);
		TemplateDescriptorDictHelper.write(__os, serverTemplates);
		TemplateDescriptorDictHelper.write(__os, serviceTemplates);
		NodeDescriptorDictHelper.write(__os, nodes);
		distrib.__write(__os);
		__os.writeString(description);
		PropertySetDescriptorDictHelper.write(__os, propertySets);
	}

	public void __read(BasicStream __is)
	{
		name = __is.readString();
		variables = new TreeMap();
		int __sz0 = __is.readSize();
		for (int __i0 = 0; __i0 < __sz0; __i0++)
		{
			String __key = __is.readString();
			String __value = __is.readString();
			variables.put(__key, __value);
		}

		replicaGroups = ReplicaGroupDescriptorSeqHelper.read(__is);
		serverTemplates = TemplateDescriptorDictHelper.read(__is);
		serviceTemplates = TemplateDescriptorDictHelper.read(__is);
		nodes = NodeDescriptorDictHelper.read(__is);
		distrib = new DistributionDescriptor();
		distrib.__read(__is);
		description = __is.readString();
		propertySets = PropertySetDescriptorDictHelper.read(__is);
	}

}
