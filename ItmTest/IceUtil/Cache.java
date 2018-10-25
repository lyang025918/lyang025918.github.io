// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Cache.java

package IceUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

// Referenced classes of package IceUtil:
//			Store

public class Cache
{
	private static class CacheValue
	{

		Object obj;
		CountDownLatch latch;

		CacheValue()
		{
			obj = null;
			latch = null;
		}

		CacheValue(Object obj)
		{
			this.obj = null;
			latch = null;
			this.obj = obj;
		}
	}


	private final Map _map = new HashMap();
	private final Store _store;
	static final boolean $assertionsDisabled = !IceUtil/Cache.desiredAssertionStatus();

	public Cache(Store store)
	{
		_store = store;
	}

	public Object getIfPinned(Object key)
	{
		Map map = _map;
		JVM INSTR monitorenter ;
		CacheValue val = (CacheValue)_map.get(key);
		return val != null ? val.obj : null;
		Exception exception;
		exception;
		throw exception;
	}

	public Object unpin(Object key)
	{
		Map map = _map;
		JVM INSTR monitorenter ;
		CacheValue val = (CacheValue)_map.remove(key);
		return val != null ? val.obj : null;
		Exception exception;
		exception;
		throw exception;
	}

	public void clear()
	{
		synchronized (_map)
		{
			_map.clear();
		}
	}

	public int size()
	{
		Map map = _map;
		JVM INSTR monitorenter ;
		return _map.size();
		Exception exception;
		exception;
		throw exception;
	}

	public Object pin(Object key, Object o)
	{
		Map map = _map;
		JVM INSTR monitorenter ;
		CacheValue existingVal;
		existingVal = (CacheValue)_map.put(key, new CacheValue(o));
		if (existingVal == null)
			break MISSING_BLOCK_LABEL_56;
		_map.put(key, existingVal);
		return existingVal.obj;
		null;
		map;
		JVM INSTR monitorexit ;
		return;
		Exception exception;
		exception;
		throw exception;
	}

	public Object pin(Object key)
	{
		return pinImpl(key, null);
	}

	public Object putIfAbsent(Object key, Object newObj)
	{
		return pinImpl(key, newObj);
	}

	private Object pinImpl(Object key, Object newObj)
	{
_L3:
		CacheValue val = null;
		CountDownLatch latch = null;
		Map map = _map;
		JVM INSTR monitorenter ;
		val = (CacheValue)_map.get(key);
		if (val == null)
		{
			val = new CacheValue();
			_map.put(key, val);
			break MISSING_BLOCK_LABEL_94;
		}
		if (val.obj != null)
			return val.obj;
		if (val.latch == null)
			val.latch = new CountDownLatch(1);
		latch = val.latch;
		map;
		JVM INSTR monitorexit ;
		  goto _L1
		Exception exception;
		exception;
		throw exception;
_L1:
		if (latch == null)
			break; /* Loop/switch isn't completed */
		try
		{
			latch.await();
		}
		catch (InterruptedException e) { }
		if (true) goto _L3; else goto _L2
_L2:
		Object obj;
		try
		{
			obj = _store.load(key);
		}
		catch (RuntimeException e)
		{
			synchronized (_map)
			{
				_map.remove(key);
				latch = val.latch;
				val.latch = null;
			}
			if (latch != null)
			{
				latch.countDown();
				if (!$assertionsDisabled && latch.getCount() != 0L)
					throw new AssertionError();
			}
			throw e;
		}
		synchronized (_map)
		{
			if (obj != null)
				val.obj = obj;
			else
			if (newObj == null)
				_map.remove(key);
			else
				val.obj = newObj;
			latch = val.latch;
			val.latch = null;
		}
		if (latch != null)
		{
			latch.countDown();
			if (!$assertionsDisabled && latch.getCount() != 0L)
				throw new AssertionError();
		}
		return obj;
	}

}
