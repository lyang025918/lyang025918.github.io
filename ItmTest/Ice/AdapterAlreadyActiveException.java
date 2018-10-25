// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AdapterAlreadyActiveException.java

package Ice;

import IceInternal.BasicStream;

// Referenced classes of package Ice:
//			UserException, MarshalException, OutputStream, InputStream

public class AdapterAlreadyActiveException extends UserException
{

	public AdapterAlreadyActiveException()
	{
	}

	public String ice_name()
	{
		return "Ice::AdapterAlreadyActiveException";
	}

	public void __write(BasicStream __os)
	{
		__os.writeString("::Ice::AdapterAlreadyActiveException");
		__os.startWriteSlice();
		__os.endWriteSlice();
	}

	public void __read(BasicStream __is, boolean __rid)
	{
		if (__rid)
			__is.readString();
		__is.startReadSlice();
		__is.endReadSlice();
	}

	public void __write(OutputStream __outS)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "exception Ice::AdapterAlreadyActiveException was not generated with stream support";
		throw ex;
	}

	public void __read(InputStream __inS, boolean __rid)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "exception Ice::AdapterAlreadyActiveException was not generated with stream support";
		throw ex;
	}
}
