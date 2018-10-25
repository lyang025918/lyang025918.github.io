// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PhraseQuery.java

package org.apache.lucene.search;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.index.*;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.util.*;

// Referenced classes of package org.apache.lucene.search:
//			Query, BooleanQuery, TermQuery, IndexSearcher, 
//			Weight, TermStatistics, ExactPhraseScorer, SloppyPhraseScorer, 
//			ComplexExplanation, Explanation, Scorer

public class PhraseQuery extends Query
{
	private class PhraseWeight extends Weight
	{

		private final Similarity similarity;
		private final org.apache.lucene.search.similarities.Similarity.SimWeight stats;
		private transient TermContext states[];
		static final boolean $assertionsDisabled = !org/apache/lucene/search/PhraseQuery.desiredAssertionStatus();
		final PhraseQuery this$0;

		public String toString()
		{
			return (new StringBuilder()).append("weight(").append(PhraseQuery.this).append(")").toString();
		}

		public Query getQuery()
		{
			return PhraseQuery.this;
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
			if (!$assertionsDisabled && terms.isEmpty())
				throw new AssertionError();
			AtomicReader reader = context.reader();
			Bits liveDocs = acceptDocs;
			PostingsAndFreq postingsFreqs[] = new PostingsAndFreq[terms.size()];
			Terms fieldTerms = reader.terms(field);
			if (fieldTerms == null)
				return null;
			TermsEnum te = fieldTerms.iterator(null);
			for (int i = 0; i < terms.size(); i++)
			{
				Term t = (Term)terms.get(i);
				TermState state = states[i].get(context.ord);
				if (state == null)
					if (!$assertionsDisabled && !termNotInReader(reader, t))
						throw new AssertionError("no termstate found but term exists in reader");
					else
						return null;
				te.seekExact(t.bytes(), state);
				DocsAndPositionsEnum postingsEnum = te.docsAndPositions(liveDocs, null, 0);
				if (postingsEnum == null)
					if (!$assertionsDisabled && !te.seekExact(t.bytes(), false))
						throw new AssertionError("termstate found but no term exists in reader");
					else
						throw new IllegalStateException((new StringBuilder()).append("field \"").append(t.field()).append("\" was indexed without position data; cannot run PhraseQuery (term=").append(t.text()).append(")").toString());
				postingsFreqs[i] = new PostingsAndFreq(postingsEnum, te.docFreq(), ((Integer)positions.get(i)).intValue(), new Term[] {
					t
				});
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

		private boolean termNotInReader(AtomicReader reader, Term term)
			throws IOException
		{
			return reader.docFreq(term) == 0;
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


		public PhraseWeight(IndexSearcher searcher)
			throws IOException
		{
			this$0 = PhraseQuery.this;
			super();
			similarity = searcher.getSimilarity();
			IndexReaderContext context = searcher.getTopReaderContext();
			states = new TermContext[terms.size()];
			TermStatistics termStats[] = new TermStatistics[terms.size()];
			for (int i = 0; i < terms.size(); i++)
			{
				Term term = (Term)terms.get(i);
				states[i] = TermContext.build(context, term, true);
				termStats[i] = searcher.termStatistics(term, states[i]);
			}

			stats = similarity.computeWeight(getBoost(), searcher.collectionStatistics(field), termStats);
		}
	}

	static class PostingsAndFreq
		implements Comparable
	{

		final DocsAndPositionsEnum postings;
		final int docFreq;
		final int position;
		final Term terms[];
		final int nTerms;

		public int compareTo(PostingsAndFreq other)
		{
			if (docFreq != other.docFreq)
				return docFreq - other.docFreq;
			if (position != other.position)
				return position - other.position;
			if (nTerms != other.nTerms)
				return nTerms - other.nTerms;
			if (nTerms == 0)
				return 0;
			for (int i = 0; i < terms.length; i++)
			{
				int res = terms[i].compareTo(other.terms[i]);
				if (res != 0)
					return res;
			}

			return 0;
		}

		public int hashCode()
		{
			int prime = 31;
			int result = 1;
			result = 31 * result + docFreq;
			result = 31 * result + position;
			for (int i = 0; i < nTerms; i++)
				result = 31 * result + terms[i].hashCode();

			return result;
		}

		public boolean equals(Object obj)
		{
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			PostingsAndFreq other = (PostingsAndFreq)obj;
			if (docFreq != other.docFreq)
				return false;
			if (position != other.position)
				return false;
			if (terms == null)
				return other.terms == null;
			else
				return Arrays.equals(terms, other.terms);
		}

		public volatile int compareTo(Object x0)
		{
			return compareTo((PostingsAndFreq)x0);
		}

		public transient PostingsAndFreq(DocsAndPositionsEnum postings, int docFreq, int position, Term terms[])
		{
			this.postings = postings;
			this.docFreq = docFreq;
			this.position = position;
			nTerms = terms != null ? terms.length : 0;
			if (nTerms > 0)
			{
				if (terms.length == 1)
				{
					this.terms = terms;
				} else
				{
					Term terms2[] = new Term[terms.length];
					System.arraycopy(terms, 0, terms2, 0, terms.length);
					Arrays.sort(terms2);
					this.terms = terms2;
				}
			} else
			{
				this.terms = null;
			}
		}
	}


	private String field;
	private ArrayList terms;
	private ArrayList positions;
	private int maxPosition;
	private int slop;

	public PhraseQuery()
	{
		terms = new ArrayList(4);
		positions = new ArrayList(4);
		maxPosition = 0;
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
		int position = 0;
		if (positions.size() > 0)
			position = ((Integer)positions.get(positions.size() - 1)).intValue() + 1;
		add(term, position);
	}

	public void add(Term term, int position)
	{
		if (terms.size() == 0)
			field = term.field();
		else
		if (!term.field().equals(field))
			throw new IllegalArgumentException((new StringBuilder()).append("All phrase terms must be in the same field: ").append(term).toString());
		terms.add(term);
		positions.add(Integer.valueOf(position));
		if (position > maxPosition)
			maxPosition = position;
	}

	public Term[] getTerms()
	{
		return (Term[])terms.toArray(new Term[0]);
	}

	public int[] getPositions()
	{
		int result[] = new int[positions.size()];
		for (int i = 0; i < positions.size(); i++)
			result[i] = ((Integer)positions.get(i)).intValue();

		return result;
	}

	public Query rewrite(IndexReader reader)
		throws IOException
	{
		if (terms.isEmpty())
		{
			BooleanQuery bq = new BooleanQuery();
			bq.setBoost(getBoost());
			return bq;
		}
		if (terms.size() == 1)
		{
			TermQuery tq = new TermQuery((Term)terms.get(0));
			tq.setBoost(getBoost());
			return tq;
		} else
		{
			return super.rewrite(reader);
		}
	}

	public Weight createWeight(IndexSearcher searcher)
		throws IOException
	{
		return new PhraseWeight(searcher);
	}

	public void extractTerms(Set queryTerms)
	{
		queryTerms.addAll(terms);
	}

	public String toString(String f)
	{
		StringBuilder buffer = new StringBuilder();
		if (field != null && !field.equals(f))
		{
			buffer.append(field);
			buffer.append(":");
		}
		buffer.append("\"");
		String pieces[] = new String[maxPosition + 1];
		for (int i = 0; i < terms.size(); i++)
		{
			int pos = ((Integer)positions.get(i)).intValue();
			String s = pieces[pos];
			if (s == null)
				s = ((Term)terms.get(i)).text();
			else
				s = (new StringBuilder()).append(s).append("|").append(((Term)terms.get(i)).text()).toString();
			pieces[pos] = s;
		}

		for (int i = 0; i < pieces.length; i++)
		{
			if (i > 0)
				buffer.append(' ');
			String s = pieces[i];
			if (s == null)
				buffer.append('?');
			else
				buffer.append(s);
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
		if (!(o instanceof PhraseQuery))
		{
			return false;
		} else
		{
			PhraseQuery other = (PhraseQuery)o;
			return getBoost() == other.getBoost() && slop == other.slop && terms.equals(other.terms) && positions.equals(other.positions);
		}
	}

	public int hashCode()
	{
		return Float.floatToIntBits(getBoost()) ^ slop ^ terms.hashCode() ^ positions.hashCode();
	}




}
