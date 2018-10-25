// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AdaptiveLoadBalancingPolicy.java

package IceGrid;

import Ice.*;
import IceInternal.BasicStream;
import java.util.Arrays;

// Referenced classes of package IceGrid:
//			LoadBalancingPolicy

public class AdaptiveLoadBalancingPolicy extends LoadBalancingPolicy
{
	private static class __F
		implements ObjectFactory
	{

		static final boolean $assertionsDisabled = !IceGrid/AdaptiveLoadBalancingPolicy.desiredAssertionStatus();

		public Ice.Object create(String type)
		{
			if (!$assertionsDisabled && !type.equals(AdaptiveLoadBalancingPolicy.ice_staticId()))
				throw new AssertionError();
			else
				return new AdaptiveLoadBalancingPolicy();
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
		"::Ice::Object", "::IceGrid::AdaptiveLoadBalancingPolicy", "::IceGrid::LoadBalancingPolicy"
	};
	public String loadSample;

	public AdaptiveLoadBalancingPolicy()
	{
	}

	public AdaptiveLoadBalancingPolicy(String nReplicas, String loadSample)
	{
		super(nReplicas);
		this.loadSample = loadSample;
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
		__os.writeString(loadSample);
		__os.endWriteSlice();
		super.__write(__os);
	}

	public void __read(BasicStream __is, boolean __rid)
	{
		if (__rid)
			__is.readTypeId();
		__is.startReadSlice();
		loadSample = __is.readString();
		__is.endReadSlice();
		super.__read(__is, true);
	}

	public void __write(OutputStream __outS)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "type IceGrid::AdaptiveLoadBalancingPolicy was not generated with stream support";
		throw ex;
	}

	public void __read(InputStream __inS, boolean __rid)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "type IceGrid::AdaptiveLoadBalancingPolicy was not generated with stream support";
		throw ex;
	}

}
