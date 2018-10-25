// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ProxyIdentityFacetKey.java

package Ice;


// Referenced classes of package Ice:
//			ObjectPrx, Identity

public class ProxyIdentityFacetKey
{

	private final ObjectPrx _proxy;
	private final Identity _identity;
	private final String _facet;
	private final int _hashCode;

	public ProxyIdentityFacetKey(ObjectPrx proxy)
	{
		_proxy = proxy;
		_identity = proxy.ice_getIdentity();
		_facet = proxy.ice_getFacet();
		int h = _identity.hashCode();
		h = 5 * h + _facet.hashCode();
		_hashCode = h;
	}

	public int hashCode()
	{
		return _hashCode;
	}

	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj instanceof ProxyIdentityFacetKey)
		{
			ProxyIdentityFacetKey other = (ProxyIdentityFacetKey)obj;
			return _hashCode == other._hashCode && _identity.equals(other._identity) && _facet.equals(other._facet);
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
