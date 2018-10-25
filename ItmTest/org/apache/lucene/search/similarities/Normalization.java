// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Normalization.java

package org.apache.lucene.search.similarities;

import org.apache.lucene.search.Explanation;

// Referenced classes of package org.apache.lucene.search.similarities:
//			BasicStats

public abstract class Normalization
{
	public static final class NoNormalization extends Normalization
	{

		public final float tfn(BasicStats stats, float tf, float len)
		{
			return tf;
		}

		public final Explanation explain(BasicStats stats, float tf, float len)
		{
			return new Explanation(1.0F, "no normalization");
		}

		public String toString()
		{
			return "";
		}

		public NoNormalization()
		{
		}
	}


	public Normalization()
	{
	}

	public abstract float tfn(BasicStats basicstats, float f, float f1);

	public Explanation explain(BasicStats stats, float tf, float len)
	{
		Explanation result = new Explanation();
		result.setDescription((new StringBuilder()).append(getClass().getSimpleName()).append(", computed from: ").toString());
		result.setValue(tfn(stats, tf, len));
		result.addDetail(new Explanation(tf, "tf"));
		result.addDetail(new Explanation(stats.getAvgFieldLength(), "avgFieldLength"));
		result.addDetail(new Explanation(len, "len"));
		return result;
	}

	public abstract String toString();
}
