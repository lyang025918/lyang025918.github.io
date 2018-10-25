// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CharTermAttribute.java

package org.apache.lucene.analysis.tokenattributes;

import org.apache.lucene.util.Attribute;

public interface CharTermAttribute
	extends Attribute, CharSequence, Appendable
{

	public abstract void copyBuffer(char ac[], int i, int j);

	public abstract char[] buffer();

	public abstract char[] resizeBuffer(int i);

	public abstract CharTermAttribute setLength(int i);

	public abstract CharTermAttribute setEmpty();

	public abstract CharTermAttribute append(CharSequence charsequence);

	public abstract CharTermAttribute append(CharSequence charsequence, int i, int j);

	public abstract CharTermAttribute append(char c);

	public abstract CharTermAttribute append(String s);

	public abstract CharTermAttribute append(StringBuilder stringbuilder);

	public abstract CharTermAttribute append(CharTermAttribute chartermattribute);
}
