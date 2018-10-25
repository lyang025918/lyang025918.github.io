// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ConnectionRefusedException.java

package Ice;


// Referenced classes of package Ice:
//			ConnectFailedException

public class ConnectionRefusedException extends ConnectFailedException
{

	public ConnectionRefusedException()
	{
	}

	public ConnectionRefusedException(Throwable cause)
	{
		super(cause);
	}

	public ConnectionRefusedException(int error)
	{
		super(error);
	}

	public ConnectionRefusedException(int error, Throwable cause)
	{
		super(error, cause);
	}

	public String ice_name()
	{
		return "Ice::ConnectionRefusedException";
	}
}
