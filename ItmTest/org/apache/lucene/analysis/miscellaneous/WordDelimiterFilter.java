// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   WordDelimiterFilter.java

package org.apache.lucene.analysis.miscellaneous;

import java.io.IOException;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.*;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.util.ArrayUtil;

// Referenced classes of package org.apache.lucene.analysis.miscellaneous:
//			WordDelimiterIterator

public final class WordDelimiterFilter extends TokenFilter
{
	final class WordDelimiterConcatenation
	{

		final StringBuilder buffer = new StringBuilder();
		int startOffset;
		int endOffset;
		int type;
		int subwordCount;
		final WordDelimiterFilter this$0;

		void append(char text[], int offset, int length)
		{
			buffer.append(text, offset, length);
			subwordCount++;
		}

		void write()
		{
			clearAttributes();
			if (termAttribute.length() < buffer.length())
				termAttribute.resizeBuffer(buffer.length());
			char termbuffer[] = termAttribute.buffer();
			buffer.getChars(0, buffer.length(), termbuffer, 0);
			termAttribute.setLength(buffer.length());
			if (hasIllegalOffsets)
				offsetAttribute.setOffset(savedStartOffset, savedEndOffset);
			else
				offsetAttribute.setOffset(startOffset, endOffset);
			posIncAttribute.setPositionIncrement(position(true));
			typeAttribute.setType(savedType);
			accumPosInc = 0;
		}

		boolean isEmpty()
		{
			return buffer.length() == 0;
		}

		void clear()
		{
			buffer.setLength(0);
			startOffset = endOffset = type = subwordCount = 0;
		}

		void writeAndClear()
		{
			write();
			clear();
		}

		WordDelimiterConcatenation()
		{
			this$0 = WordDelimiterFilter.this;
			super();
		}
	}


	public static final int LOWER = 1;
	public static final int UPPER = 2;
	public static final int DIGIT = 4;
	public static final int SUBWORD_DELIM = 8;
	public static final int ALPHA = 3;
	public static final int ALPHANUM = 7;
	public static final int GENERATE_WORD_PARTS = 1;
	public static final int GENERATE_NUMBER_PARTS = 2;
	public static final int CATENATE_WORDS = 4;
	public static final int CATENATE_NUMBERS = 8;
	public static final int CATENATE_ALL = 16;
	public static final int PRESERVE_ORIGINAL = 32;
	public static final int SPLIT_ON_CASE_CHANGE = 64;
	public static final int SPLIT_ON_NUMERICS = 128;
	public static final int STEM_ENGLISH_POSSESSIVE = 256;
	final CharArraySet protWords;
	private final int flags;
	private final CharTermAttribute termAttribute;
	private final OffsetAttribute offsetAttribute;
	private final PositionIncrementAttribute posIncAttribute;
	private final TypeAttribute typeAttribute;
	private final WordDelimiterIterator iterator;
	private final WordDelimiterConcatenation concat;
	private int lastConcatCount;
	private final WordDelimiterConcatenation concatAll;
	private int accumPosInc;
	private char savedBuffer[];
	private int savedStartOffset;
	private int savedEndOffset;
	private String savedType;
	private boolean hasSavedState;
	private boolean hasIllegalOffsets;
	private boolean hasOutputToken;
	private boolean hasOutputFollowingOriginal;

	public WordDelimiterFilter(TokenStream in, byte charTypeTable[], int configurationFlags, CharArraySet protWords)
	{
		super(in);
		termAttribute = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		offsetAttribute = (OffsetAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
		posIncAttribute = (PositionIncrementAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/PositionIncrementAttribute);
		typeAttribute = (TypeAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/TypeAttribute);
		concat = new WordDelimiterConcatenation();
		lastConcatCount = 0;
		concatAll = new WordDelimiterConcatenation();
		accumPosInc = 0;
		savedBuffer = new char[1024];
		hasSavedState = false;
		hasIllegalOffsets = false;
		hasOutputToken = false;
		hasOutputFollowingOriginal = false;
		flags = configurationFlags;
		this.protWords = protWords;
		iterator = new WordDelimiterIterator(charTypeTable, has(64), has(128), has(256));
	}

	public WordDelimiterFilter(TokenStream in, int configurationFlags, CharArraySet protWords)
	{
		this(in, WordDelimiterIterator.DEFAULT_WORD_DELIM_TABLE, configurationFlags, protWords);
	}

	public boolean incrementToken()
		throws IOException
	{
		do
		{
			if (!hasSavedState)
			{
				if (!input.incrementToken())
					return false;
				int termLength = termAttribute.length();
				char termBuffer[] = termAttribute.buffer();
				accumPosInc += posIncAttribute.getPositionIncrement();
				iterator.setText(termBuffer, termLength);
				iterator.next();
				if (iterator.current == 0 && iterator.end == termLength || protWords != null && protWords.contains(termBuffer, 0, termLength))
				{
					posIncAttribute.setPositionIncrement(accumPosInc);
					accumPosInc = 0;
					return true;
				}
				if (iterator.end == -1 && !has(32))
				{
					if (posIncAttribute.getPositionIncrement() == 1)
						accumPosInc--;
					continue;
				}
				saveState();
				hasOutputToken = false;
				hasOutputFollowingOriginal = !has(32);
				lastConcatCount = 0;
				if (has(32))
				{
					posIncAttribute.setPositionIncrement(accumPosInc);
					accumPosInc = 0;
					return true;
				}
			}
			if (iterator.end == -1)
			{
				if (!concat.isEmpty() && flushConcatenation(concat))
					return true;
				if (!concatAll.isEmpty())
				{
					if (concatAll.subwordCount > lastConcatCount)
					{
						concatAll.writeAndClear();
						return true;
					}
					concatAll.clear();
				}
				hasSavedState = false;
			} else
			{
				if (iterator.isSingleWord())
				{
					generatePart(true);
					iterator.next();
					return true;
				}
				int wordType = iterator.type();
				if (!concat.isEmpty() && (concat.type & wordType) == 0)
				{
					if (flushConcatenation(concat))
					{
						hasOutputToken = false;
						return true;
					}
					hasOutputToken = false;
				}
				if (shouldConcatenate(wordType))
				{
					if (concat.isEmpty())
						concat.type = wordType;
					concatenate(concat);
				}
				if (has(16))
					concatenate(concatAll);
				if (shouldGenerateParts(wordType))
				{
					generatePart(false);
					iterator.next();
					return true;
				}
				iterator.next();
			}
		} while (true);
	}

	public void reset()
		throws IOException
	{
		super.reset();
		hasSavedState = false;
		concat.clear();
		concatAll.clear();
		accumPosInc = 0;
	}

	private void saveState()
	{
		savedStartOffset = offsetAttribute.startOffset();
		savedEndOffset = offsetAttribute.endOffset();
		hasIllegalOffsets = savedEndOffset - savedStartOffset != termAttribute.length();
		savedType = typeAttribute.type();
		if (savedBuffer.length < termAttribute.length())
			savedBuffer = new char[ArrayUtil.oversize(termAttribute.length(), 2)];
		System.arraycopy(termAttribute.buffer(), 0, savedBuffer, 0, termAttribute.length());
		iterator.text = savedBuffer;
		hasSavedState = true;
	}

	private boolean flushConcatenation(WordDelimiterConcatenation concatenation)
	{
		lastConcatCount = concatenation.subwordCount;
		if (concatenation.subwordCount != 1 || !shouldGenerateParts(concatenation.type))
		{
			concatenation.writeAndClear();
			return true;
		} else
		{
			concatenation.clear();
			return false;
		}
	}

	private boolean shouldConcatenate(int wordType)
	{
		return has(4) && isAlpha(wordType) || has(8) && isDigit(wordType);
	}

	private boolean shouldGenerateParts(int wordType)
	{
		return has(1) && isAlpha(wordType) || has(2) && isDigit(wordType);
	}

	private void concatenate(WordDelimiterConcatenation concatenation)
	{
		if (concatenation.isEmpty())
			concatenation.startOffset = savedStartOffset + iterator.current;
		concatenation.append(savedBuffer, iterator.current, iterator.end - iterator.current);
		concatenation.endOffset = savedStartOffset + iterator.end;
	}

	private void generatePart(boolean isSingleWord)
	{
		clearAttributes();
		termAttribute.copyBuffer(savedBuffer, iterator.current, iterator.end - iterator.current);
		int startOffset = savedStartOffset + iterator.current;
		int endOffset = savedStartOffset + iterator.end;
		if (hasIllegalOffsets)
		{
			if (isSingleWord && startOffset <= savedEndOffset)
				offsetAttribute.setOffset(startOffset, savedEndOffset);
			else
				offsetAttribute.setOffset(savedStartOffset, savedEndOffset);
		} else
		{
			offsetAttribute.setOffset(startOffset, endOffset);
		}
		posIncAttribute.setPositionIncrement(position(false));
		typeAttribute.setType(savedType);
	}

	private int position(boolean inject)
	{
		int posInc = accumPosInc;
		if (hasOutputToken)
		{
			accumPosInc = 0;
			return inject ? 0 : Math.max(1, posInc);
		}
		hasOutputToken = true;
		if (!hasOutputFollowingOriginal)
		{
			hasOutputFollowingOriginal = true;
			return 0;
		} else
		{
			accumPosInc = 0;
			return Math.max(1, posInc);
		}
	}

	static boolean isAlpha(int type)
	{
		return (type & 3) != 0;
	}

	static boolean isDigit(int type)
	{
		return (type & 4) != 0;
	}

	static boolean isSubwordDelim(int type)
	{
		return (type & 8) != 0;
	}

	static boolean isUpper(int type)
	{
		return (type & 2) != 0;
	}

	private boolean has(int flag)
	{
		return (flags & flag) != 0;
	}










}
