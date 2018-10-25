// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TokenFilterFactory.java

package org.apache.lucene.analysis.util;

import java.util.Set;
import org.apache.lucene.analysis.TokenStream;

// Referenced classes of package org.apache.lucene.analysis.util:
//			AbstractAnalysisFactory, AnalysisSPILoader

public abstract class TokenFilterFactory extends AbstractAnalysisFactory
{

	private static final AnalysisSPILoader loader = new AnalysisSPILoader(org/apache/lucene/analysis/util/TokenFilterFactory, new String[] {
		"TokenFilterFactory", "FilterFactory"
	});

	public TokenFilterFactory()
	{
	}

	public static TokenFilterFactory forName(String name)
	{
		return (TokenFilterFactory)loader.newInstance(name);
	}

	public static Class lookupClass(String name)
	{
		return loader.lookupClass(name);
	}

	public static Set availableTokenFilters()
	{
		return loader.availableServices();
	}

	public static void reloadTokenFilters(ClassLoader classloader)
	{
		loader.reload(classloader);
	}

	public abstract TokenStream create(TokenStream tokenstream);

}
