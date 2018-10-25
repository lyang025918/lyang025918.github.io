// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   UnexpectedObjectException.java

package Ice;


// Referenced classes of package Ice:
//			MarshalException

public class UnexpectedObjectException extends MarshalException
{

	public String type;
	public String expectedType;

	public UnexpectedObjectException()
	{
	}

	public UnexpectedObjectException(Throwable cause)
	{
		super(cause);
	}

	public UnexpectedObjectException(String reason, String type, String expectedType)
	{
		super(reason);
		this.type = type;
		this.expectedType = expectedType;
	}

	public UnexpectedObjectException(String reason, String type, String expectedType, Throwable cause)
	{
		super(reason, cause);
		this.type = type;
		this.expectedType = expectedType;
	}

	public String ice_name()
	{
		return "Ice::UnexpectedObjectException";
	}
}
