// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RouterPrx.java

package Ice;

import java.util.Map;

// Referenced classes of package Ice:
//			ObjectPrx, AsyncResult, Callback, Callback_Router_getClientProxy, 
//			AMI_Router_getClientProxy, Callback_Router_getServerProxy, Callback_Router_addProxy, Callback_Router_addProxies, 
//			AMI_Router_addProxies

public interface RouterPrx
	extends ObjectPrx
{

	public abstract ObjectPrx getClientProxy();

	public abstract ObjectPrx getClientProxy(Map map);

	public abstract AsyncResult begin_getClientProxy();

	public abstract AsyncResult begin_getClientProxy(Map map);

	public abstract AsyncResult begin_getClientProxy(Callback callback);

	public abstract AsyncResult begin_getClientProxy(Map map, Callback callback);

	public abstract AsyncResult begin_getClientProxy(Callback_Router_getClientProxy callback_router_getclientproxy);

	public abstract AsyncResult begin_getClientProxy(Map map, Callback_Router_getClientProxy callback_router_getclientproxy);

	public abstract ObjectPrx end_getClientProxy(AsyncResult asyncresult);

	public abstract boolean getClientProxy_async(AMI_Router_getClientProxy ami_router_getclientproxy);

	public abstract boolean getClientProxy_async(AMI_Router_getClientProxy ami_router_getclientproxy, Map map);

	public abstract ObjectPrx getServerProxy();

	public abstract ObjectPrx getServerProxy(Map map);

	public abstract AsyncResult begin_getServerProxy();

	public abstract AsyncResult begin_getServerProxy(Map map);

	public abstract AsyncResult begin_getServerProxy(Callback callback);

	public abstract AsyncResult begin_getServerProxy(Map map, Callback callback);

	public abstract AsyncResult begin_getServerProxy(Callback_Router_getServerProxy callback_router_getserverproxy);

	public abstract AsyncResult begin_getServerProxy(Map map, Callback_Router_getServerProxy callback_router_getserverproxy);

	public abstract ObjectPrx end_getServerProxy(AsyncResult asyncresult);

	/**
	 * @deprecated Method addProxy is deprecated
	 */

	public abstract void addProxy(ObjectPrx objectprx);

	/**
	 * @deprecated Method addProxy is deprecated
	 */

	public abstract void addProxy(ObjectPrx objectprx, Map map);

	/**
	 * @deprecated Method begin_addProxy is deprecated
	 */

	public abstract AsyncResult begin_addProxy(ObjectPrx objectprx);

	/**
	 * @deprecated Method begin_addProxy is deprecated
	 */

	public abstract AsyncResult begin_addProxy(ObjectPrx objectprx, Map map);

	/**
	 * @deprecated Method begin_addProxy is deprecated
	 */

	public abstract AsyncResult begin_addProxy(ObjectPrx objectprx, Callback callback);

	/**
	 * @deprecated Method begin_addProxy is deprecated
	 */

	public abstract AsyncResult begin_addProxy(ObjectPrx objectprx, Map map, Callback callback);

	/**
	 * @deprecated Method begin_addProxy is deprecated
	 */

	public abstract AsyncResult begin_addProxy(ObjectPrx objectprx, Callback_Router_addProxy callback_router_addproxy);

	/**
	 * @deprecated Method begin_addProxy is deprecated
	 */

	public abstract AsyncResult begin_addProxy(ObjectPrx objectprx, Map map, Callback_Router_addProxy callback_router_addproxy);

	public abstract void end_addProxy(AsyncResult asyncresult);

	public abstract ObjectPrx[] addProxies(ObjectPrx aobjectprx[]);

	public abstract ObjectPrx[] addProxies(ObjectPrx aobjectprx[], Map map);

	public abstract AsyncResult begin_addProxies(ObjectPrx aobjectprx[]);

	public abstract AsyncResult begin_addProxies(ObjectPrx aobjectprx[], Map map);

	public abstract AsyncResult begin_addProxies(ObjectPrx aobjectprx[], Callback callback);

	public abstract AsyncResult begin_addProxies(ObjectPrx aobjectprx[], Map map, Callback callback);

	public abstract AsyncResult begin_addProxies(ObjectPrx aobjectprx[], Callback_Router_addProxies callback_router_addproxies);

	public abstract AsyncResult begin_addProxies(ObjectPrx aobjectprx[], Map map, Callback_Router_addProxies callback_router_addproxies);

	public abstract ObjectPrx[] end_addProxies(AsyncResult asyncresult);

	public abstract boolean addProxies_async(AMI_Router_addProxies ami_router_addproxies, ObjectPrx aobjectprx[]);

	public abstract boolean addProxies_async(AMI_Router_addProxies ami_router_addproxies, ObjectPrx aobjectprx[], Map map);
}
