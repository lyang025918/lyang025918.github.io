// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Instance.java

package IceInternal;

import Ice.*;
import IceUtilInternal.Assert;
import java.io.*;
import java.util.*;

// Referenced classes of package IceInternal:
//			ThreadPool, TraceLevels, DefaultsAndOverrides, RouterManager, 
//			LocatorManager, ReferenceFactory, ProxyFactory, EndpointFactoryManager, 
//			TcpEndpointFactory, UdpEndpointFactory, OutgoingConnectionFactory, ObjectFactoryManager, 
//			ObjectAdapterFactory, RetryQueue, PropertiesAdminI, ProcessI, 
//			Timer, EndpointHostResolver, ConnectionMonitor, Util, 
//			Ex

public final class Instance
{

	private static final int StateActive = 0;
	private static final int StateDestroyInProgress = 1;
	private static final int StateDestroyed = 2;
	private int _state;
	private final InitializationData _initData;
	private final TraceLevels _traceLevels;
	private final DefaultsAndOverrides _defaultsAndOverrides;
	private final int _messageSizeMax;
	private final int _cacheMessageBuffers;
	private final int _clientACM;
	private final int _serverACM;
	private final ImplicitContextI _implicitContext;
	private RouterManager _routerManager;
	private LocatorManager _locatorManager;
	private ReferenceFactory _referenceFactory;
	private ProxyFactory _proxyFactory;
	private OutgoingConnectionFactory _outgoingConnectionFactory;
	private ConnectionMonitor _connectionMonitor;
	private ObjectFactoryManager _servantFactoryManager;
	private ObjectAdapterFactory _objectAdapterFactory;
	private int _protocolSupport;
	private ThreadPool _clientThreadPool;
	private ThreadPool _serverThreadPool;
	private EndpointHostResolver _endpointHostResolver;
	private RetryQueue _retryQueue;
	private Timer _timer;
	private EndpointFactoryManager _endpointFactoryManager;
	private PluginManager _pluginManager;
	private ObjectAdapter _adminAdapter;
	private Map _adminFacets;
	private Set _adminFacetFilter;
	private Identity _adminIdentity;
	private static boolean _oneOffDone = false;
	static final boolean $assertionsDisabled = !IceInternal/Instance.desiredAssertionStatus();

	public InitializationData initializationData()
	{
		return _initData;
	}

	public TraceLevels traceLevels()
	{
		if (!$assertionsDisabled && _traceLevels == null)
			throw new AssertionError();
		else
			return _traceLevels;
	}

	public DefaultsAndOverrides defaultsAndOverrides()
	{
		if (!$assertionsDisabled && _defaultsAndOverrides == null)
			throw new AssertionError();
		else
			return _defaultsAndOverrides;
	}

	public synchronized RouterManager routerManager()
	{
		if (_state == 2)
			throw new CommunicatorDestroyedException();
		if (!$assertionsDisabled && _routerManager == null)
			throw new AssertionError();
		else
			return _routerManager;
	}

	public synchronized LocatorManager locatorManager()
	{
		if (_state == 2)
			throw new CommunicatorDestroyedException();
		if (!$assertionsDisabled && _locatorManager == null)
			throw new AssertionError();
		else
			return _locatorManager;
	}

	public synchronized ReferenceFactory referenceFactory()
	{
		if (_state == 2)
			throw new CommunicatorDestroyedException();
		if (!$assertionsDisabled && _referenceFactory == null)
			throw new AssertionError();
		else
			return _referenceFactory;
	}

	public synchronized ProxyFactory proxyFactory()
	{
		if (_state == 2)
			throw new CommunicatorDestroyedException();
		if (!$assertionsDisabled && _proxyFactory == null)
			throw new AssertionError();
		else
			return _proxyFactory;
	}

	public synchronized OutgoingConnectionFactory outgoingConnectionFactory()
	{
		if (_state == 2)
			throw new CommunicatorDestroyedException();
		if (!$assertionsDisabled && _outgoingConnectionFactory == null)
			throw new AssertionError();
		else
			return _outgoingConnectionFactory;
	}

	public synchronized ConnectionMonitor connectionMonitor()
	{
		if (_state == 2)
			throw new CommunicatorDestroyedException();
		if (!$assertionsDisabled && _connectionMonitor == null)
			throw new AssertionError();
		else
			return _connectionMonitor;
	}

	public synchronized ObjectFactoryManager servantFactoryManager()
	{
		if (_state == 2)
			throw new CommunicatorDestroyedException();
		if (!$assertionsDisabled && _servantFactoryManager == null)
			throw new AssertionError();
		else
			return _servantFactoryManager;
	}

	public synchronized ObjectAdapterFactory objectAdapterFactory()
	{
		if (_state == 2)
			throw new CommunicatorDestroyedException();
		if (!$assertionsDisabled && _objectAdapterFactory == null)
			throw new AssertionError();
		else
			return _objectAdapterFactory;
	}

	public synchronized int protocolSupport()
	{
		if (_state == 2)
			throw new CommunicatorDestroyedException();
		else
			return _protocolSupport;
	}

	public synchronized ThreadPool clientThreadPool()
	{
		if (_state == 2)
			throw new CommunicatorDestroyedException();
		if (!$assertionsDisabled && _clientThreadPool == null)
			throw new AssertionError();
		else
			return _clientThreadPool;
	}

	public synchronized ThreadPool serverThreadPool()
	{
		if (_state == 2)
			throw new CommunicatorDestroyedException();
		if (_serverThreadPool == null)
		{
			int timeout = _initData.properties.getPropertyAsInt("Ice.ServerIdleTime");
			_serverThreadPool = new ThreadPool(this, "Ice.ThreadPool.Server", timeout);
		}
		return _serverThreadPool;
	}

	public synchronized EndpointHostResolver endpointHostResolver()
	{
		if (_state == 2)
			throw new CommunicatorDestroyedException();
		if (!$assertionsDisabled && _endpointHostResolver == null)
			throw new AssertionError();
		else
			return _endpointHostResolver;
	}

	public synchronized RetryQueue retryQueue()
	{
		if (_state == 2)
			throw new CommunicatorDestroyedException();
		if (!$assertionsDisabled && _retryQueue == null)
			throw new AssertionError();
		else
			return _retryQueue;
	}

	public synchronized Timer timer()
	{
		if (_state == 2)
			throw new CommunicatorDestroyedException();
		if (!$assertionsDisabled && _timer == null)
			throw new AssertionError();
		else
			return _timer;
	}

	public synchronized EndpointFactoryManager endpointFactoryManager()
	{
		if (_state == 2)
			throw new CommunicatorDestroyedException();
		if (!$assertionsDisabled && _endpointFactoryManager == null)
			throw new AssertionError();
		else
			return _endpointFactoryManager;
	}

	public synchronized PluginManager pluginManager()
	{
		if (_state == 2)
			throw new CommunicatorDestroyedException();
		if (!$assertionsDisabled && _pluginManager == null)
			throw new AssertionError();
		else
			return _pluginManager;
	}

	public int messageSizeMax()
	{
		return _messageSizeMax;
	}

	public int cacheMessageBuffers()
	{
		return _cacheMessageBuffers;
	}

	public int clientACM()
	{
		return _clientACM;
	}

	public int serverACM()
	{
		return _serverACM;
	}

	public ImplicitContextI getImplicitContext()
	{
		return _implicitContext;
	}

	public Identity stringToIdentity(String s)
	{
		return Util.stringToIdentity(s);
	}

	public String identityToString(Identity ident)
	{
		return Util.identityToString(ident);
	}

	public ObjectPrx getAdmin()
	{
		ObjectAdapter adapter;
		String serverId;
		adapter = null;
		serverId = null;
		LocatorPrx defaultLocator = null;
		Instance instance = this;
		JVM INSTR monitorenter ;
		if (_state == 2)
			throw new CommunicatorDestroyedException();
		String adminOA = "Ice.Admin";
		if (_adminAdapter != null)
			return _adminAdapter.createProxy(_adminIdentity);
		if (_initData.properties.getProperty("Ice.Admin.Endpoints").length() != 0) goto _L2; else goto _L1
_L1:
		null;
		instance;
		JVM INSTR monitorexit ;
		return;
_L2:
		serverId = _initData.properties.getProperty("Ice.Admin.ServerId");
		String instanceName = _initData.properties.getProperty("Ice.Admin.InstanceName");
		defaultLocator = _referenceFactory.getDefaultLocator();
		if (defaultLocator != null && serverId.length() > 0 || instanceName.length() > 0)
		{
			if (_adminIdentity == null)
			{
				if (instanceName.length() == 0)
					instanceName = UUID.randomUUID().toString();
				_adminIdentity = new Identity("admin", instanceName);
			}
			_adminAdapter = _objectAdapterFactory.createObjectAdapter("Ice.Admin", null);
			Map filteredFacets = new HashMap();
			for (Iterator i$ = _adminFacets.entrySet().iterator(); i$.hasNext();)
			{
				java.util.Map.Entry p = (java.util.Map.Entry)i$.next();
				if (_adminFacetFilter.isEmpty() || _adminFacetFilter.contains(p.getKey()))
					_adminAdapter.addFacet((Ice.Object)p.getValue(), _adminIdentity, (String)p.getKey());
				else
					filteredFacets.put(p.getKey(), p.getValue());
			}

			_adminFacets = filteredFacets;
			adapter = _adminAdapter;
		}
		instance;
		JVM INSTR monitorexit ;
		  goto _L3
		Exception exception;
		exception;
		throw exception;
_L3:
		if (adapter == null)
			return null;
		try
		{
			adapter.activate();
		}
		catch (LocalException ex)
		{
			adapter.destroy();
			synchronized (this)
			{
				_adminAdapter = null;
			}
			throw ex;
		}
		ObjectPrx admin = adapter.createProxy(_adminIdentity);
		if (defaultLocator != null && serverId.length() > 0)
		{
			Ice.ProcessPrx process = ProcessPrxHelper.uncheckedCast(admin.ice_facet("Process"));
			try
			{
				defaultLocator.getRegistry().setServerProcessProxy(serverId, process);
			}
			catch (ServerNotFoundException ex)
			{
				if (_traceLevels.location >= 1)
				{
					StringBuilder s = new StringBuilder(128);
					s.append("couldn't register server `");
					s.append(serverId);
					s.append("' with the locator registry:\n");
					s.append("the server is not known to the locator registry");
					_initData.logger.trace(_traceLevels.locationCat, s.toString());
				}
				throw new InitializationException((new StringBuilder()).append("Locator knows nothing about server '").append(serverId).append("'").toString());
			}
			catch (LocalException ex)
			{
				if (_traceLevels.location >= 1)
				{
					StringBuilder s = new StringBuilder(128);
					s.append("couldn't register server `");
					s.append(serverId);
					s.append("' with the locator registry:\n");
					s.append(ex.toString());
					_initData.logger.trace(_traceLevels.locationCat, s.toString());
				}
				throw ex;
			}
			if (_traceLevels.location >= 1)
			{
				StringBuilder s = new StringBuilder(128);
				s.append("registered server `");
				s.append(serverId);
				s.append("' with the locator registry");
				_initData.logger.trace(_traceLevels.locationCat, s.toString());
			}
		}
		return admin;
	}

	public synchronized void addAdminFacet(Ice.Object servant, String facet)
	{
		if (_state == 2)
			throw new CommunicatorDestroyedException();
		if (_adminAdapter == null || !_adminFacetFilter.isEmpty() && !_adminFacetFilter.contains(facet))
		{
			if (_adminFacets.get(facet) != null)
				throw new AlreadyRegisteredException("facet", facet);
			_adminFacets.put(facet, servant);
		} else
		{
			_adminAdapter.addFacet(servant, _adminIdentity, facet);
		}
	}

	public synchronized Ice.Object removeAdminFacet(String facet)
	{
		if (_state == 2)
			throw new CommunicatorDestroyedException();
		Ice.Object result = null;
		if (_adminAdapter == null || !_adminFacetFilter.isEmpty() && !_adminFacetFilter.contains(facet))
		{
			result = (Ice.Object)_adminFacets.remove(facet);
			if (result == null)
				throw new NotRegisteredException("facet", facet);
		} else
		{
			result = _adminAdapter.removeFacet(_adminIdentity, facet);
		}
		return result;
	}

	public synchronized void setDefaultLocator(LocatorPrx locator)
	{
		if (_state == 2)
		{
			throw new CommunicatorDestroyedException();
		} else
		{
			_referenceFactory = _referenceFactory.setDefaultLocator(locator);
			return;
		}
	}

	public synchronized void setDefaultRouter(RouterPrx router)
	{
		if (_state == 2)
		{
			throw new CommunicatorDestroyedException();
		} else
		{
			_referenceFactory = _referenceFactory.setDefaultRouter(router);
			return;
		}
	}

	public void setLogger(Logger logger)
	{
		_initData.logger = logger;
	}

	public void setThreadHook(ThreadNotification threadHook)
	{
		_initData.threadHook = threadHook;
	}

	public Class findClass(String className)
	{
		return Util.findClass(className, _initData.classLoader);
	}

	public Instance(Communicator communicator, InitializationData initData)
	{
		_adminFacets = new HashMap();
		_adminFacetFilter = new HashSet();
		_state = 0;
		_initData = initData;
		try
		{
			if (_initData.properties == null)
				_initData.properties = Util.createProperties();
			synchronized (IceInternal/Instance)
			{
				if (!_oneOffDone)
				{
					String stdOut = _initData.properties.getProperty("Ice.StdOut");
					String stdErr = _initData.properties.getProperty("Ice.StdErr");
					PrintStream outStream = null;
					if (stdOut.length() > 0)
					{
						System.out.close();
						try
						{
							outStream = new PrintStream(new FileOutputStream(stdOut, true));
						}
						catch (FileNotFoundException ex)
						{
							throw new FileException(0, stdOut, ex);
						}
						System.setOut(outStream);
					}
					if (stdErr.length() > 0)
					{
						System.err.close();
						if (stdErr.equals(stdOut))
							System.setErr(outStream);
						else
							try
							{
								System.setErr(new PrintStream(new FileOutputStream(stdErr, true)));
							}
							catch (FileNotFoundException ex)
							{
								throw new FileException(0, stdErr, ex);
							}
					}
					_oneOffDone = true;
				}
			}
			if (_initData.logger == null)
			{
				String logfile = _initData.properties.getProperty("Ice.LogFile");
				if (_initData.properties.getPropertyAsInt("Ice.UseSyslog") > 0)
				{
					if (logfile.length() != 0)
						throw new InitializationException("Both syslog and file logger cannot be enabled.");
					_initData.logger = new SysLoggerI(_initData.properties.getProperty("Ice.ProgramName"), _initData.properties.getPropertyWithDefault("Ice.SyslogFacility", "LOG_USER"));
				} else
				if (logfile.length() != 0)
					_initData.logger = new LoggerI(_initData.properties.getProperty("Ice.ProgramName"), logfile);
				else
					_initData.logger = Util.getProcessLogger();
			}
			validatePackages();
			_traceLevels = new TraceLevels(_initData.properties);
			_defaultsAndOverrides = new DefaultsAndOverrides(_initData.properties);
			int defaultMessageSizeMax = 1024;
			int num = _initData.properties.getPropertyAsIntWithDefault("Ice.MessageSizeMax", 1024);
			if (num < 1)
				_messageSizeMax = 0x100000;
			else
			if (num > 0x1fffff)
				_messageSizeMax = 0x7fffffff;
			else
				_messageSizeMax = num * 1024;
			_cacheMessageBuffers = _initData.properties.getPropertyAsIntWithDefault("Ice.CacheMessageBuffers", 2);
			_clientACM = _initData.properties.getPropertyAsIntWithDefault("Ice.ACM.Client", 60);
			_serverACM = _initData.properties.getPropertyAsInt("Ice.ACM.Server");
			_implicitContext = ImplicitContextI.create(_initData.properties.getProperty("Ice.ImplicitContext"));
			_routerManager = new RouterManager();
			_locatorManager = new LocatorManager(_initData.properties);
			_referenceFactory = new ReferenceFactory(this, communicator);
			_proxyFactory = new ProxyFactory(this);
			boolean ipv4 = _initData.properties.getPropertyAsIntWithDefault("Ice.IPv4", 1) > 0;
			boolean ipv6 = _initData.properties.getPropertyAsIntWithDefault("Ice.IPv6", 0) > 0;
			if (!ipv4 && !ipv6)
				throw new InitializationException("Both IPV4 and IPv6 support cannot be disabled.");
			if (ipv4 && ipv6)
				_protocolSupport = 2;
			else
			if (ipv4)
				_protocolSupport = 0;
			else
				_protocolSupport = 1;
			_endpointFactoryManager = new EndpointFactoryManager(this);
			EndpointFactory tcpEndpointFactory = new TcpEndpointFactory(this);
			_endpointFactoryManager.add(tcpEndpointFactory);
			EndpointFactory udpEndpointFactory = new UdpEndpointFactory(this);
			_endpointFactoryManager.add(udpEndpointFactory);
			_pluginManager = new PluginManagerI(communicator);
			_outgoingConnectionFactory = new OutgoingConnectionFactory(this);
			_servantFactoryManager = new ObjectFactoryManager();
			_objectAdapterFactory = new ObjectAdapterFactory(this, communicator);
			_retryQueue = new RetryQueue(this);
			String facetFilter[] = _initData.properties.getPropertyAsList("Ice.Admin.Facets");
			if (facetFilter.length > 0)
				_adminFacetFilter.addAll(Arrays.asList(facetFilter));
			_adminFacets.put("Properties", new PropertiesAdminI(_initData.properties));
			_adminFacets.put("Process", new ProcessI(communicator));
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
		Assert.FinalizerAssert(_state == 2);
		Assert.FinalizerAssert(_referenceFactory == null);
		Assert.FinalizerAssert(_proxyFactory == null);
		Assert.FinalizerAssert(_outgoingConnectionFactory == null);
		Assert.FinalizerAssert(_connectionMonitor == null);
		Assert.FinalizerAssert(_servantFactoryManager == null);
		Assert.FinalizerAssert(_objectAdapterFactory == null);
		Assert.FinalizerAssert(_clientThreadPool == null);
		Assert.FinalizerAssert(_serverThreadPool == null);
		Assert.FinalizerAssert(_endpointHostResolver == null);
		Assert.FinalizerAssert(_timer == null);
		Assert.FinalizerAssert(_routerManager == null);
		Assert.FinalizerAssert(_locatorManager == null);
		Assert.FinalizerAssert(_endpointFactoryManager == null);
		Assert.FinalizerAssert(_pluginManager == null);
		Assert.FinalizerAssert(_retryQueue == null);
		super.finalize();
	}

	public void finishSetup(StringSeqHolder args)
	{
		if (!$assertionsDisabled && _serverThreadPool != null)
			throw new AssertionError();
		PluginManagerI pluginManagerImpl = (PluginManagerI)_pluginManager;
		pluginManagerImpl.loadPlugins(args);
		try
		{
			_timer = new Timer(this);
			if (initializationData().properties.getProperty("Ice.ThreadPriority").length() > 0)
				_timer.setPriority(Util.getThreadPriorityProperty(initializationData().properties, "Ice"));
		}
		catch (RuntimeException ex)
		{
			String s = (new StringBuilder()).append("cannot create thread for timer:\n").append(Ex.toString(ex)).toString();
			_initData.logger.error(s);
			throw ex;
		}
		try
		{
			_endpointHostResolver = new EndpointHostResolver(this);
		}
		catch (RuntimeException ex)
		{
			String s = (new StringBuilder()).append("cannot create thread for endpoint host resolver:\n").append(Ex.toString(ex)).toString();
			_initData.logger.error(s);
			throw ex;
		}
		_clientThreadPool = new ThreadPool(this, "Ice.ThreadPool.Client", 0);
		RouterPrx router = RouterPrxHelper.uncheckedCast(_proxyFactory.propertyToProxy("Ice.Default.Router"));
		if (router != null)
			_referenceFactory = _referenceFactory.setDefaultRouter(router);
		LocatorPrx loc = LocatorPrxHelper.uncheckedCast(_proxyFactory.propertyToProxy("Ice.Default.Locator"));
		if (loc != null)
			_referenceFactory = _referenceFactory.setDefaultLocator(loc);
		int interval = _initData.properties.getPropertyAsInt("Ice.MonitorConnections");
		_connectionMonitor = new ConnectionMonitor(this, interval);
		_connectionMonitor.checkIntervalForACM(_clientACM);
		_connectionMonitor.checkIntervalForACM(_serverACM);
		if (_initData.properties.getPropertyAsIntWithDefault("Ice.InitPlugins", 1) > 0)
			pluginManagerImpl.initializePlugins();
		if (_initData.properties.getPropertyAsIntWithDefault("Ice.Admin.DelayCreation", 0) <= 0)
			getAdmin();
	}

	public void destroy()
	{
label0:
		{
			synchronized (this)
			{
				if (_state == 0)
					break label0;
			}
			return;
		}
		_state = 1;
		instance;
		JVM INSTR monitorexit ;
		  goto _L1
		exception;
		throw exception;
_L1:
		if (_objectAdapterFactory != null)
			_objectAdapterFactory.shutdown();
		if (_outgoingConnectionFactory != null)
			_outgoingConnectionFactory.destroy();
		if (_objectAdapterFactory != null)
			_objectAdapterFactory.destroy();
		if (_outgoingConnectionFactory != null)
			_outgoingConnectionFactory.waitUntilFinished();
		if (_retryQueue != null)
			_retryQueue.destroy();
		ThreadPool serverThreadPool = null;
		ThreadPool clientThreadPool = null;
		EndpointHostResolver endpointHostResolver = null;
		synchronized (this)
		{
			_objectAdapterFactory = null;
			_outgoingConnectionFactory = null;
			_retryQueue = null;
			if (_connectionMonitor != null)
			{
				_connectionMonitor.destroy();
				_connectionMonitor = null;
			}
			if (_serverThreadPool != null)
			{
				_serverThreadPool.destroy();
				serverThreadPool = _serverThreadPool;
				_serverThreadPool = null;
			}
			if (_clientThreadPool != null)
			{
				_clientThreadPool.destroy();
				clientThreadPool = _clientThreadPool;
				_clientThreadPool = null;
			}
			if (_endpointHostResolver != null)
			{
				_endpointHostResolver.destroy();
				endpointHostResolver = _endpointHostResolver;
				_endpointHostResolver = null;
			}
			if (_timer != null)
			{
				_timer._destroy();
				_timer = null;
			}
			if (_servantFactoryManager != null)
			{
				_servantFactoryManager.destroy();
				_servantFactoryManager = null;
			}
			if (_referenceFactory != null)
			{
				_referenceFactory.destroy();
				_referenceFactory = null;
			}
			_proxyFactory = null;
			if (_routerManager != null)
			{
				_routerManager.destroy();
				_routerManager = null;
			}
			if (_locatorManager != null)
			{
				_locatorManager.destroy();
				_locatorManager = null;
			}
			if (_endpointFactoryManager != null)
			{
				_endpointFactoryManager.destroy();
				_endpointFactoryManager = null;
			}
			if (_pluginManager != null)
			{
				_pluginManager.destroy();
				_pluginManager = null;
			}
			_adminAdapter = null;
			_adminFacets.clear();
			_state = 2;
		}
		if (clientThreadPool != null)
			clientThreadPool.joinWithAllThreads();
		if (serverThreadPool != null)
			serverThreadPool.joinWithAllThreads();
		if (endpointHostResolver != null)
			endpointHostResolver.joinWithThread();
		if (_initData.properties.getPropertyAsInt("Ice.Warn.UnusedProperties") > 0)
		{
			List unusedProperties = ((PropertiesI)_initData.properties).getUnusedProperties();
			if (unusedProperties.size() != 0)
			{
				StringBuffer message = new StringBuffer("The following properties were set but never read:");
				String p;
				for (Iterator i$ = unusedProperties.iterator(); i$.hasNext(); message.append(p))
				{
					p = (String)i$.next();
					message.append("\n    ");
				}

				_initData.logger.warning(message.toString());
			}
		}
		return;
	}

	private void validatePackages()
	{
		String prefix = "Ice.Package.";
		Map map = _initData.properties.getPropertiesForPrefix("Ice.Package.");
		Iterator i$ = map.entrySet().iterator();
		do
		{
			if (!i$.hasNext())
				break;
			java.util.Map.Entry p = (java.util.Map.Entry)i$.next();
			String key = (String)p.getKey();
			String pkg = (String)p.getValue();
			if (key.length() == "Ice.Package.".length())
				_initData.logger.warning((new StringBuilder()).append("ignoring invalid property: ").append(key).append("=").append(pkg).toString());
			String module = key.substring("Ice.Package.".length());
			String className = (new StringBuilder()).append(pkg).append(".").append(module).append("._Marker").toString();
			Class cls = null;
			try
			{
				cls = findClass(className);
			}
			catch (Exception ex) { }
			if (cls == null)
				_initData.logger.warning((new StringBuilder()).append("unable to validate package: ").append(key).append("=").append(pkg).toString());
		} while (true);
	}

}
