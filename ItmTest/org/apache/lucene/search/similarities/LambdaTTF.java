// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LambdaTTF.java

package org.apache.lucene.search.similarities;

import org.apache.lucene.search.Explanation;

// Referenced classes of package org.apache.lucene.search.similarities:
//			Lambda, BasicStats

public class LambdaTTF extends Lambda
{

	public LambdaTTF()
	{
	}

	public final float lambda(BasicStats stats)
	{
		return ((float)stats.getTotalTermFreq() + 1.0F) / ((float)stats.getNumberOfDocuments() + 1.0F);
	}

	public final Explanation explain(BasicStats stats)
	{
		Explanation result = new Explanation();
		result.setDescription((new StringBuilder()).append(getClass().getSimpleName()).append(", computed from: ").toString());
		result.setValue(lambda(stats));
		result.addDetail(new Explanation(stats.getTotalTermFreq(), "totalTermFreq"));
		result.addDetail(new Explanation(stats.getNumberOfDocuments(), "numberOfDocuments"));
		return result;
	}

	public String toString()
	{
		return "L";
	}
}
