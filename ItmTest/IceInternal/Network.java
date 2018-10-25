// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Network.java

package IceInternal;

import Ice.ConnectFailedException;
import Ice.ConnectionRefusedException;
import Ice.DNSException;
import Ice.Logger;
import Ice.Properties;
import Ice.SocketException;
import Ice.TimeoutException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.Pipe;
import java.nio.channels.SelectableChannel;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.channels.spi.AbstractSelectableChannel;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

public final class Network
{
	public static final class SocketPair
	{

		public AbstractSelectableChannel source;
		public WritableByteChannel sink;

		public SocketPair()
		{
		}
	}


	public static final int EnableIPv4 = 0;
	public static final int EnableIPv6 = 1;
	public static final int EnableBoth = 2;
	static final boolean $assertionsDisabled = !IceInternal/Network.desiredAssertionStatus();

	public Network()
	{
	}

	public static boolean connectionRefused(ConnectException ex)
	{
		String msg = ex.getMessage().toLowerCase();
		if (msg != null)
		{
			String msgs[] = {
				"connection refused", "remote host refused an attempted connect operation"
			};
			String arr$[] = msgs;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				String m = arr$[i$];
				if (msg.indexOf(m) != -1)
					return true;
			}

		}
		return false;
	}

	public static boolean noMoreFds(Throwable ex)
	{
		String msg = ex.getMessage();
		if (msg != null)
		{
			msg = msg.toLowerCase();
			String msgs[] = {
				"too many open files", "file table overflow", "too many open files in system"
			};
			String arr$[] = msgs;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				String m = arr$[i$];
				if (msg.indexOf(m) != -1)
					return true;
			}

		}
		return false;
	}

	public static SocketChannel createTcpSocket()
	{
		SocketChannel fd;
		fd = SocketChannel.open();
		Socket socket = fd.socket();
		socket.setTcpNoDelay(true);
		socket.setKeepAlive(true);
		return fd;
		IOException ex;
		ex;
		throw new SocketException(ex);
	}

	public static ServerSocketChannel createTcpServerSocket()
	{
		ServerSocketChannel fd = ServerSocketChannel.open();
		return fd;
		IOException ex;
		ex;
		throw new SocketException(ex);
	}

	public static DatagramChannel createUdpSocket()
	{
		return DatagramChannel.open();
		IOException ex;
		ex;
		throw new SocketException(ex);
	}

	public static void closeSocketNoThrow(SelectableChannel fd)
	{
		try
		{
			fd.close();
		}
		catch (IOException ex) { }
	}

	public static void closeSocket(SelectableChannel fd)
	{
		try
		{
			fd.close();
		}
		catch (IOException ex)
		{
			throw new SocketException(ex);
		}
	}

	public static void setBlock(SelectableChannel fd, boolean block)
	{
		try
		{
			fd.configureBlocking(block);
		}
		catch (IOException ex)
		{
			closeSocketNoThrow(fd);
			throw new SocketException(ex);
		}
	}

	public static void setReuseAddress(DatagramChannel fd, boolean reuse)
	{
		try
		{
			fd.socket().setReuseAddress(reuse);
		}
		catch (IOException ex)
		{
			closeSocketNoThrow(fd);
			throw new SocketException(ex);
		}
	}

	public static void setReuseAddress(ServerSocketChannel fd, boolean reuse)
	{
		try
		{
			fd.socket().setReuseAddress(reuse);
		}
		catch (IOException ex)
		{
			closeSocketNoThrow(fd);
			throw new SocketException(ex);
		}
	}

	public static InetSocketAddress doBind(ServerSocketChannel fd, InetSocketAddress addr, int backlog)
	{
		ServerSocket sock;
		sock = fd.socket();
		sock.bind(addr, backlog);
		return (InetSocketAddress)sock.getLocalSocketAddress();
		IOException ex;
		ex;
		closeSocketNoThrow(fd);
		throw new SocketException(ex);
	}

	public static InetSocketAddress doBind(DatagramChannel fd, InetSocketAddress addr)
	{
		DatagramSocket sock;
		sock = fd.socket();
		sock.bind(addr);
		return (InetSocketAddress)sock.getLocalSocketAddress();
		IOException ex;
		ex;
		closeSocketNoThrow(fd);
		throw new SocketException(ex);
	}

	public static SocketChannel doAccept(ServerSocketChannel afd)
	{
		SocketChannel fd = null;
		do
			try
			{
				fd = afd.accept();
				break;
			}
			catch (IOException ex)
			{
				if (!interrupted(ex))
					throw new SocketException(ex);
			}
		while (true);
		try
		{
			Socket socket = fd.socket();
			socket.setTcpNoDelay(true);
			socket.setKeepAlive(true);
		}
		catch (IOException ex)
		{
			throw new SocketException(ex);
		}
		return fd;
	}

	public static boolean doConnect(SocketChannel fd, InetSocketAddress addr)
	{
		if (!fd.connect(addr))
			return false;
		break MISSING_BLOCK_LABEL_71;
		ConnectException ex;
		ex;
		closeSocketNoThrow(fd);
		if (connectionRefused(ex))
			throw new ConnectionRefusedException(ex);
		else
			throw new ConnectFailedException(ex);
		ex;
		closeSocketNoThrow(fd);
		throw new SocketException(ex);
		ex;
		closeSocketNoThrow(fd);
		throw new SocketException(ex);
		if (System.getProperty("os.name").equals("Linux") && addr.equals(fd.socket().getLocalSocketAddress()))
		{
			closeSocketNoThrow(fd);
			throw new ConnectionRefusedException();
		} else
		{
			return true;
		}
	}

	public static void doFinishConnect(SocketChannel fd)
	{
		try
		{
			if (!fd.finishConnect())
				throw new ConnectFailedException();
			if (System.getProperty("os.name").equals("Linux"))
			{
				java.net.SocketAddress addr = fd.socket().getRemoteSocketAddress();
				if (addr != null && addr.equals(fd.socket().getLocalSocketAddress()))
					throw new ConnectionRefusedException();
			}
		}
		catch (ConnectException ex)
		{
			if (connectionRefused(ex))
				throw new ConnectionRefusedException(ex);
			else
				throw new ConnectFailedException(ex);
		}
		catch (IOException ex)
		{
			throw new SocketException(ex);
		}
	}

	public static void doConnect(DatagramChannel fd, InetSocketAddress addr)
	{
		try
		{
			fd.connect(addr);
		}
		catch (ConnectException ex)
		{
			closeSocketNoThrow(fd);
			if (connectionRefused(ex))
				throw new ConnectionRefusedException(ex);
			else
				throw new ConnectFailedException(ex);
		}
		catch (IOException ex)
		{
			closeSocketNoThrow(fd);
			throw new SocketException(ex);
		}
	}

	public static SocketChannel doAccept(ServerSocketChannel fd, int timeout)
	{
		SocketChannel result = null;
_L2:
		if (result != null)
			break; /* Loop/switch isn't completed */
		Selector selector;
		result = fd.accept();
		if (result != null)
			continue; /* Loop/switch isn't completed */
		selector = Selector.open();
		do
			try
			{
				fd.register(selector, 16);
				int n;
				if (timeout > 0)
					n = selector.select(timeout);
				else
				if (timeout == 0)
					n = selector.selectNow();
				else
					n = selector.select();
				if (n == 0)
					throw new TimeoutException();
				break;
			}
			catch (IOException ex)
			{
				if (!interrupted(ex))
					throw new SocketException(ex);
			}
		while (true);
		try
		{
			selector.close();
		}
		catch (IOException ex) { }
		continue; /* Loop/switch isn't completed */
		Exception exception;
		exception;
		try
		{
			selector.close();
		}
		catch (IOException ex) { }
		throw exception;
		IOException ex;
		ex;
		if (!interrupted(ex))
			throw new SocketException(ex);
		if (true) goto _L2; else goto _L1
_L1:
		try
		{
			Socket socket = result.socket();
			socket.setTcpNoDelay(true);
			socket.setKeepAlive(true);
		}
		// Misplaced declaration of an exception variable
		catch (Socket socket)
		{
			throw new SocketException(socket);
		}
		return result;
	}

	public static void setSendBufferSize(SocketChannel fd, int size)
	{
		try
		{
			Socket socket = fd.socket();
			socket.setSendBufferSize(size);
		}
		catch (IOException ex)
		{
			closeSocketNoThrow(fd);
			throw new SocketException(ex);
		}
	}

	public static int getSendBufferSize(SocketChannel fd)
	{
		int size;
		try
		{
			Socket socket = fd.socket();
			size = socket.getSendBufferSize();
		}
		catch (IOException ex)
		{
			closeSocketNoThrow(fd);
			throw new SocketException(ex);
		}
		return size;
	}

	public static void setRecvBufferSize(SocketChannel fd, int size)
	{
		try
		{
			Socket socket = fd.socket();
			socket.setReceiveBufferSize(size);
		}
		catch (IOException ex)
		{
			closeSocketNoThrow(fd);
			throw new SocketException(ex);
		}
	}

	public static int getRecvBufferSize(SocketChannel fd)
	{
		int size;
		try
		{
			Socket socket = fd.socket();
			size = socket.getReceiveBufferSize();
		}
		catch (IOException ex)
		{
			closeSocketNoThrow(fd);
			throw new SocketException(ex);
		}
		return size;
	}

	public static void setRecvBufferSize(ServerSocketChannel fd, int size)
	{
		try
		{
			ServerSocket socket = fd.socket();
			socket.setReceiveBufferSize(size);
		}
		catch (IOException ex)
		{
			closeSocketNoThrow(fd);
			throw new SocketException(ex);
		}
	}

	public static int getRecvBufferSize(ServerSocketChannel fd)
	{
		int size;
		try
		{
			ServerSocket socket = fd.socket();
			size = socket.getReceiveBufferSize();
		}
		catch (IOException ex)
		{
			closeSocketNoThrow(fd);
			throw new SocketException(ex);
		}
		return size;
	}

	public static void setSendBufferSize(DatagramChannel fd, int size)
	{
		try
		{
			DatagramSocket socket = fd.socket();
			socket.setSendBufferSize(size);
		}
		catch (IOException ex)
		{
			closeSocketNoThrow(fd);
			throw new SocketException(ex);
		}
	}

	public static int getSendBufferSize(DatagramChannel fd)
	{
		int size;
		try
		{
			DatagramSocket socket = fd.socket();
			size = socket.getSendBufferSize();
		}
		catch (IOException ex)
		{
			closeSocketNoThrow(fd);
			throw new SocketException(ex);
		}
		return size;
	}

	public static void setRecvBufferSize(DatagramChannel fd, int size)
	{
		try
		{
			DatagramSocket socket = fd.socket();
			socket.setReceiveBufferSize(size);
		}
		catch (IOException ex)
		{
			closeSocketNoThrow(fd);
			throw new SocketException(ex);
		}
	}

	public static int getRecvBufferSize(DatagramChannel fd)
	{
		int size;
		try
		{
			DatagramSocket socket = fd.socket();
			size = socket.getReceiveBufferSize();
		}
		catch (IOException ex)
		{
			closeSocketNoThrow(fd);
			throw new SocketException(ex);
		}
		return size;
	}

	public static InetSocketAddress getAddress(String host, int port, int protocol)
	{
		return getAddressImpl(host, port, protocol, false);
	}

	public static InetSocketAddress getAddressForServer(String host, int port, int protocol)
	{
		return getAddressImpl(host, port, protocol, true);
	}

	public static int compareAddress(InetSocketAddress addr1, InetSocketAddress addr2)
	{
		if (addr1.getPort() < addr2.getPort())
			return -1;
		if (addr2.getPort() < addr1.getPort())
			return 1;
		byte larr[] = addr1.getAddress().getAddress();
		byte rarr[] = addr2.getAddress().getAddress();
		if (larr.length < rarr.length)
			return -1;
		if (rarr.length < larr.length)
			return 1;
		if (!$assertionsDisabled && larr.length != rarr.length)
			throw new AssertionError();
		for (int i = 0; i < larr.length; i++)
		{
			if (larr[i] < rarr[i])
				return -1;
			if (rarr[i] < larr[i])
				return 1;
		}

		return 0;
	}

	public static InetAddress getLocalAddress(int protocol)
	{
		InetAddress addr = null;
		try
		{
			addr = InetAddress.getLocalHost();
		}
		catch (UnknownHostException ex) { }
		catch (NullPointerException ex) { }
		if (addr == null || !isValidAddr(addr, protocol))
		{
			ArrayList addrs = getLocalAddresses(protocol);
			Iterator iter = addrs.iterator();
			do
			{
				if (addr != null || !iter.hasNext())
					break;
				InetAddress a = (InetAddress)iter.next();
				if (protocol == 2 || isValidAddr(a, protocol))
					addr = a;
			} while (true);
			if (addr == null)
				addr = getLoopbackAddresses(protocol)[0];
		}
		if (!$assertionsDisabled && addr == null)
			throw new AssertionError();
		else
			return addr;
	}

	public static ArrayList getAddresses(String host, int port, int protocol)
	{
		ArrayList addresses = new ArrayList();
		try
		{
			InetAddress addrs[];
			if (host == null || host.length() == 0)
				addrs = getLoopbackAddresses(protocol);
			else
				addrs = InetAddress.getAllByName(host);
			InetAddress arr$[] = addrs;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				InetAddress addr = arr$[i$];
				if (protocol == 2 || isValidAddr(addr, protocol))
					addresses.add(new InetSocketAddress(addr, port));
			}

		}
		catch (UnknownHostException ex)
		{
			throw new DNSException(0, host, ex);
		}
		catch (SecurityException ex)
		{
			throw new SocketException(ex);
		}
		if (addresses.size() == 0)
			throw new DNSException(0, host);
		else
			return addresses;
	}

	public static ArrayList getLocalAddresses(int protocol)
	{
		ArrayList result = new ArrayList();
		try
		{
			for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements();)
			{
				NetworkInterface iface = (NetworkInterface)ifaces.nextElement();
				Enumeration addrs = iface.getInetAddresses();
				while (addrs.hasMoreElements()) 
				{
					InetAddress addr = (InetAddress)addrs.nextElement();
					if (!addr.isLoopbackAddress() && (protocol == 2 || isValidAddr(addr, protocol)))
						result.add(addr);
				}
			}

		}
		catch (java.net.SocketException ex)
		{
			throw new SocketException(ex);
		}
		catch (SecurityException ex)
		{
			throw new SocketException(ex);
		}
		return result;
	}

	public static SocketPair createPipe()
	{
		SocketPair fds = new SocketPair();
		try
		{
			Pipe pipe = Pipe.open();
			fds.sink = pipe.sink();
			fds.source = pipe.source();
		}
		catch (IOException ex)
		{
			throw new SocketException(ex);
		}
		return fds;
	}

	public static ArrayList getHostsForEndpointExpand(String host, int protocolSupport, boolean includeLoopback)
	{
		boolean wildcard = host == null || host.length() == 0;
		if (!wildcard)
			try
			{
				wildcard = InetAddress.getByName(host).isAnyLocalAddress();
			}
			catch (UnknownHostException ex) { }
			catch (SecurityException ex)
			{
				throw new SocketException(ex);
			}
		ArrayList hosts = new ArrayList();
		if (wildcard)
		{
			ArrayList addrs = getLocalAddresses(protocolSupport);
			Iterator i$ = addrs.iterator();
			do
			{
				if (!i$.hasNext())
					break;
				InetAddress addr = (InetAddress)i$.next();
				if (!addr.isLinkLocalAddress())
					hosts.add(addr.getHostAddress());
			} while (true);
			if (includeLoopback || hosts.isEmpty())
			{
				if (protocolSupport != 1)
					hosts.add("127.0.0.1");
				if (protocolSupport != 0)
					hosts.add("0:0:0:0:0:0:0:1");
			}
		}
		return hosts;
	}

	public static void setTcpBufSize(SocketChannel socket, Properties properties, Logger logger)
	{
		int dfltBufSize = 0;
		if (System.getProperty("os.name").startsWith("Windows"))
			dfltBufSize = 0x20000;
		int sizeRequested = properties.getPropertyAsIntWithDefault("Ice.TCP.RcvSize", dfltBufSize);
		if (sizeRequested > 0)
		{
			setRecvBufferSize(socket, sizeRequested);
			int size = getRecvBufferSize(socket);
			if (size < sizeRequested)
				logger.warning((new StringBuilder()).append("TCP receive buffer size: requested size of ").append(sizeRequested).append(" adjusted to ").append(size).toString());
		}
		sizeRequested = properties.getPropertyAsIntWithDefault("Ice.TCP.SndSize", dfltBufSize);
		if (sizeRequested > 0)
		{
			setSendBufferSize(socket, sizeRequested);
			int size = getSendBufferSize(socket);
			if (size < sizeRequested)
				logger.warning((new StringBuilder()).append("TCP send buffer size: requested size of ").append(sizeRequested).append(" adjusted to ").append(size).toString());
		}
	}

	public static void setTcpBufSize(ServerSocketChannel socket, Properties properties, Logger logger)
	{
		int dfltBufSize = 0;
		if (System.getProperty("os.name").startsWith("Windows"))
			dfltBufSize = 0x20000;
		int sizeRequested = properties.getPropertyAsIntWithDefault("Ice.TCP.RcvSize", dfltBufSize);
		if (sizeRequested > 0)
		{
			setRecvBufferSize(socket, sizeRequested);
			int size = getRecvBufferSize(socket);
			if (size < sizeRequested)
				logger.warning((new StringBuilder()).append("TCP receive buffer size: requested size of ").append(sizeRequested).append(" adjusted to ").append(size).toString());
		}
	}

	public static String fdToString(SelectableChannel fd)
	{
		if (fd == null)
			return "<closed>";
		InetAddress localAddr = null;
		InetAddress remoteAddr = null;
		int localPort = -1;
		int remotePort = -1;
		if (fd instanceof SocketChannel)
		{
			Socket socket = ((SocketChannel)fd).socket();
			localAddr = socket.getLocalAddress();
			localPort = socket.getLocalPort();
			remoteAddr = socket.getInetAddress();
			remotePort = socket.getPort();
		} else
		if (fd instanceof DatagramChannel)
		{
			DatagramSocket socket = ((DatagramChannel)fd).socket();
			localAddr = socket.getLocalAddress();
			localPort = socket.getLocalPort();
			remoteAddr = socket.getInetAddress();
			remotePort = socket.getPort();
		} else
		if (!$assertionsDisabled)
			throw new AssertionError();
		return addressesToString(localAddr, localPort, remoteAddr, remotePort);
	}

	public static String fdToString(Socket fd)
	{
		if (fd == null)
		{
			return "<closed>";
		} else
		{
			InetAddress localAddr = fd.getLocalAddress();
			int localPort = fd.getLocalPort();
			InetAddress remoteAddr = fd.getInetAddress();
			int remotePort = fd.getPort();
			return addressesToString(localAddr, localPort, remoteAddr, remotePort);
		}
	}

	public static String addressesToString(InetAddress localAddr, int localPort, InetAddress remoteAddr, int remotePort)
	{
		StringBuilder s = new StringBuilder(128);
		s.append("local address = ");
		s.append(addrToString(localAddr, localPort));
		if (remoteAddr == null)
		{
			s.append("\nremote address = <not connected>");
		} else
		{
			s.append("\nremote address = ");
			s.append(addrToString(remoteAddr, remotePort));
		}
		return s.toString();
	}

	public static String addrToString(InetSocketAddress addr)
	{
		StringBuilder s = new StringBuilder(128);
		s.append(addr.getAddress().getHostAddress());
		s.append(':');
		s.append(addr.getPort());
		return s.toString();
	}

	public static boolean interrupted(IOException ex)
	{
		return ex instanceof InterruptedIOException;
	}

	private static boolean isValidAddr(InetAddress addr, int protocol)
	{
		byte bytes[] = null;
		if (addr != null)
			bytes = addr.getAddress();
		return bytes != null && (bytes.length == 16 && protocol == 1 || bytes.length == 4 && protocol == 0);
	}

	public static String addrToString(InetAddress addr, int port)
	{
		StringBuffer s = new StringBuffer();
		if (addr == null || addr.isAnyLocalAddress())
			s.append("<not available>");
		else
			s.append(addr.getHostAddress());
		if (port > 0)
		{
			s.append(':');
			s.append(port);
		}
		return s.toString();
	}

	private static InetSocketAddress getAddressImpl(String host, int port, int protocol, boolean server)
	{
		InetAddress arr$[];
		int len$;
		int i$;
		InetAddress addrs[];
		if (host == null || host.length() == 0)
		{
			if (server)
				addrs = getWildcardAddresses(protocol);
			else
				addrs = getLoopbackAddresses(protocol);
		} else
		{
			addrs = InetAddress.getAllByName(host);
		}
		arr$ = addrs;
		len$ = arr$.length;
		i$ = 0;
_L1:
		InetAddress addr;
		if (i$ >= len$)
			break MISSING_BLOCK_LABEL_125;
		addr = arr$[i$];
		if (protocol == 2 || isValidAddr(addr, protocol))
			return new InetSocketAddress(addr, port);
		try
		{
			i$++;
		}
		catch (UnknownHostException ex)
		{
			throw new DNSException(0, host, ex);
		}
		catch (SecurityException ex)
		{
			throw new SocketException(ex);
		}
		  goto _L1
		throw new DNSException(0, host);
	}

	private static InetAddress[] getLoopbackAddresses(int protocol)
	{
		InetAddress addrs[];
		addrs = new InetAddress[protocol != 2 ? 1 : 2];
		int i = 0;
		if (protocol != 1)
			addrs[i++] = InetAddress.getByName("127.0.0.1");
		if (protocol != 0)
			addrs[i++] = InetAddress.getByName("::1");
		return addrs;
		UnknownHostException ex;
		ex;
		if (!$assertionsDisabled)
			throw new AssertionError();
		else
			return null;
		ex;
		throw new SocketException(ex);
	}

	private static InetAddress[] getWildcardAddresses(int protocol)
	{
		InetAddress addrs[];
		addrs = new InetAddress[protocol != 2 ? 1 : 2];
		int i = 0;
		if (protocol != 0)
			addrs[i++] = InetAddress.getByName("::0");
		if (protocol != 1)
			addrs[i++] = InetAddress.getByName("0.0.0.0");
		return addrs;
		UnknownHostException ex;
		ex;
		if (!$assertionsDisabled)
			throw new AssertionError();
		else
			return null;
		ex;
		throw new SocketException(ex);
	}

}
