// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SItmSvc.java

package com.iflytek.itm.svc.server;

import Ice.*;
import com.iflytek.itm.api.*;
import com.iflytek.itm.svc.svccom.ItmSvcI;
import com.iflytek.itm.svc.svccom.gen.*;
import org.apache.log4j.Logger;

// Referenced classes of package com.iflytek.itm.svc.server:
//			SBuildImpl, SSearchImpl, SItmSvcFactory

public class SItmSvc extends ItmSvcI
{

	private ObjectAdapter _adapter;
	private Communicator _communicator;
	private static final Logger logger = Logger.getLogger(com/iflytek/itm/svc/server/SItmSvc);

	public SItmSvc(ObjectAdapter adapter, Communicator ic)
	{
		_adapter = null;
		_communicator = null;
		_adapter = adapter;
		_communicator = ic;
	}

	public int build(String indexPath, String params, SvcBuildCallbackPrx builder, Current __current)
	{
		info(__current);
		SBuildImpl sBuild = new SBuildImpl(builder);
		ITM inst = ITMFactory.create();
		int ret = 0;
		ret = inst.build(root(indexPath), params, sBuild);
		sBuild = null;
		inst = null;
		return ret;
	}

	public SvcSearchResultPrx search(String indexPath, String querySyntax, String params, IntHolder errcodeHolder, Current __current)
	{
		info(__current);
		ITM inst = ITMFactory.create();
		ITMSearchContext searchContext = new ITMSearchContext();
		int ret = inst.search(root(indexPath), querySyntax, params, searchContext);
		errcodeHolder.value = ret;
		SSearchImpl result = new SSearchImpl();
		result.setResultPtr(searchContext.itmSearchResult);
		Ice.ObjectPrx objectPrx = _adapter.addWithUUID(result);
		SvcSearchResultPrx svcPrx = SvcSearchResultPrxHelper.uncheckedCast(objectPrx);
		inst = null;
		searchContext = null;
		result = null;
		return svcPrx;
	}

	public int mining(String indexPath, String type, String params, StringHolder buffer, Current __current)
	{
		info(__current);
		ITM inst = ITMFactory.create();
		StringBuffer result = new StringBuffer();
		int ret = inst.mining(root(indexPath), type, params, result);
		buffer.value = result.toString();
		inst = null;
		result = null;
		return ret;
	}

	public int maintain(String indexPath, String action, String params, Current __current)
	{
		info(__current);
		ITM inst = ITMFactory.create();
		int ret = inst.maintain(root(indexPath), action, params);
		inst = null;
		return ret;
	}

	private void info(Current __current)
	{
		StringBuffer buffer = new StringBuffer(1024);
		buffer.append(__current.con._toString()).append(", operation=").append(__current.operation).append(", timeout=").append(__current.con.timeout()).append(", type=").append(__current.con.type());
		logger.info(buffer.toString());
		buffer = null;
	}

	private String root(String indexPath)
	{
		if (indexPath == null || indexPath.isEmpty())
			return SItmSvcFactory.idxRootDir;
		else
			return indexPath;
	}

}
