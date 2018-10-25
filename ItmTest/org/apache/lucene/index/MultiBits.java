// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MultiBits.java

package org.apache.lucene.index;

import org.apache.lucene.util.Bits;

// Referenced classes of package org.apache.lucene.index:
//			ReaderUtil, ReaderSlice

final class MultiBits
	implements Bits
{
	public static final class SubResult
	{

		public boolean matches;
		public Bits result;

		public SubResult()
		{
		}
	}


	private final Bits subs[];
	private final int starts[];
	private final boolean defaultValue;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/MultiBits.desiredAssertionStatus();

	public MultiBits(Bits subs[], int starts[], boolean defaultValue)
	{
		if (!$assertionsDisabled && starts.length != 1 + subs.length)
		{
			throw new AssertionError();
		} else
		{
			this.subs = subs;
			this.starts = starts;
			this.defaultValue = defaultValue;
			return;
		}
	}

	private boolean checkLength(int reader, int doc)
	{
		int length = starts[1 + reader] - starts[reader];
		if (!$assertionsDisabled && doc - starts[reader] >= length)
			throw new AssertionError((new StringBuilder()).append("doc=").append(doc).append(" reader=").append(reader).append(" starts[reader]=").append(starts[reader]).append(" length=").append(length).toString());
		else
			return true;
	}

	public boolean get(int doc)
	{
		int reader = ReaderUtil.subIndex(doc, starts);
		if (!$assertionsDisabled && reader == -1)
			throw new AssertionError();
		Bits bits = subs[reader];
		if (bits == null)
			return defaultValue;
		if (!$assertionsDisabled && !checkLength(reader, doc))
			throw new AssertionError();
		else
			return bits.get(doc - starts[reader]);
	}

	public String toString()
	{
		StringBuilder b = new StringBuilder();
		b.append((new StringBuilder()).append(subs.length).append(" subs: ").toString());
		for (int i = 0; i < subs.length; i++)
		{
			if (i != 0)
				b.append("; ");
			if (subs[i] == null)
				b.append((new StringBuilder()).append("s=").append(starts[i]).append(" l=null").toString());
			else
				b.append((new StringBuilder()).append("s=").append(starts[i]).append(" l=").append(subs[i].length()).append(" b=").append(subs[i]).toString());
		}

		b.append((new StringBuilder()).append(" end=").append(starts[subs.length]).toString());
		return b.toString();
	}

	public SubResult getMatchingSub(ReaderSlice slice)
	{
		int reader = ReaderUtil.subIndex(slice.start, starts);
		if (!$assertionsDisabled && reader == -1)
			throw new AssertionError();
		if (!$assertionsDisabled && reader >= subs.length)
			throw new AssertionError((new StringBuilder()).append("slice=").append(slice).append(" starts[-1]=").append(starts[starts.length - 1]).toString());
		SubResult subResult = new SubResult();
		if (starts[reader] == slice.start && starts[1 + reader] == slice.start + slice.length)
		{
			subResult.matches = true;
			subResult.result = subs[reader];
		} else
		{
			subResult.matches = false;
		}
		return subResult;
	}

	public int length()
	{
		return starts[starts.length - 1];
	}

}
