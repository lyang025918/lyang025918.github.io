// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TermRangeQueryNodeProcessor.java

package org.apache.lucene.queryparser.flexible.standard.processors;

import java.text.DateFormat;
import java.util.*;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.config.FieldConfig;
import org.apache.lucene.queryparser.flexible.core.config.QueryConfigHandler;
import org.apache.lucene.queryparser.flexible.core.nodes.FieldQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.core.processors.QueryNodeProcessorImpl;
import org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler;
import org.apache.lucene.queryparser.flexible.standard.nodes.TermRangeQueryNode;

public class TermRangeQueryNodeProcessor extends QueryNodeProcessorImpl
{

	public TermRangeQueryNodeProcessor()
	{
	}

	protected QueryNode postProcessNode(QueryNode node)
		throws QueryNodeException
	{
		if (node instanceof TermRangeQueryNode)
		{
			TermRangeQueryNode termRangeNode = (TermRangeQueryNode)node;
			FieldQueryNode upper = (FieldQueryNode)termRangeNode.getUpperBound();
			FieldQueryNode lower = (FieldQueryNode)termRangeNode.getLowerBound();
			org.apache.lucene.document.DateTools.Resolution dateRes = null;
			boolean inclusive = false;
			Locale locale = (Locale)getQueryConfigHandler().get(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.LOCALE);
			if (locale == null)
				locale = Locale.getDefault();
			TimeZone timeZone = (TimeZone)getQueryConfigHandler().get(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.TIMEZONE);
			if (timeZone == null)
				timeZone = TimeZone.getDefault();
			CharSequence field = termRangeNode.getField();
			String fieldStr = null;
			if (field != null)
				fieldStr = field.toString();
			FieldConfig fieldConfig = getQueryConfigHandler().getFieldConfig(fieldStr);
			if (fieldConfig != null)
				dateRes = (org.apache.lucene.document.DateTools.Resolution)fieldConfig.get(org.apache.lucene.queryparser.flexible.standard.config.StandardQueryConfigHandler.ConfigurationKeys.DATE_RESOLUTION);
			if (termRangeNode.isUpperInclusive())
				inclusive = true;
			String part1 = lower.getTextAsString();
			String part2 = upper.getTextAsString();
			try
			{
				DateFormat df = DateFormat.getDateInstance(3, locale);
				df.setLenient(true);
				if (part1.length() > 0)
				{
					Date d1 = df.parse(part1);
					part1 = DateTools.dateToString(d1, dateRes);
					lower.setText(part1);
				}
				if (part2.length() > 0)
				{
					Date d2 = df.parse(part2);
					if (inclusive)
					{
						Calendar cal = Calendar.getInstance(timeZone, locale);
						cal.setTime(d2);
						cal.set(11, 23);
						cal.set(12, 59);
						cal.set(13, 59);
						cal.set(14, 999);
						d2 = cal.getTime();
					}
					part2 = DateTools.dateToString(d2, dateRes);
					upper.setText(part2);
				}
			}
			catch (Exception e) { }
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
