// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BackgroundPattern.java

package com.iflytek.itm.mining;

import com.iflytek.itm.search.ITMSearchWrapper;
import com.iflytek.itm.util.ITMParamParser;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.wltea.analyzer.lucene.IKAnalyzer;

// Referenced classes of package com.iflytek.itm.mining:
//			WordInfoListComp, WordInfo, ITMMiningResource

public class BackgroundPattern
{

	public int dim;
	Map wordScoreMap;
	Map docWordCount;
	ITMSearchWrapper searchWrapper;
	ITMParamParser itmParamParser;
	IndexSearcher searcher;
	Analyzer analyzer;
	String mining_field;
	private static final Logger logger = Logger.getLogger(com/iflytek/itm/mining/BackgroundPattern);

	public BackgroundPattern(ITMSearchWrapper searchWrapper, ITMParamParser itmParamParser)
	{
		dim = 0;
		wordScoreMap = new HashMap();
		docWordCount = new HashMap();
		this.searchWrapper = null;
		this.itmParamParser = null;
		searcher = null;
		analyzer = null;
		mining_field = null;
		this.searchWrapper = searchWrapper;
		this.itmParamParser = itmParamParser;
		searcher = searchWrapper.searcher;
		analyzer = new IKAnalyzer(true);
		mining_field = itmParamParser.mining_field;
	}

	public List calcDiffWords(List docContents, int topN)
		throws IOException
	{
		int sampleTotalWordCount = 0;
		sampleTotalWordCount = fetchWordInfos(docContents, wordScoreMap, docWordCount);
		List topWordInfos = fetchTopWords(docContents, topN, sampleTotalWordCount);
		dim = topWordInfos.size();
		return topWordInfos;
	}

	public List fetchTopWords(List docContents, int topN, int sampleTotalWordCount)
		throws IOException
	{
		List finalWords = new ArrayList();
		toWordInfoList(wordScoreMap, finalWords, sampleTotalWordCount, docContents.size());
		WordInfoListComp comp = new WordInfoListComp();
		Collections.sort(finalWords, comp);
		int top = finalWords.size() <= topN ? finalWords.size() : topN;
		return finalWords.subList(0, top);
	}

	private int toWordInfoList(Map wordScoreMap, List finalWords, int sampleTotalWordCount, int sampleDocCount)
		throws IOException
	{
		Set keys = wordScoreMap.keySet();
		WordInfo info;
		for (Iterator it = keys.iterator(); it.hasNext(); finalWords.add(info))
		{
			String word = (String)it.next();
			info = (WordInfo)wordScoreMap.get(word);
			info.totalDocCount = sampleDocCount;
			info.totalTermCount = sampleTotalWordCount;
			info.score = diffScore(info, sampleDocCount);
		}

		return 0;
	}

	public float diffScore(WordInfo info, int sampleDocCount)
		throws IOException
	{
		float ps = (float)info.docFreq / (float)sampleDocCount;
		int totalDocCnt = searcher.getIndexReader().numDocs();
		int docFreq = searcher.getIndexReader().docFreq(new Term(mining_field, info.word));
		float pt = (float)docFreq / (float)totalDocCnt;
		info.score = (-1F * ps * (float)Math.log(pt)) / (float)Math.log(2D);
		if (Float.isInfinite(info.score))
			info.score = 0.0F;
		return info.score;
	}

	public int fetchWordInfos(List docContents, Map wordScoreMap, Map docWordCount)
		throws IOException
	{
		int totalWordCount = 0;
		Set docWords = new HashSet();
		for (int i = 0; i < docContents.size(); i++)
		{
			String content = (String)docContents.get(i);
			docWords.clear();
			TokenStream tokenStream = analyzer.tokenStream(mining_field, new StringReader(content));
			CharTermAttribute termAtt = (CharTermAttribute)tokenStream.addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
			tokenStream.reset();
			do
			{
				if (!tokenStream.incrementToken())
					break;
				String word = termAtt.toString();
				if (!ITMMiningResource.inst().isNoiseWord(word))
				{
					totalWordCount++;
					if (wordScoreMap.containsKey(word))
					{
						((WordInfo)wordScoreMap.get(word)).termFreq++;
						if (!docWords.contains(word))
							((WordInfo)wordScoreMap.get(word)).docFreq++;
					} else
					{
						WordInfo info = new WordInfo();
						info.word = word;
						info.score = 0.0F;
						info.docFreq = 1;
						info.termFreq = 1;
						info.topField = mining_field;
						wordScoreMap.put(word, info);
					}
					docWords.add(word);
				}
			} while (true);
			tokenStream.end();
			tokenStream.close();
			docWordCount.put(Integer.valueOf(i), Integer.valueOf(docWords.size()));
		}

		return totalWordCount;
	}

	public double[] featureArray(int idoc, String content, List diffWords)
		throws IOException
	{
		double scores[] = new double[dim + 1];
		List docContents = new ArrayList();
		docContents.add(content);
		Map tmpWordScoreMap = new HashMap();
		Map tmpDocWordCount = new HashMap();
		fetchWordInfos(docContents, tmpWordScoreMap, tmpDocWordCount);
		int docWordCnt = ((Integer)docWordCount.get(Integer.valueOf(idoc))).intValue();
		for (int i = 0; i < diffWords.size(); i++)
		{
			WordInfo info = (WordInfo)diffWords.get(i);
			WordInfo tmpInfo = (WordInfo)tmpWordScoreMap.get(info.word);
			if (tmpInfo == null)
			{
				scores[i] = 0.0D;
			} else
			{
				int wordCnt = ((WordInfo)tmpWordScoreMap.get(info.word)).termFreq;
				scores[i] = tfidf(info, docWordCnt, wordCnt);
			}
		}

		return scores;
	}

	public float tfidf(WordInfo info, int docWordCnt, int termFreq)
	{
		float tf = (float)termFreq / (float)docWordCnt;
		float df = (float)info.docFreq / (float)info.totalDocCount;
		float idf = (float)Math.log10(1.0F / df);
		if (info.docFreq == 0)
		{
			return 0.0F;
		} else
		{
			float tfidf = tf * idf;
			info.score = tfidf;
			return tfidf;
		}
	}

}
