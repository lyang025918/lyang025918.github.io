// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _LocatorRegistryDisp.java

package Ice;

import IceInternal.BasicStream;
import IceInternal.Incoming;
import java.util.Arrays;

// Referenced classes of package Ice:
//			ObjectImpl, _AMD_LocatorRegistry_setAdapterDirectProxy, _AMD_LocatorRegistry_setReplicatedAdapterDirectProxy, _AMD_LocatorRegistry_setServerProcessProxy, 
//			OperationNotExistException, MarshalException, LocatorRegistry, AdapterAlreadyActiveException, 
//			AdapterNotFoundException, InvalidReplicaGroupIdException, ServerNotFoundException, OperationMode, 
//			Current, AMD_LocatorRegistry_setAdapterDirectProxy, DispatchStatus, AMD_LocatorRegistry_setReplicatedAdapterDirectProxy, 
//			ProcessPrxHelper, AMD_LocatorRegistry_setServerProcessProxy, Object, ObjectPrx, 
//			ProcessPrx, OutputStream, InputStream

public abstract class _LocatorRegistryDisp extends ObjectImpl
	implements LocatorRegistry
{

	public static final String __ids[] = {
		"::Ice::LocatorRegistry", "::Ice::Object"
	};
	private static final String __all[] = {
		"ice_id", "ice_ids", "ice_isA", "ice_ping", "setAdapterDirectProxy", "setReplicatedAdapterDirectProxy", "setServerProcessProxy"
	};
	static final boolean $assertionsDisabled = !Ice/_LocatorRegistryDisp.desiredAssertionStatus();

	public _LocatorRegistryDisp()
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

	public final void setAdapterDirectProxy_async(AMD_LocatorRegistry_setAdapterDirectProxy __cb, String id, ObjectPrx proxy)
		throws AdapterAlreadyActiveException, AdapterNotFoundException
	{
		setAdapterDirectProxy_async(__cb, id, proxy, null);
	}

	public final void setReplicatedAdapterDirectProxy_async(AMD_LocatorRegistry_setReplicatedAdapterDirectProxy __cb, String adapterId, String replicaGroupId, ObjectPrx p)
		throws AdapterAlreadyActiveException, AdapterNotFoundException, InvalidReplicaGroupIdException
	{
		setReplicatedAdapterDirectProxy_async(__cb, adapterId, replicaGroupId, p, null);
	}

	public final void setServerProcessProxy_async(AMD_LocatorRegistry_setServerProcessProxy __cb, String id, ProcessPrx proxy)
		throws ServerNotFoundException
	{
		setServerProcessProxy_async(__cb, id, proxy, null);
	}

	public static DispatchStatus ___setAdapterDirectProxy(LocatorRegistry __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Idempotent, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		String id = __is.readString();
		ObjectPrx proxy = __is.readProxy();
		__is.endReadEncaps();
		AMD_LocatorRegistry_setAdapterDirectProxy __cb = new _AMD_LocatorRegistry_setAdapterDirectProxy(__inS);
		try
		{
			__obj.setAdapterDirectProxy_async(__cb, id, proxy, __current);
		}
		catch (Exception ex)
		{
			__cb.ice_exception(ex);
		}
		return DispatchStatus.DispatchAsync;
	}

	public static DispatchStatus ___setReplicatedAdapterDirectProxy(LocatorRegistry __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Idempotent, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		String adapterId = __is.readString();
		String replicaGroupId = __is.readString();
		ObjectPrx p = __is.readProxy();
		__is.endReadEncaps();
		AMD_LocatorRegistry_setReplicatedAdapterDirectProxy __cb = new _AMD_LocatorRegistry_setReplicatedAdapterDirectProxy(__inS);
		try
		{
			__obj.setReplicatedAdapterDirectProxy_async(__cb, adapterId, replicaGroupId, p, __current);
		}
		catch (Exception ex)
		{
			__cb.ice_exception(ex);
		}
		return DispatchStatus.DispatchAsync;
	}

	public static DispatchStatus ___setServerProcessProxy(LocatorRegistry __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Idempotent, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		String id = __is.readString();
		ProcessPrx proxy = ProcessPrxHelper.__read(__is);
		__is.endReadEncaps();
		AMD_LocatorRegistry_setServerProcessProxy __cb = new _AMD_LocatorRegistry_setServerProcessProxy(__inS);
		try
		{
			__obj.setServerProcessProxy_async(__cb, id, proxy, __current);
		}
		catch (Exception ex)
		{
			__cb.ice_exception(ex);
		}
		return DispatchStatus.DispatchAsync;
	}

	public DispatchStatus __dispatch(Incoming in, Current __current)
	{
		int pos = Arrays.binarySearch(__all, __current.operation);
		if (pos < 0)
			throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
		switch (pos)
		{
		case 0: // '\0'
			return ___ice_id(this, in, __current);

		case 1: // '\001'
			return ___ice_ids(this, in, __current);

		case 2: // '\002'
			return ___ice_isA(this, in, __current);

		case 3: // '\003'
			return ___ice_ping(this, in, __current);

		case 4: // '\004'
			return ___setAdapterDirectProxy(this, in, __current);

		case 5: // '\005'
			return ___setReplicatedAdapterDirectProxy(this, in, __current);

		case 6: // '\006'
			return ___setServerProcessProxy(this, in, __current);
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
		ex.reason = "type Ice::LocatorRegistry was not generated with stream support";
		throw ex;
	}

	public void __read(InputStream __inS, boolean __rid)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "type Ice::LocatorRegistry was not generated with stream support";
		throw ex;
	}

}
