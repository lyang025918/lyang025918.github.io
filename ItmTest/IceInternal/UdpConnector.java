// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   UdpConnector.java

package IceInternal;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectableChannel;

// Referenced classes of package IceInternal:
//			UdpTransceiver, Connector, Network, Instance, 
//			Transceiver

final class UdpConnector
	implements Connector
{

	private Instance _instance;
	private InetSocketAddress _addr;
	private String _mcastInterface;
	private int _mcastTtl;
	private byte _protocolMajor;
	private byte _protocolMinor;
	private byte _encodingMajor;
	private byte _encodingMinor;
	private String _connectionId;
	private int _hashCode;
	static final boolean $assertionsDisabled = !IceInternal/UdpConnector.desiredAssertionStatus();

	public Transceiver connect()
	{
		return new UdpTransceiver(_instance, _addr, _mcastInterface, _mcastTtl);
	}

	public SelectableChannel fd()
	{
		if (!$assertionsDisabled)
			throw new AssertionError();
		else
			return null;
	}

	public short type()
	{
		return 3;
	}

	public String toString()
	{
		return Network.addrToString(_addr);
	}

	public int hashCode()
	{
		return _hashCode;
	}

	UdpConnector(Instance instance, InetSocketAddress addr, String mcastInterface, int mcastTtl, byte protocolMajor, byte protocolMinor, byte encodingMajor, 
			byte encodingMinor, String connectionId)
	{
		_instance = instance;
		_addr = addr;
		_mcastInterface = mcastInterface;
		_mcastTtl = mcastTtl;
		_protocolMajor = protocolMajor;
		_protocolMinor = protocolMinor;
		_encodingMajor = encodingMajor;
		_encodingMinor = encodingMinor;
		_connectionId = connectionId;
		_hashCode = _addr.getAddress().getHostAddress().hashCode();
		_hashCode = 5 * _hashCode + _addr.getPort();
		_hashCode = 5 * _hashCode + _mcastInterface.hashCode();
		_hashCode = 5 * _hashCode + _mcastTtl;
		_hashCode = 5 * _hashCode + _connectionId.hashCode();
	}

	public boolean equals(Object obj)
	{
		UdpConnector p = null;
		try
		{
			p = (UdpConnector)obj;
		}
		catch (ClassCastException ex)
		{
			return false;
		}
		if (this == p)
			return true;
		if (!_connectionId.equals(p._connectionId))
			return false;
		if (_protocolMajor != p._protocolMajor)
			return false;
		if (_protocolMinor != p._protocolMinor)
			return false;
		if (_encodingMajor != p._encodingMajor)
			return false;
		if (_encodingMinor != p._encodingMinor)
			return false;
		if (_mcastTtl != p._mcastTtl)
			return false;
		if (_mcastInterface.compareTo(p._mcastInterface) != 0)
			return false;
		else
			return Network.compareAddress(_addr, p._addr) == 0;
	}

}
