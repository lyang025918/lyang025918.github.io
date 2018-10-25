// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ClassicTokenizer.java

package org.apache.lucene.analysis.standard;

import java.io.IOException;
import java.io.Reader;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.*;
import org.apache.lucene.util.AttributeSource;
import org.apache.lucene.util.Version;

// Referenced classes of package org.apache.lucene.analysis.standard:
//			ClassicTokenizerImpl, StandardTokenizerInterface

public final class ClassicTokenizer extends Tokenizer
{

	private StandardTokenizerInterface scanner;
	public static final int ALPHANUM = 0;
	public static final int APOSTROPHE = 1;
	public static final int ACRONYM = 2;
	public static final int COMPANY = 3;
	public static final int EMAIL = 4;
	public static final int HOST = 5;
	public static final int NUM = 6;
	public static final int CJ = 7;
	public static final int ACRONYM_DEP = 8;
	public static final String TOKEN_TYPES[] = {
		"<ALPHANUM>", "<APOSTROPHE>", "<ACRONYM>", "<COMPANY>", "<EMAIL>", "<HOST>", "<NUM>", "<CJ>", "<ACRONYM_DEP>"
	};
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

	public ClassicTokenizer(Version matchVersion, Reader input)
	{
		super(input);
		maxTokenLength = 255;
		termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		offsetAtt = (OffsetAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
		posIncrAtt = (PositionIncrementAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/PositionIncrementAttribute);
		typeAtt = (TypeAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/TypeAttribute);
		init(matchVersion);
	}

	public ClassicTokenizer(Version matchVersion, AttributeSource source, Reader input)
	{
		super(source, input);
		maxTokenLength = 255;
		termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		offsetAtt = (OffsetAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
		posIncrAtt = (PositionIncrementAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/PositionIncrementAttribute);
		typeAtt = (TypeAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/TypeAttribute);
		init(matchVersion);
	}

	public ClassicTokenizer(Version matchVersion, org.apache.lucene.util.AttributeSource.AttributeFactory factory, Reader input)
	{
		super(factory, input);
		maxTokenLength = 255;
		termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		offsetAtt = (OffsetAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
		posIncrAtt = (PositionIncrementAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/PositionIncrementAttribute);
		typeAtt = (TypeAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/TypeAttribute);
		init(matchVersion);
	}

	private void init(Version matchVersion)
	{
		scanner = new ClassicTokenizerImpl(input);
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
				if (tokenType == 8)
				{
					typeAtt.setType(TOKEN_TYPES[5]);
					termAtt.setLength(termAtt.length() - 1);
				} else
				{
					typeAtt.setType(TOKEN_TYPES[tokenType]);
				}
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

}
