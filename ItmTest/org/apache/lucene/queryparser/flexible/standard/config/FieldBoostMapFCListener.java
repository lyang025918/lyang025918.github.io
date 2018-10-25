// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FieldBoostMapFCListener.java

package org.apache.lucene.queryparser.flexible.standard.config;

import java.util.Map;
import org.apache.lucene.queryparser.flexible.core.config.*;

// Referenced classes of package org.apache.lucene.queryparser.flexible.standard.config:
//			StandardQueryConfigHandler

public class FieldBoostMapFCListener
	implements FieldConfigListener
{

	private QueryConfigHandler config;

	public FieldBoostMapFCListener(QueryConfigHandler config)
	{
		this.config = null;
		this.config = config;
	}

	public void buildFieldConfig(FieldConfig fieldConfig)
	{
		Map fieldBoostMap = (Map)config.get(StandardQueryConfigHandler.ConfigurationKeys.FIELD_BOOST_MAP);
		if (fieldBoostMap != null)
		{
			Float boost = (Float)fieldBoostMap.get(fieldConfig.getField());
			if (boost != null)
				fieldConfig.set(StandardQueryConfigHandler.ConfigurationKeys.BOOST, boost);
		}
	}
}
