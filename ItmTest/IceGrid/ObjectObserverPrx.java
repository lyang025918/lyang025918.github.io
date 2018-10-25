// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ObjectObserverPrx.java

package IceGrid;

import Ice.*;
import java.util.Map;

// Referenced classes of package IceGrid:
//			ObjectInfo, Callback_ObjectObserver_objectInit, AMI_ObjectObserver_objectInit, Callback_ObjectObserver_objectAdded, 
//			Callback_ObjectObserver_objectUpdated, Callback_ObjectObserver_objectRemoved

public interface ObjectObserverPrx
	extends ObjectPrx
{

	public abstract void objectInit(ObjectInfo aobjectinfo[]);

	public abstract void objectInit(ObjectInfo aobjectinfo[], Map map);

	public abstract AsyncResult begin_objectInit(ObjectInfo aobjectinfo[]);

	public abstract AsyncResult begin_objectInit(ObjectInfo aobjectinfo[], Map map);

	public abstract AsyncResult begin_objectInit(ObjectInfo aobjectinfo[], Callback callback);

	public abstract AsyncResult begin_objectInit(ObjectInfo aobjectinfo[], Map map, Callback callback);

	public abstract AsyncResult begin_objectInit(ObjectInfo aobjectinfo[], Callback_ObjectObserver_objectInit callback_objectobserver_objectinit);

	public abstract AsyncResult begin_objectInit(ObjectInfo aobjectinfo[], Map map, Callback_ObjectObserver_objectInit callback_objectobserver_objectinit);

	public abstract void end_objectInit(AsyncResult asyncresult);

	public abstract boolean objectInit_async(AMI_ObjectObserver_objectInit ami_objectobserver_objectinit, ObjectInfo aobjectinfo[]);

	public abstract boolean objectInit_async(AMI_ObjectObserver_objectInit ami_objectobserver_objectinit, ObjectInfo aobjectinfo[], Map map);

	public abstract void objectAdded(ObjectInfo objectinfo);

	public abstract void objectAdded(ObjectInfo objectinfo, Map map);

	public abstract AsyncResult begin_objectAdded(ObjectInfo objectinfo);

	public abstract AsyncResult begin_objectAdded(ObjectInfo objectinfo, Map map);

	public abstract AsyncResult begin_objectAdded(ObjectInfo objectinfo, Callback callback);

	public abstract AsyncResult begin_objectAdded(ObjectInfo objectinfo, Map map, Callback callback);

	public abstract AsyncResult begin_objectAdded(ObjectInfo objectinfo, Callback_ObjectObserver_objectAdded callback_objectobserver_objectadded);

	public abstract AsyncResult begin_objectAdded(ObjectInfo objectinfo, Map map, Callback_ObjectObserver_objectAdded callback_objectobserver_objectadded);

	public abstract void end_objectAdded(AsyncResult asyncresult);

	public abstract void objectUpdated(ObjectInfo objectinfo);

	public abstract void objectUpdated(ObjectInfo objectinfo, Map map);

	public abstract AsyncResult begin_objectUpdated(ObjectInfo objectinfo);

	public abstract AsyncResult begin_objectUpdated(ObjectInfo objectinfo, Map map);

	public abstract AsyncResult begin_objectUpdated(ObjectInfo objectinfo, Callback callback);

	public abstract AsyncResult begin_objectUpdated(ObjectInfo objectinfo, Map map, Callback callback);

	public abstract AsyncResult begin_objectUpdated(ObjectInfo objectinfo, Callback_ObjectObserver_objectUpdated callback_objectobserver_objectupdated);

	public abstract AsyncResult begin_objectUpdated(ObjectInfo objectinfo, Map map, Callback_ObjectObserver_objectUpdated callback_objectobserver_objectupdated);

	public abstract void end_objectUpdated(AsyncResult asyncresult);

	public abstract void objectRemoved(Identity identity);

	public abstract void objectRemoved(Identity identity, Map map);

	public abstract AsyncResult begin_objectRemoved(Identity identity);

	public abstract AsyncResult begin_objectRemoved(Identity identity, Map map);

	public abstract AsyncResult begin_objectRemoved(Identity identity, Callback callback);

	public abstract AsyncResult begin_objectRemoved(Identity identity, Map map, Callback callback);

	public abstract AsyncResult begin_objectRemoved(Identity identity, Callback_ObjectObserver_objectRemoved callback_objectobserver_objectremoved);

	public abstract AsyncResult begin_objectRemoved(Identity identity, Map map, Callback_ObjectObserver_objectRemoved callback_objectobserver_objectremoved);

	public abstract void end_objectRemoved(AsyncResult asyncresult);
}
