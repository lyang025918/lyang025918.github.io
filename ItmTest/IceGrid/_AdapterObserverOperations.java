// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _AdapterObserverOperations.java

package IceGrid;

import Ice.Current;

// Referenced classes of package IceGrid:
//			AdapterInfo

public interface _AdapterObserverOperations
{

	public abstract void adapterInit(AdapterInfo aadapterinfo[], Current current);

	public abstract void adapterAdded(AdapterInfo adapterinfo, Current current);

	public abstract void adapterUpdated(AdapterInfo adapterinfo, Current current);

	public abstract void adapterRemoved(String s, Current current);
}
