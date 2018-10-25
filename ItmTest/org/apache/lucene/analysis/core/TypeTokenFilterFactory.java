// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TypeTokenFilterFactory.java

package org.apache.lucene.analysis.core;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.*;

// Referenced classes of package org.apache.lucene.analysis.core:
//			TypeTokenFilter

public class TypeTokenFilterFactory extends TokenFilterFactory
	implements ResourceLoaderAware
{

	private boolean useWhitelist;
	private Set stopTypes;
	private boolean enablePositionIncrements;

	public TypeTokenFilterFactory()
	{
	}

	public void inform(ResourceLoader loader)
		throws IOException
	{
		String stopTypesFiles = (String)args.get("types");
		enablePositionIncrements = getBoolean("enablePositionIncrements", false);
		useWhitelist = getBoolean("useWhitelist", false);
		if (stopTypesFiles != null)
		{
			List files = splitFileNames(stopTypesFiles);
			if (files.size() > 0)
			{
				stopTypes = new HashSet();
				List typesLines;
				for (Iterator i$ = files.iterator(); i$.hasNext(); stopTypes.addAll(typesLines))
				{
					String file = (String)i$.next();
					typesLines = getLines(loader, file.trim());
				}

			}
		} else
		{
			throw new IllegalArgumentException("Missing required parameter: types.");
		}
	}

	public boolean isEnablePositionIncrements()
	{
		return enablePositionIncrements;
	}

	public Set getStopTypes()
	{
		return stopTypes;
	}

	public TokenStream create(TokenStream input)
	{
		return new TypeTokenFilter(enablePositionIncrements, input, stopTypes, useWhitelist);
	}
}
