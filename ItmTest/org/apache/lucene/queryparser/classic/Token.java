// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Token.java

package org.apache.lucene.queryparser.classic;

import java.io.Serializable;

public class Token
	implements Serializable
{

	private static final long serialVersionUID = 1L;
	public int kind;
	public int beginLine;
	public int beginColumn;
	public int endLine;
	public int endColumn;
	public String image;
	public Token next;
	public Token specialToken;

	public Object getValue()
	{
		return null;
	}

	public Token()
	{
	}

	public Token(int kind)
	{
		this(kind, null);
	}

	public Token(int kind, String image)
	{
		this.kind = kind;
		this.image = image;
	}

	public String toString()
	{
		return image;
	}

	public static Token newToken(int ofKind, String image)
	{
		switch (ofKind)
		{
		default:
			return new Token(ofKind, image);
		}
	}

	public static Token newToken(int ofKind)
	{
		return newToken(ofKind, null);
	}
}
