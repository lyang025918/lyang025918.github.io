// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _ApplicationObserverDisp.java

package IceGrid;

import Ice.*;
import IceInternal.BasicStream;
import IceInternal.Incoming;
import java.util.Arrays;
import java.util.List;

// Referenced classes of package IceGrid:
//			ApplicationInfo, ApplicationUpdateInfo, ApplicationObserver, ApplicationInfoSeqHelper

public abstract class _ApplicationObserverDisp extends ObjectImpl
	implements ApplicationObserver
{

	public static final String __ids[] = {
		"::Ice::Object", "::IceGrid::ApplicationObserver"
	};
	private static final String __all[] = {
		"applicationAdded", "applicationInit", "applicationRemoved", "applicationUpdated", "ice_id", "ice_ids", "ice_isA", "ice_ping"
	};
	static final boolean $assertionsDisabled = !IceGrid/_ApplicationObserverDisp.desiredAssertionStatus();

	public _ApplicationObserverDisp()
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

	public final void applicationAdded(int serial, ApplicationInfo desc)
	{
		applicationAdded(serial, desc, null);
	}

	public final void applicationInit(int serial, List applications)
	{
		applicationInit(serial, applications, null);
	}

	public final void applicationRemoved(int serial, String name)
	{
		applicationRemoved(serial, name, null);
	}

	public final void applicationUpdated(int serial, ApplicationUpdateInfo desc)
	{
		applicationUpdated(serial, desc, null);
	}

	public static DispatchStatus ___applicationInit(ApplicationObserver __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		int serial = __is.readInt();
		List applications = ApplicationInfoSeqHelper.read(__is);
		__is.readPendingObjects();
		__is.endReadEncaps();
		__obj.applicationInit(serial, applications, __current);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___applicationAdded(ApplicationObserver __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		int serial = __is.readInt();
		ApplicationInfo desc = new ApplicationInfo();
		desc.__read(__is);
		__is.readPendingObjects();
		__is.endReadEncaps();
		__obj.applicationAdded(serial, desc, __current);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___applicationRemoved(ApplicationObserver __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		int serial = __is.readInt();
		String name = __is.readString();
		__is.endReadEncaps();
		__obj.applicationRemoved(serial, name, __current);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___applicationUpdated(ApplicationObserver __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		int serial = __is.readInt();
		ApplicationUpdateInfo desc = new ApplicationUpdateInfo();
		desc.__read(__is);
		__is.readPendingObjects();
		__is.endReadEncaps();
		__obj.applicationUpdated(serial, desc, __current);
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
			return ___applicationAdded(this, in, __current);

		case 1: // '\001'
			return ___applicationInit(this, in, __current);

		case 2: // '\002'
			return ___applicationRemoved(this, in, __current);

		case 3: // '\003'
			return ___applicationUpdated(this, in, __current);

		case 4: // '\004'
			return ___ice_id(this, in, __current);

		case 5: // '\005'
			return ___ice_ids(this, in, __current);

		case 6: // '\006'
			return ___ice_isA(this, in, __current);

		case 7: // '\007'
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
		ex.reason = "type IceGrid::ApplicationObserver was not generated with stream support";
		throw ex;
	}

	public void __read(InputStream __inS, boolean __rid)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "type IceGrid::ApplicationObserver was not generated with stream support";
		throw ex;
	}

}
