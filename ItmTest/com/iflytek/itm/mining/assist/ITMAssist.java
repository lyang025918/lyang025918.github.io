// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ITMAssist.java

package com.iflytek.itm.mining.assist;

import com.iflytek.itm.api.ITMErrors;
import com.iflytek.itm.search.ITMSearchWrapper;
import com.iflytek.itm.util.ITMParamParser;
import java.io.IOException;
import org.apache.log4j.Logger;

public class ITMAssist
{

	private static final Logger logger = Logger.getLogger(com/iflytek/itm/mining/assist/ITMAssist);
	private ITMParamParser itmParamParser;
	private ITMSearchWrapper searchWrapper;

	public ITMAssist()
	{
		itmParamParser = new ITMParamParser();
		searchWrapper = new ITMSearchWrapper();
	}

	public int assist(String indexPath, String params, StringBuffer buffer)
	{
		int ret = 0;
		logger.info("assist | enter.");
		try
		{
			itmParamParser.clear();
			itmParamParser.funcFlag = com.iflytek.itm.util.ITMParamParser.ITM_FUNCTION_FLAG.ITM_ASSIST;
			ret = itmParamParser.parse(params);
			ret = checkParams(ret);
			if (ret != 0)
			{
				logger.error((new StringBuilder()).append("assist | param error, params=").append(params).append(", errcode=").append(ret).toString());
			} else
			{
				logger.info("open index enter");
				ret = searchWrapper.open(indexPath, itmParamParser);
				logger.info("open index leave");
				if (ret != 0)
				{
					logger.error((new StringBuilder()).append("assist | Error: searchWrapper.open, errcode=").append(ret).toString());
				} else
				{
					ret = fetchResult(buffer);
					if (ret != 0)
					{
						logger.error((new StringBuilder()).append("assist | fetchResult error, errcode=").append(ret).toString());
					} else
					{
						ret = searchWrapper.close();
						if (ret != 0)
							logger.error((new StringBuilder()).append("assist | Error: searchWrapper.close, errcode=").append(ret).toString());
					}
				}
			}
		}
		catch (IOException e)
		{
			ret = ITMErrors.ITM_ERROR_INDEX_IO_EXCEPTION.code();
			logger.error((new StringBuilder()).append("assist | IOException, msg=").append(e.getMessage()).append(", errcode=").append(ret).toString());
		}
		logger.info("assist | leave.");
		return ret;
	}

	private int fetchResult(StringBuffer buffer)
		throws IOException
	{
		return 0;
	}

	private int checkParams(int preRet)
	{
		if (preRet != 0)
		{
			logger.error((new StringBuilder()).append("assist | Error: checkParams, msg=参数格式错误, errcode=").append(preRet).toString());
			return preRet;
		}
		int ret = 0;
		if (itmParamParser.assistText == null || itmParamParser.assistText.isEmpty())
		{
			ret = ITMErrors.ITM_ERROR_INVALID_PARAM.code();
			logger.error((new StringBuilder()).append("trade | Error: checkParams, msg=没有指定assist_text, errcode=").append(ret).toString());
		}
		return ret;
	}

}
