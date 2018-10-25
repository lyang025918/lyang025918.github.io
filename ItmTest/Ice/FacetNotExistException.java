// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FacetNotExistException.java

package Ice;


// Referenced classes of package Ice:
//			RequestFailedException, Identity

public class FacetNotExistException extends RequestFailedException
{

	public FacetNotExistException()
	{
	}

	public FacetNotExistException(Throwable cause)
	{
		super(cause);
	}

	public FacetNotExistException(Identity id, String facet, String operation)
	{
		super(id, facet, operation);
	}

	public FacetNotExistException(Identity id, String facet, String operation, Throwable cause)
	{
		super(id, facet, operation, cause);
	}

	public String ice_name()
	{
		return "Ice::FacetNotExistException";
	}
}
