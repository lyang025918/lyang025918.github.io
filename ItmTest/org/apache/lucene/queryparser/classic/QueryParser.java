// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   QueryParser.java

package org.apache.lucene.queryparser.classic;

import java.io.StringReader;
import java.util.*;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;

// Referenced classes of package org.apache.lucene.queryparser.classic:
//			QueryParserBase, FastCharStream, ParseException, QueryParserTokenManager, 
//			Token, QueryParserConstants, CharStream

public class QueryParser extends QueryParserBase
	implements QueryParserConstants
{
	static final class JJCalls
	{

		int gen;
		Token first;
		int arg;
		JJCalls next;

		JJCalls()
		{
		}
	}

	private static final class LookaheadSuccess extends Error
	{

		private LookaheadSuccess()
		{
		}

	}

	public static final class Operator extends Enum
	{

		public static final Operator OR;
		public static final Operator AND;
		private static final Operator $VALUES[];

		public static Operator[] values()
		{
			return (Operator[])$VALUES.clone();
		}

		public static Operator valueOf(String name)
		{
			return (Operator)Enum.valueOf(org/apache/lucene/queryparser/classic/QueryParser$Operator, name);
		}

		static 
		{
			OR = new Operator("OR", 0);
			AND = new Operator("AND", 1);
			$VALUES = (new Operator[] {
				OR, AND
			});
		}

		private Operator(String s, int i)
		{
			super(s, i);
		}
	}


	public QueryParserTokenManager token_source;
	public Token token;
	public Token jj_nt;
	private int jj_ntk;
	private Token jj_scanpos;
	private Token jj_lastpos;
	private int jj_la;
	private int jj_gen;
	private final int jj_la1[];
	private static int jj_la1_0[];
	private static int jj_la1_1[];
	private final JJCalls jj_2_rtns[];
	private boolean jj_rescan;
	private int jj_gc;
	private final LookaheadSuccess jj_ls;
	private List jj_expentries;
	private int jj_expentry[];
	private int jj_kind;
	private int jj_lasttokens[];
	private int jj_endpos;

	public QueryParser(Version matchVersion, String f, Analyzer a)
	{
		this(((CharStream) (new FastCharStream(new StringReader("")))));
		init(matchVersion, f, a);
	}

	public final int Conjunction()
		throws ParseException
	{
		int ret = 0;
		switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
		{
		case 8: // '\b'
		case 9: // '\t'
			switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
			{
			case 8: // '\b'
				jj_consume_token(8);
				ret = 1;
				break;

			case 9: // '\t'
				jj_consume_token(9);
				ret = 2;
				break;

			default:
				jj_la1[0] = jj_gen;
				jj_consume_token(-1);
				throw new ParseException();
			}
			break;

		default:
			jj_la1[1] = jj_gen;
			break;
		}
		return ret;
	}

	public final int Modifiers()
		throws ParseException
	{
		int ret = 0;
		switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
		{
		case 10: // '\n'
		case 11: // '\013'
		case 12: // '\f'
			switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
			{
			case 11: // '\013'
				jj_consume_token(11);
				ret = 11;
				break;

			case 12: // '\f'
				jj_consume_token(12);
				ret = 10;
				break;

			case 10: // '\n'
				jj_consume_token(10);
				ret = 10;
				break;

			default:
				jj_la1[2] = jj_gen;
				jj_consume_token(-1);
				throw new ParseException();
			}
			break;

		default:
			jj_la1[3] = jj_gen;
			break;
		}
		return ret;
	}

	public final Query TopLevelQuery(String field)
		throws ParseException
	{
		Query q = Query(field);
		jj_consume_token(0);
		return q;
	}

	public final Query Query(String field)
		throws ParseException
	{
		List clauses = new ArrayList();
		Query firstQuery = null;
		int mods = Modifiers();
		Query q = Clause(field);
		addClause(clauses, 0, mods, q);
		if (mods == 0)
			firstQuery = q;
label0:
		do
			switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
			{
			case 15: // '\017'
			case 16: // '\020'
			case 18: // '\022'
			case 21: // '\025'
			default:
				jj_la1[4] = jj_gen;
				break label0;

			case 8: // '\b'
			case 9: // '\t'
			case 10: // '\n'
			case 11: // '\013'
			case 12: // '\f'
			case 13: // '\r'
			case 14: // '\016'
			case 17: // '\021'
			case 19: // '\023'
			case 20: // '\024'
			case 22: // '\026'
			case 23: // '\027'
			case 24: // '\030'
			case 25: // '\031'
			case 26: // '\032'
			case 27: // '\033'
				int conj = Conjunction();
				mods = Modifiers();
				q = Clause(field);
				addClause(clauses, conj, mods, q);
				break;
			}
		while (true);
		if (clauses.size() == 1 && firstQuery != null)
			return firstQuery;
		else
			return getBooleanQuery(clauses);
	}

	public final Query Clause(String field)
		throws ParseException
	{
		Token fieldToken = null;
		Token boost = null;
		if (jj_2_1(2))
			switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
			{
			case 20: // '\024'
				fieldToken = jj_consume_token(20);
				jj_consume_token(16);
				field = discardEscapeChar(fieldToken.image);
				break;

			case 17: // '\021'
				jj_consume_token(17);
				jj_consume_token(16);
				field = "*";
				break;

			default:
				jj_la1[5] = jj_gen;
				jj_consume_token(-1);
				throw new ParseException();
			}
		Query q;
		switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
		{
		case 13: // '\r'
		case 17: // '\021'
		case 19: // '\023'
		case 20: // '\024'
		case 22: // '\026'
		case 23: // '\027'
		case 24: // '\030'
		case 25: // '\031'
		case 26: // '\032'
		case 27: // '\033'
			q = Term(field);
			break;

		case 14: // '\016'
			jj_consume_token(14);
			q = Query(field);
			jj_consume_token(15);
			switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
			{
			case 18: // '\022'
				jj_consume_token(18);
				boost = jj_consume_token(27);
				break;

			default:
				jj_la1[6] = jj_gen;
				break;
			}
			break;

		case 15: // '\017'
		case 16: // '\020'
		case 18: // '\022'
		case 21: // '\025'
		default:
			jj_la1[7] = jj_gen;
			jj_consume_token(-1);
			throw new ParseException();
		}
		return handleBoost(q, boost);
	}

	public final Query Term(String field)
		throws ParseException
	{
		Token boost = null;
		Token fuzzySlop = null;
		boolean prefix = false;
		boolean wildcard = false;
		boolean fuzzy = false;
		boolean regexp = false;
		boolean startInc = false;
		boolean endInc = false;
		Query q;
		switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
		{
		case 13: // '\r'
		case 17: // '\021'
		case 20: // '\024'
		case 22: // '\026'
		case 23: // '\027'
		case 24: // '\030'
		case 27: // '\033'
		{
			Token term;
			switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
			{
			case 20: // '\024'
				term = jj_consume_token(20);
				break;

			case 17: // '\021'
				term = jj_consume_token(17);
				wildcard = true;
				break;

			case 22: // '\026'
				term = jj_consume_token(22);
				prefix = true;
				break;

			case 23: // '\027'
				term = jj_consume_token(23);
				wildcard = true;
				break;

			case 24: // '\030'
				term = jj_consume_token(24);
				regexp = true;
				break;

			case 27: // '\033'
				term = jj_consume_token(27);
				break;

			case 13: // '\r'
				term = jj_consume_token(13);
				term.image = term.image.substring(0, 1);
				break;

			case 14: // '\016'
			case 15: // '\017'
			case 16: // '\020'
			case 18: // '\022'
			case 19: // '\023'
			case 21: // '\025'
			case 25: // '\031'
			case 26: // '\032'
			default:
				jj_la1[8] = jj_gen;
				jj_consume_token(-1);
				throw new ParseException();
			}
			switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
			{
			case 21: // '\025'
				fuzzySlop = jj_consume_token(21);
				fuzzy = true;
				break;

			default:
				jj_la1[9] = jj_gen;
				break;
			}
			switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
			{
			case 18: // '\022'
				jj_consume_token(18);
				boost = jj_consume_token(27);
				switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
				{
				case 21: // '\025'
					fuzzySlop = jj_consume_token(21);
					fuzzy = true;
					break;

				default:
					jj_la1[10] = jj_gen;
					break;
				}
				break;

			default:
				jj_la1[11] = jj_gen;
				break;
			}
			q = handleBareTokenQuery(field, term, fuzzySlop, prefix, wildcard, fuzzy, regexp);
			break;
		}

		case 25: // '\031'
		case 26: // '\032'
		{
			switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
			{
			case 25: // '\031'
				jj_consume_token(25);
				startInc = true;
				break;

			case 26: // '\032'
				jj_consume_token(26);
				break;

			default:
				jj_la1[12] = jj_gen;
				jj_consume_token(-1);
				throw new ParseException();
			}
			Token goop1;
			switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
			{
			case 32: // ' '
				goop1 = jj_consume_token(32);
				break;

			case 31: // '\037'
				goop1 = jj_consume_token(31);
				break;

			default:
				jj_la1[13] = jj_gen;
				jj_consume_token(-1);
				throw new ParseException();
			}
			switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
			{
			case 28: // '\034'
				jj_consume_token(28);
				break;

			default:
				jj_la1[14] = jj_gen;
				break;
			}
			Token goop2;
			switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
			{
			case 32: // ' '
				goop2 = jj_consume_token(32);
				break;

			case 31: // '\037'
				goop2 = jj_consume_token(31);
				break;

			default:
				jj_la1[15] = jj_gen;
				jj_consume_token(-1);
				throw new ParseException();
			}
			switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
			{
			case 29: // '\035'
				jj_consume_token(29);
				endInc = true;
				break;

			case 30: // '\036'
				jj_consume_token(30);
				break;

			default:
				jj_la1[16] = jj_gen;
				jj_consume_token(-1);
				throw new ParseException();
			}
			switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
			{
			case 18: // '\022'
				jj_consume_token(18);
				boost = jj_consume_token(27);
				break;

			default:
				jj_la1[17] = jj_gen;
				break;
			}
			boolean startOpen = false;
			boolean endOpen = false;
			if (goop1.kind == 31)
				goop1.image = goop1.image.substring(1, goop1.image.length() - 1);
			else
			if ("*".equals(goop1.image))
				startOpen = true;
			if (goop2.kind == 31)
				goop2.image = goop2.image.substring(1, goop2.image.length() - 1);
			else
			if ("*".equals(goop2.image))
				endOpen = true;
			q = getRangeQuery(field, startOpen ? null : discardEscapeChar(goop1.image), endOpen ? null : discardEscapeChar(goop2.image), startInc, endInc);
			break;
		}

		case 19: // '\023'
		{
			Token term = jj_consume_token(19);
			switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
			{
			case 21: // '\025'
				fuzzySlop = jj_consume_token(21);
				break;

			default:
				jj_la1[18] = jj_gen;
				break;
			}
			switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
			{
			case 18: // '\022'
				jj_consume_token(18);
				boost = jj_consume_token(27);
				break;

			default:
				jj_la1[19] = jj_gen;
				break;
			}
			q = handleQuotedTerm(field, term, fuzzySlop);
			break;
		}

		case 14: // '\016'
		case 15: // '\017'
		case 16: // '\020'
		case 18: // '\022'
		case 21: // '\025'
		default:
		{
			jj_la1[20] = jj_gen;
			jj_consume_token(-1);
			throw new ParseException();
		}
		}
		return handleBoost(q, boost);
	}

	private boolean jj_2_1(int xla)
	{
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		boolean flag = !jj_3_1();
		jj_save(0, xla);
		return flag;
		LookaheadSuccess ls;
		ls;
		boolean flag1 = true;
		jj_save(0, xla);
		return flag1;
		Exception exception;
		exception;
		jj_save(0, xla);
		throw exception;
	}

	private boolean jj_3R_2()
	{
		if (jj_scan_token(20))
			return true;
		return jj_scan_token(16);
	}

	private boolean jj_3_1()
	{
		Token xsp = jj_scanpos;
		if (jj_3R_2())
		{
			jj_scanpos = xsp;
			if (jj_3R_3())
				return true;
		}
		return false;
	}

	private boolean jj_3R_3()
	{
		if (jj_scan_token(17))
			return true;
		return jj_scan_token(16);
	}

	private static void jj_la1_init_0()
	{
		jj_la1_0 = (new int[] {
			768, 768, 7168, 7168, 0xfda7f00, 0x120000, 0x40000, 0xfda6000, 0x9d22000, 0x200000, 
			0x200000, 0x40000, 0x6000000, 0x80000000, 0x10000000, 0x80000000, 0x60000000, 0x40000, 0x200000, 0x40000, 
			0xfda2000
		});
	}

	private static void jj_la1_init_1()
	{
		jj_la1_1 = (new int[] {
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
			0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 
			0
		});
	}

	protected QueryParser(CharStream stream)
	{
		jj_la1 = new int[21];
		jj_2_rtns = new JJCalls[1];
		jj_rescan = false;
		jj_gc = 0;
		jj_ls = new LookaheadSuccess();
		jj_expentries = new ArrayList();
		jj_kind = -1;
		jj_lasttokens = new int[100];
		token_source = new QueryParserTokenManager(stream);
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 21; i++)
			jj_la1[i] = -1;

		for (int i = 0; i < jj_2_rtns.length; i++)
			jj_2_rtns[i] = new JJCalls();

	}

	public void ReInit(CharStream stream)
	{
		token_source.ReInit(stream);
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 21; i++)
			jj_la1[i] = -1;

		for (int i = 0; i < jj_2_rtns.length; i++)
			jj_2_rtns[i] = new JJCalls();

	}

	protected QueryParser(QueryParserTokenManager tm)
	{
		jj_la1 = new int[21];
		jj_2_rtns = new JJCalls[1];
		jj_rescan = false;
		jj_gc = 0;
		jj_ls = new LookaheadSuccess();
		jj_expentries = new ArrayList();
		jj_kind = -1;
		jj_lasttokens = new int[100];
		token_source = tm;
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 21; i++)
			jj_la1[i] = -1;

		for (int i = 0; i < jj_2_rtns.length; i++)
			jj_2_rtns[i] = new JJCalls();

	}

	public void ReInit(QueryParserTokenManager tm)
	{
		token_source = tm;
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 21; i++)
			jj_la1[i] = -1;

		for (int i = 0; i < jj_2_rtns.length; i++)
			jj_2_rtns[i] = new JJCalls();

	}

	private Token jj_consume_token(int kind)
		throws ParseException
	{
		Token oldToken;
		if ((oldToken = token).next != null)
			token = token.next;
		else
			token = token.next = token_source.getNextToken();
		jj_ntk = -1;
		if (token.kind == kind)
		{
			jj_gen++;
			if (++jj_gc > 100)
			{
				jj_gc = 0;
				for (int i = 0; i < jj_2_rtns.length; i++)
				{
					for (JJCalls c = jj_2_rtns[i]; c != null; c = c.next)
						if (c.gen < jj_gen)
							c.first = null;

				}

			}
			return token;
		} else
		{
			token = oldToken;
			jj_kind = kind;
			throw generateParseException();
		}
	}

	private boolean jj_scan_token(int kind)
	{
		if (jj_scanpos == jj_lastpos)
		{
			jj_la--;
			if (jj_scanpos.next == null)
				jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
			else
				jj_lastpos = jj_scanpos = jj_scanpos.next;
		} else
		{
			jj_scanpos = jj_scanpos.next;
		}
		if (jj_rescan)
		{
			int i = 0;
			Token tok;
			for (tok = token; tok != null && tok != jj_scanpos; tok = tok.next)
				i++;

			if (tok != null)
				jj_add_error_token(kind, i);
		}
		if (jj_scanpos.kind != kind)
			return true;
		if (jj_la == 0 && jj_scanpos == jj_lastpos)
			throw jj_ls;
		else
			return false;
	}

	public final Token getNextToken()
	{
		if (token.next != null)
			token = token.next;
		else
			token = token.next = token_source.getNextToken();
		jj_ntk = -1;
		jj_gen++;
		return token;
	}

	public final Token getToken(int index)
	{
		Token t = token;
		for (int i = 0; i < index; i++)
			if (t.next != null)
				t = t.next;
			else
				t = t.next = token_source.getNextToken();

		return t;
	}

	private int jj_ntk()
	{
		if ((jj_nt = token.next) == null)
			return jj_ntk = (token.next = token_source.getNextToken()).kind;
		else
			return jj_ntk = jj_nt.kind;
	}

	private void jj_add_error_token(int kind, int pos)
	{
label0:
		{
label1:
			{
				if (pos >= 100)
					return;
				if (pos == jj_endpos + 1)
				{
					jj_lasttokens[jj_endpos++] = kind;
					break label0;
				}
				if (jj_endpos == 0)
					break label0;
				jj_expentry = new int[jj_endpos];
				for (int i = 0; i < jj_endpos; i++)
					jj_expentry[i] = jj_lasttokens[i];

				Iterator it = jj_expentries.iterator();
label2:
				do
				{
					int oldentry[];
					do
					{
						if (!it.hasNext())
							break label1;
						oldentry = (int[])(int[])it.next();
					} while (oldentry.length != jj_expentry.length);
					int i = 0;
					do
					{
						if (i >= jj_expentry.length)
							break label2;
						if (oldentry[i] != jj_expentry[i])
							continue label2;
						i++;
					} while (true);
				} while (true);
				jj_expentries.add(jj_expentry);
			}
			if (pos != 0)
				jj_lasttokens[(jj_endpos = pos) - 1] = kind;
		}
	}

	public ParseException generateParseException()
	{
		jj_expentries.clear();
		boolean la1tokens[] = new boolean[33];
		if (jj_kind >= 0)
		{
			la1tokens[jj_kind] = true;
			jj_kind = -1;
		}
		for (int i = 0; i < 21; i++)
		{
			if (jj_la1[i] != jj_gen)
				continue;
			for (int j = 0; j < 32; j++)
			{
				if ((jj_la1_0[i] & 1 << j) != 0)
					la1tokens[j] = true;
				if ((jj_la1_1[i] & 1 << j) != 0)
					la1tokens[32 + j] = true;
			}

		}

		for (int i = 0; i < 33; i++)
			if (la1tokens[i])
			{
				jj_expentry = new int[1];
				jj_expentry[0] = i;
				jj_expentries.add(jj_expentry);
			}

		jj_endpos = 0;
		jj_rescan_token();
		jj_add_error_token(0, 0);
		int exptokseq[][] = new int[jj_expentries.size()][];
		for (int i = 0; i < jj_expentries.size(); i++)
			exptokseq[i] = (int[])jj_expentries.get(i);

		return new ParseException(token, exptokseq, tokenImage);
	}

	public final void enable_tracing()
	{
	}

	public final void disable_tracing()
	{
	}

	private void jj_rescan_token()
	{
		jj_rescan = true;
		for (int i = 0; i < 1; i++)
			try
			{
				JJCalls p = jj_2_rtns[i];
				do
				{
					if (p.gen > jj_gen)
					{
						jj_la = p.arg;
						jj_lastpos = jj_scanpos = p.first;
						switch (i)
						{
						case 0: // '\0'
							jj_3_1();
							break;
						}
					}
					p = p.next;
				} while (p != null);
			}
			catch (LookaheadSuccess ls) { }

		jj_rescan = false;
	}

	private void jj_save(int index, int xla)
	{
		JJCalls p = jj_2_rtns[index];
		do
		{
			if (p.gen <= jj_gen)
				break;
			if (p.next == null)
			{
				p = p.next = new JJCalls();
				break;
			}
			p = p.next;
		} while (true);
		p.gen = (jj_gen + xla) - jj_la;
		p.first = token;
		p.arg = xla;
	}

	static 
	{
		jj_la1_init_0();
		jj_la1_init_1();
	}
}
