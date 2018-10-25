// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ElisionFilterFactory.java

package org.apache.lucene.analysis.util;

import java.io.IOException;
import java.util.Map;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.fr.FrenchAnalyzer;

// Referenced classes of package org.apache.lucene.analysis.util:
//			TokenFilterFactory, ElisionFilter, ResourceLoaderAware, MultiTermAwareComponent, 
//			CharArraySet, ResourceLoader, AbstractAnalysisFactory

public class ElisionFilterFactory extends TokenFilterFactory
	implements ResourceLoaderAware, MultiTermAwareComponent
{

	private CharArraySet articles;

	public ElisionFilterFactory()
	{
	}

	public void inform(ResourceLoader loader)
		throws IOException
	{
		String articlesFile = (String)args.get("articles");
		boolean ignoreCase = getBoolean("ignoreCase", false);
		if (articlesFile != null)
			articles = getWordSet(loader, articlesFile, ignoreCase);
		if (articles == null)
			articles = FrenchAnalyzer.DEFAULT_ARTICLES;
	}

	public ElisionFilter create(TokenStream input)
	{
		return new ElisionFilter(input, articles);
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
