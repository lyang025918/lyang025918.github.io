// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SocketNode.java

package org.apache.log4j.net;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerRepository;
import org.apache.log4j.spi.LoggingEvent;

public class SocketNode
	implements Runnable
{

	Socket socket;
	LoggerRepository hierarchy;
	ObjectInputStream ois;
	static Logger logger;

	public SocketNode(Socket socket, LoggerRepository hierarchy)
	{
		this.socket = socket;
		this.hierarchy = hierarchy;
		try
		{
			ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
		}
		catch (InterruptedIOException e)
		{
			Thread.currentThread().interrupt();
			logger.error("Could not open ObjectInputStream to " + socket, e);
		}
		catch (IOException e)
		{
			logger.error("Could not open ObjectInputStream to " + socket, e);
		}
		catch (RuntimeException e)
		{
			logger.error("Could not open ObjectInputStream to " + socket, e);
		}
	}

	public void run()
	{
		if (ois != null)
			do
			{
				LoggingEvent event;
				Logger remoteLogger;
				do
				{
					event = (LoggingEvent)ois.readObject();
					remoteLogger = hierarchy.getLogger(event.getLoggerName());
				} while (!event.getLevel().isGreaterOrEqual(remoteLogger.getEffectiveLevel()));
				remoteLogger.callAppenders(event);
			} while (true);
		InterruptedIOException e;
		if (ois != null)
			try
			{
				ois.close();
			}
			// Misplaced declaration of an exception variable
			catch (InterruptedIOException e)
			{
				logger.info("Could not close connection.", e);
			}
		if (socket != null)
			try
			{
				socket.close();
			}
			// Misplaced declaration of an exception variable
			catch (InterruptedIOException e)
			{
				Thread.currentThread().interrupt();
			}
			catch (IOException ex) { }
		break MISSING_BLOCK_LABEL_568;
		ex;
		logger.info("Caught java.io.EOFException closing conneciton.");
		if (ois != null)
			try
			{
				ois.close();
			}
			// Misplaced declaration of an exception variable
			catch (IOException ex)
			{
				logger.info("Could not close connection.", ex);
			}
		if (socket != null)
			try
			{
				socket.close();
			}
			// Misplaced declaration of an exception variable
			catch (IOException ex)
			{
				Thread.currentThread().interrupt();
			}
			catch (IOException ex) { }
		break MISSING_BLOCK_LABEL_568;
		ex;
		logger.info("Caught java.net.SocketException closing conneciton.");
		if (ois != null)
			try
			{
				ois.close();
			}
			// Misplaced declaration of an exception variable
			catch (IOException ex)
			{
				logger.info("Could not close connection.", ex);
			}
		if (socket != null)
			try
			{
				socket.close();
			}
			// Misplaced declaration of an exception variable
			catch (IOException ex)
			{
				Thread.currentThread().interrupt();
			}
			catch (IOException ex) { }
		break MISSING_BLOCK_LABEL_568;
		ex;
		Thread.currentThread().interrupt();
		logger.info("Caught java.io.InterruptedIOException: " + ex);
		logger.info("Closing connection.");
		if (ois != null)
			try
			{
				ois.close();
			}
			// Misplaced declaration of an exception variable
			catch (IOException ex)
			{
				logger.info("Could not close connection.", ex);
			}
		if (socket != null)
			try
			{
				socket.close();
			}
			// Misplaced declaration of an exception variable
			catch (IOException ex)
			{
				Thread.currentThread().interrupt();
			}
			catch (IOException ex) { }
		break MISSING_BLOCK_LABEL_568;
		ex;
		logger.info("Caught java.io.IOException: " + ex);
		logger.info("Closing connection.");
		if (ois != null)
			try
			{
				ois.close();
			}
			// Misplaced declaration of an exception variable
			catch (IOException ex)
			{
				logger.info("Could not close connection.", ex);
			}
		if (socket != null)
			try
			{
				socket.close();
			}
			// Misplaced declaration of an exception variable
			catch (IOException ex)
			{
				Thread.currentThread().interrupt();
			}
			catch (IOException ex) { }
		break MISSING_BLOCK_LABEL_568;
		ex;
		logger.error("Unexpected exception. Closing conneciton.", ex);
		if (ois != null)
			try
			{
				ois.close();
			}
			// Misplaced declaration of an exception variable
			catch (IOException ex)
			{
				logger.info("Could not close connection.", ex);
			}
		if (socket != null)
			try
			{
				socket.close();
			}
			// Misplaced declaration of an exception variable
			catch (IOException ex)
			{
				Thread.currentThread().interrupt();
			}
			catch (IOException ex) { }
		break MISSING_BLOCK_LABEL_568;
		Exception exception;
		exception;
		if (ois != null)
			try
			{
				ois.close();
			}
			catch (Exception e)
			{
				logger.info("Could not close connection.", e);
			}
		if (socket != null)
			try
			{
				socket.close();
			}
			catch (InterruptedIOException e)
			{
				Thread.currentThread().interrupt();
			}
			catch (IOException ex) { }
		throw exception;
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
		logger = Logger.getLogger(org.apache.log4j.net.SocketNode.class);
	}
}
