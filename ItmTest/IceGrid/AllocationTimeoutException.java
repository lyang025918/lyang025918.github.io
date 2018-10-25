// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AllocationTimeoutException.java

package IceGrid;

import Ice.*;
import IceInternal.BasicStream;

// Referenced classes of package IceGrid:
//			AllocationException

public class AllocationTimeoutException extends AllocationException
{

	public AllocationTimeoutException()
	{
	}

	public AllocationTimeoutException(Throwable cause)
	{
		super(cause);
	}

	public AllocationTimeoutException(String reason)
	{
		super(reason);
	}

	public AllocationTimeoutException(String reason, Throwable cause)
	{
		super(reason, cause);
	}

	public String ice_name()
	{
		return "IceGrid::AllocationTimeoutException";
	}

	public void __write(BasicStream __os)
	{
		__os.writeString("::IceGrid::AllocationTimeoutException");
		__os.startWriteSlice();
		__os.endWriteSlice();
		super.__write(__os);
	}

	public void __read(BasicStream __is, boolean __rid)
	{
		if (__rid)
			__is.readString();
		__is.startReadSlice();
		__is.endReadSlice();
		super.__read(__is, true);
	}

	public void __write(OutputStream __outS)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "exception IceGrid::AllocationTimeoutException was not generated with stream support";
		throw ex;
	}

	public void __read(InputStream __inS, boolean __rid)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "exception IceGrid::AllocationTimeoutException was not generated with stream support";
		throw ex;
	}
}
