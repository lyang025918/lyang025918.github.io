// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NumericPayloadTokenFilter.java

package org.apache.lucene.analysis.payloads;

import java.io.IOException;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.PayloadAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.util.BytesRef;

// Referenced classes of package org.apache.lucene.analysis.payloads:
//			PayloadHelper

public class NumericPayloadTokenFilter extends TokenFilter
{

	private String typeMatch;
	private BytesRef thePayload;
	private final PayloadAttribute payloadAtt = (PayloadAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/PayloadAttribute);
	private final TypeAttribute typeAtt = (TypeAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/TypeAttribute);

	public NumericPayloadTokenFilter(TokenStream input, float payload, String typeMatch)
	{
		super(input);
		if (typeMatch == null)
		{
			throw new IllegalArgumentException("typeMatch cannot be null");
		} else
		{
			thePayload = new BytesRef(PayloadHelper.encodeFloat(payload));
			this.typeMatch = typeMatch;
			return;
		}
	}

	public final boolean incrementToken()
		throws IOException
	{
		if (input.incrementToken())
		{
			if (typeAtt.type().equals(typeMatch))
				payloadAtt.setPayload(thePayload);
			return true;
		} else
		{
			return false;
		}
	}
}
