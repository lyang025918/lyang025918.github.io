// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NumericRangeQueryNode.java

package org.apache.lucene.queryparser.flexible.standard.nodes;

import org.apache.lucene.document.FieldType;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.messages.QueryParserMessages;
import org.apache.lucene.queryparser.flexible.messages.MessageImpl;
import org.apache.lucene.queryparser.flexible.standard.config.NumericConfig;

// Referenced classes of package org.apache.lucene.queryparser.flexible.standard.nodes:
//			AbstractRangeQueryNode, NumericQueryNode

public class NumericRangeQueryNode extends AbstractRangeQueryNode
{

	public NumericConfig numericConfig;

	public NumericRangeQueryNode(NumericQueryNode lower, NumericQueryNode upper, boolean lowerInclusive, boolean upperInclusive, NumericConfig numericConfig)
		throws QueryNodeException
	{
		setBounds(lower, upper, lowerInclusive, upperInclusive, numericConfig);
	}

	private static org.apache.lucene.document.FieldType.NumericType getNumericDataType(Number number)
		throws QueryNodeException
	{
		if (number instanceof Long)
			return org.apache.lucene.document.FieldType.NumericType.LONG;
		if (number instanceof Integer)
			return org.apache.lucene.document.FieldType.NumericType.INT;
		if (number instanceof Double)
			return org.apache.lucene.document.FieldType.NumericType.DOUBLE;
		if (number instanceof Float)
			return org.apache.lucene.document.FieldType.NumericType.FLOAT;
		else
			throw new QueryNodeException(new MessageImpl(QueryParserMessages.NUMBER_CLASS_NOT_SUPPORTED_BY_NUMERIC_RANGE_QUERY, new Object[] {
				number.getClass()
			}));
	}

	public void setBounds(NumericQueryNode lower, NumericQueryNode upper, boolean lowerInclusive, boolean upperInclusive, NumericConfig numericConfig)
		throws QueryNodeException
	{
		if (numericConfig == null)
			throw new IllegalArgumentException("numericConfig cannot be null!");
		org.apache.lucene.document.FieldType.NumericType lowerNumberType;
		if (lower != null && lower.getValue() != null)
			lowerNumberType = getNumericDataType(lower.getValue());
		else
			lowerNumberType = null;
		org.apache.lucene.document.FieldType.NumericType upperNumberType;
		if (upper != null && upper.getValue() != null)
			upperNumberType = getNumericDataType(upper.getValue());
		else
			upperNumberType = null;
		if (lowerNumberType != null && !lowerNumberType.equals(numericConfig.getType()))
			throw new IllegalArgumentException((new StringBuilder()).append("lower value's type should be the same as numericConfig type: ").append(lowerNumberType).append(" != ").append(numericConfig.getType()).toString());
		if (upperNumberType != null && !upperNumberType.equals(numericConfig.getType()))
		{
			throw new IllegalArgumentException((new StringBuilder()).append("upper value's type should be the same as numericConfig type: ").append(upperNumberType).append(" != ").append(numericConfig.getType()).toString());
		} else
		{
			super.setBounds(lower, upper, lowerInclusive, upperInclusive);
			this.numericConfig = numericConfig;
			return;
		}
	}

	public NumericConfig getNumericConfig()
	{
		return numericConfig;
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder("<numericRange lowerInclusive='");
		sb.append(isLowerInclusive()).append("' upperInclusive='").append(isUpperInclusive()).append((new StringBuilder()).append("' precisionStep='").append(numericConfig.getPrecisionStep()).toString()).append((new StringBuilder()).append("' type='").append(numericConfig.getType()).toString()).append("'>\n");
		sb.append(getLowerBound()).append('\n');
		sb.append(getUpperBound()).append('\n');
		sb.append("</numericRange>");
		return sb.toString();
	}
}
