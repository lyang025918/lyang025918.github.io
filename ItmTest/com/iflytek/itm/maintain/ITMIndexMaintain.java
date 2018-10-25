// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ITMIndexMaintain.java

package com.iflytek.itm.maintain;

import com.iflytek.itm.api.ITMErrors;
import com.iflytek.itm.util.ITMParamParser;
import com.iflytek.itm.util.ITMUtils;
import mylib.file.DelDir;
import org.apache.log4j.Logger;

public class ITMIndexMaintain
{

	private static final Logger logger = Logger.getLogger(com/iflytek/itm/maintain/ITMIndexMaintain);
	private ITMParamParser itmParamParser;

	public ITMIndexMaintain()
	{
		itmParamParser = new ITMParamParser();
	}

	public int maintain(String indexPath, String action, String params)
	{
		int ret = 0;
		logger.info("maintain | enter.");
		itmParamParser.clear();
		ret = itmParamParser.parse(params);
		ret = checkParams(ret);
		if (ret != 0)
		{
			logger.error((new StringBuilder()).append("maintain | param error, params=").append(params).append(", errcode=").append(ret).toString());
		} else
		{
			ret = action(indexPath, action);
			if (ret != 0)
				logger.error((new StringBuilder()).append("maintain | maintain action error, action=").append(action).append(", params=").append(params).append(", errcode=").append(ret).toString());
		}
		itmParamParser = null;
		logger.info("maintain | leave.");
		return ret;
	}

	private int action(String indexPath, String action)
	{
		int ret = 0;
		String subFullPath = ITMUtils.getSubIndexFullPath(indexPath, itmParamParser.subIndexDir, false);
		if (action.equals("delete"))
		{
			ret = doDelete(subFullPath);
		} else
		{
			ret = ITMErrors.ITM_ERROR_INVALID_MAINTAIN_ACTION.code();
			logger.error((new StringBuilder()).append("maintain | Error: action, msg=不支持的索引维护动作, action=").append(action).append(", errcode=").append(ret).toString());
		}
		return ret;
	}

	private int doDelete(String subFullPath)
	{
		int ret = 0;
		boolean beret = DelDir.doDel(subFullPath);
		if (beret)
		{
			ret = 0;
			logger.info((new StringBuilder()).append("maintain | do delete success, sub_dir=").append(subFullPath).toString());
		} else
		{
			ret = ITMErrors.ITM_ERROR_MAINTAIN_DELETE_FAIL.code();
			logger.error((new StringBuilder()).append("maintain | do delete failed, sub_dir=").append(subFullPath).toString());
		}
		return ret;
	}

	private int checkParams(int preRet)
	{
		if (preRet != 0)
		{
			logger.error((new StringBuilder()).append("maintain | Error: checkParams, msg=参数格式错误, errcode=").append(preRet).toString());
			return preRet;
		}
		int ret = 0;
		if (itmParamParser.subIndexDir == null || itmParamParser.subIndexDir.isEmpty())
		{
			ret = ITMErrors.ITM_ERROR_INVALID_PARAM.code();
			logger.error((new StringBuilder()).append("maintain | Error: checkParams, msg=没有指定subIndexDir, errcode=").append(ret).toString());
		}
		return ret;
	}

}
