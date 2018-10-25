// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   UdpEndpointI.java

package IceInternal;

import Ice.*;
import java.net.InetSocketAddress;
import java.util.*;

// Referenced classes of package IceInternal:
//			EndpointI, UdpTransceiver, UdpConnector, Instance, 
//			DefaultsAndOverrides, BasicStream, EndpointIHolder, Network, 
//			EndpointHostResolver, Transceiver, EndpointI_connectors, Acceptor

final class UdpEndpointI extends EndpointI
{

	private Instance _instance;
	private String _host;
	private int _port;
	private String _mcastInterface;
	private int _mcastTtl;
	private byte _protocolMajor;
	private byte _protocolMinor;
	private byte _encodingMajor;
	private byte _encodingMinor;
	private boolean _connect;
	private String _connectionId;
	private boolean _compress;
	private int _hashCode;
	static final boolean $assertionsDisabled = !IceInternal/UdpEndpointI.desiredAssertionStatus();

	public UdpEndpointI(Instance instance, String ho, int po, String mif, int mttl, byte pma, byte pmi, 
			byte ema, byte emi, boolean conn, String conId, boolean co)
	{
		_mcastInterface = "";
		_mcastTtl = -1;
		_connectionId = "";
		_instance = instance;
		_host = ho;
		_port = po;
		_mcastInterface = mif;
		_mcastTtl = mttl;
		_protocolMajor = pma;
		_protocolMinor = pmi;
		_encodingMajor = ema;
		_encodingMinor = emi;
		_connect = conn;
		_connectionId = conId;
		_compress = co;
		calcHashValue();
	}

	public UdpEndpointI(Instance instance, String str, boolean oaEndpoint)
	{
		_mcastInterface = "";
		_mcastTtl = -1;
		_connectionId = "";
		_instance = instance;
		_host = null;
		_port = 0;
		_protocolMajor = 1;
		_protocolMinor = 0;
		_encodingMajor = 1;
		_encodingMinor = 0;
		_connect = false;
		_compress = false;
		String arr[] = str.split("[ \t\n\r]+");
		int i = 0;
		do
		{
			if (i >= arr.length)
				break;
			if (arr[i].length() == 0)
			{
				i++;
				continue;
			}
			String option = arr[i++];
			if (option.charAt(0) != '-')
				throw new EndpointParseException((new StringBuilder()).append("expected an endpoint option but found `").append(option).append("' in endpoint `udp ").append(str).append("'").toString());
			String argument = null;
			if (i < arr.length && arr[i].charAt(0) != '-')
			{
				argument = arr[i++];
				if (argument.charAt(0) == '"' && argument.charAt(argument.length() - 1) == '"')
					argument = argument.substring(1, argument.length() - 1);
			}
			if (option.equals("-v"))
			{
				if (argument == null)
					throw new EndpointParseException((new StringBuilder()).append("no argument provided for -v option in endpoint `udp ").append(str).append("'").toString());
				int pos = argument.indexOf('.');
				if (pos == -1)
					throw new EndpointParseException((new StringBuilder()).append("malformed protocol version `").append(argument).append("' in endpoint `udp ").append(str).append("'").toString());
				String majStr = argument.substring(0, pos);
				String minStr = argument.substring(pos + 1, argument.length());
				int majVersion;
				int minVersion;
				try
				{
					majVersion = Integer.parseInt(majStr);
					minVersion = Integer.parseInt(minStr);
				}
				catch (NumberFormatException ex)
				{
					throw new EndpointParseException((new StringBuilder()).append("invalid protocol version `").append(argument).append("' in endpoint `udp ").append(str).append("'").toString());
				}
				if (majVersion < 1 || majVersion > 255 || minVersion < 0 || minVersion > 255)
					throw new EndpointParseException((new StringBuilder()).append("range error in protocol version `").append(argument).append("' in endpoint `udp ").append(str).append("'").toString());
				if (majVersion != 1)
				{
					UnsupportedProtocolException e = new UnsupportedProtocolException();
					e.badMajor = majVersion >= 0 ? majVersion : majVersion + 255;
					e.badMinor = minVersion >= 0 ? minVersion : minVersion + 255;
					e.major = 1;
					e.minor = 0;
					throw e;
				}
				_protocolMajor = (byte)majVersion;
				_protocolMinor = (byte)minVersion;
				continue;
			}
			if (option.equals("-e"))
			{
				if (argument == null)
					throw new EndpointParseException((new StringBuilder()).append("no argument provided for -e option in endpoint `udp ").append(str).append("'").toString());
				int pos = argument.indexOf('.');
				if (pos == -1)
					throw new EndpointParseException((new StringBuilder()).append("malformed encoding version `").append(argument).append("' in endpoint `udp ").append(str).append("'").toString());
				String majStr = argument.substring(0, pos);
				String minStr = argument.substring(pos + 1, argument.length());
				int majVersion;
				int minVersion;
				try
				{
					majVersion = Integer.parseInt(majStr);
					minVersion = Integer.parseInt(minStr);
				}
				catch (NumberFormatException ex)
				{
					throw new EndpointParseException((new StringBuilder()).append("invalid encoding version `").append(argument).append("' in endpoint `udp ").append(str).append("'").toString());
				}
				if (majVersion < 1 || majVersion > 255 || minVersion < 0 || minVersion > 255)
					throw new EndpointParseException((new StringBuilder()).append("range error in encoding version `").append(argument).append("' in endpoint `udp ").append(str).append("'").toString());
				if (majVersion != 1)
				{
					UnsupportedEncodingException e = new UnsupportedEncodingException();
					e.badMajor = majVersion >= 0 ? majVersion : majVersion + 255;
					e.badMinor = minVersion >= 0 ? minVersion : minVersion + 255;
					e.major = 1;
					e.minor = 0;
					throw e;
				}
				_encodingMajor = (byte)majVersion;
				_encodingMinor = (byte)minVersion;
				continue;
			}
			if (option.equals("-h"))
			{
				if (argument == null)
					throw new EndpointParseException((new StringBuilder()).append("no argument provided for -h option in endpoint `udp ").append(str).append("'").toString());
				_host = argument;
				continue;
			}
			if (option.equals("-p"))
			{
				if (argument == null)
					throw new EndpointParseException((new StringBuilder()).append("no argument provided for -p option in endpoint `udp ").append(str).append("'").toString());
				try
				{
					_port = Integer.parseInt(argument);
				}
				catch (NumberFormatException ex)
				{
					throw new EndpointParseException((new StringBuilder()).append("invalid port value `").append(argument).append("' in endpoint `udp ").append(str).append("'").toString());
				}
				if (_port < 0 || _port > 65535)
					throw new EndpointParseException((new StringBuilder()).append("port value `").append(argument).append("' out of range in endpoint `udp ").append(str).append("'").toString());
				continue;
			}
			if (option.equals("-c"))
			{
				if (argument != null)
					throw new EndpointParseException((new StringBuilder()).append("unexpected argument `").append(argument).append("' provided for -c option in `udp ").append(str).append("'").toString());
				_connect = true;
				break;
			}
			if (option.equals("-z"))
			{
				if (argument != null)
					throw new EndpointParseException((new StringBuilder()).append("unexpected argument `").append(argument).append("' provided for -z option in `udp ").append(str).append("'").toString());
				_compress = true;
				break;
			}
			if (option.equals("--interface"))
			{
				if (argument == null)
					throw new EndpointParseException((new StringBuilder()).append("no argument provided for --interface option in endpoint `udp ").append(str).append("'").toString());
				_mcastInterface = argument;
			} else
			if (option.equals("--ttl"))
			{
				if (argument == null)
					throw new EndpointParseException((new StringBuilder()).append("no argument provided for --ttl option in endpoint `udp ").append(str).append("'").toString());
				try
				{
					_mcastTtl = Integer.parseInt(argument);
				}
				catch (NumberFormatException ex)
				{
					throw new EndpointParseException((new StringBuilder()).append("invalid TTL value `").append(argument).append("' in endpoint `udp ").append(str).append("'").toString());
				}
				if (_mcastTtl < 0)
					throw new EndpointParseException((new StringBuilder()).append("TTL value `").append(argument).append("' out of range in endpoint `udp ").append(str).append("'").toString());
			} else
			{
				throw new EndpointParseException((new StringBuilder()).append("unknown option `").append(option).append("' in `udp ").append(str).append("'").toString());
			}
		} while (true);
		if (_host == null)
			_host = _instance.defaultsAndOverrides().defaultHost;
		else
		if (_host.equals("*"))
			if (oaEndpoint)
				_host = null;
			else
				throw new EndpointParseException((new StringBuilder()).append("`-h *' not valid for proxy endpoint `udp ").append(str).append("'").toString());
		if (_host == null)
			_host = "";
		calcHashValue();
	}

	public UdpEndpointI(BasicStream s)
	{
		_mcastInterface = "";
		_mcastTtl = -1;
		_connectionId = "";
		_instance = s.instance();
		s.startReadEncaps();
		_host = s.readString();
		_port = s.readInt();
		_protocolMajor = s.readByte();
		_protocolMinor = s.readByte();
		_encodingMajor = s.readByte();
		_encodingMinor = s.readByte();
		if (_protocolMajor != 1)
		{
			UnsupportedProtocolException e = new UnsupportedProtocolException();
			e.badMajor = _protocolMajor >= 0 ? ((int) (_protocolMajor)) : _protocolMajor + 255;
			e.badMinor = _protocolMinor >= 0 ? ((int) (_protocolMinor)) : _protocolMinor + 255;
			e.major = 1;
			e.minor = 0;
			throw e;
		}
		if (_encodingMajor != 1)
		{
			UnsupportedEncodingException e = new UnsupportedEncodingException();
			e.badMajor = _encodingMajor >= 0 ? ((int) (_encodingMajor)) : _encodingMajor + 255;
			e.badMinor = _encodingMinor >= 0 ? ((int) (_encodingMinor)) : _encodingMinor + 255;
			e.major = 1;
			e.minor = 0;
			throw e;
		} else
		{
			_connect = false;
			_compress = s.readBool();
			s.endReadEncaps();
			calcHashValue();
			return;
		}
	}

	public void streamWrite(BasicStream s)
	{
		s.writeShort((short)3);
		s.startWriteEncaps();
		s.writeString(_host);
		s.writeInt(_port);
		s.writeByte(_protocolMajor);
		s.writeByte(_protocolMinor);
		s.writeByte(_encodingMajor);
		s.writeByte(_encodingMinor);
		s.writeBool(_compress);
		s.endWriteEncaps();
	}

	public String _toString()
	{
		String s = "udp";
		if (_protocolMajor != 1 || _protocolMinor != 0)
			s = (new StringBuilder()).append(s).append(" -v ").append(_protocolMajor >= 0 ? ((int) (_protocolMajor)) : _protocolMajor + 255).append(".").append(_protocolMinor >= 0 ? ((int) (_protocolMinor)) : _protocolMinor + 255).toString();
		if (_encodingMajor != 1 || _encodingMinor != 0)
			s = (new StringBuilder()).append(s).append(" -e ").append(_encodingMajor >= 0 ? ((int) (_encodingMajor)) : _encodingMajor + 255).append(".").append(_encodingMinor >= 0 ? ((int) (_encodingMinor)) : _encodingMinor + 255).toString();
		if (_host != null && _host.length() > 0)
		{
			s = (new StringBuilder()).append(s).append(" -h ").toString();
			boolean addQuote = _host.indexOf(':') != -1;
			if (addQuote)
				s = (new StringBuilder()).append(s).append("\"").toString();
			s = (new StringBuilder()).append(s).append(_host).toString();
			if (addQuote)
				s = (new StringBuilder()).append(s).append("\"").toString();
		}
		s = (new StringBuilder()).append(s).append(" -p ").append(_port).toString();
		if (_mcastInterface.length() != 0)
			s = (new StringBuilder()).append(s).append(" --interface ").append(_mcastInterface).toString();
		if (_mcastTtl != -1)
			s = (new StringBuilder()).append(s).append(" --ttl ").append(_mcastTtl).toString();
		if (_connect)
			s = (new StringBuilder()).append(s).append(" -c").toString();
		if (_compress)
			s = (new StringBuilder()).append(s).append(" -z").toString();
		return s;
	}

	public EndpointInfo getInfo()
	{
		return new UDPEndpointInfo(-1, _compress, _host, _port, _protocolMajor, _protocolMinor, _encodingMajor, _encodingMinor, _mcastInterface, _mcastTtl) {

			final UdpEndpointI this$0;

			public short type()
			{
				return 3;
			}

			public boolean datagram()
			{
				return true;
			}

			public boolean secure()
			{
				return false;
			}

			
			{
				this$0 = UdpEndpointI.this;
				super(x0, x1, x2, x3, x4, x5, x6, x7, x8, x9);
			}
		};
	}

	public short type()
	{
		return 3;
	}

	public int timeout()
	{
		return -1;
	}

	public boolean compress()
	{
		return _compress;
	}

	public EndpointI compress(boolean compress)
	{
		if (compress == _compress)
			return this;
		else
			return new UdpEndpointI(_instance, _host, _port, _mcastInterface, _mcastTtl, _protocolMajor, _protocolMinor, _encodingMajor, _encodingMinor, _connect, _connectionId, compress);
	}

	public EndpointI connectionId(String connectionId)
	{
		if (connectionId.equals(_connectionId))
			return this;
		else
			return new UdpEndpointI(_instance, _host, _port, _mcastInterface, _mcastTtl, _protocolMajor, _protocolMinor, _encodingMajor, _encodingMinor, _connect, connectionId, _compress);
	}

	public EndpointI timeout(int timeout)
	{
		return this;
	}

	public boolean datagram()
	{
		return true;
	}

	public boolean secure()
	{
		return false;
	}

	public Transceiver transceiver(EndpointIHolder endpoint)
	{
		UdpTransceiver p = new UdpTransceiver(_instance, _host, _port, _mcastInterface, _connect);
		endpoint.value = new UdpEndpointI(_instance, _host, p.effectivePort(), _mcastInterface, _mcastTtl, _protocolMajor, _protocolMinor, _encodingMajor, _encodingMinor, _connect, _connectionId, _compress);
		return p;
	}

	public List connectors()
	{
		return connectors(((List) (Network.getAddresses(_host, _port, _instance.protocolSupport()))));
	}

	public void connectors_async(EndpointI_connectors callback)
	{
		_instance.endpointHostResolver().resolve(_host, _port, this, callback);
	}

	public Acceptor acceptor(EndpointIHolder endpoint, String adapterName)
	{
		endpoint.value = this;
		return null;
	}

	public List expand()
	{
		ArrayList endps = new ArrayList();
		ArrayList hosts = Network.getHostsForEndpointExpand(_host, _instance.protocolSupport(), false);
		if (hosts == null || hosts.isEmpty())
		{
			endps.add(this);
		} else
		{
			String host;
			for (Iterator i$ = hosts.iterator(); i$.hasNext(); endps.add(new UdpEndpointI(_instance, host, _port, _mcastInterface, _mcastTtl, _protocolMajor, _protocolMinor, _encodingMajor, _encodingMinor, _connect, _connectionId, _compress)))
				host = (String)i$.next();

		}
		return endps;
	}

	public boolean equivalent(EndpointI endpoint)
	{
		UdpEndpointI udpEndpointI = null;
		try
		{
			udpEndpointI = (UdpEndpointI)endpoint;
		}
		catch (ClassCastException ex)
		{
			return false;
		}
		return udpEndpointI._host.equals(_host) && udpEndpointI._port == _port;
	}

	public int hashCode()
	{
		return _hashCode;
	}

	public boolean equals(Object obj)
	{
		return compareTo((EndpointI)obj) == 0;
		ClassCastException ee;
		ee;
		if (!$assertionsDisabled)
			throw new AssertionError();
		else
			return false;
	}

	public int compareTo(EndpointI obj)
	{
		UdpEndpointI p = null;
		try
		{
			p = (UdpEndpointI)obj;
		}
		catch (ClassCastException ex)
		{
			return type() >= obj.type() ? 1 : -1;
		}
		if (this == p)
			return 0;
		if (_port < p._port)
			return -1;
		if (p._port < _port)
			return 1;
		if (!_connect && p._connect)
			return -1;
		if (!p._connect && _connect)
			return 1;
		if (!_connectionId.equals(p._connectionId))
			return _connectionId.compareTo(p._connectionId);
		if (!_compress && p._compress)
			return -1;
		if (!p._compress && _compress)
			return 1;
		if (_protocolMajor < p._protocolMajor)
			return -1;
		if (p._protocolMajor < _protocolMajor)
			return 1;
		if (_protocolMinor < p._protocolMinor)
			return -1;
		if (p._protocolMinor < _protocolMinor)
			return 1;
		if (_encodingMajor < p._encodingMajor)
			return -1;
		if (p._encodingMajor < _encodingMajor)
			return 1;
		if (_encodingMinor < p._encodingMinor)
			return -1;
		if (p._encodingMinor < _encodingMinor)
			return 1;
		if (_mcastTtl < p._mcastTtl)
			return -1;
		if (p._mcastTtl < _mcastTtl)
			return 1;
		int rc = _mcastInterface.compareTo(p._mcastInterface);
		if (rc != 0)
			return rc;
		else
			return _host.compareTo(p._host);
	}

	public List connectors(List addresses)
	{
		ArrayList connectors = new ArrayList();
		InetSocketAddress p;
		for (Iterator i$ = addresses.iterator(); i$.hasNext(); connectors.add(new UdpConnector(_instance, p, _mcastInterface, _mcastTtl, _protocolMajor, _protocolMinor, _encodingMajor, _encodingMinor, _connectionId)))
			p = (InetSocketAddress)i$.next();

		return connectors;
	}

	private void calcHashValue()
	{
		_hashCode = _host.hashCode();
		_hashCode = 5 * _hashCode + _port;
		_hashCode = 5 * _hashCode + _mcastInterface.hashCode();
		_hashCode = 5 * _hashCode + _mcastTtl;
		_hashCode = 5 * _hashCode + (_connect ? 1 : 0);
		_hashCode = 5 * _hashCode + _connectionId.hashCode();
		_hashCode = 5 * _hashCode + (_compress ? 1 : 0);
	}

	public volatile int compareTo(Object x0)
	{
		return compareTo((EndpointI)x0);
	}

}
