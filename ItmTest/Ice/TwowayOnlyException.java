// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TwowayOnlyException.java

package Ice;


// Referenced classes of package Ice:
//			LocalException

public class TwowayOnlyException extends LocalException
{

	public String operation;

	public TwowayOnlyException()
	{
	}

	public TwowayOnlyException(Throwable cause)
	{
		super(cause);
	}

	public TwowayOnlyException(String operation)
	{
		this.operation = operation;
	}

	public TwowayOnlyException(String operation, Throwable cause)
	{
		super(cause);
		this.operation = operation;
	}

	public String ice_name()
	{
		return "Ice::TwowayOnlyException";
	}
}
