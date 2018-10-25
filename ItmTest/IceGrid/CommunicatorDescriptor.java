// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CommunicatorDescriptor.java

package IceGrid;

import Ice.*;
import IceInternal.BasicStream;
import java.util.Arrays;
import java.util.List;

// Referenced classes of package IceGrid:
//			PropertySetDescriptor, AdapterDescriptorSeqHelper, DbEnvDescriptorSeqHelper

public class CommunicatorDescriptor extends ObjectImpl
{
	private static class __F
		implements ObjectFactory
	{

		static final boolean $assertionsDisabled = !IceGrid/CommunicatorDescriptor.desiredAssertionStatus();

		public Ice.Object create(String type)
		{
			if (!$assertionsDisabled && !type.equals(CommunicatorDescriptor.ice_staticId()))
				throw new AssertionError();
			else
				return new CommunicatorDescriptor();
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
		"::Ice::Object", "::IceGrid::CommunicatorDescriptor"
	};
	public List adapters;
	public PropertySetDescriptor propertySet;
	public List dbEnvs;
	public String logs[];
	public String description;

	public CommunicatorDescriptor()
	{
	}

	public CommunicatorDescriptor(List adapters, PropertySetDescriptor propertySet, List dbEnvs, String logs[], String description)
	{
		this.adapters = adapters;
		this.propertySet = propertySet;
		this.dbEnvs = dbEnvs;
		this.logs = logs;
		this.description = description;
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
		return __ids[1];
	}

	public String ice_id(Current __current)
	{
		return __ids[1];
	}

	public static String ice_staticId()
	{
		return __ids[1];
	}

	public void __write(BasicStream __os)
	{
		__os.writeTypeId(ice_staticId());
		__os.startWriteSlice();
		AdapterDescriptorSeqHelper.write(__os, adapters);
		propertySet.__write(__os);
		DbEnvDescriptorSeqHelper.write(__os, dbEnvs);
		StringSeqHelper.write(__os, logs);
		__os.writeString(description);
		__os.endWriteSlice();
		super.__write(__os);
	}

	public void __read(BasicStream __is, boolean __rid)
	{
		if (__rid)
			__is.readTypeId();
		__is.startReadSlice();
		adapters = AdapterDescriptorSeqHelper.read(__is);
		propertySet = new PropertySetDescriptor();
		propertySet.__read(__is);
		dbEnvs = DbEnvDescriptorSeqHelper.read(__is);
		logs = StringSeqHelper.read(__is);
		description = __is.readString();
		__is.endReadSlice();
		super.__read(__is, true);
	}

	public void __write(OutputStream __outS)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "type IceGrid::CommunicatorDescriptor was not generated with stream support";
		throw ex;
	}

	public void __read(InputStream __inS, boolean __rid)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "type IceGrid::CommunicatorDescriptor was not generated with stream support";
		throw ex;
	}

}
