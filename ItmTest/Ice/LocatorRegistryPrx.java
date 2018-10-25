// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LocatorRegistryPrx.java

package Ice;

import java.util.Map;

// Referenced classes of package Ice:
//			ObjectPrx, AdapterAlreadyActiveException, AdapterNotFoundException, InvalidReplicaGroupIdException, 
//			ServerNotFoundException, AsyncResult, Callback, Callback_LocatorRegistry_setAdapterDirectProxy, 
//			AMI_LocatorRegistry_setAdapterDirectProxy, Callback_LocatorRegistry_setReplicatedAdapterDirectProxy, AMI_LocatorRegistry_setReplicatedAdapterDirectProxy, ProcessPrx, 
//			Callback_LocatorRegistry_setServerProcessProxy

public interface LocatorRegistryPrx
	extends ObjectPrx
{

	public abstract void setAdapterDirectProxy(String s, ObjectPrx objectprx)
		throws AdapterAlreadyActiveException, AdapterNotFoundException;

	public abstract void setAdapterDirectProxy(String s, ObjectPrx objectprx, Map map)
		throws AdapterAlreadyActiveException, AdapterNotFoundException;

	public abstract AsyncResult begin_setAdapterDirectProxy(String s, ObjectPrx objectprx);

	public abstract AsyncResult begin_setAdapterDirectProxy(String s, ObjectPrx objectprx, Map map);

	public abstract AsyncResult begin_setAdapterDirectProxy(String s, ObjectPrx objectprx, Callback callback);

	public abstract AsyncResult begin_setAdapterDirectProxy(String s, ObjectPrx objectprx, Map map, Callback callback);

	public abstract AsyncResult begin_setAdapterDirectProxy(String s, ObjectPrx objectprx, Callback_LocatorRegistry_setAdapterDirectProxy callback_locatorregistry_setadapterdirectproxy);

	public abstract AsyncResult begin_setAdapterDirectProxy(String s, ObjectPrx objectprx, Map map, Callback_LocatorRegistry_setAdapterDirectProxy callback_locatorregistry_setadapterdirectproxy);

	public abstract void end_setAdapterDirectProxy(AsyncResult asyncresult)
		throws AdapterAlreadyActiveException, AdapterNotFoundException;

	public abstract boolean setAdapterDirectProxy_async(AMI_LocatorRegistry_setAdapterDirectProxy ami_locatorregistry_setadapterdirectproxy, String s, ObjectPrx objectprx);

	public abstract boolean setAdapterDirectProxy_async(AMI_LocatorRegistry_setAdapterDirectProxy ami_locatorregistry_setadapterdirectproxy, String s, ObjectPrx objectprx, Map map);

	public abstract void setReplicatedAdapterDirectProxy(String s, String s1, ObjectPrx objectprx)
		throws AdapterAlreadyActiveException, AdapterNotFoundException, InvalidReplicaGroupIdException;

	public abstract void setReplicatedAdapterDirectProxy(String s, String s1, ObjectPrx objectprx, Map map)
		throws AdapterAlreadyActiveException, AdapterNotFoundException, InvalidReplicaGroupIdException;

	public abstract AsyncResult begin_setReplicatedAdapterDirectProxy(String s, String s1, ObjectPrx objectprx);

	public abstract AsyncResult begin_setReplicatedAdapterDirectProxy(String s, String s1, ObjectPrx objectprx, Map map);

	public abstract AsyncResult begin_setReplicatedAdapterDirectProxy(String s, String s1, ObjectPrx objectprx, Callback callback);

	public abstract AsyncResult begin_setReplicatedAdapterDirectProxy(String s, String s1, ObjectPrx objectprx, Map map, Callback callback);

	public abstract AsyncResult begin_setReplicatedAdapterDirectProxy(String s, String s1, ObjectPrx objectprx, Callback_LocatorRegistry_setReplicatedAdapterDirectProxy callback_locatorregistry_setreplicatedadapterdirectproxy);

	public abstract AsyncResult begin_setReplicatedAdapterDirectProxy(String s, String s1, ObjectPrx objectprx, Map map, Callback_LocatorRegistry_setReplicatedAdapterDirectProxy callback_locatorregistry_setreplicatedadapterdirectproxy);

	public abstract void end_setReplicatedAdapterDirectProxy(AsyncResult asyncresult)
		throws AdapterAlreadyActiveException, AdapterNotFoundException, InvalidReplicaGroupIdException;

	public abstract boolean setReplicatedAdapterDirectProxy_async(AMI_LocatorRegistry_setReplicatedAdapterDirectProxy ami_locatorregistry_setreplicatedadapterdirectproxy, String s, String s1, ObjectPrx objectprx);

	public abstract boolean setReplicatedAdapterDirectProxy_async(AMI_LocatorRegistry_setReplicatedAdapterDirectProxy ami_locatorregistry_setreplicatedadapterdirectproxy, String s, String s1, ObjectPrx objectprx, Map map);

	public abstract void setServerProcessProxy(String s, ProcessPrx processprx)
		throws ServerNotFoundException;

	public abstract void setServerProcessProxy(String s, ProcessPrx processprx, Map map)
		throws ServerNotFoundException;

	public abstract AsyncResult begin_setServerProcessProxy(String s, ProcessPrx processprx);

	public abstract AsyncResult begin_setServerProcessProxy(String s, ProcessPrx processprx, Map map);

	public abstract AsyncResult begin_setServerProcessProxy(String s, ProcessPrx processprx, Callback callback);

	public abstract AsyncResult begin_setServerProcessProxy(String s, ProcessPrx processprx, Map map, Callback callback);

	public abstract AsyncResult begin_setServerProcessProxy(String s, ProcessPrx processprx, Callback_LocatorRegistry_setServerProcessProxy callback_locatorregistry_setserverprocessproxy);

	public abstract AsyncResult begin_setServerProcessProxy(String s, ProcessPrx processprx, Map map, Callback_LocatorRegistry_setServerProcessProxy callback_locatorregistry_setserverprocessproxy);

	public abstract void end_setServerProcessProxy(AsyncResult asyncresult)
		throws ServerNotFoundException;
}
