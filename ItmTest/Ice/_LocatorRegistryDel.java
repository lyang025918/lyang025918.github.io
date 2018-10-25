// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _LocatorRegistryDel.java

package Ice;

import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package Ice:
//			_ObjectDel, AdapterAlreadyActiveException, AdapterNotFoundException, InvalidReplicaGroupIdException, 
//			ServerNotFoundException, ObjectPrx, ProcessPrx

public interface _LocatorRegistryDel
	extends _ObjectDel
{

	public abstract void setAdapterDirectProxy(String s, ObjectPrx objectprx, Map map)
		throws LocalExceptionWrapper, AdapterAlreadyActiveException, AdapterNotFoundException;

	public abstract void setReplicatedAdapterDirectProxy(String s, String s1, ObjectPrx objectprx, Map map)
		throws LocalExceptionWrapper, AdapterAlreadyActiveException, AdapterNotFoundException, InvalidReplicaGroupIdException;

	public abstract void setServerProcessProxy(String s, ProcessPrx processprx, Map map)
		throws LocalExceptionWrapper, ServerNotFoundException;
}
