// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ServiceObserverPrx.java

package IceBox;

import Ice.*;
import java.util.Map;

// Referenced classes of package IceBox:
//			Callback_ServiceObserver_servicesStarted, AMI_ServiceObserver_servicesStarted, Callback_ServiceObserver_servicesStopped, AMI_ServiceObserver_servicesStopped

public interface ServiceObserverPrx
	extends ObjectPrx
{

	public abstract void servicesStarted(String as[]);

	public abstract void servicesStarted(String as[], Map map);

	public abstract AsyncResult begin_servicesStarted(String as[]);

	public abstract AsyncResult begin_servicesStarted(String as[], Map map);

	public abstract AsyncResult begin_servicesStarted(String as[], Callback callback);

	public abstract AsyncResult begin_servicesStarted(String as[], Map map, Callback callback);

	public abstract AsyncResult begin_servicesStarted(String as[], Callback_ServiceObserver_servicesStarted callback_serviceobserver_servicesstarted);

	public abstract AsyncResult begin_servicesStarted(String as[], Map map, Callback_ServiceObserver_servicesStarted callback_serviceobserver_servicesstarted);

	public abstract void end_servicesStarted(AsyncResult asyncresult);

	public abstract boolean servicesStarted_async(AMI_ServiceObserver_servicesStarted ami_serviceobserver_servicesstarted, String as[]);

	public abstract boolean servicesStarted_async(AMI_ServiceObserver_servicesStarted ami_serviceobserver_servicesstarted, String as[], Map map);

	public abstract void servicesStopped(String as[]);

	public abstract void servicesStopped(String as[], Map map);

	public abstract AsyncResult begin_servicesStopped(String as[]);

	public abstract AsyncResult begin_servicesStopped(String as[], Map map);

	public abstract AsyncResult begin_servicesStopped(String as[], Callback callback);

	public abstract AsyncResult begin_servicesStopped(String as[], Map map, Callback callback);

	public abstract AsyncResult begin_servicesStopped(String as[], Callback_ServiceObserver_servicesStopped callback_serviceobserver_servicesstopped);

	public abstract AsyncResult begin_servicesStopped(String as[], Map map, Callback_ServiceObserver_servicesStopped callback_serviceobserver_servicesstopped);

	public abstract void end_servicesStopped(AsyncResult asyncresult);

	public abstract boolean servicesStopped_async(AMI_ServiceObserver_servicesStopped ami_serviceobserver_servicesstopped, String as[]);

	public abstract boolean servicesStopped_async(AMI_ServiceObserver_servicesStopped ami_serviceobserver_servicesstopped, String as[], Map map);
}
