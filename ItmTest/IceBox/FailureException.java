// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FailureException.java

package IceBox;

import Ice.LocalException;

public class FailureException extends LocalException
{

	public String reason;

	public FailureException()
	{
	}

	public FailureException(Throwable cause)
	{
		super(cause);
	}

	public FailureException(String reason)
	{
		this.reason = reason;
	}

	public FailureException(String reason, Throwable cause)
	{
		super(cause);
		this.reason = reason;
	}

	public String ice_name()
	{
		return "IceBox::FailureException";
	}
}
