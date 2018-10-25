// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _RouterDisp.java

package Glacier2;

import Ice.Current;
import Ice.DispatchStatus;
import Ice.InputStream;
import Ice.MarshalException;
import Ice.Object;
import Ice.ObjectImpl;
import Ice.ObjectPrx;
import Ice.OperationMode;
import Ice.OperationNotExistException;
import Ice.OutputStream;
import IceInternal.BasicStream;
import IceInternal.Incoming;
import java.util.Arrays;

// Referenced classes of package Glacier2:
//			_AMD_Router_createSession, _AMD_Router_createSessionFromSecureConnection, SessionNotExistException, Router, 
//			CannotCreateSessionException, PermissionDeniedException, AMD_Router_createSession, AMD_Router_createSessionFromSecureConnection

public abstract class _RouterDisp extends ObjectImpl
	implements Router
{

	public static final String __ids[] = {
		"::Glacier2::Router", "::Ice::Object", "::Ice::Router"
	};
	private static final String __all[] = {
		"addProxies", "addProxy", "createSession", "createSessionFromSecureConnection", "destroySession", "getCategoryForClient", "getClientProxy", "getServerProxy", "getSessionTimeout", "ice_id", 
		"ice_ids", "ice_isA", "ice_ping", "refreshSession"
	};
	static final boolean $assertionsDisabled = !Glacier2/_RouterDisp.desiredAssertionStatus();

	public _RouterDisp()
	{
	}

	protected void ice_copyStateFrom(Ice.Object __obj)
		throws CloneNotSupportedException
	{
		throw new CloneNotSupportedException();
	}

	public boolean ice_isA(String s)
	{
		return Arrays.binarySearch(__ids, s) >= 0;
	}

	public boolean ice_isA(String s, Current __current)
	{
		return Arrays.binarySearch(__ids, s) >= 0;
	}

	public String[] ice_ids()
	{
		return __ids;
	}

	public String[] ice_ids(Current __current)
	{
		return __ids;
	}

	public String ice_id()
	{
		return __ids[0];
	}

	public String ice_id(Current __current)
	{
		return __ids[0];
	}

	public static String ice_staticId()
	{
		return __ids[0];
	}

	public final void createSession_async(AMD_Router_createSession __cb, String userId, String password)
		throws CannotCreateSessionException, PermissionDeniedException
	{
		createSession_async(__cb, userId, password, null);
	}

	public final void createSessionFromSecureConnection_async(AMD_Router_createSessionFromSecureConnection __cb)
		throws CannotCreateSessionException, PermissionDeniedException
	{
		createSessionFromSecureConnection_async(__cb, null);
	}

	public final void destroySession()
		throws SessionNotExistException
	{
		destroySession(null);
	}

	public final String getCategoryForClient()
	{
		return getCategoryForClient(null);
	}

	public final long getSessionTimeout()
	{
		return getSessionTimeout(null);
	}

	public final void refreshSession()
		throws SessionNotExistException
	{
		refreshSession(null);
	}

	public final ObjectPrx[] addProxies(ObjectPrx proxies[])
	{
		return addProxies(proxies, null);
	}

	/**
	 * @deprecated Method addProxy is deprecated
	 */

	public final void addProxy(ObjectPrx proxy)
	{
		addProxy(proxy, null);
	}

	public final ObjectPrx getClientProxy()
	{
		return getClientProxy(null);
	}

	public final ObjectPrx getServerProxy()
	{
		return getServerProxy(null);
	}

	public static DispatchStatus ___getCategoryForClient(Router __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Idempotent, __current.mode);
		__inS.is().skipEmptyEncaps();
		BasicStream __os = __inS.os();
		String __ret = __obj.getCategoryForClient(__current);
		__os.writeString(__ret);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___createSession(Router __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		String userId = __is.readString();
		String password = __is.readString();
		__is.endReadEncaps();
		AMD_Router_createSession __cb = new _AMD_Router_createSession(__inS);
		try
		{
			__obj.createSession_async(__cb, userId, password, __current);
		}
		catch (Exception ex)
		{
			__cb.ice_exception(ex);
		}
		return DispatchStatus.DispatchAsync;
	}

	public static DispatchStatus ___createSessionFromSecureConnection(Router __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Normal, __current.mode);
		__inS.is().skipEmptyEncaps();
		AMD_Router_createSessionFromSecureConnection __cb = new _AMD_Router_createSessionFromSecureConnection(__inS);
		try
		{
			__obj.createSessionFromSecureConnection_async(__cb, __current);
		}
		catch (Exception ex)
		{
			__cb.ice_exception(ex);
		}
		return DispatchStatus.DispatchAsync;
	}

	public static DispatchStatus ___refreshSession(Router __obj, Incoming __inS, Current __current)
	{
		BasicStream __os;
		__checkMode(OperationMode.Normal, __current.mode);
		__inS.is().skipEmptyEncaps();
		__os = __inS.os();
		__obj.refreshSession(__current);
		return DispatchStatus.DispatchOK;
		SessionNotExistException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___destroySession(Router __obj, Incoming __inS, Current __current)
	{
		BasicStream __os;
		__checkMode(OperationMode.Normal, __current.mode);
		__inS.is().skipEmptyEncaps();
		__os = __inS.os();
		__obj.destroySession(__current);
		return DispatchStatus.DispatchOK;
		SessionNotExistException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___getSessionTimeout(Router __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Idempotent, __current.mode);
		__inS.is().skipEmptyEncaps();
		BasicStream __os = __inS.os();
		long __ret = __obj.getSessionTimeout(__current);
		__os.writeLong(__ret);
		return DispatchStatus.DispatchOK;
	}

	public DispatchStatus __dispatch(Incoming in, Current __current)
	{
		int pos = Arrays.binarySearch(__all, __current.operation);
		if (pos < 0)
			throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
		switch (pos)
		{
		case 0: // '\0'
			return Ice._RouterDisp.___addProxies(this, in, __current);

		case 1: // '\001'
			return Ice._RouterDisp.___addProxy(this, in, __current);

		case 2: // '\002'
			return ___createSession(this, in, __current);

		case 3: // '\003'
			return ___createSessionFromSecureConnection(this, in, __current);

		case 4: // '\004'
			return ___destroySession(this, in, __current);

		case 5: // '\005'
			return ___getCategoryForClient(this, in, __current);

		case 6: // '\006'
			return Ice._RouterDisp.___getClientProxy(this, in, __current);

		case 7: // '\007'
			return Ice._RouterDisp.___getServerProxy(this, in, __current);

		case 8: // '\b'
			return ___getSessionTimeout(this, in, __current);

		case 9: // '\t'
			return ___ice_id(this, in, __current);

		case 10: // '\n'
			return ___ice_ids(this, in, __current);

		case 11: // '\013'
			return ___ice_isA(this, in, __current);

		case 12: // '\f'
			return ___ice_ping(this, in, __current);

		case 13: // '\r'
			return ___refreshSession(this, in, __current);
		}
		if (!$assertionsDisabled)
			throw new AssertionError();
		else
			throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
	}

	public void __write(BasicStream __os)
	{
		__os.writeTypeId(ice_staticId());
		__os.startWriteSlice();
		__os.endWriteSlice();
		super.__write(__os);
	}

	public void __read(BasicStream __is, boolean __rid)
	{
		if (__rid)
			__is.readTypeId();
		__is.startReadSlice();
		__is.endReadSlice();
		super.__read(__is, true);
	}

	public void __write(OutputStream __outS)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "type Glacier2::Router was not generated with stream support";
		throw ex;
	}

	public void __read(InputStream __inS, boolean __rid)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "type Glacier2::Router was not generated with stream support";
		throw ex;
	}

}
