// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Hit.java

package org.wltea.analyzer.dic;


// Referenced classes of package org.wltea.analyzer.dic:
//			DictSegment

public class Hit
{

	private static final int UNMATCH = 0;
	private static final int MATCH = 1;
	private static final int PREFIX = 16;
	private int hitState;
	private DictSegment matchedDictSegment;
	private int begin;
	private int end;

	public Hit()
	{
		hitState = 0;
	}

	public boolean isMatch()
	{
		return (hitState & 1) > 0;
	}

	public void setMatch()
	{
		hitState = hitState | 1;
	}

	public boolean isPrefix()
	{
		return (hitState & 0x10) > 0;
	}

	public void setPrefix()
	{
		hitState = hitState | 0x10;
	}

	public boolean isUnmatch()
	{
		return hitState == 0;
	}

	public void setUnmatch()
	{
		hitState = 0;
	}

	public DictSegment getMatchedDictSegment()
	{
		return matchedDictSegment;
	}

	public void setMatchedDictSegment(DictSegment matchedDictSegment)
	{
		this.matchedDictSegment = matchedDictSegment;
	}

	public int getBegin()
	{
		return begin;
	}

	public void setBegin(int begin)
	{
		this.begin = begin;
	}

	public int getEnd()
	{
		return end;
	}

	public void setEnd(int end)
	{
		this.end = end;
	}
}
