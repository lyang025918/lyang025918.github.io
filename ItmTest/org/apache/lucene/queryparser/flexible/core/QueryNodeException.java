// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   QueryNodeException.java

package org.apache.lucene.queryparser.flexible.core;

import java.util.Locale;
import org.apache.lucene.queryparser.flexible.core.messages.QueryParserMessages;
import org.apache.lucene.queryparser.flexible.messages.*;

public class QueryNodeException extends Exception
	implements NLSException
{

	protected Message message;

	public QueryNodeException(Message message)
	{
		super(message.getKey());
		this.message = new MessageImpl(QueryParserMessages.EMPTY_MESSAGE);
		this.message = message;
	}

	public QueryNodeException(Throwable throwable)
	{
		super(throwable);
		message = new MessageImpl(QueryParserMessages.EMPTY_MESSAGE);
	}

	public QueryNodeException(Message message, Throwable throwable)
	{
		super(message.getKey(), throwable);
		this.message = new MessageImpl(QueryParserMessages.EMPTY_MESSAGE);
		this.message = message;
	}

	public Message getMessageObject()
	{
		return message;
	}

	public String getMessage()
	{
		return getLocalizedMessage();
	}

	public String getLocalizedMessage()
	{
		return getLocalizedMessage(Locale.getDefault());
	}

	public String getLocalizedMessage(Locale locale)
	{
		return message.getLocalizedMessage(locale);
	}

	public String toString()
	{
		return (new StringBuilder()).append(message.getKey()).append(": ").append(getLocalizedMessage()).toString();
	}
}
