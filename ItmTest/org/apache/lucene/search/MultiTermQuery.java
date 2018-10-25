// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MultiTermQuery.java

package org.apache.lucene.search;

import java.io.IOException;
import org.apache.lucene.index.*;
import org.apache.lucene.util.AttributeSource;

// Referenced classes of package org.apache.lucene.search:
//			Query, ScoringRewrite, ConstantScoreAutoRewrite, TopTermsRewrite, 
//			BooleanQuery, ConstantScoreQuery, TermQuery, BooleanClause, 
//			MultiTermQueryWrapperFilter

public abstract class MultiTermQuery extends Query
{
	public static class ConstantScoreAutoRewrite extends org.apache.lucene.search.ConstantScoreAutoRewrite
	{

		public volatile boolean equals(Object x0)
		{
			return super.equals(x0);
		}

		public volatile int hashCode()
		{
			return super.hashCode();
		}

		public volatile Query rewrite(IndexReader x0, MultiTermQuery x1)
			throws IOException
		{
			return super.rewrite(x0, x1);
		}

		public volatile double getDocCountPercent()
		{
			return super.getDocCountPercent();
		}

		public volatile void setDocCountPercent(double x0)
		{
			super.setDocCountPercent(x0);
		}

		public volatile int getTermCountCutoff()
		{
			return super.getTermCountCutoff();
		}

		public volatile void setTermCountCutoff(int x0)
		{
			super.setTermCountCutoff(x0);
		}

		public ConstantScoreAutoRewrite()
		{
		}
	}

	public static final class TopTermsBoostOnlyBooleanQueryRewrite extends TopTermsRewrite
	{

		protected int getMaxSize()
		{
			return BooleanQuery.getMaxClauseCount();
		}

		protected BooleanQuery getTopLevelQuery()
		{
			return new BooleanQuery(true);
		}

		protected void addClause(BooleanQuery topLevel, Term term, int docFreq, float boost, TermContext states)
		{
			Query q = new ConstantScoreQuery(new TermQuery(term, states));
			q.setBoost(boost);
			topLevel.add(q, BooleanClause.Occur.SHOULD);
		}

		protected volatile void addClause(Query x0, Term x1, int x2, float x3, TermContext x4)
			throws IOException
		{
			addClause((BooleanQuery)x0, x1, x2, x3, x4);
		}

		protected volatile Query getTopLevelQuery()
			throws IOException
		{
			return getTopLevelQuery();
		}

		public TopTermsBoostOnlyBooleanQueryRewrite(int size)
		{
			super(size);
		}
	}

	public static final class TopTermsScoringBooleanQueryRewrite extends TopTermsRewrite
	{

		protected int getMaxSize()
		{
			return BooleanQuery.getMaxClauseCount();
		}

		protected BooleanQuery getTopLevelQuery()
		{
			return new BooleanQuery(true);
		}

		protected void addClause(BooleanQuery topLevel, Term term, int docCount, float boost, TermContext states)
		{
			TermQuery tq = new TermQuery(term, states);
			tq.setBoost(boost);
			topLevel.add(tq, BooleanClause.Occur.SHOULD);
		}

		protected volatile void addClause(Query x0, Term x1, int x2, float x3, TermContext x4)
			throws IOException
		{
			addClause((BooleanQuery)x0, x1, x2, x3, x4);
		}

		protected volatile Query getTopLevelQuery()
			throws IOException
		{
			return getTopLevelQuery();
		}

		public TopTermsScoringBooleanQueryRewrite(int size)
		{
			super(size);
		}
	}

	public static abstract class RewriteMethod
	{

		public abstract Query rewrite(IndexReader indexreader, MultiTermQuery multitermquery)
			throws IOException;

		protected TermsEnum getTermsEnum(MultiTermQuery query, Terms terms, AttributeSource atts)
			throws IOException
		{
			return query.getTermsEnum(terms, atts);
		}

		public RewriteMethod()
		{
		}
	}


	protected final String field;
	protected RewriteMethod rewriteMethod;
	public static final RewriteMethod CONSTANT_SCORE_FILTER_REWRITE = new RewriteMethod() {

		public Query rewrite(IndexReader reader, MultiTermQuery query)
		{
			Query result = new ConstantScoreQuery(new MultiTermQueryWrapperFilter(query));
			result.setBoost(query.getBoost());
			return result;
		}

	};
	public static final RewriteMethod SCORING_BOOLEAN_QUERY_REWRITE;
	public static final RewriteMethod CONSTANT_SCORE_BOOLEAN_QUERY_REWRITE;
	public static final RewriteMethod CONSTANT_SCORE_AUTO_REWRITE_DEFAULT = new ConstantScoreAutoRewrite() {

		public void setTermCountCutoff(int count)
		{
			throw new UnsupportedOperationException("Please create a private instance");
		}

		public void setDocCountPercent(double percent)
		{
			throw new UnsupportedOperationException("Please create a private instance");
		}

	};
	static final boolean $assertionsDisabled = !org/apache/lucene/search/MultiTermQuery.desiredAssertionStatus();

	public MultiTermQuery(String field)
	{
		rewriteMethod = CONSTANT_SCORE_AUTO_REWRITE_DEFAULT;
		this.field = field;
		if (!$assertionsDisabled && field == null)
			throw new AssertionError();
		else
			return;
	}

	public final String getField()
	{
		return field;
	}

	protected abstract TermsEnum getTermsEnum(Terms terms, AttributeSource attributesource)
		throws IOException;

	protected final TermsEnum getTermsEnum(Terms terms)
		throws IOException
	{
		return getTermsEnum(terms, new AttributeSource());
	}

	public final Query rewrite(IndexReader reader)
		throws IOException
	{
		return rewriteMethod.rewrite(reader, this);
	}

	public RewriteMethod getRewriteMethod()
	{
		return rewriteMethod;
	}

	public void setRewriteMethod(RewriteMethod method)
	{
		rewriteMethod = method;
	}

	public int hashCode()
	{
		int prime = 31;
		int result = 1;
		result = 31 * result + Float.floatToIntBits(getBoost());
		result = 31 * result + rewriteMethod.hashCode();
		if (field != null)
			result = 31 * result + field.hashCode();
		return result;
	}

	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MultiTermQuery other = (MultiTermQuery)obj;
		if (Float.floatToIntBits(getBoost()) != Float.floatToIntBits(other.getBoost()))
			return false;
		if (!rewriteMethod.equals(other.rewriteMethod))
			return false;
		else
			return other.field != null ? other.field.equals(field) : field == null;
	}

	static 
	{
		SCORING_BOOLEAN_QUERY_REWRITE = ScoringRewrite.SCORING_BOOLEAN_QUERY_REWRITE;
		CONSTANT_SCORE_BOOLEAN_QUERY_REWRITE = ScoringRewrite.CONSTANT_SCORE_BOOLEAN_QUERY_REWRITE;
	}
}
