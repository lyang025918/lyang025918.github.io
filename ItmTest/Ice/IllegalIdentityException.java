// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IllegalIdentityException.java

package Ice;


// Referenced classes of package Ice:
//			LocalException, Identity

public class IllegalIdentityException extends LocalException
{

	public Identity id;

	public IllegalIdentityException()
	{
	}

	public IllegalIdentityException(Throwable cause)
	{
		super(cause);
	}

	public IllegalIdentityException(Identity id)
	{
		this.id = id;
	}

	public IllegalIdentityException(Identity id, Throwable cause)
	{
		super(cause);
		this.id = id;
	}

	public String ice_name()
	{
		return "Ice::IllegalIdentityException";
	}
}
