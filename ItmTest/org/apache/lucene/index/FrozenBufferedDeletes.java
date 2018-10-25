// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FrozenBufferedDeletes.java

package org.apache.lucene.index;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.ArrayUtil;
import org.apache.lucene.util.RamUsageEstimator;

// Referenced classes of package org.apache.lucene.index:
//			Term, BufferedDeletes, PrefixCodedTerms, BufferedDeletesStream

class FrozenBufferedDeletes
{

	static final int BYTES_PER_DEL_QUERY;
	final PrefixCodedTerms terms;
	int termCount;
	final Query queries[];
	final int queryLimits[];
	final int bytesUsed;
	final int numTermDeletes;
	private long gen;
	final boolean isSegmentPrivate;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/FrozenBufferedDeletes.desiredAssertionStatus();

	public FrozenBufferedDeletes(BufferedDeletes deletes, boolean isSegmentPrivate)
	{
		gen = -1L;
		this.isSegmentPrivate = isSegmentPrivate;
		if (!$assertionsDisabled && isSegmentPrivate && deletes.terms.size() != 0)
			throw new AssertionError("segment private package should only have del queries");
		Term termsArray[] = (Term[])deletes.terms.keySet().toArray(new Term[deletes.terms.size()]);
		termCount = termsArray.length;
		ArrayUtil.mergeSort(termsArray);
		PrefixCodedTerms.Builder builder = new PrefixCodedTerms.Builder();
		Term arr$[] = termsArray;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			Term term = arr$[i$];
			builder.add(term);
		}

		terms = builder.finish();
		queries = new Query[deletes.queries.size()];
		queryLimits = new int[deletes.queries.size()];
		int upto = 0;
		for (Iterator i$ = deletes.queries.entrySet().iterator(); i$.hasNext();)
		{
			java.util.Map.Entry ent = (java.util.Map.Entry)i$.next();
			queries[upto] = (Query)ent.getKey();
			queryLimits[upto] = ((Integer)ent.getValue()).intValue();
			upto++;
		}

		bytesUsed = (int)terms.getSizeInBytes() + queries.length * BYTES_PER_DEL_QUERY;
		numTermDeletes = deletes.numTermDeletes.get();
	}

	public void setDelGen(long gen)
	{
		if (!$assertionsDisabled && this.gen != -1L)
		{
			throw new AssertionError();
		} else
		{
			this.gen = gen;
			return;
		}
	}

	public long delGen()
	{
		if (!$assertionsDisabled && gen == -1L)
			throw new AssertionError();
		else
			return gen;
	}

	public Iterable termsIterable()
	{
		return new Iterable() {

			final FrozenBufferedDeletes this$0;

			public Iterator iterator()
			{
				return terms.iterator();
			}

			
			{
				this$0 = FrozenBufferedDeletes.this;
				super();
			}
		};
	}

	public Iterable queriesIterable()
	{
		return new Iterable() {

			final FrozenBufferedDeletes this$0;

			public Iterator iterator()
			{
				return new Iterator() {

					private int upto;
					final 2 this$1;

					public boolean hasNext()
					{
						return upto < queries.length;
					}

					public BufferedDeletesStream.QueryAndLimit next()
					{
						BufferedDeletesStream.QueryAndLimit ret = new BufferedDeletesStream.QueryAndLimit(queries[upto], queryLimits[upto]);
						upto++;
						return ret;
					}

					public void remove()
					{
						throw new UnsupportedOperationException();
					}

					public volatile Object next()
					{
						return next();
					}

					
					{
						this$1 = 2.this;
						super();
					}
				};
			}

			
			{
				this$0 = FrozenBufferedDeletes.this;
				super();
			}
		};
	}

	public String toString()
	{
		String s = "";
		if (numTermDeletes != 0)
			s = (new StringBuilder()).append(s).append(" ").append(numTermDeletes).append(" deleted terms (unique count=").append(termCount).append(")").toString();
		if (queries.length != 0)
			s = (new StringBuilder()).append(s).append(" ").append(queries.length).append(" deleted queries").toString();
		if (bytesUsed != 0)
			s = (new StringBuilder()).append(s).append(" bytesUsed=").append(bytesUsed).toString();
		return s;
	}

	boolean any()
	{
		return termCount > 0 || queries.length > 0;
	}

	static 
	{
		BYTES_PER_DEL_QUERY = RamUsageEstimator.NUM_BYTES_OBJECT_REF + 4 + 24;
	}
}
