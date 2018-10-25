// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ProxyIdentityKey.java

package Ice;


// Referenced classes of package Ice:
//			ObjectPrx, Identity

public class ProxyIdentityKey
{

	private final ObjectPrx _proxy;
	private final Identity _identity;
	private final int _hashCode;

	public ProxyIdentityKey(ObjectPrx proxy)
	{
		_proxy = proxy;
		_identity = proxy.ice_getIdentity();
		_hashCode = _identity.hashCode();
	}

	public int hashCode()
	{
		return _hashCode;
	}

	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj instanceof ProxyIdentityKey)
		{
			ProxyIdentityKey other = (ProxyIdentityKey)obj;
			return _hashCode == other._hashCode && _identity.equals(other._identity);
		} else
		{
			return false;
		}
	}

	public ObjectPrx getProxy()
	{
		return _proxy;
	}
}
