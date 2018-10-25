// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SynonymFilterFactory.java

package org.apache.lucene.analysis.synonym;

import java.io.IOException;
import java.util.Map;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.*;
import org.apache.lucene.util.Version;

// Referenced classes of package org.apache.lucene.analysis.synonym:
//			FSTSynonymFilterFactory, SlowSynonymFilterFactory

public class SynonymFilterFactory extends TokenFilterFactory
	implements ResourceLoaderAware
{

	private TokenFilterFactory delegator;
	static final boolean $assertionsDisabled = !org/apache/lucene/analysis/synonym/SynonymFilterFactory.desiredAssertionStatus();

	public SynonymFilterFactory()
	{
	}

	public void init(Map args)
	{
		super.init(args);
		assureMatchVersion();
		if (luceneMatchVersion.onOrAfter(Version.LUCENE_34))
		{
			delegator = new FSTSynonymFilterFactory();
		} else
		{
			if (args.containsKey("format") && !((String)args.get("format")).equals("solr"))
				throw new IllegalArgumentException("You must specify luceneMatchVersion >= 3.4 to use alternate synonyms formats");
			delegator = new SlowSynonymFilterFactory();
		}
		delegator.init(args);
	}

	public TokenStream create(TokenStream input)
	{
		if (!$assertionsDisabled && delegator == null)
			throw new AssertionError("init() was not called!");
		else
			return delegator.create(input);
	}

	public void inform(ResourceLoader loader)
		throws IOException
	{
		if (!$assertionsDisabled && delegator == null)
		{
			throw new AssertionError("init() was not called!");
		} else
		{
			((ResourceLoaderAware)delegator).inform(loader);
			return;
		}
	}

}
