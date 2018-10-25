// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   StandardQueryNodeProcessorPipeline.java

package org.apache.lucene.queryparser.flexible.standard.processors;

import org.apache.lucene.queryparser.flexible.core.config.QueryConfigHandler;
import org.apache.lucene.queryparser.flexible.core.processors.*;

// Referenced classes of package org.apache.lucene.queryparser.flexible.standard.processors:
//			WildcardQueryNodeProcessor, MultiFieldQueryNodeProcessor, FuzzyQueryNodeProcessor, MatchAllDocsQueryNodeProcessor, 
//			OpenRangeQueryNodeProcessor, NumericQueryNodeProcessor, NumericRangeQueryNodeProcessor, LowercaseExpandedTermsQueryNodeProcessor, 
//			TermRangeQueryNodeProcessor, AllowLeadingWildcardProcessor, AnalyzerQueryNodeProcessor, PhraseSlopQueryNodeProcessor, 
//			BooleanQuery2ModifierNodeProcessor, RemoveEmptyNonLeafQueryNodeProcessor, BooleanSingleChildOptimizationQueryNodeProcessor, DefaultPhraseSlopQueryNodeProcessor, 
//			BoostQueryNodeProcessor, MultiTermRewriteMethodProcessor

public class StandardQueryNodeProcessorPipeline extends QueryNodeProcessorPipeline
{

	public StandardQueryNodeProcessorPipeline(QueryConfigHandler queryConfig)
	{
		super(queryConfig);
		add(new WildcardQueryNodeProcessor());
		add(new MultiFieldQueryNodeProcessor());
		add(new FuzzyQueryNodeProcessor());
		add(new MatchAllDocsQueryNodeProcessor());
		add(new OpenRangeQueryNodeProcessor());
		add(new NumericQueryNodeProcessor());
		add(new NumericRangeQueryNodeProcessor());
		add(new LowercaseExpandedTermsQueryNodeProcessor());
		add(new TermRangeQueryNodeProcessor());
		add(new AllowLeadingWildcardProcessor());
		add(new AnalyzerQueryNodeProcessor());
		add(new PhraseSlopQueryNodeProcessor());
		add(new BooleanQuery2ModifierNodeProcessor());
		add(new NoChildOptimizationQueryNodeProcessor());
		add(new RemoveDeletedQueryNodesProcessor());
		add(new RemoveEmptyNonLeafQueryNodeProcessor());
		add(new BooleanSingleChildOptimizationQueryNodeProcessor());
		add(new DefaultPhraseSlopQueryNodeProcessor());
		add(new BoostQueryNodeProcessor());
		add(new MultiTermRewriteMethodProcessor());
	}
}
