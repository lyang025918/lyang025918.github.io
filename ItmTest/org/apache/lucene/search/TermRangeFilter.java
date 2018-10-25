// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TermRangeFilter.java

package org.apache.lucene.search;

import org.apache.lucene.util.BytesRef;

// Referenced classes of package org.apache.lucene.search:
//			MultiTermQueryWrapperFilter, TermRangeQuery

public class TermRangeFilter extends MultiTermQueryWrapperFilter
{

	public TermRangeFilter(String fieldName, BytesRef lowerTerm, BytesRef upperTerm, boolean includeLower, boolean includeUpper)
	{
		super(new TermRangeQuery(fieldName, lowerTerm, upperTerm, includeLower, includeUpper));
	}

	public static TermRangeFilter newStringRange(String field, String lowerTerm, String upperTerm, boolean includeLower, boolean includeUpper)
	{
		BytesRef lower = lowerTerm != null ? new BytesRef(lowerTerm) : null;
		BytesRef upper = upperTerm != null ? new BytesRef(upperTerm) : null;
		return new TermRangeFilter(field, lower, upper, includeLower, includeUpper);
	}

	public static TermRangeFilter Less(String fieldName, BytesRef upperTerm)
	{
		return new TermRangeFilter(fieldName, null, upperTerm, false, true);
	}

	public static TermRangeFilter More(String fieldName, BytesRef lowerTerm)
	{
		return new TermRangeFilter(fieldName, lowerTerm, null, true, false);
	}

	public BytesRef getLowerTerm()
	{
		return ((TermRangeQuery)query).getLowerTerm();
	}

	public BytesRef getUpperTerm()
	{
		return ((TermRangeQuery)query).getUpperTerm();
	}

	public boolean includesLower()
	{
		return ((TermRangeQuery)query).includesLower();
	}

	public boolean includesUpper()
	{
		return ((TermRangeQuery)query).includesUpper();
	}
}
