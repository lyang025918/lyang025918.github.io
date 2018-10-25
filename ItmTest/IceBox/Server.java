// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Server.java

package IceBox;

import Ice.*;
import java.io.PrintStream;

// Referenced classes of package IceBox:
//			ServiceManagerI

public final class Server extends Application
{

	public Server()
	{
	}

	private static void usage()
	{
		System.err.println("Usage: IceBox.Server [options] --Ice.Config=<file>\n");
		System.err.println("Options:\n-h, --help           Show this message.\n");
	}

	public static void main(String args[])
	{
		InitializationData initData = new InitializationData();
		initData.properties = Util.createProperties();
		initData.properties.setProperty("Ice.Admin.DelayCreation", "1");
		Server server = new Server();
		System.exit(server.main("IceBox.Server", args, initData));
	}

	public int run(String args[])
	{
		String arr$[] = args;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			String arg = arr$[i$];
			if (arg.equals("-h") || arg.equals("--help"))
			{
				usage();
				return 0;
			}
			if (!arg.startsWith("--"))
			{
				System.err.println((new StringBuilder()).append("Server: unknown option `").append(arg).append("'").toString());
				usage();
				return 1;
			}
		}

		ServiceManagerI serviceManagerImpl = new ServiceManagerI(communicator(), args);
		return serviceManagerImpl.run();
	}
}
