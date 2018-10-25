// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BoostAttribute.java

package org.apache.lucene.search;

import org.apache.lucene.util.Attribute;

public interface BoostAttribute
	extends Attribute
{

	public abstract void setBoost(float f);

	public abstract float getBoost();
}
