// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TokenFilter.java

package org.apache.lucene.analysis;

import java.io.IOException;

// Referenced classes of package org.apache.lucene.analysis:
//			TokenStream

public abstract class TokenFilter extends TokenStream
{

	protected final TokenStream input;

	protected TokenFilter(TokenStream input)
	{
		super(input);
		this.input = input;
	}

	public void end()
		throws IOException
	{
		input.end();
	}

	public void close()
		throws IOException
	{
		input.close();
	}

	public void reset()
		throws IOException
	{
		input.reset();
	}
}
