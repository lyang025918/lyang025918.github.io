// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ClassicFilter.java

package org.apache.lucene.analysis.standard;

import java.io.IOException;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;

// Referenced classes of package org.apache.lucene.analysis.standard:
//			ClassicTokenizer

public class ClassicFilter extends TokenFilter
{

	private static final String APOSTROPHE_TYPE;
	private static final String ACRONYM_TYPE;
	private final TypeAttribute typeAtt = (TypeAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/TypeAttribute);
	private final CharTermAttribute termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);

	public ClassicFilter(TokenStream in)
	{
		super(in);
	}

	public final boolean incrementToken()
		throws IOException
	{
		if (!input.incrementToken())
			return false;
		char buffer[] = termAtt.buffer();
		int bufferLength = termAtt.length();
		String type = typeAtt.type();
		if (type == APOSTROPHE_TYPE && bufferLength >= 2 && buffer[bufferLength - 2] == '\'' && (buffer[bufferLength - 1] == 's' || buffer[bufferLength - 1] == 'S'))
			termAtt.setLength(bufferLength - 2);
		else
		if (type == ACRONYM_TYPE)
		{
			int upto = 0;
			for (int i = 0; i < bufferLength; i++)
			{
				char c = buffer[i];
				if (c != '.')
					buffer[upto++] = c;
			}

			termAtt.setLength(upto);
		}
		return true;
	}

	static 
	{
		APOSTROPHE_TYPE = ClassicTokenizer.TOKEN_TYPES[1];
		ACRONYM_TYPE = ClassicTokenizer.TOKEN_TYPES[2];
	}
}
