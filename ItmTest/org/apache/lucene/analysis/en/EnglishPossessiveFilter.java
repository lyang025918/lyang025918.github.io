// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EnglishPossessiveFilter.java

package org.apache.lucene.analysis.en;

import java.io.IOException;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

public final class EnglishPossessiveFilter extends TokenFilter
{

	private final CharTermAttribute termAtt;
	private Version matchVersion;

	/**
	 * @deprecated Method EnglishPossessiveFilter is deprecated
	 */

	public EnglishPossessiveFilter(TokenStream input)
	{
		this(Version.LUCENE_35, input);
	}

	public EnglishPossessiveFilter(Version version, TokenStream input)
	{
		super(input);
		termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		matchVersion = version;
	}

	public boolean incrementToken()
		throws IOException
	{
		if (!input.incrementToken())
			return false;
		char buffer[] = termAtt.buffer();
		int bufferLength = termAtt.length();
		if (bufferLength >= 2 && (buffer[bufferLength - 2] == '\'' || matchVersion.onOrAfter(Version.LUCENE_36) && (buffer[bufferLength - 2] == '\u2019' || buffer[bufferLength - 2] == '\uFF07')) && (buffer[bufferLength - 1] == 's' || buffer[bufferLength - 1] == 'S'))
			termAtt.setLength(bufferLength - 2);
		return true;
	}
}
