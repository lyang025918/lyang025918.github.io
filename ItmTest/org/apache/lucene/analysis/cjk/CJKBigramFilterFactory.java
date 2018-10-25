// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CJKBigramFilterFactory.java

package org.apache.lucene.analysis.cjk;

import java.util.Map;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;

// Referenced classes of package org.apache.lucene.analysis.cjk:
//			CJKBigramFilter

public class CJKBigramFilterFactory extends TokenFilterFactory
{

	int flags;
	boolean outputUnigrams;

	public CJKBigramFilterFactory()
	{
	}

	public void init(Map args)
	{
		super.init(args);
		flags = 0;
		if (getBoolean("han", true))
			flags |= 1;
		if (getBoolean("hiragana", true))
			flags |= 2;
		if (getBoolean("katakana", true))
			flags |= 4;
		if (getBoolean("hangul", true))
			flags |= 8;
		outputUnigrams = getBoolean("outputUnigrams", false);
	}

	public TokenStream create(TokenStream input)
	{
		return new CJKBigramFilter(input, flags, outputUnigrams);
	}
}
