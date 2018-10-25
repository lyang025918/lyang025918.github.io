// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AfterEffect.java

package org.apache.lucene.search.similarities;

import org.apache.lucene.search.Explanation;

// Referenced classes of package org.apache.lucene.search.similarities:
//			BasicStats

public abstract class AfterEffect
{
	public static final class NoAfterEffect extends AfterEffect
	{

		public final float score(BasicStats stats, float tfn)
		{
			return 1.0F;
		}

		public final Explanation explain(BasicStats stats, float tfn)
		{
			return new Explanation(1.0F, "no aftereffect");
		}

		public String toString()
		{
			return "";
		}

		public NoAfterEffect()
		{
		}
	}


	public AfterEffect()
	{
	}

	public abstract float score(BasicStats basicstats, float f);

	public abstract Explanation explain(BasicStats basicstats, float f);

	public abstract String toString();
}
