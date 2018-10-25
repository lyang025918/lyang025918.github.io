// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ObjectNotRegisteredException.java

package IceGrid;

import Ice.*;
import IceInternal.BasicStream;

public class ObjectNotRegisteredException extends UserException
{

	public Identity id;

	public ObjectNotRegisteredException()
	{
	}

	public ObjectNotRegisteredException(Throwable cause)
	{
		super(cause);
	}

	public ObjectNotRegisteredException(Identity id)
	{
		this.id = id;
	}

	public ObjectNotRegisteredException(Identity id, Throwable cause)
	{
		super(cause);
		this.id = id;
	}

	public String ice_name()
	{
		return "IceGrid::ObjectNotRegisteredException";
	}

	public void __write(BasicStream __os)
	{
		__os.writeString("::IceGrid::ObjectNotRegisteredException");
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
		ex.reason = "exception IceGrid::ObjectNotRegisteredException was not generated with stream support";
		throw ex;
	}

	public void __read(InputStream __inS, boolean __rid)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "exception IceGrid::ObjectNotRegisteredException was not generated with stream support";
		throw ex;
	}
}
