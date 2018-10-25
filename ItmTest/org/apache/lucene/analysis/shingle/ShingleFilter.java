// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ShingleFilter.java

package org.apache.lucene.analysis.shingle;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.*;
import org.apache.lucene.util.AttributeSource;

public final class ShingleFilter extends TokenFilter
{
	private class InputWindowToken
	{

		final AttributeSource attSource;
		final CharTermAttribute termAtt;
		final OffsetAttribute offsetAtt;
		boolean isFiller;
		final ShingleFilter this$0;

		public InputWindowToken(AttributeSource attSource)
		{
			this$0 = ShingleFilter.this;
			super();
			isFiller = false;
			this.attSource = attSource;
			termAtt = (CharTermAttribute)attSource.getAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
			offsetAtt = (OffsetAttribute)attSource.getAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
		}
	}

	private class CircularSequence
	{

		private int value;
		private int previousValue;
		private int minValue;
		final ShingleFilter this$0;

		public int getValue()
		{
			return value;
		}

		public void advance()
		{
			previousValue = value;
			if (value == 1)
				value = minShingleSize;
			else
			if (value == maxShingleSize)
				reset();
			else
				value++;
		}

		public void reset()
		{
			previousValue = value = minValue;
		}

		public boolean atMinValue()
		{
			return value == minValue;
		}

		public int getPreviousValue()
		{
			return previousValue;
		}



		public CircularSequence()
		{
			this$0 = ShingleFilter.this;
			super();
			minValue = outputUnigrams ? 1 : minShingleSize;
			reset();
		}
	}


	public static final char FILLER_TOKEN[] = {
		'_'
	};
	public static final int DEFAULT_MAX_SHINGLE_SIZE = 2;
	public static final int DEFAULT_MIN_SHINGLE_SIZE = 2;
	public static final String DEFAULT_TOKEN_TYPE = "shingle";
	public static final String TOKEN_SEPARATOR = " ";
	private LinkedList inputWindow;
	private CircularSequence gramSize;
	private StringBuilder gramBuilder;
	private String tokenType;
	private String tokenSeparator;
	private boolean outputUnigrams;
	private boolean outputUnigramsIfNoShingles;
	private int maxShingleSize;
	private int minShingleSize;
	private int numFillerTokensToInsert;
	private AttributeSource nextInputStreamToken;
	private boolean isNextInputStreamToken;
	private boolean isOutputHere;
	boolean noShingleOutput;
	private final CharTermAttribute termAtt;
	private final OffsetAttribute offsetAtt;
	private final PositionIncrementAttribute posIncrAtt;
	private final PositionLengthAttribute posLenAtt;
	private final TypeAttribute typeAtt;
	private boolean exhausted;

	public ShingleFilter(TokenStream input, int minShingleSize, int maxShingleSize)
	{
		super(input);
		inputWindow = new LinkedList();
		gramBuilder = new StringBuilder();
		tokenType = "shingle";
		tokenSeparator = " ";
		outputUnigrams = true;
		outputUnigramsIfNoShingles = false;
		isNextInputStreamToken = false;
		isOutputHere = false;
		noShingleOutput = true;
		termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		offsetAtt = (OffsetAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
		posIncrAtt = (PositionIncrementAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/PositionIncrementAttribute);
		posLenAtt = (PositionLengthAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/PositionLengthAttribute);
		typeAtt = (TypeAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/TypeAttribute);
		setMaxShingleSize(maxShingleSize);
		setMinShingleSize(minShingleSize);
	}

	public ShingleFilter(TokenStream input, int maxShingleSize)
	{
		this(input, 2, maxShingleSize);
	}

	public ShingleFilter(TokenStream input)
	{
		this(input, 2, 2);
	}

	public ShingleFilter(TokenStream input, String tokenType)
	{
		this(input, 2, 2);
		setTokenType(tokenType);
	}

	public void setTokenType(String tokenType)
	{
		this.tokenType = tokenType;
	}

	public void setOutputUnigrams(boolean outputUnigrams)
	{
		this.outputUnigrams = outputUnigrams;
		gramSize = new CircularSequence();
	}

	public void setOutputUnigramsIfNoShingles(boolean outputUnigramsIfNoShingles)
	{
		this.outputUnigramsIfNoShingles = outputUnigramsIfNoShingles;
	}

	public void setMaxShingleSize(int maxShingleSize)
	{
		if (maxShingleSize < 2)
		{
			throw new IllegalArgumentException("Max shingle size must be >= 2");
		} else
		{
			this.maxShingleSize = maxShingleSize;
			return;
		}
	}

	public void setMinShingleSize(int minShingleSize)
	{
		if (minShingleSize < 2)
			throw new IllegalArgumentException("Min shingle size must be >= 2");
		if (minShingleSize > maxShingleSize)
		{
			throw new IllegalArgumentException("Min shingle size must be <= max shingle size");
		} else
		{
			this.minShingleSize = minShingleSize;
			gramSize = new CircularSequence();
			return;
		}
	}

	public void setTokenSeparator(String tokenSeparator)
	{
		this.tokenSeparator = null != tokenSeparator ? tokenSeparator : "";
	}

	public final boolean incrementToken()
		throws IOException
	{
		boolean tokenAvailable = false;
		int builtGramSize = 0;
		if (gramSize.atMinValue() || inputWindow.size() < gramSize.getValue())
		{
			shiftInputWindow();
			gramBuilder.setLength(0);
		} else
		{
			builtGramSize = gramSize.getPreviousValue();
		}
		if (inputWindow.size() >= gramSize.getValue())
		{
			boolean isAllFiller = true;
			InputWindowToken nextToken = null;
			Iterator iter = inputWindow.iterator();
			for (int gramNum = 1; iter.hasNext() && builtGramSize < gramSize.getValue(); gramNum++)
			{
				nextToken = (InputWindowToken)iter.next();
				if (builtGramSize < gramNum)
				{
					if (builtGramSize > 0)
						gramBuilder.append(tokenSeparator);
					gramBuilder.append(nextToken.termAtt.buffer(), 0, nextToken.termAtt.length());
					builtGramSize++;
				}
				if (isAllFiller && nextToken.isFiller)
				{
					if (gramNum == gramSize.getValue())
						gramSize.advance();
				} else
				{
					isAllFiller = false;
				}
			}

			if (!isAllFiller && builtGramSize == gramSize.getValue())
			{
				((InputWindowToken)inputWindow.getFirst()).attSource.copyTo(this);
				posIncrAtt.setPositionIncrement(isOutputHere ? 0 : 1);
				termAtt.setEmpty().append(gramBuilder);
				if (gramSize.getValue() > 1)
				{
					typeAtt.setType(tokenType);
					noShingleOutput = false;
				}
				offsetAtt.setOffset(offsetAtt.startOffset(), nextToken.offsetAtt.endOffset());
				posLenAtt.setPositionLength(builtGramSize);
				isOutputHere = true;
				gramSize.advance();
				tokenAvailable = true;
			}
		}
		return tokenAvailable;
	}

	private InputWindowToken getNextToken(InputWindowToken target)
		throws IOException
	{
		InputWindowToken newTarget = target;
		if (numFillerTokensToInsert > 0)
		{
			if (null == target)
				newTarget = new InputWindowToken(nextInputStreamToken.cloneAttributes());
			else
				nextInputStreamToken.copyTo(target.attSource);
			newTarget.offsetAtt.setOffset(newTarget.offsetAtt.startOffset(), newTarget.offsetAtt.startOffset());
			newTarget.termAtt.copyBuffer(FILLER_TOKEN, 0, FILLER_TOKEN.length);
			newTarget.isFiller = true;
			numFillerTokensToInsert--;
		} else
		if (isNextInputStreamToken)
		{
			if (null == target)
				newTarget = new InputWindowToken(nextInputStreamToken.cloneAttributes());
			else
				nextInputStreamToken.copyTo(target.attSource);
			isNextInputStreamToken = false;
			newTarget.isFiller = false;
		} else
		if (!exhausted && input.incrementToken())
		{
			if (null == target)
				newTarget = new InputWindowToken(cloneAttributes());
			else
				copyTo(target.attSource);
			if (posIncrAtt.getPositionIncrement() > 1)
			{
				numFillerTokensToInsert = Math.min(posIncrAtt.getPositionIncrement() - 1, maxShingleSize - 1);
				if (null == nextInputStreamToken)
					nextInputStreamToken = cloneAttributes();
				else
					copyTo(nextInputStreamToken);
				isNextInputStreamToken = true;
				newTarget.offsetAtt.setOffset(offsetAtt.startOffset(), offsetAtt.startOffset());
				newTarget.termAtt.copyBuffer(FILLER_TOKEN, 0, FILLER_TOKEN.length);
				newTarget.isFiller = true;
				numFillerTokensToInsert--;
			} else
			{
				newTarget.isFiller = false;
			}
		} else
		{
			newTarget = null;
			exhausted = true;
		}
		return newTarget;
	}

	private void shiftInputWindow()
		throws IOException
	{
		InputWindowToken firstToken = null;
		if (inputWindow.size() > 0)
			firstToken = (InputWindowToken)inputWindow.removeFirst();
		do
		{
			if (inputWindow.size() >= maxShingleSize)
				break;
			if (null != firstToken)
			{
				if (null == getNextToken(firstToken))
					break;
				inputWindow.add(firstToken);
				firstToken = null;
				continue;
			}
			InputWindowToken nextToken = getNextToken(null);
			if (null == nextToken)
				break;
			inputWindow.add(nextToken);
		} while (true);
		if (outputUnigramsIfNoShingles && noShingleOutput && gramSize.minValue > 1 && inputWindow.size() < minShingleSize)
			gramSize.minValue = 1;
		gramSize.reset();
		isOutputHere = false;
	}

	public void reset()
		throws IOException
	{
		super.reset();
		gramSize.reset();
		inputWindow.clear();
		nextInputStreamToken = null;
		isNextInputStreamToken = false;
		numFillerTokensToInsert = 0;
		isOutputHere = false;
		noShingleOutput = true;
		exhausted = false;
		if (outputUnigramsIfNoShingles && !outputUnigrams)
			gramSize.minValue = minShingleSize;
	}




}
