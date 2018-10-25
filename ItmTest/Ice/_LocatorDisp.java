// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _LocatorDisp.java

package Ice;

import IceInternal.BasicStream;
import IceInternal.Incoming;
import java.util.Arrays;

// Referenced classes of package Ice:
//			ObjectImpl, Identity, _AMD_Locator_findObjectById, _AMD_Locator_findAdapterById, 
//			OperationNotExistException, MarshalException, Locator, AdapterNotFoundException, 
//			ObjectNotFoundException, OperationMode, Current, AMD_Locator_findObjectById, 
//			DispatchStatus, AMD_Locator_findAdapterById, LocatorRegistryPrxHelper, Object, 
//			LocatorRegistryPrx, OutputStream, InputStream

public abstract class _LocatorDisp extends ObjectImpl
	implements Locator
{

	public static final String __ids[] = {
		"::Ice::Locator", "::Ice::Object"
	};
	private static final String __all[] = {
		"findAdapterById", "findObjectById", "getRegistry", "ice_id", "ice_ids", "ice_isA", "ice_ping"
	};
	static final boolean $assertionsDisabled = !Ice/_LocatorDisp.desiredAssertionStatus();

	public _LocatorDisp()
	{
	}

	protected void ice_copyStateFrom(Object __obj)
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

	public static DispatchStatus ___findObjectById(Locator __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Idempotent, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		Identity id = new Identity();
		id.__read(__is);
		__is.endReadEncaps();
		AMD_Locator_findObjectById __cb = new _AMD_Locator_findObjectById(__inS);
		try
		{
			__obj.findObjectById_async(__cb, id, __current);
		}
		catch (Exception ex)
		{
			__cb.ice_exception(ex);
		}
		return DispatchStatus.DispatchAsync;
	}

	public static DispatchStatus ___findAdapterById(Locator __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Idempotent, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		String id = __is.readString();
		__is.endReadEncaps();
		AMD_Locator_findAdapterById __cb = new _AMD_Locator_findAdapterById(__inS);
		try
		{
			__obj.findAdapterById_async(__cb, id, __current);
		}
		catch (Exception ex)
		{
			__cb.ice_exception(ex);
		}
		return DispatchStatus.DispatchAsync;
	}

	public static DispatchStatus ___getRegistry(Locator __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Idempotent, __current.mode);
		__inS.is().skipEmptyEncaps();
		BasicStream __os = __inS.os();
		LocatorRegistryPrx __ret = __obj.getRegistry(__current);
		LocatorRegistryPrxHelper.__write(__os, __ret);
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
			return ___findAdapterById(this, in, __current);

		case 1: // '\001'
			return ___findObjectById(this, in, __current);

		case 2: // '\002'
			return ___getRegistry(this, in, __current);

		case 3: // '\003'
			return ___ice_id(this, in, __current);

		case 4: // '\004'
			return ___ice_ids(this, in, __current);

		case 5: // '\005'
			return ___ice_isA(this, in, __current);

		case 6: // '\006'
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
		ex.reason = "type Ice::Locator was not generated with stream support";
		throw ex;
	}

	public void __read(InputStream __inS, boolean __rid)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "type Ice::Locator was not generated with stream support";
		throw ex;
	}

}
