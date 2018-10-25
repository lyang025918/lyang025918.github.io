// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ImplicitContextI.java

package Ice;

import IceInternal.BasicStream;
import java.util.*;

// Referenced classes of package Ice:
//			InitializationException, ImplicitContext, ContextHelper

public abstract class ImplicitContextI
	implements ImplicitContext
{
	static class PerThread extends ImplicitContextI
	{

		private Map _map;

		public Map getContext()
		{
			Map threadContext = (Map)_map.get(Thread.currentThread());
			if (threadContext == null)
				threadContext = new HashMap();
			return threadContext;
		}

		public void setContext(Map context)
		{
			if (context == null || context.isEmpty())
			{
				_map.remove(Thread.currentThread());
			} else
			{
				Map threadContext = new HashMap(context);
				_map.put(Thread.currentThread(), threadContext);
			}
		}

		public boolean containsKey(String key)
		{
			if (key == null)
				key = "";
			Map threadContext = (Map)_map.get(Thread.currentThread());
			if (threadContext == null)
				return false;
			else
				return threadContext.containsKey(key);
		}

		public String get(String key)
		{
			if (key == null)
				key = "";
			Map threadContext = (Map)_map.get(Thread.currentThread());
			if (threadContext == null)
				return "";
			String val = (String)threadContext.get(key);
			if (val == null)
				val = "";
			return val;
		}

		public String put(String key, String value)
		{
			if (key == null)
				key = "";
			if (value == null)
				value = "";
			Thread currentThread = Thread.currentThread();
			Map threadContext = (Map)_map.get(currentThread);
			if (threadContext == null)
			{
				threadContext = new HashMap();
				_map.put(currentThread, threadContext);
			}
			String oldVal = (String)threadContext.put(key, value);
			if (oldVal == null)
				oldVal = "";
			return oldVal;
		}

		public String remove(String key)
		{
			if (key == null)
				key = "";
			Map threadContext = (Map)_map.get(Thread.currentThread());
			if (threadContext == null)
				return null;
			String val = (String)threadContext.remove(key);
			if (val == null)
				val = "";
			return val;
		}

		public void write(Map prxContext, BasicStream os)
		{
			Map threadContext = (Map)_map.get(Thread.currentThread());
			if (threadContext == null || threadContext.isEmpty())
				ContextHelper.write(os, prxContext);
			else
			if (prxContext.isEmpty())
			{
				ContextHelper.write(os, threadContext);
			} else
			{
				Map combined = new HashMap(threadContext);
				combined.putAll(prxContext);
				ContextHelper.write(os, combined);
			}
		}

		Map combine(Map prxContext)
		{
			Map threadContext = (Map)_map.get(Thread.currentThread());
			Map combined = new HashMap(threadContext);
			combined.putAll(prxContext);
			return combined;
		}

		PerThread()
		{
			_map = Collections.synchronizedMap(new HashMap());
		}
	}

	static class Shared extends ImplicitContextI
	{

		private Map _context;

		public synchronized Map getContext()
		{
			return new HashMap(_context);
		}

		public synchronized void setContext(Map context)
		{
			_context.clear();
			if (context != null && !context.isEmpty())
				_context.putAll(context);
		}

		public synchronized boolean containsKey(String key)
		{
			if (key == null)
				key = "";
			return _context.containsKey(key);
		}

		public synchronized String get(String key)
		{
			if (key == null)
				key = "";
			String val = (String)_context.get(key);
			if (val == null)
				val = "";
			return val;
		}

		public synchronized String put(String key, String value)
		{
			if (key == null)
				key = "";
			if (value == null)
				value = "";
			String oldVal = (String)_context.put(key, value);
			if (oldVal == null)
				oldVal = "";
			return oldVal;
		}

		public synchronized String remove(String key)
		{
			if (key == null)
				key = "";
			String val = (String)_context.remove(key);
			if (val == null)
				val = "";
			return val;
		}

		public void write(Map prxContext, BasicStream os)
		{
			if (prxContext.isEmpty())
			{
				synchronized (this)
				{
					ContextHelper.write(os, _context);
				}
			} else
			{
				Map ctx = null;
				synchronized (this)
				{
					ctx = _context.isEmpty() ? prxContext : combine(prxContext);
				}
				ContextHelper.write(os, ctx);
			}
		}

		synchronized Map combine(Map prxContext)
		{
			Map combined = new HashMap(_context);
			combined.putAll(prxContext);
			return combined;
		}

		Shared()
		{
			_context = new HashMap();
		}
	}


	public ImplicitContextI()
	{
	}

	public static ImplicitContextI create(String kind)
	{
		if (kind.equals("None") || kind.equals(""))
			return null;
		if (kind.equals("Shared"))
			return new Shared();
		if (kind.equals("PerThread"))
			return new PerThread();
		else
			throw new InitializationException((new StringBuilder()).append("'").append(kind).append("' is not a valid value for Ice.ImplicitContext").toString());
	}

	public abstract void write(Map map, BasicStream basicstream);

	abstract Map combine(Map map);
}
