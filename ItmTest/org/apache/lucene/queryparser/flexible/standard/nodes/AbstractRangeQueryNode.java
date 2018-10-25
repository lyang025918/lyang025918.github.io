// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AbstractRangeQueryNode.java

package org.apache.lucene.queryparser.flexible.standard.nodes;

import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.queryparser.flexible.core.nodes.*;
import org.apache.lucene.queryparser.flexible.core.parser.EscapeQuerySyntax;
import org.apache.lucene.queryparser.flexible.core.util.StringUtils;

public class AbstractRangeQueryNode extends QueryNodeImpl
	implements RangeQueryNode
{

	private boolean lowerInclusive;
	private boolean upperInclusive;

	protected AbstractRangeQueryNode()
	{
		setLeaf(false);
		allocate();
	}

	public CharSequence getField()
	{
		CharSequence field = null;
		FieldValuePairQueryNode lower = getLowerBound();
		FieldValuePairQueryNode upper = getUpperBound();
		if (lower != null)
			field = lower.getField();
		else
		if (upper != null)
			field = upper.getField();
		return field;
	}

	public void setField(CharSequence fieldName)
	{
		FieldValuePairQueryNode lower = getLowerBound();
		FieldValuePairQueryNode upper = getUpperBound();
		if (lower != null)
			lower.setField(fieldName);
		if (upper != null)
			upper.setField(fieldName);
	}

	public FieldValuePairQueryNode getLowerBound()
	{
		return (FieldValuePairQueryNode)getChildren().get(0);
	}

	public FieldValuePairQueryNode getUpperBound()
	{
		return (FieldValuePairQueryNode)getChildren().get(1);
	}

	public boolean isLowerInclusive()
	{
		return lowerInclusive;
	}

	public boolean isUpperInclusive()
	{
		return upperInclusive;
	}

	public void setBounds(FieldValuePairQueryNode lower, FieldValuePairQueryNode upper, boolean lowerInclusive, boolean upperInclusive)
	{
		if (lower != null && upper != null)
		{
			String lowerField = StringUtils.toString(lower.getField());
			String upperField = StringUtils.toString(upper.getField());
			if ((upperField != null || lowerField != null) && (upperField != null && !upperField.equals(lowerField) || !lowerField.equals(upperField)))
				throw new IllegalArgumentException("lower and upper bounds should have the same field name!");
			this.lowerInclusive = lowerInclusive;
			this.upperInclusive = upperInclusive;
			ArrayList children = new ArrayList(2);
			children.add(lower);
			children.add(upper);
			set(children);
		}
	}

	public CharSequence toQueryString(EscapeQuerySyntax escapeSyntaxParser)
	{
		StringBuilder sb = new StringBuilder();
		FieldValuePairQueryNode lower = getLowerBound();
		FieldValuePairQueryNode upper = getUpperBound();
		if (lowerInclusive)
			sb.append('[');
		else
			sb.append('{');
		if (lower != null)
			sb.append(lower.toQueryString(escapeSyntaxParser));
		else
			sb.append("...");
		sb.append(' ');
		if (upper != null)
			sb.append(upper.toQueryString(escapeSyntaxParser));
		else
			sb.append("...");
		if (upperInclusive)
			sb.append(']');
		else
			sb.append('}');
		return sb.toString();
	}

	public String toString()
	{
		StringBuilder sb = (new StringBuilder("<")).append(getClass().getCanonicalName());
		sb.append(" lowerInclusive=").append(isLowerInclusive());
		sb.append(" upperInclusive=").append(isUpperInclusive());
		sb.append(">\n\t");
		sb.append(getUpperBound()).append("\n\t");
		sb.append(getLowerBound()).append("\n");
		sb.append("</").append(getClass().getCanonicalName()).append(">\n");
		return sb.toString();
	}
}
