// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FinnishStemmer.java

package org.tartarus.snowball.ext;

import org.tartarus.snowball.Among;
import org.tartarus.snowball.SnowballProgram;

public class FinnishStemmer extends SnowballProgram
{

	private static final long serialVersionUID = 1L;
	private static final FinnishStemmer methodObject;
	private static final Among a_0[];
	private static final Among a_1[];
	private static final Among a_2[];
	private static final Among a_3[];
	private static final Among a_4[];
	private static final Among a_5[];
	private static final Among a_6[];
	private static final Among a_7[];
	private static final Among a_8[];
	private static final Among a_9[];
	private static final char g_AEI[] = {
		'\021', '\001', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', 
		'\0', '\0', '\0', '\0', '\0', '\0', '\b'
	};
	private static final char g_V1[] = {
		'\021', 'A', '\020', '\001', '\0', '\0', '\0', '\0', '\0', '\0', 
		'\0', '\0', '\0', '\0', '\0', '\0', '\b', '\0', ' '
	};
	private static final char g_V2[] = {
		'\021', 'A', '\020', '\0', '\0', '\0', '\0', '\0', '\0', '\0', 
		'\0', '\0', '\0', '\0', '\0', '\0', '\b', '\0', ' '
	};
	private static final char g_particle_end[] = {
		'\021', 'a', '\030', '\001', '\0', '\0', '\0', '\0', '\0', '\0', 
		'\0', '\0', '\0', '\0', '\0', '\0', '\b', '\0', ' '
	};
	private boolean B_ending_removed;
	private StringBuilder S_x;
	private int I_p2;
	private int I_p1;

	public FinnishStemmer()
	{
		S_x = new StringBuilder();
	}

	private void copy_from(FinnishStemmer other)
	{
		B_ending_removed = other.B_ending_removed;
		S_x = other.S_x;
		I_p2 = other.I_p2;
		I_p1 = other.I_p1;
		super.copy_from(other);
	}

	private boolean r_mark_regions()
	{
		I_p1 = limit;
		I_p2 = limit;
		do
		{
			int v_1 = cursor;
			if (in_grouping(g_V1, 97, 246))
			{
				cursor = v_1;
				break;
			}
			cursor = v_1;
			if (cursor >= limit)
				return false;
			cursor++;
		} while (true);
		while (!out_grouping(g_V1, 97, 246)) 
		{
			if (cursor >= limit)
				return false;
			cursor++;
		}
		I_p1 = cursor;
		do
		{
			int v_3 = cursor;
			if (in_grouping(g_V1, 97, 246))
			{
				cursor = v_3;
				break;
			}
			cursor = v_3;
			if (cursor >= limit)
				return false;
			cursor++;
		} while (true);
		while (!out_grouping(g_V1, 97, 246)) 
		{
			if (cursor >= limit)
				return false;
			cursor++;
		}
		I_p2 = cursor;
		return true;
	}

	private boolean r_R2()
	{
		return I_p2 <= cursor;
	}

	private boolean r_particle_etc()
	{
		int v_1 = limit - cursor;
		if (cursor < I_p1)
			return false;
		cursor = I_p1;
		int v_2 = limit_backward;
		limit_backward = cursor;
		cursor = limit - v_1;
		ket = cursor;
		int among_var = find_among_b(a_0, 10);
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
			if (!in_grouping_b(g_particle_end, 97, 246))
				return false;
			break;

		case 2: // '\002'
			if (!r_R2())
				return false;
			break;
		}
		slice_del();
		return true;
	}

	private boolean r_possessive()
	{
		int v_1 = limit - cursor;
		if (cursor < I_p1)
			return false;
		cursor = I_p1;
		int v_2 = limit_backward;
		limit_backward = cursor;
		cursor = limit - v_1;
		ket = cursor;
		int among_var = find_among_b(a_4, 9);
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
			int v_3 = limit - cursor;
			if (eq_s_b(1, "k"))
				return false;
			cursor = limit - v_3;
			slice_del();
			break;

		case 2: // '\002'
			slice_del();
			ket = cursor;
			if (!eq_s_b(3, "kse"))
				return false;
			bra = cursor;
			slice_from("ksi");
			break;

		case 3: // '\003'
			slice_del();
			break;

		case 4: // '\004'
			if (find_among_b(a_1, 6) == 0)
				return false;
			slice_del();
			break;

		case 5: // '\005'
			if (find_among_b(a_2, 6) == 0)
				return false;
			slice_del();
			break;

		case 6: // '\006'
			if (find_among_b(a_3, 2) == 0)
				return false;
			slice_del();
			break;
		}
		return true;
	}

	private boolean r_LONG()
	{
		return find_among_b(a_5, 7) != 0;
	}

	private boolean r_VI()
	{
		if (!eq_s_b(1, "i"))
			return false;
		return in_grouping_b(g_V2, 97, 246);
	}

	private boolean r_case_ending()
	{
		int v_1 = limit - cursor;
		if (cursor < I_p1)
			return false;
		cursor = I_p1;
		int v_2 = limit_backward;
		limit_backward = cursor;
		cursor = limit - v_1;
		ket = cursor;
		int among_var = find_among_b(a_6, 30);
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
			if (!eq_s_b(1, "a"))
				return false;
			break;

		case 2: // '\002'
			if (!eq_s_b(1, "e"))
				return false;
			break;

		case 3: // '\003'
			if (!eq_s_b(1, "i"))
				return false;
			break;

		case 4: // '\004'
			if (!eq_s_b(1, "o"))
				return false;
			break;

		case 5: // '\005'
			if (!eq_s_b(1, "\344"))
				return false;
			break;

		case 6: // '\006'
			if (!eq_s_b(1, "\366"))
				return false;
			break;

		case 7: // '\007'
			int v_3 = limit - cursor;
			int v_4 = limit - cursor;
			int v_5 = limit - cursor;
			if (!r_LONG())
			{
				cursor = limit - v_5;
				if (!eq_s_b(2, "ie"))
				{
					cursor = limit - v_3;
					break;
				}
			}
			cursor = limit - v_4;
			if (cursor <= limit_backward)
			{
				cursor = limit - v_3;
			} else
			{
				cursor--;
				bra = cursor;
			}
			break;

		case 8: // '\b'
			if (!in_grouping_b(g_V1, 97, 246))
				return false;
			if (!out_grouping_b(g_V1, 97, 246))
				return false;
			break;

		case 9: // '\t'
			if (!eq_s_b(1, "e"))
				return false;
			break;
		}
		slice_del();
		B_ending_removed = true;
		return true;
	}

	private boolean r_other_endings()
	{
		int v_1 = limit - cursor;
		if (cursor < I_p2)
			return false;
		cursor = I_p2;
		int v_2 = limit_backward;
		limit_backward = cursor;
		cursor = limit - v_1;
		ket = cursor;
		int among_var = find_among_b(a_7, 14);
		if (among_var == 0)
		{
			limit_backward = v_2;
			return false;
		}
		bra = cursor;
		limit_backward = v_2;
		switch (among_var)
		{
		case 0: // '\0'
			return false;

		case 1: // '\001'
			int v_3 = limit - cursor;
			if (eq_s_b(2, "po"))
				return false;
			cursor = limit - v_3;
			break;
		}
		slice_del();
		return true;
	}

	private boolean r_i_plural()
	{
		int v_1 = limit - cursor;
		if (cursor < I_p1)
			return false;
		cursor = I_p1;
		int v_2 = limit_backward;
		limit_backward = cursor;
		cursor = limit - v_1;
		ket = cursor;
		if (find_among_b(a_8, 2) == 0)
		{
			limit_backward = v_2;
			return false;
		} else
		{
			bra = cursor;
			limit_backward = v_2;
			slice_del();
			return true;
		}
	}

	private boolean r_t_plural()
	{
		int v_1 = limit - cursor;
		if (cursor < I_p1)
			return false;
		cursor = I_p1;
		int v_2 = limit_backward;
		limit_backward = cursor;
		cursor = limit - v_1;
		ket = cursor;
		if (!eq_s_b(1, "t"))
		{
			limit_backward = v_2;
			return false;
		}
		bra = cursor;
		int v_3 = limit - cursor;
		if (!in_grouping_b(g_V1, 97, 246))
		{
			limit_backward = v_2;
			return false;
		}
		cursor = limit - v_3;
		slice_del();
		limit_backward = v_2;
		int v_4 = limit - cursor;
		if (cursor < I_p2)
			return false;
		cursor = I_p2;
		int v_5 = limit_backward;
		limit_backward = cursor;
		cursor = limit - v_4;
		ket = cursor;
		int among_var = find_among_b(a_9, 2);
		if (among_var == 0)
		{
			limit_backward = v_5;
			return false;
		}
		bra = cursor;
		limit_backward = v_5;
		switch (among_var)
		{
		case 0: // '\0'
			return false;

		case 1: // '\001'
			int v_6 = limit - cursor;
			if (eq_s_b(2, "po"))
				return false;
			cursor = limit - v_6;
			break;
		}
		slice_del();
		return true;
	}

	private boolean r_tidy()
	{
		int v_2;
		int v_6;
label0:
		{
			int v_1 = limit - cursor;
			if (cursor < I_p1)
				return false;
			cursor = I_p1;
			v_2 = limit_backward;
			limit_backward = cursor;
			cursor = limit - v_1;
			int v_3 = limit - cursor;
			int v_4 = limit - cursor;
			if (r_LONG())
			{
				cursor = limit - v_4;
				ket = cursor;
				if (cursor > limit_backward)
				{
					cursor--;
					bra = cursor;
					slice_del();
				}
			}
			cursor = limit - v_3;
			int v_5 = limit - cursor;
			ket = cursor;
			if (in_grouping_b(g_AEI, 97, 228))
			{
				bra = cursor;
				if (out_grouping_b(g_V1, 97, 246))
					slice_del();
			}
			cursor = limit - v_5;
			v_6 = limit - cursor;
			ket = cursor;
			if (!eq_s_b(1, "j"))
				break label0;
			bra = cursor;
			int v_7 = limit - cursor;
			if (!eq_s_b(1, "o"))
			{
				cursor = limit - v_7;
				if (!eq_s_b(1, "u"))
					break label0;
			}
			slice_del();
		}
		cursor = limit - v_6;
		int v_8 = limit - cursor;
		ket = cursor;
		if (eq_s_b(1, "o"))
		{
			bra = cursor;
			if (eq_s_b(1, "j"))
				slice_del();
		}
		cursor = limit - v_8;
		limit_backward = v_2;
		do
		{
			int v_9 = limit - cursor;
			if (out_grouping_b(g_V1, 97, 246))
			{
				cursor = limit - v_9;
				break;
			}
			cursor = limit - v_9;
			if (cursor <= limit_backward)
				return false;
			cursor--;
		} while (true);
		ket = cursor;
		if (cursor <= limit_backward)
			return false;
		cursor--;
		bra = cursor;
		S_x = slice_to(S_x);
		if (!eq_v_b(S_x))
		{
			return false;
		} else
		{
			slice_del();
			return true;
		}
	}

	public boolean stem()
	{
		int v_1 = cursor;
		if (r_mark_regions());
		cursor = v_1;
		B_ending_removed = false;
		limit_backward = cursor;
		cursor = limit;
		int v_2 = limit - cursor;
		if (r_particle_etc());
		cursor = limit - v_2;
		int v_3 = limit - cursor;
		if (r_possessive());
		cursor = limit - v_3;
		int v_4 = limit - cursor;
		if (r_case_ending());
		cursor = limit - v_4;
		int v_5 = limit - cursor;
		if (r_other_endings());
		cursor = limit - v_5;
		int v_6 = limit - cursor;
		if (B_ending_removed)
		{
			int v_7 = limit - cursor;
			if (r_i_plural());
			cursor = limit - v_7;
		} else
		{
			cursor = limit - v_6;
			int v_8 = limit - cursor;
			if (r_t_plural());
			cursor = limit - v_8;
		}
		int v_9 = limit - cursor;
		if (r_tidy());
		cursor = limit - v_9;
		cursor = limit_backward;
		return true;
	}

	public boolean equals(Object o)
	{
		return o instanceof FinnishStemmer;
	}

	public int hashCode()
	{
		return org/tartarus/snowball/ext/FinnishStemmer.getName().hashCode();
	}

	static 
	{
		methodObject = new FinnishStemmer();
		a_0 = (new Among[] {
			new Among("pa", -1, 1, "", methodObject), new Among("sti", -1, 2, "", methodObject), new Among("kaan", -1, 1, "", methodObject), new Among("han", -1, 1, "", methodObject), new Among("kin", -1, 1, "", methodObject), new Among("h\344n", -1, 1, "", methodObject), new Among("k\344\344n", -1, 1, "", methodObject), new Among("ko", -1, 1, "", methodObject), new Among("p\344", -1, 1, "", methodObject), new Among("k\366", -1, 1, "", methodObject)
		});
		a_1 = (new Among[] {
			new Among("lla", -1, -1, "", methodObject), new Among("na", -1, -1, "", methodObject), new Among("ssa", -1, -1, "", methodObject), new Among("ta", -1, -1, "", methodObject), new Among("lta", 3, -1, "", methodObject), new Among("sta", 3, -1, "", methodObject)
		});
		a_2 = (new Among[] {
			new Among("ll\344", -1, -1, "", methodObject), new Among("n\344", -1, -1, "", methodObject), new Among("ss\344", -1, -1, "", methodObject), new Among("t\344", -1, -1, "", methodObject), new Among("lt\344", 3, -1, "", methodObject), new Among("st\344", 3, -1, "", methodObject)
		});
		a_3 = (new Among[] {
			new Among("lle", -1, -1, "", methodObject), new Among("ine", -1, -1, "", methodObject)
		});
		a_4 = (new Among[] {
			new Among("nsa", -1, 3, "", methodObject), new Among("mme", -1, 3, "", methodObject), new Among("nne", -1, 3, "", methodObject), new Among("ni", -1, 2, "", methodObject), new Among("si", -1, 1, "", methodObject), new Among("an", -1, 4, "", methodObject), new Among("en", -1, 6, "", methodObject), new Among("\344n", -1, 5, "", methodObject), new Among("ns\344", -1, 3, "", methodObject)
		});
		a_5 = (new Among[] {
			new Among("aa", -1, -1, "", methodObject), new Among("ee", -1, -1, "", methodObject), new Among("ii", -1, -1, "", methodObject), new Among("oo", -1, -1, "", methodObject), new Among("uu", -1, -1, "", methodObject), new Among("\344\344", -1, -1, "", methodObject), new Among("\366\366", -1, -1, "", methodObject)
		});
		a_6 = (new Among[] {
			new Among("a", -1, 8, "", methodObject), new Among("lla", 0, -1, "", methodObject), new Among("na", 0, -1, "", methodObject), new Among("ssa", 0, -1, "", methodObject), new Among("ta", 0, -1, "", methodObject), new Among("lta", 4, -1, "", methodObject), new Among("sta", 4, -1, "", methodObject), new Among("tta", 4, 9, "", methodObject), new Among("lle", -1, -1, "", methodObject), new Among("ine", -1, -1, "", methodObject), 
			new Among("ksi", -1, -1, "", methodObject), new Among("n", -1, 7, "", methodObject), new Among("han", 11, 1, "", methodObject), new Among("den", 11, -1, "r_VI", methodObject), new Among("seen", 11, -1, "r_LONG", methodObject), new Among("hen", 11, 2, "", methodObject), new Among("tten", 11, -1, "r_VI", methodObject), new Among("hin", 11, 3, "", methodObject), new Among("siin", 11, -1, "r_VI", methodObject), new Among("hon", 11, 4, "", methodObject), 
			new Among("h\344n", 11, 5, "", methodObject), new Among("h\366n", 11, 6, "", methodObject), new Among("\344", -1, 8, "", methodObject), new Among("ll\344", 22, -1, "", methodObject), new Among("n\344", 22, -1, "", methodObject), new Among("ss\344", 22, -1, "", methodObject), new Among("t\344", 22, -1, "", methodObject), new Among("lt\344", 26, -1, "", methodObject), new Among("st\344", 26, -1, "", methodObject), new Among("tt\344", 26, 9, "", methodObject)
		});
		a_7 = (new Among[] {
			new Among("eja", -1, -1, "", methodObject), new Among("mma", -1, 1, "", methodObject), new Among("imma", 1, -1, "", methodObject), new Among("mpa", -1, 1, "", methodObject), new Among("impa", 3, -1, "", methodObject), new Among("mmi", -1, 1, "", methodObject), new Among("immi", 5, -1, "", methodObject), new Among("mpi", -1, 1, "", methodObject), new Among("impi", 7, -1, "", methodObject), new Among("ej\344", -1, -1, "", methodObject), 
			new Among("mm\344", -1, 1, "", methodObject), new Among("imm\344", 10, -1, "", methodObject), new Among("mp\344", -1, 1, "", methodObject), new Among("imp\344", 12, -1, "", methodObject)
		});
		a_8 = (new Among[] {
			new Among("i", -1, -1, "", methodObject), new Among("j", -1, -1, "", methodObject)
		});
		a_9 = (new Among[] {
			new Among("mma", -1, 1, "", methodObject), new Among("imma", 0, -1, "", methodObject)
		});
	}
}
