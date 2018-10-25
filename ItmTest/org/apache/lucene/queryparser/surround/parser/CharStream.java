// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CharStream.java

package org.apache.lucene.queryparser.surround.parser;

import java.io.IOException;

public interface CharStream
{

	public abstract char readChar()
		throws IOException;

	/**
	 * @deprecated Method getColumn is deprecated
	 */

	public abstract int getColumn();

	/**
	 * @deprecated Method getLine is deprecated
	 */

	public abstract int getLine();

	/**
	 * @deprecated Method getEndColumn is deprecated
	 */

	public abstract int getEndColumn();

	public abstract int getEndLine();

	public abstract int getBeginColumn();

	public abstract int getBeginLine();

	public abstract void backup(int i);

	public abstract char BeginToken()
		throws IOException;

	public abstract String GetImage();

	public abstract char[] GetSuffix(int i);

	public abstract void Done();
}
