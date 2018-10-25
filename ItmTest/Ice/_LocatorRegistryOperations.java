// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _LocatorRegistryOperations.java

package Ice;


// Referenced classes of package Ice:
//			AdapterAlreadyActiveException, AdapterNotFoundException, InvalidReplicaGroupIdException, ServerNotFoundException, 
//			AMD_LocatorRegistry_setAdapterDirectProxy, ObjectPrx, Current, AMD_LocatorRegistry_setReplicatedAdapterDirectProxy, 
//			AMD_LocatorRegistry_setServerProcessProxy, ProcessPrx

public interface _LocatorRegistryOperations
{

	public abstract void setAdapterDirectProxy_async(AMD_LocatorRegistry_setAdapterDirectProxy amd_locatorregistry_setadapterdirectproxy, String s, ObjectPrx objectprx, Current current)
		throws AdapterAlreadyActiveException, AdapterNotFoundException;

	public abstract void setReplicatedAdapterDirectProxy_async(AMD_LocatorRegistry_setReplicatedAdapterDirectProxy amd_locatorregistry_setreplicatedadapterdirectproxy, String s, String s1, ObjectPrx objectprx, Current current)
		throws AdapterAlreadyActiveException, AdapterNotFoundException, InvalidReplicaGroupIdException;

	public abstract void setServerProcessProxy_async(AMD_LocatorRegistry_setServerProcessProxy amd_locatorregistry_setserverprocessproxy, String s, ProcessPrx processprx, Current current)
		throws ServerNotFoundException;
}
