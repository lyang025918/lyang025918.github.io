// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CatalanStemmer.java

package org.tartarus.snowball.ext;

import org.tartarus.snowball.Among;
import org.tartarus.snowball.SnowballProgram;

public class CatalanStemmer extends SnowballProgram
{

	private static final long serialVersionUID = 1L;
	private static final CatalanStemmer methodObject;
	private static final Among a_0[];
	private static final Among a_1[];
	private static final Among a_2[];
	private static final Among a_3[];
	private static final Among a_4[];
	private static final char g_v[] = {
		'\021', 'A', '\020', '\0', '\0', '\0', '\0', '\0', '\0', '\0', 
		'\0', '\0', '\0', '\0', '\0', '\200', '\201', 'Q', '\006', '\n'
	};
	private int I_p2;
	private int I_p1;

	public CatalanStemmer()
	{
	}

	private void copy_from(CatalanStemmer other)
	{
		I_p2 = other.I_p2;
		I_p1 = other.I_p1;
		super.copy_from(other);
	}

	private boolean r_mark_regions()
	{
		int v_1;
label0:
		{
			I_p1 = limit;
			I_p2 = limit;
			v_1 = cursor;
			while (!in_grouping(g_v, 97, 252)) 
			{
				if (cursor >= limit)
					break label0;
				cursor++;
			}
			while (!out_grouping(g_v, 97, 252)) 
			{
				if (cursor >= limit)
					break label0;
				cursor++;
			}
			I_p1 = cursor;
			while (!in_grouping(g_v, 97, 252)) 
			{
				if (cursor >= limit)
					break label0;
				cursor++;
			}
			while (!out_grouping(g_v, 97, 252)) 
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

	private boolean r_cleaning()
	{
		int v_1;
label0:
		do
		{
			v_1 = cursor;
			bra = cursor;
			int among_var = find_among(a_0, 13);
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
				slice_from("a");
				break;

			case 3: // '\003'
				slice_from("e");
				break;

			case 4: // '\004'
				slice_from("e");
				break;

			case 5: // '\005'
				slice_from("i");
				break;

			case 6: // '\006'
				slice_from("i");
				break;

			case 7: // '\007'
				slice_from("o");
				break;

			case 8: // '\b'
				slice_from("o");
				break;

			case 9: // '\t'
				slice_from("u");
				break;

			case 10: // '\n'
				slice_from("u");
				break;

			case 11: // '\013'
				slice_from("i");
				break;

			case 12: // '\f'
				slice_from(".");
				break;

			case 13: // '\r'
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

	private boolean r_attached_pronoun()
	{
		ket = cursor;
		int among_var = find_among_b(a_1, 39);
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
			slice_del();
			break;
		}
		return true;
	}

	private boolean r_standard_suffix()
	{
		ket = cursor;
		int among_var = find_among_b(a_2, 200);
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
			slice_del();
			break;

		case 2: // '\002'
			if (!r_R2())
				return false;
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
			slice_from("ic");
			break;

		case 5: // '\005'
			if (!r_R1())
				return false;
			slice_from("c");
			break;
		}
		return true;
	}

	private boolean r_verb_suffix()
	{
		ket = cursor;
		int among_var = find_among_b(a_3, 283);
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
			slice_del();
			break;

		case 2: // '\002'
			if (!r_R2())
				return false;
			slice_del();
			break;
		}
		return true;
	}

	private boolean r_residual_suffix()
	{
		ket = cursor;
		int among_var = find_among_b(a_4, 22);
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
			slice_del();
			break;

		case 2: // '\002'
			if (!r_R1())
				return false;
			slice_from("ic");
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
			if (r_verb_suffix());
		}
		cursor = limit - v_3;
		int v_5 = limit - cursor;
		if (r_residual_suffix());
		cursor = limit - v_5;
		cursor = limit_backward;
		int v_6 = cursor;
		if (r_cleaning());
		cursor = v_6;
		return true;
	}

	public boolean equals(Object o)
	{
		return o instanceof CatalanStemmer;
	}

	public int hashCode()
	{
		return org/tartarus/snowball/ext/CatalanStemmer.getName().hashCode();
	}

	static 
	{
		methodObject = new CatalanStemmer();
		a_0 = (new Among[] {
			new Among("", -1, 13, "", methodObject), new Among("\267", 0, 12, "", methodObject), new Among("\340", 0, 2, "", methodObject), new Among("\341", 0, 1, "", methodObject), new Among("\350", 0, 4, "", methodObject), new Among("\351", 0, 3, "", methodObject), new Among("\354", 0, 6, "", methodObject), new Among("\355", 0, 5, "", methodObject), new Among("\357", 0, 11, "", methodObject), new Among("\362", 0, 8, "", methodObject), 
			new Among("\363", 0, 7, "", methodObject), new Among("\372", 0, 9, "", methodObject), new Among("\374", 0, 10, "", methodObject)
		});
		a_1 = (new Among[] {
			new Among("la", -1, 1, "", methodObject), new Among("-la", 0, 1, "", methodObject), new Among("sela", 0, 1, "", methodObject), new Among("le", -1, 1, "", methodObject), new Among("me", -1, 1, "", methodObject), new Among("-me", 4, 1, "", methodObject), new Among("se", -1, 1, "", methodObject), new Among("-te", -1, 1, "", methodObject), new Among("hi", -1, 1, "", methodObject), new Among("'hi", 8, 1, "", methodObject), 
			new Among("li", -1, 1, "", methodObject), new Among("-li", 10, 1, "", methodObject), new Among("'l", -1, 1, "", methodObject), new Among("'m", -1, 1, "", methodObject), new Among("-m", -1, 1, "", methodObject), new Among("'n", -1, 1, "", methodObject), new Among("-n", -1, 1, "", methodObject), new Among("ho", -1, 1, "", methodObject), new Among("'ho", 17, 1, "", methodObject), new Among("lo", -1, 1, "", methodObject), 
			new Among("selo", 19, 1, "", methodObject), new Among("'s", -1, 1, "", methodObject), new Among("las", -1, 1, "", methodObject), new Among("selas", 22, 1, "", methodObject), new Among("les", -1, 1, "", methodObject), new Among("-les", 24, 1, "", methodObject), new Among("'ls", -1, 1, "", methodObject), new Among("-ls", -1, 1, "", methodObject), new Among("'ns", -1, 1, "", methodObject), new Among("-ns", -1, 1, "", methodObject), 
			new Among("ens", -1, 1, "", methodObject), new Among("los", -1, 1, "", methodObject), new Among("selos", 31, 1, "", methodObject), new Among("nos", -1, 1, "", methodObject), new Among("-nos", 33, 1, "", methodObject), new Among("vos", -1, 1, "", methodObject), new Among("us", -1, 1, "", methodObject), new Among("-us", 36, 1, "", methodObject), new Among("'t", -1, 1, "", methodObject)
		});
		a_2 = (new Among[] {
			new Among("ica", -1, 4, "", methodObject), new Among("l\363gica", 0, 3, "", methodObject), new Among("enca", -1, 1, "", methodObject), new Among("ada", -1, 2, "", methodObject), new Among("ancia", -1, 1, "", methodObject), new Among("encia", -1, 1, "", methodObject), new Among("\350ncia", -1, 1, "", methodObject), new Among("\355cia", -1, 1, "", methodObject), new Among("logia", -1, 3, "", methodObject), new Among("inia", -1, 1, "", methodObject), 
			new Among("\355inia", 9, 1, "", methodObject), new Among("eria", -1, 1, "", methodObject), new Among("\340ria", -1, 1, "", methodObject), new Among("at\362ria", -1, 1, "", methodObject), new Among("alla", -1, 1, "", methodObject), new Among("ella", -1, 1, "", methodObject), new Among("\355vola", -1, 1, "", methodObject), new Among("ima", -1, 1, "", methodObject), new Among("\355ssima", 17, 1, "", methodObject), new Among("qu\355ssima", 18, 5, "", methodObject), 
			new Among("ana", -1, 1, "", methodObject), new Among("ina", -1, 1, "", methodObject), new Among("era", -1, 1, "", methodObject), new Among("sfera", 22, 1, "", methodObject), new Among("ora", -1, 1, "", methodObject), new Among("dora", 24, 1, "", methodObject), new Among("adora", 25, 1, "", methodObject), new Among("adura", -1, 1, "", methodObject), new Among("esa", -1, 1, "", methodObject), new Among("osa", -1, 1, "", methodObject), 
			new Among("assa", -1, 1, "", methodObject), new Among("essa", -1, 1, "", methodObject), new Among("issa", -1, 1, "", methodObject), new Among("eta", -1, 1, "", methodObject), new Among("ita", -1, 1, "", methodObject), new Among("ota", -1, 1, "", methodObject), new Among("ista", -1, 1, "", methodObject), new Among("ialista", 36, 1, "", methodObject), new Among("ionista", 36, 1, "", methodObject), new Among("iva", -1, 1, "", methodObject), 
			new Among("ativa", 39, 1, "", methodObject), new Among("n\347a", -1, 1, "", methodObject), new Among("log\355a", -1, 3, "", methodObject), new Among("ic", -1, 4, "", methodObject), new Among("\355stic", 43, 1, "", methodObject), new Among("enc", -1, 1, "", methodObject), new Among("esc", -1, 1, "", methodObject), new Among("ud", -1, 1, "", methodObject), new Among("atge", -1, 1, "", methodObject), new Among("ble", -1, 1, "", methodObject), 
			new Among("able", 49, 1, "", methodObject), new Among("ible", 49, 1, "", methodObject), new Among("isme", -1, 1, "", methodObject), new Among("ialisme", 52, 1, "", methodObject), new Among("ionisme", 52, 1, "", methodObject), new Among("ivisme", 52, 1, "", methodObject), new Among("aire", -1, 1, "", methodObject), new Among("icte", -1, 1, "", methodObject), new Among("iste", -1, 1, "", methodObject), new Among("ici", -1, 1, "", methodObject), 
			new Among("\355ci", -1, 1, "", methodObject), new Among("logi", -1, 3, "", methodObject), new Among("ari", -1, 1, "", methodObject), new Among("tori", -1, 1, "", methodObject), new Among("al", -1, 1, "", methodObject), new Among("il", -1, 1, "", methodObject), new Among("all", -1, 1, "", methodObject), new Among("ell", -1, 1, "", methodObject), new Among("\355vol", -1, 1, "", methodObject), new Among("isam", -1, 1, "", methodObject), 
			new Among("issem", -1, 1, "", methodObject), new Among("\354ssem", -1, 1, "", methodObject), new Among("\355ssem", -1, 1, "", methodObject), new Among("\355ssim", -1, 1, "", methodObject), new Among("qu\355ssim", 73, 5, "", methodObject), new Among("amen", -1, 1, "", methodObject), new Among("\354ssin", -1, 1, "", methodObject), new Among("ar", -1, 1, "", methodObject), new Among("ificar", 77, 1, "", methodObject), new Among("egar", 77, 1, "", methodObject), 
			new Among("ejar", 77, 1, "", methodObject), new Among("itar", 77, 1, "", methodObject), new Among("itzar", 77, 1, "", methodObject), new Among("fer", -1, 1, "", methodObject), new Among("or", -1, 1, "", methodObject), new Among("dor", 84, 1, "", methodObject), new Among("dur", -1, 1, "", methodObject), new Among("doras", -1, 1, "", methodObject), new Among("ics", -1, 4, "", methodObject), new Among("l\363gics", 88, 3, "", methodObject), 
			new Among("uds", -1, 1, "", methodObject), new Among("nces", -1, 1, "", methodObject), new Among("ades", -1, 2, "", methodObject), new Among("ancies", -1, 1, "", methodObject), new Among("encies", -1, 1, "", methodObject), new Among("\350ncies", -1, 1, "", methodObject), new Among("\355cies", -1, 1, "", methodObject), new Among("logies", -1, 3, "", methodObject), new Among("inies", -1, 1, "", methodObject), new Among("\355nies", -1, 1, "", methodObject), 
			new Among("eries", -1, 1, "", methodObject), new Among("\340ries", -1, 1, "", methodObject), new Among("at\362ries", -1, 1, "", methodObject), new Among("bles", -1, 1, "", methodObject), new Among("ables", 103, 1, "", methodObject), new Among("ibles", 103, 1, "", methodObject), new Among("imes", -1, 1, "", methodObject), new Among("\355ssimes", 106, 1, "", methodObject), new Among("qu\355ssimes", 107, 5, "", methodObject), new Among("formes", -1, 1, "", methodObject), 
			new Among("ismes", -1, 1, "", methodObject), new Among("ialismes", 110, 1, "", methodObject), new Among("ines", -1, 1, "", methodObject), new Among("eres", -1, 1, "", methodObject), new Among("ores", -1, 1, "", methodObject), new Among("dores", 114, 1, "", methodObject), new Among("idores", 115, 1, "", methodObject), new Among("dures", -1, 1, "", methodObject), new Among("eses", -1, 1, "", methodObject), new Among("oses", -1, 1, "", methodObject), 
			new Among("asses", -1, 1, "", methodObject), new Among("ictes", -1, 1, "", methodObject), new Among("ites", -1, 1, "", methodObject), new Among("otes", -1, 1, "", methodObject), new Among("istes", -1, 1, "", methodObject), new Among("ialistes", 124, 1, "", methodObject), new Among("ionistes", 124, 1, "", methodObject), new Among("iques", -1, 4, "", methodObject), new Among("l\363giques", 127, 3, "", methodObject), new Among("ives", -1, 1, "", methodObject), 
			new Among("atives", 129, 1, "", methodObject), new Among("log\355es", -1, 3, "", methodObject), new Among("alleng\374es", -1, 1, "", methodObject), new Among("icis", -1, 1, "", methodObject), new Among("\355cis", -1, 1, "", methodObject), new Among("logis", -1, 3, "", methodObject), new Among("aris", -1, 1, "", methodObject), new Among("toris", -1, 1, "", methodObject), new Among("ls", -1, 1, "", methodObject), new Among("als", 138, 1, "", methodObject), 
			new Among("ells", 138, 1, "", methodObject), new Among("ims", -1, 1, "", methodObject), new Among("\355ssims", 141, 1, "", methodObject), new Among("qu\355ssims", 142, 5, "", methodObject), new Among("ions", -1, 1, "", methodObject), new Among("cions", 144, 1, "", methodObject), new Among("acions", 145, 2, "", methodObject), new Among("esos", -1, 1, "", methodObject), new Among("osos", -1, 1, "", methodObject), new Among("assos", -1, 1, "", methodObject), 
			new Among("issos", -1, 1, "", methodObject), new Among("ers", -1, 1, "", methodObject), new Among("ors", -1, 1, "", methodObject), new Among("dors", 152, 1, "", methodObject), new Among("adors", 153, 1, "", methodObject), new Among("idors", 153, 1, "", methodObject), new Among("ats", -1, 1, "", methodObject), new Among("itats", 156, 1, "", methodObject), new Among("bilitats", 157, 1, "", methodObject), new Among("ivitats", 157, 1, "", methodObject), 
			new Among("ativitats", 159, 1, "", methodObject), new Among("\357tats", 156, 1, "", methodObject), new Among("ets", -1, 1, "", methodObject), new Among("ants", -1, 1, "", methodObject), new Among("ents", -1, 1, "", methodObject), new Among("ments", 164, 1, "", methodObject), new Among("aments", 165, 1, "", methodObject), new Among("ots", -1, 1, "", methodObject), new Among("uts", -1, 1, "", methodObject), new Among("ius", -1, 1, "", methodObject), 
			new Among("trius", 169, 1, "", methodObject), new Among("atius", 169, 1, "", methodObject), new Among("\350s", -1, 1, "", methodObject), new Among("\351s", -1, 1, "", methodObject), new Among("\355s", -1, 1, "", methodObject), new Among("d\355s", 174, 1, "", methodObject), new Among("\363s", -1, 1, "", methodObject), new Among("itat", -1, 1, "", methodObject), new Among("bilitat", 177, 1, "", methodObject), new Among("ivitat", 177, 1, "", methodObject), 
			new Among("ativitat", 179, 1, "", methodObject), new Among("\357tat", -1, 1, "", methodObject), new Among("et", -1, 1, "", methodObject), new Among("ant", -1, 1, "", methodObject), new Among("ent", -1, 1, "", methodObject), new Among("ient", 184, 1, "", methodObject), new Among("ment", 184, 1, "", methodObject), new Among("ament", 186, 1, "", methodObject), new Among("isament", 187, 1, "", methodObject), new Among("ot", -1, 1, "", methodObject), 
			new Among("isseu", -1, 1, "", methodObject), new Among("\354sseu", -1, 1, "", methodObject), new Among("\355sseu", -1, 1, "", methodObject), new Among("triu", -1, 1, "", methodObject), new Among("\355ssiu", -1, 1, "", methodObject), new Among("atiu", -1, 1, "", methodObject), new Among("\363", -1, 1, "", methodObject), new Among("i\363", 196, 1, "", methodObject), new Among("ci\363", 197, 1, "", methodObject), new Among("aci\363", 198, 1, "", methodObject)
		});
		a_3 = (new Among[] {
			new Among("aba", -1, 1, "", methodObject), new Among("esca", -1, 1, "", methodObject), new Among("isca", -1, 1, "", methodObject), new Among("\357sca", -1, 1, "", methodObject), new Among("ada", -1, 1, "", methodObject), new Among("ida", -1, 1, "", methodObject), new Among("uda", -1, 1, "", methodObject), new Among("\357da", -1, 1, "", methodObject), new Among("ia", -1, 1, "", methodObject), new Among("aria", 8, 1, "", methodObject), 
			new Among("iria", 8, 1, "", methodObject), new Among("ara", -1, 1, "", methodObject), new Among("iera", -1, 1, "", methodObject), new Among("ira", -1, 1, "", methodObject), new Among("adora", -1, 1, "", methodObject), new Among("\357ra", -1, 1, "", methodObject), new Among("ava", -1, 1, "", methodObject), new Among("ixa", -1, 1, "", methodObject), new Among("itza", -1, 1, "", methodObject), new Among("\355a", -1, 1, "", methodObject), 
			new Among("ar\355a", 19, 1, "", methodObject), new Among("er\355a", 19, 1, "", methodObject), new Among("ir\355a", 19, 1, "", methodObject), new Among("\357a", -1, 1, "", methodObject), new Among("isc", -1, 1, "", methodObject), new Among("\357sc", -1, 1, "", methodObject), new Among("ad", -1, 1, "", methodObject), new Among("ed", -1, 1, "", methodObject), new Among("id", -1, 1, "", methodObject), new Among("ie", -1, 1, "", methodObject), 
			new Among("re", -1, 1, "", methodObject), new Among("dre", 30, 1, "", methodObject), new Among("ase", -1, 1, "", methodObject), new Among("iese", -1, 1, "", methodObject), new Among("aste", -1, 1, "", methodObject), new Among("iste", -1, 1, "", methodObject), new Among("ii", -1, 1, "", methodObject), new Among("ini", -1, 1, "", methodObject), new Among("esqui", -1, 1, "", methodObject), new Among("eixi", -1, 1, "", methodObject), 
			new Among("itzi", -1, 1, "", methodObject), new Among("am", -1, 1, "", methodObject), new Among("em", -1, 1, "", methodObject), new Among("arem", 42, 1, "", methodObject), new Among("irem", 42, 1, "", methodObject), new Among("\340rem", 42, 1, "", methodObject), new Among("\355rem", 42, 1, "", methodObject), new Among("\340ssem", 42, 1, "", methodObject), new Among("\351ssem", 42, 1, "", methodObject), new Among("iguem", 42, 1, "", methodObject), 
			new Among("\357guem", 42, 1, "", methodObject), new Among("avem", 42, 1, "", methodObject), new Among("\340vem", 42, 1, "", methodObject), new Among("\341vem", 42, 1, "", methodObject), new Among("ir\354em", 42, 1, "", methodObject), new Among("\355em", 42, 1, "", methodObject), new Among("ar\355em", 55, 1, "", methodObject), new Among("ir\355em", 55, 1, "", methodObject), new Among("assim", -1, 1, "", methodObject), new Among("essim", -1, 1, "", methodObject), 
			new Among("issim", -1, 1, "", methodObject), new Among("\340ssim", -1, 1, "", methodObject), new Among("\350ssim", -1, 1, "", methodObject), new Among("\351ssim", -1, 1, "", methodObject), new Among("\355ssim", -1, 1, "", methodObject), new Among("\357m", -1, 1, "", methodObject), new Among("an", -1, 1, "", methodObject), new Among("aban", 66, 1, "", methodObject), new Among("arian", 66, 1, "", methodObject), new Among("aran", 66, 1, "", methodObject), 
			new Among("ieran", 66, 1, "", methodObject), new Among("iran", 66, 1, "", methodObject), new Among("\355an", 66, 1, "", methodObject), new Among("ar\355an", 72, 1, "", methodObject), new Among("er\355an", 72, 1, "", methodObject), new Among("ir\355an", 72, 1, "", methodObject), new Among("en", -1, 1, "", methodObject), new Among("ien", 76, 1, "", methodObject), new Among("arien", 77, 1, "", methodObject), new Among("irien", 77, 1, "", methodObject), 
			new Among("aren", 76, 1, "", methodObject), new Among("eren", 76, 1, "", methodObject), new Among("iren", 76, 1, "", methodObject), new Among("\340ren", 76, 1, "", methodObject), new Among("\357ren", 76, 1, "", methodObject), new Among("asen", 76, 1, "", methodObject), new Among("iesen", 76, 1, "", methodObject), new Among("assen", 76, 1, "", methodObject), new Among("essen", 76, 1, "", methodObject), new Among("issen", 76, 1, "", methodObject), 
			new Among("\351ssen", 76, 1, "", methodObject), new Among("\357ssen", 76, 1, "", methodObject), new Among("esquen", 76, 1, "", methodObject), new Among("isquen", 76, 1, "", methodObject), new Among("\357squen", 76, 1, "", methodObject), new Among("aven", 76, 1, "", methodObject), new Among("ixen", 76, 1, "", methodObject), new Among("eixen", 96, 1, "", methodObject), new Among("\357xen", 76, 1, "", methodObject), new Among("\357en", 76, 1, "", methodObject), 
			new Among("in", -1, 1, "", methodObject), new Among("inin", 100, 1, "", methodObject), new Among("sin", 100, 1, "", methodObject), new Among("isin", 102, 1, "", methodObject), new Among("assin", 102, 1, "", methodObject), new Among("essin", 102, 1, "", methodObject), new Among("issin", 102, 1, "", methodObject), new Among("\357ssin", 102, 1, "", methodObject), new Among("esquin", 100, 1, "", methodObject), new Among("eixin", 100, 1, "", methodObject), 
			new Among("aron", -1, 1, "", methodObject), new Among("ieron", -1, 1, "", methodObject), new Among("ar\341n", -1, 1, "", methodObject), new Among("er\341n", -1, 1, "", methodObject), new Among("ir\341n", -1, 1, "", methodObject), new Among("i\357n", -1, 1, "", methodObject), new Among("ado", -1, 1, "", methodObject), new Among("ido", -1, 1, "", methodObject), new Among("ando", -1, 2, "", methodObject), new Among("iendo", -1, 1, "", methodObject), 
			new Among("io", -1, 1, "", methodObject), new Among("ixo", -1, 1, "", methodObject), new Among("eixo", 121, 1, "", methodObject), new Among("\357xo", -1, 1, "", methodObject), new Among("itzo", -1, 1, "", methodObject), new Among("ar", -1, 1, "", methodObject), new Among("tzar", 125, 1, "", methodObject), new Among("er", -1, 1, "", methodObject), new Among("eixer", 127, 1, "", methodObject), new Among("ir", -1, 1, "", methodObject), 
			new Among("ador", -1, 1, "", methodObject), new Among("as", -1, 1, "", methodObject), new Among("abas", 131, 1, "", methodObject), new Among("adas", 131, 1, "", methodObject), new Among("idas", 131, 1, "", methodObject), new Among("aras", 131, 1, "", methodObject), new Among("ieras", 131, 1, "", methodObject), new Among("\355as", 131, 1, "", methodObject), new Among("ar\355as", 137, 1, "", methodObject), new Among("er\355as", 137, 1, "", methodObject), 
			new Among("ir\355as", 137, 1, "", methodObject), new Among("ids", -1, 1, "", methodObject), new Among("es", -1, 1, "", methodObject), new Among("ades", 142, 1, "", methodObject), new Among("ides", 142, 1, "", methodObject), new Among("udes", 142, 1, "", methodObject), new Among("\357des", 142, 1, "", methodObject), new Among("atges", 142, 1, "", methodObject), new Among("ies", 142, 1, "", methodObject), new Among("aries", 148, 1, "", methodObject), 
			new Among("iries", 148, 1, "", methodObject), new Among("ares", 142, 1, "", methodObject), new Among("ires", 142, 1, "", methodObject), new Among("adores", 142, 1, "", methodObject), new Among("\357res", 142, 1, "", methodObject), new Among("ases", 142, 1, "", methodObject), new Among("ieses", 142, 1, "", methodObject), new Among("asses", 142, 1, "", methodObject), new Among("esses", 142, 1, "", methodObject), new Among("isses", 142, 1, "", methodObject), 
			new Among("\357sses", 142, 1, "", methodObject), new Among("ques", 142, 1, "", methodObject), new Among("esques", 161, 1, "", methodObject), new Among("\357sques", 161, 1, "", methodObject), new Among("aves", 142, 1, "", methodObject), new Among("ixes", 142, 1, "", methodObject), new Among("eixes", 165, 1, "", methodObject), new Among("\357xes", 142, 1, "", methodObject), new Among("\357es", 142, 1, "", methodObject), new Among("abais", -1, 1, "", methodObject), 
			new Among("arais", -1, 1, "", methodObject), new Among("ierais", -1, 1, "", methodObject), new Among("\355ais", -1, 1, "", methodObject), new Among("ar\355ais", 172, 1, "", methodObject), new Among("er\355ais", 172, 1, "", methodObject), new Among("ir\355ais", 172, 1, "", methodObject), new Among("aseis", -1, 1, "", methodObject), new Among("ieseis", -1, 1, "", methodObject), new Among("asteis", -1, 1, "", methodObject), new Among("isteis", -1, 1, "", methodObject), 
			new Among("inis", -1, 1, "", methodObject), new Among("sis", -1, 1, "", methodObject), new Among("isis", 181, 1, "", methodObject), new Among("assis", 181, 1, "", methodObject), new Among("essis", 181, 1, "", methodObject), new Among("issis", 181, 1, "", methodObject), new Among("\357ssis", 181, 1, "", methodObject), new Among("esquis", -1, 1, "", methodObject), new Among("eixis", -1, 1, "", methodObject), new Among("itzis", -1, 1, "", methodObject), 
			new Among("\341is", -1, 1, "", methodObject), new Among("ar\351is", -1, 1, "", methodObject), new Among("er\351is", -1, 1, "", methodObject), new Among("ir\351is", -1, 1, "", methodObject), new Among("ams", -1, 1, "", methodObject), new Among("ados", -1, 1, "", methodObject), new Among("idos", -1, 1, "", methodObject), new Among("amos", -1, 1, "", methodObject), new Among("\341bamos", 197, 1, "", methodObject), new Among("\341ramos", 197, 1, "", methodObject), 
			new Among("i\351ramos", 197, 1, "", methodObject), new Among("\355amos", 197, 1, "", methodObject), new Among("ar\355amos", 201, 1, "", methodObject), new Among("er\355amos", 201, 1, "", methodObject), new Among("ir\355amos", 201, 1, "", methodObject), new Among("aremos", -1, 1, "", methodObject), new Among("eremos", -1, 1, "", methodObject), new Among("iremos", -1, 1, "", methodObject), new Among("\341semos", -1, 1, "", methodObject), new Among("i\351semos", -1, 1, "", methodObject), 
			new Among("imos", -1, 1, "", methodObject), new Among("adors", -1, 1, "", methodObject), new Among("ass", -1, 1, "", methodObject), new Among("erass", 212, 1, "", methodObject), new Among("ess", -1, 1, "", methodObject), new Among("ats", -1, 1, "", methodObject), new Among("its", -1, 1, "", methodObject), new Among("ents", -1, 1, "", methodObject), new Among("\340s", -1, 1, "", methodObject), new Among("ar\340s", 218, 1, "", methodObject), 
			new Among("ir\340s", 218, 1, "", methodObject), new Among("ar\341s", -1, 1, "", methodObject), new Among("er\341s", -1, 1, "", methodObject), new Among("ir\341s", -1, 1, "", methodObject), new Among("\351s", -1, 1, "", methodObject), new Among("ar\351s", 224, 1, "", methodObject), new Among("\355s", -1, 1, "", methodObject), new Among("i\357s", -1, 1, "", methodObject), new Among("at", -1, 1, "", methodObject), new Among("it", -1, 1, "", methodObject), 
			new Among("ant", -1, 1, "", methodObject), new Among("ent", -1, 1, "", methodObject), new Among("int", -1, 1, "", methodObject), new Among("ut", -1, 1, "", methodObject), new Among("\357t", -1, 1, "", methodObject), new Among("au", -1, 1, "", methodObject), new Among("erau", 235, 1, "", methodObject), new Among("ieu", -1, 1, "", methodObject), new Among("ineu", -1, 1, "", methodObject), new Among("areu", -1, 1, "", methodObject), 
			new Among("ireu", -1, 1, "", methodObject), new Among("\340reu", -1, 1, "", methodObject), new Among("\355reu", -1, 1, "", methodObject), new Among("asseu", -1, 1, "", methodObject), new Among("esseu", -1, 1, "", methodObject), new Among("eresseu", 244, 1, "", methodObject), new Among("\340sseu", -1, 1, "", methodObject), new Among("\351sseu", -1, 1, "", methodObject), new Among("igueu", -1, 1, "", methodObject), new Among("\357gueu", -1, 1, "", methodObject), 
			new Among("\340veu", -1, 1, "", methodObject), new Among("\341veu", -1, 1, "", methodObject), new Among("itzeu", -1, 1, "", methodObject), new Among("\354eu", -1, 1, "", methodObject), new Among("ir\354eu", 253, 1, "", methodObject), new Among("\355eu", -1, 1, "", methodObject), new Among("ar\355eu", 255, 1, "", methodObject), new Among("ir\355eu", 255, 1, "", methodObject), new Among("assiu", -1, 1, "", methodObject), new Among("issiu", -1, 1, "", methodObject), 
			new Among("\340ssiu", -1, 1, "", methodObject), new Among("\350ssiu", -1, 1, "", methodObject), new Among("\351ssiu", -1, 1, "", methodObject), new Among("\355ssiu", -1, 1, "", methodObject), new Among("\357u", -1, 1, "", methodObject), new Among("ix", -1, 1, "", methodObject), new Among("eix", 265, 1, "", methodObject), new Among("\357x", -1, 1, "", methodObject), new Among("itz", -1, 1, "", methodObject), new Among("i\340", -1, 1, "", methodObject), 
			new Among("ar\340", -1, 1, "", methodObject), new Among("ir\340", -1, 1, "", methodObject), new Among("itz\340", -1, 1, "", methodObject), new Among("ar\341", -1, 1, "", methodObject), new Among("er\341", -1, 1, "", methodObject), new Among("ir\341", -1, 1, "", methodObject), new Among("ir\350", -1, 1, "", methodObject), new Among("ar\351", -1, 1, "", methodObject), new Among("er\351", -1, 1, "", methodObject), new Among("ir\351", -1, 1, "", methodObject), 
			new Among("\355", -1, 1, "", methodObject), new Among("i\357", -1, 1, "", methodObject), new Among("i\363", -1, 1, "", methodObject)
		});
		a_4 = (new Among[] {
			new Among("a", -1, 1, "", methodObject), new Among("e", -1, 1, "", methodObject), new Among("i", -1, 1, "", methodObject), new Among("\357n", -1, 1, "", methodObject), new Among("o", -1, 1, "", methodObject), new Among("ir", -1, 1, "", methodObject), new Among("s", -1, 1, "", methodObject), new Among("is", 6, 1, "", methodObject), new Among("os", 6, 1, "", methodObject), new Among("\357s", 6, 1, "", methodObject), 
			new Among("it", -1, 1, "", methodObject), new Among("eu", -1, 1, "", methodObject), new Among("iu", -1, 1, "", methodObject), new Among("iqu", -1, 2, "", methodObject), new Among("itz", -1, 1, "", methodObject), new Among("\340", -1, 1, "", methodObject), new Among("\341", -1, 1, "", methodObject), new Among("\351", -1, 1, "", methodObject), new Among("\354", -1, 1, "", methodObject), new Among("\355", -1, 1, "", methodObject), 
			new Among("\357", -1, 1, "", methodObject), new Among("\363", -1, 1, "", methodObject)
		});
	}
}
