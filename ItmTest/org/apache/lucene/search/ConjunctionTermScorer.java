// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ConjunctionTermScorer.java

package org.apache.lucene.search;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.index.DocsEnum;
import org.apache.lucene.util.ArrayUtil;

// Referenced classes of package org.apache.lucene.search:
//			Scorer, Weight, TermScorer

class ConjunctionTermScorer extends Scorer
{
	static final class DocsAndFreqs
	{

		final DocsEnum docs;
		final int docFreq;
		final Scorer scorer;
		int doc;

		DocsAndFreqs(TermScorer termScorer)
		{
			this(((Scorer) (termScorer)), termScorer.getDocsEnum(), termScorer.getDocFreq());
		}

		DocsAndFreqs(Scorer scorer, DocsEnum docs, int docFreq)
		{
			doc = -1;
			this.docs = docs;
			this.docFreq = docFreq;
			this.scorer = scorer;
		}
	}


	protected final float coord;
	protected int lastDoc;
	protected final DocsAndFreqs docsAndFreqs[];
	private final DocsAndFreqs lead;

	ConjunctionTermScorer(Weight weight, float coord, DocsAndFreqs docsAndFreqs[])
	{
		super(weight);
		lastDoc = -1;
		this.coord = coord;
		this.docsAndFreqs = docsAndFreqs;
		ArrayUtil.mergeSort(docsAndFreqs, new Comparator() {

			final ConjunctionTermScorer this$0;

			public int compare(DocsAndFreqs o1, DocsAndFreqs o2)
			{
				return o1.docFreq - o2.docFreq;
			}

			public volatile int compare(Object x0, Object x1)
			{
				return compare((DocsAndFreqs)x0, (DocsAndFreqs)x1);
			}

			
			{
				this$0 = ConjunctionTermScorer.this;
				super();
			}
		});
		lead = docsAndFreqs[0];
	}

	private int doNext(int doc)
		throws IOException
	{
		do
		{
label0:
			{
				if (lead.doc == 0x7fffffff)
					return 0x7fffffff;
				for (int i = 1; i < docsAndFreqs.length; i++)
				{
					if (docsAndFreqs[i].doc < doc)
						docsAndFreqs[i].doc = docsAndFreqs[i].docs.advance(doc);
					if (docsAndFreqs[i].doc > doc)
						break label0;
				}

				return doc;
			}
			doc = lead.doc = lead.docs.nextDoc();
		} while (true);
	}

	public int advance(int target)
		throws IOException
	{
		lead.doc = lead.docs.advance(target);
		return lastDoc = doNext(lead.doc);
	}

	public int docID()
	{
		return lastDoc;
	}

	public int nextDoc()
		throws IOException
	{
		lead.doc = lead.docs.nextDoc();
		return lastDoc = doNext(lead.doc);
	}

	public float score()
		throws IOException
	{
		float sum = 0.0F;
		DocsAndFreqs arr$[] = docsAndFreqs;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			DocsAndFreqs docs = arr$[i$];
			sum += docs.scorer.score();
		}

		return sum * coord;
	}

	public float freq()
	{
		return (float)docsAndFreqs.length;
	}

	public Collection getChildren()
	{
		ArrayList children = new ArrayList(docsAndFreqs.length);
		DocsAndFreqs arr$[] = docsAndFreqs;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			DocsAndFreqs docs = arr$[i$];
			children.add(new Scorer.ChildScorer(docs.scorer, "MUST"));
		}

		return children;
	}
}
