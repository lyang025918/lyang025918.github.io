// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RouterManager.java

package IceInternal;

import Ice.RouterPrx;
import Ice.RouterPrxHelper;
import java.util.*;

// Referenced classes of package IceInternal:
//			RouterInfo

public final class RouterManager
{

	private HashMap _table;

	RouterManager()
	{
		_table = new HashMap();
	}

	synchronized void destroy()
	{
		RouterInfo info;
		for (Iterator i$ = _table.values().iterator(); i$.hasNext(); info.destroy())
			info = (RouterInfo)i$.next();

		_table.clear();
	}

	public RouterInfo get(RouterPrx rtr)
	{
		if (rtr == null)
			return null;
		RouterPrx router = RouterPrxHelper.uncheckedCast(rtr.ice_router(null));
		RouterManager routermanager = this;
		JVM INSTR monitorenter ;
		RouterInfo info;
		info = (RouterInfo)_table.get(router);
		if (info == null)
		{
			info = new RouterInfo(router);
			_table.put(router, info);
		}
		return info;
		Exception exception;
		exception;
		throw exception;
	}

	public RouterInfo erase(RouterPrx rtr)
	{
		RouterInfo info = null;
		if (rtr != null)
		{
			RouterPrx router = RouterPrxHelper.uncheckedCast(rtr.ice_router(null));
			synchronized (this)
			{
				info = (RouterInfo)_table.remove(router);
			}
		}
		return info;
	}
}
