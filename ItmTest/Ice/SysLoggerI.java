// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SysLoggerI.java

package Ice;

import IceInternal.Network;
import java.io.IOException;
import java.net.*;

// Referenced classes of package Ice:
//			InitializationException, SocketException, Logger

public final class SysLoggerI
	implements Logger
{

	private String _ident;
	private int _facility;
	private DatagramSocket _socket;
	private InetAddress _host;
	private static int _port = 514;
	private static final int LOG_KERN = 0;
	private static final int LOG_USER = 1;
	private static final int LOG_MAIL = 2;
	private static final int LOG_DAEMON = 3;
	private static final int LOG_AUTH = 4;
	private static final int LOG_SYSLOG = 5;
	private static final int LOG_LPR = 6;
	private static final int LOG_NEWS = 7;
	private static final int LOG_UUCP = 8;
	private static final int LOG_CRON = 9;
	private static final int LOG_AUTHPRIV = 10;
	private static final int LOG_FTP = 11;
	private static final int LOG_LOCAL0 = 16;
	private static final int LOG_LOCAL1 = 17;
	private static final int LOG_LOCAL2 = 18;
	private static final int LOG_LOCAL3 = 19;
	private static final int LOG_LOCAL4 = 20;
	private static final int LOG_LOCAL5 = 21;
	private static final int LOG_LOCAL6 = 22;
	private static final int LOG_LOCAL7 = 23;
	private static final int LOG_ERR = 3;
	private static final int LOG_WARNING = 4;
	private static final int LOG_INFO = 6;

	public SysLoggerI(String ident, String facilityString)
	{
		int facility;
		if (facilityString.equals("LOG_KERN"))
			facility = 0;
		else
		if (facilityString.equals("LOG_USER"))
			facility = 1;
		else
		if (facilityString.equals("LOG_MAIL"))
			facility = 2;
		else
		if (facilityString.equals("LOG_DAEMON"))
			facility = 3;
		else
		if (facilityString.equals("LOG_AUTH"))
			facility = 4;
		else
		if (facilityString.equals("LOG_SYSLOG"))
			facility = 5;
		else
		if (facilityString.equals("LOG_LPR"))
			facility = 6;
		else
		if (facilityString.equals("LOG_NEWS"))
			facility = 7;
		else
		if (facilityString.equals("LOG_UUCP"))
			facility = 8;
		else
		if (facilityString.equals("LOG_CRON"))
			facility = 9;
		else
		if (facilityString.equals("LOG_AUTHPRIV"))
			facility = 10;
		else
		if (facilityString.equals("LOG_FTP"))
			facility = 11;
		else
		if (facilityString.equals("LOG_LOCAL0"))
			facility = 16;
		else
		if (facilityString.equals("LOG_LOCAL1"))
			facility = 17;
		else
		if (facilityString.equals("LOG_LOCAL2"))
			facility = 18;
		else
		if (facilityString.equals("LOG_LOCAL3"))
			facility = 19;
		else
		if (facilityString.equals("LOG_LOCAL4"))
			facility = 20;
		else
		if (facilityString.equals("LOG_LOCAL5"))
			facility = 21;
		else
		if (facilityString.equals("LOG_LOCAL6"))
			facility = 22;
		else
		if (facilityString.equals("LOG_LOCAL7"))
			facility = 23;
		else
			throw new InitializationException((new StringBuilder()).append("Invalid value for Ice.SyslogFacility: ").append(facilityString).toString());
		initialize(ident, facility);
	}

	private SysLoggerI(String ident, int facility)
	{
		initialize(ident, facility);
	}

	private void initialize(String ident, int facility)
	{
		_ident = ident;
		_facility = facility;
		try
		{
			_host = Network.getLocalAddress(2);
			_socket = new DatagramSocket();
			_socket.connect(_host, _port);
		}
		catch (IOException ex)
		{
			throw new SocketException(ex);
		}
	}

	public void print(String message)
	{
		log(6, message);
	}

	public void trace(String category, String message)
	{
		log(6, (new StringBuilder()).append(category).append(": ").append(message).toString());
	}

	public void warning(String message)
	{
		log(4, message);
	}

	public void error(String message)
	{
		log(3, message);
	}

	public Logger cloneWithPrefix(String prefix)
	{
		return new SysLoggerI(prefix, _facility);
	}

	private void log(int severity, String message)
	{
		try
		{
			int priority = _facility << 3 | severity;
			String msg = (new StringBuilder()).append('<').append(Integer.toString(priority)).append('>').append(_ident).append(": ").append(message).toString();
			byte buf[] = msg.getBytes();
			DatagramPacket p = new DatagramPacket(buf, buf.length, _host, _port);
			_socket.send(p);
		}
		catch (IOException ex)
		{
			throw new SocketException(ex);
		}
	}

}
