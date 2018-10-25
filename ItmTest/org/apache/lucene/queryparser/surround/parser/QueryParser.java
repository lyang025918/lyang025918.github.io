// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   QueryParser.java

package org.apache.lucene.queryparser.surround.parser;

import java.io.StringReader;
import java.util.*;
import org.apache.lucene.queryparser.surround.query.*;

// Referenced classes of package org.apache.lucene.queryparser.surround.parser:
//			FastCharStream, TokenMgrError, ParseException, QueryParserTokenManager, 
//			Token, QueryParserConstants, CharStream

public class QueryParser
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


	final int minimumPrefixLength = 3;
	final int minimumCharsInTrunc = 3;
	final String truncationErrorMessage = "Too unrestrictive truncation: ";
	final String boostErrorMessage = "Cannot handle boost value: ";
	final char truncator = '*';
	final char anyChar = '?';
	final char quote = '"';
	final char fieldOperator = ':';
	final char comma = ',';
	final char carat = '^';
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
	private final JJCalls jj_2_rtns[];
	private boolean jj_rescan;
	private int jj_gc;
	private final LookaheadSuccess jj_ls;
	private List jj_expentries;
	private int jj_expentry[];
	private int jj_kind;
	private int jj_lasttokens[];
	private int jj_endpos;

	public static SrndQuery parse(String query)
		throws ParseException
	{
		QueryParser parser = new QueryParser();
		return parser.parse2(query);
	}

	public QueryParser()
	{
		this(((CharStream) (new FastCharStream(new StringReader("")))));
	}

	public SrndQuery parse2(String query)
		throws ParseException
	{
		ReInit(new FastCharStream(new StringReader(query)));
		return TopSrndQuery();
		TokenMgrError tme;
		tme;
		throw new ParseException(tme.getMessage());
	}

	protected SrndQuery getFieldsQuery(SrndQuery q, ArrayList fieldNames)
	{
		return new FieldsQuery(q, fieldNames, ':');
	}

	protected SrndQuery getOrQuery(List queries, boolean infix, Token orToken)
	{
		return new OrQuery(queries, infix, orToken.image);
	}

	protected SrndQuery getAndQuery(List queries, boolean infix, Token andToken)
	{
		return new AndQuery(queries, infix, andToken.image);
	}

	protected SrndQuery getNotQuery(List queries, Token notToken)
	{
		return new NotQuery(queries, notToken.image);
	}

	protected static int getOpDistance(String distanceOp)
	{
		return distanceOp.length() != 1 ? Integer.parseInt(distanceOp.substring(0, distanceOp.length() - 1)) : 1;
	}

	protected static void checkDistanceSubQueries(DistanceQuery distq, String opName)
		throws ParseException
	{
		String m = distq.distanceSubQueryNotAllowed();
		if (m != null)
			throw new ParseException((new StringBuilder()).append("Operator ").append(opName).append(": ").append(m).toString());
		else
			return;
	}

	protected SrndQuery getDistanceQuery(List queries, boolean infix, Token dToken, boolean ordered)
		throws ParseException
	{
		DistanceQuery dq = new DistanceQuery(queries, infix, getOpDistance(dToken.image), dToken.image, ordered);
		checkDistanceSubQueries(dq, dToken.image);
		return dq;
	}

	protected SrndQuery getTermQuery(String term, boolean quoted)
	{
		return new SrndTermQuery(term, quoted);
	}

	protected boolean allowedSuffix(String suffixed)
	{
		return suffixed.length() - 1 >= 3;
	}

	protected SrndQuery getPrefixQuery(String prefix, boolean quoted)
	{
		return new SrndPrefixQuery(prefix, quoted, '*');
	}

	protected boolean allowedTruncation(String truncated)
	{
		int nrNormalChars = 0;
		for (int i = 0; i < truncated.length(); i++)
		{
			char c = truncated.charAt(i);
			if (c != '*' && c != '?')
				nrNormalChars++;
		}

		return nrNormalChars >= 3;
	}

	protected SrndQuery getTruncQuery(String truncated)
	{
		return new SrndTruncQuery(truncated, '*', '?');
	}

	public final SrndQuery TopSrndQuery()
		throws ParseException
	{
		SrndQuery q = FieldsQuery();
		jj_consume_token(0);
		return q;
	}

	public final SrndQuery FieldsQuery()
		throws ParseException
	{
		ArrayList fieldNames = OptionalFields();
		SrndQuery q = OrQuery();
		return fieldNames != null ? getFieldsQuery(q, fieldNames) : q;
	}

	public final ArrayList OptionalFields()
		throws ParseException
	{
		ArrayList fieldNames = null;
		Token fieldName;
		for (; jj_2_1(2); fieldNames.add(fieldName.image))
		{
			fieldName = jj_consume_token(22);
			jj_consume_token(16);
			if (fieldNames == null)
				fieldNames = new ArrayList();
		}

		return fieldNames;
	}

	public final SrndQuery OrQuery()
		throws ParseException
	{
		ArrayList queries = null;
		Token oprt = null;
		SrndQuery q = AndQuery();
label0:
		do
			switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
			{
			default:
				jj_la1[0] = jj_gen;
				break label0;

			case 8: // '\b'
				oprt = jj_consume_token(8);
				if (queries == null)
				{
					queries = new ArrayList();
					queries.add(q);
				}
				q = AndQuery();
				queries.add(q);
				break;
			}
		while (true);
		return queries != null ? getOrQuery(queries, true, oprt) : q;
	}

	public final SrndQuery AndQuery()
		throws ParseException
	{
		ArrayList queries = null;
		Token oprt = null;
		SrndQuery q = NotQuery();
label0:
		do
			switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
			{
			default:
				jj_la1[1] = jj_gen;
				break label0;

			case 9: // '\t'
				oprt = jj_consume_token(9);
				if (queries == null)
				{
					queries = new ArrayList();
					queries.add(q);
				}
				q = NotQuery();
				queries.add(q);
				break;
			}
		while (true);
		return queries != null ? getAndQuery(queries, true, oprt) : q;
	}

	public final SrndQuery NotQuery()
		throws ParseException
	{
		ArrayList queries = null;
		Token oprt = null;
		SrndQuery q = NQuery();
label0:
		do
			switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
			{
			default:
				jj_la1[2] = jj_gen;
				break label0;

			case 10: // '\n'
				oprt = jj_consume_token(10);
				if (queries == null)
				{
					queries = new ArrayList();
					queries.add(q);
				}
				q = NQuery();
				queries.add(q);
				break;
			}
		while (true);
		return queries != null ? getNotQuery(queries, oprt) : q;
	}

	public final SrndQuery NQuery()
		throws ParseException
	{
		SrndQuery q = WQuery();
label0:
		do
			switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
			{
			default:
				jj_la1[3] = jj_gen;
				break label0;

			case 12: // '\f'
				Token dt = jj_consume_token(12);
				ArrayList queries = new ArrayList();
				queries.add(q);
				q = WQuery();
				queries.add(q);
				q = getDistanceQuery(queries, true, dt, false);
				break;
			}
		while (true);
		return q;
	}

	public final SrndQuery WQuery()
		throws ParseException
	{
		SrndQuery q = PrimaryQuery();
label0:
		do
			switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
			{
			default:
				jj_la1[4] = jj_gen;
				break label0;

			case 11: // '\013'
				Token wt = jj_consume_token(11);
				ArrayList queries = new ArrayList();
				queries.add(q);
				q = PrimaryQuery();
				queries.add(q);
				q = getDistanceQuery(queries, true, wt, true);
				break;
			}
		while (true);
		return q;
	}

	public final SrndQuery PrimaryQuery()
		throws ParseException
	{
		SrndQuery q;
		switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
		{
		case 13: // '\r'
			jj_consume_token(13);
			q = FieldsQuery();
			jj_consume_token(14);
			break;

		case 8: // '\b'
		case 9: // '\t'
		case 11: // '\013'
		case 12: // '\f'
			q = PrefixOperatorQuery();
			break;

		case 18: // '\022'
		case 19: // '\023'
		case 20: // '\024'
		case 21: // '\025'
		case 22: // '\026'
			q = SimpleTerm();
			break;

		case 10: // '\n'
		case 14: // '\016'
		case 15: // '\017'
		case 16: // '\020'
		case 17: // '\021'
		default:
			jj_la1[5] = jj_gen;
			jj_consume_token(-1);
			throw new ParseException();
		}
		OptionalWeights(q);
		return q;
	}

	public final SrndQuery PrefixOperatorQuery()
		throws ParseException
	{
		switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
		{
		case 8: // '\b'
		{
			Token oprt = jj_consume_token(8);
			List queries = FieldsQueryList();
			return getOrQuery(queries, false, oprt);
		}

		case 9: // '\t'
		{
			Token oprt = jj_consume_token(9);
			List queries = FieldsQueryList();
			return getAndQuery(queries, false, oprt);
		}

		case 12: // '\f'
		{
			Token oprt = jj_consume_token(12);
			List queries = FieldsQueryList();
			return getDistanceQuery(queries, false, oprt, false);
		}

		case 11: // '\013'
		{
			Token oprt = jj_consume_token(11);
			List queries = FieldsQueryList();
			return getDistanceQuery(queries, false, oprt, true);
		}

		case 10: // '\n'
		default:
		{
			jj_la1[6] = jj_gen;
			jj_consume_token(-1);
			throw new ParseException();
		}
		}
	}

	public final List FieldsQueryList()
		throws ParseException
	{
		ArrayList queries = new ArrayList();
		jj_consume_token(13);
		SrndQuery q = FieldsQuery();
		queries.add(q);
label0:
		do
		{
			jj_consume_token(15);
			q = FieldsQuery();
			queries.add(q);
			switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
			{
			case 15: // '\017'
				break;

			default:
				jj_la1[7] = jj_gen;
				break label0;
			}
		} while (true);
		jj_consume_token(14);
		return queries;
	}

	public final SrndQuery SimpleTerm()
		throws ParseException
	{
		switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
		{
		case 22: // '\026'
		{
			Token term = jj_consume_token(22);
			return getTermQuery(term.image, false);
		}

		case 19: // '\023'
		{
			Token term = jj_consume_token(19);
			return getTermQuery(term.image.substring(1, term.image.length() - 1), true);
		}

		case 20: // '\024'
		{
			Token term = jj_consume_token(20);
			if (!allowedSuffix(term.image))
				throw new ParseException((new StringBuilder()).append("Too unrestrictive truncation: ").append(term.image).toString());
			else
				return getPrefixQuery(term.image.substring(0, term.image.length() - 1), false);
		}

		case 21: // '\025'
		{
			Token term = jj_consume_token(21);
			if (!allowedTruncation(term.image))
				throw new ParseException((new StringBuilder()).append("Too unrestrictive truncation: ").append(term.image).toString());
			else
				return getTruncQuery(term.image);
		}

		case 18: // '\022'
		{
			Token term = jj_consume_token(18);
			if (term.image.length() - 3 < 3)
				throw new ParseException((new StringBuilder()).append("Too unrestrictive truncation: ").append(term.image).toString());
			else
				return getPrefixQuery(term.image.substring(1, term.image.length() - 2), true);
		}
		}
		jj_la1[8] = jj_gen;
		jj_consume_token(-1);
		throw new ParseException();
	}

	public final void OptionalWeights(SrndQuery q)
		throws ParseException
	{
		Token weight = null;
label0:
		do
			switch (jj_ntk != -1 ? jj_ntk : jj_ntk())
			{
			default:
				jj_la1[9] = jj_gen;
				break label0;

			case 17: // '\021'
				jj_consume_token(17);
				weight = jj_consume_token(23);
				float f;
				try
				{
					f = Float.valueOf(weight.image).floatValue();
				}
				catch (Exception floatExc)
				{
					throw new ParseException((new StringBuilder()).append("Cannot handle boost value: ").append(weight.image).append(" (").append(floatExc).append(")").toString());
				}
				if ((double)f <= 0.0D)
					throw new ParseException((new StringBuilder()).append("Cannot handle boost value: ").append(weight.image).toString());
				q.setWeight(f * q.getWeight());
				break;
			}
		while (true);
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

	private boolean jj_3_1()
	{
		if (jj_scan_token(22))
			return true;
		return jj_scan_token(16);
	}

	private static void jj_la1_init_0()
	{
		jj_la1_0 = (new int[] {
			256, 512, 1024, 4096, 2048, 0x7c3b00, 6912, 32768, 0x7c0000, 0x20000
		});
	}

	public QueryParser(CharStream stream)
	{
		jj_la1 = new int[10];
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
		for (int i = 0; i < 10; i++)
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
		for (int i = 0; i < 10; i++)
			jj_la1[i] = -1;

		for (int i = 0; i < jj_2_rtns.length; i++)
			jj_2_rtns[i] = new JJCalls();

	}

	public QueryParser(QueryParserTokenManager tm)
	{
		jj_la1 = new int[10];
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
		for (int i = 0; i < 10; i++)
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
		for (int i = 0; i < 10; i++)
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
		boolean la1tokens[] = new boolean[24];
		if (jj_kind >= 0)
		{
			la1tokens[jj_kind] = true;
			jj_kind = -1;
		}
		for (int i = 0; i < 10; i++)
		{
			if (jj_la1[i] != jj_gen)
				continue;
			for (int j = 0; j < 32; j++)
				if ((jj_la1_0[i] & 1 << j) != 0)
					la1tokens[j] = true;

		}

		for (int i = 0; i < 24; i++)
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
	}
}
