// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MemoryLimitException.java

package Ice;


// Referenced classes of package Ice:
//			MarshalException

public class MemoryLimitException extends MarshalException
{

	public MemoryLimitException()
	{
	}

	public MemoryLimitException(Throwable cause)
	{
		super(cause);
	}

	public MemoryLimitException(String reason)
	{
		super(reason);
	}

	public MemoryLimitException(String reason, Throwable cause)
	{
		super(reason, cause);
	}

	public String ice_name()
	{
		return "Ice::MemoryLimitException";
	}
}
