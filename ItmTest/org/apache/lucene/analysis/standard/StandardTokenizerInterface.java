// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   StandardTokenizerInterface.java

package org.apache.lucene.analysis.standard;

import java.io.IOException;
import java.io.Reader;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public interface StandardTokenizerInterface
{

	public static final int YYEOF = -1;

	public abstract void getText(CharTermAttribute chartermattribute);

	public abstract int yychar();

	public abstract void yyreset(Reader reader);

	public abstract int yylength();

	public abstract int getNextToken()
		throws IOException;
}
