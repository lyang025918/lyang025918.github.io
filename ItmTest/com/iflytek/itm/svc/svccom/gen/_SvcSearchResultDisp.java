// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _SvcSearchResultDisp.java

package com.iflytek.itm.svc.svccom.gen;

import Ice.*;
import IceInternal.BasicStream;
import IceInternal.Incoming;
import java.util.*;

// Referenced classes of package com.iflytek.itm.svc.svccom.gen:
//			SvcDocument, SvcGroup, SvcSearchResult

public abstract class _SvcSearchResultDisp extends ObjectImpl
	implements SvcSearchResult
{

	public static final String __ids[] = {
		"::Ice::Object", "::com::iflytek::itm::svc::svccom::gen::SvcSearchResult"
	};
	private static final String __all[] = {
		"close", "docs", "getGroupTotalHits", "getTotalHits", "groups", "ice_id", "ice_ids", "ice_isA", "ice_ping"
	};
	static final boolean $assertionsDisabled = !com/iflytek/itm/svc/svccom/gen/_SvcSearchResultDisp.desiredAssertionStatus();

	public _SvcSearchResultDisp()
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

	public final int close()
	{
		return close(null);
	}

	public final int docs(Holder svcdocs)
	{
		return docs(svcdocs, null);
	}

	public final int getGroupTotalHits()
	{
		return getGroupTotalHits(null);
	}

	public final int getTotalHits()
	{
		return getTotalHits(null);
	}

	public final int groups(Holder svcgroups)
	{
		return groups(svcgroups, null);
	}

	public static DispatchStatus ___getTotalHits(SvcSearchResult __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Normal, __current.mode);
		__inS.is().skipEmptyEncaps();
		BasicStream __os = __inS.os();
		int __ret = __obj.getTotalHits(__current);
		__os.writeInt(__ret);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___docs(SvcSearchResult __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Normal, __current.mode);
		__inS.is().skipEmptyEncaps();
		Holder svcdocs = new Holder();
		BasicStream __os = __inS.os();
		int __ret = __obj.docs(svcdocs, __current);
		if (svcdocs.value == null)
		{
			__os.writeSize(0);
		} else
		{
			__os.writeSize(((List)svcdocs.value).size());
			SvcDocument __elem;
			for (Iterator iterator = ((List)svcdocs.value).iterator(); iterator.hasNext(); __elem.__write(__os))
				__elem = (SvcDocument)iterator.next();

		}
		__os.writeInt(__ret);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___getGroupTotalHits(SvcSearchResult __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Normal, __current.mode);
		__inS.is().skipEmptyEncaps();
		BasicStream __os = __inS.os();
		int __ret = __obj.getGroupTotalHits(__current);
		__os.writeInt(__ret);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___groups(SvcSearchResult __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Normal, __current.mode);
		__inS.is().skipEmptyEncaps();
		Holder svcgroups = new Holder();
		BasicStream __os = __inS.os();
		int __ret = __obj.groups(svcgroups, __current);
		if (svcgroups.value == null)
		{
			__os.writeSize(0);
		} else
		{
			__os.writeSize(((List)svcgroups.value).size());
			SvcGroup __elem;
			for (Iterator iterator = ((List)svcgroups.value).iterator(); iterator.hasNext(); __elem.__write(__os))
				__elem = (SvcGroup)iterator.next();

		}
		__os.writeInt(__ret);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___close(SvcSearchResult __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Normal, __current.mode);
		__inS.is().skipEmptyEncaps();
		BasicStream __os = __inS.os();
		int __ret = __obj.close(__current);
		__os.writeInt(__ret);
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
			return ___close(this, in, __current);

		case 1: // '\001'
			return ___docs(this, in, __current);

		case 2: // '\002'
			return ___getGroupTotalHits(this, in, __current);

		case 3: // '\003'
			return ___getTotalHits(this, in, __current);

		case 4: // '\004'
			return ___groups(this, in, __current);

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
		ex.reason = "type com::iflytek::itm::svc::svccom::gen::SvcSearchResult was not generated with stream support";
		throw ex;
	}

	public void __read(InputStream __inS, boolean __rid)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "type com::iflytek::itm::svc::svccom::gen::SvcSearchResult was not generated with stream support";
		throw ex;
	}

}
