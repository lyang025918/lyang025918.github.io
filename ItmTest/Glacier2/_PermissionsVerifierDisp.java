// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _PermissionsVerifierDisp.java

package Glacier2;

import Ice.*;
import IceInternal.BasicStream;
import IceInternal.Incoming;
import java.util.Arrays;

// Referenced classes of package Glacier2:
//			PermissionsVerifier

public abstract class _PermissionsVerifierDisp extends ObjectImpl
	implements PermissionsVerifier
{

	public static final String __ids[] = {
		"::Glacier2::PermissionsVerifier", "::Ice::Object"
	};
	private static final String __all[] = {
		"checkPermissions", "ice_id", "ice_ids", "ice_isA", "ice_ping"
	};
	static final boolean $assertionsDisabled = !Glacier2/_PermissionsVerifierDisp.desiredAssertionStatus();

	public _PermissionsVerifierDisp()
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

	public final boolean checkPermissions(String userId, String password, StringHolder reason)
	{
		return checkPermissions(userId, password, reason, null);
	}

	public static DispatchStatus ___checkPermissions(PermissionsVerifier __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Idempotent, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		String userId = __is.readString();
		String password = __is.readString();
		__is.endReadEncaps();
		StringHolder reason = new StringHolder();
		BasicStream __os = __inS.os();
		boolean __ret = __obj.checkPermissions(userId, password, reason, __current);
		__os.writeString(reason.value);
		__os.writeBool(__ret);
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
			return ___checkPermissions(this, in, __current);

		case 1: // '\001'
			return ___ice_id(this, in, __current);

		case 2: // '\002'
			return ___ice_ids(this, in, __current);

		case 3: // '\003'
			return ___ice_isA(this, in, __current);

		case 4: // '\004'
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
		ex.reason = "type Glacier2::PermissionsVerifier was not generated with stream support";
		throw ex;
	}

	public void __read(InputStream __inS, boolean __rid)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "type Glacier2::PermissionsVerifier was not generated with stream support";
		throw ex;
	}

}
