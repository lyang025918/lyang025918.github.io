// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   QueryNodeError.java

package org.apache.lucene.queryparser.flexible.core;

import org.apache.lucene.queryparser.flexible.messages.Message;
import org.apache.lucene.queryparser.flexible.messages.NLSException;

public class QueryNodeError extends Error
	implements NLSException
{

	private Message message;

	public QueryNodeError(Message message)
	{
		super(message.getKey());
		this.message = message;
	}

	public QueryNodeError(Throwable throwable)
	{
		super(throwable);
	}

	public QueryNodeError(Message message, Throwable throwable)
	{
		super(message.getKey(), throwable);
		this.message = message;
	}

	public Message getMessageObject()
	{
		return message;
	}
}
