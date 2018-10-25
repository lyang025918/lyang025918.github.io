// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DelimitedPayloadTokenFilterFactory.java

package org.apache.lucene.analysis.payloads;

import java.util.Map;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.*;

// Referenced classes of package org.apache.lucene.analysis.payloads:
//			DelimitedPayloadTokenFilter, FloatEncoder, IntegerEncoder, IdentityEncoder, 
//			PayloadEncoder

public class DelimitedPayloadTokenFilterFactory extends TokenFilterFactory
	implements ResourceLoaderAware
{

	public static final String ENCODER_ATTR = "encoder";
	public static final String DELIMITER_ATTR = "delimiter";
	private PayloadEncoder encoder;
	private char delimiter;

	public DelimitedPayloadTokenFilterFactory()
	{
		delimiter = '|';
	}

	public DelimitedPayloadTokenFilter create(TokenStream input)
	{
		return new DelimitedPayloadTokenFilter(input, delimiter, encoder);
	}

	public void init(Map args)
	{
		super.init(args);
	}

	public void inform(ResourceLoader loader)
	{
		String encoderClass = (String)args.get("encoder");
		if (encoderClass == null)
			throw new IllegalArgumentException("Parameter encoder is mandatory");
		if (encoderClass.equals("float"))
			encoder = new FloatEncoder();
		else
		if (encoderClass.equals("integer"))
			encoder = new IntegerEncoder();
		else
		if (encoderClass.equals("identity"))
			encoder = new IdentityEncoder();
		else
			encoder = (PayloadEncoder)loader.newInstance(encoderClass, org/apache/lucene/analysis/payloads/PayloadEncoder);
		String delim = (String)args.get("delimiter");
		if (delim != null)
			if (delim.length() == 1)
				delimiter = delim.charAt(0);
			else
				throw new IllegalArgumentException("Delimiter must be one character only");
	}

	public volatile TokenStream create(TokenStream x0)
	{
		return create(x0);
	}
}
