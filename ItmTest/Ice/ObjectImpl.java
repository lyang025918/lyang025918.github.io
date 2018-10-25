// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ObjectImpl.java

package Ice;

import IceInternal.BasicStream;
import IceInternal.Direct;
import IceInternal.Incoming;
import java.io.Serializable;
import java.util.Arrays;

// Referenced classes of package Ice:
//			OperationNotExistException, MarshalException, Object, DispatchStatus, 
//			Request, Current, OutputStream, InputStream, 
//			OperationMode, DispatchInterceptorAsyncCallback

public abstract class ObjectImpl
	implements Object, Cloneable, Serializable
{

	public static final String __ids[] = {
		"::Ice::Object"
	};
	private static final String __all[] = {
		"ice_id", "ice_ids", "ice_isA", "ice_ping"
	};
	static final boolean $assertionsDisabled = !Ice/ObjectImpl.desiredAssertionStatus();

	public ObjectImpl()
	{
	}

	public Object clone()
	{
		Object o = null;
		try
		{
			o = super.clone();
		}
		catch (CloneNotSupportedException ex)
		{
			if (!$assertionsDisabled)
				throw new AssertionError();
		}
		return o;
	}

	/**
	 * @deprecated Method ice_hash is deprecated
	 */

	public int ice_hash()
	{
		return hashCode();
	}

	public boolean ice_isA(String s)
	{
		return s.equals(__ids[0]);
	}

	public boolean ice_isA(String s, Current current)
	{
		return s.equals(__ids[0]);
	}

	public static DispatchStatus ___ice_isA(Object __obj, Incoming __inS, Current __current)
	{
		BasicStream __is = __inS.is();
		__is.startReadEncaps();
		String __id = __is.readString();
		__is.endReadEncaps();
		boolean __ret = __obj.ice_isA(__id, __current);
		BasicStream __os = __inS.os();
		__os.writeBool(__ret);
		return DispatchStatus.DispatchOK;
	}

	public void ice_ping()
	{
	}

	public void ice_ping(Current current1)
	{
	}

	public static DispatchStatus ___ice_ping(Object __obj, Incoming __inS, Current __current)
	{
		__inS.is().skipEmptyEncaps();
		__obj.ice_ping(__current);
		return DispatchStatus.DispatchOK;
	}

	public String[] ice_ids()
	{
		return __ids;
	}

	public String[] ice_ids(Current current)
	{
		return __ids;
	}

	public static DispatchStatus ___ice_ids(Object __obj, Incoming __inS, Current __current)
	{
		__inS.is().skipEmptyEncaps();
		String __ret[] = __obj.ice_ids(__current);
		BasicStream __os = __inS.os();
		__os.writeStringSeq(__ret);
		return DispatchStatus.DispatchOK;
	}

	public String ice_id()
	{
		return __ids[0];
	}

	public String ice_id(Current current)
	{
		return __ids[0];
	}

	public static DispatchStatus ___ice_id(Object __obj, Incoming __inS, Current __current)
	{
		__inS.is().skipEmptyEncaps();
		String __ret = __obj.ice_id(__current);
		BasicStream __os = __inS.os();
		__os.writeString(__ret);
		return DispatchStatus.DispatchOK;
	}

	public static String ice_staticId()
	{
		return __ids[0];
	}

	public int ice_operationAttributes(String operation)
	{
		return 0;
	}

	public void ice_preMarshal()
	{
	}

	public void ice_postUnmarshal()
	{
	}

	public DispatchStatus ice_dispatch(Request request, DispatchInterceptorAsyncCallback cb)
	{
		Incoming in;
		if (request.isCollocated())
			return __collocDispatch((Direct)request);
		in = (Incoming)request;
		if (cb != null)
			in.push(cb);
		DispatchStatus dispatchstatus;
		in.startOver();
		dispatchstatus = __dispatch(in, in.getCurrent());
		if (cb != null)
			in.pop();
		return dispatchstatus;
		Exception exception;
		exception;
		if (cb != null)
			in.pop();
		throw exception;
	}

	public DispatchStatus ice_dispatch(Request request)
	{
		return ice_dispatch(request, null);
	}

	public DispatchStatus __dispatch(Incoming in, Current current)
	{
		int pos = Arrays.binarySearch(__all, current.operation);
		if (pos < 0)
			throw new OperationNotExistException(current.id, current.facet, current.operation);
		switch (pos)
		{
		case 0: // '\0'
			return ___ice_id(this, in, current);

		case 1: // '\001'
			return ___ice_ids(this, in, current);

		case 2: // '\002'
			return ___ice_isA(this, in, current);

		case 3: // '\003'
			return ___ice_ping(this, in, current);
		}
		if (!$assertionsDisabled)
			throw new AssertionError();
		else
			throw new OperationNotExistException(current.id, current.facet, current.operation);
	}

	public DispatchStatus __collocDispatch(Direct request)
	{
		return request.run(this);
	}

	public void __write(BasicStream __os)
	{
		__os.writeTypeId(ice_staticId());
		__os.startWriteSlice();
		__os.writeSize(0);
		__os.endWriteSlice();
	}

	public void __read(BasicStream __is, boolean __rid)
	{
		if (__rid)
			__is.readTypeId();
		__is.startReadSlice();
		int sz = __is.readSize();
		if (sz != 0)
		{
			throw new MarshalException();
		} else
		{
			__is.endReadSlice();
			return;
		}
	}

	public void __write(OutputStream __outS)
	{
		__outS.writeTypeId(ice_staticId());
		__outS.startSlice();
		__outS.writeSize(0);
		__outS.endSlice();
	}

	public void __read(InputStream __inS, boolean __rid)
	{
		if (__rid)
			__inS.readTypeId();
		__inS.startSlice();
		int sz = __inS.readSize();
		if (sz != 0)
		{
			throw new MarshalException();
		} else
		{
			__inS.endSlice();
			return;
		}
	}

	private static String operationModeToString(OperationMode mode)
	{
		if (mode == OperationMode.Normal)
			return "::Ice::Normal";
		if (mode == OperationMode.Nonmutating)
			return "::Ice::Nonmutating";
		if (mode == OperationMode.Idempotent)
			return "::Ice::Idempotent";
		else
			return "???";
	}

	protected static void __checkMode(OperationMode expected, OperationMode received)
	{
		if (expected != received && (expected != OperationMode.Idempotent || received != OperationMode.Nonmutating))
		{
			MarshalException ex = new MarshalException();
			ex.reason = (new StringBuilder()).append("unexpected operation mode. expected = ").append(operationModeToString(expected)).append(" received = ").append(operationModeToString(received)).toString();
			throw ex;
		} else
		{
			return;
		}
	}

}
