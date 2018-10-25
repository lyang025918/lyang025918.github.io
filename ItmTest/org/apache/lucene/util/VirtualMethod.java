// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   VirtualMethod.java

package org.apache.lucene.util;

import java.util.*;

// Referenced classes of package org.apache.lucene.util:
//			WeakIdentityMap

public final class VirtualMethod
{

	private static final Set singletonSet = Collections.synchronizedSet(new HashSet());
	private final Class baseClass;
	private final String method;
	private final Class parameters[];
	private final WeakIdentityMap cache = WeakIdentityMap.newConcurrentHashMap();

	public transient VirtualMethod(Class baseClass, String method, Class parameters[])
	{
		this.baseClass = baseClass;
		this.method = method;
		this.parameters = parameters;
		try
		{
			if (!singletonSet.add(baseClass.getDeclaredMethod(method, parameters)))
				throw new UnsupportedOperationException("VirtualMethod instances must be singletons and therefore assigned to static final members in the same class, they use as baseClass ctor param.");
		}
		catch (NoSuchMethodException nsme)
		{
			throw new IllegalArgumentException((new StringBuilder()).append(baseClass.getName()).append(" has no such method: ").append(nsme.getMessage()).toString());
		}
	}

	public int getImplementationDistance(Class subclazz)
	{
		Integer distance = (Integer)cache.get(subclazz);
		if (distance == null)
			cache.put(subclazz, distance = Integer.valueOf(reflectImplementationDistance(subclazz)));
		return distance.intValue();
	}

	public boolean isOverriddenAsOf(Class subclazz)
	{
		return getImplementationDistance(subclazz) > 0;
	}

	private int reflectImplementationDistance(Class subclazz)
	{
		if (!baseClass.isAssignableFrom(subclazz))
			throw new IllegalArgumentException((new StringBuilder()).append(subclazz.getName()).append(" is not a subclass of ").append(baseClass.getName()).toString());
		boolean overridden = false;
		int distance = 0;
		for (Class clazz = subclazz; clazz != baseClass && clazz != null; clazz = clazz.getSuperclass())
		{
			if (!overridden)
				try
				{
					clazz.getDeclaredMethod(method, parameters);
					overridden = true;
				}
				catch (NoSuchMethodException nsme) { }
			if (overridden)
				distance++;
		}

		return distance;
	}

	public static int compareImplementationDistance(Class clazz, VirtualMethod m1, VirtualMethod m2)
	{
		return Integer.valueOf(m1.getImplementationDistance(clazz)).compareTo(Integer.valueOf(m2.getImplementationDistance(clazz)));
	}

}
