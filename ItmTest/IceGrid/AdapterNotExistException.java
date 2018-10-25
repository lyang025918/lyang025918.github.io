// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AdapterNotExistException.java

package IceGrid;

import Ice.*;
import IceInternal.BasicStream;

public class AdapterNotExistException extends UserException
{

	public String id;

	public AdapterNotExistException()
	{
	}

	public AdapterNotExistException(Throwable cause)
	{
		super(cause);
	}

	public AdapterNotExistException(String id)
	{
		this.id = id;
	}

	public AdapterNotExistException(String id, Throwable cause)
	{
		super(cause);
		this.id = id;
	}

	public String ice_name()
	{
		return "IceGrid::AdapterNotExistException";
	}

	public void __write(BasicStream __os)
	{
		__os.writeString("::IceGrid::AdapterNotExistException");
		__os.startWriteSlice();
		__os.writeString(id);
		__os.endWriteSlice();
	}

	public void __read(BasicStream __is, boolean __rid)
	{
		if (__rid)
			__is.readString();
		__is.startReadSlice();
		id = __is.readString();
		__is.endReadSlice();
	}

	public void __write(OutputStream __outS)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "exception IceGrid::AdapterNotExistException was not generated with stream support";
		throw ex;
	}

	public void __read(InputStream __inS, boolean __rid)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "exception IceGrid::AdapterNotExistException was not generated with stream support";
		throw ex;
	}
}
