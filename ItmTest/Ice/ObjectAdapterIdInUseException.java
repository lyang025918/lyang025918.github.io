// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ObjectAdapterIdInUseException.java

package Ice;


// Referenced classes of package Ice:
//			LocalException

public class ObjectAdapterIdInUseException extends LocalException
{

	public String id;

	public ObjectAdapterIdInUseException()
	{
	}

	public ObjectAdapterIdInUseException(Throwable cause)
	{
		super(cause);
	}

	public ObjectAdapterIdInUseException(String id)
	{
		this.id = id;
	}

	public ObjectAdapterIdInUseException(String id, Throwable cause)
	{
		super(cause);
		this.id = id;
	}

	public String ice_name()
	{
		return "Ice::ObjectAdapterIdInUseException";
	}
}
