// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _NodeObserverOperations.java

package IceGrid;

import Ice.Current;

// Referenced classes of package IceGrid:
//			NodeDynamicInfo, ServerDynamicInfo, AdapterDynamicInfo

public interface _NodeObserverOperations
{

	public abstract void nodeInit(NodeDynamicInfo anodedynamicinfo[], Current current);

	public abstract void nodeUp(NodeDynamicInfo nodedynamicinfo, Current current);

	public abstract void nodeDown(String s, Current current);

	public abstract void updateServer(String s, ServerDynamicInfo serverdynamicinfo, Current current);

	public abstract void updateAdapter(String s, AdapterDynamicInfo adapterdynamicinfo, Current current);
}
