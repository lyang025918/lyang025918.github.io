// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LevenshteinAutomata.java

package org.apache.lucene.util.automaton;

import java.util.*;

// Referenced classes of package org.apache.lucene.util.automaton:
//			Lev1TParametricDescription, Lev1ParametricDescription, Lev2TParametricDescription, Lev2ParametricDescription, 
//			State, Transition, Automaton, BasicAutomata

public class LevenshteinAutomata
{
	static abstract class ParametricDescription
	{

		protected final int w;
		protected final int n;
		private final int minErrors[];
		private static final long MASKS[] = {
			1L, 3L, 7L, 15L, 31L, 63L, 127L, 255L, 511L, 1023L, 
			2047L, 4095L, 8191L, 16383L, 32767L, 65535L, 0x1ffffL, 0x3ffffL, 0x7ffffL, 0xfffffL, 
			0x1fffffL, 0x3fffffL, 0x7fffffL, 0xffffffL, 0x1ffffffL, 0x3ffffffL, 0x7ffffffL, 0xfffffffL, 0x1fffffffL, 0x3fffffffL, 
			0x7fffffffL, 0xffffffffL, 0x1ffffffffL, 0x3ffffffffL, 0x7ffffffffL, 0xfffffffffL, 0x1fffffffffL, 0x3fffffffffL, 0x7fffffffffL, 0xffffffffffL, 
			0x1ffffffffffL, 0x3ffffffffffL, 0x7ffffffffffL, 0xfffffffffffL, 0x1fffffffffffL, 0x3fffffffffffL, 0x7fffffffffffL, 0xffffffffffffL, 0x1ffffffffffffL, 0x3ffffffffffffL, 
			0x7ffffffffffffL, 0xfffffffffffffL, 0x1fffffffffffffL, 0x3fffffffffffffL, 0x7fffffffffffffL, 0xffffffffffffffL, 0x1ffffffffffffffL, 0x3ffffffffffffffL, 0x7ffffffffffffffL, 0xfffffffffffffffL, 
			0x1fffffffffffffffL, 0x3fffffffffffffffL, 0x7fffffffffffffffL
		};
		static final boolean $assertionsDisabled = !org/apache/lucene/util/automaton/LevenshteinAutomata.desiredAssertionStatus();

		int size()
		{
			return minErrors.length * (w + 1);
		}

		boolean isAccept(int absState)
		{
			int state = absState / (w + 1);
			int offset = absState % (w + 1);
			if (!$assertionsDisabled && offset < 0)
				throw new AssertionError();
			else
				return (w - offset) + minErrors[state] <= n;
		}

		int getPosition(int absState)
		{
			return absState % (w + 1);
		}

		abstract int transition(int i, int j, int k);

		protected int unpack(long data[], int index, int bitsPerValue)
		{
			long bitLoc = bitsPerValue * index;
			int dataLoc = (int)(bitLoc >> 6);
			int bitStart = (int)(bitLoc & 63L);
			if (bitStart + bitsPerValue <= 64)
			{
				return (int)(data[dataLoc] >> bitStart & MASKS[bitsPerValue - 1]);
			} else
			{
				int part = 64 - bitStart;
				return (int)((data[dataLoc] >> bitStart & MASKS[part - 1]) + ((data[1 + dataLoc] & MASKS[bitsPerValue - part - 1]) << part));
			}
		}


		ParametricDescription(int w, int n, int minErrors[])
		{
			this.w = w;
			this.n = n;
			this.minErrors = minErrors;
		}
	}


	public static final int MAXIMUM_SUPPORTED_DISTANCE = 2;
	final String input;
	final int word[];
	final int alphabet[];
	final int rangeLower[];
	final int rangeUpper[];
	int numRanges;
	ParametricDescription descriptions[];

	public LevenshteinAutomata(String input, boolean withTranspositions)
	{
		numRanges = 0;
		this.input = input;
		int length = Character.codePointCount(input, 0, input.length());
		word = new int[length];
		int i = 0;
		int j = 0;
		int cp = 0;
		for (; i < input.length(); i += Character.charCount(cp))
			word[j++] = cp = input.codePointAt(i);

		SortedSet set = new TreeSet();
		for (int i = 0; i < word.length; i++)
			set.add(Integer.valueOf(word[i]));

		alphabet = new int[set.size()];
		Iterator iterator = set.iterator();
		for (int i = 0; i < alphabet.length; i++)
			alphabet[i] = ((Integer)iterator.next()).intValue();

		rangeLower = new int[alphabet.length + 2];
		rangeUpper = new int[alphabet.length + 2];
		int lower = 0;
		for (int i = 0; i < alphabet.length; i++)
		{
			int higher = alphabet[i];
			if (higher > lower)
			{
				rangeLower[numRanges] = lower;
				rangeUpper[numRanges] = higher - 1;
				numRanges++;
			}
			lower = higher + 1;
		}

		if (lower <= 0x10ffff)
		{
			rangeLower[numRanges] = lower;
			rangeUpper[numRanges] = 0x10ffff;
			numRanges++;
		}
		descriptions = (new ParametricDescription[] {
			null, withTranspositions ? new Lev1TParametricDescription(word.length) : new Lev1ParametricDescription(word.length), withTranspositions ? new Lev2TParametricDescription(word.length) : new Lev2ParametricDescription(word.length)
		});
	}

	public Automaton toAutomaton(int n)
	{
		if (n == 0)
			return BasicAutomata.makeString(input);
		if (n >= descriptions.length)
			return null;
		int range = 2 * n + 1;
		ParametricDescription description = descriptions[n];
		State states[] = new State[description.size()];
		for (int i = 0; i < states.length; i++)
		{
			states[i] = new State();
			states[i].number = i;
			states[i].setAccept(description.isAccept(i));
		}

		for (int k = 0; k < states.length; k++)
		{
			int xpos = description.getPosition(k);
			if (xpos < 0)
				continue;
			int end = xpos + Math.min(word.length - xpos, range);
			for (int x = 0; x < alphabet.length; x++)
			{
				int ch = alphabet[x];
				int cvec = getVector(ch, xpos, end);
				int dest = description.transition(k, xpos, cvec);
				if (dest >= 0)
					states[k].addTransition(new Transition(ch, states[dest]));
			}

			int dest = description.transition(k, xpos, 0);
			if (dest < 0)
				continue;
			for (int r = 0; r < numRanges; r++)
				states[k].addTransition(new Transition(rangeLower[r], rangeUpper[r], states[dest]));

		}

		Automaton a = new Automaton(states[0]);
		a.setDeterministic(true);
		a.reduce();
		return a;
	}

	int getVector(int x, int pos, int end)
	{
		int vector = 0;
		for (int i = pos; i < end; i++)
		{
			vector <<= 1;
			if (word[i] == x)
				vector |= 1;
		}

		return vector;
	}
}
