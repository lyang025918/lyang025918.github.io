// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NamedSPILoader.java

package org.apache.lucene.util;

import java.util.*;

// Referenced classes of package org.apache.lucene.util:
//			SPIClassIterator

public final class NamedSPILoader
	implements Iterable
{
	public static interface NamedSPI
	{

		public abstract String getName();
	}


	private volatile Map services;
	private final Class clazz;

	public NamedSPILoader(Class clazz)
	{
		this(clazz, Thread.currentThread().getContextClassLoader());
	}

	public NamedSPILoader(Class clazz, ClassLoader classloader)
	{
		services = Collections.emptyMap();
		this.clazz = clazz;
		reload(classloader);
	}

	public void reload(ClassLoader classloader)
	{
		LinkedHashMap services = new LinkedHashMap(this.services);
		for (SPIClassIterator loader = SPIClassIterator.get(clazz, classloader); loader.hasNext();)
		{
			Class c = loader.next();
			try
			{
				NamedSPI service = (NamedSPI)c.newInstance();
				String name = service.getName();
				if (!services.containsKey(name))
				{
					checkServiceName(name);
					services.put(name, service);
				}
			}
			catch (Exception e)
			{
				throw new ServiceConfigurationError((new StringBuilder()).append("Cannot instantiate SPI class: ").append(c.getName()).toString(), e);
			}
		}

		this.services = Collections.unmodifiableMap(services);
	}

	public static void checkServiceName(String name)
	{
		if (name.length() >= 128)
			throw new IllegalArgumentException((new StringBuilder()).append("Illegal service name: '").append(name).append("' is too long (must be < 128 chars).").toString());
		int i = 0;
		for (int len = name.length(); i < len; i++)
		{
			char c = name.charAt(i);
			if (!isLetterOrDigit(c))
				throw new IllegalArgumentException((new StringBuilder()).append("Illegal service name: '").append(name).append("' must be simple ascii alphanumeric.").toString());
		}

	}

	private static boolean isLetterOrDigit(char c)
	{
		return 'a' <= c && c <= 'z' || 'A' <= c && c <= 'Z' || '0' <= c && c <= '9';
	}

	public NamedSPI lookup(String name)
	{
		NamedSPI service = (NamedSPI)services.get(name);
		if (service != null)
			return service;
		else
			throw new IllegalArgumentException((new StringBuilder()).append("A SPI class of type ").append(clazz.getName()).append(" with name '").append(name).append("' does not exist. ").append("You need to add the corresponding JAR file supporting this SPI to your classpath.").append("The current classpath supports the following names: ").append(availableServices()).toString());
	}

	public Set availableServices()
	{
		return services.keySet();
	}

	public Iterator iterator()
	{
		return services.values().iterator();
	}
}
