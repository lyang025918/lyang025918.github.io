// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _ServiceManagerDisp.java

package IceBox;

import Ice.*;
import IceInternal.BasicStream;
import IceInternal.Incoming;
import java.util.Arrays;
import java.util.Map;

// Referenced classes of package IceBox:
//			AlreadyStartedException, NoSuchServiceException, AlreadyStoppedException, ServiceManager, 
//			ServiceObserverPrxHelper, ServiceObserverPrx

public abstract class _ServiceManagerDisp extends ObjectImpl
	implements ServiceManager
{

	public static final String __ids[] = {
		"::Ice::Object", "::IceBox::ServiceManager"
	};
	private static final String __all[] = {
		"addObserver", "getSliceChecksums", "ice_id", "ice_ids", "ice_isA", "ice_ping", "shutdown", "startService", "stopService"
	};
	static final boolean $assertionsDisabled = !IceBox/_ServiceManagerDisp.desiredAssertionStatus();

	public _ServiceManagerDisp()
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

	public final void addObserver(ServiceObserverPrx observer)
	{
		addObserver(observer, null);
	}

	public final Map getSliceChecksums()
	{
		return getSliceChecksums(null);
	}

	public final void shutdown()
	{
		shutdown(null);
	}

	public final void startService(String service)
		throws AlreadyStartedException, NoSuchServiceException
	{
		startService(service, null);
	}

	public final void stopService(String service)
		throws AlreadyStoppedException, NoSuchServiceException
	{
		stopService(service, null);
	}

	public static DispatchStatus ___getSliceChecksums(ServiceManager __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Idempotent, __current.mode);
		__inS.is().skipEmptyEncaps();
		BasicStream __os = __inS.os();
		Map __ret = __obj.getSliceChecksums(__current);
		SliceChecksumDictHelper.write(__os, __ret);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___startService(ServiceManager __obj, Incoming __inS, Current __current)
	{
		String service;
		BasicStream __os;
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		service = __is.readString();
		__is.endReadEncaps();
		__os = __inS.os();
		__obj.startService(service, __current);
		return DispatchStatus.DispatchOK;
		AlreadyStartedException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___stopService(ServiceManager __obj, Incoming __inS, Current __current)
	{
		String service;
		BasicStream __os;
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		service = __is.readString();
		__is.endReadEncaps();
		__os = __inS.os();
		__obj.stopService(service, __current);
		return DispatchStatus.DispatchOK;
		AlreadyStoppedException ex;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
		ex;
		__os.writeUserException(ex);
		return DispatchStatus.DispatchUserException;
	}

	public static DispatchStatus ___addObserver(ServiceManager __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Normal, __current.mode);
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		ServiceObserverPrx observer = ServiceObserverPrxHelper.__read(__is);
		__is.endReadEncaps();
		__obj.addObserver(observer, __current);
		return DispatchStatus.DispatchOK;
	}

	public static DispatchStatus ___shutdown(ServiceManager __obj, Incoming __inS, Current __current)
	{
		__checkMode(OperationMode.Normal, __current.mode);
		__inS.is().skipEmptyEncaps();
		__obj.shutdown(__current);
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
			return ___addObserver(this, in, __current);

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
			return ___shutdown(this, in, __current);

		case 7: // '\007'
			return ___startService(this, in, __current);

		case 8: // '\b'
			return ___stopService(this, in, __current);
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
		ex.reason = "type IceBox::ServiceManager was not generated with stream support";
		throw ex;
	}

	public void __read(InputStream __inS, boolean __rid)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "type IceBox::ServiceManager was not generated with stream support";
		throw ex;
	}

}
