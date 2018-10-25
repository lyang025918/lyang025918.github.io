// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _SessionControlDisp.java

package Glacier2;

import Ice.*;
import IceInternal.BasicStream;
import IceInternal.Incoming;
import java.util.Arrays;

// Referenced classes of package Glacier2:
//			SessionControl, StringSetPrxHelper, IdentitySetPrxHelper, StringSetPrx, 
//			IdentitySetPrx

public abstract class _SessionControlDisp extends ObjectImpl
	implements SessionControl
{

	public static final String __ids[] = {
		"::Glacier2::SessionControl", "::Ice::Object"
	};
	private static final String __all[] = {
		"adapterIds", "categories", "destroy", "getSessionTimeout", "ice_id", "ice_ids", "ice_isA", "ice_ping", "identities"
	};
	static final boolean $assertionsDisabled = !Glacier2/_SessionControlDisp.desiredAssertionStatus();

	public _SessionControlDisp()
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

	public final StringSetPrx adapterIds()
	{
		return adapterIds(null);
	}

	public final StringSetPrx categories()
	{
		return categories(null);
	}

	public final void destroy()
	{
		destroy(null);
	}

	public final int getSessionTimeout()
	{
		return getSessionTimeout(null);
	}

	public final IdentitySetPrx identities()
	{
		return identities(null);
	}

	public static DispatchStatus ___categories(SessionControl __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Normal, __current.mode);
		__inS.is().skipEmptyEncaps();
		BasicStream __os = __inS.os();
		StringSetPrx __ret = __obj.categories(__current);
		StringSetPrxHelper.__write(__os, __ret);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___adapterIds(SessionControl __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Normal, __current.mode);
		__inS.is().skipEmptyEncaps();
		BasicStream __os = __inS.os();
		StringSetPrx __ret = __obj.adapterIds(__current);
		StringSetPrxHelper.__write(__os, __ret);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___identities(SessionControl __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Normal, __current.mode);
		__inS.is().skipEmptyEncaps();
		BasicStream __os = __inS.os();
		IdentitySetPrx __ret = __obj.identities(__current);
		IdentitySetPrxHelper.__write(__os, __ret);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___getSessionTimeout(SessionControl __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Idempotent, __current.mode);
		__inS.is().skipEmptyEncaps();
		BasicStream __os = __inS.os();
		int __ret = __obj.getSessionTimeout(__current);
		__os.writeInt(__ret);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___destroy(SessionControl __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Normal, __current.mode);
		__inS.is().skipEmptyEncaps();
		__obj.destroy(__current);
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
			return ___adapterIds(this, in, __current);

		case 1: // '\001'
			return ___categories(this, in, __current);

		case 2: // '\002'
			return ___destroy(this, in, __current);

		case 3: // '\003'
			return ___getSessionTimeout(this, in, __current);

		case 4: // '\004'
			return ___ice_id(this, in, __current);

		case 5: // '\005'
			return ___ice_ids(this, in, __current);

		case 6: // '\006'
			return ___ice_isA(this, in, __current);

		case 7: // '\007'
			return ___ice_ping(this, in, __current);

		case 8: // '\b'
			return ___identities(this, in, __current);
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
		ex.reason = "type Glacier2::SessionControl was not generated with stream support";
		throw ex;
	}

	public void __read(InputStream __inS, boolean __rid)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "type Glacier2::SessionControl was not generated with stream support";
		throw ex;
	}

}
