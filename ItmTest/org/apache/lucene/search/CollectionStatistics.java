// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CollectionStatistics.java

package org.apache.lucene.search;


public class CollectionStatistics
{

	private final String field;
	private final long maxDoc;
	private final long docCount;
	private final long sumTotalTermFreq;
	private final long sumDocFreq;
	static final boolean $assertionsDisabled = !org/apache/lucene/search/CollectionStatistics.desiredAssertionStatus();

	public CollectionStatistics(String field, long maxDoc, long docCount, long sumTotalTermFreq, 
			long sumDocFreq)
	{
		if (!$assertionsDisabled && maxDoc < 0L)
			throw new AssertionError();
		if (!$assertionsDisabled && (docCount < -1L || docCount > maxDoc))
			throw new AssertionError();
		if (!$assertionsDisabled && sumDocFreq != -1L && sumDocFreq < docCount)
			throw new AssertionError();
		if (!$assertionsDisabled && sumTotalTermFreq != -1L && sumTotalTermFreq < sumDocFreq)
		{
			throw new AssertionError();
		} else
		{
			this.field = field;
			this.maxDoc = maxDoc;
			this.docCount = docCount;
			this.sumTotalTermFreq = sumTotalTermFreq;
			this.sumDocFreq = sumDocFreq;
			return;
		}
	}

	public final String field()
	{
		return field;
	}

	public final long maxDoc()
	{
		return maxDoc;
	}

	public final long docCount()
	{
		return docCount;
	}

	public final long sumTotalTermFreq()
	{
		return sumTotalTermFreq;
	}

	public final long sumDocFreq()
	{
		return sumDocFreq;
	}

}
