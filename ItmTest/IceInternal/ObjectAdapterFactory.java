// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ObjectAdapterFactory.java

package IceInternal;

import Ice.*;
import IceUtilInternal.Assert;
import java.util.*;

// Referenced classes of package IceInternal:
//			Instance, CommunicatorBatchOutgoingAsync

public final class ObjectAdapterFactory
{

	private Instance _instance;
	private Communicator _communicator;
	private Set _adapterNamesInUse;
	private List _adapters;

	public void shutdown()
	{
label0:
		{
			synchronized (this)
			{
				if (_instance != null)
					break label0;
			}
			return;
		}
		List adapters;
		_instance = null;
		_communicator = null;
		adapters = new LinkedList(_adapters);
		notifyAll();
		objectadapterfactory;
		JVM INSTR monitorexit ;
		  goto _L1
		exception;
		throw exception;
_L1:
		ObjectAdapterI adapter;
		for (Iterator i$ = adapters.iterator(); i$.hasNext(); adapter.deactivate())
			adapter = (ObjectAdapterI)i$.next();

		return;
	}

	public void waitForShutdown()
	{
		List adapters;
		synchronized (this)
		{
			while (_instance != null) 
				try
				{
					wait();
				}
				catch (InterruptedException ex) { }
			adapters = new LinkedList(_adapters);
		}
		ObjectAdapterI adapter;
		for (Iterator i$ = adapters.iterator(); i$.hasNext(); adapter.waitForDeactivate())
			adapter = (ObjectAdapterI)i$.next();

	}

	public synchronized boolean isShutdown()
	{
		return _instance == null;
	}

	public void destroy()
	{
		waitForShutdown();
		List adapters;
		synchronized (this)
		{
			adapters = new LinkedList(_adapters);
		}
		ObjectAdapterI adapter;
		for (Iterator i$ = adapters.iterator(); i$.hasNext(); adapter.destroy())
			adapter = (ObjectAdapterI)i$.next();

		synchronized (this)
		{
			_adapters.clear();
		}
	}

	public synchronized ObjectAdapter createObjectAdapter(String name, RouterPrx router)
	{
		if (_instance == null)
			throw new ObjectAdapterDeactivatedException();
		ObjectAdapterI adapter = null;
		if (name.length() == 0)
		{
			String uuid = UUID.randomUUID().toString();
			adapter = new ObjectAdapterI(_instance, _communicator, this, uuid, null, true);
		} else
		{
			if (_adapterNamesInUse.contains(name))
				throw new AlreadyRegisteredException("object adapter", name);
			adapter = new ObjectAdapterI(_instance, _communicator, this, name, router, false);
			_adapterNamesInUse.add(name);
		}
		_adapters.add(adapter);
		return adapter;
	}

	public ObjectAdapter findObjectAdapter(ObjectPrx proxy)
	{
		Iterator i$ = this;
		JVM INSTR monitorenter ;
		if (_instance == null)
			return null;
		List adapters = new LinkedList(_adapters);
		i$;
		JVM INSTR monitorexit ;
		  goto _L1
		Exception exception;
		exception;
		throw exception;
_L1:
		i$ = adapters.iterator();
_L3:
		ObjectAdapterI adapter;
		if (!i$.hasNext())
			break; /* Loop/switch isn't completed */
		adapter = (ObjectAdapterI)i$.next();
		if (adapter.isLocal(proxy))
			return adapter;
		continue; /* Loop/switch isn't completed */
		ObjectAdapterDeactivatedException ex;
		ex;
		if (true) goto _L3; else goto _L2
_L2:
		return null;
	}

	public synchronized void removeObjectAdapter(ObjectAdapter adapter)
	{
		if (_instance == null)
		{
			return;
		} else
		{
			_adapters.remove(adapter);
			_adapterNamesInUse.remove(adapter.getName());
			return;
		}
	}

	public void flushAsyncBatchRequests(CommunicatorBatchOutgoingAsync outAsync)
	{
		List adapters;
		synchronized (this)
		{
			adapters = new LinkedList(_adapters);
		}
		ObjectAdapterI adapter;
		for (Iterator i$ = adapters.iterator(); i$.hasNext(); adapter.flushAsyncBatchRequests(outAsync))
			adapter = (ObjectAdapterI)i$.next();

	}

	ObjectAdapterFactory(Instance instance, Communicator communicator)
	{
		_adapterNamesInUse = new HashSet();
		_adapters = new LinkedList();
		_instance = instance;
		_communicator = communicator;
	}

	protected synchronized void finalize()
		throws Throwable
	{
		Assert.FinalizerAssert(_instance == null);
		Assert.FinalizerAssert(_communicator == null);
		super.finalize();
	}
}
