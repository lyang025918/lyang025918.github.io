// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Hyphen.java

package org.apache.lucene.analysis.compound.hyphenation;


public class Hyphen
{

	public String preBreak;
	public String noBreak;
	public String postBreak;

	Hyphen(String pre, String no, String post)
	{
		preBreak = pre;
		noBreak = no;
		postBreak = post;
	}

	Hyphen(String pre)
	{
		preBreak = pre;
		noBreak = null;
		postBreak = null;
	}

	public String toString()
	{
		if (noBreak == null && postBreak == null && preBreak != null && preBreak.equals("-"))
		{
			return "-";
		} else
		{
			StringBuilder res = new StringBuilder("{");
			res.append(preBreak);
			res.append("}{");
			res.append(postBreak);
			res.append("}{");
			res.append(noBreak);
			res.append('}');
			return res.toString();
		}
	}
}
