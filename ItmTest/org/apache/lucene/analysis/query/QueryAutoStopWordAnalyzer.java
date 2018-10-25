// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   QueryAutoStopWordAnalyzer.java

package org.apache.lucene.analysis.query;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.AnalyzerWrapper;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.index.*;
import org.apache.lucene.util.*;

public final class QueryAutoStopWordAnalyzer extends AnalyzerWrapper
{

	private final Analyzer delegate;
	private final Map stopWordsPerField;
	public static final float defaultMaxDocFreqPercent = 0.4F;
	private final Version matchVersion;

	public QueryAutoStopWordAnalyzer(Version matchVersion, Analyzer delegate, IndexReader indexReader)
		throws IOException
	{
		this(matchVersion, delegate, indexReader, 0.4F);
	}

	public QueryAutoStopWordAnalyzer(Version matchVersion, Analyzer delegate, IndexReader indexReader, int maxDocFreq)
		throws IOException
	{
		this(matchVersion, delegate, indexReader, MultiFields.getIndexedFields(indexReader), maxDocFreq);
	}

	public QueryAutoStopWordAnalyzer(Version matchVersion, Analyzer delegate, IndexReader indexReader, float maxPercentDocs)
		throws IOException
	{
		this(matchVersion, delegate, indexReader, MultiFields.getIndexedFields(indexReader), maxPercentDocs);
	}

	public QueryAutoStopWordAnalyzer(Version matchVersion, Analyzer delegate, IndexReader indexReader, Collection fields, float maxPercentDocs)
		throws IOException
	{
		this(matchVersion, delegate, indexReader, fields, (int)((float)indexReader.numDocs() * maxPercentDocs));
	}

	public QueryAutoStopWordAnalyzer(Version matchVersion, Analyzer delegate, IndexReader indexReader, Collection fields, int maxDocFreq)
		throws IOException
	{
		stopWordsPerField = new HashMap();
		this.matchVersion = matchVersion;
		this.delegate = delegate;
		String field;
		Set stopWords;
label0:
		for (Iterator i$ = fields.iterator(); i$.hasNext(); stopWordsPerField.put(field, stopWords))
		{
			field = (String)i$.next();
			stopWords = new HashSet();
			Terms terms = MultiFields.getTerms(indexReader, field);
			CharsRef spare = new CharsRef();
			if (terms == null)
				continue;
			TermsEnum te = terms.iterator(null);
			do
			{
				org.apache.lucene.util.BytesRef text;
				do
					if ((text = te.next()) == null)
						continue label0;
				while (te.docFreq() <= maxDocFreq);
				UnicodeUtil.UTF8toUTF16(text, spare);
				stopWords.add(spare.toString());
			} while (true);
		}

	}

	protected Analyzer getWrappedAnalyzer(String fieldName)
	{
		return delegate;
	}

	protected org.apache.lucene.analysis.Analyzer.TokenStreamComponents wrapComponents(String fieldName, org.apache.lucene.analysis.Analyzer.TokenStreamComponents components)
	{
		Set stopWords = (Set)stopWordsPerField.get(fieldName);
		if (stopWords == null)
		{
			return components;
		} else
		{
			StopFilter stopFilter = new StopFilter(matchVersion, components.getTokenStream(), new CharArraySet(matchVersion, stopWords, false));
			return new org.apache.lucene.analysis.Analyzer.TokenStreamComponents(components.getTokenizer(), stopFilter);
		}
	}

	public String[] getStopWords(String fieldName)
	{
		Set stopWords = (Set)stopWordsPerField.get(fieldName);
		return stopWords == null ? new String[0] : (String[])stopWords.toArray(new String[stopWords.size()]);
	}

	public Term[] getStopWords()
	{
		List allStopWords = new ArrayList();
		for (Iterator i$ = stopWordsPerField.keySet().iterator(); i$.hasNext();)
		{
			String fieldName = (String)i$.next();
			Set stopWords = (Set)stopWordsPerField.get(fieldName);
			Iterator i$ = stopWords.iterator();
			while (i$.hasNext()) 
			{
				String text = (String)i$.next();
				allStopWords.add(new Term(fieldName, text));
			}
		}

		return (Term[])allStopWords.toArray(new Term[allStopWords.size()]);
	}
}
