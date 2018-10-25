// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _AdapterObserverOperationsNC.java

package IceGrid;


// Referenced classes of package IceGrid:
//			AdapterInfo

public interface _AdapterObserverOperationsNC
{

	public abstract void adapterInit(AdapterInfo aadapterinfo[]);

	public abstract void adapterAdded(AdapterInfo adapterinfo);

	public abstract void adapterUpdated(AdapterInfo adapterinfo);

	public abstract void adapterRemoved(String s);
}
