// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   OpaqueEndpointI.java

package IceInternal;

import Ice.*;
import IceUtilInternal.Base64;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package IceInternal:
//			EndpointI, BasicStream, EndpointIHolder, EndpointI_connectors, 
//			Transceiver, Acceptor

final class OpaqueEndpointI extends EndpointI
{

	private short _type;
	private byte _rawBytes[];
	private int _hashCode;
	static final boolean $assertionsDisabled = !IceInternal/OpaqueEndpointI.desiredAssertionStatus();

	public OpaqueEndpointI(String str)
	{
		int topt = 0;
		int vopt = 0;
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
				throw new EndpointParseException((new StringBuilder()).append("expected an endpoint option but found `").append(option).append("' in endpoint `opaque ").append(str).append("'").toString());
			String argument = null;
			if (i < arr.length && arr[i].charAt(0) != '-')
				argument = arr[i++];
			switch (option.charAt(1))
			{
			case 116: // 't'
				if (argument == null)
					throw new EndpointParseException((new StringBuilder()).append("no argument provided for -t option in endpoint `opaque ").append(str).append("'").toString());
				int t;
				try
				{
					t = Integer.parseInt(argument);
				}
				catch (NumberFormatException ex)
				{
					throw new EndpointParseException((new StringBuilder()).append("invalid timeout value `").append(argument).append("' in endpoint `opaque ").append(str).append("'").toString());
				}
				if (t < 0 || t > 65535)
					throw new EndpointParseException((new StringBuilder()).append("timeout value `").append(argument).append("' out of range in endpoint `opaque ").append(str).append("'").toString());
				_type = (short)t;
				if (++topt > 1)
					throw new EndpointParseException((new StringBuilder()).append("multiple -t options in endpoint `opaque ").append(str).append("'").toString());
				break;

			case 118: // 'v'
				if (argument == null)
					throw new EndpointParseException((new StringBuilder()).append("no argument provided for -v option in endpoint `opaque ").append(str).append("'").toString());
				for (int j = 0; j < argument.length(); j++)
					if (!Base64.isBase64(argument.charAt(j)))
						throw new EndpointParseException((new StringBuilder()).append("invalid base64 character `").append(argument.charAt(j)).append("' (ordinal ").append(argument.charAt(j)).append(") in endpoint `opaque ").append(str).append("'").toString());

				_rawBytes = Base64.decode(argument);
				if (++vopt > 1)
					throw new EndpointParseException((new StringBuilder()).append("multiple -v options in endpoint `opaque ").append(str).append("'").toString());
				break;

			default:
				throw new EndpointParseException((new StringBuilder()).append("invalid option `").append(option).append("' in endpoint `opaque ").append(str).append("'").toString());
			}
		} while (true);
		if (topt != 1)
			throw new EndpointParseException((new StringBuilder()).append("no -t option in endpoint `opaque ").append(str).append("'").toString());
		if (vopt != 1)
		{
			throw new EndpointParseException((new StringBuilder()).append("no -v option in endpoint `opaque ").append(str).append("'").toString());
		} else
		{
			calcHashValue();
			return;
		}
	}

	public OpaqueEndpointI(short type, BasicStream s)
	{
		_type = type;
		s.startReadEncaps();
		int sz = s.getReadEncapsSize();
		_rawBytes = s.readBlob(sz);
		s.endReadEncaps();
		calcHashValue();
	}

	public void streamWrite(BasicStream s)
	{
		s.writeShort(_type);
		s.startWriteEncaps();
		s.writeBlob(_rawBytes);
		s.endWriteEncaps();
	}

	public String _toString()
	{
		String val = Base64.encode(_rawBytes);
		return (new StringBuilder()).append("opaque -t ").append(_type).append(" -v ").append(val).toString();
	}

	public EndpointInfo getInfo()
	{
		return new OpaqueEndpointInfo(-1, false, _rawBytes) {

			final OpaqueEndpointI this$0;

			public short type()
			{
				return _type;
			}

			public boolean datagram()
			{
				return false;
			}

			public boolean secure()
			{
				return false;
			}

			
			{
				this$0 = OpaqueEndpointI.this;
				super(x0, x1, x2);
			}
		};
	}

	public short type()
	{
		return _type;
	}

	public int timeout()
	{
		return -1;
	}

	public EndpointI timeout(int t)
	{
		return this;
	}

	public EndpointI connectionId(String connectionId)
	{
		return this;
	}

	public boolean compress()
	{
		return false;
	}

	public EndpointI compress(boolean compress)
	{
		return this;
	}

	public boolean datagram()
	{
		return false;
	}

	public boolean secure()
	{
		return false;
	}

	public byte[] rawBytes()
	{
		return _rawBytes;
	}

	public Transceiver transceiver(EndpointIHolder endpoint)
	{
		endpoint.value = null;
		return null;
	}

	public List connectors()
	{
		return new ArrayList();
	}

	public void connectors_async(EndpointI_connectors callback)
	{
		callback.connectors(new ArrayList());
	}

	public Acceptor acceptor(EndpointIHolder endpoint, String adapterName)
	{
		endpoint.value = null;
		return null;
	}

	public List expand()
	{
		List endps = new ArrayList();
		endps.add(this);
		return endps;
	}

	public boolean equivalent(EndpointI endpoint)
	{
		return false;
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
		OpaqueEndpointI p = null;
		try
		{
			p = (OpaqueEndpointI)obj;
		}
		catch (ClassCastException ex)
		{
			return 1;
		}
		if (this == p)
			return 0;
		if (_type < p._type)
			return -1;
		if (p._type < _type)
			return 1;
		if (_rawBytes.length < p._rawBytes.length)
			return -1;
		if (p._rawBytes.length < _rawBytes.length)
			return 1;
		for (int i = 0; i < _rawBytes.length; i++)
		{
			if (_rawBytes[i] < p._rawBytes[i])
				return -1;
			if (p._rawBytes[i] < _rawBytes[i])
				return 1;
		}

		return 0;
	}

	private void calcHashValue()
	{
		_hashCode = _type;
		for (int i = 0; i < _rawBytes.length; i++)
			_hashCode = 5 * _hashCode + _rawBytes[i];

	}

	public volatile int compareTo(Object x0)
	{
		return compareTo((EndpointI)x0);
	}


}
