// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PhraseSlopQueryNode.java

package org.apache.lucene.queryparser.flexible.core.nodes;

import java.util.List;
import org.apache.lucene.queryparser.flexible.core.QueryNodeError;
import org.apache.lucene.queryparser.flexible.core.messages.QueryParserMessages;
import org.apache.lucene.queryparser.flexible.core.parser.EscapeQuerySyntax;
import org.apache.lucene.queryparser.flexible.messages.MessageImpl;

// Referenced classes of package org.apache.lucene.queryparser.flexible.core.nodes:
//			QueryNodeImpl, QueryNode, FieldableNode

public class PhraseSlopQueryNode extends QueryNodeImpl
	implements FieldableNode
{

	private int value;

	public PhraseSlopQueryNode(QueryNode query, int value)
	{
		this.value = 0;
		if (query == null)
		{
			throw new QueryNodeError(new MessageImpl(QueryParserMessages.NODE_ACTION_NOT_SUPPORTED, new Object[] {
				"query", "null"
			}));
		} else
		{
			this.value = value;
			setLeaf(false);
			allocate();
			add(query);
			return;
		}
	}

	public QueryNode getChild()
	{
		return (QueryNode)getChildren().get(0);
	}

	public int getValue()
	{
		return value;
	}

	private CharSequence getValueString()
	{
		Float f = Float.valueOf(value);
		if (f.floatValue() == (float)f.longValue())
			return (new StringBuilder()).append("").append(f.longValue()).toString();
		else
			return (new StringBuilder()).append("").append(f).toString();
	}

	public String toString()
	{
		return (new StringBuilder()).append("<phraseslop value='").append(getValueString()).append("'>").append("\n").append(getChild().toString()).append("\n</phraseslop>").toString();
	}

	public CharSequence toQueryString(EscapeQuerySyntax escapeSyntaxParser)
	{
		if (getChild() == null)
			return "";
		else
			return (new StringBuilder()).append(getChild().toQueryString(escapeSyntaxParser)).append("~").append(getValueString()).toString();
	}

	public QueryNode cloneTree()
		throws CloneNotSupportedException
	{
		PhraseSlopQueryNode clone = (PhraseSlopQueryNode)super.cloneTree();
		clone.value = value;
		return clone;
	}

	public CharSequence getField()
	{
		QueryNode child = getChild();
		if (child instanceof FieldableNode)
			return ((FieldableNode)child).getField();
		else
			return null;
	}

	public void setField(CharSequence fieldName)
	{
		QueryNode child = getChild();
		if (child instanceof FieldableNode)
			((FieldableNode)child).setField(fieldName);
	}
}
