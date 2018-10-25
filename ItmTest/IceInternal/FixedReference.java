// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FixedReference.java

package IceInternal;

import Ice.*;
import java.util.Map;

// Referenced classes of package IceInternal:
//			Reference, EndpointI, Instance, DefaultsAndOverrides, 
//			LocatorInfo, RouterInfo, BasicStream

public class FixedReference extends Reference
{

	private ConnectionI _fixedConnection;
	private static EndpointI _emptyEndpoints[] = new EndpointI[0];

	public FixedReference(Instance instance, Communicator communicator, Identity identity, String facet, int mode, boolean secure, ConnectionI connection)
	{
		super(instance, communicator, identity, facet, mode, secure);
		_fixedConnection = connection;
	}

	public EndpointI[] getEndpoints()
	{
		return _emptyEndpoints;
	}

	public String getAdapterId()
	{
		return "";
	}

	public LocatorInfo getLocatorInfo()
	{
		return null;
	}

	public RouterInfo getRouterInfo()
	{
		return null;
	}

	public boolean getCollocationOptimized()
	{
		return false;
	}

	public final boolean getCacheConnection()
	{
		return false;
	}

	public boolean getPreferSecure()
	{
		return false;
	}

	public final EndpointSelectionType getEndpointSelection()
	{
		return EndpointSelectionType.Random;
	}

	public int getLocatorCacheTimeout()
	{
		return 0;
	}

	public String getConnectionId()
	{
		return "";
	}

	public Reference changeEndpoints(EndpointI newEndpoints[])
	{
		throw new FixedProxyException();
	}

	public Reference changeAdapterId(String newAdapterId)
	{
		throw new FixedProxyException();
	}

	public Reference changeLocator(LocatorPrx newLocator)
	{
		throw new FixedProxyException();
	}

	public Reference changeRouter(RouterPrx newRouter)
	{
		throw new FixedProxyException();
	}

	public Reference changeCollocationOptimized(boolean newCollocationOptimized)
	{
		throw new FixedProxyException();
	}

	public final Reference changeCacheConnection(boolean newCache)
	{
		throw new FixedProxyException();
	}

	public Reference changePreferSecure(boolean prefSec)
	{
		throw new FixedProxyException();
	}

	public final Reference changeEndpointSelection(EndpointSelectionType newType)
	{
		throw new FixedProxyException();
	}

	public Reference changeLocatorCacheTimeout(int newTimeout)
	{
		throw new FixedProxyException();
	}

	public Reference changeTimeout(int newTimeout)
	{
		throw new FixedProxyException();
	}

	public Reference changeConnectionId(String connectionId)
	{
		throw new FixedProxyException();
	}

	public boolean isIndirect()
	{
		return false;
	}

	public boolean isWellKnown()
	{
		return false;
	}

	public void streamWrite(BasicStream s)
		throws MarshalException
	{
		throw new FixedProxyException();
	}

	public String toString()
		throws MarshalException
	{
		throw new FixedProxyException();
	}

	public Map toProperty(String prefix)
	{
		throw new FixedProxyException();
	}

	public ConnectionI getConnection(BooleanHolder compress)
	{
		switch (getMode())
		{
		default:
			break;

		case 0: // '\0'
		case 1: // '\001'
		case 2: // '\002'
			if (_fixedConnection.endpoint().datagram())
				throw new NoEndpointException("");
			break;

		case 3: // '\003'
		case 4: // '\004'
			if (!_fixedConnection.endpoint().datagram())
				throw new NoEndpointException("");
			break;
		}
		DefaultsAndOverrides defaultsAndOverrides = getInstance().defaultsAndOverrides();
		boolean secure;
		if (defaultsAndOverrides.overrideSecure)
			secure = defaultsAndOverrides.overrideSecureValue;
		else
			secure = getSecure();
		if (secure && !_fixedConnection.endpoint().secure())
			throw new NoEndpointException("");
		_fixedConnection.throwException();
		if (defaultsAndOverrides.overrideCompress)
			compress.value = defaultsAndOverrides.overrideCompressValue;
		else
		if (_overrideCompress)
			compress.value = _compress;
		else
			compress.value = _fixedConnection.endpoint().compress();
		return _fixedConnection;
	}

	public void getConnection(Reference.GetConnectionCallback callback)
	{
		try
		{
			BooleanHolder compress = new BooleanHolder();
			ConnectionI connection = getConnection(compress);
			callback.setConnection(connection, compress.value);
		}
		catch (LocalException ex)
		{
			callback.setException(ex);
		}
	}

	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!(obj instanceof FixedReference))
			return false;
		FixedReference rhs = (FixedReference)obj;
		if (!super.equals(rhs))
			return false;
		else
			return _fixedConnection.equals(rhs._fixedConnection);
	}

	public int hashCode()
	{
		return super.hashCode();
	}

}
