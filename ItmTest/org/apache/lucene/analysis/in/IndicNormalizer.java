// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IndicNormalizer.java

package org.apache.lucene.analysis.in;

import java.util.*;
import org.apache.lucene.analysis.util.StemmerUtil;

public class IndicNormalizer
{
	private static class ScriptData
	{

		final int flag;
		final int base;
		BitSet decompMask;

		ScriptData(int flag, int base)
		{
			this.flag = flag;
			this.base = base;
		}
	}


	private static final IdentityHashMap scripts;
	private static final int decompositions[][];

	public IndicNormalizer()
	{
	}

	private static int flag(Character.UnicodeBlock ub)
	{
		return ((ScriptData)scripts.get(ub)).flag;
	}

	public int normalize(char text[], int len)
	{
		for (int i = 0; i < len; i++)
		{
			Character.UnicodeBlock block = Character.UnicodeBlock.of(text[i]);
			ScriptData sd = (ScriptData)scripts.get(block);
			if (sd == null)
				continue;
			int ch = text[i] - sd.base;
			if (sd.decompMask.get(ch))
				len = compose(ch, block, sd, text, i, len);
		}

		return len;
	}

	private int compose(int ch0, Character.UnicodeBlock block0, ScriptData sd, char text[], int pos, int len)
	{
		if (pos + 1 >= len)
			return len;
		int ch1 = text[pos + 1] - sd.base;
		Character.UnicodeBlock block1 = Character.UnicodeBlock.of(text[pos + 1]);
		if (block1 != block0)
			return len;
		int ch2 = -1;
		if (pos + 2 < len)
		{
			ch2 = text[pos + 2] - sd.base;
			Character.UnicodeBlock block2 = Character.UnicodeBlock.of(text[pos + 2]);
			if (text[pos + 2] == '\u200D')
				ch2 = 255;
			else
			if (block2 != block1)
				ch2 = -1;
		}
		for (int i = 0; i < decompositions.length; i++)
			if (decompositions[i][0] == ch0 && (decompositions[i][4] & sd.flag) != 0 && decompositions[i][1] == ch1 && (decompositions[i][2] < 0 || decompositions[i][2] == ch2))
			{
				text[pos] = (char)(sd.base + decompositions[i][3]);
				len = StemmerUtil.delete(text, pos + 1, len);
				if (decompositions[i][2] >= 0)
					len = StemmerUtil.delete(text, pos + 1, len);
				return len;
			}

		return len;
	}

	static 
	{
		scripts = new IdentityHashMap(9);
		scripts.put(Character.UnicodeBlock.DEVANAGARI, new ScriptData(1, 2304));
		scripts.put(Character.UnicodeBlock.BENGALI, new ScriptData(2, 2432));
		scripts.put(Character.UnicodeBlock.GURMUKHI, new ScriptData(4, 2560));
		scripts.put(Character.UnicodeBlock.GUJARATI, new ScriptData(8, 2688));
		scripts.put(Character.UnicodeBlock.ORIYA, new ScriptData(16, 2816));
		scripts.put(Character.UnicodeBlock.TAMIL, new ScriptData(32, 2944));
		scripts.put(Character.UnicodeBlock.TELUGU, new ScriptData(64, 3072));
		scripts.put(Character.UnicodeBlock.KANNADA, new ScriptData(128, 3200));
		scripts.put(Character.UnicodeBlock.MALAYALAM, new ScriptData(256, 3328));
		decompositions = (new int[][] {
			new int[] {
				5, 62, 69, 17, flag(Character.UnicodeBlock.DEVANAGARI) | flag(Character.UnicodeBlock.GUJARATI)
			}, new int[] {
				5, 62, 70, 18, flag(Character.UnicodeBlock.DEVANAGARI)
			}, new int[] {
				5, 62, 71, 19, flag(Character.UnicodeBlock.DEVANAGARI) | flag(Character.UnicodeBlock.GUJARATI)
			}, new int[] {
				5, 62, 72, 20, flag(Character.UnicodeBlock.DEVANAGARI) | flag(Character.UnicodeBlock.GUJARATI)
			}, new int[] {
				5, 62, -1, 6, flag(Character.UnicodeBlock.DEVANAGARI) | flag(Character.UnicodeBlock.BENGALI) | flag(Character.UnicodeBlock.GURMUKHI) | flag(Character.UnicodeBlock.GUJARATI) | flag(Character.UnicodeBlock.ORIYA)
			}, new int[] {
				5, 69, -1, 114, flag(Character.UnicodeBlock.DEVANAGARI)
			}, new int[] {
				5, 69, -1, 13, flag(Character.UnicodeBlock.GUJARATI)
			}, new int[] {
				5, 70, -1, 4, flag(Character.UnicodeBlock.DEVANAGARI)
			}, new int[] {
				5, 71, -1, 15, flag(Character.UnicodeBlock.GUJARATI)
			}, new int[] {
				5, 72, -1, 16, flag(Character.UnicodeBlock.GURMUKHI) | flag(Character.UnicodeBlock.GUJARATI)
			}, new int[] {
				5, 73, -1, 17, flag(Character.UnicodeBlock.DEVANAGARI) | flag(Character.UnicodeBlock.GUJARATI)
			}, new int[] {
				5, 74, -1, 18, flag(Character.UnicodeBlock.DEVANAGARI)
			}, new int[] {
				5, 75, -1, 19, flag(Character.UnicodeBlock.DEVANAGARI) | flag(Character.UnicodeBlock.GUJARATI)
			}, new int[] {
				5, 76, -1, 20, flag(Character.UnicodeBlock.DEVANAGARI) | flag(Character.UnicodeBlock.GURMUKHI) | flag(Character.UnicodeBlock.GUJARATI)
			}, new int[] {
				6, 69, -1, 17, flag(Character.UnicodeBlock.DEVANAGARI) | flag(Character.UnicodeBlock.GUJARATI)
			}, new int[] {
				6, 70, -1, 18, flag(Character.UnicodeBlock.DEVANAGARI)
			}, new int[] {
				6, 71, -1, 19, flag(Character.UnicodeBlock.DEVANAGARI) | flag(Character.UnicodeBlock.GUJARATI)
			}, new int[] {
				6, 72, -1, 20, flag(Character.UnicodeBlock.DEVANAGARI) | flag(Character.UnicodeBlock.GUJARATI)
			}, new int[] {
				7, 87, -1, 8, flag(Character.UnicodeBlock.MALAYALAM)
			}, new int[] {
				9, 65, -1, 10, flag(Character.UnicodeBlock.DEVANAGARI)
			}, new int[] {
				9, 87, -1, 10, flag(Character.UnicodeBlock.TAMIL) | flag(Character.UnicodeBlock.MALAYALAM)
			}, new int[] {
				14, 70, -1, 16, flag(Character.UnicodeBlock.MALAYALAM)
			}, new int[] {
				15, 69, -1, 13, flag(Character.UnicodeBlock.DEVANAGARI)
			}, new int[] {
				15, 70, -1, 14, flag(Character.UnicodeBlock.DEVANAGARI)
			}, new int[] {
				15, 71, -1, 16, flag(Character.UnicodeBlock.DEVANAGARI)
			}, new int[] {
				15, 87, -1, 16, flag(Character.UnicodeBlock.ORIYA)
			}, new int[] {
				18, 62, -1, 19, flag(Character.UnicodeBlock.MALAYALAM)
			}, new int[] {
				18, 76, -1, 20, flag(Character.UnicodeBlock.TELUGU) | flag(Character.UnicodeBlock.KANNADA)
			}, new int[] {
				18, 85, -1, 19, flag(Character.UnicodeBlock.TELUGU)
			}, new int[] {
				18, 87, -1, 20, flag(Character.UnicodeBlock.TAMIL) | flag(Character.UnicodeBlock.MALAYALAM)
			}, new int[] {
				19, 87, -1, 20, flag(Character.UnicodeBlock.ORIYA)
			}, new int[] {
				21, 60, -1, 88, flag(Character.UnicodeBlock.DEVANAGARI)
			}, new int[] {
				22, 60, -1, 89, flag(Character.UnicodeBlock.DEVANAGARI) | flag(Character.UnicodeBlock.GURMUKHI)
			}, new int[] {
				23, 60, -1, 90, flag(Character.UnicodeBlock.DEVANAGARI) | flag(Character.UnicodeBlock.GURMUKHI)
			}, new int[] {
				28, 60, -1, 91, flag(Character.UnicodeBlock.DEVANAGARI) | flag(Character.UnicodeBlock.GURMUKHI)
			}, new int[] {
				33, 60, -1, 92, flag(Character.UnicodeBlock.DEVANAGARI) | flag(Character.UnicodeBlock.BENGALI) | flag(Character.UnicodeBlock.ORIYA)
			}, new int[] {
				34, 60, -1, 93, flag(Character.UnicodeBlock.DEVANAGARI) | flag(Character.UnicodeBlock.BENGALI) | flag(Character.UnicodeBlock.ORIYA)
			}, new int[] {
				35, 77, 255, 122, flag(Character.UnicodeBlock.MALAYALAM)
			}, new int[] {
				36, 77, 255, 78, flag(Character.UnicodeBlock.BENGALI)
			}, new int[] {
				40, 60, -1, 41, flag(Character.UnicodeBlock.DEVANAGARI)
			}, new int[] {
				40, 77, 255, 123, flag(Character.UnicodeBlock.MALAYALAM)
			}, new int[] {
				43, 60, -1, 94, flag(Character.UnicodeBlock.DEVANAGARI) | flag(Character.UnicodeBlock.GURMUKHI)
			}, new int[] {
				47, 60, -1, 95, flag(Character.UnicodeBlock.DEVANAGARI) | flag(Character.UnicodeBlock.BENGALI)
			}, new int[] {
				44, 65, 65, 11, flag(Character.UnicodeBlock.TELUGU)
			}, new int[] {
				48, 60, -1, 49, flag(Character.UnicodeBlock.DEVANAGARI)
			}, new int[] {
				48, 77, 255, 124, flag(Character.UnicodeBlock.MALAYALAM)
			}, new int[] {
				50, 77, 255, 125, flag(Character.UnicodeBlock.MALAYALAM)
			}, new int[] {
				51, 60, -1, 52, flag(Character.UnicodeBlock.DEVANAGARI)
			}, new int[] {
				51, 77, 255, 126, flag(Character.UnicodeBlock.MALAYALAM)
			}, new int[] {
				53, 65, -1, 46, flag(Character.UnicodeBlock.TELUGU)
			}, new int[] {
				62, 69, -1, 73, flag(Character.UnicodeBlock.DEVANAGARI) | flag(Character.UnicodeBlock.GUJARATI)
			}, new int[] {
				62, 70, -1, 74, flag(Character.UnicodeBlock.DEVANAGARI)
			}, new int[] {
				62, 71, -1, 75, flag(Character.UnicodeBlock.DEVANAGARI) | flag(Character.UnicodeBlock.GUJARATI)
			}, new int[] {
				62, 72, -1, 76, flag(Character.UnicodeBlock.DEVANAGARI) | flag(Character.UnicodeBlock.GUJARATI)
			}, new int[] {
				63, 85, -1, 64, flag(Character.UnicodeBlock.KANNADA)
			}, new int[] {
				65, 65, -1, 66, flag(Character.UnicodeBlock.GURMUKHI)
			}, new int[] {
				70, 62, -1, 74, flag(Character.UnicodeBlock.TAMIL) | flag(Character.UnicodeBlock.MALAYALAM)
			}, new int[] {
				70, 66, 85, 75, flag(Character.UnicodeBlock.KANNADA)
			}, new int[] {
				70, 66, -1, 74, flag(Character.UnicodeBlock.KANNADA)
			}, new int[] {
				70, 70, -1, 72, flag(Character.UnicodeBlock.MALAYALAM)
			}, new int[] {
				70, 85, -1, 71, flag(Character.UnicodeBlock.TELUGU) | flag(Character.UnicodeBlock.KANNADA)
			}, new int[] {
				70, 86, -1, 72, flag(Character.UnicodeBlock.TELUGU) | flag(Character.UnicodeBlock.KANNADA)
			}, new int[] {
				70, 87, -1, 76, flag(Character.UnicodeBlock.TAMIL) | flag(Character.UnicodeBlock.MALAYALAM)
			}, new int[] {
				71, 62, -1, 75, flag(Character.UnicodeBlock.BENGALI) | flag(Character.UnicodeBlock.ORIYA) | flag(Character.UnicodeBlock.TAMIL) | flag(Character.UnicodeBlock.MALAYALAM)
			}, new int[] {
				71, 87, -1, 76, flag(Character.UnicodeBlock.BENGALI) | flag(Character.UnicodeBlock.ORIYA)
			}, new int[] {
				74, 85, -1, 75, flag(Character.UnicodeBlock.KANNADA)
			}, new int[] {
				114, 63, -1, 7, flag(Character.UnicodeBlock.GURMUKHI)
			}, new int[] {
				114, 64, -1, 8, flag(Character.UnicodeBlock.GURMUKHI)
			}, new int[] {
				114, 71, -1, 15, flag(Character.UnicodeBlock.GURMUKHI)
			}, new int[] {
				115, 65, -1, 9, flag(Character.UnicodeBlock.GURMUKHI)
			}, new int[] {
				115, 66, -1, 10, flag(Character.UnicodeBlock.GURMUKHI)
			}, new int[] {
				115, 75, -1, 19, flag(Character.UnicodeBlock.GURMUKHI)
			}
		});
		for (Iterator i$ = scripts.values().iterator(); i$.hasNext();)
		{
			ScriptData sd = (ScriptData)i$.next();
			sd.decompMask = new BitSet(127);
			int i = 0;
			while (i < decompositions.length) 
			{
				int ch = decompositions[i][0];
				int flags = decompositions[i][4];
				if ((flags & sd.flag) != 0)
					sd.decompMask.set(ch);
				i++;
			}
		}

	}
}
