// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DelimitedPayloadTokenFilter.java

package org.apache.lucene.analysis.payloads;

import java.io.IOException;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PayloadAttribute;

// Referenced classes of package org.apache.lucene.analysis.payloads:
//			PayloadEncoder

public final class DelimitedPayloadTokenFilter extends TokenFilter
{

	public static final char DEFAULT_DELIMITER = 124;
	private final char delimiter;
	private final CharTermAttribute termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
	private final PayloadAttribute payAtt = (PayloadAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/PayloadAttribute);
	private final PayloadEncoder encoder;

	public DelimitedPayloadTokenFilter(TokenStream input, char delimiter, PayloadEncoder encoder)
	{
		super(input);
		this.delimiter = delimiter;
		this.encoder = encoder;
	}

	public boolean incrementToken()
		throws IOException
	{
		if (input.incrementToken())
		{
			char buffer[] = termAtt.buffer();
			int length = termAtt.length();
			for (int i = 0; i < length; i++)
				if (buffer[i] == delimiter)
				{
					payAtt.setPayload(encoder.encode(buffer, i + 1, length - (i + 1)));
					termAtt.setLength(i);
					return true;
				}

			payAtt.setPayload(null);
			return true;
		} else
		{
			return false;
		}
	}
}
