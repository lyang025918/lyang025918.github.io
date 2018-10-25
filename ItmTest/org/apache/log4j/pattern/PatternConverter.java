// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PatternConverter.java

package org.apache.log4j.pattern;


public abstract class PatternConverter
{

	private final String name;
	private final String style;

	protected PatternConverter(String name, String style)
	{
		this.name = name;
		this.style = style;
	}

	public abstract void format(Object obj, StringBuffer stringbuffer);

	public final String getName()
	{
		return name;
	}

	public String getStyleClass(Object e)
	{
		return style;
	}
}
