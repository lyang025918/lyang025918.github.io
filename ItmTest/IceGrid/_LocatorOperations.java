// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _LocatorOperations.java

package IceGrid;

import Ice.Current;

// Referenced classes of package IceGrid:
//			RegistryPrx, QueryPrx

public interface _LocatorOperations
	extends Ice._LocatorOperations
{

	public abstract RegistryPrx getLocalRegistry(Current current);

	public abstract QueryPrx getLocalQuery(Current current);
}
