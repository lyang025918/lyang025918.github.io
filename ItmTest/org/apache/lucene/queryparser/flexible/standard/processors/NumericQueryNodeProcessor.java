// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NumericQueryNodeProcessor.java

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
import org.apache.lucene.queryparser.flexible.core.nodes.*;
import org.apache.lucene.queryparser.flexible.core.processors.QueryNodeProcessorImpl;
import org.apache.lucene.queryparser.flexible.messages.MessageImpl;
import org.apache.lucene.queryparser.flexible.standard.config.NumericConfig;
import org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler;
import org.apache.lucene.queryparser.flexible.standard.nodes.NumericQueryNode;
import org.apache.lucene.queryparser.flexible.standard.nodes.NumericRangeQueryNode;

public class NumericQueryNodeProcessor extends QueryNodeProcessorImpl
{

	public NumericQueryNodeProcessor()
	{
	}

	protected QueryNode postProcessNode(QueryNode node)
		throws QueryNodeException
	{
		if ((node instanceof FieldQueryNode) && !(node.getParent() instanceof RangeQueryNode))
		{
			QueryConfigHandler config = getQueryConfigHandler();
			if (config != null)
			{
				FieldQueryNode fieldNode = (FieldQueryNode)node;
				FieldConfig fieldConfig = config.getFieldConfig(fieldNode.getFieldAsString());
				if (fieldConfig != null)
				{
					NumericConfig numericConfig = (NumericConfig)fieldConfig.get(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.NUMERIC_CONFIG);
					if (numericConfig != null)
					{
						NumberFormat numberFormat = numericConfig.getNumberFormat();
						String text = fieldNode.getTextAsString();
						Number number = null;
						if (text.length() > 0)
						{
							try
							{
								number = numberFormat.parse(text);
							}
							catch (ParseException e)
							{
								throw new QueryNodeParseException(new MessageImpl(QueryParserMessages.COULD_NOT_PARSE_NUMBER, new Object[] {
									fieldNode.getTextAsString(), numberFormat.getClass().getCanonicalName()
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
							case 1: // '\001'
								number = Long.valueOf(number.longValue());
								break;

							case 2: // '\002'
								number = Integer.valueOf(number.intValue());
								break;

							case 3: // '\003'
								number = Double.valueOf(number.doubleValue());
								break;

							case 4: // '\004'
								number = Float.valueOf(number.floatValue());
								break;
							}
						} else
						{
							throw new QueryNodeParseException(new MessageImpl(QueryParserMessages.NUMERIC_CANNOT_BE_EMPTY, new Object[] {
								fieldNode.getFieldAsString()
							}));
						}
						NumericQueryNode lowerNode = new NumericQueryNode(fieldNode.getField(), number, numberFormat);
						NumericQueryNode upperNode = new NumericQueryNode(fieldNode.getField(), number, numberFormat);
						return new NumericRangeQueryNode(lowerNode, upperNode, true, true, numericConfig);
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
