// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Application.java

package Glacier2;

import Ice.Communicator;
import Ice.CommunicatorDestroyedException;
import Ice.ConnectionLostException;
import Ice.ConnectionRefusedException;
import Ice.Identity;
import Ice.InitializationData;
import Ice.IntHolder;
import Ice.LocalException;
import Ice.Logger;
import Ice.Object;
import Ice.ObjectAdapter;
import Ice.ObjectPrx;
import Ice.Properties;
import Ice.RequestFailedException;
import Ice.SignalPolicy;
import Ice.StringSeqHolder;
import Ice.TimeoutException;
import Ice.UnknownLocalException;
import Ice.UserException;
import Ice.Util;
import IceInternal.Ex;
import java.util.UUID;

// Referenced classes of package Glacier2:
//			SessionNotExistException, RouterPrxHelper, RouterPrx, SessionPrx, 
//			AMI_Router_refreshSession

public abstract class Application extends Ice.Application
{
	private class SessionPingThread extends Thread
	{

		private final RouterPrx _router;
		private final long _period;
		private boolean _done;
		final Application this$0;

		public synchronized void run()
		{
			do
			{
				try
				{
					_router.refreshSession_async(new AMI_Router_refreshSession() {

						final SessionPingThread this$1;

						public void ice_response()
						{
						}

						public void ice_exception(LocalException ex)
						{
							done();
							sessionDestroyed();
						}

						public void ice_exception(UserException ex)
						{
							done();
							sessionDestroyed();
						}

				
				{
					this$1 = SessionPingThread.this;
					super();
				}
					});
				}
				catch (CommunicatorDestroyedException ex)
				{
					break;
				}
				if (!_done)
					try
					{
						wait(_period);
					}
					catch (InterruptedException ex) { }
			} while (!_done);
		}

		public synchronized void done()
		{
			if (!_done)
			{
				_done = true;
				notify();
			}
		}

		SessionPingThread(RouterPrx router, long period)
		{
			this$0 = Application.this;
			super();
			_done = false;
			_router = router;
			_period = period;
			_done = false;
		}
	}

	public class RestartSessionException extends Exception
	{

		final Application this$0;

		public RestartSessionException()
		{
			this$0 = Application.this;
			super();
		}
	}


	private static ObjectAdapter _adapter;
	private static RouterPrx _router;
	private static SessionPrx _session;
	private static boolean _createdSession = false;
	private static String _category;
	static final boolean $assertionsDisabled = !Glacier2/Application.desiredAssertionStatus();

	public Application()
	{
	}

	public Application(SignalPolicy signalPolicy)
	{
		super(signalPolicy);
	}

	public abstract int runWithSession(String as[])
		throws RestartSessionException;

	public final int run(String args[])
	{
		if (!$assertionsDisabled)
			throw new AssertionError();
		else
			return 0;
	}

	public void restart()
		throws RestartSessionException
	{
		throw new RestartSessionException();
	}

	public abstract SessionPrx createSession();

	public void sessionDestroyed()
	{
	}

	public static RouterPrx router()
	{
		return _router;
	}

	public static SessionPrx session()
	{
		return _session;
	}

	public String categoryForClient()
		throws SessionNotExistException
	{
		if (_router == null)
			throw new SessionNotExistException();
		else
			return _category;
	}

	public Identity createCallbackIdentity(String name)
		throws SessionNotExistException
	{
		return new Identity(name, categoryForClient());
	}

	public ObjectPrx addWithUUID(Ice.Object servant)
		throws SessionNotExistException
	{
		return objectAdapter().add(servant, createCallbackIdentity(UUID.randomUUID().toString()));
	}

	public ObjectAdapter objectAdapter()
		throws SessionNotExistException
	{
		if (_router == null)
			throw new SessionNotExistException();
		synchronized (this)
		{
			if (_adapter == null)
			{
				_adapter = communicator().createObjectAdapterWithRouter("", _router);
				_adapter.activate();
			}
		}
		return _adapter;
	}

	protected int doMain(StringSeqHolder argHolder, InitializationData initData)
	{
		initData.properties.setProperty("Ice.ACM.Client", "0");
		initData.properties.setProperty("Ice.RetryIntervals", "-1");
		IntHolder ret = new IntHolder();
		boolean restart;
		do
		{
			InitializationData id = (InitializationData)initData.clone();
			id.properties = id.properties._clone();
			StringSeqHolder h = new StringSeqHolder();
			h.value = (String[])argHolder.value.clone();
			restart = doMain(h, id, ret);
		} while (restart);
		return ret.value;
	}

	private boolean doMain(StringSeqHolder argHolder, InitializationData initData, IntHolder status)
	{
		_callbackInProgress = false;
		_destroyed = false;
		_interrupted = false;
		boolean restart = false;
		status.value = 0;
		SessionPingThread ping = null;
		try
		{
			_communicator = Util.initialize(argHolder, initData);
			_router = RouterPrxHelper.uncheckedCast(communicator().getDefaultRouter());
			if (_router == null)
			{
				Util.getProcessLogger().error("no glacier2 router configured");
				status.value = 1;
			} else
			{
				if (_signalPolicy == SignalPolicy.HandleSignals)
					destroyOnInterrupt();
				try
				{
					_session = createSession();
					_createdSession = true;
				}
				catch (LocalException ex)
				{
					Util.getProcessLogger().error(Ex.toString(ex));
					status.value = 1;
				}
				if (_createdSession)
				{
					long timeout = _router.getSessionTimeout();
					if (timeout > 0L)
					{
						ping = new SessionPingThread(_router, (timeout * 1000L) / 2L);
						ping.start();
					}
					_category = _router.getCategoryForClient();
					status.value = runWithSession(argHolder.value);
				}
			}
		}
		catch (RestartSessionException ex)
		{
			restart = true;
		}
		catch (ConnectionRefusedException ex)
		{
			Util.getProcessLogger().error(Ex.toString(ex));
			restart = true;
		}
		catch (ConnectionLostException ex)
		{
			Util.getProcessLogger().error(Ex.toString(ex));
			restart = true;
		}
		catch (UnknownLocalException ex)
		{
			Util.getProcessLogger().error(Ex.toString(ex));
			restart = true;
		}
		catch (RequestFailedException ex)
		{
			Util.getProcessLogger().error(Ex.toString(ex));
			restart = true;
		}
		catch (TimeoutException ex)
		{
			Util.getProcessLogger().error(Ex.toString(ex));
			restart = true;
		}
		catch (LocalException ex)
		{
			Util.getProcessLogger().error(Ex.toString(ex));
			status.value = 1;
		}
		catch (Exception ex)
		{
			Util.getProcessLogger().error((new StringBuilder()).append("unknown exception:\n").append(Ex.toString(ex)).toString());
			status.value = 1;
		}
		catch (Error err)
		{
			Util.getProcessLogger().error((new StringBuilder()).append("Java error:\n").append(Ex.toString(err)).toString());
			status.value = 1;
		}
		if (_signalPolicy == SignalPolicy.HandleSignals)
			defaultInterrupt();
		synchronized (_mutex)
		{
			while (_callbackInProgress) 
				try
				{
					_mutex.wait();
				}
				catch (InterruptedException ex) { }
			if (_destroyed)
				_communicator = null;
			else
				_destroyed = true;
		}
		if (ping != null)
		{
			ping.done();
			do
				try
				{
					ping.join();
					break;
				}
				catch (InterruptedException ex) { }
			while (true);
			ping = null;
		}
		if (_createdSession && _router != null)
		{
			try
			{
				_router.destroySession();
			}
			catch (ConnectionLostException ex) { }
			catch (SessionNotExistException ex) { }
			catch (Throwable ex)
			{
				Util.getProcessLogger().error((new StringBuilder()).append("unexpected exception when destroying the session:\n").append(Ex.toString(ex)).toString());
			}
			_router = null;
		}
		if (_communicator != null)
		{
			try
			{
				_communicator.destroy();
			}
			catch (LocalException ex)
			{
				Util.getProcessLogger().error(Ex.toString(ex));
				status.value = 1;
			}
			catch (Exception ex)
			{
				Util.getProcessLogger().error((new StringBuilder()).append("unknown exception:\n").append(Ex.toString(ex)).toString());
				status.value = 1;
			}
			_communicator = null;
		}
		synchronized (_mutex)
		{
			if (_appHook != null)
				_appHook.done();
		}
		_adapter = null;
		_router = null;
		_session = null;
		_createdSession = false;
		_category = null;
		return restart;
	}

}
