// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AbstractQueryConfig.java

package org.apache.lucene.queryparser.flexible.core.config;

import java.util.HashMap;

// Referenced classes of package org.apache.lucene.queryparser.flexible.core.config:
//			ConfigurationKey

public abstract class AbstractQueryConfig
{

	private final HashMap configMap = new HashMap();

	AbstractQueryConfig()
	{
	}

	public Object get(ConfigurationKey key)
	{
		if (key == null)
			throw new IllegalArgumentException("key cannot be null!");
		else
			return configMap.get(key);
	}

	public boolean has(ConfigurationKey key)
	{
		if (key == null)
			throw new IllegalArgumentException("key cannot be null!");
		else
			return configMap.containsKey(key);
	}

	public void set(ConfigurationKey key, Object value)
	{
		if (key == null)
			throw new IllegalArgumentException("key cannot be null!");
		if (value == null)
			unset(key);
		else
			configMap.put(key, value);
	}

	public boolean unset(ConfigurationKey key)
	{
		if (key == null)
			throw new IllegalArgumentException("key cannot be null!");
		else
			return configMap.remove(key) != null;
	}
}
