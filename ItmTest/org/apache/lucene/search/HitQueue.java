// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   HitQueue.java

package org.apache.lucene.search;

import org.apache.lucene.util.PriorityQueue;

// Referenced classes of package org.apache.lucene.search:
//			ScoreDoc

final class HitQueue extends PriorityQueue
{

	HitQueue(int size, boolean prePopulate)
	{
		super(size, prePopulate);
	}

	protected ScoreDoc getSentinelObject()
	{
		return new ScoreDoc(0x7fffffff, (-1.0F / 0.0F));
	}

	protected final boolean lessThan(ScoreDoc hitA, ScoreDoc hitB)
	{
		if (hitA.score == hitB.score)
			return hitA.doc > hitB.doc;
		else
			return hitA.score < hitB.score;
	}

	protected volatile Object getSentinelObject()
	{
		return getSentinelObject();
	}

	protected volatile boolean lessThan(Object x0, Object x1)
	{
		return lessThan((ScoreDoc)x0, (ScoreDoc)x1);
	}
}
