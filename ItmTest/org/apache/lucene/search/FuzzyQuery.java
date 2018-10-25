// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FuzzyQuery.java

package org.apache.lucene.search;

import java.io.IOException;
import org.apache.lucene.index.*;
import org.apache.lucene.util.AttributeSource;
import org.apache.lucene.util.ToStringUtils;

// Referenced classes of package org.apache.lucene.search:
//			MultiTermQuery, FuzzyTermsEnum

public class FuzzyQuery extends MultiTermQuery
{

	public static final int defaultMaxEdits = 2;
	public static final int defaultPrefixLength = 0;
	public static final int defaultMaxExpansions = 50;
	public static final boolean defaultTranspositions = true;
	private final int maxEdits;
	private final int maxExpansions;
	private final boolean transpositions;
	private final int prefixLength;
	private final Term term;
	/**
	 * @deprecated Field defaultMinSimilarity is deprecated
	 */
	public static final float defaultMinSimilarity = 2F;

	public FuzzyQuery(Term term, int maxEdits, int prefixLength, int maxExpansions, boolean transpositions)
	{
		super(term.field());
		if (maxEdits < 0 || maxEdits > 2)
			throw new IllegalArgumentException("maxEdits must be between 0 and 2");
		if (prefixLength < 0)
			throw new IllegalArgumentException("prefixLength cannot be negative.");
		if (maxExpansions < 0)
		{
			throw new IllegalArgumentException("maxExpansions cannot be negative.");
		} else
		{
			this.term = term;
			this.maxEdits = maxEdits;
			this.prefixLength = prefixLength;
			this.transpositions = transpositions;
			this.maxExpansions = maxExpansions;
			setRewriteMethod(new MultiTermQuery.TopTermsScoringBooleanQueryRewrite(maxExpansions));
			return;
		}
	}

	public FuzzyQuery(Term term, int maxEdits, int prefixLength)
	{
		this(term, maxEdits, prefixLength, 50, true);
	}

	public FuzzyQuery(Term term, int maxEdits)
	{
		this(term, maxEdits, 0);
	}

	public FuzzyQuery(Term term)
	{
		this(term, 2);
	}

	public int getMaxEdits()
	{
		return maxEdits;
	}

	public int getPrefixLength()
	{
		return prefixLength;
	}

	protected TermsEnum getTermsEnum(Terms terms, AttributeSource atts)
		throws IOException
	{
		if (maxEdits == 0 || prefixLength >= term.text().length())
			return new SingleTermsEnum(terms.iterator(null), term.bytes());
		else
			return new FuzzyTermsEnum(terms, atts, getTerm(), maxEdits, prefixLength, transpositions);
	}

	public Term getTerm()
	{
		return term;
	}

	public String toString(String field)
	{
		StringBuilder buffer = new StringBuilder();
		if (!term.field().equals(field))
		{
			buffer.append(term.field());
			buffer.append(":");
		}
		buffer.append(term.text());
		buffer.append('~');
		buffer.append(Integer.toString(maxEdits));
		buffer.append(ToStringUtils.boost(getBoost()));
		return buffer.toString();
	}

	public int hashCode()
	{
		int prime = 31;
		int result = super.hashCode();
		result = 31 * result + maxEdits;
		result = 31 * result + prefixLength;
		result = 31 * result + maxExpansions;
		result = 31 * result + (transpositions ? 0 : 1);
		result = 31 * result + (term != null ? term.hashCode() : 0);
		return result;
	}

	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		FuzzyQuery other = (FuzzyQuery)obj;
		if (maxEdits != other.maxEdits)
			return false;
		if (prefixLength != other.prefixLength)
			return false;
		if (maxExpansions != other.maxExpansions)
			return false;
		if (transpositions != other.transpositions)
			return false;
		if (term == null)
		{
			if (other.term != null)
				return false;
		} else
		if (!term.equals(other.term))
			return false;
		return true;
	}

	/**
	 * @deprecated Method floatToEdits is deprecated
	 */

	public static int floatToEdits(float minimumSimilarity, int termLen)
	{
		if (minimumSimilarity >= 1.0F)
			return (int)Math.min(minimumSimilarity, 2.0F);
		if (minimumSimilarity == 0.0F)
			return 0;
		else
			return Math.min((int)((1.0D - (double)minimumSimilarity) * (double)termLen), 2);
	}
}
