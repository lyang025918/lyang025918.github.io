// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IrishStemmer.java

package org.tartarus.snowball.ext;

import org.tartarus.snowball.Among;
import org.tartarus.snowball.SnowballProgram;

public class IrishStemmer extends SnowballProgram
{

	private static final long serialVersionUID = 1L;
	private static final IrishStemmer methodObject;
	private static final Among a_0[];
	private static final Among a_1[];
	private static final Among a_2[];
	private static final Among a_3[];
	private static final char g_v[] = {
		'\021', 'A', '\020', '\0', '\0', '\0', '\0', '\0', '\0', '\0', 
		'\0', '\0', '\0', '\0', '\0', '\0', '\001', '\021', '\004', '\002'
	};
	private int I_p2;
	private int I_p1;
	private int I_pV;

	public IrishStemmer()
	{
	}

	private void copy_from(IrishStemmer other)
	{
		I_p2 = other.I_p2;
		I_p1 = other.I_p1;
		I_pV = other.I_pV;
		super.copy_from(other);
	}

	private boolean r_mark_regions()
	{
		int v_1;
label0:
		{
			I_pV = limit;
			I_p1 = limit;
			I_p2 = limit;
			v_1 = cursor;
			while (!in_grouping(g_v, 97, 250)) 
			{
				if (cursor >= limit)
					break label0;
				cursor++;
			}
			I_pV = cursor;
		}
		int v_3;
label1:
		{
			cursor = v_1;
			v_3 = cursor;
			while (!in_grouping(g_v, 97, 250)) 
			{
				if (cursor >= limit)
					break label1;
				cursor++;
			}
			while (!out_grouping(g_v, 97, 250)) 
			{
				if (cursor >= limit)
					break label1;
				cursor++;
			}
			I_p1 = cursor;
			while (!in_grouping(g_v, 97, 250)) 
			{
				if (cursor >= limit)
					break label1;
				cursor++;
			}
			while (!out_grouping(g_v, 97, 250)) 
			{
				if (cursor >= limit)
					break label1;
				cursor++;
			}
			I_p2 = cursor;
		}
		cursor = v_3;
		return true;
	}

	private boolean r_initial_morph()
	{
		bra = cursor;
		int among_var = find_among(a_0, 24);
		if (among_var == 0)
			return false;
		ket = cursor;
		switch (among_var)
		{
		case 0: // '\0'
			return false;

		case 1: // '\001'
			slice_del();
			break;

		case 2: // '\002'
			slice_del();
			break;

		case 3: // '\003'
			slice_from("f");
			break;

		case 4: // '\004'
			slice_del();
			break;

		case 5: // '\005'
			slice_from("s");
			break;

		case 6: // '\006'
			slice_from("b");
			break;

		case 7: // '\007'
			slice_from("c");
			break;

		case 8: // '\b'
			slice_from("d");
			break;

		case 9: // '\t'
			slice_from("f");
			break;

		case 10: // '\n'
			slice_from("g");
			break;

		case 11: // '\013'
			slice_from("p");
			break;

		case 12: // '\f'
			slice_from("s");
			break;

		case 13: // '\r'
			slice_from("t");
			break;

		case 14: // '\016'
			slice_from("b");
			break;

		case 15: // '\017'
			slice_from("c");
			break;

		case 16: // '\020'
			slice_from("d");
			break;

		case 17: // '\021'
			slice_from("f");
			break;

		case 18: // '\022'
			slice_from("g");
			break;

		case 19: // '\023'
			slice_from("m");
			break;

		case 20: // '\024'
			slice_from("p");
			break;

		case 21: // '\025'
			slice_from("t");
			break;
		}
		return true;
	}

	private boolean r_RV()
	{
		return I_pV <= cursor;
	}

	private boolean r_R1()
	{
		return I_p1 <= cursor;
	}

	private boolean r_R2()
	{
		return I_p2 <= cursor;
	}

	private boolean r_noun_sfx()
	{
		ket = cursor;
		int among_var = find_among_b(a_1, 16);
		if (among_var == 0)
			return false;
		bra = cursor;
		switch (among_var)
		{
		default:
			break;

		case 0: // '\0'
			return false;

		case 1: // '\001'
			if (!r_R1())
				return false;
			slice_del();
			break;

		case 2: // '\002'
			if (!r_R2())
				return false;
			slice_del();
			break;
		}
		return true;
	}

	private boolean r_deriv()
	{
		ket = cursor;
		int among_var = find_among_b(a_2, 25);
		if (among_var == 0)
			return false;
		bra = cursor;
		switch (among_var)
		{
		default:
			break;

		case 0: // '\0'
			return false;

		case 1: // '\001'
			if (!r_R2())
				return false;
			slice_del();
			break;

		case 2: // '\002'
			slice_from("arc");
			break;

		case 3: // '\003'
			slice_from("gin");
			break;

		case 4: // '\004'
			slice_from("graf");
			break;

		case 5: // '\005'
			slice_from("paite");
			break;

		case 6: // '\006'
			slice_from("\363id");
			break;
		}
		return true;
	}

	private boolean r_verb_sfx()
	{
		ket = cursor;
		int among_var = find_among_b(a_3, 12);
		if (among_var == 0)
			return false;
		bra = cursor;
		switch (among_var)
		{
		default:
			break;

		case 0: // '\0'
			return false;

		case 1: // '\001'
			if (!r_RV())
				return false;
			slice_del();
			break;

		case 2: // '\002'
			if (!r_R1())
				return false;
			slice_del();
			break;
		}
		return true;
	}

	public boolean stem()
	{
		int v_1 = cursor;
		if (r_initial_morph());
		cursor = v_1;
		int v_2 = cursor;
		if (r_mark_regions());
		cursor = v_2;
		limit_backward = cursor;
		cursor = limit;
		int v_3 = limit - cursor;
		if (r_noun_sfx());
		cursor = limit - v_3;
		int v_4 = limit - cursor;
		if (r_deriv());
		cursor = limit - v_4;
		int v_5 = limit - cursor;
		if (r_verb_sfx());
		cursor = limit - v_5;
		cursor = limit_backward;
		return true;
	}

	public boolean equals(Object o)
	{
		return o instanceof IrishStemmer;
	}

	public int hashCode()
	{
		return org/tartarus/snowball/ext/IrishStemmer.getName().hashCode();
	}

	static 
	{
		methodObject = new IrishStemmer();
		a_0 = (new Among[] {
			new Among("b'", -1, 4, "", methodObject), new Among("bh", -1, 14, "", methodObject), new Among("bhf", 1, 9, "", methodObject), new Among("bp", -1, 11, "", methodObject), new Among("ch", -1, 15, "", methodObject), new Among("d'", -1, 2, "", methodObject), new Among("d'fh", 5, 3, "", methodObject), new Among("dh", -1, 16, "", methodObject), new Among("dt", -1, 13, "", methodObject), new Among("fh", -1, 17, "", methodObject), 
			new Among("gc", -1, 7, "", methodObject), new Among("gh", -1, 18, "", methodObject), new Among("h-", -1, 1, "", methodObject), new Among("m'", -1, 4, "", methodObject), new Among("mb", -1, 6, "", methodObject), new Among("mh", -1, 19, "", methodObject), new Among("n-", -1, 1, "", methodObject), new Among("nd", -1, 8, "", methodObject), new Among("ng", -1, 10, "", methodObject), new Among("ph", -1, 20, "", methodObject), 
			new Among("sh", -1, 5, "", methodObject), new Among("t-", -1, 1, "", methodObject), new Among("th", -1, 21, "", methodObject), new Among("ts", -1, 12, "", methodObject)
		});
		a_1 = (new Among[] {
			new Among("\355ochta", -1, 1, "", methodObject), new Among("a\355ochta", 0, 1, "", methodObject), new Among("ire", -1, 2, "", methodObject), new Among("aire", 2, 2, "", methodObject), new Among("abh", -1, 1, "", methodObject), new Among("eabh", 4, 1, "", methodObject), new Among("ibh", -1, 1, "", methodObject), new Among("aibh", 6, 1, "", methodObject), new Among("amh", -1, 1, "", methodObject), new Among("eamh", 8, 1, "", methodObject), 
			new Among("imh", -1, 1, "", methodObject), new Among("aimh", 10, 1, "", methodObject), new Among("\355ocht", -1, 1, "", methodObject), new Among("a\355ocht", 12, 1, "", methodObject), new Among("ir\355", -1, 2, "", methodObject), new Among("air\355", 14, 2, "", methodObject)
		});
		a_2 = (new Among[] {
			new Among("\363ideacha", -1, 6, "", methodObject), new Among("patacha", -1, 5, "", methodObject), new Among("achta", -1, 1, "", methodObject), new Among("arcachta", 2, 2, "", methodObject), new Among("eachta", 2, 1, "", methodObject), new Among("grafa\355ochta", -1, 4, "", methodObject), new Among("paite", -1, 5, "", methodObject), new Among("ach", -1, 1, "", methodObject), new Among("each", 7, 1, "", methodObject), new Among("\363ideach", 8, 6, "", methodObject), 
			new Among("gineach", 8, 3, "", methodObject), new Among("patach", 7, 5, "", methodObject), new Among("grafa\355och", -1, 4, "", methodObject), new Among("pataigh", -1, 5, "", methodObject), new Among("\363idigh", -1, 6, "", methodObject), new Among("acht\372il", -1, 1, "", methodObject), new Among("eacht\372il", 15, 1, "", methodObject), new Among("gineas", -1, 3, "", methodObject), new Among("ginis", -1, 3, "", methodObject), new Among("acht", -1, 1, "", methodObject), 
			new Among("arcacht", 19, 2, "", methodObject), new Among("eacht", 19, 1, "", methodObject), new Among("grafa\355ocht", -1, 4, "", methodObject), new Among("arcachta\355", -1, 2, "", methodObject), new Among("grafa\355ochta\355", -1, 4, "", methodObject)
		});
		a_3 = (new Among[] {
			new Among("imid", -1, 1, "", methodObject), new Among("aimid", 0, 1, "", methodObject), new Among("\355mid", -1, 1, "", methodObject), new Among("a\355mid", 2, 1, "", methodObject), new Among("adh", -1, 2, "", methodObject), new Among("eadh", 4, 2, "", methodObject), new Among("faidh", -1, 1, "", methodObject), new Among("fidh", -1, 1, "", methodObject), new Among("\341il", -1, 2, "", methodObject), new Among("ain", -1, 2, "", methodObject), 
			new Among("tear", -1, 2, "", methodObject), new Among("tar", -1, 2, "", methodObject)
		});
	}
}
