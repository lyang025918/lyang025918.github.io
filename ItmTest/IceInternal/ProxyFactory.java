// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ProxyFactory.java

package IceInternal;

import Ice.*;
import java.util.HashMap;
import java.util.Map;

// Referenced classes of package IceInternal:
//			Instance, ReferenceFactory, Reference, RouterInfo, 
//			TraceLevels, LocatorInfo, BasicStream

public final class ProxyFactory
{

	private Instance _instance;
	private int _retryIntervals[];
	static final boolean $assertionsDisabled = !IceInternal/ProxyFactory.desiredAssertionStatus();

	public ObjectPrx stringToProxy(String str)
	{
		Reference ref = _instance.referenceFactory().create(str, null);
		return referenceToProxy(ref);
	}

	public String proxyToString(ObjectPrx proxy)
	{
		if (proxy != null)
		{
			ObjectPrxHelperBase h = (ObjectPrxHelperBase)proxy;
			return h.__reference().toString();
		} else
		{
			return "";
		}
	}

	public ObjectPrx propertyToProxy(String prefix)
	{
		String proxy = _instance.initializationData().properties.getProperty(prefix);
		Reference ref = _instance.referenceFactory().create(proxy, prefix);
		return referenceToProxy(ref);
	}

	public Map proxyToProperty(ObjectPrx proxy, String prefix)
	{
		if (proxy != null)
		{
			ObjectPrxHelperBase h = (ObjectPrxHelperBase)proxy;
			return h.__reference().toProperty(prefix);
		} else
		{
			return new HashMap();
		}
	}

	public ObjectPrx streamToProxy(BasicStream s)
	{
		Identity ident = new Identity();
		ident.__read(s);
		Reference ref = _instance.referenceFactory().create(ident, s);
		return referenceToProxy(ref);
	}

	public ObjectPrx referenceToProxy(Reference ref)
	{
		if (ref != null)
		{
			ObjectPrxHelperBase proxy = new ObjectPrxHelperBase();
			proxy.setup(ref);
			return proxy;
		} else
		{
			return null;
		}
	}

	public void proxyToStream(ObjectPrx proxy, BasicStream s)
	{
		if (proxy != null)
		{
			ObjectPrxHelperBase h = (ObjectPrxHelperBase)proxy;
			Reference ref = h.__reference();
			ref.getIdentity().__write(s);
			ref.streamWrite(s);
		} else
		{
			Identity ident = new Identity();
			ident.name = "";
			ident.category = "";
			ident.__write(s);
		}
	}

	public int checkRetryAfterException(LocalException ex, Reference ref, IntHolder sleepInterval, int cnt)
	{
		TraceLevels traceLevels = _instance.traceLevels();
		Logger logger = _instance.initializationData().logger;
		if (ref.getMode() == 2 || ref.getMode() == 4)
			throw ex;
		if (ex instanceof ObjectNotExistException)
		{
			ObjectNotExistException one = (ObjectNotExistException)ex;
			if (ref.getRouterInfo() != null && one.operation.equals("ice_add_proxy"))
			{
				ref.getRouterInfo().clearCache(ref);
				if (traceLevels.retry >= 1)
				{
					String s = (new StringBuilder()).append("retrying operation call to add proxy to router\n").append(ex.toString()).toString();
					logger.trace(traceLevels.retryCat, s);
				}
				if (sleepInterval != null)
					sleepInterval.value = 0;
				return cnt;
			}
			if (ref.isIndirect())
			{
				if (ref.isWellKnown())
				{
					LocatorInfo li = ref.getLocatorInfo();
					if (li != null)
						li.clearCache(ref);
				}
			} else
			{
				throw ex;
			}
		} else
		if (ex instanceof RequestFailedException)
			throw ex;
		if (ex instanceof MarshalException)
			throw ex;
		cnt++;
		if (!$assertionsDisabled && cnt <= 0)
			throw new AssertionError();
		int interval;
		if (cnt == _retryIntervals.length + 1 && (ex instanceof CloseConnectionException))
		{
			interval = 0;
		} else
		{
			if (cnt > _retryIntervals.length)
			{
				if (traceLevels.retry >= 1)
				{
					String s = (new StringBuilder()).append("cannot retry operation call because retry limit has been exceeded\n").append(ex.toString()).toString();
					logger.trace(traceLevels.retryCat, s);
				}
				throw ex;
			}
			interval = _retryIntervals[cnt - 1];
		}
		if (traceLevels.retry >= 1)
		{
			String s = "retrying operation call";
			if (interval > 0)
				s = (new StringBuilder()).append(s).append(" in ").append(interval).append("ms").toString();
			s = (new StringBuilder()).append(s).append(" because of exception\n").append(ex).toString();
			logger.trace(traceLevels.retryCat, s);
		}
		if (sleepInterval != null)
			sleepInterval.value = interval;
		else
		if (interval > 0)
			try
			{
				Thread.sleep(interval);
			}
			catch (InterruptedException ex1) { }
		return cnt;
	}

	ProxyFactory(Instance instance)
	{
		_instance = instance;
		String arr[] = _instance.initializationData().properties.getPropertyAsList("Ice.RetryIntervals");
		if (arr.length > 0)
		{
			_retryIntervals = new int[arr.length];
			int i = 0;
			do
			{
				if (i >= arr.length)
					break;
				int v;
				try
				{
					v = Integer.parseInt(arr[i]);
				}
				catch (NumberFormatException ex)
				{
					v = 0;
				}
				if (i == 0 && v == -1)
				{
					_retryIntervals = new int[0];
					break;
				}
				_retryIntervals[i] = v <= 0 ? 0 : v;
				i++;
			} while (true);
		} else
		{
			_retryIntervals = new int[1];
			_retryIntervals[0] = 0;
		}
	}

}
