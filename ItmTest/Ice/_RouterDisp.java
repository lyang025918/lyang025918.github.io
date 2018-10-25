// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _RouterDisp.java

package Ice;

import IceInternal.BasicStream;
import IceInternal.Incoming;
import java.util.Arrays;

// Referenced classes of package Ice:
//			ObjectImpl, OperationNotExistException, MarshalException, Router, 
//			OperationMode, Current, DispatchStatus, ObjectProxySeqHelper, 
//			Object, ObjectPrx, OutputStream, InputStream

public abstract class _RouterDisp extends ObjectImpl
	implements Router
{

	public static final String __ids[] = {
		"::Ice::Object", "::Ice::Router"
	};
	private static final String __all[] = {
		"addProxies", "addProxy", "getClientProxy", "getServerProxy", "ice_id", "ice_ids", "ice_isA", "ice_ping"
	};
	static final boolean $assertionsDisabled = !Ice/_RouterDisp.desiredAssertionStatus();

	public _RouterDisp()
	{
	}

	protected void ice_copyStateFrom(Object __obj)
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

	public final ObjectPrx[] addProxies(ObjectPrx proxies[])
	{
		return addProxies(proxies, null);
	}

	/**
	 * @deprecated Method addProxy is deprecated
	 */

	public final void addProxy(ObjectPrx proxy)
	{
		addProxy(proxy, null);
	}

	public final ObjectPrx getClientProxy()
	{
		return getClientProxy(null);
	}

	public final ObjectPrx getServerProxy()
	{
		return getServerProxy(null);
	}

	public static DispatchStatus ___getClientProxy(Router __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Idempotent, __current.mode);
		__inS.is().skipEmptyEncaps();
		BasicStream __os = __inS.os();
		ObjectPrx __ret = __obj.getClientProxy(__current);
		__os.writeProxy(__ret);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___getServerProxy(Router __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Idempotent, __current.mode);
		__inS.is().skipEmptyEncaps();
		BasicStream __os = __inS.os();
		ObjectPrx __ret = __obj.getServerProxy(__current);
		__os.writeProxy(__ret);
		return DispatchStatus.DispatchOK;
	}

	/**
	 * @deprecated Method ___addProxy is deprecated
	 */

	public static DispatchStatus ___addProxy(Router __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Idempotent, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		ObjectPrx proxy = __is.readProxy();
		__is.endReadEncaps();
		__obj.addProxy(proxy, __current);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___addProxies(Router __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Idempotent, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		ObjectPrx proxies[] = ObjectProxySeqHelper.read(__is);
		__is.endReadEncaps();
		BasicStream __os = __inS.os();
		ObjectPrx __ret[] = __obj.addProxies(proxies, __current);
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
			return ___addProxies(this, in, __current);

		case 1: // '\001'
			return ___addProxy(this, in, __current);

		case 2: // '\002'
			return ___getClientProxy(this, in, __current);

		case 3: // '\003'
			return ___getServerProxy(this, in, __current);

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
		ex.reason = "type Ice::Router was not generated with stream support";
		throw ex;
	}

	public void __read(InputStream __inS, boolean __rid)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "type Ice::Router was not generated with stream support";
		throw ex;
	}

}
