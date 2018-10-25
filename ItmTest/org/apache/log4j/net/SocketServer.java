// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SocketServer.java

package org.apache.log4j.net;

import java.io.File;
import java.io.PrintStream;
import java.net.*;
import java.util.Hashtable;
import org.apache.log4j.*;
import org.apache.log4j.spi.LoggerRepository;
import org.apache.log4j.spi.RootLogger;

// Referenced classes of package org.apache.log4j.net:
//			SocketNode

public class SocketServer
{

	static String GENERIC = "generic";
	static String CONFIG_FILE_EXT = ".lcf";
	static Logger cat;
	static SocketServer server;
	static int port;
	Hashtable hierarchyMap;
	LoggerRepository genericHierarchy;
	File dir;

	public static void main(String argv[])
	{
		if (argv.length == 3)
			init(argv[0], argv[1], argv[2]);
		else
			usage("Wrong number of arguments.");
		try
		{
			cat.info("Listening on port " + port);
			ServerSocket serverSocket = new ServerSocket(port);
			do
			{
				cat.info("Waiting to accept a new client.");
				Socket socket = serverSocket.accept();
				InetAddress inetAddress = socket.getInetAddress();
				cat.info("Connected to client at " + inetAddress);
				LoggerRepository h = (LoggerRepository)server.hierarchyMap.get(inetAddress);
				if (h == null)
					h = server.configureHierarchy(inetAddress);
				cat.info("Starting new socket node.");
				(new Thread(new SocketNode(socket, h))).start();
			} while (true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	static void usage(String msg)
	{
		System.err.println(msg);
		System.err.println("Usage: java " + (org.apache.log4j.net.SocketServer.class).getName() + " port configFile directory");
		System.exit(1);
	}

	static void init(String portStr, String configFile, String dirStr)
	{
		try
		{
			port = Integer.parseInt(portStr);
		}
		catch (NumberFormatException e)
		{
			e.printStackTrace();
			usage("Could not interpret port number [" + portStr + "].");
		}
		PropertyConfigurator.configure(configFile);
		File dir = new File(dirStr);
		if (!dir.isDirectory())
			usage("[" + dirStr + "] is not a directory.");
		server = new SocketServer(dir);
	}

	public SocketServer(File directory)
	{
		dir = directory;
		hierarchyMap = new Hashtable(11);
	}

	LoggerRepository configureHierarchy(InetAddress inetAddress)
	{
		cat.info("Locating configuration file for " + inetAddress);
		String s = inetAddress.toString();
		int i = s.indexOf("/");
		if (i == -1)
		{
			cat.warn("Could not parse the inetAddress [" + inetAddress + "]. Using default hierarchy.");
			return genericHierarchy();
		}
		String key = s.substring(0, i);
		File configFile = new File(dir, key + CONFIG_FILE_EXT);
		if (configFile.exists())
		{
			Hierarchy h = new Hierarchy(new RootLogger(Level.DEBUG));
			hierarchyMap.put(inetAddress, h);
			(new PropertyConfigurator()).doConfigure(configFile.getAbsolutePath(), h);
			return h;
		} else
		{
			cat.warn("Could not find config file [" + configFile + "].");
			return genericHierarchy();
		}
	}

	LoggerRepository genericHierarchy()
	{
		if (genericHierarchy == null)
		{
			File f = new File(dir, GENERIC + CONFIG_FILE_EXT);
			if (f.exists())
			{
				genericHierarchy = new Hierarchy(new RootLogger(Level.DEBUG));
				(new PropertyConfigurator()).doConfigure(f.getAbsolutePath(), genericHierarchy);
			} else
			{
				cat.warn("Could not find config file [" + f + "]. Will use the default hierarchy.");
				genericHierarchy = LogManager.getLoggerRepository();
			}
		}
		return genericHierarchy;
	}

	static 
	{
		cat = Logger.getLogger(org.apache.log4j.net.SocketServer.class);
	}
}
