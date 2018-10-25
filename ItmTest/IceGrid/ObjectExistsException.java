// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ObjectExistsException.java

package IceGrid;

import Ice.*;
import IceInternal.BasicStream;

public class ObjectExistsException extends UserException
{

	public Identity id;

	public ObjectExistsException()
	{
	}

	public ObjectExistsException(Throwable cause)
	{
		super(cause);
	}

	public ObjectExistsException(Identity id)
	{
		this.id = id;
	}

	public ObjectExistsException(Identity id, Throwable cause)
	{
		super(cause);
		this.id = id;
	}

	public String ice_name()
	{
		return "IceGrid::ObjectExistsException";
	}

	public void __write(BasicStream __os)
	{
		__os.writeString("::IceGrid::ObjectExistsException");
		__os.startWriteSlice();
		id.__write(__os);
		__os.endWriteSlice();
	}

	public void __read(BasicStream __is, boolean __rid)
	{
		if (__rid)
			__is.readString();
		__is.startReadSlice();
		id = new Identity();
		id.__read(__is);
		__is.endReadSlice();
	}

	public void __write(OutputStream __outS)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "exception IceGrid::ObjectExistsException was not generated with stream support";
		throw ex;
	}

	public void __read(InputStream __inS, boolean __rid)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "exception IceGrid::ObjectExistsException was not generated with stream support";
		throw ex;
	}
}
