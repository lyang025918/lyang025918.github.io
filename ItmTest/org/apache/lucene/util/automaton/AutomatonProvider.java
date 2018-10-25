// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AutomatonProvider.java

package org.apache.lucene.util.automaton;

import java.io.IOException;

// Referenced classes of package org.apache.lucene.util.automaton:
//			Automaton

public interface AutomatonProvider
{

	public abstract Automaton getAutomaton(String s)
		throws IOException;
}
