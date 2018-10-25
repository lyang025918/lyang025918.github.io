// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MultiPhraseQueryNodeBuilder.java

package org.apache.lucene.queryparser.flexible.standard.builders;

import java.util.*;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.core.builders.QueryTreeBuilder;
import org.apache.lucene.queryparser.flexible.core.nodes.FieldQueryNode;
import org.apache.lucene.queryparser.flexible.core.nodes.QueryNode;
import org.apache.lucene.queryparser.flexible.standard.nodes.MultiPhraseQueryNode;
import org.apache.lucene.search.*;

// Referenced classes of package org.apache.lucene.queryparser.flexible.standard.builders:
//			StandardQueryBuilder

public class MultiPhraseQueryNodeBuilder
	implements StandardQueryBuilder
{

	public MultiPhraseQueryNodeBuilder()
	{
	}

	public MultiPhraseQuery build(QueryNode queryNode)
		throws QueryNodeException
	{
		MultiPhraseQueryNode phraseNode = (MultiPhraseQueryNode)queryNode;
		MultiPhraseQuery phraseQuery = new MultiPhraseQuery();
		List children = phraseNode.getChildren();
		if (children != null)
		{
			TreeMap positionTermMap = new TreeMap();
			TermQuery termQuery;
			List termList;
			for (Iterator i$ = children.iterator(); i$.hasNext(); termList.add(termQuery.getTerm()))
			{
				QueryNode child = (QueryNode)i$.next();
				FieldQueryNode termNode = (FieldQueryNode)child;
				termQuery = (TermQuery)termNode.getTag(QueryTreeBuilder.QUERY_TREE_BUILDER_TAGID);
				termList = (List)positionTermMap.get(Integer.valueOf(termNode.getPositionIncrement()));
				if (termList == null)
				{
					termList = new LinkedList();
					positionTermMap.put(Integer.valueOf(termNode.getPositionIncrement()), termList);
				}
			}

			int positionIncrement;
			List termList;
			for (Iterator i$ = positionTermMap.keySet().iterator(); i$.hasNext(); phraseQuery.add((Term[])termList.toArray(new Term[termList.size()]), positionIncrement))
			{
				positionIncrement = ((Integer)i$.next()).intValue();
				termList = (List)positionTermMap.get(Integer.valueOf(positionIncrement));
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
