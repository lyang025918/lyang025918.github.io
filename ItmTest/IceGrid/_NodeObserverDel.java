// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _NodeObserverDel.java

package IceGrid;

import Ice._ObjectDel;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package IceGrid:
//			NodeDynamicInfo, ServerDynamicInfo, AdapterDynamicInfo

public interface _NodeObserverDel
	extends _ObjectDel
{

	public abstract void nodeInit(NodeDynamicInfo anodedynamicinfo[], Map map)
		throws LocalExceptionWrapper;

	public abstract void nodeUp(NodeDynamicInfo nodedynamicinfo, Map map)
		throws LocalExceptionWrapper;

	public abstract void nodeDown(String s, Map map)
		throws LocalExceptionWrapper;

	public abstract void updateServer(String s, ServerDynamicInfo serverdynamicinfo, Map map)
		throws LocalExceptionWrapper;

	public abstract void updateAdapter(String s, AdapterDynamicInfo adapterdynamicinfo, Map map)
		throws LocalExceptionWrapper;
}
