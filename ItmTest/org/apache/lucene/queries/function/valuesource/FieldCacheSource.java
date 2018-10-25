// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FieldCacheSource.java

package org.apache.lucene.queries.function.valuesource;

import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.search.FieldCache;

public abstract class FieldCacheSource extends ValueSource
{

	protected final String field;
	protected final FieldCache cache;

	public FieldCacheSource(String field)
	{
		cache = FieldCache.DEFAULT;
		this.field = field;
	}

	public FieldCache getFieldCache()
	{
		return cache;
	}

	public String getField()
	{
		return field;
	}

	public String description()
	{
		return field;
	}

	public boolean equals(Object o)
	{
		if (!(o instanceof FieldCacheSource))
		{
			return false;
		} else
		{
			FieldCacheSource other = (FieldCacheSource)o;
			return field.equals(other.field) && cache == other.cache;
		}
	}

	public int hashCode()
	{
		return cache.hashCode() + field.hashCode();
	}
}
