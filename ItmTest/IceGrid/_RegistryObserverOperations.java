// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _RegistryObserverOperations.java

package IceGrid;

import Ice.Current;

// Referenced classes of package IceGrid:
//			RegistryInfo

public interface _RegistryObserverOperations
{

	public abstract void registryInit(RegistryInfo aregistryinfo[], Current current);

	public abstract void registryUp(RegistryInfo registryinfo, Current current);

	public abstract void registryDown(String s, Current current);
}
