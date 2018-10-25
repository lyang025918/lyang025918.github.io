// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CharacterUtil.java

package org.wltea.analyzer.core;


class CharacterUtil
{

	public static final int CHAR_USELESS = 0;
	public static final int CHAR_ARABIC = 1;
	public static final int CHAR_ENGLISH = 2;
	public static final int CHAR_CHINESE = 4;
	public static final int CHAR_OTHER_CJK = 8;

	CharacterUtil()
	{
	}

	static int identifyCharType(char input)
	{
		if (input >= '0' && input <= '9')
			return 1;
		if (input >= 'a' && input <= 'z' || input >= 'A' && input <= 'Z')
			return 2;
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(input);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A)
			return 4;
		return ub != Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS && ub != Character.UnicodeBlock.HANGUL_SYLLABLES && ub != Character.UnicodeBlock.HANGUL_JAMO && ub != Character.UnicodeBlock.HANGUL_COMPATIBILITY_JAMO && ub != Character.UnicodeBlock.HIRAGANA && ub != Character.UnicodeBlock.KATAKANA && ub != Character.UnicodeBlock.KATAKANA_PHONETIC_EXTENSIONS ? 0 : 8;
	}

	static char regularize(char input)
	{
		if (input == '\u3000')
			input = ' ';
		else
		if (input > '\uFF00' && input < '\uFF5F')
			input -= '\uFEE0';
		else
		if (input >= 'A' && input <= 'Z')
			input += ' ';
		return input;
	}
}
