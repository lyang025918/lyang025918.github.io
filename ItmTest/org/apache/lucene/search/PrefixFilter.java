// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PrefixFilter.java

package org.apache.lucene.search;

import org.apache.lucene.index.Term;

// Referenced classes of package org.apache.lucene.search:
//			MultiTermQueryWrapperFilter, PrefixQuery

public class PrefixFilter extends MultiTermQueryWrapperFilter
{

	public PrefixFilter(Term prefix)
	{
		super(new PrefixQuery(prefix));
	}

	public Term getPrefix()
	{
		return ((PrefixQuery)query).getPrefix();
	}

	public String toString()
	{
		StringBuilder buffer = new StringBuilder();
		buffer.append("PrefixFilter(");
		buffer.append(getPrefix().toString());
		buffer.append(")");
		return buffer.toString();
	}
}
