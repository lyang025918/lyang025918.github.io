// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PatchException.java

package IceGrid;

import Ice.*;
import IceInternal.BasicStream;

public class PatchException extends UserException
{

	public String reasons[];

	public PatchException()
	{
	}

	public PatchException(Throwable cause)
	{
		super(cause);
	}

	public PatchException(String reasons[])
	{
		this.reasons = reasons;
	}

	public PatchException(String reasons[], Throwable cause)
	{
		super(cause);
		this.reasons = reasons;
	}

	public String ice_name()
	{
		return "IceGrid::PatchException";
	}

	public void __write(BasicStream __os)
	{
		__os.writeString("::IceGrid::PatchException");
		__os.startWriteSlice();
		StringSeqHelper.write(__os, reasons);
		__os.endWriteSlice();
	}

	public void __read(BasicStream __is, boolean __rid)
	{
		if (__rid)
			__is.readString();
		__is.startReadSlice();
		reasons = StringSeqHelper.read(__is);
		__is.endReadSlice();
	}

	public void __write(OutputStream __outS)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "exception IceGrid::PatchException was not generated with stream support";
		throw ex;
	}

	public void __read(InputStream __inS, boolean __rid)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "exception IceGrid::PatchException was not generated with stream support";
		throw ex;
	}
}
