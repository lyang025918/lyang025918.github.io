// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Sort.java

package org.apache.lucene.search;

import java.io.IOException;
import java.util.Arrays;

// Referenced classes of package org.apache.lucene.search:
//			SortField, IndexSearcher

public class Sort
{

	public static final Sort RELEVANCE = new Sort();
	public static final Sort INDEXORDER;
	SortField fields[];

	public Sort()
	{
		this(SortField.FIELD_SCORE);
	}

	public Sort(SortField field)
	{
		setSort(field);
	}

	public transient Sort(SortField fields[])
	{
		setSort(fields);
	}

	public void setSort(SortField field)
	{
		fields = (new SortField[] {
			field
		});
	}

	public transient void setSort(SortField fields[])
	{
		this.fields = fields;
	}

	public SortField[] getSort()
	{
		return fields;
	}

	public Sort rewrite(IndexSearcher searcher)
		throws IOException
	{
		boolean changed = false;
		SortField rewrittenSortFields[] = new SortField[fields.length];
		for (int i = 0; i < fields.length; i++)
		{
			rewrittenSortFields[i] = fields[i].rewrite(searcher);
			if (fields[i] != rewrittenSortFields[i])
				changed = true;
		}

		return changed ? new Sort(rewrittenSortFields) : this;
	}

	public String toString()
	{
		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i < fields.length; i++)
		{
			buffer.append(fields[i].toString());
			if (i + 1 < fields.length)
				buffer.append(',');
		}

		return buffer.toString();
	}

	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (!(o instanceof Sort))
		{
			return false;
		} else
		{
			Sort other = (Sort)o;
			return Arrays.equals(fields, other.fields);
		}
	}

	public int hashCode()
	{
		return 0x45aaf665 + Arrays.hashCode(fields);
	}

	static 
	{
		INDEXORDER = new Sort(SortField.FIELD_DOC);
	}
}
