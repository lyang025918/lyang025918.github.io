// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Lexeme.java

package org.wltea.analyzer.core;


public class Lexeme
	implements Comparable
{

	public static final int TYPE_UNKNOWN = 0;
	public static final int TYPE_ENGLISH = 1;
	public static final int TYPE_ARABIC = 2;
	public static final int TYPE_LETTER = 3;
	public static final int TYPE_CNWORD = 4;
	public static final int TYPE_CNCHAR = 64;
	public static final int TYPE_OTHER_CJK = 8;
	public static final int TYPE_CNUM = 16;
	public static final int TYPE_COUNT = 32;
	public static final int TYPE_CQUAN = 48;
	private int offset;
	private int begin;
	private int length;
	private String lexemeText;
	private int lexemeType;

	public Lexeme(int offset, int begin, int length, int lexemeType)
	{
		this.offset = offset;
		this.begin = begin;
		if (length < 0)
		{
			throw new IllegalArgumentException("length < 0");
		} else
		{
			this.length = length;
			this.lexemeType = lexemeType;
			return;
		}
	}

	public boolean equals(Object o)
	{
		if (o == null)
			return false;
		if (this == o)
			return true;
		if (o instanceof Lexeme)
		{
			Lexeme other = (Lexeme)o;
			return offset == other.getOffset() && begin == other.getBegin() && length == other.getLength();
		} else
		{
			return false;
		}
	}

	public int hashCode()
	{
		int absBegin = getBeginPosition();
		int absEnd = getEndPosition();
		return absBegin * 37 + absEnd * 31 + ((absBegin * absEnd) % getLength()) * 11;
	}

	public int compareTo(Lexeme other)
	{
		if (begin < other.getBegin())
			return -1;
		if (begin == other.getBegin())
		{
			if (length > other.getLength())
				return -1;
			return length != other.getLength() ? 1 : 0;
		} else
		{
			return 1;
		}
	}

	public int getOffset()
	{
		return offset;
	}

	public void setOffset(int offset)
	{
		this.offset = offset;
	}

	public int getBegin()
	{
		return begin;
	}

	public int getBeginPosition()
	{
		return offset + begin;
	}

	public void setBegin(int begin)
	{
		this.begin = begin;
	}

	public int getEndPosition()
	{
		return offset + begin + length;
	}

	public int getLength()
	{
		return length;
	}

	public void setLength(int length)
	{
		if (this.length < 0)
		{
			throw new IllegalArgumentException("length < 0");
		} else
		{
			this.length = length;
			return;
		}
	}

	public String getLexemeText()
	{
		if (lexemeText == null)
			return "";
		else
			return lexemeText;
	}

	public void setLexemeText(String lexemeText)
	{
		if (lexemeText == null)
		{
			this.lexemeText = "";
			length = 0;
		} else
		{
			this.lexemeText = lexemeText;
			length = lexemeText.length();
		}
	}

	public int getLexemeType()
	{
		return lexemeType;
	}

	public String getLexemeTypeString()
	{
		switch (lexemeType)
		{
		case 1: // '\001'
			return "ENGLISH";

		case 2: // '\002'
			return "ARABIC";

		case 3: // '\003'
			return "LETTER";

		case 4: // '\004'
			return "CN_WORD";

		case 64: // '@'
			return "CN_CHAR";

		case 8: // '\b'
			return "OTHER_CJK";

		case 32: // ' '
			return "COUNT";

		case 16: // '\020'
			return "TYPE_CNUM";

		case 48: // '0'
			return "TYPE_CQUAN";
		}
		return "UNKONW";
	}

	public void setLexemeType(int lexemeType)
	{
		this.lexemeType = lexemeType;
	}

	public boolean append(Lexeme l, int lexemeType)
	{
		if (l != null && getEndPosition() == l.getBeginPosition())
		{
			length += l.getLength();
			this.lexemeType = lexemeType;
			return true;
		} else
		{
			return false;
		}
	}

	public String toString()
	{
		StringBuffer strbuf = new StringBuffer();
		strbuf.append(getBeginPosition()).append("-").append(getEndPosition());
		strbuf.append(" : ").append(lexemeText).append(" : \t");
		strbuf.append(getLexemeTypeString());
		return strbuf.toString();
	}

	public volatile int compareTo(Object obj)
	{
		return compareTo((Lexeme)obj);
	}
}
