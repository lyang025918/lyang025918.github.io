// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   WordDelimiterFilterFactory.java

package org.apache.lucene.analysis.miscellaneous;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.*;

// Referenced classes of package org.apache.lucene.analysis.miscellaneous:
//			WordDelimiterFilter, WordDelimiterIterator

public class WordDelimiterFilterFactory extends TokenFilterFactory
	implements ResourceLoaderAware
{

	public static final String PROTECTED_TOKENS = "protected";
	public static final String TYPES = "types";
	private CharArraySet protectedWords;
	private int flags;
	byte typeTable[];
	private static Pattern typePattern = Pattern.compile("(.*)\\s*=>\\s*(.*)\\s*$");
	char out[];

	public WordDelimiterFilterFactory()
	{
		protectedWords = null;
		typeTable = null;
		out = new char[256];
	}

	public void inform(ResourceLoader loader)
		throws IOException
	{
		String wordFiles = (String)args.get("protected");
		if (wordFiles != null)
			protectedWords = getWordSet(loader, wordFiles, false);
		String types = (String)args.get("types");
		if (types != null)
		{
			List files = splitFileNames(types);
			List wlist = new ArrayList();
			List lines;
			for (Iterator i$ = files.iterator(); i$.hasNext(); wlist.addAll(lines))
			{
				String file = (String)i$.next();
				lines = getLines(loader, file.trim());
			}

			typeTable = parseTypes(wlist);
		}
	}

	public void init(Map args)
	{
		super.init(args);
		if (getInt("generateWordParts", 1) != 0)
			flags |= 1;
		if (getInt("generateNumberParts", 1) != 0)
			flags |= 2;
		if (getInt("catenateWords", 0) != 0)
			flags |= 4;
		if (getInt("catenateNumbers", 0) != 0)
			flags |= 8;
		if (getInt("catenateAll", 0) != 0)
			flags |= 0x10;
		if (getInt("splitOnCaseChange", 1) != 0)
			flags |= 0x40;
		if (getInt("splitOnNumerics", 1) != 0)
			flags |= 0x80;
		if (getInt("preserveOriginal", 0) != 0)
			flags |= 0x20;
		if (getInt("stemEnglishPossessive", 1) != 0)
			flags |= 0x100;
	}

	public WordDelimiterFilter create(TokenStream input)
	{
		return new WordDelimiterFilter(input, typeTable != null ? typeTable : WordDelimiterIterator.DEFAULT_WORD_DELIM_TABLE, flags, protectedWords);
	}

	private byte[] parseTypes(List rules)
	{
		SortedMap typeMap = new TreeMap();
		String lhs;
		Byte rhs;
		for (Iterator i$ = rules.iterator(); i$.hasNext(); typeMap.put(Character.valueOf(lhs.charAt(0)), rhs))
		{
			String rule = (String)i$.next();
			Matcher m = typePattern.matcher(rule);
			if (!m.find())
				throw new IllegalArgumentException((new StringBuilder()).append("Invalid Mapping Rule : [").append(rule).append("]").toString());
			lhs = parseString(m.group(1).trim());
			rhs = parseType(m.group(2).trim());
			if (lhs.length() != 1)
				throw new IllegalArgumentException((new StringBuilder()).append("Invalid Mapping Rule : [").append(rule).append("]. Only a single character is allowed.").toString());
			if (rhs == null)
				throw new IllegalArgumentException((new StringBuilder()).append("Invalid Mapping Rule : [").append(rule).append("]. Illegal type.").toString());
		}

		byte types[] = new byte[Math.max(((Character)typeMap.lastKey()).charValue() + 1, WordDelimiterIterator.DEFAULT_WORD_DELIM_TABLE.length)];
		for (int i = 0; i < types.length; i++)
			types[i] = WordDelimiterIterator.getType(i);

		for (Iterator i$ = typeMap.entrySet().iterator(); i$.hasNext();)
		{
			java.util.Map.Entry mapping = (java.util.Map.Entry)i$.next();
			types[((Character)mapping.getKey()).charValue()] = ((Byte)mapping.getValue()).byteValue();
		}

		return types;
	}

	private Byte parseType(String s)
	{
		if (s.equals("LOWER"))
			return Byte.valueOf((byte)1);
		if (s.equals("UPPER"))
			return Byte.valueOf((byte)2);
		if (s.equals("ALPHA"))
			return Byte.valueOf((byte)3);
		if (s.equals("DIGIT"))
			return Byte.valueOf((byte)4);
		if (s.equals("ALPHANUM"))
			return Byte.valueOf((byte)7);
		if (s.equals("SUBWORD_DELIM"))
			return Byte.valueOf((byte)8);
		else
			return null;
	}

	private String parseString(String s)
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

	public volatile TokenStream create(TokenStream x0)
	{
		return create(x0);
	}

}
