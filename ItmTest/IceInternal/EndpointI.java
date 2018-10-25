// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EndpointI.java

package IceInternal;

import Ice.Endpoint;
import java.util.List;

// Referenced classes of package IceInternal:
//			BasicStream, EndpointIHolder, Transceiver, EndpointI_connectors, 
//			Acceptor

public abstract class EndpointI
	implements Endpoint, Comparable
{

	static final boolean $assertionsDisabled = !IceInternal/EndpointI.desiredAssertionStatus();

	public EndpointI()
	{
	}

	public String toString()
	{
		return _toString();
	}

	public abstract void streamWrite(BasicStream basicstream);

	public abstract short type();

	public abstract int timeout();

	public abstract EndpointI timeout(int i);

	public abstract EndpointI connectionId(String s);

	public abstract boolean compress();

	public abstract EndpointI compress(boolean flag);

	public abstract boolean datagram();

	public abstract boolean secure();

	public abstract Transceiver transceiver(EndpointIHolder endpointiholder);

	public abstract List connectors();

	public abstract void connectors_async(EndpointI_connectors endpointi_connectors);

	public abstract Acceptor acceptor(EndpointIHolder endpointiholder, String s);

	public abstract List expand();

	public abstract boolean equivalent(EndpointI endpointi);

	public abstract boolean equals(Object obj);

	public abstract int compareTo(EndpointI endpointi);

	public List connectors(List addresses)
	{
		if (!$assertionsDisabled)
			throw new AssertionError();
		else
			return null;
	}

	public volatile int compareTo(Object x0)
	{
		return compareTo((EndpointI)x0);
	}

}
