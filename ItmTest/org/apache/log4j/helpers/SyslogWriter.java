// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SyslogWriter.java

package org.apache.log4j.helpers;

import java.io.IOException;
import java.io.Writer;
import java.net.*;

// Referenced classes of package org.apache.log4j.helpers:
//			LogLog

public class SyslogWriter extends Writer
{

	final int SYSLOG_PORT = 514;
	/**
	 * @deprecated Field syslogHost is deprecated
	 */
	static String syslogHost;
	private InetAddress address;
	private final int port;
	private DatagramSocket ds;

	public SyslogWriter(String syslogHost)
	{
		syslogHost = syslogHost;
		if (syslogHost == null)
			throw new NullPointerException("syslogHost");
		String host = syslogHost;
		int urlPort = -1;
		if (host.indexOf("[") != -1 || host.indexOf(':') == host.lastIndexOf(':'))
			try
			{
				URL url = new URL("http://" + host);
				if (url.getHost() != null)
				{
					host = url.getHost();
					if (host.startsWith("[") && host.charAt(host.length() - 1) == ']')
						host = host.substring(1, host.length() - 1);
					urlPort = url.getPort();
				}
			}
			catch (MalformedURLException e)
			{
				LogLog.error("Malformed URL: will attempt to interpret as InetAddress.", e);
			}
		if (urlPort == -1)
			urlPort = 514;
		port = urlPort;
		try
		{
			address = InetAddress.getByName(host);
		}
		catch (UnknownHostException e)
		{
			LogLog.error("Could not find " + host + ". All logging will FAIL.", e);
		}
		try
		{
			ds = new DatagramSocket();
		}
		catch (SocketException e)
		{
			e.printStackTrace();
			LogLog.error("Could not instantiate DatagramSocket to " + host + ". All logging will FAIL.", e);
		}
	}

	public void write(char buf[], int off, int len)
		throws IOException
	{
		write(new String(buf, off, len));
	}

	public void write(String string)
		throws IOException
	{
		if (ds != null && address != null)
		{
			byte bytes[] = string.getBytes();
			int bytesLength = bytes.length;
			if (bytesLength >= 1024)
				bytesLength = 1024;
			DatagramPacket packet = new DatagramPacket(bytes, bytesLength, address, port);
			ds.send(packet);
		}
	}

	public void flush()
	{
	}

	public void close()
	{
		if (ds != null)
			ds.close();
	}
}
