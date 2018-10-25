// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IndonesianStemmer.java

package org.apache.lucene.analysis.id;

import org.apache.lucene.analysis.util.StemmerUtil;

public class IndonesianStemmer
{

	private int numSyllables;
	private int flags;
	private static final int REMOVED_KE = 1;
	private static final int REMOVED_PENG = 2;
	private static final int REMOVED_DI = 4;
	private static final int REMOVED_MENG = 8;
	private static final int REMOVED_TER = 16;
	private static final int REMOVED_BER = 32;
	private static final int REMOVED_PE = 64;

	public IndonesianStemmer()
	{
	}

	public int stem(char text[], int length, boolean stemDerivational)
	{
		flags = 0;
		numSyllables = 0;
		for (int i = 0; i < length; i++)
			if (isVowel(text[i]))
				numSyllables++;

		if (numSyllables > 2)
			length = removeParticle(text, length);
		if (numSyllables > 2)
			length = removePossessivePronoun(text, length);
		if (stemDerivational)
			length = stemDerivational(text, length);
		return length;
	}

	private int stemDerivational(char text[], int length)
	{
		int oldLength = length;
		if (numSyllables > 2)
			length = removeFirstOrderPrefix(text, length);
		if (oldLength != length)
		{
			oldLength = length;
			if (numSyllables > 2)
				length = removeSuffix(text, length);
			if (oldLength != length && numSyllables > 2)
				length = removeSecondOrderPrefix(text, length);
		} else
		{
			if (numSyllables > 2)
				length = removeSecondOrderPrefix(text, length);
			if (numSyllables > 2)
				length = removeSuffix(text, length);
		}
		return length;
	}

	private boolean isVowel(char ch)
	{
		switch (ch)
		{
		case 97: // 'a'
		case 101: // 'e'
		case 105: // 'i'
		case 111: // 'o'
		case 117: // 'u'
			return true;
		}
		return false;
	}

	private int removeParticle(char text[], int length)
	{
		if (StemmerUtil.endsWith(text, length, "kah") || StemmerUtil.endsWith(text, length, "lah") || StemmerUtil.endsWith(text, length, "pun"))
		{
			numSyllables--;
			return length - 3;
		} else
		{
			return length;
		}
	}

	private int removePossessivePronoun(char text[], int length)
	{
		if (StemmerUtil.endsWith(text, length, "ku") || StemmerUtil.endsWith(text, length, "mu"))
		{
			numSyllables--;
			return length - 2;
		}
		if (StemmerUtil.endsWith(text, length, "nya"))
		{
			numSyllables--;
			return length - 3;
		} else
		{
			return length;
		}
	}

	private int removeFirstOrderPrefix(char text[], int length)
	{
		if (StemmerUtil.startsWith(text, length, "meng"))
		{
			flags |= 8;
			numSyllables--;
			return StemmerUtil.deleteN(text, 0, length, 4);
		}
		if (StemmerUtil.startsWith(text, length, "meny") && length > 4 && isVowel(text[4]))
		{
			flags |= 8;
			text[3] = 's';
			numSyllables--;
			return StemmerUtil.deleteN(text, 0, length, 3);
		}
		if (StemmerUtil.startsWith(text, length, "men"))
		{
			flags |= 8;
			numSyllables--;
			return StemmerUtil.deleteN(text, 0, length, 3);
		}
		if (StemmerUtil.startsWith(text, length, "mem"))
		{
			flags |= 8;
			numSyllables--;
			return StemmerUtil.deleteN(text, 0, length, 3);
		}
		if (StemmerUtil.startsWith(text, length, "me"))
		{
			flags |= 8;
			numSyllables--;
			return StemmerUtil.deleteN(text, 0, length, 2);
		}
		if (StemmerUtil.startsWith(text, length, "peng"))
		{
			flags |= 2;
			numSyllables--;
			return StemmerUtil.deleteN(text, 0, length, 4);
		}
		if (StemmerUtil.startsWith(text, length, "peny") && length > 4 && isVowel(text[4]))
		{
			flags |= 2;
			text[3] = 's';
			numSyllables--;
			return StemmerUtil.deleteN(text, 0, length, 3);
		}
		if (StemmerUtil.startsWith(text, length, "peny"))
		{
			flags |= 2;
			numSyllables--;
			return StemmerUtil.deleteN(text, 0, length, 4);
		}
		if (StemmerUtil.startsWith(text, length, "pen") && length > 3 && isVowel(text[3]))
		{
			flags |= 2;
			text[2] = 't';
			numSyllables--;
			return StemmerUtil.deleteN(text, 0, length, 2);
		}
		if (StemmerUtil.startsWith(text, length, "pen"))
		{
			flags |= 2;
			numSyllables--;
			return StemmerUtil.deleteN(text, 0, length, 3);
		}
		if (StemmerUtil.startsWith(text, length, "pem"))
		{
			flags |= 2;
			numSyllables--;
			return StemmerUtil.deleteN(text, 0, length, 3);
		}
		if (StemmerUtil.startsWith(text, length, "di"))
		{
			flags |= 4;
			numSyllables--;
			return StemmerUtil.deleteN(text, 0, length, 2);
		}
		if (StemmerUtil.startsWith(text, length, "ter"))
		{
			flags |= 0x10;
			numSyllables--;
			return StemmerUtil.deleteN(text, 0, length, 3);
		}
		if (StemmerUtil.startsWith(text, length, "ke"))
		{
			flags |= 1;
			numSyllables--;
			return StemmerUtil.deleteN(text, 0, length, 2);
		} else
		{
			return length;
		}
	}

	private int removeSecondOrderPrefix(char text[], int length)
	{
		if (StemmerUtil.startsWith(text, length, "ber"))
		{
			flags |= 0x20;
			numSyllables--;
			return StemmerUtil.deleteN(text, 0, length, 3);
		}
		if (length == 7 && StemmerUtil.startsWith(text, length, "belajar"))
		{
			flags |= 0x20;
			numSyllables--;
			return StemmerUtil.deleteN(text, 0, length, 3);
		}
		if (StemmerUtil.startsWith(text, length, "be") && length > 4 && !isVowel(text[2]) && text[3] == 'e' && text[4] == 'r')
		{
			flags |= 0x20;
			numSyllables--;
			return StemmerUtil.deleteN(text, 0, length, 2);
		}
		if (StemmerUtil.startsWith(text, length, "per"))
		{
			numSyllables--;
			return StemmerUtil.deleteN(text, 0, length, 3);
		}
		if (length == 7 && StemmerUtil.startsWith(text, length, "pelajar"))
		{
			numSyllables--;
			return StemmerUtil.deleteN(text, 0, length, 3);
		}
		if (StemmerUtil.startsWith(text, length, "pe"))
		{
			flags |= 0x40;
			numSyllables--;
			return StemmerUtil.deleteN(text, 0, length, 2);
		} else
		{
			return length;
		}
	}

	private int removeSuffix(char text[], int length)
	{
		if (StemmerUtil.endsWith(text, length, "kan") && (flags & 1) == 0 && (flags & 2) == 0 && (flags & 0x40) == 0)
		{
			numSyllables--;
			return length - 3;
		}
		if (StemmerUtil.endsWith(text, length, "an") && (flags & 4) == 0 && (flags & 8) == 0 && (flags & 0x10) == 0)
		{
			numSyllables--;
			return length - 2;
		}
		if (StemmerUtil.endsWith(text, length, "i") && !StemmerUtil.endsWith(text, length, "si") && (flags & 0x20) == 0 && (flags & 1) == 0 && (flags & 2) == 0)
		{
			numSyllables--;
			return length - 1;
		} else
		{
			return length;
		}
	}
}
