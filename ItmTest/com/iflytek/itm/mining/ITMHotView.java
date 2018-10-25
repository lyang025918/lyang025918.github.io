// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ITMHotView.java

package com.iflytek.itm.mining;

import com.iflytek.itm.api.ITMBuilder;
import com.iflytek.itm.api.ITMErrors;
import com.iflytek.itm.build.ITMUserData;
import com.iflytek.itm.mining.worldlet.WorldletResult;
import com.iflytek.itm.search.*;
import com.iflytek.itm.util.ITMParamParser;
import com.iflytek.itm.util.ITMUtils;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.search.TopDocs;
import org.jgrapht.graph.AbstractBaseGraph;
import org.jgrapht.graph.SimpleGraph;
import org.wltea.analyzer.lucene.IKAnalyzer;

// Referenced classes of package com.iflytek.itm.mining:
//			HotViewResult, NgramCount, BackgroundPattern, BisectingKmeans, 
//			WordInfo, Pattern, Cluster, ITMMiningResource, 
//			ITMTrade

public class ITMHotView
{

	private ITMParamParser itmParamParser;
	private ITMSearchWrapper searchWrapper;
	private int totalHits;
	private List docContents;
	BisectingKmeans biCluster;
	HotViewResult result;
	WorldletResult wlr;
	NgramCount ngramCount;
	Set featureWords;
	private static final Logger logger = Logger.getLogger(com/iflytek/itm/mining/ITMHotView);
	AbstractBaseGraph graph[];

	public ITMHotView()
	{
		itmParamParser = new ITMParamParser();
		searchWrapper = new ITMSearchWrapper();
		totalHits = 0;
		docContents = null;
		biCluster = null;
		result = new HotViewResult();
		wlr = new WorldletResult();
		ngramCount = null;
		featureWords = new HashSet();
		graph = null;
	}

	public int hotView(String indexPath, String params, StringBuffer buffer)
	{
		int ret;
label0:
		{
			ret = 0;
			logger.info("hotView | enter.");
			try
			{
				ngramCount = new NgramCount(2, 2);
				itmParamParser.clear();
				itmParamParser.funcFlag = com.iflytek.itm.util.ITMParamParser.ITM_FUNCTION_FLAG.ITM_HOT_VIEW;
				ret = itmParamParser.parse(params);
				ret = checkParams(ret);
				if (ret != 0)
				{
					logger.error((new StringBuilder()).append("hotView | param error, params=").append(params).append(", errcode=").append(ret).toString());
					break label0;
				}
				logger.info("open index enter");
				ret = searchWrapper.open(indexPath, itmParamParser);
				logger.info("open index leave");
				if (ret != 0)
				{
					logger.error((new StringBuilder()).append("hotView | Error: searchWrapper.open, errcode=").append(ret).toString());
					break label0;
				}
				logger.info("fetchDocuments enter");
				ret = fetchDocuments();
				logger.info("fetchDocuments leave");
				if (ret != 0)
				{
					logger.error((new StringBuilder()).append("hotView | fetchDocuments error, errcode=").append(ret).toString());
					break label0;
				}
				if (docContents.size() == 0)
				{
					logger.warn("hotView | fetchDocuments empty, documents to do mining is empty.Please check the params.");
					break label0;
				}
				logger.info((new StringBuilder()).append("doCluster enter, hot_view_mode=").append(itmParamParser.hotViewMode).append(", feature_words_top=").append(itmParamParser.hotViewFeatureWordsTopN).toString());
				if (itmParamParser.hotViewMode == 0)
					ret = doClusterByResource();
				else
				if (itmParamParser.hotViewMode == 1)
					ret = doClusterByBackground();
				else
				if (itmParamParser.hotViewMode == 2)
				{
					logger.info("hotView | do cluster enter");
					ret = doClusterByBackground();
					logger.info("hotView | do cluster leave");
					logger.info("hotView | do world enter");
					ret = doWorldLet();
					logger.info("hotView | do world leave");
				}
				logger.info("doCluster leave");
				if (ret != 0)
				{
					logger.error((new StringBuilder()).append("hotView | doCluster error, errcode=").append(ret).toString());
					break label0;
				}
				if (itmParamParser.hotViewMode == 2)
				{
					logger.info("fetchResult enter");
					ret = fetchGraphResult(buffer);
					logger.info("fetchResult leave");
					if (ret != 0)
					{
						logger.error((new StringBuilder()).append("hotView | fetchResult error, errcode=").append(ret).toString());
						break label0;
					}
				} else
				{
					logger.info("fetchResult enter");
					ret = fetchResult(buffer);
					logger.info("fetchResult leave");
					if (ret != 0)
					{
						logger.error((new StringBuilder()).append("hotView | fetchResult error, errcode=").append(ret).toString());
						break label0;
					}
				}
				ret = searchWrapper.close();
				if (ret != 0)
					logger.error((new StringBuilder()).append("hotView | Error: searchWrapper.close, errcode=").append(ret).toString());
			}
			catch (IOException e)
			{
				ret = ITMErrors.ITM_ERROR_INDEX_IO_EXCEPTION.code();
				logger.error((new StringBuilder()).append("hotView | IOException, msg=").append(e.getMessage()).append(", errcode=").append(ret).toString());
			}
		}
		logger.info("hotView | leave.");
		return ret;
	}

	private int fetchResult(StringBuffer buffer)
		throws IOException
	{
		int ret = 0;
		Map classDocNum = new HashMap(biCluster.numClusters);
		for (int i = 0; i < biCluster.numClusters; i++)
		{
			addOneClusterWords(i, biCluster.clusters[i]);
			classDocNum.put(Integer.valueOf(i), Integer.valueOf(sampleToTotal(biCluster.clusters[i].numMember, totalHits, docContents.size())));
		}

		result.fetchResult(biCluster.numClusters, itmParamParser.hotViewWordTopN, classDocNum, buffer);
		return ret;
	}

	private int fetchGraphResult(StringBuffer buffer)
	{
		int ret = 0;
		if (-1 == itmParamParser.hotViewClusterIndex)
			ret = wlr.fetchResult(buffer, graph);
		else
			ret = wlr.fetchResult(buffer, graph[itmParamParser.hotViewClusterIndex]);
		return ret;
	}

	public static int sampleToTotal(int value, int totalDocHits, int sampleDocCount)
	{
		return (int)(((double)totalDocHits / (double)sampleDocCount) * (double)value);
	}

	private int addOneClusterWords(int c, Cluster cluster)
		throws IOException
	{
		ngramCount.ngrams.clear();
		for (int i = 0; i < cluster.numMember; i++)
		{
			int docIndex = cluster.members[i];
			String content = (String)docContents.get(docIndex);
			Analyzer analyzer = searchWrapper.itmUserData.getAnalyzer();
			ngramCount.generate(itmParamParser.mining_field, content, analyzer, featureWords);
		}

		Set keys = ngramCount.ngrams.keySet();
		String word;
		int count;
		for (Iterator it = keys.iterator(); it.hasNext(); result.addRelWordInfo(word, count, c))
		{
			word = (String)it.next();
			count = ((Integer)ngramCount.ngrams.get(word)).intValue();
			count = sampleToTotal(count, totalHits, docContents.size());
		}

		return 0;
	}

	private int doWorldLet()
	{
		int ret = 0;
		int word_top_n = itmParamParser.hotViewWordTopN;
		logger.info("doWorldLet | do split word enter");
		ret = splitWords();
		logger.info("doWorldLet | do split word leave");
		graph = new SimpleGraph[biCluster.numClusters];
		if (-1 == itmParamParser.hotViewClusterIndex)
		{
			for (int i = 0; i < biCluster.numClusters; i++)
				graph[i] = wlr.doWorldlet(docContents, biCluster.clusters[i].members, biCluster.clusters[i].numMember, word_top_n);

		} else
		{
			int index = itmParamParser.hotViewClusterIndex;
			graph[index] = wlr.doWorldlet(docContents, biCluster.clusters[index].members, biCluster.clusters[index].numMember, word_top_n);
		}
		return ret;
	}

	private int splitWords()
	{
		int ret = 0;
		Analyzer analyzer = new IKAnalyzer(true);
		String field = itmParamParser.mining_field;
		int size = docContents.size();
		List docs = new ArrayList(size);
		StringBuffer buf = new StringBuffer();
		try
		{
			for (int i = 0; i < size; i++)
			{
				buf.setLength(0);
				TokenStream stream = analyzer.tokenStream(field, new StringReader((String)docContents.get(i)));
				CharTermAttribute termTextAttr = (CharTermAttribute)stream.getAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
				stream.reset();
				String word;
				for (; stream.incrementToken(); buf.append(word).append(" "))
					word = termTextAttr.toString();

				docs.add(buf.toString().trim());
				stream.end();
				stream.close();
			}

			docContents = docs;
			analyzer.close();
		}
		catch (IOException e)
		{
			ret = -1;
		}
		return ret;
	}

	private int doClusterByBackground()
		throws IOException
	{
		int ret = 0;
		BackgroundPattern pattern = new BackgroundPattern(searchWrapper, itmParamParser);
		List diffWords = pattern.calcDiffWords(docContents, itmParamParser.hotViewFeatureWordsTopN);
		biCluster = new BisectingKmeans(docContents.size(), pattern.dim + 1, itmParamParser.hotViewTopN);
		for (int i = 0; i < docContents.size(); i++)
		{
			String content = (String)docContents.get(i);
			double features[] = pattern.featureArray(i, content, diffWords);
			biCluster.SetPattern(features, i);
		}

		biCluster.initClusters();
		biCluster.runClust();
		for (int i = 0; i < diffWords.size(); i++)
			featureWords.add(((WordInfo)diffWords.get(i)).word);

		return ret;
	}

	private int doClusterByResource()
		throws IOException
	{
		int ret = 0;
		Pattern pattern = ITMMiningResource.inst().pattern;
		biCluster = new BisectingKmeans(docContents.size(), pattern.dim + 1, itmParamParser.hotViewTopN);
		for (int i = 0; i < docContents.size(); i++)
		{
			String content = (String)docContents.get(i);
			Analyzer analyzer = searchWrapper.itmUserData.getAnalyzer();
			double features[] = pattern.featureArray(itmParamParser.mining_field, content, analyzer);
			biCluster.SetPattern(features, i);
		}

		biCluster.initClusters();
		biCluster.runClust();
		Set keys = pattern.m_Synonym.keySet();
		for (Iterator it = keys.iterator(); it.hasNext(); featureWords.add((String)it.next()));
		return ret;
	}

	private int fetchDocuments()
		throws IOException
	{
		int ret = 0;
		com.iflytek.itm.api.ITMBuilder.ITMField itm_field = searchWrapper.itmUserData.getField(itmParamParser.mining_field);
		if (null == itm_field)
		{
			ret = ITMErrors.ITM_ERROR_MINING_FIELD_NOT_EXISTED.code();
			logger.error((new StringBuilder()).append("hotView | fetchDocuments error, msg=mining域不存在，errcode=").append(ret).toString());
			return ret;
		}
		if (null != itmParamParser.id_field && null == searchWrapper.itmUserData.getField(itmParamParser.id_field))
		{
			ret = ITMErrors.ITM_ERROR_ID_FIELD_NOT_EXISTED.code();
			logger.error((new StringBuilder()).append("hotView | fetchDocuments error, msg=id域不存在，errcode=").append(ret).toString());
			return ret;
		}
		if (!itm_field.type.equals("string"))
		{
			ret = ITMErrors.ITM_ERROR_FIELD_TYPE_NOT_SUPPORT.code();
			logger.error((new StringBuilder()).append("hotView | fetchDocuments error, msg=不支持的mining域，errcode=").append(ret).toString());
			return ret;
		}
		String queryString = ITMUtils.getFinalQueryString(itmParamParser);
		logger.info((new StringBuilder()).append("sample_rate=").append(itmParamParser.sampleRate).toString());
		itmParamParser.pageSize = itmParamParser.sampleRate;
		itmParamParser.currentPage = 1;
		TopDocs topDocs = new TopDocs(0, null, 0.0F);
		ret = ITMIndexSearcher.searchLucene(searchWrapper, queryString, "", itmParamParser, topDocs, null);
		if (ret != 0)
		{
			logger.error((new StringBuilder()).append("hotView | fetchDocuments error, msg=检索出错，errcode=").append(ret).toString());
			return ret;
		} else
		{
			totalHits = topDocs.totalHits;
			logger.info((new StringBuilder()).append("TotalHitDoc=").append(topDocs.totalHits).append(", ScoreDoc=").append(topDocs.scoreDocs.length).toString());
			logger.info("pullDocs enter");
			docContents = ITMTrade.pullDocs(topDocs, searchWrapper, itmParamParser);
			logger.info("pullDocs leave");
			return ret;
		}
	}

	private int checkParams(int preRet)
	{
		if (preRet != 0)
		{
			logger.error((new StringBuilder()).append("hotView | Error: checkParams, msg=参数格式错误, errcode=").append(preRet).toString());
			return preRet;
		}
		int ret = 0;
		if (itmParamParser.mining_field == null || itmParamParser.mining_field.isEmpty())
		{
			ret = ITMErrors.ITM_ERROR_INVALID_PARAM.code();
			logger.error((new StringBuilder()).append("hotView | Error: checkParams, msg=没有指定mining_field, errcode=").append(ret).toString());
		}
		return ret;
	}

}
