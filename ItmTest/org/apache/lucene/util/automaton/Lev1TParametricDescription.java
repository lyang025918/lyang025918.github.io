// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Lev1TParametricDescription.java

package org.apache.lucene.util.automaton;


// Referenced classes of package org.apache.lucene.util.automaton:
//			LevenshteinAutomata

class Lev1TParametricDescription extends LevenshteinAutomata.ParametricDescription
{

	private static final long toStates0[] = {
		2L
	};
	private static final long offsetIncrs0[] = {
		0L
	};
	private static final long toStates1[] = {
		2627L
	};
	private static final long offsetIncrs1[] = {
		56L
	};
	private static final long toStates2[] = {
		0x3453491482140003L, 109L
	};
	private static final long offsetIncrs2[] = {
		0x555555a20000L
	};
	private static final long toStates3[] = {
		0x21520854900c0003L, 0x5b4d19a24534916dL, 55860L
	};
	private static final long offsetIncrs3[] = {
		0x5555ae0a20fc0000L, 0x55555555L
	};
	static final boolean $assertionsDisabled = !org/apache/lucene/util/automaton/Lev1TParametricDescription.desiredAssertionStatus();

	int transition(int absState, int position, int vector)
	{
		if (!$assertionsDisabled && absState == -1)
			throw new AssertionError();
		int state = absState / (w + 1);
		int offset = absState % (w + 1);
		if (!$assertionsDisabled && offset < 0)
			throw new AssertionError();
		if (position == w)
		{
			if (state < 2)
			{
				int loc = vector * 2 + state;
				offset += unpack(offsetIncrs0, loc, 1);
				state = unpack(toStates0, loc, 2) - 1;
			}
		} else
		if (position == w - 1)
		{
			if (state < 3)
			{
				int loc = vector * 3 + state;
				offset += unpack(offsetIncrs1, loc, 1);
				state = unpack(toStates1, loc, 2) - 1;
			}
		} else
		if (position == w - 2)
		{
			if (state < 6)
			{
				int loc = vector * 6 + state;
				offset += unpack(offsetIncrs2, loc, 2);
				state = unpack(toStates2, loc, 3) - 1;
			}
		} else
		if (state < 6)
		{
			int loc = vector * 6 + state;
			offset += unpack(offsetIncrs3, loc, 2);
			state = unpack(toStates3, loc, 3) - 1;
		}
		if (state == -1)
			return -1;
		else
			return state * (w + 1) + offset;
	}

	public Lev1TParametricDescription(int w)
	{
		super(w, 1, new int[] {
			0, 1, 0, -1, -1, -1
		});
	}

}
