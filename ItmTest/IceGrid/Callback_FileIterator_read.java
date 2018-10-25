// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Callback_FileIterator_read.java

package IceGrid;

import Ice.*;

// Referenced classes of package IceGrid:
//			FileIteratorPrx

public abstract class Callback_FileIterator_read extends TwowayCallback
{

	public Callback_FileIterator_read()
	{
	}

	public abstract void response(boolean flag, String as[]);

	public abstract void exception(UserException userexception);

	public final void __completed(AsyncResult __result)
	{
		FileIteratorPrx __proxy = (FileIteratorPrx)__result.getProxy();
		boolean __ret = false;
		StringSeqHolder lines = new StringSeqHolder();
		try
		{
			__ret = __proxy.end_read(lines, __result);
		}
		catch (UserException __ex)
		{
			exception(__ex);
			return;
		}
		catch (LocalException __ex)
		{
			exception(__ex);
			return;
		}
		response(__ret, lines.value);
	}
}
