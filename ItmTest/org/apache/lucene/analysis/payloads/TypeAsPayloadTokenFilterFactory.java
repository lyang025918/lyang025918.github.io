// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TypeAsPayloadTokenFilterFactory.java

package org.apache.lucene.analysis.payloads;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;

// Referenced classes of package org.apache.lucene.analysis.payloads:
//			TypeAsPayloadTokenFilter

public class TypeAsPayloadTokenFilterFactory extends TokenFilterFactory
{

	public TypeAsPayloadTokenFilterFactory()
	{
	}

	public TypeAsPayloadTokenFilter create(TokenStream input)
	{
		return new TypeAsPayloadTokenFilter(input);
	}

	public volatile TokenStream create(TokenStream x0)
	{
		return create(x0);
	}
}
