// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TypeAsPayloadTokenFilter.java

package org.apache.lucene.analysis.payloads;

import java.io.IOException;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.PayloadAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.util.BytesRef;

public class TypeAsPayloadTokenFilter extends TokenFilter
{

	private final PayloadAttribute payloadAtt = (PayloadAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/PayloadAttribute);
	private final TypeAttribute typeAtt = (TypeAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/TypeAttribute);

	public TypeAsPayloadTokenFilter(TokenStream input)
	{
		super(input);
	}

	public final boolean incrementToken()
		throws IOException
	{
		if (input.incrementToken())
		{
			String type = typeAtt.type();
			if (type != null && !type.equals(""))
				payloadAtt.setPayload(new BytesRef(type.getBytes("UTF-8")));
			return true;
		} else
		{
			return false;
		}
	}
}
