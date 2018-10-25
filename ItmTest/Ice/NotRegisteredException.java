// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NotRegisteredException.java

package Ice;


// Referenced classes of package Ice:
//			LocalException

public class NotRegisteredException extends LocalException
{

	public String kindOfObject;
	public String id;

	public NotRegisteredException()
	{
	}

	public NotRegisteredException(Throwable cause)
	{
		super(cause);
	}

	public NotRegisteredException(String kindOfObject, String id)
	{
		this.kindOfObject = kindOfObject;
		this.id = id;
	}

	public NotRegisteredException(String kindOfObject, String id, Throwable cause)
	{
		super(cause);
		this.kindOfObject = kindOfObject;
		this.id = id;
	}

	public String ice_name()
	{
		return "Ice::NotRegisteredException";
	}
}
