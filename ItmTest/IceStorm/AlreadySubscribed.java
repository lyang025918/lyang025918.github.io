// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AlreadySubscribed.java

package IceStorm;

import Ice.*;
import IceInternal.BasicStream;

public class AlreadySubscribed extends UserException
{

	public AlreadySubscribed()
	{
	}

	public String ice_name()
	{
		return "IceStorm::AlreadySubscribed";
	}

	public void __write(BasicStream __os)
	{
		__os.writeString("::IceStorm::AlreadySubscribed");
		__os.startWriteSlice();
		__os.endWriteSlice();
	}

	public void __read(BasicStream __is, boolean __rid)
	{
		if (__rid)
			__is.readString();
		__is.startReadSlice();
		__is.endReadSlice();
	}

	public void __write(OutputStream __outS)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "exception IceStorm::AlreadySubscribed was not generated with stream support";
		throw ex;
	}

	public void __read(InputStream __inS, boolean __rid)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "exception IceStorm::AlreadySubscribed was not generated with stream support";
		throw ex;
	}
}
