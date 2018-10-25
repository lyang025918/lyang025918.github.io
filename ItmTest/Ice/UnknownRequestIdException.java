// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   UnknownRequestIdException.java

package Ice;


// Referenced classes of package Ice:
//			ProtocolException

public class UnknownRequestIdException extends ProtocolException
{

	public UnknownRequestIdException()
	{
	}

	public UnknownRequestIdException(Throwable cause)
	{
		super(cause);
	}

	public UnknownRequestIdException(String reason)
	{
		super(reason);
	}

	public UnknownRequestIdException(String reason, Throwable cause)
	{
		super(reason, cause);
	}

	public String ice_name()
	{
		return "Ice::UnknownRequestIdException";
	}
}
