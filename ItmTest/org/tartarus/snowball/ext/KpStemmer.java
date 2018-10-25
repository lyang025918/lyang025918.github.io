// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   KpStemmer.java

package org.tartarus.snowball.ext;

import org.tartarus.snowball.Among;
import org.tartarus.snowball.SnowballProgram;

public class KpStemmer extends SnowballProgram
{

	private static final long serialVersionUID = 1L;
	private static final KpStemmer methodObject;
	private static final Among a_0[];
	private static final Among a_1[];
	private static final Among a_2[];
	private static final Among a_3[];
	private static final Among a_4[];
	private static final Among a_5[];
	private static final Among a_6[];
	private static final Among a_7[];
	private static final char g_v[] = {
		'\021', 'A', '\020', '\001'
	};
	private static final char g_v_WX[] = {
		'\021', 'A', '\320', '\001'
	};
	private static final char g_AOU[] = {
		'\001', '@', '\020'
	};
	private static final char g_AIOU[] = {
		'\001', 'A', '\020'
	};
	private boolean B_GE_removed;
	private boolean B_stemmed;
	private boolean B_Y_found;
	private int I_p2;
	private int I_p1;
	private int I_x;
	private StringBuilder S_ch;

	public KpStemmer()
	{
		S_ch = new StringBuilder();
	}

	private void copy_from(KpStemmer other)
	{
		B_GE_removed = other.B_GE_removed;
		B_stemmed = other.B_stemmed;
		B_Y_found = other.B_Y_found;
		I_p2 = other.I_p2;
		I_p1 = other.I_p1;
		I_x = other.I_x;
		S_ch = other.S_ch;
		super.copy_from(other);
	}

	private boolean r_R1()
	{
		I_x = cursor;
		return I_x >= I_p1;
	}

	private boolean r_R2()
	{
		I_x = cursor;
		return I_x >= I_p2;
	}

	private boolean r_V()
	{
		int v_1 = limit - cursor;
		int v_2 = limit - cursor;
		if (!in_grouping_b(g_v, 97, 121))
		{
			cursor = limit - v_2;
			if (!eq_s_b(2, "ij"))
				return false;
		}
		cursor = limit - v_1;
		return true;
	}

	private boolean r_VX()
	{
		int v_1 = limit - cursor;
		if (cursor <= limit_backward)
			return false;
		cursor--;
		int v_2 = limit - cursor;
		if (!in_grouping_b(g_v, 97, 121))
		{
			cursor = limit - v_2;
			if (!eq_s_b(2, "ij"))
				return false;
		}
		cursor = limit - v_1;
		return true;
	}

	private boolean r_C()
	{
		int v_1 = limit - cursor;
		int v_2 = limit - cursor;
		if (eq_s_b(2, "ij"))
			return false;
		cursor = limit - v_2;
		if (!out_grouping_b(g_v, 97, 121))
		{
			return false;
		} else
		{
			cursor = limit - v_1;
			return true;
		}
	}

	private boolean r_lengthen_V()
	{
		int v_1;
label0:
		{
label1:
			{
				int v_2;
label2:
				{
					v_1 = limit - cursor;
					if (!out_grouping_b(g_v_WX, 97, 121))
						break label0;
					ket = cursor;
					v_2 = limit - cursor;
					if (!in_grouping_b(g_AOU, 97, 117))
						break label2;
					bra = cursor;
					int v_3 = limit - cursor;
					int v_4 = limit - cursor;
					if (!out_grouping_b(g_v, 97, 121))
					{
						cursor = limit - v_4;
						if (cursor > limit_backward)
							break label2;
					}
					cursor = limit - v_3;
					break label1;
				}
				cursor = limit - v_2;
				if (!eq_s_b(1, "e"))
					break label0;
				bra = cursor;
				int v_5 = limit - cursor;
				int v_6 = limit - cursor;
				if (!out_grouping_b(g_v, 97, 121))
				{
					cursor = limit - v_6;
					if (cursor > limit_backward)
						break label0;
				}
				int v_7 = limit - cursor;
				if (in_grouping_b(g_AIOU, 97, 117))
					break label0;
				cursor = limit - v_7;
				int v_8 = limit - cursor;
				if (cursor > limit_backward)
				{
					cursor--;
					if (in_grouping_b(g_AIOU, 97, 117) && out_grouping_b(g_v, 97, 121))
						break label0;
				}
				cursor = limit - v_8;
				cursor = limit - v_5;
			}
			S_ch = slice_to(S_ch);
			int c = cursor;
			insert(cursor, cursor, S_ch);
			cursor = c;
		}
		cursor = limit - v_1;
		return true;
	}

	private boolean r_Step_1()
	{
		int among_var;
		ket = cursor;
		among_var = find_among_b(a_0, 7);
		if (among_var == 0)
			return false;
		bra = cursor;
		among_var;
		JVM INSTR tableswitch 0 7: default 623
	//	               0 80
	//	               1 82
	//	               2 89
	//	               3 159
	//	               4 177
	//	               5 334
	//	               6 361
	//	               7 617;
		   goto _L1 _L2 _L3 _L4 _L5 _L6 _L7 _L8 _L9
_L1:
		break; /* Loop/switch isn't completed */
_L2:
		return false;
_L3:
		slice_del();
		break; /* Loop/switch isn't completed */
_L4:
		if (!r_R1())
			return false;
		int v_1 = limit - cursor;
		if (eq_s_b(1, "t") && r_R1())
			return false;
		cursor = limit - v_1;
		if (!r_C())
			return false;
		slice_del();
		break; /* Loop/switch isn't completed */
_L5:
		if (!r_R1())
			return false;
		slice_from("ie");
		break; /* Loop/switch isn't completed */
_L6:
		int v_2 = limit - cursor;
		if (eq_s_b(2, "ar") && r_R1() && r_C())
		{
			bra = cursor;
			slice_del();
			if (r_lengthen_V())
				break; /* Loop/switch isn't completed */
		}
		cursor = limit - v_2;
		if (eq_s_b(2, "er") && r_R1() && r_C())
		{
			bra = cursor;
			slice_del();
			break; /* Loop/switch isn't completed */
		}
		cursor = limit - v_2;
		if (!r_R1())
			return false;
		if (!r_C())
			return false;
		slice_from("e");
		break; /* Loop/switch isn't completed */
_L7:
		if (!r_R1())
			return false;
		if (!r_V())
			return false;
		slice_from("au");
		break; /* Loop/switch isn't completed */
_L8:
		int v_3;
		int v_4;
		v_3 = limit - cursor;
		if (eq_s_b(3, "hed") && r_R1())
		{
			bra = cursor;
			slice_from("heid");
			break; /* Loop/switch isn't completed */
		}
		cursor = limit - v_3;
		if (eq_s_b(2, "nd"))
		{
			slice_del();
			break; /* Loop/switch isn't completed */
		}
		cursor = limit - v_3;
		if (eq_s_b(1, "d") && r_R1() && r_C())
		{
			bra = cursor;
			slice_del();
			break; /* Loop/switch isn't completed */
		}
		cursor = limit - v_3;
		v_4 = limit - cursor;
		if (eq_s_b(1, "i")) goto _L11; else goto _L10
_L10:
		cursor = limit - v_4;
		if (eq_s_b(1, "j")) goto _L11; else goto _L12
_L11:
		if (r_V())
		{
			slice_del();
			break; /* Loop/switch isn't completed */
		}
_L12:
		cursor = limit - v_3;
		if (!r_R1())
			return false;
		if (!r_C())
			return false;
		slice_del();
		if (!r_lengthen_V())
			return false;
		break; /* Loop/switch isn't completed */
_L9:
		slice_from("nd");
		return true;
	}

	private boolean r_Step_2()
	{
		ket = cursor;
		int among_var = find_among_b(a_1, 11);
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
			int v_1 = limit - cursor;
			if (eq_s_b(2, "'t"))
			{
				bra = cursor;
				slice_del();
				break;
			}
			cursor = limit - v_1;
			if (eq_s_b(2, "et"))
			{
				bra = cursor;
				if (r_R1() && r_C())
				{
					slice_del();
					break;
				}
			}
			cursor = limit - v_1;
			if (eq_s_b(3, "rnt"))
			{
				bra = cursor;
				slice_from("rn");
				break;
			}
			cursor = limit - v_1;
			if (eq_s_b(1, "t"))
			{
				bra = cursor;
				if (r_R1() && r_VX())
				{
					slice_del();
					break;
				}
			}
			cursor = limit - v_1;
			if (eq_s_b(3, "ink"))
			{
				bra = cursor;
				slice_from("ing");
				break;
			}
			cursor = limit - v_1;
			if (eq_s_b(2, "mp"))
			{
				bra = cursor;
				slice_from("m");
				break;
			}
			cursor = limit - v_1;
			if (eq_s_b(1, "'"))
			{
				bra = cursor;
				if (r_R1())
				{
					slice_del();
					break;
				}
			}
			cursor = limit - v_1;
			bra = cursor;
			if (!r_R1())
				return false;
			if (!r_C())
				return false;
			slice_del();
			break;

		case 2: // '\002'
			if (!r_R1())
				return false;
			slice_from("g");
			break;

		case 3: // '\003'
			if (!r_R1())
				return false;
			slice_from("lijk");
			break;

		case 4: // '\004'
			if (!r_R1())
				return false;
			slice_from("isch");
			break;

		case 5: // '\005'
			if (!r_R1())
				return false;
			if (!r_C())
				return false;
			slice_del();
			break;

		case 6: // '\006'
			if (!r_R1())
				return false;
			slice_from("t");
			break;

		case 7: // '\007'
			if (!r_R1())
				return false;
			slice_from("s");
			break;

		case 8: // '\b'
			if (!r_R1())
				return false;
			slice_from("r");
			break;

		case 9: // '\t'
			if (!r_R1())
				return false;
			slice_del();
			insert(cursor, cursor, "l");
			if (!r_lengthen_V())
				return false;
			break;

		case 10: // '\n'
			if (!r_R1())
				return false;
			if (!r_C())
				return false;
			slice_del();
			insert(cursor, cursor, "en");
			if (!r_lengthen_V())
				return false;
			break;

		case 11: // '\013'
			if (!r_R1())
				return false;
			if (!r_C())
				return false;
			slice_from("ief");
			break;
		}
		return true;
	}

	private boolean r_Step_3()
	{
		ket = cursor;
		int among_var = find_among_b(a_2, 14);
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
			slice_from("eer");
			break;

		case 2: // '\002'
			if (!r_R1())
				return false;
			slice_del();
			if (!r_lengthen_V())
				return false;
			break;

		case 3: // '\003'
			if (!r_R1())
				return false;
			slice_del();
			break;

		case 4: // '\004'
			slice_from("r");
			break;

		case 5: // '\005'
			if (!r_R1())
				return false;
			slice_del();
			if (!r_lengthen_V())
				return false;
			break;

		case 6: // '\006'
			if (!r_R1())
				return false;
			if (!r_C())
				return false;
			slice_from("aar");
			break;

		case 7: // '\007'
			if (!r_R2())
				return false;
			slice_del();
			insert(cursor, cursor, "f");
			if (!r_lengthen_V())
				return false;
			break;

		case 8: // '\b'
			if (!r_R2())
				return false;
			slice_del();
			insert(cursor, cursor, "g");
			if (!r_lengthen_V())
				return false;
			break;

		case 9: // '\t'
			if (!r_R1())
				return false;
			if (!r_C())
				return false;
			slice_from("t");
			break;

		case 10: // '\n'
			if (!r_R1())
				return false;
			if (!r_C())
				return false;
			slice_from("d");
			break;
		}
		return true;
	}

	private boolean r_Step_4()
	{
		int among_var;
		int v_1;
		v_1 = limit - cursor;
		ket = cursor;
		among_var = find_among_b(a_3, 16);
		if (among_var == 0)
			break MISSING_BLOCK_LABEL_341;
		bra = cursor;
		among_var;
		JVM INSTR tableswitch 0 10: default 338
	//	               0 104
	//	               1 107
	//	               2 126
	//	               3 145
	//	               4 162
	//	               5 191
	//	               6 220
	//	               7 249
	//	               8 268
	//	               9 287
	//	               10 304;
		   goto _L1 _L2 _L3 _L4 _L5 _L6 _L7 _L8 _L9 _L10 _L11 _L12
_L2:
		break MISSING_BLOCK_LABEL_341;
_L1:
		break; /* Loop/switch isn't completed */
_L3:
		if (r_R1())
		{
			slice_from("ie");
			break; /* Loop/switch isn't completed */
		}
		break MISSING_BLOCK_LABEL_341;
_L4:
		if (r_R1())
		{
			slice_from("eer");
			break; /* Loop/switch isn't completed */
		}
		break MISSING_BLOCK_LABEL_341;
_L5:
		if (r_R1())
		{
			slice_del();
			break; /* Loop/switch isn't completed */
		}
		break MISSING_BLOCK_LABEL_341;
_L6:
		if (r_R1() && r_V())
		{
			slice_from("n");
			break; /* Loop/switch isn't completed */
		}
		break MISSING_BLOCK_LABEL_341;
_L7:
		if (r_R1() && r_V())
		{
			slice_from("l");
			break; /* Loop/switch isn't completed */
		}
		break MISSING_BLOCK_LABEL_341;
_L8:
		if (r_R1() && r_V())
		{
			slice_from("r");
			break; /* Loop/switch isn't completed */
		}
		break MISSING_BLOCK_LABEL_341;
_L9:
		if (r_R1())
		{
			slice_from("teer");
			break; /* Loop/switch isn't completed */
		}
		break MISSING_BLOCK_LABEL_341;
_L10:
		if (r_R1())
		{
			slice_from("lijk");
			break; /* Loop/switch isn't completed */
		}
		break MISSING_BLOCK_LABEL_341;
_L11:
		if (r_R1())
		{
			slice_del();
			break; /* Loop/switch isn't completed */
		}
		break MISSING_BLOCK_LABEL_341;
_L12:
		if (r_R1() && r_C())
		{
			slice_del();
			if (r_lengthen_V())
				break; /* Loop/switch isn't completed */
		}
		cursor = limit - v_1;
		ket = cursor;
		among_var = find_among_b(a_4, 3);
		if (among_var == 0)
			return false;
		bra = cursor;
		switch (among_var)
		{
		case 0: // '\0'
			return false;

		case 1: // '\001'
			if (!r_R1())
				return false;
			if (!r_C())
				return false;
			slice_del();
			if (!r_lengthen_V())
				return false;
			break;
		}
		return true;
	}

	private boolean r_Step_7()
	{
		ket = cursor;
		int among_var = find_among_b(a_5, 3);
		if (among_var == 0)
			return false;
		bra = cursor;
		switch (among_var)
		{
		case 0: // '\0'
			return false;

		case 1: // '\001'
			slice_from("k");
			break;

		case 2: // '\002'
			slice_from("f");
			break;

		case 3: // '\003'
			slice_from("p");
			break;
		}
		return true;
	}

	private boolean r_Step_6()
	{
		ket = cursor;
		int among_var = find_among_b(a_6, 22);
		if (among_var == 0)
			return false;
		bra = cursor;
		switch (among_var)
		{
		case 0: // '\0'
			return false;

		case 1: // '\001'
			slice_from("b");
			break;

		case 2: // '\002'
			slice_from("c");
			break;

		case 3: // '\003'
			slice_from("d");
			break;

		case 4: // '\004'
			slice_from("f");
			break;

		case 5: // '\005'
			slice_from("g");
			break;

		case 6: // '\006'
			slice_from("h");
			break;

		case 7: // '\007'
			slice_from("j");
			break;

		case 8: // '\b'
			slice_from("k");
			break;

		case 9: // '\t'
			slice_from("l");
			break;

		case 10: // '\n'
			slice_from("m");
			break;

		case 11: // '\013'
			slice_from("n");
			break;

		case 12: // '\f'
			slice_from("p");
			break;

		case 13: // '\r'
			slice_from("q");
			break;

		case 14: // '\016'
			slice_from("r");
			break;

		case 15: // '\017'
			slice_from("s");
			break;

		case 16: // '\020'
			slice_from("t");
			break;

		case 17: // '\021'
			slice_from("v");
			break;

		case 18: // '\022'
			slice_from("w");
			break;

		case 19: // '\023'
			slice_from("x");
			break;

		case 20: // '\024'
			slice_from("z");
			break;

		case 21: // '\025'
			slice_from("f");
			break;

		case 22: // '\026'
			slice_from("s");
			break;
		}
		return true;
	}

	private boolean r_Step_1c()
	{
		ket = cursor;
		int among_var = find_among_b(a_7, 2);
		if (among_var == 0)
			return false;
		bra = cursor;
		if (!r_R1())
			return false;
		if (!r_C())
			return false;
		switch (among_var)
		{
		default:
			break;

		case 0: // '\0'
			return false;

		case 1: // '\001'
			int v_1 = limit - cursor;
			if (eq_s_b(1, "n") && r_R1())
				return false;
			cursor = limit - v_1;
			slice_del();
			break;

		case 2: // '\002'
			int v_2 = limit - cursor;
			if (eq_s_b(1, "h") && r_R1())
				return false;
			cursor = limit - v_2;
			slice_del();
			break;
		}
		return true;
	}

	private boolean r_Lose_prefix()
	{
		bra = cursor;
		if (!eq_s(2, "ge"))
			return false;
		ket = cursor;
		int v_1 = cursor;
		int c = cursor + 3;
		if (0 > c || c > limit)
			return false;
		cursor = c;
		cursor = v_1;
		do
		{
			int v_2 = cursor;
			if (in_grouping(g_v, 97, 121))
			{
				cursor = v_2;
				break;
			}
			cursor = v_2;
			if (cursor >= limit)
				return false;
			cursor++;
		} while (true);
		do
		{
			int v_3 = cursor;
			if (out_grouping(g_v, 97, 121))
			{
				cursor = v_3;
				break;
			}
			cursor = v_3;
			if (cursor >= limit)
				return false;
			cursor++;
		} while (true);
		B_GE_removed = true;
		slice_del();
		return true;
	}

	private boolean r_Lose_infix()
	{
		if (cursor >= limit)
			return false;
		cursor++;
		do
		{
			bra = cursor;
			if (eq_s(2, "ge"))
			{
				ket = cursor;
				break;
			}
			if (cursor >= limit)
				return false;
			cursor++;
		} while (true);
		int v_2 = cursor;
		int c = cursor + 3;
		if (0 > c || c > limit)
			return false;
		cursor = c;
		cursor = v_2;
		do
		{
			int v_3 = cursor;
			if (in_grouping(g_v, 97, 121))
			{
				cursor = v_3;
				break;
			}
			cursor = v_3;
			if (cursor >= limit)
				return false;
			cursor++;
		} while (true);
		do
		{
			int v_4 = cursor;
			if (out_grouping(g_v, 97, 121))
			{
				cursor = v_4;
				break;
			}
			cursor = v_4;
			if (cursor >= limit)
				return false;
			cursor++;
		} while (true);
		B_GE_removed = true;
		slice_del();
		return true;
	}

	private boolean r_measure()
	{
		int v_1 = cursor;
		cursor = limit;
		I_p1 = cursor;
		I_p2 = cursor;
		cursor = v_1;
		int v_2 = cursor;
		while (out_grouping(g_v, 97, 121)) ;
		int v_4 = 1;
		int v_5;
		do
		{
			v_5 = cursor;
			int v_6 = cursor;
			if (!eq_s(2, "ij"))
			{
				cursor = v_6;
				if (!in_grouping(g_v, 97, 121))
					break;
			}
			v_4--;
		} while (true);
		cursor = v_5;
		if (v_4 <= 0 && out_grouping(g_v, 97, 121))
		{
			I_p1 = cursor;
			while (out_grouping(g_v, 97, 121)) ;
			int v_8 = 1;
			int v_9;
			do
			{
				v_9 = cursor;
				int v_10 = cursor;
				if (!eq_s(2, "ij"))
				{
					cursor = v_10;
					if (!in_grouping(g_v, 97, 121))
						break;
				}
				v_8--;
			} while (true);
			cursor = v_9;
			if (v_8 <= 0 && out_grouping(g_v, 97, 121))
				I_p2 = cursor;
		}
		cursor = v_2;
		return true;
	}

	public boolean stem()
	{
		B_Y_found = false;
		B_stemmed = false;
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
label0:
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
					break label0;
				cursor++;
			} while (true);
			slice_from("Y");
			B_Y_found = true;
		} while (true);
		cursor = v_3;
		cursor = v_2;
		if (!r_measure())
			return false;
		limit_backward = cursor;
		cursor = limit;
		int v_5 = limit - cursor;
		if (r_Step_1())
			B_stemmed = true;
		cursor = limit - v_5;
		int v_6 = limit - cursor;
		if (r_Step_2())
			B_stemmed = true;
		cursor = limit - v_6;
		int v_7 = limit - cursor;
		if (r_Step_3())
			B_stemmed = true;
		cursor = limit - v_7;
		int v_8 = limit - cursor;
		if (r_Step_4())
			B_stemmed = true;
		cursor = limit - v_8;
		cursor = limit_backward;
		B_GE_removed = false;
		int v_9 = cursor;
		int v_10 = cursor;
		if (r_Lose_prefix())
		{
			cursor = v_10;
			if (r_measure());
		}
		cursor = v_9;
		limit_backward = cursor;
		cursor = limit;
		int v_11 = limit - cursor;
		if (B_GE_removed)
			if (r_Step_1c());
		cursor = limit - v_11;
		cursor = limit_backward;
		B_GE_removed = false;
		int v_12 = cursor;
		int v_13 = cursor;
		if (r_Lose_infix())
		{
			cursor = v_13;
			if (r_measure());
		}
		cursor = v_12;
		limit_backward = cursor;
		cursor = limit;
		int v_14 = limit - cursor;
		if (B_GE_removed)
			if (r_Step_1c());
		cursor = limit - v_14;
		cursor = limit_backward;
		limit_backward = cursor;
		cursor = limit;
		int v_15 = limit - cursor;
		if (r_Step_7())
			B_stemmed = true;
		cursor = limit - v_15;
		int v_16 = limit - cursor;
		if (B_stemmed || B_GE_removed)
			if (r_Step_6());
		cursor = limit - v_16;
		cursor = limit_backward;
		int v_18 = cursor;
		if (B_Y_found)
		{
			int v_19;
label1:
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
						break label1;
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
		return o instanceof KpStemmer;
	}

	public int hashCode()
	{
		return org/tartarus/snowball/ext/KpStemmer.getName().hashCode();
	}

	static 
	{
		methodObject = new KpStemmer();
		a_0 = (new Among[] {
			new Among("nde", -1, 7, "", methodObject), new Among("en", -1, 6, "", methodObject), new Among("s", -1, 2, "", methodObject), new Among("'s", 2, 1, "", methodObject), new Among("es", 2, 4, "", methodObject), new Among("ies", 4, 3, "", methodObject), new Among("aus", 2, 5, "", methodObject)
		});
		a_1 = (new Among[] {
			new Among("de", -1, 5, "", methodObject), new Among("ge", -1, 2, "", methodObject), new Among("ische", -1, 4, "", methodObject), new Among("je", -1, 1, "", methodObject), new Among("lijke", -1, 3, "", methodObject), new Among("le", -1, 9, "", methodObject), new Among("ene", -1, 10, "", methodObject), new Among("re", -1, 8, "", methodObject), new Among("se", -1, 7, "", methodObject), new Among("te", -1, 6, "", methodObject), 
			new Among("ieve", -1, 11, "", methodObject)
		});
		a_2 = (new Among[] {
			new Among("heid", -1, 3, "", methodObject), new Among("fie", -1, 7, "", methodObject), new Among("gie", -1, 8, "", methodObject), new Among("atie", -1, 1, "", methodObject), new Among("isme", -1, 5, "", methodObject), new Among("ing", -1, 5, "", methodObject), new Among("arij", -1, 6, "", methodObject), new Among("erij", -1, 5, "", methodObject), new Among("sel", -1, 3, "", methodObject), new Among("rder", -1, 4, "", methodObject), 
			new Among("ster", -1, 3, "", methodObject), new Among("iteit", -1, 2, "", methodObject), new Among("dst", -1, 10, "", methodObject), new Among("tst", -1, 9, "", methodObject)
		});
		a_3 = (new Among[] {
			new Among("end", -1, 10, "", methodObject), new Among("atief", -1, 2, "", methodObject), new Among("erig", -1, 10, "", methodObject), new Among("achtig", -1, 9, "", methodObject), new Among("ioneel", -1, 1, "", methodObject), new Among("baar", -1, 3, "", methodObject), new Among("laar", -1, 5, "", methodObject), new Among("naar", -1, 4, "", methodObject), new Among("raar", -1, 6, "", methodObject), new Among("eriger", -1, 10, "", methodObject), 
			new Among("achtiger", -1, 9, "", methodObject), new Among("lijker", -1, 8, "", methodObject), new Among("tant", -1, 7, "", methodObject), new Among("erigst", -1, 10, "", methodObject), new Among("achtigst", -1, 9, "", methodObject), new Among("lijkst", -1, 8, "", methodObject)
		});
		a_4 = (new Among[] {
			new Among("ig", -1, 1, "", methodObject), new Among("iger", -1, 1, "", methodObject), new Among("igst", -1, 1, "", methodObject)
		});
		a_5 = (new Among[] {
			new Among("ft", -1, 2, "", methodObject), new Among("kt", -1, 1, "", methodObject), new Among("pt", -1, 3, "", methodObject)
		});
		a_6 = (new Among[] {
			new Among("bb", -1, 1, "", methodObject), new Among("cc", -1, 2, "", methodObject), new Among("dd", -1, 3, "", methodObject), new Among("ff", -1, 4, "", methodObject), new Among("gg", -1, 5, "", methodObject), new Among("hh", -1, 6, "", methodObject), new Among("jj", -1, 7, "", methodObject), new Among("kk", -1, 8, "", methodObject), new Among("ll", -1, 9, "", methodObject), new Among("mm", -1, 10, "", methodObject), 
			new Among("nn", -1, 11, "", methodObject), new Among("pp", -1, 12, "", methodObject), new Among("qq", -1, 13, "", methodObject), new Among("rr", -1, 14, "", methodObject), new Among("ss", -1, 15, "", methodObject), new Among("tt", -1, 16, "", methodObject), new Among("v", -1, 21, "", methodObject), new Among("vv", 16, 17, "", methodObject), new Among("ww", -1, 18, "", methodObject), new Among("xx", -1, 19, "", methodObject), 
			new Among("z", -1, 22, "", methodObject), new Among("zz", 20, 20, "", methodObject)
		});
		a_7 = (new Among[] {
			new Among("d", -1, 1, "", methodObject), new Among("t", -1, 2, "", methodObject)
		});
	}
}
