// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FieldDateResolutionFCListener.java

package org.apache.lucene.queryparser.flexible.standard.config;

import java.util.Map;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.queryparser.flexible.core.config.*;

// Referenced classes of package org.apache.lucene.queryparser.flexible.standard.config:
//			StandardQueryConfigHandler

public class FieldDateResolutionFCListener
	implements FieldConfigListener
{

	private QueryConfigHandler config;

	public FieldDateResolutionFCListener(QueryConfigHandler config)
	{
		this.config = null;
		this.config = config;
	}

	public void buildFieldConfig(FieldConfig fieldConfig)
	{
		org.apache.lucene.document.DateTools.Resolution dateRes = null;
		Map dateResMap = (Map)config.get(StandardQueryConfigHandler.ConfigurationKeys.FIELD_DATE_RESOLUTION_MAP);
		if (dateResMap != null)
			dateRes = (org.apache.lucene.document.DateTools.Resolution)dateResMap.get(fieldConfig.getField());
		if (dateRes == null)
			dateRes = (org.apache.lucene.document.DateTools.Resolution)config.get(StandardQueryConfigHandler.ConfigurationKeys.DATE_RESOLUTION);
		if (dateRes != null)
			fieldConfig.set(StandardQueryConfigHandler.ConfigurationKeys.DATE_RESOLUTION, dateRes);
	}
}
