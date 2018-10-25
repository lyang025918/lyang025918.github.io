// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EndpointHostResolver.java

package IceInternal;

import Ice.*;
import java.util.Iterator;
import java.util.LinkedList;

// Referenced classes of package IceInternal:
//			Instance, Util, Ex, Network, 
//			EndpointI, EndpointI_connectors

public class EndpointHostResolver
{
	private final class HelperThread extends Thread
	{

		final EndpointHostResolver this$0;

		public void run()
		{
			try
			{
				EndpointHostResolver.this.run();
			}
			catch (Exception ex)
			{
				String s = (new StringBuilder()).append("exception in endpoint host resolver thread ").append(getName()).append(":\n").append(Ex.toString(ex)).toString();
				_instance.initializationData().logger.error(s);
			}
		}

		HelperThread()
		{
			this$0 = EndpointHostResolver.this;
			super();
			String threadName = _instance.initializationData().properties.getProperty("Ice.ProgramName");
			if (threadName.length() > 0)
				threadName = (new StringBuilder()).append(threadName).append("-").toString();
			setName((new StringBuilder()).append(threadName).append("Ice.EndpointHostResolverThread").toString());
		}
	}

	static class ResolveEntry
	{

		String host;
		int port;
		EndpointI endpoint;
		EndpointI_connectors callback;

		ResolveEntry()
		{
		}
	}


	private final Instance _instance;
	private boolean _destroyed;
	private LinkedList _queue;
	private HelperThread _thread;
	static final boolean $assertionsDisabled = !IceInternal/EndpointHostResolver.desiredAssertionStatus();

	EndpointHostResolver(Instance instance)
	{
		_queue = new LinkedList();
		_instance = instance;
		try
		{
			_thread = new HelperThread();
			if (_instance.initializationData().properties.getProperty("Ice.ThreadPriority").length() > 0)
				_thread.setPriority(Util.getThreadPriorityProperty(_instance.initializationData().properties, "Ice"));
			_thread.start();
		}
		catch (RuntimeException ex)
		{
			String s = (new StringBuilder()).append("cannot create thread for endpoint host resolver thread:\n").append(Ex.toString(ex)).toString();
			_instance.initializationData().logger.error(s);
			throw ex;
		}
	}

	public synchronized void resolve(String host, int port, EndpointI endpoint, EndpointI_connectors callback)
	{
		if (!$assertionsDisabled && _destroyed)
		{
			throw new AssertionError();
		} else
		{
			ResolveEntry entry = new ResolveEntry();
			entry.host = host;
			entry.port = port;
			entry.endpoint = endpoint;
			entry.callback = callback;
			_queue.add(entry);
			notify();
			return;
		}
	}

	public synchronized void destroy()
	{
		if (!$assertionsDisabled && _destroyed)
		{
			throw new AssertionError();
		} else
		{
			_destroyed = true;
			notify();
			return;
		}
	}

	public void joinWithThread()
	{
		if (_thread != null)
			try
			{
				_thread.join();
			}
			catch (InterruptedException ex) { }
	}

	public void run()
	{
_L3:
label0:
		{
			synchronized (this)
			{
				while (!_destroyed && _queue.isEmpty()) 
					try
					{
						wait();
					}
					catch (InterruptedException ex) { }
				if (!_destroyed)
					break label0;
			}
			break; /* Loop/switch isn't completed */
		}
		ResolveEntry resolve = (ResolveEntry)_queue.removeFirst();
		endpointhostresolver;
		JVM INSTR monitorexit ;
		  goto _L1
		exception;
		throw exception;
_L1:
		try
		{
			resolve.callback.connectors(resolve.endpoint.connectors(Network.getAddresses(resolve.host, resolve.port, _instance.protocolSupport())));
		}
		catch (LocalException ex)
		{
			resolve.callback.exception(ex);
		}
		if (true) goto _L3; else goto _L2
_L2:
		ResolveEntry p;
		for (Iterator i$ = _queue.iterator(); i$.hasNext(); p.callback.exception(new CommunicatorDestroyedException()))
			p = (ResolveEntry)i$.next();

		_queue.clear();
		return;
	}


}
