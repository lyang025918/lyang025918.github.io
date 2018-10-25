// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BoostAttributeImpl.java

package org.apache.lucene.search;

import org.apache.lucene.util.AttributeImpl;

// Referenced classes of package org.apache.lucene.search:
//			BoostAttribute

public final class BoostAttributeImpl extends AttributeImpl
	implements BoostAttribute
{

	private float boost;

	public BoostAttributeImpl()
	{
		boost = 1.0F;
	}

	public void setBoost(float boost)
	{
		this.boost = boost;
	}

	public float getBoost()
	{
		return boost;
	}

	public void clear()
	{
		boost = 1.0F;
	}

	public void copyTo(AttributeImpl target)
	{
		((BoostAttribute)target).setBoost(boost);
	}
}
