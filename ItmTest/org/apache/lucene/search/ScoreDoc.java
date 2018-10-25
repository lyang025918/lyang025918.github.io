// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ScoreDoc.java

package org.apache.lucene.search;


public class ScoreDoc
{

	public float score;
	public int doc;
	public int shardIndex;

	public ScoreDoc(int doc, float score)
	{
		this(doc, score, -1);
	}

	public ScoreDoc(int doc, float score, int shardIndex)
	{
		this.doc = doc;
		this.score = score;
		this.shardIndex = shardIndex;
	}

	public String toString()
	{
		return (new StringBuilder()).append("doc=").append(doc).append(" score=").append(score).append(" shardIndex=").append(shardIndex).toString();
	}
}
