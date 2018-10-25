// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SpanScorer.java

package org.apache.lucene.search.spans;

import java.io.IOException;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.Weight;
import org.apache.lucene.search.similarities.Similarity;

// Referenced classes of package org.apache.lucene.search.spans:
//			Spans

public class SpanScorer extends Scorer
{

	protected Spans spans;
	protected boolean more;
	protected int doc;
	protected float freq;
	protected final org.apache.lucene.search.similarities.Similarity.SloppySimScorer docScorer;

	protected SpanScorer(Spans spans, Weight weight, org.apache.lucene.search.similarities.Similarity.SloppySimScorer docScorer)
		throws IOException
	{
		super(weight);
		more = true;
		this.docScorer = docScorer;
		this.spans = spans;
		if (this.spans.next())
		{
			doc = -1;
		} else
		{
			doc = 0x7fffffff;
			more = false;
		}
	}

	public int nextDoc()
		throws IOException
	{
		if (!setFreqCurrentDoc())
			doc = 0x7fffffff;
		return doc;
	}

	public int advance(int target)
		throws IOException
	{
		if (!more)
			return doc = 0x7fffffff;
		if (spans.doc() < target)
			more = spans.skipTo(target);
		if (!setFreqCurrentDoc())
			doc = 0x7fffffff;
		return doc;
	}

	protected boolean setFreqCurrentDoc()
		throws IOException
	{
		if (!more)
			return false;
		doc = spans.doc();
		freq = 0.0F;
		do
		{
			int matchLength = spans.end() - spans.start();
			freq += docScorer.computeSlopFactor(matchLength);
			more = spans.next();
		} while (more && doc == spans.doc());
		return true;
	}

	public int docID()
	{
		return doc;
	}

	public float score()
		throws IOException
	{
		return docScorer.score(doc, freq);
	}

	public float freq()
		throws IOException
	{
		return freq;
	}
}
