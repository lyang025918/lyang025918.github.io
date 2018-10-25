// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IncomingConnectionFactory.java

package IceInternal;

import Ice.*;
import IceUtilInternal.Assert;
import java.nio.channels.SelectableChannel;
import java.util.*;

// Referenced classes of package IceInternal:
//			EventHandler, ConnectionReaper, EndpointIHolder, CommunicatorBatchOutgoingAsync, 
//			Acceptor, Network, Instance, Transceiver, 
//			DefaultsAndOverrides, EndpointI, ThreadPool, Ex, 
//			ThreadPoolCurrent

public final class IncomingConnectionFactory extends EventHandler
	implements Ice.ConnectionI.StartCallback
{

	private static final int StateActive = 0;
	private static final int StateHolding = 1;
	private static final int StateClosed = 2;
	private static final int StateFinished = 3;
	private final Instance _instance;
	private final ConnectionReaper _reaper = new ConnectionReaper();
	private Acceptor _acceptor;
	private Transceiver _transceiver;
	private EndpointI _endpoint;
	private ObjectAdapter _adapter;
	private final boolean _warn;
	private Set _connections;
	private int _state;
	static final boolean $assertionsDisabled = !IceInternal/IncomingConnectionFactory.desiredAssertionStatus();

	public synchronized void activate()
	{
		setState(0);
	}

	public synchronized void hold()
	{
		setState(1);
	}

	public synchronized void destroy()
	{
		setState(2);
	}

	public void waitUntilHolding()
	{
		LinkedList connections;
		synchronized (this)
		{
			while (_state < 1) 
				try
				{
					wait();
				}
				catch (InterruptedException ex) { }
			connections = new LinkedList(_connections);
		}
		ConnectionI connection;
		for (Iterator i$ = connections.iterator(); i$.hasNext(); connection.waitUntilHolding())
			connection = (ConnectionI)i$.next();

	}

	public void waitUntilFinished()
	{
		LinkedList connections = null;
		synchronized (this)
		{
			while (_state != 3) 
				try
				{
					wait();
				}
				catch (InterruptedException ex) { }
			_adapter = null;
			connections = new LinkedList(_connections);
		}
		if (connections != null)
		{
			ConnectionI connection;
			for (Iterator i$ = connections.iterator(); i$.hasNext(); connection.waitUntilFinished())
				connection = (ConnectionI)i$.next();

		}
		synchronized (this)
		{
			List cons = _reaper.swapConnections();
			if (!$assertionsDisabled && (cons != null ? cons.size() : 0) != _connections.size())
				throw new AssertionError();
			if (cons != null)
				cons.clear();
			_connections.clear();
		}
	}

	public EndpointI endpoint()
	{
		return _endpoint;
	}

	public synchronized LinkedList connections()
	{
		LinkedList connections = new LinkedList();
		Iterator i$ = _connections.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			ConnectionI connection = (ConnectionI)i$.next();
			if (connection.isActiveOrHolding())
				connections.add(connection);
		} while (true);
		return connections;
	}

	public void flushAsyncBatchRequests(CommunicatorBatchOutgoingAsync outAsync)
	{
		for (Iterator i$ = connections().iterator(); i$.hasNext();)
		{
			ConnectionI c = (ConnectionI)i$.next();
			try
			{
				outAsync.flushConnection(c);
			}
			catch (LocalException ex) { }
		}

	}

	public void message(ThreadPoolCurrent current)
	{
		ConnectionI connection;
label0:
		{
			connection = null;
			synchronized (this)
			{
				if (_state < 2)
					break label0;
			}
			return;
		}
		if (_state != 1)
			break MISSING_BLOCK_LABEL_31;
		Thread.yield();
		incomingconnectionfactory;
		JVM INSTR monitorexit ;
		return;
		Transceiver transceiver;
		List cons = _reaper.swapConnections();
		if (cons != null)
		{
			ConnectionI c;
			for (Iterator i$ = cons.iterator(); i$.hasNext(); _connections.remove(c))
				c = (ConnectionI)i$.next();

		}
		transceiver = null;
		transceiver = _acceptor.accept();
		break MISSING_BLOCK_LABEL_241;
		SocketException ex;
		ex;
		if (!Network.noMoreFds(ex.getCause()))
			break MISSING_BLOCK_LABEL_220;
		String s = (new StringBuilder()).append("fatal error: can't accept more connections:\n").append(ex.getCause().getMessage()).toString();
		s = (new StringBuilder()).append(s).append('\n').append(_acceptor.toString()).toString();
		_instance.initializationData().logger.error(s);
		Runtime.getRuntime().halt(1);
		break MISSING_BLOCK_LABEL_220;
		Exception exception;
		exception;
		Runtime.getRuntime().halt(1);
		throw exception;
		incomingconnectionfactory;
		JVM INSTR monitorexit ;
		return;
		ex;
		if (_warn)
			warning(ex);
		incomingconnectionfactory;
		JVM INSTR monitorexit ;
		return;
		if (!$assertionsDisabled && transceiver == null)
			throw new AssertionError();
		try
		{
			connection = new ConnectionI(_instance, _reaper, transceiver, null, _endpoint, _adapter);
			break MISSING_BLOCK_LABEL_320;
		}
		// Misplaced declaration of an exception variable
		catch (SocketException ex)
		{
			try
			{
				transceiver.close();
			}
			catch (LocalException exc) { }
			if (_warn)
				warning(ex);
		}
		incomingconnectionfactory;
		JVM INSTR monitorexit ;
		return;
		_connections.add(connection);
		incomingconnectionfactory;
		JVM INSTR monitorexit ;
		  goto _L1
		exception1;
		throw exception1;
_L1:
		if (!$assertionsDisabled && connection == null)
		{
			throw new AssertionError();
		} else
		{
			connection.start(this);
			return;
		}
	}

	public synchronized void finished(ThreadPoolCurrent current)
	{
		if (!$assertionsDisabled && _state != 2)
		{
			throw new AssertionError();
		} else
		{
			setState(3);
			return;
		}
	}

	public synchronized String toString()
	{
		if (_transceiver != null)
			return _transceiver.toString();
		if (!$assertionsDisabled && _acceptor == null)
			throw new AssertionError();
		else
			return _acceptor.toString();
	}

	public SelectableChannel fd()
	{
		if (!$assertionsDisabled && _acceptor == null)
			throw new AssertionError();
		else
			return _acceptor.fd();
	}

	public boolean hasMoreData()
	{
		if (!$assertionsDisabled && _acceptor == null)
			throw new AssertionError();
		else
			return false;
	}

	public synchronized void connectionStartCompleted(ConnectionI connection)
	{
		if (_state == 0)
			connection.activate();
	}

	public synchronized void connectionStartFailed(ConnectionI connection, LocalException ex)
	{
		if (_state >= 2)
			return;
		if (_warn)
			warning(ex);
	}

	public IncomingConnectionFactory(Instance instance, EndpointI endpoint, ObjectAdapter adapter, String adapterName)
	{
		_connections = new HashSet();
		_instance = instance;
		_endpoint = endpoint;
		_adapter = adapter;
		_warn = _instance.initializationData().properties.getPropertyAsInt("Ice.Warn.Connections") > 0;
		_state = 1;
		DefaultsAndOverrides defaultsAndOverrides = _instance.defaultsAndOverrides();
		if (defaultsAndOverrides.overrideTimeout)
			_endpoint = _endpoint.timeout(defaultsAndOverrides.overrideTimeoutValue);
		if (defaultsAndOverrides.overrideCompress)
			_endpoint = _endpoint.compress(defaultsAndOverrides.overrideCompressValue);
		try
		{
			EndpointIHolder h = new EndpointIHolder();
			h.value = _endpoint;
			_transceiver = _endpoint.transceiver(h);
			if (_transceiver != null)
			{
				_endpoint = h.value;
				ConnectionI connection = new ConnectionI(_instance, _reaper, _transceiver, null, _endpoint, _adapter);
				connection.start(null);
				_connections.add(connection);
			} else
			{
				h.value = _endpoint;
				_acceptor = _endpoint.acceptor(h, adapterName);
				_endpoint = h.value;
				if (!$assertionsDisabled && _acceptor == null)
					throw new AssertionError();
				_acceptor.listen();
				((ObjectAdapterI)_adapter).getThreadPool().initialize(this);
			}
		}
		catch (Exception ex)
		{
			if (_transceiver != null)
				try
				{
					_transceiver.close();
				}
				catch (LocalException e) { }
			if (_acceptor != null)
				try
				{
					_acceptor.close();
				}
				catch (LocalException e) { }
			_state = 3;
			_connections.clear();
			if (ex instanceof LocalException)
				throw (LocalException)ex;
			else
				throw new SyscallException(ex);
		}
	}

	protected synchronized void finalize()
		throws Throwable
	{
		Assert.FinalizerAssert(_state == 3);
		Assert.FinalizerAssert(_connections.isEmpty());
		super.finalize();
	}

	private void setState(int state)
	{
		if (_state == state)
			return;
		switch (state)
		{
		default:
			break;

		case 0: // '\0'
			if (_state != 1)
				return;
			if (_acceptor != null)
				((ObjectAdapterI)_adapter).getThreadPool().register(this, 1);
			ConnectionI connection;
			for (Iterator i$ = _connections.iterator(); i$.hasNext(); connection.activate())
				connection = (ConnectionI)i$.next();

			break;

		case 1: // '\001'
			if (_state != 0)
				return;
			if (_acceptor != null)
				((ObjectAdapterI)_adapter).getThreadPool().unregister(this, 1);
			ConnectionI connection;
			for (Iterator i$ = _connections.iterator(); i$.hasNext(); connection.hold())
				connection = (ConnectionI)i$.next();

			break;

		case 2: // '\002'
			if (_acceptor != null)
				((ObjectAdapterI)_adapter).getThreadPool().finish(this);
			else
				state = 3;
			ConnectionI connection;
			for (Iterator i$ = _connections.iterator(); i$.hasNext(); connection.destroy(0))
				connection = (ConnectionI)i$.next();

			break;

		case 3: // '\003'
			if (!$assertionsDisabled && _state != 2)
				throw new AssertionError();
			if (_acceptor != null)
				_acceptor.close();
			break;
		}
		_state = state;
		notifyAll();
	}

	private void warning(LocalException ex)
	{
		String s = (new StringBuilder()).append("connection exception:\n").append(Ex.toString(ex)).append('\n').append(_acceptor.toString()).toString();
		_instance.initializationData().logger.warning(s);
	}

}
