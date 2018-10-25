// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ITMIndexSearcher.java

package com.iflytek.itm.search;

import com.iflytek.itm.api.*;
import com.iflytek.itm.build.ITMUserData;
import com.iflytek.itm.util.ITMParamParser;
import com.iflytek.itm.util.ITMUtils;
import java.io.IOException;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.*;
import org.apache.lucene.util.Version;

// Referenced classes of package com.iflytek.itm.search:
//			ITMSearchResultImpl, MyQueryParser, SearchCollector, MiningCollector, 
//			IntGroupCollector, MStringGroupCollector, StringGroupCollector, LongGroupCollector, 
//			ITMSearchWrapper, GroupCollector

public class ITMIndexSearcher
{

	private ITMParamParser itmParamParser;
	private ITMSearchResultImpl searchResult;
	private static final Logger logger = Logger.getLogger(com/iflytek/itm/search/ITMIndexSearcher);

	public ITMIndexSearcher()
	{
		itmParamParser = new ITMParamParser();
		searchResult = new ITMSearchResultImpl();
	}

	public int search(String indexPath, String queryString, String params, ITMSearchContext itmSearchContext)
	{
		int ret = 0;
		logger.info("search | enter.");
		try
		{
			itmParamParser.clear();
			itmParamParser.funcFlag = com.iflytek.itm.util.ITMParamParser.ITM_FUNCTION_FLAG.ITM_SEARCH;
			ret = itmParamParser.parse(params);
			ret = checkParams(ret);
			if (ret != 0)
			{
				logger.error((new StringBuilder()).append("search | Error: searchParamParser, params=").append(params).append(", errcode=").append(ret).toString());
			} else
			{
				logger.info("open index enter");
				ret = searchResult.searchWrapper.open(indexPath, itmParamParser);
				logger.info("open index leave");
				if (ret != 0)
				{
					logger.error((new StringBuilder()).append("search | Error: searchWrapper.open, errcode=").append(ret).toString());
				} else
				{
					String finalQuery = ITMUtils.getFinalQueryString(itmParamParser);
					if (!queryString.isEmpty())
						finalQuery = (new StringBuilder()).append("+(").append(queryString).append(")+(").append(finalQuery).append(")").toString();
					searchResult.topDocs = new TopDocs(0, null, 0.0F);
					searchResult.topGroups = new ITMSearchResultImpl.ITMTopGroup();
					logger.info("searchLucene enter");
					ret = searchLucene(searchResult.searchWrapper, finalQuery, "", itmParamParser, searchResult.topDocs, searchResult.topGroups);
					itmSearchContext.itmSearchResult = searchResult;
					logger.info("searchLucene leave");
				}
			}
		}
		catch (Exception e)
		{
			ret = ITMErrors.ITM_ERROR_INDEX_IO_EXCEPTION.code();
			logger.error((new StringBuilder()).append("search | errorMsg=").append(e.getMessage()).append(", errcode=").append(ret).toString());
		}
		itmParamParser = null;
		logger.info("search | leave.");
		return ret;
	}

	public static int searchLucene(ITMSearchWrapper searcherWrapper, String queryString, String filterString, ITMParamParser itmParamParser, TopDocs topDocs, ITMSearchResultImpl.ITMTopGroup topGroups)
	{
		int ret = 0;
		try
		{
			MyQueryParser myQueryParser = new MyQueryParser(Version.LUCENE_40, "content", searcherWrapper.itmUserData);
			Query query = myQueryParser.parse(queryString);
			Query finalQuery = null;
			if (filterString == null || filterString.equals(""))
			{
				finalQuery = query;
			} else
			{
				Query filterQuery = myQueryParser.parse(filterString);
				QueryWrapperFilter filter = new QueryWrapperFilter(filterQuery);
				finalQuery = new FilteredQuery(query, filter);
			}
			String strtmp = finalQuery.toString();
			strtmp = strtmp.length() <= 200 ? strtmp : (new StringBuilder()).append(strtmp.substring(0, 199)).append(" ... ").toString();
			logger.info((new StringBuilder()).append("finalQuery=").append(strtmp).toString());
			if (itmParamParser.funcFlag == com.iflytek.itm.util.ITMParamParser.ITM_FUNCTION_FLAG.ITM_SEARCH)
			{
				if (ITMUtils.isValidStr(itmParamParser.groupField))
					ret = groupQuery(searcherWrapper, finalQuery, itmParamParser, topGroups);
				else
					ret = pageQuery(searcherWrapper, finalQuery, itmParamParser, topDocs);
			} else
			if (itmParamParser.funcFlag == com.iflytek.itm.util.ITMParamParser.ITM_FUNCTION_FLAG.ITM_TRADE || itmParamParser.funcFlag == com.iflytek.itm.util.ITMParamParser.ITM_FUNCTION_FLAG.ITM_WORD_ASSOCIATION || itmParamParser.funcFlag == com.iflytek.itm.util.ITMParamParser.ITM_FUNCTION_FLAG.ITM_HOT_VIEW)
				ret = miningQuery(searcherWrapper, finalQuery, itmParamParser, topDocs);
			else
			if (itmParamParser.funcFlag == com.iflytek.itm.util.ITMParamParser.ITM_FUNCTION_FLAG.ITM_RULE)
				ret = pageQuery(searcherWrapper, finalQuery, itmParamParser, topDocs);
		}
		catch (ParseException e)
		{
			logger.error((new StringBuilder()).append("searchLucene | Error: 检索字符串的语法有误! query=").append(queryString).append(", ").append(e.getMessage()).toString());
			ret = ITMErrors.ITM_ERROR_INVALID_QUERY_SYNTAX.code();
		}
		catch (IOException e)
		{
			logger.error((new StringBuilder()).append("searchLucene | Error: 检索时io有误! query=").append(queryString).append(", ").append(e.getMessage()).toString());
			ret = ITMErrors.ITM_ERROR_INDEX_IO_EXCEPTION.code();
		}
		catch (Exception e)
		{
			logger.error((new StringBuilder()).append("searchLucene | Error: 检索时有误! query=").append(queryString).append(", ").append(e.getMessage()).toString());
			ret = ITMErrors.ITM_FAIL.code();
		}
		return ret;
	}

	public static int searchQuery(ITMSearchWrapper searcherWrapper, Query query, ITMParamParser itmParamParser, TopDocs topDocs)
		throws IOException
	{
		int ret = 0;
		SearchCollector collector = new SearchCollector(itmParamParser.sortField, itmParamParser.isSortReverse, searcherWrapper);
		if (!collector.isValid())
		{
			ret = ITMErrors.ITM_ERROR_INVALID_PARAM.code();
			logger.error((new StringBuilder()).append("searchQuery | error, 参数错误, sortField=").append(collector.name).append(", type=").append(collector.type).append(", errcode=").append(ret).toString());
			return ret;
		} else
		{
			logger.info((new StringBuilder()).append("sortField=").append(collector.name).append(", isSortReverse=").append(collector.isSortReverse).toString());
			searcherWrapper.searcher.search(query, collector);
			logger.info((new StringBuilder()).append("pageSize=").append(itmParamParser.pageSize).append(", currentPage=").append(itmParamParser.currentPage).toString());
			collector.getTopDocs(itmParamParser.pageSize, itmParamParser.currentPage, topDocs);
			return ret;
		}
	}

	public static int pageQuery(ITMSearchWrapper searcherWrapper, Query query, ITMParamParser itmParamParser, TopDocs topDocs)
		throws IOException
	{
		int ret = 0;
		String sortFieldName = "id";
		org.apache.lucene.search.SortField.Type type = org.apache.lucene.search.SortField.Type.INT;
		if (ITMUtils.isValidStr(itmParamParser.sortField))
		{
			sortFieldName = itmParamParser.sortField;
			com.iflytek.itm.api.ITMBuilder.ITMField field = searcherWrapper.itmUserData.getField(sortFieldName);
			if (field == null)
			{
				ret = ITMErrors.ITM_ERROR_SORT_FIELD_NOT_EXISTED.code();
				logger.error((new StringBuilder()).append("pageQuery | errorMsg=待排序的这个field的不存在, fieldName=").append(sortFieldName).toString());
				return ret;
			}
			if (field.type.equals("string"))
				type = org.apache.lucene.search.SortField.Type.STRING;
			else
			if (field.type.equals("int"))
				type = org.apache.lucene.search.SortField.Type.INT;
			else
			if (field.type.equals("long"))
				type = org.apache.lucene.search.SortField.Type.LONG;
			else
			if (field.type.equals("float"))
				type = org.apache.lucene.search.SortField.Type.FLOAT;
			else
			if (field.type.equals("double"))
			{
				type = org.apache.lucene.search.SortField.Type.DOUBLE;
			} else
			{
				ret = ITMErrors.ITM_ERROR_INVALID_PARAM_VALUE.code();
				logger.error((new StringBuilder()).append("pageQuery | 参数值错误, errorMsg=待排序的这个field的类型不支持, fieldName=").append(sortFieldName).append(", type=").append(field.type).toString());
				return ret;
			}
		}
		TopDocs tmpTopDocs = null;
		if (!ITMUtils.isValidStr(itmParamParser.sortField))
		{
			logger.info("sortField=score");
			tmpTopDocs = searcherWrapper.searcher.search(query, itmParamParser.pageSize * itmParamParser.currentPage);
		} else
		{
			logger.info((new StringBuilder()).append("sortField=").append(sortFieldName).append(", isSortReverse=").append(itmParamParser.isSortReverse).toString());
			tmpTopDocs = searcherWrapper.searcher.search(query, itmParamParser.pageSize * itmParamParser.currentPage, new Sort(new SortField(sortFieldName, type, itmParamParser.isSortReverse)));
		}
		int begin = itmParamParser.pageSize * (itmParamParser.currentPage - 1);
		int end = Math.min(begin + itmParamParser.pageSize, tmpTopDocs.scoreDocs.length);
		if (begin > end)
		{
			topDocs.scoreDocs = new ScoreDoc[0];
		} else
		{
			topDocs.scoreDocs = new ScoreDoc[end - begin];
			for (int i = begin; i < end; i++)
				topDocs.scoreDocs[i - begin] = tmpTopDocs.scoreDocs[i];

		}
		topDocs.totalHits = tmpTopDocs.totalHits;
		topDocs.setMaxScore(tmpTopDocs.getMaxScore());
		return ret;
	}

	public static int miningQuery(ITMSearchWrapper searcherWrapper, Query query, ITMParamParser itmParamParser, TopDocs topDocs)
		throws IOException
	{
		int ret = 0;
		MiningCollector collector = new MiningCollector();
		searcherWrapper.searcher.search(query, collector);
		collector.getSampleDocs(itmParamParser.sampleRate, topDocs);
		return ret;
	}

	public static int groupQuery(ITMSearchWrapper searcherWrapper, Query query, ITMParamParser itmParamParser, ITMSearchResultImpl.ITMTopGroup topGroups)
		throws IOException
	{
		int ret = 0;
		GroupCollector collector = null;
		String type = MyQueryParser.getFieldType(searcherWrapper.itmUserData, itmParamParser.groupField);
		if (type.equals("int"))
			collector = new IntGroupCollector(itmParamParser.groupField, itmParamParser.groups);
		else
		if (type.equals("string"))
		{
			if (itmParamParser.isMultiValue)
				collector = new MStringGroupCollector(itmParamParser.groupField, itmParamParser.groups);
			else
				collector = new StringGroupCollector(itmParamParser.groupField, itmParamParser.groups);
		} else
		if (type.equals("long"))
		{
			collector = new LongGroupCollector(itmParamParser.groupField, itmParamParser.groups);
		} else
		{
			ret = ITMErrors.ITM_ERROR_GROUP_FIELD_NOT_SUPPORTED.code();
			logger.error((new StringBuilder()).append("search | groupQuery error, field type to group not support, group=").append(itmParamParser.groupField).append(", type=").append(type).append(", errcode=").append(ret).toString());
			return ret;
		}
		searcherWrapper.searcher.search(query, collector);
		logger.info((new StringBuilder()).append("pageSize=").append(itmParamParser.pageSize).append(", currentPage=").append(itmParamParser.currentPage).toString());
		collector.getTopGroups(itmParamParser.pageSize * itmParamParser.currentPage, topGroups);
		return ret;
	}

	private int checkParams(int preRet)
	{
		if (preRet != 0)
		{
			logger.error((new StringBuilder()).append("search | Error: checkParams, msg=参数格式错误, errcode=").append(preRet).toString());
			return preRet;
		}
		int ret = 0;
		if (itmParamParser.subIndexDirList.length == 0)
		{
			ret = ITMErrors.ITM_ERROR_NO_SUB_INDEX_DIR.code();
			logger.error((new StringBuilder()).append("search | Error: checkParams, msg=没有指定索引子目录, errcode=").append(ret).toString());
		}
		if (itmParamParser.rules.size() > 0 && !ITMUtils.isValidStr(itmParamParser.mining_field))
		{
			ret = ITMErrors.ITM_ERROR_INVALID_PARAM.code();
			logger.error((new StringBuilder()).append("rule | Error: checkParams, msg=指定了rule，但没有指定mining_field, errcode=").append(ret).toString());
		}
		return ret;
	}

}
