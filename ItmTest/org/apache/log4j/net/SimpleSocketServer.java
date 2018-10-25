// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SimpleSocketServer.java

package org.apache.log4j.net;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import org.apache.log4j.*;
import org.apache.log4j.xml.DOMConfigurator;

// Referenced classes of package org.apache.log4j.net:
//			SocketNode

public class SimpleSocketServer
{

	static Logger cat;
	static int port;

	public SimpleSocketServer()
	{
	}

	public static void main(String argv[])
	{
		if (argv.length == 2)
			init(argv[0], argv[1]);
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
				cat.info("Connected to client at " + socket.getInetAddress());
				cat.info("Starting new socket node.");
				(new Thread(new SocketNode(socket, LogManager.getLoggerRepository()), "SimpleSocketServer-" + port)).start();
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
		System.err.println("Usage: java " + (org.apache.log4j.net.SimpleSocketServer.class).getName() + " port configFile");
		System.exit(1);
	}

	static void init(String portStr, String configFile)
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
		if (configFile.endsWith(".xml"))
			DOMConfigurator.configure(configFile);
		else
			PropertyConfigurator.configure(configFile);
	}

	static 
	{
		cat = Logger.getLogger(org.apache.log4j.net.SimpleSocketServer.class);
	}
}
