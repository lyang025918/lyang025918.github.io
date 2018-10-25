// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RouterInfo.java

package IceInternal;

import Ice.*;
import java.util.*;

// Referenced classes of package IceInternal:
//			EndpointI, Reference

public final class RouterInfo
{
	static interface AddProxyCallback
	{

		public abstract void addedProxy();

		public abstract void setException(LocalException localexception);
	}

	static interface GetClientEndpointsCallback
	{

		public abstract void setEndpoints(EndpointI aendpointi[]);

		public abstract void setException(LocalException localexception);
	}


	private final RouterPrx _router;
	private EndpointI _clientEndpoints[];
	private EndpointI _serverEndpoints[];
	private ObjectAdapter _adapter;
	private Set _identities;
	private List _evictedIdentities;
	static final boolean $assertionsDisabled = !IceInternal/RouterInfo.desiredAssertionStatus();

	RouterInfo(RouterPrx router)
	{
		_identities = new HashSet();
		_evictedIdentities = new ArrayList();
		_router = router;
		if (!$assertionsDisabled && _router == null)
			throw new AssertionError();
		else
			return;
	}

	public synchronized void destroy()
	{
		_clientEndpoints = new EndpointI[0];
		_serverEndpoints = new EndpointI[0];
		_adapter = null;
		_identities.clear();
	}

	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj instanceof RouterInfo)
			return _router.equals(((RouterInfo)obj)._router);
		else
			return false;
	}

	public int hashCode()
	{
		return _router.hashCode();
	}

	public RouterPrx getRouter()
	{
		return _router;
	}

	public EndpointI[] getClientEndpoints()
	{
		RouterInfo routerinfo = this;
		JVM INSTR monitorenter ;
		if (_clientEndpoints != null)
			return _clientEndpoints;
		routerinfo;
		JVM INSTR monitorexit ;
		  goto _L1
		Exception exception;
		exception;
		throw exception;
_L1:
		return setClientEndpoints(_router.getClientProxy());
	}

	public void getClientEndpoints(final GetClientEndpointsCallback callback)
	{
		EndpointI clientEndpoints[] = null;
		synchronized (this)
		{
			clientEndpoints = _clientEndpoints;
		}
		if (clientEndpoints != null)
		{
			callback.setEndpoints(clientEndpoints);
			return;
		} else
		{
			_router.getClientProxy_async(new AMI_Router_getClientProxy() {

				final GetClientEndpointsCallback val$callback;
				final RouterInfo this$0;

				public void ice_response(ObjectPrx clientProxy)
				{
					callback.setEndpoints(setClientEndpoints(clientProxy));
				}

				public void ice_exception(LocalException ex)
				{
					if (ex instanceof CollocationOptimizationException)
						try
						{
							callback.setEndpoints(getClientEndpoints());
						}
						catch (LocalException e)
						{
							callback.setException(e);
						}
					else
						callback.setException(ex);
				}

			
			{
				this$0 = RouterInfo.this;
				callback = getclientendpointscallback;
				super();
			}
			});
			return;
		}
	}

	public EndpointI[] getServerEndpoints()
	{
		RouterInfo routerinfo = this;
		JVM INSTR monitorenter ;
		if (_serverEndpoints != null)
			return _serverEndpoints;
		routerinfo;
		JVM INSTR monitorexit ;
		  goto _L1
		Exception exception;
		exception;
		throw exception;
_L1:
		return setServerEndpoints(_router.getServerProxy());
	}

	public void addProxy(ObjectPrx proxy)
	{
label0:
		{
			if (!$assertionsDisabled && proxy == null)
				throw new AssertionError();
			synchronized (this)
			{
				if (!_identities.contains(proxy.ice_getIdentity()))
					break label0;
			}
			return;
		}
		routerinfo;
		JVM INSTR monitorexit ;
		  goto _L1
		exception;
		throw exception;
_L1:
		addAndEvictProxies(proxy, _router.addProxies(new ObjectPrx[] {
			proxy
		}));
		return;
	}

	public boolean addProxy(final ObjectPrx proxy, final AddProxyCallback callback)
	{
		if (!$assertionsDisabled && proxy == null)
			throw new AssertionError();
		RouterInfo routerinfo = this;
		JVM INSTR monitorenter ;
		if (_identities.contains(proxy.ice_getIdentity()))
			return true;
		routerinfo;
		JVM INSTR monitorexit ;
		  goto _L1
		Exception exception;
		exception;
		throw exception;
_L1:
		_router.addProxies_async(new AMI_Router_addProxies() {

			final ObjectPrx val$proxy;
			final AddProxyCallback val$callback;
			final RouterInfo this$0;

			public void ice_response(ObjectPrx evictedProxies[])
			{
				addAndEvictProxies(proxy, evictedProxies);
				callback.addedProxy();
			}

			public void ice_exception(LocalException ex)
			{
				if (ex instanceof CollocationOptimizationException)
					try
					{
						addProxy(proxy);
						callback.addedProxy();
					}
					catch (LocalException e)
					{
						callback.setException(ex);
					}
				else
					callback.setException(ex);
			}

			
			{
				this$0 = RouterInfo.this;
				proxy = objectprx;
				callback = addproxycallback;
				super();
			}
		}, new ObjectPrx[] {
			proxy
		});
		return false;
	}

	public synchronized void setAdapter(ObjectAdapter adapter)
	{
		_adapter = adapter;
	}

	public synchronized ObjectAdapter getAdapter()
	{
		return _adapter;
	}

	public synchronized void clearCache(Reference ref)
	{
		_identities.remove(ref.getIdentity());
	}

	private synchronized EndpointI[] setClientEndpoints(ObjectPrx clientProxy)
	{
		if (_clientEndpoints == null)
			if (clientProxy == null)
			{
				_clientEndpoints = ((ObjectPrxHelperBase)_router).__reference().getEndpoints();
			} else
			{
				clientProxy = clientProxy.ice_router(null);
				try
				{
					clientProxy = clientProxy.ice_timeout(_router.ice_getConnection().timeout());
				}
				catch (CollocationOptimizationException ex) { }
				_clientEndpoints = ((ObjectPrxHelperBase)clientProxy).__reference().getEndpoints();
			}
		return _clientEndpoints;
	}

	private synchronized EndpointI[] setServerEndpoints(ObjectPrx serverProxy)
	{
		if (serverProxy == null)
		{
			throw new NoEndpointException();
		} else
		{
			serverProxy = serverProxy.ice_router(null);
			_serverEndpoints = ((ObjectPrxHelperBase)serverProxy).__reference().getEndpoints();
			return _serverEndpoints;
		}
	}

	private synchronized void addAndEvictProxies(ObjectPrx proxy, ObjectPrx evictedProxies[])
	{
		int index = _evictedIdentities.indexOf(proxy.ice_getIdentity());
		if (index >= 0)
			_evictedIdentities.remove(index);
		else
			_identities.add(proxy.ice_getIdentity());
		ObjectPrx arr$[] = evictedProxies;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			ObjectPrx p = arr$[i$];
			if (!_identities.remove(p.ice_getIdentity()))
				_evictedIdentities.add(p.ice_getIdentity());
		}

	}



}
