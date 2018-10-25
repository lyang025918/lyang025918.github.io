// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GreekLowerCaseFilter.java

package org.apache.lucene.analysis.el;

import java.io.IOException;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.CharacterUtils;
import org.apache.lucene.util.Version;

public final class GreekLowerCaseFilter extends TokenFilter
{

	private final CharTermAttribute termAtt = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
	private final CharacterUtils charUtils;

	public GreekLowerCaseFilter(Version matchVersion, TokenStream in)
	{
		super(in);
		charUtils = CharacterUtils.getInstance(matchVersion);
	}

	public boolean incrementToken()
		throws IOException
	{
		if (input.incrementToken())
		{
			char chArray[] = termAtt.buffer();
			int chLen = termAtt.length();
			for (int i = 0; i < chLen; i += Character.toChars(lowerCase(charUtils.codePointAt(chArray, i)), chArray, i));
			return true;
		} else
		{
			return false;
		}
	}

	private int lowerCase(int codepoint)
	{
		switch (codepoint)
		{
		case 962: 
			return 963;

		case 902: 
		case 940: 
			return 945;

		case 904: 
		case 941: 
			return 949;

		case 905: 
		case 942: 
			return 951;

		case 906: 
		case 912: 
		case 938: 
		case 943: 
		case 970: 
			return 953;

		case 910: 
		case 939: 
		case 944: 
		case 971: 
		case 973: 
			return 965;

		case 908: 
		case 972: 
			return 959;

		case 911: 
		case 974: 
			return 969;

		case 930: 
			return 962;

		case 903: 
		case 907: 
		case 909: 
		case 913: 
		case 914: 
		case 915: 
		case 916: 
		case 917: 
		case 918: 
		case 919: 
		case 920: 
		case 921: 
		case 922: 
		case 923: 
		case 924: 
		case 925: 
		case 926: 
		case 927: 
		case 928: 
		case 929: 
		case 931: 
		case 932: 
		case 933: 
		case 934: 
		case 935: 
		case 936: 
		case 937: 
		case 945: 
		case 946: 
		case 947: 
		case 948: 
		case 949: 
		case 950: 
		case 951: 
		case 952: 
		case 953: 
		case 954: 
		case 955: 
		case 956: 
		case 957: 
		case 958: 
		case 959: 
		case 960: 
		case 961: 
		case 963: 
		case 964: 
		case 965: 
		case 966: 
		case 967: 
		case 968: 
		case 969: 
		default:
			return Character.toLowerCase(codepoint);
		}
	}
}
