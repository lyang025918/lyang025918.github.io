// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TrimFilter.java

package org.apache.lucene.analysis.miscellaneous;

import java.io.IOException;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;

public final class TrimFilter extends TokenFilter
{

	final boolean updateOffsets;
	private final CharTermAttribute termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
	private final OffsetAttribute offsetAtt = (OffsetAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);

	public TrimFilter(TokenStream in, boolean updateOffsets)
	{
		super(in);
		this.updateOffsets = updateOffsets;
	}

	public boolean incrementToken()
		throws IOException
	{
		if (!input.incrementToken())
			return false;
		char termBuffer[] = termAtt.buffer();
		int len = termAtt.length();
		if (len == 0)
			return true;
		int start = 0;
		int end = 0;
		int endOff = 0;
		for (start = 0; start < len && termBuffer[start] <= ' '; start++);
		for (end = len; end >= start && termBuffer[end - 1] <= ' '; end--)
			endOff++;

		if (start > 0 || end < len)
		{
			if (start < end)
				termAtt.copyBuffer(termBuffer, start, end - start);
			else
				termAtt.setEmpty();
			if (updateOffsets && len == offsetAtt.endOffset() - offsetAtt.startOffset())
			{
				int newStart = offsetAtt.startOffset() + start;
				int newEnd = offsetAtt.endOffset() - (start >= end ? 0 : endOff);
				offsetAtt.setOffset(newStart, newEnd);
			}
		}
		return true;
	}
}
