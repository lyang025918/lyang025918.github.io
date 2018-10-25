// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GroupQueryNode.java

package org.apache.lucene.queryparser.flexible.core.nodes;

import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.queryparser.flexible.core.QueryNodeError;
import org.apache.lucene.queryparser.flexible.core.messages.QueryParserMessages;
import org.apache.lucene.queryparser.flexible.core.parser.EscapeQuerySyntax;
import org.apache.lucene.queryparser.flexible.messages.MessageImpl;

// Referenced classes of package org.apache.lucene.queryparser.flexible.core.nodes:
//			QueryNodeImpl, QueryNode

public class GroupQueryNode extends QueryNodeImpl
{

	public GroupQueryNode(QueryNode query)
	{
		if (query == null)
		{
			throw new QueryNodeError(new MessageImpl(QueryParserMessages.PARAMETER_VALUE_NOT_SUPPORTED, new Object[] {
				"query", "null"
			}));
		} else
		{
			allocate();
			setLeaf(false);
			add(query);
			return;
		}
	}

	public QueryNode getChild()
	{
		return (QueryNode)getChildren().get(0);
	}

	public String toString()
	{
		return (new StringBuilder()).append("<group>\n").append(getChild().toString()).append("\n</group>").toString();
	}

	public CharSequence toQueryString(EscapeQuerySyntax escapeSyntaxParser)
	{
		if (getChild() == null)
			return "";
		else
			return (new StringBuilder()).append("( ").append(getChild().toQueryString(escapeSyntaxParser)).append(" )").toString();
	}

	public QueryNode cloneTree()
		throws CloneNotSupportedException
	{
		GroupQueryNode clone = (GroupQueryNode)super.cloneTree();
		return clone;
	}

	public void setChild(QueryNode child)
	{
		List list = new ArrayList();
		list.add(child);
		set(list);
	}
}
