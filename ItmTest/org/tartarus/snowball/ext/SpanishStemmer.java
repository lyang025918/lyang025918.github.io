// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SpanishStemmer.java

package org.tartarus.snowball.ext;

import org.tartarus.snowball.Among;
import org.tartarus.snowball.SnowballProgram;

public class SpanishStemmer extends SnowballProgram
{

	private static final long serialVersionUID = 1L;
	private static final SpanishStemmer methodObject;
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
	private static final char g_v[] = {
		'\021', 'A', '\020', '\0', '\0', '\0', '\0', '\0', '\0', '\0', 
		'\0', '\0', '\0', '\0', '\0', '\0', '\001', '\021', '\004', '\n'
	};
	private int I_p2;
	private int I_p1;
	private int I_pV;

	public SpanishStemmer()
	{
	}

	private void copy_from(SpanishStemmer other)
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
label1:
			{
				I_pV = limit;
				I_p1 = limit;
				I_p2 = limit;
				v_1 = cursor;
				int v_2 = cursor;
				if (in_grouping(g_v, 97, 252))
				{
					int v_3 = cursor;
					if (out_grouping(g_v, 97, 252))
						do
						{
							if (in_grouping(g_v, 97, 252))
								break label1;
							if (cursor >= limit)
								break;
							cursor++;
						} while (true);
					cursor = v_3;
					if (in_grouping(g_v, 97, 252))
						do
						{
							if (out_grouping(g_v, 97, 252))
								break label1;
							if (cursor >= limit)
								break;
							cursor++;
						} while (true);
				}
				cursor = v_2;
				if (!out_grouping(g_v, 97, 252))
					break label0;
				int v_6 = cursor;
				if (out_grouping(g_v, 97, 252))
					do
					{
						if (in_grouping(g_v, 97, 252))
							break label1;
						if (cursor >= limit)
							break;
						cursor++;
					} while (true);
				cursor = v_6;
				if (!in_grouping(g_v, 97, 252) || cursor >= limit)
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
			while (!in_grouping(g_v, 97, 252)) 
			{
				if (cursor >= limit)
					break label2;
				cursor++;
			}
			while (!out_grouping(g_v, 97, 252)) 
			{
				if (cursor >= limit)
					break label2;
				cursor++;
			}
			I_p1 = cursor;
			while (!in_grouping(g_v, 97, 252)) 
			{
				if (cursor >= limit)
					break label2;
				cursor++;
			}
			while (!out_grouping(g_v, 97, 252)) 
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
				slice_from("a");
				break;

			case 2: // '\002'
				slice_from("e");
				break;

			case 3: // '\003'
				slice_from("i");
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

	private boolean r_attached_pronoun()
	{
		ket = cursor;
		if (find_among_b(a_1, 13) == 0)
			return false;
		bra = cursor;
		int among_var = find_among_b(a_2, 11);
		if (among_var == 0)
			return false;
		if (!r_RV())
			return false;
		switch (among_var)
		{
		default:
			break;

		case 0: // '\0'
			return false;

		case 1: // '\001'
			bra = cursor;
			slice_from("iendo");
			break;

		case 2: // '\002'
			bra = cursor;
			slice_from("ando");
			break;

		case 3: // '\003'
			bra = cursor;
			slice_from("ar");
			break;

		case 4: // '\004'
			bra = cursor;
			slice_from("er");
			break;

		case 5: // '\005'
			bra = cursor;
			slice_from("ir");
			break;

		case 6: // '\006'
			slice_del();
			break;

		case 7: // '\007'
			if (!eq_s_b(1, "u"))
				return false;
			slice_del();
			break;
		}
		return true;
	}

	private boolean r_standard_suffix()
	{
		ket = cursor;
		int among_var = find_among_b(a_6, 46);
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
			if (!r_R2())
				cursor = limit - v_1;
			else
				slice_del();
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
			slice_from("ente");
			break;

		case 6: // '\006'
			if (!r_R1())
				return false;
			slice_del();
			int v_2 = limit - cursor;
			ket = cursor;
			among_var = find_among_b(a_3, 4);
			if (among_var == 0)
			{
				cursor = limit - v_2;
				break;
			}
			bra = cursor;
			if (!r_R2())
			{
				cursor = limit - v_2;
				break;
			}
			slice_del();
			switch (among_var)
			{
			default:
				break;

			case 0: // '\0'
				cursor = limit - v_2;
				break label0;

			case 1: // '\001'
				ket = cursor;
				if (!eq_s_b(2, "at"))
				{
					cursor = limit - v_2;
					break label0;
				}
				bra = cursor;
				if (!r_R2())
					cursor = limit - v_2;
				else
					slice_del();
				break;
			}
			break;

		case 7: // '\007'
			if (!r_R2())
				return false;
			slice_del();
			int v_3 = limit - cursor;
			ket = cursor;
			among_var = find_among_b(a_4, 3);
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
					cursor = limit - v_3;
				else
					slice_del();
				break;
			}
			break;

		case 8: // '\b'
			if (!r_R2())
				return false;
			slice_del();
			int v_4 = limit - cursor;
			ket = cursor;
			among_var = find_among_b(a_5, 3);
			if (among_var == 0)
			{
				cursor = limit - v_4;
				break;
			}
			bra = cursor;
			switch (among_var)
			{
			default:
				break;

			case 0: // '\0'
				cursor = limit - v_4;
				break label0;

			case 1: // '\001'
				if (!r_R2())
					cursor = limit - v_4;
				else
					slice_del();
				break;
			}
			break;

		case 9: // '\t'
			if (!r_R2())
				return false;
			slice_del();
			int v_5 = limit - cursor;
			ket = cursor;
			if (!eq_s_b(2, "at"))
			{
				cursor = limit - v_5;
				break;
			}
			bra = cursor;
			if (!r_R2())
				cursor = limit - v_5;
			else
				slice_del();
			break;
		}
		return true;
	}

	private boolean r_y_verb_suffix()
	{
		int v_1 = limit - cursor;
		if (cursor < I_pV)
			return false;
		cursor = I_pV;
		int v_2 = limit_backward;
		limit_backward = cursor;
		cursor = limit - v_1;
		ket = cursor;
		int among_var = find_among_b(a_7, 12);
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
			if (!eq_s_b(1, "u"))
				return false;
			slice_del();
			break;
		}
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
		int among_var = find_among_b(a_8, 96);
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
			if (!eq_s_b(1, "u"))
			{
				cursor = limit - v_3;
			} else
			{
				int v_4 = limit - cursor;
				if (!eq_s_b(1, "g"))
					cursor = limit - v_3;
				else
					cursor = limit - v_4;
			}
			bra = cursor;
			slice_del();
			break;

		case 2: // '\002'
			slice_del();
			break;
		}
		return true;
	}

	private boolean r_residual_suffix()
	{
		ket = cursor;
		int among_var = find_among_b(a_9, 8);
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
			if (!r_RV())
				return false;
			slice_del();
			int v_1 = limit - cursor;
			ket = cursor;
			if (!eq_s_b(1, "u"))
			{
				cursor = limit - v_1;
				break;
			}
			bra = cursor;
			int v_2 = limit - cursor;
			if (!eq_s_b(1, "g"))
			{
				cursor = limit - v_1;
				break;
			}
			cursor = limit - v_2;
			if (!r_RV())
				cursor = limit - v_1;
			else
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
		if (r_attached_pronoun());
		cursor = limit - v_2;
		int v_3 = limit - cursor;
		int v_4 = limit - cursor;
		if (!r_standard_suffix())
		{
			cursor = limit - v_4;
			if (!r_y_verb_suffix())
			{
				cursor = limit - v_4;
				if (r_verb_suffix());
			}
		}
		cursor = limit - v_3;
		int v_5 = limit - cursor;
		if (r_residual_suffix());
		cursor = limit - v_5;
		cursor = limit_backward;
		int v_6 = cursor;
		if (r_postlude());
		cursor = v_6;
		return true;
	}

	public boolean equals(Object o)
	{
		return o instanceof SpanishStemmer;
	}

	public int hashCode()
	{
		return org/tartarus/snowball/ext/SpanishStemmer.getName().hashCode();
	}

	static 
	{
		methodObject = new SpanishStemmer();
		a_0 = (new Among[] {
			new Among("", -1, 6, "", methodObject), new Among("\341", 0, 1, "", methodObject), new Among("\351", 0, 2, "", methodObject), new Among("\355", 0, 3, "", methodObject), new Among("\363", 0, 4, "", methodObject), new Among("\372", 0, 5, "", methodObject)
		});
		a_1 = (new Among[] {
			new Among("la", -1, -1, "", methodObject), new Among("sela", 0, -1, "", methodObject), new Among("le", -1, -1, "", methodObject), new Among("me", -1, -1, "", methodObject), new Among("se", -1, -1, "", methodObject), new Among("lo", -1, -1, "", methodObject), new Among("selo", 5, -1, "", methodObject), new Among("las", -1, -1, "", methodObject), new Among("selas", 7, -1, "", methodObject), new Among("les", -1, -1, "", methodObject), 
			new Among("los", -1, -1, "", methodObject), new Among("selos", 10, -1, "", methodObject), new Among("nos", -1, -1, "", methodObject)
		});
		a_2 = (new Among[] {
			new Among("ando", -1, 6, "", methodObject), new Among("iendo", -1, 6, "", methodObject), new Among("yendo", -1, 7, "", methodObject), new Among("\341ndo", -1, 2, "", methodObject), new Among("i\351ndo", -1, 1, "", methodObject), new Among("ar", -1, 6, "", methodObject), new Among("er", -1, 6, "", methodObject), new Among("ir", -1, 6, "", methodObject), new Among("\341r", -1, 3, "", methodObject), new Among("\351r", -1, 4, "", methodObject), 
			new Among("\355r", -1, 5, "", methodObject)
		});
		a_3 = (new Among[] {
			new Among("ic", -1, -1, "", methodObject), new Among("ad", -1, -1, "", methodObject), new Among("os", -1, -1, "", methodObject), new Among("iv", -1, 1, "", methodObject)
		});
		a_4 = (new Among[] {
			new Among("able", -1, 1, "", methodObject), new Among("ible", -1, 1, "", methodObject), new Among("ante", -1, 1, "", methodObject)
		});
		a_5 = (new Among[] {
			new Among("ic", -1, 1, "", methodObject), new Among("abil", -1, 1, "", methodObject), new Among("iv", -1, 1, "", methodObject)
		});
		a_6 = (new Among[] {
			new Among("ica", -1, 1, "", methodObject), new Among("ancia", -1, 2, "", methodObject), new Among("encia", -1, 5, "", methodObject), new Among("adora", -1, 2, "", methodObject), new Among("osa", -1, 1, "", methodObject), new Among("ista", -1, 1, "", methodObject), new Among("iva", -1, 9, "", methodObject), new Among("anza", -1, 1, "", methodObject), new Among("log\355a", -1, 3, "", methodObject), new Among("idad", -1, 8, "", methodObject), 
			new Among("able", -1, 1, "", methodObject), new Among("ible", -1, 1, "", methodObject), new Among("ante", -1, 2, "", methodObject), new Among("mente", -1, 7, "", methodObject), new Among("amente", 13, 6, "", methodObject), new Among("aci\363n", -1, 2, "", methodObject), new Among("uci\363n", -1, 4, "", methodObject), new Among("ico", -1, 1, "", methodObject), new Among("ismo", -1, 1, "", methodObject), new Among("oso", -1, 1, "", methodObject), 
			new Among("amiento", -1, 1, "", methodObject), new Among("imiento", -1, 1, "", methodObject), new Among("ivo", -1, 9, "", methodObject), new Among("ador", -1, 2, "", methodObject), new Among("icas", -1, 1, "", methodObject), new Among("ancias", -1, 2, "", methodObject), new Among("encias", -1, 5, "", methodObject), new Among("adoras", -1, 2, "", methodObject), new Among("osas", -1, 1, "", methodObject), new Among("istas", -1, 1, "", methodObject), 
			new Among("ivas", -1, 9, "", methodObject), new Among("anzas", -1, 1, "", methodObject), new Among("log\355as", -1, 3, "", methodObject), new Among("idades", -1, 8, "", methodObject), new Among("ables", -1, 1, "", methodObject), new Among("ibles", -1, 1, "", methodObject), new Among("aciones", -1, 2, "", methodObject), new Among("uciones", -1, 4, "", methodObject), new Among("adores", -1, 2, "", methodObject), new Among("antes", -1, 2, "", methodObject), 
			new Among("icos", -1, 1, "", methodObject), new Among("ismos", -1, 1, "", methodObject), new Among("osos", -1, 1, "", methodObject), new Among("amientos", -1, 1, "", methodObject), new Among("imientos", -1, 1, "", methodObject), new Among("ivos", -1, 9, "", methodObject)
		});
		a_7 = (new Among[] {
			new Among("ya", -1, 1, "", methodObject), new Among("ye", -1, 1, "", methodObject), new Among("yan", -1, 1, "", methodObject), new Among("yen", -1, 1, "", methodObject), new Among("yeron", -1, 1, "", methodObject), new Among("yendo", -1, 1, "", methodObject), new Among("yo", -1, 1, "", methodObject), new Among("yas", -1, 1, "", methodObject), new Among("yes", -1, 1, "", methodObject), new Among("yais", -1, 1, "", methodObject), 
			new Among("yamos", -1, 1, "", methodObject), new Among("y\363", -1, 1, "", methodObject)
		});
		a_8 = (new Among[] {
			new Among("aba", -1, 2, "", methodObject), new Among("ada", -1, 2, "", methodObject), new Among("ida", -1, 2, "", methodObject), new Among("ara", -1, 2, "", methodObject), new Among("iera", -1, 2, "", methodObject), new Among("\355a", -1, 2, "", methodObject), new Among("ar\355a", 5, 2, "", methodObject), new Among("er\355a", 5, 2, "", methodObject), new Among("ir\355a", 5, 2, "", methodObject), new Among("ad", -1, 2, "", methodObject), 
			new Among("ed", -1, 2, "", methodObject), new Among("id", -1, 2, "", methodObject), new Among("ase", -1, 2, "", methodObject), new Among("iese", -1, 2, "", methodObject), new Among("aste", -1, 2, "", methodObject), new Among("iste", -1, 2, "", methodObject), new Among("an", -1, 2, "", methodObject), new Among("aban", 16, 2, "", methodObject), new Among("aran", 16, 2, "", methodObject), new Among("ieran", 16, 2, "", methodObject), 
			new Among("\355an", 16, 2, "", methodObject), new Among("ar\355an", 20, 2, "", methodObject), new Among("er\355an", 20, 2, "", methodObject), new Among("ir\355an", 20, 2, "", methodObject), new Among("en", -1, 1, "", methodObject), new Among("asen", 24, 2, "", methodObject), new Among("iesen", 24, 2, "", methodObject), new Among("aron", -1, 2, "", methodObject), new Among("ieron", -1, 2, "", methodObject), new Among("ar\341n", -1, 2, "", methodObject), 
			new Among("er\341n", -1, 2, "", methodObject), new Among("ir\341n", -1, 2, "", methodObject), new Among("ado", -1, 2, "", methodObject), new Among("ido", -1, 2, "", methodObject), new Among("ando", -1, 2, "", methodObject), new Among("iendo", -1, 2, "", methodObject), new Among("ar", -1, 2, "", methodObject), new Among("er", -1, 2, "", methodObject), new Among("ir", -1, 2, "", methodObject), new Among("as", -1, 2, "", methodObject), 
			new Among("abas", 39, 2, "", methodObject), new Among("adas", 39, 2, "", methodObject), new Among("idas", 39, 2, "", methodObject), new Among("aras", 39, 2, "", methodObject), new Among("ieras", 39, 2, "", methodObject), new Among("\355as", 39, 2, "", methodObject), new Among("ar\355as", 45, 2, "", methodObject), new Among("er\355as", 45, 2, "", methodObject), new Among("ir\355as", 45, 2, "", methodObject), new Among("es", -1, 1, "", methodObject), 
			new Among("ases", 49, 2, "", methodObject), new Among("ieses", 49, 2, "", methodObject), new Among("abais", -1, 2, "", methodObject), new Among("arais", -1, 2, "", methodObject), new Among("ierais", -1, 2, "", methodObject), new Among("\355ais", -1, 2, "", methodObject), new Among("ar\355ais", 55, 2, "", methodObject), new Among("er\355ais", 55, 2, "", methodObject), new Among("ir\355ais", 55, 2, "", methodObject), new Among("aseis", -1, 2, "", methodObject), 
			new Among("ieseis", -1, 2, "", methodObject), new Among("asteis", -1, 2, "", methodObject), new Among("isteis", -1, 2, "", methodObject), new Among("\341is", -1, 2, "", methodObject), new Among("\351is", -1, 1, "", methodObject), new Among("ar\351is", 64, 2, "", methodObject), new Among("er\351is", 64, 2, "", methodObject), new Among("ir\351is", 64, 2, "", methodObject), new Among("ados", -1, 2, "", methodObject), new Among("idos", -1, 2, "", methodObject), 
			new Among("amos", -1, 2, "", methodObject), new Among("\341bamos", 70, 2, "", methodObject), new Among("\341ramos", 70, 2, "", methodObject), new Among("i\351ramos", 70, 2, "", methodObject), new Among("\355amos", 70, 2, "", methodObject), new Among("ar\355amos", 74, 2, "", methodObject), new Among("er\355amos", 74, 2, "", methodObject), new Among("ir\355amos", 74, 2, "", methodObject), new Among("emos", -1, 1, "", methodObject), new Among("aremos", 78, 2, "", methodObject), 
			new Among("eremos", 78, 2, "", methodObject), new Among("iremos", 78, 2, "", methodObject), new Among("\341semos", 78, 2, "", methodObject), new Among("i\351semos", 78, 2, "", methodObject), new Among("imos", -1, 2, "", methodObject), new Among("ar\341s", -1, 2, "", methodObject), new Among("er\341s", -1, 2, "", methodObject), new Among("ir\341s", -1, 2, "", methodObject), new Among("\355s", -1, 2, "", methodObject), new Among("ar\341", -1, 2, "", methodObject), 
			new Among("er\341", -1, 2, "", methodObject), new Among("ir\341", -1, 2, "", methodObject), new Among("ar\351", -1, 2, "", methodObject), new Among("er\351", -1, 2, "", methodObject), new Among("ir\351", -1, 2, "", methodObject), new Among("i\363", -1, 2, "", methodObject)
		});
		a_9 = (new Among[] {
			new Among("a", -1, 1, "", methodObject), new Among("e", -1, 2, "", methodObject), new Among("o", -1, 1, "", methodObject), new Among("os", -1, 1, "", methodObject), new Among("\341", -1, 1, "", methodObject), new Among("\351", -1, 2, "", methodObject), new Among("\355", -1, 1, "", methodObject), new Among("\363", -1, 1, "", methodObject)
		});
	}
}
