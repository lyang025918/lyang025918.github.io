// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _AdapterObserverDisp.java

package IceGrid;

import Ice.*;
import IceInternal.BasicStream;
import IceInternal.Incoming;
import java.util.Arrays;

// Referenced classes of package IceGrid:
//			AdapterInfo, AdapterObserver, AdapterInfoSeqHelper

public abstract class _AdapterObserverDisp extends ObjectImpl
	implements AdapterObserver
{

	public static final String __ids[] = {
		"::Ice::Object", "::IceGrid::AdapterObserver"
	};
	private static final String __all[] = {
		"adapterAdded", "adapterInit", "adapterRemoved", "adapterUpdated", "ice_id", "ice_ids", "ice_isA", "ice_ping"
	};
	static final boolean $assertionsDisabled = !IceGrid/_AdapterObserverDisp.desiredAssertionStatus();

	public _AdapterObserverDisp()
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

	public final void adapterAdded(AdapterInfo info)
	{
		adapterAdded(info, null);
	}

	public final void adapterInit(AdapterInfo adpts[])
	{
		adapterInit(adpts, null);
	}

	public final void adapterRemoved(String id)
	{
		adapterRemoved(id, null);
	}

	public final void adapterUpdated(AdapterInfo info)
	{
		adapterUpdated(info, null);
	}

	public static DispatchStatus ___adapterInit(AdapterObserver __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		AdapterInfo adpts[] = AdapterInfoSeqHelper.read(__is);
		__is.endReadEncaps();
		__obj.adapterInit(adpts, __current);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___adapterAdded(AdapterObserver __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		AdapterInfo info = new AdapterInfo();
		info.__read(__is);
		__is.endReadEncaps();
		__obj.adapterAdded(info, __current);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___adapterUpdated(AdapterObserver __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		AdapterInfo info = new AdapterInfo();
		info.__read(__is);
		__is.endReadEncaps();
		__obj.adapterUpdated(info, __current);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___adapterRemoved(AdapterObserver __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		String id = __is.readString();
		__is.endReadEncaps();
		__obj.adapterRemoved(id, __current);
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
			return ___adapterAdded(this, in, __current);

		case 1: // '\001'
			return ___adapterInit(this, in, __current);

		case 2: // '\002'
			return ___adapterRemoved(this, in, __current);

		case 3: // '\003'
			return ___adapterUpdated(this, in, __current);

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
		ex.reason = "type IceGrid::AdapterObserver was not generated with stream support";
		throw ex;
	}

	public void __read(InputStream __inS, boolean __rid)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "type IceGrid::AdapterObserver was not generated with stream support";
		throw ex;
	}

}
