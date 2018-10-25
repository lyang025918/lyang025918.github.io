// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IntGroupCollector.java

package com.iflytek.itm.search;

import java.io.IOException;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.search.FieldCache;

// Referenced classes of package com.iflytek.itm.search:
//			GroupCollector

public class IntGroupCollector extends GroupCollector
{

	public int docValues[];

	public IntGroupCollector(String groupField, String groups[])
	{
		super(groupField, groups);
		docValues = null;
	}

	public void setNextReader(AtomicReaderContext context)
		throws IOException
	{
		org.apache.lucene.index.AtomicReader ar = context.reader();
		docValues = FieldCache.DEFAULT.getInts(ar, name, false);
		docBase = context.docBase;
	}

	public void collect(int doc)
		throws IOException
	{
		int docId = doc + docBase;
		int value = docValues[doc];
		if (scopeValues == null)
		{
			addValue(String.valueOf(value));
		} else
		{
			String strValue = scopeValues.intValue(value);
			if (strValue != null)
				addValue(strValue);
		}
		totalDocCount++;
	}
}
