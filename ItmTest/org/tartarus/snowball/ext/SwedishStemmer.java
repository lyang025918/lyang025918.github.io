// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SwedishStemmer.java

package org.tartarus.snowball.ext;

import org.tartarus.snowball.Among;
import org.tartarus.snowball.SnowballProgram;

public class SwedishStemmer extends SnowballProgram
{

	private static final long serialVersionUID = 1L;
	private static final SwedishStemmer methodObject;
	private static final Among a_0[];
	private static final Among a_1[];
	private static final Among a_2[];
	private static final char g_v[] = {
		'\021', 'A', '\020', '\001', '\0', '\0', '\0', '\0', '\0', '\0', 
		'\0', '\0', '\0', '\0', '\0', '\0', '\030', '\0', ' '
	};
	private static final char g_s_ending[] = {
		'w', '\177', '\225'
	};
	private int I_x;
	private int I_p1;

	public SwedishStemmer()
	{
	}

	private void copy_from(SwedishStemmer other)
	{
		I_x = other.I_x;
		I_p1 = other.I_p1;
		super.copy_from(other);
	}

	private boolean r_mark_regions()
	{
		I_p1 = limit;
		int v_1 = cursor;
		int c = cursor + 3;
		if (0 > c || c > limit)
			return false;
		cursor = c;
		I_x = cursor;
		cursor = v_1;
		do
		{
			int v_2 = cursor;
			if (in_grouping(g_v, 97, 246))
			{
				cursor = v_2;
				break;
			}
			cursor = v_2;
			if (cursor >= limit)
				return false;
			cursor++;
		} while (true);
		while (!out_grouping(g_v, 97, 246)) 
		{
			if (cursor >= limit)
				return false;
			cursor++;
		}
		I_p1 = cursor;
		if (I_p1 < I_x)
			I_p1 = I_x;
		return true;
	}

	private boolean r_main_suffix()
	{
		int v_1 = limit - cursor;
		if (cursor < I_p1)
			return false;
		cursor = I_p1;
		int v_2 = limit_backward;
		limit_backward = cursor;
		cursor = limit - v_1;
		ket = cursor;
		int among_var = find_among_b(a_0, 37);
		if (among_var == 0)
		{
			limit_backward = v_2;
			return false;
		}
		bra = cursor;
		limit_backward = v_2;
		switch (among_var)
		{
		default:
			break;

		case 0: // '\0'
			return false;

		case 1: // '\001'
			slice_del();
			break;

		case 2: // '\002'
			if (!in_grouping_b(g_s_ending, 98, 121))
				return false;
			slice_del();
			break;
		}
		return true;
	}

	private boolean r_consonant_pair()
	{
		int v_1 = limit - cursor;
		if (cursor < I_p1)
			return false;
		cursor = I_p1;
		int v_2 = limit_backward;
		limit_backward = cursor;
		cursor = limit - v_1;
		int v_3 = limit - cursor;
		if (find_among_b(a_1, 7) == 0)
		{
			limit_backward = v_2;
			return false;
		}
		cursor = limit - v_3;
		ket = cursor;
		if (cursor <= limit_backward)
		{
			limit_backward = v_2;
			return false;
		} else
		{
			cursor--;
			bra = cursor;
			slice_del();
			limit_backward = v_2;
			return true;
		}
	}

	private boolean r_other_suffix()
	{
		int v_1 = limit - cursor;
		if (cursor < I_p1)
			return false;
		cursor = I_p1;
		int v_2 = limit_backward;
		limit_backward = cursor;
		cursor = limit - v_1;
		ket = cursor;
		int among_var = find_among_b(a_2, 5);
		if (among_var == 0)
		{
			limit_backward = v_2;
			return false;
		}
		bra = cursor;
		switch (among_var)
		{
		case 0: // '\0'
			limit_backward = v_2;
			return false;

		case 1: // '\001'
			slice_del();
			break;

		case 2: // '\002'
			slice_from("l\366s");
			break;

		case 3: // '\003'
			slice_from("full");
			break;
		}
		limit_backward = v_2;
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
		if (r_main_suffix());
		cursor = limit - v_2;
		int v_3 = limit - cursor;
		if (r_consonant_pair());
		cursor = limit - v_3;
		int v_4 = limit - cursor;
		if (r_other_suffix());
		cursor = limit - v_4;
		cursor = limit_backward;
		return true;
	}

	public boolean equals(Object o)
	{
		return o instanceof SwedishStemmer;
	}

	public int hashCode()
	{
		return org/tartarus/snowball/ext/SwedishStemmer.getName().hashCode();
	}

	static 
	{
		methodObject = new SwedishStemmer();
		a_0 = (new Among[] {
			new Among("a", -1, 1, "", methodObject), new Among("arna", 0, 1, "", methodObject), new Among("erna", 0, 1, "", methodObject), new Among("heterna", 2, 1, "", methodObject), new Among("orna", 0, 1, "", methodObject), new Among("ad", -1, 1, "", methodObject), new Among("e", -1, 1, "", methodObject), new Among("ade", 6, 1, "", methodObject), new Among("ande", 6, 1, "", methodObject), new Among("arne", 6, 1, "", methodObject), 
			new Among("are", 6, 1, "", methodObject), new Among("aste", 6, 1, "", methodObject), new Among("en", -1, 1, "", methodObject), new Among("anden", 12, 1, "", methodObject), new Among("aren", 12, 1, "", methodObject), new Among("heten", 12, 1, "", methodObject), new Among("ern", -1, 1, "", methodObject), new Among("ar", -1, 1, "", methodObject), new Among("er", -1, 1, "", methodObject), new Among("heter", 18, 1, "", methodObject), 
			new Among("or", -1, 1, "", methodObject), new Among("s", -1, 2, "", methodObject), new Among("as", 21, 1, "", methodObject), new Among("arnas", 22, 1, "", methodObject), new Among("ernas", 22, 1, "", methodObject), new Among("ornas", 22, 1, "", methodObject), new Among("es", 21, 1, "", methodObject), new Among("ades", 26, 1, "", methodObject), new Among("andes", 26, 1, "", methodObject), new Among("ens", 21, 1, "", methodObject), 
			new Among("arens", 29, 1, "", methodObject), new Among("hetens", 29, 1, "", methodObject), new Among("erns", 21, 1, "", methodObject), new Among("at", -1, 1, "", methodObject), new Among("andet", -1, 1, "", methodObject), new Among("het", -1, 1, "", methodObject), new Among("ast", -1, 1, "", methodObject)
		});
		a_1 = (new Among[] {
			new Among("dd", -1, -1, "", methodObject), new Among("gd", -1, -1, "", methodObject), new Among("nn", -1, -1, "", methodObject), new Among("dt", -1, -1, "", methodObject), new Among("gt", -1, -1, "", methodObject), new Among("kt", -1, -1, "", methodObject), new Among("tt", -1, -1, "", methodObject)
		});
		a_2 = (new Among[] {
			new Among("ig", -1, 1, "", methodObject), new Among("lig", 0, 1, "", methodObject), new Among("els", -1, 1, "", methodObject), new Among("fullt", -1, 3, "", methodObject), new Among("l\366st", -1, 2, "", methodObject)
		});
	}
}
