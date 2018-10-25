// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ObjectNotExistException.java

package Ice;


// Referenced classes of package Ice:
//			RequestFailedException, Identity

public class ObjectNotExistException extends RequestFailedException
{

	public ObjectNotExistException()
	{
	}

	public ObjectNotExistException(Throwable cause)
	{
		super(cause);
	}

	public ObjectNotExistException(Identity id, String facet, String operation)
	{
		super(id, facet, operation);
	}

	public ObjectNotExistException(Identity id, String facet, String operation, Throwable cause)
	{
		super(id, facet, operation, cause);
	}

	public String ice_name()
	{
		return "Ice::ObjectNotExistException";
	}
}
