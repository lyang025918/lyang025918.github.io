// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NodeNotExistException.java

package IceGrid;

import Ice.*;
import IceInternal.BasicStream;

public class NodeNotExistException extends UserException
{

	public String name;

	public NodeNotExistException()
	{
	}

	public NodeNotExistException(Throwable cause)
	{
		super(cause);
	}

	public NodeNotExistException(String name)
	{
		this.name = name;
	}

	public NodeNotExistException(String name, Throwable cause)
	{
		super(cause);
		this.name = name;
	}

	public String ice_name()
	{
		return "IceGrid::NodeNotExistException";
	}

	public void __write(BasicStream __os)
	{
		__os.writeString("::IceGrid::NodeNotExistException");
		__os.startWriteSlice();
		__os.writeString(name);
		__os.endWriteSlice();
	}

	public void __read(BasicStream __is, boolean __rid)
	{
		if (__rid)
			__is.readString();
		__is.startReadSlice();
		name = __is.readString();
		__is.endReadSlice();
	}

	public void __write(OutputStream __outS)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "exception IceGrid::NodeNotExistException was not generated with stream support";
		throw ex;
	}

	public void __read(InputStream __inS, boolean __rid)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "exception IceGrid::NodeNotExistException was not generated with stream support";
		throw ex;
	}
}
