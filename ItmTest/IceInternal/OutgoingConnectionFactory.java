// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   OutgoingConnectionFactory.java

package IceInternal;

import Ice.*;
import IceUtilInternal.Assert;
import java.util.*;

// Referenced classes of package IceInternal:
//			EndpointI, Connector, ConnectionReaper, Instance, 
//			DefaultsAndOverrides, RouterInfo, CommunicatorBatchOutgoingAsync, Transceiver, 
//			TraceLevels, EndpointI_connectors

public final class OutgoingConnectionFactory
{
	private static class ConnectCallback
		implements Ice.ConnectionI.StartCallback, EndpointI_connectors
	{

		private final OutgoingConnectionFactory _factory;
		private final boolean _hasMore;
		private final CreateConnectionCallback _callback;
		private final List _endpoints;
		private final EndpointSelectionType _selType;
		private Iterator _endpointsIter;
		private EndpointI _currentEndpoint;
		private List _connectors;
		private Iterator _iter;
		private ConnectorInfo _current;
		static final boolean $assertionsDisabled = !IceInternal/OutgoingConnectionFactory.desiredAssertionStatus();

		public void connectionStartCompleted(ConnectionI connection)
		{
			connection.activate();
			_factory.finishGetConnection(_connectors, _current, connection, this);
		}

		public void connectionStartFailed(ConnectionI connection, LocalException ex)
		{
			if (!$assertionsDisabled && _current == null)
				throw new AssertionError();
			_factory.handleConnectionException(ex, _hasMore || _iter.hasNext());
			if (ex instanceof CommunicatorDestroyedException)
				_factory.finishGetConnection(_connectors, ex, this);
			else
			if (_iter.hasNext())
				nextConnector();
			else
				_factory.finishGetConnection(_connectors, ex, this);
		}

		public void connectors(List cons)
		{
			if (_selType == EndpointSelectionType.Random)
				Collections.shuffle(cons);
			Connector p;
			for (Iterator i$ = cons.iterator(); i$.hasNext(); _connectors.add(new ConnectorInfo(p, _currentEndpoint)))
				p = (Connector)i$.next();

			if (_endpointsIter.hasNext())
			{
				nextEndpoint();
			} else
			{
				if (!$assertionsDisabled && _connectors.isEmpty())
					throw new AssertionError();
				_iter = _connectors.iterator();
				getConnection();
			}
		}

		public void exception(LocalException ex)
		{
			_factory.handleException(ex, _hasMore || _endpointsIter.hasNext());
			if (_endpointsIter.hasNext())
				nextEndpoint();
			else
			if (!_connectors.isEmpty())
			{
				_iter = _connectors.iterator();
				getConnection();
			} else
			{
				_callback.setException(ex);
				_factory.decPendingConnectCount();
			}
		}

		public void setConnection(ConnectionI connection, boolean compress)
		{
			_callback.setConnection(connection, compress);
			_factory.decPendingConnectCount();
		}

		public void setException(LocalException ex)
		{
			_callback.setException(ex);
			_factory.decPendingConnectCount();
		}

		public boolean hasConnector(ConnectorInfo ci)
		{
			return _connectors.contains(ci);
		}

		public boolean removeConnectors(List connectors)
		{
			_connectors.removeAll(connectors);
			_iter = _connectors.iterator();
			return _connectors.isEmpty();
		}

		public void removeFromPending()
		{
			_factory.removeFromPending(this, _connectors);
		}

		void getConnectors()
		{
			try
			{
				_factory.incPendingConnectCount();
			}
			catch (LocalException ex)
			{
				_callback.setException(ex);
				return;
			}
			nextEndpoint();
		}

		void nextEndpoint()
		{
			try
			{
				if (!$assertionsDisabled && !_endpointsIter.hasNext())
					throw new AssertionError();
				_currentEndpoint = (EndpointI)_endpointsIter.next();
				_currentEndpoint.connectors_async(this);
			}
			catch (LocalException ex)
			{
				exception(ex);
			}
		}

		void getConnection()
		{
			BooleanHolder compress;
			ConnectionI connection;
			compress = new BooleanHolder();
			connection = _factory.getConnection(_connectors, this, compress);
			if (connection == null)
				return;
			try
			{
				_callback.setConnection(connection, compress.value);
				_factory.decPendingConnectCount();
			}
			catch (LocalException ex)
			{
				_callback.setException(ex);
				_factory.decPendingConnectCount();
			}
			return;
		}

		void nextConnector()
		{
			ConnectionI connection = null;
			try
			{
				if (!$assertionsDisabled && !_iter.hasNext())
					throw new AssertionError();
				_current = (ConnectorInfo)_iter.next();
				connection = _factory.createConnection(_current.connector.connect(), _current);
				connection.start(this);
			}
			catch (LocalException ex)
			{
				connectionStartFailed(connection, ex);
			}
		}


		ConnectCallback(OutgoingConnectionFactory f, List endpoints, boolean more, CreateConnectionCallback cb, EndpointSelectionType selType)
		{
			_connectors = new ArrayList();
			_factory = f;
			_endpoints = endpoints;
			_hasMore = more;
			_callback = cb;
			_selType = selType;
			_endpointsIter = _endpoints.iterator();
		}
	}

	private static class ConnectorInfo
	{

		public Connector connector;
		public EndpointI endpoint;

		public boolean equals(Object obj)
		{
			ConnectorInfo r = (ConnectorInfo)obj;
			return connector.equals(r.connector);
		}

		public int hashCode()
		{
			return connector.hashCode();
		}

		public ConnectorInfo(Connector c, EndpointI e)
		{
			connector = c;
			endpoint = e;
		}
	}

	static interface CreateConnectionCallback
	{

		public abstract void setConnection(ConnectionI connectioni, boolean flag);

		public abstract void setException(LocalException localexception);
	}

	private static class MultiHashMap extends HashMap
	{

		static final boolean $assertionsDisabled = !IceInternal/OutgoingConnectionFactory.desiredAssertionStatus();

		public void put(Object key, Object value)
		{
			List list = (List)get(key);
			if (list == null)
			{
				list = new LinkedList();
				put(key, list);
			}
			list.add(value);
		}

		public void remove(Object key, Object value)
		{
			List list = (List)get(key);
			if (!$assertionsDisabled && list == null)
				throw new AssertionError();
			list.remove(value);
			if (list.isEmpty())
				remove(key);
		}


		private MultiHashMap()
		{
		}

	}


	private final Instance _instance;
	private final ConnectionReaper _reaper = new ConnectionReaper();
	private boolean _destroyed;
	private MultiHashMap _connections;
	private MultiHashMap _connectionsByEndpoint;
	private Map _pending;
	private int _pendingConnectCount;
	static final boolean $assertionsDisabled = !IceInternal/OutgoingConnectionFactory.desiredAssertionStatus();

	public synchronized void destroy()
	{
		if (_destroyed)
			return;
		for (Iterator i$ = _connections.values().iterator(); i$.hasNext();)
		{
			List connectionList = (List)i$.next();
			Iterator i$ = connectionList.iterator();
			while (i$.hasNext()) 
			{
				ConnectionI connection = (ConnectionI)i$.next();
				connection.destroy(1);
			}
		}

		_destroyed = true;
		notifyAll();
	}

	public void waitUntilFinished()
	{
		Map connections = null;
		synchronized (this)
		{
			while (!_destroyed || !_pending.isEmpty() || _pendingConnectCount > 0) 
				try
				{
					wait();
				}
				catch (InterruptedException ex) { }
			connections = new HashMap(_connections);
		}
		for (Iterator i$ = connections.values().iterator(); i$.hasNext();)
		{
			List connectionList = (List)i$.next();
			Iterator i$ = connectionList.iterator();
			while (i$.hasNext()) 
			{
				ConnectionI connection = (ConnectionI)i$.next();
				connection.waitUntilFinished();
			}
		}

		synchronized (this)
		{
			List cons = _reaper.swapConnections();
			if (cons != null)
			{
				int size = 0;
				for (Iterator i$ = _connections.values().iterator(); i$.hasNext();)
				{
					List connectionList = (List)i$.next();
					size += connectionList.size();
				}

				if (!$assertionsDisabled && cons.size() != size)
					throw new AssertionError();
				_connections.clear();
				_connectionsByEndpoint.clear();
			} else
			{
				if (!$assertionsDisabled && !_connections.isEmpty())
					throw new AssertionError();
				if (!$assertionsDisabled && !_connectionsByEndpoint.isEmpty())
					throw new AssertionError();
			}
		}
	}

	public ConnectionI create(EndpointI endpts[], boolean hasMore, EndpointSelectionType selType, BooleanHolder compress)
	{
		if (!$assertionsDisabled && endpts.length <= 0)
			throw new AssertionError();
		List endpoints = applyOverrides(endpts);
		ConnectionI connection = findConnectionByEndpoint(endpoints, compress);
		if (connection != null)
			return connection;
		LocalException exception = null;
		List connectors = new ArrayList();
		Iterator p;
		for (p = endpoints.iterator(); p.hasNext();)
		{
			EndpointI endpoint = (EndpointI)p.next();
			try
			{
				List cons = endpoint.connectors();
				if (!$assertionsDisabled && cons.size() <= 0)
					throw new AssertionError();
				if (selType == EndpointSelectionType.Random)
					Collections.shuffle(cons);
				Iterator i$ = cons.iterator();
				while (i$.hasNext()) 
				{
					Connector c = (Connector)i$.next();
					connectors.add(new ConnectorInfo(c, endpoint));
				}
			}
			catch (LocalException ex)
			{
				exception = ex;
				handleException(exception, hasMore || p.hasNext());
			}
		}

		if (connectors.isEmpty())
			if (!$assertionsDisabled && exception == null)
				throw new AssertionError();
			else
				throw exception;
		connection = getConnection(connectors, null, compress);
		if (connection != null)
			return connection;
		DefaultsAndOverrides defaultsAndOverrides = _instance.defaultsAndOverrides();
		Iterator q = connectors.iterator();
		ConnectorInfo ci = null;
		do
		{
			if (!q.hasNext())
				break;
			ci = (ConnectorInfo)q.next();
			try
			{
				connection = createConnection(ci.connector.connect(), ci);
				connection.start(null);
				if (defaultsAndOverrides.overrideCompress)
					compress.value = defaultsAndOverrides.overrideCompressValue;
				else
					compress.value = ci.endpoint.compress();
				connection.activate();
				break;
			}
			catch (CommunicatorDestroyedException ex)
			{
				exception = ex;
				handleConnectionException(exception, hasMore || p.hasNext());
				connection = null;
				break;
			}
			catch (LocalException ex)
			{
				exception = ex;
				handleConnectionException(exception, hasMore || p.hasNext());
				connection = null;
			}
		} while (true);
		if (connection != null)
			finishGetConnection(connectors, ci, connection, null);
		else
			finishGetConnection(connectors, exception, null);
		if (connection == null)
		{
			if (!$assertionsDisabled && exception == null)
				throw new AssertionError();
			else
				throw exception;
		} else
		{
			return connection;
		}
	}

	public void create(EndpointI endpts[], boolean hasMore, EndpointSelectionType selType, CreateConnectionCallback callback)
	{
		if (!$assertionsDisabled && endpts.length <= 0)
			throw new AssertionError();
		List endpoints = applyOverrides(endpts);
		try
		{
			BooleanHolder compress = new BooleanHolder();
			ConnectionI connection = findConnectionByEndpoint(endpoints, compress);
			if (connection != null)
			{
				callback.setConnection(connection, compress.value);
				return;
			}
		}
		catch (LocalException ex)
		{
			callback.setException(ex);
			return;
		}
		ConnectCallback cb = new ConnectCallback(this, endpoints, hasMore, callback, selType);
		cb.getConnectors();
	}

	public synchronized void setRouterInfo(RouterInfo routerInfo)
	{
		if (_destroyed)
			throw new CommunicatorDestroyedException();
		if (!$assertionsDisabled && routerInfo == null)
			throw new AssertionError();
		ObjectAdapter adapter = routerInfo.getAdapter();
		DefaultsAndOverrides defaultsAndOverrides = _instance.defaultsAndOverrides();
		EndpointI arr$[] = routerInfo.getClientEndpoints();
		int len$ = arr$.length;
		int i$ = 0;
		do
		{
			if (i$ >= len$)
				break;
			EndpointI endpoint = arr$[i$];
			if (defaultsAndOverrides.overrideTimeout)
				endpoint = endpoint.timeout(defaultsAndOverrides.overrideTimeoutValue);
			endpoint = endpoint.compress(false);
			for (Iterator i$ = _connections.values().iterator(); i$.hasNext();)
			{
				List connectionList = (List)i$.next();
				Iterator i$ = connectionList.iterator();
				while (i$.hasNext()) 
				{
					ConnectionI connection = (ConnectionI)i$.next();
					if (connection.endpoint() == endpoint)
						connection.setAdapter(adapter);
				}
			}

			i$++;
		} while (true);
	}

	public synchronized void removeAdapter(ObjectAdapter adapter)
	{
		if (_destroyed)
			return;
		for (Iterator i$ = _connections.values().iterator(); i$.hasNext();)
		{
			List connectionList = (List)i$.next();
			Iterator i$ = connectionList.iterator();
			while (i$.hasNext()) 
			{
				ConnectionI connection = (ConnectionI)i$.next();
				if (connection.getAdapter() == adapter)
					connection.setAdapter(null);
			}
		}

	}

	public void flushAsyncBatchRequests(CommunicatorBatchOutgoingAsync outAsync)
	{
		List c = new LinkedList();
		synchronized (this)
		{
			if (!_destroyed)
			{
				for (Iterator i$ = _connections.values().iterator(); i$.hasNext();)
				{
					List connectionList = (List)i$.next();
					Iterator i$ = connectionList.iterator();
					while (i$.hasNext()) 
					{
						ConnectionI connection = (ConnectionI)i$.next();
						if (connection.isActiveOrHolding())
							c.add(connection);
					}
				}

			}
		}
		for (Iterator i$ = c.iterator(); i$.hasNext();)
		{
			ConnectionI conn = (ConnectionI)i$.next();
			try
			{
				outAsync.flushConnection(conn);
			}
			catch (LocalException ex) { }
		}

	}

	OutgoingConnectionFactory(Instance instance)
	{
		_connections = new MultiHashMap();
		_connectionsByEndpoint = new MultiHashMap();
		_pending = new HashMap();
		_pendingConnectCount = 0;
		_instance = instance;
		_destroyed = false;
	}

	protected synchronized void finalize()
		throws Throwable
	{
		Assert.FinalizerAssert(_destroyed);
		Assert.FinalizerAssert(_pendingConnectCount == 0);
		Assert.FinalizerAssert(_pending.isEmpty());
		super.finalize();
	}

	private List applyOverrides(EndpointI endpts[])
	{
		DefaultsAndOverrides defaultsAndOverrides = _instance.defaultsAndOverrides();
		List endpoints = new ArrayList();
		EndpointI arr$[] = endpts;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			EndpointI endpoint = arr$[i$];
			if (defaultsAndOverrides.overrideTimeout)
				endpoints.add(endpoint.timeout(defaultsAndOverrides.overrideTimeoutValue));
			else
				endpoints.add(endpoint);
		}

		return endpoints;
	}

	private synchronized ConnectionI findConnectionByEndpoint(List endpoints, BooleanHolder compress)
	{
		if (_destroyed)
			throw new CommunicatorDestroyedException();
		DefaultsAndOverrides defaultsAndOverrides = _instance.defaultsAndOverrides();
		if (!$assertionsDisabled && endpoints.isEmpty())
			throw new AssertionError();
		Iterator i$ = endpoints.iterator();
		EndpointI endpoint;
		ConnectionI connection;
label0:
		do
			if (i$.hasNext())
			{
				endpoint = (EndpointI)i$.next();
				List connectionList = (List)_connectionsByEndpoint.get(endpoint);
				if (connectionList == null)
					continue;
				Iterator i$ = connectionList.iterator();
				do
				{
					if (!i$.hasNext())
						continue label0;
					connection = (ConnectionI)i$.next();
				} while (!connection.isActiveOrHolding());
				break;
			} else
			{
				return null;
			}
		while (true);
		if (defaultsAndOverrides.overrideCompress)
			compress.value = defaultsAndOverrides.overrideCompressValue;
		else
			compress.value = endpoint.compress();
		return connection;
	}

	private ConnectionI findConnection(List connectors, BooleanHolder compress)
	{
		DefaultsAndOverrides defaultsAndOverrides = _instance.defaultsAndOverrides();
		Iterator i$ = connectors.iterator();
		ConnectorInfo ci;
		ConnectionI connection;
label0:
		do
			if (i$.hasNext())
			{
				ci = (ConnectorInfo)i$.next();
				if (_pending.containsKey(ci.connector))
					continue;
				List connectionList = (List)_connections.get(ci.connector);
				if (connectionList == null)
					continue;
				Iterator i$ = connectionList.iterator();
				do
				{
					if (!i$.hasNext())
						continue label0;
					connection = (ConnectionI)i$.next();
				} while (!connection.isActiveOrHolding());
				break;
			} else
			{
				return null;
			}
		while (true);
		if (defaultsAndOverrides.overrideCompress)
			compress.value = defaultsAndOverrides.overrideCompressValue;
		else
			compress.value = ci.endpoint.compress();
		return connection;
	}

	private synchronized void incPendingConnectCount()
	{
		if (_destroyed)
		{
			throw new CommunicatorDestroyedException();
		} else
		{
			_pendingConnectCount++;
			return;
		}
	}

	private synchronized void decPendingConnectCount()
	{
		_pendingConnectCount--;
		if (!$assertionsDisabled && _pendingConnectCount < 0)
			throw new AssertionError();
		if (_destroyed && _pendingConnectCount == 0)
			notifyAll();
	}

	private ConnectionI getConnection(List connectors, ConnectCallback cb, BooleanHolder compress)
	{
		OutgoingConnectionFactory outgoingconnectionfactory = this;
		JVM INSTR monitorenter ;
		if (_destroyed)
			throw new CommunicatorDestroyedException();
		List cons = _reaper.swapConnections();
		if (cons != null)
		{
			ConnectionI c;
			for (Iterator i$ = cons.iterator(); i$.hasNext(); _connectionsByEndpoint.remove(c.endpoint().compress(true), c))
			{
				c = (ConnectionI)i$.next();
				_connections.remove(c.connector(), c);
				_connectionsByEndpoint.remove(c.endpoint(), c);
			}

		}
_L3:
		ConnectionI connection;
		if (_destroyed)
			throw new CommunicatorDestroyedException();
		connection = findConnection(connectors, compress);
		if (connection != null)
			return connection;
		if (!addToPending(cb, connectors)) goto _L2; else goto _L1
_L1:
		if (cb != null)
			break MISSING_BLOCK_LABEL_173;
		try
		{
			wait();
		}
		catch (InterruptedException ex) { }
		  goto _L3
		null;
		outgoingconnectionfactory;
		JVM INSTR monitorexit ;
		return;
_L2:
		outgoingconnectionfactory;
		JVM INSTR monitorexit ;
		  goto _L4
		Exception exception;
		exception;
		throw exception;
_L4:
		if (cb != null)
			cb.nextConnector();
		return null;
	}

	private synchronized ConnectionI createConnection(Transceiver transceiver, ConnectorInfo ci)
	{
		if (!$assertionsDisabled && (!_pending.containsKey(ci.connector) || transceiver == null))
			throw new AssertionError();
		ConnectionI connection = null;
		try
		{
			if (_destroyed)
				throw new CommunicatorDestroyedException();
			connection = new ConnectionI(_instance, _reaper, transceiver, ci.connector, ci.endpoint.compress(false), null);
		}
		catch (LocalException ex)
		{
			try
			{
				transceiver.close();
			}
			catch (LocalException exc) { }
			throw ex;
		}
		_connections.put(ci.connector, connection);
		_connectionsByEndpoint.put(connection.endpoint(), connection);
		_connectionsByEndpoint.put(connection.endpoint().compress(true), connection);
		return connection;
	}

	private void finishGetConnection(List connectors, ConnectorInfo ci, ConnectionI connection, ConnectCallback cb)
	{
		Set connectionCallbacks = new HashSet();
		if (cb != null)
			connectionCallbacks.add(cb);
		Set callbacks = new HashSet();
		synchronized (this)
		{
			Iterator i$ = connectors.iterator();
			do
			{
				if (!i$.hasNext())
					break;
				ConnectorInfo c = (ConnectorInfo)i$.next();
				Set cbs = (Set)_pending.remove(c.connector);
				if (cbs != null)
				{
					Iterator i$ = cbs.iterator();
					while (i$.hasNext()) 
					{
						ConnectCallback cc = (ConnectCallback)i$.next();
						if (cc.hasConnector(ci))
							connectionCallbacks.add(cc);
						else
							callbacks.add(cc);
					}
				}
			} while (true);
			ConnectCallback cc;
			for (i$ = connectionCallbacks.iterator(); i$.hasNext(); callbacks.remove(cc))
			{
				cc = (ConnectCallback)i$.next();
				cc.removeFromPending();
			}

			ConnectCallback cc;
			for (i$ = callbacks.iterator(); i$.hasNext(); cc.removeFromPending())
				cc = (ConnectCallback)i$.next();

			notifyAll();
		}
		DefaultsAndOverrides defaultsAndOverrides = _instance.defaultsAndOverrides();
		boolean compress;
		if (defaultsAndOverrides.overrideCompress)
			compress = defaultsAndOverrides.overrideCompressValue;
		else
			compress = ci.endpoint.compress();
		ConnectCallback cc;
		for (Iterator i$ = callbacks.iterator(); i$.hasNext(); cc.getConnection())
			cc = (ConnectCallback)i$.next();

		ConnectCallback cc;
		for (Iterator i$ = connectionCallbacks.iterator(); i$.hasNext(); cc.setConnection(connection, compress))
			cc = (ConnectCallback)i$.next();

	}

	private void finishGetConnection(List connectors, LocalException ex, ConnectCallback cb)
	{
		Set failedCallbacks = new HashSet();
		if (cb != null)
			failedCallbacks.add(cb);
		Set callbacks = new HashSet();
		synchronized (this)
		{
			Iterator i$ = connectors.iterator();
			do
			{
				if (!i$.hasNext())
					break;
				ConnectorInfo c = (ConnectorInfo)i$.next();
				Set cbs = (Set)_pending.remove(c.connector);
				if (cbs != null)
				{
					Iterator i$ = cbs.iterator();
					while (i$.hasNext()) 
					{
						ConnectCallback cc = (ConnectCallback)i$.next();
						if (cc.removeConnectors(connectors))
							failedCallbacks.add(cc);
						else
							callbacks.add(cc);
					}
				}
			} while (true);
			ConnectCallback cc;
			for (i$ = callbacks.iterator(); i$.hasNext(); cc.removeFromPending())
			{
				cc = (ConnectCallback)i$.next();
				if (!$assertionsDisabled && failedCallbacks.contains(cc))
					throw new AssertionError();
			}

			notifyAll();
		}
		ConnectCallback cc;
		for (Iterator i$ = callbacks.iterator(); i$.hasNext(); cc.getConnection())
			cc = (ConnectCallback)i$.next();

		ConnectCallback cc;
		for (Iterator i$ = failedCallbacks.iterator(); i$.hasNext(); cc.setException(ex))
			cc = (ConnectCallback)i$.next();

	}

	private boolean addToPending(ConnectCallback cb, List connectors)
	{
		boolean found = false;
		Iterator i$ = connectors.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			ConnectorInfo p = (ConnectorInfo)i$.next();
			Set cbs = (Set)_pending.get(p.connector);
			if (cbs != null)
			{
				found = true;
				if (cb != null)
					cbs.add(cb);
			}
		} while (true);
		if (found)
			return true;
		i$ = connectors.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			ConnectorInfo p = (ConnectorInfo)i$.next();
			if (!_pending.containsKey(p.connector))
				_pending.put(p.connector, new HashSet());
		} while (true);
		return false;
	}

	private void removeFromPending(ConnectCallback cb, List connectors)
	{
		Iterator i$ = connectors.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			ConnectorInfo p = (ConnectorInfo)i$.next();
			Set cbs = (Set)_pending.get(p.connector);
			if (cbs != null)
				cbs.remove(cb);
		} while (true);
	}

	private void handleConnectionException(LocalException ex, boolean hasMore)
	{
		TraceLevels traceLevels = _instance.traceLevels();
		if (traceLevels.retry >= 2)
		{
			StringBuilder s = new StringBuilder(128);
			s.append("connection to endpoint failed");
			if (ex instanceof CommunicatorDestroyedException)
				s.append("\n");
			else
			if (hasMore)
				s.append(", trying next endpoint\n");
			else
				s.append(" and no more endpoints to try\n");
			s.append(ex.toString());
			_instance.initializationData().logger.trace(traceLevels.retryCat, s.toString());
		}
	}

	private void handleException(LocalException ex, boolean hasMore)
	{
		TraceLevels traceLevels = _instance.traceLevels();
		if (traceLevels.retry >= 2)
		{
			StringBuilder s = new StringBuilder(128);
			s.append("couldn't resolve endpoint host");
			if (ex instanceof CommunicatorDestroyedException)
				s.append("\n");
			else
			if (hasMore)
				s.append(", trying next endpoint\n");
			else
				s.append(" and no more endpoints to try\n");
			s.append(ex.toString());
			_instance.initializationData().logger.trace(traceLevels.retryCat, s.toString());
		}
	}










}
