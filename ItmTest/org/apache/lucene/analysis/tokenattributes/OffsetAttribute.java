// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   OffsetAttribute.java

package org.apache.lucene.analysis.tokenattributes;

import org.apache.lucene.util.Attribute;

public interface OffsetAttribute
	extends Attribute
{

	public abstract int startOffset();

	public abstract void setOffset(int i, int j);

	public abstract int endOffset();
}
