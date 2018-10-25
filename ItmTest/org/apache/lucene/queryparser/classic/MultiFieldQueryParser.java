// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MultiFieldQueryParser.java

package org.apache.lucene.queryparser.classic;

import java.util.*;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.search.*;
import org.apache.lucene.util.Version;

// Referenced classes of package org.apache.lucene.queryparser.classic:
//			QueryParser, ParseException

public class MultiFieldQueryParser extends QueryParser
{

	protected String fields[];
	protected Map boosts;

	public MultiFieldQueryParser(Version matchVersion, String fields[], Analyzer analyzer, Map boosts)
	{
		this(matchVersion, fields, analyzer);
		this.boosts = boosts;
	}

	public MultiFieldQueryParser(Version matchVersion, String fields[], Analyzer analyzer)
	{
		super(matchVersion, null, analyzer);
		this.fields = fields;
	}

	protected Query getFieldQuery(String field, String queryText, int slop)
		throws ParseException
	{
		if (field == null)
		{
			List clauses = new ArrayList();
			for (int i = 0; i < fields.length; i++)
			{
				Query q = super.getFieldQuery(fields[i], queryText, true);
				if (q == null)
					continue;
				if (boosts != null)
				{
					Float boost = (Float)boosts.get(fields[i]);
					if (boost != null)
						q.setBoost(boost.floatValue());
				}
				applySlop(q, slop);
				clauses.add(new BooleanClause(q, org.apache.lucene.search.BooleanClause.Occur.SHOULD));
			}

			if (clauses.size() == 0)
				return null;
			else
				return getBooleanQuery(clauses, true);
		} else
		{
			Query q = super.getFieldQuery(field, queryText, true);
			applySlop(q, slop);
			return q;
		}
	}

	private void applySlop(Query q, int slop)
	{
		if (q instanceof PhraseQuery)
			((PhraseQuery)q).setSlop(slop);
		else
		if (q instanceof MultiPhraseQuery)
			((MultiPhraseQuery)q).setSlop(slop);
	}

	protected Query getFieldQuery(String field, String queryText, boolean quoted)
		throws ParseException
	{
		if (field == null)
		{
			List clauses = new ArrayList();
			for (int i = 0; i < fields.length; i++)
			{
				Query q = super.getFieldQuery(fields[i], queryText, quoted);
				if (q == null)
					continue;
				if (boosts != null)
				{
					Float boost = (Float)boosts.get(fields[i]);
					if (boost != null)
						q.setBoost(boost.floatValue());
				}
				clauses.add(new BooleanClause(q, org.apache.lucene.search.BooleanClause.Occur.SHOULD));
			}

			if (clauses.size() == 0)
				return null;
			else
				return getBooleanQuery(clauses, true);
		} else
		{
			Query q = super.getFieldQuery(field, queryText, quoted);
			return q;
		}
	}

	protected Query getFuzzyQuery(String field, String termStr, float minSimilarity)
		throws ParseException
	{
		if (field == null)
		{
			List clauses = new ArrayList();
			for (int i = 0; i < fields.length; i++)
				clauses.add(new BooleanClause(getFuzzyQuery(fields[i], termStr, minSimilarity), org.apache.lucene.search.BooleanClause.Occur.SHOULD));

			return getBooleanQuery(clauses, true);
		} else
		{
			return super.getFuzzyQuery(field, termStr, minSimilarity);
		}
	}

	protected Query getPrefixQuery(String field, String termStr)
		throws ParseException
	{
		if (field == null)
		{
			List clauses = new ArrayList();
			for (int i = 0; i < fields.length; i++)
				clauses.add(new BooleanClause(getPrefixQuery(fields[i], termStr), org.apache.lucene.search.BooleanClause.Occur.SHOULD));

			return getBooleanQuery(clauses, true);
		} else
		{
			return super.getPrefixQuery(field, termStr);
		}
	}

	protected Query getWildcardQuery(String field, String termStr)
		throws ParseException
	{
		if (field == null)
		{
			List clauses = new ArrayList();
			for (int i = 0; i < fields.length; i++)
				clauses.add(new BooleanClause(getWildcardQuery(fields[i], termStr), org.apache.lucene.search.BooleanClause.Occur.SHOULD));

			return getBooleanQuery(clauses, true);
		} else
		{
			return super.getWildcardQuery(field, termStr);
		}
	}

	protected Query getRangeQuery(String field, String part1, String part2, boolean startInclusive, boolean endInclusive)
		throws ParseException
	{
		if (field == null)
		{
			List clauses = new ArrayList();
			for (int i = 0; i < fields.length; i++)
				clauses.add(new BooleanClause(getRangeQuery(fields[i], part1, part2, startInclusive, endInclusive), org.apache.lucene.search.BooleanClause.Occur.SHOULD));

			return getBooleanQuery(clauses, true);
		} else
		{
			return super.getRangeQuery(field, part1, part2, startInclusive, endInclusive);
		}
	}

	public static Query parse(Version matchVersion, String queries[], String fields[], Analyzer analyzer)
		throws ParseException
	{
		if (queries.length != fields.length)
			throw new IllegalArgumentException("queries.length != fields.length");
		BooleanQuery bQuery = new BooleanQuery();
		for (int i = 0; i < fields.length; i++)
		{
			QueryParser qp = new QueryParser(matchVersion, fields[i], analyzer);
			Query q = qp.parse(queries[i]);
			if (q != null && (!(q instanceof BooleanQuery) || ((BooleanQuery)q).getClauses().length > 0))
				bQuery.add(q, org.apache.lucene.search.BooleanClause.Occur.SHOULD);
		}

		return bQuery;
	}

	public static Query parse(Version matchVersion, String query, String fields[], org.apache.lucene.search.BooleanClause.Occur flags[], Analyzer analyzer)
		throws ParseException
	{
		if (fields.length != flags.length)
			throw new IllegalArgumentException("fields.length != flags.length");
		BooleanQuery bQuery = new BooleanQuery();
		for (int i = 0; i < fields.length; i++)
		{
			QueryParser qp = new QueryParser(matchVersion, fields[i], analyzer);
			Query q = qp.parse(query);
			if (q != null && (!(q instanceof BooleanQuery) || ((BooleanQuery)q).getClauses().length > 0))
				bQuery.add(q, flags[i]);
		}

		return bQuery;
	}

	public static Query parse(Version matchVersion, String queries[], String fields[], org.apache.lucene.search.BooleanClause.Occur flags[], Analyzer analyzer)
		throws ParseException
	{
		if (queries.length != fields.length || queries.length != flags.length)
			throw new IllegalArgumentException("queries, fields, and flags array have have different length");
		BooleanQuery bQuery = new BooleanQuery();
		for (int i = 0; i < fields.length; i++)
		{
			QueryParser qp = new QueryParser(matchVersion, fields[i], analyzer);
			Query q = qp.parse(queries[i]);
			if (q != null && (!(q instanceof BooleanQuery) || ((BooleanQuery)q).getClauses().length > 0))
				bQuery.add(q, flags[i]);
		}

		return bQuery;
	}
}
