// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _SvcBuildCallbackDisp.java

package com.iflytek.itm.svc.svccom.gen;

import Ice.*;
import IceInternal.BasicStream;
import IceInternal.Incoming;
import java.util.Arrays;

// Referenced classes of package com.iflytek.itm.svc.svccom.gen:
//			SvcDocumentHolder, SvcBuildCallback, SvcDocument

public abstract class _SvcBuildCallbackDisp extends ObjectImpl
	implements SvcBuildCallback
{

	public static final String __ids[] = {
		"::Ice::Object", "::com::iflytek::itm::svc::svccom::gen::SvcBuildCallback"
	};
	private static final String __all[] = {
		"event", "ice_id", "ice_ids", "ice_isA", "ice_ping", "read"
	};
	static final boolean $assertionsDisabled = !com/iflytek/itm/svc/svccom/gen/_SvcBuildCallbackDisp.desiredAssertionStatus();

	public _SvcBuildCallbackDisp()
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

	public final void event(String id, int evt, String msg)
	{
		event(id, evt, msg, null);
	}

	public final int read(SvcDocumentHolder doc)
	{
		return read(doc, null);
	}

	public static DispatchStatus ___read(SvcBuildCallback __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Normal, __current.mode);
		__inS.is().skipEmptyEncaps();
		SvcDocumentHolder doc = new SvcDocumentHolder();
		BasicStream __os = __inS.os();
		int __ret = __obj.read(doc, __current);
		doc.value.__write(__os);
		__os.writeInt(__ret);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___event(SvcBuildCallback __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		String id = __is.readString();
		int evt = __is.readInt();
		String msg = __is.readString();
		__is.endReadEncaps();
		__obj.event(id, evt, msg, __current);
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
			return ___event(this, in, __current);

		case 1: // '\001'
			return ___ice_id(this, in, __current);

		case 2: // '\002'
			return ___ice_ids(this, in, __current);

		case 3: // '\003'
			return ___ice_isA(this, in, __current);

		case 4: // '\004'
			return ___ice_ping(this, in, __current);

		case 5: // '\005'
			return ___read(this, in, __current);
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
		ex.reason = "type com::iflytek::itm::svc::svccom::gen::SvcBuildCallback was not generated with stream support";
		throw ex;
	}

	public void __read(InputStream __inS, boolean __rid)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "type com::iflytek::itm::svc::svccom::gen::SvcBuildCallback was not generated with stream support";
		throw ex;
	}

}
