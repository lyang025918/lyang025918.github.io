// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SrndTermQuery.java

package org.apache.lucene.queryparser.surround.query;

import java.io.IOException;
import org.apache.lucene.index.*;
import org.apache.lucene.util.BytesRef;

// Referenced classes of package org.apache.lucene.queryparser.surround.query:
//			SimpleTerm

public class SrndTermQuery extends SimpleTerm
{

	private final String termText;

	public SrndTermQuery(String termText, boolean quoted)
	{
		super(quoted);
		this.termText = termText;
	}

	public String getTermText()
	{
		return termText;
	}

	public Term getLuceneTerm(String fieldName)
	{
		return new Term(fieldName, getTermText());
	}

	public String toStringUnquoted()
	{
		return getTermText();
	}

	public void visitMatchingTerms(IndexReader reader, String fieldName, SimpleTerm.MatchingTermVisitor mtv)
		throws IOException
	{
		Terms terms = MultiFields.getTerms(reader, fieldName);
		if (terms != null)
		{
			TermsEnum termsEnum = terms.iterator(null);
			org.apache.lucene.index.TermsEnum.SeekStatus status = termsEnum.seekCeil(new BytesRef(getTermText()));
			if (status == org.apache.lucene.index.TermsEnum.SeekStatus.FOUND)
				mtv.visitMatchingTerm(getLuceneTerm(fieldName));
		}
	}
}
