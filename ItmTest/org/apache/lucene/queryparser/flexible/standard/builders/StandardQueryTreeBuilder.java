// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   StandardQueryTreeBuilder.java

package org.apache.lucene.queryparser.flexible.standard.builders;

import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.builders.QueryTreeBuilder;
import org.apache.lucene.queryparser.flexible.core.nodes.*;
import org.apache.lucene.queryparser.flexible.standard.nodes.*;
import org.apache.lucene.search.Query;

// Referenced classes of package org.apache.lucene.queryparser.flexible.standard.builders:
//			GroupQueryNodeBuilder, FieldQueryNodeBuilder, BooleanQueryNodeBuilder, FuzzyQueryNodeBuilder, 
//			DummyQueryNodeBuilder, NumericRangeQueryNodeBuilder, BoostQueryNodeBuilder, ModifierQueryNodeBuilder, 
//			WildcardQueryNodeBuilder, PhraseQueryNodeBuilder, MatchNoDocsQueryNodeBuilder, PrefixWildcardQueryNodeBuilder, 
//			TermRangeQueryNodeBuilder, RegexpQueryNodeBuilder, SlopQueryNodeBuilder, StandardBooleanQueryNodeBuilder, 
//			MultiPhraseQueryNodeBuilder, MatchAllDocsQueryNodeBuilder, StandardQueryBuilder

public class StandardQueryTreeBuilder extends QueryTreeBuilder
	implements StandardQueryBuilder
{

	public StandardQueryTreeBuilder()
	{
		setBuilder(org/apache/lucene/queryparser/flexible/core/nodes/GroupQueryNode, new GroupQueryNodeBuilder());
		setBuilder(org/apache/lucene/queryparser/flexible/core/nodes/FieldQueryNode, new FieldQueryNodeBuilder());
		setBuilder(org/apache/lucene/queryparser/flexible/core/nodes/BooleanQueryNode, new BooleanQueryNodeBuilder());
		setBuilder(org/apache/lucene/queryparser/flexible/core/nodes/FuzzyQueryNode, new FuzzyQueryNodeBuilder());
		setBuilder(org/apache/lucene/queryparser/flexible/standard/nodes/NumericQueryNode, new DummyQueryNodeBuilder());
		setBuilder(org/apache/lucene/queryparser/flexible/standard/nodes/NumericRangeQueryNode, new NumericRangeQueryNodeBuilder());
		setBuilder(org/apache/lucene/queryparser/flexible/core/nodes/BoostQueryNode, new BoostQueryNodeBuilder());
		setBuilder(org/apache/lucene/queryparser/flexible/core/nodes/ModifierQueryNode, new ModifierQueryNodeBuilder());
		setBuilder(org/apache/lucene/queryparser/flexible/standard/nodes/WildcardQueryNode, new WildcardQueryNodeBuilder());
		setBuilder(org/apache/lucene/queryparser/flexible/core/nodes/TokenizedPhraseQueryNode, new PhraseQueryNodeBuilder());
		setBuilder(org/apache/lucene/queryparser/flexible/core/nodes/MatchNoDocsQueryNode, new MatchNoDocsQueryNodeBuilder());
		setBuilder(org/apache/lucene/queryparser/flexible/standard/nodes/PrefixWildcardQueryNode, new PrefixWildcardQueryNodeBuilder());
		setBuilder(org/apache/lucene/queryparser/flexible/standard/nodes/TermRangeQueryNode, new TermRangeQueryNodeBuilder());
		setBuilder(org/apache/lucene/queryparser/flexible/standard/nodes/RegexpQueryNode, new RegexpQueryNodeBuilder());
		setBuilder(org/apache/lucene/queryparser/flexible/core/nodes/SlopQueryNode, new SlopQueryNodeBuilder());
		setBuilder(org/apache/lucene/queryparser/flexible/standard/nodes/StandardBooleanQueryNode, new StandardBooleanQueryNodeBuilder());
		setBuilder(org/apache/lucene/queryparser/flexible/standard/nodes/MultiPhraseQueryNode, new MultiPhraseQueryNodeBuilder());
		setBuilder(org/apache/lucene/queryparser/flexible/core/nodes/MatchAllDocsQueryNode, new MatchAllDocsQueryNodeBuilder());
	}

	public Query build(QueryNode queryNode)
		throws QueryNodeException
	{
		return (Query)super.build(queryNode);
	}

	public volatile Object build(QueryNode x0)
		throws QueryNodeException
	{
		return build(x0);
	}
}
