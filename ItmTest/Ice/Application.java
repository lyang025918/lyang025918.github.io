// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Application.java

package Ice;

import IceInternal.Ex;

// Referenced classes of package Ice:
//			InitializationData, LoggerI, LocalException, StringSeqHolder, 
//			Util, Properties, Logger, SignalPolicy, 
//			Communicator

public abstract class Application
{
	static class CustomHook extends AppHook
	{

		private Thread _hook;

		public void run()
		{
label0:
			{
				synchronized (_doneMutex)
				{
					if (Application.setCallbackInProgress(false))
						break label0;
				}
				return;
			}
			_hook.run();
			Application.clearCallbackInProgress();
			obj;
			JVM INSTR monitorexit ;
			  goto _L1
			exception;
			throw exception;
_L1:
		}

		CustomHook(Thread hook)
		{
			_hook = hook;
		}
	}

	static class ShutdownHook extends AppHook
	{

		public void run()
		{
label0:
			{
				synchronized (_doneMutex)
				{
					if (Application.setCallbackInProgress(false))
						break label0;
				}
				return;
			}
			Communicator communicator = Application.communicator();
			if (communicator != null)
				communicator.shutdown();
			Application.clearCallbackInProgress();
			while (!_done) 
				try
				{
					_doneMutex.wait();
				}
				catch (InterruptedException ex) { }
			obj;
			JVM INSTR monitorexit ;
			  goto _L1
			exception;
			throw exception;
_L1:
		}

		ShutdownHook()
		{
		}
	}

	static class DestroyHook extends AppHook
	{

		public void run()
		{
label0:
			{
				synchronized (_doneMutex)
				{
					if (Application.setCallbackInProgress(true))
						break label0;
				}
				return;
			}
			Communicator communicator = Application.communicator();
			if (communicator != null)
				communicator.destroy();
			Application.clearCallbackInProgress();
			while (!_done) 
				try
				{
					_doneMutex.wait();
				}
				catch (InterruptedException ex) { }
			obj;
			JVM INSTR monitorexit ;
			  goto _L1
			exception;
			throw exception;
_L1:
		}

		DestroyHook()
		{
		}
	}

	public static class AppHook extends Thread
	{

		protected boolean _done;
		protected Object _doneMutex;

		public void done()
		{
			synchronized (_doneMutex)
			{
				_done = true;
				_doneMutex.notify();
			}
		}

		public AppHook()
		{
			_done = false;
			_doneMutex = new Object();
		}
	}


	protected static String _appName;
	protected static Communicator _communicator;
	protected static AppHook _appHook;
	protected static Object _mutex = new Object();
	protected static boolean _callbackInProgress = false;
	protected static boolean _destroyed = false;
	protected static boolean _interrupted = false;
	protected static SignalPolicy _signalPolicy;

	public Application()
	{
	}

	public Application(SignalPolicy signalPolicy)
	{
		_signalPolicy = signalPolicy;
	}

	public final int main(String appName, String args[])
	{
		return main(appName, args, new InitializationData());
	}

	public final int main(String appName, String args[], String configFile)
	{
		if (Util.getProcessLogger() instanceof LoggerI)
			Util.setProcessLogger(new LoggerI(appName, ""));
		InitializationData initData = new InitializationData();
		if (configFile != null)
			try
			{
				initData.properties = Util.createProperties();
				initData.properties.load(configFile);
			}
			catch (LocalException ex)
			{
				Util.getProcessLogger().error(Ex.toString(ex));
				return 1;
			}
			catch (Exception ex)
			{
				Util.getProcessLogger().error((new StringBuilder()).append("unknown exception: ").append(Ex.toString(ex)).toString());
				return 1;
			}
		return main(appName, args, initData);
	}

	public final int main(String appName, String args[], InitializationData initializationData)
	{
		if (Util.getProcessLogger() instanceof LoggerI)
			Util.setProcessLogger(new LoggerI(appName, ""));
		if (_communicator != null)
		{
			Util.getProcessLogger().error("only one instance of the Application class can be used");
			return 1;
		}
		_appName = appName;
		InitializationData initData;
		if (initializationData != null)
			initData = (InitializationData)initializationData.clone();
		else
			initData = new InitializationData();
		StringSeqHolder argHolder = new StringSeqHolder(args);
		initData.properties = Util.createProperties(argHolder, initData.properties);
		_appName = initData.properties.getPropertyWithDefault("Ice.ProgramName", _appName);
		if (!initData.properties.getProperty("Ice.ProgramName").equals("") && (Util.getProcessLogger() instanceof LoggerI))
			Util.setProcessLogger(new LoggerI(initData.properties.getProperty("Ice.ProgramName"), ""));
		return doMain(argHolder, initData);
	}

	protected int doMain(StringSeqHolder argHolder, InitializationData initData)
	{
		int status = 0;
		try
		{
			_communicator = Util.initialize(argHolder, initData);
			if (_signalPolicy == SignalPolicy.HandleSignals)
				destroyOnInterrupt();
			status = run(argHolder.value);
		}
		catch (LocalException ex)
		{
			Util.getProcessLogger().error(Ex.toString(ex));
			status = 1;
		}
		catch (Exception ex)
		{
			Util.getProcessLogger().error((new StringBuilder()).append("unknown exception: ").append(Ex.toString(ex)).toString());
			status = 1;
		}
		catch (Error err)
		{
			Util.getProcessLogger().error((new StringBuilder()).append("Java error: ").append(Ex.toString(err)).toString());
			status = 1;
		}
		if (_signalPolicy == SignalPolicy.HandleSignals)
			defaultInterrupt();
		synchronized (_mutex)
		{
			while (_callbackInProgress) 
				try
				{
					_mutex.wait();
				}
				catch (InterruptedException ex) { }
			if (_destroyed)
				_communicator = null;
			else
				_destroyed = true;
		}
		if (_communicator != null)
		{
			try
			{
				_communicator.destroy();
			}
			catch (LocalException ex)
			{
				Util.getProcessLogger().error(Ex.toString(ex));
				status = 1;
			}
			catch (Exception ex)
			{
				Util.getProcessLogger().error((new StringBuilder()).append("unknown exception: ").append(Ex.toString(ex)).toString());
				status = 1;
			}
			_communicator = null;
		}
		synchronized (_mutex)
		{
			if (_appHook != null)
				_appHook.done();
		}
		return status;
	}

	public abstract int run(String as[]);

	public static String appName()
	{
		return _appName;
	}

	public static Communicator communicator()
	{
		return _communicator;
	}

	public static void destroyOnInterrupt()
	{
		if (_signalPolicy == SignalPolicy.HandleSignals)
			synchronized (_mutex)
			{
				try
				{
					changeHook(new DestroyHook());
				}
				catch (IllegalStateException ex)
				{
					if (_communicator != null)
						_communicator.destroy();
				}
			}
		else
			Util.getProcessLogger().warning("interrupt method called on Application configured to not handle interrupts.");
	}

	public static void shutdownOnInterrupt()
	{
		if (_signalPolicy == SignalPolicy.HandleSignals)
			synchronized (_mutex)
			{
				try
				{
					changeHook(new ShutdownHook());
				}
				catch (IllegalStateException ex)
				{
					if (_communicator != null)
						_communicator.shutdown();
				}
			}
		else
			Util.getProcessLogger().warning("interrupt method called on Application configured to not handle interrupts.");
	}

	public static void setInterruptHook(Thread newHook)
	{
		if (_signalPolicy == SignalPolicy.HandleSignals)
			try
			{
				changeHook(new CustomHook(newHook));
			}
			catch (IllegalStateException ex) { }
		else
			Util.getProcessLogger().warning("interrupt method called on Application configured to not handle interrupts.");
	}

	public static void defaultInterrupt()
	{
		if (_signalPolicy == SignalPolicy.HandleSignals)
			changeHook(null);
		else
			Util.getProcessLogger().warning("interrupt method called on Application configured to not handle interrupts.");
	}

	public static boolean interrupted()
	{
		Object obj = _mutex;
		JVM INSTR monitorenter ;
		return _interrupted;
		Exception exception;
		exception;
		throw exception;
	}

	private static void changeHook(AppHook newHook)
	{
		synchronized (_mutex)
		{
			try
			{
				if (_appHook != null)
				{
					Runtime.getRuntime().removeShutdownHook(_appHook);
					_appHook.done();
					_appHook = null;
				}
			}
			catch (IllegalStateException ex) { }
			if (newHook != null)
			{
				Runtime.getRuntime().addShutdownHook(newHook);
				_appHook = newHook;
			}
		}
	}

	private static boolean setCallbackInProgress(boolean destroy)
	{
		Object obj = _mutex;
		JVM INSTR monitorenter ;
		if (_destroyed)
			return false;
		_callbackInProgress = true;
		_destroyed = destroy;
		_interrupted = true;
		true;
		obj;
		JVM INSTR monitorexit ;
		return;
		Exception exception;
		exception;
		throw exception;
	}

	private static void clearCallbackInProgress()
	{
		synchronized (_mutex)
		{
			_callbackInProgress = false;
			_mutex.notify();
		}
	}

	static 
	{
		_signalPolicy = SignalPolicy.HandleSignals;
	}


}
