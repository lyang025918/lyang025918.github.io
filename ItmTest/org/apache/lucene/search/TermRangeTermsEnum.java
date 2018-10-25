// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TermRangeTermsEnum.java

package org.apache.lucene.search;

import java.util.Comparator;
import org.apache.lucene.index.FilteredTermsEnum;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.util.BytesRef;

public class TermRangeTermsEnum extends FilteredTermsEnum
{

	private final boolean includeLower;
	private final boolean includeUpper;
	private final BytesRef lowerBytesRef;
	private final BytesRef upperBytesRef;
	private final Comparator termComp = getComparator();

	public TermRangeTermsEnum(TermsEnum tenum, BytesRef lowerTerm, BytesRef upperTerm, boolean includeLower, boolean includeUpper)
	{
		super(tenum);
		if (lowerTerm == null)
		{
			lowerBytesRef = new BytesRef();
			this.includeLower = true;
		} else
		{
			lowerBytesRef = lowerTerm;
			this.includeLower = includeLower;
		}
		if (upperTerm == null)
		{
			this.includeUpper = true;
			upperBytesRef = null;
		} else
		{
			this.includeUpper = includeUpper;
			upperBytesRef = upperTerm;
		}
		setInitialSeekTerm(lowerBytesRef);
	}

	protected org.apache.lucene.index.FilteredTermsEnum.AcceptStatus accept(BytesRef term)
	{
		if (!includeLower && term.equals(lowerBytesRef))
			return org.apache.lucene.index.FilteredTermsEnum.AcceptStatus.NO;
		if (upperBytesRef != null)
		{
			int cmp = termComp.compare(upperBytesRef, term);
			if (cmp < 0 || !includeUpper && cmp == 0)
				return org.apache.lucene.index.FilteredTermsEnum.AcceptStatus.END;
		}
		return org.apache.lucene.index.FilteredTermsEnum.AcceptStatus.YES;
	}
}
