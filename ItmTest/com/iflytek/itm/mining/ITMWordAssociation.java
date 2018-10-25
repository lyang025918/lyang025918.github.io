// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ITMWordAssociation.java

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
import org.apache.lucene.search.TopDocs;

// Referenced classes of package com.iflytek.itm.mining:
//			BackgroundPattern, WordInfo, WordInfoListComp, ITMTrade

public class ITMWordAssociation
{

	private static final Logger logger = Logger.getLogger(com/iflytek/itm/mining/ITMWordAssociation);
	private ITMParamParser itmParamParser;
	private ITMSearchWrapper searchWrapper;
	private Set alreadyWords;

	public ITMWordAssociation()
	{
		itmParamParser = new ITMParamParser();
		searchWrapper = new ITMSearchWrapper();
		alreadyWords = new HashSet();
	}

	public int wordAssociation(String indexPath, String params, StringBuffer buffer)
	{
		int ret = 0;
		logger.info("wordAssociation | enter.");
		try
		{
			itmParamParser.clear();
			itmParamParser.funcFlag = com.iflytek.itm.util.ITMParamParser.ITM_FUNCTION_FLAG.ITM_WORD_ASSOCIATION;
			ret = itmParamParser.parse(params);
			ret = checkParams(ret);
			if (ret != 0)
			{
				logger.error((new StringBuilder()).append("wordAssociation | param error, params=").append(params).append(", errcode=").append(ret).toString());
			} else
			{
				logger.info("open index enter");
				ret = searchWrapper.open(indexPath, itmParamParser);
				logger.info("open index leave");
				if (ret != 0)
				{
					logger.error((new StringBuilder()).append("wordAssociation | Error: searchWrapper.open, errcode=").append(ret).toString());
				} else
				{
					logger.info("fetchResult enter");
					ret = fetchResult(buffer);
					logger.info("fetchResult leave");
					if (ret != 0)
					{
						logger.error((new StringBuilder()).append("wordAssociation | fetchResult error, errcode=").append(ret).toString());
					} else
					{
						ret = searchWrapper.close();
						if (ret != 0)
							logger.error((new StringBuilder()).append("wordAssociation | Error: searchWrapper.close, errcode=").append(ret).toString());
					}
				}
			}
		}
		catch (IOException e)
		{
			ret = ITMErrors.ITM_ERROR_INDEX_IO_EXCEPTION.code();
			logger.error((new StringBuilder()).append("wordAssociation | IOException, msg=").append(e.getMessage()).append(", errcode=").append(ret).toString());
		}
		logger.info("wordAssociation | leave.");
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
			logger.error((new StringBuilder()).append("wordAssociation | fetchResult error, msg=mining域不存在，errcode=").append(ret).toString());
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
			logger.error((new StringBuilder()).append("wordAssociation | fetchResult error, msg=不支持的mining域，errcode=").append(ret).toString());
			return ret;
		}
		alreadyWords.add(itmParamParser.word_association_word);
		String query = ITMUtils.getFinalQueryString(itmParamParser);
		query = (new StringBuilder()).append(query).append(" +(").append(itmParamParser.mining_field).append(":").append(itmParamParser.word_association_word).append(")").toString();
		logger.info((new StringBuilder()).append("sample_rate=").append(itmParamParser.sampleRate).toString());
		itmParamParser.pageSize = itmParamParser.sampleRate;
		itmParamParser.currentPage = 1;
		TopDocs topDocs = new TopDocs(0, null, 0.0F);
		logger.info("searchLucene enter");
		ret = ITMIndexSearcher.searchLucene(searchWrapper, query, "", itmParamParser, topDocs, null);
		logger.info("searchLucene leave");
		if (ret != 0)
		{
			logger.error((new StringBuilder()).append("wordAssociation | fetchResult error, msg=searchLucene返回失败，errcode=").append(ret).toString());
			return ret;
		}
		logger.info((new StringBuilder()).append("TotalHitDoc=").append(topDocs.totalHits).append(", ScoreDoc=").append(topDocs.scoreDocs.length).toString());
		logger.info("pullDocs enter");
		List docContents = ITMTrade.pullDocs(topDocs, searchWrapper, itmParamParser);
		logger.info("pullDocs leave");
		BackgroundPattern pattern = new BackgroundPattern(searchWrapper, itmParamParser);
		Map wordScoreMap = new HashMap();
		Map docWordCount = new HashMap();
		int sampleTotalWordCount = 0;
		sampleTotalWordCount = pattern.fetchWordInfos(docContents, wordScoreMap, docWordCount);
		List firstWords = fetchAssociationWords(itmParamParser.word_association_word, itmParamParser.wordTopNList[0], docContents.size(), sampleTotalWordCount, wordScoreMap, pattern);
		addToAlreadySet(firstWords);
		if (itmParamParser.wordTopNList.length == 1)
		{
			float totalScore = ITMTrade.calcTotalScore(firstWords);
			int i = 0;
			do
			{
				if (i >= firstWords.size())
					break;
				WordInfo info = (WordInfo)firstWords.get(i);
				int outScore = ITMTrade.outputScore(info, totalScore);
				if (outScore == 0)
					break;
				buffer.append(info.word).append("(").append(outScore).append("%);");
				i++;
			} while (true);
		} else
		{
			float totalScore = ITMTrade.calcTotalScore(firstWords);
			for (int i = 0; i < firstWords.size(); i++)
			{
				WordInfo info = (WordInfo)firstWords.get(i);
				int outScore = ITMTrade.outputScore(info, totalScore);
				if (outScore == 0)
					break;
				buffer.append(info.word).append("(").append(outScore).append("%):");
				List secondWords = fetchAssociationWords(info.word, itmParamParser.wordTopNList[1], docContents.size(), sampleTotalWordCount, wordScoreMap, pattern);
				addToAlreadySet(secondWords);
				float totalScore2 = ITMTrade.calcTotalScore(secondWords);
				int j = 0;
				do
				{
					if (j >= secondWords.size())
						break;
					WordInfo info2 = (WordInfo)secondWords.get(j);
					int outScore2 = ITMTrade.outputScore(info2, totalScore2);
					if (outScore2 == 0)
						break;
					buffer.append(info2.word).append("(").append(outScore2).append("%)|");
					j++;
				} while (true);
				ITMUtils.deleteEndChar(buffer, '|');
				buffer.append(";");
			}

		}
		ITMUtils.deleteEndChar(buffer, ';');
		return ret;
	}

	private List fetchAssociationWords(String dstWord, int topN, int totalDocCount, int totalWordCount, Map wordScoreMap, BackgroundPattern pattern)
		throws IOException
	{
		List finalWords = new ArrayList();
		logger.info((new StringBuilder()).append("dst word=").append(dstWord).toString());
		Set keys = wordScoreMap.keySet();
		Iterator it = keys.iterator();
		do
		{
			if (!it.hasNext())
				break;
			String word = (String)it.next();
			if (!alreadyWords.contains(word))
			{
				WordInfo info = (WordInfo)wordScoreMap.get(word);
				info.totalDocCount = totalDocCount;
				info.totalTermCount = totalWordCount;
				info.score = pattern.diffScore(info, totalDocCount);
				finalWords.add(info);
			}
		} while (true);
		WordInfoListComp comp = new WordInfoListComp();
		Collections.sort(finalWords, comp);
		int top = finalWords.size() <= topN ? finalWords.size() : topN;
		return finalWords.subList(0, top);
	}

	private void addToAlreadySet(List finalWords)
	{
		for (int i = 0; i < finalWords.size(); i++)
		{
			WordInfo info = (WordInfo)finalWords.get(i);
			alreadyWords.add(info.word);
		}

	}

	private int checkParams(int preRet)
	{
		if (preRet != 0)
		{
			logger.error((new StringBuilder()).append("wordAssociation | Error: checkParams, msg=参数格式错误, errcode=").append(preRet).toString());
			return preRet;
		}
		int ret = 0;
		if (itmParamParser.mining_field == null || itmParamParser.mining_field.isEmpty())
		{
			ret = ITMErrors.ITM_ERROR_INVALID_PARAM.code();
			logger.error((new StringBuilder()).append("wordAssociation | Error: checkParams, msg=没有指定mining_field, errcode=").append(ret).toString());
		} else
		if (itmParamParser.word_association_word == null || itmParamParser.word_association_word.isEmpty())
		{
			ret = ITMErrors.ITM_ERROR_INVALID_PARAM.code();
			logger.error((new StringBuilder()).append("wordAssociation | Error: checkParams, msg=没有指定word_association_word, errcode=").append(ret).toString());
		} else
		if (!ITMUtils.isValidWord(itmParamParser.word_association_word))
		{
			ret = ITMErrors.ITM_ERROR_INVALID_PARAM_VALUE.code();
			logger.error((new StringBuilder()).append("wordAssociation | Error: checkParams, msg=词语联想的词只能是汉字，A-Z，0-9，a-z, word=").append(itmParamParser.word_association_word).append(", errcode=").append(ret).toString());
		} else
		if (itmParamParser.wordTopNList.length > 2)
		{
			ret = ITMErrors.ITM_ERROR_INVALID_PARAM_VALUE.code();
			logger.error((new StringBuilder()).append("wordAssociation | Error: checkParams, msg=暂时只支持两层哦, errcode=").append(ret).toString());
		}
		return ret;
	}

}
