// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BadQoS.java

package IceStorm;

import Ice.*;
import IceInternal.BasicStream;

public class BadQoS extends UserException
{

	public String reason;

	public BadQoS()
	{
	}

	public BadQoS(Throwable cause)
	{
		super(cause);
	}

	public BadQoS(String reason)
	{
		this.reason = reason;
	}

	public BadQoS(String reason, Throwable cause)
	{
		super(cause);
		this.reason = reason;
	}

	public String ice_name()
	{
		return "IceStorm::BadQoS";
	}

	public void __write(BasicStream __os)
	{
		__os.writeString("::IceStorm::BadQoS");
		__os.startWriteSlice();
		__os.writeString(reason);
		__os.endWriteSlice();
	}

	public void __read(BasicStream __is, boolean __rid)
	{
		if (__rid)
			__is.readString();
		__is.startReadSlice();
		reason = __is.readString();
		__is.endReadSlice();
	}

	public void __write(OutputStream __outS)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "exception IceStorm::BadQoS was not generated with stream support";
		throw ex;
	}

	public void __read(InputStream __inS, boolean __rid)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "exception IceStorm::BadQoS was not generated with stream support";
		throw ex;
	}
}
