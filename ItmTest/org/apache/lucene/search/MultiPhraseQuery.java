// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MultiPhraseQuery.java

package org.apache.lucene.search;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.index.*;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.util.*;

// Referenced classes of package org.apache.lucene.search:
//			Query, BooleanQuery, TermQuery, BooleanClause, 
//			IndexSearcher, Weight, TermStatistics, UnionDocsAndPositionsEnum, 
//			ExactPhraseScorer, SloppyPhraseScorer, ComplexExplanation, Explanation, 
//			Scorer, PhraseQuery

public class MultiPhraseQuery extends Query
{
	private class MultiPhraseWeight extends Weight
	{

		private final Similarity similarity;
		private final org.apache.lucene.search.similarities.Similarity.SimWeight stats;
		private final Map termContexts = new HashMap();
		static final boolean $assertionsDisabled = !org/apache/lucene/search/MultiPhraseQuery.desiredAssertionStatus();
		final MultiPhraseQuery this$0;

		public Query getQuery()
		{
			return MultiPhraseQuery.this;
		}

		public float getValueForNormalization()
		{
			return stats.getValueForNormalization();
		}

		public void normalize(float queryNorm, float topLevelBoost)
		{
			stats.normalize(queryNorm, topLevelBoost);
		}

		public Scorer scorer(AtomicReaderContext context, boolean scoreDocsInOrder, boolean topScorer, Bits acceptDocs)
			throws IOException
		{
			if (!$assertionsDisabled && termArrays.isEmpty())
				throw new AssertionError();
			AtomicReader reader = context.reader();
			Bits liveDocs = acceptDocs;
			PhraseQuery.PostingsAndFreq postingsFreqs[] = new PhraseQuery.PostingsAndFreq[termArrays.size()];
			Terms fieldTerms = reader.terms(field);
			if (fieldTerms == null)
				return null;
			TermsEnum termsEnum = fieldTerms.iterator(null);
			for (int pos = 0; pos < postingsFreqs.length; pos++)
			{
				Term terms[] = (Term[])termArrays.get(pos);
				DocsAndPositionsEnum postingsEnum;
				int docFreq;
				if (terms.length > 1)
				{
					postingsEnum = new UnionDocsAndPositionsEnum(liveDocs, context, terms, termContexts, termsEnum);
					docFreq = 0;
					for (int termIdx = 0; termIdx < terms.length; termIdx++)
					{
						Term term = terms[termIdx];
						TermState termState = ((TermContext)termContexts.get(term)).get(context.ord);
						if (termState != null)
						{
							termsEnum.seekExact(term.bytes(), termState);
							docFreq += termsEnum.docFreq();
						}
					}

					if (docFreq == 0)
						return null;
				} else
				{
					Term term = terms[0];
					TermState termState = ((TermContext)termContexts.get(term)).get(context.ord);
					if (termState == null)
						return null;
					termsEnum.seekExact(term.bytes(), termState);
					postingsEnum = termsEnum.docsAndPositions(liveDocs, null, 0);
					if (postingsEnum == null)
						if (!$assertionsDisabled && termsEnum.docs(liveDocs, null, 0) == null)
							throw new AssertionError("termstate found but no term exists in reader");
						else
							throw new IllegalStateException((new StringBuilder()).append("field \"").append(term.field()).append("\" was indexed without position data; cannot run PhraseQuery (term=").append(term.text()).append(")").toString());
					docFreq = termsEnum.docFreq();
				}
				postingsFreqs[pos] = new PhraseQuery.PostingsAndFreq(postingsEnum, docFreq, ((Integer)positions.get(pos)).intValue(), terms);
			}

			if (slop == 0)
				ArrayUtil.mergeSort(postingsFreqs);
			if (slop == 0)
			{
				ExactPhraseScorer s = new ExactPhraseScorer(this, postingsFreqs, similarity.exactSimScorer(stats, context));
				if (s.noDocs)
					return null;
				else
					return s;
			} else
			{
				return new SloppyPhraseScorer(this, postingsFreqs, slop, similarity.sloppySimScorer(stats, context));
			}
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


		public MultiPhraseWeight(IndexSearcher searcher)
			throws IOException
		{
			this$0 = MultiPhraseQuery.this;
			super();
			similarity = searcher.getSimilarity();
			IndexReaderContext context = searcher.getTopReaderContext();
			ArrayList allTermStats = new ArrayList();
			for (Iterator i$ = termArrays.iterator(); i$.hasNext();)
			{
				Term terms[] = (Term[])i$.next();
				Term arr$[] = terms;
				int len$ = arr$.length;
				int i$ = 0;
				while (i$ < len$) 
				{
					Term term = arr$[i$];
					TermContext termContext = (TermContext)termContexts.get(term);
					if (termContext == null)
					{
						termContext = TermContext.build(context, term, true);
						termContexts.put(term, termContext);
					}
					allTermStats.add(searcher.termStatistics(term, termContext));
					i$++;
				}
			}

			stats = similarity.computeWeight(getBoost(), searcher.collectionStatistics(field), (TermStatistics[])allTermStats.toArray(new TermStatistics[allTermStats.size()]));
		}
	}


	private String field;
	private ArrayList termArrays;
	private ArrayList positions;
	private int slop;

	public MultiPhraseQuery()
	{
		termArrays = new ArrayList();
		positions = new ArrayList();
		slop = 0;
	}

	public void setSlop(int s)
	{
		slop = s;
	}

	public int getSlop()
	{
		return slop;
	}

	public void add(Term term)
	{
		add(new Term[] {
			term
		});
	}

	public void add(Term terms[])
	{
		int position = 0;
		if (positions.size() > 0)
			position = ((Integer)positions.get(positions.size() - 1)).intValue() + 1;
		add(terms, position);
	}

	public void add(Term terms[], int position)
	{
		if (termArrays.size() == 0)
			field = terms[0].field();
		for (int i = 0; i < terms.length; i++)
			if (!terms[i].field().equals(field))
				throw new IllegalArgumentException((new StringBuilder()).append("All phrase terms must be in the same field (").append(field).append("): ").append(terms[i]).toString());

		termArrays.add(terms);
		positions.add(Integer.valueOf(position));
	}

	public List getTermArrays()
	{
		return Collections.unmodifiableList(termArrays);
	}

	public int[] getPositions()
	{
		int result[] = new int[positions.size()];
		for (int i = 0; i < positions.size(); i++)
			result[i] = ((Integer)positions.get(i)).intValue();

		return result;
	}

	public void extractTerms(Set terms)
	{
		for (Iterator i$ = termArrays.iterator(); i$.hasNext();)
		{
			Term arr[] = (Term[])i$.next();
			Term arr$[] = arr;
			int len$ = arr$.length;
			int i$ = 0;
			while (i$ < len$) 
			{
				Term term = arr$[i$];
				terms.add(term);
				i$++;
			}
		}

	}

	public Query rewrite(IndexReader reader)
	{
		if (termArrays.isEmpty())
		{
			BooleanQuery bq = new BooleanQuery();
			bq.setBoost(getBoost());
			return bq;
		}
		if (termArrays.size() == 1)
		{
			Term terms[] = (Term[])termArrays.get(0);
			BooleanQuery boq = new BooleanQuery(true);
			for (int i = 0; i < terms.length; i++)
				boq.add(new TermQuery(terms[i]), BooleanClause.Occur.SHOULD);

			boq.setBoost(getBoost());
			return boq;
		} else
		{
			return this;
		}
	}

	public Weight createWeight(IndexSearcher searcher)
		throws IOException
	{
		return new MultiPhraseWeight(searcher);
	}

	public final String toString(String f)
	{
		StringBuilder buffer = new StringBuilder();
		if (field == null || !field.equals(f))
		{
			buffer.append(field);
			buffer.append(":");
		}
		buffer.append("\"");
		int k = 0;
		Iterator i = termArrays.iterator();
		int lastPos = -1;
		boolean first = true;
		while (i.hasNext()) 
		{
			Term terms[] = (Term[])i.next();
			int position = ((Integer)positions.get(k)).intValue();
			if (first)
			{
				first = false;
			} else
			{
				buffer.append(" ");
				for (int j = 1; j < position - lastPos; j++)
					buffer.append("? ");

			}
			if (terms.length > 1)
			{
				buffer.append("(");
				for (int j = 0; j < terms.length; j++)
				{
					buffer.append(terms[j].text());
					if (j < terms.length - 1)
						buffer.append(" ");
				}

				buffer.append(")");
			} else
			{
				buffer.append(terms[0].text());
			}
			lastPos = position;
			k++;
		}
		buffer.append("\"");
		if (slop != 0)
		{
			buffer.append("~");
			buffer.append(slop);
		}
		buffer.append(ToStringUtils.boost(getBoost()));
		return buffer.toString();
	}

	public boolean equals(Object o)
	{
		if (!(o instanceof MultiPhraseQuery))
		{
			return false;
		} else
		{
			MultiPhraseQuery other = (MultiPhraseQuery)o;
			return getBoost() == other.getBoost() && slop == other.slop && termArraysEquals(termArrays, other.termArrays) && positions.equals(other.positions);
		}
	}

	public int hashCode()
	{
		return Float.floatToIntBits(getBoost()) ^ slop ^ termArraysHashCode() ^ positions.hashCode() ^ 0x4ac65113;
	}

	private int termArraysHashCode()
	{
		int hashCode = 1;
		for (Iterator i$ = termArrays.iterator(); i$.hasNext();)
		{
			Term termArray[] = (Term[])i$.next();
			hashCode = 31 * hashCode + (termArray != null ? Arrays.hashCode(termArray) : 0);
		}

		return hashCode;
	}

	private boolean termArraysEquals(List termArrays1, List termArrays2)
	{
		if (termArrays1.size() != termArrays2.size())
			return false;
		ListIterator iterator1 = termArrays1.listIterator();
		ListIterator iterator2 = termArrays2.listIterator();
		while (iterator1.hasNext()) 
		{
			Term termArray1[] = (Term[])iterator1.next();
			Term termArray2[] = (Term[])iterator2.next();
			if (termArray1 != null ? !Arrays.equals(termArray1, termArray2) : termArray2 != null)
				return false;
		}
		return true;
	}




}
