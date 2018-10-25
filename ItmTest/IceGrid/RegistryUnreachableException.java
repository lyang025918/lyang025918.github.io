// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RegistryUnreachableException.java

package IceGrid;

import Ice.*;
import IceInternal.BasicStream;

public class RegistryUnreachableException extends UserException
{

	public String name;
	public String reason;

	public RegistryUnreachableException()
	{
	}

	public RegistryUnreachableException(Throwable cause)
	{
		super(cause);
	}

	public RegistryUnreachableException(String name, String reason)
	{
		this.name = name;
		this.reason = reason;
	}

	public RegistryUnreachableException(String name, String reason, Throwable cause)
	{
		super(cause);
		this.name = name;
		this.reason = reason;
	}

	public String ice_name()
	{
		return "IceGrid::RegistryUnreachableException";
	}

	public void __write(BasicStream __os)
	{
		__os.writeString("::IceGrid::RegistryUnreachableException");
		__os.startWriteSlice();
		__os.writeString(name);
		__os.writeString(reason);
		__os.endWriteSlice();
	}

	public void __read(BasicStream __is, boolean __rid)
	{
		if (__rid)
			__is.readString();
		__is.startReadSlice();
		name = __is.readString();
		reason = __is.readString();
		__is.endReadSlice();
	}

	public void __write(OutputStream __outS)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "exception IceGrid::RegistryUnreachableException was not generated with stream support";
		throw ex;
	}

	public void __read(InputStream __inS, boolean __rid)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "exception IceGrid::RegistryUnreachableException was not generated with stream support";
		throw ex;
	}
}
