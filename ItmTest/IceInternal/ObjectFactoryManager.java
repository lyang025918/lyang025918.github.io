// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ObjectFactoryManager.java

package IceInternal;

import Ice.*;
import java.util.*;

public final class ObjectFactoryManager
{

	private Map _factoryMap;

	public synchronized void add(ObjectFactory factory, String id)
	{
		Object o = _factoryMap.get(id);
		if (o != null)
		{
			AlreadyRegisteredException ex = new AlreadyRegisteredException();
			ex.id = id;
			ex.kindOfObject = "object factory";
			throw ex;
		} else
		{
			_factoryMap.put(id, factory);
			return;
		}
	}

	public void remove(String id)
	{
		ObjectFactory factory = null;
		synchronized (this)
		{
			factory = (ObjectFactory)_factoryMap.get(id);
			if (factory == null)
			{
				NotRegisteredException ex = new NotRegisteredException();
				ex.id = id;
				ex.kindOfObject = "object factory";
				throw ex;
			}
			_factoryMap.remove(id);
		}
		factory.destroy();
	}

	public synchronized ObjectFactory find(String id)
	{
		return (ObjectFactory)_factoryMap.get(id);
	}

	ObjectFactoryManager()
	{
		_factoryMap = new HashMap();
	}

	void destroy()
	{
		Map oldMap = null;
		synchronized (this)
		{
			oldMap = _factoryMap;
			_factoryMap = new HashMap();
		}
		ObjectFactory factory;
		for (Iterator i$ = oldMap.values().iterator(); i$.hasNext(); factory.destroy())
			factory = (ObjectFactory)i$.next();

	}
}
