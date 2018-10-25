// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _ObjectObserverDel.java

package IceGrid;

import Ice.Identity;
import Ice._ObjectDel;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package IceGrid:
//			ObjectInfo

public interface _ObjectObserverDel
	extends _ObjectDel
{

	public abstract void objectInit(ObjectInfo aobjectinfo[], Map map)
		throws LocalExceptionWrapper;

	public abstract void objectAdded(ObjectInfo objectinfo, Map map)
		throws LocalExceptionWrapper;

	public abstract void objectUpdated(ObjectInfo objectinfo, Map map)
		throws LocalExceptionWrapper;

	public abstract void objectRemoved(Identity identity, Map map)
		throws LocalExceptionWrapper;
}
