// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TransceiverI.java

package IceSSL;

import Ice.*;
import IceInternal.*;
import IceUtilInternal.Assert;
import IceUtilInternal.Base64;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SocketChannel;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.util.ArrayList;
import javax.net.ssl.*;

// Referenced classes of package IceSSL:
//			NativeConnectionInfo, Instance

final class TransceiverI
	implements Transceiver
{

	private Instance _instance;
	private SocketChannel _fd;
	private SSLEngine _engine;
	private String _host;
	private boolean _incoming;
	private String _adapterName;
	private InetSocketAddress _connectAddr;
	private int _state;
	private Logger _logger;
	private Stats _stats;
	private String _desc;
	private int _maxPacketSize;
	private ByteBuffer _appInput;
	private ByteBuffer _netInput;
	private ByteBuffer _netOutput;
	private static ByteBuffer _emptyBuffer = ByteBuffer.allocate(0);
	private static final int StateNeedConnect = 0;
	private static final int StateConnectPending = 1;
	private static final int StateConnected = 2;
	private static final int StateHandshakeComplete = 3;
	static final boolean $assertionsDisabled = !IceSSL/TransceiverI.desiredAssertionStatus();

	public SelectableChannel fd()
	{
		if (!$assertionsDisabled && _fd == null)
			throw new AssertionError();
		else
			return _fd;
	}

	public int initialize()
	{
		if (_state != 0)
			break MISSING_BLOCK_LABEL_15;
		_state = 1;
		return 8;
		int status;
		if (_state <= 1)
		{
			Network.doFinishConnect(_fd);
			_state = 2;
			_desc = Network.fdToString(_fd);
		}
		if (!$assertionsDisabled && _state != 2)
			throw new AssertionError();
		status = handshakeNonBlocking();
		LocalException ex;
		if (status != 0)
			return status;
		else
			return 0;
		ex;
		if (_instance.networkTraceLevel() >= 2)
		{
			Socket fd = _fd.socket();
			StringBuilder s = new StringBuilder(128);
			s.append("failed to establish ssl connection\n");
			s.append("local address = ");
			s.append(Network.addrToString(fd.getLocalAddress(), fd.getLocalPort()));
			s.append("\nremote address = ");
			if (!$assertionsDisabled && _connectAddr == null)
				throw new AssertionError();
			s.append(Network.addrToString(_connectAddr));
			_logger.trace(_instance.networkTraceCategory(), s.toString());
		}
		throw ex;
	}

	public void close()
	{
		if (_state == 3 && _instance.networkTraceLevel() >= 1)
		{
			String s = (new StringBuilder()).append("closing ssl connection\n").append(toString()).toString();
			_logger.trace(_instance.networkTraceCategory(), s);
		}
		if (!$assertionsDisabled && _fd == null)
			throw new AssertionError();
		if (_state >= 2)
		{
			try
			{
				_engine.closeOutbound();
				_netOutput.clear();
				while (!_engine.isOutboundDone()) 
				{
					_engine.wrap(_emptyBuffer, _netOutput);
					try
					{
						flushNonBlocking();
					}
					catch (LocalException ex) { }
				}
			}
			catch (SSLException ex) { }
			try
			{
				_engine.closeInbound();
			}
			catch (SSLException ex) { }
		}
		Network.closeSocket(_fd);
		_fd = null;
		break MISSING_BLOCK_LABEL_178;
		Exception exception;
		exception;
		_fd = null;
		throw exception;
	}

	public boolean write(Buffer buf)
	{
		if (_state < 3)
			throw new ConnectionLostException();
		int status = writeNonBlocking(buf.b);
		if (status != 0)
		{
			if (!$assertionsDisabled && status != 4)
				throw new AssertionError();
			else
				return false;
		} else
		{
			return true;
		}
	}

	public boolean read(Buffer buf, BooleanHolder moreData)
	{
		int rem;
		if (_state < 3)
			throw new ConnectionLostException();
		rem = 0;
		if (_instance.networkTraceLevel() >= 3)
			rem = buf.b.remaining();
		int pos = buf.b.position();
		fill(buf.b);
		if (_instance.networkTraceLevel() >= 3 && buf.b.position() > pos)
		{
			String s = (new StringBuilder()).append("received ").append(buf.b.position() - pos).append(" of ").append(rem).append(" bytes via ssl\n").append(toString()).toString();
			_logger.trace(_instance.networkTraceCategory(), s);
		}
		if (_stats != null && buf.b.position() > pos)
			_stats.bytesReceived(type(), buf.b.position() - pos);
_L6:
		SSLEngineResult result;
		if (!buf.b.hasRemaining())
			break MISSING_BLOCK_LABEL_503;
		_netInput.flip();
		result = _engine.unwrap(_netInput, _appInput);
		_netInput.compact();
		static class 1
		{

			static final int $SwitchMap$javax$net$ssl$SSLEngineResult$Status[];
			static final int $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[];

			static 
			{
				$SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus = new int[javax.net.ssl.SSLEngineResult.HandshakeStatus.values().length];
				try
				{
					$SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[javax.net.ssl.SSLEngineResult.HandshakeStatus.FINISHED.ordinal()] = 1;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[javax.net.ssl.SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING.ordinal()] = 2;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[javax.net.ssl.SSLEngineResult.HandshakeStatus.NEED_TASK.ordinal()] = 3;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[javax.net.ssl.SSLEngineResult.HandshakeStatus.NEED_UNWRAP.ordinal()] = 4;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[javax.net.ssl.SSLEngineResult.HandshakeStatus.NEED_WRAP.ordinal()] = 5;
				}
				catch (NoSuchFieldError ex) { }
				$SwitchMap$javax$net$ssl$SSLEngineResult$Status = new int[javax.net.ssl.SSLEngineResult.Status.values().length];
				try
				{
					$SwitchMap$javax$net$ssl$SSLEngineResult$Status[javax.net.ssl.SSLEngineResult.Status.BUFFER_OVERFLOW.ordinal()] = 1;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$javax$net$ssl$SSLEngineResult$Status[javax.net.ssl.SSLEngineResult.Status.BUFFER_UNDERFLOW.ordinal()] = 2;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$javax$net$ssl$SSLEngineResult$Status[javax.net.ssl.SSLEngineResult.Status.CLOSED.ordinal()] = 3;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$javax$net$ssl$SSLEngineResult$Status[javax.net.ssl.SSLEngineResult.Status.OK.ordinal()] = 4;
				}
				catch (NoSuchFieldError ex) { }
			}
		}

		1..SwitchMap.javax.net.ssl.SSLEngineResult.Status[result.getStatus().ordinal()];
		JVM INSTR tableswitch 1 4: default 332
	//	               1 272
	//	               2 286
	//	               3 324
	//	               4 332;
		   goto _L1 _L2 _L3 _L4 _L1
_L1:
		break; /* Loop/switch isn't completed */
_L2:
		if (!$assertionsDisabled)
			throw new AssertionError();
		break; /* Loop/switch isn't completed */
_L3:
		int status = readNonBlocking();
		if (status == 0) goto _L6; else goto _L5
_L5:
		if (!$assertionsDisabled && status != 1)
			throw new AssertionError();
		moreData.value = false;
		return false;
_L4:
		throw new ConnectionLostException();
		int pos = buf.b.position();
		fill(buf.b);
		if (_instance.networkTraceLevel() >= 3 && buf.b.position() > pos)
		{
			String s = (new StringBuilder()).append("received ").append(buf.b.position() - pos).append(" of ").append(rem).append(" bytes via ssl\n").append(toString()).toString();
			_logger.trace(_instance.networkTraceCategory(), s);
		}
		if (_stats != null && buf.b.position() > pos)
			_stats.bytesReceived(type(), buf.b.position() - pos);
		  goto _L6
		SSLException ex;
		ex;
		throw new SecurityException("IceSSL: error during read", ex);
		moreData.value = _netInput.position() > 0;
		return true;
	}

	public String type()
	{
		return "ssl";
	}

	public String toString()
	{
		return _desc;
	}

	public ConnectionInfo getInfo()
	{
		return getNativeConnectionInfo();
	}

	public void checkSendSize(Buffer buf, int messageSizeMax)
	{
		if (buf.size() > messageSizeMax)
			Ex.throwMemoryLimitException(buf.size(), messageSizeMax);
	}

	TransceiverI(Instance instance, SSLEngine engine, SocketChannel fd, String host, boolean connected, boolean incoming, String adapterName, 
			InetSocketAddress connectAddr)
	{
		_instance = instance;
		_engine = engine;
		_fd = fd;
		_host = host;
		_incoming = incoming;
		_adapterName = adapterName;
		_connectAddr = connectAddr;
		_state = connected ? 2 : 0;
		_logger = instance.communicator().getLogger();
		try
		{
			_stats = instance.communicator().getStats();
		}
		catch (CommunicatorDestroyedException ex) { }
		_desc = Network.fdToString(_fd);
		_maxPacketSize = 0;
		if (System.getProperty("os.name").startsWith("Windows"))
		{
			_maxPacketSize = Network.getSendBufferSize(_fd) / 2;
			if (_maxPacketSize < 512)
				_maxPacketSize = 0;
		}
		_appInput = ByteBuffer.allocateDirect(engine.getSession().getApplicationBufferSize() * 2);
		_netInput = ByteBuffer.allocateDirect(engine.getSession().getPacketBufferSize() * 2);
		_netOutput = ByteBuffer.allocateDirect(engine.getSession().getPacketBufferSize() * 2);
	}

	protected void finalize()
		throws Throwable
	{
		Assert.FinalizerAssert(_fd == null);
		super.finalize();
	}

	private NativeConnectionInfo getNativeConnectionInfo()
	{
		if (!$assertionsDisabled && _fd == null)
			throw new AssertionError();
		NativeConnectionInfo info = new NativeConnectionInfo();
		Socket socket = _fd.socket();
		if (socket.getLocalAddress() != null)
		{
			info.localAddress = socket.getLocalAddress().getHostAddress();
			info.localPort = socket.getLocalPort();
		} else
		{
			info.localAddress = "";
			info.localPort = -1;
		}
		if (socket.getInetAddress() != null)
		{
			info.remoteAddress = socket.getInetAddress().getHostAddress();
			info.remotePort = socket.getPort();
		} else
		{
			info.remoteAddress = "";
			info.remotePort = -1;
		}
		SSLSession session = _engine.getSession();
		info.cipher = session.getCipherSuite();
		try
		{
			ArrayList certs = new ArrayList();
			info.nativeCerts = session.getPeerCertificates();
			Certificate arr$[] = info.nativeCerts;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				Certificate c = arr$[i$];
				StringBuffer s = new StringBuffer("-----BEGIN CERTIFICATE-----\n");
				s.append(Base64.encode(c.getEncoded()));
				s.append("\n-----END CERTIFICATE-----");
				certs.add(s.toString());
			}

			info.certs = (String[])certs.toArray(new String[0]);
		}
		catch (CertificateEncodingException ex) { }
		catch (SSLPeerUnverifiedException ex) { }
		info.adapterName = _adapterName;
		info.incoming = _incoming;
		return info;
	}

	private int handshakeNonBlocking()
	{
		javax.net.ssl.SSLEngineResult.HandshakeStatus status = _engine.getHandshakeStatus();
_L13:
		SSLEngineResult result;
		if (_state == 3)
			break; /* Loop/switch isn't completed */
		result = null;
		1..SwitchMap.javax.net.ssl.SSLEngineResult.HandshakeStatus[status.ordinal()];
		JVM INSTR tableswitch 1 5: default 271
	//	               1 60
	//	               2 60
	//	               3 67
	//	               4 99
	//	               5 233;
		   goto _L1 _L2 _L2 _L3 _L4 _L5
_L1:
		continue; /* Loop/switch isn't completed */
_L2:
		handshakeCompleted();
		continue; /* Loop/switch isn't completed */
_L3:
		Runnable task;
		while ((task = _engine.getDelegatedTask()) != null) 
			task.run();
		status = _engine.getHandshakeStatus();
		continue; /* Loop/switch isn't completed */
_L4:
		_netInput.flip();
		result = _engine.unwrap(_netInput, _appInput);
		_netInput.compact();
		status = result.getHandshakeStatus();
		1..SwitchMap.javax.net.ssl.SSLEngineResult.Status[result.getStatus().ordinal()];
		JVM INSTR tableswitch 1 4: default 230
	//	               1 176
	//	               2 190
	//	               3 222
	//	               4 230;
		   goto _L6 _L7 _L8 _L9 _L6
_L6:
		continue; /* Loop/switch isn't completed */
_L7:
		if (!$assertionsDisabled)
			throw new AssertionError();
		continue; /* Loop/switch isn't completed */
_L8:
		int ss;
		if (!$assertionsDisabled && status != javax.net.ssl.SSLEngineResult.HandshakeStatus.NEED_UNWRAP)
			throw new AssertionError();
		ss = readNonBlocking();
		if (ss != 0)
			return ss;
		continue; /* Loop/switch isn't completed */
_L9:
		throw new ConnectionLostException();
_L5:
		result = _engine.wrap(_emptyBuffer, _netOutput);
		if (result.bytesProduced() <= 0) goto _L11; else goto _L10
_L10:
		ss = flushNonBlocking();
		if (ss != 0)
			return ss;
_L11:
		status = result.getHandshakeStatus();
		if (result == null) goto _L13; else goto _L12
_L12:
		switch (1..SwitchMap.javax.net.ssl.SSLEngineResult.Status[result.getStatus().ordinal()])
		{
		case 1: // '\001'
			if (!$assertionsDisabled)
				throw new AssertionError();
			break;

		case 2: // '\002'
			if (!$assertionsDisabled && status != javax.net.ssl.SSLEngineResult.HandshakeStatus.NEED_UNWRAP)
				throw new AssertionError();
			break;

		case 3: // '\003'
			throw new ConnectionLostException();
		}
		if (true) goto _L13; else goto _L14
		SSLException ex;
		ex;
		throw new SecurityException("IceSSL: handshake error", ex);
_L14:
		return 0;
	}

	private void handshakeCompleted()
	{
		_state = 3;
		if (!_incoming)
		{
			int verifyPeer = _instance.communicator().getProperties().getPropertyAsIntWithDefault("IceSSL.VerifyPeer", 2);
			if (verifyPeer > 0)
				try
				{
					_engine.getSession().getPeerCertificates();
				}
				catch (SSLPeerUnverifiedException ex)
				{
					throw new SecurityException("IceSSL: server did not supply a certificate", ex);
				}
		}
		_instance.verifyPeer(getNativeConnectionInfo(), _fd, _host);
		if (_instance.networkTraceLevel() >= 1)
		{
			String s;
			if (_incoming)
				s = (new StringBuilder()).append("accepted ssl connection\n").append(_desc).toString();
			else
				s = (new StringBuilder()).append("ssl connection established\n").append(_desc).toString();
			_logger.trace(_instance.networkTraceCategory(), s);
		}
		if (_instance.securityTraceLevel() >= 1)
			_instance.traceConnection(_fd, _engine, _incoming);
	}

	private int writeNonBlocking(ByteBuffer buf)
	{
		int ss;
		while (ss == 0) 
		{
			do
			{
				if (!buf.hasRemaining() && _netOutput.position() <= 0)
					break MISSING_BLOCK_LABEL_268;
				int rem = buf.remaining();
				if (rem > 0)
				{
					SSLEngineResult result = _engine.wrap(buf, _netOutput);
					switch (1..SwitchMap.javax.net.ssl.SSLEngineResult.Status[result.getStatus().ordinal()])
					{
					case 2: // '\002'
						if (!$assertionsDisabled)
							throw new AssertionError();
						break;

					case 3: // '\003'
						throw new ConnectionLostException();
					}
					if (result.bytesConsumed() > 0)
					{
						if (_instance.networkTraceLevel() >= 3)
						{
							String s = (new StringBuilder()).append("sent ").append(result.bytesConsumed()).append(" of ").append(rem).append(" bytes via ssl\n").append(toString()).toString();
							_logger.trace(_instance.networkTraceCategory(), s);
						}
						if (_stats != null)
							_stats.bytesSent(type(), result.bytesConsumed());
					}
				}
			} while (_netOutput.position() <= 0);
			ss = flushNonBlocking();
		}
		if (!$assertionsDisabled && ss != 4)
			throw new AssertionError();
		return ss;
		SSLException ex;
		ex;
		throw new SecurityException("IceSSL: error while encoding message", ex);
		if (!$assertionsDisabled && _netOutput.position() != 0)
			throw new AssertionError();
		else
			return 0;
	}

	private int flushNonBlocking()
	{
		int size;
		int packetSize;
		int status;
		_netOutput.flip();
		size = _netOutput.limit();
		packetSize = size - _netOutput.position();
		if (_maxPacketSize > 0 && packetSize > _maxPacketSize)
		{
			packetSize = _maxPacketSize;
			_netOutput.limit(_netOutput.position() + packetSize);
		}
		status = 0;
_L2:
		if (!_netOutput.hasRemaining())
			break MISSING_BLOCK_LABEL_232;
		int ret;
		if (!$assertionsDisabled && _fd == null)
			throw new AssertionError();
		ret = _fd.write(_netOutput);
		if (ret == -1)
			throw new ConnectionLostException();
		if (ret == 0)
		{
			status = 4;
			break MISSING_BLOCK_LABEL_232;
		} else
		{
			try
			{
				if (packetSize == _maxPacketSize)
				{
					if (!$assertionsDisabled && _netOutput.position() != _netOutput.limit())
						throw new AssertionError();
					packetSize = size - _netOutput.position();
					if (packetSize > _maxPacketSize)
						packetSize = _maxPacketSize;
					_netOutput.limit(_netOutput.position() + packetSize);
				}
			}
			catch (InterruptedIOException ex) { }
			catch (IOException ex)
			{
				throw new ConnectionLostException(ex);
			}
			continue; /* Loop/switch isn't completed */
		}
		if (status == 0)
		{
			_netOutput.clear();
		} else
		{
			_netOutput.limit(size);
			_netOutput.compact();
		}
		return status;
		if (true) goto _L2; else goto _L1
_L1:
	}

	private int readNonBlocking()
	{
_L2:
		int ret;
		if (!$assertionsDisabled && _fd == null)
			throw new AssertionError();
		ret = _fd.read(_netInput);
		if (ret == -1)
			throw new ConnectionLostException();
		InterruptedIOException ex;
		return ret != 0 ? 0 : 1;
		ex;
		continue; /* Loop/switch isn't completed */
		ex;
		throw new ConnectionLostException(ex);
		if (true) goto _L2; else goto _L1
_L1:
	}

	private void fill(ByteBuffer buf)
	{
		_appInput.flip();
		if (_appInput.hasRemaining())
		{
			int bytesAvailable = _appInput.remaining();
			int bytesNeeded = buf.remaining();
			if (bytesAvailable > bytesNeeded)
				bytesAvailable = bytesNeeded;
			if (buf.hasArray())
			{
				byte arr[] = buf.array();
				int offset = buf.arrayOffset() + buf.position();
				_appInput.get(arr, offset, bytesAvailable);
				buf.position(offset + bytesAvailable);
			} else
			if (_appInput.hasArray())
			{
				byte arr[] = _appInput.array();
				int offset = _appInput.arrayOffset() + _appInput.position();
				buf.put(arr, offset, bytesAvailable);
				_appInput.position(offset + bytesAvailable);
			} else
			{
				byte arr[] = new byte[bytesAvailable];
				_appInput.get(arr);
				buf.put(arr);
			}
		}
		_appInput.compact();
	}

}
