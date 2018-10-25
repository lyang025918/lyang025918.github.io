// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ApplicationObserverPrx.java

package IceGrid;

import Ice.*;
import java.util.List;
import java.util.Map;

// Referenced classes of package IceGrid:
//			Callback_ApplicationObserver_applicationInit, AMI_ApplicationObserver_applicationInit, ApplicationInfo, Callback_ApplicationObserver_applicationAdded, 
//			Callback_ApplicationObserver_applicationRemoved, ApplicationUpdateInfo, Callback_ApplicationObserver_applicationUpdated

public interface ApplicationObserverPrx
	extends ObjectPrx
{

	public abstract void applicationInit(int i, List list);

	public abstract void applicationInit(int i, List list, Map map);

	public abstract AsyncResult begin_applicationInit(int i, List list);

	public abstract AsyncResult begin_applicationInit(int i, List list, Map map);

	public abstract AsyncResult begin_applicationInit(int i, List list, Callback callback);

	public abstract AsyncResult begin_applicationInit(int i, List list, Map map, Callback callback);

	public abstract AsyncResult begin_applicationInit(int i, List list, Callback_ApplicationObserver_applicationInit callback_applicationobserver_applicationinit);

	public abstract AsyncResult begin_applicationInit(int i, List list, Map map, Callback_ApplicationObserver_applicationInit callback_applicationobserver_applicationinit);

	public abstract void end_applicationInit(AsyncResult asyncresult);

	public abstract boolean applicationInit_async(AMI_ApplicationObserver_applicationInit ami_applicationobserver_applicationinit, int i, List list);

	public abstract boolean applicationInit_async(AMI_ApplicationObserver_applicationInit ami_applicationobserver_applicationinit, int i, List list, Map map);

	public abstract void applicationAdded(int i, ApplicationInfo applicationinfo);

	public abstract void applicationAdded(int i, ApplicationInfo applicationinfo, Map map);

	public abstract AsyncResult begin_applicationAdded(int i, ApplicationInfo applicationinfo);

	public abstract AsyncResult begin_applicationAdded(int i, ApplicationInfo applicationinfo, Map map);

	public abstract AsyncResult begin_applicationAdded(int i, ApplicationInfo applicationinfo, Callback callback);

	public abstract AsyncResult begin_applicationAdded(int i, ApplicationInfo applicationinfo, Map map, Callback callback);

	public abstract AsyncResult begin_applicationAdded(int i, ApplicationInfo applicationinfo, Callback_ApplicationObserver_applicationAdded callback_applicationobserver_applicationadded);

	public abstract AsyncResult begin_applicationAdded(int i, ApplicationInfo applicationinfo, Map map, Callback_ApplicationObserver_applicationAdded callback_applicationobserver_applicationadded);

	public abstract void end_applicationAdded(AsyncResult asyncresult);

	public abstract void applicationRemoved(int i, String s);

	public abstract void applicationRemoved(int i, String s, Map map);

	public abstract AsyncResult begin_applicationRemoved(int i, String s);

	public abstract AsyncResult begin_applicationRemoved(int i, String s, Map map);

	public abstract AsyncResult begin_applicationRemoved(int i, String s, Callback callback);

	public abstract AsyncResult begin_applicationRemoved(int i, String s, Map map, Callback callback);

	public abstract AsyncResult begin_applicationRemoved(int i, String s, Callback_ApplicationObserver_applicationRemoved callback_applicationobserver_applicationremoved);

	public abstract AsyncResult begin_applicationRemoved(int i, String s, Map map, Callback_ApplicationObserver_applicationRemoved callback_applicationobserver_applicationremoved);

	public abstract void end_applicationRemoved(AsyncResult asyncresult);

	public abstract void applicationUpdated(int i, ApplicationUpdateInfo applicationupdateinfo);

	public abstract void applicationUpdated(int i, ApplicationUpdateInfo applicationupdateinfo, Map map);

	public abstract AsyncResult begin_applicationUpdated(int i, ApplicationUpdateInfo applicationupdateinfo);

	public abstract AsyncResult begin_applicationUpdated(int i, ApplicationUpdateInfo applicationupdateinfo, Map map);

	public abstract AsyncResult begin_applicationUpdated(int i, ApplicationUpdateInfo applicationupdateinfo, Callback callback);

	public abstract AsyncResult begin_applicationUpdated(int i, ApplicationUpdateInfo applicationupdateinfo, Map map, Callback callback);

	public abstract AsyncResult begin_applicationUpdated(int i, ApplicationUpdateInfo applicationupdateinfo, Callback_ApplicationObserver_applicationUpdated callback_applicationobserver_applicationupdated);

	public abstract AsyncResult begin_applicationUpdated(int i, ApplicationUpdateInfo applicationupdateinfo, Map map, Callback_ApplicationObserver_applicationUpdated callback_applicationobserver_applicationupdated);

	public abstract void end_applicationUpdated(AsyncResult asyncresult);
}
