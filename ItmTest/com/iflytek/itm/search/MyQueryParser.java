// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MyQueryParser.java

package com.iflytek.itm.search;

import com.iflytek.itm.api.ITMBuilder;
import com.iflytek.itm.build.ITMUserData;
import com.iflytek.itm.util.ITMConstants;
import java.util.List;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.spans.*;
import org.apache.lucene.util.Version;

// Referenced classes of package com.iflytek.itm.search:
//			RuleAnalyzer

public class MyQueryParser extends QueryParser
{

	private ITMUserData itmUserData;

	public MyQueryParser(Version matchVersion, String f, ITMUserData userData)
	{
		super(matchVersion, f, userData.getAnalyzer());
		itmUserData = null;
		itmUserData = userData;
		setLowercaseExpandedTerms(false);
		setAllowLeadingWildcard(true);
		BooleanQuery.setMaxClauseCount(0x7fffffff);
	}

	protected Query getRangeQuery(String field, String part1, String part2, boolean startInclusive, boolean endInclusive)
		throws ParseException
	{
		String type = getFieldType(itmUserData, field);
		if (type.equals("int"))
			return NumericRangeQuery.newIntRange(field, part1 != null ? Integer.valueOf(Integer.parseInt(part1)) : null, part2 != null ? Integer.valueOf(Integer.parseInt(part2)) : null, startInclusive, endInclusive);
		if (type.equals("long"))
			return NumericRangeQuery.newLongRange(field, part1 != null ? Long.valueOf(Long.parseLong(part1)) : null, part2 != null ? Long.valueOf(Long.parseLong(part2)) : null, startInclusive, endInclusive);
		if (type.equals("float"))
			return NumericRangeQuery.newFloatRange(field, part1 != null ? Float.valueOf(Float.parseFloat(part1)) : null, part2 != null ? Float.valueOf(Float.parseFloat(part2)) : null, startInclusive, endInclusive);
		if (type.equals("double"))
			return NumericRangeQuery.newDoubleRange(field, part1 != null ? Double.valueOf(Double.parseDouble(part1)) : null, part2 != null ? Double.valueOf(Double.parseDouble(part2)) : null, startInclusive, endInclusive);
		else
			return super.newRangeQuery(field, part1, part2, startInclusive, endInclusive);
	}

	protected Query newTermQuery(Term term)
	{
		String type = getFieldType(itmUserData, term.field());
		if (type.equals("string"))
			return super.newTermQuery(term);
		Query query = null;
		try
		{
			query = getRangeQuery(term.field(), term.text(), term.text(), true, true);
		}
		catch (ParseException e) { }
		return query;
	}

	protected Query newFieldQuery(Analyzer analyzer, String field, String queryText, boolean quoted)
		throws ParseException
	{
		if (field.equals(ITMConstants.ITM_SUB_DIR_NAME_INNER_FIELD))
			return newTermQuery(new Term(field, queryText));
		if (field.startsWith(ITMConstants.ITM_RULE_INNER_FIELD_PREFIX))
		{
			List words = RuleAnalyzer.analyze(queryText);
			if (words.size() == 1)
				return super.newTermQuery(new Term(field, (String)words.get(0)));
			if (words.size() >= 2)
			{
				int pound_pos = queryText.indexOf("#");
				int plus_pos = queryText.indexOf("+");
				int pos = pound_pos;
				if (pound_pos == -1 && -1 == plus_pos)
					return obtaiSpanQuery(field, words, 0);
				boolean inOrder = false;
				int numPos = 0;
				if (-1 == pos)
				{
					pos = plus_pos;
					numPos = queryText.lastIndexOf("+");
					inOrder = true;
				} else
				{
					numPos = queryText.lastIndexOf("#");
				}
				String word1 = queryText.substring(0, pos);
				int span = Integer.valueOf(queryText.substring(pos + 1, numPos)).intValue();
				String word2 = queryText.substring(numPos + 1);
				List words1 = RuleAnalyzer.analyze(word1);
				List words2 = RuleAnalyzer.analyze(word2);
				SpanQuery query1 = obtaiSpanQuery(field, words1, 0);
				SpanQuery query2 = obtaiSpanQuery(field, words2, 0);
				SpanNearQuery query = new SpanNearQuery(new SpanQuery[] {
					query1, query2
				}, span, inOrder);
				return query;
			}
		}
		return super.newFieldQuery(analyzer, field, queryText, quoted);
	}

	SpanQuery obtaiSpanQuery(String field, List words, int span)
	{
		if (words.size() == 1)
			return new SpanTermQuery(new Term(field, (String)words.get(0)));
		SpanQuery queries[] = new SpanQuery[words.size()];
		for (int i = 0; i < words.size(); i++)
			queries[i] = new SpanTermQuery(new Term(field, (String)words.get(i)));

		SpanNearQuery query = new SpanNearQuery(queries, span, true);
		return query;
	}

	public static String getFieldType(ITMUserData itmUserData, String fieldName)
	{
		String type = "string";
		com.iflytek.itm.api.ITMBuilder.ITMField itmField = itmUserData.getField(fieldName);
		if (itmField != null)
			type = itmField.type;
		return type;
	}
}
