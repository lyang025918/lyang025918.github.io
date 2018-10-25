// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LocatorInfo.java

package IceInternal;

import Ice.*;
import java.util.*;

// Referenced classes of package IceInternal:
//			Reference, LocatorTable, Instance, TraceLevels, 
//			EndpointI

public final class LocatorInfo
{
	private class AdapterRequest extends Request
	{

		static final boolean $assertionsDisabled = !IceInternal/LocatorInfo.desiredAssertionStatus();
		final LocatorInfo this$0;

		protected void send(boolean async)
		{
			try
			{
				if (async)
					_locatorInfo.getLocator().findAdapterById_async(new AMI_Locator_findAdapterById() {

						final AdapterRequest this$1;

						public void ice_response(ObjectPrx proxy)
						{
							response(proxy);
						}

						public void ice_exception(UserException ex)
						{
							exception(ex);
						}

						public void ice_exception(LocalException ex)
						{
							exception(ex);
						}

				
				{
					this$1 = AdapterRequest.this;
					super();
				}
					}, _ref.getAdapterId());
				else
					response(_locatorInfo.getLocator().findAdapterById(_ref.getAdapterId()));
			}
			catch (Exception ex)
			{
				exception(ex);
			}
		}


		public AdapterRequest(LocatorInfo locatorInfo, Reference reference)
		{
			this$0 = LocatorInfo.this;
			super(locatorInfo, reference);
			if (!$assertionsDisabled && !reference.isIndirect())
				throw new AssertionError();
			else
				return;
		}
	}

	private class ObjectRequest extends Request
	{

		static final boolean $assertionsDisabled = !IceInternal/LocatorInfo.desiredAssertionStatus();
		final LocatorInfo this$0;

		protected void send(boolean async)
		{
			try
			{
				if (async)
					_locatorInfo.getLocator().findObjectById_async(new AMI_Locator_findObjectById() {

						final ObjectRequest this$1;

						public void ice_response(ObjectPrx proxy)
						{
							response(proxy);
						}

						public void ice_exception(UserException ex)
						{
							exception(ex);
						}

						public void ice_exception(LocalException ex)
						{
							exception(ex);
						}

				
				{
					this$1 = ObjectRequest.this;
					super();
				}
					}, _ref.getIdentity());
				else
					response(_locatorInfo.getLocator().findObjectById(_ref.getIdentity()));
			}
			catch (Exception ex)
			{
				exception(ex);
			}
		}


		public ObjectRequest(LocatorInfo locatorInfo, Reference reference)
		{
			this$0 = LocatorInfo.this;
			super(locatorInfo, reference);
			if (!$assertionsDisabled && !reference.isWellKnown())
				throw new AssertionError();
			else
				return;
		}
	}

	private abstract class Request
	{

		protected final LocatorInfo _locatorInfo;
		protected final Reference _ref;
		private List _callbacks;
		private List _wellKnownRefs;
		private boolean _sent;
		private boolean _response;
		private ObjectPrx _proxy;
		private Exception _exception;
		static final boolean $assertionsDisabled = !IceInternal/LocatorInfo.desiredAssertionStatus();
		final LocatorInfo this$0;

		public synchronized void addCallback(Reference ref, Reference wellKnownRef, int ttl, GetEndpointsCallback cb)
		{
			RequestCallback callback = new RequestCallback(ref, ttl, cb);
			if (_response)
				callback.response(_locatorInfo, _proxy);
			else
			if (_exception != null)
			{
				callback.exception(_locatorInfo, _exception);
			} else
			{
				_callbacks.add(callback);
				if (wellKnownRef != null)
					_wellKnownRefs.add(wellKnownRef);
				if (!_sent)
				{
					_sent = true;
					send(true);
				}
			}
		}

		synchronized EndpointI[] getEndpoints(Reference ref, Reference wellKnownRef, int ttl, BooleanHolder cached)
		{
			if (!_response || _exception == null)
			{
				if (wellKnownRef != null)
					_wellKnownRefs.add(wellKnownRef);
				if (!_sent)
				{
					_sent = true;
					send(true);
				}
				while (!_response && _exception == null) 
					try
					{
						wait();
					}
					catch (InterruptedException ex) { }
			}
			if (_exception != null)
				_locatorInfo.getEndpointsException(ref, _exception);
			if (!$assertionsDisabled && !_response)
				throw new AssertionError();
			EndpointI endpoints[] = null;
			if (_proxy != null)
			{
				Reference r = ((ObjectPrxHelperBase)_proxy).__reference();
				if (!r.isIndirect())
					endpoints = r.getEndpoints();
				else
				if (ref.isWellKnown() && !r.isWellKnown())
					return _locatorInfo.getEndpoints(r, ref, ttl, cached);
			}
			cached.value = false;
			if (_ref.getInstance().traceLevels().location >= 1)
				_locatorInfo.getEndpointsTrace(ref, endpoints, false);
			return endpoints != null ? endpoints : new EndpointI[0];
		}

		protected synchronized void response(ObjectPrx proxy)
		{
			_locatorInfo.finishRequest(_ref, _wellKnownRefs, proxy, false);
			_response = true;
			_proxy = proxy;
			RequestCallback callback;
			for (Iterator i$ = _callbacks.iterator(); i$.hasNext(); callback.response(_locatorInfo, proxy))
				callback = (RequestCallback)i$.next();

			notifyAll();
		}

		protected void exception(Exception ex)
		{
			if (ex instanceof CollocationOptimizationException)
			{
				send(false);
				return;
			}
			synchronized (this)
			{
				_locatorInfo.finishRequest(_ref, _wellKnownRefs, null, ex instanceof UserException);
				_exception = ex;
				RequestCallback callback;
				for (Iterator i$ = _callbacks.iterator(); i$.hasNext(); callback.exception(_locatorInfo, ex))
					callback = (RequestCallback)i$.next();

				notifyAll();
			}
		}

		protected abstract void send(boolean flag);


		Request(LocatorInfo locatorInfo, Reference ref)
		{
			this$0 = LocatorInfo.this;
			super();
			_callbacks = new ArrayList();
			_wellKnownRefs = new ArrayList();
			_locatorInfo = locatorInfo;
			_ref = ref;
			_sent = false;
			_response = false;
		}
	}

	private static class RequestCallback
	{

		final Reference _ref;
		final int _ttl;
		final GetEndpointsCallback _callback;

		public void response(LocatorInfo locatorInfo, ObjectPrx proxy)
		{
			EndpointI endpoints[] = null;
			if (proxy != null)
			{
				Reference r = ((ObjectPrxHelperBase)proxy).__reference();
				if (!r.isIndirect())
					endpoints = r.getEndpoints();
				else
				if (_ref.isWellKnown() && !r.isWellKnown())
				{
					locatorInfo.getEndpoints(r, _ref, _ttl, _callback);
					return;
				}
			}
			if (_ref.getInstance().traceLevels().location >= 1)
				locatorInfo.getEndpointsTrace(_ref, endpoints, false);
			if (_callback != null)
				_callback.setEndpoints(endpoints != null ? endpoints : new EndpointI[0], false);
		}

		public void exception(LocatorInfo locatorInfo, Exception exc)
		{
			try
			{
				locatorInfo.getEndpointsException(_ref, exc);
			}
			catch (LocalException ex)
			{
				if (_callback != null)
					_callback.setException(ex);
			}
		}

		RequestCallback(Reference ref, int ttl, GetEndpointsCallback cb)
		{
			_ref = ref;
			_ttl = ttl;
			_callback = cb;
		}
	}

	static interface GetEndpointsCallback
	{

		public abstract void setEndpoints(EndpointI aendpointi[], boolean flag);

		public abstract void setException(LocalException localexception);
	}


	private final LocatorPrx _locator;
	private LocatorRegistryPrx _locatorRegistry;
	private final LocatorTable _table;
	private final boolean _background;
	private Map _adapterRequests;
	private Map _objectRequests;
	static final boolean $assertionsDisabled = !IceInternal/LocatorInfo.desiredAssertionStatus();

	LocatorInfo(LocatorPrx locator, LocatorTable table, boolean background)
	{
		_adapterRequests = new HashMap();
		_objectRequests = new HashMap();
		_locator = locator;
		_table = table;
		_background = background;
	}

	public synchronized void destroy()
	{
		_locatorRegistry = null;
		_table.clear();
	}

	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj instanceof LocatorInfo)
			return _locator.equals(((LocatorInfo)obj)._locator);
		else
			return false;
	}

	public int hashCode()
	{
		return _locator.hashCode();
	}

	public LocatorPrx getLocator()
	{
		return _locator;
	}

	public LocatorRegistryPrx getLocatorRegistry()
	{
		LocatorRegistryPrx locatorRegistry = this;
		JVM INSTR monitorenter ;
		if (_locatorRegistry != null)
			return _locatorRegistry;
		locatorRegistry;
		JVM INSTR monitorexit ;
		  goto _L1
		Exception exception;
		exception;
		throw exception;
_L1:
		locatorRegistry = _locator.getRegistry();
		LocatorInfo locatorinfo = this;
		JVM INSTR monitorenter ;
		_locatorRegistry = LocatorRegistryPrxHelper.uncheckedCast(locatorRegistry.ice_locator(null));
		return _locatorRegistry;
		Exception exception1;
		exception1;
		throw exception1;
	}

	public EndpointI[] getEndpoints(Reference ref, int ttl, BooleanHolder cached)
	{
		return getEndpoints(ref, null, ttl, cached);
	}

	public EndpointI[] getEndpoints(Reference ref, Reference wellKnownRef, int ttl, BooleanHolder cached)
	{
		if (!$assertionsDisabled && !ref.isIndirect())
			throw new AssertionError();
		EndpointI endpoints[] = null;
		cached.value = false;
		if (!ref.isWellKnown())
		{
			endpoints = _table.getAdapterEndpoints(ref.getAdapterId(), ttl, cached);
			if (!cached.value)
				if (_background && endpoints != null)
					getAdapterRequest(ref).addCallback(ref, wellKnownRef, ttl, null);
				else
					return getAdapterRequest(ref).getEndpoints(ref, wellKnownRef, ttl, cached);
		} else
		{
			Reference r = _table.getObjectReference(ref.getIdentity(), ttl, cached);
			if (!cached.value)
				if (_background && r != null)
					getObjectRequest(ref).addCallback(ref, null, ttl, null);
				else
					return getObjectRequest(ref).getEndpoints(ref, null, ttl, cached);
			if (!r.isIndirect())
				endpoints = r.getEndpoints();
			else
			if (!r.isWellKnown())
				return getEndpoints(r, ref, ttl, cached);
		}
		if (!$assertionsDisabled && endpoints == null)
			throw new AssertionError();
		cached.value = true;
		if (ref.getInstance().traceLevels().location >= 1)
			getEndpointsTrace(ref, endpoints, true);
		return endpoints;
	}

	public void getEndpoints(Reference ref, int ttl, GetEndpointsCallback callback)
	{
		getEndpoints(ref, null, ttl, callback);
	}

	public void getEndpoints(Reference ref, Reference wellKnownRef, int ttl, GetEndpointsCallback callback)
	{
		if (!$assertionsDisabled && !ref.isIndirect())
			throw new AssertionError();
		EndpointI endpoints[] = null;
		BooleanHolder cached = new BooleanHolder();
		if (!ref.isWellKnown())
		{
			endpoints = _table.getAdapterEndpoints(ref.getAdapterId(), ttl, cached);
			if (!cached.value)
				if (_background && endpoints != null)
				{
					getAdapterRequest(ref).addCallback(ref, wellKnownRef, ttl, null);
				} else
				{
					getAdapterRequest(ref).addCallback(ref, wellKnownRef, ttl, callback);
					return;
				}
		} else
		{
			Reference r = _table.getObjectReference(ref.getIdentity(), ttl, cached);
			if (!cached.value)
				if (_background && r != null)
				{
					getObjectRequest(ref).addCallback(ref, null, ttl, null);
				} else
				{
					getObjectRequest(ref).addCallback(ref, null, ttl, callback);
					return;
				}
			if (!r.isIndirect())
				endpoints = r.getEndpoints();
			else
			if (!r.isWellKnown())
			{
				getEndpoints(r, ref, ttl, callback);
				return;
			}
		}
		if (!$assertionsDisabled && endpoints == null)
			throw new AssertionError();
		if (ref.getInstance().traceLevels().location >= 1)
			getEndpointsTrace(ref, endpoints, true);
		if (callback != null)
			callback.setEndpoints(endpoints, true);
	}

	public void clearCache(Reference ref)
	{
		if (!$assertionsDisabled && !ref.isIndirect())
			throw new AssertionError();
		if (!ref.isWellKnown())
		{
			EndpointI endpoints[] = _table.removeAdapterEndpoints(ref.getAdapterId());
			if (endpoints != null && ref.getInstance().traceLevels().location >= 2)
				trace("removed endpoints from locator table\n", ref, endpoints);
		} else
		{
			Reference r = _table.removeObjectReference(ref.getIdentity());
			if (r != null)
				if (!r.isIndirect())
				{
					if (ref.getInstance().traceLevels().location >= 2)
						trace("removed endpoints from locator table", ref, r.getEndpoints());
				} else
				if (!r.isWellKnown())
					clearCache(r);
		}
	}

	private void trace(String msg, Reference ref, EndpointI endpoints[])
	{
		if (!$assertionsDisabled && !ref.isIndirect())
			throw new AssertionError();
		StringBuilder s = new StringBuilder(128);
		s.append(msg);
		s.append("\n");
		if (!ref.isWellKnown())
		{
			s.append("adapter = ");
			s.append(ref.getAdapterId());
			s.append("\n");
		} else
		{
			s.append("object = ");
			s.append(ref.getInstance().identityToString(ref.getIdentity()));
			s.append("\n");
		}
		s.append("endpoints = ");
		int sz = endpoints.length;
		for (int i = 0; i < sz; i++)
		{
			s.append(endpoints[i].toString());
			if (i + 1 < sz)
				s.append(":");
		}

		ref.getInstance().initializationData().logger.trace(ref.getInstance().traceLevels().locationCat, s.toString());
	}

	private void getEndpointsException(Reference ref, Exception exc)
	{
		if (!$assertionsDisabled && !ref.isIndirect())
			throw new AssertionError();
		try
		{
			throw exc;
		}
		catch (AdapterNotFoundException ex)
		{
			Instance instance = ref.getInstance();
			if (instance.traceLevels().location >= 1)
			{
				StringBuilder s = new StringBuilder(128);
				s.append("adapter not found\n");
				s.append("adapter = ");
				s.append(ref.getAdapterId());
				instance.initializationData().logger.trace(instance.traceLevels().locationCat, s.toString());
			}
			NotRegisteredException e = new NotRegisteredException();
			e.kindOfObject = "object adapter";
			e.id = ref.getAdapterId();
			throw e;
		}
		catch (ObjectNotFoundException ex)
		{
			Instance instance = ref.getInstance();
			if (instance.traceLevels().location >= 1)
			{
				StringBuilder s = new StringBuilder(128);
				s.append("object not found\n");
				s.append("object = ");
				s.append(instance.identityToString(ref.getIdentity()));
				instance.initializationData().logger.trace(instance.traceLevels().locationCat, s.toString());
			}
			NotRegisteredException e = new NotRegisteredException();
			e.kindOfObject = "object";
			e.id = instance.identityToString(ref.getIdentity());
			throw e;
		}
		catch (NotRegisteredException ex)
		{
			throw ex;
		}
		catch (LocalException ex)
		{
			Instance instance = ref.getInstance();
			if (instance.traceLevels().location >= 1)
			{
				StringBuilder s = new StringBuilder(128);
				s.append("couldn't contact the locator to retrieve adapter endpoints\n");
				if (ref.getAdapterId().length() > 0)
				{
					s.append("adapter = ");
					s.append(ref.getAdapterId());
					s.append("\n");
				} else
				{
					s.append("object = ");
					s.append(instance.identityToString(ref.getIdentity()));
					s.append("\n");
				}
				s.append((new StringBuilder()).append("reason = ").append(ex).toString());
				instance.initializationData().logger.trace(instance.traceLevels().locationCat, s.toString());
			}
			throw ex;
		}
		catch (Exception ex) { }
		if (!$assertionsDisabled)
			throw new AssertionError();
		else
			return;
	}

	private void getEndpointsTrace(Reference ref, EndpointI endpoints[], boolean cached)
	{
		if (endpoints != null && endpoints.length > 0)
		{
			if (cached)
				trace("found endpoints in locator table", ref, endpoints);
			else
				trace("retrieved endpoints from locator, adding to locator table", ref, endpoints);
		} else
		{
			Instance instance = ref.getInstance();
			StringBuilder s = new StringBuilder(128);
			s.append("no endpoints configured for ");
			if (ref.getAdapterId().length() > 0)
			{
				s.append("adapter\n");
				s.append("adapter = ");
				s.append(ref.getAdapterId());
				s.append("\n");
			} else
			{
				s.append("object\n");
				s.append("object = ");
				s.append(instance.identityToString(ref.getIdentity()));
				s.append("\n");
			}
			instance.initializationData().logger.trace(instance.traceLevels().locationCat, s.toString());
		}
	}

	private synchronized Request getAdapterRequest(Reference ref)
	{
		if (ref.getInstance().traceLevels().location >= 1)
		{
			Instance instance = ref.getInstance();
			StringBuilder s = new StringBuilder(128);
			s.append("searching for adapter by id\n");
			s.append("adapter = ");
			s.append(ref.getAdapterId());
			instance.initializationData().logger.trace(instance.traceLevels().locationCat, s.toString());
		}
		Request request = (Request)_adapterRequests.get(ref.getAdapterId());
		if (request != null)
		{
			return request;
		} else
		{
			request = new AdapterRequest(this, ref);
			_adapterRequests.put(ref.getAdapterId(), request);
			return request;
		}
	}

	private synchronized Request getObjectRequest(Reference ref)
	{
		if (ref.getInstance().traceLevels().location >= 1)
		{
			Instance instance = ref.getInstance();
			StringBuilder s = new StringBuilder(128);
			s.append("searching for object by id\n");
			s.append("object = ");
			s.append(instance.identityToString(ref.getIdentity()));
			instance.initializationData().logger.trace(instance.traceLevels().locationCat, s.toString());
		}
		Request request = (Request)_objectRequests.get(ref.getIdentity());
		if (request != null)
		{
			return request;
		} else
		{
			request = new ObjectRequest(this, ref);
			_objectRequests.put(ref.getIdentity(), request);
			return request;
		}
	}

	private void finishRequest(Reference ref, List wellKnownRefs, ObjectPrx proxy, boolean notRegistered)
	{
		if (proxy == null || ((ObjectPrxHelperBase)proxy).__reference().isIndirect())
		{
			Reference r;
			for (Iterator i$ = wellKnownRefs.iterator(); i$.hasNext(); _table.removeObjectReference(r.getIdentity()))
				r = (Reference)i$.next();

		}
		if (!ref.isWellKnown())
		{
			if (proxy != null && !((ObjectPrxHelperBase)proxy).__reference().isIndirect())
				_table.addAdapterEndpoints(ref.getAdapterId(), ((ObjectPrxHelperBase)proxy).__reference().getEndpoints());
			else
			if (notRegistered)
				_table.removeAdapterEndpoints(ref.getAdapterId());
			synchronized (this)
			{
				if (!$assertionsDisabled && _adapterRequests.get(ref.getAdapterId()) == null)
					throw new AssertionError();
				_adapterRequests.remove(ref.getAdapterId());
			}
		} else
		{
			if (proxy != null && !((ObjectPrxHelperBase)proxy).__reference().isWellKnown())
				_table.addObjectReference(ref.getIdentity(), ((ObjectPrxHelperBase)proxy).__reference());
			else
			if (notRegistered)
				_table.removeObjectReference(ref.getIdentity());
			synchronized (this)
			{
				if (!$assertionsDisabled && _objectRequests.get(ref.getIdentity()) == null)
					throw new AssertionError();
				_objectRequests.remove(ref.getIdentity());
			}
		}
	}




}
