// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SessionPrxHelper.java

package IceGrid;

import Glacier2.AMI_Session_destroy;
import Glacier2.Callback_Session_destroy;
import Ice.*;
import IceInternal.*;
import java.util.Map;

// Referenced classes of package IceGrid:
//			_SessionDel, AllocationException, ObjectNotRegisteredException, SessionPrx, 
//			_SessionDelM, _SessionDelD, Callback_Session_allocateObjectById, AMI_Session_allocateObjectById, 
//			Callback_Session_allocateObjectByType, AMI_Session_allocateObjectByType, Callback_Session_keepAlive, Callback_Session_releaseObject, 
//			Callback_Session_setAllocationTimeout

public final class SessionPrxHelper extends ObjectPrxHelperBase
	implements SessionPrx
{

	private static final String __destroy_name = "destroy";
	private static final String __allocateObjectById_name = "allocateObjectById";
	private static final String __allocateObjectByType_name = "allocateObjectByType";
	private static final String __keepAlive_name = "keepAlive";
	private static final String __releaseObject_name = "releaseObject";
	private static final String __setAllocationTimeout_name = "setAllocationTimeout";
	public static final String __ids[] = {
		"::Glacier2::Session", "::Ice::Object", "::IceGrid::Session"
	};

	public SessionPrxHelper()
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
				_SessionDel __del = (_SessionDel)__delBase;
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

	public AsyncResult begin_destroy(Callback_Session_destroy __cb)
	{
		return begin_destroy(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_destroy(Map __ctx, Callback_Session_destroy __cb)
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

	public boolean destroy_async(AMI_Session_destroy __cb)
	{
		AsyncResult __r = begin_destroy(null, false, __cb);
		return __r.sentSynchronously();
	}

	public boolean destroy_async(AMI_Session_destroy __cb, Map __ctx)
	{
		AsyncResult __r = begin_destroy(__ctx, true, __cb);
		return __r.sentSynchronously();
	}

	public ObjectPrx allocateObjectById(Identity id)
		throws AllocationException, ObjectNotRegisteredException
	{
		return allocateObjectById(id, null, false);
	}

	public ObjectPrx allocateObjectById(Identity id, Map __ctx)
		throws AllocationException, ObjectNotRegisteredException
	{
		return allocateObjectById(id, __ctx, true);
	}

	private ObjectPrx allocateObjectById(Identity id, Map __ctx, boolean __explicitCtx)
		throws AllocationException, ObjectNotRegisteredException
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_SessionDel __del;
		__checkTwowayOnly("allocateObjectById");
		__delBase = __getDelegate(false);
		__del = (_SessionDel)__delBase;
		return __del.allocateObjectById(id, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__handleExceptionWrapper(__delBase, __ex);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_allocateObjectById(Identity id)
	{
		return begin_allocateObjectById(id, null, false, null);
	}

	public AsyncResult begin_allocateObjectById(Identity id, Map __ctx)
	{
		return begin_allocateObjectById(id, __ctx, true, null);
	}

	public AsyncResult begin_allocateObjectById(Identity id, Callback __cb)
	{
		return begin_allocateObjectById(id, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_allocateObjectById(Identity id, Map __ctx, Callback __cb)
	{
		return begin_allocateObjectById(id, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_allocateObjectById(Identity id, Callback_Session_allocateObjectById __cb)
	{
		return begin_allocateObjectById(id, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_allocateObjectById(Identity id, Map __ctx, Callback_Session_allocateObjectById __cb)
	{
		return begin_allocateObjectById(id, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_allocateObjectById(Identity id, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("allocateObjectById");
		OutgoingAsync __result = new OutgoingAsync(this, "allocateObjectById", __cb);
		try
		{
			__result.__prepare("allocateObjectById", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			id.__write(__os);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public ObjectPrx end_allocateObjectById(AsyncResult __result)
		throws AllocationException, ObjectNotRegisteredException
	{
		AsyncResult.__check(__result, this, "allocateObjectById");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (AllocationException __ex)
			{
				throw __ex;
			}
			catch (ObjectNotRegisteredException __ex)
			{
				throw __ex;
			}
			catch (UserException __ex)
			{
				throw new UnknownUserException(__ex.ice_name(), __ex);
			}
		BasicStream __is = __result.__is();
		__is.startReadEncaps();
		ObjectPrx __ret = __is.readProxy();
		__is.endReadEncaps();
		return __ret;
	}

	public boolean allocateObjectById_async(AMI_Session_allocateObjectById __cb, Identity id)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("allocateObjectById");
			__r = begin_allocateObjectById(id, null, false, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "allocateObjectById", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public boolean allocateObjectById_async(AMI_Session_allocateObjectById __cb, Identity id, Map __ctx)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("allocateObjectById");
			__r = begin_allocateObjectById(id, __ctx, true, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "allocateObjectById", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public ObjectPrx allocateObjectByType(String type)
		throws AllocationException
	{
		return allocateObjectByType(type, null, false);
	}

	public ObjectPrx allocateObjectByType(String type, Map __ctx)
		throws AllocationException
	{
		return allocateObjectByType(type, __ctx, true);
	}

	private ObjectPrx allocateObjectByType(String type, Map __ctx, boolean __explicitCtx)
		throws AllocationException
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_SessionDel __del;
		__checkTwowayOnly("allocateObjectByType");
		__delBase = __getDelegate(false);
		__del = (_SessionDel)__delBase;
		return __del.allocateObjectByType(type, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__handleExceptionWrapper(__delBase, __ex);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_allocateObjectByType(String type)
	{
		return begin_allocateObjectByType(type, null, false, null);
	}

	public AsyncResult begin_allocateObjectByType(String type, Map __ctx)
	{
		return begin_allocateObjectByType(type, __ctx, true, null);
	}

	public AsyncResult begin_allocateObjectByType(String type, Callback __cb)
	{
		return begin_allocateObjectByType(type, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_allocateObjectByType(String type, Map __ctx, Callback __cb)
	{
		return begin_allocateObjectByType(type, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_allocateObjectByType(String type, Callback_Session_allocateObjectByType __cb)
	{
		return begin_allocateObjectByType(type, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_allocateObjectByType(String type, Map __ctx, Callback_Session_allocateObjectByType __cb)
	{
		return begin_allocateObjectByType(type, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_allocateObjectByType(String type, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("allocateObjectByType");
		OutgoingAsync __result = new OutgoingAsync(this, "allocateObjectByType", __cb);
		try
		{
			__result.__prepare("allocateObjectByType", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeString(type);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public ObjectPrx end_allocateObjectByType(AsyncResult __result)
		throws AllocationException
	{
		AsyncResult.__check(__result, this, "allocateObjectByType");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (AllocationException __ex)
			{
				throw __ex;
			}
			catch (UserException __ex)
			{
				throw new UnknownUserException(__ex.ice_name(), __ex);
			}
		BasicStream __is = __result.__is();
		__is.startReadEncaps();
		ObjectPrx __ret = __is.readProxy();
		__is.endReadEncaps();
		return __ret;
	}

	public boolean allocateObjectByType_async(AMI_Session_allocateObjectByType __cb, String type)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("allocateObjectByType");
			__r = begin_allocateObjectByType(type, null, false, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "allocateObjectByType", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public boolean allocateObjectByType_async(AMI_Session_allocateObjectByType __cb, String type, Map __ctx)
	{
		AsyncResult __r;
		try
		{
			__checkTwowayOnly("allocateObjectByType");
			__r = begin_allocateObjectByType(type, __ctx, true, __cb);
		}
		catch (TwowayOnlyException ex)
		{
			__r = new OutgoingAsync(this, "allocateObjectByType", __cb);
			__r.__exceptionAsync(ex);
		}
		return __r.sentSynchronously();
	}

	public void keepAlive()
	{
		keepAlive(null, false);
	}

	public void keepAlive(Map __ctx)
	{
		keepAlive(__ctx, true);
	}

	private void keepAlive(Map __ctx, boolean __explicitCtx)
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
				_SessionDel __del = (_SessionDel)__delBase;
				__del.keepAlive(__ctx);
				return;
			}
			catch (LocalExceptionWrapper __ex)
			{
				__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
			}
			catch (LocalException __ex)
			{
				__cnt = __handleException(__delBase, __ex, null, __cnt);
			}
		} while (true);
	}

	public AsyncResult begin_keepAlive()
	{
		return begin_keepAlive(null, false, null);
	}

	public AsyncResult begin_keepAlive(Map __ctx)
	{
		return begin_keepAlive(__ctx, true, null);
	}

	public AsyncResult begin_keepAlive(Callback __cb)
	{
		return begin_keepAlive(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_keepAlive(Map __ctx, Callback __cb)
	{
		return begin_keepAlive(__ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_keepAlive(Callback_Session_keepAlive __cb)
	{
		return begin_keepAlive(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_keepAlive(Map __ctx, Callback_Session_keepAlive __cb)
	{
		return begin_keepAlive(__ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_keepAlive(Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		OutgoingAsync __result = new OutgoingAsync(this, "keepAlive", __cb);
		try
		{
			__result.__prepare("keepAlive", OperationMode.Idempotent, __ctx, __explicitCtx);
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

	public void end_keepAlive(AsyncResult __result)
	{
		__end(__result, "keepAlive");
	}

	public void releaseObject(Identity id)
		throws AllocationException, ObjectNotRegisteredException
	{
		releaseObject(id, null, false);
	}

	public void releaseObject(Identity id, Map __ctx)
		throws AllocationException, ObjectNotRegisteredException
	{
		releaseObject(id, __ctx, true);
	}

	private void releaseObject(Identity id, Map __ctx, boolean __explicitCtx)
		throws AllocationException, ObjectNotRegisteredException
	{
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		int __cnt = 0;
		do
		{
			Ice._ObjectDel __delBase = null;
			try
			{
				__checkTwowayOnly("releaseObject");
				__delBase = __getDelegate(false);
				_SessionDel __del = (_SessionDel)__delBase;
				__del.releaseObject(id, __ctx);
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

	public AsyncResult begin_releaseObject(Identity id)
	{
		return begin_releaseObject(id, null, false, null);
	}

	public AsyncResult begin_releaseObject(Identity id, Map __ctx)
	{
		return begin_releaseObject(id, __ctx, true, null);
	}

	public AsyncResult begin_releaseObject(Identity id, Callback __cb)
	{
		return begin_releaseObject(id, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_releaseObject(Identity id, Map __ctx, Callback __cb)
	{
		return begin_releaseObject(id, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_releaseObject(Identity id, Callback_Session_releaseObject __cb)
	{
		return begin_releaseObject(id, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_releaseObject(Identity id, Map __ctx, Callback_Session_releaseObject __cb)
	{
		return begin_releaseObject(id, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_releaseObject(Identity id, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("releaseObject");
		OutgoingAsync __result = new OutgoingAsync(this, "releaseObject", __cb);
		try
		{
			__result.__prepare("releaseObject", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			id.__write(__os);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public void end_releaseObject(AsyncResult __result)
		throws AllocationException, ObjectNotRegisteredException
	{
		AsyncResult.__check(__result, this, "releaseObject");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (AllocationException __ex)
			{
				throw __ex;
			}
			catch (ObjectNotRegisteredException __ex)
			{
				throw __ex;
			}
			catch (UserException __ex)
			{
				throw new UnknownUserException(__ex.ice_name(), __ex);
			}
		BasicStream __is = __result.__is();
		__is.skipEmptyEncaps();
	}

	public void setAllocationTimeout(int timeout)
	{
		setAllocationTimeout(timeout, null, false);
	}

	public void setAllocationTimeout(int timeout, Map __ctx)
	{
		setAllocationTimeout(timeout, __ctx, true);
	}

	private void setAllocationTimeout(int timeout, Map __ctx, boolean __explicitCtx)
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
				_SessionDel __del = (_SessionDel)__delBase;
				__del.setAllocationTimeout(timeout, __ctx);
				return;
			}
			catch (LocalExceptionWrapper __ex)
			{
				__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
			}
			catch (LocalException __ex)
			{
				__cnt = __handleException(__delBase, __ex, null, __cnt);
			}
		} while (true);
	}

	public AsyncResult begin_setAllocationTimeout(int timeout)
	{
		return begin_setAllocationTimeout(timeout, null, false, null);
	}

	public AsyncResult begin_setAllocationTimeout(int timeout, Map __ctx)
	{
		return begin_setAllocationTimeout(timeout, __ctx, true, null);
	}

	public AsyncResult begin_setAllocationTimeout(int timeout, Callback __cb)
	{
		return begin_setAllocationTimeout(timeout, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_setAllocationTimeout(int timeout, Map __ctx, Callback __cb)
	{
		return begin_setAllocationTimeout(timeout, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_setAllocationTimeout(int timeout, Callback_Session_setAllocationTimeout __cb)
	{
		return begin_setAllocationTimeout(timeout, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_setAllocationTimeout(int timeout, Map __ctx, Callback_Session_setAllocationTimeout __cb)
	{
		return begin_setAllocationTimeout(timeout, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_setAllocationTimeout(int timeout, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		OutgoingAsync __result = new OutgoingAsync(this, "setAllocationTimeout", __cb);
		try
		{
			__result.__prepare("setAllocationTimeout", OperationMode.Idempotent, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeInt(timeout);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public void end_setAllocationTimeout(AsyncResult __result)
	{
		__end(__result, "setAllocationTimeout");
	}

	public static SessionPrx checkedCast(ObjectPrx __obj)
	{
		SessionPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (SessionPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId()))
				{
					SessionPrxHelper __h = new SessionPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static SessionPrx checkedCast(ObjectPrx __obj, Map __ctx)
	{
		SessionPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (SessionPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId(), __ctx))
				{
					SessionPrxHelper __h = new SessionPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static SessionPrx checkedCast(ObjectPrx __obj, String __facet)
	{
		SessionPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId()))
				{
					SessionPrxHelper __h = new SessionPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static SessionPrx checkedCast(ObjectPrx __obj, String __facet, Map __ctx)
	{
		SessionPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId(), __ctx))
				{
					SessionPrxHelper __h = new SessionPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static SessionPrx uncheckedCast(ObjectPrx __obj)
	{
		SessionPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (SessionPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				SessionPrxHelper __h = new SessionPrxHelper();
				__h.__copyFrom(__obj);
				__d = __h;
			}
		return __d;
	}

	public static SessionPrx uncheckedCast(ObjectPrx __obj, String __facet)
	{
		SessionPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			SessionPrxHelper __h = new SessionPrxHelper();
			__h.__copyFrom(__bb);
			__d = __h;
		}
		return __d;
	}

	public static String ice_staticId()
	{
		return __ids[2];
	}

	protected _ObjectDelM __createDelegateM()
	{
		return new _SessionDelM();
	}

	protected _ObjectDelD __createDelegateD()
	{
		return new _SessionDelD();
	}

	public static void __write(BasicStream __os, SessionPrx v)
	{
		__os.writeProxy(v);
	}

	public static SessionPrx __read(BasicStream __is)
	{
		ObjectPrx proxy = __is.readProxy();
		if (proxy != null)
		{
			SessionPrxHelper result = new SessionPrxHelper();
			result.__copyFrom(proxy);
			return result;
		} else
		{
			return null;
		}
	}

}
