// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   QueryTreeBuilder.java

package org.apache.lucene.queryparser.flexible.core.builders;

import java.util.*;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.messages.QueryParserMessages;
import org.apache.lucene.queryparser.flexible.core.nodes.FieldableNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.messages.MessageImpl;
import org.apache.lucene.queryparser.flexible.standard.parser.EscapeQuerySyntaxImpl;

// Referenced classes of package org.apache.lucene.queryparser.flexible.core.builders:
//			QueryBuilder

public class QueryTreeBuilder
	implements QueryBuilder
{

	public static final String QUERY_TREE_BUILDER_TAGID = org/apache/lucene/queryparser/flexible/core/builders/QueryTreeBuilder.getName();
	private HashMap queryNodeBuilders;
	private HashMap fieldNameBuilders;

	public QueryTreeBuilder()
	{
	}

	public void setBuilder(CharSequence fieldName, QueryBuilder builder)
	{
		if (fieldNameBuilders == null)
			fieldNameBuilders = new HashMap();
		fieldNameBuilders.put(fieldName.toString(), builder);
	}

	public void setBuilder(Class queryNodeClass, QueryBuilder builder)
	{
		if (queryNodeBuilders == null)
			queryNodeBuilders = new HashMap();
		queryNodeBuilders.put(queryNodeClass, builder);
	}

	private void process(QueryNode node)
		throws QueryNodeException
	{
		if (node != null)
		{
			QueryBuilder builder = getBuilder(node);
			if (!(builder instanceof QueryTreeBuilder))
			{
				List children = node.getChildren();
				if (children != null)
				{
					QueryNode child;
					for (Iterator i$ = children.iterator(); i$.hasNext(); process(child))
						child = (QueryNode)i$.next();

				}
			}
			processNode(node, builder);
		}
	}

	private QueryBuilder getBuilder(QueryNode node)
	{
		QueryBuilder builder = null;
		if (fieldNameBuilders != null && (node instanceof FieldableNode))
		{
			CharSequence field = ((FieldableNode)node).getField();
			if (field != null)
				field = field.toString();
			builder = (QueryBuilder)fieldNameBuilders.get(field);
		}
		if (builder == null && queryNodeBuilders != null)
		{
			Class clazz = node.getClass();
			do
			{
				builder = getQueryBuilder(clazz);
				if (builder == null)
				{
					Class classes[] = node.getClass().getInterfaces();
					Class arr$[] = classes;
					int len$ = arr$.length;
					int i$ = 0;
					do
					{
						if (i$ >= len$)
							break;
						Class actualClass = arr$[i$];
						builder = getQueryBuilder(actualClass);
						if (builder != null)
							break;
						i$++;
					} while (true);
				}
			} while (builder == null && (clazz = clazz.getSuperclass()) != null);
		}
		return builder;
	}

	private void processNode(QueryNode node, QueryBuilder builder)
		throws QueryNodeException
	{
		if (builder == null)
			throw new QueryNodeException(new MessageImpl(QueryParserMessages.LUCENE_QUERY_CONVERSION_ERROR, new Object[] {
				node.toQueryString(new EscapeQuerySyntaxImpl()), node.getClass().getName()
			}));
		Object obj = builder.build(node);
		if (obj != null)
			node.setTag(QUERY_TREE_BUILDER_TAGID, obj);
	}

	private QueryBuilder getQueryBuilder(Class clazz)
	{
		if (org/apache/lucene/queryparser/flexible/core/nodes/QueryNode.isAssignableFrom(clazz))
			return (QueryBuilder)queryNodeBuilders.get(clazz);
		else
			return null;
	}

	public Object build(QueryNode queryNode)
		throws QueryNodeException
	{
		process(queryNode);
		return queryNode.getTag(QUERY_TREE_BUILDER_TAGID);
	}

}
