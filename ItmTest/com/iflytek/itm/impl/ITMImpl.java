// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ITMImpl.java

package com.iflytek.itm.impl;

import com.iflytek.itm.api.*;
import com.iflytek.itm.build.ITMIndexWriter;
import com.iflytek.itm.maintain.ITMIndexMaintain;
import com.iflytek.itm.mining.ITMIndexMining;
import com.iflytek.itm.search.ITMIndexSearcher;
import com.iflytek.itm.util.ITMParamCheck;

public class ITMImpl
	implements ITM
{
	class OneDocBuilder
		implements ITMBuilder
	{

		private com.iflytek.itm.api.ITMBuilder.ITMDocument itmDocument;
		private int errcode;
		private boolean over;
		final ITMImpl this$0;

		public void event(String id, int evt, String msg)
		{
			errcode = evt;
		}

		public com.iflytek.itm.api.ITMBuilder.ITMDocument read()
		{
			if (over)
			{
				return null;
			} else
			{
				over = true;
				return itmDocument;
			}
		}

		public int getErrcode()
		{
			return errcode;
		}

		OneDocBuilder(com.iflytek.itm.api.ITMBuilder.ITMDocument doc)
		{
			this.this$0 = ITMImpl.this;
			super();
			itmDocument = null;
			errcode = 0;
			over = false;
			itmDocument = doc;
			errcode = 0;
			over = false;
		}
	}


	public ITMImpl()
	{
	}

	public int build(String indexPath, String params, ITMBuilder itmBuilder)
	{
		int ret = 0;
		ret = check(params);
		if (0 == ret)
		{
			ITMIndexWriter itmIndexWriter = ITMIndexWriter.inst();
			ret = itmIndexWriter.build(indexPath, params, itmBuilder);
		}
		return ret;
	}

	public int build(String indexPath, String params, com.iflytek.itm.api.ITMBuilder.ITMDocument itmDoc)
	{
		int ret = 0;
		ret = check(params);
		if (0 == ret)
		{
			ITMIndexWriter itmIndexWriter = ITMIndexWriter.inst();
			OneDocBuilder builder = new OneDocBuilder(itmDoc);
			ret = itmIndexWriter.build(indexPath, params, builder);
			if (ret == 0)
				ret = builder.getErrcode();
			builder = null;
		}
		return ret;
	}

	public int search(String indexPath, String query, String params, ITMSearchContext itmSearchContext)
	{
		int ret = 0;
		ret = check(params);
		if (0 == ret)
		{
			ITMIndexSearcher itmIndexSearcher = new ITMIndexSearcher();
			ret = itmIndexSearcher.search(indexPath, query, params, itmSearchContext);
		}
		return ret;
	}

	public int mining(String indexPath, String type, String params, StringBuffer buffer)
	{
		int ret = 0;
		ret = check(params);
		if (0 == ret)
		{
			ITMIndexMining itmIndexMining = new ITMIndexMining();
			ret = itmIndexMining.mining(indexPath, type, params, buffer);
			itmIndexMining = null;
		}
		return ret;
	}

	public int maintain(String indexPath, String action, String params)
	{
		int ret = 0;
		ret = check(params);
		if (0 == ret)
		{
			ITMIndexMaintain itmIndexMaintain = new ITMIndexMaintain();
			ret = itmIndexMaintain.maintain(indexPath, action, params);
			itmIndexMaintain = null;
		}
		return ret;
	}

	private int check(String params)
	{
		int ret = 0;
		ITMParamCheck itm_check = new ITMParamCheck();
		ret = itm_check.checkParams(params);
		itm_check = null;
		return ret;
	}
}
