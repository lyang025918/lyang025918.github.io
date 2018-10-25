// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RegexpQueryNode.java

package org.apache.lucene.queryparser.flexible.standard.nodes;

import org.apache.lucene.queryparser.flexible.core.nodes.*;
import org.apache.lucene.queryparser.flexible.core.parser.EscapeQuerySyntax;
import org.apache.lucene.util.BytesRef;

public class RegexpQueryNode extends QueryNodeImpl
	implements TextableQueryNode, FieldableNode
{

	private CharSequence text;
	private CharSequence field;

	public RegexpQueryNode(CharSequence field, CharSequence text, int begin, int end)
	{
		this.field = field;
		this.text = text.subSequence(begin, end);
	}

	public BytesRef textToBytesRef()
	{
		return new BytesRef(text);
	}

	public String toString()
	{
		return (new StringBuilder()).append("<regexp field='").append(field).append("' term='").append(text).append("'/>").toString();
	}

	public RegexpQueryNode cloneTree()
		throws CloneNotSupportedException
	{
		RegexpQueryNode clone = (RegexpQueryNode)super.cloneTree();
		clone.field = field;
		clone.text = text;
		return clone;
	}

	public CharSequence getText()
	{
		return text;
	}

	public void setText(CharSequence text)
	{
		this.text = text;
	}

	public CharSequence getField()
	{
		return field;
	}

	public String getFieldAsString()
	{
		return field.toString();
	}

	public void setField(CharSequence field)
	{
		this.field = field;
	}

	public CharSequence toQueryString(EscapeQuerySyntax escapeSyntaxParser)
	{
		return isDefaultField(field) ? (new StringBuilder()).append("/").append(text).append("/").toString() : (new StringBuilder()).append(field).append(":/").append(text).append("/").toString();
	}

	public volatile QueryNode cloneTree()
		throws CloneNotSupportedException
	{
		return cloneTree();
	}
}
