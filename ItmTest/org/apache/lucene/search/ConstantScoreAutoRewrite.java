// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ConstantScoreAutoRewrite.java

package org.apache.lucene.search;

import java.io.IOException;
import org.apache.lucene.index.*;
import org.apache.lucene.util.*;

// Referenced classes of package org.apache.lucene.search:
//			TermCollectingRewrite, BooleanQuery, TermQuery, ConstantScoreQuery, 
//			MultiTermQuery, Query, BooleanClause

class ConstantScoreAutoRewrite extends TermCollectingRewrite
{
	static final class TermStateByteStart extends org.apache.lucene.util.BytesRefHash.DirectBytesStartArray
	{

		TermContext termState[];
		static final boolean $assertionsDisabled = !org/apache/lucene/search/ConstantScoreAutoRewrite.desiredAssertionStatus();

		public int[] init()
		{
			int ord[] = super.init();
			termState = new TermContext[ArrayUtil.oversize(ord.length, RamUsageEstimator.NUM_BYTES_OBJECT_REF)];
			if (!$assertionsDisabled && termState.length < ord.length)
				throw new AssertionError();
			else
				return ord;
		}

		public int[] grow()
		{
			int ord[] = super.grow();
			if (termState.length < ord.length)
			{
				TermContext tmpTermState[] = new TermContext[ArrayUtil.oversize(ord.length, RamUsageEstimator.NUM_BYTES_OBJECT_REF)];
				System.arraycopy(termState, 0, tmpTermState, 0, termState.length);
				termState = tmpTermState;
			}
			if (!$assertionsDisabled && termState.length < ord.length)
				throw new AssertionError();
			else
				return ord;
		}

		public int[] clear()
		{
			termState = null;
			return super.clear();
		}


		public TermStateByteStart(int initSize)
		{
			super(initSize);
		}
	}

	static final class CutOffTermCollector extends TermCollectingRewrite.TermCollector
	{

		int docVisitCount;
		boolean hasCutOff;
		TermsEnum termsEnum;
		final int docCountCutoff;
		final int termCountLimit;
		final TermStateByteStart array = new TermStateByteStart(16);
		final BytesRefHash pendingTerms;
		static final boolean $assertionsDisabled = !org/apache/lucene/search/ConstantScoreAutoRewrite.desiredAssertionStatus();

		public void setNextEnum(TermsEnum termsEnum)
		{
			this.termsEnum = termsEnum;
		}

		public boolean collect(BytesRef bytes)
			throws IOException
		{
			int pos = pendingTerms.add(bytes);
			docVisitCount += termsEnum.docFreq();
			if (pendingTerms.size() >= termCountLimit || docVisitCount >= docCountCutoff)
			{
				hasCutOff = true;
				return false;
			}
			TermState termState = termsEnum.termState();
			if (!$assertionsDisabled && termState == null)
				throw new AssertionError();
			if (pos < 0)
			{
				pos = -pos - 1;
				array.termState[pos].register(termState, readerContext.ord, termsEnum.docFreq(), termsEnum.totalTermFreq());
			} else
			{
				array.termState[pos] = new TermContext(topReaderContext, termState, readerContext.ord, termsEnum.docFreq(), termsEnum.totalTermFreq());
			}
			return true;
		}


		CutOffTermCollector(int docCountCutoff, int termCountLimit)
		{
			docVisitCount = 0;
			hasCutOff = false;
			pendingTerms = new BytesRefHash(new ByteBlockPool(new org.apache.lucene.util.ByteBlockPool.DirectAllocator()), 16, array);
			this.docCountCutoff = docCountCutoff;
			this.termCountLimit = termCountLimit;
		}
	}


	public static int DEFAULT_TERM_COUNT_CUTOFF = 350;
	public static double DEFAULT_DOC_COUNT_PERCENT = 0.10000000000000001D;
	private int termCountCutoff;
	private double docCountPercent;

	ConstantScoreAutoRewrite()
	{
		termCountCutoff = DEFAULT_TERM_COUNT_CUTOFF;
		docCountPercent = DEFAULT_DOC_COUNT_PERCENT;
	}

	public void setTermCountCutoff(int count)
	{
		termCountCutoff = count;
	}

	public int getTermCountCutoff()
	{
		return termCountCutoff;
	}

	public void setDocCountPercent(double percent)
	{
		docCountPercent = percent;
	}

	public double getDocCountPercent()
	{
		return docCountPercent;
	}

	protected BooleanQuery getTopLevelQuery()
	{
		return new BooleanQuery(true);
	}

	protected void addClause(BooleanQuery topLevel, Term term, int docFreq, float boost, TermContext states)
	{
		topLevel.add(new TermQuery(term, states), BooleanClause.Occur.SHOULD);
	}

	public Query rewrite(IndexReader reader, MultiTermQuery query)
		throws IOException
	{
		int docCountCutoff = (int)((docCountPercent / 100D) * (double)reader.maxDoc());
		int termCountLimit = Math.min(BooleanQuery.getMaxClauseCount(), termCountCutoff);
		CutOffTermCollector col = new CutOffTermCollector(docCountCutoff, termCountLimit);
		collectTerms(reader, query, col);
		int size = col.pendingTerms.size();
		if (col.hasCutOff)
			return MultiTermQuery.CONSTANT_SCORE_FILTER_REWRITE.rewrite(reader, query);
		if (size == 0)
			return getTopLevelQuery();
		BooleanQuery bq = getTopLevelQuery();
		BytesRefHash pendingTerms = col.pendingTerms;
		int sort[] = pendingTerms.sort(col.termsEnum.getComparator());
		for (int i = 0; i < size; i++)
		{
			int pos = sort[i];
			addClause(bq, new Term(query.field, pendingTerms.get(pos, new BytesRef())), 1, 1.0F, col.array.termState[pos]);
		}

		Query result = new ConstantScoreQuery(bq);
		result.setBoost(query.getBoost());
		return result;
	}

	public int hashCode()
	{
		int prime = 1279;
		return (int)((long)(1279 * termCountCutoff) + Double.doubleToLongBits(docCountPercent));
	}

	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConstantScoreAutoRewrite other = (ConstantScoreAutoRewrite)obj;
		if (other.termCountCutoff != termCountCutoff)
			return false;
		return Double.doubleToLongBits(other.docCountPercent) == Double.doubleToLongBits(docCountPercent);
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

}
