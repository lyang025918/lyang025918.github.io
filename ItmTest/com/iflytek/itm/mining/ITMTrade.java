// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ITMTrade.java

package com.iflytek.itm.mining;

import com.iflytek.itm.api.ITMBuilder;
import com.iflytek.itm.api.ITMErrors;
import com.iflytek.itm.build.ITMUserData;
import com.iflytek.itm.search.*;
import com.iflytek.itm.util.ITMParamParser;
import com.iflytek.itm.util.ITMUtils;
import java.io.IOException;
import java.util.*;
import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.*;

// Referenced classes of package com.iflytek.itm.mining:
//			BackgroundPattern, WordInfo, ITMHotView

public class ITMTrade
{

	private static final Logger logger = Logger.getLogger(com/iflytek/itm/mining/ITMTrade);
	private ITMParamParser itmParamParser;
	private ITMSearchWrapper searchWrapper;

	public ITMTrade()
	{
		itmParamParser = new ITMParamParser();
		searchWrapper = new ITMSearchWrapper();
	}

	public int trade(String indexPath, String params, StringBuffer buffer)
	{
		int ret = 0;
		logger.info("trade | enter.");
		try
		{
			itmParamParser.clear();
			itmParamParser.funcFlag = com.iflytek.itm.util.ITMParamParser.ITM_FUNCTION_FLAG.ITM_TRADE;
			ret = itmParamParser.parse(params);
			ret = checkParams(ret);
			if (ret != 0)
			{
				logger.error((new StringBuilder()).append("trade | param error, params=").append(params).append(", errcode=").append(ret).toString());
			} else
			{
				logger.info("open index enter");
				ret = searchWrapper.open(indexPath, itmParamParser);
				logger.info("open index leave");
				if (ret != 0)
				{
					logger.error((new StringBuilder()).append("trade | Error: searchWrapper.open, errcode=").append(ret).toString());
				} else
				{
					ret = fetchResult(buffer);
					if (ret != 0)
					{
						logger.error((new StringBuilder()).append("trade | fetchResult error, errcode=").append(ret).toString());
					} else
					{
						ret = searchWrapper.close();
						if (ret != 0)
							logger.error((new StringBuilder()).append("trade | Error: searchWrapper.close, errcode=").append(ret).toString());
					}
				}
			}
		}
		catch (IOException e)
		{
			ret = ITMErrors.ITM_ERROR_INDEX_IO_EXCEPTION.code();
			logger.error((new StringBuilder()).append("trade | IOException, msg=").append(e.getMessage()).append(", errcode=").append(ret).toString());
		}
		logger.info("trade | leave.");
		return ret;
	}

	private int fetchResult(StringBuffer buffer)
		throws IOException
	{
		int ret = 0;
		com.iflytek.itm.api.ITMBuilder.ITMField itm_field = searchWrapper.itmUserData.getField(itmParamParser.mining_field);
		if (null == itm_field)
		{
			ret = ITMErrors.ITM_ERROR_MINING_FIELD_NOT_EXISTED.code();
			logger.error((new StringBuilder()).append("trade | fetchResult error, msg=mining域不存在，errcode=").append(ret).toString());
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
			logger.error((new StringBuilder()).append("trade | fetchResult error, msg=不支持的mining域，errcode=").append(ret).toString());
			return ret;
		}
		String query = ITMUtils.getFinalQueryString(itmParamParser);
		logger.info((new StringBuilder()).append("sample_rate=").append(itmParamParser.sampleRate).toString());
		itmParamParser.pageSize = itmParamParser.sampleRate;
		itmParamParser.currentPage = 1;
		TopDocs topDocs = new TopDocs(0, null, 0.0F);
		logger.info((new StringBuilder()).append("searchLucene enter, query=").append(query).toString());
		ret = ITMIndexSearcher.searchLucene(searchWrapper, query, "", itmParamParser, topDocs, null);
		logger.info("searchLucene leave");
		if (ret != 0)
		{
			logger.error((new StringBuilder()).append("trade | fetchResult error, msg=searchLucene返回失败，errcode=").append(ret).toString());
			return ret;
		}
		logger.info((new StringBuilder()).append("TotalHitDoc=").append(topDocs.totalHits).append(", ScoreDoc=").append(topDocs.scoreDocs.length).toString());
		logger.info("pullDocs enter");
		List docContents = pullDocs(topDocs, searchWrapper, itmParamParser);
		logger.info("pullDocs leave");
		logger.info("calcDiffWords enter");
		BackgroundPattern pattern = new BackgroundPattern(searchWrapper, itmParamParser);
		List firstWords = pattern.calcDiffWords(docContents, itmParamParser.tradeTopN);
		logger.info("calcDiffWords leave");
		logger.info("pack result enter");
		if (ITMUtils.isValidStr(itmParamParser.tradeResultType) && itmParamParser.tradeResultType.equals("tf-idf"))
		{
			for (int i = 0; i < firstWords.size(); i++)
			{
				WordInfo info = (WordInfo)firstWords.get(i);
				buffer.append(info.word).append("(").append(ITMHotView.sampleToTotal(info.totalTermCount, topDocs.totalHits, docContents.size())).append(",").append(ITMHotView.sampleToTotal(info.termFreq, topDocs.totalHits, docContents.size())).append(",").append(ITMHotView.sampleToTotal(info.totalDocCount, topDocs.totalHits, docContents.size())).append(",").append(ITMHotView.sampleToTotal(info.docFreq, topDocs.totalHits, docContents.size())).append(");");
			}

		} else
		{
			for (int i = 0; i < firstWords.size(); i++)
			{
				WordInfo info = (WordInfo)firstWords.get(i);
				buffer.append(info.word).append("(").append(info.score).append(");");
			}

		}
		ITMUtils.deleteEndChar(buffer, ';');
		logger.info("pack result leave");
		return ret;
	}

	public static List pullDocs(TopDocs topDocs, ITMSearchWrapper searchWrapper, ITMParamParser itmParamParser)
		throws IOException
	{
		Set fieldsToLoad = new HashSet();
		fieldsToLoad.add(itmParamParser.id_field);
		fieldsToLoad.add(itmParamParser.mining_field);
		List docContents = new ArrayList(topDocs.scoreDocs.length);
		for (int i = 0; i < topDocs.scoreDocs.length; i++)
		{
			Document document = searchWrapper.searcher.document(topDocs.scoreDocs[i].doc, fieldsToLoad);
			String content = document.get(itmParamParser.mining_field);
			if (ITMUtils.isValidStr(content))
				docContents.add(content);
		}

		return docContents;
	}

	public static float calcTotalScore(List wordInfos)
	{
		float sum = 0.0F;
		int size = wordInfos.size();
		WordInfo min_info = (WordInfo)wordInfos.get(size - 1);
		for (int i = 0; i < size; i++)
		{
			WordInfo info = (WordInfo)wordInfos.get(i);
			info.score = (float)((double)(info.score - min_info.score) + 0.01D) * 100F;
			sum += info.score;
		}

		return sum;
	}

	public static int outputScore(WordInfo info, float totalScore)
	{
		int outScore = (int)((info.score * 100F) / totalScore);
		if (0 == outScore)
			outScore = 1;
		return outScore;
	}

	private int checkParams(int preRet)
	{
		if (preRet != 0)
		{
			logger.error((new StringBuilder()).append("trade | Error: checkParams, msg=参数格式错误, errcode=").append(preRet).toString());
			return preRet;
		}
		int ret = 0;
		if (itmParamParser.mining_field == null || itmParamParser.mining_field.isEmpty())
		{
			ret = ITMErrors.ITM_ERROR_INVALID_PARAM.code();
			logger.error((new StringBuilder()).append("trade | Error: checkParams, msg=没有指定mining_field, errcode=").append(ret).toString());
		}
		return ret;
	}

}
