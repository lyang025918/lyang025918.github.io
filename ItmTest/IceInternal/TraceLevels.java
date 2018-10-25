// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TraceLevels.java

package IceInternal;

import Ice.Properties;

public final class TraceLevels
{

	public final int network;
	public final String networkCat = "Network";
	public final int protocol;
	public final String protocolCat = "Protocol";
	public final int retry;
	public final String retryCat = "Retry";
	public final int location;
	public final String locationCat = "Locator";
	public final int slicing;
	public final String threadPoolCat = "ThreadPool";
	public final int threadPool;
	public final String slicingCat = "Slicing";

	TraceLevels(Properties properties)
	{
		String keyBase = "Ice.Trace.";
		network = properties.getPropertyAsInt((new StringBuilder()).append("Ice.Trace.").append(networkCat).toString());
		protocol = properties.getPropertyAsInt((new StringBuilder()).append("Ice.Trace.").append(protocolCat).toString());
		retry = properties.getPropertyAsInt((new StringBuilder()).append("Ice.Trace.").append(retryCat).toString());
		location = properties.getPropertyAsInt((new StringBuilder()).append("Ice.Trace.").append(locationCat).toString());
		slicing = properties.getPropertyAsInt((new StringBuilder()).append("Ice.Trace.").append(slicingCat).toString());
		threadPool = properties.getPropertyAsInt((new StringBuilder()).append("Ice.Trace.").append(threadPoolCat).toString());
	}
}
