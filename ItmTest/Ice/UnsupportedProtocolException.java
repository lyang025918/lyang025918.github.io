// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   UnsupportedProtocolException.java

package Ice;


// Referenced classes of package Ice:
//			ProtocolException

public class UnsupportedProtocolException extends ProtocolException
{

	public int badMajor;
	public int badMinor;
	public int major;
	public int minor;

	public UnsupportedProtocolException()
	{
	}

	public UnsupportedProtocolException(Throwable cause)
	{
		super(cause);
	}

	public UnsupportedProtocolException(String reason, int badMajor, int badMinor, int major, int minor)
	{
		super(reason);
		this.badMajor = badMajor;
		this.badMinor = badMinor;
		this.major = major;
		this.minor = minor;
	}

	public UnsupportedProtocolException(String reason, int badMajor, int badMinor, int major, int minor, Throwable cause)
	{
		super(reason, cause);
		this.badMajor = badMajor;
		this.badMinor = badMinor;
		this.major = major;
		this.minor = minor;
	}

	public String ice_name()
	{
		return "Ice::UnsupportedProtocolException";
	}
}
