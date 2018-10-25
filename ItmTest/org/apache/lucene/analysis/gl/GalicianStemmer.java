// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GalicianStemmer.java

package org.apache.lucene.analysis.gl;

import java.util.Map;
import org.apache.lucene.analysis.pt.RSLPStemmerBase;

public class GalicianStemmer extends RSLPStemmerBase
{

	private static final org.apache.lucene.analysis.pt.RSLPStemmerBase.Step plural;
	private static final org.apache.lucene.analysis.pt.RSLPStemmerBase.Step unification;
	private static final org.apache.lucene.analysis.pt.RSLPStemmerBase.Step adverb;
	private static final org.apache.lucene.analysis.pt.RSLPStemmerBase.Step augmentative;
	private static final org.apache.lucene.analysis.pt.RSLPStemmerBase.Step noun;
	private static final org.apache.lucene.analysis.pt.RSLPStemmerBase.Step verb;
	private static final org.apache.lucene.analysis.pt.RSLPStemmerBase.Step vowel;
	static final boolean $assertionsDisabled = !org/apache/lucene/analysis/gl/GalicianStemmer.desiredAssertionStatus();

	public GalicianStemmer()
	{
	}

	public int stem(char s[], int len)
	{
		if (!$assertionsDisabled && s.length < len + 1)
			throw new AssertionError("this stemmer requires an oversized array of at least 1");
		len = plural.apply(s, len);
		len = unification.apply(s, len);
		len = adverb.apply(s, len);
		int oldlen;
		do
		{
			oldlen = len;
			len = augmentative.apply(s, len);
		} while (len != oldlen);
		oldlen = len;
		len = noun.apply(s, len);
		if (len == oldlen)
			len = verb.apply(s, len);
		len = vowel.apply(s, len);
		for (int i = 0; i < len; i++)
			switch (s[i])
			{
			case 225: 
				s[i] = 'a';
				break;

			case 233: 
			case 234: 
				s[i] = 'e';
				break;

			case 237: 
				s[i] = 'i';
				break;

			case 243: 
				s[i] = 'o';
				break;

			case 250: 
				s[i] = 'u';
				break;
			}

		return len;
	}

	static 
	{
		Map steps = parse(org/apache/lucene/analysis/gl/GalicianStemmer, "galician.rslp");
		plural = (org.apache.lucene.analysis.pt.RSLPStemmerBase.Step)steps.get("Plural");
		unification = (org.apache.lucene.analysis.pt.RSLPStemmerBase.Step)steps.get("Unification");
		adverb = (org.apache.lucene.analysis.pt.RSLPStemmerBase.Step)steps.get("Adverb");
		augmentative = (org.apache.lucene.analysis.pt.RSLPStemmerBase.Step)steps.get("Augmentative");
		noun = (org.apache.lucene.analysis.pt.RSLPStemmerBase.Step)steps.get("Noun");
		verb = (org.apache.lucene.analysis.pt.RSLPStemmerBase.Step)steps.get("Verb");
		vowel = (org.apache.lucene.analysis.pt.RSLPStemmerBase.Step)steps.get("Vowel");
	}
}
