// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PrecedenceQueryNodeProcessorPipeline.java

package org.apache.lucene.queryparser.flexible.precedence.processors;

import org.apache.lucene.queryparser.flexible.core.config.QueryConfigHandler;
import org.apache.lucene.queryparser.flexible.standard.processors.BooleanQuery2ModifierNodeProcessor;
import org.apache.lucene.queryparser.flexible.standard.processors.StandardQueryNodeProcessorPipeline;

// Referenced classes of package org.apache.lucene.queryparser.flexible.precedence.processors:
//			BooleanModifiersQueryNodeProcessor

public class PrecedenceQueryNodeProcessorPipeline extends StandardQueryNodeProcessorPipeline
{

	public PrecedenceQueryNodeProcessorPipeline(QueryConfigHandler queryConfig)
	{
		super(queryConfig);
		for (int i = 0; i < size(); i++)
			if (get(i).getClass().equals(org/apache/lucene/queryparser/flexible/standard/processors/BooleanQuery2ModifierNodeProcessor))
				remove(i--);

		add(new BooleanModifiersQueryNodeProcessor());
	}
}
