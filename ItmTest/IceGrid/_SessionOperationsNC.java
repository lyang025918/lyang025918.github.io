// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _SessionOperationsNC.java

package IceGrid;

import Ice.Identity;

// Referenced classes of package IceGrid:
//			AllocationException, ObjectNotRegisteredException, AMD_Session_allocateObjectById, AMD_Session_allocateObjectByType

public interface _SessionOperationsNC
	extends Glacier2._SessionOperationsNC
{

	public abstract void keepAlive();

	public abstract void allocateObjectById_async(AMD_Session_allocateObjectById amd_session_allocateobjectbyid, Identity identity)
		throws AllocationException, ObjectNotRegisteredException;

	public abstract void allocateObjectByType_async(AMD_Session_allocateObjectByType amd_session_allocateobjectbytype, String s)
		throws AllocationException;

	public abstract void releaseObject(Identity identity)
		throws AllocationException, ObjectNotRegisteredException;

	public abstract void setAllocationTimeout(int i);
}
