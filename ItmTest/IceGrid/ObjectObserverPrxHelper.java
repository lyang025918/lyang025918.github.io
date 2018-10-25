// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ObjectObserverPrxHelper.java

package IceGrid;

import Ice.*;
import IceInternal.*;
import java.util.Map;

// Referenced classes of package IceGrid:
//			_ObjectObserverDel, ObjectObserverPrx, _ObjectObserverDelM, _ObjectObserverDelD, 
//			ObjectInfo, ObjectInfoSeqHelper, Callback_ObjectObserver_objectAdded, Callback_ObjectObserver_objectInit, 
//			AMI_ObjectObserver_objectInit, Callback_ObjectObserver_objectRemoved, Callback_ObjectObserver_objectUpdated

public final class ObjectObserverPrxHelper extends ObjectPrxHelperBase
	implements ObjectObserverPrx
{

	private static final String __objectAdded_name = "objectAdded";
	private static final String __objectInit_name = "objectInit";
	private static final String __objectRemoved_name = "objectRemoved";
	private static final String __objectUpdated_name = "objectUpdated";
	public static final String __ids[] = {
		"::Ice::Object", "::IceGrid::ObjectObserver"
	};

	public ObjectObserverPrxHelper()
	{
	}

	public void objectAdded(ObjectInfo info)
	{
		objectAdded(info, null, false);
	}

	public void objectAdded(ObjectInfo info, Map __ctx)
	{
		objectAdded(info, __ctx, true);
	}

	private void objectAdded(ObjectInfo info, Map __ctx, boolean __explicitCtx)
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
				_ObjectObserverDel __del = (_ObjectObserverDel)__delBase;
				__del.objectAdded(info, __ctx);
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

	public AsyncResult begin_objectAdded(ObjectInfo info)
	{
		return begin_objectAdded(info, null, false, null);
	}

	public AsyncResult begin_objectAdded(ObjectInfo info, Map __ctx)
	{
		return begin_objectAdded(info, __ctx, true, null);
	}

	public AsyncResult begin_objectAdded(ObjectInfo info, Callback __cb)
	{
		return begin_objectAdded(info, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_objectAdded(ObjectInfo info, Map __ctx, Callback __cb)
	{
		return begin_objectAdded(info, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_objectAdded(ObjectInfo info, Callback_ObjectObserver_objectAdded __cb)
	{
		return begin_objectAdded(info, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_objectAdded(ObjectInfo info, Map __ctx, Callback_ObjectObserver_objectAdded __cb)
	{
		return begin_objectAdded(info, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_objectAdded(ObjectInfo info, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		OutgoingAsync __result = new OutgoingAsync(this, "objectAdded", __cb);
		try
		{
			__result.__prepare("objectAdded", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			info.__write(__os);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public void end_objectAdded(AsyncResult __result)
	{
		__end(__result, "objectAdded");
	}

	public void objectInit(ObjectInfo objects[])
	{
		objectInit(objects, null, false);
	}

	public void objectInit(ObjectInfo objects[], Map __ctx)
	{
		objectInit(objects, __ctx, true);
	}

	private void objectInit(ObjectInfo objects[], Map __ctx, boolean __explicitCtx)
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
				_ObjectObserverDel __del = (_ObjectObserverDel)__delBase;
				__del.objectInit(objects, __ctx);
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

	public AsyncResult begin_objectInit(ObjectInfo objects[])
	{
		return begin_objectInit(objects, null, false, null);
	}

	public AsyncResult begin_objectInit(ObjectInfo objects[], Map __ctx)
	{
		return begin_objectInit(objects, __ctx, true, null);
	}

	public AsyncResult begin_objectInit(ObjectInfo objects[], Callback __cb)
	{
		return begin_objectInit(objects, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_objectInit(ObjectInfo objects[], Map __ctx, Callback __cb)
	{
		return begin_objectInit(objects, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_objectInit(ObjectInfo objects[], Callback_ObjectObserver_objectInit __cb)
	{
		return begin_objectInit(objects, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_objectInit(ObjectInfo objects[], Map __ctx, Callback_ObjectObserver_objectInit __cb)
	{
		return begin_objectInit(objects, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_objectInit(ObjectInfo objects[], Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		OutgoingAsync __result = new OutgoingAsync(this, "objectInit", __cb);
		try
		{
			__result.__prepare("objectInit", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			ObjectInfoSeqHelper.write(__os, objects);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public void end_objectInit(AsyncResult __result)
	{
		__end(__result, "objectInit");
	}

	public boolean objectInit_async(AMI_ObjectObserver_objectInit __cb, ObjectInfo objects[])
	{
		AsyncResult __r = begin_objectInit(objects, null, false, __cb);
		return __r.sentSynchronously();
	}

	public boolean objectInit_async(AMI_ObjectObserver_objectInit __cb, ObjectInfo objects[], Map __ctx)
	{
		AsyncResult __r = begin_objectInit(objects, __ctx, true, __cb);
		return __r.sentSynchronously();
	}

	public void objectRemoved(Identity id)
	{
		objectRemoved(id, null, false);
	}

	public void objectRemoved(Identity id, Map __ctx)
	{
		objectRemoved(id, __ctx, true);
	}

	private void objectRemoved(Identity id, Map __ctx, boolean __explicitCtx)
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
				_ObjectObserverDel __del = (_ObjectObserverDel)__delBase;
				__del.objectRemoved(id, __ctx);
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

	public AsyncResult begin_objectRemoved(Identity id)
	{
		return begin_objectRemoved(id, null, false, null);
	}

	public AsyncResult begin_objectRemoved(Identity id, Map __ctx)
	{
		return begin_objectRemoved(id, __ctx, true, null);
	}

	public AsyncResult begin_objectRemoved(Identity id, Callback __cb)
	{
		return begin_objectRemoved(id, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_objectRemoved(Identity id, Map __ctx, Callback __cb)
	{
		return begin_objectRemoved(id, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_objectRemoved(Identity id, Callback_ObjectObserver_objectRemoved __cb)
	{
		return begin_objectRemoved(id, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_objectRemoved(Identity id, Map __ctx, Callback_ObjectObserver_objectRemoved __cb)
	{
		return begin_objectRemoved(id, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_objectRemoved(Identity id, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		OutgoingAsync __result = new OutgoingAsync(this, "objectRemoved", __cb);
		try
		{
			__result.__prepare("objectRemoved", OperationMode.Normal, __ctx, __explicitCtx);
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

	public void end_objectRemoved(AsyncResult __result)
	{
		__end(__result, "objectRemoved");
	}

	public void objectUpdated(ObjectInfo info)
	{
		objectUpdated(info, null, false);
	}

	public void objectUpdated(ObjectInfo info, Map __ctx)
	{
		objectUpdated(info, __ctx, true);
	}

	private void objectUpdated(ObjectInfo info, Map __ctx, boolean __explicitCtx)
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
				_ObjectObserverDel __del = (_ObjectObserverDel)__delBase;
				__del.objectUpdated(info, __ctx);
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

	public AsyncResult begin_objectUpdated(ObjectInfo info)
	{
		return begin_objectUpdated(info, null, false, null);
	}

	public AsyncResult begin_objectUpdated(ObjectInfo info, Map __ctx)
	{
		return begin_objectUpdated(info, __ctx, true, null);
	}

	public AsyncResult begin_objectUpdated(ObjectInfo info, Callback __cb)
	{
		return begin_objectUpdated(info, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_objectUpdated(ObjectInfo info, Map __ctx, Callback __cb)
	{
		return begin_objectUpdated(info, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_objectUpdated(ObjectInfo info, Callback_ObjectObserver_objectUpdated __cb)
	{
		return begin_objectUpdated(info, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_objectUpdated(ObjectInfo info, Map __ctx, Callback_ObjectObserver_objectUpdated __cb)
	{
		return begin_objectUpdated(info, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_objectUpdated(ObjectInfo info, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		OutgoingAsync __result = new OutgoingAsync(this, "objectUpdated", __cb);
		try
		{
			__result.__prepare("objectUpdated", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			info.__write(__os);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public void end_objectUpdated(AsyncResult __result)
	{
		__end(__result, "objectUpdated");
	}

	public static ObjectObserverPrx checkedCast(ObjectPrx __obj)
	{
		ObjectObserverPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (ObjectObserverPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId()))
				{
					ObjectObserverPrxHelper __h = new ObjectObserverPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static ObjectObserverPrx checkedCast(ObjectPrx __obj, Map __ctx)
	{
		ObjectObserverPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (ObjectObserverPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId(), __ctx))
				{
					ObjectObserverPrxHelper __h = new ObjectObserverPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static ObjectObserverPrx checkedCast(ObjectPrx __obj, String __facet)
	{
		ObjectObserverPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId()))
				{
					ObjectObserverPrxHelper __h = new ObjectObserverPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static ObjectObserverPrx checkedCast(ObjectPrx __obj, String __facet, Map __ctx)
	{
		ObjectObserverPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId(), __ctx))
				{
					ObjectObserverPrxHelper __h = new ObjectObserverPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static ObjectObserverPrx uncheckedCast(ObjectPrx __obj)
	{
		ObjectObserverPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (ObjectObserverPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				ObjectObserverPrxHelper __h = new ObjectObserverPrxHelper();
				__h.__copyFrom(__obj);
				__d = __h;
			}
		return __d;
	}

	public static ObjectObserverPrx uncheckedCast(ObjectPrx __obj, String __facet)
	{
		ObjectObserverPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			ObjectObserverPrxHelper __h = new ObjectObserverPrxHelper();
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
		return new _ObjectObserverDelM();
	}

	protected _ObjectDelD __createDelegateD()
	{
		return new _ObjectObserverDelD();
	}

	public static void __write(BasicStream __os, ObjectObserverPrx v)
	{
		__os.writeProxy(v);
	}

	public static ObjectObserverPrx __read(BasicStream __is)
	{
		ObjectPrx proxy = __is.readProxy();
		if (proxy != null)
		{
			ObjectObserverPrxHelper result = new ObjectObserverPrxHelper();
			result.__copyFrom(proxy);
			return result;
		} else
		{
			return null;
		}
	}

}
