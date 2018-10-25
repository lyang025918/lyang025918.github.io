// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ProcessPrxHelper.java

package Ice;

import IceInternal.BasicStream;
import IceInternal.CallbackBase;
import IceInternal.LocalExceptionWrapper;
import IceInternal.OutgoingAsync;
import java.util.Map;

// Referenced classes of package Ice:
//			ObjectPrxHelperBase, _ProcessDel, LocalException, ProcessPrx, 
//			FacetNotExistException, _ProcessDelM, _ProcessDelD, OperationMode, 
//			AsyncResult, ObjectPrx, Callback, Callback_Process_shutdown, 
//			AMI_Process_shutdown, Callback_Process_writeMessage, AMI_Process_writeMessage, _ObjectDelM, 
//			_ObjectDelD

public final class ProcessPrxHelper extends ObjectPrxHelperBase
	implements ProcessPrx
{

	private static final String __shutdown_name = "shutdown";
	private static final String __writeMessage_name = "writeMessage";
	public static final String __ids[] = {
		"::Ice::Object", "::Ice::Process"
	};

	public ProcessPrxHelper()
	{
	}

	public void shutdown()
	{
		shutdown(null, false);
	}

	public void shutdown(Map __ctx)
	{
		shutdown(__ctx, true);
	}

	private void shutdown(Map __ctx, boolean __explicitCtx)
	{
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		int __cnt = 0;
		do
		{
			_ObjectDel __delBase = null;
			try
			{
				__delBase = __getDelegate(false);
				_ProcessDel __del = (_ProcessDel)__delBase;
				__del.shutdown(__ctx);
				return;
			}
			catch (LocalExceptionWrapper __ex)
			{
				__handleExceptionWrapper(__delBase, __ex);
			}
			catch (LocalException __ex)
			{
				__cnt = __handleException(__delBase, __ex, null, __cnt);
			}
		} while (true);
	}

	public AsyncResult begin_shutdown()
	{
		return begin_shutdown(null, false, null);
	}

	public AsyncResult begin_shutdown(Map __ctx)
	{
		return begin_shutdown(__ctx, true, null);
	}

	public AsyncResult begin_shutdown(Callback __cb)
	{
		return begin_shutdown(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_shutdown(Map __ctx, Callback __cb)
	{
		return begin_shutdown(__ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_shutdown(Callback_Process_shutdown __cb)
	{
		return begin_shutdown(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_shutdown(Map __ctx, Callback_Process_shutdown __cb)
	{
		return begin_shutdown(__ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_shutdown(Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		OutgoingAsync __result = new OutgoingAsync(this, "shutdown", __cb);
		try
		{
			__result.__prepare("shutdown", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public void end_shutdown(AsyncResult __result)
	{
		__end(__result, "shutdown");
	}

	public boolean shutdown_async(AMI_Process_shutdown __cb)
	{
		AsyncResult __r = begin_shutdown(null, false, __cb);
		return __r.sentSynchronously();
	}

	public boolean shutdown_async(AMI_Process_shutdown __cb, Map __ctx)
	{
		AsyncResult __r = begin_shutdown(__ctx, true, __cb);
		return __r.sentSynchronously();
	}

	public void writeMessage(String message, int fd)
	{
		writeMessage(message, fd, null, false);
	}

	public void writeMessage(String message, int fd, Map __ctx)
	{
		writeMessage(message, fd, __ctx, true);
	}

	private void writeMessage(String message, int fd, Map __ctx, boolean __explicitCtx)
	{
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		int __cnt = 0;
		do
		{
			_ObjectDel __delBase = null;
			try
			{
				__delBase = __getDelegate(false);
				_ProcessDel __del = (_ProcessDel)__delBase;
				__del.writeMessage(message, fd, __ctx);
				return;
			}
			catch (LocalExceptionWrapper __ex)
			{
				__handleExceptionWrapper(__delBase, __ex);
			}
			catch (LocalException __ex)
			{
				__cnt = __handleException(__delBase, __ex, null, __cnt);
			}
		} while (true);
	}

	public AsyncResult begin_writeMessage(String message, int fd)
	{
		return begin_writeMessage(message, fd, null, false, null);
	}

	public AsyncResult begin_writeMessage(String message, int fd, Map __ctx)
	{
		return begin_writeMessage(message, fd, __ctx, true, null);
	}

	public AsyncResult begin_writeMessage(String message, int fd, Callback __cb)
	{
		return begin_writeMessage(message, fd, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_writeMessage(String message, int fd, Map __ctx, Callback __cb)
	{
		return begin_writeMessage(message, fd, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_writeMessage(String message, int fd, Callback_Process_writeMessage __cb)
	{
		return begin_writeMessage(message, fd, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_writeMessage(String message, int fd, Map __ctx, Callback_Process_writeMessage __cb)
	{
		return begin_writeMessage(message, fd, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_writeMessage(String message, int fd, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		OutgoingAsync __result = new OutgoingAsync(this, "writeMessage", __cb);
		try
		{
			__result.__prepare("writeMessage", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeString(message);
			__os.writeInt(fd);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public void end_writeMessage(AsyncResult __result)
	{
		__end(__result, "writeMessage");
	}

	public boolean writeMessage_async(AMI_Process_writeMessage __cb, String message, int fd)
	{
		AsyncResult __r = begin_writeMessage(message, fd, null, false, __cb);
		return __r.sentSynchronously();
	}

	public boolean writeMessage_async(AMI_Process_writeMessage __cb, String message, int fd, Map __ctx)
	{
		AsyncResult __r = begin_writeMessage(message, fd, __ctx, true, __cb);
		return __r.sentSynchronously();
	}

	public static ProcessPrx checkedCast(ObjectPrx __obj)
	{
		ProcessPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (ProcessPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId()))
				{
					ProcessPrxHelper __h = new ProcessPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static ProcessPrx checkedCast(ObjectPrx __obj, Map __ctx)
	{
		ProcessPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (ProcessPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId(), __ctx))
				{
					ProcessPrxHelper __h = new ProcessPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static ProcessPrx checkedCast(ObjectPrx __obj, String __facet)
	{
		ProcessPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId()))
				{
					ProcessPrxHelper __h = new ProcessPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static ProcessPrx checkedCast(ObjectPrx __obj, String __facet, Map __ctx)
	{
		ProcessPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId(), __ctx))
				{
					ProcessPrxHelper __h = new ProcessPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static ProcessPrx uncheckedCast(ObjectPrx __obj)
	{
		ProcessPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (ProcessPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				ProcessPrxHelper __h = new ProcessPrxHelper();
				__h.__copyFrom(__obj);
				__d = __h;
			}
		return __d;
	}

	public static ProcessPrx uncheckedCast(ObjectPrx __obj, String __facet)
	{
		ProcessPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			ProcessPrxHelper __h = new ProcessPrxHelper();
			__h.__copyFrom(__bb);
			__d = __h;
		}
		return __d;
	}

	public static String ice_staticId()
	{
		return __ids[1];
	}

	protected _ObjectDelM __createDelegateM()
	{
		return new _ProcessDelM();
	}

	protected _ObjectDelD __createDelegateD()
	{
		return new _ProcessDelD();
	}

	public static void __write(BasicStream __os, ProcessPrx v)
	{
		__os.writeProxy(v);
	}

	public static ProcessPrx __read(BasicStream __is)
	{
		ObjectPrx proxy = __is.readProxy();
		if (proxy != null)
		{
			ProcessPrxHelper result = new ProcessPrxHelper();
			result.__copyFrom(proxy);
			return result;
		} else
		{
			return null;
		}
	}

}
