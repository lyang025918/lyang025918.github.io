// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ITMRule.java

package com.iflytek.itm.mining;

import com.iflytek.itm.api.ITMErrors;
import com.iflytek.itm.search.*;
import com.iflytek.itm.util.*;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.*;

// Referenced classes of package com.iflytek.itm.mining:
//			ITMRuleInfo

public class ITMRule
{

	private ITMParamParser itmParamParser;
	private ITMSearchWrapper searchWrapper;
	private Map ruleTopDocs;
	private Map docRules;
	private static final Logger logger = Logger.getLogger(com/iflytek/itm/mining/ITMRule);

	public ITMRule()
	{
		itmParamParser = new ITMParamParser();
		searchWrapper = new ITMSearchWrapper();
		ruleTopDocs = new HashMap();
		docRules = new TreeMap();
	}

	public int rule(String indexPath, String params, StringBuffer buffer)
	{
		int ret;
		ret = 0;
		logger.info("rule | enter.");
		itmParamParser.clear();
		itmParamParser.funcFlag = com.iflytek.itm.util.ITMParamParser.ITM_FUNCTION_FLAG.ITM_RULE;
		ret = itmParamParser.parse(params);
		if (!itmParamParser.isCheckRule)
			break MISSING_BLOCK_LABEL_109;
		if (itmParamParser.rules.size() == 0)
		{
			ret = ITMErrors.ITM_ERROR_INVALID_RULE_SYNTAX.code();
			logger.error((new StringBuilder()).append("rule | Error: check rule, errcode=").append(ret).toString());
		}
		logger.info("rule | check rule leave.");
		return ret;
		try
		{
			ret = checkParams(ret);
			if (ret != 0)
			{
				logger.error((new StringBuilder()).append("rule | Error: ruleParamParser, params=").append(params).append(", errcode=").append(ret).toString());
			} else
			{
				ret = searchWrapper.open(indexPath, itmParamParser);
				if (ret != 0)
				{
					logger.error((new StringBuilder()).append("rule | Error: searchWrapper.open, errcode=").append(ret).toString());
				} else
				{
					ret = searchTopDocs();
					if (ret != 0)
					{
						logger.error((new StringBuilder()).append("rule | Error: searchTopDocs, errcode=").append(ret).toString());
					} else
					{
						ret = groupByDoc();
						if (ret != 0)
						{
							logger.error((new StringBuilder()).append("rule | Error: groupByDoc, errcode=").append(ret).toString());
						} else
						{
							ret = fetchResult(buffer);
							if (ret != 0)
							{
								logger.error((new StringBuilder()).append("rule | Error: fetchResult, errcode=").append(ret).toString());
							} else
							{
								ret = searchWrapper.close();
								if (ret != 0)
									logger.error((new StringBuilder()).append("rule | Error: searchWrapper.close, errcode=").append(ret).toString());
							}
						}
					}
				}
			}
		}
		catch (IOException e)
		{
			ret = ITMErrors.ITM_ERROR_INDEX_IO_EXCEPTION.code();
			logger.error((new StringBuilder()).append("rule | errorMsg=").append(e.getMessage()).append(", errcode=").append(ret).toString());
		}
		logger.info("rule | leave.");
		return ret;
	}

	private int groupByDoc()
	{
		int ret = 0;
		for (Iterator it = ruleTopDocs.keySet().iterator(); it.hasNext();)
		{
			ITMRuleInfo rule = (ITMRuleInfo)it.next();
			TopDocs topDocs = (TopDocs)ruleTopDocs.get(rule);
			ScoreDoc scoreDocs[] = topDocs.scoreDocs;
			int i = 0;
			while (i < scoreDocs.length) 
			{
				int docId = scoreDocs[i].doc;
				if (docRules.containsKey(Integer.valueOf(docId)))
				{
					List thisDocRules = (List)docRules.get(Integer.valueOf(docId));
					thisDocRules.add(rule);
				} else
				{
					List emptyRules = new ArrayList();
					emptyRules.add(rule);
					docRules.put(Integer.valueOf(docId), emptyRules);
				}
				i++;
			}
		}

		return ret;
	}

	private int searchTopDocs()
	{
		int ret = 0;
		String filterString = ITMUtils.getFinalQueryString(itmParamParser, true);
		itmParamParser.sortField = itmParamParser.id_field;
		itmParamParser.pageSize = 0x7fffffff;
		itmParamParser.currentPage = 1;
		for (int i = 0; i < itmParamParser.rules.size(); i++)
		{
			ITMRuleInfo rule = (ITMRuleInfo)itmParamParser.rules.get(i);
			TopDocs topDocs1 = new TopDocs(0, null, 0.0F);
			logger.info((new StringBuilder()).append("rule.syntax=").append(rule.syntax).toString());
			ret = ITMIndexSearcher.searchLucene(searchWrapper, (new StringBuilder()).append(ITMConstants.ITM_RULE_INNER_FIELD_PREFIX).append(itmParamParser.mining_field).append(":").append(rule.syntax).toString(), filterString, itmParamParser, topDocs1, null);
			if (topDocs1.totalHits > 0)
				ruleTopDocs.put(rule, topDocs1);
		}

		return ret;
	}

	private int fetchResult(StringBuffer buffer)
		throws IOException
	{
		int ret = 0;
		buffer.setLength(0);
		Iterator it = docRules.keySet().iterator();
		do
		{
			if (!it.hasNext())
				break;
			int docId = ((Integer)it.next()).intValue();
			List ruleInfos = (List)docRules.get(Integer.valueOf(docId));
			Document doc = searchWrapper.searcher.doc(docId);
			String id = doc.get(itmParamParser.id_field);
			String content = doc.get(itmParamParser.mining_field);
			if (ITMUtils.isValidStr(content))
			{
				buffer.append((new StringBuilder()).append(id).append("@").toString());
				findMatchPos(id, content, ruleInfos, buffer);
				buffer.append("%");
			}
		} while (true);
		checkEndChar(buffer, '%');
		return ret;
	}

	void findMatchPos(String id, String content, List ruleInfos, StringBuffer buffer)
	{
		content = content.toLowerCase();
		for (int i = 0; i < ruleInfos.size(); i++)
		{
			ITMRuleInfo ruleInfo = (ITMRuleInfo)ruleInfos.get(i);
			buffer.append((new StringBuilder()).append(ruleInfo.ruleName).append(":").toString());
			for (int iw = 0; iw < ruleInfo.words.length; iw++)
			{
				String word = ruleInfo.words[iw];
				if (word == null || word.isEmpty())
					continue;
				Pattern pattern = Pattern.compile(ITMRuleInfo.toRegexStr(word, itmParamParser.ruleNearSpan, false));
				int bos;
				int eos;
				for (Matcher matcher = pattern.matcher(content); matcher.find(); buffer.append((new StringBuilder()).append(word).append(",").append(bos).append(",").append(eos).append("|").toString()))
				{
					bos = matcher.start();
					eos = matcher.end();
				}

				if (word.indexOf("#") <= 0)
					continue;
				Pattern pattern2 = Pattern.compile(ITMRuleInfo.toRegexStr(word, itmParamParser.ruleNearSpan, true));
				int bos;
				int eos;
				for (Matcher matcher2 = pattern2.matcher(content); matcher2.find(); buffer.append((new StringBuilder()).append(word).append(",").append(bos).append(",").append(eos).append("|").toString()))
				{
					bos = matcher2.start();
					eos = matcher2.end();
				}

			}

			checkEndChar(buffer, '|');
			buffer.append("*");
		}

		checkEndChar(buffer, '*');
	}

	public void checkEndChar(StringBuffer buffer, char c)
	{
		if (buffer != null && buffer.length() > 0 && buffer.charAt(buffer.length() - 1) == c)
			buffer.deleteCharAt(buffer.length() - 1);
	}

	private int calcNearGap(String word, Matcher matcher)
	{
		int trueGap = 0;
		String gapStr = matcher.group();
		gapStr = ITMUtils.delPunctuation(gapStr);
		trueGap = (gapStr.length() - word.length()) + 1;
		return trueGap;
	}

	private int checkParams(int preRet)
	{
		if (preRet != 0)
		{
			logger.error((new StringBuilder()).append("rule | Error: checkParams, msg=参数格式错误, errcode=").append(preRet).toString());
			return preRet;
		}
		int ret = 0;
		if (itmParamParser.rules.size() == 0)
		{
			ret = ITMErrors.ITM_ERROR_INVALID_PARAM.code();
			logger.error((new StringBuilder()).append("rule | Error: checkParams, msg=没有指定rule, errcode=").append(ret).toString());
		} else
		if (itmParamParser.mining_field == null || itmParamParser.mining_field.isEmpty())
		{
			ret = ITMErrors.ITM_ERROR_INVALID_PARAM.code();
			logger.error((new StringBuilder()).append("rule | Error: checkParams, msg=没有指定mining_field, errcode=").append(ret).toString());
		}
		return ret;
	}

}
