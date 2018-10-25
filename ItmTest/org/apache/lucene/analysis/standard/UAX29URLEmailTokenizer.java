// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   UAX29URLEmailTokenizer.java

package org.apache.lucene.analysis.standard;

import java.io.IOException;
import java.io.Reader;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.standard.std31.UAX29URLEmailTokenizerImpl31;
import org.apache.lucene.analysis.standard.std34.UAX29URLEmailTokenizerImpl34;
import org.apache.lucene.analysis.standard.std36.UAX29URLEmailTokenizerImpl36;
import org.apache.lucene.analysis.tokenattributes.*;
import org.apache.lucene.util.AttributeSource;
import org.apache.lucene.util.Version;

// Referenced classes of package org.apache.lucene.analysis.standard:
//			UAX29URLEmailTokenizerImpl, StandardTokenizerInterface, StandardTokenizer

public final class UAX29URLEmailTokenizer extends Tokenizer
{

	private final StandardTokenizerInterface scanner;
	public static final int ALPHANUM = 0;
	public static final int NUM = 1;
	public static final int SOUTHEAST_ASIAN = 2;
	public static final int IDEOGRAPHIC = 3;
	public static final int HIRAGANA = 4;
	public static final int KATAKANA = 5;
	public static final int HANGUL = 6;
	public static final int URL = 7;
	public static final int EMAIL = 8;
	public static final String TOKEN_TYPES[];
	private int maxTokenLength;
	private final CharTermAttribute termAtt;
	private final OffsetAttribute offsetAtt;
	private final PositionIncrementAttribute posIncrAtt;
	private final TypeAttribute typeAtt;

	public void setMaxTokenLength(int length)
	{
		maxTokenLength = length;
	}

	public int getMaxTokenLength()
	{
		return maxTokenLength;
	}

	public UAX29URLEmailTokenizer(Version matchVersion, Reader input)
	{
		super(input);
		maxTokenLength = 255;
		termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		offsetAtt = (OffsetAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
		posIncrAtt = (PositionIncrementAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/PositionIncrementAttribute);
		typeAtt = (TypeAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/TypeAttribute);
		scanner = getScannerFor(matchVersion, input);
	}

	public UAX29URLEmailTokenizer(Version matchVersion, AttributeSource source, Reader input)
	{
		super(source, input);
		maxTokenLength = 255;
		termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		offsetAtt = (OffsetAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
		posIncrAtt = (PositionIncrementAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/PositionIncrementAttribute);
		typeAtt = (TypeAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/TypeAttribute);
		scanner = getScannerFor(matchVersion, input);
	}

	public UAX29URLEmailTokenizer(Version matchVersion, org.apache.lucene.util.AttributeSource.AttributeFactory factory, Reader input)
	{
		super(factory, input);
		maxTokenLength = 255;
		termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		offsetAtt = (OffsetAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
		posIncrAtt = (PositionIncrementAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/PositionIncrementAttribute);
		typeAtt = (TypeAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/TypeAttribute);
		scanner = getScannerFor(matchVersion, input);
	}

	private static StandardTokenizerInterface getScannerFor(Version matchVersion, Reader input)
	{
		if (matchVersion.onOrAfter(Version.LUCENE_40))
			return new UAX29URLEmailTokenizerImpl(input);
		if (matchVersion.onOrAfter(Version.LUCENE_36))
			return new UAX29URLEmailTokenizerImpl36(input);
		if (matchVersion.onOrAfter(Version.LUCENE_34))
			return new UAX29URLEmailTokenizerImpl34(input);
		else
			return new UAX29URLEmailTokenizerImpl31(input);
	}

	public final boolean incrementToken()
		throws IOException
	{
		clearAttributes();
		int posIncr = 1;
		do
		{
			int tokenType = scanner.getNextToken();
			if (tokenType == -1)
				return false;
			if (scanner.yylength() <= maxTokenLength)
			{
				posIncrAtt.setPositionIncrement(posIncr);
				scanner.getText(termAtt);
				int start = scanner.yychar();
				offsetAtt.setOffset(correctOffset(start), correctOffset(start + termAtt.length()));
				typeAtt.setType(TOKEN_TYPES[tokenType]);
				return true;
			}
			posIncr++;
		} while (true);
	}

	public final void end()
	{
		int finalOffset = correctOffset(scanner.yychar() + scanner.yylength());
		offsetAtt.setOffset(finalOffset, finalOffset);
	}

	public void reset()
		throws IOException
	{
		scanner.yyreset(input);
	}

	static 
	{
		TOKEN_TYPES = (new String[] {
			StandardTokenizer.TOKEN_TYPES[0], StandardTokenizer.TOKEN_TYPES[6], StandardTokenizer.TOKEN_TYPES[9], StandardTokenizer.TOKEN_TYPES[10], StandardTokenizer.TOKEN_TYPES[11], StandardTokenizer.TOKEN_TYPES[12], StandardTokenizer.TOKEN_TYPES[13], "<URL>", "<EMAIL>"
		});
	}
}
