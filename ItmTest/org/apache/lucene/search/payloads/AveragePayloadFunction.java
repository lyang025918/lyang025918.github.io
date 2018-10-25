// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AveragePayloadFunction.java

package org.apache.lucene.search.payloads;


// Referenced classes of package org.apache.lucene.search.payloads:
//			PayloadFunction

public class AveragePayloadFunction extends PayloadFunction
{

	public AveragePayloadFunction()
	{
	}

	public float currentScore(int docId, String field, int start, int end, int numPayloadsSeen, float currentScore, float currentPayloadScore)
	{
		return currentPayloadScore + currentScore;
	}

	public float docScore(int docId, String field, int numPayloadsSeen, float payloadScore)
	{
		return numPayloadsSeen <= 0 ? 1.0F : payloadScore / (float)numPayloadsSeen;
	}

	public int hashCode()
	{
		int prime = 31;
		int result = 1;
		result = 31 * result + getClass().hashCode();
		return result;
	}

	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		return getClass() == obj.getClass();
	}
}
