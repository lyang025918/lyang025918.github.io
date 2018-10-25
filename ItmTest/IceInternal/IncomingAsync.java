// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IncomingAsync.java

package IceInternal;

import Ice.*;
import java.util.Iterator;
import java.util.LinkedList;

// Referenced classes of package IceInternal:
//			IncomingBase, Incoming, Instance, BasicStream

public class IncomingAsync extends IncomingBase
	implements AMDCallback
{

	private final boolean _retriable;
	private boolean _active;
	static final boolean $assertionsDisabled = !IceInternal/IncomingAsync.desiredAssertionStatus();

	public IncomingAsync(Incoming in)
	{
		super(in);
		_active = false;
		_retriable = in.isRetriable();
		if (_retriable)
		{
			in.setActive(this);
			_active = true;
		}
	}

	public void ice_exception(Exception ex)
	{
		if (!_retriable)
			break MISSING_BLOCK_LABEL_91;
		if (_interceptorAsyncCallbackList == null)
			break MISSING_BLOCK_LABEL_60;
		for (Iterator i$ = _interceptorAsyncCallbackList.iterator(); i$.hasNext();)
		{
			DispatchInterceptorAsyncCallback cb = (DispatchInterceptorAsyncCallback)i$.next();
			if (!cb.exception(ex))
				return;
		}

		break MISSING_BLOCK_LABEL_60;
		RuntimeException exc;
		exc;
		return;
label0:
		{
			synchronized (this)
			{
				if (_active)
					break label0;
			}
			return;
		}
		_active = false;
		incomingasync;
		JVM INSTR monitorexit ;
		break MISSING_BLOCK_LABEL_91;
		exception;
		throw exception;
		if (_connection != null)
			__exception(ex);
		else
		if (_instance.initializationData().properties.getPropertyAsIntWithDefault("Ice.Warn.Dispatch", 1) > 0)
			__warning(ex);
		return;
	}

	final void __deactivate(Incoming in)
	{
		if (!$assertionsDisabled && !_retriable)
			throw new AssertionError();
		synchronized (this)
		{
			if (!_active)
				throw new ResponseSentException();
			_active = false;
		}
		in.adopt(this);
	}

	protected final void __response(boolean ok)
	{
		if (_locator != null && !__servantLocatorFinished())
			return;
		try
		{
			if (!$assertionsDisabled && _connection == null)
				throw new AssertionError();
			if (_response)
			{
				_os.endWriteEncaps();
				int save = _os.pos();
				_os.pos(18);
				if (ok)
					_os.writeByte((byte)0);
				else
					_os.writeByte((byte)1);
				_os.pos(save);
				_connection.sendResponse(_os, _compress);
			} else
			{
				_connection.sendNoResponse();
			}
			_connection = null;
		}
		catch (LocalException ex)
		{
			_connection.invokeException(ex, 1);
		}
		return;
	}

	protected final void __exception(Exception exc)
	{
		if (_locator != null && !__servantLocatorFinished())
			return;
		try
		{
			__handleException(exc);
		}
		catch (LocalException ex)
		{
			_connection.invokeException(ex, 1);
		}
		return;
	}

	protected final boolean __validateResponse(boolean ok)
	{
		if (!_retriable)
			break MISSING_BLOCK_LABEL_94;
		Iterator i$;
		if (_interceptorAsyncCallbackList == null)
			break MISSING_BLOCK_LABEL_62;
		i$ = _interceptorAsyncCallbackList.iterator();
_L1:
		DispatchInterceptorAsyncCallback cb;
		if (!i$.hasNext())
			break MISSING_BLOCK_LABEL_62;
		cb = (DispatchInterceptorAsyncCallback)i$.next();
		if (!cb.response(ok))
			return false;
		  goto _L1
		RuntimeException ex;
		ex;
		return false;
		ex = this;
		JVM INSTR monitorenter ;
		if (!_active)
			return false;
		_active = false;
		ex;
		JVM INSTR monitorexit ;
		break MISSING_BLOCK_LABEL_94;
		Exception exception;
		exception;
		throw exception;
		return true;
	}

	protected final BasicStream __os()
	{
		return _os;
	}

}
