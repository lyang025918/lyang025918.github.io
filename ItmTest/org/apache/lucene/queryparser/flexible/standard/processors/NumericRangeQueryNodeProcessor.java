// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NumericRangeQueryNodeProcessor.java

package org.apache.lucene.queryparser.flexible.standard.processors;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.QueryNodeParseException;
import org.apache.lucene.queryparser.flexible.core.config.FieldConfig;
import org.apache.lucene.queryparser.flexible.core.config.QueryConfigHandler;
import org.apache.lucene.queryparser.flexible.core.messages.QueryParserMessages;
import org.apache.lucene.queryparser.flexible.core.nodes.FieldQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.core.processors.QueryNodeProcessorImpl;
import org.apache.lucene.queryparser.flexible.core.util.StringUtils;
import org.apache.lucene.queryparser.flexible.messages.MessageImpl;
import org.apache.lucene.queryparser.flexible.standard.config.NumericConfig;
import org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler;
import org.apache.lucene.queryparser.flexible.standard.nodes.*;

public class NumericRangeQueryNodeProcessor extends QueryNodeProcessorImpl
{

	public NumericRangeQueryNodeProcessor()
	{
	}

	protected QueryNode postProcessNode(QueryNode node)
		throws QueryNodeException
	{
		if (node instanceof TermRangeQueryNode)
		{
			QueryConfigHandler config = getQueryConfigHandler();
			if (config != null)
			{
				TermRangeQueryNode termRangeNode = (TermRangeQueryNode)node;
				FieldConfig fieldConfig = config.getFieldConfig(StringUtils.toString(termRangeNode.getField()));
				if (fieldConfig != null)
				{
					NumericConfig numericConfig = (NumericConfig)fieldConfig.get(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.NUMERIC_CONFIG);
					if (numericConfig != null)
					{
						FieldQueryNode lower = (FieldQueryNode)termRangeNode.getLowerBound();
						FieldQueryNode upper = (FieldQueryNode)termRangeNode.getUpperBound();
						String lowerText = lower.getTextAsString();
						String upperText = upper.getTextAsString();
						NumberFormat numberFormat = numericConfig.getNumberFormat();
						Number lowerNumber = null;
						Number upperNumber = null;
						if (lowerText.length() > 0)
							try
							{
								lowerNumber = numberFormat.parse(lowerText);
							}
							catch (ParseException e)
							{
								throw new QueryNodeParseException(new MessageImpl(QueryParserMessages.COULD_NOT_PARSE_NUMBER, new Object[] {
									lower.getTextAsString(), numberFormat.getClass().getCanonicalName()
								}), e);
							}
						if (upperText.length() > 0)
							try
							{
								upperNumber = numberFormat.parse(upperText);
							}
							catch (ParseException e)
							{
								throw new QueryNodeParseException(new MessageImpl(QueryParserMessages.COULD_NOT_PARSE_NUMBER, new Object[] {
									upper.getTextAsString(), numberFormat.getClass().getCanonicalName()
								}), e);
							}
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
									$SwitchMap$org$apache$lucene$document$FieldType$NumericType[org.apache.lucene.document.FieldType.NumericType.DOUBLE.ordinal()] = 3;
								}
								catch (NoSuchFieldError ex) { }
								try
								{
									$SwitchMap$org$apache$lucene$document$FieldType$NumericType[org.apache.lucene.document.FieldType.NumericType.FLOAT.ordinal()] = 4;
								}
								catch (NoSuchFieldError ex) { }
							}
						}

						switch (1..SwitchMap.org.apache.lucene.document.FieldType.NumericType[numericConfig.getType().ordinal()])
						{
						default:
							break;

						case 1: // '\001'
							if (upperNumber != null)
								upperNumber = Long.valueOf(upperNumber.longValue());
							if (lowerNumber != null)
								lowerNumber = Long.valueOf(lowerNumber.longValue());
							break;

						case 2: // '\002'
							if (upperNumber != null)
								upperNumber = Integer.valueOf(upperNumber.intValue());
							if (lowerNumber != null)
								lowerNumber = Integer.valueOf(lowerNumber.intValue());
							break;

						case 3: // '\003'
							if (upperNumber != null)
								upperNumber = Double.valueOf(upperNumber.doubleValue());
							if (lowerNumber != null)
								lowerNumber = Double.valueOf(lowerNumber.doubleValue());
							break;

						case 4: // '\004'
							if (upperNumber != null)
								upperNumber = Float.valueOf(upperNumber.floatValue());
							if (lowerNumber != null)
								lowerNumber = Float.valueOf(lowerNumber.floatValue());
							break;
						}
						NumericQueryNode lowerNode = new NumericQueryNode(termRangeNode.getField(), lowerNumber, numberFormat);
						NumericQueryNode upperNode = new NumericQueryNode(termRangeNode.getField(), upperNumber, numberFormat);
						boolean lowerInclusive = termRangeNode.isLowerInclusive();
						boolean upperInclusive = termRangeNode.isUpperInclusive();
						return new NumericRangeQueryNode(lowerNode, upperNode, lowerInclusive, upperInclusive, numericConfig);
					}
				}
			}
		}
		return node;
	}

	protected QueryNode preProcessNode(QueryNode node)
		throws QueryNodeException
	{
		return node;
	}

	protected List setChildrenOrder(List children)
		throws QueryNodeException
	{
		return children;
	}
}
