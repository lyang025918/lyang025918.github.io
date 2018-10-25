// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _CommunicatorOperationsNC.java

package Ice;

import java.util.Map;

// Referenced classes of package Ice:
//			ObjectPrx, Identity, ObjectAdapter, RouterPrx, 
//			ObjectFactory, ImplicitContext, Properties, Logger, 
//			Stats, LocatorPrx, PluginManager, Object

public interface _CommunicatorOperationsNC
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

	public abstract ObjectPrx getAdmin();

	public abstract void addAdminFacet(Object obj, String s);

	public abstract Object removeAdminFacet(String s);
}
