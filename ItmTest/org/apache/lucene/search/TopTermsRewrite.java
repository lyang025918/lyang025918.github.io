// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TopTermsRewrite.java

package org.apache.lucene.search;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.index.*;
import org.apache.lucene.util.*;

// Referenced classes of package org.apache.lucene.search:
//			TermCollectingRewrite, MultiTermQuery, Query, MaxNonCompetitiveBoostAttribute, 
//			BoostAttribute

public abstract class TopTermsRewrite extends TermCollectingRewrite
{
	static final class ScoreTerm
		implements Comparable
	{

		public final Comparator termComp;
		public final BytesRef bytes = new BytesRef();
		public float boost;
		public final TermContext termState;

		public int compareTo(ScoreTerm other)
		{
			if (boost == other.boost)
				return termComp.compare(other.bytes, bytes);
			else
				return Float.compare(boost, other.boost);
		}

		public volatile int compareTo(Object x0)
		{
			return compareTo((ScoreTerm)x0);
		}

		public ScoreTerm(Comparator termComp, TermContext termState)
		{
			this.termComp = termComp;
			this.termState = termState;
		}
	}


	private final int size;
	private static final Comparator scoreTermSortByTermComp = new Comparator() {

		static final boolean $assertionsDisabled = !org/apache/lucene/search/TopTermsRewrite.desiredAssertionStatus();

		public int compare(ScoreTerm st1, ScoreTerm st2)
		{
			if (!$assertionsDisabled && st1.termComp != st2.termComp)
				throw new AssertionError("term comparator should not change between segments");
			else
				return st1.termComp.compare(st1.bytes, st2.bytes);
		}

		public volatile int compare(Object x0, Object x1)
		{
			return compare((ScoreTerm)x0, (ScoreTerm)x1);
		}


	};
	static final boolean $assertionsDisabled = !org/apache/lucene/search/TopTermsRewrite.desiredAssertionStatus();

	public TopTermsRewrite(int size)
	{
		this.size = size;
	}

	public int getSize()
	{
		return size;
	}

	protected abstract int getMaxSize();

	public final Query rewrite(IndexReader reader, MultiTermQuery query)
		throws IOException
	{
		final int maxSize = Math.min(size, getMaxSize());
		final PriorityQueue stQueue = new PriorityQueue();
		collectTerms(reader, query, new TermCollectingRewrite.TermCollector() {

			private final MaxNonCompetitiveBoostAttribute maxBoostAtt;
			private final Map visitedTerms = new HashMap();
			private TermsEnum termsEnum;
			private Comparator termComp;
			private BoostAttribute boostAtt;
			private ScoreTerm st;
			private BytesRef lastTerm;
			static final boolean $assertionsDisabled = !org/apache/lucene/search/TopTermsRewrite.desiredAssertionStatus();
			final PriorityQueue val$stQueue;
			final int val$maxSize;
			final TopTermsRewrite this$0;

			public void setNextEnum(TermsEnum termsEnum)
			{
				this.termsEnum = termsEnum;
				termComp = termsEnum.getComparator();
				if (!$assertionsDisabled && !compareToLastTerm(null))
					throw new AssertionError();
				if (st == null)
					st = new ScoreTerm(termComp, new TermContext(topReaderContext));
				boostAtt = (BoostAttribute)termsEnum.attributes().addAttribute(org/apache/lucene/search/BoostAttribute);
			}

			private boolean compareToLastTerm(BytesRef t)
			{
				if (lastTerm == null && t != null)
					lastTerm = BytesRef.deepCopyOf(t);
				else
				if (t == null)
				{
					lastTerm = null;
				} else
				{
					if (!$assertionsDisabled && termsEnum.getComparator().compare(lastTerm, t) >= 0)
						throw new AssertionError((new StringBuilder()).append("lastTerm=").append(lastTerm).append(" t=").append(t).toString());
					lastTerm.copyBytes(t);
				}
				return true;
			}

			public boolean collect(BytesRef bytes)
				throws IOException
			{
				float boost = boostAtt.getBoost();
				if (!$assertionsDisabled && !compareToLastTerm(bytes))
					throw new AssertionError();
				ScoreTerm t;
				if (stQueue.size() == maxSize)
				{
					t = (ScoreTerm)stQueue.peek();
					if (boost < t.boost)
						return true;
					if (boost == t.boost && termComp.compare(bytes, t.bytes) > 0)
						return true;
				}
				t = (ScoreTerm)visitedTerms.get(bytes);
				TermState state = termsEnum.termState();
				if (!$assertionsDisabled && state == null)
					throw new AssertionError();
				if (t != null)
				{
					if (!$assertionsDisabled && t.boost != boost)
						throw new AssertionError("boost should be equal in all segment TermsEnums");
					t.termState.register(state, readerContext.ord, termsEnum.docFreq(), termsEnum.totalTermFreq());
				} else
				{
					st.bytes.copyBytes(bytes);
					st.boost = boost;
					visitedTerms.put(st.bytes, st);
					if (!$assertionsDisabled && st.termState.docFreq() != 0)
						throw new AssertionError();
					st.termState.register(state, readerContext.ord, termsEnum.docFreq(), termsEnum.totalTermFreq());
					stQueue.offer(st);
					if (stQueue.size() > maxSize)
					{
						st = (ScoreTerm)stQueue.poll();
						visitedTerms.remove(st.bytes);
						st.termState.clear();
					} else
					{
						st = new ScoreTerm(termComp, new TermContext(topReaderContext));
					}
					if (!$assertionsDisabled && stQueue.size() > maxSize)
						throw new AssertionError("the PQ size must be limited to maxSize");
					if (stQueue.size() == maxSize)
					{
						t = (ScoreTerm)stQueue.peek();
						maxBoostAtt.setMaxNonCompetitiveBoost(t.boost);
						maxBoostAtt.setCompetitiveTerm(t.bytes);
					}
				}
				return true;
			}


			
			{
				this$0 = TopTermsRewrite.this;
				stQueue = priorityqueue;
				maxSize = i;
				super();
				maxBoostAtt = (MaxNonCompetitiveBoostAttribute)attributes.addAttribute(org/apache/lucene/search/MaxNonCompetitiveBoostAttribute);
			}
		});
		Query q = getTopLevelQuery();
		ScoreTerm scoreTerms[] = (ScoreTerm[])stQueue.toArray(new ScoreTerm[stQueue.size()]);
		ArrayUtil.mergeSort(scoreTerms, scoreTermSortByTermComp);
		ScoreTerm arr$[] = scoreTerms;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			ScoreTerm st = arr$[i$];
			Term term = new Term(query.field, st.bytes);
			if (!$assertionsDisabled && reader.docFreq(term) != st.termState.docFreq())
				throw new AssertionError((new StringBuilder()).append("reader DF is ").append(reader.docFreq(term)).append(" vs ").append(st.termState.docFreq()).append(" term=").append(term).toString());
			addClause(q, term, st.termState.docFreq(), query.getBoost() * st.boost, st.termState);
		}

		return q;
	}

	public int hashCode()
	{
		return 31 * size;
	}

	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TopTermsRewrite other = (TopTermsRewrite)obj;
		return size == other.size;
	}

}
