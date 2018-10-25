// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GermanStemmer.java

package org.tartarus.snowball.ext;

import org.tartarus.snowball.Among;
import org.tartarus.snowball.SnowballProgram;

public class GermanStemmer extends SnowballProgram
{

	private static final long serialVersionUID = 1L;
	private static final GermanStemmer methodObject;
	private static final Among a_0[];
	private static final Among a_1[];
	private static final Among a_2[];
	private static final Among a_3[];
	private static final Among a_4[];
	private static final char g_v[] = {
		'\021', 'A', '\020', '\001', '\0', '\0', '\0', '\0', '\0', '\0', 
		'\0', '\0', '\0', '\0', '\0', '\0', '\b', '\0', ' ', '\b'
	};
	private static final char g_s_ending[] = {
		'u', '\036', '\005'
	};
	private static final char g_st_ending[] = {
		'u', '\036', '\004'
	};
	private int I_x;
	private int I_p2;
	private int I_p1;

	public GermanStemmer()
	{
	}

	private void copy_from(GermanStemmer other)
	{
		I_x = other.I_x;
		I_p2 = other.I_p2;
		I_p1 = other.I_p1;
		super.copy_from(other);
	}

	private boolean r_prelude()
	{
		int v_1 = cursor;
		int v_2;
		do
		{
			v_2 = cursor;
			int v_3 = cursor;
			bra = cursor;
			if (eq_s(1, "\337"))
			{
				ket = cursor;
				slice_from("ss");
				continue;
			}
			cursor = v_3;
			if (cursor >= limit)
				break;
			cursor++;
		} while (true);
		cursor = v_2;
		cursor = v_1;
label0:
		do
		{
			int v_4 = cursor;
			do
			{
				int v_5;
label1:
				{
label2:
					{
						v_5 = cursor;
						if (!in_grouping(g_v, 97, 252))
							break label1;
						bra = cursor;
						int v_6 = cursor;
						if (eq_s(1, "u"))
						{
							ket = cursor;
							if (in_grouping(g_v, 97, 252))
							{
								slice_from("U");
								break label2;
							}
						}
						cursor = v_6;
						if (!eq_s(1, "y"))
							break label1;
						ket = cursor;
						if (!in_grouping(g_v, 97, 252))
							break label1;
						slice_from("Y");
					}
					cursor = v_5;
					continue label0;
				}
				cursor = v_5;
				if (cursor >= limit)
					break;
				cursor++;
			} while (true);
			cursor = v_4;
			return true;
		} while (true);
	}

	private boolean r_mark_regions()
	{
		I_p1 = limit;
		I_p2 = limit;
		int v_1 = cursor;
		int c = cursor + 3;
		if (0 > c || c > limit)
			return false;
		cursor = c;
		I_x = cursor;
		for (cursor = v_1; !in_grouping(g_v, 97, 252); cursor++)
			if (cursor >= limit)
				return false;

		while (!out_grouping(g_v, 97, 252)) 
		{
			if (cursor >= limit)
				return false;
			cursor++;
		}
		I_p1 = cursor;
		if (I_p1 < I_x)
			I_p1 = I_x;
		while (!in_grouping(g_v, 97, 252)) 
		{
			if (cursor >= limit)
				return false;
			cursor++;
		}
		while (!out_grouping(g_v, 97, 252)) 
		{
			if (cursor >= limit)
				return false;
			cursor++;
		}
		I_p2 = cursor;
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
			int among_var = find_among(a_0, 6);
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
				slice_from("y");
				break;

			case 2: // '\002'
				slice_from("u");
				break;

			case 3: // '\003'
				slice_from("a");
				break;

			case 4: // '\004'
				slice_from("o");
				break;

			case 5: // '\005'
				slice_from("u");
				break;

			case 6: // '\006'
				if (cursor >= limit)
					break label0;
				cursor++;
				break;
			}
		} while (true);
		cursor = v_1;
		return true;
	}

	private boolean r_R1()
	{
		return I_p1 <= cursor;
	}

	private boolean r_R2()
	{
		return I_p2 <= cursor;
	}

	private boolean r_standard_suffix()
	{
		int v_1 = limit - cursor;
		ket = cursor;
		int among_var = find_among_b(a_1, 7);
		if (among_var != 0)
		{
			bra = cursor;
			if (r_R1())
				switch (among_var)
				{
				case 0: // '\0'
				default:
					break;

				case 1: // '\001'
					slice_del();
					break;

				case 2: // '\002'
					if (in_grouping_b(g_s_ending, 98, 116))
						slice_del();
					break;
				}
		}
		cursor = limit - v_1;
		int v_2 = limit - cursor;
		ket = cursor;
		among_var = find_among_b(a_2, 4);
		if (among_var != 0)
		{
			bra = cursor;
			if (r_R1())
				switch (among_var)
				{
				case 0: // '\0'
				default:
					break;

				case 1: // '\001'
					slice_del();
					break;

				case 2: // '\002'
					if (!in_grouping_b(g_st_ending, 98, 116))
						break;
					int c = cursor - 3;
					if (limit_backward <= c && c <= limit)
					{
						cursor = c;
						slice_del();
					}
					break;
				}
		}
		cursor = limit - v_2;
		int v_3 = limit - cursor;
		ket = cursor;
		among_var = find_among_b(a_4, 8);
		if (among_var != 0)
		{
			bra = cursor;
			if (r_R2())
				switch (among_var)
				{
				case 0: // '\0'
				default:
					break;

				case 1: // '\001'
					slice_del();
					int v_4 = limit - cursor;
					ket = cursor;
					if (!eq_s_b(2, "ig"))
					{
						cursor = limit - v_4;
					} else
					{
						bra = cursor;
						int v_5 = limit - cursor;
						if (eq_s_b(1, "e"))
						{
							cursor = limit - v_4;
						} else
						{
							cursor = limit - v_5;
							if (!r_R2())
								cursor = limit - v_4;
							else
								slice_del();
						}
					}
					break;

				case 2: // '\002'
					int v_6 = limit - cursor;
					if (!eq_s_b(1, "e"))
					{
						cursor = limit - v_6;
						slice_del();
					}
					break;

				case 3: // '\003'
					slice_del();
					int v_7 = limit - cursor;
					ket = cursor;
					int v_8 = limit - cursor;
					if (!eq_s_b(2, "er"))
					{
						cursor = limit - v_8;
						if (!eq_s_b(2, "en"))
						{
							cursor = limit - v_7;
							break;
						}
					}
					bra = cursor;
					if (!r_R1())
						cursor = limit - v_7;
					else
						slice_del();
					break;

				case 4: // '\004'
					slice_del();
					int v_9 = limit - cursor;
					ket = cursor;
					among_var = find_among_b(a_3, 2);
					if (among_var == 0)
					{
						cursor = limit - v_9;
						break;
					}
					bra = cursor;
					if (!r_R2())
					{
						cursor = limit - v_9;
						break;
					}
					switch (among_var)
					{
					case 0: // '\0'
						cursor = limit - v_9;
						break;

					case 1: // '\001'
						slice_del();
						break;
					}
					break;
				}
		}
		cursor = limit - v_3;
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
		if (r_standard_suffix());
		cursor = limit - v_3;
		cursor = limit_backward;
		int v_4 = cursor;
		if (r_postlude());
		cursor = v_4;
		return true;
	}

	public boolean equals(Object o)
	{
		return o instanceof GermanStemmer;
	}

	public int hashCode()
	{
		return org/tartarus/snowball/ext/GermanStemmer.getName().hashCode();
	}

	static 
	{
		methodObject = new GermanStemmer();
		a_0 = (new Among[] {
			new Among("", -1, 6, "", methodObject), new Among("U", 0, 2, "", methodObject), new Among("Y", 0, 1, "", methodObject), new Among("\344", 0, 3, "", methodObject), new Among("\366", 0, 4, "", methodObject), new Among("\374", 0, 5, "", methodObject)
		});
		a_1 = (new Among[] {
			new Among("e", -1, 1, "", methodObject), new Among("em", -1, 1, "", methodObject), new Among("en", -1, 1, "", methodObject), new Among("ern", -1, 1, "", methodObject), new Among("er", -1, 1, "", methodObject), new Among("s", -1, 2, "", methodObject), new Among("es", 5, 1, "", methodObject)
		});
		a_2 = (new Among[] {
			new Among("en", -1, 1, "", methodObject), new Among("er", -1, 1, "", methodObject), new Among("st", -1, 2, "", methodObject), new Among("est", 2, 1, "", methodObject)
		});
		a_3 = (new Among[] {
			new Among("ig", -1, 1, "", methodObject), new Among("lich", -1, 1, "", methodObject)
		});
		a_4 = (new Among[] {
			new Among("end", -1, 1, "", methodObject), new Among("ig", -1, 2, "", methodObject), new Among("ung", -1, 1, "", methodObject), new Among("lich", -1, 3, "", methodObject), new Among("isch", -1, 2, "", methodObject), new Among("ik", -1, 2, "", methodObject), new Among("heit", -1, 3, "", methodObject), new Among("keit", -1, 4, "", methodObject)
		});
	}
}
