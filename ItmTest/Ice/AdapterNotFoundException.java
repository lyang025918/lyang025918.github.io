// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AdapterNotFoundException.java

package Ice;

import IceInternal.BasicStream;

// Referenced classes of package Ice:
//			UserException, MarshalException, OutputStream, InputStream

public class AdapterNotFoundException extends UserException
{

	public AdapterNotFoundException()
	{
	}

	public String ice_name()
	{
		return "Ice::AdapterNotFoundException";
	}

	public void __write(BasicStream __os)
	{
		__os.writeString("::Ice::AdapterNotFoundException");
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
		ex.reason = "exception Ice::AdapterNotFoundException was not generated with stream support";
		throw ex;
	}

	public void __read(InputStream __inS, boolean __rid)
	{
		MarshalException ex = new MarshalException();
		ex.reason = "exception Ice::AdapterNotFoundException was not generated with stream support";
		throw ex;
	}
}
