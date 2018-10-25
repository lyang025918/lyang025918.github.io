// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EndpointI.java

package IceSSL;

import Ice.EndpointInfo;
import Ice.EndpointParseException;
import IceInternal.Acceptor;
import IceInternal.BasicStream;
import IceInternal.EndpointHostResolver;
import IceInternal.EndpointIHolder;
import IceInternal.EndpointI_connectors;
import IceInternal.Network;
import IceInternal.Transceiver;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// Referenced classes of package IceSSL:
//			AcceptorI, ConnectorI, Instance, EndpointInfo

final class EndpointI extends IceInternal.EndpointI
{

	private Instance _instance;
	private String _host;
	private int _port;
	private int _timeout;
	private String _connectionId;
	private boolean _compress;
	private int _hashCode;
	static final boolean $assertionsDisabled = !IceSSL/EndpointI.desiredAssertionStatus();

	public EndpointI(Instance instance, String ho, int po, int ti, String conId, boolean co)
	{
		_connectionId = "";
		_instance = instance;
		_host = ho;
		_port = po;
		_timeout = ti;
		_connectionId = conId;
		_compress = co;
		calcHashValue();
	}

	public EndpointI(Instance instance, String str, boolean oaEndpoint)
	{
		_connectionId = "";
		_instance = instance;
		_host = null;
		_port = 0;
		_timeout = -1;
		_compress = false;
		String arr[] = str.split("[ \t\n\r]+");
		int i = 0;
label0:
		do
		{
			do
			{
				if (i >= arr.length)
					break label0;
				if (arr[i].length() != 0)
					break;
				i++;
			} while (true);
			String option = arr[i++];
			if (option.length() != 2 || option.charAt(0) != '-')
				throw new EndpointParseException((new StringBuilder()).append("expected an endpoint option but found `").append(option).append("' in endpoint `ssl ").append(str).append("'").toString());
			String argument = null;
			if (i < arr.length && arr[i].charAt(0) != '-')
			{
				argument = arr[i++];
				if (argument.charAt(0) == '"' && argument.charAt(argument.length() - 1) == '"')
					argument = argument.substring(1, argument.length() - 1);
			}
			switch (option.charAt(1))
			{
			case 104: // 'h'
				if (argument == null)
					throw new EndpointParseException((new StringBuilder()).append("no argument provided for -h option in endpoint `ssl ").append(str).append("'").toString());
				_host = argument;
				break;

			case 112: // 'p'
				if (argument == null)
					throw new EndpointParseException((new StringBuilder()).append("no argument provided for -p option in endpoint `ssl ").append(str).append("'").toString());
				try
				{
					_port = Integer.parseInt(argument);
				}
				catch (NumberFormatException ex)
				{
					throw new EndpointParseException((new StringBuilder()).append("invalid port value `").append(argument).append("' in endpoint `ssl ").append(str).append("'").toString());
				}
				if (_port < 0 || _port > 65535)
					throw new EndpointParseException((new StringBuilder()).append("port value `").append(argument).append("' out of range in endpoint `ssl ").append(str).append("'").toString());
				break;

			case 116: // 't'
				if (argument == null)
					throw new EndpointParseException((new StringBuilder()).append("no argument provided for -t option in endpoint `ssl ").append(str).append("'").toString());
				try
				{
					_timeout = Integer.parseInt(argument);
				}
				catch (NumberFormatException ex)
				{
					throw new EndpointParseException((new StringBuilder()).append("invalid timeout value `").append(argument).append("' in endpoint `ssl ").append(str).append("'").toString());
				}
				break;

			case 122: // 'z'
				if (argument != null)
					throw new EndpointParseException((new StringBuilder()).append("unexpected argument `").append(argument).append("' provided for -z option in `ssl ").append(str).append("'").toString());
				_compress = true;
				break;

			default:
				throw new EndpointParseException((new StringBuilder()).append("unknown option `").append(option).append("' in `ssl ").append(str).append("'").toString());
			}
		} while (true);
		if (_host == null)
			_host = _instance.defaultHost();
		else
		if (_host.equals("*"))
			if (oaEndpoint)
				_host = null;
			else
				throw new EndpointParseException((new StringBuilder()).append("`-h *' not valid for proxy endpoint `ssl ").append(str).append("'").toString());
		if (_host == null)
			_host = "";
		calcHashValue();
	}

	public EndpointI(Instance instance, BasicStream s)
	{
		_connectionId = "";
		_instance = instance;
		s.startReadEncaps();
		_host = s.readString();
		_port = s.readInt();
		_timeout = s.readInt();
		_compress = s.readBool();
		s.endReadEncaps();
		calcHashValue();
	}

	public void streamWrite(BasicStream s)
	{
		s.writeShort((short)2);
		s.startWriteEncaps();
		s.writeString(_host);
		s.writeInt(_port);
		s.writeInt(_timeout);
		s.writeBool(_compress);
		s.endWriteEncaps();
	}

	public String _toString()
	{
		String s = "ssl";
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
		if (_timeout != -1)
			s = (new StringBuilder()).append(s).append(" -t ").append(_timeout).toString();
		if (_compress)
			s = (new StringBuilder()).append(s).append(" -z").toString();
		return s;
	}

	public EndpointInfo getInfo()
	{
		return new IceSSL.EndpointInfo(_timeout, _compress, _host, _port) {

			final EndpointI this$0;

			public short type()
			{
				return 2;
			}

			public boolean datagram()
			{
				return false;
			}

			public boolean secure()
			{
				return true;
			}

			
			{
				this$0 = EndpointI.this;
				super(x0, x1, x2, x3);
			}
		};
	}

	public short type()
	{
		return 2;
	}

	public int timeout()
	{
		return _timeout;
	}

	public IceInternal.EndpointI timeout(int timeout)
	{
		if (timeout == _timeout)
			return this;
		else
			return new EndpointI(_instance, _host, _port, timeout, _connectionId, _compress);
	}

	public IceInternal.EndpointI connectionId(String connectionId)
	{
		if (connectionId.equals(_connectionId))
			return this;
		else
			return new EndpointI(_instance, _host, _port, _timeout, connectionId, _compress);
	}

	public boolean compress()
	{
		return _compress;
	}

	public IceInternal.EndpointI compress(boolean compress)
	{
		if (compress == _compress)
			return this;
		else
			return new EndpointI(_instance, _host, _port, _timeout, _connectionId, compress);
	}

	public boolean datagram()
	{
		return false;
	}

	public boolean secure()
	{
		return true;
	}

	public Transceiver transceiver(EndpointIHolder endpoint)
	{
		endpoint.value = this;
		return null;
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
		AcceptorI p = new AcceptorI(_instance, adapterName, _host, _port);
		endpoint.value = new EndpointI(_instance, _host, p.effectivePort(), _timeout, _connectionId, _compress);
		return p;
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
			for (Iterator i$ = hosts.iterator(); i$.hasNext(); endps.add(new EndpointI(_instance, host, _port, _timeout, _connectionId, _compress)))
				host = (String)i$.next();

		}
		return endps;
	}

	public boolean equivalent(IceInternal.EndpointI endpoint)
	{
		EndpointI sslEndpointI = null;
		try
		{
			sslEndpointI = (EndpointI)endpoint;
		}
		catch (ClassCastException ex)
		{
			return false;
		}
		return sslEndpointI._host.equals(_host) && sslEndpointI._port == _port;
	}

	public int hashCode()
	{
		return _hashCode;
	}

	public boolean equals(Object obj)
	{
		return compareTo((IceInternal.EndpointI)obj) == 0;
		ClassCastException ee;
		ee;
		if (!$assertionsDisabled)
			throw new AssertionError();
		else
			return false;
	}

	public int compareTo(IceInternal.EndpointI obj)
	{
		EndpointI p = null;
		try
		{
			p = (EndpointI)obj;
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
		if (!_connectionId.equals(p._connectionId))
			return _connectionId.compareTo(p._connectionId);
		if (_timeout < p._timeout)
			return -1;
		if (p._timeout < _timeout)
			return 1;
		if (!_compress && p._compress)
			return -1;
		if (!p._compress && _compress)
			return 1;
		else
			return _host.compareTo(p._host);
	}

	public List connectors(List addresses)
	{
		List connectors = new ArrayList();
		InetSocketAddress p;
		for (Iterator i$ = addresses.iterator(); i$.hasNext(); connectors.add(new ConnectorI(_instance, _host, p, _timeout, _connectionId)))
			p = (InetSocketAddress)i$.next();

		return connectors;
	}

	private void calcHashValue()
	{
		_hashCode = _host.hashCode();
		_hashCode = 5 * _hashCode + _port;
		_hashCode = 5 * _hashCode + _timeout;
		_hashCode = 5 * _hashCode + _connectionId.hashCode();
		_hashCode = 5 * _hashCode + (_compress ? 1 : 0);
	}

	public volatile int compareTo(Object x0)
	{
		return compareTo((IceInternal.EndpointI)x0);
	}

}
