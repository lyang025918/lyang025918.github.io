// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _FileIteratorDelM.java

package IceGrid;

import Ice.*;
import IceInternal.*;
import java.util.Map;

// Referenced classes of package IceGrid:
//			FileNotAvailableException, _FileIteratorDel

public final class _FileIteratorDelM extends _ObjectDelM
	implements _FileIteratorDel
{

	public _FileIteratorDelM()
	{
	}

	public void destroy(Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("destroy", OperationMode.Normal, __ctx);
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

	public boolean read(int size, StringSeqHolder lines, Map __ctx)
		throws LocalExceptionWrapper, FileNotAvailableException
	{
		Outgoing __og = __handler.getOutgoing("read", OperationMode.Normal, __ctx);
		boolean __ok;
		try
		{
			BasicStream __os = __og.os();
			__os.writeInt(size);
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
				catch (FileNotAvailableException __ex)
				{
					throw __ex;
				}
				catch (UserException __ex)
				{
					throw new UnknownUserException(__ex.ice_name(), __ex);
				}
			BasicStream __is = __og.is();
			__is.startReadEncaps();
			lines.value = StringSeqHelper.read(__is);
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
}
