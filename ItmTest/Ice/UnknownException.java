// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   UnknownException.java

package Ice;


// Referenced classes of package Ice:
//			LocalException

public class UnknownException extends LocalException
{

	public String unknown;

	public UnknownException()
	{
	}

	public UnknownException(Throwable cause)
	{
		super(cause);
	}

	public UnknownException(String unknown)
	{
		this.unknown = unknown;
	}

	public UnknownException(String unknown, Throwable cause)
	{
		super(cause);
		this.unknown = unknown;
	}

	public String ice_name()
	{
		return "Ice::UnknownException";
	}
}
