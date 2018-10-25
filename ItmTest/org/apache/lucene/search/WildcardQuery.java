// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   WildcardQuery.java

package org.apache.lucene.search;

import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.index.Term;
import org.apache.lucene.util.ToStringUtils;
import org.apache.lucene.util.automaton.*;

// Referenced classes of package org.apache.lucene.search:
//			AutomatonQuery

public class WildcardQuery extends AutomatonQuery
{

	public static final char WILDCARD_STRING = 42;
	public static final char WILDCARD_CHAR = 63;
	public static final char WILDCARD_ESCAPE = 92;

	public WildcardQuery(Term term)
	{
		super(term, toAutomaton(term));
	}

	public static Automaton toAutomaton(Term wildcardquery)
	{
		List automata = new ArrayList();
		String wildcardText = wildcardquery.text();
		int length;
		for (int i = 0; i < wildcardText.length(); i += length)
		{
			int c = wildcardText.codePointAt(i);
			length = Character.charCount(c);
			switch (c)
			{
			case 42: // '*'
				automata.add(BasicAutomata.makeAnyString());
				break;

			case 63: // '?'
				automata.add(BasicAutomata.makeAnyChar());
				break;

			case 92: // '\\'
				if (i + length < wildcardText.length())
				{
					int nextChar = wildcardText.codePointAt(i + length);
					length += Character.charCount(nextChar);
					automata.add(BasicAutomata.makeChar(nextChar));
					break;
				}
				// fall through

			default:
				automata.add(BasicAutomata.makeChar(c));
				break;
			}
		}

		return BasicOperations.concatenate(automata);
	}

	public Term getTerm()
	{
		return term;
	}

	public String toString(String field)
	{
		StringBuilder buffer = new StringBuilder();
		if (!getField().equals(field))
		{
			buffer.append(getField());
			buffer.append(":");
		}
		buffer.append(term.text());
		buffer.append(ToStringUtils.boost(getBoost()));
		return buffer.toString();
	}
}
