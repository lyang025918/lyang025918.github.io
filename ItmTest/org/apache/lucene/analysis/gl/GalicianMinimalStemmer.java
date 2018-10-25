// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GalicianMinimalStemmer.java

package org.apache.lucene.analysis.gl;

import java.util.Map;
import org.apache.lucene.analysis.pt.RSLPStemmerBase;

public class GalicianMinimalStemmer extends RSLPStemmerBase
{

	private static final org.apache.lucene.analysis.pt.RSLPStemmerBase.Step pluralStep = (org.apache.lucene.analysis.pt.RSLPStemmerBase.Step)parse(org/apache/lucene/analysis/gl/GalicianMinimalStemmer, "galician.rslp").get("Plural");

	public GalicianMinimalStemmer()
	{
	}

	public int stem(char s[], int len)
	{
		return pluralStep.apply(s, len);
	}

}
