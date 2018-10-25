// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AutomatonQuery.java

package org.apache.lucene.search;

import java.io.IOException;
import org.apache.lucene.index.*;
import org.apache.lucene.util.AttributeSource;
import org.apache.lucene.util.ToStringUtils;
import org.apache.lucene.util.automaton.*;

// Referenced classes of package org.apache.lucene.search:
//			MultiTermQuery

public class AutomatonQuery extends MultiTermQuery
{

	protected final Automaton automaton;
	protected final CompiledAutomaton compiled;
	protected final Term term;

	public AutomatonQuery(Term term, Automaton automaton)
	{
		super(term.field());
		this.term = term;
		this.automaton = automaton;
		compiled = new CompiledAutomaton(automaton);
	}

	protected TermsEnum getTermsEnum(Terms terms, AttributeSource atts)
		throws IOException
	{
		return compiled.getTermsEnum(terms);
	}

	public int hashCode()
	{
		int prime = 31;
		int result = super.hashCode();
		if (automaton != null)
		{
			int automatonHashCode = automaton.getNumberOfStates() * 3 + automaton.getNumberOfTransitions() * 2;
			if (automatonHashCode == 0)
				automatonHashCode = 1;
			result = 31 * result + automatonHashCode;
		}
		result = 31 * result + (term != null ? term.hashCode() : 0);
		return result;
	}

	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AutomatonQuery other = (AutomatonQuery)obj;
		if (automaton == null)
		{
			if (other.automaton != null)
				return false;
		} else
		if (!BasicOperations.sameLanguage(automaton, other.automaton))
			return false;
		if (term == null)
		{
			if (other.term != null)
				return false;
		} else
		if (!term.equals(other.term))
			return false;
		return true;
	}

	public String toString(String field)
	{
		StringBuilder buffer = new StringBuilder();
		if (!term.field().equals(field))
		{
			buffer.append(term.field());
			buffer.append(":");
		}
		buffer.append(getClass().getSimpleName());
		buffer.append(" {");
		buffer.append('\n');
		buffer.append(automaton.toString());
		buffer.append("}");
		buffer.append(ToStringUtils.boost(getBoost()));
		return buffer.toString();
	}
}
