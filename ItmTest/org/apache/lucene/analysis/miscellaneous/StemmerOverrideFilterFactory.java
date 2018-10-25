// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   StemmerOverrideFilterFactory.java

package org.apache.lucene.analysis.miscellaneous;

import java.io.IOException;
import java.util.*;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.*;

// Referenced classes of package org.apache.lucene.analysis.miscellaneous:
//			StemmerOverrideFilter

public class StemmerOverrideFilterFactory extends TokenFilterFactory
	implements ResourceLoaderAware
{

	private CharArrayMap dictionary;
	private boolean ignoreCase;

	public StemmerOverrideFilterFactory()
	{
		dictionary = null;
	}

	public void inform(ResourceLoader loader)
		throws IOException
	{
		String dictionaryFiles = (String)args.get("dictionary");
		ignoreCase = getBoolean("ignoreCase", false);
		if (dictionaryFiles != null)
		{
			assureMatchVersion();
			List files = splitFileNames(dictionaryFiles);
			if (files.size() > 0)
			{
				dictionary = new CharArrayMap(luceneMatchVersion, files.size() * 10, ignoreCase);
				for (Iterator i$ = files.iterator(); i$.hasNext();)
				{
					String file = (String)i$.next();
					List list = getLines(loader, file.trim());
					Iterator i$ = list.iterator();
					while (i$.hasNext()) 
					{
						String line = (String)i$.next();
						String mapping[] = line.split("\t", 2);
						dictionary.put(mapping[0], mapping[1]);
					}
				}

			}
		}
	}

	public boolean isIgnoreCase()
	{
		return ignoreCase;
	}

	public TokenStream create(TokenStream input)
	{
		return ((TokenStream) (dictionary != null ? new StemmerOverrideFilter(luceneMatchVersion, input, dictionary) : input));
	}
}
