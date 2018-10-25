// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _LocatorDisp.java

package IceGrid;

import Ice.AMD_Locator_findAdapterById;
import Ice.AMD_Locator_findObjectById;
import Ice.AdapterNotFoundException;
import Ice.Current;
import Ice.DispatchStatus;
import Ice.Identity;
import Ice.InputStream;
import Ice.LocatorRegistryPrx;
import Ice.MarshalException;
import Ice.Object;
import Ice.ObjectImpl;
import Ice.ObjectNotFoundException;
import Ice.OperationMode;
import Ice.OperationNotExistException;
import Ice.OutputStream;
import IceInternal.BasicStream;
import IceInternal.Incoming;
import java.util.Arrays;

// Referenced classes of package IceGrid:
//			Locator, RegistryPrxHelper, QueryPrxHelper, QueryPrx, 
//			RegistryPrx

public abstract class _LocatorDisp extends ObjectImpl
	implements Locator
{

	public static final String __ids[] = {
		"::Ice::Locator", "::Ice::Object", "::IceGrid::Locator"
	};
	private static final String __all[] = {
		"findAdapterById", "findObjectById", "getLocalQuery", "getLocalRegistry", "getRegistry", "ice_id", "ice_ids", "ice_isA", "ice_ping"
	};
	static final boolean $assertionsDisabled = !IceGrid/_LocatorDisp.desiredAssertionStatus();

	public _LocatorDisp()
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
		return __ids[2];
	}

	public String ice_id(Current __current)
	{
		return __ids[2];
	}

	public static String ice_staticId()
	{
		return __ids[2];
	}

	public final void findAdapterById_async(AMD_Locator_findAdapterById __cb, String id)
		throws AdapterNotFoundException
	{
		findAdapterById_async(__cb, id, null);
	}

	public final void findObjectById_async(AMD_Locator_findObjectById __cb, Identity id)
		throws ObjectNotFoundException
	{
		findObjectById_async(__cb, id, null);
	}

	public final LocatorRegistryPrx getRegistry()
	{
		return getRegistry(null);
	}

	public final QueryPrx getLocalQuery()
	{
		return getLocalQuery(null);
	}

	public final RegistryPrx getLocalRegistry()
	{
		return getLocalRegistry(null);
	}

	public static DispatchStatus ___getLocalRegistry(Locator __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Idempotent, __current.mode);
		__inS.is().skipEmptyEncaps();
		BasicStream __os = __inS.os();
		RegistryPrx __ret = __obj.getLocalRegistry(__current);
		RegistryPrxHelper.__write(__os, __ret);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___getLocalQuery(Locator __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Idempotent, __current.mode);
		__inS.is().skipEmptyEncaps();
		BasicStream __os = __inS.os();
		QueryPrx __ret = __obj.getLocalQuery(__current);
		QueryPrxHelper.__write(__os, __ret);
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
			return Ice._LocatorDisp.___findAdapterById(this, in, __current);

		case 1: // '\001'
			return Ice._LocatorDisp.___findObjectById(this, in, __current);

		case 2: // '\002'
			return ___getLocalQuery(this, in, __current);

		case 3: // '\003'
			return ___getLocalRegistry(this, in, __current);

		case 4: // '\004'
			return Ice._LocatorDisp.___getRegistry(this, in, __current);

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
		ex.reason = "type IceGrid::Locator was not generated with stream support";
		throw ex;
	}

	public void __read(InputStream __inS, boolean __rid)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "type IceGrid::Locator was not generated with stream support";
		throw ex;
	}

}
