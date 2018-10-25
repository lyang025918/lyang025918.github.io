// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ParseException.java

package IceGrid;

import Ice.*;
import IceInternal.BasicStream;

public class ParseException extends UserException
{

	public String reason;

	public ParseException()
	{
	}

	public ParseException(Throwable cause)
	{
		super(cause);
	}

	public ParseException(String reason)
	{
		this.reason = reason;
	}

	public ParseException(String reason, Throwable cause)
	{
		super(cause);
		this.reason = reason;
	}

	public String ice_name()
	{
		return "IceGrid::ParseException";
	}

	public void __write(BasicStream __os)
	{
		__os.writeString("::IceGrid::ParseException");
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
		ex.reason = "exception IceGrid::ParseException was not generated with stream support";
		throw ex;
	}

	public void __read(InputStream __inS, boolean __rid)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "exception IceGrid::ParseException was not generated with stream support";
		throw ex;
	}
}
