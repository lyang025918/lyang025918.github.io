// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PortugueseStemmer.java

package org.tartarus.snowball.ext;

import org.tartarus.snowball.Among;
import org.tartarus.snowball.SnowballProgram;

public class PortugueseStemmer extends SnowballProgram
{

	private static final long serialVersionUID = 1L;
	private static final PortugueseStemmer methodObject;
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
		'\021', 'A', '\020', '\0', '\0', '\0', '\0', '\0', '\0', '\0', 
		'\0', '\0', '\0', '\0', '\0', '\0', '\003', '\023', '\f', '\002'
	};
	private int I_p2;
	private int I_p1;
	private int I_pV;

	public PortugueseStemmer()
	{
	}

	private void copy_from(PortugueseStemmer other)
	{
		I_p2 = other.I_p2;
		I_p1 = other.I_p1;
		I_pV = other.I_pV;
		super.copy_from(other);
	}

	private boolean r_prelude()
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
				slice_from("a~");
				break;

			case 2: // '\002'
				slice_from("o~");
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
				if (in_grouping(g_v, 97, 250))
				{
					int v_3 = cursor;
					if (out_grouping(g_v, 97, 250))
						do
						{
							if (in_grouping(g_v, 97, 250))
								break label1;
							if (cursor >= limit)
								break;
							cursor++;
						} while (true);
					cursor = v_3;
					if (in_grouping(g_v, 97, 250))
						do
						{
							if (out_grouping(g_v, 97, 250))
								break label1;
							if (cursor >= limit)
								break;
							cursor++;
						} while (true);
				}
				cursor = v_2;
				if (!out_grouping(g_v, 97, 250))
					break label0;
				int v_6 = cursor;
				if (out_grouping(g_v, 97, 250))
					do
					{
						if (in_grouping(g_v, 97, 250))
							break label1;
						if (cursor >= limit)
							break;
						cursor++;
					} while (true);
				cursor = v_6;
				if (!in_grouping(g_v, 97, 250) || cursor >= limit)
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
			while (!in_grouping(g_v, 97, 250)) 
			{
				if (cursor >= limit)
					break label2;
				cursor++;
			}
			while (!out_grouping(g_v, 97, 250)) 
			{
				if (cursor >= limit)
					break label2;
				cursor++;
			}
			I_p1 = cursor;
			while (!in_grouping(g_v, 97, 250)) 
			{
				if (cursor >= limit)
					break label2;
				cursor++;
			}
			while (!out_grouping(g_v, 97, 250)) 
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
				slice_from("\343");
				break;

			case 2: // '\002'
				slice_from("\365");
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

	private boolean r_standard_suffix()
	{
		ket = cursor;
		int among_var = find_among_b(a_5, 45);
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
			slice_from("log");
			break;

		case 3: // '\003'
			if (!r_R2())
				return false;
			slice_from("u");
			break;

		case 4: // '\004'
			if (!r_R2())
				return false;
			slice_from("ente");
			break;

		case 5: // '\005'
			if (!r_R1())
				return false;
			slice_del();
			int v_1 = limit - cursor;
			ket = cursor;
			among_var = find_among_b(a_2, 4);
			if (among_var == 0)
			{
				cursor = limit - v_1;
				break;
			}
			bra = cursor;
			if (!r_R2())
			{
				cursor = limit - v_1;
				break;
			}
			slice_del();
			switch (among_var)
			{
			default:
				break;

			case 0: // '\0'
				cursor = limit - v_1;
				break label0;

			case 1: // '\001'
				ket = cursor;
				if (!eq_s_b(2, "at"))
				{
					cursor = limit - v_1;
					break label0;
				}
				bra = cursor;
				if (!r_R2())
					cursor = limit - v_1;
				else
					slice_del();
				break;
			}
			break;

		case 6: // '\006'
			if (!r_R2())
				return false;
			slice_del();
			int v_2 = limit - cursor;
			ket = cursor;
			among_var = find_among_b(a_3, 3);
			if (among_var == 0)
			{
				cursor = limit - v_2;
				break;
			}
			bra = cursor;
			switch (among_var)
			{
			default:
				break;

			case 0: // '\0'
				cursor = limit - v_2;
				break label0;

			case 1: // '\001'
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
			if (!eq_s_b(2, "at"))
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

		case 9: // '\t'
			if (!r_RV())
				return false;
			if (!eq_s_b(1, "e"))
				return false;
			slice_from("ir");
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
		int among_var = find_among_b(a_6, 120);
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

	private boolean r_residual_suffix()
	{
		ket = cursor;
		int among_var = find_among_b(a_7, 7);
		if (among_var == 0)
			return false;
		bra = cursor;
		switch (among_var)
		{
		case 0: // '\0'
			return false;

		case 1: // '\001'
			if (!r_RV())
				return false;
			slice_del();
			break;
		}
		return true;
	}

	private boolean r_residual_form()
	{
		int among_var;
		ket = cursor;
		among_var = find_among_b(a_8, 4);
		if (among_var == 0)
			return false;
		bra = cursor;
		among_var;
		JVM INSTR tableswitch 0 2: default 236
	//	               0 60
	//	               1 62
	//	               2 230;
		   goto _L1 _L2 _L3 _L4
_L1:
		break MISSING_BLOCK_LABEL_236;
_L2:
		return false;
_L3:
		int v_1;
		if (!r_RV())
			return false;
		slice_del();
		ket = cursor;
		v_1 = limit - cursor;
		if (eq_s_b(1, "u")) goto _L6; else goto _L5
_L6:
		int v_2;
		bra = cursor;
		v_2 = limit - cursor;
		if (eq_s_b(1, "g")) goto _L7; else goto _L5
_L7:
		cursor = limit - v_2;
		  goto _L8
_L5:
		cursor = limit - v_1;
		if (!eq_s_b(1, "i"))
			return false;
		bra = cursor;
		int v_3 = limit - cursor;
		if (!eq_s_b(1, "c"))
			return false;
		cursor = limit - v_3;
_L8:
		if (!r_RV())
			return false;
		slice_del();
		break MISSING_BLOCK_LABEL_236;
_L4:
		slice_from("c");
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
					if (!r_verb_suffix())
						break label1;
				}
				cursor = limit - v_5;
				int v_7 = limit - cursor;
				ket = cursor;
				if (eq_s_b(1, "i"))
				{
					bra = cursor;
					int v_8 = limit - cursor;
					if (eq_s_b(1, "c"))
					{
						cursor = limit - v_8;
						if (r_RV())
							slice_del();
					}
				}
				cursor = limit - v_7;
				break label0;
			}
			cursor = limit - v_4;
			if (r_residual_suffix());
		}
		cursor = limit - v_3;
		int v_9 = limit - cursor;
		if (r_residual_form());
		cursor = limit - v_9;
		cursor = limit_backward;
		int v_10 = cursor;
		if (r_postlude());
		cursor = v_10;
		return true;
	}

	public boolean equals(Object o)
	{
		return o instanceof PortugueseStemmer;
	}

	public int hashCode()
	{
		return org/tartarus/snowball/ext/PortugueseStemmer.getName().hashCode();
	}

	static 
	{
		methodObject = new PortugueseStemmer();
		a_0 = (new Among[] {
			new Among("", -1, 3, "", methodObject), new Among("\343", 0, 1, "", methodObject), new Among("\365", 0, 2, "", methodObject)
		});
		a_1 = (new Among[] {
			new Among("", -1, 3, "", methodObject), new Among("a~", 0, 1, "", methodObject), new Among("o~", 0, 2, "", methodObject)
		});
		a_2 = (new Among[] {
			new Among("ic", -1, -1, "", methodObject), new Among("ad", -1, -1, "", methodObject), new Among("os", -1, -1, "", methodObject), new Among("iv", -1, 1, "", methodObject)
		});
		a_3 = (new Among[] {
			new Among("ante", -1, 1, "", methodObject), new Among("avel", -1, 1, "", methodObject), new Among("\355vel", -1, 1, "", methodObject)
		});
		a_4 = (new Among[] {
			new Among("ic", -1, 1, "", methodObject), new Among("abil", -1, 1, "", methodObject), new Among("iv", -1, 1, "", methodObject)
		});
		a_5 = (new Among[] {
			new Among("ica", -1, 1, "", methodObject), new Among("\342ncia", -1, 1, "", methodObject), new Among("\352ncia", -1, 4, "", methodObject), new Among("ira", -1, 9, "", methodObject), new Among("adora", -1, 1, "", methodObject), new Among("osa", -1, 1, "", methodObject), new Among("ista", -1, 1, "", methodObject), new Among("iva", -1, 8, "", methodObject), new Among("eza", -1, 1, "", methodObject), new Among("log\355a", -1, 2, "", methodObject), 
			new Among("idade", -1, 7, "", methodObject), new Among("ante", -1, 1, "", methodObject), new Among("mente", -1, 6, "", methodObject), new Among("amente", 12, 5, "", methodObject), new Among("\341vel", -1, 1, "", methodObject), new Among("\355vel", -1, 1, "", methodObject), new Among("uci\363n", -1, 3, "", methodObject), new Among("ico", -1, 1, "", methodObject), new Among("ismo", -1, 1, "", methodObject), new Among("oso", -1, 1, "", methodObject), 
			new Among("amento", -1, 1, "", methodObject), new Among("imento", -1, 1, "", methodObject), new Among("ivo", -1, 8, "", methodObject), new Among("a\347a~o", -1, 1, "", methodObject), new Among("ador", -1, 1, "", methodObject), new Among("icas", -1, 1, "", methodObject), new Among("\352ncias", -1, 4, "", methodObject), new Among("iras", -1, 9, "", methodObject), new Among("adoras", -1, 1, "", methodObject), new Among("osas", -1, 1, "", methodObject), 
			new Among("istas", -1, 1, "", methodObject), new Among("ivas", -1, 8, "", methodObject), new Among("ezas", -1, 1, "", methodObject), new Among("log\355as", -1, 2, "", methodObject), new Among("idades", -1, 7, "", methodObject), new Among("uciones", -1, 3, "", methodObject), new Among("adores", -1, 1, "", methodObject), new Among("antes", -1, 1, "", methodObject), new Among("a\347o~es", -1, 1, "", methodObject), new Among("icos", -1, 1, "", methodObject), 
			new Among("ismos", -1, 1, "", methodObject), new Among("osos", -1, 1, "", methodObject), new Among("amentos", -1, 1, "", methodObject), new Among("imentos", -1, 1, "", methodObject), new Among("ivos", -1, 8, "", methodObject)
		});
		a_6 = (new Among[] {
			new Among("ada", -1, 1, "", methodObject), new Among("ida", -1, 1, "", methodObject), new Among("ia", -1, 1, "", methodObject), new Among("aria", 2, 1, "", methodObject), new Among("eria", 2, 1, "", methodObject), new Among("iria", 2, 1, "", methodObject), new Among("ara", -1, 1, "", methodObject), new Among("era", -1, 1, "", methodObject), new Among("ira", -1, 1, "", methodObject), new Among("ava", -1, 1, "", methodObject), 
			new Among("asse", -1, 1, "", methodObject), new Among("esse", -1, 1, "", methodObject), new Among("isse", -1, 1, "", methodObject), new Among("aste", -1, 1, "", methodObject), new Among("este", -1, 1, "", methodObject), new Among("iste", -1, 1, "", methodObject), new Among("ei", -1, 1, "", methodObject), new Among("arei", 16, 1, "", methodObject), new Among("erei", 16, 1, "", methodObject), new Among("irei", 16, 1, "", methodObject), 
			new Among("am", -1, 1, "", methodObject), new Among("iam", 20, 1, "", methodObject), new Among("ariam", 21, 1, "", methodObject), new Among("eriam", 21, 1, "", methodObject), new Among("iriam", 21, 1, "", methodObject), new Among("aram", 20, 1, "", methodObject), new Among("eram", 20, 1, "", methodObject), new Among("iram", 20, 1, "", methodObject), new Among("avam", 20, 1, "", methodObject), new Among("em", -1, 1, "", methodObject), 
			new Among("arem", 29, 1, "", methodObject), new Among("erem", 29, 1, "", methodObject), new Among("irem", 29, 1, "", methodObject), new Among("assem", 29, 1, "", methodObject), new Among("essem", 29, 1, "", methodObject), new Among("issem", 29, 1, "", methodObject), new Among("ado", -1, 1, "", methodObject), new Among("ido", -1, 1, "", methodObject), new Among("ando", -1, 1, "", methodObject), new Among("endo", -1, 1, "", methodObject), 
			new Among("indo", -1, 1, "", methodObject), new Among("ara~o", -1, 1, "", methodObject), new Among("era~o", -1, 1, "", methodObject), new Among("ira~o", -1, 1, "", methodObject), new Among("ar", -1, 1, "", methodObject), new Among("er", -1, 1, "", methodObject), new Among("ir", -1, 1, "", methodObject), new Among("as", -1, 1, "", methodObject), new Among("adas", 47, 1, "", methodObject), new Among("idas", 47, 1, "", methodObject), 
			new Among("ias", 47, 1, "", methodObject), new Among("arias", 50, 1, "", methodObject), new Among("erias", 50, 1, "", methodObject), new Among("irias", 50, 1, "", methodObject), new Among("aras", 47, 1, "", methodObject), new Among("eras", 47, 1, "", methodObject), new Among("iras", 47, 1, "", methodObject), new Among("avas", 47, 1, "", methodObject), new Among("es", -1, 1, "", methodObject), new Among("ardes", 58, 1, "", methodObject), 
			new Among("erdes", 58, 1, "", methodObject), new Among("irdes", 58, 1, "", methodObject), new Among("ares", 58, 1, "", methodObject), new Among("eres", 58, 1, "", methodObject), new Among("ires", 58, 1, "", methodObject), new Among("asses", 58, 1, "", methodObject), new Among("esses", 58, 1, "", methodObject), new Among("isses", 58, 1, "", methodObject), new Among("astes", 58, 1, "", methodObject), new Among("estes", 58, 1, "", methodObject), 
			new Among("istes", 58, 1, "", methodObject), new Among("is", -1, 1, "", methodObject), new Among("ais", 71, 1, "", methodObject), new Among("eis", 71, 1, "", methodObject), new Among("areis", 73, 1, "", methodObject), new Among("ereis", 73, 1, "", methodObject), new Among("ireis", 73, 1, "", methodObject), new Among("\341reis", 73, 1, "", methodObject), new Among("\351reis", 73, 1, "", methodObject), new Among("\355reis", 73, 1, "", methodObject), 
			new Among("\341sseis", 73, 1, "", methodObject), new Among("\351sseis", 73, 1, "", methodObject), new Among("\355sseis", 73, 1, "", methodObject), new Among("\341veis", 73, 1, "", methodObject), new Among("\355eis", 73, 1, "", methodObject), new Among("ar\355eis", 84, 1, "", methodObject), new Among("er\355eis", 84, 1, "", methodObject), new Among("ir\355eis", 84, 1, "", methodObject), new Among("ados", -1, 1, "", methodObject), new Among("idos", -1, 1, "", methodObject), 
			new Among("amos", -1, 1, "", methodObject), new Among("\341ramos", 90, 1, "", methodObject), new Among("\351ramos", 90, 1, "", methodObject), new Among("\355ramos", 90, 1, "", methodObject), new Among("\341vamos", 90, 1, "", methodObject), new Among("\355amos", 90, 1, "", methodObject), new Among("ar\355amos", 95, 1, "", methodObject), new Among("er\355amos", 95, 1, "", methodObject), new Among("ir\355amos", 95, 1, "", methodObject), new Among("emos", -1, 1, "", methodObject), 
			new Among("aremos", 99, 1, "", methodObject), new Among("eremos", 99, 1, "", methodObject), new Among("iremos", 99, 1, "", methodObject), new Among("\341ssemos", 99, 1, "", methodObject), new Among("\352ssemos", 99, 1, "", methodObject), new Among("\355ssemos", 99, 1, "", methodObject), new Among("imos", -1, 1, "", methodObject), new Among("armos", -1, 1, "", methodObject), new Among("ermos", -1, 1, "", methodObject), new Among("irmos", -1, 1, "", methodObject), 
			new Among("\341mos", -1, 1, "", methodObject), new Among("ar\341s", -1, 1, "", methodObject), new Among("er\341s", -1, 1, "", methodObject), new Among("ir\341s", -1, 1, "", methodObject), new Among("eu", -1, 1, "", methodObject), new Among("iu", -1, 1, "", methodObject), new Among("ou", -1, 1, "", methodObject), new Among("ar\341", -1, 1, "", methodObject), new Among("er\341", -1, 1, "", methodObject), new Among("ir\341", -1, 1, "", methodObject)
		});
		a_7 = (new Among[] {
			new Among("a", -1, 1, "", methodObject), new Among("i", -1, 1, "", methodObject), new Among("o", -1, 1, "", methodObject), new Among("os", -1, 1, "", methodObject), new Among("\341", -1, 1, "", methodObject), new Among("\355", -1, 1, "", methodObject), new Among("\363", -1, 1, "", methodObject)
		});
		a_8 = (new Among[] {
			new Among("e", -1, 1, "", methodObject), new Among("\347", -1, 2, "", methodObject), new Among("\351", -1, 1, "", methodObject), new Among("\352", -1, 1, "", methodObject)
		});
	}
}
