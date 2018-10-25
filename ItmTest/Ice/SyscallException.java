// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SyscallException.java

package Ice;


// Referenced classes of package Ice:
//			LocalException

public class SyscallException extends LocalException
{

	public int error;

	public SyscallException()
	{
	}

	public SyscallException(Throwable cause)
	{
		super(cause);
	}

	public SyscallException(int error)
	{
		this.error = error;
	}

	public SyscallException(int error, Throwable cause)
	{
		super(cause);
		this.error = error;
	}

	public String ice_name()
	{
		return "Ice::SyscallException";
	}
}
