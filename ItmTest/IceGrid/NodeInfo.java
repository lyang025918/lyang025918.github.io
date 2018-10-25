// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NodeInfo.java

package IceGrid;

import IceInternal.BasicStream;
import java.io.Serializable;

public class NodeInfo
	implements Cloneable, Serializable
{

	public String name;
	public String os;
	public String hostname;
	public String release;
	public String version;
	public String machine;
	public int nProcessors;
	public String dataDir;
	static final boolean $assertionsDisabled = !IceGrid/NodeInfo.desiredAssertionStatus();

	public NodeInfo()
	{
	}

	public NodeInfo(String name, String os, String hostname, String release, String version, String machine, int nProcessors, 
			String dataDir)
	{
		this.name = name;
		this.os = os;
		this.hostname = hostname;
		this.release = release;
		this.version = version;
		this.machine = machine;
		this.nProcessors = nProcessors;
		this.dataDir = dataDir;
	}

	public boolean equals(Object rhs)
	{
		if (this == rhs)
			return true;
		NodeInfo _r = null;
		try
		{
			_r = (NodeInfo)rhs;
		}
		catch (ClassCastException ex) { }
		if (_r != null)
		{
			if (name != _r.name && (name == null || _r.name == null || !name.equals(_r.name)))
				return false;
			if (os != _r.os && (os == null || _r.os == null || !os.equals(_r.os)))
				return false;
			if (hostname != _r.hostname && (hostname == null || _r.hostname == null || !hostname.equals(_r.hostname)))
				return false;
			if (release != _r.release && (release == null || _r.release == null || !release.equals(_r.release)))
				return false;
			if (version != _r.version && (version == null || _r.version == null || !version.equals(_r.version)))
				return false;
			if (machine != _r.machine && (machine == null || _r.machine == null || !machine.equals(_r.machine)))
				return false;
			if (nProcessors != _r.nProcessors)
				return false;
			return dataDir == _r.dataDir || dataDir != null && _r.dataDir != null && dataDir.equals(_r.dataDir);
		} else
		{
			return false;
		}
	}

	public int hashCode()
	{
		int __h = 0;
		if (name != null)
			__h = 5 * __h + name.hashCode();
		if (os != null)
			__h = 5 * __h + os.hashCode();
		if (hostname != null)
			__h = 5 * __h + hostname.hashCode();
		if (release != null)
			__h = 5 * __h + release.hashCode();
		if (version != null)
			__h = 5 * __h + version.hashCode();
		if (machine != null)
			__h = 5 * __h + machine.hashCode();
		__h = 5 * __h + nProcessors;
		if (dataDir != null)
			__h = 5 * __h + dataDir.hashCode();
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
		__os.writeString(name);
		__os.writeString(os);
		__os.writeString(hostname);
		__os.writeString(release);
		__os.writeString(version);
		__os.writeString(machine);
		__os.writeInt(nProcessors);
		__os.writeString(dataDir);
	}

	public void __read(BasicStream __is)
	{
		name = __is.readString();
		os = __is.readString();
		hostname = __is.readString();
		release = __is.readString();
		version = __is.readString();
		machine = __is.readString();
		nProcessors = __is.readInt();
		dataDir = __is.readString();
	}

}
