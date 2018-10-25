// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LetterSegmenter.java

package org.wltea.analyzer.core;

import java.util.Arrays;

// Referenced classes of package org.wltea.analyzer.core:
//			ISegmenter, AnalyzeContext, Lexeme

class LetterSegmenter
	implements ISegmenter
{

	static final String SEGMENTER_NAME = "LETTER_SEGMENTER";
	private static final char Letter_Connector[] = {
		'#', '&', '+', '-', '.', '@', '_'
	};
	private static final char Num_Connector[] = {
		',', '.'
	};
	private int start;
	private int end;
	private int englishStart;
	private int englishEnd;
	private int arabicStart;
	private int arabicEnd;

	LetterSegmenter()
	{
		Arrays.sort(Letter_Connector);
		Arrays.sort(Num_Connector);
		start = -1;
		end = -1;
		englishStart = -1;
		englishEnd = -1;
		arabicStart = -1;
		arabicEnd = -1;
	}

	public void analyze(AnalyzeContext context)
	{
		boolean bufferLockFlag = false;
		bufferLockFlag = processEnglishLetter(context) || bufferLockFlag;
		bufferLockFlag = processArabicLetter(context) || bufferLockFlag;
		bufferLockFlag = processMixLetter(context) || bufferLockFlag;
		if (bufferLockFlag)
			context.lockBuffer("LETTER_SEGMENTER");
		else
			context.unlockBuffer("LETTER_SEGMENTER");
	}

	public void reset()
	{
		start = -1;
		end = -1;
		englishStart = -1;
		englishEnd = -1;
		arabicStart = -1;
		arabicEnd = -1;
	}

	private boolean processMixLetter(AnalyzeContext context)
	{
		boolean needLock = false;
		if (start == -1)
		{
			if (1 == context.getCurrentCharType() || 2 == context.getCurrentCharType())
			{
				start = context.getCursor();
				end = start;
			}
		} else
		if (1 == context.getCurrentCharType() || 2 == context.getCurrentCharType())
			end = context.getCursor();
		else
		if (context.getCurrentCharType() == 0 && isLetterConnector(context.getCurrentChar()))
		{
			end = context.getCursor();
		} else
		{
			Lexeme newLexeme = new Lexeme(context.getBufferOffset(), start, (end - start) + 1, 3);
			context.addLexeme(newLexeme);
			start = -1;
			end = -1;
		}
		if (context.isBufferConsumed() && start != -1 && end != -1)
		{
			Lexeme newLexeme = new Lexeme(context.getBufferOffset(), start, (end - start) + 1, 3);
			context.addLexeme(newLexeme);
			start = -1;
			end = -1;
		}
		if (start == -1 && end == -1)
			needLock = false;
		else
			needLock = true;
		return needLock;
	}

	private boolean processEnglishLetter(AnalyzeContext context)
	{
		boolean needLock = false;
		if (englishStart == -1)
		{
			if (2 == context.getCurrentCharType())
			{
				englishStart = context.getCursor();
				englishEnd = englishStart;
			}
		} else
		if (2 == context.getCurrentCharType())
		{
			englishEnd = context.getCursor();
		} else
		{
			Lexeme newLexeme = new Lexeme(context.getBufferOffset(), englishStart, (englishEnd - englishStart) + 1, 1);
			context.addLexeme(newLexeme);
			englishStart = -1;
			englishEnd = -1;
		}
		if (context.isBufferConsumed() && englishStart != -1 && englishEnd != -1)
		{
			Lexeme newLexeme = new Lexeme(context.getBufferOffset(), englishStart, (englishEnd - englishStart) + 1, 1);
			context.addLexeme(newLexeme);
			englishStart = -1;
			englishEnd = -1;
		}
		if (englishStart == -1 && englishEnd == -1)
			needLock = false;
		else
			needLock = true;
		return needLock;
	}

	private boolean processArabicLetter(AnalyzeContext context)
	{
		boolean needLock = false;
		if (arabicStart == -1)
		{
			if (1 == context.getCurrentCharType())
			{
				arabicStart = context.getCursor();
				arabicEnd = arabicStart;
			}
		} else
		if (1 == context.getCurrentCharType())
			arabicEnd = context.getCursor();
		else
		if (context.getCurrentCharType() != 0 || !isNumConnector(context.getCurrentChar()))
		{
			Lexeme newLexeme = new Lexeme(context.getBufferOffset(), arabicStart, (arabicEnd - arabicStart) + 1, 2);
			context.addLexeme(newLexeme);
			arabicStart = -1;
			arabicEnd = -1;
		}
		if (context.isBufferConsumed() && arabicStart != -1 && arabicEnd != -1)
		{
			Lexeme newLexeme = new Lexeme(context.getBufferOffset(), arabicStart, (arabicEnd - arabicStart) + 1, 2);
			context.addLexeme(newLexeme);
			arabicStart = -1;
			arabicEnd = -1;
		}
		if (arabicStart == -1 && arabicEnd == -1)
			needLock = false;
		else
			needLock = true;
		return needLock;
	}

	private boolean isLetterConnector(char input)
	{
		int index = Arrays.binarySearch(Letter_Connector, input);
		return index >= 0;
	}

	private boolean isNumConnector(char input)
	{
		int index = Arrays.binarySearch(Num_Connector, input);
		return index >= 0;
	}

}
