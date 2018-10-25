// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CompiledAutomaton.java

package org.apache.lucene.util.automaton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.index.*;
import org.apache.lucene.search.PrefixTermsEnum;
import org.apache.lucene.util.BytesRef;

// Referenced classes of package org.apache.lucene.util.automaton:
//			UTF32ToUTF8, ByteRunAutomaton, Automaton, Transition, 
//			BasicOperations, SpecialOperations, BasicAutomata, State

public class CompiledAutomaton
{
	public static final class AUTOMATON_TYPE extends Enum
	{

		public static final AUTOMATON_TYPE NONE;
		public static final AUTOMATON_TYPE ALL;
		public static final AUTOMATON_TYPE SINGLE;
		public static final AUTOMATON_TYPE PREFIX;
		public static final AUTOMATON_TYPE NORMAL;
		private static final AUTOMATON_TYPE $VALUES[];

		public static AUTOMATON_TYPE[] values()
		{
			return (AUTOMATON_TYPE[])$VALUES.clone();
		}

		public static AUTOMATON_TYPE valueOf(String name)
		{
			return (AUTOMATON_TYPE)Enum.valueOf(org/apache/lucene/util/automaton/CompiledAutomaton$AUTOMATON_TYPE, name);
		}

		static 
		{
			NONE = new AUTOMATON_TYPE("NONE", 0);
			ALL = new AUTOMATON_TYPE("ALL", 1);
			SINGLE = new AUTOMATON_TYPE("SINGLE", 2);
			PREFIX = new AUTOMATON_TYPE("PREFIX", 3);
			NORMAL = new AUTOMATON_TYPE("NORMAL", 4);
			$VALUES = (new AUTOMATON_TYPE[] {
				NONE, ALL, SINGLE, PREFIX, NORMAL
			});
		}

		private AUTOMATON_TYPE(String s, int i)
		{
			super(s, i);
		}
	}


	public final AUTOMATON_TYPE type;
	public final BytesRef term;
	public final ByteRunAutomaton runAutomaton;
	public final Transition sortedTransitions[][];
	public final BytesRef commonSuffixRef;
	public final Boolean finite;
	static final boolean $assertionsDisabled = !org/apache/lucene/util/automaton/CompiledAutomaton.desiredAssertionStatus();

	public CompiledAutomaton(Automaton automaton)
	{
		this(automaton, null, true);
	}

	public CompiledAutomaton(Automaton automaton, Boolean finite, boolean simplify)
	{
		if (simplify)
		{
			if (BasicOperations.isEmpty(automaton))
			{
				type = AUTOMATON_TYPE.NONE;
				term = null;
				commonSuffixRef = null;
				runAutomaton = null;
				sortedTransitions = (Transition[][])null;
				this.finite = null;
				return;
			}
			if (BasicOperations.isTotal(automaton))
			{
				type = AUTOMATON_TYPE.ALL;
				term = null;
				commonSuffixRef = null;
				runAutomaton = null;
				sortedTransitions = (Transition[][])null;
				this.finite = null;
				return;
			}
			String commonPrefix;
			String singleton;
			if (automaton.getSingleton() == null)
			{
				commonPrefix = SpecialOperations.getCommonPrefix(automaton);
				if (commonPrefix.length() > 0 && BasicOperations.sameLanguage(automaton, BasicAutomata.makeString(commonPrefix)))
					singleton = commonPrefix;
				else
					singleton = null;
			} else
			{
				commonPrefix = null;
				singleton = automaton.getSingleton();
			}
			if (singleton != null)
			{
				type = AUTOMATON_TYPE.SINGLE;
				term = new BytesRef(singleton);
				commonSuffixRef = null;
				runAutomaton = null;
				sortedTransitions = (Transition[][])null;
				this.finite = null;
				return;
			}
			if (BasicOperations.sameLanguage(automaton, BasicOperations.concatenate(BasicAutomata.makeString(commonPrefix), BasicAutomata.makeAnyString())))
			{
				type = AUTOMATON_TYPE.PREFIX;
				term = new BytesRef(commonPrefix);
				commonSuffixRef = null;
				runAutomaton = null;
				sortedTransitions = (Transition[][])null;
				this.finite = null;
				return;
			}
		}
		type = AUTOMATON_TYPE.NORMAL;
		term = null;
		if (finite == null)
			this.finite = Boolean.valueOf(SpecialOperations.isFinite(automaton));
		else
			this.finite = finite;
		Automaton utf8 = (new UTF32ToUTF8()).convert(automaton);
		if (this.finite.booleanValue())
			commonSuffixRef = null;
		else
			commonSuffixRef = SpecialOperations.getCommonSuffixBytesRef(utf8);
		runAutomaton = new ByteRunAutomaton(utf8, true);
		sortedTransitions = utf8.getSortedTransitions();
	}

	private BytesRef addTail(int state, BytesRef term, int idx, int leadLabel)
	{
		Transition maxTransition = null;
		Transition arr$[] = sortedTransitions[state];
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			Transition transition = arr$[i$];
			if (transition.min < leadLabel)
				maxTransition = transition;
		}

		if (!$assertionsDisabled && maxTransition == null)
			throw new AssertionError();
		int floorLabel;
		if (maxTransition.max > leadLabel - 1)
			floorLabel = leadLabel - 1;
		else
			floorLabel = maxTransition.max;
		if (idx >= term.bytes.length)
			term.grow(1 + idx);
		term.bytes[idx] = (byte)floorLabel;
		state = maxTransition.to.getNumber();
		idx++;
		do
		{
			Transition transitions[] = sortedTransitions[state];
			if (transitions.length == 0)
				if (!$assertionsDisabled && !runAutomaton.isAccept(state))
				{
					throw new AssertionError();
				} else
				{
					term.length = idx;
					return term;
				}
			if (!$assertionsDisabled && transitions.length == 0)
				throw new AssertionError();
			Transition lastTransition = transitions[transitions.length - 1];
			if (idx >= term.bytes.length)
				term.grow(1 + idx);
			term.bytes[idx] = (byte)lastTransition.max;
			state = lastTransition.to.getNumber();
			idx++;
		} while (true);
	}

	public TermsEnum getTermsEnum(Terms terms)
		throws IOException
	{
		static class 1
		{

			static final int $SwitchMap$org$apache$lucene$util$automaton$CompiledAutomaton$AUTOMATON_TYPE[];

			static 
			{
				$SwitchMap$org$apache$lucene$util$automaton$CompiledAutomaton$AUTOMATON_TYPE = new int[AUTOMATON_TYPE.values().length];
				try
				{
					$SwitchMap$org$apache$lucene$util$automaton$CompiledAutomaton$AUTOMATON_TYPE[AUTOMATON_TYPE.NONE.ordinal()] = 1;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$util$automaton$CompiledAutomaton$AUTOMATON_TYPE[AUTOMATON_TYPE.ALL.ordinal()] = 2;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$util$automaton$CompiledAutomaton$AUTOMATON_TYPE[AUTOMATON_TYPE.SINGLE.ordinal()] = 3;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$util$automaton$CompiledAutomaton$AUTOMATON_TYPE[AUTOMATON_TYPE.PREFIX.ordinal()] = 4;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$util$automaton$CompiledAutomaton$AUTOMATON_TYPE[AUTOMATON_TYPE.NORMAL.ordinal()] = 5;
				}
				catch (NoSuchFieldError ex) { }
			}
		}

		switch (1..SwitchMap.org.apache.lucene.util.automaton.CompiledAutomaton.AUTOMATON_TYPE[type.ordinal()])
		{
		case 1: // '\001'
			return TermsEnum.EMPTY;

		case 2: // '\002'
			return terms.iterator(null);

		case 3: // '\003'
			return new SingleTermsEnum(terms.iterator(null), term);

		case 4: // '\004'
			return new PrefixTermsEnum(terms.iterator(null), term);

		case 5: // '\005'
			return terms.intersect(this, null);
		}
		throw new RuntimeException("unhandled case");
	}

	public BytesRef floor(BytesRef input, BytesRef output)
	{
		output.offset = 0;
		int state = runAutomaton.getInitialState();
		if (input.length == 0)
			if (runAutomaton.isAccept(state))
			{
				output.length = 0;
				return output;
			} else
			{
				return null;
			}
		List stack = new ArrayList();
		int idx = 0;
		do
		{
			int label = input.bytes[input.offset + idx] & 0xff;
			int nextState = runAutomaton.step(state, label);
			if (idx == input.length - 1)
			{
				if (nextState != -1 && runAutomaton.isAccept(nextState))
				{
					if (idx >= output.bytes.length)
						output.grow(1 + idx);
					output.bytes[idx] = (byte)label;
					output.length = input.length;
					return output;
				}
				nextState = -1;
			}
			Transition transitions[];
			if (nextState == -1)
				do
				{
					transitions = sortedTransitions[state];
					if (transitions.length == 0)
						if (!$assertionsDisabled && !runAutomaton.isAccept(state))
						{
							throw new AssertionError();
						} else
						{
							output.length = idx;
							return output;
						}
					if (label - 1 < transitions[0].min)
					{
						if (runAutomaton.isAccept(state))
						{
							output.length = idx;
							return output;
						}
						if (stack.size() == 0)
							return null;
						state = ((Integer)stack.remove(stack.size() - 1)).intValue();
						idx--;
						label = input.bytes[input.offset + idx] & 0xff;
					} else
					{
						return addTail(state, output, idx, label);
					}
				} while (true);
			if (idx >= output.bytes.length)
				output.grow(1 + idx);
			output.bytes[idx] = (byte)label;
			stack.add(Integer.valueOf(state));
			state = nextState;
			idx++;
		} while (true);
	}

}
