// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _LocatorDel.java

package Ice;

import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package Ice:
//			_ObjectDel, ObjectNotFoundException, AdapterNotFoundException, Identity, 
//			ObjectPrx, LocatorRegistryPrx

public interface _LocatorDel
	extends _ObjectDel
{

	public abstract ObjectPrx findObjectById(Identity identity, Map map)
		throws LocalExceptionWrapper, ObjectNotFoundException;

	public abstract ObjectPrx findAdapterById(String s, Map map)
		throws LocalExceptionWrapper, AdapterNotFoundException;

	public abstract LocatorRegistryPrx getRegistry(Map map)
		throws LocalExceptionWrapper;
}
