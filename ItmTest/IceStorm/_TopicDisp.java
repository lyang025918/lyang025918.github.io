// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _TopicDisp.java

package IceStorm;

import Ice.*;
import IceInternal.BasicStream;
import IceInternal.Incoming;
import java.util.Arrays;
import java.util.Map;

// Referenced classes of package IceStorm:
//			AlreadySubscribed, BadQoS, LinkExists, NoSuchLink, 
//			Topic, QoSHelper, TopicPrxHelper, LinkInfoSeqHelper, 
//			LinkInfo, TopicPrx

public abstract class _TopicDisp extends ObjectImpl
	implements Topic
{

	public static final String __ids[] = {
		"::Ice::Object", "::IceStorm::Topic"
	};
	private static final String __all[] = {
		"destroy", "getLinkInfoSeq", "getName", "getNonReplicatedPublisher", "getPublisher", "ice_id", "ice_ids", "ice_isA", "ice_ping", "link", 
		"subscribe", "subscribeAndGetPublisher", "unlink", "unsubscribe"
	};
	static final boolean $assertionsDisabled = !IceStorm/_TopicDisp.desiredAssertionStatus();

	public _TopicDisp()
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

	public final void destroy()
	{
		destroy(null);
	}

	public final LinkInfo[] getLinkInfoSeq()
	{
		return getLinkInfoSeq(null);
	}

	public final String getName()
	{
		return getName(null);
	}

	public final ObjectPrx getNonReplicatedPublisher()
	{
		return getNonReplicatedPublisher(null);
	}

	public final ObjectPrx getPublisher()
	{
		return getPublisher(null);
	}

	public final void link(TopicPrx linkTo, int cost)
		throws LinkExists
	{
		link(linkTo, cost, null);
	}

	/**
	 * @deprecated Method subscribe is deprecated
	 */

	public final void subscribe(Map theQoS, ObjectPrx subscriber)
	{
		subscribe(theQoS, subscriber, null);
	}

	public final ObjectPrx subscribeAndGetPublisher(Map theQoS, ObjectPrx subscriber)
		throws AlreadySubscribed, BadQoS
	{
		return subscribeAndGetPublisher(theQoS, subscriber, null);
	}

	public final void unlink(TopicPrx linkTo)
		throws NoSuchLink
	{
		unlink(linkTo, null);
	}

	public final void unsubscribe(ObjectPrx subscriber)
	{
		unsubscribe(subscriber, null);
	}

	public static DispatchStatus ___getName(Topic __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Idempotent, __current.mode);
		__inS.is().skipEmptyEncaps();
		BasicStream __os = __inS.os();
		String __ret = __obj.getName(__current);
		__os.writeString(__ret);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___getPublisher(Topic __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Idempotent, __current.mode);
		__inS.is().skipEmptyEncaps();
		BasicStream __os = __inS.os();
		ObjectPrx __ret = __obj.getPublisher(__current);
		__os.writeProxy(__ret);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___getNonReplicatedPublisher(Topic __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Idempotent, __current.mode);
		__inS.is().skipEmptyEncaps();
		BasicStream __os = __inS.os();
		ObjectPrx __ret = __obj.getNonReplicatedPublisher(__current);
		__os.writeProxy(__ret);
		return DispatchStatus.DispatchOK;
	}

	/**
	 * @deprecated Method ___subscribe is deprecated
	 */

	public static DispatchStatus ___subscribe(Topic __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		Map theQoS = QoSHelper.read(__is);
		ObjectPrx subscriber = __is.readProxy();
		__is.endReadEncaps();
		__obj.subscribe(theQoS, subscriber, __current);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___subscribeAndGetPublisher(Topic __obj, Incoming __inS, Current __current)
	{
		Map theQoS;
		ObjectPrx subscriber;
		BasicStream __os;
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		theQoS = QoSHelper.read(__is);
		subscriber = __is.readProxy();
		__is.endReadEncaps();
		__os = __inS.os();
		ObjectPrx __ret = __obj.subscribeAndGetPublisher(theQoS, subscriber, __current);
		__os.writeProxy(__ret);
		return DispatchStatus.DispatchOK;
		AlreadySubscribed ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___unsubscribe(Topic __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Idempotent, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		ObjectPrx subscriber = __is.readProxy();
		__is.endReadEncaps();
		__obj.unsubscribe(subscriber, __current);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___link(Topic __obj, Incoming __inS, Current __current)
	{
		TopicPrx linkTo;
		int cost;
		BasicStream __os;
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		linkTo = TopicPrxHelper.__read(__is);
		cost = __is.readInt();
		__is.endReadEncaps();
		__os = __inS.os();
		__obj.link(linkTo, cost, __current);
		return DispatchStatus.DispatchOK;
		LinkExists ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___unlink(Topic __obj, Incoming __inS, Current __current)
	{
		TopicPrx linkTo;
		BasicStream __os;
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		linkTo = TopicPrxHelper.__read(__is);
		__is.endReadEncaps();
		__os = __inS.os();
		__obj.unlink(linkTo, __current);
		return DispatchStatus.DispatchOK;
		NoSuchLink ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___getLinkInfoSeq(Topic __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Idempotent, __current.mode);
		__inS.is().skipEmptyEncaps();
		BasicStream __os = __inS.os();
		LinkInfo __ret[] = __obj.getLinkInfoSeq(__current);
		LinkInfoSeqHelper.write(__os, __ret);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___destroy(Topic __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Normal, __current.mode);
		__inS.is().skipEmptyEncaps();
		__obj.destroy(__current);
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
			return ___destroy(this, in, __current);

		case 1: // '\001'
			return ___getLinkInfoSeq(this, in, __current);

		case 2: // '\002'
			return ___getName(this, in, __current);

		case 3: // '\003'
			return ___getNonReplicatedPublisher(this, in, __current);

		case 4: // '\004'
			return ___getPublisher(this, in, __current);

		case 5: // '\005'
			return ___ice_id(this, in, __current);

		case 6: // '\006'
			return ___ice_ids(this, in, __current);

		case 7: // '\007'
			return ___ice_isA(this, in, __current);

		case 8: // '\b'
			return ___ice_ping(this, in, __current);

		case 9: // '\t'
			return ___link(this, in, __current);

		case 10: // '\n'
			return ___subscribe(this, in, __current);

		case 11: // '\013'
			return ___subscribeAndGetPublisher(this, in, __current);

		case 12: // '\f'
			return ___unlink(this, in, __current);

		case 13: // '\r'
			return ___unsubscribe(this, in, __current);
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
		ex.reason = "type IceStorm::Topic was not generated with stream support";
		throw ex;
	}

	public void __read(InputStream __inS, boolean __rid)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "type IceStorm::Topic was not generated with stream support";
		throw ex;
	}

}
