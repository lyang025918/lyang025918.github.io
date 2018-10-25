// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TokenOffsetPayloadTokenFilter.java

package org.apache.lucene.analysis.payloads;

import java.io.IOException;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PayloadAttribute;
import org.apache.lucene.util.BytesRef;

// Referenced classes of package org.apache.lucene.analysis.payloads:
//			PayloadHelper

public class TokenOffsetPayloadTokenFilter extends TokenFilter
{

	private final OffsetAttribute offsetAtt = (OffsetAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/OffsetAttribute);
	private final PayloadAttribute payAtt = (PayloadAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/PayloadAttribute);

	public TokenOffsetPayloadTokenFilter(TokenStream input)
	{
		super(input);
	}

	public final boolean incrementToken()
		throws IOException
	{
		if (input.incrementToken())
		{
			byte data[] = new byte[8];
			PayloadHelper.encodeInt(offsetAtt.startOffset(), data, 0);
			PayloadHelper.encodeInt(offsetAtt.endOffset(), data, 4);
			BytesRef payload = new BytesRef(data);
			payAtt.setPayload(payload);
			return true;
		} else
		{
			return false;
		}
	}
}
