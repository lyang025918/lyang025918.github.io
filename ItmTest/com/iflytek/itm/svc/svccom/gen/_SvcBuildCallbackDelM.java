// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _SvcBuildCallbackDelM.java

package com.iflytek.itm.svc.svccom.gen;

import Ice.*;
import IceInternal.*;
import java.util.Map;

// Referenced classes of package com.iflytek.itm.svc.svccom.gen:
//			SvcDocument, _SvcBuildCallbackDel, SvcDocumentHolder

public final class _SvcBuildCallbackDelM extends _ObjectDelM
	implements _SvcBuildCallbackDel
{

	public _SvcBuildCallbackDelM()
	{
	}

	public void event(String id, int evt, String msg, Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("event", OperationMode.Normal, __ctx);
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(id);
			__os.writeInt(evt);
			__os.writeString(msg);
		}
		catch (LocalException __ex)
		{
			__og.abort(__ex);
		}
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
		break MISSING_BLOCK_LABEL_157;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public int read(SvcDocumentHolder doc, Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("read", OperationMode.Normal, __ctx);
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
			doc.value = new SvcDocument();
			doc.value.__read(__is);
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
