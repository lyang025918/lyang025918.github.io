// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Admin.java

package IceBox;

import Ice.*;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package IceBox:
//			NoSuchServiceException, AlreadyStartedException, AlreadyStoppedException, ServiceManagerPrxHelper, 
//			ServiceManagerPrx

public final class Admin
{
	private static class Client extends Application
	{

		private void usage()
		{
			System.err.println((new StringBuilder()).append("Usage: ").append(appName()).append(" [options] [command...]\n").append("Options:\n").append("-h, --help          Show this message.\n").append("\n").append("Commands:\n").append("start SERVICE       Start a service.\n").append("stop SERVICE        Stop a service.\n").append("shutdown            Shutdown the server.").toString());
		}

		public int run(String args[])
		{
			List commands = new ArrayList();
			for (int idx = 0; idx < args.length; idx++)
			{
				if (args[idx].equals("-h") || args[idx].equals("--help"))
				{
					usage();
					return 1;
				}
				if (args[idx].charAt(0) == '-')
				{
					System.err.println((new StringBuilder()).append(appName()).append(": unknown option `").append(args[idx]).append("'").toString());
					usage();
					return 1;
				}
				commands.add(args[idx]);
			}

			if (commands.isEmpty())
			{
				usage();
				return 0;
			}
			Ice.ObjectPrx base = communicator().propertyToProxy("IceBoxAdmin.ServiceManager.Proxy");
			if (base == null)
			{
				Properties properties = communicator().getProperties();
				Identity managerIdentity = new Identity();
				managerIdentity.category = properties.getPropertyWithDefault("IceBox.InstanceName", "IceBox");
				managerIdentity.name = "ServiceManager";
				String managerProxy;
				if (properties.getProperty("Ice.Default.Locator").length() == 0)
				{
					String managerEndpoints = properties.getProperty("IceBox.ServiceManager.Endpoints");
					if (managerEndpoints.length() == 0)
					{
						System.err.println((new StringBuilder()).append(appName()).append(": property `IceBoxAdmin.ServiceManager.Proxy' is not set").toString());
						return 1;
					}
					managerProxy = (new StringBuilder()).append("\"").append(communicator().identityToString(managerIdentity)).append("\" :").append(managerEndpoints).toString();
				} else
				{
					String managerAdapterId = properties.getProperty("IceBox.ServiceManager.AdapterId");
					if (managerAdapterId.length() == 0)
					{
						System.err.println((new StringBuilder()).append(appName()).append(": property `IceBoxAdmin.ServiceManager.Proxy' is not set").toString());
						return 1;
					}
					managerProxy = (new StringBuilder()).append("\"").append(communicator().identityToString(managerIdentity)).append("\" @").append(managerAdapterId).toString();
				}
				base = communicator().stringToProxy(managerProxy);
			}
			ServiceManagerPrx manager = ServiceManagerPrxHelper.checkedCast(base);
			if (manager == null)
			{
				System.err.println((new StringBuilder()).append(appName()).append(": `").append(base.toString()).append("' is not an IceBox::ServiceManager").toString());
				return 1;
			}
			for (int i = 0; i < commands.size(); i++)
			{
				String command = (String)commands.get(i);
				if (command.equals("shutdown"))
				{
					manager.shutdown();
					continue;
				}
				if (command.equals("start"))
				{
					if (++i >= commands.size())
					{
						System.err.println((new StringBuilder()).append(appName()).append(": no service name specified.").toString());
						return 1;
					}
					String service = (String)commands.get(i);
					try
					{
						manager.startService(service);
					}
					catch (NoSuchServiceException ex)
					{
						System.err.println((new StringBuilder()).append(appName()).append(": unknown service `").append(service).append("'").toString());
					}
					catch (AlreadyStartedException ex)
					{
						System.err.println((new StringBuilder()).append(appName()).append("service already started.").toString());
					}
					continue;
				}
				if (command.equals("stop"))
				{
					if (++i >= commands.size())
					{
						System.err.println((new StringBuilder()).append(appName()).append(": no service name specified.").toString());
						return 1;
					}
					String service = (String)commands.get(i);
					try
					{
						manager.stopService(service);
					}
					catch (NoSuchServiceException ex)
					{
						System.err.println((new StringBuilder()).append(appName()).append(": unknown service `").append(service).append("'").toString());
					}
					catch (AlreadyStoppedException ex)
					{
						System.err.println((new StringBuilder()).append(appName()).append("service already stopped.").toString());
					}
				} else
				{
					System.err.println((new StringBuilder()).append(appName()).append(": unknown command `").append(command).append("'").toString());
					usage();
					return 1;
				}
			}

			return 0;
		}

		private Client()
		{
		}

	}


	public Admin()
	{
	}

	public static void main(String args[])
	{
		Client app = new Client();
		int rc = app.main("IceBox.Admin", args);
		System.exit(rc);
	}
}
