// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MyMoreLikeThis.java

package com.iflytek.itm.mining;

import java.io.*;
import java.util.*;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.search.similarities.DefaultSimilarity;
import org.apache.lucene.search.similarities.TFIDFSimilarity;
import org.apache.lucene.util.*;

// Referenced classes of package com.iflytek.itm.mining:
//			ITMMiningResource

public class MyMoreLikeThis
{
	private static class Int
	{

		int x;

		Int()
		{
			x = 1;
		}
	}

	private static class FreqQ extends PriorityQueue
	{

		protected boolean lessThan(Object aa[], Object bb[])
		{
			Float fa = (Float)aa[2];
			Float fb = (Float)bb[2];
			return fa.floatValue() > fb.floatValue();
		}

		protected volatile boolean lessThan(Object obj, Object obj1)
		{
			return lessThan((Object[])obj, (Object[])obj1);
		}

		FreqQ(int s)
		{
			super(s);
		}
	}


	public static final int DEFAULT_MAX_NUM_TOKENS_PARSED = 5000;
	public static final int DEFAULT_MIN_TERM_FREQ = 2;
	public static final int DEFAULT_MIN_DOC_FREQ = 5;
	public static final int DEFAULT_MAX_DOC_FREQ = 0x7fffffff;
	public static final boolean DEFAULT_BOOST = false;
	public static final String DEFAULT_FIELD_NAMES[] = {
		"contents"
	};
	public static final int DEFAULT_MIN_WORD_LENGTH = 0;
	public static final int DEFAULT_MAX_WORD_LENGTH = 0;
	public static final Set DEFAULT_STOP_WORDS = null;
	private Set stopWords;
	public static final int DEFAULT_MAX_QUERY_TERMS = 25;
	private Analyzer analyzer;
	private int minTermFreq;
	private int minDocFreq;
	private int maxDocFreq;
	private boolean boost;
	private String fieldNames[];
	private int maxNumTokensParsed;
	private int minWordLen;
	private int maxWordLen;
	private int maxQueryTerms;
	private TFIDFSimilarity similarity;
	private final IndexReader ir;
	private float boostFactor;

	public Object[] getTopRelevantTerms(String word)
	{
		return null;
	}

	public Object[] getTopRelevantTerms(int docNum)
		throws IOException
	{
		ArrayList al = new ArrayList(maxQueryTerms);
		PriorityQueue pq = retrieveTerms(docNum);
		Object cur;
		for (int lim = maxQueryTerms; (cur = pq.pop()) != null && lim-- > 0;)
		{
			Object ar[] = (Object[])(Object[])cur;
			al.add(((Object) (ar)));
		}

		return al.toArray();
	}

	public float getBoostFactor()
	{
		return boostFactor;
	}

	public void setBoostFactor(float boostFactor)
	{
		this.boostFactor = boostFactor;
	}

	public MyMoreLikeThis(IndexReader ir)
	{
		this(ir, ((TFIDFSimilarity) (new DefaultSimilarity())));
	}

	public MyMoreLikeThis(IndexReader ir, TFIDFSimilarity sim)
	{
		stopWords = DEFAULT_STOP_WORDS;
		analyzer = null;
		minTermFreq = 2;
		minDocFreq = 5;
		maxDocFreq = 0x7fffffff;
		boost = false;
		fieldNames = DEFAULT_FIELD_NAMES;
		maxNumTokensParsed = 5000;
		minWordLen = 0;
		maxWordLen = 0;
		maxQueryTerms = 25;
		boostFactor = 1.0F;
		this.ir = ir;
		similarity = sim;
	}

	public TFIDFSimilarity getSimilarity()
	{
		return similarity;
	}

	public void setSimilarity(TFIDFSimilarity similarity)
	{
		this.similarity = similarity;
	}

	public Analyzer getAnalyzer()
	{
		return analyzer;
	}

	public void setAnalyzer(Analyzer analyzer)
	{
		this.analyzer = analyzer;
	}

	public int getMinTermFreq()
	{
		return minTermFreq;
	}

	public void setMinTermFreq(int minTermFreq)
	{
		this.minTermFreq = minTermFreq;
	}

	public int getMinDocFreq()
	{
		return minDocFreq;
	}

	public void setMinDocFreq(int minDocFreq)
	{
		this.minDocFreq = minDocFreq;
	}

	public int getMaxDocFreq()
	{
		return maxDocFreq;
	}

	public void setMaxDocFreq(int maxFreq)
	{
		maxDocFreq = maxFreq;
	}

	public void setMaxDocFreqPct(int maxPercentage)
	{
		maxDocFreq = (maxPercentage * ir.numDocs()) / 100;
	}

	public boolean isBoost()
	{
		return boost;
	}

	public void setBoost(boolean boost)
	{
		this.boost = boost;
	}

	public String[] getFieldNames()
	{
		return fieldNames;
	}

	public void setFieldNames(String fieldNames[])
	{
		this.fieldNames = fieldNames;
	}

	public int getMinWordLen()
	{
		return minWordLen;
	}

	public void setMinWordLen(int minWordLen)
	{
		this.minWordLen = minWordLen;
	}

	public int getMaxWordLen()
	{
		return maxWordLen;
	}

	public void setMaxWordLen(int maxWordLen)
	{
		this.maxWordLen = maxWordLen;
	}

	public void setStopWords(Set stopWords)
	{
		this.stopWords = stopWords;
	}

	public Set getStopWords()
	{
		return stopWords;
	}

	public int getMaxQueryTerms()
	{
		return maxQueryTerms;
	}

	public void setMaxQueryTerms(int maxQueryTerms)
	{
		this.maxQueryTerms = maxQueryTerms;
	}

	public int getMaxNumTokensParsed()
	{
		return maxNumTokensParsed;
	}

	public void setMaxNumTokensParsed(int i)
	{
		maxNumTokensParsed = i;
	}

	public Query like(int docNum)
		throws IOException
	{
		if (fieldNames == null)
		{
			Collection fields = MultiFields.getIndexedFields(ir);
			fieldNames = (String[])fields.toArray(new String[fields.size()]);
		}
		return createQuery(retrieveTerms(docNum));
	}

	public Query like(Reader r, String fieldName)
		throws IOException
	{
		return createQuery(retrieveTerms(r, fieldName));
	}

	private Query createQuery(PriorityQueue q)
	{
		BooleanQuery query = new BooleanQuery();
		int qterms = 0;
		float bestScore = 0.0F;
		do
		{
			Object cur;
			if ((cur = q.pop()) == null)
				break;
			Object ar[] = (Object[])(Object[])cur;
			TermQuery tq = new TermQuery(new Term((String)ar[1], (String)ar[0]));
			if (boost)
			{
				if (qterms == 0)
					bestScore = ((Float)ar[2]).floatValue();
				float myScore = ((Float)ar[2]).floatValue();
				tq.setBoost((boostFactor * myScore) / bestScore);
			}
			try
			{
				query.add(tq, org.apache.lucene.search.BooleanClause.Occur.SHOULD);
			}
			catch (org.apache.lucene.search.BooleanQuery.TooManyClauses ignore)
			{
				break;
			}
			qterms++;
		} while (maxQueryTerms <= 0 || qterms < maxQueryTerms);
		return query;
	}

	private PriorityQueue createQueue(Map words)
		throws IOException
	{
		int numDocs = ir.numDocs();
		FreqQ res = new FreqQ(words.size());
		Iterator iterator = words.keySet().iterator();
		do
		{
			if (!iterator.hasNext())
				break;
			String word = (String)iterator.next();
			int tf = ((Int)words.get(word)).x;
			if (minTermFreq <= 0 || tf >= minTermFreq)
			{
				String topField = fieldNames[0];
				int docFreq = 0;
				String as[] = fieldNames;
				int i = as.length;
				for (int j = 0; j < i; j++)
				{
					String fieldName = as[j];
					int freq = ir.docFreq(new Term(fieldName, word));
					topField = freq <= docFreq ? topField : fieldName;
					docFreq = freq <= docFreq ? docFreq : freq;
				}

				if ((minDocFreq <= 0 || docFreq >= minDocFreq) && docFreq <= maxDocFreq && docFreq != 0)
				{
					float idf = similarity.idf(docFreq, numDocs);
					float score = (float)tf * idf;
					res.insertWithOverflow(((Object) (new Object[] {
						word, topField, Float.valueOf(score), Float.valueOf(idf), Integer.valueOf(docFreq), Integer.valueOf(tf)
					})));
				}
			}
		} while (true);
		return res;
	}

	public String describeParams()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("\t").append("maxQueryTerms  : ").append(maxQueryTerms).append("\n");
		sb.append("\t").append("minWordLen     : ").append(minWordLen).append("\n");
		sb.append("\t").append("maxWordLen     : ").append(maxWordLen).append("\n");
		sb.append("\t").append("fieldNames     : ");
		String delim = "";
		String as[] = fieldNames;
		int i = as.length;
		for (int j = 0; j < i; j++)
		{
			String fieldName = as[j];
			sb.append(delim).append(fieldName);
			delim = ", ";
		}

		sb.append("\n");
		sb.append("\t").append("boost          : ").append(boost).append("\n");
		sb.append("\t").append("minTermFreq    : ").append(minTermFreq).append("\n");
		sb.append("\t").append("minDocFreq     : ").append(minDocFreq).append("\n");
		return sb.toString();
	}

	public PriorityQueue retrieveTerms(int docNum)
		throws IOException
	{
		Map termFreqMap = new HashMap();
		String as[] = fieldNames;
		int i = as.length;
label0:
		for (int j = 0; j < i; j++)
		{
			String fieldName = as[j];
			Fields vectors = ir.getTermVectors(docNum);
			Terms vector;
			if (vectors != null)
				vector = vectors.terms(fieldName);
			else
				vector = null;
			if (vector == null)
			{
				Document d = ir.document(docNum);
				IndexableField fields[] = d.getFields(fieldName);
				IndexableField aindexablefield[] = fields;
				int k = aindexablefield.length;
				int l = 0;
				do
				{
					if (l >= k)
						continue label0;
					IndexableField field = aindexablefield[l];
					String stringValue = field.stringValue();
					if (stringValue != null)
						addTermFrequencies(new StringReader(stringValue), termFreqMap, fieldName);
					l++;
				} while (true);
			}
			addTermFrequencies(termFreqMap, vector);
		}

		return createQueue(termFreqMap);
	}

	private void addTermFrequencies(Map termFreqMap, Terms vector)
		throws IOException
	{
		TermsEnum termsEnum = vector.iterator(null);
		CharsRef spare = new CharsRef();
		do
		{
			BytesRef text;
			if ((text = termsEnum.next()) == null)
				break;
			UnicodeUtil.UTF8toUTF16(text, spare);
			String term = spare.toString();
			if (!ITMMiningResource.inst().isNoiseWord(term))
			{
				int freq = (int)termsEnum.totalTermFreq();
				Int cnt = (Int)termFreqMap.get(term);
				if (cnt == null)
				{
					cnt = new Int();
					termFreqMap.put(term, cnt);
					cnt.x = freq;
				} else
				{
					cnt.x += freq;
				}
			}
		} while (true);
	}

	private void addTermFrequencies(Reader r, Map termFreqMap, String fieldName)
		throws IOException
	{
		if (analyzer == null)
			throw new UnsupportedOperationException("To use MoreLikeThis without term vectors, you must provide an Analyzer");
		TokenStream ts = analyzer.tokenStream(fieldName, r);
		int tokenCount = 0;
		CharTermAttribute termAtt = (CharTermAttribute)ts.addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		ts.reset();
		do
		{
			if (!ts.incrementToken())
				break;
			String word = termAtt.toString();
			if (++tokenCount > maxNumTokensParsed)
				break;
			if (!ITMMiningResource.inst().isNoiseWord(word))
			{
				Int cnt = (Int)termFreqMap.get(word);
				if (cnt == null)
					termFreqMap.put(word, new Int());
				else
					cnt.x++;
			}
		} while (true);
		ts.end();
		ts.close();
	}

	public PriorityQueue retrieveTerms(Reader r, String fieldName)
		throws IOException
	{
		Map words = new HashMap();
		addTermFrequencies(r, words, fieldName);
		return createQueue(words);
	}

	public String[] retrieveInterestingTerms(int docNum)
		throws IOException
	{
		ArrayList al = new ArrayList(maxQueryTerms);
		PriorityQueue pq = retrieveTerms(docNum);
		Object cur;
		for (int lim = maxQueryTerms; (cur = pq.pop()) != null && lim-- > 0;)
		{
			Object ar[] = (Object[])(Object[])cur;
			al.add(ar[0]);
		}

		String res[] = new String[al.size()];
		return (String[])al.toArray(res);
	}

	public String[] retrieveInterestingTerms(Reader r, String fieldName)
		throws IOException
	{
		ArrayList al = new ArrayList(maxQueryTerms);
		PriorityQueue pq = retrieveTerms(r, fieldName);
		Object cur;
		for (int lim = maxQueryTerms; (cur = pq.pop()) != null && lim-- > 0;)
		{
			Object ar[] = (Object[])(Object[])cur;
			al.add(ar[0]);
		}

		String res[] = new String[al.size()];
		return (String[])al.toArray(res);
	}

}
