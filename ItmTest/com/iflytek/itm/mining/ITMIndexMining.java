// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ITMIndexMining.java

package com.iflytek.itm.mining;

import com.iflytek.itm.api.ITMErrors;
import com.iflytek.itm.mining.assist.ITMAssist;
import org.apache.log4j.Logger;

// Referenced classes of package com.iflytek.itm.mining:
//			ITMRule, ITMWordAssociation, ITMHotView, ITMTrade, 
//			ITMMiningResource

public class ITMIndexMining
{

	private static final Logger logger = Logger.getLogger(com/iflytek/itm/mining/ITMIndexMining);

	public ITMIndexMining()
	{
	}

	public int mining(String indexPath, String type, String params, StringBuffer buffer)
	{
		int ret = 0;
		ITMMiningResource.inst();
		type = type.trim().toLowerCase();
		if (type.equals("rule"))
		{
			ITMRule itmRule = new ITMRule();
			ret = itmRule.rule(indexPath, params, buffer);
			itmRule = null;
		} else
		if (type.equals("word_association"))
		{
			ITMWordAssociation itmWordAssociation = new ITMWordAssociation();
			ret = itmWordAssociation.wordAssociation(indexPath, params, buffer);
			itmWordAssociation = null;
		} else
		if (type.equals("hot_view"))
		{
			ITMHotView itmHotView = new ITMHotView();
			ret = itmHotView.hotView(indexPath, params, buffer);
			itmHotView = null;
		} else
		if (type.equals("trade"))
		{
			ITMTrade itmTrade = new ITMTrade();
			ret = itmTrade.trade(indexPath, params, buffer);
			itmTrade = null;
		} else
		if (type.equals("assist"))
		{
			ITMAssist itmAssist = new ITMAssist();
			ret = itmAssist.assist(indexPath, params, buffer);
			itmAssist = null;
		} else
		{
			ret = ITMErrors.ITM_ERROR_MINING_TYPE_NOT_SUPPORT.code();
			logger.error((new StringBuilder()).append("mining | Error: mining, msg=��֧�ֵ�mining����, errcode=").append(ret).toString());
		}
		return ret;
	}

}
