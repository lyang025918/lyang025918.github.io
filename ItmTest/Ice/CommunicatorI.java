// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CommunicatorI.java

package Ice;

import IceInternal.CallbackBase;
import IceInternal.CommunicatorBatchOutgoingAsync;
import IceInternal.Instance;
import IceInternal.ObjectAdapterFactory;
import IceInternal.ObjectFactoryManager;
import IceInternal.OutgoingConnectionFactory;
import IceInternal.ProxyFactory;
import IceInternal.ReferenceFactory;
import java.util.*;

// Referenced classes of package Ice:
//			Communicator, Properties, InitializationData, AsyncResult, 
//			ObjectPrx, Identity, ObjectAdapter, RouterPrx, 
//			ObjectFactory, Logger, Stats, LocatorPrx, 
//			ImplicitContext, PluginManager, Callback, Callback_Communicator_flushBatchRequests, 
//			Object, StringSeqHolder

public final class CommunicatorI
	implements Communicator
{

	private static final String __flushBatchRequests_name = "flushBatchRequests";
	private Instance _instance;

	public void destroy()
	{
		_instance.destroy();
	}

	public void shutdown()
	{
		_instance.objectAdapterFactory().shutdown();
	}

	public void waitForShutdown()
	{
		_instance.objectAdapterFactory().waitForShutdown();
	}

	public boolean isShutdown()
	{
		return _instance.objectAdapterFactory().isShutdown();
	}

	public ObjectPrx stringToProxy(String s)
	{
		return _instance.proxyFactory().stringToProxy(s);
	}

	public String proxyToString(ObjectPrx proxy)
	{
		return _instance.proxyFactory().proxyToString(proxy);
	}

	public ObjectPrx propertyToProxy(String s)
	{
		return _instance.proxyFactory().propertyToProxy(s);
	}

	public Map proxyToProperty(ObjectPrx proxy, String prefix)
	{
		return _instance.proxyFactory().proxyToProperty(proxy, prefix);
	}

	public Identity stringToIdentity(String s)
	{
		return _instance.stringToIdentity(s);
	}

	public String identityToString(Identity ident)
	{
		return _instance.identityToString(ident);
	}

	public ObjectAdapter createObjectAdapter(String name)
	{
		return _instance.objectAdapterFactory().createObjectAdapter(name, null);
	}

	public ObjectAdapter createObjectAdapterWithEndpoints(String name, String endpoints)
	{
		if (name.length() == 0)
			name = UUID.randomUUID().toString();
		getProperties().setProperty((new StringBuilder()).append(name).append(".Endpoints").toString(), endpoints);
		return _instance.objectAdapterFactory().createObjectAdapter(name, null);
	}

	public ObjectAdapter createObjectAdapterWithRouter(String name, RouterPrx router)
	{
		if (name.length() == 0)
			name = UUID.randomUUID().toString();
		Map properties = proxyToProperty(router, (new StringBuilder()).append(name).append(".Router").toString());
		java.util.Map.Entry p;
		for (Iterator i$ = properties.entrySet().iterator(); i$.hasNext(); getProperties().setProperty((String)p.getKey(), (String)p.getValue()))
			p = (java.util.Map.Entry)i$.next();

		return _instance.objectAdapterFactory().createObjectAdapter(name, router);
	}

	public void addObjectFactory(ObjectFactory factory, String id)
	{
		_instance.servantFactoryManager().add(factory, id);
	}

	public ObjectFactory findObjectFactory(String id)
	{
		return _instance.servantFactoryManager().find(id);
	}

	public Properties getProperties()
	{
		return _instance.initializationData().properties;
	}

	public Logger getLogger()
	{
		return _instance.initializationData().logger;
	}

	public Stats getStats()
	{
		return _instance.initializationData().stats;
	}

	public RouterPrx getDefaultRouter()
	{
		return _instance.referenceFactory().getDefaultRouter();
	}

	public void setDefaultRouter(RouterPrx router)
	{
		_instance.setDefaultRouter(router);
	}

	public LocatorPrx getDefaultLocator()
	{
		return _instance.referenceFactory().getDefaultLocator();
	}

	public void setDefaultLocator(LocatorPrx locator)
	{
		_instance.setDefaultLocator(locator);
	}

	public ImplicitContext getImplicitContext()
	{
		return _instance.getImplicitContext();
	}

	public PluginManager getPluginManager()
	{
		return _instance.pluginManager();
	}

	public void flushBatchRequests()
	{
		AsyncResult r = begin_flushBatchRequests();
		end_flushBatchRequests(r);
	}

	public AsyncResult begin_flushBatchRequests()
	{
		return begin_flushBatchRequestsInternal(null);
	}

	public AsyncResult begin_flushBatchRequests(Callback cb)
	{
		return begin_flushBatchRequestsInternal(cb);
	}

	public AsyncResult begin_flushBatchRequests(Callback_Communicator_flushBatchRequests cb)
	{
		return begin_flushBatchRequestsInternal(cb);
	}

	private AsyncResult begin_flushBatchRequestsInternal(CallbackBase cb)
	{
		OutgoingConnectionFactory connectionFactory = _instance.outgoingConnectionFactory();
		ObjectAdapterFactory adapterFactory = _instance.objectAdapterFactory();
		CommunicatorBatchOutgoingAsync result = new CommunicatorBatchOutgoingAsync(this, _instance, "flushBatchRequests", cb);
		connectionFactory.flushAsyncBatchRequests(result);
		adapterFactory.flushAsyncBatchRequests(result);
		result.ready();
		return result;
	}

	public void end_flushBatchRequests(AsyncResult r)
	{
		AsyncResult.__check(r, this, "flushBatchRequests");
		r.__wait();
	}

	public ObjectPrx getAdmin()
	{
		return _instance.getAdmin();
	}

	public void addAdminFacet(Object servant, String facet)
	{
		_instance.addAdminFacet(servant, facet);
	}

	public Object removeAdminFacet(String facet)
	{
		return _instance.removeAdminFacet(facet);
	}

	CommunicatorI(InitializationData initData)
	{
		_instance = new Instance(this, initData);
	}

	void finishSetup(StringSeqHolder args)
	{
		try
		{
			_instance.finishSetup(args);
		}
		catch (RuntimeException ex)
		{
			_instance.destroy();
			throw ex;
		}
	}

	public Instance getInstance()
	{
		return _instance;
	}
}
