// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _LocatorDel.java

package IceGrid;

import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package IceGrid:
//			RegistryPrx, QueryPrx

public interface _LocatorDel
	extends Ice._LocatorDel
{

	public abstract RegistryPrx getLocalRegistry(Map map)
		throws LocalExceptionWrapper;

	public abstract QueryPrx getLocalQuery(Map map)
		throws LocalExceptionWrapper;
}
