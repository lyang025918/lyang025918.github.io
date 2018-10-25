// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PersianCharFilter.java

package org.apache.lucene.analysis.fa;

import java.io.IOException;
import java.io.Reader;
import org.apache.lucene.analysis.CharFilter;

public class PersianCharFilter extends CharFilter
{

	public PersianCharFilter(Reader in)
	{
		super(in);
	}

	public int read(char cbuf[], int off, int len)
		throws IOException
	{
		int charsRead = input.read(cbuf, off, len);
		if (charsRead > 0)
		{
			for (int end = off + charsRead; off < end; off++)
				if (cbuf[off] == '\u200C')
					cbuf[off] = ' ';

		}
		return charsRead;
	}

	public int read()
		throws IOException
	{
		int ch = input.read();
		if (ch == 8204)
			return 32;
		else
			return ch;
	}

	protected int correct(int currentOff)
	{
		return currentOff;
	}
}
