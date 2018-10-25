// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _LocatorOperations.java

package Ice;


// Referenced classes of package Ice:
//			ObjectNotFoundException, AdapterNotFoundException, AMD_Locator_findObjectById, Identity, 
//			Current, AMD_Locator_findAdapterById, LocatorRegistryPrx

public interface _LocatorOperations
{

	public abstract void findObjectById_async(AMD_Locator_findObjectById amd_locator_findobjectbyid, Identity identity, Current current)
		throws ObjectNotFoundException;

	public abstract void findAdapterById_async(AMD_Locator_findAdapterById amd_locator_findadapterbyid, String s, Current current)
		throws AdapterNotFoundException;

	public abstract LocatorRegistryPrx getRegistry(Current current);
}
