// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SBuildImpl.java

package com.iflytek.itm.svc.server;

import com.iflytek.itm.api.ITMBuilder;
import com.iflytek.itm.svc.svccom.gen.*;
import java.util.List;

public class SBuildImpl
	implements ITMBuilder
{

	private SvcBuildCallbackPrx builder;

	public SBuildImpl(SvcBuildCallbackPrx builder)
	{
		this.builder = null;
		this.builder = builder;
	}

	public com.iflytek.itm.api.ITMBuilder.ITMDocument read()
	{
		SvcDocumentHolder svcDocumentHolder = new SvcDocumentHolder();
		int bret = builder.read(svcDocumentHolder);
		if (bret == -1)
			return null;
		com.iflytek.itm.api.ITMBuilder.ITMDocument doc = new com.iflytek.itm.api.ITMBuilder.ITMDocument();
		List svcFields = svcDocumentHolder.value.fields;
		for (int i = 0; i < svcFields.size(); i++)
		{
			SvcField svcField = (SvcField)svcFields.get(i);
			com.iflytek.itm.api.ITMBuilder.ITMField field = new com.iflytek.itm.api.ITMBuilder.ITMField(svcField.name, svcField.type, svcField.value, svcField.analyzer, svcField.isPrimaryKey);
			doc.add(field);
		}

		return doc;
	}

	public void event(String id, int evt, String msg)
	{
		builder.event(id, evt, msg);
	}
}
