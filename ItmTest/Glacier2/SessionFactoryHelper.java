// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SessionFactoryHelper.java

package Glacier2;

import Ice.*;
import java.util.Map;

// Referenced classes of package Glacier2:
//			SessionHelper, SessionCallback

public class SessionFactoryHelper
{

	private SessionCallback _callback;
	private String _routerHost;
	private InitializationData _initData;
	private Identity _identity;
	private boolean _secure;
	private int _port;
	private int _timeout;
	private Map _context;
	private static final int GLACIER2_SSL_PORT = 4064;
	private static final int GLACIER2_TCP_PORT = 4063;

	public SessionFactoryHelper(SessionCallback callback)
		throws InitializationException
	{
		_routerHost = "127.0.0.1";
		_identity = new Identity("router", "Glacier2");
		_secure = true;
		_port = 0;
		_timeout = 10000;
		initialize(callback, new InitializationData(), Util.createProperties());
	}

	public SessionFactoryHelper(InitializationData initData, SessionCallback callback)
		throws InitializationException
	{
		_routerHost = "127.0.0.1";
		_identity = new Identity("router", "Glacier2");
		_secure = true;
		_port = 0;
		_timeout = 10000;
		initialize(callback, initData, initData.properties);
	}

	public SessionFactoryHelper(Properties properties, SessionCallback callback)
		throws InitializationException
	{
		_routerHost = "127.0.0.1";
		_identity = new Identity("router", "Glacier2");
		_secure = true;
		_port = 0;
		_timeout = 10000;
		initialize(callback, new InitializationData(), properties);
	}

	private void initialize(SessionCallback callback, InitializationData initData, Properties properties)
		throws InitializationException
	{
		if (callback == null)
			throw new InitializationException("Attempt to create a SessionFactoryHelper with a null SessionCallback argument");
		if (initData == null)
			throw new InitializationException("Attempt to create a SessionFactoryHelper with a null InitializationData argument");
		if (properties == null)
			throw new InitializationException("Attempt to create a SessionFactoryHelper with a null Properties argument");
		_callback = callback;
		_initData = initData;
		_initData.properties = properties;
		_initData.properties.setProperty("Ice.ACM.Client", "0");
		_initData.properties.setProperty("Ice.RetryIntervals", "-1");
		if (_secure)
			_initData.properties.setProperty("Ice.Plugin.IceSSL", "IceSSL.PluginFactory");
	}

	public synchronized void setRouterIdentity(Identity identity)
	{
		_identity = identity;
	}

	public synchronized Identity getRouterIdentity()
	{
		return _identity;
	}

	public synchronized void setRouterHost(String hostname)
	{
		_routerHost = hostname;
	}

	public synchronized String getRouterHost()
	{
		return _routerHost;
	}

	public synchronized void setSecure(boolean secure)
	{
		_secure = secure;
	}

	public synchronized boolean getSecure()
	{
		return _secure;
	}

	public synchronized void setTimeout(int timeoutMillisecs)
	{
		_timeout = timeoutMillisecs;
	}

	public synchronized int getTimeout()
	{
		return _timeout;
	}

	public synchronized void setPort(int port)
	{
		_port = port;
	}

	public synchronized int getPort()
	{
		return _port != 0 ? _port : _secure ? 4064 : 4063;
	}

	public synchronized InitializationData getInitializationData()
	{
		return _initData;
	}

	public synchronized void setConnectContext(Map context)
	{
		_context = context;
	}

	public synchronized SessionHelper connect()
	{
		SessionHelper session = new SessionHelper(_callback, createInitData());
		session.connect(_context);
		return session;
	}

	public synchronized SessionHelper connect(String username, String password)
	{
		SessionHelper session = new SessionHelper(_callback, createInitData());
		session.connect(username, password, _context);
		return session;
	}

	private InitializationData createInitData()
	{
		InitializationData initData = (InitializationData)_initData.clone();
		initData.properties = initData.properties._clone();
		if (initData.properties.getProperty("Ice.Default.Router").length() == 0)
		{
			StringBuffer sb = new StringBuffer();
			sb.append("\"");
			sb.append(Util.identityToString(_identity));
			sb.append("\"");
			sb.append(":");
			if (_secure)
				sb.append("ssl -p ");
			else
				sb.append("tcp -p ");
			if (_port != 0)
				sb.append(_port);
			else
			if (_secure)
				sb.append(4064);
			else
				sb.append(4063);
			sb.append(" -h ");
			sb.append(_routerHost);
			if (_timeout > 0)
			{
				sb.append(" -t ");
				sb.append(_timeout);
			}
			initData.properties.setProperty("Ice.Default.Router", sb.toString());
		}
		return initData;
	}
}
