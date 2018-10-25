// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LocatorPrx.java

package Ice;

import java.util.Map;

// Referenced classes of package Ice:
//			ObjectPrx, ObjectNotFoundException, AdapterNotFoundException, Identity, 
//			AsyncResult, Callback, Callback_Locator_findObjectById, AMI_Locator_findObjectById, 
//			Callback_Locator_findAdapterById, AMI_Locator_findAdapterById, LocatorRegistryPrx, Callback_Locator_getRegistry

public interface LocatorPrx
	extends ObjectPrx
{

	public abstract ObjectPrx findObjectById(Identity identity)
		throws ObjectNotFoundException;

	public abstract ObjectPrx findObjectById(Identity identity, Map map)
		throws ObjectNotFoundException;

	public abstract AsyncResult begin_findObjectById(Identity identity);

	public abstract AsyncResult begin_findObjectById(Identity identity, Map map);

	public abstract AsyncResult begin_findObjectById(Identity identity, Callback callback);

	public abstract AsyncResult begin_findObjectById(Identity identity, Map map, Callback callback);

	public abstract AsyncResult begin_findObjectById(Identity identity, Callback_Locator_findObjectById callback_locator_findobjectbyid);

	public abstract AsyncResult begin_findObjectById(Identity identity, Map map, Callback_Locator_findObjectById callback_locator_findobjectbyid);

	public abstract ObjectPrx end_findObjectById(AsyncResult asyncresult)
		throws ObjectNotFoundException;

	public abstract boolean findObjectById_async(AMI_Locator_findObjectById ami_locator_findobjectbyid, Identity identity);

	public abstract boolean findObjectById_async(AMI_Locator_findObjectById ami_locator_findobjectbyid, Identity identity, Map map);

	public abstract ObjectPrx findAdapterById(String s)
		throws AdapterNotFoundException;

	public abstract ObjectPrx findAdapterById(String s, Map map)
		throws AdapterNotFoundException;

	public abstract AsyncResult begin_findAdapterById(String s);

	public abstract AsyncResult begin_findAdapterById(String s, Map map);

	public abstract AsyncResult begin_findAdapterById(String s, Callback callback);

	public abstract AsyncResult begin_findAdapterById(String s, Map map, Callback callback);

	public abstract AsyncResult begin_findAdapterById(String s, Callback_Locator_findAdapterById callback_locator_findadapterbyid);

	public abstract AsyncResult begin_findAdapterById(String s, Map map, Callback_Locator_findAdapterById callback_locator_findadapterbyid);

	public abstract ObjectPrx end_findAdapterById(AsyncResult asyncresult)
		throws AdapterNotFoundException;

	public abstract boolean findAdapterById_async(AMI_Locator_findAdapterById ami_locator_findadapterbyid, String s);

	public abstract boolean findAdapterById_async(AMI_Locator_findAdapterById ami_locator_findadapterbyid, String s, Map map);

	public abstract LocatorRegistryPrx getRegistry();

	public abstract LocatorRegistryPrx getRegistry(Map map);

	public abstract AsyncResult begin_getRegistry();

	public abstract AsyncResult begin_getRegistry(Map map);

	public abstract AsyncResult begin_getRegistry(Callback callback);

	public abstract AsyncResult begin_getRegistry(Map map, Callback callback);

	public abstract AsyncResult begin_getRegistry(Callback_Locator_getRegistry callback_locator_getregistry);

	public abstract AsyncResult begin_getRegistry(Map map, Callback_Locator_getRegistry callback_locator_getregistry);

	public abstract LocatorRegistryPrx end_getRegistry(AsyncResult asyncresult);
}
