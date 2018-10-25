// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ScoringRewrite.java

package org.apache.lucene.search;

import java.io.IOException;
import java.util.List;
import org.apache.lucene.index.*;
import org.apache.lucene.util.*;

// Referenced classes of package org.apache.lucene.search:
//			TermCollectingRewrite, MultiTermQuery, Query, BoostAttribute, 
//			BooleanQuery, TermQuery, BooleanClause, ConstantScoreQuery

public abstract class ScoringRewrite extends TermCollectingRewrite
{
	static final class TermFreqBoostByteStart extends org.apache.lucene.util.BytesRefHash.DirectBytesStartArray
	{

		float boost[];
		TermContext termState[];
		static final boolean $assertionsDisabled = !org/apache/lucene/search/ScoringRewrite.desiredAssertionStatus();

		public int[] init()
		{
			int ord[] = super.init();
			boost = new float[ArrayUtil.oversize(ord.length, 4)];
			termState = new TermContext[ArrayUtil.oversize(ord.length, RamUsageEstimator.NUM_BYTES_OBJECT_REF)];
			if (!$assertionsDisabled && (termState.length < ord.length || boost.length < ord.length))
				throw new AssertionError();
			else
				return ord;
		}

		public int[] grow()
		{
			int ord[] = super.grow();
			boost = ArrayUtil.grow(boost, ord.length);
			if (termState.length < ord.length)
			{
				TermContext tmpTermState[] = new TermContext[ArrayUtil.oversize(ord.length, RamUsageEstimator.NUM_BYTES_OBJECT_REF)];
				System.arraycopy(termState, 0, tmpTermState, 0, termState.length);
				termState = tmpTermState;
			}
			if (!$assertionsDisabled && (termState.length < ord.length || boost.length < ord.length))
				throw new AssertionError();
			else
				return ord;
		}

		public int[] clear()
		{
			boost = null;
			termState = null;
			return super.clear();
		}


		public TermFreqBoostByteStart(int initSize)
		{
			super(initSize);
		}
	}

	final class ParallelArraysTermCollector extends TermCollectingRewrite.TermCollector
	{

		final TermFreqBoostByteStart array = new TermFreqBoostByteStart(16);
		final BytesRefHash terms;
		TermsEnum termsEnum;
		private BoostAttribute boostAtt;
		static final boolean $assertionsDisabled = !org/apache/lucene/search/ScoringRewrite.desiredAssertionStatus();
		final ScoringRewrite this$0;

		public void setNextEnum(TermsEnum termsEnum)
		{
			this.termsEnum = termsEnum;
			boostAtt = (BoostAttribute)termsEnum.attributes().addAttribute(org/apache/lucene/search/BoostAttribute);
		}

		public boolean collect(BytesRef bytes)
			throws IOException
		{
			int e = terms.add(bytes);
			TermState state = termsEnum.termState();
			if (!$assertionsDisabled && state == null)
				throw new AssertionError();
			if (e < 0)
			{
				int pos = -e - 1;
				array.termState[pos].register(state, readerContext.ord, termsEnum.docFreq(), termsEnum.totalTermFreq());
				if (!$assertionsDisabled && array.boost[pos] != boostAtt.getBoost())
					throw new AssertionError("boost should be equal in all segment TermsEnums");
			} else
			{
				array.boost[e] = boostAtt.getBoost();
				array.termState[e] = new TermContext(topReaderContext, state, readerContext.ord, termsEnum.docFreq(), termsEnum.totalTermFreq());
				checkMaxClauseCount(terms.size());
			}
			return true;
		}


		ParallelArraysTermCollector()
		{
			this$0 = ScoringRewrite.this;
			super();
			terms = new BytesRefHash(new ByteBlockPool(new org.apache.lucene.util.ByteBlockPool.DirectAllocator()), 16, array);
		}
	}


	public static final ScoringRewrite SCORING_BOOLEAN_QUERY_REWRITE = new ScoringRewrite() {

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

		protected void checkMaxClauseCount(int count)
		{
			if (count > BooleanQuery.getMaxClauseCount())
				throw new BooleanQuery.TooManyClauses();
			else
				return;
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

	};
	public static final MultiTermQuery.RewriteMethod CONSTANT_SCORE_BOOLEAN_QUERY_REWRITE = new MultiTermQuery.RewriteMethod() {

		public Query rewrite(IndexReader reader, MultiTermQuery query)
			throws IOException
		{
			BooleanQuery bq = (BooleanQuery)ScoringRewrite.SCORING_BOOLEAN_QUERY_REWRITE.rewrite(reader, query);
			if (bq.clauses().isEmpty())
			{
				return bq;
			} else
			{
				Query result = new ConstantScoreQuery(bq);
				result.setBoost(query.getBoost());
				return result;
			}
		}

	};
	static final boolean $assertionsDisabled = !org/apache/lucene/search/ScoringRewrite.desiredAssertionStatus();

	public ScoringRewrite()
	{
	}

	protected abstract void checkMaxClauseCount(int i)
		throws IOException;

	public final Query rewrite(IndexReader reader, MultiTermQuery query)
		throws IOException
	{
		Query result = getTopLevelQuery();
		ParallelArraysTermCollector col = new ParallelArraysTermCollector();
		collectTerms(reader, query, col);
		int size = col.terms.size();
		if (size > 0)
		{
			int sort[] = col.terms.sort(col.termsEnum.getComparator());
			float boost[] = col.array.boost;
			TermContext termStates[] = col.array.termState;
			for (int i = 0; i < size; i++)
			{
				int pos = sort[i];
				Term term = new Term(query.getField(), col.terms.get(pos, new BytesRef()));
				if (!$assertionsDisabled && reader.docFreq(term) != termStates[pos].docFreq())
					throw new AssertionError();
				addClause(result, term, termStates[pos].docFreq(), query.getBoost() * boost[pos], termStates[pos]);
			}

		}
		return result;
	}

}
