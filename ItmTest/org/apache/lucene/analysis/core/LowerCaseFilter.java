// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LowerCaseFilter.java

package org.apache.lucene.analysis.core;

import java.io.IOException;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.CharacterUtils;
import org.apache.lucene.util.Version;

public final class LowerCaseFilter extends TokenFilter
{

	private final CharacterUtils charUtils;
	private final CharTermAttribute termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);

	public LowerCaseFilter(Version matchVersion, TokenStream in)
	{
		super(in);
		charUtils = CharacterUtils.getInstance(matchVersion);
	}

	public final boolean incrementToken()
		throws IOException
	{
		if (input.incrementToken())
		{
			char buffer[] = termAtt.buffer();
			int length = termAtt.length();
			for (int i = 0; i < length; i += Character.toChars(Character.toLowerCase(charUtils.codePointAt(buffer, i)), buffer, i));
			return true;
		} else
		{
			return false;
		}
	}
}
