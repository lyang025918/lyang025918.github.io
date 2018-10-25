// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NumericRangeQueryNodeBuilder.java

package org.apache.lucene.queryparser.flexible.standard.builders;

import org.apache.lucene.document.FieldType;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.messages.QueryParserMessages;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.core.util.StringUtils;
import org.apache.lucene.queryparser.flexible.messages.MessageImpl;
import org.apache.lucene.queryparser.flexible.standard.config.NumericConfig;
import org.apache.lucene.queryparser.flexible.standard.nodes.NumericQueryNode;
import org.apache.lucene.queryparser.flexible.standard.nodes.NumericRangeQueryNode;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;

// Referenced classes of package org.apache.lucene.queryparser.flexible.standard.builders:
//			StandardQueryBuilder

public class NumericRangeQueryNodeBuilder
	implements StandardQueryBuilder
{

	public NumericRangeQueryNodeBuilder()
	{
	}

	public NumericRangeQuery build(QueryNode queryNode)
		throws QueryNodeException
	{
		NumericRangeQueryNode numericRangeNode = (NumericRangeQueryNode)queryNode;
		NumericQueryNode lowerNumericNode = (NumericQueryNode)numericRangeNode.getLowerBound();
		NumericQueryNode upperNumericNode = (NumericQueryNode)numericRangeNode.getUpperBound();
		Number lowerNumber = lowerNumericNode.getValue();
		Number upperNumber = upperNumericNode.getValue();
		NumericConfig numericConfig = numericRangeNode.getNumericConfig();
		org.apache.lucene.document.FieldType.NumericType numberType = numericConfig.getType();
		String field = StringUtils.toString(numericRangeNode.getField());
		boolean minInclusive = numericRangeNode.isLowerInclusive();
		boolean maxInclusive = numericRangeNode.isUpperInclusive();
		int precisionStep = numericConfig.getPrecisionStep();
		static class 1
		{

			static final int $SwitchMap$org$apache$lucene$document$FieldType$NumericType[];

			static 
			{
				$SwitchMap$org$apache$lucene$document$FieldType$NumericType = new int[org.apache.lucene.document.FieldType.NumericType.values().length];
				try
				{
					$SwitchMap$org$apache$lucene$document$FieldType$NumericType[org.apache.lucene.document.FieldType.NumericType.LONG.ordinal()] = 1;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$document$FieldType$NumericType[org.apache.lucene.document.FieldType.NumericType.INT.ordinal()] = 2;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$document$FieldType$NumericType[org.apache.lucene.document.FieldType.NumericType.FLOAT.ordinal()] = 3;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$document$FieldType$NumericType[org.apache.lucene.document.FieldType.NumericType.DOUBLE.ordinal()] = 4;
				}
				catch (NoSuchFieldError ex) { }
			}
		}

		switch (1..SwitchMap.org.apache.lucene.document.FieldType.NumericType[numberType.ordinal()])
		{
		case 1: // '\001'
			return NumericRangeQuery.newLongRange(field, precisionStep, (Long)lowerNumber, (Long)upperNumber, minInclusive, maxInclusive);

		case 2: // '\002'
			return NumericRangeQuery.newIntRange(field, precisionStep, (Integer)lowerNumber, (Integer)upperNumber, minInclusive, maxInclusive);

		case 3: // '\003'
			return NumericRangeQuery.newFloatRange(field, precisionStep, (Float)lowerNumber, (Float)upperNumber, minInclusive, maxInclusive);

		case 4: // '\004'
			return NumericRangeQuery.newDoubleRange(field, precisionStep, (Double)lowerNumber, (Double)upperNumber, minInclusive, maxInclusive);
		}
		throw new QueryNodeException(new MessageImpl(QueryParserMessages.UNSUPPORTED_NUMERIC_DATA_TYPE, new Object[] {
			numberType
		}));
	}

	public volatile Query build(QueryNode x0)
		throws QueryNodeException
	{
		return build(x0);
	}

	public volatile Object build(QueryNode x0)
		throws QueryNodeException
	{
		return build(x0);
	}
}
