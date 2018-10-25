// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ChineseFilter.java

package org.apache.lucene.analysis.cn;

import java.io.IOException;
import java.util.Arrays;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.util.Version;

/**
 * @deprecated Class ChineseFilter is deprecated
 */

public final class ChineseFilter extends TokenFilter
{

	public static final String STOP_WORDS[] = {
		"and", "are", "as", "at", "be", "but", "by", "for", "if", "in", 
		"into", "is", "it", "no", "not", "of", "on", "or", "such", "that", 
		"the", "their", "then", "there", "these", "they", "this", "to", "was", "will", 
		"with"
	};
	private CharArraySet stopTable;
	private CharTermAttribute termAtt;

	public ChineseFilter(TokenStream in)
	{
		super(in);
		termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		stopTable = new CharArraySet(Version.LUCENE_CURRENT, Arrays.asList(STOP_WORDS), false);
	}

	public boolean incrementToken()
		throws IOException
	{
_L7:
		char text[];
		int termLength;
		do
		{
			if (!input.incrementToken())
				break MISSING_BLOCK_LABEL_96;
			text = termAtt.buffer();
			termLength = termAtt.length();
		} while (stopTable.contains(text, 0, termLength));
		Character.getType(text[0]);
		JVM INSTR tableswitch 1 5: default 93
	//	               1 84
	//	               2 84
	//	               3 93
	//	               4 93
	//	               5 91;
		   goto _L1 _L2 _L2 _L1 _L1 _L3
_L3:
		break; /* Loop/switch isn't completed */
_L1:
		break; /* Loop/switch isn't completed */
_L2:
		if (termLength <= 1) goto _L5; else goto _L4
_L5:
		if (true) goto _L7; else goto _L6
_L4:
		return true;
_L6:
		return true;
		return false;
	}

}
