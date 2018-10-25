// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FileException.java

package Ice;


// Referenced classes of package Ice:
//			SyscallException

public class FileException extends SyscallException
{

	public String path;

	public FileException()
	{
	}

	public FileException(Throwable cause)
	{
		super(cause);
	}

	public FileException(int error, String path)
	{
		super(error);
		this.path = path;
	}

	public FileException(int error, String path, Throwable cause)
	{
		super(error, cause);
		this.path = path;
	}

	public String ice_name()
	{
		return "Ice::FileException";
	}
}
