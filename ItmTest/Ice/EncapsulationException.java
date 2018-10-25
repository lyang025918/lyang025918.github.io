// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EncapsulationException.java

package Ice;


// Referenced classes of package Ice:
//			MarshalException

public class EncapsulationException extends MarshalException
{

	public EncapsulationException()
	{
	}

	public EncapsulationException(Throwable cause)
	{
		super(cause);
	}

	public EncapsulationException(String reason)
	{
		super(reason);
	}

	public EncapsulationException(String reason, Throwable cause)
	{
		super(reason, cause);
	}

	public String ice_name()
	{
		return "Ice::EncapsulationException";
	}
}
