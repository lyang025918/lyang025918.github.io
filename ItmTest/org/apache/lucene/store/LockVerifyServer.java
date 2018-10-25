// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LockVerifyServer.java

package org.apache.lucene.store;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class LockVerifyServer
{

	public LockVerifyServer()
	{
	}

	private static String getTime(long startTime)
	{
		return (new StringBuilder()).append("[").append((System.currentTimeMillis() - startTime) / 1000L).append("s] ").toString();
	}

	public static void main(String args[])
		throws IOException
	{
		if (args.length != 1)
		{
			System.out.println("\nUsage: java org.apache.lucene.store.LockVerifyServer port\n");
			System.exit(1);
		}
		int port = Integer.parseInt(args[0]);
		ServerSocket s = new ServerSocket(port);
		s.setReuseAddress(true);
		System.out.println((new StringBuilder()).append("\nReady on port ").append(port).append("...").toString());
		int lockedID = 0;
		long startTime = System.currentTimeMillis();
		do
		{
			Socket cs = s.accept();
			OutputStream out = cs.getOutputStream();
			InputStream in = cs.getInputStream();
			int id = in.read();
			int command = in.read();
			boolean err = false;
			if (command == 1)
			{
				if (lockedID != 0)
				{
					err = true;
					System.out.println((new StringBuilder()).append(getTime(startTime)).append(" ERROR: id ").append(id).append(" got lock, but ").append(lockedID).append(" already holds the lock").toString());
				}
				lockedID = id;
			} else
			if (command == 0)
			{
				if (lockedID != id)
				{
					err = true;
					System.out.println((new StringBuilder()).append(getTime(startTime)).append(" ERROR: id ").append(id).append(" released the lock, but ").append(lockedID).append(" is the one holding the lock").toString());
				}
				lockedID = 0;
			} else
			{
				throw new RuntimeException((new StringBuilder()).append("unrecognized command ").append(command).toString());
			}
			System.out.print(".");
			if (err)
				out.write(1);
			else
				out.write(0);
			out.close();
			in.close();
			cs.close();
		} while (true);
	}
}
