// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _RegistryDisp.java

package IceGrid;

import Ice.*;
import IceInternal.BasicStream;
import IceInternal.Incoming;
import java.util.Arrays;

// Referenced classes of package IceGrid:
//			PermissionDeniedException, Registry, SessionPrxHelper, AdminSessionPrxHelper, 
//			AdminSessionPrx, SessionPrx

public abstract class _RegistryDisp extends ObjectImpl
	implements Registry
{

	public static final String __ids[] = {
		"::Ice::Object", "::IceGrid::Registry"
	};
	private static final String __all[] = {
		"createAdminSession", "createAdminSessionFromSecureConnection", "createSession", "createSessionFromSecureConnection", "getSessionTimeout", "ice_id", "ice_ids", "ice_isA", "ice_ping"
	};
	static final boolean $assertionsDisabled = !IceGrid/_RegistryDisp.desiredAssertionStatus();

	public _RegistryDisp()
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
		return __ids[1];
	}

	public String ice_id(Current __current)
	{
		return __ids[1];
	}

	public static String ice_staticId()
	{
		return __ids[1];
	}

	public final AdminSessionPrx createAdminSession(String userId, String password)
		throws PermissionDeniedException
	{
		return createAdminSession(userId, password, null);
	}

	public final AdminSessionPrx createAdminSessionFromSecureConnection()
		throws PermissionDeniedException
	{
		return createAdminSessionFromSecureConnection(null);
	}

	public final SessionPrx createSession(String userId, String password)
		throws PermissionDeniedException
	{
		return createSession(userId, password, null);
	}

	public final SessionPrx createSessionFromSecureConnection()
		throws PermissionDeniedException
	{
		return createSessionFromSecureConnection(null);
	}

	public final int getSessionTimeout()
	{
		return getSessionTimeout(null);
	}

	public static DispatchStatus ___createSession(Registry __obj, Incoming __inS, Current __current)
	{
		String userId;
		String password;
		BasicStream __os;
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		userId = __is.readString();
		password = __is.readString();
		__is.endReadEncaps();
		__os = __inS.os();
		SessionPrx __ret = __obj.createSession(userId, password, __current);
		SessionPrxHelper.__write(__os, __ret);
		return DispatchStatus.DispatchOK;
		PermissionDeniedException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___createAdminSession(Registry __obj, Incoming __inS, Current __current)
	{
		String userId;
		String password;
		BasicStream __os;
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		userId = __is.readString();
		password = __is.readString();
		__is.endReadEncaps();
		__os = __inS.os();
		AdminSessionPrx __ret = __obj.createAdminSession(userId, password, __current);
		AdminSessionPrxHelper.__write(__os, __ret);
		return DispatchStatus.DispatchOK;
		PermissionDeniedException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___createSessionFromSecureConnection(Registry __obj, Incoming __inS, Current __current)
	{
		BasicStream __os;
		__checkMode(OperationMode.Normal, __current.mode);
		__inS.is().skipEmptyEncaps();
		__os = __inS.os();
		SessionPrx __ret = __obj.createSessionFromSecureConnection(__current);
		SessionPrxHelper.__write(__os, __ret);
		return DispatchStatus.DispatchOK;
		PermissionDeniedException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___createAdminSessionFromSecureConnection(Registry __obj, Incoming __inS, Current __current)
	{
		BasicStream __os;
		__checkMode(OperationMode.Normal, __current.mode);
		__inS.is().skipEmptyEncaps();
		__os = __inS.os();
		AdminSessionPrx __ret = __obj.createAdminSessionFromSecureConnection(__current);
		AdminSessionPrxHelper.__write(__os, __ret);
		return DispatchStatus.DispatchOK;
		PermissionDeniedException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___getSessionTimeout(Registry __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Idempotent, __current.mode);
		__inS.is().skipEmptyEncaps();
		BasicStream __os = __inS.os();
		int __ret = __obj.getSessionTimeout(__current);
		__os.writeInt(__ret);
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
			return ___createAdminSession(this, in, __current);

		case 1: // '\001'
			return ___createAdminSessionFromSecureConnection(this, in, __current);

		case 2: // '\002'
			return ___createSession(this, in, __current);

		case 3: // '\003'
			return ___createSessionFromSecureConnection(this, in, __current);

		case 4: // '\004'
			return ___getSessionTimeout(this, in, __current);

		case 5: // '\005'
			return ___ice_id(this, in, __current);

		case 6: // '\006'
			return ___ice_ids(this, in, __current);

		case 7: // '\007'
			return ___ice_isA(this, in, __current);

		case 8: // '\b'
			return ___ice_ping(this, in, __current);
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
		ex.reason = "type IceGrid::Registry was not generated with stream support";
		throw ex;
	}

	public void __read(InputStream __inS, boolean __rid)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "type IceGrid::Registry was not generated with stream support";
		throw ex;
	}

}
