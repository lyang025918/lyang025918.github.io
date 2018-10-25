// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NodeDescriptor.java

package IceGrid;

import IceInternal.BasicStream;
import java.io.Serializable;
import java.util.*;

// Referenced classes of package IceGrid:
//			StringStringDictHelper, ServerInstanceDescriptorSeqHelper, ServerDescriptorSeqHelper, PropertySetDescriptorDictHelper

public class NodeDescriptor
	implements Cloneable, Serializable
{

	public Map variables;
	public List serverInstances;
	public List servers;
	public String loadFactor;
	public String description;
	public Map propertySets;
	static final boolean $assertionsDisabled = !IceGrid/NodeDescriptor.desiredAssertionStatus();

	public NodeDescriptor()
	{
	}

	public NodeDescriptor(Map variables, List serverInstances, List servers, String loadFactor, String description, Map propertySets)
	{
		this.variables = variables;
		this.serverInstances = serverInstances;
		this.servers = servers;
		this.loadFactor = loadFactor;
		this.description = description;
		this.propertySets = propertySets;
	}

	public boolean equals(Object rhs)
	{
		if (this == rhs)
			return true;
		NodeDescriptor _r = null;
		try
		{
			_r = (NodeDescriptor)rhs;
		}
		catch (ClassCastException ex) { }
		if (_r != null)
		{
			if (variables != _r.variables && (variables == null || _r.variables == null || !variables.equals(_r.variables)))
				return false;
			if (serverInstances != _r.serverInstances && (serverInstances == null || _r.serverInstances == null || !serverInstances.equals(_r.serverInstances)))
				return false;
			if (servers != _r.servers && (servers == null || _r.servers == null || !servers.equals(_r.servers)))
				return false;
			if (loadFactor != _r.loadFactor && (loadFactor == null || _r.loadFactor == null || !loadFactor.equals(_r.loadFactor)))
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
		if (variables != null)
			__h = 5 * __h + variables.hashCode();
		if (serverInstances != null)
			__h = 5 * __h + serverInstances.hashCode();
		if (servers != null)
			__h = 5 * __h + servers.hashCode();
		if (loadFactor != null)
			__h = 5 * __h + loadFactor.hashCode();
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
		StringStringDictHelper.write(__os, variables);
		ServerInstanceDescriptorSeqHelper.write(__os, serverInstances);
		ServerDescriptorSeqHelper.write(__os, servers);
		__os.writeString(loadFactor);
		__os.writeString(description);
		PropertySetDescriptorDictHelper.write(__os, propertySets);
	}

	public void __read(BasicStream __is)
	{
		variables = new TreeMap();
		int __sz0 = __is.readSize();
		for (int __i0 = 0; __i0 < __sz0; __i0++)
		{
			String __key = __is.readString();
			String __value = __is.readString();
			variables.put(__key, __value);
		}

		serverInstances = ServerInstanceDescriptorSeqHelper.read(__is);
		servers = ServerDescriptorSeqHelper.read(__is);
		loadFactor = __is.readString();
		description = __is.readString();
		propertySets = PropertySetDescriptorDictHelper.read(__is);
	}

}
