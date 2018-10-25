// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MaxNonCompetitiveBoostAttribute.java

package org.apache.lucene.search;

import org.apache.lucene.util.Attribute;
import org.apache.lucene.util.BytesRef;

public interface MaxNonCompetitiveBoostAttribute
	extends Attribute
{

	public abstract void setMaxNonCompetitiveBoost(float f);

	public abstract float getMaxNonCompetitiveBoost();

	public abstract void setCompetitiveTerm(BytesRef bytesref);

	public abstract BytesRef getCompetitiveTerm();
}
