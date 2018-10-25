// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TermScorer.java

package org.apache.lucene.search;

import java.io.IOException;
import org.apache.lucene.index.DocsEnum;
import org.apache.lucene.search.similarities.Similarity;

// Referenced classes of package org.apache.lucene.search:
//			Scorer, Weight

final class TermScorer extends Scorer
{

	private final DocsEnum docsEnum;
	private final org.apache.lucene.search.similarities.Similarity.ExactSimScorer docScorer;
	private final int docFreq;
	static final boolean $assertionsDisabled = !org/apache/lucene/search/TermScorer.desiredAssertionStatus();

	TermScorer(Weight weight, DocsEnum td, org.apache.lucene.search.similarities.Similarity.ExactSimScorer docScorer, int docFreq)
	{
		super(weight);
		this.docScorer = docScorer;
		docsEnum = td;
		this.docFreq = docFreq;
	}

	public int docID()
	{
		return docsEnum.docID();
	}

	public float freq()
		throws IOException
	{
		return (float)docsEnum.freq();
	}

	public int nextDoc()
		throws IOException
	{
		return docsEnum.nextDoc();
	}

	public float score()
		throws IOException
	{
		if (!$assertionsDisabled && docID() == 0x7fffffff)
			throw new AssertionError();
		else
			return docScorer.score(docsEnum.docID(), docsEnum.freq());
	}

	public int advance(int target)
		throws IOException
	{
		return docsEnum.advance(target);
	}

	public String toString()
	{
		return (new StringBuilder()).append("scorer(").append(weight).append(")").toString();
	}

	DocsEnum getDocsEnum()
	{
		return docsEnum;
	}

	int getDocFreq()
	{
		return docFreq;
	}

}
