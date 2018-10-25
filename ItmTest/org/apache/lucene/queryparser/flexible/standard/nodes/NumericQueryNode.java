// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NumericQueryNode.java

package org.apache.lucene.queryparser.flexible.standard.nodes;

import java.text.NumberFormat;
import java.util.Locale;
import org.apache.lucene.queryparser.flexible.core.nodes.FieldValuePairQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNodeImpl;
import org.apache.lucene.queryparser.flexible.core.parser.EscapeQuerySyntax;

public class NumericQueryNode extends QueryNodeImpl
	implements FieldValuePairQueryNode
{

	private NumberFormat numberFormat;
	private CharSequence field;
	private Number value;

	public NumericQueryNode(CharSequence field, Number value, NumberFormat numberFormat)
	{
		setNumberFormat(numberFormat);
		setField(field);
		setValue(value);
	}

	public CharSequence getField()
	{
		return field;
	}

	public void setField(CharSequence fieldName)
	{
		field = fieldName;
	}

	protected CharSequence getTermEscaped(EscapeQuerySyntax escaper)
	{
		return escaper.escape(numberFormat.format(value), Locale.ROOT, org.apache.lucene.queryparser.flexible.core.parser.EscapeQuerySyntax.Type.NORMAL);
	}

	public CharSequence toQueryString(EscapeQuerySyntax escapeSyntaxParser)
	{
		if (isDefaultField(field))
			return getTermEscaped(escapeSyntaxParser);
		else
			return (new StringBuilder()).append(field).append(":").append(getTermEscaped(escapeSyntaxParser)).toString();
	}

	public void setNumberFormat(NumberFormat format)
	{
		numberFormat = format;
	}

	public NumberFormat getNumberFormat()
	{
		return numberFormat;
	}

	public Number getValue()
	{
		return value;
	}

	public void setValue(Number value)
	{
		this.value = value;
	}

	public String toString()
	{
		return (new StringBuilder()).append("<numeric field='").append(field).append("' number='").append(numberFormat.format(value)).append("'/>").toString();
	}

	public volatile Object getValue()
	{
		return getValue();
	}

	public volatile void setValue(Object x0)
	{
		setValue((Number)x0);
	}
}
