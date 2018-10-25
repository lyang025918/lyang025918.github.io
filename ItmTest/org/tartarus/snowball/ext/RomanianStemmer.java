// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RomanianStemmer.java

package org.tartarus.snowball.ext;

import org.tartarus.snowball.Among;
import org.tartarus.snowball.SnowballProgram;

public class RomanianStemmer extends SnowballProgram
{

	private static final long serialVersionUID = 1L;
	private static final RomanianStemmer methodObject;
	private static final Among a_0[];
	private static final Among a_1[];
	private static final Among a_2[];
	private static final Among a_3[];
	private static final Among a_4[];
	private static final Among a_5[];
	private static final char g_v[] = {
		'\021', 'A', '\020', '\0', '\0', '\0', '\0', '\0', '\0', '\0', 
		'\0', '\0', '\0', '\0', '\0', '\0', '\002', ' ', '\0', '\0', 
		'\004'
	};
	private boolean B_standard_suffix_removed;
	private int I_p2;
	private int I_p1;
	private int I_pV;

	public RomanianStemmer()
	{
	}

	private void copy_from(RomanianStemmer other)
	{
		B_standard_suffix_removed = other.B_standard_suffix_removed;
		I_p2 = other.I_p2;
		I_p1 = other.I_p1;
		I_pV = other.I_pV;
		super.copy_from(other);
	}

	private boolean r_prelude()
	{
label0:
		do
		{
			int v_1 = cursor;
			do
			{
				int v_2;
label1:
				{
label2:
					{
						v_2 = cursor;
						if (!in_grouping(g_v, 97, 259))
							break label1;
						bra = cursor;
						int v_3 = cursor;
						if (eq_s(1, "u"))
						{
							ket = cursor;
							if (in_grouping(g_v, 97, 259))
							{
								slice_from("U");
								break label2;
							}
						}
						cursor = v_3;
						if (!eq_s(1, "i"))
							break label1;
						ket = cursor;
						if (!in_grouping(g_v, 97, 259))
							break label1;
						slice_from("I");
					}
					cursor = v_2;
					continue label0;
				}
				cursor = v_2;
				if (cursor >= limit)
					break;
				cursor++;
			} while (true);
			cursor = v_1;
			return true;
		} while (true);
	}

	private boolean r_mark_regions()
	{
		int v_1;
label0:
		{
label1:
			{
				I_pV = limit;
				I_p1 = limit;
				I_p2 = limit;
				v_1 = cursor;
				int v_2 = cursor;
				if (in_grouping(g_v, 97, 259))
				{
					int v_3 = cursor;
					if (out_grouping(g_v, 97, 259))
						do
						{
							if (in_grouping(g_v, 97, 259))
								break label1;
							if (cursor >= limit)
								break;
							cursor++;
						} while (true);
					cursor = v_3;
					if (in_grouping(g_v, 97, 259))
						do
						{
							if (out_grouping(g_v, 97, 259))
								break label1;
							if (cursor >= limit)
								break;
							cursor++;
						} while (true);
				}
				cursor = v_2;
				if (!out_grouping(g_v, 97, 259))
					break label0;
				int v_6 = cursor;
				if (out_grouping(g_v, 97, 259))
					do
					{
						if (in_grouping(g_v, 97, 259))
							break label1;
						if (cursor >= limit)
							break;
						cursor++;
					} while (true);
				cursor = v_6;
				if (!in_grouping(g_v, 97, 259) || cursor >= limit)
					break label0;
				cursor++;
			}
			I_pV = cursor;
		}
		int v_8;
label2:
		{
			cursor = v_1;
			v_8 = cursor;
			while (!in_grouping(g_v, 97, 259)) 
			{
				if (cursor >= limit)
					break label2;
				cursor++;
			}
			while (!out_grouping(g_v, 97, 259)) 
			{
				if (cursor >= limit)
					break label2;
				cursor++;
			}
			I_p1 = cursor;
			while (!in_grouping(g_v, 97, 259)) 
			{
				if (cursor >= limit)
					break label2;
				cursor++;
			}
			while (!out_grouping(g_v, 97, 259)) 
			{
				if (cursor >= limit)
					break label2;
				cursor++;
			}
			I_p2 = cursor;
		}
		cursor = v_8;
		return true;
	}

	private boolean r_postlude()
	{
		int v_1;
label0:
		do
		{
			v_1 = cursor;
			bra = cursor;
			int among_var = find_among(a_0, 3);
			if (among_var == 0)
				break;
			ket = cursor;
			switch (among_var)
			{
			default:
				break;

			case 0: // '\0'
				break label0;

			case 1: // '\001'
				slice_from("i");
				break;

			case 2: // '\002'
				slice_from("u");
				break;

			case 3: // '\003'
				if (cursor >= limit)
					break label0;
				cursor++;
				break;
			}
		} while (true);
		cursor = v_1;
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

	private boolean r_step_0()
	{
		ket = cursor;
		int among_var = find_among_b(a_1, 16);
		if (among_var == 0)
			return false;
		bra = cursor;
		if (!r_R1())
			return false;
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
			slice_from("a");
			break;

		case 3: // '\003'
			slice_from("e");
			break;

		case 4: // '\004'
			slice_from("i");
			break;

		case 5: // '\005'
			int v_1 = limit - cursor;
			if (eq_s_b(2, "ab"))
				return false;
			cursor = limit - v_1;
			slice_from("i");
			break;

		case 6: // '\006'
			slice_from("at");
			break;

		case 7: // '\007'
			slice_from("a?i");
			break;
		}
		return true;
	}

	private boolean r_combo_suffix()
	{
		int v_1 = limit - cursor;
		ket = cursor;
		int among_var = find_among_b(a_2, 46);
		if (among_var == 0)
			return false;
		bra = cursor;
		if (!r_R1())
			return false;
		switch (among_var)
		{
		case 0: // '\0'
			return false;

		case 1: // '\001'
			slice_from("abil");
			break;

		case 2: // '\002'
			slice_from("ibil");
			break;

		case 3: // '\003'
			slice_from("iv");
			break;

		case 4: // '\004'
			slice_from("ic");
			break;

		case 5: // '\005'
			slice_from("at");
			break;

		case 6: // '\006'
			slice_from("it");
			break;
		}
		B_standard_suffix_removed = true;
		cursor = limit - v_1;
		return true;
	}

	private boolean r_standard_suffix()
	{
		B_standard_suffix_removed = false;
		int v_1;
		do
			v_1 = limit - cursor;
		while (r_combo_suffix());
		cursor = limit - v_1;
		ket = cursor;
		int among_var = find_among_b(a_3, 62);
		if (among_var == 0)
			return false;
		bra = cursor;
		if (!r_R2())
			return false;
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
			if (!eq_s_b(1, "?"))
				return false;
			bra = cursor;
			slice_from("t");
			break;

		case 3: // '\003'
			slice_from("ist");
			break;
		}
		B_standard_suffix_removed = true;
		return true;
	}

	private boolean r_verb_suffix()
	{
		int v_1 = limit - cursor;
		if (cursor < I_pV)
			return false;
		cursor = I_pV;
		int v_2 = limit_backward;
		limit_backward = cursor;
		cursor = limit - v_1;
		ket = cursor;
		int among_var = find_among_b(a_4, 94);
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
			int v_3 = limit - cursor;
			if (!out_grouping_b(g_v, 97, 259))
			{
				cursor = limit - v_3;
				if (!eq_s_b(1, "u"))
				{
					limit_backward = v_2;
					return false;
				}
			}
			slice_del();
			break;

		case 2: // '\002'
			slice_del();
			break;
		}
		limit_backward = v_2;
		return true;
	}

	private boolean r_vowel_suffix()
	{
		ket = cursor;
		int among_var = find_among_b(a_5, 5);
		if (among_var == 0)
			return false;
		bra = cursor;
		if (!r_RV())
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
		if (r_prelude());
		cursor = v_1;
		int v_2 = cursor;
		if (r_mark_regions());
		cursor = v_2;
		limit_backward = cursor;
		cursor = limit;
		int v_3 = limit - cursor;
		if (r_step_0());
		cursor = limit - v_3;
		int v_4 = limit - cursor;
		if (r_standard_suffix());
		cursor = limit - v_4;
		int v_5 = limit - cursor;
		int v_6 = limit - cursor;
		if (!B_standard_suffix_removed)
		{
			cursor = limit - v_6;
			if (r_verb_suffix());
		}
		cursor = limit - v_5;
		int v_7 = limit - cursor;
		if (r_vowel_suffix());
		cursor = limit - v_7;
		cursor = limit_backward;
		int v_8 = cursor;
		if (r_postlude());
		cursor = v_8;
		return true;
	}

	public boolean equals(Object o)
	{
		return o instanceof RomanianStemmer;
	}

	public int hashCode()
	{
		return org/tartarus/snowball/ext/RomanianStemmer.getName().hashCode();
	}

	static 
	{
		methodObject = new RomanianStemmer();
		a_0 = (new Among[] {
			new Among("", -1, 3, "", methodObject), new Among("I", 0, 1, "", methodObject), new Among("U", 0, 2, "", methodObject)
		});
		a_1 = (new Among[] {
			new Among("ea", -1, 3, "", methodObject), new Among("a?ia", -1, 7, "", methodObject), new Among("aua", -1, 2, "", methodObject), new Among("iua", -1, 4, "", methodObject), new Among("a?ie", -1, 7, "", methodObject), new Among("ele", -1, 3, "", methodObject), new Among("ile", -1, 5, "", methodObject), new Among("iile", 6, 4, "", methodObject), new Among("iei", -1, 4, "", methodObject), new Among("atei", -1, 6, "", methodObject), 
			new Among("ii", -1, 4, "", methodObject), new Among("ului", -1, 1, "", methodObject), new Among("ul", -1, 1, "", methodObject), new Among("elor", -1, 3, "", methodObject), new Among("ilor", -1, 4, "", methodObject), new Among("iilor", 14, 4, "", methodObject)
		});
		a_2 = (new Among[] {
			new Among("icala", -1, 4, "", methodObject), new Among("iciva", -1, 4, "", methodObject), new Among("ativa", -1, 5, "", methodObject), new Among("itiva", -1, 6, "", methodObject), new Among("icale", -1, 4, "", methodObject), new Among("a?iune", -1, 5, "", methodObject), new Among("i?iune", -1, 6, "", methodObject), new Among("atoare", -1, 5, "", methodObject), new Among("itoare", -1, 6, "", methodObject), new Among("?toare", -1, 5, "", methodObject), 
			new Among("icitate", -1, 4, "", methodObject), new Among("abilitate", -1, 1, "", methodObject), new Among("ibilitate", -1, 2, "", methodObject), new Among("ivitate", -1, 3, "", methodObject), new Among("icive", -1, 4, "", methodObject), new Among("ative", -1, 5, "", methodObject), new Among("itive", -1, 6, "", methodObject), new Among("icali", -1, 4, "", methodObject), new Among("atori", -1, 5, "", methodObject), new Among("icatori", 18, 4, "", methodObject), 
			new Among("itori", -1, 6, "", methodObject), new Among("?tori", -1, 5, "", methodObject), new Among("icitati", -1, 4, "", methodObject), new Among("abilitati", -1, 1, "", methodObject), new Among("ivitati", -1, 3, "", methodObject), new Among("icivi", -1, 4, "", methodObject), new Among("ativi", -1, 5, "", methodObject), new Among("itivi", -1, 6, "", methodObject), new Among("icit?i", -1, 4, "", methodObject), new Among("abilit?i", -1, 1, "", methodObject), 
			new Among("ivit?i", -1, 3, "", methodObject), new Among("icit??i", -1, 4, "", methodObject), new Among("abilit??i", -1, 1, "", methodObject), new Among("ivit??i", -1, 3, "", methodObject), new Among("ical", -1, 4, "", methodObject), new Among("ator", -1, 5, "", methodObject), new Among("icator", 35, 4, "", methodObject), new Among("itor", -1, 6, "", methodObject), new Among("?tor", -1, 5, "", methodObject), new Among("iciv", -1, 4, "", methodObject), 
			new Among("ativ", -1, 5, "", methodObject), new Among("itiv", -1, 6, "", methodObject), new Among("ical?", -1, 4, "", methodObject), new Among("iciv?", -1, 4, "", methodObject), new Among("ativ?", -1, 5, "", methodObject), new Among("itiv?", -1, 6, "", methodObject)
		});
		a_3 = (new Among[] {
			new Among("ica", -1, 1, "", methodObject), new Among("abila", -1, 1, "", methodObject), new Among("ibila", -1, 1, "", methodObject), new Among("oasa", -1, 1, "", methodObject), new Among("ata", -1, 1, "", methodObject), new Among("ita", -1, 1, "", methodObject), new Among("anta", -1, 1, "", methodObject), new Among("ista", -1, 3, "", methodObject), new Among("uta", -1, 1, "", methodObject), new Among("iva", -1, 1, "", methodObject), 
			new Among("ic", -1, 1, "", methodObject), new Among("ice", -1, 1, "", methodObject), new Among("abile", -1, 1, "", methodObject), new Among("ibile", -1, 1, "", methodObject), new Among("isme", -1, 3, "", methodObject), new Among("iune", -1, 2, "", methodObject), new Among("oase", -1, 1, "", methodObject), new Among("ate", -1, 1, "", methodObject), new Among("itate", 17, 1, "", methodObject), new Among("ite", -1, 1, "", methodObject), 
			new Among("ante", -1, 1, "", methodObject), new Among("iste", -1, 3, "", methodObject), new Among("ute", -1, 1, "", methodObject), new Among("ive", -1, 1, "", methodObject), new Among("ici", -1, 1, "", methodObject), new Among("abili", -1, 1, "", methodObject), new Among("ibili", -1, 1, "", methodObject), new Among("iuni", -1, 2, "", methodObject), new Among("atori", -1, 1, "", methodObject), new Among("osi", -1, 1, "", methodObject), 
			new Among("ati", -1, 1, "", methodObject), new Among("itati", 30, 1, "", methodObject), new Among("iti", -1, 1, "", methodObject), new Among("anti", -1, 1, "", methodObject), new Among("isti", -1, 3, "", methodObject), new Among("uti", -1, 1, "", methodObject), new Among("i?ti", -1, 3, "", methodObject), new Among("ivi", -1, 1, "", methodObject), new Among("it?i", -1, 1, "", methodObject), new Among("o?i", -1, 1, "", methodObject), 
			new Among("it??i", -1, 1, "", methodObject), new Among("abil", -1, 1, "", methodObject), new Among("ibil", -1, 1, "", methodObject), new Among("ism", -1, 3, "", methodObject), new Among("ator", -1, 1, "", methodObject), new Among("os", -1, 1, "", methodObject), new Among("at", -1, 1, "", methodObject), new Among("it", -1, 1, "", methodObject), new Among("ant", -1, 1, "", methodObject), new Among("ist", -1, 3, "", methodObject), 
			new Among("ut", -1, 1, "", methodObject), new Among("iv", -1, 1, "", methodObject), new Among("ic?", -1, 1, "", methodObject), new Among("abil?", -1, 1, "", methodObject), new Among("ibil?", -1, 1, "", methodObject), new Among("oas?", -1, 1, "", methodObject), new Among("at?", -1, 1, "", methodObject), new Among("it?", -1, 1, "", methodObject), new Among("ant?", -1, 1, "", methodObject), new Among("ist?", -1, 3, "", methodObject), 
			new Among("ut?", -1, 1, "", methodObject), new Among("iv?", -1, 1, "", methodObject)
		});
		a_4 = (new Among[] {
			new Among("ea", -1, 1, "", methodObject), new Among("ia", -1, 1, "", methodObject), new Among("esc", -1, 1, "", methodObject), new Among("?sc", -1, 1, "", methodObject), new Among("ind", -1, 1, "", methodObject), new Among("\342nd", -1, 1, "", methodObject), new Among("are", -1, 1, "", methodObject), new Among("ere", -1, 1, "", methodObject), new Among("ire", -1, 1, "", methodObject), new Among("\342re", -1, 1, "", methodObject), 
			new Among("se", -1, 2, "", methodObject), new Among("ase", 10, 1, "", methodObject), new Among("sese", 10, 2, "", methodObject), new Among("ise", 10, 1, "", methodObject), new Among("use", 10, 1, "", methodObject), new Among("\342se", 10, 1, "", methodObject), new Among("e?te", -1, 1, "", methodObject), new Among("??te", -1, 1, "", methodObject), new Among("eze", -1, 1, "", methodObject), new Among("ai", -1, 1, "", methodObject), 
			new Among("eai", 19, 1, "", methodObject), new Among("iai", 19, 1, "", methodObject), new Among("sei", -1, 2, "", methodObject), new Among("e?ti", -1, 1, "", methodObject), new Among("??ti", -1, 1, "", methodObject), new Among("ui", -1, 1, "", methodObject), new Among("ezi", -1, 1, "", methodObject), new Among("\342i", -1, 1, "", methodObject), new Among("a?i", -1, 1, "", methodObject), new Among("se?i", -1, 2, "", methodObject), 
			new Among("ase?i", 29, 1, "", methodObject), new Among("sese?i", 29, 2, "", methodObject), new Among("ise?i", 29, 1, "", methodObject), new Among("use?i", 29, 1, "", methodObject), new Among("\342se?i", 29, 1, "", methodObject), new Among("i?i", -1, 1, "", methodObject), new Among("u?i", -1, 1, "", methodObject), new Among("\342?i", -1, 1, "", methodObject), new Among("a?i", -1, 2, "", methodObject), new Among("ea?i", 38, 1, "", methodObject), 
			new Among("ia?i", 38, 1, "", methodObject), new Among("e?i", -1, 2, "", methodObject), new Among("i?i", -1, 2, "", methodObject), new Among("\342?i", -1, 2, "", methodObject), new Among("ar??i", -1, 1, "", methodObject), new Among("ser??i", -1, 2, "", methodObject), new Among("aser??i", 45, 1, "", methodObject), new Among("seser??i", 45, 2, "", methodObject), new Among("iser??i", 45, 1, "", methodObject), new Among("user??i", 45, 1, "", methodObject), 
			new Among("\342ser??i", 45, 1, "", methodObject), new Among("ir??i", -1, 1, "", methodObject), new Among("ur??i", -1, 1, "", methodObject), new Among("\342r??i", -1, 1, "", methodObject), new Among("am", -1, 1, "", methodObject), new Among("eam", 54, 1, "", methodObject), new Among("iam", 54, 1, "", methodObject), new Among("em", -1, 2, "", methodObject), new Among("asem", 57, 1, "", methodObject), new Among("sesem", 57, 2, "", methodObject), 
			new Among("isem", 57, 1, "", methodObject), new Among("usem", 57, 1, "", methodObject), new Among("\342sem", 57, 1, "", methodObject), new Among("im", -1, 2, "", methodObject), new Among("\342m", -1, 2, "", methodObject), new Among("?m", -1, 2, "", methodObject), new Among("ar?m", 65, 1, "", methodObject), new Among("ser?m", 65, 2, "", methodObject), new Among("aser?m", 67, 1, "", methodObject), new Among("seser?m", 67, 2, "", methodObject), 
			new Among("iser?m", 67, 1, "", methodObject), new Among("user?m", 67, 1, "", methodObject), new Among("\342ser?m", 67, 1, "", methodObject), new Among("ir?m", 65, 1, "", methodObject), new Among("ur?m", 65, 1, "", methodObject), new Among("\342r?m", 65, 1, "", methodObject), new Among("au", -1, 1, "", methodObject), new Among("eau", 76, 1, "", methodObject), new Among("iau", 76, 1, "", methodObject), new Among("indu", -1, 1, "", methodObject), 
			new Among("\342ndu", -1, 1, "", methodObject), new Among("ez", -1, 1, "", methodObject), new Among("easc?", -1, 1, "", methodObject), new Among("ar?", -1, 1, "", methodObject), new Among("ser?", -1, 2, "", methodObject), new Among("aser?", 84, 1, "", methodObject), new Among("seser?", 84, 2, "", methodObject), new Among("iser?", 84, 1, "", methodObject), new Among("user?", 84, 1, "", methodObject), new Among("\342ser?", 84, 1, "", methodObject), 
			new Among("ir?", -1, 1, "", methodObject), new Among("ur?", -1, 1, "", methodObject), new Among("\342r?", -1, 1, "", methodObject), new Among("eaz?", -1, 1, "", methodObject)
		});
		a_5 = (new Among[] {
			new Among("a", -1, 1, "", methodObject), new Among("e", -1, 1, "", methodObject), new Among("ie", 1, 1, "", methodObject), new Among("i", -1, 1, "", methodObject), new Among("?", -1, 1, "", methodObject)
		});
	}
}
