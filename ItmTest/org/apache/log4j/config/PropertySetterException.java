// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PropertySetterException.java

package org.apache.log4j.config;


public class PropertySetterException extends Exception
{

	private static final long serialVersionUID = 0xed3a8cf9a3762f2bL;
	protected Throwable rootCause;

	public PropertySetterException(String msg)
	{
		super(msg);
	}

	public PropertySetterException(Throwable rootCause)
	{
		this.rootCause = rootCause;
	}

	public String getMessage()
	{
		String msg = super.getMessage();
		if (msg == null && rootCause != null)
			msg = rootCause.getMessage();
		return msg;
	}
}
