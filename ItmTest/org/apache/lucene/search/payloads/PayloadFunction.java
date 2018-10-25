// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PayloadFunction.java

package org.apache.lucene.search.payloads;

import org.apache.lucene.search.Explanation;

public abstract class PayloadFunction
{

	public PayloadFunction()
	{
	}

	public abstract float currentScore(int i, String s, int j, int k, int l, float f, float f1);

	public abstract float docScore(int i, String s, int j, float f);

	public Explanation explain(int docId, String field, int numPayloadsSeen, float payloadScore)
	{
		Explanation result = new Explanation();
		result.setDescription((new StringBuilder()).append(getClass().getSimpleName()).append(".docScore()").toString());
		result.setValue(docScore(docId, field, numPayloadsSeen, payloadScore));
		return result;
	}

	public abstract int hashCode();

	public abstract boolean equals(Object obj);
}
