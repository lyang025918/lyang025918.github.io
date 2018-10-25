// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AcceptorI.java

package IceSSL;

import Ice.*;
import IceInternal.*;
import IceUtilInternal.Assert;
import IceUtilInternal.StringUtil;
import java.net.*;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.List;

// Referenced classes of package IceSSL:
//			TransceiverI, Instance

final class AcceptorI
	implements Acceptor
{

	private Instance _instance;
	private String _adapterName;
	private Logger _logger;
	private ServerSocketChannel _fd;
	private int _backlog;
	private InetSocketAddress _addr;
	static final boolean $assertionsDisabled = !IceSSL/AcceptorI.desiredAssertionStatus();

	public ServerSocketChannel fd()
	{
		return _fd;
	}

	public void close()
	{
		if (_instance.networkTraceLevel() >= 1)
		{
			String s = (new StringBuilder()).append("stopping to accept ssl connections at ").append(toString()).toString();
			_logger.trace(_instance.networkTraceCategory(), s);
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
		if (_instance.networkTraceLevel() >= 1)
		{
			StringBuffer s = new StringBuffer("accepting ssl connections at ");
			s.append(toString());
			List interfaces = Network.getHostsForEndpointExpand(_addr.getAddress().getHostAddress(), _instance.protocolSupport(), true);
			if (!interfaces.isEmpty())
			{
				s.append("\nlocal interfaces: ");
				s.append(StringUtil.joinString(interfaces, ", "));
			}
			_logger.trace(_instance.networkTraceCategory(), s.toString());
		}
	}

	public Transceiver accept()
	{
		if (!_instance.initialized())
		{
			PluginInitializationException ex = new PluginInitializationException();
			ex.reason = "IceSSL: plug-in is not initialized";
			throw ex;
		}
		SocketChannel fd = Network.doAccept(_fd);
		javax.net.ssl.SSLEngine engine = null;
		try
		{
			Network.setBlock(fd, false);
			Network.setTcpBufSize(fd, _instance.communicator().getProperties(), _logger);
			InetSocketAddress peerAddr = (InetSocketAddress)fd.socket().getRemoteSocketAddress();
			engine = _instance.createSSLEngine(true, peerAddr);
		}
		catch (RuntimeException ex)
		{
			Network.closeSocketNoThrow(fd);
			throw ex;
		}
		if (_instance.networkTraceLevel() >= 1)
			_logger.trace(_instance.networkTraceCategory(), (new StringBuilder()).append("accepting ssl connection\n").append(Network.fdToString(fd)).toString());
		return new TransceiverI(_instance, engine, fd, "", true, true, _adapterName, null);
	}

	public String toString()
	{
		return Network.addrToString(_addr);
	}

	int effectivePort()
	{
		return _addr.getPort();
	}

	AcceptorI(Instance instance, String adapterName, String host, int port)
	{
		_instance = instance;
		_adapterName = adapterName;
		_logger = instance.communicator().getLogger();
		_backlog = instance.communicator().getProperties().getPropertyAsIntWithDefault("Ice.TCP.Backlog", 511);
		try
		{
			_fd = Network.createTcpServerSocket();
			Network.setBlock(_fd, false);
			Network.setTcpBufSize(_fd, _instance.communicator().getProperties(), _logger);
			if (!System.getProperty("os.name").startsWith("Windows"))
				Network.setReuseAddress(_fd, true);
			_addr = Network.getAddressForServer(host, port, _instance.protocolSupport());
			if (_instance.networkTraceLevel() >= 2)
			{
				String s = (new StringBuilder()).append("attempting to bind to ssl socket ").append(toString()).toString();
				_logger.trace(_instance.networkTraceCategory(), s);
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
