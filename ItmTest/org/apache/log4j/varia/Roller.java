// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Roller.java

package org.apache.log4j.varia;

import java.io.*;
import java.net.Socket;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class Roller
{

	static Logger cat;
	static String host;
	static int port;

	Roller()
	{
	}

	public static void main(String argv[])
	{
		BasicConfigurator.configure();
		if (argv.length == 2)
			init(argv[0], argv[1]);
		else
			usage("Wrong number of arguments.");
		roll();
	}

	static void usage(String msg)
	{
		System.err.println(msg);
		System.err.println("Usage: java " + (org.apache.log4j.varia.Roller.class).getName() + "host_name port_number");
		System.exit(1);
	}

	static void init(String hostArg, String portArg)
	{
		host = hostArg;
		try
		{
			port = Integer.parseInt(portArg);
		}
		catch (NumberFormatException e)
		{
			usage("Second argument " + portArg + " is not a valid integer.");
		}
	}

	static void roll()
	{
		try
		{
			Socket socket = new Socket(host, port);
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			dos.writeUTF("RollOver");
			String rc = dis.readUTF();
			if ("OK".equals(rc))
			{
				cat.info("Roll over signal acknowledged by remote appender.");
			} else
			{
				cat.warn("Unexpected return code " + rc + " from remote entity.");
				System.exit(2);
			}
		}
		catch (IOException e)
		{
			cat.error("Could not send roll signal on host " + host + " port " + port + " .", e);
			System.exit(2);
		}
		System.exit(0);
	}

	static 
	{
		cat = Logger.getLogger(org.apache.log4j.varia.Roller.class);
	}
}
