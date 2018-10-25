// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FrenchStemmer.java

package org.tartarus.snowball.ext;

import org.tartarus.snowball.Among;
import org.tartarus.snowball.SnowballProgram;

public class FrenchStemmer extends SnowballProgram
{

	private static final long serialVersionUID = 1L;
	private static final FrenchStemmer methodObject;
	private static final Among a_0[];
	private static final Among a_1[];
	private static final Among a_2[];
	private static final Among a_3[];
	private static final Among a_4[];
	private static final Among a_5[];
	private static final Among a_6[];
	private static final Among a_7[];
	private static final Among a_8[];
	private static final char g_v[] = {
		'\021', 'A', '\020', '\001', '\0', '\0', '\0', '\0', '\0', '\0', 
		'\0', '\0', '\0', '\0', '\0', '\200', '\202', 'g', '\b', '\005'
	};
	private static final char g_keep_with_s[] = {
		'\001', 'A', '\024', '\0', '\0', '\0', '\0', '\0', '\0', '\0', 
		'\0', '\0', '\0', '\0', '\0', '\0', '\200'
	};
	private int I_p2;
	private int I_p1;
	private int I_pV;

	public FrenchStemmer()
	{
	}

	private void copy_from(FrenchStemmer other)
	{
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
						int v_3 = cursor;
						if (in_grouping(g_v, 97, 251))
						{
							bra = cursor;
							int v_4 = cursor;
							if (eq_s(1, "u"))
							{
								ket = cursor;
								if (in_grouping(g_v, 97, 251))
								{
									slice_from("U");
									break label2;
								}
							}
							cursor = v_4;
							if (eq_s(1, "i"))
							{
								ket = cursor;
								if (in_grouping(g_v, 97, 251))
								{
									slice_from("I");
									break label2;
								}
							}
							cursor = v_4;
							if (eq_s(1, "y"))
							{
								ket = cursor;
								slice_from("Y");
								break label2;
							}
						}
						cursor = v_3;
						bra = cursor;
						if (eq_s(1, "y"))
						{
							ket = cursor;
							if (in_grouping(g_v, 97, 251))
							{
								slice_from("Y");
								break label2;
							}
						}
						cursor = v_3;
						if (!eq_s(1, "q"))
							break label1;
						bra = cursor;
						if (!eq_s(1, "u"))
							break label1;
						ket = cursor;
						slice_from("U");
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
			I_pV = limit;
			I_p1 = limit;
			I_p2 = limit;
			v_1 = cursor;
			int v_2 = cursor;
			if (in_grouping(g_v, 97, 251) && in_grouping(g_v, 97, 251) && cursor < limit)
			{
				cursor++;
			} else
			{
				cursor = v_2;
				if (find_among(a_0, 3) == 0)
				{
					cursor = v_2;
					if (cursor >= limit)
						break label0;
					for (cursor++; !in_grouping(g_v, 97, 251); cursor++)
						if (cursor >= limit)
							break label0;

				}
			}
			I_pV = cursor;
		}
		int v_4;
label1:
		{
			cursor = v_1;
			v_4 = cursor;
			while (!in_grouping(g_v, 97, 251)) 
			{
				if (cursor >= limit)
					break label1;
				cursor++;
			}
			while (!out_grouping(g_v, 97, 251)) 
			{
				if (cursor >= limit)
					break label1;
				cursor++;
			}
			I_p1 = cursor;
			while (!in_grouping(g_v, 97, 251)) 
			{
				if (cursor >= limit)
					break label1;
				cursor++;
			}
			while (!out_grouping(g_v, 97, 251)) 
			{
				if (cursor >= limit)
					break label1;
				cursor++;
			}
			I_p2 = cursor;
		}
		cursor = v_4;
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
			int among_var = find_among(a_1, 4);
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
				slice_from("y");
				break;

			case 4: // '\004'
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

	private boolean r_standard_suffix()
	{
		ket = cursor;
		int among_var = find_among_b(a_4, 43);
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
			if (!r_R2())
				return false;
			slice_del();
			break;

		case 2: // '\002'
			if (!r_R2())
				return false;
			slice_del();
			int v_1 = limit - cursor;
			ket = cursor;
			if (!eq_s_b(2, "ic"))
			{
				cursor = limit - v_1;
				break;
			}
			bra = cursor;
			int v_2 = limit - cursor;
			if (r_R2())
			{
				slice_del();
			} else
			{
				cursor = limit - v_2;
				slice_from("iqU");
			}
			break;

		case 3: // '\003'
			if (!r_R2())
				return false;
			slice_from("log");
			break;

		case 4: // '\004'
			if (!r_R2())
				return false;
			slice_from("u");
			break;

		case 5: // '\005'
			if (!r_R2())
				return false;
			slice_from("ent");
			break;

		case 6: // '\006'
			if (!r_RV())
				return false;
			slice_del();
			int v_3 = limit - cursor;
			ket = cursor;
			among_var = find_among_b(a_2, 6);
			if (among_var == 0)
			{
				cursor = limit - v_3;
				break;
			}
			bra = cursor;
			switch (among_var)
			{
			default:
				break;

			case 0: // '\0'
				cursor = limit - v_3;
				break label0;

			case 1: // '\001'
				if (!r_R2())
				{
					cursor = limit - v_3;
					break label0;
				}
				slice_del();
				ket = cursor;
				if (!eq_s_b(2, "at"))
				{
					cursor = limit - v_3;
					break label0;
				}
				bra = cursor;
				if (!r_R2())
					cursor = limit - v_3;
				else
					slice_del();
				break label0;

			case 2: // '\002'
				int v_4 = limit - cursor;
				if (r_R2())
				{
					slice_del();
					break label0;
				}
				cursor = limit - v_4;
				if (!r_R1())
					cursor = limit - v_3;
				else
					slice_from("eux");
				break label0;

			case 3: // '\003'
				if (!r_R2())
					cursor = limit - v_3;
				else
					slice_del();
				break label0;

			case 4: // '\004'
				if (!r_RV())
					cursor = limit - v_3;
				else
					slice_from("i");
				break;
			}
			break;

		case 7: // '\007'
			if (!r_R2())
				return false;
			slice_del();
			int v_5 = limit - cursor;
			ket = cursor;
			among_var = find_among_b(a_3, 3);
			if (among_var == 0)
			{
				cursor = limit - v_5;
				break;
			}
			bra = cursor;
			switch (among_var)
			{
			default:
				break;

			case 0: // '\0'
				cursor = limit - v_5;
				break label0;

			case 1: // '\001'
				int v_6 = limit - cursor;
				if (r_R2())
				{
					slice_del();
				} else
				{
					cursor = limit - v_6;
					slice_from("abl");
				}
				break label0;

			case 2: // '\002'
				int v_7 = limit - cursor;
				if (r_R2())
				{
					slice_del();
				} else
				{
					cursor = limit - v_7;
					slice_from("iqU");
				}
				break label0;

			case 3: // '\003'
				if (!r_R2())
					cursor = limit - v_5;
				else
					slice_del();
				break;
			}
			break;

		case 8: // '\b'
			if (!r_R2())
				return false;
			slice_del();
			int v_8 = limit - cursor;
			ket = cursor;
			if (!eq_s_b(2, "at"))
			{
				cursor = limit - v_8;
				break;
			}
			bra = cursor;
			if (!r_R2())
			{
				cursor = limit - v_8;
				break;
			}
			slice_del();
			ket = cursor;
			if (!eq_s_b(2, "ic"))
			{
				cursor = limit - v_8;
				break;
			}
			bra = cursor;
			int v_9 = limit - cursor;
			if (r_R2())
			{
				slice_del();
			} else
			{
				cursor = limit - v_9;
				slice_from("iqU");
			}
			break;

		case 9: // '\t'
			slice_from("eau");
			break;

		case 10: // '\n'
			if (!r_R1())
				return false;
			slice_from("al");
			break;

		case 11: // '\013'
			int v_10 = limit - cursor;
			if (r_R2())
			{
				slice_del();
				break;
			}
			cursor = limit - v_10;
			if (!r_R1())
				return false;
			slice_from("eux");
			break;

		case 12: // '\f'
			if (!r_R1())
				return false;
			if (!out_grouping_b(g_v, 97, 251))
				return false;
			slice_del();
			break;

		case 13: // '\r'
			if (!r_RV())
			{
				return false;
			} else
			{
				slice_from("ant");
				return false;
			}

		case 14: // '\016'
			if (!r_RV())
			{
				return false;
			} else
			{
				slice_from("ent");
				return false;
			}

		case 15: // '\017'
			int v_11 = limit - cursor;
			if (!in_grouping_b(g_v, 97, 251))
				return false;
			if (!r_RV())
			{
				return false;
			} else
			{
				cursor = limit - v_11;
				slice_del();
				return false;
			}
		}
		return true;
	}

	private boolean r_i_verb_suffix()
	{
		int v_1 = limit - cursor;
		if (cursor < I_pV)
			return false;
		cursor = I_pV;
		int v_2 = limit_backward;
		limit_backward = cursor;
		cursor = limit - v_1;
		ket = cursor;
		int among_var = find_among_b(a_5, 35);
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
			if (!out_grouping_b(g_v, 97, 251))
			{
				limit_backward = v_2;
				return false;
			}
			slice_del();
			break;
		}
		limit_backward = v_2;
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
		int among_var = find_among_b(a_6, 38);
		if (among_var == 0)
		{
			limit_backward = v_2;
			return false;
		}
		bra = cursor;
		switch (among_var)
		{
		default:
			break;

		case 0: // '\0'
			limit_backward = v_2;
			return false;

		case 1: // '\001'
			if (!r_R2())
			{
				limit_backward = v_2;
				return false;
			}
			slice_del();
			break;

		case 2: // '\002'
			slice_del();
			break;

		case 3: // '\003'
			slice_del();
			int v_3 = limit - cursor;
			ket = cursor;
			if (!eq_s_b(1, "e"))
			{
				cursor = limit - v_3;
			} else
			{
				bra = cursor;
				slice_del();
			}
			break;
		}
		limit_backward = v_2;
		return true;
	}

	private boolean r_residual_suffix()
	{
		int v_1 = limit - cursor;
		ket = cursor;
		if (!eq_s_b(1, "s"))
		{
			cursor = limit - v_1;
		} else
		{
			bra = cursor;
			int v_2 = limit - cursor;
			if (!out_grouping_b(g_keep_with_s, 97, 232))
			{
				cursor = limit - v_1;
			} else
			{
				cursor = limit - v_2;
				slice_del();
			}
		}
		int v_3 = limit - cursor;
		if (cursor < I_pV)
			return false;
		cursor = I_pV;
		int v_4 = limit_backward;
		limit_backward = cursor;
		cursor = limit - v_3;
		ket = cursor;
		int among_var = find_among_b(a_7, 7);
		if (among_var == 0)
		{
			limit_backward = v_4;
			return false;
		}
		bra = cursor;
		switch (among_var)
		{
		default:
			break;

		case 0: // '\0'
			limit_backward = v_4;
			return false;

		case 1: // '\001'
			if (!r_R2())
			{
				limit_backward = v_4;
				return false;
			}
			int v_5 = limit - cursor;
			if (!eq_s_b(1, "s"))
			{
				cursor = limit - v_5;
				if (!eq_s_b(1, "t"))
				{
					limit_backward = v_4;
					return false;
				}
			}
			slice_del();
			break;

		case 2: // '\002'
			slice_from("i");
			break;

		case 3: // '\003'
			slice_del();
			break;

		case 4: // '\004'
			if (!eq_s_b(2, "gu"))
			{
				limit_backward = v_4;
				return false;
			}
			slice_del();
			break;
		}
		limit_backward = v_4;
		return true;
	}

	private boolean r_un_double()
	{
		int v_1 = limit - cursor;
		if (find_among_b(a_8, 5) == 0)
			return false;
		cursor = limit - v_1;
		ket = cursor;
		if (cursor <= limit_backward)
		{
			return false;
		} else
		{
			cursor--;
			bra = cursor;
			slice_del();
			return true;
		}
	}

	private boolean r_un_accent()
	{
		int v_1;
		for (v_1 = 1; out_grouping_b(g_v, 97, 251); v_1--);
		if (v_1 > 0)
			return false;
		ket = cursor;
		int v_3 = limit - cursor;
		if (!eq_s_b(1, "\351"))
		{
			cursor = limit - v_3;
			if (!eq_s_b(1, "\350"))
				return false;
		}
		bra = cursor;
		slice_from("e");
		return true;
	}

	public boolean stem()
	{
		int v_3;
label0:
		{
			int v_4;
label1:
			{
				int v_1 = cursor;
				if (r_prelude());
				cursor = v_1;
				int v_2 = cursor;
				if (r_mark_regions());
				cursor = v_2;
				limit_backward = cursor;
				cursor = limit;
				v_3 = limit - cursor;
				v_4 = limit - cursor;
				int v_5 = limit - cursor;
				int v_6 = limit - cursor;
				if (!r_standard_suffix())
				{
					cursor = limit - v_6;
					if (!r_i_verb_suffix())
					{
						cursor = limit - v_6;
						if (!r_verb_suffix())
							break label1;
					}
				}
				cursor = limit - v_5;
				int v_7 = limit - cursor;
				ket = cursor;
				int v_8 = limit - cursor;
				if (eq_s_b(1, "Y"))
				{
					bra = cursor;
					slice_from("i");
				} else
				{
					cursor = limit - v_8;
					if (!eq_s_b(1, "\347"))
					{
						cursor = limit - v_7;
					} else
					{
						bra = cursor;
						slice_from("c");
					}
				}
				break label0;
			}
			cursor = limit - v_4;
			if (r_residual_suffix());
		}
		cursor = limit - v_3;
		int v_9 = limit - cursor;
		if (r_un_double());
		cursor = limit - v_9;
		int v_10 = limit - cursor;
		if (r_un_accent());
		cursor = limit - v_10;
		cursor = limit_backward;
		int v_11 = cursor;
		if (r_postlude());
		cursor = v_11;
		return true;
	}

	public boolean equals(Object o)
	{
		return o instanceof FrenchStemmer;
	}

	public int hashCode()
	{
		return org/tartarus/snowball/ext/FrenchStemmer.getName().hashCode();
	}

	static 
	{
		methodObject = new FrenchStemmer();
		a_0 = (new Among[] {
			new Among("col", -1, -1, "", methodObject), new Among("par", -1, -1, "", methodObject), new Among("tap", -1, -1, "", methodObject)
		});
		a_1 = (new Among[] {
			new Among("", -1, 4, "", methodObject), new Among("I", 0, 1, "", methodObject), new Among("U", 0, 2, "", methodObject), new Among("Y", 0, 3, "", methodObject)
		});
		a_2 = (new Among[] {
			new Among("iqU", -1, 3, "", methodObject), new Among("abl", -1, 3, "", methodObject), new Among("I\350r", -1, 4, "", methodObject), new Among("i\350r", -1, 4, "", methodObject), new Among("eus", -1, 2, "", methodObject), new Among("iv", -1, 1, "", methodObject)
		});
		a_3 = (new Among[] {
			new Among("ic", -1, 2, "", methodObject), new Among("abil", -1, 1, "", methodObject), new Among("iv", -1, 3, "", methodObject)
		});
		a_4 = (new Among[] {
			new Among("iqUe", -1, 1, "", methodObject), new Among("atrice", -1, 2, "", methodObject), new Among("ance", -1, 1, "", methodObject), new Among("ence", -1, 5, "", methodObject), new Among("logie", -1, 3, "", methodObject), new Among("able", -1, 1, "", methodObject), new Among("isme", -1, 1, "", methodObject), new Among("euse", -1, 11, "", methodObject), new Among("iste", -1, 1, "", methodObject), new Among("ive", -1, 8, "", methodObject), 
			new Among("if", -1, 8, "", methodObject), new Among("usion", -1, 4, "", methodObject), new Among("ation", -1, 2, "", methodObject), new Among("ution", -1, 4, "", methodObject), new Among("ateur", -1, 2, "", methodObject), new Among("iqUes", -1, 1, "", methodObject), new Among("atrices", -1, 2, "", methodObject), new Among("ances", -1, 1, "", methodObject), new Among("ences", -1, 5, "", methodObject), new Among("logies", -1, 3, "", methodObject), 
			new Among("ables", -1, 1, "", methodObject), new Among("ismes", -1, 1, "", methodObject), new Among("euses", -1, 11, "", methodObject), new Among("istes", -1, 1, "", methodObject), new Among("ives", -1, 8, "", methodObject), new Among("ifs", -1, 8, "", methodObject), new Among("usions", -1, 4, "", methodObject), new Among("ations", -1, 2, "", methodObject), new Among("utions", -1, 4, "", methodObject), new Among("ateurs", -1, 2, "", methodObject), 
			new Among("ments", -1, 15, "", methodObject), new Among("ements", 30, 6, "", methodObject), new Among("issements", 31, 12, "", methodObject), new Among("it\351s", -1, 7, "", methodObject), new Among("ment", -1, 15, "", methodObject), new Among("ement", 34, 6, "", methodObject), new Among("issement", 35, 12, "", methodObject), new Among("amment", 34, 13, "", methodObject), new Among("emment", 34, 14, "", methodObject), new Among("aux", -1, 10, "", methodObject), 
			new Among("eaux", 39, 9, "", methodObject), new Among("eux", -1, 1, "", methodObject), new Among("it\351", -1, 7, "", methodObject)
		});
		a_5 = (new Among[] {
			new Among("ira", -1, 1, "", methodObject), new Among("ie", -1, 1, "", methodObject), new Among("isse", -1, 1, "", methodObject), new Among("issante", -1, 1, "", methodObject), new Among("i", -1, 1, "", methodObject), new Among("irai", 4, 1, "", methodObject), new Among("ir", -1, 1, "", methodObject), new Among("iras", -1, 1, "", methodObject), new Among("ies", -1, 1, "", methodObject), new Among("\356mes", -1, 1, "", methodObject), 
			new Among("isses", -1, 1, "", methodObject), new Among("issantes", -1, 1, "", methodObject), new Among("\356tes", -1, 1, "", methodObject), new Among("is", -1, 1, "", methodObject), new Among("irais", 13, 1, "", methodObject), new Among("issais", 13, 1, "", methodObject), new Among("irions", -1, 1, "", methodObject), new Among("issions", -1, 1, "", methodObject), new Among("irons", -1, 1, "", methodObject), new Among("issons", -1, 1, "", methodObject), 
			new Among("issants", -1, 1, "", methodObject), new Among("it", -1, 1, "", methodObject), new Among("irait", 21, 1, "", methodObject), new Among("issait", 21, 1, "", methodObject), new Among("issant", -1, 1, "", methodObject), new Among("iraIent", -1, 1, "", methodObject), new Among("issaIent", -1, 1, "", methodObject), new Among("irent", -1, 1, "", methodObject), new Among("issent", -1, 1, "", methodObject), new Among("iront", -1, 1, "", methodObject), 
			new Among("\356t", -1, 1, "", methodObject), new Among("iriez", -1, 1, "", methodObject), new Among("issiez", -1, 1, "", methodObject), new Among("irez", -1, 1, "", methodObject), new Among("issez", -1, 1, "", methodObject)
		});
		a_6 = (new Among[] {
			new Among("a", -1, 3, "", methodObject), new Among("era", 0, 2, "", methodObject), new Among("asse", -1, 3, "", methodObject), new Among("ante", -1, 3, "", methodObject), new Among("\351e", -1, 2, "", methodObject), new Among("ai", -1, 3, "", methodObject), new Among("erai", 5, 2, "", methodObject), new Among("er", -1, 2, "", methodObject), new Among("as", -1, 3, "", methodObject), new Among("eras", 8, 2, "", methodObject), 
			new Among("\342mes", -1, 3, "", methodObject), new Among("asses", -1, 3, "", methodObject), new Among("antes", -1, 3, "", methodObject), new Among("\342tes", -1, 3, "", methodObject), new Among("\351es", -1, 2, "", methodObject), new Among("ais", -1, 3, "", methodObject), new Among("erais", 15, 2, "", methodObject), new Among("ions", -1, 1, "", methodObject), new Among("erions", 17, 2, "", methodObject), new Among("assions", 17, 3, "", methodObject), 
			new Among("erons", -1, 2, "", methodObject), new Among("ants", -1, 3, "", methodObject), new Among("\351s", -1, 2, "", methodObject), new Among("ait", -1, 3, "", methodObject), new Among("erait", 23, 2, "", methodObject), new Among("ant", -1, 3, "", methodObject), new Among("aIent", -1, 3, "", methodObject), new Among("eraIent", 26, 2, "", methodObject), new Among("\350rent", -1, 2, "", methodObject), new Among("assent", -1, 3, "", methodObject), 
			new Among("eront", -1, 2, "", methodObject), new Among("\342t", -1, 3, "", methodObject), new Among("ez", -1, 2, "", methodObject), new Among("iez", 32, 2, "", methodObject), new Among("eriez", 33, 2, "", methodObject), new Among("assiez", 33, 3, "", methodObject), new Among("erez", 32, 2, "", methodObject), new Among("\351", -1, 2, "", methodObject)
		});
		a_7 = (new Among[] {
			new Among("e", -1, 3, "", methodObject), new Among("I\350re", 0, 2, "", methodObject), new Among("i\350re", 0, 2, "", methodObject), new Among("ion", -1, 1, "", methodObject), new Among("Ier", -1, 2, "", methodObject), new Among("ier", -1, 2, "", methodObject), new Among("\353", -1, 4, "", methodObject)
		});
		a_8 = (new Among[] {
			new Among("ell", -1, -1, "", methodObject), new Among("eill", -1, -1, "", methodObject), new Among("enn", -1, -1, "", methodObject), new Among("onn", -1, -1, "", methodObject), new Among("ett", -1, -1, "", methodObject)
		});
	}
}
