// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   HunspellAffix.java

package org.apache.lucene.analysis.hunspell;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HunspellAffix
{

	private String append;
	private char appendFlags[];
	private String strip;
	private String condition;
	private Pattern conditionPattern;
	private char flag;
	private boolean crossProduct;

	public HunspellAffix()
	{
	}

	public boolean checkCondition(CharSequence text)
	{
		return conditionPattern.matcher(text).matches();
	}

	public String getAppend()
	{
		return append;
	}

	public void setAppend(String append)
	{
		this.append = append;
	}

	public char[] getAppendFlags()
	{
		return appendFlags;
	}

	public void setAppendFlags(char appendFlags[])
	{
		this.appendFlags = appendFlags;
	}

	public String getStrip()
	{
		return strip;
	}

	public void setStrip(String strip)
	{
		this.strip = strip;
	}

	public String getCondition()
	{
		return condition;
	}

	public void setCondition(String condition, String pattern)
	{
		this.condition = condition;
		conditionPattern = Pattern.compile(pattern);
	}

	public char getFlag()
	{
		return flag;
	}

	public void setFlag(char flag)
	{
		this.flag = flag;
	}

	public boolean isCrossProduct()
	{
		return crossProduct;
	}

	public void setCrossProduct(boolean crossProduct)
	{
		this.crossProduct = crossProduct;
	}
}
