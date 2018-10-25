// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   QueryParserHelper.java

package org.apache.lucene.queryparser.flexible.core;

import org.apache.lucene.queryparser.flexible.core.builders.QueryBuilder;
import org.apache.lucene.queryparser.flexible.core.config.QueryConfigHandler;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.core.parser.SyntaxParser;
import org.apache.lucene.queryparser.flexible.core.processors.QueryNodeProcessor;

// Referenced classes of package org.apache.lucene.queryparser.flexible.core:
//			QueryNodeException

public class QueryParserHelper
{

	private QueryNodeProcessor processor;
	private SyntaxParser syntaxParser;
	private QueryBuilder builder;
	private QueryConfigHandler config;

	public QueryParserHelper(QueryConfigHandler queryConfigHandler, SyntaxParser syntaxParser, QueryNodeProcessor processor, QueryBuilder builder)
	{
		this.syntaxParser = syntaxParser;
		config = queryConfigHandler;
		this.processor = processor;
		this.builder = builder;
		if (processor != null)
			processor.setQueryConfigHandler(queryConfigHandler);
	}

	public QueryNodeProcessor getQueryNodeProcessor()
	{
		return processor;
	}

	public void setQueryNodeProcessor(QueryNodeProcessor processor)
	{
		this.processor = processor;
		this.processor.setQueryConfigHandler(getQueryConfigHandler());
	}

	public void setSyntaxParser(SyntaxParser syntaxParser)
	{
		if (syntaxParser == null)
		{
			throw new IllegalArgumentException("textParser should not be null!");
		} else
		{
			this.syntaxParser = syntaxParser;
			return;
		}
	}

	public void setQueryBuilder(QueryBuilder queryBuilder)
	{
		if (queryBuilder == null)
		{
			throw new IllegalArgumentException("queryBuilder should not be null!");
		} else
		{
			builder = queryBuilder;
			return;
		}
	}

	public QueryConfigHandler getQueryConfigHandler()
	{
		return config;
	}

	public QueryBuilder getQueryBuilder()
	{
		return builder;
	}

	public SyntaxParser getSyntaxParser()
	{
		return syntaxParser;
	}

	public void setQueryConfigHandler(QueryConfigHandler config)
	{
		this.config = config;
		QueryNodeProcessor processor = getQueryNodeProcessor();
		if (processor != null)
			processor.setQueryConfigHandler(config);
	}

	public Object parse(String query, String defaultField)
		throws QueryNodeException
	{
		QueryNode queryTree = getSyntaxParser().parse(query, defaultField);
		QueryNodeProcessor processor = getQueryNodeProcessor();
		if (processor != null)
			queryTree = processor.process(queryTree);
		return getQueryBuilder().build(queryTree);
	}
}
