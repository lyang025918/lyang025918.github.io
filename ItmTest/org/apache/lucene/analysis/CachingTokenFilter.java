// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CachingTokenFilter.java

package org.apache.lucene.analysis;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.util.AttributeSource;

// Referenced classes of package org.apache.lucene.analysis:
//			TokenFilter, TokenStream

public final class CachingTokenFilter extends TokenFilter
{

	private List cache;
	private Iterator iterator;
	private org.apache.lucene.util.AttributeSource.State finalState;

	public CachingTokenFilter(TokenStream input)
	{
		super(input);
		cache = null;
		iterator = null;
	}

	public final boolean incrementToken()
		throws IOException
	{
		if (cache == null)
		{
			cache = new LinkedList();
			fillCache();
			iterator = cache.iterator();
		}
		if (!iterator.hasNext())
		{
			return false;
		} else
		{
			restoreState((org.apache.lucene.util.AttributeSource.State)iterator.next());
			return true;
		}
	}

	public final void end()
	{
		if (finalState != null)
			restoreState(finalState);
	}

	public void reset()
	{
		if (cache != null)
			iterator = cache.iterator();
	}

	private void fillCache()
		throws IOException
	{
		for (; input.incrementToken(); cache.add(captureState()));
		input.end();
		finalState = captureState();
	}
}
