// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TypeAttribute.java

package org.apache.lucene.analysis.tokenattributes;

import org.apache.lucene.util.Attribute;

public interface TypeAttribute
	extends Attribute
{

	public static final String DEFAULT_TYPE = "word";

	public abstract String type();

	public abstract void setType(String s);
}
