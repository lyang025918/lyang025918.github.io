// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ExtensionQuery.java

package org.apache.lucene.queryparser.ext;

import org.apache.lucene.queryparser.classic.QueryParser;

public class ExtensionQuery
{

	private final String field;
	private final String rawQueryString;
	private final QueryParser topLevelParser;

	public ExtensionQuery(QueryParser topLevelParser, String field, String rawQueryString)
	{
		this.field = field;
		this.rawQueryString = rawQueryString;
		this.topLevelParser = topLevelParser;
	}

	public String getField()
	{
		return field;
	}

	public String getRawQueryString()
	{
		return rawQueryString;
	}

	public QueryParser getTopLevelParser()
	{
		return topLevelParser;
	}
}
