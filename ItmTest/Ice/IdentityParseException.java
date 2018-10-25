// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IdentityParseException.java

package Ice;


// Referenced classes of package Ice:
//			LocalException

public class IdentityParseException extends LocalException
{

	public String str;

	public IdentityParseException()
	{
	}

	public IdentityParseException(Throwable cause)
	{
		super(cause);
	}

	public IdentityParseException(String str)
	{
		this.str = str;
	}

	public IdentityParseException(String str, Throwable cause)
	{
		super(cause);
		this.str = str;
	}

	public String ice_name()
	{
		return "Ice::IdentityParseException";
	}
}
