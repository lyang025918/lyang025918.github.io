// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ServerDescriptor.java

package IceGrid;

import Ice.*;
import IceInternal.BasicStream;
import java.util.*;

// Referenced classes of package IceGrid:
//			CommunicatorDescriptor, DistributionDescriptor, PropertySetDescriptor

public class ServerDescriptor extends CommunicatorDescriptor
{
	private static class __F
		implements ObjectFactory
	{

		static final boolean $assertionsDisabled = !IceGrid/ServerDescriptor.desiredAssertionStatus();

		public Ice.Object create(String type)
		{
			if (!$assertionsDisabled && !type.equals(ServerDescriptor.ice_staticId()))
				throw new AssertionError();
			else
				return new ServerDescriptor();
		}

		public void destroy()
		{
		}


		private __F()
		{
		}

	}


	private static ObjectFactory _factory = new __F();
	public static final String __ids[] = {
		"::Ice::Object", "::IceGrid::CommunicatorDescriptor", "::IceGrid::ServerDescriptor"
	};
	public String id;
	public String exe;
	public String iceVersion;
	public String pwd;
	public List options;
	public List envs;
	public String activation;
	public String activationTimeout;
	public String deactivationTimeout;
	public boolean applicationDistrib;
	public DistributionDescriptor distrib;
	public boolean allocatable;
	public String user;

	public ServerDescriptor()
	{
	}

	public ServerDescriptor(List adapters, PropertySetDescriptor propertySet, List dbEnvs, String logs[], String description, String id, String exe, 
			String iceVersion, String pwd, List options, List envs, String activation, String activationTimeout, String deactivationTimeout, 
			boolean applicationDistrib, DistributionDescriptor distrib, boolean allocatable, String user)
	{
		super(adapters, propertySet, dbEnvs, logs, description);
		this.id = id;
		this.exe = exe;
		this.iceVersion = iceVersion;
		this.pwd = pwd;
		this.options = options;
		this.envs = envs;
		this.activation = activation;
		this.activationTimeout = activationTimeout;
		this.deactivationTimeout = deactivationTimeout;
		this.applicationDistrib = applicationDistrib;
		this.distrib = distrib;
		this.allocatable = allocatable;
		this.user = user;
	}

	public static ObjectFactory ice_factory()
	{
		return _factory;
	}

	public boolean ice_isA(String s)
	{
		return Arrays.binarySearch(__ids, s) >= 0;
	}

	public boolean ice_isA(String s, Current __current)
	{
		return Arrays.binarySearch(__ids, s) >= 0;
	}

	public String[] ice_ids()
	{
		return __ids;
	}

	public String[] ice_ids(Current __current)
	{
		return __ids;
	}

	public String ice_id()
	{
		return __ids[2];
	}

	public String ice_id(Current __current)
	{
		return __ids[2];
	}

	public static String ice_staticId()
	{
		return __ids[2];
	}

	public void __write(BasicStream __os)
	{
		__os.writeTypeId(ice_staticId());
		__os.startWriteSlice();
		__os.writeString(id);
		__os.writeString(exe);
		__os.writeString(iceVersion);
		__os.writeString(pwd);
		if (options == null)
		{
			__os.writeSize(0);
		} else
		{
			__os.writeSize(options.size());
			String __elem;
			for (Iterator i$ = options.iterator(); i$.hasNext(); __os.writeString(__elem))
				__elem = (String)i$.next();

		}
		if (envs == null)
		{
			__os.writeSize(0);
		} else
		{
			__os.writeSize(envs.size());
			String __elem;
			for (Iterator i$ = envs.iterator(); i$.hasNext(); __os.writeString(__elem))
				__elem = (String)i$.next();

		}
		__os.writeString(activation);
		__os.writeString(activationTimeout);
		__os.writeString(deactivationTimeout);
		__os.writeBool(applicationDistrib);
		distrib.__write(__os);
		__os.writeBool(allocatable);
		__os.writeString(user);
		__os.endWriteSlice();
		super.__write(__os);
	}

	public void __read(BasicStream __is, boolean __rid)
	{
		if (__rid)
			__is.readTypeId();
		__is.startReadSlice();
		id = __is.readString();
		exe = __is.readString();
		iceVersion = __is.readString();
		pwd = __is.readString();
		options = new LinkedList();
		int __len0 = __is.readAndCheckSeqSize(1);
		for (int __i0 = 0; __i0 < __len0; __i0++)
		{
			String __elem = __is.readString();
			options.add(__elem);
		}

		envs = new LinkedList();
		int __len1 = __is.readAndCheckSeqSize(1);
		for (int __i1 = 0; __i1 < __len1; __i1++)
		{
			String __elem = __is.readString();
			envs.add(__elem);
		}

		activation = __is.readString();
		activationTimeout = __is.readString();
		deactivationTimeout = __is.readString();
		applicationDistrib = __is.readBool();
		distrib = new DistributionDescriptor();
		distrib.__read(__is);
		allocatable = __is.readBool();
		user = __is.readString();
		__is.endReadSlice();
		super.__read(__is, true);
	}

	public void __write(OutputStream __outS)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "type IceGrid::ServerDescriptor was not generated with stream support";
		throw ex;
	}

	public void __read(InputStream __inS, boolean __rid)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "type IceGrid::ServerDescriptor was not generated with stream support";
		throw ex;
	}

}
