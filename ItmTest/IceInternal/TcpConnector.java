// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TcpConnector.java

package IceInternal;

import Ice.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;

// Referenced classes of package IceInternal:
//			TcpTransceiver, Connector, TraceLevels, Network, 
//			Instance, Transceiver

final class TcpConnector
	implements Connector
{

	private Instance _instance;
	private TraceLevels _traceLevels;
	private Logger _logger;
	private InetSocketAddress _addr;
	private int _timeout;
	private String _connectionId;
	private int _hashCode;

	public Transceiver connect()
	{
		if (_traceLevels.network >= 2)
		{
			String s = (new StringBuilder()).append("trying to establish tcp connection to ").append(toString()).toString();
			_logger.trace(_traceLevels.networkCat, s);
		}
		java.nio.channels.SocketChannel fd;
		boolean connected;
		fd = Network.createTcpSocket();
		Network.setBlock(fd, false);
		Network.setTcpBufSize(fd, _instance.initializationData().properties, _logger);
		connected = Network.doConnect(fd, _addr);
		if (connected && _traceLevels.network >= 1)
		{
			String s = (new StringBuilder()).append("tcp connection established\n").append(Network.fdToString(fd)).toString();
			_logger.trace(_traceLevels.networkCat, s);
		}
		return new TcpTransceiver(_instance, fd, connected, _addr);
		LocalException ex;
		ex;
		if (_traceLevels.network >= 2)
		{
			String s = (new StringBuilder()).append("failed to establish tcp connection to ").append(toString()).append("\n").append(ex).toString();
			_logger.trace(_traceLevels.networkCat, s);
		}
		throw ex;
	}

	public short type()
	{
		return 1;
	}

	public String toString()
	{
		return Network.addrToString(_addr);
	}

	public int hashCode()
	{
		return _hashCode;
	}

	TcpConnector(Instance instance, InetSocketAddress addr, int timeout, String connectionId)
	{
		_connectionId = "";
		_instance = instance;
		_traceLevels = instance.traceLevels();
		_logger = instance.initializationData().logger;
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
		TcpConnector p = null;
		try
		{
			p = (TcpConnector)obj;
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
