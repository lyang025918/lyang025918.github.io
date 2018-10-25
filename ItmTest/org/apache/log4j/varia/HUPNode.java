// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ExternallyRolledFileAppender.java

package org.apache.log4j.varia;

import java.io.*;
import java.net.Socket;
import org.apache.log4j.helpers.LogLog;

// Referenced classes of package org.apache.log4j.varia:
//			ExternallyRolledFileAppender

class HUPNode
	implements Runnable
{

	Socket socket;
	DataInputStream dis;
	DataOutputStream dos;
	ExternallyRolledFileAppender er;

	public HUPNode(Socket socket, ExternallyRolledFileAppender er)
	{
		this.socket = socket;
		this.er = er;
		try
		{
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
		}
		catch (InterruptedIOException e)
		{
			Thread.currentThread().interrupt();
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (RuntimeException e)
		{
			e.printStackTrace();
		}
	}

	public void run()
	{
		try
		{
			String line = dis.readUTF();
			LogLog.debug("Got external roll over signal.");
			if ("RollOver".equals(line))
			{
				synchronized (er)
				{
					er.rollOver();
				}
				dos.writeUTF("OK");
			} else
			{
				dos.writeUTF("Expecting [RollOver] string.");
			}
			dos.close();
		}
		catch (InterruptedIOException e)
		{
			Thread.currentThread().interrupt();
			LogLog.error("Unexpected exception. Exiting HUPNode.", e);
		}
		catch (IOException e)
		{
			LogLog.error("Unexpected exception. Exiting HUPNode.", e);
		}
		catch (RuntimeException e)
		{
			LogLog.error("Unexpected exception. Exiting HUPNode.", e);
		}
	}
}
