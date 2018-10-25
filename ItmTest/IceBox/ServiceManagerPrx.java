// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ServiceManagerPrx.java

package IceBox;

import Ice.*;
import java.util.Map;

// Referenced classes of package IceBox:
//			AlreadyStartedException, NoSuchServiceException, AlreadyStoppedException, Callback_ServiceManager_getSliceChecksums, 
//			Callback_ServiceManager_startService, AMI_ServiceManager_startService, Callback_ServiceManager_stopService, AMI_ServiceManager_stopService, 
//			ServiceObserverPrx, Callback_ServiceManager_addObserver, AMI_ServiceManager_addObserver, Callback_ServiceManager_shutdown

public interface ServiceManagerPrx
	extends ObjectPrx
{

	public abstract Map getSliceChecksums();

	public abstract Map getSliceChecksums(Map map);

	public abstract AsyncResult begin_getSliceChecksums();

	public abstract AsyncResult begin_getSliceChecksums(Map map);

	public abstract AsyncResult begin_getSliceChecksums(Callback callback);

	public abstract AsyncResult begin_getSliceChecksums(Map map, Callback callback);

	public abstract AsyncResult begin_getSliceChecksums(Callback_ServiceManager_getSliceChecksums callback_servicemanager_getslicechecksums);

	public abstract AsyncResult begin_getSliceChecksums(Map map, Callback_ServiceManager_getSliceChecksums callback_servicemanager_getslicechecksums);

	public abstract Map end_getSliceChecksums(AsyncResult asyncresult);

	public abstract void startService(String s)
		throws AlreadyStartedException, NoSuchServiceException;

	public abstract void startService(String s, Map map)
		throws AlreadyStartedException, NoSuchServiceException;

	public abstract AsyncResult begin_startService(String s);

	public abstract AsyncResult begin_startService(String s, Map map);

	public abstract AsyncResult begin_startService(String s, Callback callback);

	public abstract AsyncResult begin_startService(String s, Map map, Callback callback);

	public abstract AsyncResult begin_startService(String s, Callback_ServiceManager_startService callback_servicemanager_startservice);

	public abstract AsyncResult begin_startService(String s, Map map, Callback_ServiceManager_startService callback_servicemanager_startservice);

	public abstract void end_startService(AsyncResult asyncresult)
		throws AlreadyStartedException, NoSuchServiceException;

	public abstract boolean startService_async(AMI_ServiceManager_startService ami_servicemanager_startservice, String s);

	public abstract boolean startService_async(AMI_ServiceManager_startService ami_servicemanager_startservice, String s, Map map);

	public abstract void stopService(String s)
		throws AlreadyStoppedException, NoSuchServiceException;

	public abstract void stopService(String s, Map map)
		throws AlreadyStoppedException, NoSuchServiceException;

	public abstract AsyncResult begin_stopService(String s);

	public abstract AsyncResult begin_stopService(String s, Map map);

	public abstract AsyncResult begin_stopService(String s, Callback callback);

	public abstract AsyncResult begin_stopService(String s, Map map, Callback callback);

	public abstract AsyncResult begin_stopService(String s, Callback_ServiceManager_stopService callback_servicemanager_stopservice);

	public abstract AsyncResult begin_stopService(String s, Map map, Callback_ServiceManager_stopService callback_servicemanager_stopservice);

	public abstract void end_stopService(AsyncResult asyncresult)
		throws AlreadyStoppedException, NoSuchServiceException;

	public abstract boolean stopService_async(AMI_ServiceManager_stopService ami_servicemanager_stopservice, String s);

	public abstract boolean stopService_async(AMI_ServiceManager_stopService ami_servicemanager_stopservice, String s, Map map);

	public abstract void addObserver(ServiceObserverPrx serviceobserverprx);

	public abstract void addObserver(ServiceObserverPrx serviceobserverprx, Map map);

	public abstract AsyncResult begin_addObserver(ServiceObserverPrx serviceobserverprx);

	public abstract AsyncResult begin_addObserver(ServiceObserverPrx serviceobserverprx, Map map);

	public abstract AsyncResult begin_addObserver(ServiceObserverPrx serviceobserverprx, Callback callback);

	public abstract AsyncResult begin_addObserver(ServiceObserverPrx serviceobserverprx, Map map, Callback callback);

	public abstract AsyncResult begin_addObserver(ServiceObserverPrx serviceobserverprx, Callback_ServiceManager_addObserver callback_servicemanager_addobserver);

	public abstract AsyncResult begin_addObserver(ServiceObserverPrx serviceobserverprx, Map map, Callback_ServiceManager_addObserver callback_servicemanager_addobserver);

	public abstract void end_addObserver(AsyncResult asyncresult);

	public abstract boolean addObserver_async(AMI_ServiceManager_addObserver ami_servicemanager_addobserver, ServiceObserverPrx serviceobserverprx);

	public abstract boolean addObserver_async(AMI_ServiceManager_addObserver ami_servicemanager_addobserver, ServiceObserverPrx serviceobserverprx, Map map);

	public abstract void shutdown();

	public abstract void shutdown(Map map);

	public abstract AsyncResult begin_shutdown();

	public abstract AsyncResult begin_shutdown(Map map);

	public abstract AsyncResult begin_shutdown(Callback callback);

	public abstract AsyncResult begin_shutdown(Map map, Callback callback);

	public abstract AsyncResult begin_shutdown(Callback_ServiceManager_shutdown callback_servicemanager_shutdown);

	public abstract AsyncResult begin_shutdown(Map map, Callback_ServiceManager_shutdown callback_servicemanager_shutdown);

	public abstract void end_shutdown(AsyncResult asyncresult);
}
