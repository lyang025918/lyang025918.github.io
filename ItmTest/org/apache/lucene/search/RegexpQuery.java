// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RegexpQuery.java

package org.apache.lucene.search;

import org.apache.lucene.index.Term;
import org.apache.lucene.util.ToStringUtils;
import org.apache.lucene.util.automaton.*;

// Referenced classes of package org.apache.lucene.search:
//			AutomatonQuery

public class RegexpQuery extends AutomatonQuery
{

	private static AutomatonProvider defaultProvider = new AutomatonProvider() {

		public Automaton getAutomaton(String name)
		{
			return null;
		}

	};

	public RegexpQuery(Term term)
	{
		this(term, 65535);
	}

	public RegexpQuery(Term term, int flags)
	{
		this(term, flags, defaultProvider);
	}

	public RegexpQuery(Term term, int flags, AutomatonProvider provider)
	{
		super(term, (new RegExp(term.text(), flags)).toAutomaton(provider));
	}

	public String toString(String field)
	{
		StringBuilder buffer = new StringBuilder();
		if (!term.field().equals(field))
		{
			buffer.append(term.field());
			buffer.append(":");
		}
		buffer.append('/');
		buffer.append(term.text());
		buffer.append('/');
		buffer.append(ToStringUtils.boost(getBoost()));
		return buffer.toString();
	}

}
