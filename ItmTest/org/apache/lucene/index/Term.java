// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Term.java

package org.apache.lucene.index;

import org.apache.lucene.util.BytesRef;

public final class Term
	implements Comparable
{

	String field;
	BytesRef bytes;

	public Term(String fld, BytesRef bytes)
	{
		field = fld;
		this.bytes = bytes;
	}

	public Term(String fld, String text)
	{
		this(fld, new BytesRef(text));
	}

	public Term(String fld)
	{
		this(fld, new BytesRef());
	}

	public final String field()
	{
		return field;
	}

	public final String text()
	{
		return bytes.utf8ToString();
	}

	public final BytesRef bytes()
	{
		return bytes;
	}

	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Term other = (Term)obj;
		if (field == null)
		{
			if (other.field != null)
				return false;
		} else
		if (!field.equals(other.field))
			return false;
		if (bytes == null)
		{
			if (other.bytes != null)
				return false;
		} else
		if (!bytes.equals(other.bytes))
			return false;
		return true;
	}

	public int hashCode()
	{
		int prime = 31;
		int result = 1;
		result = 31 * result + (field != null ? field.hashCode() : 0);
		result = 31 * result + (bytes != null ? bytes.hashCode() : 0);
		return result;
	}

	public final int compareTo(Term other)
	{
		if (field.equals(other.field))
			return bytes.compareTo(other.bytes);
		else
			return field.compareTo(other.field);
	}

	final void set(String fld, BytesRef bytes)
	{
		field = fld;
		this.bytes = bytes;
	}

	public final String toString()
	{
		return (new StringBuilder()).append(field).append(":").append(bytes.utf8ToString()).toString();
	}

	public volatile int compareTo(Object x0)
	{
		return compareTo((Term)x0);
	}
}
