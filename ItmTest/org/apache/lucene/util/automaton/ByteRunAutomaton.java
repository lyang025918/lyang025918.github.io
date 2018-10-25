// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ByteRunAutomaton.java

package org.apache.lucene.util.automaton;


// Referenced classes of package org.apache.lucene.util.automaton:
//			RunAutomaton, UTF32ToUTF8, Automaton

public class ByteRunAutomaton extends RunAutomaton
{

	public ByteRunAutomaton(Automaton a)
	{
		this(a, false);
	}

	public ByteRunAutomaton(Automaton a, boolean utf8)
	{
		super(utf8 ? a : (new UTF32ToUTF8()).convert(a), 256, true);
	}

	public boolean run(byte s[], int offset, int length)
	{
		int p = initial;
		int l = offset + length;
		for (int i = offset; i < l; i++)
		{
			p = step(p, s[i] & 0xff);
			if (p == -1)
				return false;
		}

		return accept[p];
	}
}
