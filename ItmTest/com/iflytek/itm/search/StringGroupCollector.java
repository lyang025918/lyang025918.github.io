// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   StringGroupCollector.java

package com.iflytek.itm.search;

import java.io.IOException;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.search.FieldCache;
import org.apache.lucene.util.BytesRef;

// Referenced classes of package com.iflytek.itm.search:
//			GroupCollector

public class StringGroupCollector extends GroupCollector
{

	public org.apache.lucene.search.FieldCache.DocTerms docTerms;
	public BytesRef bytesRef;

	public StringGroupCollector(String groupField, String groups[])
	{
		super(groupField, groups);
		docTerms = null;
		bytesRef = new BytesRef(1024);
	}

	public void setNextReader(AtomicReaderContext context)
		throws IOException
	{
		org.apache.lucene.index.AtomicReader ar = context.reader();
		docTerms = FieldCache.DEFAULT.getTerms(ar, name);
		docBase = context.docBase;
	}

	public void collect(int doc)
		throws IOException
	{
		int docId = doc + docBase;
		bytesRef = docTerms.getTerm(doc, bytesRef);
		String value = bytesRef.utf8ToString();
		if (scopeValues == null)
		{
			addValue(value);
		} else
		{
			String strValue = scopeValues.strValue(value);
			if (strValue != null)
				addValue(strValue);
		}
		totalDocCount++;
	}
}
