// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PluginManagerI.java

package Ice;

import IceInternal.Instance;
import IceInternal.Util;
import java.util.*;

// Referenced classes of package Ice:
//			InitializationException, Plugin, CommunicatorDestroyedException, NotRegisteredException, 
//			AlreadyRegisteredException, PluginInitializationException, PluginFactory, PluginManager, 
//			Util, Logger, Communicator, Properties, 
//			StringSeqHolder

public final class PluginManagerI
	implements PluginManager
{

	private static String _kindOfObject = "plugin";
	private Communicator _communicator;
	private Map _plugins;
	private List _initOrder;
	private boolean _initialized;
	static final boolean $assertionsDisabled = !Ice/PluginManagerI.desiredAssertionStatus();

	public synchronized void initializePlugins()
	{
		if (_initialized)
		{
			InitializationException ex = new InitializationException();
			ex.reason = "plug-ins already initialized";
			throw ex;
		}
		List initializedPlugins = new ArrayList();
		try
		{
			Plugin p;
			for (Iterator i$ = _initOrder.iterator(); i$.hasNext(); initializedPlugins.add(p))
			{
				p = (Plugin)i$.next();
				p.initialize();
			}

		}
		catch (RuntimeException ex)
		{
			for (ListIterator i = initializedPlugins.listIterator(initializedPlugins.size()); i.hasPrevious();)
			{
				Plugin p = (Plugin)i.previous();
				try
				{
					p.destroy();
				}
				catch (RuntimeException e) { }
			}

			throw ex;
		}
		_initialized = true;
	}

	public synchronized String[] getPlugins()
	{
		ArrayList names = new ArrayList();
		java.util.Map.Entry p;
		for (Iterator i$ = _plugins.entrySet().iterator(); i$.hasNext(); names.add(p.getKey()))
			p = (java.util.Map.Entry)i$.next();

		return (String[])names.toArray(new String[0]);
	}

	public synchronized Plugin getPlugin(String name)
	{
		if (_communicator == null)
			throw new CommunicatorDestroyedException();
		Plugin p = (Plugin)_plugins.get(name);
		if (p != null)
		{
			return p;
		} else
		{
			NotRegisteredException ex = new NotRegisteredException();
			ex.id = name;
			ex.kindOfObject = _kindOfObject;
			throw ex;
		}
	}

	public synchronized void addPlugin(String name, Plugin plugin)
	{
		if (_communicator == null)
			throw new CommunicatorDestroyedException();
		if (_plugins.containsKey(name))
		{
			AlreadyRegisteredException ex = new AlreadyRegisteredException();
			ex.id = name;
			ex.kindOfObject = _kindOfObject;
			throw ex;
		} else
		{
			_plugins.put(name, plugin);
			return;
		}
	}

	public synchronized void destroy()
	{
		if (_communicator != null)
		{
			if (_initialized)
			{
				for (Iterator i$ = _plugins.entrySet().iterator(); i$.hasNext();)
				{
					java.util.Map.Entry p = (java.util.Map.Entry)i$.next();
					try
					{
						((Plugin)p.getValue()).destroy();
					}
					catch (RuntimeException ex)
					{
						Util.getProcessLogger().warning((new StringBuilder()).append("unexpected exception raised by plug-in `").append((String)p.getKey()).append("' destruction:\n").append(ex.toString()).toString());
					}
				}

			}
			_communicator = null;
		}
	}

	public PluginManagerI(Communicator communicator)
	{
		_plugins = new HashMap();
		_initOrder = new ArrayList();
		_communicator = communicator;
		_initialized = false;
	}

	public void loadPlugins(StringSeqHolder cmdArgs)
	{
		if (!$assertionsDisabled && _communicator == null)
			throw new AssertionError();
		String prefix = "Ice.Plugin.";
		Properties properties = _communicator.getProperties();
		Map plugins = properties.getPropertiesForPrefix("Ice.Plugin.");
		String loadOrder[] = properties.getPropertyAsList("Ice.PluginLoadOrder");
		String arr$[] = loadOrder;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			String name = arr$[i$];
			if (_plugins.containsKey(name))
			{
				PluginInitializationException ex = new PluginInitializationException();
				ex.reason = (new StringBuilder()).append("plug-in `").append(name).append("' already loaded").toString();
				throw ex;
			}
			String key = (new StringBuilder()).append("Ice.Plugin.").append(name).append(".java").toString();
			boolean hasKey = plugins.containsKey(key);
			if (hasKey)
			{
				plugins.remove((new StringBuilder()).append("Ice.Plugin.").append(name).toString());
			} else
			{
				key = (new StringBuilder()).append("Ice.Plugin.").append(name).toString();
				hasKey = plugins.containsKey(key);
			}
			if (hasKey)
			{
				String value = (String)plugins.get(key);
				loadPlugin(name, value, cmdArgs);
				plugins.remove(key);
			} else
			{
				PluginInitializationException ex = new PluginInitializationException();
				ex.reason = (new StringBuilder()).append("plug-in `").append(name).append("' not defined").toString();
				throw ex;
			}
		}

		do
		{
			if (plugins.isEmpty())
				break;
			Iterator p = plugins.entrySet().iterator();
			java.util.Map.Entry entry = (java.util.Map.Entry)p.next();
			String name = ((String)entry.getKey()).substring("Ice.Plugin.".length());
			int dotPos = name.lastIndexOf('.');
			if (dotPos != -1)
			{
				String suffix = name.substring(dotPos + 1);
				if (suffix.equals("cpp") || suffix.equals("clr"))
					p.remove();
				else
				if (suffix.equals("java"))
				{
					name = name.substring(0, dotPos);
					loadPlugin(name, (String)entry.getValue(), cmdArgs);
					p.remove();
					plugins.remove((new StringBuilder()).append("Ice.Plugin.").append(name).toString());
				} else
				{
					dotPos = -1;
				}
			}
			if (dotPos == -1)
			{
				String value = (String)entry.getValue();
				p.remove();
				String javaValue = (String)plugins.remove((new StringBuilder()).append("Ice.Plugin.").append(name).append(".java").toString());
				if (javaValue != null)
					value = javaValue;
				loadPlugin(name, value, cmdArgs);
			}
		} while (true);
	}

	private void loadPlugin(String name, String pluginSpec, StringSeqHolder cmdArgs)
	{
		if (!$assertionsDisabled && _communicator == null)
			throw new AssertionError();
		int pos = pluginSpec.indexOf(' ');
		if (pos == -1)
			pos = pluginSpec.indexOf('\t');
		if (pos == -1)
			pos = pluginSpec.indexOf('\n');
		String className;
		String args[];
		if (pos == -1)
		{
			className = pluginSpec;
			args = new String[0];
		} else
		{
			className = pluginSpec.substring(0, pos);
			args = pluginSpec.substring(pos).trim().split("[ \t\n]+", pos);
		}
		Properties properties = _communicator.getProperties();
		args = properties.parseCommandLineOptions(name, args);
		cmdArgs.value = properties.parseCommandLineOptions(name, cmdArgs.value);
		PluginFactory pluginFactory = null;
		try
		{
			Class c = Util.getInstance(_communicator).findClass(className);
			if (c == null)
			{
				PluginInitializationException e = new PluginInitializationException();
				e.reason = (new StringBuilder()).append("class ").append(className).append(" not found").toString();
				throw e;
			}
			Object obj = c.newInstance();
			try
			{
				pluginFactory = (PluginFactory)obj;
			}
			catch (ClassCastException ex)
			{
				throw new PluginInitializationException((new StringBuilder()).append("class ").append(className).append(" does not implement Ice.PluginFactory").toString(), ex);
			}
		}
		catch (IllegalAccessException ex)
		{
			throw new PluginInitializationException((new StringBuilder()).append("unable to access default constructor in class ").append(className).toString(), ex);
		}
		catch (InstantiationException ex)
		{
			throw new PluginInitializationException((new StringBuilder()).append("unable to instantiate class ").append(className).toString(), ex);
		}
		Plugin plugin = null;
		try
		{
			plugin = pluginFactory.create(_communicator, name, args);
		}
		catch (PluginInitializationException ex)
		{
			throw ex;
		}
		catch (Throwable ex)
		{
			throw new PluginInitializationException((new StringBuilder()).append("exception in factory ").append(className).toString(), ex);
		}
		if (plugin == null)
		{
			throw new PluginInitializationException((new StringBuilder()).append("failure in factory ").append(className).toString());
		} else
		{
			_plugins.put(name, plugin);
			_initOrder.add(plugin);
			return;
		}
	}

}
