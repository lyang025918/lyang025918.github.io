// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CharFilterFactory.java

package org.apache.lucene.analysis.util;

import java.io.Reader;
import java.util.Set;

// Referenced classes of package org.apache.lucene.analysis.util:
//			AbstractAnalysisFactory, AnalysisSPILoader

public abstract class CharFilterFactory extends AbstractAnalysisFactory
{

	private static final AnalysisSPILoader loader = new AnalysisSPILoader(org/apache/lucene/analysis/util/CharFilterFactory);

	public CharFilterFactory()
	{
	}

	public static CharFilterFactory forName(String name)
	{
		return (CharFilterFactory)loader.newInstance(name);
	}

	public static Class lookupClass(String name)
	{
		return loader.lookupClass(name);
	}

	public static Set availableCharFilters()
	{
		return loader.availableServices();
	}

	public static void reloadCharFilters(ClassLoader classloader)
	{
		loader.reload(classloader);
	}

	public abstract Reader create(Reader reader);

}
