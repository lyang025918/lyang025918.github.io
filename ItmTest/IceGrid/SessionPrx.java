// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SessionPrx.java

package IceGrid;

import Ice.AsyncResult;
import Ice.Callback;
import Ice.Identity;
import Ice.ObjectPrx;
import java.util.Map;

// Referenced classes of package IceGrid:
//			AllocationException, ObjectNotRegisteredException, Callback_Session_keepAlive, Callback_Session_allocateObjectById, 
//			AMI_Session_allocateObjectById, Callback_Session_allocateObjectByType, AMI_Session_allocateObjectByType, Callback_Session_releaseObject, 
//			Callback_Session_setAllocationTimeout

public interface SessionPrx
	extends Glacier2.SessionPrx
{

	public abstract void keepAlive();

	public abstract void keepAlive(Map map);

	public abstract AsyncResult begin_keepAlive();

	public abstract AsyncResult begin_keepAlive(Map map);

	public abstract AsyncResult begin_keepAlive(Callback callback);

	public abstract AsyncResult begin_keepAlive(Map map, Callback callback);

	public abstract AsyncResult begin_keepAlive(Callback_Session_keepAlive callback_session_keepalive);

	public abstract AsyncResult begin_keepAlive(Map map, Callback_Session_keepAlive callback_session_keepalive);

	public abstract void end_keepAlive(AsyncResult asyncresult);

	public abstract ObjectPrx allocateObjectById(Identity identity)
		throws AllocationException, ObjectNotRegisteredException;

	public abstract ObjectPrx allocateObjectById(Identity identity, Map map)
		throws AllocationException, ObjectNotRegisteredException;

	public abstract AsyncResult begin_allocateObjectById(Identity identity);

	public abstract AsyncResult begin_allocateObjectById(Identity identity, Map map);

	public abstract AsyncResult begin_allocateObjectById(Identity identity, Callback callback);

	public abstract AsyncResult begin_allocateObjectById(Identity identity, Map map, Callback callback);

	public abstract AsyncResult begin_allocateObjectById(Identity identity, Callback_Session_allocateObjectById callback_session_allocateobjectbyid);

	public abstract AsyncResult begin_allocateObjectById(Identity identity, Map map, Callback_Session_allocateObjectById callback_session_allocateobjectbyid);

	public abstract ObjectPrx end_allocateObjectById(AsyncResult asyncresult)
		throws AllocationException, ObjectNotRegisteredException;

	public abstract boolean allocateObjectById_async(AMI_Session_allocateObjectById ami_session_allocateobjectbyid, Identity identity);

	public abstract boolean allocateObjectById_async(AMI_Session_allocateObjectById ami_session_allocateobjectbyid, Identity identity, Map map);

	public abstract ObjectPrx allocateObjectByType(String s)
		throws AllocationException;

	public abstract ObjectPrx allocateObjectByType(String s, Map map)
		throws AllocationException;

	public abstract AsyncResult begin_allocateObjectByType(String s);

	public abstract AsyncResult begin_allocateObjectByType(String s, Map map);

	public abstract AsyncResult begin_allocateObjectByType(String s, Callback callback);

	public abstract AsyncResult begin_allocateObjectByType(String s, Map map, Callback callback);

	public abstract AsyncResult begin_allocateObjectByType(String s, Callback_Session_allocateObjectByType callback_session_allocateobjectbytype);

	public abstract AsyncResult begin_allocateObjectByType(String s, Map map, Callback_Session_allocateObjectByType callback_session_allocateobjectbytype);

	public abstract ObjectPrx end_allocateObjectByType(AsyncResult asyncresult)
		throws AllocationException;

	public abstract boolean allocateObjectByType_async(AMI_Session_allocateObjectByType ami_session_allocateobjectbytype, String s);

	public abstract boolean allocateObjectByType_async(AMI_Session_allocateObjectByType ami_session_allocateobjectbytype, String s, Map map);

	public abstract void releaseObject(Identity identity)
		throws AllocationException, ObjectNotRegisteredException;

	public abstract void releaseObject(Identity identity, Map map)
		throws AllocationException, ObjectNotRegisteredException;

	public abstract AsyncResult begin_releaseObject(Identity identity);

	public abstract AsyncResult begin_releaseObject(Identity identity, Map map);

	public abstract AsyncResult begin_releaseObject(Identity identity, Callback callback);

	public abstract AsyncResult begin_releaseObject(Identity identity, Map map, Callback callback);

	public abstract AsyncResult begin_releaseObject(Identity identity, Callback_Session_releaseObject callback_session_releaseobject);

	public abstract AsyncResult begin_releaseObject(Identity identity, Map map, Callback_Session_releaseObject callback_session_releaseobject);

	public abstract void end_releaseObject(AsyncResult asyncresult)
		throws AllocationException, ObjectNotRegisteredException;

	public abstract void setAllocationTimeout(int i);

	public abstract void setAllocationTimeout(int i, Map map);

	public abstract AsyncResult begin_setAllocationTimeout(int i);

	public abstract AsyncResult begin_setAllocationTimeout(int i, Map map);

	public abstract AsyncResult begin_setAllocationTimeout(int i, Callback callback);

	public abstract AsyncResult begin_setAllocationTimeout(int i, Map map, Callback callback);

	public abstract AsyncResult begin_setAllocationTimeout(int i, Callback_Session_setAllocationTimeout callback_session_setallocationtimeout);

	public abstract AsyncResult begin_setAllocationTimeout(int i, Map map, Callback_Session_setAllocationTimeout callback_session_setallocationtimeout);

	public abstract void end_setAllocationTimeout(AsyncResult asyncresult);
}
