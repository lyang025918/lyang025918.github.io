// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ItalianStemmer.java

package org.tartarus.snowball.ext;

import org.tartarus.snowball.Among;
import org.tartarus.snowball.SnowballProgram;

public class ItalianStemmer extends SnowballProgram
{

	private static final long serialVersionUID = 1L;
	private static final ItalianStemmer methodObject;
	private static final Among a_0[];
	private static final Among a_1[];
	private static final Among a_2[];
	private static final Among a_3[];
	private static final Among a_4[];
	private static final Among a_5[];
	private static final Among a_6[];
	private static final Among a_7[];
	private static final char g_v[] = {
		'\021', 'A', '\020', '\0', '\0', '\0', '\0', '\0', '\0', '\0', 
		'\0', '\0', '\0', '\0', '\0', '\200', '\200', '\b', '\002', '\001'
	};
	private static final char g_AEIO[] = {
		'\021', 'A', '\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0', 
		'\0', '\0', '\0', '\0', '\0', '\200', '\200', '\b', '\002'
	};
	private static final char g_CG[] = {
		'\021'
	};
	private int I_p2;
	private int I_p1;
	private int I_pV;

	public ItalianStemmer()
	{
	}

	private void copy_from(ItalianStemmer other)
	{
		I_p2 = other.I_p2;
		I_p1 = other.I_p1;
		I_pV = other.I_pV;
		super.copy_from(other);
	}

	private boolean r_prelude()
	{
		int v_1 = cursor;
		int v_2;
label0:
		do
		{
			v_2 = cursor;
			bra = cursor;
			int among_var = find_among(a_0, 7);
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
				slice_from("\340");
				break;

			case 2: // '\002'
				slice_from("\350");
				break;

			case 3: // '\003'
				slice_from("\354");
				break;

			case 4: // '\004'
				slice_from("\362");
				break;

			case 5: // '\005'
				slice_from("\371");
				break;

			case 6: // '\006'
				slice_from("qU");
				break;

			case 7: // '\007'
				if (cursor >= limit)
					break label0;
				cursor++;
				break;
			}
		} while (true);
		cursor = v_2;
		cursor = v_1;
label1:
		do
		{
			int v_3 = cursor;
			do
			{
				int v_4;
label2:
				{
label3:
					{
						v_4 = cursor;
						if (!in_grouping(g_v, 97, 249))
							break label2;
						bra = cursor;
						int v_5 = cursor;
						if (eq_s(1, "u"))
						{
							ket = cursor;
							if (in_grouping(g_v, 97, 249))
							{
								slice_from("U");
								break label3;
							}
						}
						cursor = v_5;
						if (!eq_s(1, "i"))
							break label2;
						ket = cursor;
						if (!in_grouping(g_v, 97, 249))
							break label2;
						slice_from("I");
					}
					cursor = v_4;
					continue label1;
				}
				cursor = v_4;
				if (cursor >= limit)
					break;
				cursor++;
			} while (true);
			cursor = v_3;
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
				if (in_grouping(g_v, 97, 249))
				{
					int v_3 = cursor;
					if (out_grouping(g_v, 97, 249))
						do
						{
							if (in_grouping(g_v, 97, 249))
								break label1;
							if (cursor >= limit)
								break;
							cursor++;
						} while (true);
					cursor = v_3;
					if (in_grouping(g_v, 97, 249))
						do
						{
							if (out_grouping(g_v, 97, 249))
								break label1;
							if (cursor >= limit)
								break;
							cursor++;
						} while (true);
				}
				cursor = v_2;
				if (!out_grouping(g_v, 97, 249))
					break label0;
				int v_6 = cursor;
				if (out_grouping(g_v, 97, 249))
					do
					{
						if (in_grouping(g_v, 97, 249))
							break label1;
						if (cursor >= limit)
							break;
						cursor++;
					} while (true);
				cursor = v_6;
				if (!in_grouping(g_v, 97, 249) || cursor >= limit)
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
			while (!in_grouping(g_v, 97, 249)) 
			{
				if (cursor >= limit)
					break label2;
				cursor++;
			}
			while (!out_grouping(g_v, 97, 249)) 
			{
				if (cursor >= limit)
					break label2;
				cursor++;
			}
			I_p1 = cursor;
			while (!in_grouping(g_v, 97, 249)) 
			{
				if (cursor >= limit)
					break label2;
				cursor++;
			}
			while (!out_grouping(g_v, 97, 249)) 
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
			int among_var = find_among(a_1, 3);
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

	private boolean r_attached_pronoun()
	{
		ket = cursor;
		if (find_among_b(a_2, 37) == 0)
			return false;
		bra = cursor;
		int among_var = find_among_b(a_3, 5);
		if (among_var == 0)
			return false;
		if (!r_RV())
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
		}
		return true;
	}

	private boolean r_standard_suffix()
	{
		ket = cursor;
		int among_var = find_among_b(a_6, 51);
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
			if (!r_RV())
				return false;
			slice_del();
			break;

		case 7: // '\007'
			if (!r_R1())
				return false;
			slice_del();
			int v_2 = limit - cursor;
			ket = cursor;
			among_var = find_among_b(a_4, 4);
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

		case 8: // '\b'
			if (!r_R2())
				return false;
			slice_del();
			int v_3 = limit - cursor;
			ket = cursor;
			among_var = find_among_b(a_5, 3);
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

		case 9: // '\t'
			if (!r_R2())
				return false;
			slice_del();
			int v_4 = limit - cursor;
			ket = cursor;
			if (!eq_s_b(2, "at"))
			{
				cursor = limit - v_4;
				break;
			}
			bra = cursor;
			if (!r_R2())
			{
				cursor = limit - v_4;
				break;
			}
			slice_del();
			ket = cursor;
			if (!eq_s_b(2, "ic"))
			{
				cursor = limit - v_4;
				break;
			}
			bra = cursor;
			if (!r_R2())
				cursor = limit - v_4;
			else
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
		int among_var = find_among_b(a_7, 87);
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
		}
		limit_backward = v_2;
		return true;
	}

	private boolean r_vowel_suffix()
	{
		int v_1 = limit - cursor;
		ket = cursor;
		if (!in_grouping_b(g_AEIO, 97, 242))
		{
			cursor = limit - v_1;
		} else
		{
			bra = cursor;
			if (!r_RV())
			{
				cursor = limit - v_1;
			} else
			{
				slice_del();
				ket = cursor;
				if (!eq_s_b(1, "i"))
				{
					cursor = limit - v_1;
				} else
				{
					bra = cursor;
					if (!r_RV())
						cursor = limit - v_1;
					else
						slice_del();
				}
			}
		}
		int v_2 = limit - cursor;
		ket = cursor;
		if (!eq_s_b(1, "h"))
		{
			cursor = limit - v_2;
		} else
		{
			bra = cursor;
			if (!in_grouping_b(g_CG, 99, 103))
				cursor = limit - v_2;
			else
			if (!r_RV())
				cursor = limit - v_2;
			else
				slice_del();
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
		if (r_attached_pronoun());
		cursor = limit - v_3;
		int v_4 = limit - cursor;
		int v_5 = limit - cursor;
		if (!r_standard_suffix())
		{
			cursor = limit - v_5;
			if (r_verb_suffix());
		}
		cursor = limit - v_4;
		int v_6 = limit - cursor;
		if (r_vowel_suffix());
		cursor = limit - v_6;
		cursor = limit_backward;
		int v_7 = cursor;
		if (r_postlude());
		cursor = v_7;
		return true;
	}

	public boolean equals(Object o)
	{
		return o instanceof ItalianStemmer;
	}

	public int hashCode()
	{
		return org/tartarus/snowball/ext/ItalianStemmer.getName().hashCode();
	}

	static 
	{
		methodObject = new ItalianStemmer();
		a_0 = (new Among[] {
			new Among("", -1, 7, "", methodObject), new Among("qu", 0, 6, "", methodObject), new Among("\341", 0, 1, "", methodObject), new Among("\351", 0, 2, "", methodObject), new Among("\355", 0, 3, "", methodObject), new Among("\363", 0, 4, "", methodObject), new Among("\372", 0, 5, "", methodObject)
		});
		a_1 = (new Among[] {
			new Among("", -1, 3, "", methodObject), new Among("I", 0, 1, "", methodObject), new Among("U", 0, 2, "", methodObject)
		});
		a_2 = (new Among[] {
			new Among("la", -1, -1, "", methodObject), new Among("cela", 0, -1, "", methodObject), new Among("gliela", 0, -1, "", methodObject), new Among("mela", 0, -1, "", methodObject), new Among("tela", 0, -1, "", methodObject), new Among("vela", 0, -1, "", methodObject), new Among("le", -1, -1, "", methodObject), new Among("cele", 6, -1, "", methodObject), new Among("gliele", 6, -1, "", methodObject), new Among("mele", 6, -1, "", methodObject), 
			new Among("tele", 6, -1, "", methodObject), new Among("vele", 6, -1, "", methodObject), new Among("ne", -1, -1, "", methodObject), new Among("cene", 12, -1, "", methodObject), new Among("gliene", 12, -1, "", methodObject), new Among("mene", 12, -1, "", methodObject), new Among("sene", 12, -1, "", methodObject), new Among("tene", 12, -1, "", methodObject), new Among("vene", 12, -1, "", methodObject), new Among("ci", -1, -1, "", methodObject), 
			new Among("li", -1, -1, "", methodObject), new Among("celi", 20, -1, "", methodObject), new Among("glieli", 20, -1, "", methodObject), new Among("meli", 20, -1, "", methodObject), new Among("teli", 20, -1, "", methodObject), new Among("veli", 20, -1, "", methodObject), new Among("gli", 20, -1, "", methodObject), new Among("mi", -1, -1, "", methodObject), new Among("si", -1, -1, "", methodObject), new Among("ti", -1, -1, "", methodObject), 
			new Among("vi", -1, -1, "", methodObject), new Among("lo", -1, -1, "", methodObject), new Among("celo", 31, -1, "", methodObject), new Among("glielo", 31, -1, "", methodObject), new Among("melo", 31, -1, "", methodObject), new Among("telo", 31, -1, "", methodObject), new Among("velo", 31, -1, "", methodObject)
		});
		a_3 = (new Among[] {
			new Among("ando", -1, 1, "", methodObject), new Among("endo", -1, 1, "", methodObject), new Among("ar", -1, 2, "", methodObject), new Among("er", -1, 2, "", methodObject), new Among("ir", -1, 2, "", methodObject)
		});
		a_4 = (new Among[] {
			new Among("ic", -1, -1, "", methodObject), new Among("abil", -1, -1, "", methodObject), new Among("os", -1, -1, "", methodObject), new Among("iv", -1, 1, "", methodObject)
		});
		a_5 = (new Among[] {
			new Among("ic", -1, 1, "", methodObject), new Among("abil", -1, 1, "", methodObject), new Among("iv", -1, 1, "", methodObject)
		});
		a_6 = (new Among[] {
			new Among("ica", -1, 1, "", methodObject), new Among("logia", -1, 3, "", methodObject), new Among("osa", -1, 1, "", methodObject), new Among("ista", -1, 1, "", methodObject), new Among("iva", -1, 9, "", methodObject), new Among("anza", -1, 1, "", methodObject), new Among("enza", -1, 5, "", methodObject), new Among("ice", -1, 1, "", methodObject), new Among("atrice", 7, 1, "", methodObject), new Among("iche", -1, 1, "", methodObject), 
			new Among("logie", -1, 3, "", methodObject), new Among("abile", -1, 1, "", methodObject), new Among("ibile", -1, 1, "", methodObject), new Among("usione", -1, 4, "", methodObject), new Among("azione", -1, 2, "", methodObject), new Among("uzione", -1, 4, "", methodObject), new Among("atore", -1, 2, "", methodObject), new Among("ose", -1, 1, "", methodObject), new Among("ante", -1, 1, "", methodObject), new Among("mente", -1, 1, "", methodObject), 
			new Among("amente", 19, 7, "", methodObject), new Among("iste", -1, 1, "", methodObject), new Among("ive", -1, 9, "", methodObject), new Among("anze", -1, 1, "", methodObject), new Among("enze", -1, 5, "", methodObject), new Among("ici", -1, 1, "", methodObject), new Among("atrici", 25, 1, "", methodObject), new Among("ichi", -1, 1, "", methodObject), new Among("abili", -1, 1, "", methodObject), new Among("ibili", -1, 1, "", methodObject), 
			new Among("ismi", -1, 1, "", methodObject), new Among("usioni", -1, 4, "", methodObject), new Among("azioni", -1, 2, "", methodObject), new Among("uzioni", -1, 4, "", methodObject), new Among("atori", -1, 2, "", methodObject), new Among("osi", -1, 1, "", methodObject), new Among("anti", -1, 1, "", methodObject), new Among("amenti", -1, 6, "", methodObject), new Among("imenti", -1, 6, "", methodObject), new Among("isti", -1, 1, "", methodObject), 
			new Among("ivi", -1, 9, "", methodObject), new Among("ico", -1, 1, "", methodObject), new Among("ismo", -1, 1, "", methodObject), new Among("oso", -1, 1, "", methodObject), new Among("amento", -1, 6, "", methodObject), new Among("imento", -1, 6, "", methodObject), new Among("ivo", -1, 9, "", methodObject), new Among("it\340", -1, 8, "", methodObject), new Among("ist\340", -1, 1, "", methodObject), new Among("ist\350", -1, 1, "", methodObject), 
			new Among("ist\354", -1, 1, "", methodObject)
		});
		a_7 = (new Among[] {
			new Among("isca", -1, 1, "", methodObject), new Among("enda", -1, 1, "", methodObject), new Among("ata", -1, 1, "", methodObject), new Among("ita", -1, 1, "", methodObject), new Among("uta", -1, 1, "", methodObject), new Among("ava", -1, 1, "", methodObject), new Among("eva", -1, 1, "", methodObject), new Among("iva", -1, 1, "", methodObject), new Among("erebbe", -1, 1, "", methodObject), new Among("irebbe", -1, 1, "", methodObject), 
			new Among("isce", -1, 1, "", methodObject), new Among("ende", -1, 1, "", methodObject), new Among("are", -1, 1, "", methodObject), new Among("ere", -1, 1, "", methodObject), new Among("ire", -1, 1, "", methodObject), new Among("asse", -1, 1, "", methodObject), new Among("ate", -1, 1, "", methodObject), new Among("avate", 16, 1, "", methodObject), new Among("evate", 16, 1, "", methodObject), new Among("ivate", 16, 1, "", methodObject), 
			new Among("ete", -1, 1, "", methodObject), new Among("erete", 20, 1, "", methodObject), new Among("irete", 20, 1, "", methodObject), new Among("ite", -1, 1, "", methodObject), new Among("ereste", -1, 1, "", methodObject), new Among("ireste", -1, 1, "", methodObject), new Among("ute", -1, 1, "", methodObject), new Among("erai", -1, 1, "", methodObject), new Among("irai", -1, 1, "", methodObject), new Among("isci", -1, 1, "", methodObject), 
			new Among("endi", -1, 1, "", methodObject), new Among("erei", -1, 1, "", methodObject), new Among("irei", -1, 1, "", methodObject), new Among("assi", -1, 1, "", methodObject), new Among("ati", -1, 1, "", methodObject), new Among("iti", -1, 1, "", methodObject), new Among("eresti", -1, 1, "", methodObject), new Among("iresti", -1, 1, "", methodObject), new Among("uti", -1, 1, "", methodObject), new Among("avi", -1, 1, "", methodObject), 
			new Among("evi", -1, 1, "", methodObject), new Among("ivi", -1, 1, "", methodObject), new Among("isco", -1, 1, "", methodObject), new Among("ando", -1, 1, "", methodObject), new Among("endo", -1, 1, "", methodObject), new Among("Yamo", -1, 1, "", methodObject), new Among("iamo", -1, 1, "", methodObject), new Among("avamo", -1, 1, "", methodObject), new Among("evamo", -1, 1, "", methodObject), new Among("ivamo", -1, 1, "", methodObject), 
			new Among("eremo", -1, 1, "", methodObject), new Among("iremo", -1, 1, "", methodObject), new Among("assimo", -1, 1, "", methodObject), new Among("ammo", -1, 1, "", methodObject), new Among("emmo", -1, 1, "", methodObject), new Among("eremmo", 54, 1, "", methodObject), new Among("iremmo", 54, 1, "", methodObject), new Among("immo", -1, 1, "", methodObject), new Among("ano", -1, 1, "", methodObject), new Among("iscano", 58, 1, "", methodObject), 
			new Among("avano", 58, 1, "", methodObject), new Among("evano", 58, 1, "", methodObject), new Among("ivano", 58, 1, "", methodObject), new Among("eranno", -1, 1, "", methodObject), new Among("iranno", -1, 1, "", methodObject), new Among("ono", -1, 1, "", methodObject), new Among("iscono", 65, 1, "", methodObject), new Among("arono", 65, 1, "", methodObject), new Among("erono", 65, 1, "", methodObject), new Among("irono", 65, 1, "", methodObject), 
			new Among("erebbero", -1, 1, "", methodObject), new Among("irebbero", -1, 1, "", methodObject), new Among("assero", -1, 1, "", methodObject), new Among("essero", -1, 1, "", methodObject), new Among("issero", -1, 1, "", methodObject), new Among("ato", -1, 1, "", methodObject), new Among("ito", -1, 1, "", methodObject), new Among("uto", -1, 1, "", methodObject), new Among("avo", -1, 1, "", methodObject), new Among("evo", -1, 1, "", methodObject), 
			new Among("ivo", -1, 1, "", methodObject), new Among("ar", -1, 1, "", methodObject), new Among("ir", -1, 1, "", methodObject), new Among("er\340", -1, 1, "", methodObject), new Among("ir\340", -1, 1, "", methodObject), new Among("er\362", -1, 1, "", methodObject), new Among("ir\362", -1, 1, "", methodObject)
		});
	}
}
