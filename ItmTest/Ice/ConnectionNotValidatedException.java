// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ConnectionNotValidatedException.java

package Ice;


// Referenced classes of package Ice:
//			ProtocolException

public class ConnectionNotValidatedException extends ProtocolException
{

	public ConnectionNotValidatedException()
	{
	}

	public ConnectionNotValidatedException(Throwable cause)
	{
		super(cause);
	}

	public ConnectionNotValidatedException(String reason)
	{
		super(reason);
	}

	public ConnectionNotValidatedException(String reason, Throwable cause)
	{
		super(reason, cause);
	}

	public String ice_name()
	{
		return "Ice::ConnectionNotValidatedException";
	}
}
