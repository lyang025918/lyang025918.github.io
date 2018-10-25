// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   HunspellDictionary.java

package org.apache.lucene.analysis.hunspell;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.text.ParseException;
import java.util.*;
import org.apache.lucene.analysis.util.CharArrayMap;
import org.apache.lucene.util.Version;

// Referenced classes of package org.apache.lucene.analysis.hunspell:
//			HunspellAffix, HunspellWord

public class HunspellDictionary
{
	private static class DoubleASCIIFlagParsingStrategy extends FlagParsingStrategy
	{

		public char[] parseFlags(String rawFlags)
		{
			if (rawFlags.length() == 0)
				return new char[0];
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < rawFlags.length(); i += 2)
			{
				char cookedFlag = (char)(rawFlags.charAt(i) + rawFlags.charAt(i + 1));
				builder.append(cookedFlag);
			}

			char flags[] = new char[builder.length()];
			builder.getChars(0, builder.length(), flags, 0);
			return flags;
		}

		private DoubleASCIIFlagParsingStrategy()
		{
		}

	}

	private static class NumFlagParsingStrategy extends FlagParsingStrategy
	{

		public char[] parseFlags(String rawFlags)
		{
			String rawFlagParts[] = rawFlags.trim().split(",");
			char flags[] = new char[rawFlagParts.length];
			for (int i = 0; i < rawFlagParts.length; i++)
				flags[i] = (char)Integer.parseInt(rawFlagParts[i].replaceAll("[^0-9]", ""));

			return flags;
		}

		private NumFlagParsingStrategy()
		{
		}

	}

	private static class SimpleFlagParsingStrategy extends FlagParsingStrategy
	{

		public char[] parseFlags(String rawFlags)
		{
			return rawFlags.toCharArray();
		}

		private SimpleFlagParsingStrategy()
		{
		}

	}

	private static abstract class FlagParsingStrategy
	{

		char parseFlag(String rawFlag)
		{
			return parseFlags(rawFlag)[0];
		}

		abstract char[] parseFlags(String s);

		private FlagParsingStrategy()
		{
		}

	}


	static final HunspellWord NOFLAGS = new HunspellWord();
	private static final String ALIAS_KEY = "AF";
	private static final String PREFIX_KEY = "PFX";
	private static final String SUFFIX_KEY = "SFX";
	private static final String FLAG_KEY = "FLAG";
	private static final String NUM_FLAG_TYPE = "num";
	private static final String UTF8_FLAG_TYPE = "UTF-8";
	private static final String LONG_FLAG_TYPE = "long";
	private static final String PREFIX_CONDITION_REGEX_PATTERN = "%s.*";
	private static final String SUFFIX_CONDITION_REGEX_PATTERN = ".*%s";
	private static final boolean IGNORE_CASE_DEFAULT = false;
	private static final boolean STRICT_AFFIX_PARSING_DEFAULT = true;
	private CharArrayMap words;
	private CharArrayMap prefixes;
	private CharArrayMap suffixes;
	private FlagParsingStrategy flagParsingStrategy;
	private boolean ignoreCase;
	private final Version version;
	private String aliases[];
	private int aliasCount;

	public HunspellDictionary(InputStream affix, InputStream dictionary, Version version)
		throws IOException, ParseException
	{
		this(affix, Arrays.asList(new InputStream[] {
			dictionary
		}), version, false);
	}

	public HunspellDictionary(InputStream affix, InputStream dictionary, Version version, boolean ignoreCase)
		throws IOException, ParseException
	{
		this(affix, Arrays.asList(new InputStream[] {
			dictionary
		}), version, ignoreCase);
	}

	public HunspellDictionary(InputStream affix, List dictionaries, Version version, boolean ignoreCase)
		throws IOException, ParseException
	{
		this(affix, dictionaries, version, ignoreCase, true);
	}

	public HunspellDictionary(InputStream affix, List dictionaries, Version version, boolean ignoreCase, boolean strictAffixParsing)
		throws IOException, ParseException
	{
		flagParsingStrategy = new SimpleFlagParsingStrategy();
		this.ignoreCase = false;
		aliasCount = 0;
		this.version = version;
		this.ignoreCase = ignoreCase;
		String encoding = getDictionaryEncoding(affix);
		CharsetDecoder decoder = getJavaEncoding(encoding);
		readAffixFile(affix, decoder, strictAffixParsing);
		words = new CharArrayMap(version, 65535, this.ignoreCase);
		InputStream dictionary;
		for (Iterator i$ = dictionaries.iterator(); i$.hasNext(); readDictionaryFile(dictionary, decoder))
			dictionary = (InputStream)i$.next();

	}

	public List lookupWord(char word[], int offset, int length)
	{
		return (List)words.get(word, offset, length);
	}

	public List lookupPrefix(char word[], int offset, int length)
	{
		return (List)prefixes.get(word, offset, length);
	}

	public List lookupSuffix(char word[], int offset, int length)
	{
		return (List)suffixes.get(word, offset, length);
	}

	private void readAffixFile(InputStream affixStream, CharsetDecoder decoder, boolean strict)
		throws IOException, ParseException
	{
		prefixes = new CharArrayMap(version, 8, ignoreCase);
		suffixes = new CharArrayMap(version, 8, ignoreCase);
		LineNumberReader reader = new LineNumberReader(new InputStreamReader(affixStream, decoder));
		String line = null;
		do
		{
			if ((line = reader.readLine()) == null)
				break;
			if (line.startsWith("AF"))
				parseAlias(line);
			else
			if (line.startsWith("PFX"))
				parseAffix(prefixes, line, reader, "%s.*", strict);
			else
			if (line.startsWith("SFX"))
				parseAffix(suffixes, line, reader, ".*%s", strict);
			else
			if (line.startsWith("FLAG"))
				flagParsingStrategy = getFlagParsingStrategy(line);
		} while (true);
	}

	private void parseAffix(CharArrayMap affixes, String header, LineNumberReader reader, String conditionPattern, boolean strict)
		throws IOException, ParseException
	{
		String args[] = header.split("\\s+");
		boolean crossProduct = args[2].equals("Y");
		int numLines = Integer.parseInt(args[3]);
		for (int i = 0; i < numLines; i++)
		{
			String line = reader.readLine();
			String ruleArgs[] = line.split("\\s+");
			if (ruleArgs.length < 5)
			{
				if (strict)
					throw new ParseException("The affix file contains a rule with less than five elements", reader.getLineNumber());
				continue;
			}
			HunspellAffix affix = new HunspellAffix();
			affix.setFlag(flagParsingStrategy.parseFlag(ruleArgs[1]));
			affix.setStrip(ruleArgs[2].equals("0") ? "" : ruleArgs[2]);
			String affixArg = ruleArgs[3];
			int flagSep = affixArg.lastIndexOf('/');
			if (flagSep != -1)
			{
				String flagPart = affixArg.substring(flagSep + 1);
				if (aliasCount > 0)
					flagPart = getAliasValue(Integer.parseInt(flagPart));
				char appendFlags[] = flagParsingStrategy.parseFlags(flagPart);
				Arrays.sort(appendFlags);
				affix.setAppendFlags(appendFlags);
				affix.setAppend(affixArg.substring(0, flagSep));
			} else
			{
				affix.setAppend(affixArg);
			}
			String condition = ruleArgs[4];
			affix.setCondition(condition, String.format(Locale.ROOT, conditionPattern, new Object[] {
				condition
			}));
			affix.setCrossProduct(crossProduct);
			List list = (List)affixes.get(affix.getAppend());
			if (list == null)
			{
				list = new ArrayList();
				affixes.put(affix.getAppend(), list);
			}
			list.add(affix);
		}

	}

	private String getDictionaryEncoding(InputStream affix)
		throws IOException, ParseException
	{
		StringBuilder encoding = new StringBuilder();
		do
		{
			encoding.setLength(0);
			int ch;
			do
			{
				if ((ch = affix.read()) < 0 || ch == 10)
					break;
				if (ch != 13)
					encoding.append((char)ch);
			} while (true);
			if (encoding.length() == 0 || encoding.charAt(0) == '#' || encoding.toString().trim().length() == 0)
			{
				if (ch < 0)
					throw new ParseException("Unexpected end of affix file.", 0);
			} else
			if ("SET ".equals(encoding.substring(0, 4)))
				return encoding.substring(4).trim();
			else
				throw new ParseException((new StringBuilder()).append("The first non-comment line in the affix file must be a 'SET charset', was: '").append(encoding).append("'").toString(), 0);
		} while (true);
	}

	private CharsetDecoder getJavaEncoding(String encoding)
	{
		Charset charset = Charset.forName(encoding);
		return charset.newDecoder();
	}

	private FlagParsingStrategy getFlagParsingStrategy(String flagLine)
	{
		String flagType = flagLine.substring(5);
		if ("num".equals(flagType))
			return new NumFlagParsingStrategy();
		if ("UTF-8".equals(flagType))
			return new SimpleFlagParsingStrategy();
		if ("long".equals(flagType))
			return new DoubleASCIIFlagParsingStrategy();
		else
			throw new IllegalArgumentException((new StringBuilder()).append("Unknown flag type: ").append(flagType).toString());
	}

	private void readDictionaryFile(InputStream dictionary, CharsetDecoder decoder)
		throws IOException
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(dictionary, decoder));
		String line = reader.readLine();
		int numEntries = Integer.parseInt(line);
		while ((line = reader.readLine()) != null) 
		{
			int flagSep = line.lastIndexOf('/');
			String entry;
			HunspellWord wordForm;
			if (flagSep == -1)
			{
				wordForm = NOFLAGS;
				entry = line;
			} else
			{
				int end = line.indexOf('\t', flagSep);
				if (end == -1)
					end = line.length();
				String flagPart = line.substring(flagSep + 1, end);
				if (aliasCount > 0)
					flagPart = getAliasValue(Integer.parseInt(flagPart));
				wordForm = new HunspellWord(flagParsingStrategy.parseFlags(flagPart));
				Arrays.sort(wordForm.getFlags());
				entry = line.substring(0, flagSep);
				if (ignoreCase)
					entry = entry.toLowerCase(Locale.ROOT);
			}
			List entries = (List)words.get(entry);
			if (entries == null)
			{
				entries = new ArrayList();
				words.put(entry, entries);
			}
			entries.add(wordForm);
		}
	}

	public Version getVersion()
	{
		return version;
	}

	private void parseAlias(String line)
	{
		String ruleArgs[] = line.split("\\s+");
		if (aliases == null)
		{
			int count = Integer.parseInt(ruleArgs[1]);
			aliases = new String[count];
		} else
		{
			aliases[aliasCount++] = ruleArgs[1];
		}
	}

	private String getAliasValue(int id)
	{
		return aliases[id - 1];
		IndexOutOfBoundsException ex;
		ex;
		throw new IllegalArgumentException((new StringBuilder()).append("Bad flag alias number:").append(id).toString(), ex);
	}

	public boolean isIgnoreCase()
	{
		return ignoreCase;
	}

}
