// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PropertiesAdminI.java

package IceInternal;

import Ice.*;
import java.util.Map;
import java.util.TreeMap;

class PropertiesAdminI extends _PropertiesAdminDisp
{

	private final Properties _properties;

	PropertiesAdminI(Properties properties)
	{
		_properties = properties;
	}

	public String getProperty(String name, Current current)
	{
		return _properties.getProperty(name);
	}

	public TreeMap getPropertiesForPrefix(String name, Current current)
	{
		return new TreeMap(_properties.getPropertiesForPrefix(name));
	}

	public volatile Map getPropertiesForPrefix(String x0, Current x1)
	{
		return getPropertiesForPrefix(x0, x1);
	}
}
