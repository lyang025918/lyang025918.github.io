// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AnalysisSPILoader.java

package org.apache.lucene.analysis.util;

import java.util.*;
import org.apache.lucene.util.SPIClassIterator;

// Referenced classes of package org.apache.lucene.analysis.util:
//			AbstractAnalysisFactory

final class AnalysisSPILoader
{

	private volatile Map services;
	private final Class clazz;
	private final String suffixes[];

	public AnalysisSPILoader(Class clazz)
	{
		this(clazz, new String[] {
			clazz.getSimpleName()
		});
	}

	public AnalysisSPILoader(Class clazz, ClassLoader loader)
	{
		this(clazz, new String[] {
			clazz.getSimpleName()
		}, loader);
	}

	public AnalysisSPILoader(Class clazz, String suffixes[])
	{
		this(clazz, suffixes, Thread.currentThread().getContextClassLoader());
	}

	public AnalysisSPILoader(Class clazz, String suffixes[], ClassLoader classloader)
	{
		services = Collections.emptyMap();
		this.clazz = clazz;
		this.suffixes = suffixes;
		reload(classloader);
	}

	public void reload(ClassLoader classloader)
	{
		SPIClassIterator loader = SPIClassIterator.get(clazz, classloader);
		LinkedHashMap services = new LinkedHashMap();
		do
		{
			if (!loader.hasNext())
				break;
			Class service = loader.next();
			String clazzName = service.getSimpleName();
			String name = null;
			String arr$[] = suffixes;
			int len$ = arr$.length;
			int i$ = 0;
			do
			{
				if (i$ >= len$)
					break;
				String suffix = arr$[i$];
				if (clazzName.endsWith(suffix))
				{
					name = clazzName.substring(0, clazzName.length() - suffix.length()).toLowerCase(Locale.ROOT);
					break;
				}
				i$++;
			} while (true);
			if (name == null)
				throw new ServiceConfigurationError((new StringBuilder()).append("The class name ").append(service.getName()).append(" has wrong suffix, allowed are: ").append(Arrays.toString(suffixes)).toString());
			if (!services.containsKey(name))
				services.put(name, service);
		} while (true);
		this.services = Collections.unmodifiableMap(services);
	}

	public AbstractAnalysisFactory newInstance(String name)
	{
		Class service = lookupClass(name);
		return (AbstractAnalysisFactory)service.newInstance();
		Exception e;
		e;
		throw new IllegalArgumentException((new StringBuilder()).append("SPI class of type ").append(clazz.getName()).append(" with name '").append(name).append("' cannot be instantiated. ").append("This is likely due to a misconfiguration of the java class '").append(service.getName()).append("': ").toString(), e);
	}

	public Class lookupClass(String name)
	{
		Class service = (Class)services.get(name.toLowerCase(Locale.ROOT));
		if (service != null)
			return service;
		else
			throw new IllegalArgumentException((new StringBuilder()).append("A SPI class of type ").append(clazz.getName()).append(" with name '").append(name).append("' does not exist. ").append("You need to add the corresponding JAR file supporting this SPI to your classpath.").append("The current classpath supports the following names: ").append(availableServices()).toString());
	}

	public Set availableServices()
	{
		return services.keySet();
	}
}
