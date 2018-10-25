// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   UnmarshalOutOfBoundsException.java

package Ice;


// Referenced classes of package Ice:
//			MarshalException

public class UnmarshalOutOfBoundsException extends MarshalException
{

	public UnmarshalOutOfBoundsException()
	{
	}

	public UnmarshalOutOfBoundsException(Throwable cause)
	{
		super(cause);
	}

	public UnmarshalOutOfBoundsException(String reason)
	{
		super(reason);
	}

	public UnmarshalOutOfBoundsException(String reason, Throwable cause)
	{
		super(reason, cause);
	}

	public String ice_name()
	{
		return "Ice::UnmarshalOutOfBoundsException";
	}
}
