// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   HunspellStemmer.java

package org.apache.lucene.analysis.hunspell;

import java.util.*;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.analysis.util.CharacterUtils;
import org.apache.lucene.util.Version;

// Referenced classes of package org.apache.lucene.analysis.hunspell:
//			HunspellAffix, HunspellWord, HunspellDictionary

public class HunspellStemmer
{
	public static class Stem
	{

		private final List prefixes = new ArrayList();
		private final List suffixes = new ArrayList();
		private final char stem[];
		private final int stemLength;

		public void addPrefix(HunspellAffix prefix)
		{
			prefixes.add(0, prefix);
		}

		public void addSuffix(HunspellAffix suffix)
		{
			suffixes.add(suffix);
		}

		public List getPrefixes()
		{
			return prefixes;
		}

		public List getSuffixes()
		{
			return suffixes;
		}

		public char[] getStem()
		{
			return stem;
		}

		public int getStemLength()
		{
			return stemLength;
		}

		public String getStemString()
		{
			return new String(stem, 0, stemLength);
		}


		public Stem(char stem[], int stemLength)
		{
			this.stem = stem;
			this.stemLength = stemLength;
		}
	}


	private static final int RECURSION_CAP = 2;
	private final HunspellDictionary dictionary;
	private final StringBuilder segment = new StringBuilder();
	private CharacterUtils charUtils;

	public HunspellStemmer(HunspellDictionary dictionary)
	{
		charUtils = CharacterUtils.getInstance(Version.LUCENE_40);
		this.dictionary = dictionary;
	}

	public List stem(String word)
	{
		return stem(word.toCharArray(), word.length());
	}

	public List stem(char word[], int length)
	{
		List stems = new ArrayList();
		if (dictionary.lookupWord(word, 0, length) != null)
			stems.add(new Stem(word, length));
		stems.addAll(stem(word, length, null, 0));
		return stems;
	}

	public List uniqueStems(char word[], int length)
	{
		List stems = new ArrayList();
		CharArraySet terms = new CharArraySet(dictionary.getVersion(), 8, dictionary.isIgnoreCase());
		if (dictionary.lookupWord(word, 0, length) != null)
		{
			stems.add(new Stem(word, length));
			terms.add(word);
		}
		List otherStems = stem(word, length, null, 0);
		Iterator i$ = otherStems.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			Stem s = (Stem)i$.next();
			if (!terms.contains(s.stem))
			{
				stems.add(s);
				terms.add(s.stem);
			}
		} while (true);
		return stems;
	}

	private List stem(char word[], int length, char flags[], int recursionDepth)
	{
		List stems = new ArrayList();
label0:
		for (int i = 0; i < length; i++)
		{
			List suffixes = dictionary.lookupSuffix(word, i, length - i);
			if (suffixes == null)
				continue;
			Iterator i$ = suffixes.iterator();
			do
			{
				HunspellAffix suffix;
				do
				{
					if (!i$.hasNext())
						continue label0;
					suffix = (HunspellAffix)i$.next();
				} while (!hasCrossCheckedFlag(suffix.getFlag(), flags));
				int deAffixedLength = length - suffix.getAppend().length();
				String strippedWord = (new StringBuilder()).append(word, 0, deAffixedLength).append(suffix.getStrip()).toString();
				List stemList = applyAffix(strippedWord.toCharArray(), strippedWord.length(), suffix, recursionDepth);
				Stem stem;
				for (Iterator i$ = stemList.iterator(); i$.hasNext(); stem.addSuffix(suffix))
					stem = (Stem)i$.next();

				stems.addAll(stemList);
			} while (true);
		}

label1:
		for (int i = length - 1; i >= 0; i--)
		{
			List prefixes = dictionary.lookupPrefix(word, 0, i);
			if (prefixes == null)
				continue;
			Iterator i$ = prefixes.iterator();
			do
			{
				HunspellAffix prefix;
				do
				{
					if (!i$.hasNext())
						continue label1;
					prefix = (HunspellAffix)i$.next();
				} while (!hasCrossCheckedFlag(prefix.getFlag(), flags));
				int deAffixedStart = prefix.getAppend().length();
				int deAffixedLength = length - deAffixedStart;
				String strippedWord = (new StringBuilder()).append(prefix.getStrip()).append(word, deAffixedStart, deAffixedLength).toString();
				List stemList = applyAffix(strippedWord.toCharArray(), strippedWord.length(), prefix, recursionDepth);
				Stem stem;
				for (Iterator i$ = stemList.iterator(); i$.hasNext(); stem.addPrefix(prefix))
					stem = (Stem)i$.next();

				stems.addAll(stemList);
			} while (true);
		}

		return stems;
	}

	public List applyAffix(char strippedWord[], int length, HunspellAffix affix, int recursionDepth)
	{
		if (dictionary.isIgnoreCase())
		{
			for (int i = 0; i < strippedWord.length; i += Character.toChars(Character.toLowerCase(charUtils.codePointAt(strippedWord, i)), strippedWord, i));
		}
		segment.setLength(0);
		segment.append(strippedWord, 0, length);
		if (!affix.checkCondition(segment))
			return Collections.EMPTY_LIST;
		List stems = new ArrayList();
		List words = dictionary.lookupWord(strippedWord, 0, length);
		if (words != null)
		{
			Iterator i$ = words.iterator();
			do
			{
				if (!i$.hasNext())
					break;
				HunspellWord hunspellWord = (HunspellWord)i$.next();
				if (hunspellWord.hasFlag(affix.getFlag()))
					stems.add(new Stem(strippedWord, length));
			} while (true);
		}
		if (affix.isCrossProduct() && recursionDepth < 2)
			stems.addAll(stem(strippedWord, length, affix.getAppendFlags(), ++recursionDepth));
		return stems;
	}

	private boolean hasCrossCheckedFlag(char flag, char flags[])
	{
		return flags == null || Arrays.binarySearch(flags, flag) >= 0;
	}
}
