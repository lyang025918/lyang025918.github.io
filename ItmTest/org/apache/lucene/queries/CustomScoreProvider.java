// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CustomScoreProvider.java

package org.apache.lucene.queries;

import java.io.IOException;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.search.Explanation;

public class CustomScoreProvider
{

	protected final AtomicReaderContext context;

	public CustomScoreProvider(AtomicReaderContext context)
	{
		this.context = context;
	}

	public float customScore(int doc, float subQueryScore, float valSrcScores[])
		throws IOException
	{
		if (valSrcScores.length == 1)
			return customScore(doc, subQueryScore, valSrcScores[0]);
		if (valSrcScores.length == 0)
			return customScore(doc, subQueryScore, 1.0F);
		float score = subQueryScore;
		float arr$[] = valSrcScores;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			float valSrcScore = arr$[i$];
			score *= valSrcScore;
		}

		return score;
	}

	public float customScore(int doc, float subQueryScore, float valSrcScore)
		throws IOException
	{
		return subQueryScore * valSrcScore;
	}

	public Explanation customExplain(int doc, Explanation subQueryExpl, Explanation valSrcExpls[])
		throws IOException
	{
		if (valSrcExpls.length == 1)
			return customExplain(doc, subQueryExpl, valSrcExpls[0]);
		if (valSrcExpls.length == 0)
			return subQueryExpl;
		float valSrcScore = 1.0F;
		Explanation arr$[] = valSrcExpls;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			Explanation valSrcExpl = arr$[i$];
			valSrcScore *= valSrcExpl.getValue();
		}

		Explanation exp = new Explanation(valSrcScore * subQueryExpl.getValue(), "custom score: product of:");
		exp.addDetail(subQueryExpl);
		Explanation arr$[] = valSrcExpls;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			Explanation valSrcExpl = arr$[i$];
			exp.addDetail(valSrcExpl);
		}

		return exp;
	}

	public Explanation customExplain(int doc, Explanation subQueryExpl, Explanation valSrcExpl)
		throws IOException
	{
		float valSrcScore = 1.0F;
		if (valSrcExpl != null)
			valSrcScore *= valSrcExpl.getValue();
		Explanation exp = new Explanation(valSrcScore * subQueryExpl.getValue(), "custom score: product of:");
		exp.addDetail(subQueryExpl);
		exp.addDetail(valSrcExpl);
		return exp;
	}
}
