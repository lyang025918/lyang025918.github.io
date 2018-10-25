// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _ItmSvcDisp.java

package com.iflytek.itm.svc.svccom.gen;

import Ice.*;
import IceInternal.BasicStream;
import IceInternal.Incoming;
import java.util.Arrays;

// Referenced classes of package com.iflytek.itm.svc.svccom.gen:
//			ItmSvc, SvcBuildCallbackPrxHelper, SvcSearchResultPrxHelper, SvcBuildCallbackPrx, 
//			SvcSearchResultPrx

public abstract class _ItmSvcDisp extends ObjectImpl
	implements ItmSvc
{

	public static final String __ids[] = {
		"::Ice::Object", "::com::iflytek::itm::svc::svccom::gen::ItmSvc"
	};
	private static final String __all[] = {
		"build", "ice_id", "ice_ids", "ice_isA", "ice_ping", "maintain", "mining", "search"
	};
	static final boolean $assertionsDisabled = !com/iflytek/itm/svc/svccom/gen/_ItmSvcDisp.desiredAssertionStatus();

	public _ItmSvcDisp()
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

	public final int build(String indexPath, String params, SvcBuildCallbackPrx builder)
	{
		return build(indexPath, params, builder, null);
	}

	public final int maintain(String indexPath, String action, String params)
	{
		return maintain(indexPath, action, params, null);
	}

	public final int mining(String indexPath, String type, String params, StringHolder buffer)
	{
		return mining(indexPath, type, params, buffer, null);
	}

	public final SvcSearchResultPrx search(String indexPath, String querySyntax, String params, IntHolder errcode)
	{
		return search(indexPath, querySyntax, params, errcode, null);
	}

	public static DispatchStatus ___build(ItmSvc __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		String indexPath = __is.readString();
		String params = __is.readString();
		SvcBuildCallbackPrx builder = SvcBuildCallbackPrxHelper.__read(__is);
		__is.endReadEncaps();
		BasicStream __os = __inS.os();
		int __ret = __obj.build(indexPath, params, builder, __current);
		__os.writeInt(__ret);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___search(ItmSvc __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		String indexPath = __is.readString();
		String querySyntax = __is.readString();
		String params = __is.readString();
		__is.endReadEncaps();
		IntHolder errcode = new IntHolder();
		BasicStream __os = __inS.os();
		SvcSearchResultPrx __ret = __obj.search(indexPath, querySyntax, params, errcode, __current);
		__os.writeInt(errcode.value);
		SvcSearchResultPrxHelper.__write(__os, __ret);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___mining(ItmSvc __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		String indexPath = __is.readString();
		String type = __is.readString();
		String params = __is.readString();
		__is.endReadEncaps();
		StringHolder buffer = new StringHolder();
		BasicStream __os = __inS.os();
		int __ret = __obj.mining(indexPath, type, params, buffer, __current);
		__os.writeString(buffer.value);
		__os.writeInt(__ret);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___maintain(ItmSvc __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		String indexPath = __is.readString();
		String action = __is.readString();
		String params = __is.readString();
		__is.endReadEncaps();
		BasicStream __os = __inS.os();
		int __ret = __obj.maintain(indexPath, action, params, __current);
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
			return ___build(this, in, __current);

		case 1: // '\001'
			return ___ice_id(this, in, __current);

		case 2: // '\002'
			return ___ice_ids(this, in, __current);

		case 3: // '\003'
			return ___ice_isA(this, in, __current);

		case 4: // '\004'
			return ___ice_ping(this, in, __current);

		case 5: // '\005'
			return ___maintain(this, in, __current);

		case 6: // '\006'
			return ___mining(this, in, __current);

		case 7: // '\007'
			return ___search(this, in, __current);
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
		ex.reason = "type com::iflytek::itm::svc::svccom::gen::ItmSvc was not generated with stream support";
		throw ex;
	}

	public void __read(InputStream __inS, boolean __rid)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "type com::iflytek::itm::svc::svccom::gen::ItmSvc was not generated with stream support";
		throw ex;
	}

}
