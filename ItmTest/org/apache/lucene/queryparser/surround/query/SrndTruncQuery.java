// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SrndTruncQuery.java

package org.apache.lucene.queryparser.surround.query;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.lucene.index.*;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.StringHelper;

// Referenced classes of package org.apache.lucene.queryparser.surround.query:
//			SimpleTerm

public class SrndTruncQuery extends SimpleTerm
{

	private final String truncated;
	private final char unlimited;
	private final char mask;
	private String prefix;
	private BytesRef prefixRef;
	private Pattern pattern;

	public SrndTruncQuery(String truncated, char unlimited, char mask)
	{
		super(false);
		this.truncated = truncated;
		this.unlimited = unlimited;
		this.mask = mask;
		truncatedToPrefixAndPattern();
	}

	public String getTruncated()
	{
		return truncated;
	}

	public String toStringUnquoted()
	{
		return getTruncated();
	}

	protected boolean matchingChar(char c)
	{
		return c != unlimited && c != mask;
	}

	protected void appendRegExpForChar(char c, StringBuilder re)
	{
		if (c == unlimited)
			re.append(".*");
		else
		if (c == mask)
			re.append(".");
		else
			re.append(c);
	}

	protected void truncatedToPrefixAndPattern()
	{
		int i;
		for (i = 0; i < truncated.length() && matchingChar(truncated.charAt(i)); i++);
		prefix = truncated.substring(0, i);
		prefixRef = new BytesRef(prefix);
		StringBuilder re = new StringBuilder();
		for (; i < truncated.length(); i++)
			appendRegExpForChar(truncated.charAt(i), re);

		pattern = Pattern.compile(re.toString());
	}

	public void visitMatchingTerms(IndexReader reader, String fieldName, SimpleTerm.MatchingTermVisitor mtv)
		throws IOException
	{
		int prefixLength;
		Terms terms;
		Matcher matcher;
		prefixLength = prefix.length();
		terms = MultiFields.getTerms(reader, fieldName);
		if (terms == null)
			break MISSING_BLOCK_LABEL_185;
		matcher = pattern.matcher("");
		TermsEnum termsEnum = terms.iterator(null);
		org.apache.lucene.index.TermsEnum.SeekStatus status = termsEnum.seekCeil(prefixRef);
		BytesRef text;
		if (status == org.apache.lucene.index.TermsEnum.SeekStatus.FOUND)
			text = prefixRef;
		else
		if (status == org.apache.lucene.index.TermsEnum.SeekStatus.NOT_FOUND)
			text = termsEnum.term();
		else
			text = null;
		for (; text != null && text != null && StringHelper.startsWith(text, prefixRef); text = termsEnum.next())
		{
			String textString = text.utf8ToString();
			matcher.reset(textString.substring(prefixLength));
			if (matcher.matches())
				mtv.visitMatchingTerm(new Term(fieldName, textString));
		}

		matcher.reset();
		break MISSING_BLOCK_LABEL_185;
		Exception exception;
		exception;
		matcher.reset();
		throw exception;
	}
}
