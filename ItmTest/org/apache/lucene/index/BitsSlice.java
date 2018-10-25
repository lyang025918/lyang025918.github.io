// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BitsSlice.java

package org.apache.lucene.index;

import org.apache.lucene.util.Bits;

// Referenced classes of package org.apache.lucene.index:
//			ReaderSlice

final class BitsSlice
	implements Bits
{

	private final Bits parent;
	private final int start;
	private final int length;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/BitsSlice.desiredAssertionStatus();

	public BitsSlice(Bits parent, ReaderSlice slice)
	{
		this.parent = parent;
		start = slice.start;
		length = slice.length;
		if (!$assertionsDisabled && length < 0)
			throw new AssertionError((new StringBuilder()).append("length=").append(length).toString());
		else
			return;
	}

	public boolean get(int doc)
	{
		if (doc >= length)
			throw new RuntimeException((new StringBuilder()).append("doc ").append(doc).append(" is out of bounds 0 .. ").append(length - 1).toString());
		if (!$assertionsDisabled && doc >= length)
			throw new AssertionError((new StringBuilder()).append("doc=").append(doc).append(" length=").append(length).toString());
		else
			return parent.get(doc + start);
	}

	public int length()
	{
		return length;
	}

}
