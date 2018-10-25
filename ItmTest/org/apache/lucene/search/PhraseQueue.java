// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PhraseQueue.java

package org.apache.lucene.search;

import org.apache.lucene.util.PriorityQueue;

// Referenced classes of package org.apache.lucene.search:
//			PhrasePositions

final class PhraseQueue extends PriorityQueue
{

	PhraseQueue(int size)
	{
		super(size);
	}

	protected final boolean lessThan(PhrasePositions pp1, PhrasePositions pp2)
	{
		if (pp1.doc == pp2.doc)
		{
			if (pp1.position == pp2.position)
			{
				if (pp1.offset == pp2.offset)
					return pp1.ord < pp2.ord;
				else
					return pp1.offset < pp2.offset;
			} else
			{
				return pp1.position < pp2.position;
			}
		} else
		{
			return pp1.doc < pp2.doc;
		}
	}

	protected volatile boolean lessThan(Object x0, Object x1)
	{
		return lessThan((PhrasePositions)x0, (PhrasePositions)x1);
	}
}
