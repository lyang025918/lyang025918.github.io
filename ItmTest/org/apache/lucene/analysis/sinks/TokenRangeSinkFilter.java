// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TokenRangeSinkFilter.java

package org.apache.lucene.analysis.sinks;

import java.io.IOException;
import org.apache.lucene.util.AttributeSource;

// Referenced classes of package org.apache.lucene.analysis.sinks:
//			TeeSinkTokenFilter

public class TokenRangeSinkFilter extends TeeSinkTokenFilter.SinkFilter
{

	private int lower;
	private int upper;
	private int count;

	public TokenRangeSinkFilter(int lower, int upper)
	{
		this.lower = lower;
		this.upper = upper;
	}

	public boolean accept(AttributeSource source)
	{
		boolean flag;
		if (count < lower || count >= upper)
			break MISSING_BLOCK_LABEL_36;
		flag = true;
		count++;
		return flag;
		flag = false;
		count++;
		return flag;
		Exception exception;
		exception;
		count++;
		throw exception;
	}

	public void reset()
		throws IOException
	{
		count = 0;
	}
}
