// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _AdapterObserverDel.java

package IceGrid;

import Ice._ObjectDel;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package IceGrid:
//			AdapterInfo

public interface _AdapterObserverDel
	extends _ObjectDel
{

	public abstract void adapterInit(AdapterInfo aadapterinfo[], Map map)
		throws LocalExceptionWrapper;

	public abstract void adapterAdded(AdapterInfo adapterinfo, Map map)
		throws LocalExceptionWrapper;

	public abstract void adapterUpdated(AdapterInfo adapterinfo, Map map)
		throws LocalExceptionWrapper;

	public abstract void adapterRemoved(String s, Map map)
		throws LocalExceptionWrapper;
}
