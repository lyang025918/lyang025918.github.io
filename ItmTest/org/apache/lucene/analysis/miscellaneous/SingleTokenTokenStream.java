// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SingleTokenTokenStream.java

package org.apache.lucene.analysis.miscellaneous;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.AttributeImpl;
import org.apache.lucene.util.AttributeSource;

public final class SingleTokenTokenStream extends TokenStream
{

	private boolean exhausted;
	private Token singleToken;
	private final AttributeImpl tokenAtt = (AttributeImpl)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
	static final boolean $assertionsDisabled = !org/apache/lucene/analysis/miscellaneous/SingleTokenTokenStream.desiredAssertionStatus();

	public SingleTokenTokenStream(Token token)
	{
		super(Token.TOKEN_ATTRIBUTE_FACTORY);
		exhausted = false;
		if (!$assertionsDisabled && token == null)
			throw new AssertionError();
		singleToken = token.clone();
		if (!$assertionsDisabled && !(tokenAtt instanceof Token))
			throw new AssertionError();
		else
			return;
	}

	public final boolean incrementToken()
	{
		if (exhausted)
		{
			return false;
		} else
		{
			clearAttributes();
			singleToken.copyTo(tokenAtt);
			exhausted = true;
			return true;
		}
	}

	public void reset()
	{
		exhausted = false;
	}

	public Token getToken()
	{
		return singleToken.clone();
	}

	public void setToken(Token token)
	{
		singleToken = token.clone();
	}

}
