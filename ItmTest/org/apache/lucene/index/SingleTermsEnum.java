// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SingleTermsEnum.java

package org.apache.lucene.index;

import org.apache.lucene.util.BytesRef;

// Referenced classes of package org.apache.lucene.index:
//			FilteredTermsEnum, TermsEnum

public final class SingleTermsEnum extends FilteredTermsEnum
{

	private final BytesRef singleRef;

	public SingleTermsEnum(TermsEnum tenum, BytesRef termText)
	{
		super(tenum);
		singleRef = termText;
		setInitialSeekTerm(termText);
	}

	protected FilteredTermsEnum.AcceptStatus accept(BytesRef term)
	{
		return term.equals(singleRef) ? FilteredTermsEnum.AcceptStatus.YES : FilteredTermsEnum.AcceptStatus.END;
	}
}
