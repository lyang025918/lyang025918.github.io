// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IndicNormalizationFilterFactory.java

package org.apache.lucene.analysis.in;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.*;

// Referenced classes of package org.apache.lucene.analysis.in:
//			IndicNormalizationFilter

public class IndicNormalizationFilterFactory extends TokenFilterFactory
	implements MultiTermAwareComponent
{

	public IndicNormalizationFilterFactory()
	{
	}

	public TokenStream create(TokenStream input)
	{
		return new IndicNormalizationFilter(input);
	}

	public AbstractAnalysisFactory getMultiTermComponent()
	{
		return this;
	}
}
