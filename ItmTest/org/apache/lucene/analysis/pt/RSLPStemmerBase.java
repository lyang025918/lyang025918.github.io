// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RSLPStemmerBase.java

package org.apache.lucene.analysis.pt;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.analysis.util.StemmerUtil;
import org.apache.lucene.util.Version;

public abstract class RSLPStemmerBase
{
	protected static class Step
	{

		protected final String name;
		protected final Rule rules[];
		protected final int min;
		protected final char suffixes[][];

		public int apply(char s[], int len)
		{
			if (len < min)
				return len;
			if (suffixes != null)
			{
				boolean found = false;
				int i = 0;
				do
				{
					if (i >= suffixes.length)
						break;
					if (StemmerUtil.endsWith(s, len, suffixes[i]))
					{
						found = true;
						break;
					}
					i++;
				} while (true);
				if (!found)
					return len;
			}
			for (int i = 0; i < rules.length; i++)
				if (rules[i].matches(s, len))
					return rules[i].replace(s, len);

			return len;
		}

		public Step(String name, Rule rules[], int min, String suffixes[])
		{
			this.name = name;
			this.rules = rules;
			if (min == 0)
			{
				min = 0x7fffffff;
				Rule arr$[] = rules;
				int len$ = arr$.length;
				for (int i$ = 0; i$ < len$; i$++)
				{
					Rule r = arr$[i$];
					min = Math.min(min, r.min + r.suffix.length);
				}

			}
			this.min = min;
			if (suffixes == null || suffixes.length == 0)
			{
				this.suffixes = (char[][])null;
			} else
			{
				this.suffixes = new char[suffixes.length][];
				for (int i = 0; i < suffixes.length; i++)
					this.suffixes[i] = suffixes[i].toCharArray();

			}
		}
	}

	protected static class RuleWithSuffixExceptions extends Rule
	{

		protected final char exceptions[][];

		public boolean matches(char s[], int len)
		{
			if (!super.matches(s, len))
				return false;
			for (int i = 0; i < exceptions.length; i++)
				if (StemmerUtil.endsWith(s, len, exceptions[i]))
					return false;

			return true;
		}

		public RuleWithSuffixExceptions(String suffix, int min, String replacement, String exceptions[])
		{
			super(suffix, min, replacement);
			for (int i = 0; i < exceptions.length; i++)
				if (!exceptions[i].endsWith(suffix))
					throw new RuntimeException((new StringBuilder()).append("warning: useless exception '").append(exceptions[i]).append("' does not end with '").append(suffix).append("'").toString());

			this.exceptions = new char[exceptions.length][];
			for (int i = 0; i < exceptions.length; i++)
				this.exceptions[i] = exceptions[i].toCharArray();

		}
	}

	protected static class RuleWithSetExceptions extends Rule
	{

		protected final CharArraySet exceptions;

		public boolean matches(char s[], int len)
		{
			return super.matches(s, len) && !exceptions.contains(s, 0, len);
		}

		public RuleWithSetExceptions(String suffix, int min, String replacement, String exceptions[])
		{
			super(suffix, min, replacement);
			for (int i = 0; i < exceptions.length; i++)
				if (!exceptions[i].endsWith(suffix))
					throw new RuntimeException((new StringBuilder()).append("useless exception '").append(exceptions[i]).append("' does not end with '").append(suffix).append("'").toString());

			this.exceptions = new CharArraySet(Version.LUCENE_31, Arrays.asList(exceptions), false);
		}
	}

	protected static class Rule
	{

		protected final char suffix[];
		protected final char replacement[];
		protected final int min;

		public boolean matches(char s[], int len)
		{
			return len - suffix.length >= min && StemmerUtil.endsWith(s, len, suffix);
		}

		public int replace(char s[], int len)
		{
			if (replacement.length > 0)
				System.arraycopy(replacement, 0, s, len - suffix.length, replacement.length);
			return (len - suffix.length) + replacement.length;
		}

		public Rule(String suffix, int min, String replacement)
		{
			this.suffix = suffix.toCharArray();
			this.replacement = replacement.toCharArray();
			this.min = min;
		}
	}


	private static final Pattern headerPattern = Pattern.compile("^\\{\\s*\"([^\"]*)\",\\s*([0-9]+),\\s*(0|1),\\s*\\{(.*)\\},\\s*$");
	private static final Pattern stripPattern = Pattern.compile("^\\{\\s*\"([^\"]*)\",\\s*([0-9]+)\\s*\\}\\s*(,|(\\}\\s*;))$");
	private static final Pattern repPattern = Pattern.compile("^\\{\\s*\"([^\"]*)\",\\s*([0-9]+),\\s*\"([^\"]*)\"\\}\\s*(,|(\\}\\s*;))$");
	private static final Pattern excPattern = Pattern.compile("^\\{\\s*\"([^\"]*)\",\\s*([0-9]+),\\s*\"([^\"]*)\",\\s*\\{(.*)\\}\\s*\\}\\s*(,|(\\}\\s*;))$");
	static final boolean $assertionsDisabled = !org/apache/lucene/analysis/pt/RSLPStemmerBase.desiredAssertionStatus();

	public RSLPStemmerBase()
	{
	}

	protected static Map parse(Class clazz, String resource)
	{
		Map steps;
		InputStream is = clazz.getResourceAsStream(resource);
		LineNumberReader r = new LineNumberReader(new InputStreamReader(is, "UTF-8"));
		steps = new HashMap();
		String step;
		while ((step = readLine(r)) != null) 
		{
			Step s = parseStep(r, step);
			steps.put(s.name, s);
		}
		r.close();
		return steps;
		IOException e;
		e;
		throw new RuntimeException(e);
	}

	private static Step parseStep(LineNumberReader r, String header)
		throws IOException
	{
		Matcher matcher = headerPattern.matcher(header);
		if (!matcher.find())
			throw new RuntimeException((new StringBuilder()).append("Illegal Step header specified at line ").append(r.getLineNumber()).toString());
		if (!$assertionsDisabled && matcher.groupCount() != 4)
		{
			throw new AssertionError();
		} else
		{
			String name = matcher.group(1);
			int min = Integer.parseInt(matcher.group(2));
			int type = Integer.parseInt(matcher.group(3));
			String suffixes[] = parseList(matcher.group(4));
			Rule rules[] = parseRules(r, type);
			return new Step(name, rules, min, suffixes);
		}
	}

	private static Rule[] parseRules(LineNumberReader r, int type)
		throws IOException
	{
		List rules = new ArrayList();
		String line;
		while ((line = readLine(r)) != null) 
		{
			Matcher matcher = stripPattern.matcher(line);
			if (matcher.matches())
			{
				rules.add(new Rule(matcher.group(1), Integer.parseInt(matcher.group(2)), ""));
			} else
			{
				matcher = repPattern.matcher(line);
				if (matcher.matches())
				{
					rules.add(new Rule(matcher.group(1), Integer.parseInt(matcher.group(2)), matcher.group(3)));
				} else
				{
					matcher = excPattern.matcher(line);
					if (matcher.matches())
					{
						if (type == 0)
							rules.add(new RuleWithSuffixExceptions(matcher.group(1), Integer.parseInt(matcher.group(2)), matcher.group(3), parseList(matcher.group(4))));
						else
							rules.add(new RuleWithSetExceptions(matcher.group(1), Integer.parseInt(matcher.group(2)), matcher.group(3), parseList(matcher.group(4))));
					} else
					{
						throw new RuntimeException((new StringBuilder()).append("Illegal Step rule specified at line ").append(r.getLineNumber()).toString());
					}
				}
			}
			if (line.endsWith(";"))
				return (Rule[])rules.toArray(new Rule[rules.size()]);
		}
		return null;
	}

	private static String[] parseList(String s)
	{
		if (s.length() == 0)
			return null;
		String list[] = s.split(",");
		for (int i = 0; i < list.length; i++)
			list[i] = parseString(list[i].trim());

		return list;
	}

	private static String parseString(String s)
	{
		return s.substring(1, s.length() - 1);
	}

	private static String readLine(LineNumberReader r)
		throws IOException
	{
		String line;
		for (line = null; (line = r.readLine()) != null;)
		{
			line = line.trim();
			if (line.length() > 0 && line.charAt(0) != '#')
				return line;
		}

		return line;
	}

}
