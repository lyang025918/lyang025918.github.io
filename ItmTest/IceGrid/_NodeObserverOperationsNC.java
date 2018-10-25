// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _NodeObserverOperationsNC.java

package IceGrid;


// Referenced classes of package IceGrid:
//			NodeDynamicInfo, ServerDynamicInfo, AdapterDynamicInfo

public interface _NodeObserverOperationsNC
{

	public abstract void nodeInit(NodeDynamicInfo anodedynamicinfo[]);

	public abstract void nodeUp(NodeDynamicInfo nodedynamicinfo);

	public abstract void nodeDown(String s);

	public abstract void updateServer(String s, ServerDynamicInfo serverdynamicinfo);

	public abstract void updateAdapter(String s, AdapterDynamicInfo adapterdynamicinfo);
}
