// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FuzzyTermsEnum.java

package org.apache.lucene.search;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.index.*;
import org.apache.lucene.util.*;
import org.apache.lucene.util.automaton.*;

// Referenced classes of package org.apache.lucene.search:
//			BoostAttribute, MaxNonCompetitiveBoostAttribute

public class FuzzyTermsEnum extends TermsEnum
{
	public static final class LevenshteinAutomataAttributeImpl extends AttributeImpl
		implements LevenshteinAutomataAttribute
	{

		private final List automata = new ArrayList();

		public List automata()
		{
			return automata;
		}

		public void clear()
		{
			automata.clear();
		}

		public int hashCode()
		{
			return automata.hashCode();
		}

		public boolean equals(Object other)
		{
			if (this == other)
				return true;
			if (!(other instanceof LevenshteinAutomataAttributeImpl))
				return false;
			else
				return automata.equals(((LevenshteinAutomataAttributeImpl)other).automata);
		}

		public void copyTo(AttributeImpl target)
		{
			List targetAutomata = ((LevenshteinAutomataAttribute)target).automata();
			targetAutomata.clear();
			targetAutomata.addAll(automata);
		}

		public LevenshteinAutomataAttributeImpl()
		{
		}
	}

	public static interface LevenshteinAutomataAttribute
		extends Attribute
	{

		public abstract List automata();
	}

	private class AutomatonFuzzyTermsEnum extends FilteredTermsEnum
	{

		private final ByteRunAutomaton matchers[];
		private final BytesRef termRef;
		private final BoostAttribute boostAtt = (BoostAttribute)attributes().addAttribute(org/apache/lucene/search/BoostAttribute);
		final FuzzyTermsEnum this$0;

		protected org.apache.lucene.index.FilteredTermsEnum.AcceptStatus accept(BytesRef term)
		{
			int ed;
			for (ed = matchers.length - 1; ed > 0 && matches(term, ed - 1); ed--);
			if (ed == 0)
			{
				boostAtt.setBoost(1.0F);
				return org.apache.lucene.index.FilteredTermsEnum.AcceptStatus.YES;
			}
			int codePointCount = UnicodeUtil.codePointCount(term);
			float similarity = 1.0F - (float)ed / (float)Math.min(codePointCount, termLength);
			if (similarity > minSimilarity)
			{
				boostAtt.setBoost((similarity - minSimilarity) * scale_factor);
				return org.apache.lucene.index.FilteredTermsEnum.AcceptStatus.YES;
			} else
			{
				return org.apache.lucene.index.FilteredTermsEnum.AcceptStatus.NO;
			}
		}

		final boolean matches(BytesRef term, int k)
		{
			return k != 0 ? matchers[k].run(term.bytes, term.offset, term.length) : term.equals(termRef);
		}

		public AutomatonFuzzyTermsEnum(TermsEnum tenum, CompiledAutomaton compiled[])
		{
			this$0 = FuzzyTermsEnum.this;
			super(tenum, false);
			matchers = new ByteRunAutomaton[compiled.length];
			for (int i = 0; i < compiled.length; i++)
				matchers[i] = compiled[i].runAutomaton;

			termRef = new BytesRef(term.text());
		}
	}


	private TermsEnum actualEnum;
	private BoostAttribute actualBoostAtt;
	private final BoostAttribute boostAtt = (BoostAttribute)attributes().addAttribute(org/apache/lucene/search/BoostAttribute);
	private final MaxNonCompetitiveBoostAttribute maxBoostAtt;
	private final LevenshteinAutomataAttribute dfaAtt;
	private float bottom;
	private BytesRef bottomTerm;
	private final Comparator termComparator = BytesRef.getUTF8SortedAsUnicodeComparator();
	protected final float minSimilarity;
	protected final float scale_factor;
	protected final int termLength;
	protected int maxEdits;
	protected final boolean raw;
	protected final Terms terms;
	private final Term term;
	protected final int termText[];
	protected final int realPrefixLength;
	private final boolean transpositions;
	private BytesRef queuedBottom;
	static final boolean $assertionsDisabled = !org/apache/lucene/search/FuzzyTermsEnum.desiredAssertionStatus();

	public FuzzyTermsEnum(Terms terms, AttributeSource atts, Term term, float minSimilarity, int prefixLength, boolean transpositions)
		throws IOException
	{
		queuedBottom = null;
		if (minSimilarity >= 1.0F && minSimilarity != (float)(int)minSimilarity)
			throw new IllegalArgumentException("fractional edit distances are not allowed");
		if (minSimilarity < 0.0F)
			throw new IllegalArgumentException("minimumSimilarity cannot be less than 0");
		if (prefixLength < 0)
			throw new IllegalArgumentException("prefixLength cannot be less than 0");
		this.terms = terms;
		this.term = term;
		String utf16 = term.text();
		termText = new int[utf16.codePointCount(0, utf16.length())];
		int i = 0;
		int j = 0;
		int cp;
		for (; i < utf16.length(); i += Character.charCount(cp))
			termText[j++] = cp = utf16.codePointAt(i);

		termLength = termText.length;
		dfaAtt = (LevenshteinAutomataAttribute)atts.addAttribute(org/apache/lucene/search/FuzzyTermsEnum$LevenshteinAutomataAttribute);
		realPrefixLength = prefixLength <= termLength ? prefixLength : termLength;
		if (minSimilarity >= 1.0F)
		{
			this.minSimilarity = 0.0F;
			maxEdits = (int)minSimilarity;
			raw = true;
		} else
		{
			this.minSimilarity = minSimilarity;
			maxEdits = initialMaxDistance(this.minSimilarity, termLength);
			raw = false;
		}
		if (transpositions && maxEdits > 2)
		{
			throw new UnsupportedOperationException("with transpositions enabled, distances > 2 are not supported ");
		} else
		{
			this.transpositions = transpositions;
			scale_factor = 1.0F / (1.0F - this.minSimilarity);
			maxBoostAtt = (MaxNonCompetitiveBoostAttribute)atts.addAttribute(org/apache/lucene/search/MaxNonCompetitiveBoostAttribute);
			bottom = maxBoostAtt.getMaxNonCompetitiveBoost();
			bottomTerm = maxBoostAtt.getCompetitiveTerm();
			bottomChanged(null, true);
			return;
		}
	}

	protected TermsEnum getAutomatonEnum(int editDistance, BytesRef lastTerm)
		throws IOException
	{
		List runAutomata = initAutomata(editDistance);
		if (editDistance < runAutomata.size())
		{
			CompiledAutomaton compiled = (CompiledAutomaton)runAutomata.get(editDistance);
			return new AutomatonFuzzyTermsEnum(terms.intersect(compiled, lastTerm != null ? compiled.floor(lastTerm, new BytesRef()) : null), (CompiledAutomaton[])runAutomata.subList(0, editDistance + 1).toArray(new CompiledAutomaton[editDistance + 1]));
		} else
		{
			return null;
		}
	}

	private List initAutomata(int maxDistance)
	{
		List runAutomata = dfaAtt.automata();
		if (runAutomata.size() <= maxDistance && maxDistance <= 2)
		{
			LevenshteinAutomata builder = new LevenshteinAutomata(UnicodeUtil.newString(termText, realPrefixLength, termText.length - realPrefixLength), transpositions);
			for (int i = runAutomata.size(); i <= maxDistance; i++)
			{
				Automaton a = builder.toAutomaton(i);
				if (realPrefixLength > 0)
				{
					Automaton prefix = BasicAutomata.makeString(UnicodeUtil.newString(termText, 0, realPrefixLength));
					a = BasicOperations.concatenate(prefix, a);
				}
				runAutomata.add(new CompiledAutomaton(a, Boolean.valueOf(true), false));
			}

		}
		return runAutomata;
	}

	protected void setEnum(TermsEnum actualEnum)
	{
		this.actualEnum = actualEnum;
		actualBoostAtt = (BoostAttribute)actualEnum.attributes().addAttribute(org/apache/lucene/search/BoostAttribute);
	}

	private void bottomChanged(BytesRef lastTerm, boolean init)
		throws IOException
	{
		int oldMaxEdits = maxEdits;
		for (boolean termAfter = bottomTerm == null || lastTerm != null && termComparator.compare(lastTerm, bottomTerm) >= 0; maxEdits > 0 && (termAfter ? bottom >= calculateMaxBoost(maxEdits) : bottom > calculateMaxBoost(maxEdits)); maxEdits--);
		if (oldMaxEdits != maxEdits || init)
			maxEditDistanceChanged(lastTerm, maxEdits, init);
	}

	protected void maxEditDistanceChanged(BytesRef lastTerm, int maxEdits, boolean init)
		throws IOException
	{
		TermsEnum newEnum = getAutomatonEnum(maxEdits, lastTerm);
		if (newEnum == null)
		{
			if (!$assertionsDisabled && maxEdits <= 2)
				throw new AssertionError();
			else
				throw new IllegalArgumentException("maxEdits cannot be > LevenshteinAutomata.MAXIMUM_SUPPORTED_DISTANCE");
		} else
		{
			setEnum(newEnum);
			return;
		}
	}

	private int initialMaxDistance(float minimumSimilarity, int termLen)
	{
		return (int)((1.0D - (double)minimumSimilarity) * (double)termLen);
	}

	private float calculateMaxBoost(int nEdits)
	{
		float similarity = 1.0F - (float)nEdits / (float)termLength;
		return (similarity - minSimilarity) * scale_factor;
	}

	public BytesRef next()
		throws IOException
	{
		if (queuedBottom != null)
		{
			bottomChanged(queuedBottom, false);
			queuedBottom = null;
		}
		BytesRef term = actualEnum.next();
		boostAtt.setBoost(actualBoostAtt.getBoost());
		float bottom = maxBoostAtt.getMaxNonCompetitiveBoost();
		BytesRef bottomTerm = maxBoostAtt.getCompetitiveTerm();
		if (term != null && (bottom != this.bottom || bottomTerm != this.bottomTerm))
		{
			this.bottom = bottom;
			this.bottomTerm = bottomTerm;
			queuedBottom = BytesRef.deepCopyOf(term);
		}
		return term;
	}

	public int docFreq()
		throws IOException
	{
		return actualEnum.docFreq();
	}

	public long totalTermFreq()
		throws IOException
	{
		return actualEnum.totalTermFreq();
	}

	public DocsEnum docs(Bits liveDocs, DocsEnum reuse, int flags)
		throws IOException
	{
		return actualEnum.docs(liveDocs, reuse, flags);
	}

	public DocsAndPositionsEnum docsAndPositions(Bits liveDocs, DocsAndPositionsEnum reuse, int flags)
		throws IOException
	{
		return actualEnum.docsAndPositions(liveDocs, reuse, flags);
	}

	public void seekExact(BytesRef term, TermState state)
		throws IOException
	{
		actualEnum.seekExact(term, state);
	}

	public TermState termState()
		throws IOException
	{
		return actualEnum.termState();
	}

	public Comparator getComparator()
	{
		return actualEnum.getComparator();
	}

	public long ord()
		throws IOException
	{
		return actualEnum.ord();
	}

	public boolean seekExact(BytesRef text, boolean useCache)
		throws IOException
	{
		return actualEnum.seekExact(text, useCache);
	}

	public org.apache.lucene.index.TermsEnum.SeekStatus seekCeil(BytesRef text, boolean useCache)
		throws IOException
	{
		return actualEnum.seekCeil(text, useCache);
	}

	public void seekExact(long ord)
		throws IOException
	{
		actualEnum.seekExact(ord);
	}

	public BytesRef term()
		throws IOException
	{
		return actualEnum.term();
	}

	public float getMinSimilarity()
	{
		return minSimilarity;
	}

	public float getScaleFactor()
	{
		return scale_factor;
	}


}
