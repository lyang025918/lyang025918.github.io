// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PortugueseStemmer.java

package org.apache.lucene.analysis.pt;

import java.util.Map;

// Referenced classes of package org.apache.lucene.analysis.pt:
//			RSLPStemmerBase

public class PortugueseStemmer extends RSLPStemmerBase
{

	private static final RSLPStemmerBase.Step plural;
	private static final RSLPStemmerBase.Step feminine;
	private static final RSLPStemmerBase.Step adverb;
	private static final RSLPStemmerBase.Step augmentative;
	private static final RSLPStemmerBase.Step noun;
	private static final RSLPStemmerBase.Step verb;
	private static final RSLPStemmerBase.Step vowel;
	static final boolean $assertionsDisabled = !org/apache/lucene/analysis/pt/PortugueseStemmer.desiredAssertionStatus();

	public PortugueseStemmer()
	{
	}

	public int stem(char s[], int len)
	{
		if (!$assertionsDisabled && s.length < len + 1)
			throw new AssertionError("this stemmer requires an oversized array of at least 1");
		len = plural.apply(s, len);
		len = adverb.apply(s, len);
		len = feminine.apply(s, len);
		len = augmentative.apply(s, len);
		int oldlen = len;
		len = noun.apply(s, len);
		if (len == oldlen)
		{
			oldlen = len;
			len = verb.apply(s, len);
			if (len == oldlen)
				len = vowel.apply(s, len);
		}
		for (int i = 0; i < len; i++)
			switch (s[i])
			{
			case 224: 
			case 225: 
			case 226: 
			case 227: 
			case 228: 
			case 229: 
				s[i] = 'a';
				break;

			case 231: 
				s[i] = 'c';
				break;

			case 232: 
			case 233: 
			case 234: 
			case 235: 
				s[i] = 'e';
				break;

			case 236: 
			case 237: 
			case 238: 
			case 239: 
				s[i] = 'i';
				break;

			case 241: 
				s[i] = 'n';
				break;

			case 242: 
			case 243: 
			case 244: 
			case 245: 
			case 246: 
				s[i] = 'o';
				break;

			case 249: 
			case 250: 
			case 251: 
			case 252: 
				s[i] = 'u';
				break;

			case 253: 
			case 255: 
				s[i] = 'y';
				break;
			}

		return len;
	}

	static 
	{
		Map steps = parse(org/apache/lucene/analysis/pt/PortugueseStemmer, "portuguese.rslp");
		plural = (RSLPStemmerBase.Step)steps.get("Plural");
		feminine = (RSLPStemmerBase.Step)steps.get("Feminine");
		adverb = (RSLPStemmerBase.Step)steps.get("Adverb");
		augmentative = (RSLPStemmerBase.Step)steps.get("Augmentative");
		noun = (RSLPStemmerBase.Step)steps.get("Noun");
		verb = (RSLPStemmerBase.Step)steps.get("Verb");
		vowel = (RSLPStemmerBase.Step)steps.get("Vowel");
	}
}
