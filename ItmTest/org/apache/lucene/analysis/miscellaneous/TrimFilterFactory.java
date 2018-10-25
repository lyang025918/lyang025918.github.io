// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TrimFilterFactory.java

package org.apache.lucene.analysis.miscellaneous;

import java.util.Map;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;

// Referenced classes of package org.apache.lucene.analysis.miscellaneous:
//			TrimFilter

public class TrimFilterFactory extends TokenFilterFactory
{

	protected boolean updateOffsets;

	public TrimFilterFactory()
	{
		updateOffsets = false;
	}

	public void init(Map args)
	{
		super.init(args);
		String v = (String)args.get("updateOffsets");
		if (v != null)
			updateOffsets = Boolean.valueOf(v).booleanValue();
	}

	public TrimFilter create(TokenStream input)
	{
		return new TrimFilter(input, updateOffsets);
	}

	public volatile TokenStream create(TokenStream x0)
	{
		return create(x0);
	}
}
