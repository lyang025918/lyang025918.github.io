// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MStringGroupCollector.java

package com.iflytek.itm.search;

import java.io.IOException;
import org.apache.lucene.index.*;
import org.apache.lucene.search.FieldCache;
import org.apache.lucene.util.BytesRef;

// Referenced classes of package com.iflytek.itm.search:
//			GroupCollector

public class MStringGroupCollector extends GroupCollector
{

	private DocTermOrds doctermOrds;
	private int buffer[];
	private TermsEnum termsEnum;
	public BytesRef bytesRef;

	public MStringGroupCollector(String groupField, String groups[])
	{
		super(groupField, groups);
		doctermOrds = null;
		buffer = null;
		termsEnum = null;
		bytesRef = new BytesRef(1024);
	}

	public void setNextReader(AtomicReaderContext context)
		throws IOException
	{
		org.apache.lucene.index.AtomicReader ar = context.reader();
		doctermOrds = FieldCache.DEFAULT.getDocTermOrds(ar, name);
		termsEnum = doctermOrds.getOrdTermsEnum(ar);
		int num = doctermOrds.numTerms();
		buffer = null;
		buffer = new int[num];
		docBase = context.docBase;
	}

	public void collect(int doc)
		throws IOException
	{
		try
		{
			int docId = doc + docBase;
			org.apache.lucene.index.DocTermOrds.TermOrdsIterator it = null;
			it = doctermOrds.lookup(doc, it);
			int chunk = 0;
			chunk = it.read(buffer);
			for (int idx = 0; idx < chunk; idx++)
			{
				int key = buffer[idx];
				termsEnum.seekExact(key);
				bytesRef = termsEnum.term();
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
			}

		}
		catch (Exception e) { }
		totalDocCount++;
	}
}
