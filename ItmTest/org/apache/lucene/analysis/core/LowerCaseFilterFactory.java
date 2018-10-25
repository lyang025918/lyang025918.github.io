// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LowerCaseFilterFactory.java

package org.apache.lucene.analysis.core;

import java.util.Map;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.*;

// Referenced classes of package org.apache.lucene.analysis.core:
//			LowerCaseFilter

public class LowerCaseFilterFactory extends TokenFilterFactory
	implements MultiTermAwareComponent
{

	public LowerCaseFilterFactory()
	{
	}

	public void init(Map args)
	{
		super.init(args);
		assureMatchVersion();
	}

	public LowerCaseFilter create(TokenStream input)
	{
		return new LowerCaseFilter(luceneMatchVersion, input);
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
