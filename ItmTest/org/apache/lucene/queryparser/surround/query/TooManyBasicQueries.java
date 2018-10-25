// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TooManyBasicQueries.java

package org.apache.lucene.queryparser.surround.query;

import java.io.IOException;

public class TooManyBasicQueries extends IOException
{

	public TooManyBasicQueries(int maxBasicQueries)
	{
		super((new StringBuilder()).append("Exceeded maximum of ").append(maxBasicQueries).append(" basic queries.").toString());
	}
}
