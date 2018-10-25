// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ComplexExplanation.java

package org.apache.lucene.search;


// Referenced classes of package org.apache.lucene.search:
//			Explanation

public class ComplexExplanation extends Explanation
{

	private Boolean match;

	public ComplexExplanation()
	{
	}

	public ComplexExplanation(boolean match, float value, String description)
	{
		super(value, description);
		this.match = Boolean.valueOf(match);
	}

	public Boolean getMatch()
	{
		return match;
	}

	public void setMatch(Boolean match)
	{
		this.match = match;
	}

	public boolean isMatch()
	{
		Boolean m = getMatch();
		return null == m ? super.isMatch() : m.booleanValue();
	}

	protected String getSummary()
	{
		if (null == getMatch())
			return super.getSummary();
		else
			return (new StringBuilder()).append(getValue()).append(" = ").append(isMatch() ? "(MATCH) " : "(NON-MATCH) ").append(getDescription()).toString();
	}
}
