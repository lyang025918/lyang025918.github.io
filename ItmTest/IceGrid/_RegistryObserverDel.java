// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _RegistryObserverDel.java

package IceGrid;

import Ice._ObjectDel;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package IceGrid:
//			RegistryInfo

public interface _RegistryObserverDel
	extends _ObjectDel
{

	public abstract void registryInit(RegistryInfo aregistryinfo[], Map map)
		throws LocalExceptionWrapper;

	public abstract void registryUp(RegistryInfo registryinfo, Map map)
		throws LocalExceptionWrapper;

	public abstract void registryDown(String s, Map map)
		throws LocalExceptionWrapper;
}
