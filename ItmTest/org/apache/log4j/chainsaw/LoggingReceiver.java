// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LoggingReceiver.java

package org.apache.log4j.chainsaw;

import java.io.*;
import java.net.*;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;

// Referenced classes of package org.apache.log4j.chainsaw:
//			MyTableModel, EventDetails

class LoggingReceiver extends Thread
{
	private class Slurper
		implements Runnable
	{

		private final Socket mClient;

		public void run()
		{
			LoggingReceiver.LOG.debug("Starting to get data");
			try
			{
				ObjectInputStream ois = new ObjectInputStream(mClient.getInputStream());
				do
				{
					LoggingEvent event = (LoggingEvent)ois.readObject();
					mModel.addEvent(new EventDetails(event));
				} while (true);
			}
			catch (EOFException e)
			{
				LoggingReceiver.LOG.info("Reached EOF, closing connection");
			}
			catch (SocketException e)
			{
				LoggingReceiver.LOG.info("Caught SocketException, closing connection");
			}
			catch (IOException e)
			{
				LoggingReceiver.LOG.warn("Got IOException, closing connection", e);
			}
			catch (ClassNotFoundException e)
			{
				LoggingReceiver.LOG.warn("Got ClassNotFoundException, closing connection", e);
			}
			try
			{
				mClient.close();
			}
			catch (IOException e)
			{
				LoggingReceiver.LOG.warn("Error closing connection", e);
			}
		}

		Slurper(Socket aClient)
		{
			super();
			mClient = aClient;
		}
	}


	private static final Logger LOG;
	private MyTableModel mModel;
	private ServerSocket mSvrSock;

	LoggingReceiver(MyTableModel aModel, int aPort)
		throws IOException
	{
		setDaemon(true);
		mModel = aModel;
		mSvrSock = new ServerSocket(aPort);
	}

	public void run()
	{
		LOG.info("Thread started");
		try
		{
			do
			{
				LOG.debug("Waiting for a connection");
				Socket client = mSvrSock.accept();
				LOG.debug("Got a connection from " + client.getInetAddress().getHostName());
				Thread t = new Thread(new Slurper(client));
				t.setDaemon(true);
				t.start();
			} while (true);
		}
		catch (IOException e)
		{
			LOG.error("Error in accepting connections, stopping.", e);
		}
	}

	static Class class$(String x0)
	{
		return Class.forName(x0);
		ClassNotFoundException x1;
		x1;
		throw (new NoClassDefFoundError()).initCause(x1);
	}

	static 
	{
		LOG = Logger.getLogger(org.apache.log4j.chainsaw.LoggingReceiver.class);
	}


}
