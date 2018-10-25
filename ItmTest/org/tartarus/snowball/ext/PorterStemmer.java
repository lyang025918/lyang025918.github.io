// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PorterStemmer.java

package org.tartarus.snowball.ext;

import org.tartarus.snowball.Among;
import org.tartarus.snowball.SnowballProgram;

public class PorterStemmer extends SnowballProgram
{

	private static final long serialVersionUID = 1L;
	private static final PorterStemmer methodObject;
	private static final Among a_0[];
	private static final Among a_1[];
	private static final Among a_2[];
	private static final Among a_3[];
	private static final Among a_4[];
	private static final Among a_5[];
	private static final char g_v[] = {
		'\021', 'A', '\020', '\001'
	};
	private static final char g_v_WXY[] = {
		'\001', '\021', 'A', '\320', '\001'
	};
	private boolean B_Y_found;
	private int I_p2;
	private int I_p1;

	public PorterStemmer()
	{
	}

	private void copy_from(PorterStemmer other)
	{
		B_Y_found = other.B_Y_found;
		I_p2 = other.I_p2;
		I_p1 = other.I_p1;
		super.copy_from(other);
	}

	private boolean r_shortv()
	{
		if (!out_grouping_b(g_v_WXY, 89, 121))
			return false;
		if (!in_grouping_b(g_v, 97, 121))
			return false;
		return out_grouping_b(g_v, 97, 121);
	}

	private boolean r_R1()
	{
		return I_p1 <= cursor;
	}

	private boolean r_R2()
	{
		return I_p2 <= cursor;
	}

	private boolean r_Step_1a()
	{
		ket = cursor;
		int among_var = find_among_b(a_0, 4);
		if (among_var == 0)
			return false;
		bra = cursor;
		switch (among_var)
		{
		case 0: // '\0'
			return false;

		case 1: // '\001'
			slice_from("ss");
			break;

		case 2: // '\002'
			slice_from("i");
			break;

		case 3: // '\003'
			slice_del();
			break;
		}
		return true;
	}

	private boolean r_Step_1b()
	{
		ket = cursor;
		int among_var = find_among_b(a_2, 3);
		if (among_var == 0)
			return false;
		bra = cursor;
label0:
		switch (among_var)
		{
		default:
			break;

		case 0: // '\0'
			return false;

		case 1: // '\001'
			if (!r_R1())
				return false;
			slice_from("ee");
			break;

		case 2: // '\002'
			int v_1 = limit - cursor;
			while (!in_grouping_b(g_v, 97, 121)) 
			{
				if (cursor <= limit_backward)
					return false;
				cursor--;
			}
			cursor = limit - v_1;
			slice_del();
			int v_3 = limit - cursor;
			among_var = find_among_b(a_1, 13);
			if (among_var == 0)
				return false;
			cursor = limit - v_3;
			int c;
			switch (among_var)
			{
			default:
				break label0;

			case 0: // '\0'
				return false;

			case 1: // '\001'
				c = cursor;
				insert(cursor, cursor, "e");
				cursor = c;
				break label0;

			case 2: // '\002'
				ket = cursor;
				if (cursor <= limit_backward)
					return false;
				cursor--;
				bra = cursor;
				slice_del();
				break label0;

			case 3: // '\003'
				break;
			}
			if (cursor != I_p1)
				return false;
			int v_4 = limit - cursor;
			if (!r_shortv())
				return false;
			cursor = limit - v_4;
			c = cursor;
			insert(cursor, cursor, "e");
			cursor = c;
			break;
		}
		return true;
	}

	private boolean r_Step_1c()
	{
		ket = cursor;
		int v_1 = limit - cursor;
		if (!eq_s_b(1, "y"))
		{
			cursor = limit - v_1;
			if (!eq_s_b(1, "Y"))
				return false;
		}
		bra = cursor;
		while (!in_grouping_b(g_v, 97, 121)) 
		{
			if (cursor <= limit_backward)
				return false;
			cursor--;
		}
		slice_from("i");
		return true;
	}

	private boolean r_Step_2()
	{
		ket = cursor;
		int among_var = find_among_b(a_3, 20);
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
			slice_from("tion");
			break;

		case 2: // '\002'
			slice_from("ence");
			break;

		case 3: // '\003'
			slice_from("ance");
			break;

		case 4: // '\004'
			slice_from("able");
			break;

		case 5: // '\005'
			slice_from("ent");
			break;

		case 6: // '\006'
			slice_from("e");
			break;

		case 7: // '\007'
			slice_from("ize");
			break;

		case 8: // '\b'
			slice_from("ate");
			break;

		case 9: // '\t'
			slice_from("al");
			break;

		case 10: // '\n'
			slice_from("al");
			break;

		case 11: // '\013'
			slice_from("ful");
			break;

		case 12: // '\f'
			slice_from("ous");
			break;

		case 13: // '\r'
			slice_from("ive");
			break;

		case 14: // '\016'
			slice_from("ble");
			break;
		}
		return true;
	}

	private boolean r_Step_3()
	{
		ket = cursor;
		int among_var = find_among_b(a_4, 7);
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
			slice_from("al");
			break;

		case 2: // '\002'
			slice_from("ic");
			break;

		case 3: // '\003'
			slice_del();
			break;
		}
		return true;
	}

	private boolean r_Step_4()
	{
		ket = cursor;
		int among_var = find_among_b(a_5, 19);
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
			int v_1 = limit - cursor;
			if (!eq_s_b(1, "s"))
			{
				cursor = limit - v_1;
				if (!eq_s_b(1, "t"))
					return false;
			}
			slice_del();
			break;
		}
		return true;
	}

	private boolean r_Step_5a()
	{
		ket = cursor;
		if (!eq_s_b(1, "e"))
			return false;
		bra = cursor;
		int v_1 = limit - cursor;
		if (!r_R2())
		{
			cursor = limit - v_1;
			if (!r_R1())
				return false;
			int v_2 = limit - cursor;
			if (r_shortv())
				return false;
			cursor = limit - v_2;
		}
		slice_del();
		return true;
	}

	private boolean r_Step_5b()
	{
		ket = cursor;
		if (!eq_s_b(1, "l"))
			return false;
		bra = cursor;
		if (!r_R2())
			return false;
		if (!eq_s_b(1, "l"))
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
		int v_5;
label0:
		{
			B_Y_found = false;
			int v_1 = cursor;
			bra = cursor;
			if (eq_s(1, "y"))
			{
				ket = cursor;
				slice_from("Y");
				B_Y_found = true;
			}
			cursor = v_1;
			int v_2 = cursor;
			int v_3;
label1:
			do
			{
				v_3 = cursor;
				do
				{
					int v_4 = cursor;
					if (in_grouping(g_v, 97, 121))
					{
						bra = cursor;
						if (eq_s(1, "y"))
						{
							ket = cursor;
							cursor = v_4;
							break;
						}
					}
					cursor = v_4;
					if (cursor >= limit)
						break label1;
					cursor++;
				} while (true);
				slice_from("Y");
				B_Y_found = true;
			} while (true);
			cursor = v_3;
			cursor = v_2;
			I_p1 = limit;
			I_p2 = limit;
			v_5 = cursor;
			while (!in_grouping(g_v, 97, 121)) 
			{
				if (cursor >= limit)
					break label0;
				cursor++;
			}
			while (!out_grouping(g_v, 97, 121)) 
			{
				if (cursor >= limit)
					break label0;
				cursor++;
			}
			I_p1 = cursor;
			while (!in_grouping(g_v, 97, 121)) 
			{
				if (cursor >= limit)
					break label0;
				cursor++;
			}
			while (!out_grouping(g_v, 97, 121)) 
			{
				if (cursor >= limit)
					break label0;
				cursor++;
			}
			I_p2 = cursor;
		}
		cursor = v_5;
		limit_backward = cursor;
		cursor = limit;
		int v_10 = limit - cursor;
		if (r_Step_1a());
		cursor = limit - v_10;
		int v_11 = limit - cursor;
		if (r_Step_1b());
		cursor = limit - v_11;
		int v_12 = limit - cursor;
		if (r_Step_1c());
		cursor = limit - v_12;
		int v_13 = limit - cursor;
		if (r_Step_2());
		cursor = limit - v_13;
		int v_14 = limit - cursor;
		if (r_Step_3());
		cursor = limit - v_14;
		int v_15 = limit - cursor;
		if (r_Step_4());
		cursor = limit - v_15;
		int v_16 = limit - cursor;
		if (r_Step_5a());
		cursor = limit - v_16;
		int v_17 = limit - cursor;
		if (r_Step_5b());
		cursor = limit - v_17;
		cursor = limit_backward;
		int v_18 = cursor;
		if (B_Y_found)
		{
			int v_19;
label2:
			do
			{
				v_19 = cursor;
				do
				{
					int v_20 = cursor;
					bra = cursor;
					if (eq_s(1, "Y"))
					{
						ket = cursor;
						cursor = v_20;
						break;
					}
					cursor = v_20;
					if (cursor >= limit)
						break label2;
					cursor++;
				} while (true);
				slice_from("y");
			} while (true);
			cursor = v_19;
		}
		cursor = v_18;
		return true;
	}

	public boolean equals(Object o)
	{
		return o instanceof PorterStemmer;
	}

	public int hashCode()
	{
		return org/tartarus/snowball/ext/PorterStemmer.getName().hashCode();
	}

	static 
	{
		methodObject = new PorterStemmer();
		a_0 = (new Among[] {
			new Among("s", -1, 3, "", methodObject), new Among("ies", 0, 2, "", methodObject), new Among("sses", 0, 1, "", methodObject), new Among("ss", 0, -1, "", methodObject)
		});
		a_1 = (new Among[] {
			new Among("", -1, 3, "", methodObject), new Among("bb", 0, 2, "", methodObject), new Among("dd", 0, 2, "", methodObject), new Among("ff", 0, 2, "", methodObject), new Among("gg", 0, 2, "", methodObject), new Among("bl", 0, 1, "", methodObject), new Among("mm", 0, 2, "", methodObject), new Among("nn", 0, 2, "", methodObject), new Among("pp", 0, 2, "", methodObject), new Among("rr", 0, 2, "", methodObject), 
			new Among("at", 0, 1, "", methodObject), new Among("tt", 0, 2, "", methodObject), new Among("iz", 0, 1, "", methodObject)
		});
		a_2 = (new Among[] {
			new Among("ed", -1, 2, "", methodObject), new Among("eed", 0, 1, "", methodObject), new Among("ing", -1, 2, "", methodObject)
		});
		a_3 = (new Among[] {
			new Among("anci", -1, 3, "", methodObject), new Among("enci", -1, 2, "", methodObject), new Among("abli", -1, 4, "", methodObject), new Among("eli", -1, 6, "", methodObject), new Among("alli", -1, 9, "", methodObject), new Among("ousli", -1, 12, "", methodObject), new Among("entli", -1, 5, "", methodObject), new Among("aliti", -1, 10, "", methodObject), new Among("biliti", -1, 14, "", methodObject), new Among("iviti", -1, 13, "", methodObject), 
			new Among("tional", -1, 1, "", methodObject), new Among("ational", 10, 8, "", methodObject), new Among("alism", -1, 10, "", methodObject), new Among("ation", -1, 8, "", methodObject), new Among("ization", 13, 7, "", methodObject), new Among("izer", -1, 7, "", methodObject), new Among("ator", -1, 8, "", methodObject), new Among("iveness", -1, 13, "", methodObject), new Among("fulness", -1, 11, "", methodObject), new Among("ousness", -1, 12, "", methodObject)
		});
		a_4 = (new Among[] {
			new Among("icate", -1, 2, "", methodObject), new Among("ative", -1, 3, "", methodObject), new Among("alize", -1, 1, "", methodObject), new Among("iciti", -1, 2, "", methodObject), new Among("ical", -1, 2, "", methodObject), new Among("ful", -1, 3, "", methodObject), new Among("ness", -1, 3, "", methodObject)
		});
		a_5 = (new Among[] {
			new Among("ic", -1, 1, "", methodObject), new Among("ance", -1, 1, "", methodObject), new Among("ence", -1, 1, "", methodObject), new Among("able", -1, 1, "", methodObject), new Among("ible", -1, 1, "", methodObject), new Among("ate", -1, 1, "", methodObject), new Among("ive", -1, 1, "", methodObject), new Among("ize", -1, 1, "", methodObject), new Among("iti", -1, 1, "", methodObject), new Among("al", -1, 1, "", methodObject), 
			new Among("ism", -1, 1, "", methodObject), new Among("ion", -1, 2, "", methodObject), new Among("er", -1, 1, "", methodObject), new Among("ous", -1, 1, "", methodObject), new Among("ant", -1, 1, "", methodObject), new Among("ent", -1, 1, "", methodObject), new Among("ment", 15, 1, "", methodObject), new Among("ement", 16, 1, "", methodObject), new Among("ou", -1, 1, "", methodObject)
		});
	}
}
