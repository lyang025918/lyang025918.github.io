// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TelnetAppender.java

package org.apache.log4j.net;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Layout;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LoggingEvent;

public class TelnetAppender extends AppenderSkeleton
{
	protected class SocketHandler extends Thread
	{

		private Vector writers;
		private Vector connections;
		private ServerSocket serverSocket;
		private int MAX_CONNECTIONS;

		public void finalize()
		{
			close();
		}

		public void close()
		{
			synchronized (this)
			{
				for (Enumeration e = connections.elements(); e.hasMoreElements();)
					try
					{
						((Socket)e.nextElement()).close();
					}
					catch (InterruptedIOException ex)
					{
						Thread.currentThread().interrupt();
					}
					catch (IOException ex) { }
					catch (RuntimeException ex) { }

			}
			try
			{
				serverSocket.close();
			}
			catch (InterruptedIOException ex)
			{
				Thread.currentThread().interrupt();
			}
			catch (IOException ex) { }
			catch (RuntimeException ex) { }
		}

		public synchronized void send(String message)
		{
			Iterator ce = connections.iterator();
			Iterator e = writers.iterator();
			do
			{
				if (!e.hasNext())
					break;
				ce.next();
				PrintWriter writer = (PrintWriter)e.next();
				writer.print(message);
				if (writer.checkError())
				{
					ce.remove();
					e.remove();
				}
			} while (true);
		}

		public void run()
		{
_L2:
			if (serverSocket.isClosed())
				break; /* Loop/switch isn't completed */
			Socket newClient = serverSocket.accept();
			PrintWriter pw = new PrintWriter(newClient.getOutputStream());
			if (connections.size() < MAX_CONNECTIONS)
			{
				synchronized (this)
				{
					connections.addElement(newClient);
					writers.addElement(pw);
					pw.print("TelnetAppender v1.0 (" + connections.size() + " active connections)\r\n\r\n");
					pw.flush();
				}
			} else
			{
				pw.print("Too many connections.\r\n");
				pw.flush();
				newClient.close();
			}
			if (true) goto _L2; else goto _L1
			Exception e;
			e;
			if ((e instanceof InterruptedIOException) || (e instanceof InterruptedException))
				Thread.currentThread().interrupt();
			if (!serverSocket.isClosed())
				LogLog.error("Encountered error while in SocketHandler loop.", e);
_L1:
			try
			{
				serverSocket.close();
			}
			catch (InterruptedIOException ex)
			{
				Thread.currentThread().interrupt();
			}
			catch (IOException ex) { }
			return;
		}

		public SocketHandler(int port)
			throws IOException
		{
			super();
			writers = new Vector();
			connections = new Vector();
			MAX_CONNECTIONS = 20;
			serverSocket = new ServerSocket(port);
			setName("TelnetAppender-" + getName() + "-" + port);
		}
	}


	private SocketHandler sh;
	private int port;

	public TelnetAppender()
	{
		port = 23;
	}

	public boolean requiresLayout()
	{
		return true;
	}

	public void activateOptions()
	{
		try
		{
			sh = new SocketHandler(port);
			sh.start();
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
		super.activateOptions();
	}

	public int getPort()
	{
		return port;
	}

	public void setPort(int port)
	{
		this.port = port;
	}

	public void close()
	{
		if (sh != null)
		{
			sh.close();
			try
			{
				sh.join();
			}
			catch (InterruptedException ex)
			{
				Thread.currentThread().interrupt();
			}
		}
	}

	protected void append(LoggingEvent event)
	{
		if (sh != null)
		{
			sh.send(layout.format(event));
			if (layout.ignoresThrowable())
			{
				String s[] = event.getThrowableStrRep();
				if (s != null)
				{
					StringBuffer buf = new StringBuffer();
					for (int i = 0; i < s.length; i++)
					{
						buf.append(s[i]);
						buf.append("\r\n");
					}

					sh.send(buf.toString());
				}
			}
		}
	}
}
