// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _ItmSvcDelM.java

package com.iflytek.itm.svc.svccom.gen;

import Ice.*;
import IceInternal.*;
import java.util.Map;

// Referenced classes of package com.iflytek.itm.svc.svccom.gen:
//			_ItmSvcDel, SvcBuildCallbackPrx, SvcBuildCallbackPrxHelper, SvcSearchResultPrxHelper, 
//			SvcSearchResultPrx

public final class _ItmSvcDelM extends _ObjectDelM
	implements _ItmSvcDel
{

	public _ItmSvcDelM()
	{
	}

	public int build(String indexPath, String params, SvcBuildCallbackPrx builder, Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("build", OperationMode.Normal, __ctx);
		boolean __ok;
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(indexPath);
			__os.writeString(params);
			SvcBuildCallbackPrxHelper.__write(__os, builder);
		}
		catch (LocalException __ex)
		{
			__og.abort(__ex);
		}
		__ok = __og.invoke();
		int i;
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
			int __ret = __is.readInt();
			__is.endReadEncaps();
			i = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return i;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public int maintain(String indexPath, String action, String params, Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("maintain", OperationMode.Normal, __ctx);
		boolean __ok;
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(indexPath);
			__os.writeString(action);
			__os.writeString(params);
		}
		catch (LocalException __ex)
		{
			__og.abort(__ex);
		}
		__ok = __og.invoke();
		int i;
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
			int __ret = __is.readInt();
			__is.endReadEncaps();
			i = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return i;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public int mining(String indexPath, String type, String params, StringHolder buffer, Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("mining", OperationMode.Normal, __ctx);
		boolean __ok;
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(indexPath);
			__os.writeString(type);
			__os.writeString(params);
		}
		catch (LocalException __ex)
		{
			__og.abort(__ex);
		}
		__ok = __og.invoke();
		int i;
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
			buffer.value = __is.readString();
			int __ret = __is.readInt();
			__is.endReadEncaps();
			i = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return i;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public SvcSearchResultPrx search(String indexPath, String querySyntax, String params, IntHolder errcode, Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("search", OperationMode.Normal, __ctx);
		boolean __ok;
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(indexPath);
			__os.writeString(querySyntax);
			__os.writeString(params);
		}
		catch (LocalException __ex)
		{
			__og.abort(__ex);
		}
		__ok = __og.invoke();
		SvcSearchResultPrx svcsearchresultprx;
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
			errcode.value = __is.readInt();
			SvcSearchResultPrx __ret = SvcSearchResultPrxHelper.__read(__is);
			__is.endReadEncaps();
			svcsearchresultprx = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return svcsearchresultprx;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}
}
