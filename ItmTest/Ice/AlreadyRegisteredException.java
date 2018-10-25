// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AlreadyRegisteredException.java

package Ice;


// Referenced classes of package Ice:
//			LocalException

public class AlreadyRegisteredException extends LocalException
{

	public String kindOfObject;
	public String id;

	public AlreadyRegisteredException()
	{
	}

	public AlreadyRegisteredException(Throwable cause)
	{
		super(cause);
	}

	public AlreadyRegisteredException(String kindOfObject, String id)
	{
		this.kindOfObject = kindOfObject;
		this.id = id;
	}

	public AlreadyRegisteredException(String kindOfObject, String id, Throwable cause)
	{
		super(cause);
		this.kindOfObject = kindOfObject;
		this.id = id;
	}

	public String ice_name()
	{
		return "Ice::AlreadyRegisteredException";
	}
}
