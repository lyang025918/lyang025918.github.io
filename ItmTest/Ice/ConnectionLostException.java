// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ConnectionLostException.java

package Ice;


// Referenced classes of package Ice:
//			SocketException

public class ConnectionLostException extends SocketException
{

	public ConnectionLostException()
	{
	}

	public ConnectionLostException(Throwable cause)
	{
		super(cause);
	}

	public ConnectionLostException(int error)
	{
		super(error);
	}

	public ConnectionLostException(int error, Throwable cause)
	{
		super(error, cause);
	}

	public String ice_name()
	{
		return "Ice::ConnectionLostException";
	}
}
