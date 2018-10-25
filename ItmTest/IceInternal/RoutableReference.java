// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RoutableReference.java

package IceInternal;

import Ice.*;
import IceUtilInternal.StringUtil;
import java.util.*;

// Referenced classes of package IceInternal:
//			Reference, EndpointI, OpaqueEndpointI, Instance, 
//			ReferenceFactory, LocatorManager, LocatorInfo, RouterManager, 
//			RouterInfo, BasicStream, TraceLevels, DefaultsAndOverrides, 
//			OutgoingConnectionFactory

public class RoutableReference extends Reference
{
	static class EndpointComparator
		implements Comparator
	{

		private boolean _preferSecure;

		public int compare(EndpointI le, EndpointI re)
		{
			boolean ls = le.secure();
			boolean rs = re.secure();
			if (ls && rs || !ls && !rs)
				return 0;
			if (!ls && rs)
				return !_preferSecure ? -1 : 1;
			return !_preferSecure ? 1 : -1;
		}

		public volatile int compare(Object x0, Object x1)
		{
			return compare((EndpointI)x0, (EndpointI)x1);
		}

		EndpointComparator(boolean preferSecure)
		{
			_preferSecure = preferSecure;
		}
	}


	private static EndpointComparator _preferNonSecureEndpointComparator = new EndpointComparator(false);
	private static EndpointComparator _preferSecureEndpointComparator = new EndpointComparator(true);
	private static EndpointI _emptyEndpoints[] = new EndpointI[0];
	private EndpointI _endpoints[];
	private String _adapterId;
	private LocatorInfo _locatorInfo;
	private RouterInfo _routerInfo;
	private boolean _collocationOptimized;
	private boolean _cacheConnection;
	private boolean _preferSecure;
	private EndpointSelectionType _endpointSelection;
	private int _locatorCacheTimeout;
	private boolean _overrideTimeout;
	private int _timeout;
	private String _connectionId;
	static final boolean $assertionsDisabled = !IceInternal/RoutableReference.desiredAssertionStatus();

	public final EndpointI[] getEndpoints()
	{
		return _endpoints;
	}

	public final String getAdapterId()
	{
		return _adapterId;
	}

	public final LocatorInfo getLocatorInfo()
	{
		return _locatorInfo;
	}

	public final RouterInfo getRouterInfo()
	{
		return _routerInfo;
	}

	public final boolean getCollocationOptimized()
	{
		return _collocationOptimized;
	}

	public final boolean getCacheConnection()
	{
		return _cacheConnection;
	}

	public final boolean getPreferSecure()
	{
		return _preferSecure;
	}

	public final EndpointSelectionType getEndpointSelection()
	{
		return _endpointSelection;
	}

	public final int getLocatorCacheTimeout()
	{
		return _locatorCacheTimeout;
	}

	public final String getConnectionId()
	{
		return _connectionId;
	}

	public Reference changeCompress(boolean newCompress)
	{
		RoutableReference r = (RoutableReference)super.changeCompress(newCompress);
		if (r != this && _endpoints.length > 0)
		{
			EndpointI newEndpoints[] = new EndpointI[_endpoints.length];
			for (int i = 0; i < _endpoints.length; i++)
				newEndpoints[i] = _endpoints[i].compress(newCompress);

			r._endpoints = newEndpoints;
		}
		return r;
	}

	public Reference changeEndpoints(EndpointI newEndpoints[])
	{
		if (Arrays.equals(newEndpoints, _endpoints))
		{
			return this;
		} else
		{
			RoutableReference r = (RoutableReference)getInstance().referenceFactory().copy(this);
			r._endpoints = newEndpoints;
			r._adapterId = "";
			r.applyOverrides(r._endpoints);
			return r;
		}
	}

	public Reference changeAdapterId(String newAdapterId)
	{
		if (_adapterId.equals(newAdapterId))
		{
			return this;
		} else
		{
			RoutableReference r = (RoutableReference)getInstance().referenceFactory().copy(this);
			r._adapterId = newAdapterId;
			r._endpoints = _emptyEndpoints;
			return r;
		}
	}

	public Reference changeLocator(LocatorPrx newLocator)
	{
		LocatorInfo newLocatorInfo = getInstance().locatorManager().get(newLocator);
		if (newLocatorInfo != null && _locatorInfo != null && newLocatorInfo.equals(_locatorInfo))
		{
			return this;
		} else
		{
			RoutableReference r = (RoutableReference)getInstance().referenceFactory().copy(this);
			r._locatorInfo = newLocatorInfo;
			return r;
		}
	}

	public Reference changeRouter(RouterPrx newRouter)
	{
		RouterInfo newRouterInfo = getInstance().routerManager().get(newRouter);
		if (newRouterInfo != null && _routerInfo != null && newRouterInfo.equals(_routerInfo))
		{
			return this;
		} else
		{
			RoutableReference r = (RoutableReference)getInstance().referenceFactory().copy(this);
			r._routerInfo = newRouterInfo;
			return r;
		}
	}

	public Reference changeCollocationOptimized(boolean newCollocationOptimized)
	{
		if (newCollocationOptimized == _collocationOptimized)
		{
			return this;
		} else
		{
			RoutableReference r = (RoutableReference)getInstance().referenceFactory().copy(this);
			r._collocationOptimized = newCollocationOptimized;
			return r;
		}
	}

	public final Reference changeCacheConnection(boolean newCache)
	{
		if (newCache == _cacheConnection)
		{
			return this;
		} else
		{
			RoutableReference r = (RoutableReference)getInstance().referenceFactory().copy(this);
			r._cacheConnection = newCache;
			return r;
		}
	}

	public Reference changePreferSecure(boolean newPreferSecure)
	{
		if (newPreferSecure == _preferSecure)
		{
			return this;
		} else
		{
			RoutableReference r = (RoutableReference)getInstance().referenceFactory().copy(this);
			r._preferSecure = newPreferSecure;
			return r;
		}
	}

	public final Reference changeEndpointSelection(EndpointSelectionType newType)
	{
		if (newType == _endpointSelection)
		{
			return this;
		} else
		{
			RoutableReference r = (RoutableReference)getInstance().referenceFactory().copy(this);
			r._endpointSelection = newType;
			return r;
		}
	}

	public Reference changeLocatorCacheTimeout(int newTimeout)
	{
		if (_locatorCacheTimeout == newTimeout)
		{
			return this;
		} else
		{
			RoutableReference r = (RoutableReference)getInstance().referenceFactory().copy(this);
			r._locatorCacheTimeout = newTimeout;
			return r;
		}
	}

	public Reference changeTimeout(int newTimeout)
	{
		if (_overrideTimeout && _timeout == newTimeout)
			return this;
		RoutableReference r = (RoutableReference)getInstance().referenceFactory().copy(this);
		r._timeout = newTimeout;
		r._overrideTimeout = true;
		if (_endpoints.length > 0)
		{
			EndpointI newEndpoints[] = new EndpointI[_endpoints.length];
			for (int i = 0; i < _endpoints.length; i++)
				newEndpoints[i] = _endpoints[i].timeout(newTimeout);

			r._endpoints = newEndpoints;
		}
		return r;
	}

	public Reference changeConnectionId(String id)
	{
		if (_connectionId.equals(id))
			return this;
		RoutableReference r = (RoutableReference)getInstance().referenceFactory().copy(this);
		r._connectionId = id;
		if (_endpoints.length > 0)
		{
			EndpointI newEndpoints[] = new EndpointI[_endpoints.length];
			for (int i = 0; i < _endpoints.length; i++)
				newEndpoints[i] = _endpoints[i].connectionId(id);

			r._endpoints = newEndpoints;
		}
		return r;
	}

	public boolean isIndirect()
	{
		return _endpoints.length == 0;
	}

	public boolean isWellKnown()
	{
		return _endpoints.length == 0 && _adapterId.length() == 0;
	}

	public void streamWrite(BasicStream s)
		throws MarshalException
	{
		super.streamWrite(s);
		s.writeSize(_endpoints.length);
		if (_endpoints.length > 0)
		{
			if (!$assertionsDisabled && _adapterId.length() != 0)
				throw new AssertionError();
			EndpointI arr$[] = _endpoints;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				EndpointI endpoint = arr$[i$];
				endpoint.streamWrite(s);
			}

		} else
		{
			s.writeString(_adapterId);
		}
	}

	public String toString()
	{
		StringBuilder s = new StringBuilder(128);
		s.append(super.toString());
		if (_endpoints.length > 0)
		{
			EndpointI arr$[] = _endpoints;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				EndpointI endpoint = arr$[i$];
				String endp = endpoint.toString();
				if (endp != null && endp.length() > 0)
				{
					s.append(':');
					s.append(endp);
				}
			}

		} else
		if (_adapterId.length() > 0)
		{
			s.append(" @ ");
			String a = StringUtil.escapeString(_adapterId, null);
			if (StringUtil.findFirstOf(a, " :@") != -1)
			{
				s.append('"');
				s.append(a);
				s.append('"');
			} else
			{
				s.append(a);
			}
		}
		return s.toString();
	}

	public Map toProperty(String prefix)
	{
		Map properties = new HashMap();
		properties.put(prefix, toString());
		properties.put((new StringBuilder()).append(prefix).append(".CollocationOptimized").toString(), _collocationOptimized ? "1" : "0");
		properties.put((new StringBuilder()).append(prefix).append(".ConnectionCached").toString(), _cacheConnection ? "1" : "0");
		properties.put((new StringBuilder()).append(prefix).append(".PreferSecure").toString(), _preferSecure ? "1" : "0");
		properties.put((new StringBuilder()).append(prefix).append(".EndpointSelection").toString(), _endpointSelection != EndpointSelectionType.Random ? "Ordered" : "Random");
		StringBuffer s = new StringBuffer();
		s.append(_locatorCacheTimeout);
		properties.put((new StringBuilder()).append(prefix).append(".LocatorCacheTimeout").toString(), s.toString());
		if (_routerInfo != null)
		{
			ObjectPrxHelperBase h = (ObjectPrxHelperBase)_routerInfo.getRouter();
			Map routerProperties = h.__reference().toProperty((new StringBuilder()).append(prefix).append(".Router").toString());
			java.util.Map.Entry p;
			for (Iterator i$ = routerProperties.entrySet().iterator(); i$.hasNext(); properties.put(p.getKey(), p.getValue()))
				p = (java.util.Map.Entry)i$.next();

		}
		if (_locatorInfo != null)
		{
			ObjectPrxHelperBase h = (ObjectPrxHelperBase)_locatorInfo.getLocator();
			Map locatorProperties = h.__reference().toProperty((new StringBuilder()).append(prefix).append(".Locator").toString());
			java.util.Map.Entry p;
			for (Iterator i$ = locatorProperties.entrySet().iterator(); i$.hasNext(); properties.put(p.getKey(), p.getValue()))
				p = (java.util.Map.Entry)i$.next();

		}
		return properties;
	}

	public synchronized int hashCode()
	{
		if (!_hashInitialized)
		{
			super.hashCode();
			_hashValue = 5 * _hashValue + _adapterId.hashCode();
		}
		return _hashValue;
	}

	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!(obj instanceof RoutableReference))
			return false;
		if (!super.equals(obj))
			return false;
		RoutableReference rhs = (RoutableReference)obj;
		if (_locatorInfo != null ? !_locatorInfo.equals(rhs._locatorInfo) : rhs._locatorInfo != null)
			return false;
		if (_routerInfo != null ? !_routerInfo.equals(rhs._routerInfo) : rhs._routerInfo != null)
			return false;
		if (_collocationOptimized != rhs._collocationOptimized)
			return false;
		if (_cacheConnection != rhs._cacheConnection)
			return false;
		if (_preferSecure != rhs._preferSecure)
			return false;
		if (_endpointSelection != rhs._endpointSelection)
			return false;
		if (_locatorCacheTimeout != rhs._locatorCacheTimeout)
			return false;
		if (!_connectionId.equals(rhs._connectionId))
			return false;
		if (_overrideTimeout != rhs._overrideTimeout)
			return false;
		if (_overrideTimeout && _timeout != rhs._timeout)
			return false;
		if (!Arrays.equals(_endpoints, rhs._endpoints))
			return false;
		return _adapterId.equals(rhs._adapterId);
	}

	public ConnectionI getConnection(BooleanHolder comp)
	{
		if (_routerInfo != null)
		{
			EndpointI endpts[] = _routerInfo.getClientEndpoints();
			if (endpts.length > 0)
			{
				applyOverrides(endpts);
				return createConnection(endpts, comp);
			}
		}
		if (_endpoints.length > 0)
			return createConnection(_endpoints, comp);
_L2:
		BooleanHolder cached;
		EndpointI endpts[];
		cached = new BooleanHolder(false);
		endpts = null;
		if (_locatorInfo != null)
		{
			endpts = _locatorInfo.getEndpoints(this, _locatorCacheTimeout, cached);
			applyOverrides(endpts);
		}
		if (endpts == null || endpts.length == 0)
			throw new NoEndpointException(toString());
		return createConnection(endpts, comp);
		NoEndpointException ex;
		ex;
		throw ex;
		ex;
		if (!$assertionsDisabled && _locatorInfo == null)
			throw new AssertionError();
		_locatorInfo.clearCache(this);
		if (!cached.value)
			break; /* Loop/switch isn't completed */
		TraceLevels traceLevels = getInstance().traceLevels();
		if (traceLevels.retry >= 2)
		{
			String s = (new StringBuilder()).append("connection to cached endpoints failed\nremoving endpoints from cache and trying one more time\n").append(ex).toString();
			getInstance().initializationData().logger.trace(traceLevels.retryCat, s);
		}
		if (true) goto _L2; else goto _L1
_L1:
		throw ex;
	}

	public void getConnection(final Reference.GetConnectionCallback callback)
	{
		if (_routerInfo != null)
			_routerInfo.getClientEndpoints(new RouterInfo.GetClientEndpointsCallback() {

				final Reference.GetConnectionCallback val$callback;
				final RoutableReference this$0;

				public void setEndpoints(EndpointI endpts[])
				{
					if (endpts.length > 0)
					{
						applyOverrides(endpts);
						createConnection(endpts, callback);
					} else
					{
						getConnectionNoRouterInfo(callback);
					}
				}

				public void setException(LocalException ex)
				{
					callback.setException(ex);
				}

			
			{
				this$0 = RoutableReference.this;
				callback = getconnectioncallback;
				super();
			}
			});
		else
			getConnectionNoRouterInfo(callback);
	}

	public void getConnectionNoRouterInfo(final Reference.GetConnectionCallback callback)
	{
		if (_endpoints.length > 0)
		{
			createConnection(_endpoints, callback);
			return;
		}
		final RoutableReference self = this;
		if (_locatorInfo != null)
			_locatorInfo.getEndpoints(this, _locatorCacheTimeout, new LocatorInfo.GetEndpointsCallback() {

				final Reference.GetConnectionCallback val$callback;
				final RoutableReference val$self;
				final RoutableReference this$0;

				public void setEndpoints(EndpointI endpoints[], final boolean cached)
				{
					if (endpoints.length == 0)
					{
						callback.setException(new NoEndpointException(self.toString()));
						return;
					} else
					{
						applyOverrides(endpoints);
						createConnection(endpoints, new Reference.GetConnectionCallback() {

							static final boolean $assertionsDisabled = !IceInternal/RoutableReference.desiredAssertionStatus();
							final boolean val$cached;
							final 2 this$1;

							public void setConnection(ConnectionI connection, boolean compress)
							{
								callback.setConnection(connection, compress);
							}

							public void setException(LocalException exc)
							{
								try
								{
									throw exc;
								}
								catch (NoEndpointException ex)
								{
									callback.setException(ex);
								}
								catch (LocalException ex)
								{
									if (!$assertionsDisabled && _locatorInfo == null)
										throw new AssertionError();
									_locatorInfo.clearCache(self);
									if (cached)
									{
										TraceLevels traceLvls = getInstance().traceLevels();
										if (traceLvls.retry >= 2)
										{
											String s = (new StringBuilder()).append("connection to cached endpoints failed\nremoving endpoints from cache and trying one more time\n").append(ex).toString();
											getInstance().initializationData().logger.trace(traceLvls.retryCat, s);
										}
										getConnectionNoRouterInfo(callback);
										return;
									}
									callback.setException(ex);
								}
							}


					
					{
						this$1 = 2.this;
						cached = flag;
						super();
					}
						});
						return;
					}
				}

				public void setException(LocalException ex)
				{
					callback.setException(ex);
				}

			
			{
				this$0 = RoutableReference.this;
				callback = getconnectioncallback;
				self = routablereference1;
				super();
			}
			});
		else
			callback.setException(new NoEndpointException(toString()));
	}

	protected RoutableReference(Instance instance, Communicator communicator, Identity identity, String facet, int mode, boolean secure, EndpointI endpoints[], 
			String adapterId, LocatorInfo locatorInfo, RouterInfo routerInfo, boolean collocationOptimized, boolean cacheConnection, boolean prefereSecure, EndpointSelectionType endpointSelection, 
			int locatorCacheTimeout)
	{
		super(instance, communicator, identity, facet, mode, secure);
		_connectionId = "";
		_endpoints = endpoints;
		_adapterId = adapterId;
		_locatorInfo = locatorInfo;
		_routerInfo = routerInfo;
		_collocationOptimized = collocationOptimized;
		_cacheConnection = cacheConnection;
		_preferSecure = prefereSecure;
		_endpointSelection = endpointSelection;
		_locatorCacheTimeout = locatorCacheTimeout;
		_overrideTimeout = false;
		_timeout = -1;
		if (_endpoints == null)
			_endpoints = _emptyEndpoints;
		if (_adapterId == null)
			_adapterId = "";
		if (!$assertionsDisabled && _adapterId.length() != 0 && _endpoints.length != 0)
			throw new AssertionError();
		else
			return;
	}

	protected void applyOverrides(EndpointI endpts[])
	{
		for (int i = 0; i < endpts.length; i++)
		{
			endpts[i] = endpts[i].connectionId(_connectionId);
			if (_overrideCompress)
				endpts[i] = endpts[i].compress(_compress);
			if (_overrideTimeout)
				endpts[i] = endpts[i].timeout(_timeout);
		}

	}

	private EndpointI[] filterEndpoints(EndpointI allEndpoints[])
	{
		List endpoints = new ArrayList();
		EndpointI arr$[] = allEndpoints;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			EndpointI endpoint = arr$[i$];
			if (!(endpoint instanceof OpaqueEndpointI))
				endpoints.add(endpoint);
		}

		switch (getMode())
		{
		case 0: // '\0'
		case 1: // '\001'
		case 2: // '\002'
		{
			Iterator i = endpoints.iterator();
			do
			{
				if (!i.hasNext())
					break;
				EndpointI endpoint = (EndpointI)i.next();
				if (endpoint.datagram())
					i.remove();
			} while (true);
			break;
		}

		case 3: // '\003'
		case 4: // '\004'
		{
			Iterator i = endpoints.iterator();
			do
			{
				if (!i.hasNext())
					break;
				EndpointI endpoint = (EndpointI)i.next();
				if (!endpoint.datagram())
					i.remove();
			} while (true);
			break;
		}
		}
		static class 5
		{

			static final int $SwitchMap$Ice$EndpointSelectionType[];

			static 
			{
				$SwitchMap$Ice$EndpointSelectionType = new int[EndpointSelectionType.values().length];
				try
				{
					$SwitchMap$Ice$EndpointSelectionType[EndpointSelectionType.Random.ordinal()] = 1;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$Ice$EndpointSelectionType[EndpointSelectionType.Ordered.ordinal()] = 2;
				}
				catch (NoSuchFieldError ex) { }
			}
		}

		switch (5..SwitchMap.Ice.EndpointSelectionType[getEndpointSelection().ordinal()])
		{
		case 1: // '\001'
			Collections.shuffle(endpoints);
			break;

		case 2: // '\002'
			break;

		default:
			if (!$assertionsDisabled)
				throw new AssertionError();
			break;
		}
		DefaultsAndOverrides overrides = getInstance().defaultsAndOverrides();
		if (overrides.overrideSecure ? overrides.overrideSecureValue : getSecure())
		{
			Iterator i = endpoints.iterator();
			do
			{
				if (!i.hasNext())
					break;
				EndpointI endpoint = (EndpointI)i.next();
				if (!endpoint.secure())
					i.remove();
			} while (true);
		} else
		if (getPreferSecure())
			Collections.sort(endpoints, _preferSecureEndpointComparator);
		else
			Collections.sort(endpoints, _preferNonSecureEndpointComparator);
		return (EndpointI[])(EndpointI[])endpoints.toArray(new EndpointI[endpoints.size()]);
	}

	protected ConnectionI createConnection(EndpointI allEndpoints[], BooleanHolder compress)
	{
		EndpointI endpoints[] = filterEndpoints(allEndpoints);
		if (endpoints.length == 0)
			throw new NoEndpointException(toString());
		OutgoingConnectionFactory factory = getInstance().outgoingConnectionFactory();
		ConnectionI connection = null;
		if (getCacheConnection() || endpoints.length == 1)
		{
			connection = factory.create(endpoints, false, getEndpointSelection(), compress);
		} else
		{
			LocalException exception = null;
			EndpointI endpoint[] = new EndpointI[1];
			int i = 0;
			do
			{
				if (i >= endpoints.length)
					break;
				try
				{
					endpoint[0] = endpoints[i];
					boolean more = i != endpoints.length - 1;
					connection = factory.create(endpoint, more, getEndpointSelection(), compress);
					break;
				}
				catch (LocalException ex)
				{
					exception = ex;
					i++;
				}
			} while (true);
			if (connection == null)
				if (!$assertionsDisabled && exception == null)
					throw new AssertionError();
				else
					throw exception;
		}
		if (!$assertionsDisabled && connection == null)
			throw new AssertionError();
		if (_routerInfo != null && _routerInfo.getAdapter() != null)
			connection.setAdapter(_routerInfo.getAdapter());
		return connection;
	}

	protected void createConnection(EndpointI allEndpoints[], final Reference.GetConnectionCallback callback)
	{
		final EndpointI endpoints[] = filterEndpoints(allEndpoints);
		if (endpoints.length == 0)
		{
			callback.setException(new NoEndpointException(toString()));
			return;
		}
		final OutgoingConnectionFactory factory = getInstance().outgoingConnectionFactory();
		if (getCacheConnection() || endpoints.length == 1)
			factory.create(endpoints, false, getEndpointSelection(), new OutgoingConnectionFactory.CreateConnectionCallback() {

				final Reference.GetConnectionCallback val$callback;
				final RoutableReference this$0;

				public void setConnection(ConnectionI connection, boolean compress)
				{
					if (_routerInfo != null && _routerInfo.getAdapter() != null)
						connection.setAdapter(_routerInfo.getAdapter());
					callback.setConnection(connection, compress);
				}

				public void setException(LocalException ex)
				{
					callback.setException(ex);
				}

			
			{
				this$0 = RoutableReference.this;
				callback = getconnectioncallback;
				super();
			}
			});
		else
			factory.create(new EndpointI[] {
				endpoints[0]
			}, true, getEndpointSelection(), new OutgoingConnectionFactory.CreateConnectionCallback() {

				private int _i;
				private LocalException _exception;
				final Reference.GetConnectionCallback val$callback;
				final EndpointI val$endpoints[];
				final OutgoingConnectionFactory val$factory;
				final RoutableReference this$0;

				public void setConnection(ConnectionI connection, boolean compress)
				{
					if (_routerInfo != null && _routerInfo.getAdapter() != null)
						connection.setAdapter(_routerInfo.getAdapter());
					callback.setConnection(connection, compress);
				}

				public void setException(LocalException ex)
				{
					if (_exception == null)
						_exception = ex;
					if (++_i == endpoints.length)
					{
						callback.setException(_exception);
						return;
					} else
					{
						boolean more = _i != endpoints.length - 1;
						EndpointI endpoint[] = {
							endpoints[_i]
						};
						factory.create(endpoint, more, getEndpointSelection(), this);
						return;
					}
				}

			
			{
				this$0 = RoutableReference.this;
				callback = getconnectioncallback;
				endpoints = aendpointi;
				factory = outgoingconnectionfactory;
				super();
				_i = 0;
				_exception = null;
			}
			});
	}



}
