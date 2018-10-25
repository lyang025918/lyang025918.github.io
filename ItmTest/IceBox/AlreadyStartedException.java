// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AlreadyStartedException.java

package IceBox;

import Ice.*;
import IceInternal.BasicStream;

public class AlreadyStartedException extends UserException
{

	public AlreadyStartedException()
	{
	}

	public String ice_name()
	{
		return "IceBox::AlreadyStartedException";
	}

	public void __write(BasicStream __os)
	{
		__os.writeString("::IceBox::AlreadyStartedException");
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
		ex.reason = "exception IceBox::AlreadyStartedException was not generated with stream support";
		throw ex;
	}

	public void __read(InputStream __inS, boolean __rid)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "exception IceBox::AlreadyStartedException was not generated with stream support";
		throw ex;
	}
}
