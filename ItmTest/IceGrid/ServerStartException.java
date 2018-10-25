// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ServerStartException.java

package IceGrid;

import Ice.*;
import IceInternal.BasicStream;

public class ServerStartException extends UserException
{

	public String id;
	public String reason;

	public ServerStartException()
	{
	}

	public ServerStartException(Throwable cause)
	{
		super(cause);
	}

	public ServerStartException(String id, String reason)
	{
		this.id = id;
		this.reason = reason;
	}

	public ServerStartException(String id, String reason, Throwable cause)
	{
		super(cause);
		this.id = id;
		this.reason = reason;
	}

	public String ice_name()
	{
		return "IceGrid::ServerStartException";
	}

	public void __write(BasicStream __os)
	{
		__os.writeString("::IceGrid::ServerStartException");
		__os.startWriteSlice();
		__os.writeString(id);
		__os.writeString(reason);
		__os.endWriteSlice();
	}

	public void __read(BasicStream __is, boolean __rid)
	{
		if (__rid)
			__is.readString();
		__is.startReadSlice();
		id = __is.readString();
		reason = __is.readString();
		__is.endReadSlice();
	}

	public void __write(OutputStream __outS)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "exception IceGrid::ServerStartException was not generated with stream support";
		throw ex;
	}

	public void __read(InputStream __inS, boolean __rid)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "exception IceGrid::ServerStartException was not generated with stream support";
		throw ex;
	}
}
