// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SSLInfo.java

package Glacier2;

import Ice.StringSeqHelper;
import IceInternal.BasicStream;
import java.io.Serializable;
import java.util.Arrays;

public class SSLInfo
	implements Cloneable, Serializable
{

	public String remoteHost;
	public int remotePort;
	public String localHost;
	public int localPort;
	public String cipher;
	public String certs[];
	static final boolean $assertionsDisabled = !Glacier2/SSLInfo.desiredAssertionStatus();

	public SSLInfo()
	{
	}

	public SSLInfo(String remoteHost, int remotePort, String localHost, int localPort, String cipher, String certs[])
	{
		this.remoteHost = remoteHost;
		this.remotePort = remotePort;
		this.localHost = localHost;
		this.localPort = localPort;
		this.cipher = cipher;
		this.certs = certs;
	}

	public boolean equals(Object rhs)
	{
		if (this == rhs)
			return true;
		SSLInfo _r = null;
		try
		{
			_r = (SSLInfo)rhs;
		}
		catch (ClassCastException ex) { }
		if (_r != null)
		{
			if (remoteHost != _r.remoteHost && (remoteHost == null || _r.remoteHost == null || !remoteHost.equals(_r.remoteHost)))
				return false;
			if (remotePort != _r.remotePort)
				return false;
			if (localHost != _r.localHost && (localHost == null || _r.localHost == null || !localHost.equals(_r.localHost)))
				return false;
			if (localPort != _r.localPort)
				return false;
			if (cipher != _r.cipher && (cipher == null || _r.cipher == null || !cipher.equals(_r.cipher)))
				return false;
			return Arrays.equals(certs, _r.certs);
		} else
		{
			return false;
		}
	}

	public int hashCode()
	{
		int __h = 0;
		if (remoteHost != null)
			__h = 5 * __h + remoteHost.hashCode();
		__h = 5 * __h + remotePort;
		if (localHost != null)
			__h = 5 * __h + localHost.hashCode();
		__h = 5 * __h + localPort;
		if (cipher != null)
			__h = 5 * __h + cipher.hashCode();
		if (certs != null)
		{
			for (int __i0 = 0; __i0 < certs.length; __i0++)
				if (certs[__i0] != null)
					__h = 5 * __h + certs[__i0].hashCode();

		}
		return __h;
	}

	public Object clone()
	{
		Object o = null;
		try
		{
			o = super.clone();
		}
		catch (CloneNotSupportedException ex)
		{
			if (!$assertionsDisabled)
				throw new AssertionError();
		}
		return o;
	}

	public void __write(BasicStream __os)
	{
		__os.writeString(remoteHost);
		__os.writeInt(remotePort);
		__os.writeString(localHost);
		__os.writeInt(localPort);
		__os.writeString(cipher);
		StringSeqHelper.write(__os, certs);
	}

	public void __read(BasicStream __is)
	{
		remoteHost = __is.readString();
		remotePort = __is.readInt();
		localHost = __is.readString();
		localPort = __is.readInt();
		cipher = __is.readString();
		certs = StringSeqHelper.read(__is);
	}

}
