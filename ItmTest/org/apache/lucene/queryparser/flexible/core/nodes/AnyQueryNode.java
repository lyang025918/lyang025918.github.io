// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AnyQueryNode.java

package org.apache.lucene.queryparser.flexible.core.nodes;

import java.util.Iterator;
import java.util.List;
import org.apache.lucene.queryparser.flexible.core.parser.EscapeQuerySyntax;

// Referenced classes of package org.apache.lucene.queryparser.flexible.core.nodes:
//			AndQueryNode, QueryNode, FieldQueryNode, QueryNodeImpl, 
//			FieldableNode

public class AnyQueryNode extends AndQueryNode
{

	private CharSequence field;
	private int minimumMatchingmElements;

	public AnyQueryNode(List clauses, CharSequence field, int minimumMatchingElements)
	{
		super(clauses);
		this.field = null;
		minimumMatchingmElements = 0;
		this.field = field;
		minimumMatchingmElements = minimumMatchingElements;
		if (clauses != null)
		{
			Iterator i$ = clauses.iterator();
			do
			{
				if (!i$.hasNext())
					break;
				QueryNode clause = (QueryNode)i$.next();
				if (clause instanceof FieldQueryNode)
				{
					if (clause instanceof QueryNodeImpl)
						((QueryNodeImpl)clause).toQueryStringIgnoreFields = true;
					if (clause instanceof FieldableNode)
						((FieldableNode)clause).setField(field);
				}
			} while (true);
		}
	}

	public int getMinimumMatchingElements()
	{
		return minimumMatchingmElements;
	}

	public CharSequence getField()
	{
		return field;
	}

	public String getFieldAsString()
	{
		if (field == null)
			return null;
		else
			return field.toString();
	}

	public void setField(CharSequence field)
	{
		this.field = field;
	}

	public QueryNode cloneTree()
		throws CloneNotSupportedException
	{
		AnyQueryNode clone = (AnyQueryNode)super.cloneTree();
		clone.field = field;
		clone.minimumMatchingmElements = minimumMatchingmElements;
		return clone;
	}

	public String toString()
	{
		if (getChildren() == null || getChildren().size() == 0)
			return (new StringBuilder()).append("<any field='").append(field).append("'  matchelements=").append(minimumMatchingmElements).append("/>").toString();
		StringBuilder sb = new StringBuilder();
		sb.append((new StringBuilder()).append("<any field='").append(field).append("'  matchelements=").append(minimumMatchingmElements).append(">").toString());
		QueryNode clause;
		for (Iterator i$ = getChildren().iterator(); i$.hasNext(); sb.append(clause.toString()))
		{
			clause = (QueryNode)i$.next();
			sb.append("\n");
		}

		sb.append("\n</any>");
		return sb.toString();
	}

	public CharSequence toQueryString(EscapeQuerySyntax escapeSyntaxParser)
	{
		String anySTR = (new StringBuilder()).append("ANY ").append(minimumMatchingmElements).toString();
		StringBuilder sb = new StringBuilder();
		if (getChildren() != null && getChildren().size() != 0)
		{
			String filler = "";
			for (Iterator i$ = getChildren().iterator(); i$.hasNext();)
			{
				QueryNode clause = (QueryNode)i$.next();
				sb.append(filler).append(clause.toQueryString(escapeSyntaxParser));
				filler = " ";
			}

		}
		if (isDefaultField(field))
			return (new StringBuilder()).append("( ").append(sb.toString()).append(" ) ").append(anySTR).toString();
		else
			return (new StringBuilder()).append(field).append(":(( ").append(sb.toString()).append(" ) ").append(anySTR).append(")").toString();
	}
}
