// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FilteringTokenFilter.java

package org.apache.lucene.analysis.util;

import java.io.IOException;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;

public abstract class FilteringTokenFilter extends TokenFilter
{

	private final PositionIncrementAttribute posIncrAtt = (PositionIncrementAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/PositionIncrementAttribute);
	private boolean enablePositionIncrements;
	private boolean first;

	public FilteringTokenFilter(boolean enablePositionIncrements, TokenStream input)
	{
		super(input);
		first = true;
		this.enablePositionIncrements = enablePositionIncrements;
	}

	protected abstract boolean accept()
		throws IOException;

	public final boolean incrementToken()
		throws IOException
	{
		if (enablePositionIncrements)
		{
			for (int skippedPositions = 0; input.incrementToken(); skippedPositions += posIncrAtt.getPositionIncrement())
				if (accept())
				{
					if (skippedPositions != 0)
						posIncrAtt.setPositionIncrement(posIncrAtt.getPositionIncrement() + skippedPositions);
					return true;
				}

		} else
		{
			while (input.incrementToken()) 
				if (accept())
				{
					if (first)
					{
						if (posIncrAtt.getPositionIncrement() == 0)
							posIncrAtt.setPositionIncrement(1);
						first = false;
					}
					return true;
				}
		}
		return false;
	}

	public void reset()
		throws IOException
	{
		super.reset();
		first = true;
	}

	public boolean getEnablePositionIncrements()
	{
		return enablePositionIncrements;
	}

	public void setEnablePositionIncrements(boolean enable)
	{
		enablePositionIncrements = enable;
	}
}
