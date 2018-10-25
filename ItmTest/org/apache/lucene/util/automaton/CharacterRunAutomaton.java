// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CharacterRunAutomaton.java

package org.apache.lucene.util.automaton;


// Referenced classes of package org.apache.lucene.util.automaton:
//			RunAutomaton, Automaton

public class CharacterRunAutomaton extends RunAutomaton
{

	public CharacterRunAutomaton(Automaton a)
	{
		super(a, 0x10ffff, false);
	}

	public boolean run(String s)
	{
		int p = initial;
		int l = s.length();
		int i = 0;
		int cp = 0;
		for (; i < l; i += Character.charCount(cp))
		{
			p = step(p, cp = s.codePointAt(i));
			if (p == -1)
				return false;
		}

		return accept[p];
	}

	public boolean run(char s[], int offset, int length)
	{
		int p = initial;
		int l = offset + length;
		int i = offset;
		int cp = 0;
		for (; i < l; i += Character.charCount(cp))
		{
			p = step(p, cp = Character.codePointAt(s, i, l));
			if (p == -1)
				return false;
		}

		return accept[p];
	}
}
