// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GreekLowerCaseFilterFactory.java

package org.apache.lucene.analysis.el;

import java.util.Map;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.*;

// Referenced classes of package org.apache.lucene.analysis.el:
//			GreekLowerCaseFilter

public class GreekLowerCaseFilterFactory extends TokenFilterFactory
	implements MultiTermAwareComponent
{

	public GreekLowerCaseFilterFactory()
	{
	}

	public void init(Map args)
	{
		super.init(args);
		assureMatchVersion();
		if (args.containsKey("charset"))
			throw new IllegalArgumentException("The charset parameter is no longer supported.  Please process your documents as Unicode instead.");
		else
			return;
	}

	public GreekLowerCaseFilter create(TokenStream in)
	{
		return new GreekLowerCaseFilter(luceneMatchVersion, in);
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
