// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TokenizerFactory.java

package org.apache.lucene.analysis.util;

import java.io.Reader;
import java.util.Set;
import org.apache.lucene.analysis.Tokenizer;

// Referenced classes of package org.apache.lucene.analysis.util:
//			AbstractAnalysisFactory, AnalysisSPILoader

public abstract class TokenizerFactory extends AbstractAnalysisFactory
{

	private static final AnalysisSPILoader loader = new AnalysisSPILoader(org/apache/lucene/analysis/util/TokenizerFactory);

	public TokenizerFactory()
	{
	}

	public static TokenizerFactory forName(String name)
	{
		return (TokenizerFactory)loader.newInstance(name);
	}

	public static Class lookupClass(String name)
	{
		return loader.lookupClass(name);
	}

	public static Set availableTokenizers()
	{
		return loader.availableServices();
	}

	public static void reloadTokenizers(ClassLoader classloader)
	{
		loader.reload(classloader);
	}

	public abstract Tokenizer create(Reader reader);

}
