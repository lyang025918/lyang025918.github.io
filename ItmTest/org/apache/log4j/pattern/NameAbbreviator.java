// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NameAbbreviator.java

package org.apache.log4j.pattern;

import java.util.ArrayList;
import java.util.List;

public abstract class NameAbbreviator
{
	private static class PatternAbbreviator extends NameAbbreviator
	{

		private final PatternAbbreviatorFragment fragments[];

		public void abbreviate(int nameStart, StringBuffer buf)
		{
			int pos = nameStart;
			for (int i = 0; i < fragments.length - 1 && pos < buf.length(); i++)
				pos = fragments[i].abbreviate(buf, pos);

			PatternAbbreviatorFragment terminalFragment = fragments[fragments.length - 1];
			for (; pos < buf.length() && pos >= 0; pos = terminalFragment.abbreviate(buf, pos));
		}

		public PatternAbbreviator(List fragments)
		{
			if (fragments.size() == 0)
			{
				throw new IllegalArgumentException("fragments must have at least one element");
			} else
			{
				this.fragments = new PatternAbbreviatorFragment[fragments.size()];
				fragments.toArray(this.fragments);
				return;
			}
		}
	}

	private static class PatternAbbreviatorFragment
	{

		private final int charCount;
		private final char ellipsis;

		public int abbreviate(StringBuffer buf, int startPos)
		{
			int nextDot = buf.toString().indexOf(".", startPos);
			if (nextDot != -1)
			{
				if (nextDot - startPos > charCount)
				{
					buf.delete(startPos + charCount, nextDot);
					nextDot = startPos + charCount;
					if (ellipsis != 0)
					{
						buf.insert(nextDot, ellipsis);
						nextDot++;
					}
				}
				nextDot++;
			}
			return nextDot;
		}

		public PatternAbbreviatorFragment(int charCount, char ellipsis)
		{
			this.charCount = charCount;
			this.ellipsis = ellipsis;
		}
	}

	private static class DropElementAbbreviator extends NameAbbreviator
	{

		private final int count;

		public void abbreviate(int nameStart, StringBuffer buf)
		{
			int i = count;
			int pos = buf.indexOf(".", nameStart);
			do
			{
				if (pos == -1)
					break;
				if (--i == 0)
				{
					buf.delete(nameStart, pos + 1);
					break;
				}
				pos = buf.indexOf(".", pos + 1);
			} while (true);
		}

		public DropElementAbbreviator(int count)
		{
			this.count = count;
		}
	}

	private static class MaxElementAbbreviator extends NameAbbreviator
	{

		private final int count;

		public void abbreviate(int nameStart, StringBuffer buf)
		{
			int end = buf.length() - 1;
			String bufString = buf.toString();
			for (int i = count; i > 0; i--)
			{
				end = bufString.lastIndexOf(".", end - 1);
				if (end == -1 || end < nameStart)
					return;
			}

			buf.delete(nameStart, end + 1);
		}

		public MaxElementAbbreviator(int count)
		{
			this.count = count;
		}
	}

	private static class NOPAbbreviator extends NameAbbreviator
	{

		public void abbreviate(int i, StringBuffer stringbuffer)
		{
		}

		public NOPAbbreviator()
		{
		}
	}


	private static final NameAbbreviator DEFAULT = new NOPAbbreviator();

	public NameAbbreviator()
	{
	}

	public static NameAbbreviator getAbbreviator(String pattern)
	{
		if (pattern.length() > 0)
		{
			String trimmed = pattern.trim();
			if (trimmed.length() == 0)
				return DEFAULT;
			int i = 0;
			if (trimmed.length() > 0)
			{
				if (trimmed.charAt(0) == '-')
					i++;
				for (; i < trimmed.length() && trimmed.charAt(i) >= '0' && trimmed.charAt(i) <= '9'; i++);
			}
			if (i == trimmed.length())
			{
				int elements = Integer.parseInt(trimmed);
				if (elements >= 0)
					return new MaxElementAbbreviator(elements);
				else
					return new DropElementAbbreviator(-elements);
			}
			ArrayList fragments = new ArrayList(5);
			int pos = 0;
			do
			{
				if (pos >= trimmed.length() || pos < 0)
					break;
				int ellipsisPos = pos;
				int charCount;
				if (trimmed.charAt(pos) == '*')
				{
					charCount = 0x7fffffff;
					ellipsisPos++;
				} else
				if (trimmed.charAt(pos) >= '0' && trimmed.charAt(pos) <= '9')
				{
					charCount = trimmed.charAt(pos) - 48;
					ellipsisPos++;
				} else
				{
					charCount = 0;
				}
				char ellipsis = '\0';
				if (ellipsisPos < trimmed.length())
				{
					ellipsis = trimmed.charAt(ellipsisPos);
					if (ellipsis == '.')
						ellipsis = '\0';
				}
				fragments.add(new PatternAbbreviatorFragment(charCount, ellipsis));
				pos = trimmed.indexOf(".", pos);
				if (pos == -1)
					break;
				pos++;
			} while (true);
			return new PatternAbbreviator(fragments);
		} else
		{
			return DEFAULT;
		}
	}

	public static NameAbbreviator getDefaultAbbreviator()
	{
		return DEFAULT;
	}

	public abstract void abbreviate(int i, StringBuffer stringbuffer);

}
