// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FieldInvertState.java

package org.apache.lucene.index;

import org.apache.lucene.util.AttributeSource;

public final class FieldInvertState
{

	String name;
	int position;
	int length;
	int numOverlap;
	int offset;
	int maxTermFrequency;
	int uniqueTermCount;
	float boost;
	AttributeSource attributeSource;

	public FieldInvertState(String name)
	{
		this.name = name;
	}

	public FieldInvertState(String name, int position, int length, int numOverlap, int offset, float boost)
	{
		this.name = name;
		this.position = position;
		this.length = length;
		this.numOverlap = numOverlap;
		this.offset = offset;
		this.boost = boost;
	}

	void reset()
	{
		position = 0;
		length = 0;
		numOverlap = 0;
		offset = 0;
		maxTermFrequency = 0;
		uniqueTermCount = 0;
		boost = 1.0F;
		attributeSource = null;
	}

	public int getPosition()
	{
		return position;
	}

	public int getLength()
	{
		return length;
	}

	public void setLength(int length)
	{
		this.length = length;
	}

	public int getNumOverlap()
	{
		return numOverlap;
	}

	public void setNumOverlap(int numOverlap)
	{
		this.numOverlap = numOverlap;
	}

	public int getOffset()
	{
		return offset;
	}

	public float getBoost()
	{
		return boost;
	}

	public void setBoost(float boost)
	{
		this.boost = boost;
	}

	public int getMaxTermFrequency()
	{
		return maxTermFrequency;
	}

	public int getUniqueTermCount()
	{
		return uniqueTermCount;
	}

	public AttributeSource getAttributeSource()
	{
		return attributeSource;
	}

	public String getName()
	{
		return name;
	}
}
