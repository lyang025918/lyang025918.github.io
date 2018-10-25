// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LocatorPrx.java

package IceGrid;

import Ice.AsyncResult;
import Ice.Callback;
import java.util.Map;

// Referenced classes of package IceGrid:
//			RegistryPrx, Callback_Locator_getLocalRegistry, QueryPrx, Callback_Locator_getLocalQuery

public interface LocatorPrx
	extends Ice.LocatorPrx
{

	public abstract RegistryPrx getLocalRegistry();

	public abstract RegistryPrx getLocalRegistry(Map map);

	public abstract AsyncResult begin_getLocalRegistry();

	public abstract AsyncResult begin_getLocalRegistry(Map map);

	public abstract AsyncResult begin_getLocalRegistry(Callback callback);

	public abstract AsyncResult begin_getLocalRegistry(Map map, Callback callback);

	public abstract AsyncResult begin_getLocalRegistry(Callback_Locator_getLocalRegistry callback_locator_getlocalregistry);

	public abstract AsyncResult begin_getLocalRegistry(Map map, Callback_Locator_getLocalRegistry callback_locator_getlocalregistry);

	public abstract RegistryPrx end_getLocalRegistry(AsyncResult asyncresult);

	public abstract QueryPrx getLocalQuery();

	public abstract QueryPrx getLocalQuery(Map map);

	public abstract AsyncResult begin_getLocalQuery();

	public abstract AsyncResult begin_getLocalQuery(Map map);

	public abstract AsyncResult begin_getLocalQuery(Callback callback);

	public abstract AsyncResult begin_getLocalQuery(Map map, Callback callback);

	public abstract AsyncResult begin_getLocalQuery(Callback_Locator_getLocalQuery callback_locator_getlocalquery);

	public abstract AsyncResult begin_getLocalQuery(Map map, Callback_Locator_getLocalQuery callback_locator_getlocalquery);

	public abstract QueryPrx end_getLocalQuery(AsyncResult asyncresult);
}
