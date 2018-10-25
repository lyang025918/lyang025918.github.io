// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PrefixQuery.java

package org.apache.lucene.search;

import java.io.IOException;
import org.apache.lucene.index.*;
import org.apache.lucene.util.*;

// Referenced classes of package org.apache.lucene.search:
//			MultiTermQuery, PrefixTermsEnum

public class PrefixQuery extends MultiTermQuery
{

	private Term prefix;

	public PrefixQuery(Term prefix)
	{
		super(prefix.field());
		this.prefix = prefix;
	}

	public Term getPrefix()
	{
		return prefix;
	}

	protected TermsEnum getTermsEnum(Terms terms, AttributeSource atts)
		throws IOException
	{
		TermsEnum tenum = terms.iterator(null);
		if (prefix.bytes().length == 0)
			return tenum;
		else
			return new PrefixTermsEnum(tenum, prefix.bytes());
	}

	public String toString(String field)
	{
		StringBuilder buffer = new StringBuilder();
		if (!getField().equals(field))
		{
			buffer.append(getField());
			buffer.append(":");
		}
		buffer.append(prefix.text());
		buffer.append('*');
		buffer.append(ToStringUtils.boost(getBoost()));
		return buffer.toString();
	}

	public int hashCode()
	{
		int prime = 31;
		int result = super.hashCode();
		result = 31 * result + (prefix != null ? prefix.hashCode() : 0);
		return result;
	}

	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PrefixQuery other = (PrefixQuery)obj;
		if (prefix == null)
		{
			if (other.prefix != null)
				return false;
		} else
		if (!prefix.equals(other.prefix))
			return false;
		return true;
	}
}
