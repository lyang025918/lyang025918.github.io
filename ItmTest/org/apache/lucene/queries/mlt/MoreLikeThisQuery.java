// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MoreLikeThisQuery.java

package org.apache.lucene.queries.mlt;

import java.io.IOException;
import java.io.StringReader;
import java.util.Set;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;

// Referenced classes of package org.apache.lucene.queries.mlt:
//			MoreLikeThis

public class MoreLikeThisQuery extends Query
{

	private String likeText;
	private String moreLikeFields[];
	private Analyzer analyzer;
	private final String fieldName;
	private float percentTermsToMatch;
	private int minTermFrequency;
	private int maxQueryTerms;
	private Set stopWords;
	private int minDocFreq;

	public MoreLikeThisQuery(String likeText, String moreLikeFields[], Analyzer analyzer, String fieldName)
	{
		percentTermsToMatch = 0.3F;
		minTermFrequency = 1;
		maxQueryTerms = 5;
		stopWords = null;
		minDocFreq = -1;
		this.likeText = likeText;
		this.moreLikeFields = moreLikeFields;
		this.analyzer = analyzer;
		this.fieldName = fieldName;
	}

	public Query rewrite(IndexReader reader)
		throws IOException
	{
		MoreLikeThis mlt = new MoreLikeThis(reader);
		mlt.setFieldNames(moreLikeFields);
		mlt.setAnalyzer(analyzer);
		mlt.setMinTermFreq(minTermFrequency);
		if (minDocFreq >= 0)
			mlt.setMinDocFreq(minDocFreq);
		mlt.setMaxQueryTerms(maxQueryTerms);
		mlt.setStopWords(stopWords);
		BooleanQuery bq = (BooleanQuery)mlt.like(new StringReader(likeText), fieldName);
		org.apache.lucene.search.BooleanClause clauses[] = bq.getClauses();
		bq.setMinimumNumberShouldMatch((int)((float)clauses.length * percentTermsToMatch));
		return bq;
	}

	public String toString(String field)
	{
		return (new StringBuilder()).append("like:").append(likeText).toString();
	}

	public float getPercentTermsToMatch()
	{
		return percentTermsToMatch;
	}

	public void setPercentTermsToMatch(float percentTermsToMatch)
	{
		this.percentTermsToMatch = percentTermsToMatch;
	}

	public Analyzer getAnalyzer()
	{
		return analyzer;
	}

	public void setAnalyzer(Analyzer analyzer)
	{
		this.analyzer = analyzer;
	}

	public String getLikeText()
	{
		return likeText;
	}

	public void setLikeText(String likeText)
	{
		this.likeText = likeText;
	}

	public int getMaxQueryTerms()
	{
		return maxQueryTerms;
	}

	public void setMaxQueryTerms(int maxQueryTerms)
	{
		this.maxQueryTerms = maxQueryTerms;
	}

	public int getMinTermFrequency()
	{
		return minTermFrequency;
	}

	public void setMinTermFrequency(int minTermFrequency)
	{
		this.minTermFrequency = minTermFrequency;
	}

	public String[] getMoreLikeFields()
	{
		return moreLikeFields;
	}

	public void setMoreLikeFields(String moreLikeFields[])
	{
		this.moreLikeFields = moreLikeFields;
	}

	public Set getStopWords()
	{
		return stopWords;
	}

	public void setStopWords(Set stopWords)
	{
		this.stopWords = stopWords;
	}

	public int getMinDocFreq()
	{
		return minDocFreq;
	}

	public void setMinDocFreq(int minDocFreq)
	{
		this.minDocFreq = minDocFreq;
	}
}
