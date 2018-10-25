// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ObserverAlreadyRegisteredException.java

package IceGrid;

import Ice.*;
import IceInternal.BasicStream;

public class ObserverAlreadyRegisteredException extends UserException
{

	public Identity id;

	public ObserverAlreadyRegisteredException()
	{
	}

	public ObserverAlreadyRegisteredException(Throwable cause)
	{
		super(cause);
	}

	public ObserverAlreadyRegisteredException(Identity id)
	{
		this.id = id;
	}

	public ObserverAlreadyRegisteredException(Identity id, Throwable cause)
	{
		super(cause);
		this.id = id;
	}

	public String ice_name()
	{
		return "IceGrid::ObserverAlreadyRegisteredException";
	}

	public void __write(BasicStream __os)
	{
		__os.writeString("::IceGrid::ObserverAlreadyRegisteredException");
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
		ex.reason = "exception IceGrid::ObserverAlreadyRegisteredException was not generated with stream support";
		throw ex;
	}

	public void __read(InputStream __inS, boolean __rid)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "exception IceGrid::ObserverAlreadyRegisteredException was not generated with stream support";
		throw ex;
	}
}
