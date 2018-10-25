// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _SessionOperations.java

package IceGrid;

import Ice.Current;
import Ice.Identity;

// Referenced classes of package IceGrid:
//			AllocationException, ObjectNotRegisteredException, AMD_Session_allocateObjectById, AMD_Session_allocateObjectByType

public interface _SessionOperations
	extends Glacier2._SessionOperations
{

	public abstract void keepAlive(Current current);

	public abstract void allocateObjectById_async(AMD_Session_allocateObjectById amd_session_allocateobjectbyid, Identity identity, Current current)
		throws AllocationException, ObjectNotRegisteredException;

	public abstract void allocateObjectByType_async(AMD_Session_allocateObjectByType amd_session_allocateobjectbytype, String s, Current current)
		throws AllocationException;

	public abstract void releaseObject(Identity identity, Current current)
		throws AllocationException, ObjectNotRegisteredException;

	public abstract void setAllocationTimeout(int i, Current current);
}
