// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ParserExtension.java

package org.apache.lucene.queryparser.ext;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.Query;

// Referenced classes of package org.apache.lucene.queryparser.ext:
//			ExtensionQuery

public abstract class ParserExtension
{

	public ParserExtension()
	{
	}

	public abstract Query parse(ExtensionQuery extensionquery)
		throws ParseException;
}
