// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ChineseFilterFactory.java

package org.apache.lucene.analysis.cn;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;

// Referenced classes of package org.apache.lucene.analysis.cn:
//			ChineseFilter

/**
 * @deprecated Class ChineseFilterFactory is deprecated
 */

public class ChineseFilterFactory extends TokenFilterFactory
{

	public ChineseFilterFactory()
	{
	}

	public ChineseFilter create(TokenStream in)
	{
		return new ChineseFilter(in);
	}

	public volatile TokenStream create(TokenStream x0)
	{
		return create(x0);
	}
}
