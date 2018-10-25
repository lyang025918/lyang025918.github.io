// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RequestFailedException.java

package Ice;


// Referenced classes of package Ice:
//			LocalException, Identity

public class RequestFailedException extends LocalException
{

	public Identity id;
	public String facet;
	public String operation;

	public RequestFailedException()
	{
	}

	public RequestFailedException(Throwable cause)
	{
		super(cause);
	}

	public RequestFailedException(Identity id, String facet, String operation)
	{
		this.id = id;
		this.facet = facet;
		this.operation = operation;
	}

	public RequestFailedException(Identity id, String facet, String operation, Throwable cause)
	{
		super(cause);
		this.id = id;
		this.facet = facet;
		this.operation = operation;
	}

	public String ice_name()
	{
		return "Ice::RequestFailedException";
	}
}
