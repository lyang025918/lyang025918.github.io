// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _ObjectObserverDisp.java

package IceGrid;

import Ice.*;
import IceInternal.BasicStream;
import IceInternal.Incoming;
import java.util.Arrays;

// Referenced classes of package IceGrid:
//			ObjectInfo, ObjectObserver, ObjectInfoSeqHelper

public abstract class _ObjectObserverDisp extends ObjectImpl
	implements ObjectObserver
{

	public static final String __ids[] = {
		"::Ice::Object", "::IceGrid::ObjectObserver"
	};
	private static final String __all[] = {
		"ice_id", "ice_ids", "ice_isA", "ice_ping", "objectAdded", "objectInit", "objectRemoved", "objectUpdated"
	};
	static final boolean $assertionsDisabled = !IceGrid/_ObjectObserverDisp.desiredAssertionStatus();

	public _ObjectObserverDisp()
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

	public final void objectAdded(ObjectInfo info)
	{
		objectAdded(info, null);
	}

	public final void objectInit(ObjectInfo objects[])
	{
		objectInit(objects, null);
	}

	public final void objectRemoved(Identity id)
	{
		objectRemoved(id, null);
	}

	public final void objectUpdated(ObjectInfo info)
	{
		objectUpdated(info, null);
	}

	public static DispatchStatus ___objectInit(ObjectObserver __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		ObjectInfo objects[] = ObjectInfoSeqHelper.read(__is);
		__is.endReadEncaps();
		__obj.objectInit(objects, __current);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___objectAdded(ObjectObserver __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		ObjectInfo info = new ObjectInfo();
		info.__read(__is);
		__is.endReadEncaps();
		__obj.objectAdded(info, __current);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___objectUpdated(ObjectObserver __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		ObjectInfo info = new ObjectInfo();
		info.__read(__is);
		__is.endReadEncaps();
		__obj.objectUpdated(info, __current);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___objectRemoved(ObjectObserver __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		Identity id = new Identity();
		id.__read(__is);
		__is.endReadEncaps();
		__obj.objectRemoved(id, __current);
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
			return ___ice_id(this, in, __current);

		case 1: // '\001'
			return ___ice_ids(this, in, __current);

		case 2: // '\002'
			return ___ice_isA(this, in, __current);

		case 3: // '\003'
			return ___ice_ping(this, in, __current);

		case 4: // '\004'
			return ___objectAdded(this, in, __current);

		case 5: // '\005'
			return ___objectInit(this, in, __current);

		case 6: // '\006'
			return ___objectRemoved(this, in, __current);

		case 7: // '\007'
			return ___objectUpdated(this, in, __current);
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
		ex.reason = "type IceGrid::ObjectObserver was not generated with stream support";
		throw ex;
	}

	public void __read(InputStream __inS, boolean __rid)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "type IceGrid::ObjectObserver was not generated with stream support";
		throw ex;
	}

}
