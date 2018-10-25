// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Communicator.java

package Ice;

import java.util.Map;

// Referenced classes of package Ice:
//			ObjectPrx, Identity, ObjectAdapter, RouterPrx, 
//			ObjectFactory, ImplicitContext, Properties, Logger, 
//			Stats, LocatorPrx, PluginManager, AsyncResult, 
//			Callback, Callback_Communicator_flushBatchRequests, Object

public interface Communicator
{

	public abstract void destroy();

	public abstract void shutdown();

	public abstract void waitForShutdown();

	public abstract boolean isShutdown();

	public abstract ObjectPrx stringToProxy(String s);

	public abstract String proxyToString(ObjectPrx objectprx);

	public abstract ObjectPrx propertyToProxy(String s);

	public abstract Map proxyToProperty(ObjectPrx objectprx, String s);

	public abstract Identity stringToIdentity(String s);

	public abstract String identityToString(Identity identity);

	public abstract ObjectAdapter createObjectAdapter(String s);

	public abstract ObjectAdapter createObjectAdapterWithEndpoints(String s, String s1);

	public abstract ObjectAdapter createObjectAdapterWithRouter(String s, RouterPrx routerprx);

	public abstract void addObjectFactory(ObjectFactory objectfactory, String s);

	public abstract ObjectFactory findObjectFactory(String s);

	public abstract ImplicitContext getImplicitContext();

	public abstract Properties getProperties();

	public abstract Logger getLogger();

	public abstract Stats getStats();

	public abstract RouterPrx getDefaultRouter();

	public abstract void setDefaultRouter(RouterPrx routerprx);

	public abstract LocatorPrx getDefaultLocator();

	public abstract void setDefaultLocator(LocatorPrx locatorprx);

	public abstract PluginManager getPluginManager();

	public abstract void flushBatchRequests();

	public abstract AsyncResult begin_flushBatchRequests();

	public abstract AsyncResult begin_flushBatchRequests(Callback callback);

	public abstract AsyncResult begin_flushBatchRequests(Callback_Communicator_flushBatchRequests callback_communicator_flushbatchrequests);

	public abstract void end_flushBatchRequests(AsyncResult asyncresult);

	public abstract ObjectPrx getAdmin();

	public abstract void addAdminFacet(Object obj, String s);

	public abstract Object removeAdminFacet(String s);
}
