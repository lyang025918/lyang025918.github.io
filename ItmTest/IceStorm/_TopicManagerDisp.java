// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _TopicManagerDisp.java

package IceStorm;

import Ice.*;
import IceInternal.BasicStream;
import IceInternal.Incoming;
import java.util.Arrays;
import java.util.Map;

// Referenced classes of package IceStorm:
//			TopicExists, NoSuchTopic, TopicManager, TopicPrxHelper, 
//			TopicDictHelper, TopicPrx

public abstract class _TopicManagerDisp extends ObjectImpl
	implements TopicManager
{

	public static final String __ids[] = {
		"::Ice::Object", "::IceStorm::TopicManager"
	};
	private static final String __all[] = {
		"create", "getSliceChecksums", "ice_id", "ice_ids", "ice_isA", "ice_ping", "retrieve", "retrieveAll"
	};
	static final boolean $assertionsDisabled = !IceStorm/_TopicManagerDisp.desiredAssertionStatus();

	public _TopicManagerDisp()
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

	public final TopicPrx create(String name)
		throws TopicExists
	{
		return create(name, null);
	}

	public final Map getSliceChecksums()
	{
		return getSliceChecksums(null);
	}

	public final TopicPrx retrieve(String name)
		throws NoSuchTopic
	{
		return retrieve(name, null);
	}

	public final Map retrieveAll()
	{
		return retrieveAll(null);
	}

	public static DispatchStatus ___create(TopicManager __obj, Incoming __inS, Current __current)
	{
		String name;
		BasicStream __os;
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		name = __is.readString();
		__is.endReadEncaps();
		__os = __inS.os();
		TopicPrx __ret = __obj.create(name, __current);
		TopicPrxHelper.__write(__os, __ret);
		return DispatchStatus.DispatchOK;
		TopicExists ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___retrieve(TopicManager __obj, Incoming __inS, Current __current)
	{
		String name;
		BasicStream __os;
		__checkMode(OperationMode.Idempotent, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		name = __is.readString();
		__is.endReadEncaps();
		__os = __inS.os();
		TopicPrx __ret = __obj.retrieve(name, __current);
		TopicPrxHelper.__write(__os, __ret);
		return DispatchStatus.DispatchOK;
		NoSuchTopic ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___retrieveAll(TopicManager __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Idempotent, __current.mode);
		__inS.is().skipEmptyEncaps();
		BasicStream __os = __inS.os();
		Map __ret = __obj.retrieveAll(__current);
		TopicDictHelper.write(__os, __ret);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___getSliceChecksums(TopicManager __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Idempotent, __current.mode);
		__inS.is().skipEmptyEncaps();
		BasicStream __os = __inS.os();
		Map __ret = __obj.getSliceChecksums(__current);
		SliceChecksumDictHelper.write(__os, __ret);
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
			return ___create(this, in, __current);

		case 1: // '\001'
			return ___getSliceChecksums(this, in, __current);

		case 2: // '\002'
			return ___ice_id(this, in, __current);

		case 3: // '\003'
			return ___ice_ids(this, in, __current);

		case 4: // '\004'
			return ___ice_isA(this, in, __current);

		case 5: // '\005'
			return ___ice_ping(this, in, __current);

		case 6: // '\006'
			return ___retrieve(this, in, __current);

		case 7: // '\007'
			return ___retrieveAll(this, in, __current);
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
		ex.reason = "type IceStorm::TopicManager was not generated with stream support";
		throw ex;
	}

	public void __read(InputStream __inS, boolean __rid)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "type IceStorm::TopicManager was not generated with stream support";
		throw ex;
	}

}
