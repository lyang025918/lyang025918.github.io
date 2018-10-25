// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _LocatorOperationsNC.java

package IceGrid;


// Referenced classes of package IceGrid:
//			RegistryPrx, QueryPrx

public interface _LocatorOperationsNC
	extends Ice._LocatorOperationsNC
{

	public abstract RegistryPrx getLocalRegistry();

	public abstract QueryPrx getLocalQuery();
}
