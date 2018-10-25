// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   UTF32ToUTF8.java

package org.apache.lucene.util.automaton;

import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.util.ArrayUtil;
import org.apache.lucene.util.RamUsageEstimator;

// Referenced classes of package org.apache.lucene.util.automaton:
//			Transition, State, Automaton

public final class UTF32ToUTF8
{
	private static class UTF8Sequence
	{

		private final UTF8Byte bytes[] = new UTF8Byte[4];
		private int len;

		public int byteAt(int idx)
		{
			return bytes[idx].value;
		}

		public int numBits(int idx)
		{
			return bytes[idx].bits;
		}

		private void set(int code)
		{
			if (code < 128)
			{
				bytes[0].value = code;
				bytes[0].bits = 7;
				len = 1;
			} else
			if (code < 2048)
			{
				bytes[0].value = 0xc0 | code >> 6;
				bytes[0].bits = 5;
				setRest(code, 1);
				len = 2;
			} else
			if (code < 0x10000)
			{
				bytes[0].value = 0xe0 | code >> 12;
				bytes[0].bits = 4;
				setRest(code, 2);
				len = 3;
			} else
			{
				bytes[0].value = 0xf0 | code >> 18;
				bytes[0].bits = 3;
				setRest(code, 3);
				len = 4;
			}
		}

		private void setRest(int code, int numBytes)
		{
			for (int i = 0; i < numBytes; i++)
			{
				bytes[numBytes - i].value = 0x80 | code & UTF32ToUTF8.MASKS[5];
				bytes[numBytes - i].bits = 6;
				code >>= 6;
			}

		}

		public String toString()
		{
			StringBuilder b = new StringBuilder();
			for (int i = 0; i < len; i++)
			{
				if (i > 0)
					b.append(' ');
				b.append(Integer.toBinaryString(bytes[i].value));
			}

			return b.toString();
		}



		public UTF8Sequence()
		{
			for (int i = 0; i < 4; i++)
				bytes[i] = new UTF8Byte();

		}
	}

	private static class UTF8Byte
	{

		int value;
		byte bits;

		private UTF8Byte()
		{
		}

	}


	private static final int startCodes[] = {
		0, 128, 2048, 0x10000
	};
	private static final int endCodes[] = {
		127, 2047, 65535, 0x10ffff
	};
	static int MASKS[];
	private final UTF8Sequence startUTF8 = new UTF8Sequence();
	private final UTF8Sequence endUTF8 = new UTF8Sequence();
	private final UTF8Sequence tmpUTF8a = new UTF8Sequence();
	private final UTF8Sequence tmpUTF8b = new UTF8Sequence();
	private State utf8States[];
	private int utf8StateCount;
	static final boolean $assertionsDisabled = !org/apache/lucene/util/automaton/UTF32ToUTF8.desiredAssertionStatus();

	public UTF32ToUTF8()
	{
	}

	void convertOneEdge(State start, State end, int startCodePoint, int endCodePoint)
	{
		startUTF8.set(startCodePoint);
		endUTF8.set(endCodePoint);
		build(start, end, startUTF8, endUTF8, 0);
	}

	private void build(State start, State end, UTF8Sequence startUTF8, UTF8Sequence endUTF8, int upto)
	{
		if (startUTF8.byteAt(upto) == endUTF8.byteAt(upto))
		{
			if (upto == startUTF8.len - 1 && upto == endUTF8.len - 1)
			{
				start.addTransition(new Transition(startUTF8.byteAt(upto), endUTF8.byteAt(upto), end));
				return;
			}
			if (!$assertionsDisabled && startUTF8.len <= upto + 1)
				throw new AssertionError();
			if (!$assertionsDisabled && endUTF8.len <= upto + 1)
				throw new AssertionError();
			State n = newUTF8State();
			start.addTransition(new Transition(startUTF8.byteAt(upto), n));
			build(n, end, startUTF8, endUTF8, 1 + upto);
		} else
		if (startUTF8.len == endUTF8.len)
		{
			if (upto == startUTF8.len - 1)
			{
				start.addTransition(new Transition(startUTF8.byteAt(upto), endUTF8.byteAt(upto), end));
			} else
			{
				start(start, end, startUTF8, upto, false);
				if (endUTF8.byteAt(upto) - startUTF8.byteAt(upto) > 1)
					all(start, end, startUTF8.byteAt(upto) + 1, endUTF8.byteAt(upto) - 1, startUTF8.len - upto - 1);
				end(start, end, endUTF8, upto, false);
			}
		} else
		{
			start(start, end, startUTF8, upto, true);
			int byteCount = (1 + startUTF8.len) - upto;
			for (int limit = endUTF8.len - upto; byteCount < limit; byteCount++)
			{
				tmpUTF8a.set(startCodes[byteCount - 1]);
				tmpUTF8b.set(endCodes[byteCount - 1]);
				all(start, end, tmpUTF8a.byteAt(0), tmpUTF8b.byteAt(0), tmpUTF8a.len - 1);
			}

			end(start, end, endUTF8, upto, true);
		}
	}

	private void start(State start, State end, UTF8Sequence utf8, int upto, boolean doAll)
	{
		if (upto == utf8.len - 1)
		{
			start.addTransition(new Transition(utf8.byteAt(upto), utf8.byteAt(upto) | MASKS[utf8.numBits(upto) - 1], end));
		} else
		{
			State n = newUTF8State();
			start.addTransition(new Transition(utf8.byteAt(upto), n));
			start(n, end, utf8, 1 + upto, true);
			int endCode = utf8.byteAt(upto) | MASKS[utf8.numBits(upto) - 1];
			if (doAll && utf8.byteAt(upto) != endCode)
				all(start, end, utf8.byteAt(upto) + 1, endCode, utf8.len - upto - 1);
		}
	}

	private void end(State start, State end, UTF8Sequence utf8, int upto, boolean doAll)
	{
		if (upto == utf8.len - 1)
		{
			start.addTransition(new Transition(utf8.byteAt(upto) & ~MASKS[utf8.numBits(upto) - 1], utf8.byteAt(upto), end));
		} else
		{
			int startCode;
			if (utf8.numBits(upto) == 5)
				startCode = 194;
			else
				startCode = utf8.byteAt(upto) & ~MASKS[utf8.numBits(upto) - 1];
			if (doAll && utf8.byteAt(upto) != startCode)
				all(start, end, startCode, utf8.byteAt(upto) - 1, utf8.len - upto - 1);
			State n = newUTF8State();
			start.addTransition(new Transition(utf8.byteAt(upto), n));
			end(n, end, utf8, 1 + upto, true);
		}
	}

	private void all(State start, State end, int startCode, int endCode, int left)
	{
		if (left == 0)
		{
			start.addTransition(new Transition(startCode, endCode, end));
		} else
		{
			State lastN = newUTF8State();
			start.addTransition(new Transition(startCode, endCode, lastN));
			while (left > 1) 
			{
				State n = newUTF8State();
				lastN.addTransition(new Transition(128, 191, n));
				left--;
				lastN = n;
			}
			lastN.addTransition(new Transition(128, 191, end));
		}
	}

	public Automaton convert(Automaton utf32)
	{
		if (utf32.isSingleton())
			utf32 = utf32.cloneExpanded();
		State map[] = new State[utf32.getNumberedStates().length];
		List pending = new ArrayList();
		State utf32State = utf32.getInitialState();
		pending.add(utf32State);
		Automaton utf8 = new Automaton();
		utf8.setDeterministic(false);
		State utf8State = utf8.getInitialState();
		utf8States = new State[5];
		utf8StateCount = 0;
		utf8State.number = utf8StateCount;
		utf8States[utf8StateCount] = utf8State;
		utf8StateCount++;
		utf8State.setAccept(utf32State.isAccept());
		map[utf32State.number] = utf8State;
		while (pending.size() != 0) 
		{
			utf32State = (State)pending.remove(pending.size() - 1);
			utf8State = map[utf32State.number];
			int i = 0;
			while (i < utf32State.numTransitions) 
			{
				Transition t = utf32State.transitionsArray[i];
				State destUTF32 = t.to;
				State destUTF8 = map[destUTF32.number];
				if (destUTF8 == null)
				{
					destUTF8 = newUTF8State();
					destUTF8.accept = destUTF32.accept;
					map[destUTF32.number] = destUTF8;
					pending.add(destUTF32);
				}
				convertOneEdge(utf8State, destUTF8, t.min, t.max);
				i++;
			}
		}
		utf8.setNumberedStates(utf8States, utf8StateCount);
		return utf8;
	}

	private State newUTF8State()
	{
		State s = new State();
		if (utf8StateCount == utf8States.length)
		{
			State newArray[] = new State[ArrayUtil.oversize(1 + utf8StateCount, RamUsageEstimator.NUM_BYTES_OBJECT_REF)];
			System.arraycopy(utf8States, 0, newArray, 0, utf8StateCount);
			utf8States = newArray;
		}
		utf8States[utf8StateCount] = s;
		s.number = utf8StateCount;
		utf8StateCount++;
		return s;
	}

	static 
	{
		MASKS = new int[32];
		int v = 2;
		for (int i = 0; i < 32; i++)
		{
			MASKS[i] = v - 1;
			v *= 2;
		}

	}
}
