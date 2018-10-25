// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SocketException.java

package Ice;


// Referenced classes of package Ice:
//			SyscallException

public class SocketException extends SyscallException
{

	public SocketException()
	{
	}

	public SocketException(Throwable cause)
	{
		super(cause);
	}

	public SocketException(int error)
	{
		super(error);
	}

	public SocketException(int error, Throwable cause)
	{
		super(error, cause);
	}

	public String ice_name()
	{
		return "Ice::SocketException";
	}
}
