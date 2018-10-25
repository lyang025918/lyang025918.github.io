// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ITMParamCheck.java

package com.iflytek.itm.util;

import com.iflytek.itm.api.ITMErrors;
import java.util.*;
import org.apache.log4j.Logger;

// Referenced classes of package com.iflytek.itm.util:
//			ParamParser

public class ITMParamCheck
{
	static class param_info
	{

		public itm_params para_id;
		public String para_str;
		public String module;
		public String para_type;
		public String value_default;
		public String value_min;
		public String value_max;
		public String value_excludes;

		param_info(itm_params para_id, String para_str, String module, String para_type, String value_default, String value_min, String value_max, 
				String value_excludes)
		{
			this.para_id = para_id;
			this.para_str = para_str;
			this.module = module;
			this.para_type = para_type;
			this.value_default = value_default;
			this.value_min = value_min;
			this.value_max = value_max;
			this.value_excludes = value_excludes;
		}
	}

	private static final class par_type extends Enum
	{

		public static final par_type ITM_TYPE_UNKNOW;
		public static final par_type ITM_TYPE_INT;
		public static final par_type ITM_TYPE_FLOAT;
		public static final par_type ITM_TYPE_DOUBLE;
		public static final par_type ITM_TYPE_BOOLEAN;
		public String type_name;
		public int type_serial;
		private static final par_type $VALUES[];

		public static par_type[] values()
		{
			return (par_type[])$VALUES.clone();
		}

		public static par_type valueOf(String name)
		{
			return (par_type)Enum.valueOf(com/iflytek/itm/util/ITMParamCheck$par_type, name);
		}

		static 
		{
			ITM_TYPE_UNKNOW = new par_type("ITM_TYPE_UNKNOW", 0, "unknow", -1);
			ITM_TYPE_INT = new par_type("ITM_TYPE_INT", 1, "int", 1);
			ITM_TYPE_FLOAT = new par_type("ITM_TYPE_FLOAT", 2, "float", 2);
			ITM_TYPE_DOUBLE = new par_type("ITM_TYPE_DOUBLE", 3, "double", 3);
			ITM_TYPE_BOOLEAN = new par_type("ITM_TYPE_BOOLEAN", 4, "boolean", 4);
			$VALUES = (new par_type[] {
				ITM_TYPE_UNKNOW, ITM_TYPE_INT, ITM_TYPE_FLOAT, ITM_TYPE_DOUBLE, ITM_TYPE_BOOLEAN
			});
		}

		private par_type(String s, int i, String type_name, int type_serial)
		{
			super(s, i);
			this.type_name = type_name;
			this.type_serial = type_serial;
		}
	}

	private static final class itm_params extends Enum
	{

		public static final itm_params ITM_PARAM_NOT_EXIST;
		public static final itm_params SUB_INDEX_DIR;
		public static final itm_params IS_UPDATE_DOCUMENT;
		public static final itm_params IS_DELETE_DOCUMENT;
		public static final itm_params BUILD_THREAD_NUM;
		public static final itm_params MAX_BUFFERED_DOCS;
		public static final itm_params DO_MERGE;
		public static final itm_params DO_OPTIMIZE;
		public static final itm_params PAGE_SIZE;
		public static final itm_params CURRENT_PAGE;
		public static final itm_params SORT_FIELD;
		public static final itm_params IS_SORT_REVERSE;
		public static final itm_params GROUP_FIELD;
		public static final itm_params GROUPS;
		public static final itm_params GROUP_IS_MULTIVALUE;
		public static final itm_params ID_LIST;
		public static final itm_params ID_FIELD;
		public static final itm_params SUB_INDEX_DIR_LIST;
		public static final itm_params MINING_FIELD;
		public static final itm_params MINING_QUERY;
		public static final itm_params SAMPLE_RATE;
		public static final itm_params RULE;
		public static final itm_params RULE_FETCH_POS;
		public static final itm_params RULE_NEAR_SPAN;
		public static final itm_params TRADE_TOP_N;
		public static final itm_params TRADE_RESULT_TYPE;
		public static final itm_params WORD_ASSOCIATION_WORD;
		public static final itm_params WORD_ASSOCIATION_LEVEL_TOP_N;
		public static final itm_params HOT_VIEW_MODE;
		public static final itm_params HOT_VIEW_CLUSTER_INDEX;
		public static final itm_params HOT_VIEW_FEATURE_WORDS_TOP_N;
		public static final itm_params HOT_VIEW_TOP_N;
		public static final itm_params HOT_VIEW_WORD_TOP_N;
		public static final itm_params RULE_IS_CHECK_RULE;
		public static final itm_params ASSIST_TOP_N;
		public static final itm_params ASSIST_TEXT;
		public String param_name;
		public int param_serial;
		private static final itm_params $VALUES[];

		public static itm_params[] values()
		{
			return (itm_params[])$VALUES.clone();
		}

		public static itm_params valueOf(String name)
		{
			return (itm_params)Enum.valueOf(com/iflytek/itm/util/ITMParamCheck$itm_params, name);
		}

		static 
		{
			ITM_PARAM_NOT_EXIST = new itm_params("ITM_PARAM_NOT_EXIST", 0, 1200, null);
			SUB_INDEX_DIR = new itm_params("SUB_INDEX_DIR", 1, 1, "sub_index_dir");
			IS_UPDATE_DOCUMENT = new itm_params("IS_UPDATE_DOCUMENT", 2, 2, "is_update_document");
			IS_DELETE_DOCUMENT = new itm_params("IS_DELETE_DOCUMENT", 3, 3, "is_delete_document");
			BUILD_THREAD_NUM = new itm_params("BUILD_THREAD_NUM", 4, 4, "build_thread_num");
			MAX_BUFFERED_DOCS = new itm_params("MAX_BUFFERED_DOCS", 5, 5, "max_buffered_docs");
			DO_MERGE = new itm_params("DO_MERGE", 6, 6, "do_merge");
			DO_OPTIMIZE = new itm_params("DO_OPTIMIZE", 7, 7, "do_optimize");
			PAGE_SIZE = new itm_params("PAGE_SIZE", 8, 8, "page_size");
			CURRENT_PAGE = new itm_params("CURRENT_PAGE", 9, 9, "current_page");
			SORT_FIELD = new itm_params("SORT_FIELD", 10, 10, "sort_field");
			IS_SORT_REVERSE = new itm_params("IS_SORT_REVERSE", 11, 11, "is_sort_reverse");
			GROUP_FIELD = new itm_params("GROUP_FIELD", 12, 12, "group_field");
			GROUPS = new itm_params("GROUPS", 13, 13, "groups");
			GROUP_IS_MULTIVALUE = new itm_params("GROUP_IS_MULTIVALUE", 14, 14, "group_is_multivalue");
			ID_LIST = new itm_params("ID_LIST", 15, 15, "id_list");
			ID_FIELD = new itm_params("ID_FIELD", 16, 16, "id_field");
			SUB_INDEX_DIR_LIST = new itm_params("SUB_INDEX_DIR_LIST", 17, 17, "sub_index_dir_list");
			MINING_FIELD = new itm_params("MINING_FIELD", 18, 18, "mining_field");
			MINING_QUERY = new itm_params("MINING_QUERY", 19, 19, "mining_query");
			SAMPLE_RATE = new itm_params("SAMPLE_RATE", 20, 20, "sample_rate");
			RULE = new itm_params("RULE", 21, 21, "rule");
			RULE_FETCH_POS = new itm_params("RULE_FETCH_POS", 22, 22, "rule_fetch_pos");
			RULE_NEAR_SPAN = new itm_params("RULE_NEAR_SPAN", 23, 23, "rule_near_span");
			TRADE_TOP_N = new itm_params("TRADE_TOP_N", 24, 24, "trade_top_n");
			TRADE_RESULT_TYPE = new itm_params("TRADE_RESULT_TYPE", 25, 25, "trade_result_type");
			WORD_ASSOCIATION_WORD = new itm_params("WORD_ASSOCIATION_WORD", 26, 26, "word_association_word");
			WORD_ASSOCIATION_LEVEL_TOP_N = new itm_params("WORD_ASSOCIATION_LEVEL_TOP_N", 27, 27, "word_association_level_top_n");
			HOT_VIEW_MODE = new itm_params("HOT_VIEW_MODE", 28, 28, "hot_view_mode");
			HOT_VIEW_CLUSTER_INDEX = new itm_params("HOT_VIEW_CLUSTER_INDEX", 29, 29, "hot_view_cluster_index");
			HOT_VIEW_FEATURE_WORDS_TOP_N = new itm_params("HOT_VIEW_FEATURE_WORDS_TOP_N", 30, 30, "hot_view_feature_words_top_n");
			HOT_VIEW_TOP_N = new itm_params("HOT_VIEW_TOP_N", 31, 31, "hot_view_top_n");
			HOT_VIEW_WORD_TOP_N = new itm_params("HOT_VIEW_WORD_TOP_N", 32, 32, "hot_view_word_top_n");
			RULE_IS_CHECK_RULE = new itm_params("RULE_IS_CHECK_RULE", 33, 33, "is_check_rule");
			ASSIST_TOP_N = new itm_params("ASSIST_TOP_N", 34, 34, "assist_top_n");
			ASSIST_TEXT = new itm_params("ASSIST_TEXT", 35, 35, "assist_text");
			$VALUES = (new itm_params[] {
				ITM_PARAM_NOT_EXIST, SUB_INDEX_DIR, IS_UPDATE_DOCUMENT, IS_DELETE_DOCUMENT, BUILD_THREAD_NUM, MAX_BUFFERED_DOCS, DO_MERGE, DO_OPTIMIZE, PAGE_SIZE, CURRENT_PAGE, 
				SORT_FIELD, IS_SORT_REVERSE, GROUP_FIELD, GROUPS, GROUP_IS_MULTIVALUE, ID_LIST, ID_FIELD, SUB_INDEX_DIR_LIST, MINING_FIELD, MINING_QUERY, 
				SAMPLE_RATE, RULE, RULE_FETCH_POS, RULE_NEAR_SPAN, TRADE_TOP_N, TRADE_RESULT_TYPE, WORD_ASSOCIATION_WORD, WORD_ASSOCIATION_LEVEL_TOP_N, HOT_VIEW_MODE, HOT_VIEW_CLUSTER_INDEX, 
				HOT_VIEW_FEATURE_WORDS_TOP_N, HOT_VIEW_TOP_N, HOT_VIEW_WORD_TOP_N, RULE_IS_CHECK_RULE, ASSIST_TOP_N, ASSIST_TEXT
			});
		}

		private itm_params(String s, int i, int param_serial, String param_name)
		{
			super(s, i);
			this.param_serial = param_serial;
			this.param_name = param_name;
		}
	}


	private static final Logger logger = Logger.getLogger(com/iflytek/itm/util/ITMParamCheck);
	private static param_info param_info_table[];
	static final boolean $assertionsDisabled = !com/iflytek/itm/util/ITMParamCheck.desiredAssertionStatus();

	public ITMParamCheck()
	{
	}

	private itm_params get_para_id(String para_str)
	{
		for (int index = 0; index < param_info_table.length; index++)
			if (para_str.equals(param_info_table[index].para_str))
				return param_info_table[index].para_id;

		return itm_params.ITM_PARAM_NOT_EXIST;
	}

	private String get_para_type(itm_params para_id)
	{
		if (!$assertionsDisabled && (para_id.param_serial <= 0 || para_id.param_serial >= param_info_table.length))
			throw new AssertionError();
		if (!$assertionsDisabled && para_id != param_info_table[para_id.param_serial].para_id)
			throw new AssertionError();
		else
			return param_info_table[para_id.param_serial].para_type;
	}

	private int get_type_serial(String type_name)
	{
		int serial = par_type.ITM_TYPE_UNKNOW.type_serial;
		par_type types[] = par_type.values();
		int i = 0;
		do
		{
			if (i >= types.length)
				break;
			if (types[i].type_name.equals(type_name))
			{
				serial = types[i].type_serial;
				break;
			}
			i++;
		} while (true);
		return serial;
	}

	private int para_verfiy(String para_name, String para_value)
	{
		int ret = 0;
		if (null == para_name || para_name.isEmpty())
		{
			ret = ITMErrors.ITM_ERROR_PARAM_EMPTY.code();
			logger.error((new StringBuilder()).append("para_verfiy | Error: check para_name, msg=参数为空, param_value=").append(para_value).append(", errcode=").append(ret).toString());
		} else
		{
			itm_params para_id = get_para_id(para_name);
			if (para_id == itm_params.ITM_PARAM_NOT_EXIST)
			{
				ret = ITMErrors.ITM_ERROR_PARAM_NOT_EXISTED.code();
				logger.error((new StringBuilder()).append("para_verfiy | Error: get_para_id, msg=参数不存在, param_name=").append(para_name).append(", errcode=").append(ret).toString());
			} else
			if (null == para_value || para_value.isEmpty())
			{
				ret = ITMErrors.ITM_ERROR_PARAM_VALUE_EMPTY.code();
				logger.error((new StringBuilder()).append("para_verfiy | Error: msg=参数值为空, param_name=").append(para_name).append(", errcode=").append(ret).toString());
			} else
			{
				param_info pi = param_info_table[para_id.param_serial];
				switch (get_type_serial(pi.para_type))
				{
				case -1: 
				case 0: // '\0'
				default:
					break;

				case 1: // '\001'
					if (0 != (ret = checkNumber(para_value)))
					{
						ret = ITMErrors.ITM_ERROR_NUMBER_FORMAT.code();
						logger.error((new StringBuilder()).append("para_verfiy | Error: switch checkNumber, msg=数值类型格式不正确, param_name=").append(para_name).append(", param_value=").append(para_value).append(", errcode=").append(ret).toString());
						break;
					}
					int int_value = 0;
					try
					{
						int_value = Integer.valueOf(para_value).intValue();
					}
					catch (NumberFormatException e)
					{
						ret = ITMErrors.ITM_ERROR_NUMBER_RANGE.code();
						logger.error((new StringBuilder()).append("para_verfiy | Error: switch catch NumberFormatException, msg=数值超出范围, param_name=").append(para_name).append(", param_value=").append(para_value).append(", errcode=").append(ret).toString());
						break;
					}
					if (null != pi.value_min && Integer.parseInt(pi.value_min) > int_value)
					{
						ret = ITMErrors.ITM_ERROR_NUMBER_RANGE.code();
						logger.error((new StringBuilder()).append("para_verfiy | Error: switch msg=数值超出范围, param_name=").append(para_name).append(", param_value=").append(para_value).append(", errcode=").append(ret).toString());
						break;
					}
					if (null != pi.value_max && Integer.parseInt(pi.value_max) < int_value)
					{
						ret = ITMErrors.ITM_ERROR_NUMBER_RANGE.code();
						logger.error((new StringBuilder()).append("para_verfiy | Error: switch msg=数值超出范围, param_name=").append(para_name).append(", param_value=").append(para_value).append(", errcode=").append(ret).toString());
					}
					break;

				case 2: // '\002'
				case 3: // '\003'
					if (0 != (ret = checkNumber(para_value)))
					{
						ret = ITMErrors.ITM_ERROR_NUMBER_FORMAT.code();
						logger.error((new StringBuilder()).append("para_verfiy | Error: switch checkNumber, msg=数值类型格式不正确, param_name=").append(para_name).append(", param_value=").append(para_value).append(", errcode=").append(ret).toString());
						break;
					}
					double double_value = 0.0D;
					try
					{
						double_value = Double.parseDouble(para_value);
					}
					catch (NumberFormatException e)
					{
						ret = ITMErrors.ITM_ERROR_NUMBER_RANGE.code();
						logger.error((new StringBuilder()).append("para_verfiy | Error: switch catch NumberFormatException, msg=数值超出范围, param_name=").append(para_name).append(", param_value=").append(para_value).append(", errcode=").append(ret).toString());
						break;
					}
					if (null != pi.value_min && Double.parseDouble(pi.value_min) > double_value + 1.0000000000000001E-009D)
					{
						ret = ITMErrors.ITM_ERROR_NUMBER_RANGE.code();
						logger.error((new StringBuilder()).append("para_verfiy | Error: switch msg=数值超出范围, param_name=").append(para_name).append(", param_value=").append(para_value).append(", errcode=").append(ret).toString());
						break;
					}
					if (null != pi.value_max && Double.parseDouble(pi.value_max) < double_value - 1.0000000000000001E-009D)
					{
						ret = ITMErrors.ITM_ERROR_NUMBER_RANGE.code();
						logger.error((new StringBuilder()).append("para_verfiy | Error: switch, msg=数值超出范围, param_name=").append(para_name).append(", param_value=").append(para_value).append(", errcode=").append(ret).toString());
					}
					break;

				case 4: // '\004'
					if (!para_value.equals("true") && !para_value.equals("false"))
					{
						ret = ITMErrors.ITM_ERROR_BOOL_VALUE.code();
						logger.error((new StringBuilder()).append("para_verfiy | Error: switch, msg=boolean类型参数值错误, param_name=").append(para_name).append(", param_value=").append(para_value).append(", errcode=").append(ret).toString());
					}
					break;
				}
			}
		}
		return ret;
	}

	private int checkNumber(String str)
	{
		int ret = 0;
		int point = 0;
		String tmpstr = str;
		if (str.startsWith("-"))
			tmpstr = str.substring(1);
		for (int i = 0; i < tmpstr.length(); i++)
		{
			if (Character.isDigit(tmpstr.charAt(i)))
				continue;
			if (tmpstr.charAt(i) == '.' && point < 1)
			{
				point++;
				continue;
			}
			ret = ITMErrors.ITM_ERROR_NUMBER_FORMAT.code();
			break;
		}

		return ret;
	}

	public int checkParams(String params)
	{
		int ret = 0;
		String value = null;
		Map keyValues = new HashMap();
		ret = ParamParser.parse(params, keyValues);
		if (0 == ret)
		{
			Iterator iterator = keyValues.keySet().iterator();
			do
			{
				if (!iterator.hasNext())
					break;
				String key = (String)iterator.next();
				value = (String)keyValues.get(key);
				ret = para_verfiy(key, value);
			} while (0 == ret);
		}
		return ret;
	}

	static 
	{
		param_info_table = (new param_info[] {
			new param_info(itm_params.ITM_PARAM_NOT_EXIST, null, "", "", null, null, null, null), new param_info(itm_params.SUB_INDEX_DIR, "sub_index_dir", "build", "String", null, null, null, null), new param_info(itm_params.IS_UPDATE_DOCUMENT, "is_update_document", "build", "boolean", "false", null, null, null), new param_info(itm_params.IS_DELETE_DOCUMENT, "is_delete_document", "build", "boolean", "false", null, null, null), new param_info(itm_params.BUILD_THREAD_NUM, "build_thread_num", "build", "int", "1", "1", "2147483647", null), new param_info(itm_params.MAX_BUFFERED_DOCS, "max_buffered_docs", "build", "int", "100000", "1", "2147483647", null), new param_info(itm_params.DO_MERGE, "do_merge", "build", "boolean", "true", null, null, null), new param_info(itm_params.DO_OPTIMIZE, "do_optimize", "build", "boolean", "true", null, null, null), new param_info(itm_params.PAGE_SIZE, "page_size", "search", "int", "10", "1", "2147483647", null), new param_info(itm_params.CURRENT_PAGE, "current_page", "search", "int", "1", "1", "2147483647", null), 
			new param_info(itm_params.SORT_FIELD, "sort_field", "search", "String", "id", null, null, null), new param_info(itm_params.IS_SORT_REVERSE, "is_sort_reverse", "search", "boolean", "false", null, null, null), new param_info(itm_params.GROUP_FIELD, "group_field", "search", "String", null, null, null, null), new param_info(itm_params.GROUPS, "groups", "search", "String[]", null, null, null, null), new param_info(itm_params.GROUP_IS_MULTIVALUE, "group_is_multivalue", "search", "boolean", "false", null, null, null), new param_info(itm_params.ID_LIST, "id_list", "mining", "String[]", null, null, null, null), new param_info(itm_params.ID_FIELD, "id_field", "mining", "String", null, null, null, null), new param_info(itm_params.SUB_INDEX_DIR_LIST, "sub_index_dir_list", "mining", "String[]", null, null, null, null), new param_info(itm_params.MINING_FIELD, "mining_field", "mining", "String", null, null, null, null), new param_info(itm_params.MINING_QUERY, "mining_query", "mining", "String", null, null, null, null), 
			new param_info(itm_params.SAMPLE_RATE, "sample_rate", "mining", "int", "500", "1", "2147483647", null), new param_info(itm_params.RULE, "rule", "mining", "List", null, null, null, null), new param_info(itm_params.RULE_FETCH_POS, "rule_fetch_pos", "mining", "boolean", "false", null, null, null), new param_info(itm_params.RULE_NEAR_SPAN, "rule_near_span", "mining", "int", "10", "1", "2147483647", null), new param_info(itm_params.TRADE_TOP_N, "trade_top_n", "mining", "int", "10", "1", "2147483647", null), new param_info(itm_params.TRADE_RESULT_TYPE, "trade_result_type", "mining", "String", "score", null, null, null), new param_info(itm_params.WORD_ASSOCIATION_WORD, "word_association_word", "mining", "String", null, null, null, null), new param_info(itm_params.WORD_ASSOCIATION_LEVEL_TOP_N, "word_association_level_top_n", "mining", "String", null, null, null, null), new param_info(itm_params.HOT_VIEW_MODE, "hot_view_mode", "mining", "int", "1", "1", "2147483647", null), new param_info(itm_params.HOT_VIEW_CLUSTER_INDEX, "hot_view_cluster_index", "mining", "int", "-1", "-1", "2147483647", null), 
			new param_info(itm_params.HOT_VIEW_FEATURE_WORDS_TOP_N, "hot_view_feature_words_top_n", "mining", "int[]", null, null, null, null), new param_info(itm_params.HOT_VIEW_TOP_N, "hot_view_top_n", "mining", "int", "5", "1", "2147483647", null), new param_info(itm_params.HOT_VIEW_WORD_TOP_N, "hot_view_word_top_n", "mining", "int", "10", "1", "2147483647", null), new param_info(itm_params.RULE_IS_CHECK_RULE, "is_check_rule", "mining", "boolean", "false", null, null, null), new param_info(itm_params.ASSIST_TOP_N, "assist_top_n", "mining", "int", "1", "1", "2147483647", null), new param_info(itm_params.ASSIST_TEXT, "assist_text", "mining", "String", null, null, null, null)
		});
	}
}
