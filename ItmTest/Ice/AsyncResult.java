// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AsyncResult.java

package Ice;

import IceInternal.BasicStream;
import IceInternal.CallbackBase;
import IceInternal.DispatchWorkItem;
import IceInternal.Ex;
import IceInternal.Instance;
import IceInternal.ThreadPool;

// Referenced classes of package Ice:
//			UserException, CommunicatorDestroyedException, InitializationData, Properties, 
//			Logger, LocalException, Communicator, Connection, 
//			ObjectPrx

public class AsyncResult
{

	protected Instance _instance;
	protected String _operation;
	protected Object _monitor;
	protected BasicStream _is;
	protected BasicStream _os;
	protected static final byte OK = 1;
	protected static final byte Done = 2;
	protected static final byte Sent = 4;
	protected static final byte EndCalled = 8;
	protected byte _state;
	protected boolean _sentSynchronously;
	protected LocalException _exception;
	private CallbackBase _callback;

	protected AsyncResult(Instance instance, String op, CallbackBase del)
	{
		_monitor = new Object();
		_instance = instance;
		_operation = op;
		_is = new BasicStream(instance, false, false);
		_os = new BasicStream(instance, false, false);
		_state = 0;
		_exception = null;
		_callback = del;
	}

	public Communicator getCommunicator()
	{
		return null;
	}

	public Connection getConnection()
	{
		return null;
	}

	public ObjectPrx getProxy()
	{
		return null;
	}

	public final boolean isCompleted()
	{
		Object obj = _monitor;
		JVM INSTR monitorenter ;
		return (_state & 2) > 0;
		Exception exception;
		exception;
		throw exception;
	}

	public final void waitForCompleted()
	{
		synchronized (_monitor)
		{
			while ((_state & 2) == 0) 
				try
				{
					_monitor.wait();
				}
				catch (InterruptedException ex) { }
		}
	}

	public final boolean isSent()
	{
		Object obj = _monitor;
		JVM INSTR monitorenter ;
		return (_state & 4) > 0;
		Exception exception;
		exception;
		throw exception;
	}

	public final void waitForSent()
	{
		synchronized (_monitor)
		{
			while ((_state & 4) == 0 && _exception == null) 
				try
				{
					_monitor.wait();
				}
				catch (InterruptedException ex) { }
		}
	}

	public final void throwLocalException()
	{
		synchronized (_monitor)
		{
			if (_exception != null)
				throw _exception;
		}
	}

	public final boolean sentSynchronously()
	{
		return _sentSynchronously;
	}

	public final String getOperation()
	{
		return _operation;
	}

	public final BasicStream __os()
	{
		return _os;
	}

	public final BasicStream __is()
	{
		return _is;
	}

	public final boolean __wait()
	{
		Object obj = _monitor;
		JVM INSTR monitorenter ;
		if ((_state & 8) > 0)
			throw new IllegalArgumentException("end_ method called more than once");
		for (_state |= 8; (_state & 2) == 0;)
			try
			{
				_monitor.wait();
			}
			catch (InterruptedException ex) { }

		if (_exception != null)
			throw _exception;
		return (_state & 1) > 0;
		Exception exception;
		exception;
		throw exception;
	}

	public final void __throwUserException()
		throws UserException
	{
		try
		{
			_is.startReadEncaps();
			_is.throwException();
		}
		catch (UserException ex)
		{
			_is.endReadEncaps();
			throw ex;
		}
	}

	public final void __exceptionAsync(LocalException ex)
	{
		try
		{
			_instance.clientThreadPool().execute(new DispatchWorkItem(ex) {

				final LocalException val$ex;
				final AsyncResult this$0;

				public void run()
				{
					__exception(ex);
				}

			
			{
				this$0 = AsyncResult.this;
				ex = localexception;
				super(x0);
			}
			});
		}
		catch (CommunicatorDestroyedException exc)
		{
			throw exc;
		}
	}

	public final void __exception(LocalException ex)
	{
		synchronized (_monitor)
		{
			_state |= 2;
			_exception = ex;
			_monitor.notifyAll();
		}
		if (_callback != null)
			try
			{
				_callback.__completed(this);
			}
			catch (RuntimeException exc)
			{
				__warning(exc);
			}
			catch (AssertionError exc)
			{
				__error(exc);
			}
			catch (OutOfMemoryError exc)
			{
				__error(exc);
			}
	}

	protected final void __sentInternal()
	{
		if (_callback != null)
			try
			{
				_callback.__sent(this);
			}
			catch (RuntimeException ex)
			{
				__warning(ex);
			}
			catch (AssertionError exc)
			{
				__error(exc);
			}
			catch (OutOfMemoryError exc)
			{
				__error(exc);
			}
	}

	public final void __sentAsync()
	{
		try
		{
			_instance.clientThreadPool().execute(new DispatchWorkItem(_instance) {

				final AsyncResult this$0;

				public void run()
				{
					__sentInternal();
				}

			
			{
				this$0 = AsyncResult.this;
				super(x0);
			}
			});
		}
		catch (CommunicatorDestroyedException exc) { }
	}

	public static void __check(AsyncResult r, ObjectPrx prx, String operation)
	{
		__check(r, operation);
		if (r.getProxy() != prx)
			throw new IllegalArgumentException((new StringBuilder()).append("Proxy for call to end_").append(operation).append(" does not match proxy that was used to call corresponding begin_").append(operation).append(" method").toString());
		else
			return;
	}

	public static void __check(AsyncResult r, Connection con, String operation)
	{
		__check(r, operation);
		if (r.getConnection() != con)
			throw new IllegalArgumentException((new StringBuilder()).append("Connection for call to end_").append(operation).append(" does not match connection that was used to call corresponding begin_").append(operation).append(" method").toString());
		else
			return;
	}

	public static void __check(AsyncResult r, Communicator com, String operation)
	{
		__check(r, operation);
		if (r.getCommunicator() != com)
			throw new IllegalArgumentException((new StringBuilder()).append("Communicator for call to end_").append(operation).append(" does not match communicator that was used to call corresponding ").append("begin_").append(operation).append(" method").toString());
		else
			return;
	}

	protected static void __check(AsyncResult r, String operation)
	{
		if (r == null)
			throw new IllegalArgumentException("AsyncResult == null");
		if (r.getOperation() != operation)
			throw new IllegalArgumentException((new StringBuilder()).append("Incorrect operation for end_").append(operation).append(" method: ").append(r.getOperation()).toString());
		else
			return;
	}

	protected final void __response()
	{
		if (_callback != null)
			try
			{
				_callback.__completed(this);
			}
			catch (RuntimeException ex)
			{
				__warning(ex);
			}
			catch (AssertionError exc)
			{
				__error(exc);
			}
			catch (OutOfMemoryError exc)
			{
				__error(exc);
			}
	}

	protected final void __warning(RuntimeException ex)
	{
		if (_instance.initializationData().properties.getPropertyAsIntWithDefault("Ice.Warn.AMICallback", 1) > 0)
		{
			String s = (new StringBuilder()).append("exception raised by AMI callback:\n").append(Ex.toString(ex)).toString();
			_instance.initializationData().logger.warning(s);
		}
	}

	protected final void __error(Error error)
	{
		String s = (new StringBuilder()).append("error raised by AMI callback:\n").append(Ex.toString(error)).toString();
		_instance.initializationData().logger.error(s);
	}
}
