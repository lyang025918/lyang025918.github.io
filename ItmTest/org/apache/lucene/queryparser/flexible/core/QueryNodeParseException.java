// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   QueryNodeParseException.java

package org.apache.lucene.queryparser.flexible.core;

import org.apache.lucene.queryparser.flexible.core.messages.QueryParserMessages;
import org.apache.lucene.queryparser.flexible.messages.Message;
import org.apache.lucene.queryparser.flexible.messages.MessageImpl;

// Referenced classes of package org.apache.lucene.queryparser.flexible.core:
//			QueryNodeException

public class QueryNodeParseException extends QueryNodeException
{

	private CharSequence query;
	private int beginColumn;
	private int beginLine;
	private String errorToken;

	public QueryNodeParseException(Message message)
	{
		super(message);
		beginColumn = -1;
		beginLine = -1;
		errorToken = "";
	}

	public QueryNodeParseException(Throwable throwable)
	{
		super(throwable);
		beginColumn = -1;
		beginLine = -1;
		errorToken = "";
	}

	public QueryNodeParseException(Message message, Throwable throwable)
	{
		super(message, throwable);
		beginColumn = -1;
		beginLine = -1;
		errorToken = "";
	}

	public void setQuery(CharSequence query)
	{
		this.query = query;
		message = new MessageImpl(QueryParserMessages.INVALID_SYNTAX_CANNOT_PARSE, new Object[] {
			query, ""
		});
	}

	public CharSequence getQuery()
	{
		return query;
	}

	protected void setErrorToken(String errorToken)
	{
		this.errorToken = errorToken;
	}

	public String getErrorToken()
	{
		return errorToken;
	}

	public void setNonLocalizedMessage(Message message)
	{
		this.message = message;
	}

	public int getBeginLine()
	{
		return beginLine;
	}

	public int getBeginColumn()
	{
		return beginColumn;
	}

	protected void setBeginLine(int beginLine)
	{
		this.beginLine = beginLine;
	}

	protected void setBeginColumn(int beginColumn)
	{
		this.beginColumn = beginColumn;
	}
}
