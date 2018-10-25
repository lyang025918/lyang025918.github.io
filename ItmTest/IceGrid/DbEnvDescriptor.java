// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DbEnvDescriptor.java

package IceGrid;

import IceInternal.BasicStream;
import java.io.Serializable;
import java.util.List;

// Referenced classes of package IceGrid:
//			PropertyDescriptorSeqHelper

public class DbEnvDescriptor
	implements Cloneable, Serializable
{

	public String name;
	public String description;
	public String dbHome;
	public List properties;
	static final boolean $assertionsDisabled = !IceGrid/DbEnvDescriptor.desiredAssertionStatus();

	public DbEnvDescriptor()
	{
	}

	public DbEnvDescriptor(String name, String description, String dbHome, List properties)
	{
		this.name = name;
		this.description = description;
		this.dbHome = dbHome;
		this.properties = properties;
	}

	public boolean equals(Object rhs)
	{
		if (this == rhs)
			return true;
		DbEnvDescriptor _r = null;
		try
		{
			_r = (DbEnvDescriptor)rhs;
		}
		catch (ClassCastException ex) { }
		if (_r != null)
		{
			if (name != _r.name && (name == null || _r.name == null || !name.equals(_r.name)))
				return false;
			if (description != _r.description && (description == null || _r.description == null || !description.equals(_r.description)))
				return false;
			if (dbHome != _r.dbHome && (dbHome == null || _r.dbHome == null || !dbHome.equals(_r.dbHome)))
				return false;
			return properties == _r.properties || properties != null && _r.properties != null && properties.equals(_r.properties);
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
		if (dbHome != null)
			__h = 5 * __h + dbHome.hashCode();
		if (properties != null)
			__h = 5 * __h + properties.hashCode();
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
		__os.writeString(dbHome);
		PropertyDescriptorSeqHelper.write(__os, properties);
	}

	public void __read(BasicStream __is)
	{
		name = __is.readString();
		description = __is.readString();
		dbHome = __is.readString();
		properties = PropertyDescriptorSeqHelper.read(__is);
	}

}
