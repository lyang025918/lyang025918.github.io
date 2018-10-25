// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ConnectorI.java

package IceSSL;

import Ice.*;
import IceInternal.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;

// Referenced classes of package IceSSL:
//			TransceiverI, Instance

final class ConnectorI
	implements Connector
{

	private Instance _instance;
	private Logger _logger;
	private String _host;
	private InetSocketAddress _addr;
	private int _timeout;
	private String _connectionId;
	private int _hashCode;

	public Transceiver connect()
	{
		if (!_instance.initialized())
		{
			PluginInitializationException ex = new PluginInitializationException();
			ex.reason = "IceSSL: plug-in is not initialized";
			throw ex;
		}
		if (_instance.networkTraceLevel() >= 2)
		{
			String s = (new StringBuilder()).append("trying to establish ssl connection to ").append(toString()).toString();
			_logger.trace(_instance.networkTraceCategory(), s);
		}
		java.nio.channels.SocketChannel fd;
		boolean connected;
		fd = Network.createTcpSocket();
		Network.setBlock(fd, false);
		Network.setTcpBufSize(fd, _instance.communicator().getProperties(), _logger);
		connected = Network.doConnect(fd, _addr);
		javax.net.ssl.SSLEngine engine = _instance.createSSLEngine(false, _addr);
		return new TransceiverI(_instance, engine, fd, _host, connected, false, "", _addr);
		RuntimeException ex;
		ex;
		Network.closeSocketNoThrow(fd);
		throw ex;
		LocalException ex;
		ex;
		if (_instance.networkTraceLevel() >= 2)
		{
			String s = (new StringBuilder()).append("failed to establish ssl connection to ").append(toString()).append("\n").append(ex).toString();
			_logger.trace(_instance.networkTraceCategory(), s);
		}
		throw ex;
	}

	public short type()
	{
		return 2;
	}

	public String toString()
	{
		return Network.addrToString(_addr);
	}

	public int hashCode()
	{
		return _hashCode;
	}

	ConnectorI(Instance instance, String host, InetSocketAddress addr, int timeout, String connectionId)
	{
		_instance = instance;
		_logger = instance.communicator().getLogger();
		_host = host;
		_addr = addr;
		_timeout = timeout;
		_connectionId = connectionId;
		_hashCode = _addr.getAddress().getHostAddress().hashCode();
		_hashCode = 5 * _hashCode + _addr.getPort();
		_hashCode = 5 * _hashCode + _timeout;
		_hashCode = 5 * _hashCode + _connectionId.hashCode();
	}

	public boolean equals(Object obj)
	{
		ConnectorI p = null;
		try
		{
			p = (ConnectorI)obj;
		}
		catch (ClassCastException ex)
		{
			return false;
		}
		if (this == p)
			return true;
		if (_timeout != p._timeout)
			return false;
		if (!_connectionId.equals(p._connectionId))
			return false;
		else
			return Network.compareAddress(_addr, p._addr) == 0;
	}
}
