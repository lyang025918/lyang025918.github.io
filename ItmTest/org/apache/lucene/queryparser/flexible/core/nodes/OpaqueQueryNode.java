// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   OpaqueQueryNode.java

package org.apache.lucene.queryparser.flexible.core.nodes;

import org.apache.lucene.queryparser.flexible.core.parser.EscapeQuerySyntax;

// Referenced classes of package org.apache.lucene.queryparser.flexible.core.nodes:
//			QueryNodeImpl, QueryNode

public class OpaqueQueryNode extends QueryNodeImpl
{

	private CharSequence schema;
	private CharSequence value;

	public OpaqueQueryNode(CharSequence schema, CharSequence value)
	{
		this.schema = null;
		this.value = null;
		setLeaf(true);
		this.schema = schema;
		this.value = value;
	}

	public String toString()
	{
		return (new StringBuilder()).append("<opaque schema='").append(schema).append("' value='").append(value).append("'/>").toString();
	}

	public CharSequence toQueryString(EscapeQuerySyntax escapeSyntaxParser)
	{
		return (new StringBuilder()).append("@").append(schema).append(":'").append(value).append("'").toString();
	}

	public QueryNode cloneTree()
		throws CloneNotSupportedException
	{
		OpaqueQueryNode clone = (OpaqueQueryNode)super.cloneTree();
		clone.schema = schema;
		clone.value = value;
		return clone;
	}

	public CharSequence getSchema()
	{
		return schema;
	}

	public CharSequence getValue()
	{
		return value;
	}
}
