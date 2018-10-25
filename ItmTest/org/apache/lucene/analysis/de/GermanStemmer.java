// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GermanStemmer.java

package org.apache.lucene.analysis.de;

import java.util.Locale;

public class GermanStemmer
{

	private StringBuilder sb;
	private int substCount;
	private static final Locale locale = new Locale("de", "DE");

	public GermanStemmer()
	{
		sb = new StringBuilder();
		substCount = 0;
	}

	protected String stem(String term)
	{
		term = term.toLowerCase(locale);
		if (!isStemmable(term))
		{
			return term;
		} else
		{
			sb.delete(0, sb.length());
			sb.insert(0, term);
			substitute(sb);
			strip(sb);
			optimize(sb);
			resubstitute(sb);
			removeParticleDenotion(sb);
			return sb.toString();
		}
	}

	private boolean isStemmable(String term)
	{
		for (int c = 0; c < term.length(); c++)
			if (!Character.isLetter(term.charAt(c)))
				return false;

		return true;
	}

	private void strip(StringBuilder buffer)
	{
		for (boolean doMore = true; doMore && buffer.length() > 3;)
			if (buffer.length() + substCount > 5 && buffer.substring(buffer.length() - 2, buffer.length()).equals("nd"))
				buffer.delete(buffer.length() - 2, buffer.length());
			else
			if (buffer.length() + substCount > 4 && buffer.substring(buffer.length() - 2, buffer.length()).equals("em"))
				buffer.delete(buffer.length() - 2, buffer.length());
			else
			if (buffer.length() + substCount > 4 && buffer.substring(buffer.length() - 2, buffer.length()).equals("er"))
				buffer.delete(buffer.length() - 2, buffer.length());
			else
			if (buffer.charAt(buffer.length() - 1) == 'e')
				buffer.deleteCharAt(buffer.length() - 1);
			else
			if (buffer.charAt(buffer.length() - 1) == 's')
				buffer.deleteCharAt(buffer.length() - 1);
			else
			if (buffer.charAt(buffer.length() - 1) == 'n')
				buffer.deleteCharAt(buffer.length() - 1);
			else
			if (buffer.charAt(buffer.length() - 1) == 't')
				buffer.deleteCharAt(buffer.length() - 1);
			else
				doMore = false;

	}

	private void optimize(StringBuilder buffer)
	{
		if (buffer.length() > 5 && buffer.substring(buffer.length() - 5, buffer.length()).equals("erin*"))
		{
			buffer.deleteCharAt(buffer.length() - 1);
			strip(buffer);
		}
		if (buffer.length() > 0 && buffer.charAt(buffer.length() - 1) == 'z')
			buffer.setCharAt(buffer.length() - 1, 'x');
	}

	private void removeParticleDenotion(StringBuilder buffer)
	{
		if (buffer.length() > 4)
		{
			for (int c = 0; c < buffer.length() - 3; c++)
				if (buffer.substring(c, c + 4).equals("gege"))
				{
					buffer.delete(c, c + 2);
					return;
				}

		}
	}

	private void substitute(StringBuilder buffer)
	{
		substCount = 0;
		for (int c = 0; c < buffer.length(); c++)
		{
			if (c > 0 && buffer.charAt(c) == buffer.charAt(c - 1))
				buffer.setCharAt(c, '*');
			else
			if (buffer.charAt(c) == '\344')
				buffer.setCharAt(c, 'a');
			else
			if (buffer.charAt(c) == '\366')
				buffer.setCharAt(c, 'o');
			else
			if (buffer.charAt(c) == '\374')
				buffer.setCharAt(c, 'u');
			else
			if (buffer.charAt(c) == '\337')
			{
				buffer.setCharAt(c, 's');
				buffer.insert(c + 1, 's');
				substCount++;
			}
			if (c >= buffer.length() - 1)
				continue;
			if (c < buffer.length() - 2 && buffer.charAt(c) == 's' && buffer.charAt(c + 1) == 'c' && buffer.charAt(c + 2) == 'h')
			{
				buffer.setCharAt(c, '$');
				buffer.delete(c + 1, c + 3);
				substCount = 2;
				continue;
			}
			if (buffer.charAt(c) == 'c' && buffer.charAt(c + 1) == 'h')
			{
				buffer.setCharAt(c, '\247');
				buffer.deleteCharAt(c + 1);
				substCount++;
				continue;
			}
			if (buffer.charAt(c) == 'e' && buffer.charAt(c + 1) == 'i')
			{
				buffer.setCharAt(c, '%');
				buffer.deleteCharAt(c + 1);
				substCount++;
				continue;
			}
			if (buffer.charAt(c) == 'i' && buffer.charAt(c + 1) == 'e')
			{
				buffer.setCharAt(c, '&');
				buffer.deleteCharAt(c + 1);
				substCount++;
				continue;
			}
			if (buffer.charAt(c) == 'i' && buffer.charAt(c + 1) == 'g')
			{
				buffer.setCharAt(c, '#');
				buffer.deleteCharAt(c + 1);
				substCount++;
				continue;
			}
			if (buffer.charAt(c) == 's' && buffer.charAt(c + 1) == 't')
			{
				buffer.setCharAt(c, '!');
				buffer.deleteCharAt(c + 1);
				substCount++;
			}
		}

	}

	private void resubstitute(StringBuilder buffer)
	{
		for (int c = 0; c < buffer.length(); c++)
		{
			if (buffer.charAt(c) == '*')
			{
				char x = buffer.charAt(c - 1);
				buffer.setCharAt(c, x);
				continue;
			}
			if (buffer.charAt(c) == '$')
			{
				buffer.setCharAt(c, 's');
				buffer.insert(c + 1, new char[] {
					'c', 'h'
				}, 0, 2);
				continue;
			}
			if (buffer.charAt(c) == '\247')
			{
				buffer.setCharAt(c, 'c');
				buffer.insert(c + 1, 'h');
				continue;
			}
			if (buffer.charAt(c) == '%')
			{
				buffer.setCharAt(c, 'e');
				buffer.insert(c + 1, 'i');
				continue;
			}
			if (buffer.charAt(c) == '&')
			{
				buffer.setCharAt(c, 'i');
				buffer.insert(c + 1, 'e');
				continue;
			}
			if (buffer.charAt(c) == '#')
			{
				buffer.setCharAt(c, 'i');
				buffer.insert(c + 1, 'g');
				continue;
			}
			if (buffer.charAt(c) == '!')
			{
				buffer.setCharAt(c, 's');
				buffer.insert(c + 1, 't');
			}
		}

	}

}
