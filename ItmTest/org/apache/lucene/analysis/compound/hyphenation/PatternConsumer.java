// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PatternConsumer.java

package org.apache.lucene.analysis.compound.hyphenation;

import java.util.ArrayList;

public interface PatternConsumer
{

	public abstract void addClass(String s);

	public abstract void addException(String s, ArrayList arraylist);

	public abstract void addPattern(String s, String s1);
}
