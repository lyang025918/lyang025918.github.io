// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ReqOptSumScorer.java

package org.apache.lucene.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

// Referenced classes of package org.apache.lucene.search:
//			Scorer

class ReqOptSumScorer extends Scorer
{

	private Scorer reqScorer;
	private Scorer optScorer;
	static final boolean $assertionsDisabled = !org/apache/lucene/search/ReqOptSumScorer.desiredAssertionStatus();

	public ReqOptSumScorer(Scorer reqScorer, Scorer optScorer)
	{
		super(reqScorer.weight);
		if (!$assertionsDisabled && reqScorer == null)
			throw new AssertionError();
		if (!$assertionsDisabled && optScorer == null)
		{
			throw new AssertionError();
		} else
		{
			this.reqScorer = reqScorer;
			this.optScorer = optScorer;
			return;
		}
	}

	public int nextDoc()
		throws IOException
	{
		return reqScorer.nextDoc();
	}

	public int advance(int target)
		throws IOException
	{
		return reqScorer.advance(target);
	}

	public int docID()
	{
		return reqScorer.docID();
	}

	public float score()
		throws IOException
	{
		int curDoc = reqScorer.docID();
		float reqScore = reqScorer.score();
		if (optScorer == null)
			return reqScore;
		int optScorerDoc = optScorer.docID();
		if (optScorerDoc < curDoc && (optScorerDoc = optScorer.advance(curDoc)) == 0x7fffffff)
		{
			optScorer = null;
			return reqScore;
		} else
		{
			return optScorerDoc != curDoc ? reqScore : reqScore + optScorer.score();
		}
	}

	public float freq()
		throws IOException
	{
		score();
		return optScorer == null || optScorer.docID() != reqScorer.docID() ? 1.0F : 2.0F;
	}

	public Collection getChildren()
	{
		ArrayList children = new ArrayList(2);
		children.add(new Scorer.ChildScorer(reqScorer, "MUST"));
		children.add(new Scorer.ChildScorer(optScorer, "SHOULD"));
		return children;
	}

}
