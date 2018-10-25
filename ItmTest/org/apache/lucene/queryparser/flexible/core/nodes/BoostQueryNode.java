// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BoostQueryNode.java

package org.apache.lucene.queryparser.flexible.core.nodes;

import java.util.List;
import org.apache.lucene.queryparser.flexible.core.QueryNodeError;
import org.apache.lucene.queryparser.flexible.core.messages.QueryParserMessages;
import org.apache.lucene.queryparser.flexible.core.parser.EscapeQuerySyntax;
import org.apache.lucene.queryparser.flexible.messages.MessageImpl;

// Referenced classes of package org.apache.lucene.queryparser.flexible.core.nodes:
//			QueryNodeImpl, QueryNode

public class BoostQueryNode extends QueryNodeImpl
{

	private float value;

	public BoostQueryNode(QueryNode query, float value)
	{
		this.value = 0.0F;
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
		List children = getChildren();
		if (children == null || children.size() == 0)
			return null;
		else
			return (QueryNode)children.get(0);
	}

	public float getValue()
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
		return (new StringBuilder()).append("<boost value='").append(getValueString()).append("'>").append("\n").append(getChild().toString()).append("\n</boost>").toString();
	}

	public CharSequence toQueryString(EscapeQuerySyntax escapeSyntaxParser)
	{
		if (getChild() == null)
			return "";
		else
			return (new StringBuilder()).append(getChild().toQueryString(escapeSyntaxParser)).append("^").append(getValueString()).toString();
	}

	public QueryNode cloneTree()
		throws CloneNotSupportedException
	{
		BoostQueryNode clone = (BoostQueryNode)super.cloneTree();
		clone.value = value;
		return clone;
	}
}
