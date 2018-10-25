// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AdminSessionPrxHelper.java

package IceGrid;

import Glacier2.AMI_Session_destroy;
import Glacier2.Callback_Session_destroy;
import Ice.*;
import IceInternal.*;
import java.util.Map;

// Referenced classes of package IceGrid:
//			_AdminSessionDel, AccessDeniedException, FileNotAvailableException, NodeNotExistException, 
//			NodeUnreachableException, RegistryNotExistException, RegistryUnreachableException, DeploymentException, 
//			ServerNotExistException, ObserverAlreadyRegisteredException, AdminSessionPrx, _AdminSessionDelM, 
//			_AdminSessionDelD, AdminPrxHelper, FileIteratorPrxHelper, RegistryObserverPrxHelper, 
//			NodeObserverPrxHelper, ApplicationObserverPrxHelper, AdapterObserverPrxHelper, ObjectObserverPrxHelper, 
//			Callback_AdminSession_finishUpdate, AdminPrx, Callback_AdminSession_getAdmin, Callback_AdminSession_getAdminCallbackTemplate, 
//			Callback_AdminSession_getReplicaName, Callback_AdminSession_keepAlive, FileIteratorPrx, Callback_AdminSession_openNodeStdErr, 
//			Callback_AdminSession_openNodeStdOut, Callback_AdminSession_openRegistryStdErr, Callback_AdminSession_openRegistryStdOut, Callback_AdminSession_openServerLog, 
//			Callback_AdminSession_openServerStdErr, Callback_AdminSession_openServerStdOut, RegistryObserverPrx, NodeObserverPrx, 
//			ApplicationObserverPrx, AdapterObserverPrx, ObjectObserverPrx, Callback_AdminSession_setObservers, 
//			Callback_AdminSession_setObserversByIdentity, Callback_AdminSession_startUpdate

public final class AdminSessionPrxHelper extends ObjectPrxHelperBase
	implements AdminSessionPrx
{

	private static final String __destroy_name = "destroy";
	private static final String __finishUpdate_name = "finishUpdate";
	private static final String __getAdmin_name = "getAdmin";
	private static final String __getAdminCallbackTemplate_name = "getAdminCallbackTemplate";
	private static final String __getReplicaName_name = "getReplicaName";
	private static final String __keepAlive_name = "keepAlive";
	private static final String __openNodeStdErr_name = "openNodeStdErr";
	private static final String __openNodeStdOut_name = "openNodeStdOut";
	private static final String __openRegistryStdErr_name = "openRegistryStdErr";
	private static final String __openRegistryStdOut_name = "openRegistryStdOut";
	private static final String __openServerLog_name = "openServerLog";
	private static final String __openServerStdErr_name = "openServerStdErr";
	private static final String __openServerStdOut_name = "openServerStdOut";
	private static final String __setObservers_name = "setObservers";
	private static final String __setObserversByIdentity_name = "setObserversByIdentity";
	private static final String __startUpdate_name = "startUpdate";
	public static final String __ids[] = {
		"::Glacier2::Session", "::Ice::Object", "::IceGrid::AdminSession"
	};

	public AdminSessionPrxHelper()
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
				_AdminSessionDel __del = (_AdminSessionDel)__delBase;
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

	public void finishUpdate()
		throws AccessDeniedException
	{
		finishUpdate(null, false);
	}

	public void finishUpdate(Map __ctx)
		throws AccessDeniedException
	{
		finishUpdate(__ctx, true);
	}

	private void finishUpdate(Map __ctx, boolean __explicitCtx)
		throws AccessDeniedException
	{
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		int __cnt = 0;
		do
		{
			Ice._ObjectDel __delBase = null;
			try
			{
				__checkTwowayOnly("finishUpdate");
				__delBase = __getDelegate(false);
				_AdminSessionDel __del = (_AdminSessionDel)__delBase;
				__del.finishUpdate(__ctx);
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

	public AsyncResult begin_finishUpdate()
	{
		return begin_finishUpdate(null, false, null);
	}

	public AsyncResult begin_finishUpdate(Map __ctx)
	{
		return begin_finishUpdate(__ctx, true, null);
	}

	public AsyncResult begin_finishUpdate(Callback __cb)
	{
		return begin_finishUpdate(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_finishUpdate(Map __ctx, Callback __cb)
	{
		return begin_finishUpdate(__ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_finishUpdate(Callback_AdminSession_finishUpdate __cb)
	{
		return begin_finishUpdate(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_finishUpdate(Map __ctx, Callback_AdminSession_finishUpdate __cb)
	{
		return begin_finishUpdate(__ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_finishUpdate(Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("finishUpdate");
		OutgoingAsync __result = new OutgoingAsync(this, "finishUpdate", __cb);
		try
		{
			__result.__prepare("finishUpdate", OperationMode.Normal, __ctx, __explicitCtx);
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

	public void end_finishUpdate(AsyncResult __result)
		throws AccessDeniedException
	{
		AsyncResult.__check(__result, this, "finishUpdate");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (AccessDeniedException __ex)
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

	public AdminPrx getAdmin()
	{
		return getAdmin(null, false);
	}

	public AdminPrx getAdmin(Map __ctx)
	{
		return getAdmin(__ctx, true);
	}

	private AdminPrx getAdmin(Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_AdminSessionDel __del;
		__checkTwowayOnly("getAdmin");
		__delBase = __getDelegate(false);
		__del = (_AdminSessionDel)__delBase;
		return __del.getAdmin(__ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_getAdmin()
	{
		return begin_getAdmin(null, false, null);
	}

	public AsyncResult begin_getAdmin(Map __ctx)
	{
		return begin_getAdmin(__ctx, true, null);
	}

	public AsyncResult begin_getAdmin(Callback __cb)
	{
		return begin_getAdmin(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getAdmin(Map __ctx, Callback __cb)
	{
		return begin_getAdmin(__ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getAdmin(Callback_AdminSession_getAdmin __cb)
	{
		return begin_getAdmin(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getAdmin(Map __ctx, Callback_AdminSession_getAdmin __cb)
	{
		return begin_getAdmin(__ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_getAdmin(Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("getAdmin");
		OutgoingAsync __result = new OutgoingAsync(this, "getAdmin", __cb);
		try
		{
			__result.__prepare("getAdmin", OperationMode.Nonmutating, __ctx, __explicitCtx);
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

	public AdminPrx end_getAdmin(AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "getAdmin");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (UserException __ex)
			{
				throw new UnknownUserException(__ex.ice_name(), __ex);
			}
		BasicStream __is = __result.__is();
		__is.startReadEncaps();
		AdminPrx __ret = AdminPrxHelper.__read(__is);
		__is.endReadEncaps();
		return __ret;
	}

	public ObjectPrx getAdminCallbackTemplate()
	{
		return getAdminCallbackTemplate(null, false);
	}

	public ObjectPrx getAdminCallbackTemplate(Map __ctx)
	{
		return getAdminCallbackTemplate(__ctx, true);
	}

	private ObjectPrx getAdminCallbackTemplate(Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_AdminSessionDel __del;
		__checkTwowayOnly("getAdminCallbackTemplate");
		__delBase = __getDelegate(false);
		__del = (_AdminSessionDel)__delBase;
		return __del.getAdminCallbackTemplate(__ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_getAdminCallbackTemplate()
	{
		return begin_getAdminCallbackTemplate(null, false, null);
	}

	public AsyncResult begin_getAdminCallbackTemplate(Map __ctx)
	{
		return begin_getAdminCallbackTemplate(__ctx, true, null);
	}

	public AsyncResult begin_getAdminCallbackTemplate(Callback __cb)
	{
		return begin_getAdminCallbackTemplate(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getAdminCallbackTemplate(Map __ctx, Callback __cb)
	{
		return begin_getAdminCallbackTemplate(__ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getAdminCallbackTemplate(Callback_AdminSession_getAdminCallbackTemplate __cb)
	{
		return begin_getAdminCallbackTemplate(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getAdminCallbackTemplate(Map __ctx, Callback_AdminSession_getAdminCallbackTemplate __cb)
	{
		return begin_getAdminCallbackTemplate(__ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_getAdminCallbackTemplate(Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("getAdminCallbackTemplate");
		OutgoingAsync __result = new OutgoingAsync(this, "getAdminCallbackTemplate", __cb);
		try
		{
			__result.__prepare("getAdminCallbackTemplate", OperationMode.Idempotent, __ctx, __explicitCtx);
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

	public ObjectPrx end_getAdminCallbackTemplate(AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "getAdminCallbackTemplate");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
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

	public String getReplicaName()
	{
		return getReplicaName(null, false);
	}

	public String getReplicaName(Map __ctx)
	{
		return getReplicaName(__ctx, true);
	}

	private String getReplicaName(Map __ctx, boolean __explicitCtx)
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_AdminSessionDel __del;
		__checkTwowayOnly("getReplicaName");
		__delBase = __getDelegate(false);
		__del = (_AdminSessionDel)__delBase;
		return __del.getReplicaName(__ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__cnt = __handleExceptionWrapperRelaxed(__delBase, __ex, null, __cnt);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_getReplicaName()
	{
		return begin_getReplicaName(null, false, null);
	}

	public AsyncResult begin_getReplicaName(Map __ctx)
	{
		return begin_getReplicaName(__ctx, true, null);
	}

	public AsyncResult begin_getReplicaName(Callback __cb)
	{
		return begin_getReplicaName(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getReplicaName(Map __ctx, Callback __cb)
	{
		return begin_getReplicaName(__ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getReplicaName(Callback_AdminSession_getReplicaName __cb)
	{
		return begin_getReplicaName(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_getReplicaName(Map __ctx, Callback_AdminSession_getReplicaName __cb)
	{
		return begin_getReplicaName(__ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_getReplicaName(Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("getReplicaName");
		OutgoingAsync __result = new OutgoingAsync(this, "getReplicaName", __cb);
		try
		{
			__result.__prepare("getReplicaName", OperationMode.Idempotent, __ctx, __explicitCtx);
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

	public String end_getReplicaName(AsyncResult __result)
	{
		AsyncResult.__check(__result, this, "getReplicaName");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (UserException __ex)
			{
				throw new UnknownUserException(__ex.ice_name(), __ex);
			}
		BasicStream __is = __result.__is();
		__is.startReadEncaps();
		String __ret = __is.readString();
		__is.endReadEncaps();
		return __ret;
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
				_AdminSessionDel __del = (_AdminSessionDel)__delBase;
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

	public AsyncResult begin_keepAlive(Callback_AdminSession_keepAlive __cb)
	{
		return begin_keepAlive(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_keepAlive(Map __ctx, Callback_AdminSession_keepAlive __cb)
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

	public FileIteratorPrx openNodeStdErr(String name, int count)
		throws FileNotAvailableException, NodeNotExistException, NodeUnreachableException
	{
		return openNodeStdErr(name, count, null, false);
	}

	public FileIteratorPrx openNodeStdErr(String name, int count, Map __ctx)
		throws FileNotAvailableException, NodeNotExistException, NodeUnreachableException
	{
		return openNodeStdErr(name, count, __ctx, true);
	}

	private FileIteratorPrx openNodeStdErr(String name, int count, Map __ctx, boolean __explicitCtx)
		throws FileNotAvailableException, NodeNotExistException, NodeUnreachableException
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_AdminSessionDel __del;
		__checkTwowayOnly("openNodeStdErr");
		__delBase = __getDelegate(false);
		__del = (_AdminSessionDel)__delBase;
		return __del.openNodeStdErr(name, count, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__handleExceptionWrapper(__delBase, __ex);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_openNodeStdErr(String name, int count)
	{
		return begin_openNodeStdErr(name, count, null, false, null);
	}

	public AsyncResult begin_openNodeStdErr(String name, int count, Map __ctx)
	{
		return begin_openNodeStdErr(name, count, __ctx, true, null);
	}

	public AsyncResult begin_openNodeStdErr(String name, int count, Callback __cb)
	{
		return begin_openNodeStdErr(name, count, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_openNodeStdErr(String name, int count, Map __ctx, Callback __cb)
	{
		return begin_openNodeStdErr(name, count, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_openNodeStdErr(String name, int count, Callback_AdminSession_openNodeStdErr __cb)
	{
		return begin_openNodeStdErr(name, count, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_openNodeStdErr(String name, int count, Map __ctx, Callback_AdminSession_openNodeStdErr __cb)
	{
		return begin_openNodeStdErr(name, count, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_openNodeStdErr(String name, int count, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("openNodeStdErr");
		OutgoingAsync __result = new OutgoingAsync(this, "openNodeStdErr", __cb);
		try
		{
			__result.__prepare("openNodeStdErr", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeString(name);
			__os.writeInt(count);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public FileIteratorPrx end_openNodeStdErr(AsyncResult __result)
		throws FileNotAvailableException, NodeNotExistException, NodeUnreachableException
	{
		AsyncResult.__check(__result, this, "openNodeStdErr");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (FileNotAvailableException __ex)
			{
				throw __ex;
			}
			catch (NodeNotExistException __ex)
			{
				throw __ex;
			}
			catch (NodeUnreachableException __ex)
			{
				throw __ex;
			}
			catch (UserException __ex)
			{
				throw new UnknownUserException(__ex.ice_name(), __ex);
			}
		BasicStream __is = __result.__is();
		__is.startReadEncaps();
		FileIteratorPrx __ret = FileIteratorPrxHelper.__read(__is);
		__is.endReadEncaps();
		return __ret;
	}

	public FileIteratorPrx openNodeStdOut(String name, int count)
		throws FileNotAvailableException, NodeNotExistException, NodeUnreachableException
	{
		return openNodeStdOut(name, count, null, false);
	}

	public FileIteratorPrx openNodeStdOut(String name, int count, Map __ctx)
		throws FileNotAvailableException, NodeNotExistException, NodeUnreachableException
	{
		return openNodeStdOut(name, count, __ctx, true);
	}

	private FileIteratorPrx openNodeStdOut(String name, int count, Map __ctx, boolean __explicitCtx)
		throws FileNotAvailableException, NodeNotExistException, NodeUnreachableException
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_AdminSessionDel __del;
		__checkTwowayOnly("openNodeStdOut");
		__delBase = __getDelegate(false);
		__del = (_AdminSessionDel)__delBase;
		return __del.openNodeStdOut(name, count, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__handleExceptionWrapper(__delBase, __ex);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_openNodeStdOut(String name, int count)
	{
		return begin_openNodeStdOut(name, count, null, false, null);
	}

	public AsyncResult begin_openNodeStdOut(String name, int count, Map __ctx)
	{
		return begin_openNodeStdOut(name, count, __ctx, true, null);
	}

	public AsyncResult begin_openNodeStdOut(String name, int count, Callback __cb)
	{
		return begin_openNodeStdOut(name, count, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_openNodeStdOut(String name, int count, Map __ctx, Callback __cb)
	{
		return begin_openNodeStdOut(name, count, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_openNodeStdOut(String name, int count, Callback_AdminSession_openNodeStdOut __cb)
	{
		return begin_openNodeStdOut(name, count, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_openNodeStdOut(String name, int count, Map __ctx, Callback_AdminSession_openNodeStdOut __cb)
	{
		return begin_openNodeStdOut(name, count, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_openNodeStdOut(String name, int count, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("openNodeStdOut");
		OutgoingAsync __result = new OutgoingAsync(this, "openNodeStdOut", __cb);
		try
		{
			__result.__prepare("openNodeStdOut", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeString(name);
			__os.writeInt(count);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public FileIteratorPrx end_openNodeStdOut(AsyncResult __result)
		throws FileNotAvailableException, NodeNotExistException, NodeUnreachableException
	{
		AsyncResult.__check(__result, this, "openNodeStdOut");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (FileNotAvailableException __ex)
			{
				throw __ex;
			}
			catch (NodeNotExistException __ex)
			{
				throw __ex;
			}
			catch (NodeUnreachableException __ex)
			{
				throw __ex;
			}
			catch (UserException __ex)
			{
				throw new UnknownUserException(__ex.ice_name(), __ex);
			}
		BasicStream __is = __result.__is();
		__is.startReadEncaps();
		FileIteratorPrx __ret = FileIteratorPrxHelper.__read(__is);
		__is.endReadEncaps();
		return __ret;
	}

	public FileIteratorPrx openRegistryStdErr(String name, int count)
		throws FileNotAvailableException, RegistryNotExistException, RegistryUnreachableException
	{
		return openRegistryStdErr(name, count, null, false);
	}

	public FileIteratorPrx openRegistryStdErr(String name, int count, Map __ctx)
		throws FileNotAvailableException, RegistryNotExistException, RegistryUnreachableException
	{
		return openRegistryStdErr(name, count, __ctx, true);
	}

	private FileIteratorPrx openRegistryStdErr(String name, int count, Map __ctx, boolean __explicitCtx)
		throws FileNotAvailableException, RegistryNotExistException, RegistryUnreachableException
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_AdminSessionDel __del;
		__checkTwowayOnly("openRegistryStdErr");
		__delBase = __getDelegate(false);
		__del = (_AdminSessionDel)__delBase;
		return __del.openRegistryStdErr(name, count, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__handleExceptionWrapper(__delBase, __ex);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_openRegistryStdErr(String name, int count)
	{
		return begin_openRegistryStdErr(name, count, null, false, null);
	}

	public AsyncResult begin_openRegistryStdErr(String name, int count, Map __ctx)
	{
		return begin_openRegistryStdErr(name, count, __ctx, true, null);
	}

	public AsyncResult begin_openRegistryStdErr(String name, int count, Callback __cb)
	{
		return begin_openRegistryStdErr(name, count, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_openRegistryStdErr(String name, int count, Map __ctx, Callback __cb)
	{
		return begin_openRegistryStdErr(name, count, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_openRegistryStdErr(String name, int count, Callback_AdminSession_openRegistryStdErr __cb)
	{
		return begin_openRegistryStdErr(name, count, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_openRegistryStdErr(String name, int count, Map __ctx, Callback_AdminSession_openRegistryStdErr __cb)
	{
		return begin_openRegistryStdErr(name, count, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_openRegistryStdErr(String name, int count, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("openRegistryStdErr");
		OutgoingAsync __result = new OutgoingAsync(this, "openRegistryStdErr", __cb);
		try
		{
			__result.__prepare("openRegistryStdErr", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeString(name);
			__os.writeInt(count);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public FileIteratorPrx end_openRegistryStdErr(AsyncResult __result)
		throws FileNotAvailableException, RegistryNotExistException, RegistryUnreachableException
	{
		AsyncResult.__check(__result, this, "openRegistryStdErr");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (FileNotAvailableException __ex)
			{
				throw __ex;
			}
			catch (RegistryNotExistException __ex)
			{
				throw __ex;
			}
			catch (RegistryUnreachableException __ex)
			{
				throw __ex;
			}
			catch (UserException __ex)
			{
				throw new UnknownUserException(__ex.ice_name(), __ex);
			}
		BasicStream __is = __result.__is();
		__is.startReadEncaps();
		FileIteratorPrx __ret = FileIteratorPrxHelper.__read(__is);
		__is.endReadEncaps();
		return __ret;
	}

	public FileIteratorPrx openRegistryStdOut(String name, int count)
		throws FileNotAvailableException, RegistryNotExistException, RegistryUnreachableException
	{
		return openRegistryStdOut(name, count, null, false);
	}

	public FileIteratorPrx openRegistryStdOut(String name, int count, Map __ctx)
		throws FileNotAvailableException, RegistryNotExistException, RegistryUnreachableException
	{
		return openRegistryStdOut(name, count, __ctx, true);
	}

	private FileIteratorPrx openRegistryStdOut(String name, int count, Map __ctx, boolean __explicitCtx)
		throws FileNotAvailableException, RegistryNotExistException, RegistryUnreachableException
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_AdminSessionDel __del;
		__checkTwowayOnly("openRegistryStdOut");
		__delBase = __getDelegate(false);
		__del = (_AdminSessionDel)__delBase;
		return __del.openRegistryStdOut(name, count, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__handleExceptionWrapper(__delBase, __ex);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_openRegistryStdOut(String name, int count)
	{
		return begin_openRegistryStdOut(name, count, null, false, null);
	}

	public AsyncResult begin_openRegistryStdOut(String name, int count, Map __ctx)
	{
		return begin_openRegistryStdOut(name, count, __ctx, true, null);
	}

	public AsyncResult begin_openRegistryStdOut(String name, int count, Callback __cb)
	{
		return begin_openRegistryStdOut(name, count, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_openRegistryStdOut(String name, int count, Map __ctx, Callback __cb)
	{
		return begin_openRegistryStdOut(name, count, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_openRegistryStdOut(String name, int count, Callback_AdminSession_openRegistryStdOut __cb)
	{
		return begin_openRegistryStdOut(name, count, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_openRegistryStdOut(String name, int count, Map __ctx, Callback_AdminSession_openRegistryStdOut __cb)
	{
		return begin_openRegistryStdOut(name, count, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_openRegistryStdOut(String name, int count, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("openRegistryStdOut");
		OutgoingAsync __result = new OutgoingAsync(this, "openRegistryStdOut", __cb);
		try
		{
			__result.__prepare("openRegistryStdOut", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeString(name);
			__os.writeInt(count);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public FileIteratorPrx end_openRegistryStdOut(AsyncResult __result)
		throws FileNotAvailableException, RegistryNotExistException, RegistryUnreachableException
	{
		AsyncResult.__check(__result, this, "openRegistryStdOut");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (FileNotAvailableException __ex)
			{
				throw __ex;
			}
			catch (RegistryNotExistException __ex)
			{
				throw __ex;
			}
			catch (RegistryUnreachableException __ex)
			{
				throw __ex;
			}
			catch (UserException __ex)
			{
				throw new UnknownUserException(__ex.ice_name(), __ex);
			}
		BasicStream __is = __result.__is();
		__is.startReadEncaps();
		FileIteratorPrx __ret = FileIteratorPrxHelper.__read(__is);
		__is.endReadEncaps();
		return __ret;
	}

	public FileIteratorPrx openServerLog(String id, String path, int count)
		throws DeploymentException, FileNotAvailableException, NodeUnreachableException, ServerNotExistException
	{
		return openServerLog(id, path, count, null, false);
	}

	public FileIteratorPrx openServerLog(String id, String path, int count, Map __ctx)
		throws DeploymentException, FileNotAvailableException, NodeUnreachableException, ServerNotExistException
	{
		return openServerLog(id, path, count, __ctx, true);
	}

	private FileIteratorPrx openServerLog(String id, String path, int count, Map __ctx, boolean __explicitCtx)
		throws DeploymentException, FileNotAvailableException, NodeUnreachableException, ServerNotExistException
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_AdminSessionDel __del;
		__checkTwowayOnly("openServerLog");
		__delBase = __getDelegate(false);
		__del = (_AdminSessionDel)__delBase;
		return __del.openServerLog(id, path, count, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__handleExceptionWrapper(__delBase, __ex);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_openServerLog(String id, String path, int count)
	{
		return begin_openServerLog(id, path, count, null, false, null);
	}

	public AsyncResult begin_openServerLog(String id, String path, int count, Map __ctx)
	{
		return begin_openServerLog(id, path, count, __ctx, true, null);
	}

	public AsyncResult begin_openServerLog(String id, String path, int count, Callback __cb)
	{
		return begin_openServerLog(id, path, count, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_openServerLog(String id, String path, int count, Map __ctx, Callback __cb)
	{
		return begin_openServerLog(id, path, count, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_openServerLog(String id, String path, int count, Callback_AdminSession_openServerLog __cb)
	{
		return begin_openServerLog(id, path, count, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_openServerLog(String id, String path, int count, Map __ctx, Callback_AdminSession_openServerLog __cb)
	{
		return begin_openServerLog(id, path, count, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_openServerLog(String id, String path, int count, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("openServerLog");
		OutgoingAsync __result = new OutgoingAsync(this, "openServerLog", __cb);
		try
		{
			__result.__prepare("openServerLog", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeString(id);
			__os.writeString(path);
			__os.writeInt(count);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public FileIteratorPrx end_openServerLog(AsyncResult __result)
		throws DeploymentException, FileNotAvailableException, NodeUnreachableException, ServerNotExistException
	{
		AsyncResult.__check(__result, this, "openServerLog");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (DeploymentException __ex)
			{
				throw __ex;
			}
			catch (FileNotAvailableException __ex)
			{
				throw __ex;
			}
			catch (NodeUnreachableException __ex)
			{
				throw __ex;
			}
			catch (ServerNotExistException __ex)
			{
				throw __ex;
			}
			catch (UserException __ex)
			{
				throw new UnknownUserException(__ex.ice_name(), __ex);
			}
		BasicStream __is = __result.__is();
		__is.startReadEncaps();
		FileIteratorPrx __ret = FileIteratorPrxHelper.__read(__is);
		__is.endReadEncaps();
		return __ret;
	}

	public FileIteratorPrx openServerStdErr(String id, int count)
		throws DeploymentException, FileNotAvailableException, NodeUnreachableException, ServerNotExistException
	{
		return openServerStdErr(id, count, null, false);
	}

	public FileIteratorPrx openServerStdErr(String id, int count, Map __ctx)
		throws DeploymentException, FileNotAvailableException, NodeUnreachableException, ServerNotExistException
	{
		return openServerStdErr(id, count, __ctx, true);
	}

	private FileIteratorPrx openServerStdErr(String id, int count, Map __ctx, boolean __explicitCtx)
		throws DeploymentException, FileNotAvailableException, NodeUnreachableException, ServerNotExistException
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_AdminSessionDel __del;
		__checkTwowayOnly("openServerStdErr");
		__delBase = __getDelegate(false);
		__del = (_AdminSessionDel)__delBase;
		return __del.openServerStdErr(id, count, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__handleExceptionWrapper(__delBase, __ex);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_openServerStdErr(String id, int count)
	{
		return begin_openServerStdErr(id, count, null, false, null);
	}

	public AsyncResult begin_openServerStdErr(String id, int count, Map __ctx)
	{
		return begin_openServerStdErr(id, count, __ctx, true, null);
	}

	public AsyncResult begin_openServerStdErr(String id, int count, Callback __cb)
	{
		return begin_openServerStdErr(id, count, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_openServerStdErr(String id, int count, Map __ctx, Callback __cb)
	{
		return begin_openServerStdErr(id, count, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_openServerStdErr(String id, int count, Callback_AdminSession_openServerStdErr __cb)
	{
		return begin_openServerStdErr(id, count, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_openServerStdErr(String id, int count, Map __ctx, Callback_AdminSession_openServerStdErr __cb)
	{
		return begin_openServerStdErr(id, count, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_openServerStdErr(String id, int count, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("openServerStdErr");
		OutgoingAsync __result = new OutgoingAsync(this, "openServerStdErr", __cb);
		try
		{
			__result.__prepare("openServerStdErr", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeString(id);
			__os.writeInt(count);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public FileIteratorPrx end_openServerStdErr(AsyncResult __result)
		throws DeploymentException, FileNotAvailableException, NodeUnreachableException, ServerNotExistException
	{
		AsyncResult.__check(__result, this, "openServerStdErr");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (DeploymentException __ex)
			{
				throw __ex;
			}
			catch (FileNotAvailableException __ex)
			{
				throw __ex;
			}
			catch (NodeUnreachableException __ex)
			{
				throw __ex;
			}
			catch (ServerNotExistException __ex)
			{
				throw __ex;
			}
			catch (UserException __ex)
			{
				throw new UnknownUserException(__ex.ice_name(), __ex);
			}
		BasicStream __is = __result.__is();
		__is.startReadEncaps();
		FileIteratorPrx __ret = FileIteratorPrxHelper.__read(__is);
		__is.endReadEncaps();
		return __ret;
	}

	public FileIteratorPrx openServerStdOut(String id, int count)
		throws DeploymentException, FileNotAvailableException, NodeUnreachableException, ServerNotExistException
	{
		return openServerStdOut(id, count, null, false);
	}

	public FileIteratorPrx openServerStdOut(String id, int count, Map __ctx)
		throws DeploymentException, FileNotAvailableException, NodeUnreachableException, ServerNotExistException
	{
		return openServerStdOut(id, count, __ctx, true);
	}

	private FileIteratorPrx openServerStdOut(String id, int count, Map __ctx, boolean __explicitCtx)
		throws DeploymentException, FileNotAvailableException, NodeUnreachableException, ServerNotExistException
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_AdminSessionDel __del;
		__checkTwowayOnly("openServerStdOut");
		__delBase = __getDelegate(false);
		__del = (_AdminSessionDel)__delBase;
		return __del.openServerStdOut(id, count, __ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__handleExceptionWrapper(__delBase, __ex);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_openServerStdOut(String id, int count)
	{
		return begin_openServerStdOut(id, count, null, false, null);
	}

	public AsyncResult begin_openServerStdOut(String id, int count, Map __ctx)
	{
		return begin_openServerStdOut(id, count, __ctx, true, null);
	}

	public AsyncResult begin_openServerStdOut(String id, int count, Callback __cb)
	{
		return begin_openServerStdOut(id, count, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_openServerStdOut(String id, int count, Map __ctx, Callback __cb)
	{
		return begin_openServerStdOut(id, count, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_openServerStdOut(String id, int count, Callback_AdminSession_openServerStdOut __cb)
	{
		return begin_openServerStdOut(id, count, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_openServerStdOut(String id, int count, Map __ctx, Callback_AdminSession_openServerStdOut __cb)
	{
		return begin_openServerStdOut(id, count, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_openServerStdOut(String id, int count, Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("openServerStdOut");
		OutgoingAsync __result = new OutgoingAsync(this, "openServerStdOut", __cb);
		try
		{
			__result.__prepare("openServerStdOut", OperationMode.Normal, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			__os.writeString(id);
			__os.writeInt(count);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public FileIteratorPrx end_openServerStdOut(AsyncResult __result)
		throws DeploymentException, FileNotAvailableException, NodeUnreachableException, ServerNotExistException
	{
		AsyncResult.__check(__result, this, "openServerStdOut");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (DeploymentException __ex)
			{
				throw __ex;
			}
			catch (FileNotAvailableException __ex)
			{
				throw __ex;
			}
			catch (NodeUnreachableException __ex)
			{
				throw __ex;
			}
			catch (ServerNotExistException __ex)
			{
				throw __ex;
			}
			catch (UserException __ex)
			{
				throw new UnknownUserException(__ex.ice_name(), __ex);
			}
		BasicStream __is = __result.__is();
		__is.startReadEncaps();
		FileIteratorPrx __ret = FileIteratorPrxHelper.__read(__is);
		__is.endReadEncaps();
		return __ret;
	}

	public void setObservers(RegistryObserverPrx registryObs, NodeObserverPrx nodeObs, ApplicationObserverPrx appObs, AdapterObserverPrx adptObs, ObjectObserverPrx objObs)
		throws ObserverAlreadyRegisteredException
	{
		setObservers(registryObs, nodeObs, appObs, adptObs, objObs, null, false);
	}

	public void setObservers(RegistryObserverPrx registryObs, NodeObserverPrx nodeObs, ApplicationObserverPrx appObs, AdapterObserverPrx adptObs, ObjectObserverPrx objObs, Map __ctx)
		throws ObserverAlreadyRegisteredException
	{
		setObservers(registryObs, nodeObs, appObs, adptObs, objObs, __ctx, true);
	}

	private void setObservers(RegistryObserverPrx registryObs, NodeObserverPrx nodeObs, ApplicationObserverPrx appObs, AdapterObserverPrx adptObs, ObjectObserverPrx objObs, Map __ctx, boolean __explicitCtx)
		throws ObserverAlreadyRegisteredException
	{
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		int __cnt = 0;
		do
		{
			Ice._ObjectDel __delBase = null;
			try
			{
				__checkTwowayOnly("setObservers");
				__delBase = __getDelegate(false);
				_AdminSessionDel __del = (_AdminSessionDel)__delBase;
				__del.setObservers(registryObs, nodeObs, appObs, adptObs, objObs, __ctx);
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

	public AsyncResult begin_setObservers(RegistryObserverPrx registryObs, NodeObserverPrx nodeObs, ApplicationObserverPrx appObs, AdapterObserverPrx adptObs, ObjectObserverPrx objObs)
	{
		return begin_setObservers(registryObs, nodeObs, appObs, adptObs, objObs, null, false, null);
	}

	public AsyncResult begin_setObservers(RegistryObserverPrx registryObs, NodeObserverPrx nodeObs, ApplicationObserverPrx appObs, AdapterObserverPrx adptObs, ObjectObserverPrx objObs, Map __ctx)
	{
		return begin_setObservers(registryObs, nodeObs, appObs, adptObs, objObs, __ctx, true, null);
	}

	public AsyncResult begin_setObservers(RegistryObserverPrx registryObs, NodeObserverPrx nodeObs, ApplicationObserverPrx appObs, AdapterObserverPrx adptObs, ObjectObserverPrx objObs, Callback __cb)
	{
		return begin_setObservers(registryObs, nodeObs, appObs, adptObs, objObs, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_setObservers(RegistryObserverPrx registryObs, NodeObserverPrx nodeObs, ApplicationObserverPrx appObs, AdapterObserverPrx adptObs, ObjectObserverPrx objObs, Map __ctx, Callback __cb)
	{
		return begin_setObservers(registryObs, nodeObs, appObs, adptObs, objObs, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_setObservers(RegistryObserverPrx registryObs, NodeObserverPrx nodeObs, ApplicationObserverPrx appObs, AdapterObserverPrx adptObs, ObjectObserverPrx objObs, Callback_AdminSession_setObservers __cb)
	{
		return begin_setObservers(registryObs, nodeObs, appObs, adptObs, objObs, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_setObservers(RegistryObserverPrx registryObs, NodeObserverPrx nodeObs, ApplicationObserverPrx appObs, AdapterObserverPrx adptObs, ObjectObserverPrx objObs, Map __ctx, Callback_AdminSession_setObservers __cb)
	{
		return begin_setObservers(registryObs, nodeObs, appObs, adptObs, objObs, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_setObservers(RegistryObserverPrx registryObs, NodeObserverPrx nodeObs, ApplicationObserverPrx appObs, AdapterObserverPrx adptObs, ObjectObserverPrx objObs, Map __ctx, boolean __explicitCtx, 
			CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("setObservers");
		OutgoingAsync __result = new OutgoingAsync(this, "setObservers", __cb);
		try
		{
			__result.__prepare("setObservers", OperationMode.Idempotent, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			RegistryObserverPrxHelper.__write(__os, registryObs);
			NodeObserverPrxHelper.__write(__os, nodeObs);
			ApplicationObserverPrxHelper.__write(__os, appObs);
			AdapterObserverPrxHelper.__write(__os, adptObs);
			ObjectObserverPrxHelper.__write(__os, objObs);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public void end_setObservers(AsyncResult __result)
		throws ObserverAlreadyRegisteredException
	{
		AsyncResult.__check(__result, this, "setObservers");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (ObserverAlreadyRegisteredException __ex)
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

	public void setObserversByIdentity(Identity registryObs, Identity nodeObs, Identity appObs, Identity adptObs, Identity objObs)
		throws ObserverAlreadyRegisteredException
	{
		setObserversByIdentity(registryObs, nodeObs, appObs, adptObs, objObs, null, false);
	}

	public void setObserversByIdentity(Identity registryObs, Identity nodeObs, Identity appObs, Identity adptObs, Identity objObs, Map __ctx)
		throws ObserverAlreadyRegisteredException
	{
		setObserversByIdentity(registryObs, nodeObs, appObs, adptObs, objObs, __ctx, true);
	}

	private void setObserversByIdentity(Identity registryObs, Identity nodeObs, Identity appObs, Identity adptObs, Identity objObs, Map __ctx, boolean __explicitCtx)
		throws ObserverAlreadyRegisteredException
	{
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		int __cnt = 0;
		do
		{
			Ice._ObjectDel __delBase = null;
			try
			{
				__checkTwowayOnly("setObserversByIdentity");
				__delBase = __getDelegate(false);
				_AdminSessionDel __del = (_AdminSessionDel)__delBase;
				__del.setObserversByIdentity(registryObs, nodeObs, appObs, adptObs, objObs, __ctx);
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

	public AsyncResult begin_setObserversByIdentity(Identity registryObs, Identity nodeObs, Identity appObs, Identity adptObs, Identity objObs)
	{
		return begin_setObserversByIdentity(registryObs, nodeObs, appObs, adptObs, objObs, null, false, null);
	}

	public AsyncResult begin_setObserversByIdentity(Identity registryObs, Identity nodeObs, Identity appObs, Identity adptObs, Identity objObs, Map __ctx)
	{
		return begin_setObserversByIdentity(registryObs, nodeObs, appObs, adptObs, objObs, __ctx, true, null);
	}

	public AsyncResult begin_setObserversByIdentity(Identity registryObs, Identity nodeObs, Identity appObs, Identity adptObs, Identity objObs, Callback __cb)
	{
		return begin_setObserversByIdentity(registryObs, nodeObs, appObs, adptObs, objObs, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_setObserversByIdentity(Identity registryObs, Identity nodeObs, Identity appObs, Identity adptObs, Identity objObs, Map __ctx, Callback __cb)
	{
		return begin_setObserversByIdentity(registryObs, nodeObs, appObs, adptObs, objObs, __ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_setObserversByIdentity(Identity registryObs, Identity nodeObs, Identity appObs, Identity adptObs, Identity objObs, Callback_AdminSession_setObserversByIdentity __cb)
	{
		return begin_setObserversByIdentity(registryObs, nodeObs, appObs, adptObs, objObs, null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_setObserversByIdentity(Identity registryObs, Identity nodeObs, Identity appObs, Identity adptObs, Identity objObs, Map __ctx, Callback_AdminSession_setObserversByIdentity __cb)
	{
		return begin_setObserversByIdentity(registryObs, nodeObs, appObs, adptObs, objObs, __ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_setObserversByIdentity(Identity registryObs, Identity nodeObs, Identity appObs, Identity adptObs, Identity objObs, Map __ctx, boolean __explicitCtx, 
			CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("setObserversByIdentity");
		OutgoingAsync __result = new OutgoingAsync(this, "setObserversByIdentity", __cb);
		try
		{
			__result.__prepare("setObserversByIdentity", OperationMode.Idempotent, __ctx, __explicitCtx);
			BasicStream __os = __result.__os();
			registryObs.__write(__os);
			nodeObs.__write(__os);
			appObs.__write(__os);
			adptObs.__write(__os);
			objObs.__write(__os);
			__os.endWriteEncaps();
			__result.__send(true);
		}
		catch (LocalException __ex)
		{
			__result.__exceptionAsync(__ex);
		}
		return __result;
	}

	public void end_setObserversByIdentity(AsyncResult __result)
		throws ObserverAlreadyRegisteredException
	{
		AsyncResult.__check(__result, this, "setObserversByIdentity");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (ObserverAlreadyRegisteredException __ex)
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

	public int startUpdate()
		throws AccessDeniedException
	{
		return startUpdate(null, false);
	}

	public int startUpdate(Map __ctx)
		throws AccessDeniedException
	{
		return startUpdate(__ctx, true);
	}

	private int startUpdate(Map __ctx, boolean __explicitCtx)
		throws AccessDeniedException
	{
		int __cnt;
		if (__explicitCtx && __ctx == null)
			__ctx = _emptyContext;
		__cnt = 0;
_L2:
		Ice._ObjectDel __delBase = null;
		_AdminSessionDel __del;
		__checkTwowayOnly("startUpdate");
		__delBase = __getDelegate(false);
		__del = (_AdminSessionDel)__delBase;
		return __del.startUpdate(__ctx);
		LocalExceptionWrapper __ex;
		__ex;
		__handleExceptionWrapper(__delBase, __ex);
		continue; /* Loop/switch isn't completed */
		__ex;
		__cnt = __handleException(__delBase, __ex, null, __cnt);
		if (true) goto _L2; else goto _L1
_L1:
	}

	public AsyncResult begin_startUpdate()
	{
		return begin_startUpdate(null, false, null);
	}

	public AsyncResult begin_startUpdate(Map __ctx)
	{
		return begin_startUpdate(__ctx, true, null);
	}

	public AsyncResult begin_startUpdate(Callback __cb)
	{
		return begin_startUpdate(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_startUpdate(Map __ctx, Callback __cb)
	{
		return begin_startUpdate(__ctx, true, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_startUpdate(Callback_AdminSession_startUpdate __cb)
	{
		return begin_startUpdate(null, false, ((CallbackBase) (__cb)));
	}

	public AsyncResult begin_startUpdate(Map __ctx, Callback_AdminSession_startUpdate __cb)
	{
		return begin_startUpdate(__ctx, true, ((CallbackBase) (__cb)));
	}

	private AsyncResult begin_startUpdate(Map __ctx, boolean __explicitCtx, CallbackBase __cb)
	{
		__checkAsyncTwowayOnly("startUpdate");
		OutgoingAsync __result = new OutgoingAsync(this, "startUpdate", __cb);
		try
		{
			__result.__prepare("startUpdate", OperationMode.Normal, __ctx, __explicitCtx);
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

	public int end_startUpdate(AsyncResult __result)
		throws AccessDeniedException
	{
		AsyncResult.__check(__result, this, "startUpdate");
		if (!__result.__wait())
			try
			{
				__result.__throwUserException();
			}
			catch (AccessDeniedException __ex)
			{
				throw __ex;
			}
			catch (UserException __ex)
			{
				throw new UnknownUserException(__ex.ice_name(), __ex);
			}
		BasicStream __is = __result.__is();
		__is.startReadEncaps();
		int __ret = __is.readInt();
		__is.endReadEncaps();
		return __ret;
	}

	public static AdminSessionPrx checkedCast(ObjectPrx __obj)
	{
		AdminSessionPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (AdminSessionPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId()))
				{
					AdminSessionPrxHelper __h = new AdminSessionPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static AdminSessionPrx checkedCast(ObjectPrx __obj, Map __ctx)
	{
		AdminSessionPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (AdminSessionPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				if (__obj.ice_isA(ice_staticId(), __ctx))
				{
					AdminSessionPrxHelper __h = new AdminSessionPrxHelper();
					__h.__copyFrom(__obj);
					__d = __h;
				}
			}
		return __d;
	}

	public static AdminSessionPrx checkedCast(ObjectPrx __obj, String __facet)
	{
		AdminSessionPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId()))
				{
					AdminSessionPrxHelper __h = new AdminSessionPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static AdminSessionPrx checkedCast(ObjectPrx __obj, String __facet, Map __ctx)
	{
		AdminSessionPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			try
			{
				if (__bb.ice_isA(ice_staticId(), __ctx))
				{
					AdminSessionPrxHelper __h = new AdminSessionPrxHelper();
					__h.__copyFrom(__bb);
					__d = __h;
				}
			}
			catch (FacetNotExistException ex) { }
		}
		return __d;
	}

	public static AdminSessionPrx uncheckedCast(ObjectPrx __obj)
	{
		AdminSessionPrx __d = null;
		if (__obj != null)
			try
			{
				__d = (AdminSessionPrx)__obj;
			}
			catch (ClassCastException ex)
			{
				AdminSessionPrxHelper __h = new AdminSessionPrxHelper();
				__h.__copyFrom(__obj);
				__d = __h;
			}
		return __d;
	}

	public static AdminSessionPrx uncheckedCast(ObjectPrx __obj, String __facet)
	{
		AdminSessionPrx __d = null;
		if (__obj != null)
		{
			ObjectPrx __bb = __obj.ice_facet(__facet);
			AdminSessionPrxHelper __h = new AdminSessionPrxHelper();
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
		return new _AdminSessionDelM();
	}

	protected _ObjectDelD __createDelegateD()
	{
		return new _AdminSessionDelD();
	}

	public static void __write(BasicStream __os, AdminSessionPrx v)
	{
		__os.writeProxy(v);
	}

	public static AdminSessionPrx __read(BasicStream __is)
	{
		ObjectPrx proxy = __is.readProxy();
		if (proxy != null)
		{
			AdminSessionPrxHelper result = new AdminSessionPrxHelper();
			result.__copyFrom(proxy);
			return result;
		} else
		{
			return null;
		}
	}

}
