// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AutomatonTermsEnum.java

package org.apache.lucene.index;

import java.io.IOException;
import java.util.Comparator;
import org.apache.lucene.util.*;
import org.apache.lucene.util.automaton.*;

// Referenced classes of package org.apache.lucene.index:
//			FilteredTermsEnum, TermsEnum

class AutomatonTermsEnum extends FilteredTermsEnum
{

	private final ByteRunAutomaton runAutomaton;
	private final BytesRef commonSuffixRef;
	private final boolean finite;
	private final Transition allTransitions[][];
	private final long visited[];
	private long curGen;
	private final BytesRef seekBytesRef = new BytesRef(10);
	private boolean linear;
	private final BytesRef linearUpperBound = new BytesRef(10);
	private final Comparator termComp;
	private final IntsRef savedStates = new IntsRef(10);
	static final boolean $assertionsDisabled = !org/apache/lucene/index/AutomatonTermsEnum.desiredAssertionStatus();

	public AutomatonTermsEnum(TermsEnum tenum, CompiledAutomaton compiled)
	{
		super(tenum);
		linear = false;
		finite = compiled.finite.booleanValue();
		runAutomaton = compiled.runAutomaton;
		if (!$assertionsDisabled && runAutomaton == null)
		{
			throw new AssertionError();
		} else
		{
			commonSuffixRef = compiled.commonSuffixRef;
			allTransitions = compiled.sortedTransitions;
			visited = new long[runAutomaton.getSize()];
			termComp = getComparator();
			return;
		}
	}

	protected FilteredTermsEnum.AcceptStatus accept(BytesRef term)
	{
		if (commonSuffixRef == null || StringHelper.endsWith(term, commonSuffixRef))
		{
			if (runAutomaton.run(term.bytes, term.offset, term.length))
				return linear ? FilteredTermsEnum.AcceptStatus.YES : FilteredTermsEnum.AcceptStatus.YES_AND_SEEK;
			else
				return !linear || termComp.compare(term, linearUpperBound) >= 0 ? FilteredTermsEnum.AcceptStatus.NO_AND_SEEK : FilteredTermsEnum.AcceptStatus.NO;
		} else
		{
			return !linear || termComp.compare(term, linearUpperBound) >= 0 ? FilteredTermsEnum.AcceptStatus.NO_AND_SEEK : FilteredTermsEnum.AcceptStatus.NO;
		}
	}

	protected BytesRef nextSeekTerm(BytesRef term)
		throws IOException
	{
		if (term == null)
		{
			if (!$assertionsDisabled && seekBytesRef.length != 0)
				throw new AssertionError();
			if (runAutomaton.isAccept(runAutomaton.getInitialState()))
				return seekBytesRef;
		} else
		{
			seekBytesRef.copyBytes(term);
		}
		if (nextString())
			return seekBytesRef;
		else
			return null;
	}

	private void setLinear(int position)
	{
		if (!$assertionsDisabled && linear)
			throw new AssertionError();
		int state = runAutomaton.getInitialState();
		int maxInterval = 255;
		int i;
		for (i = 0; i < position; i++)
		{
			state = runAutomaton.step(state, seekBytesRef.bytes[i] & 0xff);
			if (!$assertionsDisabled && state < 0)
				throw new AssertionError((new StringBuilder()).append("state=").append(state).toString());
		}

		i = 0;
		do
		{
			if (i >= allTransitions[state].length)
				break;
			Transition t = allTransitions[state][i];
			if (t.getMin() <= (seekBytesRef.bytes[position] & 0xff) && (seekBytesRef.bytes[position] & 0xff) <= t.getMax())
			{
				maxInterval = t.getMax();
				break;
			}
			i++;
		} while (true);
		if (maxInterval != 255)
			maxInterval++;
		int length = position + 1;
		if (linearUpperBound.bytes.length < length)
			linearUpperBound.bytes = new byte[length];
		System.arraycopy(seekBytesRef.bytes, 0, linearUpperBound.bytes, 0, position);
		linearUpperBound.bytes[position] = (byte)maxInterval;
		linearUpperBound.length = length;
		linear = true;
	}

	private boolean nextString()
	{
		int pos = 0;
		savedStates.grow(seekBytesRef.length + 1);
		int states[] = savedStates.ints;
		states[0] = runAutomaton.getInitialState();
		do
		{
			do
			{
				curGen++;
				linear = false;
				int state = states[pos];
				for (; pos < seekBytesRef.length; pos++)
				{
					visited[state] = curGen;
					int nextState = runAutomaton.step(state, seekBytesRef.bytes[pos] & 0xff);
					if (nextState == -1)
						break;
					states[pos + 1] = nextState;
					if (!finite && !linear && visited[nextState] == curGen)
						setLinear(pos);
					state = nextState;
				}

				if (nextString(state, pos))
					return true;
				if ((pos = backtrack(pos)) < 0)
					return false;
				int newState = runAutomaton.step(states[pos], seekBytesRef.bytes[pos] & 0xff);
				if (newState >= 0 && runAutomaton.isAccept(newState))
					return true;
			} while (finite);
			pos = 0;
		} while (true);
	}

	private boolean nextString(int state, int position)
	{
		int c = 0;
		if (position < seekBytesRef.length)
		{
			c = seekBytesRef.bytes[position] & 0xff;
			if (c++ == 255)
				return false;
		}
		seekBytesRef.length = position;
		visited[state] = curGen;
		Transition transitions[] = allTransitions[state];
		for (int i = 0; i < transitions.length; i++)
		{
			Transition transition = transitions[i];
			if (transition.getMax() >= c)
			{
				int nextChar = Math.max(c, transition.getMin());
				seekBytesRef.grow(seekBytesRef.length + 1);
				seekBytesRef.length++;
				seekBytesRef.bytes[seekBytesRef.length - 1] = (byte)nextChar;
				state = transition.getDest().getNumber();
				do
				{
					if (visited[state] == curGen || runAutomaton.isAccept(state))
						break;
					visited[state] = curGen;
					transition = allTransitions[state][0];
					state = transition.getDest().getNumber();
					seekBytesRef.grow(seekBytesRef.length + 1);
					seekBytesRef.length++;
					seekBytesRef.bytes[seekBytesRef.length - 1] = (byte)transition.getMin();
					if (!finite && !linear && visited[state] == curGen)
						setLinear(seekBytesRef.length - 1);
				} while (true);
				return true;
			}
		}

		return false;
	}

	private int backtrack(int position)
	{
		while (position-- > 0) 
		{
			int nextChar = seekBytesRef.bytes[position] & 0xff;
			if (nextChar++ != 255)
			{
				seekBytesRef.bytes[position] = (byte)nextChar;
				seekBytesRef.length = position + 1;
				return position;
			}
		}
		return -1;
	}

}
