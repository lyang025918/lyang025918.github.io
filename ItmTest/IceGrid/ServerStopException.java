// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ServerStopException.java

package IceGrid;

import Ice.*;
import IceInternal.BasicStream;

public class ServerStopException extends UserException
{

	public String id;
	public String reason;

	public ServerStopException()
	{
	}

	public ServerStopException(Throwable cause)
	{
		super(cause);
	}

	public ServerStopException(String id, String reason)
	{
		this.id = id;
		this.reason = reason;
	}

	public ServerStopException(String id, String reason, Throwable cause)
	{
		super(cause);
		this.id = id;
		this.reason = reason;
	}

	public String ice_name()
	{
		return "IceGrid::ServerStopException";
	}

	public void __write(BasicStream __os)
	{
		__os.writeString("::IceGrid::ServerStopException");
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
		ex.reason = "exception IceGrid::ServerStopException was not generated with stream support";
		throw ex;
	}

	public void __read(InputStream __inS, boolean __rid)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "exception IceGrid::ServerStopException was not generated with stream support";
		throw ex;
	}
}
