// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ReverseStringFilter.java

package org.apache.lucene.analysis.reverse;

import java.io.IOException;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

public final class ReverseStringFilter extends TokenFilter
{

	private final CharTermAttribute termAtt;
	private final char marker;
	private final Version matchVersion;
	private static final char NOMARKER = 65535;
	public static final char START_OF_HEADING_MARKER = 1;
	public static final char INFORMATION_SEPARATOR_MARKER = 31;
	public static final char PUA_EC00_MARKER = 60416;
	public static final char RTL_DIRECTION_MARKER = 8207;

	public ReverseStringFilter(Version matchVersion, TokenStream in)
	{
		this(matchVersion, in, '\uFFFF');
	}

	public ReverseStringFilter(Version matchVersion, TokenStream in, char marker)
	{
		super(in);
		termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		this.matchVersion = matchVersion;
		this.marker = marker;
	}

	public boolean incrementToken()
		throws IOException
	{
		if (input.incrementToken())
		{
			int len = termAtt.length();
			if (marker != '\uFFFF')
			{
				len++;
				termAtt.resizeBuffer(len);
				termAtt.buffer()[len - 1] = marker;
			}
			reverse(matchVersion, termAtt.buffer(), 0, len);
			termAtt.setLength(len);
			return true;
		} else
		{
			return false;
		}
	}

	public static String reverse(Version matchVersion, String input)
	{
		char charInput[] = input.toCharArray();
		reverse(matchVersion, charInput, 0, charInput.length);
		return new String(charInput);
	}

	public static void reverse(Version matchVersion, char buffer[])
	{
		reverse(matchVersion, buffer, 0, buffer.length);
	}

	public static void reverse(Version matchVersion, char buffer[], int len)
	{
		reverse(matchVersion, buffer, 0, len);
	}

	/**
	 * @deprecated Method reverseUnicode3 is deprecated
	 */

	private static void reverseUnicode3(char buffer[], int start, int len)
	{
		if (len <= 1)
			return;
		int num = len >> 1;
		for (int i = start; i < start + num; i++)
		{
			char c = buffer[i];
			buffer[i] = buffer[(start * 2 + len) - i - 1];
			buffer[(start * 2 + len) - i - 1] = c;
		}

	}

	public static void reverse(Version matchVersion, char buffer[], int start, int len)
	{
		if (!matchVersion.onOrAfter(Version.LUCENE_31))
		{
			reverseUnicode3(buffer, start, len);
			return;
		}
		if (len < 2)
			return;
		int end = (start + len) - 1;
		char frontHigh = buffer[start];
		char endLow = buffer[end];
		boolean allowFrontSur = true;
		boolean allowEndSur = true;
		int mid = start + (len >> 1);
		for (int i = start; i < mid;)
		{
			char frontLow = buffer[i + 1];
			char endHigh = buffer[end - 1];
			boolean surAtFront = allowFrontSur && Character.isSurrogatePair(frontHigh, frontLow);
			if (surAtFront && len < 3)
				return;
			boolean surAtEnd = allowEndSur && Character.isSurrogatePair(endHigh, endLow);
			allowFrontSur = allowEndSur = true;
			if (surAtFront == surAtEnd)
			{
				if (surAtFront)
				{
					buffer[end] = frontLow;
					buffer[--end] = frontHigh;
					buffer[i] = endHigh;
					buffer[++i] = endLow;
					frontHigh = buffer[i + 1];
					endLow = buffer[end - 1];
				} else
				{
					buffer[end] = frontHigh;
					buffer[i] = endLow;
					frontHigh = frontLow;
					endLow = endHigh;
				}
			} else
			if (surAtFront)
			{
				buffer[end] = frontLow;
				buffer[i] = endLow;
				endLow = endHigh;
				allowFrontSur = false;
			} else
			{
				buffer[end] = frontHigh;
				buffer[i] = endHigh;
				frontHigh = frontLow;
				allowEndSur = false;
			}
			i++;
			end--;
		}

		if ((len & 1) == 1 && (!allowFrontSur || !allowEndSur))
			buffer[end] = allowFrontSur ? endLow : frontHigh;
	}
}
