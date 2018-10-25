// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RussianStemmer.java

package org.tartarus.snowball.ext;

import org.tartarus.snowball.Among;
import org.tartarus.snowball.SnowballProgram;

public class RussianStemmer extends SnowballProgram
{

	private static final long serialVersionUID = 1L;
	private static final RussianStemmer methodObject;
	private static final Among a_0[];
	private static final Among a_1[];
	private static final Among a_2[];
	private static final Among a_3[];
	private static final Among a_4[];
	private static final Among a_5[];
	private static final Among a_6[];
	private static final Among a_7[];
	private static final char g_v[] = {
		'!', 'A', '\b', '\350'
	};
	private int I_p2;
	private int I_pV;

	public RussianStemmer()
	{
	}

	private void copy_from(RussianStemmer other)
	{
		I_p2 = other.I_p2;
		I_pV = other.I_pV;
		super.copy_from(other);
	}

	private boolean r_mark_regions()
	{
		int v_1;
label0:
		{
			I_pV = limit;
			I_p2 = limit;
			v_1 = cursor;
			while (!in_grouping(g_v, 1072, 1103)) 
			{
				if (cursor >= limit)
					break label0;
				cursor++;
			}
			I_pV = cursor;
			while (!out_grouping(g_v, 1072, 1103)) 
			{
				if (cursor >= limit)
					break label0;
				cursor++;
			}
			while (!in_grouping(g_v, 1072, 1103)) 
			{
				if (cursor >= limit)
					break label0;
				cursor++;
			}
			while (!out_grouping(g_v, 1072, 1103)) 
			{
				if (cursor >= limit)
					break label0;
				cursor++;
			}
			I_p2 = cursor;
		}
		cursor = v_1;
		return true;
	}

	private boolean r_R2()
	{
		return I_p2 <= cursor;
	}

	private boolean r_perfective_gerund()
	{
		ket = cursor;
		int among_var = find_among_b(a_0, 9);
		if (among_var == 0)
			return false;
		bra = cursor;
		switch (among_var)
		{
		case 0: // '\0'
			return false;

		case 1: // '\001'
			int v_1 = limit - cursor;
			if (!eq_s_b(1, "��"))
			{
				cursor = limit - v_1;
				if (!eq_s_b(1, "��"))
					return false;
			}
			slice_del();
			break;

		case 2: // '\002'
			slice_del();
			break;
		}
		return true;
	}

	private boolean r_adjective()
	{
		ket = cursor;
		int among_var = find_among_b(a_1, 26);
		if (among_var == 0)
			return false;
		bra = cursor;
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

	private boolean r_adjectival()
	{
		if (!r_adjective())
			return false;
		int v_1 = limit - cursor;
		ket = cursor;
		int among_var = find_among_b(a_2, 8);
		if (among_var == 0)
		{
			cursor = limit - v_1;
		} else
		{
			bra = cursor;
			switch (among_var)
			{
			default:
				break;

			case 0: // '\0'
				cursor = limit - v_1;
				break;

			case 1: // '\001'
				int v_2 = limit - cursor;
				if (!eq_s_b(1, "��"))
				{
					cursor = limit - v_2;
					if (!eq_s_b(1, "��"))
					{
						cursor = limit - v_1;
						break;
					}
				}
				slice_del();
				break;

			case 2: // '\002'
				slice_del();
				break;
			}
		}
		return true;
	}

	private boolean r_reflexive()
	{
		ket = cursor;
		int among_var = find_among_b(a_3, 2);
		if (among_var == 0)
			return false;
		bra = cursor;
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

	private boolean r_verb()
	{
		ket = cursor;
		int among_var = find_among_b(a_4, 46);
		if (among_var == 0)
			return false;
		bra = cursor;
		switch (among_var)
		{
		case 0: // '\0'
			return false;

		case 1: // '\001'
			int v_1 = limit - cursor;
			if (!eq_s_b(1, "��"))
			{
				cursor = limit - v_1;
				if (!eq_s_b(1, "��"))
					return false;
			}
			slice_del();
			break;

		case 2: // '\002'
			slice_del();
			break;
		}
		return true;
	}

	private boolean r_noun()
	{
		ket = cursor;
		int among_var = find_among_b(a_5, 36);
		if (among_var == 0)
			return false;
		bra = cursor;
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

	private boolean r_derivational()
	{
		ket = cursor;
		int among_var = find_among_b(a_6, 2);
		if (among_var == 0)
			return false;
		bra = cursor;
		if (!r_R2())
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

	private boolean r_tidy_up()
	{
		ket = cursor;
		int among_var = find_among_b(a_7, 4);
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
			slice_del();
			ket = cursor;
			if (!eq_s_b(1, "��"))
				return false;
			bra = cursor;
			if (!eq_s_b(1, "��"))
				return false;
			slice_del();
			break;

		case 2: // '\002'
			if (!eq_s_b(1, "��"))
				return false;
			slice_del();
			break;

		case 3: // '\003'
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
		if (cursor < I_pV)
			return false;
		cursor = I_pV;
		int v_3 = limit_backward;
		limit_backward = cursor;
		cursor = limit - v_2;
		int v_4 = limit - cursor;
		int v_5 = limit - cursor;
		if (!r_perfective_gerund())
		{
			cursor = limit - v_5;
			int v_6 = limit - cursor;
			if (!r_reflexive())
				cursor = limit - v_6;
			int v_7 = limit - cursor;
			if (!r_adjectival())
			{
				cursor = limit - v_7;
				if (!r_verb())
				{
					cursor = limit - v_7;
					if (r_noun());
				}
			}
		}
		cursor = limit - v_4;
		int v_8 = limit - cursor;
		ket = cursor;
		if (!eq_s_b(1, "��"))
		{
			cursor = limit - v_8;
		} else
		{
			bra = cursor;
			slice_del();
		}
		int v_9 = limit - cursor;
		if (r_derivational());
		cursor = limit - v_9;
		int v_10 = limit - cursor;
		if (r_tidy_up());
		cursor = limit - v_10;
		limit_backward = v_3;
		cursor = limit_backward;
		return true;
	}

	public boolean equals(Object o)
	{
		return o instanceof RussianStemmer;
	}

	public int hashCode()
	{
		return org/tartarus/snowball/ext/RussianStemmer.getName().hashCode();
	}

	static 
	{
		methodObject = new RussianStemmer();
		a_0 = (new Among[] {
			new Among("��", -1, 1, "", methodObject), new Among("�ڧ�", 0, 2, "", methodObject), new Among("���", 0, 2, "", methodObject), new Among("�ӧ��", -1, 1, "", methodObject), new Among("�ڧӧ��", 3, 2, "", methodObject), new Among("��ӧ��", 3, 2, "", methodObject), new Among("�ӧ�ڧ��", -1, 1, "", methodObject), new Among("�ڧӧ�ڧ��", 6, 2, "", methodObject), new Among("��ӧ�ڧ��", 6, 2, "", methodObject)
		});
		a_1 = (new Among[] {
			new Among("�֧�", -1, 1, "", methodObject), new Among("�ڧ�", -1, 1, "", methodObject), new Among("���", -1, 1, "", methodObject), new Among("���", -1, 1, "", methodObject), new Among("�ڧާ�", -1, 1, "", methodObject), new Among("��ާ�", -1, 1, "", methodObject), new Among("�֧�", -1, 1, "", methodObject), new Among("�ڧ�", -1, 1, "", methodObject), new Among("���", -1, 1, "", methodObject), new Among("���", -1, 1, "", methodObject), 
			new Among("�֧�", -1, 1, "", methodObject), new Among("�ڧ�", -1, 1, "", methodObject), new Among("���", -1, 1, "", methodObject), new Among("���", -1, 1, "", methodObject), new Among("�֧ԧ�", -1, 1, "", methodObject), new Among("��ԧ�", -1, 1, "", methodObject), new Among("�֧ާ�", -1, 1, "", methodObject), new Among("��ާ�", -1, 1, "", methodObject), new Among("�ڧ�", -1, 1, "", methodObject), new Among("���", -1, 1, "", methodObject), 
			new Among("�֧�", -1, 1, "", methodObject), new Among("���", -1, 1, "", methodObject), new Among("���", -1, 1, "", methodObject), new Among("���", -1, 1, "", methodObject), new Among("�ѧ�", -1, 1, "", methodObject), new Among("���", -1, 1, "", methodObject)
		});
		a_2 = (new Among[] {
			new Among("�֧�", -1, 1, "", methodObject), new Among("�ߧ�", -1, 1, "", methodObject), new Among("�ӧ�", -1, 1, "", methodObject), new Among("�ڧӧ�", 2, 2, "", methodObject), new Among("��ӧ�", 2, 2, "", methodObject), new Among("��", -1, 1, "", methodObject), new Among("���", 5, 1, "", methodObject), new Among("����", 6, 2, "", methodObject)
		});
		a_3 = (new Among[] {
			new Among("���", -1, 1, "", methodObject), new Among("���", -1, 1, "", methodObject)
		});
		a_4 = (new Among[] {
			new Among("�ݧ�", -1, 1, "", methodObject), new Among("�ڧݧ�", 0, 2, "", methodObject), new Among("��ݧ�", 0, 2, "", methodObject), new Among("�ߧ�", -1, 1, "", methodObject), new Among("�֧ߧ�", 3, 2, "", methodObject), new Among("�֧��", -1, 1, "", methodObject), new Among("�ڧ��", -1, 2, "", methodObject), new Among("�ۧ��", -1, 1, "", methodObject), new Among("�֧ۧ��", 7, 2, "", methodObject), new Among("��ۧ��", 7, 2, "", methodObject), 
			new Among("�ݧ�", -1, 1, "", methodObject), new Among("�ڧݧ�", 10, 2, "", methodObject), new Among("��ݧ�", 10, 2, "", methodObject), new Among("��", -1, 1, "", methodObject), new Among("�֧�", 13, 2, "", methodObject), new Among("���", 13, 2, "", methodObject), new Among("��", -1, 1, "", methodObject), new Among("�ڧ�", 16, 2, "", methodObject), new Among("���", 16, 2, "", methodObject), new Among("�֧�", -1, 1, "", methodObject), 
			new Among("�ڧ�", -1, 2, "", methodObject), new Among("���", -1, 2, "", methodObject), new Among("��", -1, 1, "", methodObject), new Among("�֧�", 22, 2, "", methodObject), new Among("�ݧ�", -1, 1, "", methodObject), new Among("�ڧݧ�", 24, 2, "", methodObject), new Among("��ݧ�", 24, 2, "", methodObject), new Among("�ߧ�", -1, 1, "", methodObject), new Among("�֧ߧ�", 27, 2, "", methodObject), new Among("�ߧߧ�", 27, 1, "", methodObject), 
			new Among("�֧�", -1, 1, "", methodObject), new Among("��֧�", 30, 2, "", methodObject), new Among("�ڧ�", -1, 2, "", methodObject), new Among("���", -1, 2, "", methodObject), new Among("���", -1, 1, "", methodObject), new Among("����", 34, 2, "", methodObject), new Among("���", -1, 2, "", methodObject), new Among("�ߧ�", -1, 1, "", methodObject), new Among("�֧ߧ�", 37, 2, "", methodObject), new Among("���", -1, 1, "", methodObject), 
			new Among("�ڧ��", 39, 2, "", methodObject), new Among("����", 39, 2, "", methodObject), new Among("�֧��", -1, 1, "", methodObject), new Among("�ڧ��", -1, 2, "", methodObject), new Among("��", -1, 2, "", methodObject), new Among("���", 44, 2, "", methodObject)
		});
		a_5 = (new Among[] {
			new Among("��", -1, 1, "", methodObject), new Among("�֧�", -1, 1, "", methodObject), new Among("���", -1, 1, "", methodObject), new Among("��", -1, 1, "", methodObject), new Among("�ڧ�", 3, 1, "", methodObject), new Among("���", 3, 1, "", methodObject), new Among("��", -1, 1, "", methodObject), new Among("�֧�", 6, 1, "", methodObject), new Among("�ڧ�", 6, 1, "", methodObject), new Among("�ѧާ�", 6, 1, "", methodObject), 
			new Among("��ާ�", 6, 1, "", methodObject), new Among("�ڧ�ާ�", 10, 1, "", methodObject), new Among("��", -1, 1, "", methodObject), new Among("�֧�", 12, 1, "", methodObject), new Among("�ڧ֧�", 13, 1, "", methodObject), new Among("�ڧ�", 12, 1, "", methodObject), new Among("���", 12, 1, "", methodObject), new Among("�ѧ�", -1, 1, "", methodObject), new Among("�֧�", -1, 1, "", methodObject), new Among("�ڧ֧�", 18, 1, "", methodObject), 
			new Among("���", -1, 1, "", methodObject), new Among("���", -1, 1, "", methodObject), new Among("�ڧ��", 21, 1, "", methodObject), new Among("��", -1, 1, "", methodObject), new Among("��", -1, 1, "", methodObject), new Among("�ѧ�", -1, 1, "", methodObject), new Among("���", -1, 1, "", methodObject), new Among("�ڧ��", 26, 1, "", methodObject), new Among("��", -1, 1, "", methodObject), new Among("��", -1, 1, "", methodObject), 
			new Among("��", -1, 1, "", methodObject), new Among("�ڧ�", 30, 1, "", methodObject), new Among("���", 30, 1, "", methodObject), new Among("��", -1, 1, "", methodObject), new Among("�ڧ�", 33, 1, "", methodObject), new Among("���", 33, 1, "", methodObject)
		});
		a_6 = (new Among[] {
			new Among("����", -1, 1, "", methodObject), new Among("�����", -1, 1, "", methodObject)
		});
		a_7 = (new Among[] {
			new Among("�֧ۧ��", -1, 1, "", methodObject), new Among("��", -1, 2, "", methodObject), new Among("�֧ۧ�", -1, 1, "", methodObject), new Among("��", -1, 3, "", methodObject)
		});
	}
}
