// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PortugueseMinimalStemmer.java

package org.apache.lucene.analysis.pt;

import java.util.Map;

// Referenced classes of package org.apache.lucene.analysis.pt:
//			RSLPStemmerBase

public class PortugueseMinimalStemmer extends RSLPStemmerBase
{

	private static final RSLPStemmerBase.Step pluralStep = (RSLPStemmerBase.Step)parse(org/apache/lucene/analysis/pt/PortugueseMinimalStemmer, "portuguese.rslp").get("Plural");

	public PortugueseMinimalStemmer()
	{
	}

	public int stem(char s[], int len)
	{
		return pluralStep.apply(s, len);
	}

}
