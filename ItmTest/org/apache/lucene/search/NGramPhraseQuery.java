// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NGramPhraseQuery.java

package org.apache.lucene.search;

import java.io.IOException;
import org.apache.lucene.index.IndexReader;

// Referenced classes of package org.apache.lucene.search:
//			PhraseQuery, Query

public class NGramPhraseQuery extends PhraseQuery
{

	private final int n;

	public NGramPhraseQuery(int n)
	{
		this.n = n;
	}

	public Query rewrite(IndexReader reader)
		throws IOException
	{
		if (getSlop() != 0)
			return super.rewrite(reader);
		if (n < 2 || getTerms().length < 3)
			return super.rewrite(reader);
		int positions[] = getPositions();
		org.apache.lucene.index.Term terms[] = getTerms();
		int prevPosition = positions[0];
		int pos;
		for (int i = 1; i < positions.length; i++)
		{
			pos = positions[i];
			if (prevPosition + 1 != pos)
				return super.rewrite(reader);
			prevPosition = pos;
		}

		PhraseQuery optimized = new PhraseQuery();
		pos = 0;
		int lastPos = terms.length - 1;
		for (int i = 0; i < terms.length; i++)
		{
			if (pos % n == 0 || pos >= lastPos)
				optimized.add(terms[i], positions[i]);
			pos++;
		}

		return optimized;
	}

	public boolean equals(Object o)
	{
		if (!(o instanceof NGramPhraseQuery))
			return false;
		NGramPhraseQuery other = (NGramPhraseQuery)o;
		if (n != other.n)
			return false;
		else
			return super.equals(other);
	}

	public int hashCode()
	{
		return Float.floatToIntBits(getBoost()) ^ getSlop() ^ getTerms().hashCode() ^ getPositions().hashCode() ^ n;
	}
}
