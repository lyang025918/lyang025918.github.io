// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MappingCharFilterFactory.java

package org.apache.lucene.analysis.charfilter;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.lucene.analysis.util.*;

// Referenced classes of package org.apache.lucene.analysis.charfilter:
//			MappingCharFilter, NormalizeCharMap

public class MappingCharFilterFactory extends CharFilterFactory
	implements ResourceLoaderAware, MultiTermAwareComponent
{

	protected NormalizeCharMap normMap;
	private String mapping;
	static Pattern p = Pattern.compile("\"(.*)\"\\s*=>\\s*\"(.*)\"\\s*$");
	char out[];

	public MappingCharFilterFactory()
	{
		out = new char[256];
	}

	public void inform(ResourceLoader loader)
		throws IOException
	{
		mapping = (String)args.get("mapping");
		if (mapping != null)
		{
			List wlist = null;
			File mappingFile = new File(mapping);
			if (mappingFile.exists())
			{
				wlist = getLines(loader, mapping);
			} else
			{
				List files = splitFileNames(mapping);
				wlist = new ArrayList();
				List lines;
				for (Iterator i$ = files.iterator(); i$.hasNext(); wlist.addAll(lines))
				{
					String file = (String)i$.next();
					lines = getLines(loader, file.trim());
				}

			}
			NormalizeCharMap.Builder builder = new NormalizeCharMap.Builder();
			parseRules(wlist, builder);
			normMap = builder.build();
			if (normMap.map == null)
				normMap = null;
		}
	}

	public Reader create(Reader input)
	{
		return ((Reader) (normMap != null ? new MappingCharFilter(normMap, input) : input));
	}

	protected void parseRules(List rules, NormalizeCharMap.Builder builder)
	{
		Matcher m;
		for (Iterator i$ = rules.iterator(); i$.hasNext(); builder.add(parseString(m.group(1)), parseString(m.group(2))))
		{
			String rule = (String)i$.next();
			m = p.matcher(rule);
			if (!m.find())
				throw new IllegalArgumentException((new StringBuilder()).append("Invalid Mapping Rule : [").append(rule).append("], file = ").append(mapping).toString());
		}

	}

	protected String parseString(String s)
	{
		int readPos = 0;
		int len = s.length();
		int writePos = 0;
		while (readPos < len) 
		{
			char c = s.charAt(readPos++);
			if (c == '\\')
			{
				if (readPos >= len)
					throw new IllegalArgumentException((new StringBuilder()).append("Invalid escaped char in [").append(s).append("]").toString());
				c = s.charAt(readPos++);
				switch (c)
				{
				default:
					break;

				case 92: // '\\'
					c = '\\';
					break;

				case 34: // '"'
					c = '"';
					break;

				case 110: // 'n'
					c = '\n';
					break;

				case 116: // 't'
					c = '\t';
					break;

				case 114: // 'r'
					c = '\r';
					break;

				case 98: // 'b'
					c = '\b';
					break;

				case 102: // 'f'
					c = '\f';
					break;

				case 117: // 'u'
					if (readPos + 3 >= len)
						throw new IllegalArgumentException((new StringBuilder()).append("Invalid escaped char in [").append(s).append("]").toString());
					c = (char)Integer.parseInt(s.substring(readPos, readPos + 4), 16);
					readPos += 4;
					break;
				}
			}
			out[writePos++] = c;
		}
		return new String(out, 0, writePos);
	}

	public AbstractAnalysisFactory getMultiTermComponent()
	{
		return this;
	}

}
