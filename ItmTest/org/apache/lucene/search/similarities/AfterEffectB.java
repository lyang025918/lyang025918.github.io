// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AfterEffectB.java

package org.apache.lucene.search.similarities;

import org.apache.lucene.search.Explanation;

// Referenced classes of package org.apache.lucene.search.similarities:
//			AfterEffect, BasicStats

public class AfterEffectB extends AfterEffect
{

	public AfterEffectB()
	{
	}

	public final float score(BasicStats stats, float tfn)
	{
		long F = stats.getTotalTermFreq() + 1L;
		long n = stats.getDocFreq() + 1L;
		return (float)(F + 1L) / ((float)n * (tfn + 1.0F));
	}

	public final Explanation explain(BasicStats stats, float tfn)
	{
		Explanation result = new Explanation();
		result.setDescription((new StringBuilder()).append(getClass().getSimpleName()).append(", computed from: ").toString());
		result.setValue(score(stats, tfn));
		result.addDetail(new Explanation(tfn, "tfn"));
		result.addDetail(new Explanation(stats.getTotalTermFreq(), "totalTermFreq"));
		result.addDetail(new Explanation(stats.getDocFreq(), "docFreq"));
		return result;
	}

	public String toString()
	{
		return "B";
	}
}
