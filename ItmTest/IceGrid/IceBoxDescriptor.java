// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IceBoxDescriptor.java

package IceGrid;

import Ice.*;
import IceInternal.BasicStream;
import java.util.Arrays;
import java.util.List;

// Referenced classes of package IceGrid:
//			ServerDescriptor, ServiceInstanceDescriptorSeqHelper, PropertySetDescriptor, DistributionDescriptor

public class IceBoxDescriptor extends ServerDescriptor
{
	private static class __F
		implements ObjectFactory
	{

		static final boolean $assertionsDisabled = !IceGrid/IceBoxDescriptor.desiredAssertionStatus();

		public Ice.Object create(String type)
		{
			if (!$assertionsDisabled && !type.equals(IceBoxDescriptor.ice_staticId()))
				throw new AssertionError();
			else
				return new IceBoxDescriptor();
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
		"::Ice::Object", "::IceGrid::CommunicatorDescriptor", "::IceGrid::IceBoxDescriptor", "::IceGrid::ServerDescriptor"
	};
	public List services;

	public IceBoxDescriptor()
	{
	}

	public IceBoxDescriptor(List adapters, PropertySetDescriptor propertySet, List dbEnvs, String logs[], String description, String id, String exe, 
			String iceVersion, String pwd, List options, List envs, String activation, String activationTimeout, String deactivationTimeout, 
			boolean applicationDistrib, DistributionDescriptor distrib, boolean allocatable, String user, List services)
	{
		super(adapters, propertySet, dbEnvs, logs, description, id, exe, iceVersion, pwd, options, envs, activation, activationTimeout, deactivationTimeout, applicationDistrib, distrib, allocatable, user);
		this.services = services;
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
		ServiceInstanceDescriptorSeqHelper.write(__os, services);
		__os.endWriteSlice();
		super.__write(__os);
	}

	public void __read(BasicStream __is, boolean __rid)
	{
		if (__rid)
			__is.readTypeId();
		__is.startReadSlice();
		services = ServiceInstanceDescriptorSeqHelper.read(__is);
		__is.endReadSlice();
		super.__read(__is, true);
	}

	public void __write(OutputStream __outS)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "type IceGrid::IceBoxDescriptor was not generated with stream support";
		throw ex;
	}

	public void __read(InputStream __inS, boolean __rid)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "type IceGrid::IceBoxDescriptor was not generated with stream support";
		throw ex;
	}

}
