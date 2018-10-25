// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PrefixTermsEnum.java

package org.apache.lucene.search;

import org.apache.lucene.index.FilteredTermsEnum;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.StringHelper;

public class PrefixTermsEnum extends FilteredTermsEnum
{

	private final BytesRef prefixRef;

	public PrefixTermsEnum(TermsEnum tenum, BytesRef prefixText)
	{
		super(tenum);
		setInitialSeekTerm(prefixRef = prefixText);
	}

	protected org.apache.lucene.index.FilteredTermsEnum.AcceptStatus accept(BytesRef term)
	{
		if (StringHelper.startsWith(term, prefixRef))
			return org.apache.lucene.index.FilteredTermsEnum.AcceptStatus.YES;
		else
			return org.apache.lucene.index.FilteredTermsEnum.AcceptStatus.END;
	}
}
