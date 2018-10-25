// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LocatorManager.java

package IceInternal;

import Ice.*;
import java.util.*;

// Referenced classes of package IceInternal:
//			LocatorInfo, LocatorTable

public final class LocatorManager
{

	private final boolean _background;
	private HashMap _table;
	private HashMap _locatorTables;

	LocatorManager(Properties properties)
	{
		_table = new HashMap();
		_locatorTables = new HashMap();
		_background = properties.getPropertyAsInt("Ice.BackgroundLocatorCacheUpdates") > 0;
	}

	synchronized void destroy()
	{
		LocatorInfo info;
		for (Iterator i$ = _table.values().iterator(); i$.hasNext(); info.destroy())
			info = (LocatorInfo)i$.next();

		_table.clear();
		_locatorTables.clear();
	}

	public LocatorInfo get(LocatorPrx loc)
	{
		if (loc == null)
			return null;
		LocatorPrx locator = LocatorPrxHelper.uncheckedCast(loc.ice_locator(null));
		LocatorManager locatormanager = this;
		JVM INSTR monitorenter ;
		LocatorInfo info;
		info = (LocatorInfo)_table.get(locator);
		if (info == null)
		{
			LocatorTable table = (LocatorTable)_locatorTables.get(locator.ice_getIdentity());
			if (table == null)
			{
				table = new LocatorTable();
				_locatorTables.put(locator.ice_getIdentity(), table);
			}
			info = new LocatorInfo(locator, table, _background);
			_table.put(locator, info);
		}
		return info;
		Exception exception;
		exception;
		throw exception;
	}
}
