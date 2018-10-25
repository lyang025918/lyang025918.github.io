// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   StatePair.java

package org.apache.lucene.util.automaton;


// Referenced classes of package org.apache.lucene.util.automaton:
//			State

public class StatePair
{

	State s;
	State s1;
	State s2;

	StatePair(State s, State s1, State s2)
	{
		this.s = s;
		this.s1 = s1;
		this.s2 = s2;
	}

	public StatePair(State s1, State s2)
	{
		this.s1 = s1;
		this.s2 = s2;
	}

	public State getFirstState()
	{
		return s1;
	}

	public State getSecondState()
	{
		return s2;
	}

	public boolean equals(Object obj)
	{
		if (obj instanceof StatePair)
		{
			StatePair p = (StatePair)obj;
			return p.s1 == s1 && p.s2 == s2;
		} else
		{
			return false;
		}
	}

	public int hashCode()
	{
		return s1.hashCode() + s2.hashCode();
	}
}
