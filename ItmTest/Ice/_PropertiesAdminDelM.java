// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _PropertiesAdminDelM.java

package Ice;

import IceInternal.BasicStream;
import IceInternal.LocalExceptionWrapper;
import IceInternal.Outgoing;
import IceInternal.RequestHandler;
import java.util.Map;
import java.util.TreeMap;

// Referenced classes of package Ice:
//			_ObjectDelM, LocalException, UserException, UnknownUserException, 
//			_PropertiesAdminDel, OperationMode

public final class _PropertiesAdminDelM extends _ObjectDelM
	implements _PropertiesAdminDel
{

	public _PropertiesAdminDelM()
	{
	}

	public Map getPropertiesForPrefix(String prefix, Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("getPropertiesForPrefix", OperationMode.Normal, __ctx);
		boolean __ok;
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(prefix);
		}
		catch (LocalException __ex)
		{
			__og.abort(__ex);
		}
		__ok = __og.invoke();
		Map map;
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
			Map __ret = new TreeMap();
			int __sz0 = __is.readSize();
			for (int __i0 = 0; __i0 < __sz0; __i0++)
			{
				String __key = __is.readString();
				String __value = __is.readString();
				__ret.put(__key, __value);
			}

			__is.endReadEncaps();
			map = __ret;
		}
		catch (LocalException __ex)
		{
			throw new LocalExceptionWrapper(__ex, false);
		}
		__handler.reclaimOutgoing(__og);
		return map;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public String getProperty(String key, Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("getProperty", OperationMode.Normal, __ctx);
		boolean __ok;
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(key);
		}
		catch (LocalException __ex)
		{
			__og.abort(__ex);
		}
		__ok = __og.invoke();
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
}
