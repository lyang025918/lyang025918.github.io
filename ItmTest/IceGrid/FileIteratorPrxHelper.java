// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FileIteratorPrxHelper.java

package IceGrid;

import Ice.*;
import IceInternal.*;
import java.util.Map;

// Referenced classes of package IceGrid:
//			_FileIteratorDel, FileNotAvailableException, FileIteratorPrx, _FileIteratorDelM, 
//			_FileIteratorDelD, Callback_FileIterator_destroy, Callback_FileIterator_read

public final class FileIteratorPrxHelper extends ObjectPrxHelperBase
	implements FileIteratorPrx
{

	private static final String __destroy_name = "destroy";
	private static final String __read_name = "read";
	public static final String __ids[] = {
		"::Ice::Object", "::IceGrid::FileIterator"
	};

	public FileIteratorPrxHelper()
	{
	}

	public void destroy()
	{
		destroy(null, false);
	}

	public void destroy(Map __ctx)
	{
		destroy(__ctx, true);
	}

	private void destroy(Map __ctx, boolean __explicitCtx)
	{
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		int __cnt = 0;
		do
		{
			Ice._ObjectDel __delBase = null;
			try
			{
				__delBase = __getDelegate(false);
				_FileIteratorDel __del = (_FileIteratorDel)__delBase;
				__del.destroy(__ctx);
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

	public AsyncResult begin_destroy()
	{
		return begin_destroy(null, false, null);
	}

	public AsyncResult begin_destroy(Map __ctx)
	{
		return begin_destroy(__ctx, true, null);
	}

	public AsyncResult begin_destroy(Callback __cb)
	{
		return begin_destroy(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_destroy(Map __ctx, Callback __cb)
	{
		return begin_destroy(__ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_destroy(Callback_FileIterator_destroy __cb)
	{
		return begin_destroy(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_destroy(Map __ctx, Callback_FileIterator_destroy __cb)
	{
		return begin_destroy(__ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_destroy(Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		OutgoingAsync __result = new OutgoingAsync(this, "destroy", __cb);
		try
		{
			__result.__prepare("destroy", OperationMode.Normal, __ctx, __explicitCtx);
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

	public void end_destroy(AsyncResult __result)
	{
		__end(__result, "destroy");
	}

	public boolean read(int size, StringSeqHolder lines)
		throws FileNotAvailableException
	{
		return read(size, lines, null, false);
	}

	public boolean read(int size, StringSeqHolder lines, Map __ctx)
		throws FileNotAvailableException
	{
		return read(size, lines, __ctx, true);
	}

	private boolean read(int size, StringSeqHolder lines, Map __ctx, boolean __explicitCtx)
		throws FileNotAvailableException
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_FileIteratorDel __del;
		__checkTwowayOnly("read");
		__delBase = __getDelegate(false);
		__del = (_FileIteratorDel)__delBase;
		return __del.read(size, lines, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__handleExceptionWrapper(__delBase, __ex);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_read(int size)
	{
		return begin_read(size, null, false, null);
	}

	public AsyncResult begin_read(int size, Map __ctx)
	{
		return begin_read(size, __ctx, true, null);
	}

	public AsyncResult begin_read(int size, Callback __cb)
	{
		return begin_read(size, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_read(int size, Map __ctx, Callback __cb)
	{
		return begin_read(size, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_read(int size, Callback_FileIterator_read __cb)
	{
		return begin_read(size, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_read(int size, Map __ctx, Callback_FileIterator_read __cb)
	{
		return begin_read(size, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_read(int size, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("read");
		OutgoingAsync __result = new OutgoingAsync(this, "read", __cb);
		try
		{
			__result.__prepare("read", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeInt(size);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public boolean end_read(StringSeqHolder lines, AsyncResult __result)
		throws FileNotAvailableException
	{
		AsyncResult.__check(__result, this, "read");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (FileNotAvailableException __ex)
			{
				throw __ex;
			}
			catch (UserException __ex)
			{
				throw new UnknownUserException(__ex.ice_name(), __ex);
			}
		BasicStream __is = __result.__is();
		__is.startReadEncaps();
		lines.value = StringSeqHelper.read(__is);
		boolean __ret = __is.readBool();
		__is.endReadEncaps();
		return __ret;
	}

	public static FileIteratorPrx checkedCast(ObjectPrx __obj)
	{
		FileIteratorPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (FileIteratorPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId()))
				{
					FileIteratorPrxHelper __h = new FileIteratorPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static FileIteratorPrx checkedCast(ObjectPrx __obj, Map __ctx)
	{
		FileIteratorPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (FileIteratorPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId(), __ctx))
				{
					FileIteratorPrxHelper __h = new FileIteratorPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static FileIteratorPrx checkedCast(ObjectPrx __obj, String __facet)
	{
		FileIteratorPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId()))
				{
					FileIteratorPrxHelper __h = new FileIteratorPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static FileIteratorPrx checkedCast(ObjectPrx __obj, String __facet, Map __ctx)
	{
		FileIteratorPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId(), __ctx))
				{
					FileIteratorPrxHelper __h = new FileIteratorPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static FileIteratorPrx uncheckedCast(ObjectPrx __obj)
	{
		FileIteratorPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (FileIteratorPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				FileIteratorPrxHelper __h = new FileIteratorPrxHelper();
				__h.__copyFrom(__obj);
				__d = __h;
			}
		return __d;
	}

	public static FileIteratorPrx uncheckedCast(ObjectPrx __obj, String __facet)
	{
		FileIteratorPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			FileIteratorPrxHelper __h = new FileIteratorPrxHelper();
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
		return new _FileIteratorDelM();
	}

	protected _ObjectDelD __createDelegateD()
	{
		return new _FileIteratorDelD();
	}

	public static void __write(BasicStream __os, FileIteratorPrx v)
	{
		__os.writeProxy(v);
	}

	public static FileIteratorPrx __read(BasicStream __is)
	{
		ObjectPrx proxy = __is.readProxy();
		if (proxy != null)
		{
			FileIteratorPrxHelper result = new FileIteratorPrxHelper();
			result.__copyFrom(proxy);
			return result;
		} else
		{
			return null;
		}
	}

}
