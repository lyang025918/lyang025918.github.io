// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PrefixAwareTokenFilter.java

package org.apache.lucene.analysis.miscellaneous;

import java.io.IOException;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.*;
import org.apache.lucene.util.BytesRef;

public class PrefixAwareTokenFilter extends TokenStream
{

	private TokenStream prefix;
	private TokenStream suffix;
	private CharTermAttribute termAtt;
	private PositionIncrementAttribute posIncrAtt;
	private PayloadAttribute payloadAtt;
	private OffsetAttribute offsetAtt;
	private TypeAttribute typeAtt;
	private FlagsAttribute flagsAtt;
	private CharTermAttribute p_termAtt;
	private PositionIncrementAttribute p_posIncrAtt;
	private PayloadAttribute p_payloadAtt;
	private OffsetAttribute p_offsetAtt;
	private TypeAttribute p_typeAtt;
	private FlagsAttribute p_flagsAtt;
	private Token previousPrefixToken;
	private Token reusableToken;
	private boolean prefixExhausted;

	public PrefixAwareTokenFilter(TokenStream prefix, TokenStream suffix)
	{
		super(suffix);
		previousPrefixToken = new Token();
		reusableToken = new Token();
		this.suffix = suffix;
		this.prefix = prefix;
		prefixExhausted = false;
		termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		posIncrAtt = (PositionIncrementAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/PositionIncrementAttribute);
		payloadAtt = (PayloadAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/PayloadAttribute);
		offsetAtt = (OffsetAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
		typeAtt = (TypeAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/TypeAttribute);
		flagsAtt = (FlagsAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/FlagsAttribute);
		p_termAtt = (CharTermAttribute)prefix.addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		p_posIncrAtt = (PositionIncrementAttribute)prefix.addAttribute(org/apache/lucene/analysis/tokenattributes/PositionIncrementAttribute);
		p_payloadAtt = (PayloadAttribute)prefix.addAttribute(org/apache/lucene/analysis/tokenattributes/PayloadAttribute);
		p_offsetAtt = (OffsetAttribute)prefix.addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
		p_typeAtt = (TypeAttribute)prefix.addAttribute(org/apache/lucene/analysis/tokenattributes/TypeAttribute);
		p_flagsAtt = (FlagsAttribute)prefix.addAttribute(org/apache/lucene/analysis/tokenattributes/FlagsAttribute);
	}

	public final boolean incrementToken()
		throws IOException
	{
		Token nextToken;
		if (!prefixExhausted)
		{
			nextToken = getNextPrefixInputToken(reusableToken);
			if (nextToken == null)
			{
				prefixExhausted = true;
			} else
			{
				previousPrefixToken.reinit(nextToken);
				BytesRef p = previousPrefixToken.getPayload();
				if (p != null)
					previousPrefixToken.setPayload(p.clone());
				setCurrentToken(nextToken);
				return true;
			}
		}
		nextToken = getNextSuffixInputToken(reusableToken);
		if (nextToken == null)
		{
			return false;
		} else
		{
			nextToken = updateSuffixToken(nextToken, previousPrefixToken);
			setCurrentToken(nextToken);
			return true;
		}
	}

	private void setCurrentToken(Token token)
	{
		if (token == null)
		{
			return;
		} else
		{
			clearAttributes();
			termAtt.copyBuffer(token.buffer(), 0, token.length());
			posIncrAtt.setPositionIncrement(token.getPositionIncrement());
			flagsAtt.setFlags(token.getFlags());
			offsetAtt.setOffset(token.startOffset(), token.endOffset());
			typeAtt.setType(token.type());
			payloadAtt.setPayload(token.getPayload());
			return;
		}
	}

	private Token getNextPrefixInputToken(Token token)
		throws IOException
	{
		if (!prefix.incrementToken())
		{
			return null;
		} else
		{
			token.copyBuffer(p_termAtt.buffer(), 0, p_termAtt.length());
			token.setPositionIncrement(p_posIncrAtt.getPositionIncrement());
			token.setFlags(p_flagsAtt.getFlags());
			token.setOffset(p_offsetAtt.startOffset(), p_offsetAtt.endOffset());
			token.setType(p_typeAtt.type());
			token.setPayload(p_payloadAtt.getPayload());
			return token;
		}
	}

	private Token getNextSuffixInputToken(Token token)
		throws IOException
	{
		if (!suffix.incrementToken())
		{
			return null;
		} else
		{
			token.copyBuffer(termAtt.buffer(), 0, termAtt.length());
			token.setPositionIncrement(posIncrAtt.getPositionIncrement());
			token.setFlags(flagsAtt.getFlags());
			token.setOffset(offsetAtt.startOffset(), offsetAtt.endOffset());
			token.setType(typeAtt.type());
			token.setPayload(payloadAtt.getPayload());
			return token;
		}
	}

	public Token updateSuffixToken(Token suffixToken, Token lastPrefixToken)
	{
		suffixToken.setOffset(lastPrefixToken.endOffset() + suffixToken.startOffset(), lastPrefixToken.endOffset() + suffixToken.endOffset());
		return suffixToken;
	}

	public void end()
		throws IOException
	{
		prefix.end();
		suffix.end();
	}

	public void close()
		throws IOException
	{
		prefix.close();
		suffix.close();
	}

	public void reset()
		throws IOException
	{
		super.reset();
		if (prefix != null)
		{
			prefixExhausted = false;
			prefix.reset();
		}
		if (suffix != null)
			suffix.reset();
	}

	public TokenStream getPrefix()
	{
		return prefix;
	}

	public void setPrefix(TokenStream prefix)
	{
		this.prefix = prefix;
	}

	public TokenStream getSuffix()
	{
		return suffix;
	}

	public void setSuffix(TokenStream suffix)
	{
		this.suffix = suffix;
	}
}
