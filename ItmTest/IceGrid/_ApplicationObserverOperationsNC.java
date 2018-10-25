// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _ApplicationObserverOperationsNC.java

package IceGrid;

import java.util.List;

// Referenced classes of package IceGrid:
//			ApplicationInfo, ApplicationUpdateInfo

public interface _ApplicationObserverOperationsNC
{

	public abstract void applicationInit(int i, List list);

	public abstract void applicationAdded(int i, ApplicationInfo applicationinfo);

	public abstract void applicationRemoved(int i, String s);

	public abstract void applicationUpdated(int i, ApplicationUpdateInfo applicationupdateinfo);
}
