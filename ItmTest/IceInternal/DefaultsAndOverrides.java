// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DefaultsAndOverrides.java

package IceInternal;

import Ice.*;
import java.io.PrintStream;

// Referenced classes of package IceInternal:
//			BasicStream

public final class DefaultsAndOverrides
{

	public final String defaultHost;
	public final String defaultProtocol;
	public final boolean defaultCollocationOptimization;
	public final EndpointSelectionType defaultEndpointSelection;
	public final int defaultLocatorCacheTimeout;
	public final boolean defaultPreferSecure;
	public final boolean overrideTimeout;
	public final int overrideTimeoutValue;
	public final boolean overrideConnectTimeout;
	public final int overrideConnectTimeoutValue;
	public final boolean overrideCloseTimeout;
	public final int overrideCloseTimeoutValue;
	public final boolean overrideCompress;
	public final boolean overrideCompressValue;
	public final boolean overrideSecure;
	public final boolean overrideSecureValue;

	DefaultsAndOverrides(Properties properties)
	{
		defaultProtocol = properties.getPropertyWithDefault("Ice.Default.Protocol", "tcp");
		String value = properties.getProperty("Ice.Default.Host");
		if (value.length() != 0)
			defaultHost = value;
		else
			defaultHost = null;
		value = properties.getProperty("Ice.Override.Timeout");
		if (value.length() > 0)
		{
			overrideTimeout = true;
			overrideTimeoutValue = properties.getPropertyAsInt("Ice.Override.Timeout");
		} else
		{
			overrideTimeout = false;
			overrideTimeoutValue = -1;
		}
		value = properties.getProperty("Ice.Override.ConnectTimeout");
		if (value.length() > 0)
		{
			overrideConnectTimeout = true;
			overrideConnectTimeoutValue = properties.getPropertyAsInt("Ice.Override.ConnectTimeout");
		} else
		{
			overrideConnectTimeout = false;
			overrideConnectTimeoutValue = -1;
		}
		value = properties.getProperty("Ice.Override.CloseTimeout");
		if (value.length() > 0)
		{
			overrideCloseTimeout = true;
			overrideCloseTimeoutValue = properties.getPropertyAsInt("Ice.Override.CloseTimeout");
		} else
		{
			overrideCloseTimeout = false;
			overrideCloseTimeoutValue = -1;
		}
		value = properties.getProperty("Ice.Override.Compress");
		if (value.length() > 0)
		{
			overrideCompress = true;
			boolean b = properties.getPropertyAsInt("Ice.Override.Compress") > 0;
			if (b && !BasicStream.compressible())
			{
				System.err.println("warning: bzip2 support not available, Ice.Override.Compress ignored");
				b = false;
			}
			overrideCompressValue = b;
		} else
		{
			overrideCompress = false;
			overrideCompressValue = false;
		}
		value = properties.getProperty("Ice.Override.Secure");
		if (value.length() > 0)
		{
			overrideSecure = true;
			overrideSecureValue = properties.getPropertyAsInt("Ice.Override.Secure") > 0;
		} else
		{
			overrideSecure = false;
			overrideSecureValue = false;
		}
		defaultCollocationOptimization = properties.getPropertyAsIntWithDefault("Ice.Default.CollocationOptimized", 1) > 0;
		value = properties.getPropertyWithDefault("Ice.Default.EndpointSelection", "Random");
		if (value.equals("Random"))
			defaultEndpointSelection = EndpointSelectionType.Random;
		else
		if (value.equals("Ordered"))
		{
			defaultEndpointSelection = EndpointSelectionType.Ordered;
		} else
		{
			EndpointSelectionTypeParseException ex = new EndpointSelectionTypeParseException();
			ex.str = (new StringBuilder()).append("illegal value `").append(value).append("'; expected `Random' or `Ordered'").toString();
			throw ex;
		}
		defaultLocatorCacheTimeout = properties.getPropertyAsIntWithDefault("Ice.Default.LocatorCacheTimeout", -1);
		defaultPreferSecure = properties.getPropertyAsIntWithDefault("Ice.Default.PreferSecure", 0) > 0;
	}
}
