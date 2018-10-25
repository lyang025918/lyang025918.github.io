// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _QueryDisp.java

package IceGrid;

import Ice.*;
import IceInternal.BasicStream;
import IceInternal.Incoming;
import java.util.Arrays;

// Referenced classes of package IceGrid:
//			Query, LoadSample

public abstract class _QueryDisp extends ObjectImpl
	implements Query
{

	public static final String __ids[] = {
		"::Ice::Object", "::IceGrid::Query"
	};
	private static final String __all[] = {
		"findAllObjectsByType", "findAllReplicas", "findObjectById", "findObjectByType", "findObjectByTypeOnLeastLoadedNode", "ice_id", "ice_ids", "ice_isA", "ice_ping"
	};
	static final boolean $assertionsDisabled = !IceGrid/_QueryDisp.desiredAssertionStatus();

	public _QueryDisp()
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

	public final ObjectPrx[] findAllObjectsByType(String type)
	{
		return findAllObjectsByType(type, null);
	}

	public final ObjectPrx[] findAllReplicas(ObjectPrx proxy)
	{
		return findAllReplicas(proxy, null);
	}

	public final ObjectPrx findObjectById(Identity id)
	{
		return findObjectById(id, null);
	}

	public final ObjectPrx findObjectByType(String type)
	{
		return findObjectByType(type, null);
	}

	public final ObjectPrx findObjectByTypeOnLeastLoadedNode(String type, LoadSample sample)
	{
		return findObjectByTypeOnLeastLoadedNode(type, sample, null);
	}

	public static DispatchStatus ___findObjectById(Query __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Idempotent, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		Identity id = new Identity();
		id.__read(__is);
		__is.endReadEncaps();
		BasicStream __os = __inS.os();
		ObjectPrx __ret = __obj.findObjectById(id, __current);
		__os.writeProxy(__ret);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___findObjectByType(Query __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Idempotent, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		String type = __is.readString();
		__is.endReadEncaps();
		BasicStream __os = __inS.os();
		ObjectPrx __ret = __obj.findObjectByType(type, __current);
		__os.writeProxy(__ret);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___findObjectByTypeOnLeastLoadedNode(Query __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Idempotent, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		String type = __is.readString();
		LoadSample sample = LoadSample.__read(__is);
		__is.endReadEncaps();
		BasicStream __os = __inS.os();
		ObjectPrx __ret = __obj.findObjectByTypeOnLeastLoadedNode(type, sample, __current);
		__os.writeProxy(__ret);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___findAllObjectsByType(Query __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Idempotent, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		String type = __is.readString();
		__is.endReadEncaps();
		BasicStream __os = __inS.os();
		ObjectPrx __ret[] = __obj.findAllObjectsByType(type, __current);
		ObjectProxySeqHelper.write(__os, __ret);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___findAllReplicas(Query __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Idempotent, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		ObjectPrx proxy = __is.readProxy();
		__is.endReadEncaps();
		BasicStream __os = __inS.os();
		ObjectPrx __ret[] = __obj.findAllReplicas(proxy, __current);
		ObjectProxySeqHelper.write(__os, __ret);
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
			return ___findAllObjectsByType(this, in, __current);

		case 1: // '\001'
			return ___findAllReplicas(this, in, __current);

		case 2: // '\002'
			return ___findObjectById(this, in, __current);

		case 3: // '\003'
			return ___findObjectByType(this, in, __current);

		case 4: // '\004'
			return ___findObjectByTypeOnLeastLoadedNode(this, in, __current);

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
		ex.reason = "type IceGrid::Query was not generated with stream support";
		throw ex;
	}

	public void __read(InputStream __inS, boolean __rid)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "type IceGrid::Query was not generated with stream support";
		throw ex;
	}

}
