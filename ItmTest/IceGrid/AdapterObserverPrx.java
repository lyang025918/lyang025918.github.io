// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AdapterObserverPrx.java

package IceGrid;

import Ice.*;
import java.util.Map;

// Referenced classes of package IceGrid:
//			AdapterInfo, Callback_AdapterObserver_adapterInit, AMI_AdapterObserver_adapterInit, Callback_AdapterObserver_adapterAdded, 
//			Callback_AdapterObserver_adapterUpdated, Callback_AdapterObserver_adapterRemoved

public interface AdapterObserverPrx
	extends ObjectPrx
{

	public abstract void adapterInit(AdapterInfo aadapterinfo[]);

	public abstract void adapterInit(AdapterInfo aadapterinfo[], Map map);

	public abstract AsyncResult begin_adapterInit(AdapterInfo aadapterinfo[]);

	public abstract AsyncResult begin_adapterInit(AdapterInfo aadapterinfo[], Map map);

	public abstract AsyncResult begin_adapterInit(AdapterInfo aadapterinfo[], Callback callback);

	public abstract AsyncResult begin_adapterInit(AdapterInfo aadapterinfo[], Map map, Callback callback);

	public abstract AsyncResult begin_adapterInit(AdapterInfo aadapterinfo[], Callback_AdapterObserver_adapterInit callback_adapterobserver_adapterinit);

	public abstract AsyncResult begin_adapterInit(AdapterInfo aadapterinfo[], Map map, Callback_AdapterObserver_adapterInit callback_adapterobserver_adapterinit);

	public abstract void end_adapterInit(AsyncResult asyncresult);

	public abstract boolean adapterInit_async(AMI_AdapterObserver_adapterInit ami_adapterobserver_adapterinit, AdapterInfo aadapterinfo[]);

	public abstract boolean adapterInit_async(AMI_AdapterObserver_adapterInit ami_adapterobserver_adapterinit, AdapterInfo aadapterinfo[], Map map);

	public abstract void adapterAdded(AdapterInfo adapterinfo);

	public abstract void adapterAdded(AdapterInfo adapterinfo, Map map);

	public abstract AsyncResult begin_adapterAdded(AdapterInfo adapterinfo);

	public abstract AsyncResult begin_adapterAdded(AdapterInfo adapterinfo, Map map);

	public abstract AsyncResult begin_adapterAdded(AdapterInfo adapterinfo, Callback callback);

	public abstract AsyncResult begin_adapterAdded(AdapterInfo adapterinfo, Map map, Callback callback);

	public abstract AsyncResult begin_adapterAdded(AdapterInfo adapterinfo, Callback_AdapterObserver_adapterAdded callback_adapterobserver_adapteradded);

	public abstract AsyncResult begin_adapterAdded(AdapterInfo adapterinfo, Map map, Callback_AdapterObserver_adapterAdded callback_adapterobserver_adapteradded);

	public abstract void end_adapterAdded(AsyncResult asyncresult);

	public abstract void adapterUpdated(AdapterInfo adapterinfo);

	public abstract void adapterUpdated(AdapterInfo adapterinfo, Map map);

	public abstract AsyncResult begin_adapterUpdated(AdapterInfo adapterinfo);

	public abstract AsyncResult begin_adapterUpdated(AdapterInfo adapterinfo, Map map);

	public abstract AsyncResult begin_adapterUpdated(AdapterInfo adapterinfo, Callback callback);

	public abstract AsyncResult begin_adapterUpdated(AdapterInfo adapterinfo, Map map, Callback callback);

	public abstract AsyncResult begin_adapterUpdated(AdapterInfo adapterinfo, Callback_AdapterObserver_adapterUpdated callback_adapterobserver_adapterupdated);

	public abstract AsyncResult begin_adapterUpdated(AdapterInfo adapterinfo, Map map, Callback_AdapterObserver_adapterUpdated callback_adapterobserver_adapterupdated);

	public abstract void end_adapterUpdated(AsyncResult asyncresult);

	public abstract void adapterRemoved(String s);

	public abstract void adapterRemoved(String s, Map map);

	public abstract AsyncResult begin_adapterRemoved(String s);

	public abstract AsyncResult begin_adapterRemoved(String s, Map map);

	public abstract AsyncResult begin_adapterRemoved(String s, Callback callback);

	public abstract AsyncResult begin_adapterRemoved(String s, Map map, Callback callback);

	public abstract AsyncResult begin_adapterRemoved(String s, Callback_AdapterObserver_adapterRemoved callback_adapterobserver_adapterremoved);

	public abstract AsyncResult begin_adapterRemoved(String s, Map map, Callback_AdapterObserver_adapterRemoved callback_adapterobserver_adapterremoved);

	public abstract void end_adapterRemoved(AsyncResult asyncresult);
}
