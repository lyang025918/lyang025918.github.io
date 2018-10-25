// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BadMagicException.java

package Ice;


// Referenced classes of package Ice:
//			ProtocolException

public class BadMagicException extends ProtocolException
{

	public byte badMagic[];

	public BadMagicException()
	{
	}

	public BadMagicException(Throwable cause)
	{
		super(cause);
	}

	public BadMagicException(String reason, byte badMagic[])
	{
		super(reason);
		this.badMagic = badMagic;
	}

	public BadMagicException(String reason, byte badMagic[], Throwable cause)
	{
		super(reason, cause);
		this.badMagic = badMagic;
	}

	public String ice_name()
	{
		return "Ice::BadMagicException";
	}
}
