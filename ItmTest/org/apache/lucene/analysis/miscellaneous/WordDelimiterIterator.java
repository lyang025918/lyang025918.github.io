// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   WordDelimiterIterator.java

package org.apache.lucene.analysis.miscellaneous;


// Referenced classes of package org.apache.lucene.analysis.miscellaneous:
//			WordDelimiterFilter

public final class WordDelimiterIterator
{

	public static final int DONE = -1;
	public static final byte DEFAULT_WORD_DELIM_TABLE[];
	char text[];
	int length;
	int startBounds;
	int endBounds;
	int current;
	int end;
	private boolean hasFinalPossessive;
	final boolean splitOnCaseChange;
	final boolean splitOnNumerics;
	final boolean stemEnglishPossessive;
	private final byte charTypeTable[];
	private boolean skipPossessive;

	WordDelimiterIterator(byte charTypeTable[], boolean splitOnCaseChange, boolean splitOnNumerics, boolean stemEnglishPossessive)
	{
		hasFinalPossessive = false;
		skipPossessive = false;
		this.charTypeTable = charTypeTable;
		this.splitOnCaseChange = splitOnCaseChange;
		this.splitOnNumerics = splitOnNumerics;
		this.stemEnglishPossessive = stemEnglishPossessive;
	}

	int next()
	{
		current = end;
		if (current == -1)
			return -1;
		if (skipPossessive)
		{
			current += 2;
			skipPossessive = false;
		}
		int lastType;
		for (lastType = 0; current < endBounds && WordDelimiterFilter.isSubwordDelim(lastType = charType(text[current])); current++);
		if (current >= endBounds)
			return end = -1;
		end = current + 1;
		do
		{
			if (end >= endBounds)
				break;
			int type = charType(text[end]);
			if (isBreak(lastType, type))
				break;
			lastType = type;
			end++;
		} while (true);
		if (end < endBounds - 1 && endsWithPossessive(end + 2))
			skipPossessive = true;
		return end;
	}

	int type()
	{
		if (end == -1)
			return 0;
		int type = charType(text[current]);
		switch (type)
		{
		case 1: // '\001'
		case 2: // '\002'
			return 3;
		}
		return type;
	}

	void setText(char text[], int length)
	{
		this.text = text;
		this.length = endBounds = length;
		current = startBounds = end = 0;
		skipPossessive = hasFinalPossessive = false;
		setBounds();
	}

	private boolean isBreak(int lastType, int type)
	{
		if ((type & lastType) != 0)
			return false;
		if (!splitOnCaseChange && WordDelimiterFilter.isAlpha(lastType) && WordDelimiterFilter.isAlpha(type))
			return false;
		if (WordDelimiterFilter.isUpper(lastType) && WordDelimiterFilter.isAlpha(type))
			return false;
		return splitOnNumerics || (!WordDelimiterFilter.isAlpha(lastType) || !WordDelimiterFilter.isDigit(type)) && (!WordDelimiterFilter.isDigit(lastType) || !WordDelimiterFilter.isAlpha(type));
	}

	boolean isSingleWord()
	{
		if (hasFinalPossessive)
			return current == startBounds && end == endBounds - 2;
		else
			return current == startBounds && end == endBounds;
	}

	private void setBounds()
	{
		for (; startBounds < length && WordDelimiterFilter.isSubwordDelim(charType(text[startBounds])); startBounds++);
		for (; endBounds > startBounds && WordDelimiterFilter.isSubwordDelim(charType(text[endBounds - 1])); endBounds--);
		if (endsWithPossessive(endBounds))
			hasFinalPossessive = true;
		current = startBounds;
	}

	private boolean endsWithPossessive(int pos)
	{
		return stemEnglishPossessive && pos > 2 && text[pos - 2] == '\'' && (text[pos - 1] == 's' || text[pos - 1] == 'S') && WordDelimiterFilter.isAlpha(charType(text[pos - 3])) && (pos == endBounds || WordDelimiterFilter.isSubwordDelim(charType(text[pos])));
	}

	private int charType(int ch)
	{
		if (ch < charTypeTable.length)
			return charTypeTable[ch];
		else
			return getType(ch);
	}

	public static byte getType(int ch)
	{
		switch (Character.getType(ch))
		{
		case 1: // '\001'
			return 2;

		case 2: // '\002'
			return 1;

		case 3: // '\003'
		case 4: // '\004'
		case 5: // '\005'
		case 6: // '\006'
		case 7: // '\007'
		case 8: // '\b'
			return 3;

		case 9: // '\t'
		case 10: // '\n'
		case 11: // '\013'
			return 4;

		case 19: // '\023'
			return 7;

		case 12: // '\f'
		case 13: // '\r'
		case 14: // '\016'
		case 15: // '\017'
		case 16: // '\020'
		case 17: // '\021'
		case 18: // '\022'
		default:
			return 8;
		}
	}

	static 
	{
		byte tab[] = new byte[256];
		for (int i = 0; i < 256; i++)
		{
			byte code = 0;
			if (Character.isLowerCase(i))
				code |= 1;
			else
			if (Character.isUpperCase(i))
				code |= 2;
			else
			if (Character.isDigit(i))
				code |= 4;
			if (code == 0)
				code = 8;
			tab[i] = code;
		}

		DEFAULT_WORD_DELIM_TABLE = tab;
	}
}
