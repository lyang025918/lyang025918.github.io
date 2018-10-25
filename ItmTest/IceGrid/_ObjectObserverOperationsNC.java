// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _ObjectObserverOperationsNC.java

package IceGrid;

import Ice.Identity;

// Referenced classes of package IceGrid:
//			ObjectInfo

public interface _ObjectObserverOperationsNC
{

	public abstract void objectInit(ObjectInfo aobjectinfo[]);

	public abstract void objectAdded(ObjectInfo objectinfo);

	public abstract void objectUpdated(ObjectInfo objectinfo);

	public abstract void objectRemoved(Identity identity);
}
