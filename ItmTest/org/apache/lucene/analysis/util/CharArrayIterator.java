// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CharArrayIterator.java

package org.apache.lucene.analysis.util;

import java.text.BreakIterator;
import java.text.CharacterIterator;
import java.util.Locale;

public abstract class CharArrayIterator
	implements CharacterIterator
{

	private char array[];
	private int start;
	private int index;
	private int length;
	private int limit;
	public static final boolean HAS_BUGGY_BREAKITERATORS;

	public CharArrayIterator()
	{
	}

	public char[] getText()
	{
		return array;
	}

	public int getStart()
	{
		return start;
	}

	public int getLength()
	{
		return length;
	}

	public void setText(char array[], int start, int length)
	{
		this.array = array;
		this.start = start;
		index = start;
		this.length = length;
		limit = start + length;
	}

	public char current()
	{
		return index != limit ? jreBugWorkaround(array[index]) : '\uFFFF';
	}

	protected abstract char jreBugWorkaround(char c);

	public char first()
	{
		index = start;
		return current();
	}

	public int getBeginIndex()
	{
		return 0;
	}

	public int getEndIndex()
	{
		return length;
	}

	public int getIndex()
	{
		return index - start;
	}

	public char last()
	{
		index = limit != start ? limit - 1 : limit;
		return current();
	}

	public char next()
	{
		if (++index >= limit)
		{
			index = limit;
			return '\uFFFF';
		} else
		{
			return current();
		}
	}

	public char previous()
	{
		if (--index < start)
		{
			index = start;
			return '\uFFFF';
		} else
		{
			return current();
		}
	}

	public char setIndex(int position)
	{
		if (position < getBeginIndex() || position > getEndIndex())
		{
			throw new IllegalArgumentException((new StringBuilder()).append("Illegal Position: ").append(position).toString());
		} else
		{
			index = start + position;
			return current();
		}
	}

	public CharArrayIterator clone()
	{
		return (CharArrayIterator)super.clone();
		CloneNotSupportedException e;
		e;
		throw new RuntimeException(e);
	}

	public static CharArrayIterator newSentenceInstance()
	{
		if (HAS_BUGGY_BREAKITERATORS)
			return new CharArrayIterator() {

				protected char jreBugWorkaround(char ch)
				{
					return ch < '\uD800' || ch > '\uDFFF' ? ch : ',';
				}

				public volatile Object clone()
				{
					return clone();
				}

			};
		else
			return new CharArrayIterator() {

				protected char jreBugWorkaround(char ch)
				{
					return ch;
				}

				public volatile Object clone()
				{
					return clone();
				}

			};
	}

	public static CharArrayIterator newWordInstance()
	{
		if (HAS_BUGGY_BREAKITERATORS)
			return new CharArrayIterator() {

				protected char jreBugWorkaround(char ch)
				{
					return ch < '\uD800' || ch > '\uDFFF' ? ch : 'A';
				}

				public volatile Object clone()
				{
					return clone();
				}

			};
		else
			return new CharArrayIterator() {

				protected char jreBugWorkaround(char ch)
				{
					return ch;
				}

				public volatile Object clone()
				{
					return clone();
				}

			};
	}

	public volatile Object clone()
		throws CloneNotSupportedException
	{
		return clone();
	}

	static 
	{
		boolean v;
		try
		{
			BreakIterator bi = BreakIterator.getSentenceInstance(Locale.US);
			bi.setText("??");
			bi.next();
			v = false;
		}
		catch (Exception e)
		{
			v = true;
		}
		HAS_BUGGY_BREAKITERATORS = v;
	}
}
