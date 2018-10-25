// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SSearchImpl.java

package com.iflytek.itm.svc.server;

import Ice.Current;
import Ice.Holder;
import com.iflytek.itm.api.*;
import com.iflytek.itm.svc.svccom.SvcSearchResultI;
import com.iflytek.itm.svc.svccom.gen.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class SSearchImpl extends SvcSearchResultI
{

	private com.iflytek.itm.api.ITMSearchContext.ITMSearchResult result;
	private static final Logger logger = Logger.getLogger(com/iflytek/itm/svc/server/SSearchImpl);

	public SSearchImpl()
	{
		result = null;
	}

	public void setResultPtr(com.iflytek.itm.api.ITMSearchContext.ITMSearchResult result)
	{
		this.result = result;
	}

	public int getTotalHits(Current __current)
	{
		if (result == null)
			return 0;
		else
			return result.getTotalHits();
	}

	public int docs(Holder svcdocs, Current __current)
	{
		int ret = 0;
		if (result == null)
			return 0;
		com.iflytek.itm.api.ITMBuilder.ITMDocument docs[] = result.docs();
		if (docs == null)
			return 0;
		try
		{
			svcdocs.value = new ArrayList();
			for (int i = 0; i < docs.length; i++)
			{
				com.iflytek.itm.api.ITMBuilder.ITMDocument doc = docs[i];
				SvcDocument svcDocument = new SvcDocument();
				svcDocument.fields = new ArrayList();
				List fields = doc.getFields();
				for (int j = 0; j < fields.size(); j++)
				{
					com.iflytek.itm.api.ITMBuilder.ITMField field = (com.iflytek.itm.api.ITMBuilder.ITMField)fields.get(j);
					SvcField svcField = new SvcField(field.name, field.type, field.value, field.analyzer, field.isPrimaryKey);
					if (field.isPrimaryKey)
						svcDocument.primaryIndex = j;
					svcDocument.fields.add(svcField);
				}

				((List)svcdocs.value).add(svcDocument);
			}

		}
		catch (Exception e)
		{
			ITMErrors error = ITMErrors.ITM_ERROR_NETWORK_ERROR;
			ret = error.code();
			logger.error((new StringBuilder()).append("SSearchImpl | invoke docs error, errmsg=").append(error.msg()).append(", excepmsg=").append(e.getMessage()).toString());
		}
		return ret;
	}

	public int getGroupTotalHits(Current __current)
	{
		if (result == null)
			return 0;
		else
			return result.getGroupTotalHits();
	}

	public int groups(Holder svcgroups, Current __current)
	{
		int ret = 0;
		if (result == null)
			return 0;
		com.iflytek.itm.api.ITMSearchContext.ITMGroup groups[];
		groups = result.groups();
		if (groups != null)
			break MISSING_BLOCK_LABEL_34;
		svcgroups.value = null;
		return 0;
		try
		{
			svcgroups.value = new ArrayList();
			for (int i = 0; i < groups.length; i++)
			{
				com.iflytek.itm.api.ITMSearchContext.ITMGroup group = groups[i];
				SvcGroup svcGroup = new SvcGroup(group.value, group.docCount);
				((List)svcgroups.value).add(svcGroup);
			}

		}
		catch (Exception e)
		{
			ITMErrors error = ITMErrors.ITM_ERROR_NETWORK_ERROR;
			ret = error.code();
			logger.error((new StringBuilder()).append("SSearchImpl | invoke groups error, errmsg=").append(error.msg()).append(", excepmsg=").append(e.getMessage()).toString());
		}
		return ret;
	}

	public int close(Current __current)
	{
		int ret = 0;
		try
		{
			ret = result.close();
			result = null;
		}
		catch (Exception e)
		{
			ITMErrors error = ITMErrors.ITM_ERROR_NETWORK_ERROR;
			ret = error.code();
			logger.error((new StringBuilder()).append("SSearchImpl | invoke close error, errmsg=").append(error.msg()).append(", excepmsg=").append(e.getMessage()).toString());
		}
		return ret;
	}

}
