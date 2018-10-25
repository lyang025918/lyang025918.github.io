// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   InitializationException.java

package Ice;


// Referenced classes of package Ice:
//			LocalException

public class InitializationException extends LocalException
{

	public String reason;

	public InitializationException()
	{
	}

	public InitializationException(Throwable cause)
	{
		super(cause);
	}

	public InitializationException(String reason)
	{
		this.reason = reason;
	}

	public InitializationException(String reason, Throwable cause)
	{
		super(cause);
		this.reason = reason;
	}

	public String ice_name()
	{
		return "Ice::InitializationException";
	}
}
