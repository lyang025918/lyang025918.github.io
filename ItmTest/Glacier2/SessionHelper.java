// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SessionHelper.java

package Glacier2;

import Ice.*;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Semaphore;

// Referenced classes of package Glacier2:
//			SessionNotExistException, RouterPrx, SessionPrx, SessionCallback, 
//			CannotCreateSessionException, PermissionDeniedException, AMI_Router_refreshSession, RouterPrxHelper

public class SessionHelper
{
	private static interface ConnectStrategy
	{

		public abstract SessionPrx connect(RouterPrx routerprx)
			throws CannotCreateSessionException, PermissionDeniedException;
	}

	private class SessionRefreshThread extends Thread
	{

		private final RouterPrx _router;
		private final long _period;
		private boolean _done;
		final SessionHelper this$0;

		public synchronized void run()
		{
			do
			{
				try
				{
					_router.refreshSession_async(new AMI_Router_refreshSession() {

						final SessionRefreshThread this$1;

						public void ice_response()
						{
						}

						public void ice_exception(LocalException ex)
						{
							done();
							destroy();
						}

						public void ice_exception(UserException ex)
						{
							done();
							destroy();
						}

				
				{
					this$1 = SessionRefreshThread.this;
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

		SessionRefreshThread(RouterPrx router, long period)
		{
			this$0 = SessionHelper.this;
			super();
			_done = false;
			_router = router;
			_period = period;
			_done = false;
		}
	}


	private InitializationData _initData;
	private Communicator _communicator;
	private ObjectAdapter _adapter;
	private RouterPrx _router;
	private SessionPrx _session;
	private String _category;
	private SessionRefreshThread _refreshThread;
	private SessionCallback _callback;
	private boolean _destroy;
	private boolean _connected;
	private Thread _shutdownHook;
	static final boolean $assertionsDisabled = !Glacier2/SessionHelper.desiredAssertionStatus();

	public SessionHelper(SessionCallback callback, InitializationData initData)
	{
		_destroy = false;
		_connected = false;
		_callback = callback;
		_initData = initData;
	}

	public void destroy()
	{
label0:
		{
			synchronized (this)
			{
				if (!_destroy)
					break label0;
			}
			return;
		}
		_destroy = true;
		if (_connected)
			break MISSING_BLOCK_LABEL_29;
		sessionhelper;
		JVM INSTR monitorexit ;
		return;
		_session = null;
		try
		{
			Runtime.getRuntime().removeShutdownHook(_shutdownHook);
		}
		catch (IllegalStateException ex) { }
		catch (SecurityException ex) { }
		sessionhelper;
		JVM INSTR monitorexit ;
		  goto _L1
		exception;
		throw exception;
_L1:
		(new Thread(new Runnable() {

			final SessionHelper this$0;

			public void run()
			{
				destroyInternal();
			}

			
			{
				this$0 = SessionHelper.this;
				super();
			}
		})).start();
		return;
	}

	public synchronized Communicator communicator()
	{
		return _communicator;
	}

	public synchronized String categoryForClient()
		throws SessionNotExistException
	{
		if (_router == null)
			throw new SessionNotExistException();
		else
			return _category;
	}

	public synchronized ObjectPrx addWithUUID(Ice.Object servant)
		throws SessionNotExistException
	{
		if (_router == null)
			throw new SessionNotExistException();
		else
			return internalObjectAdapter().add(servant, new Identity(UUID.randomUUID().toString(), _category));
	}

	public synchronized SessionPrx session()
		throws SessionNotExistException
	{
		if (_session == null)
			throw new SessionNotExistException();
		else
			return _session;
	}

	public synchronized boolean isConnected()
	{
		return _connected;
	}

	public synchronized ObjectAdapter objectAdapter()
		throws SessionNotExistException
	{
		return internalObjectAdapter();
	}

	private ObjectAdapter internalObjectAdapter()
		throws SessionNotExistException
	{
		if (_router == null)
			throw new SessionNotExistException();
		if (_adapter == null)
		{
			_adapter = _communicator.createObjectAdapterWithRouter("", _router);
			_adapter.activate();
		}
		return _adapter;
	}

	protected synchronized void connect(final Map context)
	{
		connectImpl(new ConnectStrategy() {

			final Map val$context;
			final SessionHelper this$0;

			public SessionPrx connect(RouterPrx router)
				throws CannotCreateSessionException, PermissionDeniedException
			{
				return router.createSessionFromSecureConnection(context);
			}

			
				throws PermissionDeniedException, CannotCreateSessionException
			{
				this$0 = SessionHelper.this;
				context = map;
				super();
			}
		});
	}

	protected synchronized void connect(final String username, final String password, final Map context)
	{
		connectImpl(new ConnectStrategy() {

			final String val$username;
			final String val$password;
			final Map val$context;
			final SessionHelper this$0;

			public SessionPrx connect(RouterPrx router)
				throws CannotCreateSessionException, PermissionDeniedException
			{
				return router.createSession(username, password, context);
			}

			
				throws PermissionDeniedException, CannotCreateSessionException
			{
				this$0 = SessionHelper.this;
				username = s;
				password = s1;
				context = map;
				super();
			}
		});
	}

	private void connected(RouterPrx router, SessionPrx session)
	{
		Connection conn;
		long timeout;
		String category;
label0:
		{
			conn = router.ice_getCachedConnection();
			timeout = router.getSessionTimeout();
			category = router.getCategoryForClient();
			synchronized (this)
			{
				_router = router;
				if (!_destroy)
					break label0;
				(new Thread(new Runnable() {

					final SessionHelper this$0;

					public void run()
					{
						destroyInternal();
					}

			
			{
				this$0 = SessionHelper.this;
				super();
			}
				})).start();
			}
			return;
		}
		_category = category;
		_session = session;
		_connected = true;
		if (!$assertionsDisabled && _refreshThread != null)
			throw new AssertionError();
		if (timeout > 0L)
		{
			_refreshThread = new SessionRefreshThread(_router, (timeout * 1000L) / 2L);
			_refreshThread.start();
		}
		_shutdownHook = new Thread("Shutdown hook") {

			final SessionHelper this$0;

			public void run()
			{
				destroy();
			}

			
			{
				this$0 = SessionHelper.this;
				super(x0);
			}
		};
		try
		{
			Runtime.getRuntime().addShutdownHook(_shutdownHook);
		}
		catch (IllegalStateException e) { }
		catch (SecurityException ex) { }
		sessionhelper;
		JVM INSTR monitorexit ;
		  goto _L1
		exception;
		throw exception;
_L1:
		dispatchCallback(new Runnable() {

			final SessionHelper this$0;

			public void run()
			{
				try
				{
					_callback.connected(SessionHelper.this);
				}
				catch (SessionNotExistException ex)
				{
					destroy();
				}
			}

			
			{
				this$0 = SessionHelper.this;
				super();
			}
		}, conn);
		return;
	}

	private void destroyInternal()
	{
		RouterPrx router;
		Communicator communicator;
		SessionRefreshThread refreshThread;
label0:
		{
			if (!$assertionsDisabled && !_destroy)
				throw new AssertionError();
			router = null;
			communicator = null;
			refreshThread = null;
			synchronized (this)
			{
				if (_router != null)
					break label0;
			}
			return;
		}
		router = _router;
		_router = null;
		refreshThread = _refreshThread;
		_refreshThread = null;
		communicator = _communicator;
		_communicator = null;
		sessionhelper;
		JVM INSTR monitorexit ;
		  goto _L1
		exception;
		throw exception;
_L1:
		if (!$assertionsDisabled && communicator == null)
			throw new AssertionError();
		try
		{
			router.destroySession();
		}
		catch (ConnectionLostException e) { }
		catch (SessionNotExistException e) { }
		catch (Throwable e)
		{
			communicator.getLogger().warning((new StringBuilder()).append("SessionHelper: unexpected exception when destroying the session:\n").append(e).toString());
		}
		_connected = false;
		if (refreshThread != null)
		{
			refreshThread.done();
			do
				try
				{
					refreshThread.join();
					break;
				}
				catch (InterruptedException e) { }
			while (true);
		}
		try
		{
			communicator.destroy();
		}
		catch (Throwable ex) { }
		dispatchCallback(new Runnable() {

			final SessionHelper this$0;

			public void run()
			{
				_callback.disconnected(SessionHelper.this);
			}

			
			{
				this$0 = SessionHelper.this;
				super();
			}
		}, null);
		return;
	}

	private void connectImpl(final ConnectStrategy factory)
	{
		if (!$assertionsDisabled && _destroy)
			throw new AssertionError();
		try
		{
			_communicator = Util.initialize(_initData);
		}
		catch (final LocalException ex)
		{
			_destroy = true;
			dispatchCallback(new Runnable() {

				final LocalException val$ex;
				final SessionHelper this$0;

				public void run()
				{
					_callback.connectFailed(SessionHelper.this, ex);
				}

			
			{
				this$0 = SessionHelper.this;
				ex = localexception;
				super();
			}
			}, null);
			return;
		}
		(new Thread(new Runnable() {

			final ConnectStrategy val$factory;
			final SessionHelper this$0;

			public void run()
			{
				try
				{
					dispatchCallbackAndWait(new Runnable() {

						final 9 this$1;

						public void run()
						{
							_callback.createdCommunicator(0);
						}

					
					{
						this$1 = 9.this;
						super();
					}
					});
					RouterPrx routerPrx = RouterPrxHelper.uncheckedCast(_communicator.getDefaultRouter());
					SessionPrx session = factory.connect(routerPrx);
					connected(routerPrx, session);
				}
				catch (final Exception ex)
				{
					try
					{
						_communicator.destroy();
						_communicator = null;
					}
					catch (Throwable ex1) { }
					dispatchCallback(new Runnable() {

						final Exception val$ex;
						final 9 this$1;

						public void run()
						{
							_callback.connectFailed(0, ex);
						}

					
					{
						this$1 = 9.this;
						ex = exception;
						super();
					}
					}, null);
				}
			}

			
			{
				this$0 = SessionHelper.this;
				factory = connectstrategy;
				super();
			}
		})).start();
	}

	private void dispatchCallback(Runnable runnable, Connection conn)
	{
		if (_initData.dispatcher != null)
			_initData.dispatcher.dispatch(runnable, conn);
		else
			runnable.run();
	}

	private void dispatchCallbackAndWait(final Runnable runnable)
	{
		if (_initData.dispatcher != null)
		{
			final Semaphore sem = new Semaphore(0);
			_initData.dispatcher.dispatch(new Runnable() {

				final Runnable val$runnable;
				final Semaphore val$sem;
				final SessionHelper this$0;

				public void run()
				{
					runnable.run();
					sem.release();
				}

			
			{
				this$0 = SessionHelper.this;
				runnable = runnable1;
				sem = semaphore;
				super();
			}
			}, null);
			sem.acquireUninterruptibly();
		} else
		{
			runnable.run();
		}
	}








}
