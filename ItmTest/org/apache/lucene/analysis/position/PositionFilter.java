// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PositionFilter.java

package org.apache.lucene.analysis.position;

import java.io.IOException;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;

public final class PositionFilter extends TokenFilter
{

	private final int positionIncrement;
	private boolean firstTokenPositioned;
	private PositionIncrementAttribute posIncrAtt;

	public PositionFilter(TokenStream input)
	{
		this(input, 0);
	}

	public PositionFilter(TokenStream input, int positionIncrement)
	{
		super(input);
		firstTokenPositioned = false;
		posIncrAtt = (PositionIncrementAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/PositionIncrementAttribute);
		if (positionIncrement < 0)
		{
			throw new IllegalArgumentException("positionIncrement may not be negative");
		} else
		{
			this.positionIncrement = positionIncrement;
			return;
		}
	}

	public final boolean incrementToken()
		throws IOException
	{
		if (input.incrementToken())
		{
			if (firstTokenPositioned)
				posIncrAtt.setPositionIncrement(positionIncrement);
			else
				firstTokenPositioned = true;
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
		firstTokenPositioned = false;
	}
}
