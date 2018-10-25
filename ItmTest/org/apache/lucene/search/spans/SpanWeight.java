// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SpanWeight.java

package org.apache.lucene.search.spans;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.util.Bits;

// Referenced classes of package org.apache.lucene.search.spans:
//			SpanScorer, SpanQuery

public class SpanWeight extends Weight
{

	protected Similarity similarity;
	protected Map termContexts;
	protected SpanQuery query;
	protected org.apache.lucene.search.similarities.Similarity.SimWeight stats;

	public SpanWeight(SpanQuery query, IndexSearcher searcher)
		throws IOException
	{
		similarity = searcher.getSimilarity();
		this.query = query;
		termContexts = new HashMap();
		TreeSet terms = new TreeSet();
		query.extractTerms(terms);
		IndexReaderContext context = searcher.getTopReaderContext();
		TermStatistics termStats[] = new TermStatistics[terms.size()];
		int i = 0;
		for (Iterator i$ = terms.iterator(); i$.hasNext();)
		{
			Term term = (Term)i$.next();
			TermContext state = TermContext.build(context, term, true);
			termStats[i] = searcher.termStatistics(term, state);
			termContexts.put(term, state);
			i++;
		}

		String field = query.getField();
		if (field != null)
			stats = similarity.computeWeight(query.getBoost(), searcher.collectionStatistics(query.getField()), termStats);
	}

	public Query getQuery()
	{
		return query;
	}

	public float getValueForNormalization()
		throws IOException
	{
		return stats != null ? stats.getValueForNormalization() : 1.0F;
	}

	public void normalize(float queryNorm, float topLevelBoost)
	{
		if (stats != null)
			stats.normalize(queryNorm, topLevelBoost);
	}

	public Scorer scorer(AtomicReaderContext context, boolean scoreDocsInOrder, boolean topScorer, Bits acceptDocs)
		throws IOException
	{
		if (stats == null)
			return null;
		else
			return new SpanScorer(query.getSpans(context, acceptDocs, termContexts), this, similarity.sloppySimScorer(stats, context));
	}

	public Explanation explain(AtomicReaderContext context, int doc)
		throws IOException
	{
		Scorer scorer = scorer(context, true, false, context.reader().getLiveDocs());
		if (scorer != null)
		{
			int newDoc = scorer.advance(doc);
			if (newDoc == doc)
			{
				float freq = scorer.freq();
				org.apache.lucene.search.similarities.Similarity.SloppySimScorer docScorer = similarity.sloppySimScorer(stats, context);
				ComplexExplanation result = new ComplexExplanation();
				result.setDescription((new StringBuilder()).append("weight(").append(getQuery()).append(" in ").append(doc).append(") [").append(similarity.getClass().getSimpleName()).append("], result of:").toString());
				Explanation scoreExplanation = docScorer.explain(doc, new Explanation(freq, (new StringBuilder()).append("phraseFreq=").append(freq).toString()));
				result.addDetail(scoreExplanation);
				result.setValue(scoreExplanation.getValue());
				result.setMatch(Boolean.valueOf(true));
				return result;
			}
		}
		return new ComplexExplanation(false, 0.0F, "no matching term");
	}
}
