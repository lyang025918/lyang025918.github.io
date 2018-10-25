// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _ObjectDelM.java

package Ice;

import IceInternal.BasicStream;
import IceInternal.BatchOutgoing;
import IceInternal.ConnectRequestHandler;
import IceInternal.ConnectionRequestHandler;
import IceInternal.LocalExceptionWrapper;
import IceInternal.Outgoing;
import IceInternal.Reference;
import IceInternal.RequestHandler;
import java.util.Map;

// Referenced classes of package Ice:
//			LocalException, UserException, UnknownUserException, _ObjectDel, 
//			OperationMode, ByteSeqHolder, ObjectPrx

public class _ObjectDelM
	implements _ObjectDel
{

	protected RequestHandler __handler;
	static final boolean $assertionsDisabled = !Ice/_ObjectDelM.desiredAssertionStatus();

	public _ObjectDelM()
	{
	}

	public boolean ice_isA(String __id, Map __context)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("ice_isA", OperationMode.Nonmutating, __context);
		boolean __ok;
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(__id);
		}
		catch (LocalException __ex)
		{
			__og.abort(__ex);
		}
		__ok = __og.invoke();
		boolean flag;
		try
		{
			if (!__ok)
				try
				{
					__og.throwUserException();
				}
				catch (UserException __ex)
				{
					throw new UnknownUserException(__ex.ice_name(), __ex);
				}
			BasicStream __is = __og.is();
			__is.startReadEncaps();
			boolean __ret = __is.readBool();
			__is.endReadEncaps();
			flag = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return flag;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public void ice_ping(Map __context)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("ice_ping", OperationMode.Nonmutating, __context);
		boolean __ok = __og.invoke();
		if (!__og.is().isEmpty())
			try
			{
				if (!__ok)
					try
					{
						__og.throwUserException();
					}
					catch (UserException __ex)
					{
						throw new UnknownUserException(__ex.ice_name(), __ex);
					}
				__og.is().skipEmptyEncaps();
			}
			catch (LocalException __ex)
			{
				throw new LocalExceptionWrapper(__ex, false);
			}
		__handler.reclaimOutgoing(__og);
		break MISSING_BLOCK_LABEL_110;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public String[] ice_ids(Map __context)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("ice_ids", OperationMode.Nonmutating, __context);
		boolean __ok = __og.invoke();
		String as[];
		try
		{
			if (!__ok)
				try
				{
					__og.throwUserException();
				}
				catch (UserException __ex)
				{
					throw new UnknownUserException(__ex.ice_name(), __ex);
				}
			BasicStream __is = __og.is();
			__is.startReadEncaps();
			String __ret[] = __is.readStringSeq();
			__is.endReadEncaps();
			as = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return as;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public String ice_id(Map __context)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("ice_id", OperationMode.Nonmutating, __context);
		boolean __ok = __og.invoke();
		String s;
		try
		{
			if (!__ok)
				try
				{
					__og.throwUserException();
				}
				catch (UserException __ex)
				{
					throw new UnknownUserException(__ex.ice_name(), __ex);
				}
			BasicStream __is = __og.is();
			__is.startReadEncaps();
			String __ret = __is.readString();
			__is.endReadEncaps();
			s = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return s;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public boolean ice_invoke(String operation, OperationMode mode, byte inParams[], ByteSeqHolder outParams, Map __context)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing(operation, mode, __context);
		boolean flag;
		if (inParams != null)
			try
			{
				BasicStream __os = __og.os();
				__os.writeBlob(inParams);
			}
			catch (LocalException __ex)
			{
				__og.abort(__ex);
			}
		boolean ok = __og.invoke();
		if (__handler.getReference().getMode() == 0)
			try
			{
				BasicStream __is = __og.is();
				__is.startReadEncaps();
				int sz = __is.getReadEncapsSize();
				if (outParams != null)
					outParams.value = __is.readBlob(sz);
				__is.endReadEncaps();
			}
			catch (LocalException __ex)
			{
				throw new LocalExceptionWrapper(__ex, false);
			}
		flag = ok;
		__handler.reclaimOutgoing(__og);
		return flag;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public void ice_flushBatchRequests()
	{
		BatchOutgoing out = new BatchOutgoing(__handler);
		out.invoke();
	}

	public RequestHandler __getRequestHandler()
	{
		return __handler;
	}

	public void __setRequestHandler(RequestHandler handler)
	{
		__handler = handler;
	}

	final void __copyFrom(_ObjectDelM from)
	{
		if (!$assertionsDisabled && __handler != null)
		{
			throw new AssertionError();
		} else
		{
			__handler = from.__handler;
			return;
		}
	}

	public void setup(Reference ref, ObjectPrx proxy, boolean async)
	{
		if (!$assertionsDisabled && __handler != null)
			throw new AssertionError();
		if (async)
		{
			ConnectRequestHandler handler = new ConnectRequestHandler(ref, proxy, this);
			__handler = handler.connect();
		} else
		{
			__handler = new ConnectionRequestHandler(ref, proxy);
		}
	}

}
