// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ReqExclScorer.java

package org.apache.lucene.search;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

// Referenced classes of package org.apache.lucene.search:
//			Scorer, DocIdSetIterator

class ReqExclScorer extends Scorer
{

	private Scorer reqScorer;
	private DocIdSetIterator exclDisi;
	private int doc;

	public ReqExclScorer(Scorer reqScorer, DocIdSetIterator exclDisi)
	{
		super(reqScorer.weight);
		doc = -1;
		this.reqScorer = reqScorer;
		this.exclDisi = exclDisi;
	}

	public int nextDoc()
		throws IOException
	{
		if (reqScorer == null)
			return doc;
		doc = reqScorer.nextDoc();
		if (doc == 0x7fffffff)
		{
			reqScorer = null;
			return doc;
		}
		if (exclDisi == null)
			return doc;
		else
			return doc = toNonExcluded();
	}

	private int toNonExcluded()
		throws IOException
	{
		int exclDoc = exclDisi.docID();
		int reqDoc = reqScorer.docID();
		do
		{
			if (reqDoc < exclDoc)
				return reqDoc;
			if (reqDoc > exclDoc)
			{
				exclDoc = exclDisi.advance(reqDoc);
				if (exclDoc == 0x7fffffff)
				{
					exclDisi = null;
					return reqDoc;
				}
				if (exclDoc > reqDoc)
					return reqDoc;
			}
		} while ((reqDoc = reqScorer.nextDoc()) != 0x7fffffff);
		reqScorer = null;
		return 0x7fffffff;
	}

	public int docID()
	{
		return doc;
	}

	public float score()
		throws IOException
	{
		return reqScorer.score();
	}

	public float freq()
		throws IOException
	{
		return reqScorer.freq();
	}

	public Collection getChildren()
	{
		return Collections.singleton(new Scorer.ChildScorer(reqScorer, "FILTERED"));
	}

	public int advance(int target)
		throws IOException
	{
		if (reqScorer == null)
			return doc = 0x7fffffff;
		if (exclDisi == null)
			return doc = reqScorer.advance(target);
		if (reqScorer.advance(target) == 0x7fffffff)
		{
			reqScorer = null;
			return doc = 0x7fffffff;
		} else
		{
			return doc = toNonExcluded();
		}
	}
}
