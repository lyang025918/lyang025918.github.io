// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PositionLengthAttribute.java

package org.apache.lucene.analysis.tokenattributes;

import org.apache.lucene.util.Attribute;

public interface PositionLengthAttribute
	extends Attribute
{

	public abstract void setPositionLength(int i);

	public abstract int getPositionLength();
}
