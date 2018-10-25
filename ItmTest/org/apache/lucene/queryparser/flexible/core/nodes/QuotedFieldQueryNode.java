// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   QuotedFieldQueryNode.java

package org.apache.lucene.queryparser.flexible.core.nodes;

import org.apache.lucene.queryparser.flexible.core.parser.EscapeQuerySyntax;

// Referenced classes of package org.apache.lucene.queryparser.flexible.core.nodes:
//			FieldQueryNode, QueryNode

public class QuotedFieldQueryNode extends FieldQueryNode
{

	public QuotedFieldQueryNode(CharSequence field, CharSequence text, int begin, int end)
	{
		super(field, text, begin, end);
	}

	public CharSequence toQueryString(EscapeQuerySyntax escaper)
	{
		if (isDefaultField(field))
			return (new StringBuilder()).append("\"").append(getTermEscapeQuoted(escaper)).append("\"").toString();
		else
			return (new StringBuilder()).append(field).append(":").append("\"").append(getTermEscapeQuoted(escaper)).append("\"").toString();
	}

	public String toString()
	{
		return (new StringBuilder()).append("<quotedfield start='").append(begin).append("' end='").append(end).append("' field='").append(field).append("' term='").append(text).append("'/>").toString();
	}

	public QuotedFieldQueryNode cloneTree()
		throws CloneNotSupportedException
	{
		QuotedFieldQueryNode clone = (QuotedFieldQueryNode)super.cloneTree();
		return clone;
	}

	public volatile FieldQueryNode cloneTree()
		throws CloneNotSupportedException
	{
		return cloneTree();
	}

	public volatile QueryNode cloneTree()
		throws CloneNotSupportedException
	{
		return cloneTree();
	}
}
