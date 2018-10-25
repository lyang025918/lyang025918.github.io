// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   UdpTransceiver.java

package IceInternal;

import Ice.*;
import IceUtilInternal.Assert;
import IceUtilInternal.StringUtil;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.lang.reflect.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.List;

// Referenced classes of package IceInternal:
//			Transceiver, TraceLevels, Buffer, Network, 
//			Ex, Instance, Util

final class UdpTransceiver
	implements Transceiver
{

	private TraceLevels _traceLevels;
	private Logger _logger;
	private Stats _stats;
	private int _state;
	private int _rcvSize;
	private int _sndSize;
	private DatagramChannel _fd;
	private InetSocketAddress _addr;
	private InetSocketAddress _mcastAddr;
	private InetSocketAddress _peerAddr;
	private static final int _udpOverhead = 28;
	private static final int _maxPacketSize = 65507;
	private static final int StateNeedConnect = 0;
	private static final int StateConnected = 1;
	private static final int StateNotConnected = 2;
	static final boolean $assertionsDisabled = !IceInternal/UdpTransceiver.desiredAssertionStatus();

	public SelectableChannel fd()
	{
		if (!$assertionsDisabled && _fd == null)
			throw new AssertionError();
		else
			return _fd;
	}

	public int initialize()
	{
		return 0;
	}

	public void close()
	{
		if (!$assertionsDisabled && _fd == null)
			throw new AssertionError();
		if (_state >= 1 && _traceLevels.network >= 1)
		{
			String s = (new StringBuilder()).append("closing udp connection\n").append(toString()).toString();
			_logger.trace(_traceLevels.networkCat, s);
		}
		try
		{
			_fd.close();
		}
		catch (IOException ex) { }
		_fd = null;
	}

	public boolean write(Buffer buf)
	{
		if (!$assertionsDisabled && buf.b.position() != 0)
			throw new AssertionError();
		if (!$assertionsDisabled && (_fd == null || _state < 1))
			throw new AssertionError();
		if (!$assertionsDisabled && Math.min(65507, _sndSize - 28) < buf.size())
			throw new AssertionError();
		int ret = 0;
		do
		{
			try
			{
				if (_state == 1)
				{
					ret = _fd.write(buf.b);
				} else
				{
					if (_peerAddr == null)
						throw new SocketException();
					ret = _fd.send(buf.b, _peerAddr);
				}
			}
			catch (AsynchronousCloseException ex)
			{
				throw new ConnectionLostException(ex);
			}
			catch (PortUnreachableException ex)
			{
				throw new ConnectionLostException(ex);
			}
			catch (InterruptedIOException ex)
			{
				continue;
			}
			catch (IOException ex)
			{
				throw new SocketException(ex);
			}
			if (ret == 0)
				return false;
			if (_traceLevels.network >= 3)
			{
				String s = (new StringBuilder()).append("sent ").append(ret).append(" bytes via udp\n").append(toString()).toString();
				_logger.trace(_traceLevels.networkCat, s);
			}
			if (_stats != null)
				_stats.bytesSent(type(), ret);
			if (!$assertionsDisabled && ret != buf.b.limit())
				throw new AssertionError();
			return true;
		} while (true);
	}

	public boolean read(Buffer buf, BooleanHolder moreData)
	{
		if (!$assertionsDisabled && buf.b.position() != 0)
			throw new AssertionError();
		moreData.value = false;
		int packetSize = Math.min(65507, _rcvSize - 28);
		buf.resize(packetSize, true);
		buf.b.position(0);
		int ret = 0;
_L2:
		SocketAddress peerAddr = _fd.receive(buf.b);
		if (peerAddr == null || buf.b.position() == 0)
			return false;
		int ret;
		try
		{
			_peerAddr = (InetSocketAddress)peerAddr;
			ret = buf.b.position();
		}
		catch (AsynchronousCloseException ex)
		{
			throw new ConnectionLostException(ex);
		}
		catch (PortUnreachableException ex)
		{
			throw new ConnectionLostException(ex);
		}
		catch (InterruptedIOException ex)
		{
			continue; /* Loop/switch isn't completed */
		}
		catch (IOException ex)
		{
			throw new ConnectionLostException(ex);
		}
		if (_state == 0)
		{
			Network.doConnect(_fd, _peerAddr);
			_state = 1;
			if (_traceLevels.network >= 1)
			{
				String s = (new StringBuilder()).append("connected udp socket\n").append(toString()).toString();
				_logger.trace(_traceLevels.networkCat, s);
			}
		}
		if (_traceLevels.network >= 3)
		{
			String s = (new StringBuilder()).append("received ").append(ret).append(" bytes via udp\n").append(toString()).toString();
			_logger.trace(_traceLevels.networkCat, s);
		}
		if (_stats != null)
			_stats.bytesReceived(type(), ret);
		buf.resize(ret, true);
		buf.b.position(ret);
		return true;
		if (true) goto _L2; else goto _L1
_L1:
	}

	public String type()
	{
		return "udp";
	}

	public String toString()
	{
		if (_fd == null)
			return "<closed>";
		String s;
		if (_state == 2)
		{
			DatagramSocket socket = _fd.socket();
			s = (new StringBuilder()).append("local address = ").append(Network.addrToString((InetSocketAddress)socket.getLocalSocketAddress())).toString();
			if (_peerAddr != null)
				s = (new StringBuilder()).append(s).append("\nremote address = ").append(Network.addrToString(_peerAddr)).toString();
		} else
		{
			s = Network.fdToString(_fd);
		}
		if (_mcastAddr != null)
			s = (new StringBuilder()).append(s).append("\nmulticast address = ").append(Network.addrToString(_mcastAddr)).toString();
		return s;
	}

	public ConnectionInfo getInfo()
	{
		if (!$assertionsDisabled && _fd == null)
			throw new AssertionError();
		UDPConnectionInfo info = new UDPConnectionInfo();
		DatagramSocket socket = _fd.socket();
		info.localAddress = socket.getLocalAddress().getHostAddress();
		info.localPort = socket.getLocalPort();
		if (_state == 2)
		{
			if (_peerAddr != null)
			{
				info.remoteAddress = _peerAddr.getAddress().getHostAddress();
				info.remotePort = _peerAddr.getPort();
			} else
			{
				info.remoteAddress = "";
				info.remotePort = -1;
			}
		} else
		if (socket.getInetAddress() != null)
		{
			info.remoteAddress = socket.getInetAddress().getHostAddress();
			info.remotePort = socket.getPort();
		} else
		{
			info.remoteAddress = "";
			info.remotePort = -1;
		}
		if (_mcastAddr != null)
		{
			info.mcastAddress = _mcastAddr.getAddress().getHostAddress();
			info.mcastPort = _mcastAddr.getPort();
		} else
		{
			info.mcastAddress = "";
			info.mcastPort = -1;
		}
		return info;
	}

	public void checkSendSize(Buffer buf, int messageSizeMax)
	{
		if (buf.size() > messageSizeMax)
			Ex.throwMemoryLimitException(buf.size(), messageSizeMax);
		int packetSize = Math.min(65507, _sndSize - 28);
		if (packetSize < buf.size())
			throw new DatagramLimitException();
		else
			return;
	}

	public final int effectivePort()
	{
		return _addr.getPort();
	}

	UdpTransceiver(Instance instance, InetSocketAddress addr, String mcastInterface, int mcastTtl)
	{
		_mcastAddr = null;
		_peerAddr = null;
		_traceLevels = instance.traceLevels();
		_logger = instance.initializationData().logger;
		_stats = instance.initializationData().stats;
		_state = 0;
		_addr = addr;
		try
		{
			_fd = Network.createUdpSocket();
			setBufSize(instance);
			Network.setBlock(_fd, false);
			Network.doConnect(_fd, _addr);
			_state = 1;
			if (_addr.getAddress().isMulticastAddress())
				configureMulticast(null, mcastInterface, mcastTtl);
			if (_traceLevels.network >= 1)
			{
				String s = (new StringBuilder()).append("starting to send udp packets\n").append(toString()).toString();
				_logger.trace(_traceLevels.networkCat, s);
			}
		}
		catch (LocalException ex)
		{
			_fd = null;
			throw ex;
		}
	}

	UdpTransceiver(Instance instance, String host, int port, String mcastInterface, boolean connect)
	{
		_mcastAddr = null;
		_peerAddr = null;
		_traceLevels = instance.traceLevels();
		_logger = instance.initializationData().logger;
		_stats = instance.initializationData().stats;
		_state = connect ? 0 : 2;
		try
		{
			_fd = Network.createUdpSocket();
			setBufSize(instance);
			Network.setBlock(_fd, false);
			_addr = Network.getAddressForServer(host, port, instance.protocolSupport());
			if (_traceLevels.network >= 2)
			{
				String s = (new StringBuilder()).append("attempting to bind to udp socket ").append(Network.addrToString(_addr)).toString();
				_logger.trace(_traceLevels.networkCat, s);
			}
			if (_addr.getAddress().isMulticastAddress())
			{
				Network.setReuseAddress(_fd, true);
				_mcastAddr = _addr;
				if (System.getProperty("os.name").startsWith("Windows") || System.getProperty("java.vm.name").startsWith("OpenJDK"))
				{
					int protocol = _mcastAddr.getAddress().getAddress().length != 4 ? 1 : 0;
					_addr = Network.getAddressForServer("", port, protocol);
				}
				_addr = Network.doBind(_fd, _addr);
				if (port == 0)
					_mcastAddr = new InetSocketAddress(_mcastAddr.getAddress(), _addr.getPort());
				configureMulticast(_mcastAddr, mcastInterface, -1);
			} else
			{
				if (!System.getProperty("os.name").startsWith("Windows"))
					Network.setReuseAddress(_fd, true);
				_addr = Network.doBind(_fd, _addr);
			}
			if (_traceLevels.network >= 1)
			{
				StringBuffer s = new StringBuffer("starting to receive udp packets\n");
				s.append(toString());
				List interfaces = Network.getHostsForEndpointExpand(_addr.getAddress().getHostAddress(), instance.protocolSupport(), true);
				if (!interfaces.isEmpty())
				{
					s.append("\nlocal interfaces: ");
					s.append(StringUtil.joinString(interfaces, ", "));
				}
				_logger.trace(_traceLevels.networkCat, s.toString());
			}
		}
		catch (LocalException ex)
		{
			_fd = null;
			throw ex;
		}
	}

	private synchronized void setBufSize(Instance instance)
	{
		if (!$assertionsDisabled && _fd == null)
			throw new AssertionError();
		for (int i = 0; i < 2; i++)
		{
			String direction;
			String prop;
			int dfltSize;
			if (i == 0)
			{
				direction = "receive";
				prop = "Ice.UDP.RcvSize";
				dfltSize = Network.getRecvBufferSize(_fd);
				_rcvSize = dfltSize;
			} else
			{
				direction = "send";
				prop = "Ice.UDP.SndSize";
				dfltSize = Network.getSendBufferSize(_fd);
				_sndSize = dfltSize;
			}
			int sizeRequested = instance.initializationData().properties.getPropertyAsIntWithDefault(prop, dfltSize);
			if (sizeRequested < 42)
			{
				_logger.warning((new StringBuilder()).append("Invalid ").append(prop).append(" value of ").append(sizeRequested).append(" adjusted to ").append(dfltSize).toString());
				sizeRequested = dfltSize;
			}
			if (sizeRequested == dfltSize)
				continue;
			int sizeSet;
			if (i == 0)
			{
				Network.setRecvBufferSize(_fd, sizeRequested);
				_rcvSize = Network.getRecvBufferSize(_fd);
				sizeSet = _rcvSize;
			} else
			{
				Network.setSendBufferSize(_fd, sizeRequested);
				_sndSize = Network.getSendBufferSize(_fd);
				sizeSet = _sndSize;
			}
			if (sizeSet < sizeRequested)
				_logger.warning((new StringBuilder()).append("UDP ").append(direction).append(" buffer size: requested size of ").append(sizeRequested).append(" adjusted to ").append(sizeSet).toString());
		}

	}

	private void configureMulticast(InetSocketAddress group, String interfaceAddr, int ttl)
	{
		DatagramSocketImpl socketImpl;
		Field socketFd;
		Class cls = Util.findClass("java.net.PlainDatagramSocketImpl", null);
		if (cls == null)
			throw new SocketException();
		Constructor c = cls.getDeclaredConstructor((Class[])null);
		c.setAccessible(true);
		socketImpl = (DatagramSocketImpl)c.newInstance((Object[])null);
		try
		{
			Method m = cls.getDeclaredMethod("create", (Class[])null);
			m.setAccessible(true);
			m.invoke(socketImpl, new Object[0]);
		}
		catch (NoSuchMethodException ex) { }
		cls = Util.findClass("sun.nio.ch.DatagramChannelImpl", null);
		if (cls == null)
			throw new SocketException();
		Field channelFd = cls.getDeclaredField("fd");
		channelFd.setAccessible(true);
		socketFd = java/net/DatagramSocketImpl.getDeclaredField("fd");
		socketFd.setAccessible(true);
		socketFd.set(socketImpl, channelFd.get(_fd));
		NetworkInterface intf = null;
		if (interfaceAddr.length() != 0)
		{
			intf = NetworkInterface.getByName(interfaceAddr);
			if (intf == null)
			{
				InetSocketAddress addr = Network.getAddress(interfaceAddr, 0, 0);
				intf = NetworkInterface.getByInetAddress(addr.getAddress());
			}
		}
		if (group != null)
		{
			Method m;
			Object args[];
			try
			{
				Class types[] = {
					java/net/SocketAddress, java/net/NetworkInterface
				};
				m = socketImpl.getClass().getDeclaredMethod("joinGroup", types);
				args = (new Object[] {
					group, intf
				});
			}
			catch (NoSuchMethodException ex)
			{
				Class types[] = {
					java/net/InetAddress, java/net/NetworkInterface
				};
				m = socketImpl.getClass().getDeclaredMethod("join", types);
				args = (new Object[] {
					group.getAddress(), intf
				});
			}
			m.setAccessible(true);
			m.invoke(socketImpl, args);
		} else
		if (intf != null)
		{
			Class types[] = {
				Integer.TYPE, java/lang/Object
			};
			Method m;
			try
			{
				m = socketImpl.getClass().getDeclaredMethod("setOption", types);
			}
			catch (NoSuchMethodException ex)
			{
				m = socketImpl.getClass().getDeclaredMethod("socketSetOption", types);
			}
			m.setAccessible(true);
			Object args[] = {
				Integer.valueOf(31), intf
			};
			m.invoke(socketImpl, args);
		}
		if (ttl != -1)
		{
			Class types[] = {
				Integer.TYPE
			};
			Method m = java/net/DatagramSocketImpl.getDeclaredMethod("setTimeToLive", types);
			m.setAccessible(true);
			Object args[] = {
				Integer.valueOf(ttl)
			};
			m.invoke(socketImpl, args);
		}
		socketFd.set(socketImpl, null);
		break MISSING_BLOCK_LABEL_508;
		Exception exception;
		exception;
		socketFd.set(socketImpl, null);
		throw exception;
		Exception ex;
		ex;
		throw new SocketException(ex);
	}

	protected synchronized void finalize()
		throws Throwable
	{
		Assert.FinalizerAssert(_fd == null);
		super.finalize();
	}

}
