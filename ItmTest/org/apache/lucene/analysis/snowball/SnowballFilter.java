// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SnowballFilter.java

package org.apache.lucene.analysis.snowball;

import java.io.IOException;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.KeywordAttribute;
import org.tartarus.snowball.SnowballProgram;

public final class SnowballFilter extends TokenFilter
{

	private final SnowballProgram stemmer;
	private final CharTermAttribute termAtt;
	private final KeywordAttribute keywordAttr;

	public SnowballFilter(TokenStream input, SnowballProgram stemmer)
	{
		super(input);
		termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		keywordAttr = (KeywordAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/KeywordAttribute);
		this.stemmer = stemmer;
	}

	public SnowballFilter(TokenStream in, String name)
	{
		super(in);
		termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		keywordAttr = (KeywordAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/KeywordAttribute);
		try
		{
			Class stemClass = Class.forName((new StringBuilder()).append("org.tartarus.snowball.ext.").append(name).append("Stemmer").toString()).asSubclass(org/tartarus/snowball/SnowballProgram);
			stemmer = (SnowballProgram)stemClass.newInstance();
		}
		catch (Exception e)
		{
			throw new IllegalArgumentException((new StringBuilder()).append("Invalid stemmer class specified: ").append(name).toString(), e);
		}
	}

	public final boolean incrementToken()
		throws IOException
	{
		if (input.incrementToken())
		{
			if (!keywordAttr.isKeyword())
			{
				char termBuffer[] = termAtt.buffer();
				int length = termAtt.length();
				stemmer.setCurrent(termBuffer, length);
				stemmer.stem();
				char finalTerm[] = stemmer.getCurrentBuffer();
				int newLength = stemmer.getCurrentBufferLength();
				if (finalTerm != termBuffer)
					termAtt.copyBuffer(finalTerm, 0, newLength);
				else
					termAtt.setLength(newLength);
			}
			return true;
		} else
		{
			return false;
		}
	}
}
