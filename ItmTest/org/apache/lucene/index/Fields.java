// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Fields.java

package org.apache.lucene.index;

import java.io.IOException;
import java.util.Iterator;

// Referenced classes of package org.apache.lucene.index:
//			Terms

public abstract class Fields
	implements Iterable
{

	public static final Fields EMPTY_ARRAY[] = new Fields[0];

	protected Fields()
	{
	}

	public abstract Iterator iterator();

	public abstract Terms terms(String s)
		throws IOException;

	public abstract int size();

	/**
	 * @deprecated Method getUniqueTermCount is deprecated
	 */

	public long getUniqueTermCount()
		throws IOException
	{
		long numTerms = 0L;
		Iterator i$ = iterator();
		do
		{
			if (!i$.hasNext())
				break;
			String field = (String)i$.next();
			Terms terms = terms(field);
			if (terms != null)
			{
				long termCount = terms.size();
				if (termCount == -1L)
					return -1L;
				numTerms += termCount;
			}
		} while (true);
		return numTerms;
	}

}
