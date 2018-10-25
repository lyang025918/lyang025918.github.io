// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   StringConversionException.java

package Ice;


// Referenced classes of package Ice:
//			MarshalException

public class StringConversionException extends MarshalException
{

	public StringConversionException()
	{
	}

	public StringConversionException(Throwable cause)
	{
		super(cause);
	}

	public StringConversionException(String reason)
	{
		super(reason);
	}

	public StringConversionException(String reason, Throwable cause)
	{
		super(reason, cause);
	}

	public String ice_name()
	{
		return "Ice::StringConversionException";
	}
}
