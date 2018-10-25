// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _FileParserDelM.java

package IceGrid;

import Ice.*;
import IceInternal.*;
import java.util.Map;

// Referenced classes of package IceGrid:
//			ParseException, ApplicationDescriptor, _FileParserDel, AdminPrxHelper, 
//			AdminPrx

public final class _FileParserDelM extends _ObjectDelM
	implements _FileParserDel
{

	public _FileParserDelM()
	{
	}

	public ApplicationDescriptor parse(String xmlFile, AdminPrx adminProxy, Map __ctx)
		throws LocalExceptionWrapper, ParseException
	{
		Outgoing __og = __handler.getOutgoing("parse", OperationMode.Idempotent, __ctx);
		boolean __ok;
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(xmlFile);
			AdminPrxHelper.__write(__os, adminProxy);
		}
		catch (LocalException __ex)
		{
			__og.abort(__ex);
		}
		__ok = __og.invoke();
		ApplicationDescriptor applicationdescriptor;
		try
		{
			if (!__ok)
				try
				{
					__og.throwUserException();
				}
				catch (ParseException __ex)
				{
					throw __ex;
				}
				catch (UserException __ex)
				{
					throw new UnknownUserException(__ex.ice_name(), __ex);
				}
			BasicStream __is = __og.is();
			__is.startReadEncaps();
			ApplicationDescriptor __ret = new ApplicationDescriptor();
			__ret.__read(__is);
			__is.readPendingObjects();
			__is.endReadEncaps();
			applicationdescriptor = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return applicationdescriptor;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}
}
