// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ArabicNormalizationFilterFactory.java

package org.apache.lucene.analysis.ar;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.*;

// Referenced classes of package org.apache.lucene.analysis.ar:
//			ArabicNormalizationFilter

public class ArabicNormalizationFilterFactory extends TokenFilterFactory
	implements MultiTermAwareComponent
{

	public ArabicNormalizationFilterFactory()
	{
	}

	public ArabicNormalizationFilter create(TokenStream input)
	{
		return new ArabicNormalizationFilter(input);
	}

	public AbstractAnalysisFactory getMultiTermComponent()
	{
		return this;
	}

	public volatile TokenStream create(TokenStream x0)
	{
		return create(x0);
	}
}
