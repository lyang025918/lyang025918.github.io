// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ObjectAdapterI.java

package Ice;

import IceInternal.CommunicatorBatchOutgoingAsync;
import IceInternal.ConnectionMonitor;
import IceInternal.EndpointFactoryManager;
import IceInternal.EndpointI;
import IceInternal.IncomingConnectionFactory;
import IceInternal.Instance;
import IceInternal.LocatorInfo;
import IceInternal.LocatorManager;
import IceInternal.ObjectAdapterFactory;
import IceInternal.OutgoingConnectionFactory;
import IceInternal.ProcessI;
import IceInternal.PropertyNames;
import IceInternal.ProxyFactory;
import IceInternal.Reference;
import IceInternal.ReferenceFactory;
import IceInternal.RouterInfo;
import IceInternal.RouterManager;
import IceInternal.ServantManager;
import IceInternal.ThreadPool;
import IceInternal.TraceLevels;
import IceUtilInternal.Assert;
import IceUtilInternal.StringUtil;
import java.io.PrintStream;
import java.util.*;

// Referenced classes of package Ice:
//			Identity, LocalException, ObjectPrxHelperBase, Endpoint, 
//			InitializationException, ProxyParseException, AlreadyRegisteredException, ObjectAdapterDeactivatedException, 
//			IllegalIdentityException, EndpointParseException, AdapterNotFoundException, NotRegisteredException, 
//			InvalidReplicaGroupIdException, AdapterAlreadyActiveException, ObjectAdapterIdInUseException, ServerNotFoundException, 
//			ObjectAdapter, InitializationData, Properties, ObjectPrx, 
//			Logger, RouterPrxHelper, RouterPrx, LocatorPrxHelper, 
//			LocatorRegistryPrx, ProcessPrxHelper, Communicator, Object, 
//			ServantLocator, LocatorPrx

public final class ObjectAdapterI
	implements ObjectAdapter
{

	private static String _suffixes[] = {
		"ACM", "AdapterId", "Endpoints", "Locator", "Locator.EndpointSelection", "Locator.ConnectionCached", "Locator.PreferSecure", "Locator.CollocationOptimized", "Locator.Router", "PublishedEndpoints", 
		"RegisterProcess", "ReplicaGroupId", "Router", "Router.EndpointSelection", "Router.ConnectionCached", "Router.PreferSecure", "Router.CollocationOptimized", "Router.Locator", "Router.Locator.EndpointSelection", "Router.Locator.ConnectionCached", 
		"Router.Locator.PreferSecure", "Router.Locator.CollocationOptimized", "Router.Locator.LocatorCacheTimeout", "Router.LocatorCacheTimeout", "ProxyOptions", "ThreadPool.Size", "ThreadPool.SizeMax", "ThreadPool.SizeWarn", "ThreadPool.StackSize", "ThreadPool.Serialize"
	};
	private boolean _deactivated;
	private Instance _instance;
	private Communicator _communicator;
	private ObjectAdapterFactory _objectAdapterFactory;
	private ThreadPool _threadPool;
	private boolean _hasAcmTimeout;
	private int _acmTimeout;
	private ServantManager _servantManager;
	private boolean _activateOneOffDone;
	private final String _name;
	private final String _id;
	private final String _replicaGroupId;
	private Reference _reference;
	private List _incomingConnectionFactories;
	private List _routerEndpoints;
	private RouterInfo _routerInfo;
	private List _publishedEndpoints;
	private LocatorInfo _locatorInfo;
	private int _directCount;
	private boolean _waitForActivate;
	private int _waitForHold;
	private boolean _waitForHoldRetry;
	private boolean _destroying;
	private boolean _destroyed;
	private boolean _noConfig;
	private Identity _processId;
	static final boolean $assertionsDisabled = !Ice/ObjectAdapterI.desiredAssertionStatus();

	public String getName()
	{
		return _noConfig ? "" : _name;
	}

	public synchronized Communicator getCommunicator()
	{
		return _communicator;
	}

	public void activate()
	{
		LocatorInfo locatorInfo;
		boolean registerProcess;
		boolean printAdapterReady;
label0:
		{
			locatorInfo = null;
			registerProcess = false;
			printAdapterReady = false;
			synchronized (this)
			{
				checkForDeactivation();
				_waitForHoldRetry = _waitForHold > 0;
				if (!_activateOneOffDone)
					break label0;
				IncomingConnectionFactory factory;
				for (Iterator i$ = _incomingConnectionFactories.iterator(); i$.hasNext(); factory.activate())
					factory = (IncomingConnectionFactory)i$.next();

			}
			return;
		}
		_waitForActivate = true;
		locatorInfo = _locatorInfo;
		if (!_noConfig)
		{
			Properties properties = _instance.initializationData().properties;
			registerProcess = properties.getPropertyAsInt((new StringBuilder()).append(_name).append(".RegisterProcess").toString()) > 0;
			printAdapterReady = properties.getPropertyAsInt("Ice.PrintAdapterReady") > 0;
		}
		objectadapteri;
		JVM INSTR monitorexit ;
		  goto _L1
		exception;
		throw exception;
_L1:
		try
		{
			Identity dummy = new Identity();
			dummy.name = "dummy";
			updateLocatorRegistry(locatorInfo, createDirectProxy(dummy), registerProcess);
		}
		catch (LocalException ex)
		{
			synchronized (this)
			{
				_waitForActivate = false;
				notifyAll();
			}
			throw ex;
		}
		if (printAdapterReady)
			System.out.println((new StringBuilder()).append(_name).append(" ready").toString());
		synchronized (this)
		{
			if (!$assertionsDisabled && _deactivated)
				throw new AssertionError();
			_waitForActivate = false;
			notifyAll();
			_activateOneOffDone = true;
			IncomingConnectionFactory factory;
			for (Iterator i$ = _incomingConnectionFactories.iterator(); i$.hasNext(); factory.activate())
				factory = (IncomingConnectionFactory)i$.next();

		}
		return;
	}

	public synchronized void hold()
	{
		checkForDeactivation();
		IncomingConnectionFactory factory;
		for (Iterator i$ = _incomingConnectionFactories.iterator(); i$.hasNext(); factory.hold())
			factory = (IncomingConnectionFactory)i$.next();

	}

	public void waitForHold()
	{
_L2:
label0:
		{
			List incomingConnectionFactories;
			synchronized (this)
			{
				checkForDeactivation();
				incomingConnectionFactories = new ArrayList(_incomingConnectionFactories);
				_waitForHold++;
			}
			IncomingConnectionFactory factory;
			for (Iterator i$ = incomingConnectionFactories.iterator(); i$.hasNext(); factory.waitUntilHolding())
				factory = (IncomingConnectionFactory)i$.next();

			synchronized (this)
			{
				if (--_waitForHold == 0)
					notifyAll();
				if (_waitForHoldRetry)
					break label0;
			}
			return;
		}
		while (_waitForHold > 0) 
		{
			checkForDeactivation();
			try
			{
				wait();
			}
			catch (InterruptedException ex) { }
		}
		_waitForHoldRetry = false;
		objectadapteri1;
		JVM INSTR monitorexit ;
		if (true) goto _L2; else goto _L1
_L1:
		exception1;
		throw exception1;
	}

	public void deactivate()
	{
label0:
		{
			synchronized (this)
			{
				if (!_deactivated)
					break label0;
			}
			return;
		}
		OutgoingConnectionFactory outgoingConnectionFactory;
		List incomingConnectionFactories;
		LocatorInfo locatorInfo;
		while (_waitForActivate) 
			try
			{
				wait();
			}
			catch (InterruptedException ex) { }
		if (_routerInfo != null)
		{
			_instance.routerManager().erase(_routerInfo.getRouter());
			_routerInfo.setAdapter(null);
		}
		incomingConnectionFactories = new ArrayList(_incomingConnectionFactories);
		outgoingConnectionFactory = _instance.outgoingConnectionFactory();
		locatorInfo = _locatorInfo;
		_deactivated = true;
		notifyAll();
		objectadapteri;
		JVM INSTR monitorexit ;
		  goto _L1
		exception;
		throw exception;
_L1:
		try
		{
			updateLocatorRegistry(locatorInfo, null, false);
		}
		catch (LocalException ex) { }
		IncomingConnectionFactory factory;
		for (Iterator i$ = incomingConnectionFactories.iterator(); i$.hasNext(); factory.destroy())
			factory = (IncomingConnectionFactory)i$.next();

		outgoingConnectionFactory.removeAdapter(this);
		return;
	}

	public void waitForDeactivate()
	{
label0:
		{
			synchronized (this)
			{
				if (!_destroyed)
					break label0;
			}
			return;
		}
		IncomingConnectionFactory incomingConnectionFactories[];
		while (!_deactivated || _directCount > 0) 
			try
			{
				wait();
			}
			catch (InterruptedException ex) { }
		incomingConnectionFactories = (IncomingConnectionFactory[])_incomingConnectionFactories.toArray(new IncomingConnectionFactory[0]);
		objectadapteri;
		JVM INSTR monitorexit ;
		  goto _L1
		exception;
		throw exception;
_L1:
		IncomingConnectionFactory arr$[] = incomingConnectionFactories;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			IncomingConnectionFactory f = arr$[i$];
			f.waitUntilFinished();
		}

		return;
	}

	public synchronized boolean isDeactivated()
	{
		return _deactivated;
	}

	public void destroy()
	{
label0:
		{
			synchronized (this)
			{
				while (_destroying) 
					try
					{
						wait();
					}
					catch (InterruptedException ex) { }
				if (!_destroyed)
					break label0;
			}
			return;
		}
		_destroying = true;
		objectadapteri;
		JVM INSTR monitorexit ;
		  goto _L1
		exception;
		throw exception;
_L1:
		deactivate();
		waitForDeactivate();
		_servantManager.destroy();
		if (_threadPool != null)
		{
			_threadPool.destroy();
			_threadPool.joinWithAllThreads();
		}
		ObjectAdapterFactory objectAdapterFactory;
		synchronized (this)
		{
			_destroying = false;
			_destroyed = true;
			notifyAll();
			_incomingConnectionFactories.clear();
			_instance = null;
			_threadPool = null;
			_routerEndpoints = null;
			_routerInfo = null;
			_publishedEndpoints = null;
			_locatorInfo = null;
			_reference = null;
			objectAdapterFactory = _objectAdapterFactory;
			_objectAdapterFactory = null;
		}
		if (objectAdapterFactory != null)
			objectAdapterFactory.removeObjectAdapter(this);
		return;
	}

	public ObjectPrx add(Object object, Identity ident)
	{
		return addFacet(object, ident, "");
	}

	public synchronized ObjectPrx addFacet(Object object, Identity ident, String facet)
	{
		checkForDeactivation();
		checkIdentity(ident);
		Identity id = new Identity();
		id.category = ident.category;
		id.name = ident.name;
		_servantManager.addServant(object, id, facet);
		return newProxy(id, facet);
	}

	public ObjectPrx addWithUUID(Object object)
	{
		return addFacetWithUUID(object, "");
	}

	public ObjectPrx addFacetWithUUID(Object object, String facet)
	{
		Identity ident = new Identity();
		ident.category = "";
		ident.name = UUID.randomUUID().toString();
		return addFacet(object, ident, facet);
	}

	public synchronized void addDefaultServant(Object servant, String category)
	{
		checkForDeactivation();
		_servantManager.addDefaultServant(servant, category);
	}

	public Object remove(Identity ident)
	{
		return removeFacet(ident, "");
	}

	public synchronized Object removeFacet(Identity ident, String facet)
	{
		checkForDeactivation();
		checkIdentity(ident);
		return _servantManager.removeServant(ident, facet);
	}

	public synchronized Map removeAllFacets(Identity ident)
	{
		checkForDeactivation();
		checkIdentity(ident);
		return _servantManager.removeAllFacets(ident);
	}

	public synchronized Object removeDefaultServant(String category)
	{
		checkForDeactivation();
		return _servantManager.removeDefaultServant(category);
	}

	public Object find(Identity ident)
	{
		return findFacet(ident, "");
	}

	public synchronized Object findFacet(Identity ident, String facet)
	{
		checkForDeactivation();
		checkIdentity(ident);
		return _servantManager.findServant(ident, facet);
	}

	public synchronized Map findAllFacets(Identity ident)
	{
		checkForDeactivation();
		checkIdentity(ident);
		return _servantManager.findAllFacets(ident);
	}

	public synchronized Object findByProxy(ObjectPrx proxy)
	{
		checkForDeactivation();
		Reference ref = ((ObjectPrxHelperBase)proxy).__reference();
		return findFacet(ref.getIdentity(), ref.getFacet());
	}

	public synchronized Object findDefaultServant(String category)
	{
		checkForDeactivation();
		return _servantManager.findDefaultServant(category);
	}

	public synchronized void addServantLocator(ServantLocator locator, String prefix)
	{
		checkForDeactivation();
		_servantManager.addServantLocator(locator, prefix);
	}

	public synchronized ServantLocator removeServantLocator(String prefix)
	{
		checkForDeactivation();
		return _servantManager.removeServantLocator(prefix);
	}

	public synchronized ServantLocator findServantLocator(String prefix)
	{
		checkForDeactivation();
		return _servantManager.findServantLocator(prefix);
	}

	public synchronized ObjectPrx createProxy(Identity ident)
	{
		checkForDeactivation();
		checkIdentity(ident);
		return newProxy(ident, "");
	}

	public synchronized ObjectPrx createDirectProxy(Identity ident)
	{
		checkForDeactivation();
		checkIdentity(ident);
		return newDirectProxy(ident, "");
	}

	public synchronized ObjectPrx createIndirectProxy(Identity ident)
	{
		checkForDeactivation();
		checkIdentity(ident);
		return newIndirectProxy(ident, "", _id);
	}

	public synchronized void setLocator(LocatorPrx locator)
	{
		checkForDeactivation();
		_locatorInfo = _instance.locatorManager().get(locator);
	}

	public void refreshPublishedEndpoints()
	{
		LocatorInfo locatorInfo;
		boolean registerProcess;
		List oldPublishedEndpoints;
		locatorInfo = null;
		registerProcess = false;
		synchronized (this)
		{
			checkForDeactivation();
			oldPublishedEndpoints = _publishedEndpoints;
			_publishedEndpoints = parsePublishedEndpoints();
			locatorInfo = _locatorInfo;
			if (!_noConfig)
				registerProcess = _instance.initializationData().properties.getPropertyAsInt((new StringBuilder()).append(_name).append(".RegisterProcess").toString()) > 0;
		}
		Identity dummy = new Identity();
		dummy.name = "dummy";
		updateLocatorRegistry(locatorInfo, createDirectProxy(dummy), registerProcess);
		break MISSING_BLOCK_LABEL_152;
		LocalException ex;
		ex;
		ObjectAdapterI objectadapteri1 = this;
		JVM INSTR monitorenter ;
		_publishedEndpoints = oldPublishedEndpoints;
		throw ex;
	}

	public synchronized Endpoint[] getEndpoints()
	{
		List endpoints = new ArrayList();
		IncomingConnectionFactory factory;
		for (Iterator i$ = _incomingConnectionFactories.iterator(); i$.hasNext(); endpoints.add(factory.endpoint()))
			factory = (IncomingConnectionFactory)i$.next();

		return (Endpoint[])endpoints.toArray(new Endpoint[0]);
	}

	public synchronized Endpoint[] getPublishedEndpoints()
	{
		return (Endpoint[])_publishedEndpoints.toArray(new Endpoint[0]);
	}

	public boolean isLocal(ObjectPrx proxy)
	{
		Reference ref;
		ref = ((ObjectPrxHelperBase)proxy).__reference();
		if (ref.isWellKnown())
			return _servantManager.hasServant(ref.getIdentity());
		if (ref.isIndirect())
			return ref.getAdapterId().equals(_id) || ref.getAdapterId().equals(_replicaGroupId);
		EndpointI endpoints[] = ref.getEndpoints();
		ObjectAdapterI objectadapteri = this;
		JVM INSTR monitorenter ;
		EndpointI arr$[];
		int len$;
		int i$;
		checkForDeactivation();
		arr$ = endpoints;
		len$ = arr$.length;
		i$ = 0;
_L8:
		EndpointI endpoint;
		Iterator i$;
		if (i$ >= len$)
			break MISSING_BLOCK_LABEL_218;
		endpoint = arr$[i$];
		i$ = _publishedEndpoints.iterator();
_L3:
		if (!i$.hasNext()) goto _L2; else goto _L1
_L1:
		EndpointI p = (EndpointI)i$.next();
		if (endpoint.equivalent(p))
			return true;
		  goto _L3
_L2:
		i$ = _incomingConnectionFactories.iterator();
_L7:
		if (!i$.hasNext()) goto _L5; else goto _L4
_L4:
		p = (IncomingConnectionFactory)i$.next();
		if (!endpoint.equivalent(p.endpoint())) goto _L7; else goto _L6
_L6:
		true;
		objectadapteri;
		JVM INSTR monitorexit ;
		return;
_L5:
		i$++;
		  goto _L8
		if (_routerInfo == null || !_routerInfo.getRouter().equals(proxy.ice_getRouter()))
			break MISSING_BLOCK_LABEL_326;
		arr$ = endpoints;
		len$ = arr$.length;
		i$ = 0;
_L13:
		if (i$ >= len$)
			break MISSING_BLOCK_LABEL_326;
		endpoint = arr$[i$];
		i$ = _routerEndpoints.iterator();
_L12:
		if (!i$.hasNext()) goto _L10; else goto _L9
_L9:
		p = (EndpointI)i$.next();
		if (!endpoint.equivalent(p)) goto _L12; else goto _L11
_L11:
		true;
		objectadapteri;
		JVM INSTR monitorexit ;
		return;
_L10:
		i$++;
		  goto _L13
		objectadapteri;
		JVM INSTR monitorexit ;
		  goto _L14
		Exception exception;
		exception;
		throw exception;
_L14:
		return false;
	}

	public void flushAsyncBatchRequests(CommunicatorBatchOutgoingAsync outAsync)
	{
		List f;
		synchronized (this)
		{
			f = new ArrayList(_incomingConnectionFactories);
		}
		IncomingConnectionFactory p;
		for (Iterator i$ = f.iterator(); i$.hasNext(); p.flushAsyncBatchRequests(outAsync))
			p = (IncomingConnectionFactory)i$.next();

	}

	public synchronized void incDirectCount()
	{
		checkForDeactivation();
		if (!$assertionsDisabled && _directCount < 0)
		{
			throw new AssertionError();
		} else
		{
			_directCount++;
			return;
		}
	}

	public synchronized void decDirectCount()
	{
		if (!$assertionsDisabled && _instance == null)
			throw new AssertionError();
		if (!$assertionsDisabled && _directCount <= 0)
			throw new AssertionError();
		if (--_directCount == 0)
			notifyAll();
	}

	public ThreadPool getThreadPool()
	{
		if (!$assertionsDisabled && _instance == null)
			throw new AssertionError();
		if (_threadPool != null)
			return _threadPool;
		else
			return _instance.serverThreadPool();
	}

	public ServantManager getServantManager()
	{
		return _servantManager;
	}

	public int getACM()
	{
		if (!$assertionsDisabled && _instance == null)
			throw new AssertionError();
		if (_hasAcmTimeout)
			return _acmTimeout;
		else
			return _instance.serverACM();
	}

	public ObjectAdapterI(Instance instance, Communicator communicator, ObjectAdapterFactory objectAdapterFactory, String name, RouterPrx router, boolean noConfig)
	{
		_incomingConnectionFactories = new ArrayList();
		_routerEndpoints = new ArrayList();
		_routerInfo = null;
		_publishedEndpoints = new ArrayList();
		_processId = null;
		_deactivated = false;
		_instance = instance;
		_communicator = communicator;
		_objectAdapterFactory = objectAdapterFactory;
		_hasAcmTimeout = false;
		_acmTimeout = 0;
		_servantManager = new ServantManager(instance, name);
		_activateOneOffDone = false;
		_name = name;
		_directCount = 0;
		_waitForActivate = false;
		_waitForHold = 0;
		_waitForHoldRetry = false;
		_destroying = false;
		_destroyed = false;
		_noConfig = noConfig;
		if (_noConfig)
		{
			_id = "";
			_replicaGroupId = "";
			_reference = _instance.referenceFactory().create("dummy -t", "");
			return;
		}
		Properties properties = _instance.initializationData().properties;
		List unknownProps = new ArrayList();
		boolean noProps = filterProperties(unknownProps);
		if (unknownProps.size() != 0 && properties.getPropertyAsIntWithDefault("Ice.Warn.UnknownProperties", 1) > 0)
		{
			StringBuffer message = new StringBuffer("found unknown properties for object adapter `");
			message.append(_name);
			message.append("':");
			String p;
			for (Iterator i$ = unknownProps.iterator(); i$.hasNext(); message.append(p))
			{
				p = (String)i$.next();
				message.append("\n    ");
			}

			_instance.initializationData().logger.warning(message.toString());
		}
		if (router == null && noProps)
		{
			_deactivated = true;
			_destroyed = true;
			_instance = null;
			_incomingConnectionFactories = null;
			InitializationException ex = new InitializationException();
			ex.reason = (new StringBuilder()).append("object adapter `").append(_name).append("' requires configuration").toString();
			throw ex;
		}
		_id = properties.getProperty((new StringBuilder()).append(_name).append(".AdapterId").toString());
		_replicaGroupId = properties.getProperty((new StringBuilder()).append(_name).append(".ReplicaGroupId").toString());
		String proxyOptions = properties.getPropertyWithDefault((new StringBuilder()).append(_name).append(".ProxyOptions").toString(), "-t");
		try
		{
			_reference = _instance.referenceFactory().create((new StringBuilder()).append("dummy ").append(proxyOptions).toString(), "");
		}
		catch (ProxyParseException e)
		{
			InitializationException ex = new InitializationException();
			ex.reason = (new StringBuilder()).append("invalid proxy options `").append(proxyOptions).append("' for object adapter `").append(_name).append("'").toString();
			throw ex;
		}
		try
		{
			int threadPoolSize = properties.getPropertyAsInt((new StringBuilder()).append(_name).append(".ThreadPool.Size").toString());
			int threadPoolSizeMax = properties.getPropertyAsInt((new StringBuilder()).append(_name).append(".ThreadPool.SizeMax").toString());
			if (threadPoolSize > 0 || threadPoolSizeMax > 0)
				_threadPool = new ThreadPool(_instance, (new StringBuilder()).append(_name).append(".ThreadPool").toString(), 0);
			_hasAcmTimeout = properties.getProperty((new StringBuilder()).append(_name).append(".ACM").toString()).length() > 0;
			if (_hasAcmTimeout)
			{
				_acmTimeout = properties.getPropertyAsInt((new StringBuilder()).append(_name).append(".ACM").toString());
				_instance.connectionMonitor().checkIntervalForACM(_acmTimeout);
			}
			if (router == null)
				router = RouterPrxHelper.uncheckedCast(_instance.proxyFactory().propertyToProxy((new StringBuilder()).append(name).append(".Router").toString()));
			if (router != null)
			{
				_routerInfo = _instance.routerManager().get(router);
				if (_routerInfo != null)
				{
					if (_routerInfo.getAdapter() != null)
						throw new AlreadyRegisteredException("object adapter with router", _instance.identityToString(router.ice_getIdentity()));
					EndpointI endpoints[] = _routerInfo.getServerEndpoints();
					EndpointI arr$[] = endpoints;
					int len$ = arr$.length;
					for (int i$ = 0; i$ < len$; i$++)
					{
						EndpointI endpoint = arr$[i$];
						_routerEndpoints.add(endpoint);
					}

					Collections.sort(_routerEndpoints);
					for (int i = 0; i < _routerEndpoints.size() - 1;)
					{
						EndpointI e1 = (EndpointI)_routerEndpoints.get(i);
						EndpointI e2 = (EndpointI)_routerEndpoints.get(i + 1);
						if (e1.equals(e2))
							_routerEndpoints.remove(i);
						else
							i++;
					}

					_routerInfo.setAdapter(this);
					_instance.outgoingConnectionFactory().setRouterInfo(_routerInfo);
				}
			} else
			{
				List endpoints = parseEndpoints(properties.getProperty((new StringBuilder()).append(_name).append(".Endpoints").toString()), true);
				IncomingConnectionFactory factory;
				for (Iterator i$ = endpoints.iterator(); i$.hasNext(); _incomingConnectionFactories.add(factory))
				{
					EndpointI endp = (EndpointI)i$.next();
					factory = new IncomingConnectionFactory(instance, endp, this, _name);
				}

				if (endpoints.size() == 0)
				{
					TraceLevels tl = _instance.traceLevels();
					if (tl.network >= 2)
						_instance.initializationData().logger.trace(tl.networkCat, (new StringBuilder()).append("created adapter `").append(name).append("' without endpoints").toString());
				}
				_publishedEndpoints = parsePublishedEndpoints();
			}
			if (properties.getProperty((new StringBuilder()).append(_name).append(".Locator").toString()).length() > 0)
				setLocator(LocatorPrxHelper.uncheckedCast(_instance.proxyFactory().propertyToProxy((new StringBuilder()).append(_name).append(".Locator").toString())));
			else
				setLocator(_instance.referenceFactory().getDefaultLocator());
		}
		catch (LocalException ex)
		{
			destroy();
			throw ex;
		}
	}

	protected synchronized void finalize()
		throws Throwable
	{
		if (!_deactivated)
			_instance.initializationData().logger.warning((new StringBuilder()).append("object adapter `").append(getName()).append("' has not been deactivated").toString());
		else
		if (!_destroyed)
		{
			_instance.initializationData().logger.warning((new StringBuilder()).append("object adapter `").append(getName()).append("' has not been destroyed").toString());
		} else
		{
			Assert.FinalizerAssert(_threadPool == null);
			Assert.FinalizerAssert(_directCount == 0);
			Assert.FinalizerAssert(!_waitForActivate);
		}
		super.finalize();
	}

	private ObjectPrx newProxy(Identity ident, String facet)
	{
		if (_id.length() == 0)
			return newDirectProxy(ident, facet);
		if (_replicaGroupId.length() == 0)
			return newIndirectProxy(ident, facet, _id);
		else
			return newIndirectProxy(ident, facet, _replicaGroupId);
	}

	private ObjectPrx newDirectProxy(Identity ident, String facet)
	{
		int sz = _publishedEndpoints.size();
		EndpointI endpoints[] = new EndpointI[sz + _routerEndpoints.size()];
		_publishedEndpoints.toArray(endpoints);
		for (int i = 0; i < _routerEndpoints.size(); i++)
			endpoints[sz + i] = (EndpointI)_routerEndpoints.get(i);

		Reference ref = _instance.referenceFactory().create(ident, facet, _reference, endpoints);
		return _instance.proxyFactory().referenceToProxy(ref);
	}

	private ObjectPrx newIndirectProxy(Identity ident, String facet, String id)
	{
		Reference ref = _instance.referenceFactory().create(ident, facet, _reference, id);
		return _instance.proxyFactory().referenceToProxy(ref);
	}

	private void checkForDeactivation()
	{
		if (_deactivated)
		{
			ObjectAdapterDeactivatedException ex = new ObjectAdapterDeactivatedException();
			ex.name = getName();
			throw ex;
		} else
		{
			return;
		}
	}

	private static void checkIdentity(Identity ident)
	{
		if (ident.name == null || ident.name.length() == 0)
		{
			IllegalIdentityException e = new IllegalIdentityException();
			e.id = (Identity)ident.clone();
			throw e;
		}
		if (ident.category == null)
			ident.category = "";
	}

	private List parseEndpoints(String endpts, boolean oaEndpoints)
	{
		int end = 0;
		String delim = " \t\n\r";
		List endpoints = new ArrayList();
		do
		{
			if (end >= endpts.length())
				break;
			int beg = StringUtil.findFirstNotOf(endpts, " \t\n\r", end);
			if (beg == -1)
				break;
			end = beg;
			do
			{
				end = endpts.indexOf(':', end);
				if (end == -1)
				{
					end = endpts.length();
					break;
				}
				boolean quoted = false;
				int quote = beg;
				do
				{
					quote = endpts.indexOf('"', quote);
					if (quote == -1 || end < quote)
						break;
					quote = endpts.indexOf('"', ++quote);
					if (quote == -1)
						break;
					if (end < quote)
					{
						quoted = true;
						break;
					}
					quote++;
				} while (true);
				if (!quoted)
					break;
				end++;
			} while (true);
			if (end == beg)
			{
				end++;
			} else
			{
				String s = endpts.substring(beg, end);
				EndpointI endp = _instance.endpointFactoryManager().create(s, oaEndpoints);
				if (endp == null)
				{
					EndpointParseException e = new EndpointParseException();
					e.str = (new StringBuilder()).append("invalid object adapter endpoint `").append(s).append("'").toString();
					throw e;
				}
				endpoints.add(endp);
				end++;
			}
		} while (true);
		return endpoints;
	}

	private List parsePublishedEndpoints()
	{
		String endpts = _instance.initializationData().properties.getProperty((new StringBuilder()).append(_name).append(".PublishedEndpoints").toString());
		List endpoints = parseEndpoints(endpts, false);
		if (endpoints.isEmpty())
		{
			IncomingConnectionFactory factory;
			for (Iterator i$ = _incomingConnectionFactories.iterator(); i$.hasNext(); endpoints.addAll(factory.endpoint().expand()))
				factory = (IncomingConnectionFactory)i$.next();

		}
		if (_instance.traceLevels().network >= 1)
		{
			StringBuffer s = new StringBuffer("published endpoints for object adapter `");
			s.append(_name);
			s.append("':\n");
			boolean first = true;
			for (Iterator i$ = endpoints.iterator(); i$.hasNext();)
			{
				EndpointI endpoint = (EndpointI)i$.next();
				if (!first)
					s.append(":");
				s.append(endpoint.toString());
				first = false;
			}

			_instance.initializationData().logger.trace(_instance.traceLevels().networkCat, s.toString());
		}
		return endpoints;
	}

	private void updateLocatorRegistry(LocatorInfo locatorInfo, ObjectPrx proxy, boolean registerProcess)
	{
		if (!registerProcess && _id.length() == 0)
			return;
		LocatorRegistryPrx locatorRegistry = locatorInfo == null ? null : locatorInfo.getLocatorRegistry();
		String serverId = "";
		if (registerProcess)
		{
			if (!$assertionsDisabled && _instance == null)
				throw new AssertionError();
			serverId = _instance.initializationData().properties.getProperty("Ice.ServerId");
			if (locatorRegistry == null)
				_instance.initializationData().logger.warning((new StringBuilder()).append("object adapter `").append(getName()).append("' cannot register the process without a locator registry").toString());
			else
			if (serverId.length() == 0)
				_instance.initializationData().logger.warning((new StringBuilder()).append("object adapter `").append(getName()).append("' cannot register the process without a value for Ice.ServerId").toString());
		}
		if (locatorRegistry == null)
			return;
		if (_id.length() > 0)
		{
			try
			{
				if (_replicaGroupId.length() == 0)
					locatorRegistry.setAdapterDirectProxy(_id, proxy);
				else
					locatorRegistry.setReplicatedAdapterDirectProxy(_id, _replicaGroupId, proxy);
			}
			catch (AdapterNotFoundException ex)
			{
				if (_instance.traceLevels().location >= 1)
				{
					StringBuilder s = new StringBuilder(128);
					s.append("couldn't update object adapter `");
					s.append(_id);
					s.append("' endpoints with the locator registry:\n");
					s.append("the object adapter is not known to the locator registry");
					_instance.initializationData().logger.trace(_instance.traceLevels().locationCat, s.toString());
				}
				NotRegisteredException ex1 = new NotRegisteredException();
				ex1.kindOfObject = "object adapter";
				ex1.id = _id;
				throw ex1;
			}
			catch (InvalidReplicaGroupIdException ex)
			{
				if (_instance.traceLevels().location >= 1)
				{
					StringBuilder s = new StringBuilder(128);
					s.append("couldn't update object adapter `");
					s.append(_id);
					s.append("' endpoints with the locator registry:\n");
					s.append("the replica group `");
					s.append(_replicaGroupId);
					s.append("' is not known to the locator registry");
					_instance.initializationData().logger.trace(_instance.traceLevels().locationCat, s.toString());
				}
				NotRegisteredException ex1 = new NotRegisteredException();
				ex1.kindOfObject = "replica group";
				ex1.id = _replicaGroupId;
				throw ex1;
			}
			catch (AdapterAlreadyActiveException ex)
			{
				if (_instance.traceLevels().location >= 1)
				{
					StringBuilder s = new StringBuilder(128);
					s.append("couldn't update object adapter `");
					s.append(_id);
					s.append("' endpoints with the locator registry:\n");
					s.append("the object adapter endpoints are already set");
					_instance.initializationData().logger.trace(_instance.traceLevels().locationCat, s.toString());
				}
				ObjectAdapterIdInUseException ex1 = new ObjectAdapterIdInUseException();
				ex1.id = _id;
				throw ex1;
			}
			catch (LocalException e)
			{
				if (_instance.traceLevels().location >= 1)
				{
					StringBuilder s = new StringBuilder(128);
					s.append("couldn't update object adapter `");
					s.append(_id);
					s.append("' endpoints with the locator registry:\n");
					s.append(e.toString());
					_instance.initializationData().logger.trace(_instance.traceLevels().locationCat, s.toString());
				}
				throw e;
			}
			if (_instance.traceLevels().location >= 1)
			{
				StringBuilder s = new StringBuilder(128);
				s.append("updated object adapter `");
				s.append(_id);
				s.append("' endpoints with the locator registry\n");
				s.append("endpoints = ");
				if (proxy != null)
				{
					Endpoint endpoints[] = proxy.ice_getEndpoints();
					for (int i = 0; i < endpoints.length; i++)
					{
						s.append(endpoints[i].toString());
						if (i + 1 < endpoints.length)
							s.append(":");
					}

				}
				_instance.initializationData().logger.trace(_instance.traceLevels().locationCat, s.toString());
			}
		}
		if (registerProcess && serverId.length() > 0)
		{
			synchronized (this)
			{
				if (_processId == null)
				{
					Process servant = new ProcessI(_communicator);
					_processId = addWithUUID(servant).ice_getIdentity();
				}
			}
			try
			{
				locatorRegistry.setServerProcessProxy(serverId, ProcessPrxHelper.uncheckedCast(createDirectProxy(_processId)));
			}
			catch (ServerNotFoundException ex)
			{
				if (_instance.traceLevels().location >= 1)
				{
					StringBuilder s = new StringBuilder(128);
					s.append("couldn't register server `");
					s.append(serverId);
					s.append("' with the locator registry:\n");
					s.append("the server is not known to the locator registry");
					_instance.initializationData().logger.trace(_instance.traceLevels().locationCat, s.toString());
				}
				NotRegisteredException ex1 = new NotRegisteredException();
				ex1.id = serverId;
				ex1.kindOfObject = "server";
				throw ex1;
			}
			catch (LocalException ex)
			{
				if (_instance.traceLevels().location >= 1)
				{
					StringBuilder s = new StringBuilder(128);
					s.append("couldn't register server `");
					s.append(serverId);
					s.append("' with the locator registry:\n");
					s.append(ex.toString());
					_instance.initializationData().logger.trace(_instance.traceLevels().locationCat, s.toString());
				}
				throw ex;
			}
			if (_instance.traceLevels().location >= 1)
			{
				StringBuilder s = new StringBuilder(128);
				s.append("registered server `");
				s.append(serverId);
				s.append("' with the locator registry");
				_instance.initializationData().logger.trace(_instance.traceLevels().locationCat, s.toString());
			}
		}
	}

	boolean filterProperties(List unknownProps)
	{
		boolean addUnknown = true;
		String prefix = (new StringBuilder()).append(_name).append(".").toString();
		int i = 0;
		do
		{
			if (PropertyNames.clPropNames[i] == null)
				break;
			if (prefix.startsWith((new StringBuilder()).append(PropertyNames.clPropNames[i]).append(".").toString()))
			{
				addUnknown = false;
				break;
			}
			i++;
		} while (true);
		boolean noProps = true;
		Map props = _instance.initializationData().properties.getPropertiesForPrefix(prefix);
		Iterator i$ = props.keySet().iterator();
		do
		{
			if (!i$.hasNext())
				break;
			String prop = (String)i$.next();
			boolean valid = false;
			String arr$[] = _suffixes;
			int len$ = arr$.length;
			int i$ = 0;
			do
			{
				if (i$ >= len$)
					break;
				String suffix = arr$[i$];
				if (prop.equals((new StringBuilder()).append(prefix).append(suffix).toString()))
				{
					noProps = false;
					valid = true;
					break;
				}
				i$++;
			} while (true);
			if (!valid && addUnknown)
				unknownProps.add(prop);
		} while (true);
		return noProps;
	}

}
