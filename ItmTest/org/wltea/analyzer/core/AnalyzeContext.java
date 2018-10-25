// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AnalyzeContext.java

package org.wltea.analyzer.core;

import java.io.IOException;
import java.io.Reader;
import java.util.*;
import org.wltea.analyzer.cfg.Configuration;
import org.wltea.analyzer.dic.Dictionary;

// Referenced classes of package org.wltea.analyzer.core:
//			QuickSortSet, CharacterUtil, LexemePath, Lexeme

class AnalyzeContext
{

	private static final int BUFF_SIZE = 4096;
	private static final int BUFF_EXHAUST_CRITICAL = 100;
	private char segmentBuff[];
	private int charTypes[];
	private int buffOffset;
	private int cursor;
	private int available;
	private Set buffLocker;
	private QuickSortSet orgLexemes;
	private Map pathMap;
	private LinkedList results;
	private Configuration cfg;

	public AnalyzeContext(Configuration cfg)
	{
		this.cfg = cfg;
		segmentBuff = new char[4096];
		charTypes = new int[4096];
		buffLocker = new HashSet();
		orgLexemes = new QuickSortSet();
		pathMap = new HashMap();
		results = new LinkedList();
	}

	int getCursor()
	{
		return cursor;
	}

	char[] getSegmentBuff()
	{
		return segmentBuff;
	}

	char getCurrentChar()
	{
		return segmentBuff[cursor];
	}

	int getCurrentCharType()
	{
		return charTypes[cursor];
	}

	int getBufferOffset()
	{
		return buffOffset;
	}

	int fillBuffer(Reader reader)
		throws IOException
	{
		int readCount = 0;
		if (buffOffset == 0)
		{
			readCount = reader.read(segmentBuff);
		} else
		{
			int offset = available - cursor;
			if (offset > 0)
			{
				System.arraycopy(segmentBuff, cursor, segmentBuff, 0, offset);
				readCount = offset;
			}
			readCount += reader.read(segmentBuff, offset, 4096 - offset);
		}
		available = readCount;
		cursor = 0;
		return readCount;
	}

	void initCursor()
	{
		cursor = 0;
		segmentBuff[cursor] = CharacterUtil.regularize(segmentBuff[cursor]);
		charTypes[cursor] = CharacterUtil.identifyCharType(segmentBuff[cursor]);
	}

	boolean moveCursor()
	{
		if (cursor < available - 1)
		{
			cursor++;
			segmentBuff[cursor] = CharacterUtil.regularize(segmentBuff[cursor]);
			charTypes[cursor] = CharacterUtil.identifyCharType(segmentBuff[cursor]);
			return true;
		} else
		{
			return false;
		}
	}

	void lockBuffer(String segmenterName)
	{
		buffLocker.add(segmenterName);
	}

	void unlockBuffer(String segmenterName)
	{
		buffLocker.remove(segmenterName);
	}

	boolean isBufferLocked()
	{
		return buffLocker.size() > 0;
	}

	boolean isBufferConsumed()
	{
		return cursor == available - 1;
	}

	boolean needRefillBuffer()
	{
		return available == 4096 && cursor < available - 1 && cursor > available - 100 && !isBufferLocked();
	}

	void markBufferOffset()
	{
		buffOffset += cursor;
	}

	void addLexeme(Lexeme lexeme)
	{
		orgLexemes.addLexeme(lexeme);
	}

	void addLexemePath(LexemePath path)
	{
		if (path != null)
			pathMap.put(Integer.valueOf(path.getPathBegin()), path);
	}

	QuickSortSet getOrgLexemes()
	{
		return orgLexemes;
	}

	void outputToResult()
	{
		for (int index = 0; index <= cursor;)
			if (charTypes[index] == 0)
			{
				index++;
			} else
			{
				LexemePath path = (LexemePath)pathMap.get(Integer.valueOf(index));
				if (path != null)
				{
					for (Lexeme l = path.pollFirst(); l != null;)
					{
						results.add(l);
						index = l.getBegin() + l.getLength();
						l = path.pollFirst();
						if (l != null)
							for (; index < l.getBegin(); index++)
								outputSingleCJK(index);

					}

				} else
				{
					outputSingleCJK(index);
					index++;
				}
			}

		pathMap.clear();
	}

	private void outputSingleCJK(int index)
	{
		if (4 == charTypes[index])
		{
			Lexeme singleCharLexeme = new Lexeme(buffOffset, index, 1, 64);
			results.add(singleCharLexeme);
		} else
		if (8 == charTypes[index])
		{
			Lexeme singleCharLexeme = new Lexeme(buffOffset, index, 1, 8);
			results.add(singleCharLexeme);
		}
	}

	Lexeme getNextLexeme()
	{
		Lexeme result = (Lexeme)results.pollFirst();
		while (result != null) 
		{
			compound(result);
			if (Dictionary.getSingleton().isStopWord(segmentBuff, result.getBegin(), result.getLength()))
			{
				result = (Lexeme)results.pollFirst();
				continue;
			}
			result.setLexemeText(String.valueOf(segmentBuff, result.getBegin(), result.getLength()));
			break;
		}
		return result;
	}

	void reset()
	{
		buffLocker.clear();
		orgLexemes = new QuickSortSet();
		available = 0;
		buffOffset = 0;
		charTypes = new int[4096];
		cursor = 0;
		results.clear();
		segmentBuff = new char[4096];
		pathMap.clear();
	}

	private void compound(Lexeme result)
	{
		if (!cfg.useSmart())
			return;
		if (!results.isEmpty())
		{
			if (2 == result.getLexemeType())
			{
				Lexeme nextLexeme = (Lexeme)results.peekFirst();
				boolean appendOk = false;
				if (16 == nextLexeme.getLexemeType())
					appendOk = result.append(nextLexeme, 16);
				else
				if (32 == nextLexeme.getLexemeType())
					appendOk = result.append(nextLexeme, 48);
				if (appendOk)
					results.pollFirst();
			}
			if (16 == result.getLexemeType() && !results.isEmpty())
			{
				Lexeme nextLexeme = (Lexeme)results.peekFirst();
				boolean appendOk = false;
				if (32 == nextLexeme.getLexemeType())
					appendOk = result.append(nextLexeme, 48);
				if (appendOk)
					results.pollFirst();
			}
		}
	}
}
