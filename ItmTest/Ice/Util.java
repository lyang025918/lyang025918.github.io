// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Util.java

package Ice;

import IceUtilInternal.StringUtil;
import java.util.UUID;

// Referenced classes of package Ice:
//			PropertiesI, StringSeqHolder, InitializationData, CommunicatorI, 
//			Identity, IdentityParseException, InputStreamI, OutputStreamI, 
//			LoggerI, ObjectPrx, Logger, Properties, 
//			Communicator, InputStream, OutputStream

public final class Util
{

	private static String _localAddress = null;
	private static Object _processLoggerMutex = new Object();
	private static Logger _processLogger = null;

	public Util()
	{
	}

	public static Properties createProperties()
	{
		return new PropertiesI();
	}

	public static Properties createProperties(StringSeqHolder args)
	{
		return new PropertiesI(args, null);
	}

	public static Properties createProperties(StringSeqHolder args, Properties defaults)
	{
		return new PropertiesI(args, defaults);
	}

	public static Properties createProperties(String args[])
	{
		StringSeqHolder argsH = new StringSeqHolder(args);
		return createProperties(argsH);
	}

	public static Properties createProperties(String args[], Properties defaults)
	{
		StringSeqHolder argsH = new StringSeqHolder(args);
		return createProperties(argsH, defaults);
	}

	public static Communicator initialize(StringSeqHolder args)
	{
		return initialize(args, null);
	}

	public static Communicator initialize(String args[])
	{
		StringSeqHolder argsH = new StringSeqHolder(args);
		return initialize(argsH);
	}

	public static Communicator initialize(StringSeqHolder args, InitializationData initData)
	{
		if (initData == null)
			initData = new InitializationData();
		else
			initData = (InitializationData)initData.clone();
		initData.properties = createProperties(args, initData.properties);
		CommunicatorI result = new CommunicatorI(initData);
		result.finishSetup(args);
		return result;
	}

	public static Communicator initialize(String args[], InitializationData initData)
	{
		StringSeqHolder argsH = new StringSeqHolder(args);
		return initialize(argsH, initData);
	}

	public static Communicator initialize(InitializationData initData)
	{
		if (initData == null)
			initData = new InitializationData();
		else
			initData = (InitializationData)initData.clone();
		CommunicatorI result = new CommunicatorI(initData);
		result.finishSetup(new StringSeqHolder(new String[0]));
		return result;
	}

	public static Communicator initialize()
	{
		return initialize(new InitializationData());
	}

	public static Identity stringToIdentity(String s)
	{
		Identity ident = new Identity();
		int slash = -1;
		for (int pos = 0; (pos = s.indexOf('/', pos)) != -1; pos++)
		{
			if (pos != 0 && s.charAt(pos - 1) == '\\')
				continue;
			if (slash == -1)
			{
				slash = pos;
			} else
			{
				IdentityParseException ex = new IdentityParseException();
				ex.str = (new StringBuilder()).append("unescaped backslash in identity `").append(s).append("'").toString();
				throw ex;
			}
		}

		if (slash == -1)
		{
			ident.category = "";
			try
			{
				ident.name = StringUtil.unescapeString(s, 0, s.length());
			}
			catch (IllegalArgumentException e)
			{
				IdentityParseException ex = new IdentityParseException();
				ex.str = (new StringBuilder()).append("invalid identity name `").append(s).append("': ").append(e.getMessage()).toString();
				throw ex;
			}
		} else
		{
			try
			{
				ident.category = StringUtil.unescapeString(s, 0, slash);
			}
			catch (IllegalArgumentException e)
			{
				IdentityParseException ex = new IdentityParseException();
				ex.str = (new StringBuilder()).append("invalid category in identity `").append(s).append("': ").append(e.getMessage()).toString();
				throw ex;
			}
			if (slash + 1 < s.length())
				try
				{
					ident.name = StringUtil.unescapeString(s, slash + 1, s.length());
				}
				catch (IllegalArgumentException e)
				{
					IdentityParseException ex = new IdentityParseException();
					ex.str = (new StringBuilder()).append("invalid name in identity `").append(s).append("': ").append(e.getMessage()).toString();
					throw ex;
				}
			else
				ident.name = "";
		}
		return ident;
	}

	public static String identityToString(Identity ident)
	{
		if (ident.category == null || ident.category.length() == 0)
			return StringUtil.escapeString(ident.name, "/");
		else
			return (new StringBuilder()).append(StringUtil.escapeString(ident.category, "/")).append('/').append(StringUtil.escapeString(ident.name, "/")).toString();
	}

	/**
	 * @deprecated Method generateUUID is deprecated
	 */

	public static String generateUUID()
	{
		return UUID.randomUUID().toString();
	}

	public static int proxyIdentityCompare(ObjectPrx lhs, ObjectPrx rhs)
	{
		if (lhs == null && rhs == null)
			return 0;
		if (lhs == null && rhs != null)
			return -1;
		if (lhs != null && rhs == null)
			return 1;
		Identity lhsIdentity = lhs.ice_getIdentity();
		Identity rhsIdentity = rhs.ice_getIdentity();
		int n;
		if ((n = lhsIdentity.name.compareTo(rhsIdentity.name)) != 0)
			return n;
		else
			return lhsIdentity.category.compareTo(rhsIdentity.category);
	}

	public static int proxyIdentityAndFacetCompare(ObjectPrx lhs, ObjectPrx rhs)
	{
		if (lhs == null && rhs == null)
			return 0;
		if (lhs == null && rhs != null)
			return -1;
		if (lhs != null && rhs == null)
			return 1;
		Identity lhsIdentity = lhs.ice_getIdentity();
		Identity rhsIdentity = rhs.ice_getIdentity();
		int n;
		if ((n = lhsIdentity.name.compareTo(rhsIdentity.name)) != 0)
			return n;
		if ((n = lhsIdentity.category.compareTo(rhsIdentity.category)) != 0)
			return n;
		String lhsFacet = lhs.ice_getFacet();
		String rhsFacet = rhs.ice_getFacet();
		if (lhsFacet == null && rhsFacet == null)
			return 0;
		if (lhsFacet == null)
			return -1;
		if (rhsFacet == null)
			return 1;
		else
			return lhsFacet.compareTo(rhsFacet);
	}

	public static InputStream createInputStream(Communicator communicator, byte bytes[])
	{
		return new InputStreamI(communicator, bytes);
	}

	public static OutputStream createOutputStream(Communicator communicator)
	{
		return new OutputStreamI(communicator);
	}

	public static Logger getProcessLogger()
	{
		Object obj = _processLoggerMutex;
		JVM INSTR monitorenter ;
		if (_processLogger == null)
			_processLogger = new LoggerI("", "");
		return _processLogger;
		Exception exception;
		exception;
		throw exception;
	}

	public static void setProcessLogger(Logger logger)
	{
		synchronized (_processLoggerMutex)
		{
			_processLogger = logger;
		}
	}

	public static String stringVersion()
	{
		return "3.4.2";
	}

	public static int intVersion()
	{
		return 30402;
	}

}
