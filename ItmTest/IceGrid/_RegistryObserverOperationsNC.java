// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _RegistryObserverOperationsNC.java

package IceGrid;


// Referenced classes of package IceGrid:
//			RegistryInfo

public interface _RegistryObserverOperationsNC
{

	public abstract void registryInit(RegistryInfo aregistryinfo[]);

	public abstract void registryUp(RegistryInfo registryinfo);

	public abstract void registryDown(String s);
}
