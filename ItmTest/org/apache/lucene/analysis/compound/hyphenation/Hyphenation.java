// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Hyphenation.java

package org.apache.lucene.analysis.compound.hyphenation;


public class Hyphenation
{

	private int hyphenPoints[];

	Hyphenation(int points[])
	{
		hyphenPoints = points;
	}

	public int length()
	{
		return hyphenPoints.length;
	}

	public int[] getHyphenationPoints()
	{
		return hyphenPoints;
	}
}
