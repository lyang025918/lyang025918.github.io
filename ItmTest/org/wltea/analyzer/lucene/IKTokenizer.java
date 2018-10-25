// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IKTokenizer.java

package org.wltea.analyzer.lucene;

import java.io.IOException;
import java.io.Reader;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.*;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

public final class IKTokenizer extends Tokenizer
{

	private IKSegmenter _IKImplement;
	private final CharTermAttribute termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
	private final OffsetAttribute offsetAtt = (OffsetAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
	private final TypeAttribute typeAtt = (TypeAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/TypeAttribute);
	private int endPosition;

	public IKTokenizer(Reader in, boolean useSmart)
	{
		super(in);
		_IKImplement = new IKSegmenter(input, useSmart);
	}

	public boolean incrementToken()
		throws IOException
	{
		clearAttributes();
		Lexeme nextLexeme = _IKImplement.next();
		if (nextLexeme != null)
		{
			termAtt.append(nextLexeme.getLexemeText());
			termAtt.setLength(nextLexeme.getLength());
			offsetAtt.setOffset(nextLexeme.getBeginPosition(), nextLexeme.getEndPosition());
			endPosition = nextLexeme.getEndPosition();
			typeAtt.setType(nextLexeme.getLexemeTypeString());
			return true;
		} else
		{
			return false;
		}
	}

	public void reset()
		throws IOException
	{
		super.reset();
		_IKImplement.reset(input);
	}

	public final void end()
	{
		int finalOffset = correctOffset(endPosition);
		offsetAtt.setOffset(finalOffset, finalOffset);
	}
}
