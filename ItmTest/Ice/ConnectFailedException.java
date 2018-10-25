// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ConnectFailedException.java

package Ice;


// Referenced classes of package Ice:
//			SocketException

public class ConnectFailedException extends SocketException
{

	public ConnectFailedException()
	{
	}

	public ConnectFailedException(Throwable cause)
	{
		super(cause);
	}

	public ConnectFailedException(int error)
	{
		super(error);
	}

	public ConnectFailedException(int error, Throwable cause)
	{
		super(error, cause);
	}

	public String ice_name()
	{
		return "Ice::ConnectFailedException";
	}
}
