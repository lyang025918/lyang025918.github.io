// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TcpTransceiver.java

package IceInternal;

import Ice.*;
import IceUtilInternal.Assert;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SocketChannel;

// Referenced classes of package IceInternal:
//			Transceiver, Network, TraceLevels, Buffer, 
//			Ex, Instance

final class TcpTransceiver
	implements Transceiver
{

	private SocketChannel _fd;
	private InetSocketAddress _connectAddr;
	private TraceLevels _traceLevels;
	private Logger _logger;
	private Stats _stats;
	private String _desc;
	private int _state;
	private int _maxSendPacketSize;
	private static final int StateNeedConnect = 0;
	private static final int StateConnectPending = 1;
	private static final int StateConnected = 2;
	static final boolean $assertionsDisabled = !IceInternal/TcpTransceiver.desiredAssertionStatus();

	public SelectableChannel fd()
	{
		if (!$assertionsDisabled && _fd == null)
			throw new AssertionError();
		else
			return _fd;
	}

	public int initialize()
	{
		if (_state == 0)
		{
			_state = 1;
			return 8;
		}
		if (_state <= 1)
		{
			try
			{
				Network.doFinishConnect(_fd);
				_state = 2;
				_desc = Network.fdToString(_fd);
			}
			catch (LocalException ex)
			{
				if (_traceLevels.network >= 2)
				{
					Socket fd = _fd.socket();
					StringBuilder s = new StringBuilder(128);
					s.append("failed to establish tcp connection\n");
					s.append("local address = ");
					s.append(Network.addrToString(fd.getLocalAddress(), fd.getLocalPort()));
					s.append("\nremote address = ");
					if (!$assertionsDisabled && _connectAddr == null)
						throw new AssertionError();
					s.append(Network.addrToString(_connectAddr));
					_logger.trace(_traceLevels.networkCat, s.toString());
				}
				throw ex;
			}
			if (_traceLevels.network >= 1)
			{
				String s = (new StringBuilder()).append("tcp connection established\n").append(_desc).toString();
				_logger.trace(_traceLevels.networkCat, s);
			}
		}
		if (!$assertionsDisabled && _state != 2)
			throw new AssertionError();
		else
			return 0;
	}

	public void close()
	{
		Exception exception;
		if (_state == 2 && _traceLevels.network >= 1)
		{
			String s = (new StringBuilder()).append("closing tcp connection\n").append(toString()).toString();
			_logger.trace(_traceLevels.networkCat, s);
		}
		if (!$assertionsDisabled && _fd == null)
			throw new AssertionError();
		try
		{
			_fd.close();
		}
		catch (IOException ex)
		{
			throw new SocketException(ex);
		}
		finally
		{
			_fd = null;
		}
		_fd = null;
		break MISSING_BLOCK_LABEL_113;
		throw exception;
	}

	public boolean write(Buffer buf)
	{
		int size;
		int packetSize;
		size = buf.b.limit();
		packetSize = size - buf.b.position();
		if (_maxSendPacketSize > 0 && packetSize > _maxSendPacketSize)
		{
			packetSize = _maxSendPacketSize;
			buf.b.limit(buf.b.position() + packetSize);
		}
_L2:
		if (!buf.b.hasRemaining())
			break MISSING_BLOCK_LABEL_330;
		int ret;
		if (!$assertionsDisabled && _fd == null)
			throw new AssertionError();
		ret = _fd.write(buf.b);
		if (ret == -1)
			throw new ConnectionLostException();
		if (ret != 0)
			break MISSING_BLOCK_LABEL_137;
		if (packetSize == _maxSendPacketSize)
			buf.b.limit(size);
		return false;
		try
		{
			if (_traceLevels.network >= 3)
			{
				String s = (new StringBuilder()).append("sent ").append(ret).append(" of ").append(packetSize).append(" bytes via tcp\n").append(toString()).toString();
				_logger.trace(_traceLevels.networkCat, s);
			}
			if (_stats != null)
				_stats.bytesSent(type(), ret);
			if (packetSize == _maxSendPacketSize)
			{
				if (!$assertionsDisabled && buf.b.position() != buf.b.limit())
					throw new AssertionError();
				packetSize = size - buf.b.position();
				if (packetSize > _maxSendPacketSize)
					packetSize = _maxSendPacketSize;
				buf.b.limit(buf.b.position() + packetSize);
			}
		}
		catch (InterruptedIOException ex) { }
		catch (IOException ex)
		{
			throw new SocketException(ex);
		}
		continue; /* Loop/switch isn't completed */
		return true;
		if (true) goto _L2; else goto _L1
_L1:
	}

	public boolean read(Buffer buf, BooleanHolder moreData)
	{
		int packetSize;
		packetSize = buf.b.remaining();
		moreData.value = false;
_L2:
		if (!buf.b.hasRemaining())
			break; /* Loop/switch isn't completed */
		int ret;
		if (!$assertionsDisabled && _fd == null)
			throw new AssertionError();
		ret = _fd.read(buf.b);
		if (ret == -1)
			throw new ConnectionLostException();
		if (ret == 0)
			return false;
		try
		{
			if (ret > 0)
			{
				if (_traceLevels.network >= 3)
				{
					String s = (new StringBuilder()).append("received ").append(ret).append(" of ").append(packetSize).append(" bytes via tcp\n").append(toString()).toString();
					_logger.trace(_traceLevels.networkCat, s);
				}
				if (_stats != null)
					_stats.bytesReceived(type(), ret);
			}
			packetSize = buf.b.remaining();
		}
		catch (InterruptedIOException ex) { }
		catch (IOException ex)
		{
			throw new ConnectionLostException(ex);
		}
		if (true) goto _L2; else goto _L1
_L1:
		return true;
	}

	public String type()
	{
		return "tcp";
	}

	public String toString()
	{
		return _desc;
	}

	public ConnectionInfo getInfo()
	{
		if (!$assertionsDisabled && _fd == null)
			throw new AssertionError();
		TCPConnectionInfo info = new TCPConnectionInfo();
		Socket socket = _fd.socket();
		info.localAddress = socket.getLocalAddress().getHostAddress();
		info.localPort = socket.getLocalPort();
		if (socket.getInetAddress() != null)
		{
			info.remoteAddress = socket.getInetAddress().getHostAddress();
			info.remotePort = socket.getPort();
		} else
		{
			info.remoteAddress = "";
			info.remotePort = -1;
		}
		return info;
	}

	public void checkSendSize(Buffer buf, int messageSizeMax)
	{
		if (buf.size() > messageSizeMax)
			Ex.throwMemoryLimitException(buf.size(), messageSizeMax);
	}

	TcpTransceiver(Instance instance, SocketChannel fd, boolean connected, InetSocketAddress connectAddr)
	{
		_fd = fd;
		_connectAddr = connectAddr;
		_traceLevels = instance.traceLevels();
		_logger = instance.initializationData().logger;
		_stats = instance.initializationData().stats;
		_state = connected ? 2 : 0;
		_desc = Network.fdToString(_fd);
		_maxSendPacketSize = 0;
		if (System.getProperty("os.name").startsWith("Windows"))
		{
			_maxSendPacketSize = Network.getSendBufferSize(_fd) / 2;
			if (_maxSendPacketSize < 512)
				_maxSendPacketSize = 0;
		}
	}

	protected synchronized void finalize()
		throws Throwable
	{
		Assert.FinalizerAssert(_fd == null);
		super.finalize();
	}

}
