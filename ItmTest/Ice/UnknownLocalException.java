// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   UnknownLocalException.java

package Ice;


// Referenced classes of package Ice:
//			UnknownException

public class UnknownLocalException extends UnknownException
{

	public UnknownLocalException()
	{
	}

	public UnknownLocalException(Throwable cause)
	{
		super(cause);
	}

	public UnknownLocalException(String unknown)
	{
		super(unknown);
	}

	public UnknownLocalException(String unknown, Throwable cause)
	{
		super(unknown, cause);
	}

	public String ice_name()
	{
		return "Ice::UnknownLocalException";
	}
}
