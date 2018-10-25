// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TermRangeQuery.java

package org.apache.lucene.search;

import java.io.IOException;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.util.*;

// Referenced classes of package org.apache.lucene.search:
//			MultiTermQuery, TermRangeTermsEnum

public class TermRangeQuery extends MultiTermQuery
{

	private BytesRef lowerTerm;
	private BytesRef upperTerm;
	private boolean includeLower;
	private boolean includeUpper;

	public TermRangeQuery(String field, BytesRef lowerTerm, BytesRef upperTerm, boolean includeLower, boolean includeUpper)
	{
		super(field);
		this.lowerTerm = lowerTerm;
		this.upperTerm = upperTerm;
		this.includeLower = includeLower;
		this.includeUpper = includeUpper;
	}

	public static TermRangeQuery newStringRange(String field, String lowerTerm, String upperTerm, boolean includeLower, boolean includeUpper)
	{
		BytesRef lower = lowerTerm != null ? new BytesRef(lowerTerm) : null;
		BytesRef upper = upperTerm != null ? new BytesRef(upperTerm) : null;
		return new TermRangeQuery(field, lower, upper, includeLower, includeUpper);
	}

	public BytesRef getLowerTerm()
	{
		return lowerTerm;
	}

	public BytesRef getUpperTerm()
	{
		return upperTerm;
	}

	public boolean includesLower()
	{
		return includeLower;
	}

	public boolean includesUpper()
	{
		return includeUpper;
	}

	protected TermsEnum getTermsEnum(Terms terms, AttributeSource atts)
		throws IOException
	{
		if (lowerTerm != null && upperTerm != null && lowerTerm.compareTo(upperTerm) > 0)
			return TermsEnum.EMPTY;
		TermsEnum tenum = terms.iterator(null);
		if ((lowerTerm == null || includeLower && lowerTerm.length == 0) && upperTerm == null)
			return tenum;
		else
			return new TermRangeTermsEnum(tenum, lowerTerm, upperTerm, includeLower, includeUpper);
	}

	public String toString(String field)
	{
		StringBuilder buffer = new StringBuilder();
		if (!getField().equals(field))
		{
			buffer.append(getField());
			buffer.append(":");
		}
		buffer.append(includeLower ? '[' : '{');
		buffer.append(lowerTerm == null ? "*" : "*".equals(lowerTerm.utf8ToString()) ? "\\*" : lowerTerm.utf8ToString());
		buffer.append(" TO ");
		buffer.append(upperTerm == null ? "*" : "*".equals(upperTerm.utf8ToString()) ? "\\*" : upperTerm.utf8ToString());
		buffer.append(includeUpper ? ']' : '}');
		buffer.append(ToStringUtils.boost(getBoost()));
		return buffer.toString();
	}

	public int hashCode()
	{
		int prime = 31;
		int result = super.hashCode();
		result = 31 * result + (includeLower ? 1231 : '\u04D5');
		result = 31 * result + (includeUpper ? 1231 : '\u04D5');
		result = 31 * result + (lowerTerm != null ? lowerTerm.hashCode() : 0);
		result = 31 * result + (upperTerm != null ? upperTerm.hashCode() : 0);
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
		TermRangeQuery other = (TermRangeQuery)obj;
		if (includeLower != other.includeLower)
			return false;
		if (includeUpper != other.includeUpper)
			return false;
		if (lowerTerm == null)
		{
			if (other.lowerTerm != null)
				return false;
		} else
		if (!lowerTerm.equals(other.lowerTerm))
			return false;
		if (upperTerm == null)
		{
			if (other.upperTerm != null)
				return false;
		} else
		if (!upperTerm.equals(other.upperTerm))
			return false;
		return true;
	}
}
