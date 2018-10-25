// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TermStatistics.java

package org.apache.lucene.search;

import org.apache.lucene.util.BytesRef;

public class TermStatistics
{

	private final BytesRef term;
	private final long docFreq;
	private final long totalTermFreq;
	static final boolean $assertionsDisabled = !org/apache/lucene/search/TermStatistics.desiredAssertionStatus();

	public TermStatistics(BytesRef term, long docFreq, long totalTermFreq)
	{
		if (!$assertionsDisabled && docFreq < 0L)
			throw new AssertionError();
		if (!$assertionsDisabled && totalTermFreq != -1L && totalTermFreq < docFreq)
		{
			throw new AssertionError();
		} else
		{
			this.term = term;
			this.docFreq = docFreq;
			this.totalTermFreq = totalTermFreq;
			return;
		}
	}

	public final BytesRef term()
	{
		return term;
	}

	public final long docFreq()
	{
		return docFreq;
	}

	public final long totalTermFreq()
	{
		return totalTermFreq;
	}

}
