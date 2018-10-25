// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _SvcSearchResultDelM.java

package com.iflytek.itm.svc.svccom.gen;

import Ice.*;
import IceInternal.*;
import java.util.*;

// Referenced classes of package com.iflytek.itm.svc.svccom.gen:
//			SvcDocument, SvcGroup, _SvcSearchResultDel

public final class _SvcSearchResultDelM extends _ObjectDelM
	implements _SvcSearchResultDel
{

	public _SvcSearchResultDelM()
	{
	}

	public int close(Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("close", OperationMode.Normal, __ctx);
		boolean __ok = __og.invoke();
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

	public int docs(Holder svcdocs, Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("docs", OperationMode.Normal, __ctx);
		boolean __ok = __og.invoke();
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
			svcdocs.value = new ArrayList();
			int __len0 = __is.readAndCheckSeqSize(5);
			for (int __i0 = 0; __i0 < __len0; __i0++)
			{
				SvcDocument __elem = new SvcDocument();
				__elem.__read(__is);
				((List)svcdocs.value).add(__elem);
			}

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

	public int getGroupTotalHits(Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("getGroupTotalHits", OperationMode.Normal, __ctx);
		boolean __ok = __og.invoke();
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

	public int getTotalHits(Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("getTotalHits", OperationMode.Normal, __ctx);
		boolean __ok = __og.invoke();
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

	public int groups(Holder svcgroups, Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("groups", OperationMode.Normal, __ctx);
		boolean __ok = __og.invoke();
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
			svcgroups.value = new ArrayList();
			int __len0 = __is.readAndCheckSeqSize(5);
			for (int __i0 = 0; __i0 < __len0; __i0++)
			{
				SvcGroup __elem = new SvcGroup();
				__elem.__read(__is);
				((List)svcgroups.value).add(__elem);
			}

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
}
