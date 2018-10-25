// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FieldDoc.java

package org.apache.lucene.search;


// Referenced classes of package org.apache.lucene.search:
//			ScoreDoc

public class FieldDoc extends ScoreDoc
{

	public Object fields[];

	public FieldDoc(int doc, float score)
	{
		super(doc, score);
	}

	public FieldDoc(int doc, float score, Object fields[])
	{
		super(doc, score);
		this.fields = fields;
	}

	public FieldDoc(int doc, float score, Object fields[], int shardIndex)
	{
		super(doc, score, shardIndex);
		this.fields = fields;
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder(super.toString());
		sb.append("[");
		for (int i = 0; i < fields.length; i++)
			sb.append(fields[i]).append(", ");

		sb.setLength(sb.length() - 2);
		sb.append("]");
		return sb.toString();
	}
}
