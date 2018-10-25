// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   QueryConfigHandler.java

package org.apache.lucene.queryparser.flexible.core.config;

import java.util.Iterator;
import java.util.LinkedList;
import org.apache.lucene.queryparser.flexible.core.util.StringUtils;

// Referenced classes of package org.apache.lucene.queryparser.flexible.core.config:
//			AbstractQueryConfig, FieldConfig, FieldConfigListener

public abstract class QueryConfigHandler extends AbstractQueryConfig
{

	private final LinkedList listeners = new LinkedList();

	public QueryConfigHandler()
	{
	}

	public FieldConfig getFieldConfig(String fieldName)
	{
		FieldConfig fieldConfig = new FieldConfig(StringUtils.toString(fieldName));
		FieldConfigListener listener;
		for (Iterator i$ = listeners.iterator(); i$.hasNext(); listener.buildFieldConfig(fieldConfig))
			listener = (FieldConfigListener)i$.next();

		return fieldConfig;
	}

	public void addFieldConfigListener(FieldConfigListener listener)
	{
		listeners.add(listener);
	}
}
