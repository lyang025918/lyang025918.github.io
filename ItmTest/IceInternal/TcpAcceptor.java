// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TcpAcceptor.java

package IceInternal;

import Ice.*;
import IceUtilInternal.Assert;
import IceUtilInternal.StringUtil;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.util.List;

// Referenced classes of package IceInternal:
//			TcpTransceiver, Acceptor, TraceLevels, Network, 
//			Instance, Transceiver

class TcpAcceptor
	implements Acceptor
{

	private Instance _instance;
	private TraceLevels _traceLevels;
	private Logger _logger;
	private ServerSocketChannel _fd;
	private int _backlog;
	private InetSocketAddress _addr;
	static final boolean $assertionsDisabled = !IceInternal/TcpAcceptor.desiredAssertionStatus();

	public ServerSocketChannel fd()
	{
		return _fd;
	}

	public void close()
	{
		if (_traceLevels.network >= 1)
		{
			String s = (new StringBuilder()).append("stopping to accept tcp connections at ").append(toString()).toString();
			_logger.trace(_traceLevels.networkCat, s);
		}
		if (!$assertionsDisabled && _fd == null)
		{
			throw new AssertionError();
		} else
		{
			Network.closeSocketNoThrow(_fd);
			_fd = null;
			return;
		}
	}

	public void listen()
	{
		if (_traceLevels.network >= 1)
		{
			StringBuffer s = new StringBuffer("accepting tcp connections at ");
			s.append(toString());
			List interfaces = Network.getHostsForEndpointExpand(_addr.getAddress().getHostAddress(), _instance.protocolSupport(), true);
			if (!interfaces.isEmpty())
			{
				s.append("\nlocal interfaces: ");
				s.append(StringUtil.joinString(interfaces, ", "));
			}
			_logger.trace(_traceLevels.networkCat, s.toString());
		}
	}

	public Transceiver accept()
	{
		java.nio.channels.SocketChannel fd = Network.doAccept(_fd);
		Network.setBlock(fd, false);
		Network.setTcpBufSize(fd, _instance.initializationData().properties, _logger);
		if (_traceLevels.network >= 1)
		{
			String s = (new StringBuilder()).append("accepted tcp connection\n").append(Network.fdToString(fd)).toString();
			_logger.trace(_traceLevels.networkCat, s);
		}
		return new TcpTransceiver(_instance, fd, true, null);
	}

	public String toString()
	{
		return Network.addrToString(_addr);
	}

	int effectivePort()
	{
		return _addr.getPort();
	}

	TcpAcceptor(Instance instance, String host, int port)
	{
		_instance = instance;
		_traceLevels = instance.traceLevels();
		_logger = instance.initializationData().logger;
		_backlog = instance.initializationData().properties.getPropertyAsIntWithDefault("Ice.TCP.Backlog", 511);
		try
		{
			_fd = Network.createTcpServerSocket();
			Network.setBlock(_fd, false);
			Network.setTcpBufSize(_fd, _instance.initializationData().properties, _logger);
			if (!System.getProperty("os.name").startsWith("Windows"))
				Network.setReuseAddress(_fd, true);
			_addr = Network.getAddressForServer(host, port, _instance.protocolSupport());
			if (_traceLevels.network >= 2)
			{
				String s = (new StringBuilder()).append("attempting to bind to tcp socket ").append(toString()).toString();
				_logger.trace(_traceLevels.networkCat, s);
			}
			_addr = Network.doBind(_fd, _addr, _backlog);
		}
		catch (RuntimeException ex)
		{
			_fd = null;
			throw ex;
		}
	}

	protected synchronized void finalize()
		throws Throwable
	{
		Assert.FinalizerAssert(_fd == null);
		super.finalize();
	}

}
