// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MaxNonCompetitiveBoostAttributeImpl.java

package org.apache.lucene.search;

import org.apache.lucene.util.AttributeImpl;
import org.apache.lucene.util.BytesRef;

// Referenced classes of package org.apache.lucene.search:
//			MaxNonCompetitiveBoostAttribute

public final class MaxNonCompetitiveBoostAttributeImpl extends AttributeImpl
	implements MaxNonCompetitiveBoostAttribute
{

	private float maxNonCompetitiveBoost;
	private BytesRef competitiveTerm;

	public MaxNonCompetitiveBoostAttributeImpl()
	{
		maxNonCompetitiveBoost = (-1.0F / 0.0F);
		competitiveTerm = null;
	}

	public void setMaxNonCompetitiveBoost(float maxNonCompetitiveBoost)
	{
		this.maxNonCompetitiveBoost = maxNonCompetitiveBoost;
	}

	public float getMaxNonCompetitiveBoost()
	{
		return maxNonCompetitiveBoost;
	}

	public void setCompetitiveTerm(BytesRef competitiveTerm)
	{
		this.competitiveTerm = competitiveTerm;
	}

	public BytesRef getCompetitiveTerm()
	{
		return competitiveTerm;
	}

	public void clear()
	{
		maxNonCompetitiveBoost = (-1.0F / 0.0F);
		competitiveTerm = null;
	}

	public void copyTo(AttributeImpl target)
	{
		MaxNonCompetitiveBoostAttributeImpl t = (MaxNonCompetitiveBoostAttributeImpl)target;
		t.setMaxNonCompetitiveBoost(maxNonCompetitiveBoost);
		t.setCompetitiveTerm(competitiveTerm);
	}
}
