// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   HungarianStemmer.java

package org.tartarus.snowball.ext;

import org.tartarus.snowball.Among;
import org.tartarus.snowball.SnowballProgram;

public class HungarianStemmer extends SnowballProgram
{

	private static final long serialVersionUID = 1L;
	private static final HungarianStemmer methodObject;
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
	private static final Among a_10[];
	private static final Among a_11[];
	private static final char g_v[] = {
		'\021', 'A', '\020', '\0', '\0', '\0', '\0', '\0', '\0', '\0', 
		'\0', '\0', '\0', '\0', '\0', '\0', '\001', '\021', '4', '\016'
	};
	private int I_p1;

	public HungarianStemmer()
	{
	}

	private void copy_from(HungarianStemmer other)
	{
		I_p1 = other.I_p1;
		super.copy_from(other);
	}

	private boolean r_mark_regions()
	{
label0:
		{
			int v_1;
label1:
			{
				I_p1 = limit;
				v_1 = cursor;
				if (!in_grouping(g_v, 97, 252))
					break label1;
				do
				{
					int v_2 = cursor;
					if (out_grouping(g_v, 97, 252))
					{
						cursor = v_2;
						break;
					}
					cursor = v_2;
					if (cursor >= limit)
						break label1;
					cursor++;
				} while (true);
				int v_3 = cursor;
				if (find_among(a_0, 8) == 0)
				{
					cursor = v_3;
					if (cursor >= limit)
						break label1;
					cursor++;
				}
				I_p1 = cursor;
				break label0;
			}
			cursor = v_1;
			if (!out_grouping(g_v, 97, 252))
				return false;
			while (!in_grouping(g_v, 97, 252)) 
			{
				if (cursor >= limit)
					return false;
				cursor++;
			}
			I_p1 = cursor;
		}
		return true;
	}

	private boolean r_R1()
	{
		return I_p1 <= cursor;
	}

	private boolean r_v_ending()
	{
		ket = cursor;
		int among_var = find_among_b(a_1, 2);
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
			slice_from("a");
			break;

		case 2: // '\002'
			slice_from("e");
			break;
		}
		return true;
	}

	private boolean r_double()
	{
		int v_1 = limit - cursor;
		if (find_among_b(a_2, 23) == 0)
		{
			return false;
		} else
		{
			cursor = limit - v_1;
			return true;
		}
	}

	private boolean r_undouble()
	{
		if (cursor <= limit_backward)
			return false;
		cursor--;
		ket = cursor;
		int c = cursor - 1;
		if (limit_backward > c || c > limit)
		{
			return false;
		} else
		{
			cursor = c;
			bra = cursor;
			slice_del();
			return true;
		}
	}

	private boolean r_instrum()
	{
		ket = cursor;
		int among_var = find_among_b(a_3, 2);
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
			if (!r_double())
				return false;
			break;

		case 2: // '\002'
			if (!r_double())
				return false;
			break;
		}
		slice_del();
		return r_undouble();
	}

	private boolean r_case()
	{
		ket = cursor;
		if (find_among_b(a_4, 44) == 0)
			return false;
		bra = cursor;
		if (!r_R1())
			return false;
		slice_del();
		return r_v_ending();
	}

	private boolean r_case_special()
	{
		ket = cursor;
		int among_var = find_among_b(a_5, 3);
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
			slice_from("e");
			break;

		case 2: // '\002'
			slice_from("a");
			break;

		case 3: // '\003'
			slice_from("a");
			break;
		}
		return true;
	}

	private boolean r_case_other()
	{
		ket = cursor;
		int among_var = find_among_b(a_6, 6);
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
			slice_del();
			break;

		case 2: // '\002'
			slice_del();
			break;

		case 3: // '\003'
			slice_from("a");
			break;

		case 4: // '\004'
			slice_from("e");
			break;
		}
		return true;
	}

	private boolean r_factive()
	{
		ket = cursor;
		int among_var = find_among_b(a_7, 2);
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
			if (!r_double())
				return false;
			break;

		case 2: // '\002'
			if (!r_double())
				return false;
			break;
		}
		slice_del();
		return r_undouble();
	}

	private boolean r_plural()
	{
		ket = cursor;
		int among_var = find_among_b(a_8, 7);
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
			slice_from("a");
			break;

		case 2: // '\002'
			slice_from("e");
			break;

		case 3: // '\003'
			slice_del();
			break;

		case 4: // '\004'
			slice_del();
			break;

		case 5: // '\005'
			slice_del();
			break;

		case 6: // '\006'
			slice_del();
			break;

		case 7: // '\007'
			slice_del();
			break;
		}
		return true;
	}

	private boolean r_owned()
	{
		ket = cursor;
		int among_var = find_among_b(a_9, 12);
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
			slice_del();
			break;

		case 2: // '\002'
			slice_from("e");
			break;

		case 3: // '\003'
			slice_from("a");
			break;

		case 4: // '\004'
			slice_del();
			break;

		case 5: // '\005'
			slice_from("e");
			break;

		case 6: // '\006'
			slice_from("a");
			break;

		case 7: // '\007'
			slice_del();
			break;

		case 8: // '\b'
			slice_from("e");
			break;

		case 9: // '\t'
			slice_del();
			break;
		}
		return true;
	}

	private boolean r_sing_owner()
	{
		ket = cursor;
		int among_var = find_among_b(a_10, 31);
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
			slice_del();
			break;

		case 2: // '\002'
			slice_from("a");
			break;

		case 3: // '\003'
			slice_from("e");
			break;

		case 4: // '\004'
			slice_del();
			break;

		case 5: // '\005'
			slice_from("a");
			break;

		case 6: // '\006'
			slice_from("e");
			break;

		case 7: // '\007'
			slice_del();
			break;

		case 8: // '\b'
			slice_del();
			break;

		case 9: // '\t'
			slice_del();
			break;

		case 10: // '\n'
			slice_from("a");
			break;

		case 11: // '\013'
			slice_from("e");
			break;

		case 12: // '\f'
			slice_del();
			break;

		case 13: // '\r'
			slice_del();
			break;

		case 14: // '\016'
			slice_from("a");
			break;

		case 15: // '\017'
			slice_from("e");
			break;

		case 16: // '\020'
			slice_del();
			break;

		case 17: // '\021'
			slice_del();
			break;

		case 18: // '\022'
			slice_del();
			break;

		case 19: // '\023'
			slice_from("a");
			break;

		case 20: // '\024'
			slice_from("e");
			break;
		}
		return true;
	}

	private boolean r_plur_owner()
	{
		ket = cursor;
		int among_var = find_among_b(a_11, 42);
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
			slice_del();
			break;

		case 2: // '\002'
			slice_from("a");
			break;

		case 3: // '\003'
			slice_from("e");
			break;

		case 4: // '\004'
			slice_del();
			break;

		case 5: // '\005'
			slice_del();
			break;

		case 6: // '\006'
			slice_del();
			break;

		case 7: // '\007'
			slice_from("a");
			break;

		case 8: // '\b'
			slice_from("e");
			break;

		case 9: // '\t'
			slice_del();
			break;

		case 10: // '\n'
			slice_del();
			break;

		case 11: // '\013'
			slice_del();
			break;

		case 12: // '\f'
			slice_from("a");
			break;

		case 13: // '\r'
			slice_from("e");
			break;

		case 14: // '\016'
			slice_del();
			break;

		case 15: // '\017'
			slice_del();
			break;

		case 16: // '\020'
			slice_del();
			break;

		case 17: // '\021'
			slice_del();
			break;

		case 18: // '\022'
			slice_from("a");
			break;

		case 19: // '\023'
			slice_from("e");
			break;

		case 20: // '\024'
			slice_del();
			break;

		case 21: // '\025'
			slice_del();
			break;

		case 22: // '\026'
			slice_from("a");
			break;

		case 23: // '\027'
			slice_from("e");
			break;

		case 24: // '\030'
			slice_del();
			break;

		case 25: // '\031'
			slice_del();
			break;

		case 26: // '\032'
			slice_del();
			break;

		case 27: // '\033'
			slice_from("a");
			break;

		case 28: // '\034'
			slice_from("e");
			break;

		case 29: // '\035'
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
		if (r_instrum());
		cursor = limit - v_2;
		int v_3 = limit - cursor;
		if (r_case());
		cursor = limit - v_3;
		int v_4 = limit - cursor;
		if (r_case_special());
		cursor = limit - v_4;
		int v_5 = limit - cursor;
		if (r_case_other());
		cursor = limit - v_5;
		int v_6 = limit - cursor;
		if (r_factive());
		cursor = limit - v_6;
		int v_7 = limit - cursor;
		if (r_owned());
		cursor = limit - v_7;
		int v_8 = limit - cursor;
		if (r_sing_owner());
		cursor = limit - v_8;
		int v_9 = limit - cursor;
		if (r_plur_owner());
		cursor = limit - v_9;
		int v_10 = limit - cursor;
		if (r_plural());
		cursor = limit - v_10;
		cursor = limit_backward;
		return true;
	}

	public boolean equals(Object o)
	{
		return o instanceof HungarianStemmer;
	}

	public int hashCode()
	{
		return org/tartarus/snowball/ext/HungarianStemmer.getName().hashCode();
	}

	static 
	{
		methodObject = new HungarianStemmer();
		a_0 = (new Among[] {
			new Among("cs", -1, -1, "", methodObject), new Among("dzs", -1, -1, "", methodObject), new Among("gy", -1, -1, "", methodObject), new Among("ly", -1, -1, "", methodObject), new Among("ny", -1, -1, "", methodObject), new Among("sz", -1, -1, "", methodObject), new Among("ty", -1, -1, "", methodObject), new Among("zs", -1, -1, "", methodObject)
		});
		a_1 = (new Among[] {
			new Among("\341", -1, 1, "", methodObject), new Among("\351", -1, 2, "", methodObject)
		});
		a_2 = (new Among[] {
			new Among("bb", -1, -1, "", methodObject), new Among("cc", -1, -1, "", methodObject), new Among("dd", -1, -1, "", methodObject), new Among("ff", -1, -1, "", methodObject), new Among("gg", -1, -1, "", methodObject), new Among("jj", -1, -1, "", methodObject), new Among("kk", -1, -1, "", methodObject), new Among("ll", -1, -1, "", methodObject), new Among("mm", -1, -1, "", methodObject), new Among("nn", -1, -1, "", methodObject), 
			new Among("pp", -1, -1, "", methodObject), new Among("rr", -1, -1, "", methodObject), new Among("ccs", -1, -1, "", methodObject), new Among("ss", -1, -1, "", methodObject), new Among("zzs", -1, -1, "", methodObject), new Among("tt", -1, -1, "", methodObject), new Among("vv", -1, -1, "", methodObject), new Among("ggy", -1, -1, "", methodObject), new Among("lly", -1, -1, "", methodObject), new Among("nny", -1, -1, "", methodObject), 
			new Among("tty", -1, -1, "", methodObject), new Among("ssz", -1, -1, "", methodObject), new Among("zz", -1, -1, "", methodObject)
		});
		a_3 = (new Among[] {
			new Among("al", -1, 1, "", methodObject), new Among("el", -1, 2, "", methodObject)
		});
		a_4 = (new Among[] {
			new Among("ba", -1, -1, "", methodObject), new Among("ra", -1, -1, "", methodObject), new Among("be", -1, -1, "", methodObject), new Among("re", -1, -1, "", methodObject), new Among("ig", -1, -1, "", methodObject), new Among("nak", -1, -1, "", methodObject), new Among("nek", -1, -1, "", methodObject), new Among("val", -1, -1, "", methodObject), new Among("vel", -1, -1, "", methodObject), new Among("ul", -1, -1, "", methodObject), 
			new Among("n\341l", -1, -1, "", methodObject), new Among("n\351l", -1, -1, "", methodObject), new Among("b\363l", -1, -1, "", methodObject), new Among("r\363l", -1, -1, "", methodObject), new Among("t\363l", -1, -1, "", methodObject), new Among("b\365l", -1, -1, "", methodObject), new Among("r\365l", -1, -1, "", methodObject), new Among("t\365l", -1, -1, "", methodObject), new Among("\374l", -1, -1, "", methodObject), new Among("n", -1, -1, "", methodObject), 
			new Among("an", 19, -1, "", methodObject), new Among("ban", 20, -1, "", methodObject), new Among("en", 19, -1, "", methodObject), new Among("ben", 22, -1, "", methodObject), new Among("k\351ppen", 22, -1, "", methodObject), new Among("on", 19, -1, "", methodObject), new Among("\366n", 19, -1, "", methodObject), new Among("k\351pp", -1, -1, "", methodObject), new Among("kor", -1, -1, "", methodObject), new Among("t", -1, -1, "", methodObject), 
			new Among("at", 29, -1, "", methodObject), new Among("et", 29, -1, "", methodObject), new Among("k\351nt", 29, -1, "", methodObject), new Among("ank\351nt", 32, -1, "", methodObject), new Among("enk\351nt", 32, -1, "", methodObject), new Among("onk\351nt", 32, -1, "", methodObject), new Among("ot", 29, -1, "", methodObject), new Among("\351rt", 29, -1, "", methodObject), new Among("\366t", 29, -1, "", methodObject), new Among("hez", -1, -1, "", methodObject), 
			new Among("hoz", -1, -1, "", methodObject), new Among("h\366z", -1, -1, "", methodObject), new Among("v\341", -1, -1, "", methodObject), new Among("v\351", -1, -1, "", methodObject)
		});
		a_5 = (new Among[] {
			new Among("\341n", -1, 2, "", methodObject), new Among("\351n", -1, 1, "", methodObject), new Among("\341nk\351nt", -1, 3, "", methodObject)
		});
		a_6 = (new Among[] {
			new Among("stul", -1, 2, "", methodObject), new Among("astul", 0, 1, "", methodObject), new Among("\341stul", 0, 3, "", methodObject), new Among("st\374l", -1, 2, "", methodObject), new Among("est\374l", 3, 1, "", methodObject), new Among("\351st\374l", 3, 4, "", methodObject)
		});
		a_7 = (new Among[] {
			new Among("\341", -1, 1, "", methodObject), new Among("\351", -1, 2, "", methodObject)
		});
		a_8 = (new Among[] {
			new Among("k", -1, 7, "", methodObject), new Among("ak", 0, 4, "", methodObject), new Among("ek", 0, 6, "", methodObject), new Among("ok", 0, 5, "", methodObject), new Among("\341k", 0, 1, "", methodObject), new Among("\351k", 0, 2, "", methodObject), new Among("\366k", 0, 3, "", methodObject)
		});
		a_9 = (new Among[] {
			new Among("\351i", -1, 7, "", methodObject), new Among("\341\351i", 0, 6, "", methodObject), new Among("\351\351i", 0, 5, "", methodObject), new Among("\351", -1, 9, "", methodObject), new Among("k\351", 3, 4, "", methodObject), new Among("ak\351", 4, 1, "", methodObject), new Among("ek\351", 4, 1, "", methodObject), new Among("ok\351", 4, 1, "", methodObject), new Among("\341k\351", 4, 3, "", methodObject), new Among("\351k\351", 4, 2, "", methodObject), 
			new Among("\366k\351", 4, 1, "", methodObject), new Among("\351\351", 3, 8, "", methodObject)
		});
		a_10 = (new Among[] {
			new Among("a", -1, 18, "", methodObject), new Among("ja", 0, 17, "", methodObject), new Among("d", -1, 16, "", methodObject), new Among("ad", 2, 13, "", methodObject), new Among("ed", 2, 13, "", methodObject), new Among("od", 2, 13, "", methodObject), new Among("\341d", 2, 14, "", methodObject), new Among("\351d", 2, 15, "", methodObject), new Among("\366d", 2, 13, "", methodObject), new Among("e", -1, 18, "", methodObject), 
			new Among("je", 9, 17, "", methodObject), new Among("nk", -1, 4, "", methodObject), new Among("unk", 11, 1, "", methodObject), new Among("\341nk", 11, 2, "", methodObject), new Among("\351nk", 11, 3, "", methodObject), new Among("\374nk", 11, 1, "", methodObject), new Among("uk", -1, 8, "", methodObject), new Among("juk", 16, 7, "", methodObject), new Among("\341juk", 17, 5, "", methodObject), new Among("\374k", -1, 8, "", methodObject), 
			new Among("j\374k", 19, 7, "", methodObject), new Among("\351j\374k", 20, 6, "", methodObject), new Among("m", -1, 12, "", methodObject), new Among("am", 22, 9, "", methodObject), new Among("em", 22, 9, "", methodObject), new Among("om", 22, 9, "", methodObject), new Among("\341m", 22, 10, "", methodObject), new Among("\351m", 22, 11, "", methodObject), new Among("o", -1, 18, "", methodObject), new Among("\341", -1, 19, "", methodObject), 
			new Among("\351", -1, 20, "", methodObject)
		});
		a_11 = (new Among[] {
			new Among("id", -1, 10, "", methodObject), new Among("aid", 0, 9, "", methodObject), new Among("jaid", 1, 6, "", methodObject), new Among("eid", 0, 9, "", methodObject), new Among("jeid", 3, 6, "", methodObject), new Among("\341id", 0, 7, "", methodObject), new Among("\351id", 0, 8, "", methodObject), new Among("i", -1, 15, "", methodObject), new Among("ai", 7, 14, "", methodObject), new Among("jai", 8, 11, "", methodObject), 
			new Among("ei", 7, 14, "", methodObject), new Among("jei", 10, 11, "", methodObject), new Among("\341i", 7, 12, "", methodObject), new Among("\351i", 7, 13, "", methodObject), new Among("itek", -1, 24, "", methodObject), new Among("eitek", 14, 21, "", methodObject), new Among("jeitek", 15, 20, "", methodObject), new Among("\351itek", 14, 23, "", methodObject), new Among("ik", -1, 29, "", methodObject), new Among("aik", 18, 26, "", methodObject), 
			new Among("jaik", 19, 25, "", methodObject), new Among("eik", 18, 26, "", methodObject), new Among("jeik", 21, 25, "", methodObject), new Among("\341ik", 18, 27, "", methodObject), new Among("\351ik", 18, 28, "", methodObject), new Among("ink", -1, 20, "", methodObject), new Among("aink", 25, 17, "", methodObject), new Among("jaink", 26, 16, "", methodObject), new Among("eink", 25, 17, "", methodObject), new Among("jeink", 28, 16, "", methodObject), 
			new Among("\341ink", 25, 18, "", methodObject), new Among("\351ink", 25, 19, "", methodObject), new Among("aitok", -1, 21, "", methodObject), new Among("jaitok", 32, 20, "", methodObject), new Among("\341itok", -1, 22, "", methodObject), new Among("im", -1, 5, "", methodObject), new Among("aim", 35, 4, "", methodObject), new Among("jaim", 36, 1, "", methodObject), new Among("eim", 35, 4, "", methodObject), new Among("jeim", 38, 1, "", methodObject), 
			new Among("\341im", 35, 2, "", methodObject), new Among("\351im", 35, 3, "", methodObject)
		});
	}
}
