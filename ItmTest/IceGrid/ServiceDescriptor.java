// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ServiceDescriptor.java

package IceGrid;

import Ice.*;
import IceInternal.BasicStream;
import java.util.Arrays;
import java.util.List;

// Referenced classes of package IceGrid:
//			CommunicatorDescriptor, PropertySetDescriptor

public class ServiceDescriptor extends CommunicatorDescriptor
{
	private static class __F
		implements ObjectFactory
	{

		static final boolean $assertionsDisabled = !IceGrid/ServiceDescriptor.desiredAssertionStatus();

		public Ice.Object create(String type)
		{
			if (!$assertionsDisabled && !type.equals(ServiceDescriptor.ice_staticId()))
				throw new AssertionError();
			else
				return new ServiceDescriptor();
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
		"::Ice::Object", "::IceGrid::CommunicatorDescriptor", "::IceGrid::ServiceDescriptor"
	};
	public String name;
	public String entry;

	public ServiceDescriptor()
	{
	}

	public ServiceDescriptor(List adapters, PropertySetDescriptor propertySet, List dbEnvs, String logs[], String description, String name, String entry)
	{
		super(adapters, propertySet, dbEnvs, logs, description);
		this.name = name;
		this.entry = entry;
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
		__os.writeString(name);
		__os.writeString(entry);
		__os.endWriteSlice();
		super.__write(__os);
	}

	public void __read(BasicStream __is, boolean __rid)
	{
		if (__rid)
			__is.readTypeId();
		__is.startReadSlice();
		name = __is.readString();
		entry = __is.readString();
		__is.endReadSlice();
		super.__read(__is, true);
	}

	public void __write(OutputStream __outS)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "type IceGrid::ServiceDescriptor was not generated with stream support";
		throw ex;
	}

	public void __read(InputStream __inS, boolean __rid)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "type IceGrid::ServiceDescriptor was not generated with stream support";
		throw ex;
	}

}
