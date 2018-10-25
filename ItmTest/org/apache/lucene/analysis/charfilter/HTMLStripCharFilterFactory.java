// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   HTMLStripCharFilterFactory.java

package org.apache.lucene.analysis.charfilter;

import java.io.Reader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.lucene.analysis.util.CharFilterFactory;

// Referenced classes of package org.apache.lucene.analysis.charfilter:
//			HTMLStripCharFilter

public class HTMLStripCharFilterFactory extends CharFilterFactory
{

	Set escapedTags;
	Pattern TAG_NAME_PATTERN;

	public HTMLStripCharFilterFactory()
	{
		escapedTags = null;
		TAG_NAME_PATTERN = Pattern.compile("[^\\s,]+");
	}

	public HTMLStripCharFilter create(Reader input)
	{
		HTMLStripCharFilter charFilter;
		if (null == escapedTags)
			charFilter = new HTMLStripCharFilter(input);
		else
			charFilter = new HTMLStripCharFilter(input, escapedTags);
		return charFilter;
	}

	public void init(Map args)
	{
		super.init(args);
		String escapedTagsArg = (String)args.get("escapedTags");
		if (null != escapedTagsArg)
		{
			for (Matcher matcher = TAG_NAME_PATTERN.matcher(escapedTagsArg); matcher.find(); escapedTags.add(matcher.group(0)))
				if (null == escapedTags)
					escapedTags = new HashSet();

		}
	}

	public volatile Reader create(Reader x0)
	{
		return create(x0);
	}
}
