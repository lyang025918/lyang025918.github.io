// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IndonesianStemFilterFactory.java

package org.apache.lucene.analysis.id;

import java.util.Map;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;

// Referenced classes of package org.apache.lucene.analysis.id:
//			IndonesianStemFilter

public class IndonesianStemFilterFactory extends TokenFilterFactory
{

	private boolean stemDerivational;

	public IndonesianStemFilterFactory()
	{
		stemDerivational = true;
	}

	public void init(Map args)
	{
		super.init(args);
		stemDerivational = getBoolean("stemDerivational", true);
	}

	public TokenStream create(TokenStream input)
	{
		return new IndonesianStemFilter(input, stemDerivational);
	}
}
