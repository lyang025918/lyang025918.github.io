// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SolrSynonymParser.java

package org.apache.lucene.analysis.synonym;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.util.CharsRef;

// Referenced classes of package org.apache.lucene.analysis.synonym:
//			SynonymMap

public class SolrSynonymParser extends SynonymMap.Builder
{

	private final boolean expand;
	private final Analyzer analyzer;

	public SolrSynonymParser(boolean dedup, boolean expand, Analyzer analyzer)
	{
		super(dedup);
		this.expand = expand;
		this.analyzer = analyzer;
	}

	public void add(Reader in)
		throws IOException, ParseException
	{
		Exception exception;
		LineNumberReader br = new LineNumberReader(in);
		try
		{
			addInternal(br);
		}
		catch (IllegalArgumentException e)
		{
			ParseException ex = new ParseException((new StringBuilder()).append("Invalid synonym rule at line ").append(br.getLineNumber()).toString(), 0);
			ex.initCause(e);
			throw ex;
		}
		finally
		{
			br.close();
		}
		br.close();
		break MISSING_BLOCK_LABEL_73;
		throw exception;
	}

	private void addInternal(BufferedReader in)
		throws IOException
	{
		String line = null;
		do
		{
			if ((line = in.readLine()) == null)
				break;
			if (line.length() != 0 && line.charAt(0) != '#')
			{
				String sides[] = split(line, "=>");
				CharsRef inputs[];
				CharsRef outputs[];
				if (sides.length > 1)
				{
					if (sides.length != 2)
						throw new IllegalArgumentException("more than one explicit mapping specified on the same line");
					String inputStrings[] = split(sides[0], ",");
					inputs = new CharsRef[inputStrings.length];
					for (int i = 0; i < inputs.length; i++)
						inputs[i] = analyze(analyzer, unescape(inputStrings[i]).trim(), new CharsRef());

					String outputStrings[] = split(sides[1], ",");
					outputs = new CharsRef[outputStrings.length];
					for (int i = 0; i < outputs.length; i++)
						outputs[i] = analyze(analyzer, unescape(outputStrings[i]).trim(), new CharsRef());

				} else
				{
					String inputStrings[] = split(line, ",");
					inputs = new CharsRef[inputStrings.length];
					for (int i = 0; i < inputs.length; i++)
						inputs[i] = analyze(analyzer, unescape(inputStrings[i]).trim(), new CharsRef());

					if (expand)
					{
						outputs = inputs;
					} else
					{
						outputs = new CharsRef[1];
						outputs[0] = inputs[0];
					}
				}
				int i = 0;
				while (i < inputs.length) 
				{
					for (int j = 0; j < outputs.length; j++)
						add(inputs[i], outputs[j], false);

					i++;
				}
			}
		} while (true);
	}

	private static String[] split(String s, String separator)
	{
		ArrayList list = new ArrayList(2);
		StringBuilder sb = new StringBuilder();
		int pos = 0;
		int end = s.length();
		do
		{
			if (pos >= end)
				break;
			if (s.startsWith(separator, pos))
			{
				if (sb.length() > 0)
				{
					list.add(sb.toString());
					sb = new StringBuilder();
				}
				pos += separator.length();
				continue;
			}
			char ch = s.charAt(pos++);
			if (ch == '\\')
			{
				sb.append(ch);
				if (pos >= end)
					break;
				ch = s.charAt(pos++);
			}
			sb.append(ch);
		} while (true);
		if (sb.length() > 0)
			list.add(sb.toString());
		return (String[])list.toArray(new String[list.size()]);
	}

	private String unescape(String s)
	{
		if (s.indexOf("\\") >= 0)
		{
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < s.length(); i++)
			{
				char ch = s.charAt(i);
				if (ch == '\\' && i < s.length() - 1)
					sb.append(s.charAt(++i));
				else
					sb.append(ch);
			}

			return sb.toString();
		} else
		{
			return s;
		}
	}
}
