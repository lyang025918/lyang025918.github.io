// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LimitTokenCountFilter.java

package org.apache.lucene.analysis.miscellaneous;

import java.io.IOException;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;

public final class LimitTokenCountFilter extends TokenFilter
{

	private final int maxTokenCount;
	private int tokenCount;

	public LimitTokenCountFilter(TokenStream in, int maxTokenCount)
	{
		super(in);
		tokenCount = 0;
		this.maxTokenCount = maxTokenCount;
	}

	public boolean incrementToken()
		throws IOException
	{
		if (tokenCount < maxTokenCount && input.incrementToken())
		{
			tokenCount++;
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
		tokenCount = 0;
	}
}
