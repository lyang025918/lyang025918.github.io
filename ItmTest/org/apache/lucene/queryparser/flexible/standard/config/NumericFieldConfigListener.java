// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NumericFieldConfigListener.java

package org.apache.lucene.queryparser.flexible.standard.config;

import java.util.Map;
import org.apache.lucene.queryparser.flexible.core.config.*;

// Referenced classes of package org.apache.lucene.queryparser.flexible.standard.config:
//			NumericConfig, StandardQueryConfigHandler

public class NumericFieldConfigListener
	implements FieldConfigListener
{

	private final QueryConfigHandler config;

	public NumericFieldConfigListener(QueryConfigHandler config)
	{
		if (config == null)
		{
			throw new IllegalArgumentException("config cannot be null!");
		} else
		{
			this.config = config;
			return;
		}
	}

	public void buildFieldConfig(FieldConfig fieldConfig)
	{
		Map numericConfigMap = (Map)config.get(StandardQueryConfigHandler.ConfigurationKeys.NUMERIC_CONFIG_MAP);
		if (numericConfigMap != null)
		{
			NumericConfig numericConfig = (NumericConfig)numericConfigMap.get(fieldConfig.getField());
			if (numericConfig != null)
				fieldConfig.set(StandardQueryConfigHandler.ConfigurationKeys.NUMERIC_CONFIG, numericConfig);
		}
	}
}
