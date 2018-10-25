// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FieldQueryNode.java

package org.apache.lucene.queryparser.flexible.core.nodes;

import java.util.Locale;
import org.apache.lucene.queryparser.flexible.core.parser.EscapeQuerySyntax;

// Referenced classes of package org.apache.lucene.queryparser.flexible.core.nodes:
//			QueryNodeImpl, FieldValuePairQueryNode, TextableQueryNode, QueryNode

public class FieldQueryNode extends QueryNodeImpl
	implements FieldValuePairQueryNode, TextableQueryNode
{

	protected CharSequence field;
	protected CharSequence text;
	protected int begin;
	protected int end;
	protected int positionIncrement;

	public FieldQueryNode(CharSequence field, CharSequence text, int begin, int end)
	{
		this.field = field;
		this.text = text;
		this.begin = begin;
		this.end = end;
		setLeaf(true);
	}

	protected CharSequence getTermEscaped(EscapeQuerySyntax escaper)
	{
		return escaper.escape(text, Locale.getDefault(), org.apache.lucene.queryparser.flexible.core.parser.EscapeQuerySyntax.Type.NORMAL);
	}

	protected CharSequence getTermEscapeQuoted(EscapeQuerySyntax escaper)
	{
		return escaper.escape(text, Locale.getDefault(), org.apache.lucene.queryparser.flexible.core.parser.EscapeQuerySyntax.Type.STRING);
	}

	public CharSequence toQueryString(EscapeQuerySyntax escaper)
	{
		if (isDefaultField(field))
			return getTermEscaped(escaper);
		else
			return (new StringBuilder()).append(field).append(":").append(getTermEscaped(escaper)).toString();
	}

	public String toString()
	{
		return (new StringBuilder()).append("<field start='").append(begin).append("' end='").append(end).append("' field='").append(field).append("' text='").append(text).append("'/>").toString();
	}

	public String getTextAsString()
	{
		if (text == null)
			return null;
		else
			return text.toString();
	}

	public String getFieldAsString()
	{
		if (field == null)
			return null;
		else
			return field.toString();
	}

	public int getBegin()
	{
		return begin;
	}

	public void setBegin(int begin)
	{
		this.begin = begin;
	}

	public int getEnd()
	{
		return end;
	}

	public void setEnd(int end)
	{
		this.end = end;
	}

	public CharSequence getField()
	{
		return field;
	}

	public void setField(CharSequence field)
	{
		this.field = field;
	}

	public int getPositionIncrement()
	{
		return positionIncrement;
	}

	public void setPositionIncrement(int pi)
	{
		positionIncrement = pi;
	}

	public CharSequence getText()
	{
		return text;
	}

	public void setText(CharSequence text)
	{
		this.text = text;
	}

	public FieldQueryNode cloneTree()
		throws CloneNotSupportedException
	{
		FieldQueryNode fqn = (FieldQueryNode)super.cloneTree();
		fqn.begin = begin;
		fqn.end = end;
		fqn.field = field;
		fqn.text = text;
		fqn.positionIncrement = positionIncrement;
		fqn.toQueryStringIgnoreFields = toQueryStringIgnoreFields;
		return fqn;
	}

	public CharSequence getValue()
	{
		return getText();
	}

	public void setValue(CharSequence value)
	{
		setText(value);
	}

	public volatile QueryNode cloneTree()
		throws CloneNotSupportedException
	{
		return cloneTree();
	}

	public volatile Object getValue()
	{
		return getValue();
	}

	public volatile void setValue(Object x0)
	{
		setValue((CharSequence)x0);
	}
}
