// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _SessionDel.java

package IceGrid;

import Ice.Identity;
import Ice.ObjectPrx;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package IceGrid:
//			AllocationException, ObjectNotRegisteredException

public interface _SessionDel
	extends Glacier2._SessionDel
{

	public abstract void keepAlive(Map map)
		throws LocalExceptionWrapper;

	public abstract ObjectPrx allocateObjectById(Identity identity, Map map)
		throws LocalExceptionWrapper, AllocationException, ObjectNotRegisteredException;

	public abstract ObjectPrx allocateObjectByType(String s, Map map)
		throws LocalExceptionWrapper, AllocationException;

	public abstract void releaseObject(Identity identity, Map map)
		throws LocalExceptionWrapper, AllocationException, ObjectNotRegisteredException;

	public abstract void setAllocationTimeout(int i, Map map)
		throws LocalExceptionWrapper;
}
