// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _ObjectObserverOperations.java

package IceGrid;

import Ice.Current;
import Ice.Identity;

// Referenced classes of package IceGrid:
//			ObjectInfo

public interface _ObjectObserverOperations
{

	public abstract void objectInit(ObjectInfo aobjectinfo[], Current current);

	public abstract void objectAdded(ObjectInfo objectinfo, Current current);

	public abstract void objectUpdated(ObjectInfo objectinfo, Current current);

	public abstract void objectRemoved(Identity identity, Current current);
}
