// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Object.java

package Ice;

import IceInternal.BasicStream;
import IceInternal.Direct;
import IceInternal.Incoming;

// Referenced classes of package Ice:
//			Current, Request, DispatchInterceptorAsyncCallback, DispatchStatus, 
//			OutputStream, InputStream

public interface Object
{

	public abstract Object clone()
		throws CloneNotSupportedException;

	/**
	 * @deprecated Method ice_hash is deprecated
	 */

	public abstract int ice_hash();

	public abstract boolean ice_isA(String s);

	public abstract boolean ice_isA(String s, Current current);

	public abstract void ice_ping();

	public abstract void ice_ping(Current current);

	public abstract String[] ice_ids();

	public abstract String[] ice_ids(Current current);

	public abstract String ice_id();

	public abstract String ice_id(Current current);

	public abstract int ice_operationAttributes(String s);

	public abstract void ice_preMarshal();

	public abstract void ice_postUnmarshal();

	public abstract DispatchStatus ice_dispatch(Request request, DispatchInterceptorAsyncCallback dispatchinterceptorasynccallback);

	public abstract DispatchStatus ice_dispatch(Request request);

	public abstract DispatchStatus __dispatch(Incoming incoming, Current current);

	public abstract DispatchStatus __collocDispatch(Direct direct);

	public abstract void __write(BasicStream basicstream);

	public abstract void __read(BasicStream basicstream, boolean flag);

	public abstract void __write(OutputStream outputstream);

	public abstract void __read(InputStream inputstream, boolean flag);
}
