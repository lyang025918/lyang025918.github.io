// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ArmenianStemmer.java

package org.tartarus.snowball.ext;

import org.tartarus.snowball.Among;
import org.tartarus.snowball.SnowballProgram;

public class ArmenianStemmer extends SnowballProgram
{

	private static final long serialVersionUID = 1L;
	private static final ArmenianStemmer methodObject;
	private static final Among a_0[];
	private static final Among a_1[];
	private static final Among a_2[];
	private static final Among a_3[];
	private static final char g_v[] = {
		'\321', '\004', '\200', '\0', '\022'
	};
	private int I_p2;
	private int I_pV;

	public ArmenianStemmer()
	{
	}

	private void copy_from(ArmenianStemmer other)
	{
		I_p2 = other.I_p2;
		I_pV = other.I_pV;
		super.copy_from(other);
	}

	private boolean r_mark_regions()
	{
		int v_1;
label0:
		{
			I_pV = limit;
			I_p2 = limit;
			v_1 = cursor;
			while (!in_grouping(g_v, 1377, 1413)) 
			{
				if (cursor >= limit)
					break label0;
				cursor++;
			}
			I_pV = cursor;
			while (!out_grouping(g_v, 1377, 1413)) 
			{
				if (cursor >= limit)
					break label0;
				cursor++;
			}
			while (!in_grouping(g_v, 1377, 1413)) 
			{
				if (cursor >= limit)
					break label0;
				cursor++;
			}
			while (!out_grouping(g_v, 1377, 1413)) 
			{
				if (cursor >= limit)
					break label0;
				cursor++;
			}
			I_p2 = cursor;
		}
		cursor = v_1;
		return true;
	}

	private boolean r_R2()
	{
		return I_p2 <= cursor;
	}

	private boolean r_adjective()
	{
		ket = cursor;
		int among_var = find_among_b(a_0, 23);
		if (among_var == 0)
			return false;
		bra = cursor;
		switch (among_var)
		{
		case 0: // '\0'
			return false;

		case 1: // '\001'
			slice_del();
			break;
		}
		return true;
	}

	private boolean r_verb()
	{
		ket = cursor;
		int among_var = find_among_b(a_1, 71);
		if (among_var == 0)
			return false;
		bra = cursor;
		switch (among_var)
		{
		case 0: // '\0'
			return false;

		case 1: // '\001'
			slice_del();
			break;
		}
		return true;
	}

	private boolean r_noun()
	{
		ket = cursor;
		int among_var = find_among_b(a_2, 40);
		if (among_var == 0)
			return false;
		bra = cursor;
		switch (among_var)
		{
		case 0: // '\0'
			return false;

		case 1: // '\001'
			slice_del();
			break;
		}
		return true;
	}

	private boolean r_ending()
	{
		ket = cursor;
		int among_var = find_among_b(a_3, 57);
		if (among_var == 0)
			return false;
		bra = cursor;
		if (!r_R2())
			return false;
		switch (among_var)
		{
		case 0: // '\0'
			return false;

		case 1: // '\001'
			slice_del();
			break;
		}
		return true;
	}

	public boolean stem()
	{
		int v_1 = cursor;
		if (r_mark_regions());
		cursor = v_1;
		limit_backward = cursor;
		cursor = limit;
		int v_2 = limit - cursor;
		if (cursor < I_pV)
		{
			return false;
		} else
		{
			cursor = I_pV;
			int v_3 = limit_backward;
			limit_backward = cursor;
			cursor = limit - v_2;
			int v_4 = limit - cursor;
			if (r_ending());
			cursor = limit - v_4;
			int v_5 = limit - cursor;
			if (r_verb());
			cursor = limit - v_5;
			int v_6 = limit - cursor;
			if (r_adjective());
			cursor = limit - v_6;
			int v_7 = limit - cursor;
			if (r_noun());
			cursor = limit - v_7;
			limit_backward = v_3;
			cursor = limit_backward;
			return true;
		}
	}

	public boolean equals(Object o)
	{
		return o instanceof ArmenianStemmer;
	}

	public int hashCode()
	{
		return org/tartarus/snowball/ext/ArmenianStemmer.getName().hashCode();
	}

	static 
	{
		methodObject = new ArmenianStemmer();
		a_0 = (new Among[] {
			new Among("????", -1, 1, "", methodObject), new Among("?????", 0, 1, "", methodObject), new Among("???", -1, 1, "", methodObject), new Among("???", -1, 1, "", methodObject), new Among("????", -1, 1, "", methodObject), new Among("??", -1, 1, "", methodObject), new Among("????", -1, 1, "", methodObject), new Among("????", -1, 1, "", methodObject), new Among("??", -1, 1, "", methodObject), new Among("????", 8, 1, "", methodObject), 
			new Among("????", 8, 1, "", methodObject), new Among("????", -1, 1, "", methodObject), new Among("??", -1, 1, "", methodObject), new Among("???", 12, 1, "", methodObject), new Among("????", 12, 1, "", methodObject), new Among("????", -1, 1, "", methodObject), new Among("????", -1, 1, "", methodObject), new Among("???", -1, 1, "", methodObject), new Among("??", -1, 1, "", methodObject), new Among("??", -1, 1, "", methodObject), 
			new Among("????", -1, 1, "", methodObject), new Among("???", -1, 1, "", methodObject), new Among("???", -1, 1, "", methodObject)
		});
		a_1 = (new Among[] {
			new Among("?", -1, 1, "", methodObject), new Among("???", 0, 1, "", methodObject), new Among("???", 0, 1, "", methodObject), new Among("??", -1, 1, "", methodObject), new Among("????", -1, 1, "", methodObject), new Among("???", -1, 1, "", methodObject), new Among("???", -1, 1, "", methodObject), new Among("????", 6, 1, "", methodObject), new Among("??", -1, 1, "", methodObject), new Among("???", 8, 1, "", methodObject), 
			new Among("????", 8, 1, "", methodObject), new Among("????", 8, 1, "", methodObject), new Among("?????", 8, 1, "", methodObject), new Among("??", -1, 1, "", methodObject), new Among("???", 13, 1, "", methodObject), new Among("???", 13, 1, "", methodObject), new Among("????", 15, 1, "", methodObject), new Among("?????", 16, 1, "", methodObject), new Among("???", 13, 1, "", methodObject), new Among("???", 13, 1, "", methodObject), 
			new Among("?????", 19, 1, "", methodObject), new Among("?????", 19, 1, "", methodObject), new Among("???", 13, 1, "", methodObject), new Among("????", 22, 1, "", methodObject), new Among("????", 22, 1, "", methodObject), new Among("?????", 24, 1, "", methodObject), new Among("???", -1, 1, "", methodObject), new Among("???", -1, 1, "", methodObject), new Among("????", 27, 1, "", methodObject), new Among("??", -1, 1, "", methodObject), 
			new Among("???", 29, 1, "", methodObject), new Among("????", 30, 1, "", methodObject), new Among("?????", -1, 1, "", methodObject), new Among("????", -1, 1, "", methodObject), new Among("????", -1, 1, "", methodObject), new Among("?????", 34, 1, "", methodObject), new Among("????", -1, 1, "", methodObject), new Among("????", -1, 1, "", methodObject), new Among("??", -1, 1, "", methodObject), new Among("????", 38, 1, "", methodObject), 
			new Among("????", 38, 1, "", methodObject), new Among("????", -1, 1, "", methodObject), new Among("????", -1, 1, "", methodObject), new Among("??", -1, 1, "", methodObject), new Among("????", 43, 1, "", methodObject), new Among("????", 43, 1, "", methodObject), new Among("?????", -1, 1, "", methodObject), new Among("????", -1, 1, "", methodObject), new Among("????", -1, 1, "", methodObject), new Among("?????", 48, 1, "", methodObject), 
			new Among("??", -1, 1, "", methodObject), new Among("??", -1, 1, "", methodObject), new Among("?????", 51, 1, "", methodObject), new Among("?????", -1, 1, "", methodObject), new Among("?????", -1, 1, "", methodObject), new Among("????", -1, 1, "", methodObject), new Among("????", -1, 1, "", methodObject), new Among("??", -1, 1, "", methodObject), new Among("???", 57, 1, "", methodObject), new Among("????", 58, 1, "", methodObject), 
			new Among("?????", -1, 1, "", methodObject), new Among("????", -1, 1, "", methodObject), new Among("????", -1, 1, "", methodObject), new Among("?????", 62, 1, "", methodObject), new Among("???", -1, 1, "", methodObject), new Among("????", 64, 1, "", methodObject), new Among("?????", 65, 1, "", methodObject), new Among("??????", -1, 1, "", methodObject), new Among("?????", -1, 1, "", methodObject), new Among("?????", -1, 1, "", methodObject), 
			new Among("??????", 69, 1, "", methodObject)
		});
		a_2 = (new Among[] {
			new Among("???", -1, 1, "", methodObject), new Among("????", -1, 1, "", methodObject), new Among("????", -1, 1, "", methodObject), new Among("??", -1, 1, "", methodObject), new Among("??", -1, 1, "", methodObject), new Among("??", -1, 1, "", methodObject), new Among("???", 5, 1, "", methodObject), new Among("????", 5, 1, "", methodObject), new Among("??", -1, 1, "", methodObject), new Among("???", -1, 1, "", methodObject), 
			new Among("??", -1, 1, "", methodObject), new Among("???", 10, 1, "", methodObject), new Among("????", 10, 1, "", methodObject), new Among("????", 10, 1, "", methodObject), new Among("????", -1, 1, "", methodObject), new Among("????", -1, 1, "", methodObject), new Among("???????", 15, 1, "", methodObject), new Among("???", -1, 1, "", methodObject), new Among("??", -1, 1, "", methodObject), new Among("???", -1, 1, "", methodObject), 
			new Among("????", -1, 1, "", methodObject), new Among("???", -1, 1, "", methodObject), new Among("???", -1, 1, "", methodObject), new Among("????", 22, 1, "", methodObject), new Among("??", -1, 1, "", methodObject), new Among("????", -1, 1, "", methodObject), new Among("??", -1, 1, "", methodObject), new Among("?", -1, 1, "", methodObject), new Among("???", 27, 1, "", methodObject), new Among("??", 27, 1, "", methodObject), 
			new Among("????", 29, 1, "", methodObject), new Among("????", 29, 1, "", methodObject), new Among("????", 27, 1, "", methodObject), new Among("????", 27, 1, "", methodObject), new Among("???", 27, 1, "", methodObject), new Among("???", 27, 1, "", methodObject), new Among("????", 27, 1, "", methodObject), new Among("?????", 36, 1, "", methodObject), new Among("???", 27, 1, "", methodObject), new Among("???", 27, 1, "", methodObject)
		});
		a_3 = (new Among[] {
			new Among("??", -1, 1, "", methodObject), new Among("??", -1, 1, "", methodObject), new Among("???", -1, 1, "", methodObject), new Among("?", -1, 1, "", methodObject), new Among("???", 3, 1, "", methodObject), new Among("???????", 4, 1, "", methodObject), new Among("????", 4, 1, "", methodObject), new Among("???", 3, 1, "", methodObject), new Among("???", 3, 1, "", methodObject), new Among("????", 8, 1, "", methodObject), 
			new Among("???", 3, 1, "", methodObject), new Among("?", -1, 1, "", methodObject), new Among("???", 11, 1, "", methodObject), new Among("???????", 12, 1, "", methodObject), new Among("????", 12, 1, "", methodObject), new Among("???", 11, 1, "", methodObject), new Among("???", 11, 1, "", methodObject), new Among("????", 16, 1, "", methodObject), new Among("?", -1, 1, "", methodObject), new Among("??", 18, 1, "", methodObject), 
			new Among("???", 18, 1, "", methodObject), new Among("????", 20, 1, "", methodObject), new Among("?????", -1, 1, "", methodObject), new Among("?????", -1, 1, "", methodObject), new Among("??????", 23, 1, "", methodObject), new Among("?", -1, 1, "", methodObject), new Among("??", 25, 1, "", methodObject), new Among("??????", 26, 1, "", methodObject), new Among("???", 26, 1, "", methodObject), new Among("??", 25, 1, "", methodObject), 
			new Among("????", 29, 1, "", methodObject), new Among("?????", 30, 1, "", methodObject), new Among("???????", 25, 1, "", methodObject), new Among("???", 25, 1, "", methodObject), new Among("????", 33, 1, "", methodObject), new Among("???", 25, 1, "", methodObject), new Among("??", -1, 1, "", methodObject), new Among("???????", -1, 1, "", methodObject), new Among("????", -1, 1, "", methodObject), new Among("???", -1, 1, "", methodObject), 
			new Among("??", -1, 1, "", methodObject), new Among("????", 40, 1, "", methodObject), new Among("???", 40, 1, "", methodObject), new Among("????", 40, 1, "", methodObject), new Among("?????", 43, 1, "", methodObject), new Among("??", -1, 1, "", methodObject), new Among("???", 45, 1, "", methodObject), new Among("?", -1, 1, "", methodObject), new Among("??", 47, 1, "", methodObject), new Among("?????", 48, 1, "", methodObject), 
			new Among("????", 48, 1, "", methodObject), new Among("???", 48, 1, "", methodObject), new Among("????", 48, 1, "", methodObject), new Among("?????", 52, 1, "", methodObject), new Among("???", 48, 1, "", methodObject), new Among("??", 47, 1, "", methodObject), new Among("???", 47, 1, "", methodObject)
		});
	}
}
