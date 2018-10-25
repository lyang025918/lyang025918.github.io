// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PositionFilterFactory.java

package org.apache.lucene.analysis.position;

import java.util.Map;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;

// Referenced classes of package org.apache.lucene.analysis.position:
//			PositionFilter

public class PositionFilterFactory extends TokenFilterFactory
{

	private int positionIncrement;

	public PositionFilterFactory()
	{
	}

	public void init(Map args)
	{
		super.init(args);
		positionIncrement = getInt("positionIncrement", 0);
	}

	public PositionFilter create(TokenStream input)
	{
		return new PositionFilter(input, positionIncrement);
	}

	public volatile TokenStream create(TokenStream x0)
	{
		return create(x0);
	}
}
