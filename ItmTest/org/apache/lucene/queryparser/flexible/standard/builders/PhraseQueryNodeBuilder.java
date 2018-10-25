// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PhraseQueryNodeBuilder.java

package org.apache.lucene.queryparser.flexible.standard.builders;

import java.util.Iterator;
import java.util.List;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.builders.QueryTreeBuilder;
import org.apache.lucene.queryparser.flexible.core.nodes.*;
import org.apache.lucene.search.*;

// Referenced classes of package org.apache.lucene.queryparser.flexible.standard.builders:
//			StandardQueryBuilder

public class PhraseQueryNodeBuilder
	implements StandardQueryBuilder
{

	public PhraseQueryNodeBuilder()
	{
	}

	public PhraseQuery build(QueryNode queryNode)
		throws QueryNodeException
	{
		TokenizedPhraseQueryNode phraseNode = (TokenizedPhraseQueryNode)queryNode;
		PhraseQuery phraseQuery = new PhraseQuery();
		List children = phraseNode.getChildren();
		if (children != null)
		{
			TermQuery termQuery;
			FieldQueryNode termNode;
			for (Iterator i$ = children.iterator(); i$.hasNext(); phraseQuery.add(termQuery.getTerm(), termNode.getPositionIncrement()))
			{
				QueryNode child = (QueryNode)i$.next();
				termQuery = (TermQuery)child.getTag(QueryTreeBuilder.QUERY_TREE_BUILDER_TAGID);
				termNode = (FieldQueryNode)child;
			}

		}
		return phraseQuery;
	}

	public volatile Query build(QueryNode x0)
		throws QueryNodeException
	{
		return build(x0);
	}

	public volatile Object build(QueryNode x0)
		throws QueryNodeException
	{
		return build(x0);
	}
}
