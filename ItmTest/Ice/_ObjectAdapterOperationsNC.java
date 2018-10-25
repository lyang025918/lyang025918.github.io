// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _ObjectAdapterOperationsNC.java

package Ice;

import java.util.Map;

// Referenced classes of package Ice:
//			Communicator, Object, Identity, ObjectPrx, 
//			ServantLocator, LocatorPrx, Endpoint

public interface _ObjectAdapterOperationsNC
{

	public abstract String getName();

	public abstract Communicator getCommunicator();

	public abstract void activate();

	public abstract void hold();

	public abstract void waitForHold();

	public abstract void deactivate();

	public abstract void waitForDeactivate();

	public abstract boolean isDeactivated();

	public abstract void destroy();

	public abstract ObjectPrx add(Object obj, Identity identity);

	public abstract ObjectPrx addFacet(Object obj, Identity identity, String s);

	public abstract ObjectPrx addWithUUID(Object obj);

	public abstract ObjectPrx addFacetWithUUID(Object obj, String s);

	public abstract void addDefaultServant(Object obj, String s);

	public abstract Object remove(Identity identity);

	public abstract Object removeFacet(Identity identity, String s);

	public abstract Map removeAllFacets(Identity identity);

	public abstract Object removeDefaultServant(String s);

	public abstract Object find(Identity identity);

	public abstract Object findFacet(Identity identity, String s);

	public abstract Map findAllFacets(Identity identity);

	public abstract Object findByProxy(ObjectPrx objectprx);

	public abstract void addServantLocator(ServantLocator servantlocator, String s);

	public abstract ServantLocator removeServantLocator(String s);

	public abstract ServantLocator findServantLocator(String s);

	public abstract Object findDefaultServant(String s);

	public abstract ObjectPrx createProxy(Identity identity);

	public abstract ObjectPrx createDirectProxy(Identity identity);

	public abstract ObjectPrx createIndirectProxy(Identity identity);

	public abstract void setLocator(LocatorPrx locatorprx);

	public abstract void refreshPublishedEndpoints();

	public abstract Endpoint[] getEndpoints();

	public abstract Endpoint[] getPublishedEndpoints();
}
