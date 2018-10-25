// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AccessDeniedException.java

package IceGrid;

import Ice.*;
import IceInternal.BasicStream;

public class AccessDeniedException extends UserException
{

	public String lockUserId;

	public AccessDeniedException()
	{
	}

	public AccessDeniedException(Throwable cause)
	{
		super(cause);
	}

	public AccessDeniedException(String lockUserId)
	{
		this.lockUserId = lockUserId;
	}

	public AccessDeniedException(String lockUserId, Throwable cause)
	{
		super(cause);
		this.lockUserId = lockUserId;
	}

	public String ice_name()
	{
		return "IceGrid::AccessDeniedException";
	}

	public void __write(BasicStream __os)
	{
		__os.writeString("::IceGrid::AccessDeniedException");
		__os.startWriteSlice();
		__os.writeString(lockUserId);
		__os.endWriteSlice();
	}

	public void __read(BasicStream __is, boolean __rid)
	{
		if (__rid)
			__is.readString();
		__is.startReadSlice();
		lockUserId = __is.readString();
		__is.endReadSlice();
	}

	public void __write(OutputStream __outS)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "exception IceGrid::AccessDeniedException was not generated with stream support";
		throw ex;
	}

	public void __read(InputStream __inS, boolean __rid)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "exception IceGrid::AccessDeniedException was not generated with stream support";
		throw ex;
	}
}
