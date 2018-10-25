// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ServiceManagerI.java

package IceBox;

import Ice.Application;
import Ice.Communicator;
import Ice.CommunicatorDestroyedException;
import Ice.Current;
import Ice.Identity;
import Ice.InitializationData;
import Ice.LocalException;
import Ice.Logger;
import Ice.ObjectAdapter;
import Ice.ObjectAdapterDeactivatedException;
import Ice.Properties;
import Ice.StringSeqHolder;
import Ice.Util;
import Ice._PropertiesAdminDisp;
import IceUtilInternal.Options;
import IceUtilInternal.StringUtil;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

// Referenced classes of package IceBox:
//			_ServiceManagerDisp, AlreadyStartedException, NoSuchServiceException, AlreadyStoppedException, 
//			FailureException, Service, ServiceObserverPrx, SliceChecksums, 
//			AMI_ServiceObserver_servicesStarted, AMI_ServiceObserver_servicesStopped

public class ServiceManagerI extends _ServiceManagerDisp
{
	static class StartServiceInfo
	{

		String name;
		String args[];
		String className;

		StartServiceInfo(String service, String value, String serverArgs[])
		{
			name = service;
			int pos = StringUtil.findFirstOf(value, " \t\n");
			if (pos == -1)
			{
				className = value;
				args = new String[0];
			} else
			{
				className = value.substring(0, pos);
				try
				{
					args = Options.split(value.substring(pos));
				}
				catch (IceUtilInternal.Options.BadQuote ex)
				{
					FailureException e = new FailureException();
					e.reason = (new StringBuilder()).append("ServiceManager: invalid arguments for service `").append(name).append("':\n").append(ex.toString()).toString();
					throw e;
				}
			}
			if (serverArgs.length > 0)
			{
				List l = new ArrayList(Arrays.asList(args));
				String arr$[] = serverArgs;
				int len$ = arr$.length;
				for (int i$ = 0; i$ < len$; i$++)
				{
					String arg = arr$[i$];
					if (arg.startsWith((new StringBuilder()).append("--").append(service).append(".").toString()))
						l.add(arg);
				}

				args = (String[])l.toArray(args);
			}
		}
	}

	static class PropertiesAdminI extends _PropertiesAdminDisp
	{

		private final Properties _properties;

		public String getProperty(String name, Current current)
		{
			return _properties.getProperty(name);
		}

		public TreeMap getPropertiesForPrefix(String name, Current current)
		{
			return new TreeMap(_properties.getPropertiesForPrefix(name));
		}

		public volatile Map getPropertiesForPrefix(String x0, Current x1)
		{
			return getPropertiesForPrefix(x0, x1);
		}

		PropertiesAdminI(Properties properties)
		{
			_properties = properties;
		}
	}

	static class ServiceInfo
		implements Cloneable
	{

		public String name;
		public Service service;
		public Communicator communicator;
		public int status;
		public String args[];

		public Object clone()
		{
			Object o = null;
			try
			{
				o = super.clone();
			}
			catch (CloneNotSupportedException ex) { }
			return o;
		}

		ServiceInfo()
		{
			communicator = null;
		}
	}


	public static final int StatusStopping = 0;
	public static final int StatusStopped = 1;
	public static final int StatusStarting = 2;
	public static final int StatusStarted = 3;
	private Communicator _communicator;
	private Communicator _sharedCommunicator;
	private Logger _logger;
	private String _argv[];
	private List _services;
	private boolean _pendingStatusChanges;
	HashSet _observers;
	int _traceServiceObserver;
	static final boolean $assertionsDisabled = !IceBox/ServiceManagerI.desiredAssertionStatus();

	public ServiceManagerI(Communicator communicator, String args[])
	{
		_services = new LinkedList();
		_pendingStatusChanges = false;
		_observers = new HashSet();
		_traceServiceObserver = 0;
		_communicator = communicator;
		_logger = _communicator.getLogger();
		_argv = args;
		_traceServiceObserver = _communicator.getProperties().getPropertyAsInt("IceBox.Trace.ServiceObserver");
	}

	public Map getSliceChecksums(Current current)
	{
		return SliceChecksums.checksums;
	}

	public void startService(String name, Current current)
		throws AlreadyStartedException, NoSuchServiceException
	{
		ServiceInfo info = null;
		synchronized (this)
		{
			Iterator i$ = _services.iterator();
			do
			{
				if (!i$.hasNext())
					break;
				ServiceInfo p = (ServiceInfo)i$.next();
				if (!p.name.equals(name))
					continue;
				if (p.status == 3)
					throw new AlreadyStartedException();
				p.status = 2;
				info = (ServiceInfo)p.clone();
				break;
			} while (true);
			if (info == null)
				throw new NoSuchServiceException();
			_pendingStatusChanges = true;
		}
		boolean started = false;
		try
		{
			info.service.start(name, info.communicator != null ? info.communicator : _sharedCommunicator, info.args);
			started = true;
		}
		catch (Exception e)
		{
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			pw.flush();
			_logger.warning((new StringBuilder()).append("ServiceManager: exception while starting service ").append(info.name).append(":\n").append(sw.toString()).toString());
		}
		synchronized (this)
		{
			Iterator i$ = _services.iterator();
			do
			{
				if (!i$.hasNext())
					break;
				ServiceInfo p = (ServiceInfo)i$.next();
				if (!p.name.equals(name))
					continue;
				if (started)
				{
					p.status = 3;
					List services = new ArrayList();
					services.add(name);
					servicesStarted(services, _observers);
				} else
				{
					p.status = 1;
				}
				break;
			} while (true);
			_pendingStatusChanges = false;
			notifyAll();
		}
	}

	public void stopService(String name, Current current)
		throws AlreadyStoppedException, NoSuchServiceException
	{
		ServiceInfo info = null;
		synchronized (this)
		{
			Iterator i$ = _services.iterator();
			do
			{
				if (!i$.hasNext())
					break;
				ServiceInfo p = (ServiceInfo)i$.next();
				if (!p.name.equals(name))
					continue;
				if (p.status == 1)
					throw new AlreadyStoppedException();
				p.status = 0;
				info = (ServiceInfo)p.clone();
				break;
			} while (true);
			if (info == null)
				throw new NoSuchServiceException();
			_pendingStatusChanges = true;
		}
		boolean stopped = false;
		try
		{
			info.service.stop();
			stopped = true;
		}
		catch (Exception e)
		{
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			pw.flush();
			_logger.warning((new StringBuilder()).append("ServiceManager: exception while stopping service ").append(info.name).append(":\n").append(sw.toString()).toString());
		}
		synchronized (this)
		{
			Iterator i$ = _services.iterator();
			do
			{
				if (!i$.hasNext())
					break;
				ServiceInfo p = (ServiceInfo)i$.next();
				if (!p.name.equals(name))
					continue;
				if (stopped)
				{
					p.status = 1;
					List services = new ArrayList();
					services.add(name);
					servicesStopped(services, _observers);
				} else
				{
					p.status = 3;
				}
				break;
			} while (true);
			_pendingStatusChanges = false;
			notifyAll();
		}
	}

	public void addObserver(final ServiceObserverPrx observer, Current current)
	{
		List activeServices = new LinkedList();
		synchronized (this)
		{
			if (observer != null && _observers.add(observer))
			{
				if (_traceServiceObserver >= 1)
					_logger.trace("IceBox.ServiceObserver", (new StringBuilder()).append("Added service observer ").append(_communicator.proxyToString(observer)).toString());
				Iterator i$ = _services.iterator();
				do
				{
					if (!i$.hasNext())
						break;
					ServiceInfo info = (ServiceInfo)i$.next();
					if (info.status == 3)
						activeServices.add(info.name);
				} while (true);
			}
		}
		if (activeServices.size() > 0)
		{
			AMI_ServiceObserver_servicesStarted cb = new AMI_ServiceObserver_servicesStarted() {

				final ServiceObserverPrx val$observer;
				final ServiceManagerI this$0;

				public void ice_response()
				{
				}

				public void ice_exception(LocalException ex)
				{
					removeObserver(observer, ex);
				}

			
			{
				this$0 = ServiceManagerI.this;
				observer = serviceobserverprx;
				super();
			}
			};
			observer.servicesStarted_async(cb, (String[])activeServices.toArray(new String[0]));
		}
	}

	public void shutdown(Current current)
	{
		_communicator.shutdown();
	}

	public int run()
	{
		try
		{
			Properties properties = _communicator.getProperties();
			ObjectAdapter adapter = null;
			if (!properties.getProperty("IceBox.ServiceManager.Endpoints").equals(""))
			{
				adapter = _communicator.createObjectAdapter("IceBox.ServiceManager");
				Identity identity = new Identity();
				identity.category = properties.getPropertyWithDefault("IceBox.InstanceName", "IceBox");
				identity.name = "ServiceManager";
				adapter.add(this, identity);
			}
			String prefix = "IceBox.Service.";
			Map services = properties.getPropertiesForPrefix("IceBox.Service.");
			String loadOrder[] = properties.getPropertyAsList("IceBox.LoadOrder");
			List servicesInfo = new ArrayList();
			String arr$[] = loadOrder;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				String name = arr$[i$];
				if (name.length() <= 0)
					continue;
				String key = (new StringBuilder()).append("IceBox.Service.").append(name).toString();
				String value = (String)services.get(key);
				if (value == null)
				{
					FailureException ex = new FailureException();
					ex.reason = (new StringBuilder()).append("ServiceManager: no service definition for `").append(name).append("'").toString();
					throw ex;
				}
				servicesInfo.add(new StartServiceInfo(name, value, _argv));
				services.remove(key);
			}

			String name;
			String value;
			for (Iterator i$ = services.entrySet().iterator(); i$.hasNext(); servicesInfo.add(new StartServiceInfo(name, value, _argv)))
			{
				java.util.Map.Entry p = (java.util.Map.Entry)i$.next();
				name = ((String)p.getKey()).substring("IceBox.Service.".length());
				value = (String)p.getValue();
			}

			if (properties.getPropertiesForPrefix("IceBox.UseSharedCommunicator.").size() > 0)
			{
				InitializationData initData = new InitializationData();
				initData.properties = createServiceProperties("SharedCommunicator");
				Iterator i$ = servicesInfo.iterator();
				do
				{
					if (!i$.hasNext())
						break;
					StartServiceInfo service = (StartServiceInfo)i$.next();
					if (properties.getPropertyAsInt((new StringBuilder()).append("IceBox.UseSharedCommunicator.").append(service.name).toString()) > 0)
					{
						StringSeqHolder serviceArgs = new StringSeqHolder(service.args);
						Properties svcProperties = Util.createProperties(serviceArgs, initData.properties);
						service.args = serviceArgs.value;
						Map allProps = initData.properties.getPropertiesForPrefix("");
						Iterator i$ = allProps.keySet().iterator();
						do
						{
							if (!i$.hasNext())
								break;
							String key = (String)i$.next();
							if (svcProperties.getProperty(key).length() == 0)
								initData.properties.setProperty(key, "");
						} while (true);
						java.util.Map.Entry p;
						for (i$ = svcProperties.getPropertiesForPrefix("").entrySet().iterator(); i$.hasNext(); initData.properties.setProperty((String)p.getKey(), (String)p.getValue()))
							p = (java.util.Map.Entry)i$.next();

						service.args = initData.properties.parseCommandLineOptions(service.name, service.args);
					}
				} while (true);
				_sharedCommunicator = Util.initialize(initData);
			}
			StartServiceInfo s;
			for (Iterator i$ = servicesInfo.iterator(); i$.hasNext(); start(s.name, s.className, s.args))
				s = (StartServiceInfo)i$.next();

			String bundleName = properties.getProperty("IceBox.PrintServicesReady");
			if (bundleName.length() > 0)
				System.out.println((new StringBuilder()).append(bundleName).append(" ready").toString());
			Application.shutdownOnInterrupt();
			try
			{
				_communicator.addAdminFacet(this, "IceBox.ServiceManager");
				ServiceInfo info;
				Communicator communicator;
				for (Iterator i$ = _services.iterator(); i$.hasNext(); _communicator.addAdminFacet(new PropertiesAdminI(communicator.getProperties()), (new StringBuilder()).append("IceBox.Service.").append(info.name).append(".Properties").toString()))
				{
					info = (ServiceInfo)i$.next();
					communicator = info.communicator == null ? _sharedCommunicator : info.communicator;
				}

				_communicator.getAdmin();
			}
			catch (ObjectAdapterDeactivatedException ex) { }
			if (adapter != null)
				try
				{
					adapter.activate();
				}
				catch (ObjectAdapterDeactivatedException ex) { }
			_communicator.waitForShutdown();
			Application.defaultInterrupt();
			stopAll();
		}
		catch (FailureException ex)
		{
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			pw.println(ex.reason);
			ex.printStackTrace(pw);
			pw.flush();
			_logger.error(sw.toString());
			stopAll();
			return 1;
		}
		catch (Throwable ex)
		{
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			pw.flush();
			_logger.error((new StringBuilder()).append("ServiceManager: caught exception:\n").append(sw.toString()).toString());
			stopAll();
			return 1;
		}
		return 0;
	}

	private synchronized void start(String service, String className, String args[])
		throws FailureException
	{
		ServiceInfo info = new ServiceInfo();
		info.name = service;
		info.status = 1;
		info.args = args;
		try
		{
			Class c = IceInternal.Util.findClass(className, null);
			if (c == null)
			{
				FailureException e = new FailureException();
				e.reason = (new StringBuilder()).append("ServiceManager: class ").append(className).append(" not found").toString();
				throw e;
			}
			Object obj = null;
			try
			{
				Constructor con = c.getDeclaredConstructor(new Class[] {
					Ice/Communicator
				});
				obj = con.newInstance(new Object[] {
					_communicator
				});
			}
			catch (IllegalAccessException ex)
			{
				throw new FailureException((new StringBuilder()).append("ServiceManager: unable to access service constructor ").append(className).append("(Ice.Communicator)").toString(), ex);
			}
			catch (NoSuchMethodException ex) { }
			catch (InvocationTargetException ex)
			{
				if (ex.getCause() != null)
					throw ex.getCause();
				else
					throw new FailureException((new StringBuilder()).append("ServiceManager: exception in service constructor for ").append(className).toString(), ex);
			}
			if (obj == null)
				try
				{
					obj = c.newInstance();
				}
				catch (IllegalAccessException ex)
				{
					throw new FailureException((new StringBuilder()).append("ServiceManager: unable to access default service constructor in class ").append(className).toString(), ex);
				}
			try
			{
				info.service = (Service)obj;
			}
			catch (ClassCastException ex)
			{
				throw new FailureException((new StringBuilder()).append("ServiceManager: class ").append(className).append(" does not implement IceBox.Service").toString());
			}
		}
		catch (InstantiationException ex)
		{
			throw new FailureException((new StringBuilder()).append("ServiceManager: unable to instantiate class ").append(className).toString(), ex);
		}
		catch (FailureException ex)
		{
			throw ex;
		}
		catch (Throwable ex)
		{
			throw new FailureException((new StringBuilder()).append("ServiceManager: exception in service constructor for ").append(className).toString(), ex);
		}
		try
		{
			Communicator communicator;
			if (_communicator.getProperties().getPropertyAsInt((new StringBuilder()).append("IceBox.UseSharedCommunicator.").append(service).toString()) > 0)
			{
				if (!$assertionsDisabled && _sharedCommunicator == null)
					throw new AssertionError();
				communicator = _sharedCommunicator;
			} else
			{
				InitializationData initData = new InitializationData();
				initData.properties = createServiceProperties(service);
				StringSeqHolder serviceArgs = new StringSeqHolder(info.args);
				if (serviceArgs.value.length > 0)
				{
					initData.properties = Util.createProperties(serviceArgs, initData.properties);
					serviceArgs.value = initData.properties.parseCommandLineOptions(service, serviceArgs.value);
				}
				initData.logger = _logger.cloneWithPrefix(initData.properties.getProperty("Ice.ProgramName"));
				info.communicator = Util.initialize(serviceArgs, initData);
				info.args = serviceArgs.value;
				communicator = info.communicator;
			}
			try
			{
				info.service.start(service, communicator, info.args);
				info.status = 3;
			}
			catch (Throwable ex)
			{
				if (info.communicator != null)
				{
					try
					{
						info.communicator.shutdown();
						info.communicator.waitForShutdown();
					}
					catch (CommunicatorDestroyedException e) { }
					catch (Exception e)
					{
						StringWriter sw = new StringWriter();
						PrintWriter pw = new PrintWriter(sw);
						e.printStackTrace(pw);
						pw.flush();
						_logger.warning((new StringBuilder()).append("ServiceManager: exception while shutting down communicator for service ").append(service).append(":\n").append(sw.toString()).toString());
					}
					try
					{
						info.communicator.destroy();
					}
					catch (Exception e)
					{
						StringWriter sw = new StringWriter();
						PrintWriter pw = new PrintWriter(sw);
						e.printStackTrace(pw);
						pw.flush();
						_logger.warning((new StringBuilder()).append("ServiceManager: exception while destroying communicator for service ").append(service).append(":\n").append(sw.toString()).toString());
					}
				}
				throw ex;
			}
			_services.add(info);
		}
		catch (FailureException ex)
		{
			throw ex;
		}
		catch (Throwable ex)
		{
			throw new FailureException((new StringBuilder()).append("ServiceManager: exception while starting service ").append(service).toString(), ex);
		}
	}

	private synchronized void stopAll()
	{
		while (_pendingStatusChanges) 
			try
			{
				wait();
			}
			catch (InterruptedException ex) { }
		List stoppedServices = new ArrayList();
		ListIterator p = _services.listIterator(_services.size());
		do
		{
			if (!p.hasPrevious())
				break;
			ServiceInfo info = (ServiceInfo)p.previous();
			if (info.status == 3)
				try
				{
					info.service.stop();
					info.status = 1;
					stoppedServices.add(info.name);
				}
				catch (Throwable e)
				{
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					e.printStackTrace(pw);
					pw.flush();
					_logger.warning((new StringBuilder()).append("ServiceManager: exception while stopping service ").append(info.name).append(":\n").append(sw.toString()).toString());
				}
			try
			{
				_communicator.removeAdminFacet((new StringBuilder()).append("IceBox.Service.").append(info.name).append(".Properties").toString());
			}
			catch (LocalException e) { }
			if (info.communicator != null)
			{
				try
				{
					info.communicator.shutdown();
					info.communicator.waitForShutdown();
				}
				catch (CommunicatorDestroyedException e) { }
				catch (Exception e)
				{
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					e.printStackTrace(pw);
					pw.flush();
					_logger.warning((new StringBuilder()).append("ServiceManager: exception while stopping service ").append(info.name).append(":\n").append(sw.toString()).toString());
				}
				try
				{
					info.communicator.destroy();
				}
				catch (Exception e)
				{
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					e.printStackTrace(pw);
					pw.flush();
					_logger.warning((new StringBuilder()).append("ServiceManager: exception while stopping service ").append(info.name).append(":\n").append(sw.toString()).toString());
				}
			}
		} while (true);
		if (_sharedCommunicator != null)
		{
			try
			{
				_sharedCommunicator.destroy();
			}
			catch (Exception e)
			{
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				pw.flush();
				_logger.warning((new StringBuilder()).append("ServiceManager: exception while destroying shared communicator:\n").append(sw.toString()).toString());
			}
			_sharedCommunicator = null;
		}
		_services.clear();
		servicesStopped(stoppedServices, _observers);
	}

	private void servicesStarted(List services, Set observers)
	{
		if (services.size() > 0)
		{
			String servicesArray[] = (String[])services.toArray(new String[0]);
			final ServiceObserverPrx observer;
			AMI_ServiceObserver_servicesStarted cb;
			for (Iterator i$ = observers.iterator(); i$.hasNext(); observer.servicesStarted_async(cb, servicesArray))
			{
				observer = (ServiceObserverPrx)i$.next();
				cb = new AMI_ServiceObserver_servicesStarted() {

					final ServiceObserverPrx val$observer;
					final ServiceManagerI this$0;

					public void ice_response()
					{
					}

					public void ice_exception(LocalException ex)
					{
						removeObserver(observer, ex);
					}

			
			{
				this$0 = ServiceManagerI.this;
				observer = serviceobserverprx;
				super();
			}
				};
			}

		}
	}

	private void servicesStopped(List services, Set observers)
	{
		if (services.size() > 0)
		{
			String servicesArray[] = (String[])services.toArray(new String[0]);
			final ServiceObserverPrx observer;
			AMI_ServiceObserver_servicesStopped cb;
			for (Iterator i$ = observers.iterator(); i$.hasNext(); observer.servicesStopped_async(cb, servicesArray))
			{
				observer = (ServiceObserverPrx)i$.next();
				cb = new AMI_ServiceObserver_servicesStopped() {

					final ServiceObserverPrx val$observer;
					final ServiceManagerI this$0;

					public void ice_response()
					{
					}

					public void ice_exception(LocalException ex)
					{
						removeObserver(observer, ex);
					}

			
			{
				this$0 = ServiceManagerI.this;
				observer = serviceobserverprx;
				super();
			}
				};
			}

		}
	}

	private synchronized void removeObserver(ServiceObserverPrx observer, LocalException ex)
	{
		if (_observers.remove(observer))
			observerRemoved(observer, ex);
	}

	private void observerRemoved(ServiceObserverPrx observer, RuntimeException ex)
	{
		if (_traceServiceObserver >= 1 && !(ex instanceof CommunicatorDestroyedException))
			_logger.trace("IceBox.ServiceObserver", (new StringBuilder()).append("Removed service observer ").append(_communicator.proxyToString(observer)).append("\nafter catching ").append(ex.toString()).toString());
	}

	private Properties createServiceProperties(String service)
	{
		Properties communicatorProperties = _communicator.getProperties();
		Properties properties;
		if (communicatorProperties.getPropertyAsInt("IceBox.InheritProperties") > 0)
		{
			properties = communicatorProperties._clone();
			properties.setProperty("Ice.Admin.Endpoints", "");
		} else
		{
			properties = Util.createProperties();
		}
		String programName = communicatorProperties.getProperty("Ice.ProgramName");
		if (programName.length() == 0)
			properties.setProperty("Ice.ProgramName", service);
		else
			properties.setProperty("Ice.ProgramName", (new StringBuilder()).append(programName).append("-").append(service).toString());
		return properties;
	}


}
