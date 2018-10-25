// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ConnectionReaper.java

package IceInternal;

import Ice.ConnectionI;
import java.util.ArrayList;
import java.util.List;

public class ConnectionReaper
{

	private List _connections;

	public ConnectionReaper()
	{
		_connections = new ArrayList();
	}

	public synchronized void add(ConnectionI connection)
	{
		_connections.add(connection);
	}

	public synchronized List swapConnections()
	{
		if (_connections.isEmpty())
		{
			return null;
		} else
		{
			List connections = _connections;
			_connections = new ArrayList();
			return connections;
		}
	}
}
