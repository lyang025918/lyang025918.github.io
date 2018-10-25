// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ITMParamParser.java

package com.iflytek.itm.util;

import com.iflytek.itm.api.ITMErrors;
import com.iflytek.itm.api.ITMParams;
import com.iflytek.itm.mining.ITMRuleInfo;
import java.util.*;
import org.apache.log4j.Logger;

// Referenced classes of package com.iflytek.itm.util:
//			ParamParser, ITMUtils

public class ITMParamParser
{
	public static final class ITM_FUNCTION_FLAG extends Enum
	{

		public static final ITM_FUNCTION_FLAG ITM_NONE;
		public static final ITM_FUNCTION_FLAG ITM_BUILD;
		public static final ITM_FUNCTION_FLAG ITM_SEARCH;
		public static final ITM_FUNCTION_FLAG ITM_RULE;
		public static final ITM_FUNCTION_FLAG ITM_WORD_ASSOCIATION;
		public static final ITM_FUNCTION_FLAG ITM_HOT_VIEW;
		public static final ITM_FUNCTION_FLAG ITM_TRADE;
		public static final ITM_FUNCTION_FLAG ITM_ASSIST;
		private static final ITM_FUNCTION_FLAG $VALUES[];

		public static ITM_FUNCTION_FLAG[] values()
		{
			return (ITM_FUNCTION_FLAG[])$VALUES.clone();
		}

		public static ITM_FUNCTION_FLAG valueOf(String name)
		{
			return (ITM_FUNCTION_FLAG)Enum.valueOf(com/iflytek/itm/util/ITMParamParser$ITM_FUNCTION_FLAG, name);
		}

		static 
		{
			ITM_NONE = new ITM_FUNCTION_FLAG("ITM_NONE", 0);
			ITM_BUILD = new ITM_FUNCTION_FLAG("ITM_BUILD", 1);
			ITM_SEARCH = new ITM_FUNCTION_FLAG("ITM_SEARCH", 2);
			ITM_RULE = new ITM_FUNCTION_FLAG("ITM_RULE", 3);
			ITM_WORD_ASSOCIATION = new ITM_FUNCTION_FLAG("ITM_WORD_ASSOCIATION", 4);
			ITM_HOT_VIEW = new ITM_FUNCTION_FLAG("ITM_HOT_VIEW", 5);
			ITM_TRADE = new ITM_FUNCTION_FLAG("ITM_TRADE", 6);
			ITM_ASSIST = new ITM_FUNCTION_FLAG("ITM_ASSIST", 7);
			$VALUES = (new ITM_FUNCTION_FLAG[] {
				ITM_NONE, ITM_BUILD, ITM_SEARCH, ITM_RULE, ITM_WORD_ASSOCIATION, ITM_HOT_VIEW, ITM_TRADE, ITM_ASSIST
			});
		}

		private ITM_FUNCTION_FLAG(String s, int i)
		{
			super(s, i);
		}
	}


	public ITM_FUNCTION_FLAG funcFlag;
	public String subIndexDir;
	public boolean isUpdate;
	public boolean isDelete;
	public int threadNum;
	public int maxBufferedDocs;
	public boolean doMerge;
	public boolean doOptimize;
	public String subIndexDirList[];
	public int pageSize;
	public int currentPage;
	public String sortField;
	public boolean isSortReverse;
	public String groupField;
	public boolean isMultiValue;
	public String groups[];
	public String idList[];
	public String id_field;
	public String mining_field;
	public String mining_query;
	public List rules;
	public boolean fetchPos;
	public int ruleNearSpan;
	public boolean isCheckRule;
	public int hotViewTopN;
	public int hotViewFeatureWordsTopN;
	public int sampleRate;
	public int hotViewWordTopN;
	public int hotViewMode;
	public int hotViewClusterIndex;
	public String word_association_word;
	public int wordTopNList[];
	public int tradeTopN;
	public String tradeResultType;
	public int assistTopN;
	public String assistText;
	private static final Logger logger = Logger.getLogger(com/iflytek/itm/util/ITMParamParser);

	public ITMParamParser()
	{
		clear();
	}

	public int clear()
	{
		funcFlag = ITM_FUNCTION_FLAG.ITM_NONE;
		subIndexDir = "";
		isUpdate = false;
		isDelete = false;
		threadNum = 1;
		maxBufferedDocs = 0x186a0;
		doMerge = true;
		doOptimize = true;
		subIndexDirList = null;
		pageSize = 10;
		currentPage = 1;
		sortField = "id";
		isSortReverse = false;
		groupField = "";
		isMultiValue = false;
		groups = null;
		idList = null;
		mining_field = "";
		mining_query = "";
		rules = new ArrayList();
		fetchPos = false;
		ruleNearSpan = 10;
		isCheckRule = false;
		hotViewTopN = 5;
		hotViewFeatureWordsTopN = 50;
		sampleRate = 500;
		hotViewWordTopN = 10;
		hotViewMode = 1;
		hotViewClusterIndex = -1;
		word_association_word = "";
		wordTopNList = null;
		tradeTopN = 10;
		tradeResultType = "score";
		assistTopN = 1;
		assistText = "";
		return 0;
	}

	public int parse(String params)
	{
		int ret = 0;
		Map keyValues;
		keyValues = new HashMap();
		ret = ParamParser.parse(params, keyValues);
		if (ret != 0)
			break MISSING_BLOCK_LABEL_2177;
		subIndexDir = (String)keyValues.get(ITMParams.ITM_PARAM_SUB_INDEX_DIR);
		String isUpdateStr = (String)keyValues.get(ITMParams.ITM_PARAM_IS_UPDATE_DOCUMENT);
		if (isUpdateStr != null && !isUpdateStr.isEmpty())
			isUpdate = Boolean.valueOf(isUpdateStr).booleanValue();
		String isDeleteStr = (String)keyValues.get(ITMParams.ITM_PARAM_IS_DELETE_DOCUMENT);
		if (isDeleteStr != null && !isDeleteStr.isEmpty())
			isDelete = Boolean.valueOf(isDeleteStr).booleanValue();
		String doMergeStr = (String)keyValues.get(ITMParams.ITM_PARAM_DO_MERGE);
		if (doMergeStr != null && !doMergeStr.isEmpty())
			doMerge = Boolean.valueOf(doMergeStr).booleanValue();
		String doOptimizeStr = (String)keyValues.get(ITMParams.ITM_PARAM_DO_OPTIMIZE);
		if (doOptimizeStr != null && !doOptimizeStr.isEmpty())
			doOptimize = Boolean.valueOf(doOptimizeStr).booleanValue();
		String threadNumStr = (String)keyValues.get(ITMParams.ITM_PARAM_BUILD_THREAD_NUM);
		if (threadNumStr != null && !threadNumStr.isEmpty())
		{
			threadNum = Integer.valueOf(threadNumStr).intValue();
			if (!ITMUtils.isValidInt(threadNum, 1, 0x7fffffff))
			{
				ret = ITMErrors.ITM_ERROR_INVALID_PARAM_VALUE.code();
				logger.error((new StringBuilder()).append("parse | Error: parse, msg=build_thread_num至少为1, errcode=").append(ret).toString());
				break MISSING_BLOCK_LABEL_2177;
			}
		}
		String maxBufferedDocsStr = (String)keyValues.get(ITMParams.ITM_PARAM_MAX_BUFFERED_DOCS);
		if (maxBufferedDocsStr != null && !maxBufferedDocsStr.isEmpty())
		{
			maxBufferedDocs = Integer.valueOf(maxBufferedDocsStr).intValue();
			if (!ITMUtils.isValidInt(maxBufferedDocs, 1, 0x7fffffff))
			{
				ret = ITMErrors.ITM_ERROR_INVALID_PARAM_VALUE.code();
				logger.error((new StringBuilder()).append("parse | Error: parse, msg=maxBufferedDocs至少为2, errcode=").append(ret).toString());
				break MISSING_BLOCK_LABEL_2177;
			}
		}
		sortField = (String)keyValues.get(ITMParams.ITM_PARAM_SORT_FIELD);
		String isSortReverseStr = (String)keyValues.get(ITMParams.ITM_PARAM_IS_SORT_REVERSE);
		if (isSortReverseStr != null && !isSortReverseStr.isEmpty())
			isSortReverse = Boolean.valueOf(isSortReverseStr).booleanValue();
		groupField = (String)keyValues.get(ITMParams.ITM_PARAM_GROUP_FIELD);
		String isMultivalueStr = (String)keyValues.get(ITMParams.ITM_PARAM_GROUP_IS_MULTIVALUE);
		if (ITMUtils.isValidStr(isMultivalueStr))
			isMultiValue = Boolean.valueOf(isMultivalueStr).booleanValue();
		String groupsStr = (String)keyValues.get(ITMParams.ITM_PARAM_GROUPS);
		if (ITMUtils.isValidStr(groupsStr))
			groups = groupsStr.split(";");
		String subIndexDirStr = (String)keyValues.get(ITMParams.ITM_PARAM_SUB_INDEX_DIR_LIST);
		if (subIndexDirStr != null && !subIndexDirStr.isEmpty())
			subIndexDirList = subIndexDirStr.split(";");
		String idListStr = (String)keyValues.get(ITMParams.ITM_PARAM_ID_LIST);
		if (idListStr != null && !idListStr.isEmpty())
			idList = idListStr.split(";");
		mining_query = (String)keyValues.get(ITMParams.ITM_PARAM_MINING_QUERY);
		String fetchPosStr = (String)keyValues.get(ITMParams.ITM_PARAM_RULE_FETCH_POS);
		if (fetchPosStr != null && !fetchPosStr.isEmpty())
			fetchPos = Boolean.valueOf(fetchPosStr).booleanValue();
		String isCheckRuleStr = (String)keyValues.get(ITMParams.ITM_PARAM_IS_CHECK_RULE);
		if (isCheckRuleStr != null && !isCheckRuleStr.isEmpty())
			isCheckRule = Boolean.valueOf(isCheckRuleStr).booleanValue();
		String pageSizeStr = (String)keyValues.get(ITMParams.ITM_PARAM_PAGE_SIZE);
		if (pageSizeStr != null && !pageSizeStr.isEmpty())
		{
			pageSize = Integer.valueOf(pageSizeStr).intValue();
			if (!ITMUtils.isValidInt(pageSize, 1, 0x7fffffff))
			{
				ret = ITMErrors.ITM_ERROR_INVALID_PARAM_VALUE.code();
				logger.error((new StringBuilder()).append("parse | Error: parse, msg=page_size至少为1, errcode=").append(ret).toString());
				break MISSING_BLOCK_LABEL_2177;
			}
		}
		String ruleNearSpanStr = (String)keyValues.get(ITMParams.ITM_PARAM_RULE_NEAR_SPAN);
		if (ruleNearSpanStr != null && !ruleNearSpanStr.isEmpty())
		{
			ruleNearSpan = Integer.valueOf(ruleNearSpanStr).intValue();
			if (!ITMUtils.isValidInt(ruleNearSpan, 0, 0x7fffffff))
			{
				ret = ITMErrors.ITM_ERROR_INVALID_PARAM_VALUE.code();
				logger.error((new StringBuilder()).append("parse | Error: parse, msg=rule_near_span至少为0, errcode=").append(ret).toString());
				break MISSING_BLOCK_LABEL_2177;
			}
		}
		String ruleStr = (String)keyValues.get(ITMParams.ITM_PARAM_RULE);
		Set ruleNames = new HashSet(1024);
		if (ruleStr != null && !ruleStr.isEmpty())
		{
			String ruleStrs[] = ruleStr.split(";");
			for (int i = 0; i < ruleStrs.length; i++)
			{
				ITMRuleInfo ruleInfo = new ITMRuleInfo();
				ret = ruleInfo.rule(i, ruleStrs[i], ruleNearSpan);
				if (ret != 0)
				{
					ret = ITMErrors.ITM_ERROR_INVALID_RULE_SYNTAX.code();
					logger.warn((new StringBuilder()).append("parse | Warning: parse, msg=ruleInfo rule错误, errcode=").append(ret).toString());
					ret = 0;
					continue;
				}
				if (ruleNames.contains(ruleInfo.ruleName))
				{
					ret = ITMErrors.ITM_ERROR_INVALID_RULE_REPEATED.code();
					logger.warn((new StringBuilder()).append("parse | Warning: parse, msg=ruleInfo rule名字重复了, rule=").append(ruleStrs[i]).append("errcode=").append(ret).toString());
					ret = 0;
				} else
				{
					rules.add(ruleInfo);
					ruleNames.add(ruleInfo.ruleName);
				}
			}

		}
		String currentPageStr = (String)keyValues.get(ITMParams.ITM_PARAM_CURRENT_PAGE);
		if (currentPageStr != null && !currentPageStr.isEmpty())
		{
			currentPage = Integer.valueOf(currentPageStr).intValue();
			if (!ITMUtils.isValidInt(currentPage, 1, 0x7fffffff))
			{
				ret = ITMErrors.ITM_ERROR_INVALID_PARAM_VALUE.code();
				logger.error((new StringBuilder()).append("parse | Error: parse, msg=current_page至少为1, errcode=").append(ret).toString());
				break MISSING_BLOCK_LABEL_2177;
			}
		}
		id_field = (String)keyValues.get(ITMParams.ITM_PARAM_ID_FIELD);
		mining_field = (String)keyValues.get(ITMParams.ITM_PARAM_MINING_FIELD);
		String hotViewTopNStr = (String)keyValues.get(ITMParams.ITM_PARAM_HOT_VIEW_TOP_N);
		if (hotViewTopNStr != null && !hotViewTopNStr.isEmpty())
		{
			hotViewTopN = Integer.valueOf(hotViewTopNStr).intValue();
			if (!ITMUtils.isValidInt(hotViewTopN, 1, 0x7fffffff))
			{
				ret = ITMErrors.ITM_ERROR_INVALID_PARAM_VALUE.code();
				logger.error((new StringBuilder()).append("parse | Error: parse, msg=hot_view_top_n至少为1, errcode=").append(ret).toString());
				break MISSING_BLOCK_LABEL_2177;
			}
		}
		String hotViewClusterIndexStr = (String)keyValues.get(ITMParams.ITM_PARAM_HOT_VIEW_CLUSTER_INDEX);
		if (hotViewClusterIndexStr != null && !hotViewClusterIndexStr.isEmpty())
		{
			hotViewClusterIndex = Integer.valueOf(hotViewClusterIndexStr).intValue();
			if (!ITMUtils.isValidInt(hotViewClusterIndex, -1, hotViewTopN - 1))
			{
				ret = ITMErrors.ITM_ERROR_INVALID_PARAM_VALUE.code();
				logger.error((new StringBuilder()).append("parse | Error: parse, msg=hot_view_mode至多为").append(hotViewTopN - 1).append(", errcode=").append(ret).toString());
				break MISSING_BLOCK_LABEL_2177;
			}
		}
		String sampleRateStr = (String)keyValues.get(ITMParams.ITM_PARAM_SAMPLE_RATE);
		if (sampleRateStr != null && !sampleRateStr.isEmpty())
		{
			sampleRate = Integer.valueOf(sampleRateStr).intValue();
			if (!ITMUtils.isValidInt(sampleRate, 1, 0x7fffffff))
			{
				ret = ITMErrors.ITM_ERROR_INVALID_PARAM_VALUE.code();
				logger.error((new StringBuilder()).append("parse | Error: parse, msg=sample_rate至少为1, errcode=").append(ret).toString());
				break MISSING_BLOCK_LABEL_2177;
			}
		}
		String hotViewWordTopNStr = (String)keyValues.get(ITMParams.ITM_PARAM_HOT_VIEW_WORD_TOP_N);
		if (hotViewWordTopNStr != null && !hotViewWordTopNStr.isEmpty())
		{
			hotViewWordTopN = Integer.valueOf(hotViewWordTopNStr).intValue();
			if (!ITMUtils.isValidInt(hotViewWordTopN, 1, 0x7fffffff))
			{
				ret = ITMErrors.ITM_ERROR_INVALID_PARAM_VALUE.code();
				logger.error((new StringBuilder()).append("parse | Error: parse, msg=hot_view_word_top_n至少为1, errcode=").append(ret).toString());
				break MISSING_BLOCK_LABEL_2177;
			}
		}
		String hotViewModeStr = (String)keyValues.get(ITMParams.ITM_PARAM_HOT_VIEW_MODE);
		if (hotViewModeStr != null && !hotViewModeStr.isEmpty())
		{
			hotViewMode = Integer.valueOf(hotViewModeStr).intValue();
			if (!ITMUtils.isValidInt(hotViewMode, 0, 1))
			{
				ret = ITMErrors.ITM_ERROR_INVALID_PARAM_VALUE.code();
				logger.error((new StringBuilder()).append("parse | Error: parse, msg=hot_view_mode至多为1, errcode=").append(ret).toString());
				break MISSING_BLOCK_LABEL_2177;
			}
		}
		String hotViewFeatureWordsTopNStr = (String)keyValues.get(ITMParams.ITM_PARAM_HOT_VIEW_FEATURE_WORDS_TOP_N);
		if (hotViewFeatureWordsTopNStr != null && !hotViewFeatureWordsTopNStr.isEmpty())
		{
			hotViewFeatureWordsTopN = Integer.valueOf(hotViewFeatureWordsTopNStr).intValue();
			if (!ITMUtils.isValidInt(hotViewFeatureWordsTopN, 1, 0x7fffffff))
			{
				ret = ITMErrors.ITM_ERROR_INVALID_PARAM_VALUE.code();
				logger.error((new StringBuilder()).append("parse | Error: parse, msg=hot_view_feature_words_top_n至少为1, errcode=").append(ret).toString());
				break MISSING_BLOCK_LABEL_2177;
			}
		}
		String tmps[];
		word_association_word = (String)keyValues.get(ITMParams.ITM_PARAM_WORD_ASSOCIATION_WORD);
		if (ITMUtils.isValidStr(word_association_word))
			word_association_word = word_association_word.toLowerCase();
		String levelTopNStr = (String)keyValues.get(ITMParams.ITM_PARAM_WORD_ASSOCIATION_LEVEL_TOP_N);
		if (levelTopNStr == null || levelTopNStr.isEmpty())
			break MISSING_BLOCK_LABEL_1907;
		tmps = levelTopNStr.split(":|;");
		if (tmps.length <= 1)
		{
			ret = ITMErrors.ITM_ERROR_INVALID_PARAM_VALUE.code();
			break MISSING_BLOCK_LABEL_2177;
		}
		int levelNum;
		levelNum = Integer.valueOf(tmps[0].trim()).intValue();
		if (tmps.length != levelNum + 1)
		{
			ret = ITMErrors.ITM_ERROR_INVALID_PARAM_VALUE.code();
			break MISSING_BLOCK_LABEL_2177;
		}
		wordTopNList = new int[levelNum];
		for (int i = 1; i < tmps.length; i++)
			wordTopNList[i - 1] = Integer.valueOf(tmps[i].trim()).intValue();

		break MISSING_BLOCK_LABEL_1922;
		wordTopNList = new int[1];
		wordTopNList[0] = 10;
		String tradeTopNStr = (String)keyValues.get(ITMParams.ITM_PARAM_TRADE_TOP_N);
		if (tradeTopNStr != null && !tradeTopNStr.isEmpty())
		{
			tradeTopN = Integer.valueOf(tradeTopNStr).intValue();
			if (!ITMUtils.isValidInt(tradeTopN, 1, 0x7fffffff))
			{
				ret = ITMErrors.ITM_ERROR_INVALID_PARAM_VALUE.code();
				logger.error((new StringBuilder()).append("parse | Error: parse, msg=trade_top_n至少为1, errcode=").append(ret).toString());
				break MISSING_BLOCK_LABEL_2177;
			}
		}
		tradeResultType = (String)keyValues.get(ITMParams.ITM_PARAM_TRADE_RESULT_TYPE);
		String assistTopNStr = (String)keyValues.get("assist_top_n");
		if (assistTopNStr != null && !assistTopNStr.isEmpty())
		{
			assistTopN = Integer.valueOf(assistTopNStr).intValue();
			if (!ITMUtils.isValidInt(assistTopN, 1, 0x7fffffff))
			{
				ret = ITMErrors.ITM_ERROR_INVALID_PARAM_VALUE.code();
				logger.error((new StringBuilder()).append("parse | Error: parse, msg=assist_top_n至少为1, errcode=").append(ret).toString());
				break MISSING_BLOCK_LABEL_2177;
			}
		}
		try
		{
			assistText = (String)keyValues.get("assist_text");
		}
		catch (NumberFormatException e)
		{
			ret = ITMErrors.ITM_ERROR_INVALID_PARAM_VALUE.code();
			logger.error((new StringBuilder()).append("parse | Error: NumberFormatException, msg=").append(e.getMessage()).append(", errcode=").append(ret).toString());
		}
		return ret;
	}

}
