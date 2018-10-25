// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SrndBooleanQuery.java

package org.apache.lucene.queryparser.surround.query;

import java.util.List;
import org.apache.lucene.search.*;

class SrndBooleanQuery
{

	SrndBooleanQuery()
	{
	}

	public static void addQueriesToBoolean(BooleanQuery bq, List queries, org.apache.lucene.search.BooleanClause.Occur occur)
	{
		for (int i = 0; i < queries.size(); i++)
			bq.add((Query)queries.get(i), occur);

	}

	public static Query makeBooleanQuery(List queries, org.apache.lucene.search.BooleanClause.Occur occur)
	{
		if (queries.size() <= 1)
		{
			throw new AssertionError((new StringBuilder()).append("Too few subqueries: ").append(queries.size()).toString());
		} else
		{
			BooleanQuery bq = new BooleanQuery();
			addQueriesToBoolean(bq, queries.subList(0, queries.size()), occur);
			return bq;
		}
	}
}
