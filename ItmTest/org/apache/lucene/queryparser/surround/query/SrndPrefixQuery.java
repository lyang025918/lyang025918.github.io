// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SrndPrefixQuery.java

package org.apache.lucene.queryparser.surround.query;

import java.io.IOException;
import org.apache.lucene.index.*;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.StringHelper;

// Referenced classes of package org.apache.lucene.queryparser.surround.query:
//			SimpleTerm

public class SrndPrefixQuery extends SimpleTerm
{

	private final BytesRef prefixRef;
	private final String prefix;
	private final char truncator;

	public SrndPrefixQuery(String prefix, boolean quoted, char truncator)
	{
		super(quoted);
		this.prefix = prefix;
		prefixRef = new BytesRef(prefix);
		this.truncator = truncator;
	}

	public String getPrefix()
	{
		return prefix;
	}

	public char getSuffixOperator()
	{
		return truncator;
	}

	public Term getLucenePrefixTerm(String fieldName)
	{
		return new Term(fieldName, getPrefix());
	}

	public String toStringUnquoted()
	{
		return getPrefix();
	}

	protected void suffixToString(StringBuilder r)
	{
		r.append(getSuffixOperator());
	}

	public void visitMatchingTerms(IndexReader reader, String fieldName, SimpleTerm.MatchingTermVisitor mtv)
		throws IOException
	{
		Terms terms = MultiFields.getTerms(reader, fieldName);
		if (terms != null)
		{
			TermsEnum termsEnum = terms.iterator(null);
			boolean skip = false;
			org.apache.lucene.index.TermsEnum.SeekStatus status = termsEnum.seekCeil(new BytesRef(getPrefix()));
			if (status == org.apache.lucene.index.TermsEnum.SeekStatus.FOUND)
				mtv.visitMatchingTerm(getLucenePrefixTerm(fieldName));
			else
			if (status == org.apache.lucene.index.TermsEnum.SeekStatus.NOT_FOUND)
			{
				if (StringHelper.startsWith(termsEnum.term(), prefixRef))
					mtv.visitMatchingTerm(new Term(fieldName, termsEnum.term().utf8ToString()));
				else
					skip = true;
			} else
			{
				skip = true;
			}
			if (!skip)
				do
				{
					BytesRef text = termsEnum.next();
					if (text == null || !StringHelper.startsWith(text, prefixRef))
						break;
					mtv.visitMatchingTerm(new Term(fieldName, text.utf8ToString()));
				} while (true);
		}
	}
}
