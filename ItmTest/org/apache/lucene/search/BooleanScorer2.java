// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BooleanScorer2.java

package org.apache.lucene.search;

import java.io.IOException;
import java.util.*;

// Referenced classes of package org.apache.lucene.search:
//			Scorer, ConjunctionScorer, ReqOptSumScorer, ReqExclScorer, 
//			DisjunctionSumScorer, Collector, BooleanQuery, Weight

class BooleanScorer2 extends Scorer
{
	private class SingleMatchScorer extends Scorer
	{

		private Scorer scorer;
		private int lastScoredDoc;
		private float lastDocScore;
		final BooleanScorer2 this$0;

		public float score()
			throws IOException
		{
			int doc = docID();
			if (doc >= lastScoredDoc)
			{
				if (doc > lastScoredDoc)
				{
					lastDocScore = scorer.score();
					lastScoredDoc = doc;
				}
				coordinator.nrMatchers++;
			}
			return lastDocScore;
		}

		public float freq()
			throws IOException
		{
			return 1.0F;
		}

		public int docID()
		{
			return scorer.docID();
		}

		public int nextDoc()
			throws IOException
		{
			return scorer.nextDoc();
		}

		public int advance(int target)
			throws IOException
		{
			return scorer.advance(target);
		}

		SingleMatchScorer(Scorer scorer)
		{
			this$0 = BooleanScorer2.this;
			super(scorer.weight);
			lastScoredDoc = -1;
			lastDocScore = (0.0F / 0.0F);
			this.scorer = scorer;
		}
	}

	private class Coordinator
	{

		final float coordFactors[];
		int nrMatchers;
		final BooleanScorer2 this$0;

		Coordinator(int maxCoord, boolean disableCoord)
		{
			this$0 = BooleanScorer2.this;
			super();
			coordFactors = new float[optionalScorers.size() + requiredScorers.size() + 1];
			for (int i = 0; i < coordFactors.length; i++)
				coordFactors[i] = disableCoord ? 1.0F : ((BooleanQuery.BooleanWeight)weight).coord(i, maxCoord);

		}
	}


	private final List requiredScorers;
	private final List optionalScorers;
	private final List prohibitedScorers;
	private final Coordinator coordinator;
	private final Scorer countingSumScorer;
	private final int minNrShouldMatch;
	private int doc;

	public BooleanScorer2(BooleanQuery.BooleanWeight weight, boolean disableCoord, int minNrShouldMatch, List required, List prohibited, List optional, int maxCoord)
		throws IOException
	{
		super(weight);
		doc = -1;
		if (minNrShouldMatch < 0)
		{
			throw new IllegalArgumentException("Minimum number of optional scorers should not be negative");
		} else
		{
			this.minNrShouldMatch = minNrShouldMatch;
			optionalScorers = optional;
			requiredScorers = required;
			prohibitedScorers = prohibited;
			coordinator = new Coordinator(maxCoord, disableCoord);
			countingSumScorer = makeCountingSumScorer(disableCoord);
			return;
		}
	}

	private Scorer countingDisjunctionSumScorer(List scorers, int minNrShouldMatch)
		throws IOException
	{
		return new DisjunctionSumScorer(weight, scorers, minNrShouldMatch) {

			private int lastScoredDoc;
			private float lastDocScore;
			final BooleanScorer2 this$0;

			public float score()
				throws IOException
			{
				int doc = docID();
				if (doc >= lastScoredDoc)
				{
					if (doc > lastScoredDoc)
					{
						lastDocScore = super.score();
						lastScoredDoc = doc;
					}
					coordinator.nrMatchers += super.nrMatchers;
				}
				return lastDocScore;
			}

			
			{
				this$0 = BooleanScorer2.this;
				super(x0, x1, x2);
				lastScoredDoc = -1;
				lastDocScore = (0.0F / 0.0F);
			}
		};
	}

	private Scorer countingConjunctionSumScorer(boolean disableCoord, List requiredScorers)
		throws IOException
	{
		int requiredNrMatchers = requiredScorers.size();
		return new ConjunctionScorer(requiredScorers, requiredNrMatchers) {

			private int lastScoredDoc;
			private float lastDocScore;
			final int val$requiredNrMatchers;
			final BooleanScorer2 this$0;

			public float score()
				throws IOException
			{
				int doc = docID();
				if (doc >= lastScoredDoc)
				{
					if (doc > lastScoredDoc)
					{
						lastDocScore = super.score();
						lastScoredDoc = doc;
					}
					coordinator.nrMatchers += requiredNrMatchers;
				}
				return lastDocScore;
			}

			
			{
				this$0 = BooleanScorer2.this;
				requiredNrMatchers = i;
				super(x0, x1);
				lastScoredDoc = -1;
				lastDocScore = (0.0F / 0.0F);
			}
		};
	}

	private Scorer dualConjunctionSumScorer(boolean disableCoord, Scorer req1, Scorer req2)
		throws IOException
	{
		return new ConjunctionScorer(weight, new Scorer[] {
			req1, req2
		});
	}

	private Scorer makeCountingSumScorer(boolean disableCoord)
		throws IOException
	{
		return requiredScorers.size() != 0 ? makeCountingSumScorerSomeReq(disableCoord) : makeCountingSumScorerNoReq(disableCoord);
	}

	private Scorer makeCountingSumScorerNoReq(boolean disableCoord)
		throws IOException
	{
		int nrOptRequired = minNrShouldMatch >= 1 ? minNrShouldMatch : 1;
		Scorer requiredCountingSumScorer;
		if (optionalScorers.size() > nrOptRequired)
			requiredCountingSumScorer = countingDisjunctionSumScorer(optionalScorers, nrOptRequired);
		else
		if (optionalScorers.size() == 1)
			requiredCountingSumScorer = new SingleMatchScorer((Scorer)optionalScorers.get(0));
		else
			requiredCountingSumScorer = countingConjunctionSumScorer(disableCoord, optionalScorers);
		return addProhibitedScorers(requiredCountingSumScorer);
	}

	private Scorer makeCountingSumScorerSomeReq(boolean disableCoord)
		throws IOException
	{
		if (optionalScorers.size() == minNrShouldMatch)
		{
			ArrayList allReq = new ArrayList(requiredScorers);
			allReq.addAll(optionalScorers);
			return addProhibitedScorers(countingConjunctionSumScorer(disableCoord, allReq));
		}
		Scorer requiredCountingSumScorer = ((Scorer) (requiredScorers.size() != 1 ? countingConjunctionSumScorer(disableCoord, requiredScorers) : ((Scorer) (new SingleMatchScorer((Scorer)requiredScorers.get(0))))));
		if (minNrShouldMatch > 0)
			return addProhibitedScorers(dualConjunctionSumScorer(disableCoord, requiredCountingSumScorer, countingDisjunctionSumScorer(optionalScorers, minNrShouldMatch)));
		else
			return new ReqOptSumScorer(addProhibitedScorers(requiredCountingSumScorer), ((Scorer) (optionalScorers.size() != 1 ? countingDisjunctionSumScorer(optionalScorers, 1) : ((Scorer) (new SingleMatchScorer((Scorer)optionalScorers.get(0)))))));
	}

	private Scorer addProhibitedScorers(Scorer requiredCountingSumScorer)
		throws IOException
	{
		return ((Scorer) (prohibitedScorers.size() != 0 ? new ReqExclScorer(requiredCountingSumScorer, ((DocIdSetIterator) (prohibitedScorers.size() != 1 ? ((DocIdSetIterator) (new DisjunctionSumScorer(weight, prohibitedScorers))) : ((DocIdSetIterator) ((Scorer)prohibitedScorers.get(0)))))) : requiredCountingSumScorer));
	}

	public void score(Collector collector)
		throws IOException
	{
		collector.setScorer(this);
		while ((doc = countingSumScorer.nextDoc()) != 0x7fffffff) 
			collector.collect(doc);
	}

	public boolean score(Collector collector, int max, int firstDocID)
		throws IOException
	{
		doc = firstDocID;
		collector.setScorer(this);
		for (; doc < max; doc = countingSumScorer.nextDoc())
			collector.collect(doc);

		return doc != 0x7fffffff;
	}

	public int docID()
	{
		return doc;
	}

	public int nextDoc()
		throws IOException
	{
		return doc = countingSumScorer.nextDoc();
	}

	public float score()
		throws IOException
	{
		coordinator.nrMatchers = 0;
		float sum = countingSumScorer.score();
		return sum * coordinator.coordFactors[coordinator.nrMatchers];
	}

	public float freq()
		throws IOException
	{
		return countingSumScorer.freq();
	}

	public int advance(int target)
		throws IOException
	{
		return doc = countingSumScorer.advance(target);
	}

	public Collection getChildren()
	{
		ArrayList children = new ArrayList();
		Scorer s;
		for (Iterator i$ = optionalScorers.iterator(); i$.hasNext(); children.add(new Scorer.ChildScorer(s, "SHOULD")))
			s = (Scorer)i$.next();

		Scorer s;
		for (Iterator i$ = prohibitedScorers.iterator(); i$.hasNext(); children.add(new Scorer.ChildScorer(s, "MUST_NOT")))
			s = (Scorer)i$.next();

		Scorer s;
		for (Iterator i$ = requiredScorers.iterator(); i$.hasNext(); children.add(new Scorer.ChildScorer(s, "MUST")))
			s = (Scorer)i$.next();

		return children;
	}



}
