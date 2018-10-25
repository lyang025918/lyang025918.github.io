// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PersianNormalizationFilterFactory.java

package org.apache.lucene.analysis.fa;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.*;

// Referenced classes of package org.apache.lucene.analysis.fa:
//			PersianNormalizationFilter

public class PersianNormalizationFilterFactory extends TokenFilterFactory
	implements MultiTermAwareComponent
{

	public PersianNormalizationFilterFactory()
	{
	}

	public PersianNormalizationFilter create(TokenStream input)
	{
		return new PersianNormalizationFilter(input);
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
