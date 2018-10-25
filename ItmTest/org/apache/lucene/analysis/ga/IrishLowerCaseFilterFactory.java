// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IrishLowerCaseFilterFactory.java

package org.apache.lucene.analysis.ga;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.*;

// Referenced classes of package org.apache.lucene.analysis.ga:
//			IrishLowerCaseFilter

public class IrishLowerCaseFilterFactory extends TokenFilterFactory
	implements MultiTermAwareComponent
{

	public IrishLowerCaseFilterFactory()
	{
	}

	public TokenStream create(TokenStream input)
	{
		return new IrishLowerCaseFilter(input);
	}

	public AbstractAnalysisFactory getMultiTermComponent()
	{
		return this;
	}
}
