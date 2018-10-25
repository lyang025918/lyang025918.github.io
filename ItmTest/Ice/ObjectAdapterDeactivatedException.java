// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ObjectAdapterDeactivatedException.java

package Ice;


// Referenced classes of package Ice:
//			LocalException

public class ObjectAdapterDeactivatedException extends LocalException
{

	public String name;

	public ObjectAdapterDeactivatedException()
	{
	}

	public ObjectAdapterDeactivatedException(Throwable cause)
	{
		super(cause);
	}

	public ObjectAdapterDeactivatedException(String name)
	{
		this.name = name;
	}

	public ObjectAdapterDeactivatedException(String name, Throwable cause)
	{
		super(cause);
		this.name = name;
	}

	public String ice_name()
	{
		return "Ice::ObjectAdapterDeactivatedException";
	}
}
