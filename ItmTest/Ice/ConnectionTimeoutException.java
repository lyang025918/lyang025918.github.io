// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ConnectionTimeoutException.java

package Ice;


// Referenced classes of package Ice:
//			TimeoutException

public class ConnectionTimeoutException extends TimeoutException
{

	public ConnectionTimeoutException()
	{
	}

	public String ice_name()
	{
		return "Ice::ConnectionTimeoutException";
	}
}
