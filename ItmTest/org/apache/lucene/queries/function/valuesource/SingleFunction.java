// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SingleFunction.java

package org.apache.lucene.queries.function.valuesource;

import java.io.IOException;
import java.util.Map;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.search.IndexSearcher;

public abstract class SingleFunction extends ValueSource
{

	protected final ValueSource source;

	public SingleFunction(ValueSource source)
	{
		this.source = source;
	}

	protected abstract String name();

	public String description()
	{
		return (new StringBuilder()).append(name()).append('(').append(source.description()).append(')').toString();
	}

	public int hashCode()
	{
		return source.hashCode() + name().hashCode();
	}

	public boolean equals(Object o)
	{
		if (getClass() != o.getClass())
		{
			return false;
		} else
		{
			SingleFunction other = (SingleFunction)o;
			return name().equals(other.name()) && source.equals(other.source);
		}
	}

	public void createWeight(Map context, IndexSearcher searcher)
		throws IOException
	{
		source.createWeight(context, searcher);
	}
}
