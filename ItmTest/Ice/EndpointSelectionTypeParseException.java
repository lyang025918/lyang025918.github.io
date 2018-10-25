// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EndpointSelectionTypeParseException.java

package Ice;


// Referenced classes of package Ice:
//			LocalException

public class EndpointSelectionTypeParseException extends LocalException
{

	public String str;

	public EndpointSelectionTypeParseException()
	{
	}

	public EndpointSelectionTypeParseException(Throwable cause)
	{
		super(cause);
	}

	public EndpointSelectionTypeParseException(String str)
	{
		this.str = str;
	}

	public EndpointSelectionTypeParseException(String str, Throwable cause)
	{
		super(cause);
		this.str = str;
	}

	public String ice_name()
	{
		return "Ice::EndpointSelectionTypeParseException";
	}
}
