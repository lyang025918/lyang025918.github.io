// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BasicModel.java

package org.apache.lucene.search.similarities;

import org.apache.lucene.search.Explanation;

// Referenced classes of package org.apache.lucene.search.similarities:
//			BasicStats

public abstract class BasicModel
{

	public BasicModel()
	{
	}

	public abstract float score(BasicStats basicstats, float f);

	public Explanation explain(BasicStats stats, float tfn)
	{
		Explanation result = new Explanation();
		result.setDescription((new StringBuilder()).append(getClass().getSimpleName()).append(", computed from: ").toString());
		result.setValue(score(stats, tfn));
		result.addDetail(new Explanation(tfn, "tfn"));
		result.addDetail(new Explanation(stats.getNumberOfDocuments(), "numberOfDocuments"));
		result.addDetail(new Explanation(stats.getTotalTermFreq(), "totalTermFreq"));
		return result;
	}

	public abstract String toString();
}
