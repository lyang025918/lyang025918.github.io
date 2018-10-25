// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BasicModelIn.java

package org.apache.lucene.search.similarities;

import org.apache.lucene.search.Explanation;

// Referenced classes of package org.apache.lucene.search.similarities:
//			BasicModel, BasicStats, SimilarityBase

public class BasicModelIn extends BasicModel
{

	public BasicModelIn()
	{
	}

	public final float score(BasicStats stats, float tfn)
	{
		long N = stats.getNumberOfDocuments();
		long n = stats.getDocFreq();
		return tfn * (float)SimilarityBase.log2((double)(N + 1L) / ((double)n + 0.5D));
	}

	public final Explanation explain(BasicStats stats, float tfn)
	{
		Explanation result = new Explanation();
		result.setDescription((new StringBuilder()).append(getClass().getSimpleName()).append(", computed from: ").toString());
		result.setValue(score(stats, tfn));
		result.addDetail(new Explanation(tfn, "tfn"));
		result.addDetail(new Explanation(stats.getNumberOfDocuments(), "numberOfDocuments"));
		result.addDetail(new Explanation(stats.getDocFreq(), "docFreq"));
		return result;
	}

	public String toString()
	{
		return "I(n)";
	}
}
