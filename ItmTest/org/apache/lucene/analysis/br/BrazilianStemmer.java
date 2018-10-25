// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BrazilianStemmer.java

package org.apache.lucene.analysis.br;

import java.util.Locale;

public class BrazilianStemmer
{

	private static final Locale locale = new Locale("pt", "BR");
	private String TERM;
	private String CT;
	private String R1;
	private String R2;
	private String RV;

	public BrazilianStemmer()
	{
	}

	protected String stem(String term)
	{
		boolean altered = false;
		createCT(term);
		if (!isIndexable(CT))
			return null;
		if (!isStemmable(CT))
			return CT;
		R1 = getR1(CT);
		R2 = getR1(R1);
		RV = getRV(CT);
		TERM = (new StringBuilder()).append(term).append(";").append(CT).toString();
		altered = step1();
		if (!altered)
			altered = step2();
		if (altered)
			step3();
		else
			step4();
		step5();
		return CT;
	}

	private boolean isStemmable(String term)
	{
		for (int c = 0; c < term.length(); c++)
			if (!Character.isLetter(term.charAt(c)))
				return false;

		return true;
	}

	private boolean isIndexable(String term)
	{
		return term.length() < 30 && term.length() > 2;
	}

	private boolean isVowel(char value)
	{
		return value == 'a' || value == 'e' || value == 'i' || value == 'o' || value == 'u';
	}

	private String getR1(String value)
	{
		if (value == null)
			return null;
		int i = value.length() - 1;
		int j;
		for (j = 0; j < i && !isVowel(value.charAt(j)); j++);
		if (j >= i)
			return null;
		for (; j < i && isVowel(value.charAt(j)); j++);
		if (j >= i)
			return null;
		else
			return value.substring(j + 1);
	}

	private String getRV(String value)
	{
		if (value == null)
			return null;
		int i = value.length() - 1;
		if (i > 0 && !isVowel(value.charAt(1)))
		{
			int j;
			for (j = 2; j < i && !isVowel(value.charAt(j)); j++);
			if (j < i)
				return value.substring(j + 1);
		}
		if (i > 1 && isVowel(value.charAt(0)) && isVowel(value.charAt(1)))
		{
			int j;
			for (j = 2; j < i && isVowel(value.charAt(j)); j++);
			if (j < i)
				return value.substring(j + 1);
		}
		if (i > 2)
			return value.substring(3);
		else
			return null;
	}

	private String changeTerm(String value)
	{
		String r = "";
		if (value == null)
			return null;
		value = value.toLowerCase(locale);
		for (int j = 0; j < value.length(); j++)
		{
			if (value.charAt(j) == '\341' || value.charAt(j) == '\342' || value.charAt(j) == '\343')
			{
				r = (new StringBuilder()).append(r).append("a").toString();
				continue;
			}
			if (value.charAt(j) == '\351' || value.charAt(j) == '\352')
			{
				r = (new StringBuilder()).append(r).append("e").toString();
				continue;
			}
			if (value.charAt(j) == '\355')
			{
				r = (new StringBuilder()).append(r).append("i").toString();
				continue;
			}
			if (value.charAt(j) == '\363' || value.charAt(j) == '\364' || value.charAt(j) == '\365')
			{
				r = (new StringBuilder()).append(r).append("o").toString();
				continue;
			}
			if (value.charAt(j) == '\372' || value.charAt(j) == '\374')
			{
				r = (new StringBuilder()).append(r).append("u").toString();
				continue;
			}
			if (value.charAt(j) == '\347')
			{
				r = (new StringBuilder()).append(r).append("c").toString();
				continue;
			}
			if (value.charAt(j) == '\361')
				r = (new StringBuilder()).append(r).append("n").toString();
			else
				r = (new StringBuilder()).append(r).append(value.charAt(j)).toString();
		}

		return r;
	}

	private boolean suffix(String value, String suffix)
	{
		if (value == null || suffix == null)
			return false;
		if (suffix.length() > value.length())
			return false;
		else
			return value.substring(value.length() - suffix.length()).equals(suffix);
	}

	private String replaceSuffix(String value, String toReplace, String changeTo)
	{
		if (value == null || toReplace == null || changeTo == null)
			return value;
		String vvalue = removeSuffix(value, toReplace);
		if (value.equals(vvalue))
			return value;
		else
			return (new StringBuilder()).append(vvalue).append(changeTo).toString();
	}

	private String removeSuffix(String value, String toRemove)
	{
		if (value == null || toRemove == null || !suffix(value, toRemove))
			return value;
		else
			return value.substring(0, value.length() - toRemove.length());
	}

	private boolean suffixPreceded(String value, String suffix, String preceded)
	{
		if (value == null || suffix == null || preceded == null || !suffix(value, suffix))
			return false;
		else
			return suffix(removeSuffix(value, suffix), preceded);
	}

	private void createCT(String term)
	{
		CT = changeTerm(term);
		if (CT.length() < 2)
			return;
		if (CT.charAt(0) == '"' || CT.charAt(0) == '\'' || CT.charAt(0) == '-' || CT.charAt(0) == ',' || CT.charAt(0) == ';' || CT.charAt(0) == '.' || CT.charAt(0) == '?' || CT.charAt(0) == '!')
			CT = CT.substring(1);
		if (CT.length() < 2)
			return;
		if (CT.charAt(CT.length() - 1) == '-' || CT.charAt(CT.length() - 1) == ',' || CT.charAt(CT.length() - 1) == ';' || CT.charAt(CT.length() - 1) == '.' || CT.charAt(CT.length() - 1) == '?' || CT.charAt(CT.length() - 1) == '!' || CT.charAt(CT.length() - 1) == '\'' || CT.charAt(CT.length() - 1) == '"')
			CT = CT.substring(0, CT.length() - 1);
	}

	private boolean step1()
	{
		if (CT == null)
			return false;
		if (suffix(CT, "uciones") && suffix(R2, "uciones"))
		{
			CT = replaceSuffix(CT, "uciones", "u");
			return true;
		}
		if (CT.length() >= 6)
		{
			if (suffix(CT, "imentos") && suffix(R2, "imentos"))
			{
				CT = removeSuffix(CT, "imentos");
				return true;
			}
			if (suffix(CT, "amentos") && suffix(R2, "amentos"))
			{
				CT = removeSuffix(CT, "amentos");
				return true;
			}
			if (suffix(CT, "adores") && suffix(R2, "adores"))
			{
				CT = removeSuffix(CT, "adores");
				return true;
			}
			if (suffix(CT, "adoras") && suffix(R2, "adoras"))
			{
				CT = removeSuffix(CT, "adoras");
				return true;
			}
			if (suffix(CT, "logias") && suffix(R2, "logias"))
			{
				replaceSuffix(CT, "logias", "log");
				return true;
			}
			if (suffix(CT, "encias") && suffix(R2, "encias"))
			{
				CT = replaceSuffix(CT, "encias", "ente");
				return true;
			}
			if (suffix(CT, "amente") && suffix(R1, "amente"))
			{
				CT = removeSuffix(CT, "amente");
				return true;
			}
			if (suffix(CT, "idades") && suffix(R2, "idades"))
			{
				CT = removeSuffix(CT, "idades");
				return true;
			}
		}
		if (CT.length() >= 5)
		{
			if (suffix(CT, "acoes") && suffix(R2, "acoes"))
			{
				CT = removeSuffix(CT, "acoes");
				return true;
			}
			if (suffix(CT, "imento") && suffix(R2, "imento"))
			{
				CT = removeSuffix(CT, "imento");
				return true;
			}
			if (suffix(CT, "amento") && suffix(R2, "amento"))
			{
				CT = removeSuffix(CT, "amento");
				return true;
			}
			if (suffix(CT, "adora") && suffix(R2, "adora"))
			{
				CT = removeSuffix(CT, "adora");
				return true;
			}
			if (suffix(CT, "ismos") && suffix(R2, "ismos"))
			{
				CT = removeSuffix(CT, "ismos");
				return true;
			}
			if (suffix(CT, "istas") && suffix(R2, "istas"))
			{
				CT = removeSuffix(CT, "istas");
				return true;
			}
			if (suffix(CT, "logia") && suffix(R2, "logia"))
			{
				CT = replaceSuffix(CT, "logia", "log");
				return true;
			}
			if (suffix(CT, "ucion") && suffix(R2, "ucion"))
			{
				CT = replaceSuffix(CT, "ucion", "u");
				return true;
			}
			if (suffix(CT, "encia") && suffix(R2, "encia"))
			{
				CT = replaceSuffix(CT, "encia", "ente");
				return true;
			}
			if (suffix(CT, "mente") && suffix(R2, "mente"))
			{
				CT = removeSuffix(CT, "mente");
				return true;
			}
			if (suffix(CT, "idade") && suffix(R2, "idade"))
			{
				CT = removeSuffix(CT, "idade");
				return true;
			}
		}
		if (CT.length() >= 4)
		{
			if (suffix(CT, "acao") && suffix(R2, "acao"))
			{
				CT = removeSuffix(CT, "acao");
				return true;
			}
			if (suffix(CT, "ezas") && suffix(R2, "ezas"))
			{
				CT = removeSuffix(CT, "ezas");
				return true;
			}
			if (suffix(CT, "icos") && suffix(R2, "icos"))
			{
				CT = removeSuffix(CT, "icos");
				return true;
			}
			if (suffix(CT, "icas") && suffix(R2, "icas"))
			{
				CT = removeSuffix(CT, "icas");
				return true;
			}
			if (suffix(CT, "ismo") && suffix(R2, "ismo"))
			{
				CT = removeSuffix(CT, "ismo");
				return true;
			}
			if (suffix(CT, "avel") && suffix(R2, "avel"))
			{
				CT = removeSuffix(CT, "avel");
				return true;
			}
			if (suffix(CT, "ivel") && suffix(R2, "ivel"))
			{
				CT = removeSuffix(CT, "ivel");
				return true;
			}
			if (suffix(CT, "ista") && suffix(R2, "ista"))
			{
				CT = removeSuffix(CT, "ista");
				return true;
			}
			if (suffix(CT, "osos") && suffix(R2, "osos"))
			{
				CT = removeSuffix(CT, "osos");
				return true;
			}
			if (suffix(CT, "osas") && suffix(R2, "osas"))
			{
				CT = removeSuffix(CT, "osas");
				return true;
			}
			if (suffix(CT, "ador") && suffix(R2, "ador"))
			{
				CT = removeSuffix(CT, "ador");
				return true;
			}
			if (suffix(CT, "ivas") && suffix(R2, "ivas"))
			{
				CT = removeSuffix(CT, "ivas");
				return true;
			}
			if (suffix(CT, "ivos") && suffix(R2, "ivos"))
			{
				CT = removeSuffix(CT, "ivos");
				return true;
			}
			if (suffix(CT, "iras") && suffix(RV, "iras") && suffixPreceded(CT, "iras", "e"))
			{
				CT = replaceSuffix(CT, "iras", "ir");
				return true;
			}
		}
		if (CT.length() >= 3)
		{
			if (suffix(CT, "eza") && suffix(R2, "eza"))
			{
				CT = removeSuffix(CT, "eza");
				return true;
			}
			if (suffix(CT, "ico") && suffix(R2, "ico"))
			{
				CT = removeSuffix(CT, "ico");
				return true;
			}
			if (suffix(CT, "ica") && suffix(R2, "ica"))
			{
				CT = removeSuffix(CT, "ica");
				return true;
			}
			if (suffix(CT, "oso") && suffix(R2, "oso"))
			{
				CT = removeSuffix(CT, "oso");
				return true;
			}
			if (suffix(CT, "osa") && suffix(R2, "osa"))
			{
				CT = removeSuffix(CT, "osa");
				return true;
			}
			if (suffix(CT, "iva") && suffix(R2, "iva"))
			{
				CT = removeSuffix(CT, "iva");
				return true;
			}
			if (suffix(CT, "ivo") && suffix(R2, "ivo"))
			{
				CT = removeSuffix(CT, "ivo");
				return true;
			}
			if (suffix(CT, "ira") && suffix(RV, "ira") && suffixPreceded(CT, "ira", "e"))
			{
				CT = replaceSuffix(CT, "ira", "ir");
				return true;
			}
		}
		return false;
	}

	private boolean step2()
	{
		if (RV == null)
			return false;
		if (RV.length() >= 7)
		{
			if (suffix(RV, "issemos"))
			{
				CT = removeSuffix(CT, "issemos");
				return true;
			}
			if (suffix(RV, "essemos"))
			{
				CT = removeSuffix(CT, "essemos");
				return true;
			}
			if (suffix(RV, "assemos"))
			{
				CT = removeSuffix(CT, "assemos");
				return true;
			}
			if (suffix(RV, "ariamos"))
			{
				CT = removeSuffix(CT, "ariamos");
				return true;
			}
			if (suffix(RV, "eriamos"))
			{
				CT = removeSuffix(CT, "eriamos");
				return true;
			}
			if (suffix(RV, "iriamos"))
			{
				CT = removeSuffix(CT, "iriamos");
				return true;
			}
		}
		if (RV.length() >= 6)
		{
			if (suffix(RV, "iremos"))
			{
				CT = removeSuffix(CT, "iremos");
				return true;
			}
			if (suffix(RV, "eremos"))
			{
				CT = removeSuffix(CT, "eremos");
				return true;
			}
			if (suffix(RV, "aremos"))
			{
				CT = removeSuffix(CT, "aremos");
				return true;
			}
			if (suffix(RV, "avamos"))
			{
				CT = removeSuffix(CT, "avamos");
				return true;
			}
			if (suffix(RV, "iramos"))
			{
				CT = removeSuffix(CT, "iramos");
				return true;
			}
			if (suffix(RV, "eramos"))
			{
				CT = removeSuffix(CT, "eramos");
				return true;
			}
			if (suffix(RV, "aramos"))
			{
				CT = removeSuffix(CT, "aramos");
				return true;
			}
			if (suffix(RV, "asseis"))
			{
				CT = removeSuffix(CT, "asseis");
				return true;
			}
			if (suffix(RV, "esseis"))
			{
				CT = removeSuffix(CT, "esseis");
				return true;
			}
			if (suffix(RV, "isseis"))
			{
				CT = removeSuffix(CT, "isseis");
				return true;
			}
			if (suffix(RV, "arieis"))
			{
				CT = removeSuffix(CT, "arieis");
				return true;
			}
			if (suffix(RV, "erieis"))
			{
				CT = removeSuffix(CT, "erieis");
				return true;
			}
			if (suffix(RV, "irieis"))
			{
				CT = removeSuffix(CT, "irieis");
				return true;
			}
		}
		if (RV.length() >= 5)
		{
			if (suffix(RV, "irmos"))
			{
				CT = removeSuffix(CT, "irmos");
				return true;
			}
			if (suffix(RV, "iamos"))
			{
				CT = removeSuffix(CT, "iamos");
				return true;
			}
			if (suffix(RV, "armos"))
			{
				CT = removeSuffix(CT, "armos");
				return true;
			}
			if (suffix(RV, "ermos"))
			{
				CT = removeSuffix(CT, "ermos");
				return true;
			}
			if (suffix(RV, "areis"))
			{
				CT = removeSuffix(CT, "areis");
				return true;
			}
			if (suffix(RV, "ereis"))
			{
				CT = removeSuffix(CT, "ereis");
				return true;
			}
			if (suffix(RV, "ireis"))
			{
				CT = removeSuffix(CT, "ireis");
				return true;
			}
			if (suffix(RV, "asses"))
			{
				CT = removeSuffix(CT, "asses");
				return true;
			}
			if (suffix(RV, "esses"))
			{
				CT = removeSuffix(CT, "esses");
				return true;
			}
			if (suffix(RV, "isses"))
			{
				CT = removeSuffix(CT, "isses");
				return true;
			}
			if (suffix(RV, "astes"))
			{
				CT = removeSuffix(CT, "astes");
				return true;
			}
			if (suffix(RV, "assem"))
			{
				CT = removeSuffix(CT, "assem");
				return true;
			}
			if (suffix(RV, "essem"))
			{
				CT = removeSuffix(CT, "essem");
				return true;
			}
			if (suffix(RV, "issem"))
			{
				CT = removeSuffix(CT, "issem");
				return true;
			}
			if (suffix(RV, "ardes"))
			{
				CT = removeSuffix(CT, "ardes");
				return true;
			}
			if (suffix(RV, "erdes"))
			{
				CT = removeSuffix(CT, "erdes");
				return true;
			}
			if (suffix(RV, "irdes"))
			{
				CT = removeSuffix(CT, "irdes");
				return true;
			}
			if (suffix(RV, "ariam"))
			{
				CT = removeSuffix(CT, "ariam");
				return true;
			}
			if (suffix(RV, "eriam"))
			{
				CT = removeSuffix(CT, "eriam");
				return true;
			}
			if (suffix(RV, "iriam"))
			{
				CT = removeSuffix(CT, "iriam");
				return true;
			}
			if (suffix(RV, "arias"))
			{
				CT = removeSuffix(CT, "arias");
				return true;
			}
			if (suffix(RV, "erias"))
			{
				CT = removeSuffix(CT, "erias");
				return true;
			}
			if (suffix(RV, "irias"))
			{
				CT = removeSuffix(CT, "irias");
				return true;
			}
			if (suffix(RV, "estes"))
			{
				CT = removeSuffix(CT, "estes");
				return true;
			}
			if (suffix(RV, "istes"))
			{
				CT = removeSuffix(CT, "istes");
				return true;
			}
			if (suffix(RV, "areis"))
			{
				CT = removeSuffix(CT, "areis");
				return true;
			}
			if (suffix(RV, "aveis"))
			{
				CT = removeSuffix(CT, "aveis");
				return true;
			}
		}
		if (RV.length() >= 4)
		{
			if (suffix(RV, "aria"))
			{
				CT = removeSuffix(CT, "aria");
				return true;
			}
			if (suffix(RV, "eria"))
			{
				CT = removeSuffix(CT, "eria");
				return true;
			}
			if (suffix(RV, "iria"))
			{
				CT = removeSuffix(CT, "iria");
				return true;
			}
			if (suffix(RV, "asse"))
			{
				CT = removeSuffix(CT, "asse");
				return true;
			}
			if (suffix(RV, "esse"))
			{
				CT = removeSuffix(CT, "esse");
				return true;
			}
			if (suffix(RV, "isse"))
			{
				CT = removeSuffix(CT, "isse");
				return true;
			}
			if (suffix(RV, "aste"))
			{
				CT = removeSuffix(CT, "aste");
				return true;
			}
			if (suffix(RV, "este"))
			{
				CT = removeSuffix(CT, "este");
				return true;
			}
			if (suffix(RV, "iste"))
			{
				CT = removeSuffix(CT, "iste");
				return true;
			}
			if (suffix(RV, "arei"))
			{
				CT = removeSuffix(CT, "arei");
				return true;
			}
			if (suffix(RV, "erei"))
			{
				CT = removeSuffix(CT, "erei");
				return true;
			}
			if (suffix(RV, "irei"))
			{
				CT = removeSuffix(CT, "irei");
				return true;
			}
			if (suffix(RV, "aram"))
			{
				CT = removeSuffix(CT, "aram");
				return true;
			}
			if (suffix(RV, "eram"))
			{
				CT = removeSuffix(CT, "eram");
				return true;
			}
			if (suffix(RV, "iram"))
			{
				CT = removeSuffix(CT, "iram");
				return true;
			}
			if (suffix(RV, "avam"))
			{
				CT = removeSuffix(CT, "avam");
				return true;
			}
			if (suffix(RV, "arem"))
			{
				CT = removeSuffix(CT, "arem");
				return true;
			}
			if (suffix(RV, "erem"))
			{
				CT = removeSuffix(CT, "erem");
				return true;
			}
			if (suffix(RV, "irem"))
			{
				CT = removeSuffix(CT, "irem");
				return true;
			}
			if (suffix(RV, "ando"))
			{
				CT = removeSuffix(CT, "ando");
				return true;
			}
			if (suffix(RV, "endo"))
			{
				CT = removeSuffix(CT, "endo");
				return true;
			}
			if (suffix(RV, "indo"))
			{
				CT = removeSuffix(CT, "indo");
				return true;
			}
			if (suffix(RV, "arao"))
			{
				CT = removeSuffix(CT, "arao");
				return true;
			}
			if (suffix(RV, "erao"))
			{
				CT = removeSuffix(CT, "erao");
				return true;
			}
			if (suffix(RV, "irao"))
			{
				CT = removeSuffix(CT, "irao");
				return true;
			}
			if (suffix(RV, "adas"))
			{
				CT = removeSuffix(CT, "adas");
				return true;
			}
			if (suffix(RV, "idas"))
			{
				CT = removeSuffix(CT, "idas");
				return true;
			}
			if (suffix(RV, "aras"))
			{
				CT = removeSuffix(CT, "aras");
				return true;
			}
			if (suffix(RV, "eras"))
			{
				CT = removeSuffix(CT, "eras");
				return true;
			}
			if (suffix(RV, "iras"))
			{
				CT = removeSuffix(CT, "iras");
				return true;
			}
			if (suffix(RV, "avas"))
			{
				CT = removeSuffix(CT, "avas");
				return true;
			}
			if (suffix(RV, "ares"))
			{
				CT = removeSuffix(CT, "ares");
				return true;
			}
			if (suffix(RV, "eres"))
			{
				CT = removeSuffix(CT, "eres");
				return true;
			}
			if (suffix(RV, "ires"))
			{
				CT = removeSuffix(CT, "ires");
				return true;
			}
			if (suffix(RV, "ados"))
			{
				CT = removeSuffix(CT, "ados");
				return true;
			}
			if (suffix(RV, "idos"))
			{
				CT = removeSuffix(CT, "idos");
				return true;
			}
			if (suffix(RV, "amos"))
			{
				CT = removeSuffix(CT, "amos");
				return true;
			}
			if (suffix(RV, "emos"))
			{
				CT = removeSuffix(CT, "emos");
				return true;
			}
			if (suffix(RV, "imos"))
			{
				CT = removeSuffix(CT, "imos");
				return true;
			}
			if (suffix(RV, "iras"))
			{
				CT = removeSuffix(CT, "iras");
				return true;
			}
			if (suffix(RV, "ieis"))
			{
				CT = removeSuffix(CT, "ieis");
				return true;
			}
		}
		if (RV.length() >= 3)
		{
			if (suffix(RV, "ada"))
			{
				CT = removeSuffix(CT, "ada");
				return true;
			}
			if (suffix(RV, "ida"))
			{
				CT = removeSuffix(CT, "ida");
				return true;
			}
			if (suffix(RV, "ara"))
			{
				CT = removeSuffix(CT, "ara");
				return true;
			}
			if (suffix(RV, "era"))
			{
				CT = removeSuffix(CT, "era");
				return true;
			}
			if (suffix(RV, "ira"))
			{
				CT = removeSuffix(CT, "ava");
				return true;
			}
			if (suffix(RV, "iam"))
			{
				CT = removeSuffix(CT, "iam");
				return true;
			}
			if (suffix(RV, "ado"))
			{
				CT = removeSuffix(CT, "ado");
				return true;
			}
			if (suffix(RV, "ido"))
			{
				CT = removeSuffix(CT, "ido");
				return true;
			}
			if (suffix(RV, "ias"))
			{
				CT = removeSuffix(CT, "ias");
				return true;
			}
			if (suffix(RV, "ais"))
			{
				CT = removeSuffix(CT, "ais");
				return true;
			}
			if (suffix(RV, "eis"))
			{
				CT = removeSuffix(CT, "eis");
				return true;
			}
			if (suffix(RV, "ira"))
			{
				CT = removeSuffix(CT, "ira");
				return true;
			}
			if (suffix(RV, "ear"))
			{
				CT = removeSuffix(CT, "ear");
				return true;
			}
		}
		if (RV.length() >= 2)
		{
			if (suffix(RV, "ia"))
			{
				CT = removeSuffix(CT, "ia");
				return true;
			}
			if (suffix(RV, "ei"))
			{
				CT = removeSuffix(CT, "ei");
				return true;
			}
			if (suffix(RV, "am"))
			{
				CT = removeSuffix(CT, "am");
				return true;
			}
			if (suffix(RV, "em"))
			{
				CT = removeSuffix(CT, "em");
				return true;
			}
			if (suffix(RV, "ar"))
			{
				CT = removeSuffix(CT, "ar");
				return true;
			}
			if (suffix(RV, "er"))
			{
				CT = removeSuffix(CT, "er");
				return true;
			}
			if (suffix(RV, "ir"))
			{
				CT = removeSuffix(CT, "ir");
				return true;
			}
			if (suffix(RV, "as"))
			{
				CT = removeSuffix(CT, "as");
				return true;
			}
			if (suffix(RV, "es"))
			{
				CT = removeSuffix(CT, "es");
				return true;
			}
			if (suffix(RV, "is"))
			{
				CT = removeSuffix(CT, "is");
				return true;
			}
			if (suffix(RV, "eu"))
			{
				CT = removeSuffix(CT, "eu");
				return true;
			}
			if (suffix(RV, "iu"))
			{
				CT = removeSuffix(CT, "iu");
				return true;
			}
			if (suffix(RV, "iu"))
			{
				CT = removeSuffix(CT, "iu");
				return true;
			}
			if (suffix(RV, "ou"))
			{
				CT = removeSuffix(CT, "ou");
				return true;
			}
		}
		return false;
	}

	private void step3()
	{
		if (RV == null)
			return;
		if (suffix(RV, "i") && suffixPreceded(RV, "i", "c"))
			CT = removeSuffix(CT, "i");
	}

	private void step4()
	{
		if (RV == null)
			return;
		if (suffix(RV, "os"))
		{
			CT = removeSuffix(CT, "os");
			return;
		}
		if (suffix(RV, "a"))
		{
			CT = removeSuffix(CT, "a");
			return;
		}
		if (suffix(RV, "i"))
		{
			CT = removeSuffix(CT, "i");
			return;
		}
		if (suffix(RV, "o"))
		{
			CT = removeSuffix(CT, "o");
			return;
		} else
		{
			return;
		}
	}

	private void step5()
	{
		if (RV == null)
			return;
		if (suffix(RV, "e"))
		{
			if (suffixPreceded(RV, "e", "gu"))
			{
				CT = removeSuffix(CT, "e");
				CT = removeSuffix(CT, "u");
				return;
			}
			if (suffixPreceded(RV, "e", "ci"))
			{
				CT = removeSuffix(CT, "e");
				CT = removeSuffix(CT, "i");
				return;
			} else
			{
				CT = removeSuffix(CT, "e");
				return;
			}
		} else
		{
			return;
		}
	}

	public String log()
	{
		return (new StringBuilder()).append(" (TERM = ").append(TERM).append(")").append(" (CT = ").append(CT).append(")").append(" (RV = ").append(RV).append(")").append(" (R1 = ").append(R1).append(")").append(" (R2 = ").append(R2).append(")").toString();
	}

}
