// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NumericPayloadTokenFilterFactory.java

package org.apache.lucene.analysis.payloads;

import java.util.Map;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;

// Referenced classes of package org.apache.lucene.analysis.payloads:
//			NumericPayloadTokenFilter

public class NumericPayloadTokenFilterFactory extends TokenFilterFactory
{

	private float payload;
	private String typeMatch;

	public NumericPayloadTokenFilterFactory()
	{
	}

	public void init(Map args)
	{
		super.init(args);
		String payloadArg = (String)args.get("payload");
		typeMatch = (String)args.get("typeMatch");
		if (payloadArg == null || typeMatch == null)
		{
			throw new IllegalArgumentException("Both payload and typeMatch are required");
		} else
		{
			payload = Float.parseFloat(payloadArg);
			return;
		}
	}

	public NumericPayloadTokenFilter create(TokenStream input)
	{
		return new NumericPayloadTokenFilter(input, payload, typeMatch);
	}

	public volatile TokenStream create(TokenStream x0)
	{
		return create(x0);
	}
}
