// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _SessionDisp.java

package IceGrid;

import Ice.Current;
import Ice.DispatchStatus;
import Ice.Identity;
import Ice.InputStream;
import Ice.MarshalException;
import Ice.Object;
import Ice.ObjectImpl;
import Ice.OperationMode;
import Ice.OperationNotExistException;
import Ice.OutputStream;
import IceInternal.BasicStream;
import IceInternal.Incoming;
import java.util.Arrays;

// Referenced classes of package IceGrid:
//			_AMD_Session_allocateObjectById, _AMD_Session_allocateObjectByType, AllocationException, ObjectNotRegisteredException, 
//			Session, AMD_Session_allocateObjectById, AMD_Session_allocateObjectByType

public abstract class _SessionDisp extends ObjectImpl
	implements Session
{

	public static final String __ids[] = {
		"::Glacier2::Session", "::Ice::Object", "::IceGrid::Session"
	};
	private static final String __all[] = {
		"allocateObjectById", "allocateObjectByType", "destroy", "ice_id", "ice_ids", "ice_isA", "ice_ping", "keepAlive", "releaseObject", "setAllocationTimeout"
	};
	static final boolean $assertionsDisabled = !IceGrid/_SessionDisp.desiredAssertionStatus();

	public _SessionDisp()
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

	public final void destroy()
	{
		destroy(null);
	}

	public final void allocateObjectById_async(AMD_Session_allocateObjectById __cb, Identity id)
		throws AllocationException, ObjectNotRegisteredException
	{
		allocateObjectById_async(__cb, id, null);
	}

	public final void allocateObjectByType_async(AMD_Session_allocateObjectByType __cb, String type)
		throws AllocationException
	{
		allocateObjectByType_async(__cb, type, null);
	}

	public final void keepAlive()
	{
		keepAlive(null);
	}

	public final void releaseObject(Identity id)
		throws AllocationException, ObjectNotRegisteredException
	{
		releaseObject(id, null);
	}

	public final void setAllocationTimeout(int timeout)
	{
		setAllocationTimeout(timeout, null);
	}

	public static DispatchStatus ___keepAlive(Session __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Idempotent, __current.mode);
		__inS.is().skipEmptyEncaps();
		__obj.keepAlive(__current);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___allocateObjectById(Session __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		Identity id = new Identity();
		id.__read(__is);
		__is.endReadEncaps();
		AMD_Session_allocateObjectById __cb = new _AMD_Session_allocateObjectById(__inS);
		try
		{
			__obj.allocateObjectById_async(__cb, id, __current);
		}
		catch (Exception ex)
		{
			__cb.ice_exception(ex);
		}
		return DispatchStatus.DispatchAsync;
	}

	public static DispatchStatus ___allocateObjectByType(Session __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		String type = __is.readString();
		__is.endReadEncaps();
		AMD_Session_allocateObjectByType __cb = new _AMD_Session_allocateObjectByType(__inS);
		try
		{
			__obj.allocateObjectByType_async(__cb, type, __current);
		}
		catch (Exception ex)
		{
			__cb.ice_exception(ex);
		}
		return DispatchStatus.DispatchAsync;
	}

	public static DispatchStatus ___releaseObject(Session __obj, Incoming __inS, Current __current)
	{
		Identity id;
		BasicStream __os;
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		id = new Identity();
		id.__read(__is);
		__is.endReadEncaps();
		__os = __inS.os();
		__obj.releaseObject(id, __current);
		return DispatchStatus.DispatchOK;
		AllocationException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___setAllocationTimeout(Session __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Idempotent, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		int timeout = __is.readInt();
		__is.endReadEncaps();
		__obj.setAllocationTimeout(timeout, __current);
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
			return ___allocateObjectById(this, in, __current);

		case 1: // '\001'
			return ___allocateObjectByType(this, in, __current);

		case 2: // '\002'
			return Glacier2._SessionDisp.___destroy(this, in, __current);

		case 3: // '\003'
			return ___ice_id(this, in, __current);

		case 4: // '\004'
			return ___ice_ids(this, in, __current);

		case 5: // '\005'
			return ___ice_isA(this, in, __current);

		case 6: // '\006'
			return ___ice_ping(this, in, __current);

		case 7: // '\007'
			return ___keepAlive(this, in, __current);

		case 8: // '\b'
			return ___releaseObject(this, in, __current);

		case 9: // '\t'
			return ___setAllocationTimeout(this, in, __current);
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
		ex.reason = "type IceGrid::Session was not generated with stream support";
		throw ex;
	}

	public void __read(InputStream __inS, boolean __rid)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "type IceGrid::Session was not generated with stream support";
		throw ex;
	}

}
