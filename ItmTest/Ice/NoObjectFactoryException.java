// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NoObjectFactoryException.java

package Ice;


// Referenced classes of package Ice:
//			MarshalException

public class NoObjectFactoryException extends MarshalException
{

	public String type;

	public NoObjectFactoryException()
	{
	}

	public NoObjectFactoryException(Throwable cause)
	{
		super(cause);
	}

	public NoObjectFactoryException(String reason, String type)
	{
		super(reason);
		this.type = type;
	}

	public NoObjectFactoryException(String reason, String type, Throwable cause)
	{
		super(reason, cause);
		this.type = type;
	}

	public String ice_name()
	{
		return "Ice::NoObjectFactoryException";
	}
}
