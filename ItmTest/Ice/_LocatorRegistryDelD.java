// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _LocatorRegistryDelD.java

package Ice;

import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package Ice:
//			_ObjectDelD, CollocationOptimizationException, _LocatorRegistryDel, AdapterAlreadyActiveException, 
//			AdapterNotFoundException, InvalidReplicaGroupIdException, ServerNotFoundException, ObjectPrx, 
//			ProcessPrx

public final class _LocatorRegistryDelD extends _ObjectDelD
	implements _LocatorRegistryDel
{

	public _LocatorRegistryDelD()
	{
	}

	public void setAdapterDirectProxy(String id, ObjectPrx proxy, Map __ctx)
		throws LocalExceptionWrapper, AdapterAlreadyActiveException, AdapterNotFoundException
	{
		throw new CollocationOptimizationException();
	}

	public void setReplicatedAdapterDirectProxy(String adapterId, String replicaGroupId, ObjectPrx p, Map __ctx)
		throws LocalExceptionWrapper, AdapterAlreadyActiveException, AdapterNotFoundException, InvalidReplicaGroupIdException
	{
		throw new CollocationOptimizationException();
	}

	public void setServerProcessProxy(String id, ProcessPrx proxy, Map __ctx)
		throws LocalExceptionWrapper, ServerNotFoundException
	{
		throw new CollocationOptimizationException();
	}
}
