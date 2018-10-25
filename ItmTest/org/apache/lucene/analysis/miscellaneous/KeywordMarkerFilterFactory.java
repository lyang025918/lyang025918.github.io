// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   KeywordMarkerFilterFactory.java

package org.apache.lucene.analysis.miscellaneous;

import java.io.IOException;
import java.util.Map;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.*;

// Referenced classes of package org.apache.lucene.analysis.miscellaneous:
//			KeywordMarkerFilter

public class KeywordMarkerFilterFactory extends TokenFilterFactory
	implements ResourceLoaderAware
{

	public static final String PROTECTED_TOKENS = "protected";
	private CharArraySet protectedWords;
	private boolean ignoreCase;

	public KeywordMarkerFilterFactory()
	{
	}

	public void inform(ResourceLoader loader)
		throws IOException
	{
		String wordFiles = (String)args.get("protected");
		ignoreCase = getBoolean("ignoreCase", false);
		if (wordFiles != null)
			protectedWords = getWordSet(loader, wordFiles, ignoreCase);
	}

	public boolean isIgnoreCase()
	{
		return ignoreCase;
	}

	public TokenStream create(TokenStream input)
	{
		return ((TokenStream) (protectedWords != null ? new KeywordMarkerFilter(input, protectedWords) : input));
	}
}
