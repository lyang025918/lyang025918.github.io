// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ParserException.java

package org.apache.lucene.queryparser.xml;


public class ParserException extends Exception
{

	public ParserException()
	{
	}

	public ParserException(String message)
	{
		super(message);
	}

	public ParserException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public ParserException(Throwable cause)
	{
		super(cause);
	}
}
